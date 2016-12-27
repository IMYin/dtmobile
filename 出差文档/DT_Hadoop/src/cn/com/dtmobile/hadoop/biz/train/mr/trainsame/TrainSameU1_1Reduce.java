package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.model.trainsame.TrainSame;
import cn.com.dtmobile.hadoop.biz.train.service.trainsame.TrainSameService;
import cn.com.dtmobile.hadoop.util.StringUtils;
import cn.com.dtmobile.hadoop.util.UuComparator;

public class TrainSameU1_1Reduce extends Reducer<Text, Text, NullWritable, Text> {
	
	public MultipleOutputs<NullWritable,Text> mos;
	
	protected void setup(Context context) throws IOException ,InterruptedException {
		mos = new MultipleOutputs<NullWritable,Text>(context);
	}
	
	static int sn;
	@SuppressWarnings("unchecked")
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		List<TrainSame> trainSameList = new ArrayList<TrainSame>();
		TrainSame trainSame = null;
		String str;
		for(Text value :values){
			str = value.toString();
			trainSame  = new TrainSame(str.split(StringUtils.DELIMITER_INNER_ITEM));
			trainSameList.add(trainSame);
		}
		UuComparator comparator = new UuComparator();
		comparator.setFieldName("getProcedureStartTime");
		Collections.sort(trainSameList,comparator);
		
		
		TrainSameService trainSameService  = new TrainSameService();
		
			Long confRange = context.getConfiguration().getLong("confRange",50);
			Long step = context.getConfiguration().getLong("step",5);
			Long imsiRange = context.getConfiguration().getLong("imsiRange",8);
			
			Object[] result = trainSameService.getU1TrainSameList(trainSameList, confRange, step, imsiRange,sn);
			sn = (Integer) result[2];
			Map<String, List<TrainSame>> trainSameMap = (Map<String, List<TrainSame>>) result[0];
			for (Map.Entry<String, List<TrainSame>> entry : trainSameMap.entrySet()) {
				for (TrainSame ts : entry.getValue()) {
//					System.out.println(ts.getProcedureStartTime());
					mos.write("u1",NullWritable.get(), new Text(ts.toString()));
				}
			}
	}

	protected void cleanup(Context context) throws IOException ,InterruptedException {
		mos.close();
	}
	
}