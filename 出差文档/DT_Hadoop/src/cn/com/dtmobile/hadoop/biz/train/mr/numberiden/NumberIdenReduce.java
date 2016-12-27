package cn.com.dtmobile.hadoop.biz.train.mr.numberiden;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.model.numberiden.NumberIdentification;
import cn.com.dtmobile.hadoop.biz.train.model.numberiden.Station;
import cn.com.dtmobile.hadoop.biz.train.model.numberiden.UpDownTrain;
import cn.com.dtmobile.hadoop.biz.train.service.numberiden.NumberIdenService;
import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class NumberIdenReduce extends Reducer<Text, Text, NullWritable, Text> {
	
	public MultipleOutputs<NullWritable,Text> mos;
	
	private Map<String,UpDownTrain> upDownTrainMap = new HashMap<String, UpDownTrain>();
	private Map<Long,String> trainStationsMap = new HashMap<Long, String>();
	private Map<String,List<Station>> trainStationTimesMap = new HashMap<String, List<Station>>();
	int step ;
	protected void setup(Context context) throws IOException ,InterruptedException {
		Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
//		BufferedReader in = new BufferedReader(new FileReader(new Path("/root/data/uporDown_out/part-r-00000").toString()));
		BufferedReader in = new BufferedReader(new FileReader(paths[1].toString()));
		String line = null;
		String[] values;
		//上下车用户    imsi:updownTrain
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			upDownTrainMap.put(values[0], new UpDownTrain(values));
		}
		in.close();
		
		// 采集站点map cellid:xiaoqu
//		in = new BufferedReader(new FileReader(new Path("/root/data/stations.txt").toString()));
		in = new BufferedReader(new FileReader(paths[2].toString()));
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			trainStationsMap.put(ParseUtils.parseLong(values[2]), values[3]);
		}
		List<Station> stationsList;
		//列车时刻表
//		in = new BufferedReader(new FileReader(new Path("/root/data/stationTimes.txt").toString()));
		in = new BufferedReader(new FileReader(paths[3].toString()));
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			stationsList = trainStationTimesMap.get(values[1]);
			if(stationsList == null){
				stationsList = new ArrayList<Station>();
			}
			stationsList.add(new Station(values));
			trainStationTimesMap.put(values[1],stationsList);
		}
		
		//上下车人数  >10 停 靠 站点
		step = context.getConfiguration().getInt("size",10);
		
		mos = new MultipleOutputs<NullWritable,Text>(context);
	}
		
	@SuppressWarnings("unchecked")
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Iterator<Text> iter = values.iterator();
		List<NumberIdentification> numberIdentificationList = new ArrayList<NumberIdentification>();
		Map<String,List<NumberIdentification>> numberidenMap = new HashMap<String, List<NumberIdentification>>();
		Map<String,Long> avgUpDownTimeMap = new HashMap<String, Long>();
		Map<String,List<Station>> containStationTimesMap = new HashMap<String, List<Station>>();
		NumberIdenService numberIdenService =  new NumberIdenService();
		UpDownTrain upDownTrain = null;
		String[] val = null;
		while(iter.hasNext()){
			val = iter.next().toString().split(StringUtils.DELIMITER_INNER_ITEM);
			if(upDownTrainMap.containsKey(val[0])){
				upDownTrain = upDownTrainMap.get(val[0]);
			}
			NumberIdentification numberIden = new NumberIdentification();
			numberIden.setImsi(upDownTrain.getImsi());
			numberIden.setGroupName(upDownTrain.getGroupName());
			numberIden.setIntime(upDownTrain.getIntime());
			numberIden.setOuttime(upDownTrain.getOuttime());
			numberIden.setInstation(upDownTrain.getInstation());
			numberIden.setOutstation(upDownTrain.getOutstation());
			numberIden.setCellId(val[2]);
			numberIdentificationList.add(numberIden);
		}
		Object[] obj = numberIdenService.analyseNumberIdentification(numberIdentificationList, trainStationsMap, step);
		//停靠站点
		numberidenMap = (Map<String, List<NumberIdentification>>) obj[0];
		//站点上下车平均时间
		avgUpDownTimeMap = (Map<String, Long>) obj[1];
		
		containStationTimesMap = numberIdenService.getContainStations(trainStationTimesMap, numberidenMap);
		
		long time = 0;
		for (Entry<String, Long> entry : avgUpDownTimeMap.entrySet()) {
			time += entry.getValue();
		}
		long time2 = 0;
		long curTime;
		String trainNumber = null;
		int upordown = 0;
		Long minTime = Long.MAX_VALUE;
		for (Entry<String, List<Station>> entry : containStationTimesMap.entrySet()) {
			for(Station station : entry.getValue()){
				try {
					time2 += station.getAvgTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			curTime = time - time2;
			if (minTime > curTime) {
				minTime = curTime;
				trainNumber = entry.getKey();
				upordown = entry.getValue().get(0).getUpOrDown();
			}
		}
		mos.write("numberIden", NullWritable.get(), new Text(trainNumber + StringUtils.DELIMITER_INNER_ITEM + upordown + StringUtils.DELIMITER_INNER_ITEM + numberIdentificationList.get(0).getGroupName()));
	}

	protected void cleanup(Context context) throws IOException ,InterruptedException {
		mos.close();
	}
	
}