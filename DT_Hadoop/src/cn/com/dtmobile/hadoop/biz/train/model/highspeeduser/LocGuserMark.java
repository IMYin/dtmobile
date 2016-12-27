package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;


public class LocGuserMark {
	private String id;
	private String imsi;
	private Double slong;
	private Double slat;
	private Double dlong;
	private Double dlat;
	private Double avgspeed;
	private Double speedv0;
	private Double speedv1;
	private Double speedv2;
	private Long timet;
	private Long timet0;
	private Long timet1;
	private Long timet2;
	private Long timetend;
	private String rangetime;
	private int speedok;
	private int flag;
	private int chkflag;
	private int eupordown;
	private String swp;
	private String remark2;
	private String remark3;
	private String remark4;
	private String remark5;
	/**
	 * \u8f85\u52a9\u5b57\u6bb5
	 */
	private int dir_State;
	private Long procedureStartTime;
	private Double nlong;
	private Double nlat;
	//\u8d77\u70b9\u5230\u5207\u6362\u70b9\u8ddd\u79bb
	private Double switchfpDistance;

	public LocGuserMark() {
		
	}


	public LocGuserMark(String [] arr) {
		this.id=arr[0];
		this.imsi = arr[1];
		this.slong = ParseUtils.parseDouble(arr[2]);
		this.slat = ParseUtils.parseDouble(arr[3]);
		this.dlong =ParseUtils.parseDouble(arr[4]);
		this.dlat = ParseUtils.parseDouble(arr[5]);
		this.avgspeed = ParseUtils.parseDouble(arr[6]);
		this.speedv0 = ParseUtils.parseDouble(arr[7]);
		this.speedv1 = ParseUtils.parseDouble(arr[8]);
		this.speedv2 = ParseUtils.parseDouble(arr[9]);
		this.timet =  ParseUtils.parseLong(arr[10]) ;
		this.timet0 =  ParseUtils.parseLong(arr[11]) ;
		this.timet1 =  ParseUtils.parseLong(arr[12]) ;
		this.timet2 = ParseUtils.parseLong(arr[13]) ;
		this.timetend = ParseUtils.parseLong(arr[14]) ;
		this.rangetime =arr[15];
		this.speedok = ParseUtils.parseInteger(arr[16]);
		this.flag = ParseUtils.parseInteger(arr[17]) ;
		this.chkflag =  ParseUtils.parseInteger(arr[18]);
		this.eupordown =  ParseUtils.parseInteger(arr[19]) ;
		this.swp = StringUtils.isNotEmpty(arr[20]) ? arr[20] : null;
		this.remark2 = arr[21];
		this.remark3 = arr[22];
		this.remark4 = arr[23];
		this.remark5 = arr[24];
		this.switchfpDistance = ParseUtils.parseDouble(arr[25]);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(slong);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(slat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dlong);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dlat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(avgspeed);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(speedv0);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(speedv1);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(speedv2);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(timet);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(timet0);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(timet1);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(timet2);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(timetend);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(rangetime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(speedok);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(flag);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(chkflag);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(eupordown);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(swp);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(remark2);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(remark3);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(remark4);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(remark5);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(switchfpDistance);
		return sb.toString();
	}

	public LocGuserMark(String imsi, Double slong, Double slat,
			Double dlong, Double dlat, Double avgspeed, Long timet,
			Long timetend, String rangetime, int speedok, int flag,
			int chkflag, int eupordown, String swp) {
		this.imsi = imsi;
		this.slong = slong;
		this.slat = slat;
		this.dlong = dlong;
		this.dlat = dlat;
		this.avgspeed = avgspeed;
		this.timet = timet;
		this.timetend = timetend;
		this.rangetime = rangetime;
		this.speedok = speedok;
		this.flag = flag;
		this.chkflag = chkflag;
		this.eupordown = eupordown;
		this.swp = swp;
	}


	public LocGuserMark( String imsi, Double slong, Double slat,
			Double dlong, Double dlat, Double avgspeed,  Long timet,  Long timetend, String rangetime
			) {
		this.imsi = imsi;
		this.slong = slong;
		this.slat = slat;
		this.dlong = dlong;
		this.dlat = dlat;
		this.avgspeed = avgspeed;
		this.timet = timet;
		this.timetend = timetend;
		this.rangetime = rangetime;
	}
	
	public LocGuserMark(String imsi, Double nlong, Double nlat,
			String rangetime, int dir_State, Long procedureStartTime) {
		super();
		this.imsi = imsi;
		this.nlong = nlong;
		this.nlat = nlat;
		this.rangetime = rangetime;
		this.dir_State = dir_State;
		this.procedureStartTime = procedureStartTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Double getSlong() {
		return slong;
	}

	public void setSlong(Double slong) {
		this.slong = slong;
	}

	public Double getSlat() {
		return slat;
	}

	public void setSlat(Double slat) {
		this.slat = slat;
	}

	public Double getDlong() {
		return dlong;
	}

	public void setDlong(Double dlong) {
		this.dlong = dlong;
	}

	public Double getDlat() {
		return dlat;
	}

	public void setDlat(Double dlat) {
		this.dlat = dlat;
	}

	public Double getAvgspeed() {
		return avgspeed;
	}

	public void setAvgspeed(Double avgspeed) {
		this.avgspeed = avgspeed;
	}

	public Double getSpeedv0() {
		return speedv0;
	}

	public void setSpeedv0(Double speedv0) {
		this.speedv0 = speedv0;
	}

	public Double getSpeedv1() {
		return speedv1;
	}

	public void setSpeedv1(Double speedv1) {
		this.speedv1 = speedv1;
	}

	public Double getSpeedv2() {
		return speedv2;
	}

	public void setSpeedv2(Double speedv2) {
		this.speedv2 = speedv2;
	}

	public Long getTimet() {
		return timet;
	}

	public void setTimet(Long timet) {
		this.timet = timet;
	}

	public Long getTimet0() {
		return timet0;
	}

	public void setTimet0(Long timet0) {
		this.timet0 = timet0;
	}

	public Long getTimet1() {
		return timet1;
	}

	public void setTimet1(Long timet1) {
		this.timet1 = timet1;
	}

	public Long getTimet2() {
		return timet2;
	}

	public void setTimet2(Long timet2) {
		this.timet2 = timet2;
	}

	public Long getTimetend() {
		return timetend;
	}

	public void setTimetend(Long timetend) {
		this.timetend = timetend;
	}

	public String getRangetime() {
		return rangetime;
	}

	public void setRangetime(String rangetime) {
		this.rangetime = rangetime;
	}

	public int getSpeedok() {
		return speedok;
	}

	public void setSpeedok(int speedok) {
		this.speedok = speedok;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getChkflag() {
		return chkflag;
	}

	public void setChkflag(int chkflag) {
		this.chkflag = chkflag;
	}

	public int getEupordown() {
		return eupordown;
	}

	public void setEupordown(int eupordown) {
		this.eupordown = eupordown;
	}

	public String getSwp() {
		return swp;
	}

	public void setSwp(String swp) {
		this.swp = swp;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	public String getRemark5() {
		return remark5;
	}

	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}

	/**
	 * @return the dir_State
	 */
	public int getDir_State() {
		return dir_State;
	}

	/**
	 * @param dir_State the dir_State to set
	 */
	public void setDir_State(int dir_State) {
		this.dir_State = dir_State;
	}

	/**
	 * @return the procedureStartTime
	 */
	public Long getProcedureStartTime() {
		return procedureStartTime;
	}

	/**
	 * @param procedureStartTime the procedureStartTime to set
	 */
	public void setProcedureStartTime(Long procedureStartTime) {
		this.procedureStartTime = procedureStartTime;
	}

	/**
	 * @return the nlong
	 */
	public Double getNlong() {
		return nlong;
	}

	/**
	 * @param nlong the nlong to set
	 */
	public void setNlong(Double nlong) {
		this.nlong = nlong;
	}

	/**
	 * @return the nlat
	 */
	public Double getNlat() {
		return nlat;
	}

	/**
	 * @param nlat the nlat to set
	 */
	public void setNlat(Double nlat) {
		this.nlat = nlat;
	}


	/**
	 * @return the switchfpDistance
	 */
	public Double getSwitchfpDistance() {
		return switchfpDistance;
	}


	/**
	 * @param switchfpDistance the switchfpDistance to set
	 */
	public void setSwitchfpDistance(Double switchfpDistance) {
		this.switchfpDistance = switchfpDistance;
	}

	
	
}
