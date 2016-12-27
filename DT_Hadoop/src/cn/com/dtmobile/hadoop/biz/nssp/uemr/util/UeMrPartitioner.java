package cn.com.dtmobile.hadoop.biz.nssp.uemr.util;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class UeMrPartitioner extends Partitioner<UeMrKey, IntWritable> {
	@Override
	public int getPartition(UeMrKey key, IntWritable value, int numPartitions) {
		return (key.hashCode()&Integer.MAX_VALUE) % numPartitions;
	}
}
