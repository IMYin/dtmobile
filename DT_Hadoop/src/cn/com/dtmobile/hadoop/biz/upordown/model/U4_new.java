package cn.com.dtmobile.hadoop.biz.upordown.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;

public class U4_new {
	String imsi;
	String xdrid;

	int upordown;
	String groupname;
	String groupmapping;

	public U4_new(String[] values) {
		this.imsi = values[0];
		this.xdrid = values[1];
		this.upordown = ParseUtils.parseInteger(values[2]);
		this.groupname = values[3];
		this.groupmapping = values[4];
	}

	public String getXdrid() {
		return xdrid;
	}

	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public int getUpordown() {
		return upordown;
	}

	public void setUpordown(int upordown) {
		this.upordown = upordown;
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