package cn.com.dtmobile.hadoop.biz.deletequotation;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DeleteQuo {
	static class DelMap extends Mapper<NullWritable, Text, NullWritable, Text>{
		Text value = new Text();
		@Override
		public void map(NullWritable keys, Text values, Context context)
				throws IOException, InterruptedException {
			value.set(values.toString().replaceAll("\"", ""));
			context.write(NullWritable.get(), value);
		}
	}
	@SuppressWarnings("deprecation")
	public static void secMR(Path input, Path output)
			throws IOException, ClassNotFoundException, InterruptedException,
			URISyntaxException {
		Configuration conf = new Configuration();
		conf.setBoolean("mapred.output.compress", true);
		conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);		
		Job job = new Job(conf, "delete quotation");
		
		job.setMapperClass(DelMap.class);
		job.setNumReduceTasks(0);
		 
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		job.setJarByClass(DeleteQuo.class);
		
		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);
		job.waitForCompletion(true);
	}
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IOException, InterruptedException, URISyntaxException {
		secMR(new Path(args[0]), new Path(args[1]));
	}
}
