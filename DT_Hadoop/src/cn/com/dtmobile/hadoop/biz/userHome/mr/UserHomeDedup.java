package cn.com.dtmobile.hadoop.biz.userHome.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;


public class UserHomeDedup {
	
	static class DedupMap extends Mapper<LongWritable, Text, Text, NullWritable> {
		private final Text key = new Text();
		
		@Override
		public void map(LongWritable inKey, Text value, Context context)
				throws IOException, InterruptedException {
			String filePath = context.getInputSplit().toString();
			String[] values = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM);
			if (filePath.contains(TablesConstants.TABLE_VOLTE_GT_BUSI_USER_DATA)) {
				key.set(values[0] + StringUtils.DELIMITER_INNER_ITEM + values[2]);
				context.write(key, NullWritable.get());
			} else if (filePath
					.contains(TablesConstants.TABLE_VOLTE_GT_FREE_USER_DATA)) {
				key.set(values[5] + StringUtils.DELIMITER_INNER_ITEM + values[7]);
				context.write(key, NullWritable.get());
			} else if (filePath.contains(TablesConstants.TABLE_VOLTE_GTUSER_DATA)) {
				key.set(values[0]);
				context.write(key, NullWritable.get());
			}else {
				return;
		}
	}
	}
		@SuppressWarnings("rawtypes")
	static class DedupReduce extends Reducer<Text,Text ,Text,NullWritable>{
		public MultipleOutputs mos;
		@SuppressWarnings("unchecked")
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			mos = new MultipleOutputs(context);
		}
		@SuppressWarnings("unchecked")
		@Override
		public void reduce(Text key,Iterable<Text> values, Context context)throws IOException, InterruptedException {
			if (String.valueOf(key).contains(StringUtils.DELIMITER_INNER_ITEM)) {
				mos.write("FourG", key, NullWritable.get());
			}else {
				mos.write("Volte", key, NullWritable.get());
			}
		}
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			mos.close();
		}
	}
	@SuppressWarnings("deprecation")
	public static void firMR(Path input_busy, Path input_free,Path input_volte, Path output) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "UserHomeFirstMR");
		job.setMapperClass(DedupMap.class);
		job.setReducerClass(DedupReduce.class);
//		job.setNumReduceTasks(0);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		job.setJarByClass(UserHomeDedup.class);
//		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		MultipleOutputs.addNamedOutput(job, "FourG", TextOutputFormat.class, Text.class, NullWritable.class);
		MultipleOutputs.addNamedOutput(job, "Volte", TextOutputFormat.class, Text.class, NullWritable.class);
		
		FileInputFormat.addInputPath(job, input_busy);
		FileInputFormat.addInputPath(job, input_free);
		FileInputFormat.addInputPath(job, input_volte);
		FileOutputFormat.setOutputPath(job, output);
		job.waitForCompletion(true);
	}
}
