package cn.com.dtmobile.hadoop.biz.train.mr.trainsame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class TrainSameu3_2Map  extends Mapper<LongWritable, Text, Text, Text>  {

	private Map<String,String> imsiMapping = new HashMap<String,String>();

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
//		Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		BufferedReader in = new BufferedReader(new FileReader(new Path("/root/output/u2/u2Mapping/u2ImsiMapping-r-00000").toString()));
//		BufferedReader in = new BufferedReader(new FileReader(paths[1].toString()));
		String line = null;
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			imsiMapping.put(values[0], values[1]);
		}
		in.close();
	}
	
	public final Text key = new Text();
	
	@Override
	protected void map(LongWritable  inKey, Text value,Context context)throws IOException, InterruptedException {
		if(value.getLength() > 0){
			String [] val = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
			for (Map.Entry<String, String> imsiMapp : imsiMapping.entrySet()) {
				if(imsiMapp.getKey().contains(val[8])){
					key.set(val[8]);
					context.write(key,new Text(value.toString() + StringUtils.DELIMITER_INNER_ITEM + imsiMapp.getValue() + StringUtils.DELIMITER_INNER_ITEM + imsiMapp.getKey()));
				}
			}
		}
	}
	/**
	 * if(imsiMapping.containsKey(val[8])){
				key.set(imsiMapping.get(val[8]));
				context.write(key,new Text(value.toString() + StringUtils.DELIMITER_INNER_ITEM + imsiMapping.get(val[8])));
	}*/
}
