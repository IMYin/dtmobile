package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class X2processMap extends Mapper<LongWritable, Text, Text, Text> {
	private final Text key = new Text();

	@Override
	public void map(LongWritable inkey, Text value, Context context)
			throws IOException, InterruptedException {
		if (value.getLength() > 0) {
			String[] words = value.toString().split(
					StringUtils.DELIMITER_COMMA);
			if (words.length > 20) {
				key.set(words[5]);
				context.write(key, value);
			}
		}
	}
}
