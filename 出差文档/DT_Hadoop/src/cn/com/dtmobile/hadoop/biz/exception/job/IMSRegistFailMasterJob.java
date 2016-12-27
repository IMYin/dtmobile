package cn.com.dtmobile.hadoop.biz.exception.job;

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

import cn.com.dtmobile.hadoop.biz.exception.mr.CommonMaps;
import cn.com.dtmobile.hadoop.biz.exception.mr.IMSRegistFailMasterReduce;

@SuppressWarnings("deprecation")
public class IMSRegistFailMasterJob extends Configured implements Tool {
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: IMSRegistFailMasterJob <in> <out>");
			System.exit(2);
		}
		
		//use DistributedCache
		DistributedCache.addCacheFile(new URI(otherArgs[3]), conf);  //exception
		DistributedCache.addCacheFile(new URI(otherArgs[4]), conf);  //ltecell
		DistributedCache.addCacheFile(new URI(otherArgs[5]), conf);  //cellXdr
		Job job = new Job(conf, "IMSRegistFailMasterJob");
		job.setJarByClass(IMSRegistFailMasterJob.class);
		job.setMapperClass(CommonMaps.class);
		job.setReducerClass(IMSRegistFailMasterReduce.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		try {
			int returnCode = ToolRunner.run(new IMSRegistFailMasterJob(), args);
			System.exit(returnCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
