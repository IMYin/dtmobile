package cn.com.dtmobile.hadoop.biz.upordown.model;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class U2Xdr {

	String imsi;
	String xdrid;
	String cellid;
	String targetcellid;
	int upordown;
	Long procedurestarttime;
	String rangetime;
	int seqnum;
	String groupname;
	String groupmapping;

	public U2Xdr(String[] values) {
		this.imsi = values[0];
		this.xdrid = values[1];
		this.cellid = values[2];
		this.targetcellid = values[3];
		this.upordown = values[4].equals("") ? 0 : Integer.parseInt(values[4]);
		this.procedurestarttime = values[5].equals("") ? 0 : Long
				.parseLong(values[5]);
		this.rangetime = values[6];
		this.seqnum = values[7].equals("") ? 0 : Integer.parseInt(values[7]);
		this.groupname = values[8];
		this.groupmapping = values[9];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(cellid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetcellid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedurestarttime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		return sb.toString();
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getXdrid() {
		return xdrid;
	}

	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}

	public String getCellid() {
		return cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public String getTargetcellid() {
		return targetcellid;
	}

	public void setTargetcellid(String targetcellid) {
		this.targetcellid = targetcellid;
	}

	public int getUpordown() {
		return upordown;
	}

	public void setUpordown(int upordown) {
		this.upordown = upordown;
	}

	public Long getProcedurestarttime() {
		return procedurestarttime;
	}

	public void setProcedurestarttime(Long procedurestarttime) {
		this.procedurestarttime = procedurestarttime;
	}

	public String getRangetime() {
		return rangetime;
	}

	public void setRangetime(String rangetime) {
		this.rangetime = rangetime;
	}

	public int getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(int seqnum) {
		this.seqnum = seqnum;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupmapping() {
		return groupmapping;
	}

	public void setGroupmapping(String groupmapping) {
		this.groupmapping = groupmapping;
	}

}
