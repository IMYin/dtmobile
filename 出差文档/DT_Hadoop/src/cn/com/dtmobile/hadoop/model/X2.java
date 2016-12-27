package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class X2 {
	 private CommXdr commXdr;
	 private String procedureType; 
	 private Long procedureStartTime; 
	 private Long procedureEndTime ;
	 private Integer procedureStatus;
	 private Long cellId;
	 private String targetCellId;
	 private Long enbid;
	 private String targetEnbId;
	 private String mmeUes1apId;
	 private String mmeGroupId;
	 private String mmeCode;
	 private String requestCause; 
	 private String failureCause; 
	 private Integer epsBearerNumber; 
	 private String [] bearerArr;
	 private String rangeTime;
	 
	public X2(String [] values) {
		this.commXdr = new CommXdr(values);
		this.procedureType = values[8];
		this.procedureStartTime = ParseUtils.parseLong(values[9]);
		this.procedureEndTime = ParseUtils.parseLong(values[10]);
		this.procedureStatus = ParseUtils.parseInteger(values[11]);
		this.cellId = ParseUtils.parseLong(values[12]);
		this.targetCellId = values[13];
		this.enbid = ParseUtils.parseLong(values[14]);
		this.targetEnbId = values[15];
		this.mmeUes1apId = values[16];
		this.mmeGroupId = values[17];
		this.mmeCode = values[18];
		this.requestCause = values[19];
		this.failureCause = values[20];
		/*this.epsBearerNumber = ParseUtils.parseInteger(values[22]);
		bearerArr = new String[this.epsBearerNumber];
		int start = 20;
		int step = 4;
		for (int i = 0; i < this.epsBearerNumber; i++) {
			StringBuffer sb = new StringBuffer();
			for (int j = start; j <= start + step; j++) {
				sb.append(values[j]);
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
			}
			start = start + step;
			sb.deleteCharAt(sb.length() - 1);
			bearerArr[i] = sb.toString();
		}*/
		this.rangeTime = values[values.length-1];
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(commXdr.toString());
		sb.append(procedureType);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureStartTime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureEndTime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureStatus); 
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(cellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetCellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(enbid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetEnbId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeUes1apId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeGroupId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeCode);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(requestCause); 
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(failureCause); 
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		/*sb.append(epsBearerNumber); 
		sb.append(StringUtils.DELIMITER_INNER_ITEM);*/
		/*for(String val : this.bearerArr){
			sb.append(val);
			sb.append(StringUtils.DELIMITER_INNER_ITEM);
		}*/
		sb.append(rangeTime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		return sb.toString();
	}
	/**
	 * @return the commXdr
	 */
	public CommXdr getCommXdr() {
		return commXdr;
	}
	/**
	 * @param commXdr the commXdr to set
	 */
	public void setCommXdr(CommXdr commXdr) {
		this.commXdr = commXdr;
	}

	/**
	 * @return the procedureType
	 */
	public String getProcedureType() {
		return procedureType;
	}

	/**
	 * @param procedureType the procedureType to set
	 */
	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	/**
	 * @return the procedureStartTime
	 */
	public Long getProcedureStartTime() {
		return procedureStartTime;
	}
	/**
	 * @param procedureStartTime the procedureStartTime to set
	 */
	public void setProcedureStartTime(Long procedureStartTime) {
		this.procedureStartTime = procedureStartTime;
	}
	/**
	 * @return the procedureEndTime
	 */
	public Long getProcedureEndTime() {
		return procedureEndTime;
	}
	/**
	 * @param procedureEndTime the procedureEndTime to set
	 */
	public void setProcedureEndTime(Long procedureEndTime) {
		this.procedureEndTime = procedureEndTime;
	}
	/**
	 * @return the procedureStatus
	 */
	public Integer getProcedureStatus() {
		return procedureStatus;
	}
	/**
	 * @param procedureStatus the procedureStatus to set
	 */
	public void setProcedureStatus(Integer procedureStatus) {
		this.procedureStatus = procedureStatus;
	}
	/**
	 * @return the sourceCellId
	 */
	/**
	 * @return the targetCellId
	 */
	public String getTargetCellId() {
		return targetCellId;
	}
	/**
	 * @param targetCellId the targetCellId to set
	 */
	public void setTargetCellId(String targetCellId) {
		this.targetCellId = targetCellId;
	}
	/**
	 * @return the sourceEnbId
	 */
	/**
	 * @return the targetEnbId
	 */
	public String getTargetEnbId() {
		return targetEnbId;
	}
	/**
	 * @param targetEnbId the targetEnbId to set
	 */
	public void setTargetEnbId(String targetEnbId) {
		this.targetEnbId = targetEnbId;
	}
	/**
	 * @return the mmeUes1apId
	 */
	public String getMmeUes1apId() {
		return mmeUes1apId;
	}
	/**
	 * @param mmeUes1apId the mmeUes1apId to set
	 */
	public void setMmeUes1apId(String mmeUes1apId) {
		this.mmeUes1apId = mmeUes1apId;
	}
	/**
	 * @return the mmeGroupId
	 */
	public String getMmeGroupId() {
		return mmeGroupId;
	}
	/**
	 * @param mmeGroupId the mmeGroupId to set
	 */
	public void setMmeGroupId(String mmeGroupId) {
		this.mmeGroupId = mmeGroupId;
	}
	/**
	 * @return the mmeCode
	 */
	public String getMmeCode() {
		return mmeCode;
	}
	/**
	 * @param mmeCode the mmeCode to set
	 */
	public void setMmeCode(String mmeCode) {
		this.mmeCode = mmeCode;
	}
	/**
	 * @return the requestCause
	 */
	public String getRequestCause() {
		return requestCause;
	}
	/**
	 * @param requestCause the requestCause to set
	 */
	public void setRequestCause(String requestCause) {
		this.requestCause = requestCause;
	}
	/**
	 * @return the failureCause
	 */
	public String getFailureCause() {
		return failureCause;
	}
	/**
	 * @param failureCause the failureCause to set
	 */
	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}
	
	/**
	 * @return the epsBearerNumber
	 */
	public Integer getEpsBearerNumber() {
		return epsBearerNumber;
	}

	/**
	 * @param epsBearerNumber the epsBearerNumber to set
	 */
	public void setEpsBearerNumber(Integer epsBearerNumber) {
		this.epsBearerNumber = epsBearerNumber;
	}

	/**
	 * @return the bearerArr
	 */
	public String[] getBearerArr() {
		return bearerArr;
	}
	/**
	 * @param bearerArr the bearerArr to set
	 */
	public void setBearerArr(String[] bearerArr) {
		this.bearerArr = bearerArr;
	}


	/**
	 * @return the cellId
	 */
	public Long getCellId() {
		return cellId;
	}

	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(Long cellId) {
		this.cellId = cellId;
	}

	/**
	 * @return the enbid
	 */
	public Long getEnbid() {
		return enbid;
	}

	/**
	 * @param enbid the enbid to set
	 */
	public void setEnbid(Long enbid) {
		this.enbid = enbid;
	}

	public String getRangeTime() {
		return rangeTime;
	}

	public void setRangeTime(String rangeTime) {
		this.rangeTime = rangeTime;
	}


}
