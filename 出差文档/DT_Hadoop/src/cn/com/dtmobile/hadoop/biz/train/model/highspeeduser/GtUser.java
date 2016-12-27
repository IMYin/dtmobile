package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

public class GtUser {
	public String rangetime;
	public int dir;
	public int seqnum;
	public int ispub;
	
	public GtUser(String rangetime, int dir, int seqnum, int ispub) {
		super();
		this.rangetime = rangetime;
		this.dir = dir;
		this.seqnum = seqnum;
		this.ispub = ispub;
	}
	public GtUser(int dir2, int seqnum2, int ispub2) {
		// TODO Auto-generated constructor stub
	}
	public String getRangetime() {
		return rangetime;
	}
	public void setRangetime(String rangetime) {
		this.rangetime = rangetime;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public int getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(int seqnum) {
		this.seqnum = seqnum;
	}
	public int getIspub() {
		return ispub;
	}
	public void setIspub(int ispub) {
		this.ispub = ispub;
	}
	@Override
	public String toString() {
		return rangetime + ", " + dir + ", " + seqnum + ", " + ispub;
	}
	
}
