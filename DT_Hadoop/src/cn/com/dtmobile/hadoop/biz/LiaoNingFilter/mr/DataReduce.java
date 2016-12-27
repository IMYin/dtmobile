package cn.com.dtmobile.hadoop.biz.LiaoNingFilter.mr;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.constants.TablesConstants;

@SuppressWarnings("rawtypes")
public class DataReduce extends Reducer<Text, Text, NullWritable, Text> {
	public MultipleOutputs mos;

	@SuppressWarnings("unchecked")
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		mos = new MultipleOutputs(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		if (String.valueOf(key).contains(TablesConstants.FILTER_RX)) {
			for (Text text : values) {
				mos.write("volterx", NullWritable.get(), text);
			}
		} else if (String.valueOf(key)
				.contains(TablesConstants.FILTER_S1MME)) {
			for (Text text : values) {
				mos.write("s1mmeorgn", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.FILTER_SV)) {
			for (Text text : values) {
				mos.write("voltesv", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.FILTER_SGS)) {
			for (Text text : values) {
				mos.write("sgsorgn", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.FILTER_HTTP)){
			for (Text text : values) {
				mos.write("s1uhttporgn", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.FILTER_VOLTE)){
			for (Text text : values) {
				mos.write("volteorgn", NullWritable.get(), text);
			}
		}else {
			return;
		}
	}

	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		mos.close();
	}
}
