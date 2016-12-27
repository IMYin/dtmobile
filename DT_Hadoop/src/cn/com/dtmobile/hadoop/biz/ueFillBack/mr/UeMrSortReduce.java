package cn.com.dtmobile.hadoop.biz.ueFillBack.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.biz.ueFillBack.model.UeMrModel;
import cn.com.dtmobile.hadoop.util.DateUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class UeMrSortReduce extends Reducer<Text , Text, NullWritable, Text> {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		List<UeMrModel> ueList = new ArrayList<UeMrModel>();
		String tail = ",,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		
		for (Text value : values) {
			ueList.add(new UeMrModel(value.toString().split(StringUtils.DELIMITER_INNER_ITEM)));
		}
		Collections.sort(ueList, new Comparator() {
			public int compare(Object a, Object b) {
				int one =  DateUtils.convertSecond(((UeMrModel) a).getTime());
				int two =  DateUtils.convertSecond(((UeMrModel) b).getTime());
				return (int) (one - two);
			}
		});
		
		// 回填 TA和AOA字段
		
		for (UeMrModel value_b : ueList) {
			int t_b = DateUtils.convertSecond(value_b.getTime()) / 1000;
			String TA = "";
			String AOA = "";
			for (UeMrModel value_n : ueList) {
				int t_n = DateUtils.convertSecond(value_n.getTime()) / 1000;
				if ( t_n - t_b <= 30) {
					if (TA.length() == 0) {TA = value_n.getTA();}
					if (AOA.length() == 0) {AOA = value_n.getAoA();}
				}else {continue;}
			}
			value_b.setAoA(AOA);
			value_b.setTA(TA);
		}
		
		// get the number of the column "NeighborCellNum"
		for (UeMrModel value : ueList) {
			if (value.getNeighborCellNumber() ==0) {
				StringBuffer sb = new StringBuffer();
				sb.append(value.toString());
				sb.append(tail+",,,,");
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
			for(int i = 0;i < value.getNeighborCellNumber();i++){
				StringBuffer sb = new StringBuffer();
				sb.append(value.toString());
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(value.getNeighbor()[i]);
				sb.append(tail);
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
		
	}
}