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

import cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser.VolteTrainS1mmeMap;
import cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser.VolteTrainS1mmeReduce;

public class VolteTrainS1mmeJob extends Configured implements Tool {
	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: VolteNoConnMasterJob <in> <out>");
			System.exit(2);
		}
	    DistributedCache.addCacheFile(new URI(otherArgs[2]), conf); 
		DistributedCache.addCacheFile(new URI(otherArgs[3]), conf); 
		DistributedCache.addCacheFile(new URI(otherArgs[4]), conf); 
		conf.set("confSpeed", new Path(otherArgs[5]).toString());
		Job job = new Job(conf, "VolteTrainS1mmeJob");
		
		//从参数里传
		job.setJarByClass(VolteTrainS1mmeJob.class);
		job.setMapperClass(VolteTrainS1mmeMap.class);
		job.setReducerClass(VolteTrainS1mmeReduce.class);
		job.setMapOutputKeyClass(Text.class);
	
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		try {
			int returnCode = ToolRunner.run(new VolteTrainS1mmeJob(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
