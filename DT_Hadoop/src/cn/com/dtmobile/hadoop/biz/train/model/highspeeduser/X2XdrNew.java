package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

import cn.com.dtmobile.hadoop.model.X2;
import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class X2XdrNew {
	
	private X2 x2Xdr;
	private Long gridid;
	private Double slong;
	private Double slat;
	private Double dlong;
	private Double dlat;
	private Double distance;
	private Double espeed;
	private Double elong;
	private Double elat;
	private String falurecause;
	private int etype;
	private int flag;
	private int beforeflag;
	private int eupordown;
	public X2XdrNew(String [] str) {
		this.x2Xdr = new X2(str);
		this.etype = ParseUtils.parseInteger(str[str.length-2]);
//		this.elong = ParseUtils.parseDouble(str[30]);
//		this.elat = ParseUtils.parseDouble(str[31]);
//		this.eupordown = ParseUtils.parseInteger(str[35]);
	}

	public X2XdrNew() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(x2Xdr.toString().substring(0,x2Xdr.toString().lastIndexOf(StringUtils.DELIMITER_INNER_ITEM)));
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(etype);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gridid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(slong);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(slat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dlong);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dlat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(distance);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(espeed);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(elong);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(elat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(falurecause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(flag);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(beforeflag);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(eupordown);
		return sb.toString();
	}
	
	/**
	 * @return the gridid
	 */
	public Long getGridid() {
		return gridid;
	}
	/**
	 * @param gridid the gridid to set
	 */
	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}
	/**
	 * @return the slong
	 */
	public Double getSlong() {
		return slong;
	}
	/**
	 * @param slong the slong to set
	 */
	public void setSlong(Double slong) {
		this.slong = slong;
	}
	/**
	 * @return the slat
	 */
	public Double getSlat() {
		return slat;
	}
	/**
	 * @param slat the slat to set
	 */
	public void setSlat(Double slat) {
		this.slat = slat;
	}
	/**
	 * @return the dlong
	 */
	public Double getDlong() {
		return dlong;
	}
	/**
	 * @param dlong the dlong to set
	 */
	public void setDlong(Double dlong) {
		this.dlong = dlong;
	}
	/**
	 * @return the dlat
	 */
	public Double getDlat() {
		return dlat;
	}
	/**
	 * @param dlat the dlat to set
	 */
	public void setDlat(Double dlat) {
		this.dlat = dlat;
	}
	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	/**
	 * @return the espeed
	 */
	public Double getEspeed() {
		return espeed;
	}
	/**
	 * @param espeed the espeed to set
	 */
	public void setEspeed(Double espeed) {
		this.espeed = espeed;
	}
	/**
	 * @return the elong
	 */
	public Double getElong() {
		return elong;
	}
	/**
	 * @param elong the elong to set
	 */
	public void setElong(Double elong) {
		this.elong = elong;
	}
	/**
	 * @return the elat
	 */
	public Double getElat() {
		return elat;
	}
	/**
	 * @param elat the elat to set
	 */
	public void setElat(Double elat) {
		this.elat = elat;
	}
	/**
	 * @return the falurecause
	 */
	public String getFalurecause() {
		return falurecause;
	}
	/**
	 * @param falurecause the falurecause to set
	 */
	public void setFalurecause(String falurecause) {
		this.falurecause = falurecause;
	}
	/**
	 * @return the etype
	 */
	public int getEtype() {
		return etype;
	}
	/**
	 * @param etype the etype to set
	 */
	public void setEtype(int etype) {
		this.etype = etype;
	}
	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
	/**
	 * @return the beforeflag
	 */
	public int getBeforeflag() {
		return beforeflag;
	}
	/**
	 * @param beforeflag the beforeflag to set
	 */
	public void setBeforeflag(int beforeflag) {
		this.beforeflag = beforeflag;
	}
	/**
	 * @return the eupordown
	 */
	public int getEupordown() {
		return eupordown;
	}
	/**
	 * @param eupordown the eupordown to set
	 */
	public void setEupordown(int eupordown) {
		this.eupordown = eupordown;
	}


	
	
	public void setX2Xdr(X2 x2Xdr) {
		this.x2Xdr = x2Xdr;
	}

	public X2 getX2Xdr() {
		return x2Xdr;
	}
}