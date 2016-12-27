package cn.com.dtmobile.hadoop.biz.exception.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.TbNetRelConstants;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class UuExceptionMessage {
	private UuXdrNew uuXdrNew;
	private String exceptionArea;
	private String exceptionType;
	private String exceptionTag;
	private String exceptionInterface;
	private String failureCause;

	public UuExceptionMessage(UuXdrNew uuXdrNew, String failureCause,
			String exceptionInterface, Map<String, String> exceptionMap) {
		this.exceptionType = exceptionMap.get(exceptionInterface
				+ uuXdrNew.getUuXdr().getProcedureStatus());
		this.uuXdrNew = uuXdrNew;
		this.failureCause = failureCause;
		this.exceptionInterface = exceptionInterface;
		if (TbNetRelConstants.TB_NET_REL_MAP.get(exceptionType) == null) {
			this.exceptionArea = "";
		} else {
			this.exceptionArea = TbNetRelConstants.TB_NET_REL_MAP.get(
					exceptionType).split(StringUtils.DELIMITER_INNER_ITEM)[0];
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(uuXdrNew.getUuXdr().getProcedureStartTime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getUuXdr().getCommXdr().getImsi());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getUuXdr().getProcedureType());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getEtype());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getUuXdr().getCellId());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(failureCause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionType);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionArea);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		if (exceptionType == null) {
			sb.append("");
		} else if (exceptionType.equals("UE")) {
			sb.append(uuXdrNew.getUuXdr().getCommXdr().getImsi());
		} else if (exceptionType.equals("CELL")) {
			sb.append(uuXdrNew.getUuXdr().getCellId());
		}
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getUuXdr().getCommXdr().getInterfaces());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionInterface);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append(sdf.format(new Date(uuXdrNew.getUuXdr().getProcedureStartTime())));
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getElong());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getElat());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(uuXdrNew.getEupordown());
		return sb.toString();
	}

	public UuXdrNew getUuXdr() {
		return uuXdrNew;
	}

	public void setUuXdr(UuXdrNew uuXdrNew) {
		this.uuXdrNew = uuXdrNew;
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
}
