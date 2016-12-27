package cn.com.dtmobile.hadoop.biz.train.job.numberiden;

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

import cn.com.dtmobile.hadoop.biz.train.mr.numberiden.NumberIdenMap;
import cn.com.dtmobile.hadoop.biz.train.mr.numberiden.NumberIdenReduce;

public class NumberIdenJob extends Configured implements Tool {
	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 5) {
			System.err.println("Usage: VolteNoConnMasterJob <in> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "numberIdenJob");
		
		job.getConfiguration().setLong("size", 10);

		
		job.setJarByClass(NumberIdenJob.class);
		job.setMapperClass(NumberIdenMap.class);
		job.setReducerClass(NumberIdenReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[4]));
		MultipleOutputs.addNamedOutput(job, "numberIden", TextOutputFormat.class, NullWritable.class, Text.class);
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		try {
			int returnCode = ToolRunner.run(new NumberIdenJob(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
