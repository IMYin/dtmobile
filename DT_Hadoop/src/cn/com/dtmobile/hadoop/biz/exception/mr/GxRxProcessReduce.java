package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class GxRxProcessReduce extends Reducer<Text, Text, NullWritable, Text> {
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		for (Text value : values) {
			StringBuffer sb = new StringBuffer();
			String[] words = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM);
			for (int i = 0; i < words.length; i++) {
				sb.append(words[i]);
				sb.append(StringUtils.DELIMITER_VERTICAL);
			}
			if ("3".equals(words[8])
					&& "26".equals(words[2])
					&& ("0".equals(words[21]) || "1".equals(words[21])
							|| "2".equals(words[21]) || "4".equals(words[21]))) {
				if ("0".equals(words[20])) {
					sb.append("5");
				} else if ("1".equals(words[20])) {
					sb.append("7");
				} else {
					sb.append("0");
				}

			} else {
				sb.append("0");
			}
			context.write(NullWritable.get(), new Text(sb.toString()));
		}
	}
}
