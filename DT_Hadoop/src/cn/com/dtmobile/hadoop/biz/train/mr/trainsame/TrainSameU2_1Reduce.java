package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

public class TrainSameU2_1Reduce extends Reducer<Text, Text, NullWritable, Text> {
	
	public MultipleOutputs<NullWritable,Text> mos;
	
	protected void setup(Context context) throws IOException ,InterruptedException {
		mos = new MultipleOutputs<NullWritable,Text>(context);
	}
	
	static int seq = 1;
	static Map<String, String> imsiMapping =  new HashMap<String, String>();
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		List<String> groupList = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		for(Text value :values){
			set.add(value.toString().split(StringUtils.DELIMITER_INNER_ITEM)[0]);
		}
		groupList.addAll(set);
		
		//sort by u1_1  
		UuComparator comparator = new UuComparator();
		Collections.sort(groupList,comparator);
		
		TrainSameService trainSameService  = new TrainSameService();
		
		// shi bushi tong yi zu yi zu shuju hui duo ci jin lai 
		boolean same = false;
		if(imsiMapping.get(groupList.get(0))!=null){
			same = true;
		}
		//得到imsi的关系 所有imsi的是一样的 所属的组不一样
		imsiMapping = trainSameService.getImsiMapping(groupList, seq, imsiMapping);
		if(same == false){
			for (Map.Entry<String, String> imsiMapp : imsiMapping.entrySet()) {
				mos.write("u1ImsiMapping",NullWritable.get(),new Text(imsiMapp.getKey() + StringUtils.DELIMITER_INNER_ITEM + imsiMapp.getValue()));
			}
		}
		if(same == false){
			seq++;
		}
		
	}
	
	protected void cleanup(Context context) throws IOException ,InterruptedException {
		mos.close();
	}
	
}