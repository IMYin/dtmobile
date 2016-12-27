package cn.com.dtmobile.hadoop.biz.train.util;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class VolteBusiNewPartition extends Partitioner<VolteBusiNewKey, LongWritable> {
	@Override
	public int getPartition(VolteBusiNewKey key, LongWritable value, int reduceNum) {
		return (key.getImsi().hashCode() & Integer.MAX_VALUE) % reduceNum;
	}
}
