package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class UuProcessMap extends Mapper<LongWritable, Text, Text, Text>{
	private final Text key = new Text();
	@Override
	public void map(LongWritable inkey, Text value, Context context) throws IOException,InterruptedException{
		if (value.getLength() > 0) {
			String[] words = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
			key.set(words[6]);
			context.write(key, value);
		}
	}
}
