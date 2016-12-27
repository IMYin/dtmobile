package cn.com.dtmobile.hadoop.biz.train.util;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparator;

public class VolteBusiNewGroupComparator implements RawComparator<VolteBusiNewKey> {
	@Override
	public int compare(VolteBusiNewKey o1, VolteBusiNewKey o2) {
		return o1.getImsi().compareTo(o2.getImsi());
	}

	@Override
	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
		return WritableComparator.compareBytes(b1, s1, Integer.SIZE / 8, b2, s2, Integer.SIZE / 8);
	}
}
