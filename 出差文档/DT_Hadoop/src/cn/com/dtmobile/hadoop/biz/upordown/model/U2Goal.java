package cn.com.dtmobile.hadoop.biz.upordown.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;

public class U2Goal {
	String groupname;
	String cellid;
	String targetcellid;
	Long procedurestarttime;

	public U2Goal(String[] values) {
		this.groupname = values[0];
		this.cellid = values[1];
		this.targetcellid = values[2];
		this.procedurestarttime = ParseUtils.parseLong(values[3]);
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
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

	public Long getProcedurestarttime() {
		return procedurestarttime;
	}

	public void setProcedurestarttime(Long procedurestarttime) {
		this.procedurestarttime = procedurestarttime;
	}
}
