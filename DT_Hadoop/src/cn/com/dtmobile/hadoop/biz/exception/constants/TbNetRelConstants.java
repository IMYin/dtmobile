package cn.com.dtmobile.hadoop.biz.exception.constants;

import java.util.HashMap;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class TbNetRelConstants {
	public static final HashMap<String, String> TB_NET_REL_MAP;
	// 问题网元类型
	public static final String UE = "UE";
	public static final String CELL = "CELL";
	public static final String SOURCE_CELL = "SOURCE_CELL";
	public static final String TAGGET_CELL = "TAGGET_CELL";
	public static final String NEIGHBOR_REL = "NEIGHBOR_REL";
	public static final String ENB = "ENB";
	public static final String MME = "MME";
	public static final String MME_SIM = "MME_SIM";
	public static final String MME_SV = "MME_SV";
	public static final String MSC = "MSC";
	public static final String IMS = "IMS";
	// 问题域
	public static final String UE_TYPE = "UE";
	public static final String EUTRAN_TYPE = "EUTRAN";
	public static final String EPC_TYPE = "EPC";
	public static final String IMS_TYPE = "IMS";
	// 问题网元标识
	public static final String IMS_TAG = "IMS";
	public static final String CELLID_TAG = "CELLID";
	public static final String SOUR_CELLID_TAG = "SOUR_CELLID";
	public static final String TAR_CELLID_TAG = "TAR_CELLID";
	public static final String SOUR_TAR_CELLID_TAG = "SOUR_TAR_CELLID";
	public static final String ENBID = "eNBID";
	public static final String MMEGroupID_MME = "MMEGroupID_MME";
	public static final String MME_IP = "MME_IP";
	public static final String MSC_IP = "MSC_IP";
	public static final String PCSCF_IP = "PCSCF_IP";

	static {
		TB_NET_REL_MAP = new HashMap<String,String>();
		TB_NET_REL_MAP.put(UE, UE_TYPE + StringUtils.DELIMITER_COMMA + IMS_TAG);
		TB_NET_REL_MAP.put(CELL, EUTRAN_TYPE + StringUtils.DELIMITER_COMMA + CELLID_TAG);
		TB_NET_REL_MAP.put(SOURCE_CELL, EUTRAN_TYPE + StringUtils.DELIMITER_COMMA + SOUR_CELLID_TAG);
		TB_NET_REL_MAP.put(TAGGET_CELL, EUTRAN_TYPE + StringUtils.DELIMITER_COMMA + TAR_CELLID_TAG);
		TB_NET_REL_MAP.put(NEIGHBOR_REL, EUTRAN_TYPE + StringUtils.DELIMITER_COMMA + SOUR_TAR_CELLID_TAG);
		TB_NET_REL_MAP.put(ENB, EUTRAN_TYPE + StringUtils.DELIMITER_COMMA + ENBID);
		TB_NET_REL_MAP.put(MME, EPC_TYPE + StringUtils.DELIMITER_COMMA + MMEGroupID_MME);
		TB_NET_REL_MAP.put(MME_SV, EPC_TYPE + StringUtils.DELIMITER_COMMA + MME_IP);
		TB_NET_REL_MAP.put(MSC, EPC_TYPE + StringUtils.DELIMITER_COMMA + MSC_IP);
		TB_NET_REL_MAP.put(IMS, IMS_TYPE + StringUtils.DELIMITER_COMMA + PCSCF_IP);
	}

	public static void main(String[] args) {
		System.out.println(TbNetRelConstants.TB_NET_REL_MAP.get(TbNetRelConstants.CELL));
	}
}
