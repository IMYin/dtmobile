package cn.com.dtmobile.hadoop.biz.LiaoNingFilter.job;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cn.com.dtmobile.hadoop.biz.LiaoNingFilter.mr.DataMap;

@SuppressWarnings("deprecation")
public class DataJob extends Configured implements Tool {
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		conf.setBoolean("mapred.output.compress", true);
		conf.setClass("mapred.output.compression.codec", GzipCodec.class,
				CompressionCodec.class);
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: DataJob <in> <out>");
			System.exit(2);
		}

		// use DistributedCache
		DistributedCache.addCacheFile(new URI(otherArgs[7]), conf); // cellid
		Job job = new Job(conf, "LiaoNingFilter");
		job.setJarByClass(DataJob.class);
		job.setMapperClass(DataMap.class);
//		job.setReducerClass(DataReduce.class);
		job.setNumReduceTasks(0);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		MultipleOutputs.addNamedOutput(job, "volterx", TextOutputFormat.class,
				NullWritable.class, Text.class);
		// MultipleOutputs.addNamedOutput(job, "UU", TextOutputFormat.class,
		// NullWritable.class, Text.class);
		MultipleOutputs.addNamedOutput(job, "s1mmeorgn",
				TextOutputFormat.class, NullWritable.class, Text.class);
		// MultipleOutputs.addNamedOutput(job, "LTE_MR_SOURCE",
		// TextOutputFormat.class, NullWritable.class, Text.class);
		MultipleOutputs.addNamedOutput(job, "voltesv", TextOutputFormat.class,
				NullWritable.class, Text.class);
		MultipleOutputs.addNamedOutput(job, "sgsorgn", TextOutputFormat.class,
				NullWritable.class, Text.class);
		 MultipleOutputs.addNamedOutput(job, "s1uhttporgn", TextOutputFormat.class,
		 NullWritable.class, Text.class);
		 MultipleOutputs.addNamedOutput(job, "volteorgn", TextOutputFormat.class,
		 NullWritable.class, Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[2]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[3]));
		 FileInputFormat.addInputPath(job, new Path(otherArgs[4]));
		 FileInputFormat.addInputPath(job, new Path(otherArgs[5]));
		// FileInputFormat.addInputPath(job, new Path(otherArgs[6]));
		// FileInputFormat.addInputPath(job, new Path(otherArgs[7]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[6]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		try {
			int returnCode = ToolRunner.run(new DataJob(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
