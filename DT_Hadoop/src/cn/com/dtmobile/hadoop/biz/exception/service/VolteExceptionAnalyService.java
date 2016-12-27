package cn.com.dtmobile.hadoop.biz.exception.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.ExceptionConstnts;
import cn.com.dtmobile.hadoop.biz.exception.constants.InterfaceConstants;
import cn.com.dtmobile.hadoop.biz.exception.model.MwExceptionMessage;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.MwNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteExceptionAnalyService {
	MwExceptionMessage mwExceptionMessage = null;

	/*
	 * @param mwXdrList:mw\u63a5\u53e3\u96c6\u5408
	 * 
	 * @param s1mmeXdrList:s1mme\u63a5\u53e3\u96c6\u5408
	 * 
	 * @param lteMroSourceList:lteMroSource\u63a5\u53e3\u96c6\u5408
	 * 
	 * @param cellXdrMap:cellXdrMap \u5b58\u50a8\u683c\u5f0f key:cellid value:time+","+kpiavg
	 * 
	 * @param exceptionMap:\u9519\u8bef\u539f\u56e0\u4ee3\u7801
	 * 
	 * @return List<MwExceptionMessage>:\u8fd4\u56de\u5bfc\u5e38\u5206\u6790\u7ed3\u679c
	 */
	public List<MwExceptionMessage> volteNoConnService(List<MwNew> mwXdrList, List<S1mmeXdrNew> s1mmeXdrList,
			List<LteMroSourceNew> ueMroSourceList, Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		List<MwExceptionMessage> mwExceptionMessageList = new ArrayList<MwExceptionMessage>();
		List<MwNew> iscMgXdrList = new ArrayList<MwNew>();
		if (mwXdrList.size() > 0) {
			
			for (MwNew mwXdr : mwXdrList) {
				if (mwXdr.getMwXdr().getInterfaces() == 15 || mwXdr.getMwXdr().getInterfaces() == 18) {
					iscMgXdrList.add(mwXdr);
				}
			}
			for (MwNew mwXdr : mwXdrList) {
				// MW\u53e3\uff0c ETYPE= 4\u30016\u4e14call_side=0
				if (mwXdr.getMwXdr().getInterfaces() == 14 && ExceptionConstnts.MASTER_CALL.equals(mwXdr.getMwXdr().getCall_side())
						&& (ExceptionConstnts.VOLET_VOICE_CALL_FAIL == mwXdr.getEtype()
								|| ExceptionConstnts.VOLET_VIEDO_CALL_FAIL == mwXdr.getEtype())) {
					// \u6b65\u9aa41
					mwExceptionMessage = volteNoConnAnalyOne(mwXdr, exceptionMap, lteCellMap);
					if (mwExceptionMessage != null) {
						mwExceptionMessageList.add(mwExceptionMessage);
						continue;
					}
					// \u6b65\u9aa42
					mwExceptionMessage = volteNoConnAnalyTwo(mwXdr, iscMgXdrList, exceptionMap, lteCellMap);
					if (mwExceptionMessage != null) {
						mwExceptionMessageList.add(mwExceptionMessage);
						continue;
					}
					// \u6b65\u9aa42\u30014\u30015\u30016
					mwExceptionMessage = volteNoConnAnalyS1mme(mwXdr, s1mmeXdrList, exceptionMap, lteCellMap);
					if (mwExceptionMessage != null) {
						mwExceptionMessageList.add(mwExceptionMessage);
						continue;
					}
					// \u6b65\u9aa47
					mwExceptionMessage = volteNoConnAnalyLteMroSou(mwXdr, ueMroSourceList, cellXdrMap, exceptionMap,
							lteCellMap);
					if (mwExceptionMessage != null) {
						mwExceptionMessageList.add(mwExceptionMessage);
						continue;
					}
				}
			}
		}
		return mwExceptionMessageList;
	}

	public MwExceptionMessage volteNoConnAnalyOne(MwNew mwXdr, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		if (!"408".equals(mwXdr.getMwXdr().getResponse_code()) || !"480".equals(mwXdr.getMwXdr().getResponse_code())
				|| !"487".equals(mwXdr.getMwXdr().getResponse_code()) || !"500".equals(mwXdr.getMwXdr().getResponse_code())
				|| !"503".equals(mwXdr.getMwXdr().getResponse_code()) || !"504".equals(mwXdr.getMwXdr().getResponse_code())
				|| !"580".equals(mwXdr.getMwXdr().getResponse_code())) {
			mwExceptionMessage = new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.SIP,
					exceptionMap, lteCellMap);
		}
		return mwExceptionMessage;
	}

	public MwExceptionMessage volteNoConnAnalyTwo(MwNew mwXdr, List<MwNew> iscMgXdrList,
			Map<String, String> exceptionMap, Map<String, String> lteCellMap) {
		for (MwNew iscMgXdr : iscMgXdrList) {
			if (iscMgXdr.getMwXdr().getProcedurestarttime() >= mwXdr.getMwXdr().getProcedurestarttime()
					&& iscMgXdr.getMwXdr().getProcedurestarttime() <= mwXdr.getMwXdr().getProcedureendtime()) {
				if ("0".equals(iscMgXdr.getMwXdr().getProcedurestatus())) {
					mwExceptionMessage = new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.SIP,
							exceptionMap, lteCellMap);
					return mwExceptionMessage;
				}
			}
		}
		return mwExceptionMessage;
	}

	public MwExceptionMessage volteNoConnAnalyS1mme(MwNew mwXdr, List<S1mmeXdrNew> s1mmeXdrList,
			Map<String, String> exceptionMap, Map<String, String> lteCellMap) {
		if (s1mmeXdrList.size() > 0) {
			for (S1mmeXdrNew s1mmeXdr : s1mmeXdrList) {
				if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr().getProcedurestarttime()
						&& s1mmeXdr.getS1mmeXdr().getProcedureStartTime() <= mwXdr.getMwXdr().getProcedureendtime()) {
					if (s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces() == 5 && "13".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
						if (!"0".equals(s1mmeXdr.getS1mmeXdr().getProcedureStatus())) {
							mwExceptionMessage = new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(),
									InterfaceConstants.NAS, exceptionMap, lteCellMap);
							return mwExceptionMessage;
						}
					} else if (s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces() == 5 && !"21".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
						if (!"13".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
							mwExceptionMessage = new MwExceptionMessage(mwXdr, "2006", InterfaceConstants.S1AP,
									exceptionMap, lteCellMap);
							return mwExceptionMessage;
						} else if ("13".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
							mwExceptionMessage = new MwExceptionMessage(mwXdr, "2005", InterfaceConstants.S1AP,
									exceptionMap, lteCellMap);
							return mwExceptionMessage;
						} else {
							String bear1reqCause = s1mmeXdr.getS1mmeXdr().getBearerArr()[0].split(StringUtils.DELIMITER_INNER_ITEM)[4];
							if ("3".equals(bear1reqCause) && "6".equals(bear1reqCause) && "21".equals(bear1reqCause)
									&& "26".equals(bear1reqCause)) {
								mwExceptionMessage = new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(),
										InterfaceConstants.S1AP, exceptionMap, lteCellMap);
								return mwExceptionMessage;
							}
						}
					}
				}
			}
			
		}else {
			return new MwExceptionMessage(mwXdr, "2006", InterfaceConstants.S1AP, exceptionMap, lteCellMap);
		}
		return mwExceptionMessage;
	}

	/*
	 * @param mwXdr
	 * 
	 * @param ueMroSourceList:uemr\u96c6\u5408
	 * 
	 * @param cellXdrMap:cellXdrMap \u5b58\u50a8\u683c\u5f0f key:cellid value:time+","+kpiavg
	 */
	public MwExceptionMessage volteNoConnAnalyLteMroSou(MwNew mwXdr, List<LteMroSourceNew> ueMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap, Map<String, String> lteCellMap) {
		List<LteMroSourceNew> ueMrKpi1List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMrKpi6List = new ArrayList<LteMroSourceNew>();
		if (ueMroSourceList.size() > 0) {
			for (LteMroSourceNew ueMroSource : ueMroSourceList) {
				if (ueMroSource.getLtemrosource().getMrtime() >= mwXdr.getMwXdr().getProcedurestarttime() - 20000
						&& ueMroSource.getLtemrosource().getMrtime() <= mwXdr.getMwXdr().getProcedurestarttime() + 1000
						&& ueMroSource.getLtemrosource().getCellid().equals(mwXdr.getMwXdr().getSource_eci())) {
					// kpi1\u4e0d\u4e3a\u7a7a
					if (ueMroSource.getLtemrosource().getKpi1() != 0) {
						ueMrKpi1List.add(ueMroSource);
						// kpi6\u4e0d\u4e3a\u7a7a
					} else if (ueMroSource.getLtemrosource().getKpi6() != 0) {
						ueMrKpi6List.add(ueMroSource);
					}
				}
			}
			// KPI1\u4e0d\u4e3a\u7a7a\u7684UE_MR,\u6700\u5c0fKPI1\uff08\u4e3b\u5c0f\u533aRSRP\uff09<31\uff0c\u8fd4\u56deO5
			if (ueMrKpi1List.size() > 0) {
				if (getMinKpi(mwXdr, ueMrKpi1List).getLtemrosource().getKpi1() < 31) {
					return new MwExceptionMessage(mwXdr, "1", InterfaceConstants.UU, exceptionMap, lteCellMap);
				}
			}
			Long kpiAvg = getAvgKpi(mwXdr, cellXdrMap);
			if (kpiAvg > 111) {
				return new MwExceptionMessage(mwXdr, "2", InterfaceConstants.UU, exceptionMap, lteCellMap);
			}
			// UE_MR,kpi6kpi6\u4e0d\u4e3a\u7a7a
			if (ueMrKpi6List.size() > 0) {
				// \u53d6\u6700\u8fd1UE_MR
				LteMroSourceNew ueMroSource = getMinKpi(mwXdr, ueMrKpi6List);
				if (ueMroSource.getLtemrosource().getKpi6() < 25) {
					// kpi8\uff08UL SINR\uff09>=22,\u8fd4\u56deO7
					if (ueMroSource.getLtemrosource().getKpi8() >= 22) {
						return new MwExceptionMessage(mwXdr, "3", InterfaceConstants.UU, exceptionMap, lteCellMap);
					} else {
						// kpi8\uff08UL SINR\uff09<22,\u8fd4\u56deO8
						return new MwExceptionMessage(mwXdr, "4", InterfaceConstants.UU, exceptionMap, lteCellMap);
					}
				} else {
					// kpi6>=25,\u8fd4\u56deO3
					return new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.NAS, exceptionMap,
							lteCellMap);
				}
			}
		
		}else {
			return new MwExceptionMessage(mwXdr, "2006", InterfaceConstants.S1AP, exceptionMap, lteCellMap);
		}
		return mwExceptionMessage;
	}
	
	@Deprecated
	public MwExceptionMessage volteNoConnAnalyLteMroSou(MwNew mwXdr, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> exceptionMap, Map<String, String> lteCellMap) {
		List<LteMroSourceNew> ueMrKpi1List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMrKpi6List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> cellMrList = new ArrayList<LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			// starttime-20s\uff0cstarttime+1s ,lteMroSource.cellid=mw.cellid
			if (lteMroSource.getLtemrosource().getMrtime() >= mwXdr.getMwXdr().getProcedurestarttime() - 20000
					&& lteMroSource.getLtemrosource().getMrtime() <= mwXdr.getMwXdr().getProcedurestarttime() + 1000
					&& lteMroSource.getLtemrosource().getCellid().equals(mwXdr.getMwXdr().getSource_eci())) {
				// ue_mr
				if (TablesConstants.TABLE_UE_MR_XDR.equals(lteMroSource.getLtemrosource().getMrname()) && lteMroSource.getLtemrosource().getVid() == 1) {
					// kpi1\u4e0d\u4e3a\u7a7a
					if (lteMroSource.getLtemrosource().getKpi1() != 0) {
						ueMrKpi1List.add(lteMroSource);
						// kpi6\u4e0d\u4e3a\u7a7a
					} else if (lteMroSource.getLtemrosource().getKpi6() != 0) {
						ueMrKpi6List.add(lteMroSource);
					}
					// cell_mr
				} else if (TablesConstants.TABLE_CELL_MR_XDR.equals(lteMroSource.getLtemrosource().getMrname())) {
					cellMrList.add(lteMroSource);
				}
			}
		}
		// KPI1\u4e0d\u4e3a\u7a7a\u7684UE_MR,\u6700\u5c0fKPI1\uff08\u4e3b\u5c0f\u533aRSRP\uff09<31\uff0c\u8fd4\u56deO5
		if (ueMrKpi1List.size() > 0) {
			if (getMinKpi(mwXdr, ueMrKpi1List).getLtemrosource().getKpi1() < 31) {
				return new MwExceptionMessage(mwXdr, "1", InterfaceConstants.UU, exceptionMap, lteCellMap);
			}
		}
		// \u6700\u8fd1CELL_MR,avg(KPI1~KPI10)>111,\u8fd4\u56deO6
		if (cellMrList.size() > 0) {
			// \u53d6\u6700\u8fd1CELL_MR
			LteMroSourceNew cellMrSource = getMinKpi(mwXdr, cellMrList);
			Double sum = cellMrSource.getLtemrosource().getKpi1() + cellMrSource.getLtemrosource().getKpi2() + cellMrSource.getLtemrosource().getKpi3() + cellMrSource.getLtemrosource().getKpi4()
					+ cellMrSource.getLtemrosource().getKpi5() + cellMrSource.getLtemrosource().getKpi6() + cellMrSource.getLtemrosource().getKpi7() + cellMrSource.getLtemrosource().getKpi8()
					+ cellMrSource.getLtemrosource().getKpi9() + cellMrSource.getLtemrosource().getKpi10();
			Double avg = (double) (sum / 10);
			if (avg > 111) {
				return new MwExceptionMessage(mwXdr, "2", InterfaceConstants.UU, exceptionMap, lteCellMap);
			}
		}
		// UE_MR,kpi6kpi6\u4e0d\u4e3a\u7a7a
		if (ueMrKpi6List.size() > 0) {
			// \u53d6\u6700\u8fd1UE_MR
			LteMroSourceNew ueMroSource = getMinKpi(mwXdr, ueMrKpi6List);

			if (ueMroSource.getLtemrosource().getKpi6() < 25) {
				// kpi8\uff08UL SINR\uff09>=22,\u8fd4\u56deO7
				if (ueMroSource.getLtemrosource().getKpi8() >= 22) {
					return new MwExceptionMessage(mwXdr, "3", InterfaceConstants.UU, exceptionMap, lteCellMap);
				} else {
					// kpi8\uff08UL SINR\uff09<22,\u8fd4\u56deO8
					return new MwExceptionMessage(mwXdr, "4", InterfaceConstants.UU, exceptionMap, lteCellMap);
				}
			} else {
				// kpi6>=25,\u8fd4\u56deO3
				return new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.NAS, exceptionMap,
						lteCellMap);
			}
		}
		return mwExceptionMessage;
	}

	public LteMroSourceNew getMinKpi(MwNew mwXdr, List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			curTime = Math.abs(mwXdr.getMwXdr().getProcedurestarttime() - lteMroSource.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			LteMroSourceMap.put(minTime, lteMroSource);
		}
		return LteMroSourceMap.get(minTime);
	}

	// cellXdrMap key:cellid value:time+","+kpiavg
	@SuppressWarnings("rawtypes")
	public Long getAvgKpi(MwNew mwXdr, Map<String, String> cellXdrMap) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Iterator iter = cellXdrMap.entrySet().iterator();
		Long kpiAvg = 0L;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getKey().equals(mwXdr.getMwXdr().getSource_eci())) {
				curTime = Math
						.abs(mwXdr.getMwXdr().getProcedurestarttime() - Long.valueOf(entry.getValue().toString().split(",")[0]));
				if (minTime > curTime) {
					minTime = curTime;
					kpiAvg = Long.valueOf(entry.getValue().toString().split(",")[1]);
				}
			}
		}
		return kpiAvg;
	}
}
