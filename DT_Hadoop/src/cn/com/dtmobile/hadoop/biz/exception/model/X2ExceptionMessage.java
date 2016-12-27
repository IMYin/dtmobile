package cn.com.dtmobile.hadoop.biz.exception.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.TbNetRelConstants;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.X2XdrNew;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class X2ExceptionMessage {
	private X2XdrNew x2;
	private String exceptionArea;
	private String exceptionType;
	private String exceptionTag;
	private String exceptionInterface;
	private String failureCause;

	public X2ExceptionMessage(X2XdrNew x2, String failureCause,
			String exceptionInterface, Map<String, String> exceptionMap) {
		this.exceptionType = exceptionMap.get(exceptionInterface
				+ x2.getX2Xdr().getFailureCause());
		this.x2 = x2;
		this.failureCause = failureCause;
		this.exceptionInterface = exceptionInterface;
		if (TbNetRelConstants.TB_NET_REL_MAP.get(exceptionType) == null) {
			this.exceptionArea = "";
		} else {
			this.exceptionArea = TbNetRelConstants.TB_NET_REL_MAP.get(
					exceptionType).split(StringUtils.DELIMITER_COMMA)[0];
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(x2.getX2Xdr().getProcedureStartTime());
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(x2.getX2Xdr().getCommXdr().getImsi());
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(x2.getX2Xdr().getProcedureType());
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(x2.getEtype());
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(x2.getX2Xdr().getTargetCellId());
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(failureCause);
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(exceptionType);
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(exceptionArea);
		sb.append(StringUtils.DELIMITER_COMMA);
		if (exceptionType == null) {
			sb.append("");
		} else if (exceptionType.equals("CELL")
				|| exceptionType.equals("目标CELL")) {
			sb.append(x2.getX2Xdr().getTargetCellId());
		} else if (exceptionType.equals("MME")) {
			sb.append(x2.getX2Xdr().getMmeGroupId() + "_" + x2.getX2Xdr().getMmeCode());
		} else if (exceptionType.equals("源CELL")) {
			sb.append(x2.getX2Xdr().getTargetCellId());
		}
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(x2.getX2Xdr().getCommXdr().getInterfaces());
		sb.append(StringUtils.DELIMITER_COMMA);
		sb.append(exceptionInterface);
		sb.append(StringUtils.DELIMITER_COMMA);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append(sdf.format(new Date(x2.getX2Xdr().getProcedureStartTime())));
//		sb.append(StringUtils.DELIMITER_VERTICAL);
//		sb.append(x2.getElong());
//		sb.append(StringUtils.DELIMITER_VERTICAL);
//		sb.append(x2.getElat());
//		sb.append(StringUtils.DELIMITER_VERTICAL);
//		sb.append(x2.getEupordown());
		return sb.toString();
	}

	public X2XdrNew getX2() {
		return x2;
	}

	public void setX2(X2XdrNew x2) {
		this.x2 = x2;
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
