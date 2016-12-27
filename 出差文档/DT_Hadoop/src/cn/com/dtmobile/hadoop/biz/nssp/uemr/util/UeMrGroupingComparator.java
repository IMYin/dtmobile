package cn.com.dtmobile.hadoop.biz.nssp.uemr.util;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparator;

public class UeMrGroupingComparator implements RawComparator<UeMrKey> {
	@Override
	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
		return WritableComparator.compareBytes(b1, s1, Integer.SIZE / 8, b2, s2, Integer.SIZE / 8);
	}

	@Override
	public int compare(UeMrKey o1, UeMrKey o2) {
		String l = o1.getKey();
		String r = o2.getKey();
		return l.compareTo(r);
	}

	public static void main(String[] args) {
		String[] str = new String[2];
		str[0] = "2,1,1,2,2";
		str[1] = "4,1,1,2,2,3,3,4,4";

		for (String s : str) {
			String[] arr = s.split(",");
			StringBuffer sb = new StringBuffer();
			int i = Integer.valueOf(arr[0]);
			int j = 1;
			do {
				sb.append(arr[j]);
				sb.append("|");
				sb.append(arr[j + 1]);
				sb.append("\t");
				j = j + 2;
			} while (j <= i*2);
			System.out.println(sb.toString());
		}
	}
}
