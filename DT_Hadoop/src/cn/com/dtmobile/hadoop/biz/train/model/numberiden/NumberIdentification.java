package cn.com.dtmobile.hadoop.biz.train.model.numberiden;

import cn.com.dtmobile.hadoop.util.StringUtils;


public class NumberIdentification {

	private Long imsi;
	private String xdrid;
	private String cellId;
	private int upOrDown;
	private String groupName;
	private Long intime;
	private Long outtime;
	private String instation;
	private String outstation;
	private Long sortTime;
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(xdrid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(cellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(upOrDown);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(groupName);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(intime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(outtime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(instation);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(outstation);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(sortTime);
		return sb.toString();
	}
	public Long getImsi() {
		return imsi;
	}
	public void setImsi(Long imsi) {
		this.imsi = imsi;
	}
	public String getXdrid() {
		return xdrid;
	}
	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}
	public int getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(int upOrDown) {
		this.upOrDown = upOrDown;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getIntime() {
		return intime;
	}
	public void setIntime(Long intime) {
		this.intime = intime;
	}
	public Long getOuttime() {
		return outtime;
	}
	public void setOuttime(Long outtime) {
		this.outtime = outtime;
	}
	public String getInstation() {
		return instation;
	}
	public void setInstation(String instation) {
		this.instation = instation;
	}
	public String getOutstation() {
		return outstation;
	}
	public void setOutstation(String outstation) {
		this.outstation = outstation;
	}
	public Long getSortTime() {
		return sortTime;
	}
	public void setSortTime(Long sortTime) {
		this.sortTime = sortTime;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

}
