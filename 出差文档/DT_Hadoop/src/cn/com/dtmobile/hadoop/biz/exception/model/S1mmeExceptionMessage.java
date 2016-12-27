package cn.com.dtmobile.hadoop.biz.exception.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.TbNetRelConstants;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class S1mmeExceptionMessage {
	private S1mmeXdrNew s1;
	private String exceptionArea;
	private String exceptionType;
	private String exceptionTag;
	private String exceptionInterface;
	
	public S1mmeExceptionMessage(S1mmeXdrNew s1,String failureCause, String exceptionInterface,
			Map<String, String> exceptionMap){
		this.exceptionType = exceptionMap.get(exceptionInterface + s1.getS1mmeXdr().getFailureCause());
		this.s1 = s1;
		this.failureCause = failureCause;
		this.exceptionInterface = exceptionInterface;
		if (TbNetRelConstants.TB_NET_REL_MAP.get(exceptionType) == null) {
			this.exceptionArea = "";
		}else {
			this.exceptionArea = TbNetRelConstants.TB_NET_REL_MAP.get(exceptionType)
			.split(StringUtils.DELIMITER_INNER_ITEM)[0];
		}
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(s1.getS1mmeXdr().getProcedureStartTime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getS1mmeXdr().getCommXdr().getImsi());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getS1mmeXdr().getProcedureType());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getEtype());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getS1mmeXdr().getCellId());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(failureCause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionType);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionArea);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		if (exceptionType == null) {
			sb.append("");
		}else if (exceptionType.equals("UE")) {
			sb.append(s1.getS1mmeXdr().getCommXdr().getImsi());
		}else if (exceptionType.equals("CELL") || exceptionType.equals("目标CELL")){
			sb.append(s1.getS1mmeXdr().getCellId());
		}else if (exceptionType.equals("MME")) {
			sb.append(s1.getS1mmeXdr().getMmeGroupId()+"_"+s1.getS1mmeXdr().getMmeCode());
		}
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getS1mmeXdr().getCommXdr().getInterfaces());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionInterface);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append(sdf.format(new Date(s1.getS1mmeXdr().getProcedureStartTime())));
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getElong());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getElat());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(s1.getEupordown());
		return sb.toString();
	}
	public S1mmeXdrNew getS1() {
		return s1;
	}
	public void setS1(S1mmeXdrNew s1) {
		this.s1 = s1;
	}
	public String getExceptionArea() {
		return exceptionArea;
	}
	public void setExceptionArea(String exceptionArea) {
		this.exceptionArea = exceptionArea;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionTag() {
		return exceptionTag;
	}
	public void setExceptionTag(String exceptionTag) {
		this.exceptionTag = exceptionTag;
	}
	public String getExceptionInterface() {
		return exceptionInterface;
	}
	public void setExceptionInterface(String exceptionInterface) {
		this.exceptionInterface = exceptionInterface;
	}
	public String getFailureCause() {
		return failureCause;
	}
	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}
	private String failureCause;
}
