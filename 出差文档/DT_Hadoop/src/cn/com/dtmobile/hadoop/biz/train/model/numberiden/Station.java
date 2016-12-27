package cn.com.dtmobile.hadoop.biz.train.model.numberiden;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.com.dtmobile.hadoop.util.ParseUtils;

public class Station {

	private String stationName;
	private String inStationTime;
	private String startStationTime;
	private int upOrDown;
	public Station(String[] val) {
		stationName  = val[0];
		inStationTime = val[2];
		startStationTime = val[3];
		upOrDown = ParseUtils.parseInteger(val[4]);
	}
	
	public long getAvgTime() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		if(inStationTime.equals(startStationTime)){
			return sdf.parse(inStationTime).getTime();
		}
		long result = (sdf.parse(startStationTime).getTime()+ sdf.parse(inStationTime).getTime())/2;
		return result;
	}
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getInStationTime() {
		return inStationTime;
	}
	public void setInStationTime(String inStationTime) {
		this.inStationTime = inStationTime;
	}
	public String getStartStationTime() {
		return startStationTime;
	}
	public void setStartStationTime(String startStationTime) {
		this.startStationTime = startStationTime;
	}

	public int getUpOrDown() {
		return upOrDown;
	}

	public void setUpOrDown(int upOrDown) {
		this.upOrDown = upOrDown;
	}
	
	
}
