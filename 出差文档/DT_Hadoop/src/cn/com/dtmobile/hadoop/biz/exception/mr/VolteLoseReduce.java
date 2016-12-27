package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.biz.exception.model.GxExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.service.VolteExceptionLoseAnalyService;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.GxRxNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.MapTableUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteLoseReduce extends Reducer<Text, Text, Text, Text> {
	List<GxRxNew> gxXdrList = null;
	List<S1mmeXdrNew> s1mmeXdrList = null;
	List<LteMroSourceNew> lteMroSourceList = null;
	Map<String, String> exceptionResonMap = new HashMap<String, String>();
	Map<String, String> cellXdrMap = new HashMap<String, String>();

	@Override
	protected void setup(Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		MapTableUtils maptables = new MapTableUtils();
		exceptionResonMap = maptables.map_exceptions(context);
		cellXdrMap = maptables.map_cellXdr(context);
	}

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		gxXdrList = new ArrayList<GxRxNew>();
		s1mmeXdrList = new ArrayList<S1mmeXdrNew>();
		lteMroSourceList = new ArrayList<LteMroSourceNew>();
		// Iterator<Text> iter = values.iterator();

		for (Text value : values) {
			StringBuffer sb = new StringBuffer();
			sb.append(value.toString());
			int len = value.toString().split(StringUtils.DELIMITER_INNER_ITEM).length;
			String tableTag = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM)[len - 1];
			if (TablesConstants.TABLE_RX_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				gxXdrList.add(new GxRxNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_S1MME_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 6, sb.length());
				s1mmeXdrList.add(new S1mmeXdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_LTE_MRO_SOU.equals(tableTag)) {
				sb.delete(sb.length() - 15, sb.length());
				lteMroSourceList.add(new LteMroSourceNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else {
				return;
			}
		}

		VolteExceptionLoseAnalyService volteExceptionLoseAnalyService = new VolteExceptionLoseAnalyService();
		List<GxExceptionMessage> gxExceptionMessageList = volteExceptionLoseAnalyService
				.volteNoConnService(gxXdrList, s1mmeXdrList, lteMroSourceList,
						cellXdrMap, exceptionResonMap);
		if (gxExceptionMessageList.size() > 0) {
			for (GxExceptionMessage gxExceptionMessage : gxExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("VOLTE掉话");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(gxExceptionMessage.toString());
				context.write(null, new Text(sb.toString()));
			}
		}
	}
}
