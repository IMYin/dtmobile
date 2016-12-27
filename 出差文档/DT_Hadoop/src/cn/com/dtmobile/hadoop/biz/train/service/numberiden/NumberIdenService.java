package cn.com.dtmobile.hadoop.biz.train.service.numberiden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.dtmobile.hadoop.biz.train.model.numberiden.NumberIdentification;
import cn.com.dtmobile.hadoop.biz.train.model.numberiden.Station;

public class NumberIdenService {

	
	public Object[] analyseNumberIdentification (List<NumberIdentification> numberIdentificationList , Map<Long,String> trainStationsMap , int size){
		Object[] obj =new Object[2];
		List<NumberIdentification> inList = new ArrayList<NumberIdentification>();
		List<NumberIdentification> outList = new ArrayList<NumberIdentification>();
		Map<String,List<NumberIdentification>> numberidenMap = new HashMap<String, List<NumberIdentification>>();
		Map<String,Long> avgUpDownTimeMap = new HashMap<String, Long>();
		for (NumberIdentification numberIden : numberIdentificationList) {
			// cai ji qu jian fan wei nei che zhan
			if(trainStationsMap.containsKey(numberIden.getCellId())){
				inList = numberidenMap.get(numberIden.getInstation());
				if(inList == null){
					inList = new ArrayList<NumberIdentification>();
				}
				inList.add(numberIden);
				
				outList = numberidenMap.get(numberIden.getOutstation());
				if(outList == null){
					outList = new ArrayList<NumberIdentification>();
				}
				outList.add(numberIden);
				
				numberidenMap.put(numberIden.getInstation(), inList);
				numberidenMap.put(numberIden.getInstation(), outList);
			}
			
			Iterator<Map.Entry<String, List<NumberIdentification>>> it = numberidenMap.entrySet().iterator();
			long time = 0;
			while(it.hasNext()){
				Map.Entry<String, List<NumberIdentification>> entry = it.next();
				for (NumberIdentification numberIdentification : entry.getValue()) {
					// zhan dian ping jun shang xia che shijian
					time  += numberIdentification.getIntime() + numberIdentification.getOuttime();
				}

				avgUpDownTimeMap.put(entry.getKey(), time / entry.getValue().size());
				//  shang xia che renshu >10 wei tingkao zhan dian
				if(entry.getValue().size() < size){
					it.remove();
				}
			}
		}
		obj[0] = numberidenMap;
		obj[1] = avgUpDownTimeMap;
		return obj;
	}
	
	public Map<String,List<Station>>  getContainStations(Map<String,List<Station>> trainStationTimesMap , Map<String,List<NumberIdentification>> numberidenMap){
		Map<String,List<Station>> containStationTimesMap = new HashMap<String, List<Station>>();
		int count = 0;
		//在列车时刻表筛选出包含停靠站点的列车车次
		for (Entry<String, List<Station>> entry : trainStationTimesMap.entrySet()) {
			for (Station station : entry.getValue()) {
				for (Entry<String, List<NumberIdentification>> ni : numberidenMap.entrySet()) {
					if(station.getStationName().equals(ni.getKey()) && station.getUpOrDown() == ni.getValue().get(0).getUpOrDown()){
						count ++;
					}
				}
			}
			if(count == numberidenMap.size()){
				containStationTimesMap.put(entry.getKey(), entry.getValue());
			}
		}
		return containStationTimesMap;
	}
}
