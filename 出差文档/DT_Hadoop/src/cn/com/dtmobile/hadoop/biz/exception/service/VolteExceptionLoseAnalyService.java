package cn.com.dtmobile.hadoop.biz.exception.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.ExceptionConstnts;
import cn.com.dtmobile.hadoop.biz.exception.constants.InterfaceConstants;
import cn.com.dtmobile.hadoop.biz.exception.model.GxExceptionMessage;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.GxRxNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

/**
 * VOLTE\u6389\u8bdd\u5206\u6790\u6d41\u7a0b
 * 
 * @author zhangchao15
 * @version 2016-11-14
 * 
 */
public class VolteExceptionLoseAnalyService {
	GxExceptionMessage gxExceptionMessage = null;
	LteMroSourceNew miniUeMr;
	LteMroSourceNew miniUe6Mr;
	LteMroSourceNew miniCellMr;

	public List<GxExceptionMessage> volteNoConnService(List<GxRxNew> gxXdrList,
			List<S1mmeXdrNew> s1mmeXdrList, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		List<GxExceptionMessage> gxExceptionMessageList = new ArrayList<GxExceptionMessage>();
		if (gxXdrList.size() > 0) {

			for (GxRxNew gxXdr : gxXdrList) {
				if (ExceptionConstnts.VOLET_VOICE_OFFLINE_FAIL == gxXdr
						.getEtype()
						|| ExceptionConstnts.VOLET_VIEDO_OFFLINE_FAIL == gxXdr
								.getEtype()) {
					// \u6b65\u9aa41
					gxExceptionMessage = this._volteNoConnAnalyOne(gxXdr,
							s1mmeXdrList, lteMroSourceList, cellXdrMap,
							exceptionMap);
					if (gxExceptionMessage != null) {
						gxExceptionMessageList.add(gxExceptionMessage);
						continue;
					}

				} else {
					gxExceptionMessageList.add(new GxExceptionMessage(gxXdr,
							"2005", InterfaceConstants.S1AP, exceptionMap));
				}
			}
		}
		return gxExceptionMessageList;
	}

	private GxExceptionMessage _volteNoConnAnalyOne(GxRxNew gxXdr,
			List<S1mmeXdrNew> s1mmeXdrList, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		if ("26".equals(gxXdr.getGxXdr().getInterfaces())) {
			List<S1mmeXdrNew> s1mmeCellIdList = new ArrayList<S1mmeXdrNew>();
			// rx\u53e3\u6ca1\u6709cellid \u65e0\u6cd5\u548ccellMap\u7684key\u5339\u914d,\u9700\u8981\u5f80\u4e0a\u67e5\u4eces1mme\u91cc\u53d6cellid
			if (s1mmeXdrList.size() > 0) {

				for (S1mmeXdrNew s1mmeXdr : s1mmeXdrList) {
					if ("5".equals(s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces())
							&& s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= gxXdr.getGxXdr()
									.getProcedureStartTime()
							&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= gxXdr.getGxXdr()
									.getProcedureEndTime()) {
						s1mmeCellIdList.add(s1mmeXdr);
					}
				}
				for (S1mmeXdrNew s1mmeXdr : s1mmeXdrList) {
					if ("5".equals(s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces())
							&& s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= gxXdr.getGxXdr()
									.getProcedureStartTime()
							&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= gxXdr.getGxXdr()
									.getProcedureEndTime()) {
						if ("21".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
							// \u6b65\u9aa42
							this._volteNoConnAnalyTwo(gxXdr, s1mmeXdr,
									s1mmeCellIdList, lteMroSourceList,
									cellXdrMap, exceptionMap);
						} else {
							// \u6b65\u9aa44
							this._volteNoConnAnalyFour(gxXdr, s1mmeXdr,
									s1mmeXdrList, exceptionMap);
						}
					}
				}
			} else {
				return new GxExceptionMessage(gxXdr, "2005",
						InterfaceConstants.S1AP, exceptionMap);
			}
		}
		return gxExceptionMessage;
	}

	private GxExceptionMessage _volteNoConnAnalyTwo(GxRxNew gxXdr,
			S1mmeXdrNew s1mmeXdr, List<S1mmeXdrNew> s1mmeCellIdList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		String bear1reqcase = s1mmeXdr.getS1mmeXdr().getBearerArr()[0]
				.split(StringUtils.DELIMITER_INNER_ITEM)[4];
		if ("3".equals(bear1reqcase) || "6".equals(bear1reqcase)
				|| "21".equals(bear1reqcase) || "26".equals(bear1reqcase)) {
			// \u6b65\u9aa43
			this._volteNoConnAnalyThree(gxXdr, s1mmeXdr, s1mmeCellIdList,
					lteMroSourceList, cellXdrMap, exceptionMap);
		} else {
			return new GxExceptionMessage(gxXdr, gxXdr.getGxXdr().getAbortCause()
					.toString(), InterfaceConstants.S1AP, exceptionMap);
		}
		return gxExceptionMessage;
	}

