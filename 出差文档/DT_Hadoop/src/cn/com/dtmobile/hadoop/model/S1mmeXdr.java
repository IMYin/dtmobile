package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class S1mmeXdr {
	private CommXdr commXdr;
	private String procedureType;
	private Long procedureStartTime;
	private Long procedureEndTime;
	private String procedureStatus;
	private String requestCause;
	private String failureCause;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String mmeUeS1apId;
	private String oldMmeGroupId;
	private String oldMmeCode;
	private String oldMtmsi;
	private String mmeGroupId;
	private String mmeCode;
	private String mTmsi;
	private String tmsi;
	private String userIpv4;
	private String userIpv6;
	private String mmeIpAdd;
	private String enbIpAdd;
	private String mmePort;
	private String enbPort;
	private String tac;
	private String cellId;
	private String otherTac;
	private String otherEci;
	private String apn;
	private Integer epsBearerNumber;
	private String[] bearerArr;
	private String rangeTime;

	public S1mmeXdr() {
		
	}
	public S1mmeXdr(String[] values) {
		commXdr = new CommXdr(values);
		this.procedureType = values[8];
		this.procedureStartTime = ParseUtils.parseLong(values[9]);
		this.procedureEndTime = ParseUtils.parseLong(values[10]);
		this.procedureStatus = values[11];
		this.requestCause = values[12];
		this.failureCause = values[13];
		this.keyword1 = values[14];
		this.keyword2 = values[15];
		this.keyword3 = values[16];
		this.keyword4 = values[17];
		this.mmeUeS1apId = values[18];
		this.oldMmeGroupId = values[19];
		this.oldMmeCode = values[20];
		this.oldMtmsi = values[21];
		this.mmeGroupId = values[22];
		this.mmeCode = values[23];
		this.mTmsi = values[24];
		this.tmsi = values[25];
		this.userIpv4 = values[26];
		this.userIpv6 = values[27];
		this.mmeIpAdd = values[28];
		this.enbIpAdd = values[29];
		this.mmePort = values[30];
		this.enbPort = values[31];
		this.tac = values[32];
		this.cellId = values[33];
		this.otherTac = values[34];
		this.otherEci = values[35];
		this.apn = values[36];
		//this.epsBearerNumber = ParseUtils.parseInteger(values[36]);
		/*Integer start = 38;
		Integer step = 8;
		for (Integer i = 0; i < this.epsBearerNumber; i++) {
			StringBuffer sb = new StringBuffer();
			for (Integer j = start; j <= start + step; j++) {
				sb.append(values[j]);
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
			}
			start = start + step;
			sb.deleteCharAt(sb.length() - 1);
			bearerArr[i] = sb.toString();
		}*/
		this.rangeTime = values[values.length - 1];
	}

	
	public String toString(){
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
		sb.append(requestCause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(failureCause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(keyword1);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(keyword2);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(keyword3);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(keyword4);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeUeS1apId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(oldMmeGroupId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(oldMmeCode);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(oldMtmsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeGroupId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeCode);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mTmsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(tmsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(userIpv4);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(userIpv6);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeIpAdd);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(enbIpAdd);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmePort);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(enbPort);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(tac);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(cellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(otherTac);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(otherEci);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(apn);
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
	public CommXdr getCommXdr() {
		return commXdr;
	}

	public void setCommXdr(CommXdr commXdr) {
		this.commXdr = commXdr;
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

	public Long getProcedureEndTime() {
		return procedureEndTime;
	}

	public void setProcedureEndTime(Long procedureEndTime) {
		this.procedureEndTime = procedureEndTime;
	}

	public String getProcedureStatus() {
		return procedureStatus;
	}

	public void setProcedureStatus(String procedureStatus) {
		this.procedureStatus = procedureStatus;
	}

	public String getRequestCause() {
		return requestCause;
	}

	public void setRequestCause(String requestCause) {
		this.requestCause = requestCause;
	}

	public String getFailureCause() {
		return failureCause;
	}

	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	public String getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}

	public String getMmeUeS1apId() {
		return mmeUeS1apId;
	}

	public void setMmeUeS1apId(String mmeUeS1apId) {
		this.mmeUeS1apId = mmeUeS1apId;
	}

	public String getOldMmeGroupId() {
		return oldMmeGroupId;
	}

	public void setOldMmeGroupId(String oldMmeGroupId) {
		this.oldMmeGroupId = oldMmeGroupId;
	}

	public String getOldMmeCode() {
		return oldMmeCode;
	}

	public void setOldMmeCode(String oldMmeCode) {
		this.oldMmeCode = oldMmeCode;
	}

	public String getOldMtmsi() {
		return oldMtmsi;
	}

	public void setOldMtmsi(String oldMtmsi) {
		this.oldMtmsi = oldMtmsi;
	}

	public String getMmeGroupId() {
		return mmeGroupId;
	}

	public void setMmeGroupId(String mmeGroupId) {
		this.mmeGroupId = mmeGroupId;
	}

	public String getMmeCode() {
		return mmeCode;
	}

	public void setMmeCode(String mmeCode) {
		this.mmeCode = mmeCode;
	}

	public String getmTmsi() {
		return mTmsi;
	}

	public void setmTmsi(String mTmsi) {
		this.mTmsi = mTmsi;
	}

	public String getTmsi() {
		return tmsi;
	}

	public void setTmsi(String tmsi) {
		this.tmsi = tmsi;
	}

	public String getUserIpv4() {
		return userIpv4;
	}

	public void setUserIpv4(String userIpv4) {
		this.userIpv4 = userIpv4;
	}

	public String getUserIpv6() {
		return userIpv6;
	}

	public void setUserIpv6(String userIpv6) {
		this.userIpv6 = userIpv6;
	}

	public String getMmeIpAdd() {
		return mmeIpAdd;
	}

	public void setMmeIpAdd(String mmeIpAdd) {
		this.mmeIpAdd = mmeIpAdd;
	}

	public String getEnbIpAdd() {
		return enbIpAdd;
	}

	public void setEnbIpAdd(String enbIpAdd) {
		this.enbIpAdd = enbIpAdd;
	}

	public String getMmePort() {
		return mmePort;
	}

	public void setMmePort(String mmePort) {
		this.mmePort = mmePort;
	}

	public String getEnbPort() {
		return enbPort;
	}

	public void setEnbPort(String enbPort) {
		this.enbPort = enbPort;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getOtherTac() {
		return otherTac;
	}

	public void setOtherTac(String otherTac) {
		this.otherTac = otherTac;
	}

	public String getOtherEci() {
		return otherEci;
	}

	public void setOtherEci(String otherEci) {
		this.otherEci = otherEci;
	}

	public String getApn() {
		return apn;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public Integer getEpsBearerNumber() {
		return epsBearerNumber;
	}

	public void setEpsBearerNumber(Integer epsBearerNumber) {
		this.epsBearerNumber = epsBearerNumber;
	}

	public String[] getBearerArr() {
		return bearerArr;
	}

	public void setBearerArr(String[] bearerArr) {
		this.bearerArr = bearerArr;
	}

	public String getRangeTime() {
		return rangeTime;
	}
	public void setRangeTime(String rangeTime) {
		this.rangeTime = rangeTime;
	}
}
