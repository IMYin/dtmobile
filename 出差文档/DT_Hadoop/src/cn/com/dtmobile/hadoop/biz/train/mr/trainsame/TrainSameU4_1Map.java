package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class TrainSameU4_1Map extends Mapper<LongWritable, Text, Text, Text> {
	public final Text key = new Text();
	@Override
	protected void map(LongWritable inKey, Text value,Context context)throws IOException, InterruptedException {
		if(value.getLength() > 0){
			String [] values = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
			//需要u3一整天的数据
			//imsi
			key.set(values[0]);
//			System.out.println(values[8]);
			context.write(key,new Text(values[8]));
		}
	}
}
