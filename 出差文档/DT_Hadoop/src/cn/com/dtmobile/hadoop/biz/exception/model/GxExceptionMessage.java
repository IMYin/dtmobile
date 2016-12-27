package cn.com.dtmobile.hadoop.biz.exception.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.TbNetRelConstants;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.GxRxNew;
import cn.com.dtmobile.hadoop.util.StringUtils;

/**
 * GX\RX 接口
 * @author zhangchao15
 * @version 2016-11-14
 */
public class GxExceptionMessage {
	private GxRxNew gxRx;
	private String eventName;
	private String exceptionArea;
	private String exceptionkey;
	private String exceptionTag;
	private String exceptionInterface;
	private String failureCause;
	private String rangeTime;
	private String exceptionType;

	

	public GxExceptionMessage(GxRxNew GxRxNew, String failureCause, String exceptionInterface,
			Map<String, String> exceptionMap) {
		this.exceptionType = exceptionMap.get(exceptionInterface + GxRxNew.getGxXdr().getAbortCause());
		this.gxRx = GxRxNew;
		this.failureCause = failureCause;
		this.exceptionInterface = exceptionInterface;
		if (TbNetRelConstants.TB_NET_REL_MAP.get(exceptionType) == null) {
			this.exceptionArea = "";
		}else {
			this.exceptionArea = TbNetRelConstants.TB_NET_REL_MAP.get(exceptionType)
			.split(StringUtils.DELIMITER_INNER_ITEM)[0];
		}
		if (exceptionType == null) {
			this.exceptionkey = "";
		}else if(TbNetRelConstants.UE.equals(this.exceptionType)){
			this.exceptionkey = GxRxNew.getGxXdr().getImsi();
		}else if(TbNetRelConstants.CELL.equals(this.exceptionType)){
			this.exceptionkey = GxRxNew.getGxXdr().getCellId();
		}else if(TbNetRelConstants.MME.equals(this.exceptionType)){
			this.exceptionkey = GxRxNew.getGxXdr().getMmeGroupId()+"_"+GxRxNew.getGxXdr().getMmeCode();
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(eventName);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getGxXdr().getProcedureStartTime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getGxXdr().getImsi());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getGxXdr().getProcedureType());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getEtype());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getGxXdr().getAbortCause());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(this.getExceptionArea());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionkey);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getGxXdr().getInterfaces());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(this.exceptionInterface);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append(sdf.format(new Date(gxRx.getGxXdr().getProcedureStartTime())));
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getElong());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getElat());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gxRx.getEupordown());
		return sb.toString();
	}
	
	/**
	 * @return the GxRxNew
	 */
	public GxRxNew getGxXdr() {
		return gxRx;
	}

	/**
	 * @param GxRxNew the GxRxNew to set
	 */
	public void setGxXdr(GxRxNew GxRxNew) {
		this.gxRx = GxRxNew;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		if("".equals(eventName) || eventName == null){
			this.eventName = "VOLTE掉话";
		}else{
			this.eventName = eventName;
		}
		
	}

	/**
	 * @return the exceptionArea
	 */ 
	public String getExceptionArea() {
		return exceptionArea;
	}

	/**
	 * @param exceptionArea the exceptionArea to set
	 */
	public void setExceptionArea(String exceptionArea) {
		this.exceptionArea = exceptionArea;
	}

	/**
	 * @return the exceptionTag
	 */
	public String getExceptionTag() {
		return exceptionTag;
	}

	/**
	 * @param exceptionTag the exceptionTag to set
	 */
	public void setExceptionTag(String exceptionTag) {
		this.exceptionTag = exceptionTag;
	}

	/**
	 * @return the exceptionInterface
	 */
	public String getExceptionInterface() {
		return exceptionInterface;
	}

	/**
	 * @param exceptionInterface the exceptionInterface to set
	 */
	public void setExceptionInterface(String exceptionInterface) {
		this.exceptionInterface = exceptionInterface;
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

	/**
	 * @return the exceptionkey
	 */
	public String getExceptionkey() {
		return exceptionkey;
	}

	/**
	 * @param exceptionkey the exceptionkey to set
	 */
	public void setExceptionkey(String exceptionkey) {
		this.exceptionkey = exceptionkey;
	}
	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	
}
