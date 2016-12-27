package cn.com.dtmobile.hadoop.biz.train.model.numberiden;

import cn.com.dtmobile.hadoop.util.ParseUtils;

public class UpDownTrain {

	private Long imsi;
	private String xdrid;
	private String groupName;
	private Long intime;
	private Long outtime;
	private String instation;
	private String outstation;
	
	public UpDownTrain(String[] val) {
		imsi = ParseUtils.parseLong(val[0]);
		xdrid = val[1];
		groupName = val[2];
		intime = ParseUtils.parseLong(val[3]);
		instation = val[4];
		outtime = ParseUtils.parseLong(val[5]);
		if(6 == val.length){
			outstation = "";
		}else{
			outstation = val[6];
		}
		
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

}
