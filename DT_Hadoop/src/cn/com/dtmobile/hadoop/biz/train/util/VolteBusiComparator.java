package cn.com.dtmobile.hadoop.biz.train.util;

import org.apache.hadoop.io.WritableComparator;

public class VolteBusiComparator extends WritableComparator {
	public VolteBusiComparator() {
		super(VolteBusiNewKey.class);
	}

	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
		return compareBytes(b1, s1, l1, b2, s2, l2);
	}
}
