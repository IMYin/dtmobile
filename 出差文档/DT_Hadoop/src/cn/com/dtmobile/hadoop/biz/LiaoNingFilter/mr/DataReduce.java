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
		if (String.valueOf(key).contains(TablesConstants.TABLE_GX)) {
			for (Text text : values) {
				mos.write("GX", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.TABLE_UU_XDR)) {
			for (Text text : values) {
				mos.write("UU", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.TABLE_S1MME_XDR)) {
			for (Text text : values) {
				mos.write("S1", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.TABLE_SV)) {
			for (Text text : values) {
				mos.write("SV", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.TABLE_SGS_XDR)) {
			for (Text text : values) {
				mos.write("SGS", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.TABLE_MW_XDR)) {
			for (Text text : values) {
				mos.write("MW", NullWritable.get(), text);
			}
		} else if (String.valueOf(key).contains(TablesConstants.TABLE_X2)) {
			for (Text text : values) {
				mos.write("X2", NullWritable.get(), text);
			}
		} else if (String.valueOf(key)
				.contains(TablesConstants.TABLE_LTE_MRO_SOU)) {
			for (Text text : values) {
				mos.write("LTE_MRO_SOURCE", NullWritable.get(), text);
			}
		} else {
			return;
		}
	}

	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		mos.close();
	}
}
