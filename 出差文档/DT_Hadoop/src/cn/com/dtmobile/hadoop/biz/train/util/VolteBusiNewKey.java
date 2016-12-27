package cn.com.dtmobile.hadoop.biz.train.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;

//重写key,按imsi归类，按procedureStartTime排序
public class VolteBusiNewKey implements WritableComparable<VolteBusiNewKey> {
	private String imsi;
	private Long procedureStartTime = 0L;

	public VolteBusiNewKey(String imsi, Long procedureStartTime) {
		this.imsi = imsi;
		this.procedureStartTime = procedureStartTime;
	}

	public String getImsi() {
		return imsi;
	}

	public Long getProcedureStartTime() {
		return procedureStartTime;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		imsi = in.readUTF();
		procedureStartTime = in.readLong() + Long.MAX_VALUE;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(imsi);
		out.writeLong(procedureStartTime - Long.MAX_VALUE);
	}

	@Override
	public int hashCode() {
		return imsi.hashCode() + procedureStartTime.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof VolteBusiNewKey) {
			VolteBusiNewKey volteBusiNewKey = (VolteBusiNewKey) other;
			return volteBusiNewKey.imsi.equals(imsi) && volteBusiNewKey.procedureStartTime == procedureStartTime;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(VolteBusiNewKey other) {
		if (!imsi.equals(other.imsi)) {
			return imsi.compareTo(other.imsi);
		} else if (procedureStartTime != other.getProcedureStartTime()) {
			return procedureStartTime < other.getProcedureStartTime() ? -1 : 1;
		} else {
			return 0;
		}
	}
}
