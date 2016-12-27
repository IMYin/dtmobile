package cn.com.dtmobile.hadoop.biz.nssp.uemr.mr;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.biz.nssp.uemr.util.UeMrKey;

public class UeMrSortReduce extends Reducer<UeMrKey, Text, Text, Text> {
	private final Text keyVal = new Text();

	@Override
	public void reduce(UeMrKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		keyVal.set(key.getKey());
		for (Text value : values) {
			context.write(keyVal, value);
		}
	}
}

