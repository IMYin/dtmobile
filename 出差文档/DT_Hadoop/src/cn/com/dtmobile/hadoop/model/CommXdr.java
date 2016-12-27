package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class CommXdr {
	private Integer length;
	private String city;
	private Integer interfaces;
	private String xdrid;
	private Integer rat;
	private String imsi;
	private String imei;
	private String msisdn;

	public CommXdr(String[] values) {
		this.length = ParseUtils.parseInteger(values[0]);
		this.city = values[1];
		this.interfaces = ParseUtils.parseInteger(values[2]);
		this.xdrid = values[3];
		this.rat = ParseUtils.parseInteger(values[4]);
		this.imsi = values[5];
		this.imei = values[6];
		this.msisdn = values[7];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(length);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(city);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(interfaces);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(xdrid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(rat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(imei);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(msisdn);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		return sb.toString();
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Integer interfaces) {
		this.interfaces = interfaces;
	}

	public String getXdrid() {
		return xdrid;
	}

	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}

	public Integer getRat() {
		return rat;
	}

	public void setRat(Integer rat) {
		this.rat = rat;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

}
