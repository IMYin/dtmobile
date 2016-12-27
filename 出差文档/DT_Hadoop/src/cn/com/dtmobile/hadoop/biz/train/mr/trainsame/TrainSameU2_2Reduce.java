package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.model.trainsame.TrainSame;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class TrainSameU2_2Reduce  extends Reducer<Text, Text, NullWritable, Text>{

	public MultipleOutputs<NullWritable,Text> mos;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		mos = new MultipleOutputs<NullWritable,Text>(context);
	}
	static Map<String,List<TrainSame>> u2Map = new HashMap<String,List<TrainSame>>();
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Iterator<Text> iter = values.iterator();
		List<TrainSame> trainSameList = new ArrayList<TrainSame>();
		String [] val;
		while(iter.hasNext()){
			val = iter.next().toString().split(StringUtils.DELIMITER_INNER_ITEM);
			TrainSame trainSame = new TrainSame(val,"");
			trainSame.setGroupName(val[val.length-2]);
			trainSame.setGroupMapping(val[val.length-1]);
			trainSameList.add(trainSame);
		} 
		for (TrainSame trainSame : trainSameList) {
			mos.write("u2",NullWritable.get(), new Text(trainSame.toString()));
		}
	}
	protected void cleanup(Context context) throws IOException ,InterruptedException {
		mos.close();
	}
}
 