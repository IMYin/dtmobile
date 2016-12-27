package cn.com.dtmobile.hadoop.biz.exception.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cn.com.dtmobile.hadoop.biz.exception.mr.MwProcessMap;
import cn.com.dtmobile.hadoop.biz.exception.mr.MwProcessReduce;

public class MwProcessJob extends Configured implements Tool{
	@SuppressWarnings("deprecation")
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: MwProcessJob <in> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "MwProcessJob");
		job.setJarByClass(MwProcessJob.class);
		job.setMapperClass(MwProcessMap.class);
		job.setReducerClass(MwProcessReduce.class);
		job.setNumReduceTasks(15);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
	public static void main(String[] args) {
		try {
			int returnCode = ToolRunner.run(new MwProcessJob(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
