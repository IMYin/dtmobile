package cn.com.dtmobile.hadoop.biz.ueFillBack.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UeMrSortMap extends Mapper<LongWritable, Text, Text, Text> {

	private final Text key = new Text();
	private final Text value = new Text();

	@Override
	public void map(LongWritable inKey, Text inValue, Context context) throws IOException, InterruptedException {
		String[] values = inValue.toString().split(",");
		key.set(values[11] + values[12]);
		value.set(inValue.toString());
		context.write(key, value);

	}
}