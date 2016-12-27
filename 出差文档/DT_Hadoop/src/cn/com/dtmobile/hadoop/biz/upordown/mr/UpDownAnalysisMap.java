package cn.com.dtmobile.hadoop.biz.upordown.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.biz.upordown.constants.EndConstants;
import cn.com.dtmobile.hadoop.biz.upordown.model.S1MME_NEW;
import cn.com.dtmobile.hadoop.biz.upordown.model.U2Xdr;
import cn.com.dtmobile.hadoop.biz.upordown.model.U3Xdr;
import cn.com.dtmobile.hadoop.biz.upordown.model.U4Xdr;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class UpDownAnalysisMap extends Mapper<LongWritable, Text, Text, Text> {
	private final Text key = new Text();

	@Override
	public void map(LongWritable inKey, Text value, Context context)
			throws IOException, InterruptedException {
		String filePath = context.getInputSplit().toString();
		String[] values = value.toString().split(
				StringUtils.DELIMITER_INNER_ITEM);
		if (filePath.contains(TablesConstants.TABLE_U4)) {
			U4Xdr u4 = new U4Xdr(values);
			key.set(u4.getImsi());
			context.write(key, new Text(u4.toString()
					+ StringUtils.DELIMITER_INNER_ITEM + EndConstants.U4END));
		} else if (filePath.contains(TablesConstants.TABLE_U3)) {
			U3Xdr u3 = new U3Xdr(values);
			key.set(u3.getImsi());
			context.write(key, new Text(u3.toString()
					+ StringUtils.DELIMITER_INNER_ITEM + EndConstants.U3END));
		} else if (filePath.contains(TablesConstants.TABLE_U2)) {
			U2Xdr u2 = new U2Xdr(values);
			key.set(u2.getImsi());
			context.write(key, new Text(u2.toString()
					+ StringUtils.DELIMITER_INNER_ITEM + EndConstants.U2END));
		} else if (filePath.contains(TablesConstants.TABLE_S1MME_XDR)) {
			S1MME_NEW s1_new = new S1MME_NEW(values);
			key.set(s1_new.getImsi());
			context.write(key, new Text(s1_new.toString()
					+ StringUtils.DELIMITER_INNER_ITEM
					+ TablesConstants.TABLE_S1MME_XDR));
		} else {
			return;
		}
	}
}
