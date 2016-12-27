package cn.com.dtmobile.hadoop.biz.exception.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class Exception4GUtils {
	public String stepEightUu(Object xdr, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			Map<String, String> exceptionMap) {
		List<LteMroSourceNew> ueMr1List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMr6List = new ArrayList<LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			if (lteMroSource.getLtemrosource().getVid() == 1
					&& lteMroSource.getLtemrosource().getMrtime() >= this.getLongVal(xdr,
							"getProcedureStartTime") - 20000
					&& lteMroSource.getLtemrosource().getMrtime() <= this.getLongVal(xdr,
							"getProcedureEndTime") + 1000) {
				if (TablesConstants.TABLE_UE_MR_XDR_FLAG.equals(lteMroSource.getLtemrosource()
						.getMrname())) {
					if (this.getStringVal(cellXdrMap, "getCellId").equals(
							lteMroSource.getLtemrosource().getCellid())) {
						for (Map.Entry<String, String> e:ltecellMap.entrySet()) {
							String[] values = e.getValue().split(StringUtils.DELIMITER_BETWEEN_ITEMS);
							String freq = values[0];
							String pci = values[1];
							if (freq.equals(
									lteMroSource.getLtemrosource().getKpi11())
									&& pci.equals(
											lteMroSource.getLtemrosource().getKpi12())
									|| "A3".equals(lteMroSource.getLtemrosource().getEventtype())) {
								if (lteMroSource.getLtemrosource().getKpi1() != 0) {
									ueMr1List.add(lteMroSource);
								} else if (lteMroSource.getLtemrosource().getKpi6() != 0) {
									ueMr6List.add(lteMroSource);
								}
							}
						}
					}
				}
			}
		}
		if (ueMr1List.size() > 0
				&& this.getMinKpi(xdr, ueMr1List).getLtemrosource().getKpi1() < 31) {
			return "O3";
		} else {
			double avg = this.getAvgKpi(xdr, cellXdrMap);
			if (avg > 111) {
				return "O10";
			} else {
				if (ueMr6List.size() > 0) {
					LteMroSourceNew ue6MrSource = this.getMinKpi(xdr, ueMr6List);
					if (ue6MrSource.getLtemrosource().getKpi6() < 25) {
						if (ue6MrSource.getLtemrosource().getKpi8() >= 22) {
							return "O5";
						}else if (ue6MrSource.getLtemrosource().getKpi8() < 22){
							return "O6";
						}
					} else {
						return "O8";
					}
				}
			}

			return "";
		}
	}

	public String getStringVal(Object xdr, String methodName) {
		Method method;
		try {
			method = xdr.getClass().getMethod(methodName);
			return (String) method.invoke(xdr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public long getLongVal(Object xdr, String methodName) {
		Method method;
		try {
			method = xdr.getClass().getMethod(methodName);
			return (Long) method.invoke(xdr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public LteMroSourceNew getMinKpi(Object xdr,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			curTime = Math.abs(this.getLongVal(xdr, "getProcedureStartTime")
					- lteMroSource.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			LteMroSourceMap.put(minTime, lteMroSource);
		}
		return LteMroSourceMap.get(minTime);
	}

	@SuppressWarnings("rawtypes")
	public Double getAvgKpi(Object xdr, Map<String, String> cellXdrMap) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Iterator iter = cellXdrMap.entrySet().iterator();
		Double kpiAvg = 0.0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getKey().equals(this.getStringVal(xdr, "getSource_eci"))) {
				curTime = Math
						.abs(this.getLongVal(xdr, "getProcedureStartTime")
								- Long.valueOf(entry.getValue().toString()
										.split(",")[0]));
				if (minTime > curTime) {
					minTime = curTime;
					kpiAvg = Double.valueOf(entry.getValue().toString()
							.split(",")[1]);
				}
			}
		}
		return kpiAvg;
	}

}