package cn.com.dtmobile.hadoop.biz.ueFillBack.model;

import cn.com.dtmobile.hadoop.util.StringUtils;

public class UeMrModel {
	private String Length;
	private String City;
	private String Interface;
	private String XDRID;
	private String RAT;
	private String IMSI;
	private String IMEI;
	private String MSISDN;
	private String MMEGroupID;
	private String MMECode;
	private String MMEUES1APID;
	private String eNBID;
	private String CellID;
	private String Time;
	private String MRtype;
	private String PHR;
	private String eNBReceivedPower;
	private String ULSINR;
	private String TA;
	private String AoA;
	private String ServingFreq;
	private String ServingRSRP;
	private String ServingRSRQ;
	private int NeighborCellNumber;
	private String[] Neighbor;

	public UeMrModel(String[] values) {
		this.Length = values[0];
		this.City = values[1];
		this.Interface = values[2];
		this.XDRID = values[3];
		this.RAT = values[4];
		this.IMSI = values[5];
		this.IMEI = values[6];
		this.MSISDN = values[7];
		this.MMEGroupID = values[8];
		this.MMECode = values[9];
		this.MMEUES1APID = values[10];
		this.eNBID = values[11];
		this.CellID = values[12];
		this.Time = values[13];
		this.MRtype = values[14];
		this.PHR = values[15];
		this.eNBReceivedPower = values[16];
		this.ULSINR = values[17];
		this.TA = values[18];
		this.AoA = values[19];
		this.ServingFreq = values[20];
		this.ServingRSRP = values[21];
		this.ServingRSRQ = values[22];
		this.NeighborCellNumber = values[23].equals("") ? 0 : Integer
				.parseInt(values[23]);
		Neighbor = new String[this.NeighborCellNumber];
		int start = 24;
		int step = 4;
		for (int i = 0; i < this.NeighborCellNumber; i++) {
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < step; j++) {
				sb.append(values[j + start]);
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
			}
			start = start + step;
			sb.deleteCharAt(sb.length() - 1);
			Neighbor[i] = sb.toString();
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(Length);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(City);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(Interface);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(XDRID);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(RAT);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(IMSI);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(IMEI);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(MSISDN);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(MMEGroupID);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(MMECode);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(MMEUES1APID);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(eNBID);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(CellID);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(Time);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(MRtype);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(PHR);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(eNBReceivedPower);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(ULSINR);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(TA);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(AoA);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(ServingFreq);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(ServingRSRP);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(ServingRSRQ);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(NeighborCellNumber);
		return sb.toString();
	}

	public String getLength() {
		return Length;
	}

	public void setLength(String length) {
		Length = length;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getInterface() {
		return Interface;
	}

	public void setInterface(String interface1) {
		Interface = interface1;
	}

	public String getXDRID() {
		return XDRID;
	}

	public void setXDRID(String xDRID) {
		XDRID = xDRID;
	}

	public String getRAT() {
		return RAT;
	}

	public void setRAT(String rAT) {
		RAT = rAT;
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getMSISDN() {
		return MSISDN;
	}

	public void setMSISDN(String mSISDN) {
		MSISDN = mSISDN;
	}

	public String getMMEGroupID() {
		return MMEGroupID;
	}

	public void setMMEGroupID(String mMEGroupID) {
		MMEGroupID = mMEGroupID;
	}

	public String getMMECode() {
		return MMECode;
	}

	public void setMMECode(String mMECode) {
		MMECode = mMECode;
	}

	public String getMMEUES1APID() {
		return MMEUES1APID;
	}

	public void setMMEUES1APID(String mMEUES1APID) {
		MMEUES1APID = mMEUES1APID;
	}

	public String geteNBID() {
		return eNBID;
	}

	public void seteNBID(String eNBID) {
		this.eNBID = eNBID;
	}

	public String getCellID() {
		return CellID;
	}

	public void setCellID(String cellID) {
		CellID = cellID;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getMRtype() {
		return MRtype;
	}

	public void setMRtype(String mRtype) {
		MRtype = mRtype;
	}

	public String getPHR() {
		return PHR;
	}

	public void setPHR(String pHR) {
		PHR = pHR;
	}

	public String geteNBReceivedPower() {
		return eNBReceivedPower;
	}

	public void seteNBReceivedPower(String eNBReceivedPower) {
		this.eNBReceivedPower = eNBReceivedPower;
	}

	public String getULSINR() {
		return ULSINR;
	}

	public void setULSINR(String uLSINR) {
		ULSINR = uLSINR;
	}

	public String getTA() {
		return TA;
	}

	public void setTA(String tA) {
		TA = tA;
	}

	public String getAoA() {
		return AoA;
	}

	public void setAoA(String aoA) {
		AoA = aoA;
	}

	public String getServingFreq() {
		return ServingFreq;
	}

	public void setServingFreq(String servingFreq) {
		ServingFreq = servingFreq;
	}

	public String getServingRSRP() {
		return ServingRSRP;
	}

	public void setServingRSRP(String servingRSRP) {
		ServingRSRP = servingRSRP;
	}

	public String getServingRSRQ() {
		return ServingRSRQ;
	}

	public void setServingRSRQ(String servingRSRQ) {
		ServingRSRQ = servingRSRQ;
	}

	public int getNeighborCellNumber() {
		return NeighborCellNumber;
	}

	public void setNeighborCellNumber(int neighborCellNumber) {
		NeighborCellNumber = neighborCellNumber;
	}

	public String[] getNeighbor() {
		return Neighbor;
	}

	public void setNeighbor(String[] neighbor) {
		Neighbor = neighbor;
	}
}