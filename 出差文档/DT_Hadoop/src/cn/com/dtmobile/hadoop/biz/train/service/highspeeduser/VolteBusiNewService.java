package cn.com.dtmobile.hadoop.biz.train.service.highspeeduser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.train.constants.CommonConstnts;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.GtUser;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LocGuserMark;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.VolteBusiNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.VolteFreeNew;
import cn.com.dtmobile.hadoop.util.ArithmeticUtil;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteBusiNewService {
	public VolteBusiNew getVolteBusiNewService(List<VolteBusiNew> volteBusiNewList,
			Map<String, String> professNetCellMap, Map<String, String> publicNetCellMap, Long confSpeed) {
		Double speed = 0D;
		Long time = 0L;
		String currentLatlon = "";
		String nextLatlon = "";
		VolteBusiNew currVolteBusiNew = null;
		VolteBusiNew nextVolteBusiNew = null;
		if (volteBusiNewList.size() <= 2) {
			return null;
		}
		int dir = getDir(volteBusiNewList);
		for (int i = 0; i < volteBusiNewList.size() - 1; i++) {
			currVolteBusiNew = volteBusiNewList.get(i);
			nextVolteBusiNew = volteBusiNewList.get(i + 1);
			// 相邻时间相差10000毫秒，当前cellId不等于下一第targetcellid或当前targetcellid不等于下一第cellId
			time = Long.valueOf(nextVolteBusiNew.getUuXdr().getProcedureStartTime())
					- Long.valueOf(currVolteBusiNew.getUuXdr().getProcedureStartTime());
			if (time > 10000
					&& (!currVolteBusiNew.getUuXdr().getCellId().equals(nextVolteBusiNew.getUuXdr().getTargetCellId())
							|| !currVolteBusiNew.getUuXdr().getTargetCellId()
									.equals(nextVolteBusiNew.getUuXdr().getCellId()))) {
				// 专网切专网
				if (currVolteBusiNew.getIspub() == 0 && nextVolteBusiNew.getIspub() == 0) {
					// 如果方向为上行
					if (dir == 0) {
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
					} else {// 如果方向为下行
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
					}
				} else if (currVolteBusiNew.getIspub() == 0 && nextVolteBusiNew.getIspub() == 1) {// 专网切公网
					// 公网经纬度
					nextLatlon = professNetCellMap.get(nextVolteBusiNew.getUuXdr().getCellId());
					// 如果方向为上行
					if (dir == 0) {
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
					} else {// 如果方向为下行
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
					}
				} else if (currVolteBusiNew.getIspub() == 1 && nextVolteBusiNew.getIspub() == 0) {// 公网切专网
					// 公网经纬度
					currentLatlon = professNetCellMap.get(currVolteBusiNew.getUuXdr().getCellId());
					// 如果方向为上行
					if (dir == 0) {
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
					} else {// 如果方向为下行
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getUuXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
					}
				} else if (currVolteBusiNew.getIspub() == 1 && nextVolteBusiNew.getIspub() == 1) {// 公网切公网
					currentLatlon = professNetCellMap.get(currVolteBusiNew.getUuXdr().getCellId());
					nextLatlon = professNetCellMap.get(nextVolteBusiNew.getUuXdr().getCellId());
				}
				if (!StringUtils.isEmpty(currentLatlon) && !StringUtils.isEmpty(nextLatlon)) {
					speed = getSpeed(currentLatlon, nextLatlon, time / 3600000.0);
					if (speed > confSpeed && speed < 320) {
						currVolteBusiNew.setDir(dir);
						return currVolteBusiNew;
					}
				}
			}
		}
		return null;
	}

	// 分析上下行
	private int getDir(List<? extends GtUser> volteBusiNewList) {
		GtUser firstVolteBusiNew = null;
		GtUser middleVolteBusiNew = null;
		GtUser lastVolteBusiNew = null;
		int dir = 0;
		// 在List中取开始、中间、结尾的三个位置确定方向
		firstVolteBusiNew = volteBusiNewList.get(0);
		middleVolteBusiNew = volteBusiNewList.get(volteBusiNewList.size() / 2);
		lastVolteBusiNew = volteBusiNewList.get(volteBusiNewList.size() - 1);
		if (firstVolteBusiNew.getIspub() == middleVolteBusiNew.getIspub()) { // 如果开始点和中间点网络一致
			if (middleVolteBusiNew.getSeqnum() <= firstVolteBusiNew.getSeqnum()) {
				dir = 1;
			}
		} else if (firstVolteBusiNew.getIspub() == lastVolteBusiNew.getIspub()) { // 如果开始点和结束点网络一致
			if (lastVolteBusiNew.getSeqnum() <= firstVolteBusiNew.getSeqnum()) {
				dir = 1;
			}
		} else if (middleVolteBusiNew.getIspub() == lastVolteBusiNew.getIspub()) {// 如果中间点和结束点网络一致
			if (lastVolteBusiNew.getSeqnum() <= middleVolteBusiNew.getSeqnum()) {
				dir = 1;
			}
		}
		return dir;
	}
	

	public List<LocGuserMark> getLocGuserMarkList(List<LocGuserMark> locGuserMarkList){
		List<LocGuserMark> result = new ArrayList<LocGuserMark>();
		LocGuserMark locGuserMark = new LocGuserMark();
		int k = 0;
		int indexVal = 1;
		double avgspeed;
		double v0vot1;
		double v0vot2;
		long timetmpbgn=Calendar.getInstance().getTimeInMillis();
		for(int i = 0;i<locGuserMarkList.size();i++){
			if( i == 0){
				if(locGuserMarkList.get(i).getDir_State() == 1){
					locGuserMark = new LocGuserMark(locGuserMarkList.get(i).getImsi(), CommonConstnts.slong_dir1, CommonConstnts.slat_dir1, locGuserMarkList.get(i).getNlong(), locGuserMarkList.get(i).getNlat(), 0.0, timetmpbgn, locGuserMarkList.get(i).getProcedureStartTime(), locGuserMarkList.get(i).getRangetime(), 2, 0, 2, locGuserMarkList.get(i).getDir_State(), "0");
				}else{
					locGuserMark = new LocGuserMark(locGuserMarkList.get(i).getImsi(), CommonConstnts.slong, CommonConstnts.slat,  locGuserMarkList.get(i).getNlong(), locGuserMarkList.get(i).getNlat(), 0.0, timetmpbgn, locGuserMarkList.get(i).getProcedureStartTime(), locGuserMarkList.get(i).getRangetime(), 2, 0, 2, locGuserMarkList.get(i).getDir_State(), "0");
				}
				locGuserMark.setSwitchfpDistance(locGuserMarkList.get(i).getSwitchfpDistance());
				result.add(locGuserMark);
				indexVal = 1;
				k++;
			}else{
				locGuserMark = new LocGuserMark(locGuserMarkList.get(i).getImsi(), result.get(k-1).getDlong(), result.get(k-1).getDlat(), locGuserMarkList.get(i).getNlong(), locGuserMarkList.get(i).getNlat(),null, result.get(k-1).getTimetend(), locGuserMarkList.get(i).getProcedureStartTime(), locGuserMarkList.get(i).getRangetime());
				locGuserMark.setSwitchfpDistance(locGuserMarkList.get(i).getSwitchfpDistance());
				result.add(locGuserMark);
				if(locGuserMark.getTimetend() - locGuserMark.getTimet() <=0){
					avgspeed = 150;
				}else{
					avgspeed = getDistance(result.get(k).getSlong(), result.get(k).getSlat(), result.get(k).getDlong(), result.get(k).getDlat());
					if(avgspeed > 350){
						avgspeed = 200;
					}
				}
				locGuserMark.setAvgspeed(avgspeed);
				locGuserMark.setSpeedok(2);
				locGuserMark.setFlag(0);
				locGuserMark.setChkflag(1);
				locGuserMark.setEupordown(locGuserMarkList.get(i).getDir_State());
				if(indexVal > 1){
					if(result.get(k).getAvgspeed() != 0 && result.get(k-2).getAvgspeed() != 0 ){
						v0vot2 = result.get(k - 1 ).getAvgspeed() / result.get(k - 2 ).getAvgspeed();
						v0vot1 = result.get(k - 1 ).getAvgspeed() / result.get(k).getAvgspeed();
						if(v0vot1 > 2 && v0vot2 < 2){
							result.get(k-1).setSpeedok(1);
							result.get(k-1).setSpeedv0((2 * v0vot1 -1) /v0vot1 * result.get(k - 1).getAvgspeed());
							result.get(k-1).setSpeedv1(result.get(k-1).getAvgspeed());
							result.get(k-1).setSpeedv2(1 / v0vot1 * result.get(k-1).getAvgspeed());
						}else if(v0vot1 > 2 && v0vot2 >= 2){
							result.get(k-1).setSpeedok(1);
							result.get(k-1).setSpeedv0(1 / v0vot2 * result.get(k -1 ).getAvgspeed());
							result.get(k-1).setSpeedv1((3 - 1 / v0vot2 - 1 / v0vot2) * result.get(k - 1).getAvgspeed());
							result.get(k-1).setSpeedv2(1 / v0vot1 * result.get(k - 1).getAvgspeed());
						}else if( v0vot1 < 1/2 && v0vot2 > 1/2){
							result.get(k-1).setSpeedok(1);
							result.get(k-1).setSpeedv0(v0vot1 * result.get(k -1 ).getAvgspeed());
							result.get(k-1).setSpeedv1(result.get(k - 1).getAvgspeed());
							result.get(k-1).setSpeedv2((2 - v0vot1) * result.get(k - 1).getAvgspeed());
						}else if(v0vot1 < 1 / 2 && v0vot2 <= 1 / 2){
							result.get(k-1).setSpeedok(1);
							result.get(k-1).setSpeedv0((1 + v0vot2) * result.get(k - 1).getAvgspeed());
							result.get(k-1).setSpeedv1((1 - v0vot1 - v0vot2) * result.get(k - 1).getAvgspeed());
							result.get(k-1).setSpeedv2((1 + v0vot1) * result.get(k - 1).getAvgspeed());
						}else{
							result.get(k-1).setSpeedok(2);
							result.get(k-1).setSpeedv0(result.get(k - 1).getAvgspeed());
							result.get(k-1).setSpeedv1(result.get(k - 1).getAvgspeed());
							result.get(k-1).setSpeedv2(result.get(k - 1).getAvgspeed());

						}
						result.get(k-1).setTimet0(result.get(k-1).getTimet());
						result.get(k-1).setTimet1(result.get(k-1).getTimet() + (result.get(k-1).getTimetend() - result.get(k-1).getTimet()) / 3);
						result.get(k-1).setTimet2(result.get(k-1).getTimet() + 2 * (result.get(k-1).getTimetend() - result.get(k-1).getTimet()) / 3);
					
					}
				}
				k++;
				indexVal++;
			}
			//最后一条
			if(i == locGuserMarkList.size()-1){
				if(locGuserMarkList.get(i).getDir_State() == 1){
					if(locGuserMarkList.get(i).getDir_State() == 1){
						locGuserMark = new LocGuserMark(locGuserMarkList.get(i).getImsi(), result.get(k - 1).getDlong(), result.get(k - 1).getDlat(), CommonConstnts.dlong_dir1, CommonConstnts.dlat_dir1, result.get(k - 1).getAvgspeed(), locGuserMarkList.get(i).getProcedureStartTime(), null, locGuserMarkList.get(i).getRangetime(), 2, 0, 2 , locGuserMarkList.get(i).getDir_State(), "1");
					}else{
						locGuserMark = new LocGuserMark(locGuserMarkList.get(i).getImsi(), result.get(k - 1).getDlong(), result.get(k - 1).getDlat(), CommonConstnts.dlong, CommonConstnts.dlat, result.get(k - 1).getAvgspeed(), locGuserMarkList.get(i).getProcedureStartTime(), null, locGuserMarkList.get(i).getRangetime(), 2, 0, 2 , locGuserMarkList.get(i).getDir_State(), "1");
					}
					locGuserMark.setSwitchfpDistance(locGuserMarkList.get(i).getSwitchfpDistance());
					result.add(locGuserMark);
				}
					
			}
			
		}
		return result;
	}
	
	
	private double getSpeed(String currentLatlon, String nextLatlon, Double time) {
		Double currentLat = Double.valueOf(currentLatlon.split(StringUtils.DELIMITER_INNER_ITEM)[0]);
		Double currentLon = Double.valueOf(currentLatlon.split(StringUtils.DELIMITER_INNER_ITEM)[1]);
		Double nextLat = Double.valueOf(nextLatlon.split(StringUtils.DELIMITER_INNER_ITEM)[0]);
		Double nextLon = Double.valueOf(nextLatlon.split(StringUtils.DELIMITER_INNER_ITEM)[1]);
		return ArithmeticUtil.div(getDistance(currentLat, currentLon, nextLat, nextLon), time);
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param currentLat
	 *            第一点经度
	 * @param currentLon
	 *            第一点纬度
	 * @param nextLat
	 *            第二点经度
	 * @param nextLon
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double getDistance(double currentLat, double currentLon, double nextLat, double nextLon) {
		double R = 6378.137; // 地球半径
		double a = (currentLon - nextLon) * Math.PI / 180.0;
		double b = (currentLat - nextLat) * Math.PI / 180.0;
		double sa2 = Math.sin(a / 2.0);
		double sb2 = Math.sin(b / 2.0);
		return 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(currentLon) * Math.cos(nextLon) * sb2 * sb2));
	}

	public VolteFreeNew getVolteFreeNewService(List<VolteFreeNew> volteFreeNewTrainAnalyList,Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap, Long confSpeed) {
		Double speed = 0D;
		Long time = 0L;
		String currentLatlon = "";
		String nextLatlon = "";
		VolteFreeNew currVolteBusiNew = null;
		VolteFreeNew nextVolteBusiNew = null;
		if (volteFreeNewTrainAnalyList.size() <= 2) {
			return null;
		}
		int dir = getDir(volteFreeNewTrainAnalyList);
		for (int i = 0; i < volteFreeNewTrainAnalyList.size() - 1; i++) {
			currVolteBusiNew = volteFreeNewTrainAnalyList.get(i);
			nextVolteBusiNew = volteFreeNewTrainAnalyList.get(i + 1);
			time = Long.valueOf(nextVolteBusiNew.getS1mmeXdr().getProcedureStartTime())
					- Long.valueOf(currVolteBusiNew.getS1mmeXdr().getProcedureStartTime());
			if (time > 10000) {
				// 专网切专网
				if (currVolteBusiNew.getIspub() == 0 && nextVolteBusiNew.getIspub() == 0) {
					// 如果方向为上行
					if (dir == 0) {
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
					} else {// 如果方向为下行
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
					}
				} else if (currVolteBusiNew.getIspub() == 0 && nextVolteBusiNew.getIspub() == 1) {// 专网切公网
					// 公网经纬度
					nextLatlon = professNetCellMap.get(nextVolteBusiNew.getS1mmeXdr().getCellId());
					// 如果方向为上行
					if (dir == 0) {
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
					} else {// 如果方向为下行
						currentLatlon = professNetCellMap.get(currVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
					}
				} else if (currVolteBusiNew.getIspub() == 1 && nextVolteBusiNew.getIspub() == 0) {// 公网切专网
					// 公网经纬度
					currentLatlon = professNetCellMap.get(currVolteBusiNew.getS1mmeXdr().getCellId());
					// 如果方向为上行
					if (dir == 0) {
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[0];
					} else {// 如果方向为下行
						nextLatlon = professNetCellMap.get(nextVolteBusiNew.getS1mmeXdr().getCellId())
								.split(StringUtils.DELIMITER_INNER_ITEM1)[1];
					}
				} else if (currVolteBusiNew.getIspub() == 1 && nextVolteBusiNew.getIspub() == 1) {// 公网切公网
					currentLatlon = professNetCellMap.get(currVolteBusiNew.getS1mmeXdr().getCellId());
					nextLatlon = professNetCellMap.get(nextVolteBusiNew.getS1mmeXdr().getCellId());
				}
				if (!StringUtils.isEmpty(currentLatlon) && !StringUtils.isEmpty(nextLatlon)) {
					speed = getSpeed(currentLatlon, nextLatlon, time / 3600000.0);
					if (speed > confSpeed && speed < 320) {
						currVolteBusiNew.setDir(dir);
						return currVolteBusiNew;
					}
				}
			}
		}
		return null;
	}
	
}
