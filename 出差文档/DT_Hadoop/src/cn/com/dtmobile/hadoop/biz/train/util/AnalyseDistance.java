package cn.com.dtmobile.hadoop.biz.train.util;

import java.util.Map;

import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LocGuserMark;

public class AnalyseDistance {

	public static double analyseDistance(long cellid,long procedureStartTime,LocGuserMark locGuserMark,Map<String, String> professNetCellMap,Map<String, String> publicNetCellMap){
		double distance = 0;
		//第一条记录
		if("0".equals(locGuserMark.getSwp())){
			if(procedureStartTime < locGuserMark.getTimetend()
					&& procedureStartTime > locGuserMark.getTimetend() - 60 * 1000
					&& professNetCellMap.containsKey(cellid)){
				distance = ((locGuserMark.getTimetend() - procedureStartTime) / 60) * 2800;
			}else if(procedureStartTime < locGuserMark.getTimetend()
					&& procedureStartTime > locGuserMark.getTimetend() - 10 * 1000
					&& publicNetCellMap.containsKey(cellid)){
				distance = ((locGuserMark.getTimetend() - procedureStartTime) / 10) * 300;
			}
		}else if("1".equals(locGuserMark.getSwp())){ //最后一条记录
			if(procedureStartTime > locGuserMark.getTimet() 
					&& procedureStartTime < locGuserMark.getTimet() + 10 *1000 
					&& publicNetCellMap.containsKey(cellid)){
				distance = ((procedureStartTime - locGuserMark.getTimet()) / 10) * 300;
			}else if(procedureStartTime > locGuserMark.getTimet()
					&& procedureStartTime < locGuserMark.getTimet() + 60 *1000 
					&& professNetCellMap.containsKey(cellid)){
				distance = ((procedureStartTime - locGuserMark.getTimet()) / 60) * 2800;
			}
		}else if(locGuserMark.getSpeedok() == 2){
			distance = locGuserMark.getAvgspeed() * (procedureStartTime - locGuserMark.getTimet());
		}else if(locGuserMark.getSpeedok() == 1 
				&& procedureStartTime > locGuserMark.getTimet2()){
			distance = locGuserMark.getSpeedv2() * (procedureStartTime - locGuserMark.getTimet2()) 
						+ locGuserMark.getSpeedv1() * (locGuserMark.getTimet2() - locGuserMark.getTimet1()) 
						+ locGuserMark.getSpeedv0() * (locGuserMark.getTimet1() - locGuserMark.getTimet0());
		}else if(locGuserMark.getSpeedok() == 1 
				&& procedureStartTime > locGuserMark.getTimet1()){
			distance =  locGuserMark.getSpeedv1() * (procedureStartTime - locGuserMark.getTimet1()) 
						+ locGuserMark.getSpeedv0() * (locGuserMark.getTimet1() - locGuserMark.getTimet0());
		}else{
			distance = locGuserMark.getAvgspeed() * (procedureStartTime - locGuserMark.getTimet0());
		}
		
		return distance /(1000 * 60 * 60);
	}
	
	public static double AnalyseSpeed(LocGuserMark locGuserMark ,long procedureStartTime ){
		double speed;
		if(locGuserMark.getSpeedok() == 2){
			speed = locGuserMark.getSpeedok();
		}else if(locGuserMark.getSpeedok() == 1 && procedureStartTime > locGuserMark.getTimet2()){
			speed = locGuserMark.getSpeedv2();  
		}else if(locGuserMark.getSpeedok() == 1 && procedureStartTime > locGuserMark.getTimet1()){
			speed = locGuserMark.getSpeedv1();
		}else{
    	  speed = locGuserMark.getSpeedv0();
		}
		return speed;
	}
}
