package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

import cn.com.dtmobile.hadoop.model.CommXdr;
import cn.com.dtmobile.hadoop.model.S1mmeXdr;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteFreeNew  extends GtUser{
	private S1mmeXdr s1mmeXdr;

	public VolteFreeNew(S1mmeXdr s1mmeXdr, int dir, int seqnum, int ispub) {
		super(dir,seqnum,ispub);
		this.s1mmeXdr = s1mmeXdr;
	}

	public VolteFreeNew(String[] values) {
		super(values[61],0,0,0);
		CommXdr commXdr = new CommXdr(values);
		S1mmeXdr s1mmeXdr = new S1mmeXdr();
		commXdr.setImsi(values[5]);
		commXdr.setImei(values[6]);
		commXdr.setMsisdn(values[7]);
		s1mmeXdr.setProcedureType(values[8]);
		s1mmeXdr.setCommXdr(commXdr);
		s1mmeXdr.setProcedureStartTime(Long.valueOf(values[9]));
		s1mmeXdr.setProcedureEndTime(Long.valueOf(values[10]));
		s1mmeXdr.setCellId(values[32]);
		s1mmeXdr.setProcedureStatus(values[14]);
		this.s1mmeXdr = s1mmeXdr;
	}

	
	public String getRangetime() {
		return rangetime;
	}

	public void setRangetime(String rangetime) {
		this.rangetime = rangetime;
	}


	public S1mmeXdr getS1mmeXdr() {
		return s1mmeXdr;
	}

	public void setS1mmeXdr(S1mmeXdr s1mmeXdr) {
		this.s1mmeXdr = s1mmeXdr;
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

	/*
	 * @return:
	 * imsi,imei,msisdn,rangetime,procedurestarttime,procedureendtime,enbid,
	 * cellid,targetenbid,targetcellid,dir,seqnum,ispub
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.s1mmeXdr.getCommXdr().getImsi());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.s1mmeXdr.getCellId());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.s1mmeXdr.getProcedureType());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.s1mmeXdr.getProcedureStatus());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.rangetime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.s1mmeXdr.getCommXdr().getImei());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.s1mmeXdr.getCommXdr().getMsisdn());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.s1mmeXdr.getProcedureStartTime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.s1mmeXdr.getProcedureEndTime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.dir);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.seqnum);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.ispub);

		return sb.toString();
	}
}
