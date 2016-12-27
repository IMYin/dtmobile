package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

import cn.com.dtmobile.hadoop.model.GxXdr;
import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class GxRxNew {
	private GxXdr gxXdr;
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
	
	public GxRxNew() {
		
	}
	
	public GxRxNew(String [] arr) {
		gxXdr = new GxXdr(arr);
		this.etype = ParseUtils.parseInteger(arr[29]);
		this.elong = ParseUtils.parseDouble(arr[37]);
		this.elat = ParseUtils.parseDouble(arr[38]);
		this.eupordown = ParseUtils.parseInteger(arr[42]);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(gxXdr.toString());
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
	 * @return the gxXdr
	 */
	public GxXdr getGxXdr() {
		return gxXdr;
	}
	/**
	 * @param gxXdr the gxXdr to set
	 */
	public void setGxXdr(GxXdr gxXdr) {
		this.gxXdr = gxXdr;
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
	
}
