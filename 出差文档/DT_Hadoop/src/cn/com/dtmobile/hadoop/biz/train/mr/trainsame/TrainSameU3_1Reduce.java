package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.service.trainsame.TrainSameService;
import cn.com.dtmobile.hadoop.util.StringUtils;
import cn.com.dtmobile.hadoop.util.UuComparator;

public class TrainSameU3_1Reduce  extends Reducer<Text, Text, NullWritable, Text>{
	
	public MultipleOutputs<NullWritable,Text> mos;
	
	protected void setup(Context context) throws IOException ,InterruptedException {
		mos = new MultipleOutputs<NullWritable,Text>(context);
	}
	
	static int seq = 1;
	
	Map<String,String> imsiMappingU2Count = new HashMap<String,String>();

	@SuppressWarnings("unchecked")
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		TrainSameService trainSameService = new TrainSameService();
		Map<String,String> imsiMappingU2 = new HashMap<String,String>();
//		Map<String,String> imsiMappingU2Tmp = new HashMap<String,String>();
		Set<String> set = new HashSet<String>();
		List<String> groupNameList = new ArrayList<String>();
		for (Text value : values) {
			set.add(value.toString().split(StringUtils.DELIMITER_INNER_ITEM)[0]);
		}
		
		groupNameList.addAll(set);
		UuComparator comp = new UuComparator();
		Collections.sort(groupNameList,comp);
		
		Object[] obj = trainSameService.getImsiMappingU2(groupNameList, seq, imsiMappingU2);
		imsiMappingU2 = (Map<String, String>) obj[0];
		seq = (Integer) obj[1];
		Iterator<Map.Entry<String, String>> it = imsiMappingU2.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = it.next();
			if(imsiMappingU2Count.containsKey(entry.getKey())){
				it.remove();
			}else{
				imsiMappingU2Count.put(entry.getKey(),entry.getValue());
			}
		}
		for (Map.Entry<String, String> imsiMapp : imsiMappingU2.entrySet()) {
//			System.out.println(imsiMapp.getKey()+"\t" + imsiMapp.getValue());
			mos.write("u2ImsiMapping",NullWritable.get(), new Text(imsiMapp.getKey() + StringUtils.DELIMITER_INNER_ITEM + imsiMapp.getValue()));
		}
	}
	protected void cleanup(Context context) throws IOException ,InterruptedException {
		mos.close();
	}
}
 