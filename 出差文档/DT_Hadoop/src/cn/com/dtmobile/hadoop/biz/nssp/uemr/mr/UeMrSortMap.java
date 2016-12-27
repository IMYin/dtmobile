package cn.com.dtmobile.hadoop.biz.nssp.uemr.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.biz.nssp.uemr.util.UeMrKey;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class UeMrSortMap extends Mapper<LongWritable, Text, UeMrKey, Text> {

	private final UeMrKey key = new UeMrKey();
	private final Text value = new Text();

	@Override
	public void map(LongWritable inKey, Text inValue, Context context) throws IOException, InterruptedException {
		String[] values = inValue.toString().split(StringUtils.DELIMITER_INNER_ITEM);
		key.set(values[0] + values[1], Integer.valueOf(values[2]));
		value.set(values[2]+values[3]);
		context.write(key, value);

	}
}
