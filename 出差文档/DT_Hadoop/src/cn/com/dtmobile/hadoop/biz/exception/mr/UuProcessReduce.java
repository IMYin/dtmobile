package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.util.StringUtils;

/**
 * 
 * @author root 
 * 
 * words[9] = procedureType 
 * 
 * words[12] = procedureStatus 
 * 
 */

public class UuProcessReduce extends Reducer<Text, Text, NullWritable, Text> {
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		for (Text value : values) {
			StringBuffer sb = new StringBuffer();
			String[] words = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM);
			for (int i = 0; i < words.length; i++) {
				sb.append(words[i]);
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
			}
			if ("7".equals(words[9]) && ("1".equals(words[13])||"255".equals(words[13]))) {
				sb.append("10");
			} else {
				sb.append("0");
			}
			context.write(NullWritable.get(), new Text(sb.toString()));
		}
	}
}
