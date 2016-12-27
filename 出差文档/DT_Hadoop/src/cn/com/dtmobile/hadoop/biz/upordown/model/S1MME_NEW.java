package cn.com.dtmobile.hadoop.biz.upordown.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class S1MME_NEW {
	private String imsi; 
	private String procedureType;
	private Long procedureStartTime;
	private String procedureStatus;
	private String cellId;
	
	public S1MME_NEW(String[] values){
		this.imsi = values[5];
		this.procedureType = values[8];
		this.procedureStartTime = ParseUtils.parseLong(values[9]);
		this.procedureStatus = values[11];
		this.cellId = values[33];
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureType);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureStatus);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureStartTime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(cellId);
		return sb.toString();
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