	private GxExceptionMessage _volteNoConnAnalyThree(GxRxNew gxXdr,
			S1mmeXdrNew s1mmeXdr, List<S1mmeXdrNew> s1mmeCellIdList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		List<LteMroSourceNew> ueMrList = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMr6List = new ArrayList<LteMroSourceNew>();
		S1mmeXdrNew s1mmeLeast = this.getMinS1mme(gxXdr, s1mmeCellIdList);
		gxXdr.getGxXdr().setCellId(s1mmeLeast.getS1mmeXdr().getCellId());
		gxXdr.getGxXdr().setMmeCode(s1mmeLeast.getS1mmeXdr().getMmeCode());
		gxXdr.getGxXdr().setMmeGroupId(s1mmeLeast.getS1mmeXdr().getMmeGroupId());
		if (lteMroSourceList.size() > 0) {

			for (LteMroSourceNew lteMroSource : lteMroSourceList) {
				if (lteMroSource.getLtemrosource().getMrtime() >= s1mmeXdr.getS1mmeXdr()
						.getProcedureStartTime() - 20000
						&& lteMroSource.getLtemrosource().getMrtime() <= s1mmeXdr.getS1mmeXdr()
								.getProcedureStartTime() + 1000
						&& lteMroSource.getLtemrosource().getCellid().equals(
								s1mmeLeast.getS1mmeXdr().getCellId())) {
					if (TablesConstants.TABLE_UE_MR_XDR.equals(lteMroSource.getLtemrosource()
							.getMrname())) {
						if (lteMroSource.getLtemrosource().getKpi1() != 0) {
							ueMrList.add(lteMroSource);
						} else if (lteMroSource.getLtemrosource().getKpi6() != 0) {
							ueMr6List.add(lteMroSource);
						}
					}
				}
			}
			miniUeMr = this.getMinKpi(gxXdr, ueMrList);
			if (miniUeMr.getLtemrosource().getKpi1() != 0) {
				if (miniUeMr != null && miniUeMr.getLtemrosource().getKpi1() < 31) {
					if ("21".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())
							&& "6".equals(s1mmeXdr.getS1mmeXdr().getBearerArr()[0]
									.split(StringUtils.DELIMITER_INNER_ITEM)[4])) {
						return new GxExceptionMessage(gxXdr, "14",
								InterfaceConstants.UU, exceptionMap);
					} else {
						return new GxExceptionMessage(gxXdr, "1",
								InterfaceConstants.UU, exceptionMap);
					}
				} else {
					long avg = this.getAvgKpi(s1mmeLeast, cellXdrMap);
					if (avg > 111) {
						return new GxExceptionMessage(gxXdr, "2",
								InterfaceConstants.UU, exceptionMap);
					}
					if (ueMr6List.size() > 0) {
						miniUe6Mr = this.getMinKpi(gxXdr, ueMr6List);
						if (miniUe6Mr.getLtemrosource().getKpi6() < 25) {
							if (miniUe6Mr.getLtemrosource().getKpi8() >= 22) {
								return new GxExceptionMessage(gxXdr, "3",
										InterfaceConstants.UU, exceptionMap);
							} else {
								return new GxExceptionMessage(gxXdr, "4",
										InterfaceConstants.UU, exceptionMap);
							}
						}
					}
				}
			}
		} else {
			return new GxExceptionMessage(gxXdr, gxXdr.getGxXdr().getAbortCause()
					.toString(), InterfaceConstants.S1AP, exceptionMap);
		}
		return gxExceptionMessage;
	}

	private GxExceptionMessage _volteNoConnAnalyFour(GxRxNew gxXdr,
			S1mmeXdrNew s1mmeXdr, List<S1mmeXdrNew> s1mmeXdrList,
			Map<String, String> exceptionMap) {
		if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= gxXdr.getGxXdr().getProcedureStartTime() - 3000
				&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= gxXdr.getGxXdr()
						.getProcedureEndTime()) {
			if ("5".equals(s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces())
					&& "1".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
				// \u6b65\u9aa45
				this._volteNoConnAnalyFive(gxXdr, s1mmeXdr, s1mmeXdrList,
						exceptionMap);
			} else {
				return new GxExceptionMessage(gxXdr, "2005",
						InterfaceConstants.S1AP, exceptionMap);
			}
		}
		return gxExceptionMessage;
	}

	private GxExceptionMessage _volteNoConnAnalyFive(GxRxNew gxXdr,
			S1mmeXdrNew s1mmeXdr, List<S1mmeXdrNew> s1mmeXdrList,
			Map<String, String> exceptionMap) {
		if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= gxXdr.getGxXdr().getProcedureStartTime() - 5000
				&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= gxXdr.getGxXdr()
						.getProcedureEndTime()) {
			if (("2".equals(s1mmeXdr.getS1mmeXdr().getProcedureType()) || "5".equals(s1mmeXdr.getS1mmeXdr()
					.getProcedureType()))
					&& "1".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())
					&& "10".equals(s1mmeXdr.getS1mmeXdr().getFailureCause())) {
				// \u6b65\u9aa46
				this._volteNoConnAnalySix(gxXdr, s1mmeXdr, s1mmeXdrList,
						exceptionMap);
			} else {
				return new GxExceptionMessage(gxXdr, "2008",
						InterfaceConstants.S1AP, exceptionMap);
			}
		}
		return gxExceptionMessage;
	}

	private GxExceptionMessage _volteNoConnAnalySix(GxRxNew gxXdr,
			S1mmeXdrNew s1mmeXdr, List<S1mmeXdrNew> s1mmeXdrList,
			Map<String, String> exceptionMap) {
		if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() - 60000 >= gxXdr.getGxXdr()
				.getProcedureStartTime() - 5000
				&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= gxXdr.getGxXdr()
						.getProcedureEndTime()) {
			if ("5".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())
					&& "255".equals(s1mmeXdr.getS1mmeXdr().getProcedureStatus())) {
				// \u6b65\u9aa47
				this._volteNoConnAnalySeven(gxXdr, s1mmeXdr, s1mmeXdrList,
						exceptionMap);
			} else {
				return new GxExceptionMessage(gxXdr, "2008",
						InterfaceConstants.S1AP, exceptionMap);
			}
		}
		return gxExceptionMessage;
	}

	private GxExceptionMessage _volteNoConnAnalySeven(GxRxNew gxXdr,
			S1mmeXdrNew s1mmeXdr, List<S1mmeXdrNew> s1mmeXdrList,
			Map<String, String> exceptionMap) {
		if (s1mmeXdrList.size() > 0) {

			for (S1mmeXdrNew s1xdr : s1mmeXdrList) {
				if ("14".equals(s1xdr.getS1mmeXdr().getProcedureType())
						&& 15 == s1xdr.getS1mmeXdr().getCommXdr().getInterfaces()
						&& s1xdr.getS1mmeXdr().getProcedureStartTime() >= s1mmeXdr.getS1mmeXdr()
								.getProcedureStartTime()
						&& s1xdr.getS1mmeXdr().getProcedureEndTime() <= s1mmeXdr.getS1mmeXdr()
								.getProcedureEndTime() + 300) {
					return new GxExceptionMessage(gxXdr, "2002",
							InterfaceConstants.S1AP, exceptionMap);
				} else {
					return new GxExceptionMessage(gxXdr, "2007",
							InterfaceConstants.S1AP, exceptionMap);
				}
			}
		} else {
			return new GxExceptionMessage(gxXdr, "2007",
					InterfaceConstants.S1AP, exceptionMap);
		}
		return gxExceptionMessage;
	}

	public LteMroSourceNew getMinKpi(GxRxNew gxXdr,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = 10000000L;
		Long curTime = 0L;
		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			curTime = Math.abs(gxXdr.getGxXdr().getProcedureStartTime()
					- lteMroSource.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			LteMroSourceMap.put(minTime, lteMroSource);
		}
		return LteMroSourceMap.get(minTime);
	}

	public S1mmeXdrNew getMinS1mme(GxRxNew gxXdr, List<S1mmeXdrNew> s1mmeXdrList) {
		Long minTime = 10000000L;
		Long curTime = 0L;
		Map<Long, S1mmeXdrNew> S1mmeXdrMap = new HashMap<Long, S1mmeXdrNew>();
		for (S1mmeXdrNew s1mme : s1mmeXdrList) {
			curTime = Math.abs(gxXdr.getGxXdr().getProcedureStartTime()
					- s1mme.getS1mmeXdr().getProcedureStartTime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			S1mmeXdrMap.put(minTime, s1mme);
		}
		return S1mmeXdrMap.get(minTime);
	}

	@SuppressWarnings("rawtypes")
	public Long getAvgKpi(S1mmeXdrNew s1mme, Map<String, String> cellXdrMap) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Iterator iter = cellXdrMap.entrySet().iterator();
		Long kpiAvg = 0L;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getKey().equals(s1mme.getS1mmeXdr().getCellId())) {
				curTime = Math
						.abs(s1mme.getS1mmeXdr().getProcedureStartTime()
								- Long.valueOf(entry.getValue().toString()
										.split(",")[0]));
				if (minTime > curTime) {
					minTime = curTime;
					kpiAvg = Long.valueOf(entry.getValue().toString()
							.split(",")[1]);
				}
			}
		}
		return kpiAvg;
	}
}
