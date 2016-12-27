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
import cn.com.dtmobile.hadoop.biz.exception.service.Lose4GAnalyService;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.MapTableUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class Lose4GReduce extends Reducer<Text, Text, NullWritable, Text> {
	List<S1mmeXdrNew> s1mmeXdrList = null;
	List<LteMroSourceNew> lteMroSourceList = null;
	List<UuXdrNew> uuXdrList = null;
	Map<String, String> exceptionResonMap = new HashMap<String, String>();
	Map<String, String> cellXdrMap = new HashMap<String, String>();

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		MapTableUtils maptables = new MapTableUtils();
		cellXdrMap = maptables.map_cellXdr(context);
		exceptionResonMap = maptables.map_exceptions(context);
	}

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		s1mmeXdrList = new ArrayList<S1mmeXdrNew>();
		uuXdrList = new ArrayList<UuXdrNew>();
		lteMroSourceList = new ArrayList<LteMroSourceNew>();
		// Iterator<Text> iter = values.iterator();

		for (Text value : values) {
			int len = value.toString().split(StringUtils.DELIMITER_INNER_ITEM).length;
			String tableTag = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM)[len - 1];
			StringBuffer sb = new StringBuffer();
			sb.append(value.toString());
			if (TablesConstants.TABLE_S1MME_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 6, sb.length());
				s1mmeXdrList.add(new S1mmeXdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_LTE_MRO_SOU.equals(tableTag)) {
				sb.delete(sb.length() - 15, sb.length());
				lteMroSourceList.add(new LteMroSourceNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_UU_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				uuXdrList.add(new UuXdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else {
				return;
			}
		}

		Lose4GAnalyService lose4GAnalyService = new Lose4GAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList = lose4GAnalyService
				.Lose4GService(s1mmeXdrList, lteMroSourceList, uuXdrList,
						cellXdrMap, exceptionResonMap);
		if (s1mmeExceptionMessageList.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("4G掉线");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
	}
}
