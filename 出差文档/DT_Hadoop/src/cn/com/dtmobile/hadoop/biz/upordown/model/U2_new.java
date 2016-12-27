package cn.com.dtmobile.hadoop.biz.upordown.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class U2_new {
	String cellid;
	String targetcellid;
	Long procedurestarttime;
	String groupname;
	
	

	public U2_new(String[] values){
		this.cellid = values[0];
		this.targetcellid = values[1];
		this.procedurestarttime = ParseUtils.parseLong(values[2]);
		this.groupname= values[3];
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(cellid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetcellid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedurestarttime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(groupname);
		return sb.toString();
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
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}