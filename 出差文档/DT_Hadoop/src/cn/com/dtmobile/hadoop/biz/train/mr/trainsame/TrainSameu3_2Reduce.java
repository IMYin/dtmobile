package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.model.trainsame.TrainSame;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class TrainSameu3_2Reduce  extends Reducer<Text, Text, NullWritable, Text>{

	public MultipleOutputs<NullWritable,Text> mos;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		mos = new MultipleOutputs<NullWritable, Text>(context);
	}
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			Iterator<Text> iter = values.iterator();
//			TrainSameService trainSameService = new TrainSameService();
//			Map<String,List<TrainSame>> u2Map = new HashMap<String,List<TrainSame>>();
//			Map<String,List<TrainSame>> u3Map = new HashMap<String,List<TrainSame>>();
			List<TrainSame> trainSameList = new ArrayList<TrainSame>();
			String [] val;
			while(iter.hasNext()){
				val = iter.next().toString().split(StringUtils.DELIMITER_INNER_ITEM);
				TrainSame trainSame = new TrainSame(val,"");
				trainSame.setGroupName(val[val.length-2]);
				trainSame.setGroupMapping(val[val.length-1]);
				trainSameList.add(trainSame);
			} 
			
//			String[] vals;
//			//u3Map key:U3_1 value: List    imsiMap  key u2_1|u2_2 value :u3_1
//			for (Map.Entry<String, String> entry : imsiMapping.entrySet()) {
//				vals = entry.getKey().split(StringUtils.DELIMITER_INNER_ITEM5);
//				String[] mappings = new String[vals.length];
//				if(entry.getKey().contains(StringUtils.DELIMITER_INNER_ITEM5)){
//					mappings = vals;
//				}else{
//					mappings[0] =vals[0];
//				}
//				List<TrainSame> result = trainSameService.addNext(mappings, u2Map);
//				u3Map.put(entry.getValue(),result); 
//			}
			// List | u3_1
//			for (Map.Entry<String, List<TrainSame>> entry : u3Map.entrySet()) {
//				for (TrainSame trainSame : entry.getValue()) {
//					trainSame.setGroupName(entry.getKey());
//					System.out.println(entry.getKey());
//					mos.write("u3",NullWritable.get(), new Text(trainSame.toString()));
//				}
//			}
			
			for (TrainSame trainSame : trainSameList) {
				mos.write("u3",NullWritable.get(), new Text(trainSame.toString()));
			}
		}
	
		protected void cleanup(Context context) throws IOException ,InterruptedException {
			mos.close();
		}
		
	}
