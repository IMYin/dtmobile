package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.util.StringUtils;

/**
 * 
 * @author root words[9] = procedureType words[12] = procedureStatus words[19] =
 *         failureCause
 */
public class X2processReduce extends Reducer<Text, Text, NullWritable, Text> {
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		for (Text value : values) {
			StringBuffer sb = new StringBuffer();
			String[] words = value.toString().split(
					StringUtils.DELIMITER_COMMA);
			for (int i = 1; i < words.length; i++) {
				sb.append(words[i]);
				sb.append(StringUtils.DELIMITER_VERTICAL);
			}
			if ("1".equals(words[9]) && ("1".equals(words[12])||"255".equals(words[12]))
					&& (!"1000".equals(words[19]) || "".equals(words[19]))) {
				sb.append("10");
			} else {
				sb.append("0");
			}
			context.write(NullWritable.get(), new Text(sb.toString()));
		}
	}
}
