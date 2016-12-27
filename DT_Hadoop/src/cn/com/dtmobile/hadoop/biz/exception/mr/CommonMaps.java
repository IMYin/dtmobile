package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class CommonMaps extends Mapper<LongWritable, Text, Text, Text> {

	private final Text key = new Text();

	@Override
	public void map(LongWritable inKey, Text value, Context context)
			throws IOException, InterruptedException {
		if (value.getLength() > 0) {
			String filePath = context.getInputSplit().toString().toUpperCase();
			if (filePath.contains(TablesConstants.HIVE_MW)) {
				String[] values = value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM);
				if (!"0".equals(values[values.length-1])) {
					key.set(values[5]);
					context.write(key, new Text(value.toString()
							+ StringUtils.DELIMITER_VERTICAL
							+ TablesConstants.HIVE_MW));
				}
			} else if (filePath.contains(TablesConstants.HIVE_S1MME)) {
				String[] values = value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM);
				if (!"0".equals(values[values.length-1])) {
					key.set(values[5]);
					context.write(key, new Text(value.toString()
							+ StringUtils.DELIMITER_VERTICAL
							+ TablesConstants.HIVE_S1MME));
				}
			} else if (filePath.contains(TablesConstants.HIVE_LTE_MRO_SOURCE)) {
				value = new Text(value.toString().replaceAll(",", "|"));
				String[] values = value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM);
				key.set(values[97]);
				context.write(key, new Text(value.toString()
						+ StringUtils.DELIMITER_VERTICAL
						+ TablesConstants.HIVE_LTE_MRO_SOURCE));
			} else if (filePath.contains(TablesConstants.HIVE_UU)) {
//				value = new Text(value.toString().replaceAll(",", "|"));
				String[] values = value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM);
				if (!"0".equals(values[values.length-1])) {
					key.set(values[5]);
					context.write(key, new Text(value.toString()
							+ StringUtils.DELIMITER_VERTICAL
							+ TablesConstants.HIVE_UU));
				}
			} else if (filePath.contains(TablesConstants.HIVE_GXRX)) {
				String[] values = value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM);
				if (!"0".equals(values[values.length-1])) {
					key.set(values[5]);
					context.write(key, new Text(value.toString()
							+ StringUtils.DELIMITER_VERTICAL
							+ TablesConstants.HIVE_GXRX));
				}
			} else if (filePath.contains(TablesConstants.HIVE_SV)) {
				String[] values = value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM);
				key.set(values[5]);
				context.write(key, new Text(value.toString()
						+ StringUtils.DELIMITER_VERTICAL
						+ TablesConstants.HIVE_SV));
			} else if (filePath.contains(TablesConstants.HIVE_X2)) {
//				value = new Text(value.toString().replaceAll(",", "|"));
				String[] values = value.toString().split(
						StringUtils.DELIMITER_INNER_ITEM);
				if (!"0".equals(values[values.length-1])) {
					key.set(values[5]);
					context.write(key, new Text(value.toString()
							+ StringUtils.DELIMITER_VERTICAL
							+ TablesConstants.HIVE_X2));
				}
			} else {
				return;
			}
		}
	}
}
