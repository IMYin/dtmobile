package cn.com.dtmobile.hadoop.biz.nssp.uemr.model;

import cn.com.dtmobile.hadoop.biz.nssp.uemr.util.UeMrKey;

public class UeMrModel {
	private UeMrKey ueMrKEY;
	private String value;

	public UeMrKey getUeMrKEY() {
		return ueMrKEY;
	}

	public void setUeMrKEY(UeMrKey ueMrKEY) {
		this.ueMrKEY = ueMrKEY;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
