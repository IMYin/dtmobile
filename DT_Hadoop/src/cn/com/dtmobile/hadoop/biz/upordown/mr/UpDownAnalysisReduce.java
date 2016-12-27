package cn.com.dtmobile.hadoop.biz.upordown.mr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.biz.upordown.constants.EndConstants;
import cn.com.dtmobile.hadoop.biz.upordown.model.S1MME_NEW;
import cn.com.dtmobile.hadoop.biz.upordown.model.S1MME_reduce;
import cn.com.dtmobile.hadoop.biz.upordown.model.U2Goal;
import cn.com.dtmobile.hadoop.biz.upordown.model.U2_new;
import cn.com.dtmobile.hadoop.biz.upordown.model.U3_new;
import cn.com.dtmobile.hadoop.biz.upordown.model.U4_new;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;
public class UpDownAnalysisReduce extends
		Reducer<Text, Text, NullWritable, Text> {
	Map<String, String> cellidStation = new HashMap<String, String>();
	private BufferedReader data;

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		// HashMap for \u9ad8\u94c1\u7ad9\u70b9\u5bf9\u5e94\u8868
		// Path[] paths;
		File file = new File("/root/workspace/all_test/upordown/t_process");
		data = new BufferedReader(new FileReader(file));
		String lines = null;
		while (StringUtils.isNotEmpty((lines = data.readLine()))) {
			String[] words = lines.split(StringUtils.DELIMITER_INNER_ITEM);
			cellidStation.put(words[1], words[0]);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		List<U4_new> u4List = new ArrayList<U4_new>();
		List<U3_new> u3List = new ArrayList<U3_new>();
		List<U2_new> u2List = new ArrayList<U2_new>();
		List<S1MME_reduce> s1mmeList = new ArrayList<S1MME_reduce>();
		for (Text value : values) {
			if (value.toString().contains(EndConstants.U4END)) {
				u4List.add(new U4_new(value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (value.toString().contains(EndConstants.U3END)) {
				u3List.add(new U3_new(value.toString().split(StringUtils.DELIMITER_INNER_ITEM)));
			} else if (value.toString().contains(EndConstants.U2END)) {
				u2List.add(new U2_new(value.toString().split(StringUtils.DELIMITER_INNER_ITEM)));
			} else if (value.toString().contains(
					TablesConstants.TABLE_S1MME_XDR)) {
				s1mmeList.add(new S1MME_reduce(value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			}
		}

		// collect data of u2 by u4.
		for (U4_new value4 : u4List) {
			List<U2Goal> u2GoalList = new ArrayList<U2Goal>();
			List<S1MME_reduce> s1mme_Goal_up = new ArrayList<S1MME_reduce>();
			List<S1MME_reduce> s1mme_Goal_down = new ArrayList<S1MME_reduce>();
			String relation_4_3 = value4.getGroupmapping();
			for (U3_new value3 : u3List) {
				if (relation_4_3.contains(value3.getGroupname())) {
					String relation_3_2 = value3.getGroupmapping();
					for (U2_new value2 : u2List) {
						if (relation_3_2.contains(value2.getGroupname())) {
							u2GoalList.add(new U2Goal(value2.toString().split(
									StringUtils.DELIMITER_INNER_ITEM)));
						}
					}
				}
			}

			// Sort by start time
			Collections.sort(u2GoalList, new Comparator() {
				public int compare(Object a, Object b) {
					Long one = ((U2Goal) a).getProcedurestarttime();
					Long two = ((U2Goal) b).getProcedurestarttime();
					return (int) (one - two);
				}
			});
			Long fir_time = (long) 0;
			if (u2GoalList.size()>0) {
				fir_time = u2GoalList.get(0).getProcedurestarttime();
			}else {
				continue;
			}
			Long last_time = u2GoalList.get(u2GoalList.size() - 1)
					.getProcedurestarttime();

			for (S1MME_reduce s1mme : s1mmeList) {
				if ("5".equals(s1mme.getProcedureType())
						&& "0".equals(s1mme.getProcedureStatus())
						&& cellidStation.containsKey(s1mme.getCellId())) {
					if (s1mme.getProcedureStartTime() - fir_time < context
							.getConfiguration().getInt("SECOND_T", 5) * 1000) {
						s1mme_Goal_up.add(s1mme);
					} else if (last_time - s1mme.getProcedureStartTime() < context
							.getConfiguration().getInt("SECOND_T", 5) * 1000) {
						s1mme_Goal_down.add(s1mme);
					}
				}
			}

			// Sort by start time
			Long start_time_up = (long) 0;
			Long start_time_down = (long) 0;
			String station_up = "";
			String station_down = "";

			// Determine get in information.
			if (s1mme_Goal_up.size() > 0) {
				Collections.sort(s1mme_Goal_up, new Comparator() {
					public int compare(Object a, Object b) {
						Long one = ((S1MME_NEW) a).getProcedureStartTime();
						Long two = ((S1MME_NEW) b).getProcedureStartTime();
						return (int) (one - two);
					}
				});
				S1MME_reduce determined_xdr_up = s1mme_Goal_up.get(s1mme_Goal_down
						.size() - 1);
				start_time_up = determined_xdr_up.getProcedureStartTime();
				station_up = cellidStation.get(determined_xdr_up.getCellId());
			} else {
				start_time_up = fir_time;
				if (value4.getUpordown() == 1) {
					station_up = EndConstants.STATION_OF_UP_1;
				} else if (value4.getUpordown() == 0) {
					station_up = EndConstants.STATION_OF_UP_0;
				}
			}

			// Determine get off information.
			if (s1mme_Goal_down.size() > 0) {
				Collections.sort(s1mme_Goal_down, new Comparator() {
					public int compare(Object a, Object b) {
						Long one = ((S1MME_NEW) a).getProcedureStartTime();
						Long two = ((S1MME_NEW) b).getProcedureStartTime();
						return (int) (one - two);
					}
				});

				S1MME_reduce determined_xdr_down = s1mme_Goal_down
						.get(s1mme_Goal_down.size() - 1);
				start_time_down = determined_xdr_down.getProcedureStartTime();
				station_down = cellidStation.get(determined_xdr_down
						.getCellId());

			} else {
				// Determine the direction of the train.
				start_time_up = last_time;
				if (value4.getUpordown() == 1) {
					station_up = EndConstants.STATION_OF_DOWN_1;
				} else if (value4.getUpordown() == 0) {
					station_down = EndConstants.STATION_OF_DOWN_0;
				}
			}
			StringBuffer sb = new StringBuffer();
			sb.append(value4.getImsi());
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
			sb.append(value4.getXdrid());
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
			sb.append(value4.getGroupname());
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
			sb.append(start_time_up);
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
			sb.append(station_up);
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
			sb.append(start_time_down);
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
			sb.append(station_down);
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
			context.write(NullWritable.get(), new Text(sb.toString()));
		}
	}
}
