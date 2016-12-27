package cn.com.dtmobile.hadoop.biz.train.mr.numberiden;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class NumberIdenMap extends Mapper<LongWritable, Text, Text, Text> {

	public final Text key = new Text();
	@Override
	protected void map(LongWritable inKey, Text value,Context context)throws IOException, InterruptedException {
		if(value.getLength() > 0){
			String [] values = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
			//groupName
			key.set(values[values.length-2]);
			context.write(key,new Text(value.toString()));
		}
	}
}
