package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class TrainSameU1_1Map extends Mapper<LongWritable, Text, Text, Text> {
	public final Text key = new Text();
	@Override
	protected void map(LongWritable inKey, Text value,Context context)throws IOException, InterruptedException {
		if(value.getLength() > 0){
			String [] values = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
			//cellid+targetcellid+dir 同一切换点 上下行需要分开计算 不是一趟车
			key.set(values[1] + StringUtils.DELIMITER_INNER_ITEM + values[2]  + StringUtils.DELIMITER_INNER_ITEM + values[12]);
//			System.out.println(values[1] +"\t" +values[2] +"\t" +values[12]);
			context.write(key,new Text(value.toString()));
		}
	}
}
