package cn.com.dtmobile.hadoop.biz.train.model.trainsame;

import java.util.Objects;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class TrainSame {
	private String imsi;
	private String xdrid;
	private String cellId;
	private String targetCellId;
	private String procedureType;
	private Long procedureStartTime;
	private int hour; //u3 u4 没有procedureStartTime 有小时
	private String rangetime;
	private int upordown;
	private String udate;
	private int sn;
	private String groupName;//为了让每个imsi知道自己是属于哪个组的
	private String groupMapping;
	
	public TrainSame(String[] values){
		this.imsi = values[0];
//		this.xdrid = values[3];
		this.cellId = values[1];
		this.targetCellId = values[2];
		this.upordown = ParseUtils.parseInteger(values[12]);
		this.procedureStartTime = Long.valueOf(values[8]);
		this.rangetime = values[5];
		this.groupName = null;

	}
	
	public TrainSame(String[] values,String s){
		this.imsi = values[0];
		this.xdrid = values[1];
		this.cellId = values[2];
		this.targetCellId = values[3];
		this.upordown = ParseUtils.parseInteger(values[4]);
		this.procedureStartTime = Long.valueOf(values[5]);
		this.rangetime = values[6];
//		this.groupName = values[0];
		this.sn = ParseUtils.parseInteger(values[7]);
		this.groupName = values[8];
//		this.sn = ParseUtils.parseInteger(values[8]);
	}
	
	public String getImsi() {
		return imsi;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getXdrid() {
		return xdrid;
	}

	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getTargetCellId() {
		return targetCellId;
	}

	public void setTargetCellId(String targetCellId) {
		this.targetCellId = targetCellId;
	}

	public String getRangetime() {
		return rangetime;
	}

	public void setRangetime(String rangetime) {
		this.rangetime = rangetime;
	}

	public Long getProcedureStartTime() {
		return procedureStartTime;
	}

	public void setProcedureStartTime(Long procedureStartTime) {
		this.procedureStartTime = procedureStartTime;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public int getUpordown() {
		return upordown;
	}

	public void setUpordown(int upordown) {
		this.upordown = upordown;
	}

	public String getU() {
		return udate;
	}

	public void setU(String u) {
		this.udate = u;
	}

	public String getUdate() {
		return udate;
	}

	public void setUdate(String udate) {
		this.udate = udate;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(xdrid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(cellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetCellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(upordown);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureStartTime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(rangetime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(sn);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(groupName);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(groupMapping);
		return sb.toString();
	}

	public String getGroupMapping() {
		return groupMapping;
	}

	public void setGroupMapping(String groupMapping) {
		this.groupMapping = groupMapping;
	}

	@Override
	public int hashCode() {
		return Objects.hash(imsi,groupName);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TrainSame)){
			return false;
		}
		TrainSame trainSame = (TrainSame) obj;
		if(trainSame.getImsi().equals(imsi) && trainSame.getGroupName().equals(groupName)){
			if(trainSame.getProcedureStartTime() != procedureStartTime  || trainSame.getProcedureStartTime() == procedureStartTime){
				return true;
			}
		}
		return false;
	}
}
