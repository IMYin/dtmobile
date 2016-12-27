package cn.com.dtmobile.hadoop.biz.train.service.highspeeduser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.GxRxNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LocGuserMark;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.MwNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.SgsXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.SvNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.X2XdrNew;
import cn.com.dtmobile.hadoop.biz.train.util.AnalyseDistance;
import cn.com.dtmobile.hadoop.model.GxXdr;
import cn.com.dtmobile.hadoop.model.LteMroSource;
import cn.com.dtmobile.hadoop.model.MwXdr;
import cn.com.dtmobile.hadoop.model.S1mmeXdr;
import cn.com.dtmobile.hadoop.model.SgsXdr;
import cn.com.dtmobile.hadoop.model.Sv;
import cn.com.dtmobile.hadoop.model.UuXdr;
import cn.com.dtmobile.hadoop.model.X2;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class XdrNewService {
	

	/**
	 * etype <>0 and gridid is null correct gridid 
	 * @param cellid
	 * @param htSwitChfpMap
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @return
	 */
	public Long correctGrid(String cellid,int upordown, Map<String, String> htSwitChfpMap, Map<String, String> professNetCellMap, Map<String, String> publicNetCellMap){
		String position=publicNetCellMap.get(cellid);
		//is public net cell
		Long gridId=null;
		if(StringUtils.isNotEmpty(position)){
			String[] posis=position.split(StringUtils.DELIMITER_INNER_ITEM);
			double cellLon=Double.valueOf(posis[0]);
			double cellLat=Double.valueOf(posis[1]);
			Set<Entry<String, String>> ks=htSwitChfpMap.entrySet();
			Iterator<Entry<String, String>> it=ks.iterator();
			double distence=10000000000000000000.0;
			while(it.hasNext()){
				Entry<String, String> item=it.next();
				String[] keyMsgs=item.getKey().split(StringUtils.DELIMITER_INNER_ITEM);
				boolean flag=keyMsgs[0].equals(cellid)&&(upordown==Integer.valueOf(keyMsgs[1]));
				if(flag){
					String[] values=item.getValue().split(StringUtils.DELIMITER_INNER_ITEM);
					double switchLon=Double.valueOf(values[0]);
					double switchLat=Double.valueOf(values[1]);
					double dis=VolteBusiNewService.getDistance(cellLat, cellLon, switchLat, switchLon);
					gridId=dis<distence?Long.valueOf(values[2]):null;
				}
			}
		}else{
			//is profess net cell
			String proPosi=professNetCellMap.get(cellid);
			if(StringUtils.isNotEmpty(proPosi)){
				Set<Entry<String, String>> ks=htSwitChfpMap.entrySet();
				Iterator<Entry<String, String>> it=ks.iterator();
				while(it.hasNext()){
					Entry<String, String> item=it.next();
					String[] keyMsgs=item.getKey().split(StringUtils.DELIMITER_INNER_ITEM);
					boolean flag=keyMsgs[0].equals(cellid)&&(upordown==Integer.valueOf(keyMsgs[1]));
					String ss=publicNetCellMap.get(keyMsgs[2]);
					boolean flag1=StringUtils.isNotEmpty(ss);
					if(flag&&flag1){
						String[] values=item.getValue().split(StringUtils.DELIMITER_INNER_ITEM);
						gridId=Long.valueOf(values[2]);
						break;
					}
				}
				
			}
		}
		return gridId;
	}
	/**
	 * uu \u63a5\u53e3 \u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param uUxdrNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 * @throws ParseException 
	 */
	public List<UuXdrNew> analyseUuXdrNew(List<UuXdr> uUxdrNewList,
			List<LocGuserMark> locGuserMarkList,
			Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,
			Map<String, String[]> switchPointMap, Double distanceRange, Map<String, String> htSwitChfpMap) throws ParseException {
		List<UuXdrNew> result = new ArrayList<UuXdrNew>();
		/*Calendar aa = org.apache.commons.lang.time.DateUtils.truncate(
				Calendar.getInstance(), Calendar.HOUR);*/
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (UuXdr uuXdr : uUxdrNewList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
				if (locGuserMark.getTimet() > begintimemill - 5000
						&& locGuserMark.getTimet() < endtimemill  
						&& uuXdr.getProcedureStartTime() > locGuserMark.getTimet()
						&& uuXdr.getProcedureStartTime() < locGuserMark.getTimetend()) {
					UuXdrNew uuXdrNew=new UuXdrNew();
					uuXdrNew.setUuXdr(uuXdr);
					uuXdrNew.setSlong(locGuserMark.getSlong());
					uuXdrNew.setSlat(locGuserMark.getSlat());
					uuXdrNew.setDlong(locGuserMark.getDlong());
					uuXdrNew.setDlat(locGuserMark.getDlat());
					uuXdrNew.setEspeed(AnalyseDistance.AnalyseSpeed(locGuserMark, uuXdr.getProcedureStartTime()));
					uuXdrNew.setDistance(AnalyseDistance.analyseDistance(Long.valueOf(uuXdr.getCellId()),uuXdr.getProcedureStartTime(),locGuserMark, professNetCellMap, publicNetCellMap));
					// \u7ecf\u7eac\u5ea6\u56de\u586b
					String[] switchPoint = this._backfillXyGrid(switchPointMap,distanceRange, uuXdrNew.getDistance(),locGuserMark.getSwitchfpDistance());
					uuXdrNew.setElong(null);
					uuXdrNew.setElat(null);
					if (uuXdr.getProcedureStatus().equals("255")) {
						uuXdrNew.setFalurecause("接口超时");
					} else {
						uuXdrNew.setFalurecause("");
					}
					if ("7".equals(uuXdr.getProcedureType())&& ("1".equals(uuXdr.getProcedureStatus()) || "255".equals(uuXdr.getProcedureStatus()))) {
						uuXdrNew.setEtype(10);
					} else {
						uuXdrNew.setEtype(0);
					}
					uuXdrNew.setEupordown(locGuserMark.getEupordown());
					if(switchPoint==null&&uuXdrNew.getEtype()!=0){
						uuXdrNew.setGridid(correctGrid(uuXdr.getCellId(),uuXdrNew.getEupordown(),htSwitChfpMap,professNetCellMap,publicNetCellMap));
					}else{
						uuXdrNew.setGridid(Long.valueOf(switchPoint[0]));
					}
					uuXdrNew.setFlag(2);
					if (locGuserMark.getSpeedok() == 2&& (locGuserMark.getSlong() == 110.330997 || locGuserMark.getSlong() == 113.214038)&& (locGuserMark.getSlat() == 35.024218 || locGuserMark.getSlat() == 39.933898)) {
						uuXdrNew.setBeforeflag(1);
					} else {
						uuXdrNew.setBeforeflag(0);
					}
					result.add(uuXdrNew);
				}
			}
		}
		return result;
	}

	/**
	 * x2 \u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param x2XdrNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 * @throws ParseException 
	 */
	public List<X2XdrNew> analyseX2XdrNew(List<X2> x2XdrList,List<LocGuserMark> locGuserMarkList,Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,Map<String, String[]> switchPointMap, Double distanceRange, Map<String, String> htSwitChfpMap) throws ParseException {
		List<X2XdrNew> resultList = new ArrayList<X2XdrNew>();
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (X2 x2Xdr : x2XdrList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
				if (locGuserMark.getTimet() > begintimemill - 5000
						&& locGuserMark.getTimet() < endtimemill  
						&& x2Xdr.getProcedureStartTime() > locGuserMark.getTimet()
						&& x2Xdr.getProcedureStartTime() < locGuserMark.getTimetend()) {
					X2XdrNew x2XdrNew=new X2XdrNew();
					x2XdrNew.setX2Xdr(x2Xdr);
					x2XdrNew.setSlong(locGuserMark.getSlong());
					x2XdrNew.setSlat(locGuserMark.getSlat());
					x2XdrNew.setDlong(locGuserMark.getDlong());
					x2XdrNew.setDlat(locGuserMark.getDlat());
					x2XdrNew.setEspeed(AnalyseDistance.AnalyseSpeed(locGuserMark,x2XdrNew.getX2Xdr().getProcedureStartTime()));
					x2XdrNew.setDistance(AnalyseDistance.analyseDistance(x2XdrNew.getX2Xdr().getCellId(), x2XdrNew.getX2Xdr().getProcedureStartTime(), locGuserMark,professNetCellMap, publicNetCellMap));
					String[] switchPoint = this._backfillXyGrid(switchPointMap,distanceRange, x2XdrNew.getDistance(),locGuserMark.getSwitchfpDistance());
					x2XdrNew.setGridid(Long.valueOf(switchPoint[0]));
					x2XdrNew.setElong(null);
					x2XdrNew.setElat(null);
					if (x2XdrNew.getX2Xdr().getProcedureStatus() == 255) {
						x2XdrNew.setFalurecause("接口超时");
					} else {
						x2XdrNew.setFalurecause("");
					}
					if ("1".equals(x2XdrNew.getX2Xdr().getProcedureType())
							&& ("1".equals(x2XdrNew.getX2Xdr().getProcedureStatus()) || "255"
									.equals(x2XdrNew.getX2Xdr()
											.getProcedureStatus()))
							&& (!"1000".equals(x2XdrNew.getFalurecause()) || StringUtils
									.isEmpty(x2XdrNew.getFalurecause()))) {
						x2XdrNew.setEtype(10);
					} else {
						x2XdrNew.setEtype(0);
					}
					x2XdrNew.setFlag(0);
					if (locGuserMark.getSpeedok() == 2
							&& (locGuserMark.getSlong() == 110.330997 || locGuserMark
									.getSlong() == 113.214038)
							&& (locGuserMark.getSlat() == 35.024218 || locGuserMark
									.getSlat() == 39.933898)) {
						x2XdrNew.setBeforeflag(1);
					} else {
						x2XdrNew.setBeforeflag(0);
					}
					x2XdrNew.setEupordown(locGuserMark.getEupordown());
					x2XdrNew.setEupordown(locGuserMark.getEupordown());
					if(switchPoint==null&&x2XdrNew.getEtype()!=0){
						x2XdrNew.setGridid(correctGrid(String.valueOf(x2XdrNew.getX2Xdr().getCellId()),x2XdrNew.getEupordown(),htSwitChfpMap,professNetCellMap,publicNetCellMap));
					}else{
						x2XdrNew.setGridid(Long.valueOf(switchPoint[0]));
					}
					resultList.add(x2XdrNew);
				}
			}
		}
		return resultList;
	}

	/**
	 * LteMroSource \u63a5\u53e3 \u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param lteMroSourceNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @param htSwitChfpMap 
	 * @return
	 * @throws ParseException 
	 */
	public List<LteMroSourceNew> analyseLteMroSourceNew(
			List<LteMroSource> lteMroSourceList,
			List<LocGuserMark> locGuserMarkList,
			Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,
			Map<String, String[]> switchPointMap, Double distanceRange) throws ParseException {
		List<LteMroSourceNew> resultList = new ArrayList<LteMroSourceNew>();
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (LteMroSource lteMroSource : lteMroSourceList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
				if (locGuserMark.getTimet() > begintimemill - 5000
						&& locGuserMark.getTimet() < endtimemill  
						&& lteMroSource.getMrtime() > locGuserMark.getTimet()
						&& lteMroSource.getMrtime() < locGuserMark.getTimetend()) {
					LteMroSourceNew lteMroSourceNew=new LteMroSourceNew();
					lteMroSourceNew.setLtemrosource(lteMroSource);
					lteMroSourceNew.setGridid(Long.valueOf(-1));
					lteMroSourceNew.setSlong(locGuserMark.getSlong());
					lteMroSourceNew.setSlat(locGuserMark.getSlat());
					lteMroSourceNew.setDlong(locGuserMark.getDlong());
					lteMroSourceNew.setDlat(locGuserMark.getDlat());
					lteMroSourceNew.setEspeed(AnalyseDistance.AnalyseSpeed(
							locGuserMark, lteMroSourceNew.getLtemrosource()
									.getMrtime()));
					lteMroSourceNew.setDistance(AnalyseDistance.analyseDistance(
							lteMroSourceNew.getLtemrosource().getCellid(),
							lteMroSourceNew.getLtemrosource().getMrtime(),
							locGuserMark, professNetCellMap, publicNetCellMap));
					String[] switchPoint = this._backfillXyGrid(switchPointMap,
							distanceRange, lteMroSourceNew.getDistance(),
							locGuserMark.getSwitchfpDistance());
					if(switchPoint==null){
						
					}
					lteMroSourceNew.setGridid(Long.valueOf(switchPoint[0]));
					lteMroSourceNew.setElong(null);
					lteMroSourceNew.setElat(null);
					lteMroSourceNew.setFalurecause(null);
					lteMroSourceNew.setEtype(0);
					lteMroSourceNew.setFlag(0);
					if (locGuserMark.getSpeedok() == 2
							&& (locGuserMark.getSlong() == 110.330997 || locGuserMark
									.getSlong() == 113.214038)
							&& (locGuserMark.getSlat() == 35.024218 || locGuserMark
									.getSlat() == 39.933898)) {
						lteMroSourceNew.setBeforeflag(1);
					} else {
						lteMroSourceNew.setBeforeflag(0);
					}
					lteMroSourceNew.setEupordown(locGuserMark.getEupordown());
					resultList.add(lteMroSourceNew);
				}
			}
		}
		return resultList;
	}

	/**
	 * mw\u63a5\u53e3\u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param mWNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 * @throws ParseException 
	 */
	public List<MwNew> analyseMwNew(List<MwXdr> mWList,
			List<LocGuserMark> locGuserMarkList,
			Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,
			Map<String, String[]> switchPointMap, Double distanceRange, Map<String, String> htSwitChfpMap) throws ParseException {
		List<MwNew> resultList = new ArrayList<MwNew>();
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (MwXdr mw : mWList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
				if (locGuserMark.getTimet() > begintimemill - 5000&&locGuserMark.getTimet() < endtimemill  
						&& mw.getProcedurestarttime() > locGuserMark.getTimet()
						&& mw.getProcedurestarttime() < locGuserMark.getTimetend()) {
					MwNew mwNew=new MwNew();
					mwNew.setMwXdr(mw);
					mwNew.setGridid(Long.valueOf(-1));
					mwNew.setSlong(locGuserMark.getSlong());
					mwNew.setSlat(locGuserMark.getSlat());
					mwNew.setDlong(locGuserMark.getDlong());
					mwNew.setDlat(locGuserMark.getDlat());
					mwNew.setEspeed(AnalyseDistance.AnalyseSpeed(locGuserMark,mwNew.getMwXdr().getProcedurestarttime()));
					String cellId = "0".equals(mwNew.getMwXdr().getCall_side())? mwNew.getMwXdr().getSource_eci() : mwNew.getMwXdr().getDest_eci();
					mwNew.setDistance(AnalyseDistance.analyseDistance(StringUtils.isNotEmpty(cellId)?Long.valueOf(cellId):0, mwNew.getMwXdr().getProcedurestarttime(), locGuserMark,professNetCellMap, publicNetCellMap));
					String[] switchPoint = this._backfillXyGrid(switchPointMap,distanceRange, mwNew.getDistance(),locGuserMark.getSwitchfpDistance());
					mwNew.setGridid(Long.valueOf(switchPoint[0]));
					mwNew.setElong(null);
					mwNew.setElat(null);
					if ("1".equals(mwNew.getMwXdr().getProcedurestatus())) {
						mwNew.setFalurecause(mwNew.getMwXdr().getResponse_code()
								+ mwNew.getMwXdr().getFinish_reason_protocol()
								+ mwNew.getMwXdr().getFinish_reason_cause());
					} else if ("3".equals(mwNew.getMwXdr().getProcedurestatus())) {
						mwNew.setFalurecause("接口超时");
					} else {
						mwNew.setFalurecause(null);
					}
					if ("1".equals(mwNew.getMwXdr().getProceduretype())
							&& "14".equals(mwNew.getMwXdr().getInterfaces())) {
						if ("1".equals(mwNew.getMwXdr().getProcedurestatus())|| "3".equals(mwNew.getMwXdr().getProcedurestatus())) {
							mwNew.setEtype(1);
						} else if ("0".equals(mwNew.getMwXdr().getCall_side())&& StringUtils.isEmpty(mwNew.getMwXdr().getAlerting_time())
								&& "0".equals(mwNew.getMwXdr().getServicetype())) {
							mwNew.setEtype(4);
						} else if ("0".equals(mwNew.getMwXdr().getCall_side())
								&& StringUtils.isEmpty(mwNew.getMwXdr()
										.getAlerting_time())
								&& "1".equals(mwNew.getMwXdr().getServicetype())) {
							mwNew.setEtype(6);
						} else {
							mwNew.setEtype(0);
						}
					}
					mwNew.setEtype(0);
					mwNew.setFlag(2);
					if (locGuserMark.getSpeedok() == 2
							&& (locGuserMark.getSlong() == 110.330997 || locGuserMark
									.getSlong() == 113.214038)
							&& (locGuserMark.getSlat() == 35.024218 || locGuserMark
									.getSlat() == 39.933898)) {
						mwNew.setBeforeflag(1);
					} else {
						mwNew.setBeforeflag(0);
					}
					mwNew.setEupordown(locGuserMark.getEupordown());
					if(switchPoint==null&&mwNew.getEtype()!=0){
						mwNew.setGridid(correctGrid(cellId,mwNew.getEupordown(),htSwitChfpMap,professNetCellMap,publicNetCellMap));
					}else{
						mwNew.setGridid(Long.valueOf(switchPoint[0]));
					}
					resultList.add(mwNew);
				}
				
			}
		}
		return resultList;
	}

	/**
	 * sv\u63a5\u53e3\u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param svNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 * @throws ParseException 
	 */
	public List<SvNew> analyseSvXdrNew(List<Sv> svList,
			List<LocGuserMark> locGuserMarkList,
			Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,
			Map<String, String[]> switchPointMap, Double distanceRange) throws ParseException {
		List<SvNew> result = new ArrayList<SvNew>();
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (Sv sv : svList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
			if (locGuserMark.getTimet() > begintimemill - 5000&&locGuserMark.getTimet() < endtimemill  
					&& sv.getProcedureStartTime() > locGuserMark.getTimet()
					&& sv.getProcedureStartTime() < locGuserMark.getTimetend()){
				    SvNew svNew=new SvNew();
				    svNew.setSv(sv);
				    svNew.setGridid(Long.valueOf(-1));
					svNew.setSlong(locGuserMark.getSlong());
					svNew.setSlat(locGuserMark.getSlat());
					svNew.setDlong(locGuserMark.getDlong());
					svNew.setDlat(locGuserMark.getDlat());
					svNew.setEspeed(AnalyseDistance.AnalyseSpeed(locGuserMark,
							svNew.getSv().getProcedureStartTime()));
					svNew.setDistance(AnalyseDistance.analyseDistance(Long
							.valueOf(svNew.getSv().getSourceEci()), svNew.getSv()
							.getProcedureStartTime(), locGuserMark,
							professNetCellMap, publicNetCellMap));
					String[] switchPoint = this._backfillXyGrid(switchPointMap,
							distanceRange, svNew.getDistance(),
							locGuserMark.getSwitchfpDistance());
					svNew.setGridid(Long.valueOf(switchPoint[0]));
					svNew.setElong(null);
					svNew.setElat(null);
					svNew.setFalurecause(svNew.getSv().getResult()
							+ svNew.getSv().getSvCause()
							+ svNew.getSv().getPostFailureCause());
					if ("1".equals(svNew.getSv().getProcedureType())) {
						svNew.setEchksvtype(18);
					} else {
						svNew.setEchksvtype(0);
					}
					svNew.setEtype(0);
					svNew.setFlag(0);
					if (locGuserMark.getSpeedok() == 2
							&& (locGuserMark.getSlong() == 110.330997 || locGuserMark
									.getSlong() == 113.214038)
							&& (locGuserMark.getSlat() == 35.024218 || locGuserMark
									.getSlat() == 39.933898)) {
						svNew.setBeforeflag(1);
					} else {
						svNew.setBeforeflag(0);
					}
					svNew.setEupordown(locGuserMark.getEupordown());
					result.add(svNew);
				}
			}
		}
		return result;
	}

	/**
	 * sgs\u63a5\u53e3 \u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param sgsXdrNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 * @throws ParseException 
	 */
	public List<SgsXdrNew> analyseSgsXdrNew(List<SgsXdr> sgsXdrList,
			List<LocGuserMark> locGuserMarkList,
			Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,
			Map<String, String[]> switchPointMap, Double distanceRange, Map<String, String> htSwitChfpMap) throws ParseException {
		List<SgsXdrNew> result = new ArrayList<SgsXdrNew>();
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (SgsXdr sgsXdr : sgsXdrList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
				if (locGuserMark.getTimet() > begintimemill - 5000&&locGuserMark.getTimet() < endtimemill  
						&& sgsXdr.getProcedureStartTime() > locGuserMark.getTimet()
						&& sgsXdr.getProcedureStartTime() < locGuserMark.getTimetend()){
					SgsXdrNew sgsXdrNew =new SgsXdrNew();
					sgsXdrNew.setSgsXdr(sgsXdr);
					sgsXdrNew.setGridid(Long.valueOf(-1));
					sgsXdrNew.setSlong(locGuserMark.getSlong());
					sgsXdrNew.setSlat(locGuserMark.getSlat());
					sgsXdrNew.setDlong(locGuserMark.getDlong());
					sgsXdrNew.setDlat(locGuserMark.getDlat());
					sgsXdrNew.setEspeed(AnalyseDistance.AnalyseSpeed(locGuserMark,
							sgsXdrNew.getSgsXdr().getProcedureStartTime()));
					sgsXdrNew.setDistance(AnalyseDistance.analyseDistance(
							StringUtils.isNotEmpty(sgsXdrNew.getSgsXdr().getCellId())?Long.valueOf(sgsXdrNew.getSgsXdr().getCellId()):0,
							sgsXdrNew.getSgsXdr().getProcedureStartTime(),
							locGuserMark, professNetCellMap, publicNetCellMap));
					String[] switchPoint = this._backfillXyGrid(switchPointMap,
							distanceRange, sgsXdrNew.getDistance(),
							locGuserMark.getSwitchfpDistance());
					sgsXdrNew.setGridid(Long.valueOf(switchPoint[0]));
					sgsXdrNew.setElong(null);
					sgsXdrNew.setElat(null);
					if (sgsXdrNew.getSgsXdr().getProcedureStatus() == 1) {
						sgsXdrNew.setFalurecause(String.valueOf(sgsXdrNew
								.getSgsXdr().getSgsCause()
								+ sgsXdrNew.getSgsXdr().getRejectCause()
								+ sgsXdrNew.getSgsXdr().getCpCause()));
					} else if (sgsXdrNew.getSgsXdr().getProcedureStatus() == 255) {
						sgsXdrNew.setFalurecause("接口超时");
					} else {
						sgsXdrNew.setFalurecause(null);
					}
					if (sgsXdrNew.getSgsXdr().getProcedureType() == 17
							&& (sgsXdrNew.getSgsXdr().getProcedureStatus() == 1 || sgsXdrNew
									.getSgsXdr().getProcedureStatus() == 255)) {
						sgsXdrNew.setEtype(12);
					} else {
						sgsXdrNew.setEtype(0);
					}
					sgsXdrNew.setFlag(0);
					if (locGuserMark.getSpeedok() == 2
							&& (locGuserMark.getSlong() == 110.330997 || locGuserMark
									.getSlong() == 113.214038)
							&& (locGuserMark.getSlat() == 35.024218 || locGuserMark
									.getSlat() == 39.933898)) {
						sgsXdrNew.setBeforeflag(1);
					} else {
						sgsXdrNew.setBeforeflag(0);
					}
					sgsXdrNew.setEupordown(locGuserMark.getEupordown());
					if(switchPoint==null&&sgsXdrNew.getEtype()!=0){
						sgsXdrNew.setGridid(correctGrid(sgsXdrNew.getSgsXdr().getCellId(),sgsXdrNew.getEupordown(),htSwitChfpMap,professNetCellMap,publicNetCellMap));
					}else{
						sgsXdrNew.setGridid(Long.valueOf(switchPoint[0]));
					}
					result.add(sgsXdrNew);
				}
			}
		}
		return result;
	}

	/**
	 * s1 \u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param s1mmeXdrNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 * @throws ParseException 
	 */
	public List<S1mmeXdrNew> analyseS1mmeXdrNew(
			List<S1mmeXdr> s1mmeXdrList,
			List<LocGuserMark> locGuserMarkList,
			Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,
			Map<String, String[]> switchPointMap, Double distanceRange, Map<String, String> htSwitChfpMap) throws ParseException {
		List<S1mmeXdrNew> result = new ArrayList<S1mmeXdrNew>();
		long procedureType;
		long procedureStatus;
		long requestcause;
		String keyword1 = null;
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (S1mmeXdr s1mmeXdr : s1mmeXdrList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
				if (locGuserMark.getTimet() > begintimemill - 5000&&locGuserMark.getTimet() < endtimemill  
						&& s1mmeXdr.getProcedureStartTime() > locGuserMark.getTimet()
						&& s1mmeXdr.getProcedureStartTime() < locGuserMark.getTimetend()){
					S1mmeXdrNew s1mmeXdrNew=new S1mmeXdrNew();
					s1mmeXdrNew.setS1mmeXdr(s1mmeXdr);
					s1mmeXdrNew.setGridid(Long.valueOf(-1));
					s1mmeXdrNew.setSlong(locGuserMark.getSlong());
					s1mmeXdrNew.setSlat(locGuserMark.getSlat());
					s1mmeXdrNew.setDlong(locGuserMark.getDlong());
					s1mmeXdrNew.setDlat(locGuserMark.getDlat());
					procedureType = StringUtils.isNotEmpty(s1mmeXdrNew
							.getS1mmeXdr().getProcedureType()) ? Long
							.valueOf(s1mmeXdrNew.getS1mmeXdr().getProcedureType())
							: null;
					procedureStatus = StringUtils.isNotEmpty(s1mmeXdrNew
							.getS1mmeXdr().getProcedureStatus()) ? Long
							.valueOf(s1mmeXdrNew.getS1mmeXdr().getProcedureStatus())
							: null;
					s1mmeXdrNew.setEspeed(AnalyseDistance.AnalyseSpeed(
							locGuserMark, s1mmeXdrNew.getS1mmeXdr()
									.getProcedureStartTime()));
					s1mmeXdrNew.setDistance(AnalyseDistance.analyseDistance(
							Long.valueOf(StringUtils.isNotEmpty(s1mmeXdrNew.getS1mmeXdr().getCellId())?s1mmeXdrNew.getS1mmeXdr().getCellId():"0"),
							s1mmeXdrNew.getS1mmeXdr().getProcedureStartTime(),
							locGuserMark, professNetCellMap, publicNetCellMap));
					String[] switchPoint = this._backfillXyGrid(switchPointMap,
							distanceRange, s1mmeXdrNew.getDistance(),
							locGuserMark.getSwitchfpDistance());
					s1mmeXdrNew.setGridid(Long.valueOf(switchPoint[0]));
					s1mmeXdrNew.setElong(null);
					s1mmeXdrNew.setElat(null);
					if (procedureStatus == 1) {
						s1mmeXdrNew.setFalurecause(s1mmeXdrNew.getS1mmeXdr()
								.getFailureCause());
					} else if (procedureStatus == 255) {
						s1mmeXdrNew.setFalurecause("接口超时");
					} else {
						s1mmeXdrNew.setFalurecause(null);
					}
					requestcause = StringUtils.isNotEmpty(s1mmeXdrNew.getS1mmeXdr()
							.getRequestCause()) ? Long.valueOf(s1mmeXdrNew
							.getS1mmeXdr().getRequestCause()) : 0;
					if (procedureType == 1
							&& (procedureStatus == 1 || procedureStatus == 255)) {
						s1mmeXdrNew.setEtype(2);
					} else if (procedureType == 2
							&& (procedureStatus == 1 || procedureStatus == 255)) {
						s1mmeXdrNew.setEtype(3);
					} else if (procedureType == 5
							&& (procedureStatus == 1 || procedureStatus == 255)) {
						s1mmeXdrNew.setEtype(8);
					} else if (procedureType == 20
							&& "0".equals(keyword1)
							&& (requestcause != 2 || requestcause != 20
									|| requestcause != 23 || requestcause != 24
									|| requestcause != 28 || requestcause != 512 || requestcause != 514)) {
						s1mmeXdrNew.setEtype(9);
					} else if (procedureType == 16 && "3".equals(keyword1)
							&& (procedureStatus == 1 || procedureStatus == 255)) {
						s1mmeXdrNew.setEtype(13);
					} else if (procedureType == 16 && "3".equals(keyword1)) {
						s1mmeXdrNew.setEtype(14);
					} else if (procedureType == 16
							&& "1".equals(keyword1)
							&& (procedureStatus == 1 || (procedureStatus == 0 || procedureStatus == 255))) {
						for (S1mmeXdr s1mmeXdrTmp : s1mmeXdrList) {
							if (("20".equals(s1mmeXdrTmp
									.getProcedureType())
									&& "2".equals(s1mmeXdrTmp
											.getRequestCause())
									&& s1mmeXdrTmp
											.getProcedureStartTime() >= s1mmeXdrNew
											.getS1mmeXdr().getProcedureStartTime()
									&& s1mmeXdrTmp
											.getProcedureStartTime() <= s1mmeXdrNew
											.getS1mmeXdr().getProcedureStartTime() + 5 * 1000 && s1mmeXdrTmp.getCellId()
									.equals(s1mmeXdrNew.getS1mmeXdr().getCellId())) == false) {
								s1mmeXdrNew.setEtype(10);
							}
						}
					} else if (procedureType == 20
							&& "21".equals(s1mmeXdrNew.getFalurecause())
							&& "26".equals(s1mmeXdrNew.getFalurecause())
							&& "28".equals(s1mmeXdrNew.getFalurecause())) {
						if ("20".equals(s1mmeXdrNew.getS1mmeXdr()
								.getProcedureType())
								&& ("21".equals(s1mmeXdrNew.getS1mmeXdr()
										.getRequestCause())
										|| "26".equals(s1mmeXdrNew.getS1mmeXdr()
												.getRequestCause()) || "28"
											.equals(s1mmeXdrNew.getS1mmeXdr()
													.getRequestCause()))) {
							for (S1mmeXdr s1mmeXdrTmp : s1mmeXdrList) {
								int procedureTypeTmp = StringUtils
										.isNotEmpty(s1mmeXdrTmp.getProcedureType()) ? Integer
										.valueOf(s1mmeXdrTmp.getProcedureType()) : null;
								if (((procedureTypeTmp == 21
										|| procedureTypeTmp == 26 || procedureTypeTmp == 28)
										&& s1mmeXdrTmp.getProcedureStartTime() >= s1mmeXdrNew
												.getS1mmeXdr()
												.getProcedureStartTime() && s1mmeXdrTmp.getProcedureStartTime() <= s1mmeXdrTmp.getProcedureStartTime() + 3 * 1000) == false) {
									s1mmeXdrNew.setEtype(14);
								}
							}
						}
					}
	
					if (procedureType == 16 && "1".equals(keyword1)) {
						s1mmeXdrNew.setEchk4gType(16);
					} else {
						s1mmeXdrNew.setEchk4gType(0);
					}
					if (procedureType == 16
							&& ("2".equals(keyword1) || "3".equals(keyword1))) {
						s1mmeXdrNew.setEchk4g23Type(17);
					} else {
						s1mmeXdrNew.setEchk4g23Type(0);
					}
					s1mmeXdrNew.setFlag(0);
					if (locGuserMark.getSpeedok() == 2
							&& (locGuserMark.getSlong() == 110.330997 || locGuserMark
									.getSlong() == 113.214038)
							&& (locGuserMark.getSlat() == 35.024218 || locGuserMark
									.getSlat() == 39.933898)) {
						s1mmeXdrNew.setBeforeflag(1);
					} else {
						s1mmeXdrNew.setBeforeflag(0);
					}
					s1mmeXdrNew.setEupordown(locGuserMark.getEupordown());
					if(switchPoint==null&&s1mmeXdrNew.getEtype()!=0){
						s1mmeXdrNew.setGridid(correctGrid(s1mmeXdrNew.getS1mmeXdr().getCellId(),s1mmeXdrNew.getEupordown(),htSwitChfpMap,professNetCellMap,publicNetCellMap));
					}else{
						s1mmeXdrNew.setGridid(Long.valueOf(switchPoint[0]));
					}
					result.add(s1mmeXdrNew);
			}
		}
		}
		return result;
	}

	/**
	 * gxRx\u63a5\u53e3 \u901f\u5ea6\u8ddd\u79bb\u5206\u6790
	 * 
	 * @param gxRxNewList
	 * @param locGuserMarkList
	 * @param professNetCellMap
	 * @param publicNetCellMap
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 * @throws ParseException 
	 */
	public List<GxRxNew> analyseGxRxXdrNew(List<GxXdr> gxRxList,
			List<LocGuserMark> locGuserMarkList,
			Map<String, String> professNetCellMap,
			Map<String, String> publicNetCellMap,
			Map<String, String[]> switchPointMap, Double distanceRange) throws ParseException {
		List<GxRxNew> result = new ArrayList<GxRxNew>();
		Date beginDate=org.apache.commons.lang.time.DateUtils.parseDate("2016-06-09 13", new String[]{"yyyy-MM-dd HH"});
		Calendar aa = Calendar.getInstance();
		aa.setTime(beginDate);
		long begintimemill = aa.getTimeInMillis();
		long endtimemill = aa.getTimeInMillis() + 3600 * 1000;
		for (GxXdr gxRx : gxRxList) {
			for (LocGuserMark locGuserMark : locGuserMarkList) {
				if (locGuserMark.getTimet() > begintimemill - 5000&&locGuserMark.getTimet() < endtimemill  
							&& gxRx.getProcedureStartTime() > locGuserMark.getTimet()
							&& gxRx.getProcedureStartTime() < locGuserMark.getTimetend()){
								GxRxNew gxRxNew=new GxRxNew();
								gxRxNew.setGxXdr(gxRx);
								gxRxNew.setGridid(Long.valueOf(-1));
								gxRxNew.setSlong(locGuserMark.getSlong());
								gxRxNew.setSlat(locGuserMark.getSlat());
								gxRxNew.setDlong(locGuserMark.getDlong());
								gxRxNew.setDlat(locGuserMark.getDlat());
								gxRxNew.setEspeed(AnalyseDistance.AnalyseSpeed(locGuserMark,
										gxRxNew.getGxXdr().getProcedureStartTime()));
								gxRxNew.setDistance(this._analyseGxRxDistance(locGuserMark,
										gxRxNew.getGxXdr().getProcedureStartTime()));
								String[] switchPoint = this._backfillXyGrid(switchPointMap,
										distanceRange, gxRxNew.getDistance(),
										locGuserMark.getSwitchfpDistance());
								gxRxNew.setGridid(Long.valueOf(switchPoint[0]));
								gxRxNew.setElong(null);
								gxRxNew.setElat(null);
								gxRxNew.setFalurecause(String.valueOf(gxRxNew.getGxXdr()
										.getResultCode()
										+ gxRxNew.getGxXdr().getExperimentalResultCode()
										+ gxRxNew.getGxXdr().getSessionReleaseCause()));
								if (gxRxNew.getGxXdr().getProcedureType() == 3
										&& gxRxNew.getGxXdr().getInterfaces() == 26
										&& ("0".equals(gxRxNew.getGxXdr().getAbortCause())
												|| "1".equals(gxRxNew.getGxXdr().getAbortCause())
												|| "2".equals(gxRxNew.getGxXdr().getAbortCause()) || "4".equals(gxRxNew
												.getGxXdr().getAbortCause()))) {
									if ("0".equals(gxRxNew.getGxXdr().getMediaType())) {
										gxRxNew.setEtype(5);
									} else if ("0".equals(gxRxNew.getGxXdr().getMediaType())) {
										gxRxNew.setEtype(7);
									} else {
										gxRxNew.setEtype(0);
									}
								}
								gxRxNew.setFlag(0);
								if (locGuserMark.getSpeedok() == 2
										&& (locGuserMark.getSlong() == 110.330997 || locGuserMark
												.getSlong() == 113.214038)
										&& (locGuserMark.getSlat() == 35.024218 || locGuserMark
												.getSlat() == 39.933898)) {
									gxRxNew.setBeforeflag(1);
								} else {
									gxRxNew.setBeforeflag(0);
								}
								gxRxNew.setEupordown(locGuserMark.getEupordown());
								result.add(gxRxNew);
				}
			}
		}
		return result;
	}

	/**
	 * rx\u63a5\u53e3\u7b97\u4e09\u6bb5\u8ddd\u79bb
	 * 
	 * @param locGuserMark
	 * @param procedureStartTime
	 * @return
	 */
	private Double _analyseGxRxDistance(LocGuserMark locGuserMark,
			long procedureStartTime) {
		Double distance;
		if (locGuserMark.getSpeedok() == 2) {
			distance = locGuserMark.getAvgspeed()
					* (procedureStartTime - locGuserMark.getTimet());
		} else if (locGuserMark.getSpeedok() == 1
				&& procedureStartTime > locGuserMark.getTimet2()) {
			distance = locGuserMark.getSpeedv2()
					* (procedureStartTime - locGuserMark.getTimet2())
					+ locGuserMark.getSpeedv1()
					* (locGuserMark.getTimet2() - locGuserMark.getTimet1())
					+ locGuserMark.getSpeedv0()
					* (locGuserMark.getTimet1() - locGuserMark.getTimet0());

		} else if (locGuserMark.getSpeedok() == 1
				&& procedureStartTime > locGuserMark.getTimet1()) {
			distance = locGuserMark.getSpeedv1()
					* (procedureStartTime - locGuserMark.getTimet1())
					+ locGuserMark.getSpeedv0()
					* (locGuserMark.getTimet1() - locGuserMark.getTimet0());
		} else {
			distance = locGuserMark.getAvgspeed()
					* (procedureStartTime - locGuserMark.getTimet0());
		}
		return distance / (1000 * 60 * 60);
	}

	/**
	 * \u6805\u683c\u7ecf\u7eac\u5ea6\u56de\u586b \u6839\u636e \u8d77\u59cb\u70b9\u5230\u5207\u6362\u70b9\u8ddd\u79bb + \u5207\u6362\u70b9\u8ddd\u79bb \u5224\u65ad\u662f\u5426\u5728\u8303\u56f4\u5185 \u53d6\u6805\u683c\u7ecf\u7eac\u5ea6
	 * 
	 * @param switchPointMap
	 * @param distanceRange
	 * @return
	 */
	private String[] _backfillXyGrid(Map<String, String[]> switchPointMap,
			Double distanceRange, Double distance, Double SwitchDistance) {
		Iterator<String> it = switchPointMap.keySet().iterator();
		String[] switchPoint = null;
		while (it.hasNext()) {
			String key = it.next().toString();
			double val = Math.abs(Double.valueOf(key)
					- (distance + SwitchDistance));
			if (val <= distanceRange) {
				switchPoint = switchPointMap.get(key);
				break;
			}
		}
		return switchPoint;

	}

}
