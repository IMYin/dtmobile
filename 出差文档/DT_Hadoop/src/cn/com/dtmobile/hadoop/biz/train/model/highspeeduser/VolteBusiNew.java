package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

import cn.com.dtmobile.hadoop.model.UuXdr;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteBusiNew extends GtUser{
	private UuXdr uuXdr;

	public VolteBusiNew(UuXdr uuXdr, int dir, int seqnum, int ispub) {
		super(dir,seqnum,ispub);
		this.uuXdr = uuXdr;
	}

	public VolteBusiNew(String[] values) {
		super(values[values.length-1],0,0,0);
		UuXdr uuXdr = new UuXdr(values);
		this.uuXdr = uuXdr;
	}
	
	public UuXdr getUuXdr() {
		return uuXdr;
	}

	public void setUuXdr(UuXdr uuXdr) {
		this.uuXdr = uuXdr;
	}

	/*
	 * @return:
	 * imsi,imei,msisdn,rangetime,procedurestarttime,procedureendtime,enbid,
	 * cellid,targetenbid,targetcellid,dir,seqnum,ispub
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.uuXdr.getCommXdr().getImsi());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.uuXdr.getCellId());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.uuXdr.getTargetCellId());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.uuXdr.getProcedureType());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.uuXdr.getProcedureStatus());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		
		sb.append(this.rangetime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.uuXdr.getCommXdr().getImei());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.uuXdr.getCommXdr().getMsisdn());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.uuXdr.getProcedureStartTime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.uuXdr.getProcedureEndTime());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.uuXdr.getEnbId());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.uuXdr.getTargetEnbId());
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.dir);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.seqnum);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);

		sb.append(this.ispub);

		return sb.toString();
	}
}
