package cn.com.dtmobile.hadoop.biz.train.job.trainsame;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cn.com.dtmobile.hadoop.biz.train.mr.trainsame.TrainSameU1_1Map;
import cn.com.dtmobile.hadoop.biz.train.mr.trainsame.TrainSameU1_1Reduce;

public class TrainSameU1_1Job extends Configured implements Tool {
	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: VolteNoConnMasterJob <in> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "trainSameU1Job");
		//滑动窗口
		job.getConfiguration().setLong("confRange", 50);
		//imsi分组个数
		job.getConfiguration().setLong("imsiRange", 8);
		//滑动单位
		job.getConfiguration().setLong("step", 5);
		
		job.setJarByClass(TrainSameU1_1Job.class);
		job.setMapperClass(TrainSameU1_1Map.class);
		job.setReducerClass(TrainSameU1_1Reduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		MultipleOutputs.addNamedOutput(job, "u1", TextOutputFormat.class, NullWritable.class, Text.class);
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		try {
			int returnCode = ToolRunner.run(new TrainSameU1_1Job(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
