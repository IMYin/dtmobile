package cn.com.dtmobile.hadoop.biz.exception.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.ExceptionConstnts;
import cn.com.dtmobile.hadoop.biz.exception.constants.InterfaceConstants;
import cn.com.dtmobile.hadoop.biz.exception.model.S1mmeExceptionMessage;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class Lose4GAnalyService {

	public S1mmeExceptionMessage s1mmeExceptionMessage;
	public LteMroSourceNew miniUeMr;
	public LteMroSourceNew miniUe6Mr;
	public LteMroSourceNew miniCellMr;

	/**
	 * 4G\u6389\u7ebf\u5206\u6790
	 * 
	 * @param s1mmeXdrList
	 * @param lteMroSourceList
	 * @param uuXdrList
	 * @param cellXdrMap
	 * @param exceptionMap
	 * @return
	 */
	public List<S1mmeExceptionMessage> Lose4GService(
			List<S1mmeXdrNew> s1mmeXdrList, List<LteMroSourceNew> lteMroSourceList,
			List<UuXdrNew> uuXdrList, Map<String, String> cellXdrMap,
			Map<String, String> exceptionMap) {
		List<S1mmeExceptionMessage> s1ExceptionMessageList = new ArrayList<S1mmeExceptionMessage>();
		if (s1mmeXdrList.size() > 0) {

			for (S1mmeXdrNew s1mmeXdr : s1mmeXdrList) {
				if (5 == s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces()
						&& "20".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())
						&& (!"2".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
								|| !"20".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
								|| !"23".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
								|| !"24".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
								|| !"512".equals(s1mmeXdr.getS1mmeXdr().getRequestCause()) || !"514"
								.equals(s1mmeXdr.getS1mmeXdr().getRequestCause()))) {
					s1mmeExceptionMessage = this._4GLoseAnalyOne(s1mmeXdr,
							lteMroSourceList, cellXdrMap, exceptionMap);
					if (s1mmeExceptionMessage != null) {
						s1ExceptionMessageList.add(s1mmeExceptionMessage);
						continue;
					}

				} 
			}
		}
		return s1ExceptionMessageList;
	}

	private S1mmeExceptionMessage _4GLoseAnalyOne(S1mmeXdrNew s1mmeXdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		if (ExceptionConstnts.FG_OFFLINE_FAIL == s1mmeXdr.getEtype()) {
			if (StringUtils.isNotEmpty(s1mmeXdr.getS1mmeXdr().getRequestCause())
					&& Integer.parseInt(s1mmeXdr.getS1mmeXdr().getRequestCause()) > 256) {
				return new S1mmeExceptionMessage(s1mmeXdr,
						s1mmeXdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.S1AP,
						exceptionMap);
			} else if ("10".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
					|| "12".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
					|| "25".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())) {
				return new S1mmeExceptionMessage(s1mmeXdr,
						s1mmeXdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.S1AP,
						exceptionMap);
			} else if ("1".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())) {
				return new S1mmeExceptionMessage(s1mmeXdr,
						s1mmeXdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.S1AP,
						exceptionMap);
			} else if ("8".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())) {
				return new S1mmeExceptionMessage(s1mmeXdr,
						s1mmeXdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.S1AP,
						exceptionMap);
			} else if ("3".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
					|| "6".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
					|| "21".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())
					|| "26".equals(s1mmeXdr.getS1mmeXdr().getRequestCause())) {
				// \u6b65\u9aa42
				this._4GLoseAnalyTwo(s1mmeXdr, lteMroSourceList, cellXdrMap,
						exceptionMap);
			}
		}
		return s1mmeExceptionMessage;
	}

	private S1mmeExceptionMessage _4GLoseAnalyTwo(S1mmeXdrNew s1mmeXdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		List<LteMroSourceNew> ueMr1List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMr6List = new ArrayList<LteMroSourceNew>();
		if (lteMroSourceList.size() > 0) {
			for (LteMroSourceNew lteMroSource : lteMroSourceList) {
				if (lteMroSource.getLtemrosource().getMrtime() >= s1mmeXdr.getS1mmeXdr()
						.getProcedureStartTime() - 20000
						&& lteMroSource.getLtemrosource().getMrtime() <= s1mmeXdr.getS1mmeXdr()
								.getProcedureEndTime() + 1000
						&& lteMroSource.getLtemrosource().getCellid()
								.equals(s1mmeXdr.getS1mmeXdr().getCellId())) {
					if (TablesConstants.TABLE_UE_MR_XDR.equals(lteMroSource.getLtemrosource()
							.getMrname())) {
						if (lteMroSource.getLtemrosource().getKpi1() != 0) {
							ueMr1List.add(lteMroSource);
						} else if (lteMroSource.getLtemrosource().getKpi6() != 0) {
							ueMr6List.add(lteMroSource);
						}
					}
				}
			}
			miniUeMr = this.getMinKpi(s1mmeXdr, ueMr1List);
			if (miniUeMr != null) {
				if (miniUeMr.getLtemrosource().getKpi1() < 31
						&& "21".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
					if ("6".equals(s1mmeXdr.getS1mmeXdr().getBearerArr()[0].split(StringUtils.DELIMITER_INNER_ITEM)[4])) {
						return new S1mmeExceptionMessage(s1mmeXdr, "14",
								InterfaceConstants.UU, exceptionMap);
					}
				}
			}
			double avg = this.getAvgKpi(s1mmeXdr, cellXdrMap);
			if (avg > 111) {
				return new S1mmeExceptionMessage(s1mmeXdr, "2",
						InterfaceConstants.UU, exceptionMap);
			}
			miniUe6Mr = this.getMinKpi(s1mmeXdr, ueMr6List);
			if (miniUe6Mr != null) {
				
				if (miniUe6Mr.getLtemrosource().getKpi6() < 25) {
					if (miniUe6Mr.getLtemrosource().getKpi8() >= 22) {
						return new S1mmeExceptionMessage(s1mmeXdr, "3",
								InterfaceConstants.UU, exceptionMap);
					} else {
						return new S1mmeExceptionMessage(s1mmeXdr, "4",
								InterfaceConstants.UU, exceptionMap);
					}
				} else {
					return new S1mmeExceptionMessage(s1mmeXdr,
							s1mmeXdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.NAS,
							exceptionMap);
				}
			}else {
				return new S1mmeExceptionMessage(s1mmeXdr,
						s1mmeXdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.S1AP,
						exceptionMap);
			}
		} else {
			return new S1mmeExceptionMessage(s1mmeXdr,
					s1mmeXdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.S1AP,
					exceptionMap);
		}
	}

	public LteMroSourceNew getMinKpi(S1mmeXdrNew s1mmeXdr,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			curTime = Math.abs(s1mmeXdr.getS1mmeXdr().getProcedureStartTime()
					- lteMroSource.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			LteMroSourceMap.put(minTime, lteMroSource);
		}
		return LteMroSourceMap.get(minTime);
	}

	@SuppressWarnings("rawtypes")
	public Double getAvgKpi(S1mmeXdrNew s1mme, Map<String, String> cellXdrMap) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Iterator iter = cellXdrMap.entrySet().iterator();
		Double kpiAvg = (double) 0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getKey().equals(s1mme.getS1mmeXdr().getCellId())) {
				curTime = Math
						.abs(s1mme.getS1mmeXdr().getProcedureStartTime()
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
