package cn.com.dtmobile.hadoop.biz.userHome.mr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.com.dtmobile.hadoop.biz.userHome.constants.UserHomeConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

@SuppressWarnings("deprecation")
public class UserHome {
	static class UserHomeMap extends
			Mapper<NullWritable, Text, NullWritable, Text> {
		@Override
		public void map(NullWritable keys, Text values, Context context)
				throws IOException, InterruptedException {
			context.write(NullWritable.get(), values);
		}
	}

	static class UserHomeReduce extends
			Reducer<NullWritable, Text, NullWritable, Text> {
		private BufferedReader phone_addr;
		Map<String, String> cellidStation = new HashMap<String, String>();
		Set<String> h = new HashSet<String>();

		@SuppressWarnings("resource")
		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			// HashMap for 高铁站点对应表
			Path[] paths;
			paths = DistributedCache.getLocalCacheFiles(context
					.getConfiguration());
			// File file = new File("/root/workspace/input/phone-addr.csv");
			// phone_addr = new BufferedReader(new FileReader(file));
			phone_addr = new BufferedReader(new FileReader(paths[0].toString()));
			String lines = null;
			while (StringUtils.isNotEmpty((lines = phone_addr.readLine()))) {
				String[] words = lines.split(StringUtils.DELIMITER_INNER_ITEM4);
				cellidStation.put(words[1], words[0]);
			}
			
			// HashMap the user data of volte.
			BufferedReader volte_data = new BufferedReader(new FileReader(
					paths[1].toString()));
			String lines_volte = null;
			HashSet<String> h = new HashSet<String>();
			while (StringUtils
					.isNotEmpty((lines_volte = volte_data.readLine()))) {
				h.add(lines_volte);
			}
		}

		@Override
		public void reduce(NullWritable keys, Iterable<Text> values,
				Context context) throws IOException, InterruptedException {
			String str[] = null;
			for (Text value : values) {
				StringBuffer sb = new StringBuffer();
				str = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(str[0]);// imsi
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(str[1]);// phone
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				StringBuffer sbCommon = new StringBuffer(sb.toString());
				StringBuffer sbVolte = new StringBuffer(sb.toString());
				if (!"".equals(str[1])) {
					if (cellidStation
							.get(str[1].substring(str[1].length() - 7)).equals(
									UserHomeConstants.SHANXI)) {
						sbCommon.append(UserHomeConstants.Inside4G);
						context.write(NullWritable.get(),
								new Text(sbCommon.toString()));
						if (h.contains(str[0])) {
							sbVolte.append(UserHomeConstants.InsideVolte);
							context.write(NullWritable.get(),
									new Text(sbVolte.toString()));
						} else {
							sbVolte.append(UserHomeConstants.OutsideVolte);
							context.write(NullWritable.get(),
									new Text(sbVolte.toString()));
						}
					} else {
						sbCommon.append(UserHomeConstants.Outside4G);
						context.write(NullWritable.get(),
								new Text(sbCommon.toString()));
						if (h.contains(str[0])) {
							sbVolte.append(UserHomeConstants.InsideVolte);
							context.write(NullWritable.get(),
									new Text(sbVolte.toString()));
						} else {
							sbVolte.append(UserHomeConstants.OutsideVolte);
							context.write(NullWritable.get(),
									new Text(sbVolte.toString()));
						}
					}
				} else {
					return;
				}
			}
		}
	}

	public static void secMR(Path input, Path output, String volte, String phone_addr)
			throws IOException, ClassNotFoundException, InterruptedException,
			URISyntaxException {
		Configuration conf = new Configuration();
		DistributedCache.addCacheFile(new URI(phone_addr), conf);
		DistributedCache.addCacheFile(new URI(volte), conf);
		Job job = new Job(conf, "UserHomeSecondMR");
		job.setMapperClass(UserHomeMap.class);
		job.setReducerClass(UserHomeReduce.class);
		// job.setNumReduceTasks(0);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		job.setJarByClass(UserHome.class);
		// job.setOutputFormatClass(Text.class);
		FileInputFormat.addInputPath(job, input);
		// SequenceFileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileOutputFormat.setOutputPath(job, output);
		job.waitForCompletion(true);
	}
}
