package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class SgsXdr {
	 private CommXdr commXdr;
	 private Long  procedureType;     
	 private Long  procedureStartTime; 
	 private Long  procedureEndTime;
	 private Long  procedureStatus;    
	 private String  sgsCause;           
	 private String  rejectCause;        
	 private String  cpCause;            
	 private String  rpCause;            
	 private String  userIpv4;        
	 private String  userIpv6 ;       
	 private String  mmeIpAdd ;       
	 private String  mscserverIpAdd;  
	 private String  mmePort;            
	 private String  mscServerPort;      
	 private String  serviceIndicator;   
	 private String  mmeName;         
	 private String  tmsi;               
	 private String  newLac;             
	 private String  oldLac;             
	 private String  tac;                
	 private String  cellId;             
	 private String  callingId;       
	 private String  vlrnameLength;      
	 private String  vlrName;         
	 private String  rangeTime;
	 
	 public SgsXdr(String [] values) {
		 commXdr = new CommXdr(values);
		 this.procedureType = ParseUtils.parseLong(values[8]);     
		 this.procedureStartTime = ParseUtils.parseLong(values[9]); 
		 this.procedureEndTime = ParseUtils.parseLong(values[10]);
		 this.procedureStatus = ParseUtils.parseLong(values[11]);    
		 this.sgsCause = values[12];           
		 this.rejectCause = values[13];        
		 this.cpCause = values[14];            
		 this.rpCause = values[15];            
		 this.userIpv4 = values[16];        
		 this.userIpv6  = values[17];       
		 this.mmeIpAdd  = values[18];       
		 this.mscserverIpAdd = values[19];  
		 this.mmePort = values[20];            
		 this.mscServerPort = values[21];      
		 this.serviceIndicator = values[22];   
		 this.mmeName = values[23];         
		 this.tmsi = values[24];               
		 this.newLac = values[25];             
		 this.oldLac = values[26];             
		 this.tac = values[27];                
		 this.cellId = values[28];             
		 this.callingId = values[29];       
		 this.vlrnameLength = values[30];      
		 this.vlrName = values[31];         
		 this.rangeTime = values[32];
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
		 sb.append(sgsCause);          
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(rejectCause);       
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(cpCause);           
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(rpCause);           
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(userIpv4);       
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(userIpv6 );      
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(mmeIpAdd );      
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(mscserverIpAdd); 
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(mmePort);           
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(mscServerPort);     
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(serviceIndicator);  
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(mmeName);        
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(tmsi);              
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(newLac);            
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(oldLac);            
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(tac);               
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(cellId);            
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(callingId);      
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(vlrnameLength);     
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
		 sb.append(vlrName);        
		 sb.append(StringUtils.DELIMITER_INNER_ITEM);
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
	public Long getProcedureType() {
		return procedureType;
	}

	/**
	 * @param procedureType the procedureType to set
	 */
	public void setProcedureType(Long procedureType) {
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
	public Long getProcedureStatus() {
		return procedureStatus;
	}

	/**
	 * @param procedureStatus the procedureStatus to set
	 */
	public void setProcedureStatus(Long procedureStatus) {
		this.procedureStatus = procedureStatus;
	}

	/**
	 * @return the sgsCause
	 */
	public String getSgsCause() {
		return sgsCause;
	}

	/**
	 * @param sgsCause the sgsCause to set
	 */
	public void setSgsCause(String sgsCause) {
		this.sgsCause = sgsCause;
	}

	/**
	 * @return the rejectCause
	 */
	public String getRejectCause() {
		return rejectCause;
	}

	/**
	 * @param rejectCause the rejectCause to set
	 */
	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}

	/**
	 * @return the cpCause
	 */
	public String getCpCause() {
		return cpCause;
	}

	/**
	 * @param cpCause the cpCause to set
	 */
	public void setCpCause(String cpCause) {
		this.cpCause = cpCause;
	}

	/**
	 * @return the rpCause
	 */
	public String getRpCause() {
		return rpCause;
	}

	/**
	 * @param rpCause the rpCause to set
	 */
	public void setRpCause(String rpCause) {
		this.rpCause = rpCause;
	}

	/**
	 * @return the userIpv4
	 */
	public String getUserIpv4() {
		return userIpv4;
	}

	/**
	 * @param userIpv4 the userIpv4 to set
	 */
	public void setUserIpv4(String userIpv4) {
		this.userIpv4 = userIpv4;
	}

	/**
	 * @return the userIpv6
	 */
	public String getUserIpv6() {
		return userIpv6;
	}

	/**
	 * @param userIpv6 the userIpv6 to set
	 */
	public void setUserIpv6(String userIpv6) {
		this.userIpv6 = userIpv6;
	}

	/**
	 * @return the mmeIpAdd
	 */
	public String getMmeIpAdd() {
		return mmeIpAdd;
	}

	/**
	 * @param mmeIpAdd the mmeIpAdd to set
	 */
	public void setMmeIpAdd(String mmeIpAdd) {
		this.mmeIpAdd = mmeIpAdd;
	}

	/**
	 * @return the mscserverIpAdd
	 */
	public String getMscserverIpAdd() {
		return mscserverIpAdd;
	}

	/**
	 * @param mscserverIpAdd the mscserverIpAdd to set
	 */
	public void setMscserverIpAdd(String mscserverIpAdd) {
		this.mscserverIpAdd = mscserverIpAdd;
	}

	/**
	 * @return the mmePort
	 */
	public String getMmePort() {
		return mmePort;
	}

	/**
	 * @param mmePort the mmePort to set
	 */
	public void setMmePort(String mmePort) {
		this.mmePort = mmePort;
	}

	/**
	 * @return the mscServerPort
	 */
	public String getMscServerPort() {
		return mscServerPort;
	}

	/**
	 * @param mscServerPort the mscServerPort to set
	 */
	public void setMscServerPort(String mscServerPort) {
		this.mscServerPort = mscServerPort;
	}

	/**
	 * @return the serviceIndicator
	 */
	public String getServiceIndicator() {
		return serviceIndicator;
	}

	/**
	 * @param serviceIndicator the serviceIndicator to set
	 */
	public void setServiceIndicator(String serviceIndicator) {
		this.serviceIndicator = serviceIndicator;
	}

	/**
	 * @return the mmeName
	 */
	public String getMmeName() {
		return mmeName;
	}

	/**
	 * @param mmeName the mmeName to set
	 */
	public void setMmeName(String mmeName) {
		this.mmeName = mmeName;
	}

	/**
	 * @return the tmsi
	 */
	public String getTmsi() {
		return tmsi;
	}

	/**
	 * @param tmsi the tmsi to set
	 */
	public void setTmsi(String tmsi) {
		this.tmsi = tmsi;
	}

	/**
	 * @return the newLac
	 */
	public String getNewLac() {
		return newLac;
	}

	/**
	 * @param newLac the newLac to set
	 */
	public void setNewLac(String newLac) {
		this.newLac = newLac;
	}

	/**
	 * @return the oldLac
	 */
	public String getOldLac() {
		return oldLac;
	}

	/**
	 * @param oldLac the oldLac to set
	 */
	public void setOldLac(String oldLac) {
		this.oldLac = oldLac;
	}

	/**
	 * @return the tac
	 */
	public String getTac() {
		return tac;
	}

	/**
	 * @param tac the tac to set
	 */
	public void setTac(String tac) {
		this.tac = tac;
	}

	/**
	 * @return the cellId
	 */
	public String getCellId() {
		return cellId;
	}

	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	/**
	 * @return the callingId
	 */
	public String getCallingId() {
		return callingId;
	}

	/**
	 * @param callingId the callingId to set
	 */
	public void setCallingId(String callingId) {
		this.callingId = callingId;
	}

	/**
	 * @return the vlrnameLength
	 */
	public String getVlrnameLength() {
		return vlrnameLength;
	}

	/**
	 * @param vlrnameLength the vlrnameLength to set
	 */
	public void setVlrnameLength(String vlrnameLength) {
		this.vlrnameLength = vlrnameLength;
	}

	/**
	 * @return the vlrName
	 */
	public String getVlrName() {
		return vlrName;
	}

	/**
	 * @param vlrName the vlrName to set
	 */
	public void setVlrName(String vlrName) {
		this.vlrName = vlrName;
	}

	/**
	 * @return the rangeTime
	 */
	public String getRangeTime() {
		return rangeTime;
	}

	/**
	 * @param rangeTime the rangeTime to set
	 */
	public void setRangeTime(String rangeTime) {
		this.rangeTime = rangeTime;
	}
	 
}
