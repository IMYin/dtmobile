package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

/**
 * SV接口
 * @author zhangchao15
 * @version 2016-11-14
 */
public class Sv {
	private CommXdr commXdr;
	private String procedureType;
	private Long procedureStartTime;
	private Long procedureEndTime;
	private String sourceNeIp;
	private String sourceNePort;
	private String destNeIp;
	private String destNePort;
	private String roamDirection;
	private String homeMcc;
	private String homeMnc;
	private String mcc;
	private String mnc;
	private String targetLac;
	private String sourceTac;
	private String sourceEci;
	private String svFlags;
	private String ulCMscIp;
	private String dlCMmeIp;
	private String ulCMscTeid;
	private String dlCMmeTeid;
	private String stnSr;
	private String targetRncId;
	private String targetCellId;
	private String arp;
	private String requestResult;
	private String result;
	private String svCause;
	private String postFailureCause;
	private String respDelay;
	private String svDelay;
	private String rangeTime;
	public Sv(String [] values) {
		this.commXdr = new CommXdr(values);
		this.procedureType = values[7];
		this.procedureStartTime = ParseUtils.parseLong(values[8]);
		this.procedureEndTime = ParseUtils.parseLong(values[9]);
		this.sourceNeIp = values[10];
		this.sourceNePort = values[11];
		this.destNeIp = values[12];
		this.destNePort = values[13];
		this.roamDirection = values[14];
		this.homeMcc = values[15];
		this.homeMnc = values[16];
		this.mcc = values[17];
		this.mnc = values[18];
		this.targetLac = values[19];
		this.sourceTac = values[20];
		this.sourceEci = values[21];
		this.svFlags = values[22];
		this.ulCMscIp = values[23];
		this.dlCMmeIp = values[24];
		this.ulCMscTeid = values[25];
		this.dlCMmeTeid = values[26];
		this.stnSr = values[27];
		this.targetRncId = values[28];
		this.targetCellId = values[29];
		this.arp = values[30];
		this.requestResult = values[31];
		this.result = values[32];
		this.svCause = values[33];
		this.postFailureCause = values[34];
		this.respDelay = values[35];
		this.svDelay = values[36];
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
		sb.append(sourceNeIp);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(sourceNePort);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(destNeIp);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(destNePort);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(roamDirection);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(homeMcc);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(homeMnc);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mcc);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mnc);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetLac);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(sourceTac);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(sourceEci);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(svFlags);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(ulCMscIp);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dlCMmeIp);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(ulCMscTeid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dlCMmeTeid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(stnSr);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetRncId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetCellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(arp);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(requestResult);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(result);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(svCause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(postFailureCause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(respDelay);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(svDelay);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(rangeTime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		return sb.toString();
	}
	
	
	public String getRangeTime() {
		return rangeTime;
	}

	public void setRangeTime(String rangeTime) {
		this.rangeTime = rangeTime;
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
	 * @return the sourceNeIp
	 */
	public String getSourceNeIp() {
		return sourceNeIp;
	}

	/**
	 * @param sourceNeIp the sourceNeIp to set
	 */
	public void setSourceNeIp(String sourceNeIp) {
		this.sourceNeIp = sourceNeIp;
	}

	/**
	 * @return the sourceNePort
	 */
	public String getSourceNePort() {
		return sourceNePort;
	}

	/**
	 * @param sourceNePort the sourceNePort to set
	 */
	public void setSourceNePort(String sourceNePort) {
		this.sourceNePort = sourceNePort;
	}

	/**
	 * @return the destNeIp
	 */
	public String getDestNeIp() {
		return destNeIp;
	}

	/**
	 * @param destNeIp the destNeIp to set
	 */
	public void setDestNeIp(String destNeIp) {
		this.destNeIp = destNeIp;
	}

	/**
	 * @return the destNePort
	 */
	public String getDestNePort() {
		return destNePort;
	}

	/**
	 * @param destNePort the destNePort to set
	 */
	public void setDestNePort(String destNePort) {
		this.destNePort = destNePort;
	}

	/**
	 * @return the roamDirection
	 */
	public String getRoamDirection() {
		return roamDirection;
	}

	/**
	 * @param roamDirection the roamDirection to set
	 */
	public void setRoamDirection(String roamDirection) {
		this.roamDirection = roamDirection;
	}

	/**
	 * @return the homeMcc
	 */
	public String getHomeMcc() {
		return homeMcc;
	}

	/**
	 * @param homeMcc the homeMcc to set
	 */
	public void setHomeMcc(String homeMcc) {
		this.homeMcc = homeMcc;
	}

	/**
	 * @return the homeMnc
	 */
	public String getHomeMnc() {
		return homeMnc;
	}

	/**
	 * @param homeMnc the homeMnc to set
	 */
	public void setHomeMnc(String homeMnc) {
		this.homeMnc = homeMnc;
	}

	/**
	 * @return the mcc
	 */
	public String getMcc() {
		return mcc;
	}

	/**
	 * @param mcc the mcc to set
	 */
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	/**
	 * @return the mnc
	 */
	public String getMnc() {
		return mnc;
	}

	/**
	 * @param mnc the mnc to set
	 */
	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	/**
	 * @return the targetLac
	 */
	public String getTargetLac() {
		return targetLac;
	}

	/**
	 * @param targetLac the targetLac to set
	 */
	public void setTargetLac(String targetLac) {
		this.targetLac = targetLac;
	}

	/**
	 * @return the sourceTac
	 */
	public String getSourceTac() {
		return sourceTac;
	}

	/**
	 * @param sourceTac the sourceTac to set
	 */
	public void setSourceTac(String sourceTac) {
		this.sourceTac = sourceTac;
	}

	/**
	 * @return the sourceEci
	 */
	public String getSourceEci() {
		return sourceEci;
	}

	/**
	 * @param sourceEci the sourceEci to set
	 */
	public void setSourceEci(String sourceEci) {
		this.sourceEci = sourceEci;
	}

	/**
	 * @return the svFlags
	 */
	public String getSvFlags() {
		return svFlags;
	}

	/**
	 * @param svFlags the svFlags to set
	 */
	public void setSvFlags(String svFlags) {
		this.svFlags = svFlags;
	}

	/**
	 * @return the ulCMscIp
	 */
	public String getUlCMscIp() {
		return ulCMscIp;
	}

	/**
	 * @param ulCMscIp the ulCMscIp to set
	 */
	public void setUlCMscIp(String ulCMscIp) {
		this.ulCMscIp = ulCMscIp;
	}

	/**
	 * @return the dlCMmeIp
	 */
	public String getDlCMmeIp() {
		return dlCMmeIp;
	}

	/**
	 * @param dlCMmeIp the dlCMmeIp to set
	 */
	public void setDlCMmeIp(String dlCMmeIp) {
		this.dlCMmeIp = dlCMmeIp;
	}

	/**
	 * @return the ulCMscTeid
	 */
	public String getUlCMscTeid() {
		return ulCMscTeid;
	}

	/**
	 * @param ulCMscTeid the ulCMscTeid to set
	 */
	public void setUlCMscTeid(String ulCMscTeid) {
		this.ulCMscTeid = ulCMscTeid;
	}

	/**
	 * @return the dlCMmeTeid
	 */
	public String getDlCMmeTeid() {
		return dlCMmeTeid;
	}

	/**
	 * @param dlCMmeTeid the dlCMmeTeid to set
	 */
	public void setDlCMmeTeid(String dlCMmeTeid) {
		this.dlCMmeTeid = dlCMmeTeid;
	}

	/**
	 * @return the stnSr
	 */
	public String getStnSr() {
		return stnSr;
	}

	/**
	 * @param stnSr the stnSr to set
	 */
	public void setStnSr(String stnSr) {
		this.stnSr = stnSr;
	}

	/**
	 * @return the targetRncId
	 */
	public String getTargetRncId() {
		return targetRncId;
	}

	/**
	 * @param targetRncId the targetRncId to set
	 */
	public void setTargetRncId(String targetRncId) {
		this.targetRncId = targetRncId;
	}

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
	 * @return the arp
	 */
	public String getArp() {
		return arp;
	}

	/**
	 * @param arp the arp to set
	 */
	public void setArp(String arp) {
		this.arp = arp;
	}

	/**
	 * @return the requestResult
	 */
	public String getRequestResult() {
		return requestResult;
	}

	/**
	 * @param requestResult the requestResult to set
	 */
	public void setRequestResult(String requestResult) {
		this.requestResult = requestResult;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the svCause
	 */
	public String getSvCause() {
		return svCause;
	}

	/**
	 * @param svCause the svCause to set
	 */
	public void setSvCause(String svCause) {
		this.svCause = svCause;
	}

	/**
	 * @return the postFailureCause
	 */
	public String getPostFailureCause() {
		return postFailureCause;
	}

	/**
	 * @param postFailureCause the postFailureCause to set
	 */
	public void setPostFailureCause(String postFailureCause) {
		this.postFailureCause = postFailureCause;
	}

	/**
	 * @return the respDelay
	 */
	public String getRespDelay() {
		return respDelay;
	}

	/**
	 * @param respDelay the respDelay to set
	 */
	public void setRespDelay(String respDelay) {
		this.respDelay = respDelay;
	}

	/**
	 * @return the svDelay
	 */
	public String getSvDelay() {
		return svDelay;
	}

	/**
	 * @param svDelay the svDelay to set
	 */
	public void setSvDelay(String svDelay) {
		this.svDelay = svDelay;
	}
	
}
