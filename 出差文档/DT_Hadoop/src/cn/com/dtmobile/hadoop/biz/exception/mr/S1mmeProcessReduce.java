package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.util.StringUtils;

/**
 * process the table s1mme, delete parentxdrid and fillback etype.
 * 
 * @author suntianxu
 * 
 *         words[9]: proceduretype
 * 
 *         words[10]: procedureStartTime
 * 
 *         words[12]: procedurestatus
 * 
 *         words[13]: requestCause
 * 
 *         words[15]: keyword1
 * 
 */
public class S1mmeProcessReduce extends Reducer<Text, Text, NullWritable, Text> {
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		List<Text> rows = new ArrayList<Text>();
		for (Text value : values) {
			rows.add(new Text(value));
		}

		for (Text value : rows) {
			StringBuffer sb = new StringBuffer();
			String[] words = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM);
			for (int i = 0; i < words.length; i++) {
				sb.append(words[i]);
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
			}
			if ("1".equals(words[12])||"255".equals(words[12])) {
				if ("1".equals(words[9])) {
					sb.append("2");
				} else if ("2".equals(words[9])) {
					sb.append("3");
				} else if ("5".equals(words[9])) {
					sb.append("8");
				} else if ("16".equals(words[9]) && "3".equals(words[15])) {
					sb.append("13");
				} else {
					sb.append("0");
				}
			} else if ("20".equals(words[9]) && "0".equals(words[15])
					&& (!"20".equals(words[13])
							&&!"23".equals(words[13])
							&&!"24".equals(words[13])
							&&!"28".equals(words[13])
							&&!"512".equals(words[13])
							&&!"514".equals(words[13]))) {
				sb.append("9");
			} else if ("16".equals(words[9]) && "3".equals(words[15])) {
				sb.append("14");
			} else if ("16".equals(words[9]) && "1".equals(words[15])) {
				if ("1".equals(words[12])) {
					sb.append("10");
				} else if ("0".equals(words[12])||"255".equals(words[12])) {
					int num = 0;
					for (Text value_t : rows) {
						String[] words_t = value_t.toString().split(
								StringUtils.DELIMITER_INNER_ITEM);
						if ("20".equals(words_t[12])
								&& "2".equals(words[13])
								&& words[34].equals(words_t[34])
								&& (Integer.parseInt(words_t[10]) > Integer
										.parseInt(words[10]) && Integer
										.parseInt(words_t[10])
										- Integer.parseInt(words[10]) < 5000)) {
							num++;
							break;
						}
					}
					if (num > 0) {
						sb.append("10");
					} else {
						sb.append("0");
					}
				} else {
					sb.append("0");
				}
			} else if ("20".equals(words[9]) && ("21".equals(words[13])||"26".equals(words[13])||"28".equals(words[13]))) {
				int num = 0;
				for (Text value_t : rows) {
					String[] words_t = value_t.toString().split(
							StringUtils.DELIMITER_INNER_ITEM);
					if (("1".equals(words[9])||"2".equals(words[9])||"5".equals(words[9]))
							&& (Integer.parseInt(words_t[10]) > Integer
									.parseInt(words[10]) && Integer
									.parseInt(words_t[10])
									- Integer.parseInt(words[10]) < 3000)) {
						num ++;
						break;
					}
				}
				if (num > 0) {
					sb.append("14");
				}else {
					sb.append("0");
				}
			} else {
				sb.append("0");
			}
			context.write(NullWritable.get(), new Text(sb.toString()));
		}
	}
}
