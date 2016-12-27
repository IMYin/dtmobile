package cn.com.dtmobile.hadoop.biz.nssp.uemr.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UeMrKey implements WritableComparable<UeMrKey> {
	static { 
		WritableComparator.define(UeMrKey.class, new UeMrComparator());
	}
	
	private String key ;
	private int time = 0;
	
	public void set(String left, int right) {
		key = left;
		time = right;
	}

	public String getKey() {
		return key;
	}


	public int getTime() {
		return time;
	}


	@Override
	public void readFields(DataInput in) throws IOException {
		key = in.readUTF();
		time = in.readInt() + Integer.MIN_VALUE;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(key);;
		out.writeInt(time - Integer.MIN_VALUE);
	}

	@Override
	public int hashCode() {
		return key.hashCode() + time;
	}
	
	@Override
	public boolean equals(Object right) {
		if (right instanceof UeMrKey) {
			UeMrKey r = (UeMrKey) right;
			return r.key.equals(key) && r.time == time;
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(UeMrKey o) {
		if (!key.equals(o.key)) {
			return key.compareTo(o.key);
		} else if (time != o.time) {
			return time < o.time ? -1 : 1;
		} else {
			return 0;
		}
	}

}
