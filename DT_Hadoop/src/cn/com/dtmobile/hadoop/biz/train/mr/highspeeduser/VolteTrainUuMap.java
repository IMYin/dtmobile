package cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteTrainUuMap extends Mapper<LongWritable, Text, Text,Text> {

	public final Text vnk = new Text();
	@Override
	protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {
		if(value.getLength() > 0){
			String [] values = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
			Text vnk = new Text();
			vnk.set(values[5]);
			context.write(vnk, new Text(value.toString()));
		}
	}
}
