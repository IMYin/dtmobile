package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.biz.exception.model.MwExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.service.IMSRegistFailAnalyService;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.MwNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.MapTableUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class IMSRegistFailMasterReduce extends
		Reducer<Text, Text, NullWritable, Text> {
	List<MwNew> mwXdrList = null;
	List<LteMroSourceNew> lteMroSourceList = null;
	Map<String, String> exceptionResonMap = new HashMap<String, String>();
	Map<String, String> cellXdrMap = new HashMap<String, String>();
	Map<String, String> lteCellMap = new HashMap<String, String>();

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		MapTableUtils maptables = new MapTableUtils();
		exceptionResonMap = maptables.map_exceptions(context);
		cellXdrMap = maptables.map_cellXdr(context);
		lteCellMap = maptables.map_lte(context);
	}

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		mwXdrList = new ArrayList<MwNew>();
		lteMroSourceList = new ArrayList<LteMroSourceNew>();
		// Iterator<Text> iter = values.iterator();

		for (Text value : values) {
			StringBuffer sb = new StringBuffer();
			sb.append(value.toString());
			int len = value.toString().split(StringUtils.DELIMITER_INNER_ITEM).length;
			String tableTag = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM)[len - 1];
			if (TablesConstants.TABLE_MW_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				mwXdrList.add(new MwNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_LTE_MRO_SOU.equals(tableTag)) {
				sb.delete(sb.length() - 15, sb.length());
				lteMroSourceList.add(new LteMroSourceNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else {
				return;
			}
		}

		IMSRegistFailAnalyService iMSRegistFailAnalyService = new IMSRegistFailAnalyService();
		List<MwExceptionMessage> mwExceptionMessageList = iMSRegistFailAnalyService
				.imsRegistFailAnalyService(mwXdrList, lteMroSourceList,
						cellXdrMap, exceptionResonMap, lteCellMap);
		if (mwExceptionMessageList.size() > 0) {
			
			for (MwExceptionMessage mwExceptionMessage : mwExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("IMS注册失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(mwExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
				System.out.println(mwExceptionMessage.getMwXdr().getMwXdr()
						.getProcedurestarttime());
			}
		}
	}
}
