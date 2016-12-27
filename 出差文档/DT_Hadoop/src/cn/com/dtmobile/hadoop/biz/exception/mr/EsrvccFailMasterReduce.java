package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.biz.exception.model.S1mmeExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.service.EsrvccFailAnalyService;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.SvNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.MapTableUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class EsrvccFailMasterReduce extends
		Reducer<Text, Text, NullWritable, Text> {
	List<S1mmeXdrNew> s1mmeXdrList = null;
	List<SvNew> svList = null;
	List<UuXdrNew> uuList = null;
	List<LteMroSourceNew> lteMroSourceList = null;
	Map<String, String> exceptionResonMap = new HashMap<String, String>();

	@Override
	protected void setup(Reducer<Text, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		MapTableUtils maptables = new MapTableUtils();
		exceptionResonMap = maptables.map_exceptions(context);
	}

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		svList = new ArrayList<SvNew>();
		uuList = new ArrayList<UuXdrNew>();
		s1mmeXdrList = new ArrayList<S1mmeXdrNew>();
		lteMroSourceList = new ArrayList<LteMroSourceNew>();
		// Iterator<Text> iter = values.iterator();

		for (Text value : values) {
			StringBuffer sb = new StringBuffer();
			sb.append(value.toString());
			int len = value.toString().split(StringUtils.DELIMITER_INNER_ITEM).length;
			String tableTag = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM)[len - 1];
			if (TablesConstants.TABLE_UU_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				uuList.add(new UuXdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_S1MME_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 6, sb.length());
				s1mmeXdrList.add(new S1mmeXdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_LTE_MRO_SOU.equals(tableTag)) {
				sb.delete(sb.length() - 15, sb.length());
				lteMroSourceList.add(new LteMroSourceNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_SV.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				svList.add(new SvNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else {
				return;
			}

		}

		EsrvccFailAnalyService esrvccFailAnalyService = new EsrvccFailAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList = esrvccFailAnalyService
				.esrvccFailAnalyService(svList, uuList, s1mmeXdrList,
						lteMroSourceList, exceptionResonMap);
		if (s1mmeExceptionMessageList.size() > 0) {
			
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("ESRVCC失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
	}
}
