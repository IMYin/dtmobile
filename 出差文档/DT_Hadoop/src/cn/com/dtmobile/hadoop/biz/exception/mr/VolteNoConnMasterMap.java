package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteNoConnMasterMap extends Mapper<LongWritable, Text, Text, Text> {

	private final Text key = new Text();

	@Override
	public void map(LongWritable inKey, Text value, Context context) throws IOException, InterruptedException {
		String filePath = context.getInputSplit().toString();
		String[] values = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
		if (filePath.contains(TablesConstants.TABLE_MW_XDR)) {
			key.set(values[3] + StringUtils.DELIMITER_BETWEEN_ITEMS + values[9] + StringUtils.DELIMITER_BETWEEN_ITEMS
					+ TablesConstants.TABLE_MW_XDR);
			context.write(key, value);
		} else if (filePath.contains(TablesConstants.TABLE_S1MME_XDR)) {
			key.set(values[3] + StringUtils.DELIMITER_BETWEEN_ITEMS + values[9] + StringUtils.DELIMITER_BETWEEN_ITEMS
					+ TablesConstants.TABLE_S1MME_XDR);
			context.write(key, value);
		} else if (filePath.contains(TablesConstants.TABLE_LTE_MRO_SOU)) {
			key.set(values[3] + StringUtils.DELIMITER_BETWEEN_ITEMS + values[9] + StringUtils.DELIMITER_BETWEEN_ITEMS
					+ TablesConstants.TABLE_LTE_MRO_SOU);
			context.write(key, value);
		} else {
			return;
		}
	}
}
