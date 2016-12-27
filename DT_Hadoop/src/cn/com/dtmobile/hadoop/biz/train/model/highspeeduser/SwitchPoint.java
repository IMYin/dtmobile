package cn.com.dtmobile.hadoop.biz.train.model.highspeeduser;

import cn.com.dtmobile.hadoop.util.StringUtils;


public class SwitchPoint {
	private long cellId;
	private long targetCellId ;
	private long upordown;
	private double nlong;
	private double nlat;
	private long gridId;
	private long dcenter ;
	
	
	public SwitchPoint() {
		
	}
	public SwitchPoint(String[] arr) {
		this.cellId = StringUtils.isNotEmpty(arr[0]) ? Long.valueOf(arr[0]) : null;
		this.targetCellId = StringUtils.isNotEmpty(arr[1]) ? Long.valueOf(arr[1]) : null;
		this.upordown = StringUtils.isNotEmpty(arr[2]) ? Long.valueOf(arr[2]) : null;
		this.nlong = StringUtils.isNotEmpty(arr[3]) ? Long.valueOf(arr[3]) : null;
		this.nlat = StringUtils.isNotEmpty(arr[4]) ? Long.valueOf(arr[4]) : null;
		this.gridId = StringUtils.isNotEmpty(arr[5]) ? Long.valueOf(arr[5]) : null;
		this.dcenter = StringUtils.isNotEmpty(arr[8]) ? Long.valueOf(arr[8]) : null;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(cellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(targetCellId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(upordown);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(nlong);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(nlat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gridId);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dcenter);
		
		return sb.toString();
	}
	/**
	 * @return the cellId
	 */
	public long getCellId() {
		return cellId;
	}
	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(long cellId) {
		this.cellId = cellId;
	}
	/**
	 * @return the targetCellId
	 */
	public long getTargetCellId() {
		return targetCellId;
	}
	/**
	 * @param targetCellId the targetCellId to set
	 */
	public void setTargetCellId(long targetCellId) {
		this.targetCellId = targetCellId;
	}
	/**
	 * @return the upordown
	 */
	public long getUpordown() {
		return upordown;
	}
	/**
	 * @param upordown the upordown to set
	 */
	public void setUpordown(long upordown) {
		this.upordown = upordown;
	}
	/**
	 * @return the nlong
	 */
	public double getNlong() {
		return nlong;
	}
	/**
	 * @param nlong the nlong to set
	 */
	public void setNlong(double nlong) {
		this.nlong = nlong;
	}
	/**
	 * @return the nlat
	 */
	public double getNlat() {
		return nlat;
	}
	/**
	 * @param nlat the nlat to set
	 */
	public void setNlat(double nlat) {
		this.nlat = nlat;
	}
	/**
	 * @return the gridId
	 */
	public long getGridId() {
		return gridId;
	}
	/**
	 * @param gridId the gridId to set
	 */
	public void setGridId(long gridId) {
		this.gridId = gridId;
	}

	/**
	 * @return the dcenter
	 */
	public long getDcenter() {
		return dcenter;
	}
	/**
	 * @param dcenter the dcenter to set
	 */
	public void setDcenter(long dcenter) {
		this.dcenter = dcenter;
	}

	
}
