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

import cn.com.dtmobile.hadoop.biz.exception.mr.CommonMaps;
import cn.com.dtmobile.hadoop.biz.exception.mr.EsrvccFailMasterReduce;

@SuppressWarnings("deprecation")
public class EsrvccFailAnalyJob extends Configured implements Tool {
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 5) {
			System.err.println("Usage: EsrvccFailAnalyJob <in> <out>");
			System.exit(2);
		}
		
		//use DistributedCache
//		DistributedCache.addCacheFile(new URI(otherArgs[5]), conf);  //exception
//		DistributedCache.addCacheFile(new URI(otherArgs[6]), conf);  //ltecell
//		DistributedCache.addCacheFile(new URI(otherArgs[7]), conf);  //cellXdr
		
		Job job = new Job(conf, "EsrvccFailAnalyJob");
		job.setJarByClass(EsrvccFailAnalyJob.class);
		job.setMapperClass(CommonMaps.class);
		job.setReducerClass(EsrvccFailMasterReduce.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[2]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[3]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[4]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		try {
			int returnCode = ToolRunner.run(new EsrvccFailAnalyJob(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}