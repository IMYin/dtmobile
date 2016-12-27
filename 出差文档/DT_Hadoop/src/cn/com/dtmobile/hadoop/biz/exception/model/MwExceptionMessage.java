package cn.com.dtmobile.hadoop.biz.exception.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.sun.swing.internal.plaf.metal.resources.metal;

import cn.com.dtmobile.hadoop.biz.exception.constants.TbNetRelConstants;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.MwNew;
import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class MwExceptionMessage {
	private MwNew mwNew;
	private String exceptionArea;
	private String exceptionType;
	private String exceptionTag;
	private String exceptionInterface;
	private String failureCause;
	private String exceptionkey;

	// public MwExceptionMessage(MwNew MwNew, String failureCause, String
	// exceptionInterface,
	// Map<String, String> exceptionMap) {
	// this.MwNew = MwNew;
	// String[] words = exceptionMap.get(exceptionInterface +
	// MwNew.getResponse_code()).split(StringUtils.DELIMITER_INNER_ITEM1);
	// this.failureCause = failureCause;
	// this.exceptionInterface = exceptionInterface;
	// this.exceptionType = words[0];
	// this.exceptionTag = words[1];
	// this.exceptionArea = TbNetRelConstants.TB_NET_REL_MAP.get(exceptionTag)
	// .split(StringUtils.DELIMITER_INNER_ITEM)[0];
	// }
	public MwExceptionMessage(MwNew MwNew, String failureCause,
			String exceptionInterface, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		this.mwNew = MwNew;
		this.exceptionType = exceptionMap.get(exceptionInterface
				+ MwNew.getMwXdr().getResponse_code());
		this.exceptionInterface = exceptionInterface;
		this.failureCause = failureCause;
		if (TbNetRelConstants.TB_NET_REL_MAP.get(exceptionType) == null) {
			this.exceptionArea = "";
		} else {
			this.exceptionArea = TbNetRelConstants.TB_NET_REL_MAP.get(
					exceptionType).split(StringUtils.DELIMITER_INNER_ITEM)[0];
		}
		if (exceptionType == null) {
			this.exceptionkey = "";
		} else if (TbNetRelConstants.UE.equals(exceptionType)) {
			this.exceptionkey = MwNew.getMwXdr().getImsi();
		} else if (TbNetRelConstants.CELL.equals(exceptionType)) {
			this.exceptionkey = String.valueOf(MwNew.getMwXdr().getSource_eci());
		} else if (TbNetRelConstants.IMS.equals(exceptionType)) {
			this.exceptionkey = MwNew.getMwXdr().getSource_ne_ip();
		} else if (TbNetRelConstants.MME.equals(exceptionType)) {
			this.exceptionkey = lteCellMap.get(MwNew.getMwXdr().getSource_eci());
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(mwNew.getMwXdr().getProcedurestarttime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mwNew.getMwXdr().getImsi());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mwNew.getMwXdr().getProceduretype());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mwNew.getEtype());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		if (mwNew.getMwXdr().getCall_side() == null || "".equals(mwNew.getMwXdr().getCall_side())) {
			sb.append("");
		} else if (ParseUtils.parseInteger(mwNew.getMwXdr().getCall_side()) == 1) {
			sb.append(mwNew.getMwXdr().getDest_eci());
		} else if (ParseUtils.parseInteger(mwNew.getMwXdr().getCall_side()) == 0) {
			sb.append(mwNew.getMwXdr().getSource_eci());
		} else {
			sb.append("");
		}
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(failureCause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionType);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionArea);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionkey);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mwNew.getMwXdr().getInterfaces());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(exceptionInterface);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append(sdf.format(new Date(mwNew.getMwXdr().getProcedurestarttime())));
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mwNew.getElong());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mwNew.getElat());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mwNew.getEupordown());
		return sb.toString();
	}

	public MwNew getMwXdr() {
		return mwNew;
	}

	public void setMwXdr(MwNew MwNew) {
		this.mwNew = MwNew;
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
