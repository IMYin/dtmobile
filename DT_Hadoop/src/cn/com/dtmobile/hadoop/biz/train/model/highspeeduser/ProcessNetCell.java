package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

import org.apache.commons.lang.StringUtils;

public class ProcessNetCell {
	private String cellid;
	private int rrunum;
	private String lat;
	private String lon;
	private String seqnum;
	private String tac;
    
	public ProcessNetCell(String[] values){
		this.cellid = values[3];
		this.rrunum = StringUtils.isNotEmpty(values[11]) ? Integer.valueOf(values[11]) : null;
		this.lat = values[6];
		this.lon = values[7];
		this.seqnum = values[12];
		this.tac=values[1];
	}
	
	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getCellid() {
		return cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public int getRrunum() {
		return rrunum;
	}

	public void setRrunum(int rrunum) {
		this.rrunum = rrunum;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}
}
