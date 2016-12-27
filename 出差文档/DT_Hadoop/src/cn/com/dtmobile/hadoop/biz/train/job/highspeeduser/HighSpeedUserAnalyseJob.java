package cn.com.dtmobile.hadoop.biz.train.job.highspeeduser;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser.HighSpeedUserAnalyseMap;
import cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser.HighSpeedUserAnalyseReduce;

public class HighSpeedUserAnalyseJob extends Configured implements Tool {
	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length <= 2) {
			System.err.println("Usage: VolteNoConnMasterJob <in> <out>");
			System.exit(2);
		}
		//use DistributedCache
		DistributedCache.addCacheFile(new URI(otherArgs[10]), conf);  //exception
		DistributedCache.addCacheFile(new URI(otherArgs[11]), conf);  //ltecell
		DistributedCache.addCacheFile(new URI(otherArgs[12]), conf);  //cellXdr
		DistributedCache.addCacheFile(new URI(otherArgs[13]), conf);  //cellXdr
		conf.set("gridLength", new Path(otherArgs[14]).toString());
		Job job = new Job(conf, "HighSpeedUserAnalyseJob");
		//距离阀值
		
		job.setJarByClass(HighSpeedUserAnalyseJob.class);
		job.setMapperClass(HighSpeedUserAnalyseMap.class);
		job.setReducerClass(HighSpeedUserAnalyseReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setNumReduceTasks(8);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[2]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[3]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[4]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[5]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[6]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[7]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[8]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[9]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		try {
			int returnCode = ToolRunner.run(new HighSpeedUserAnalyseJob(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
