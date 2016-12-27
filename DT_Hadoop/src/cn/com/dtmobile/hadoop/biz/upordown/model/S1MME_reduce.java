package cn.com.dtmobile.hadoop.biz.upordown.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;

public class S1MME_reduce {
	private String imsi; 
	private String procedureType;
	private Long procedureStartTime;
	private String procedureStatus;
	private String cellId;
	
	public S1MME_reduce(String[] values){
		this.imsi = values[0];
		this.procedureType = values[1];
		this.procedureStartTime = ParseUtils.parseLong(values[2]);
		this.procedureStatus = values[3];
		this.cellId = values[4];
	} 
	
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getProcedureType() {
		return procedureType;
	}
	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}
	public Long getProcedureStartTime() {
		return procedureStartTime;
	}
	public void setProcedureStartTime(Long procedureStartTime) {
		this.procedureStartTime = procedureStartTime;
	}
	public String getProcedureStatus() {
		return procedureStatus;
	}
	public void setProcedureStatus(String procedureStatus) {
		this.procedureStatus = procedureStatus;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
}