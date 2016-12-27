package cn.com.dtmobile.hadoop.biz.exception.service;

import java.util.ArrayList;
import java.util.Collections;
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
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.DateUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;
import cn.com.dtmobile.hadoop.util.UuComparator;

public class VolteExceptionCalledAnalyService {

	/**
	 * VOLTE\u672a\u63a5\u901a\u5206\u6790\u6d41\u7a0b\uff08\u88ab\u53eb\uff09 zhangchao
	 * 
	 * @return
	 * @version 2016-11-11
	 */
	MwExceptionMessage mwExceptionMessage = null;
	LteMroSourceNew miniKpi = null;

	public List<MwExceptionMessage> volteNoConnCalledService(
			List<MwNew> mwXdrList, List<S1mmeXdrNew> s1mmeXdrList,
			List<UuXdrNew> uuXdrList, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		List<MwExceptionMessage> mwExceptionMessageList = new ArrayList<MwExceptionMessage>();
		MwExceptionMessage mwExceptionMessage = null;
		List<MwNew> iscMgXdrList = new ArrayList<MwNew>();
		if (mwXdrList.size() > 0) {
			for (MwNew mwXdr : mwXdrList) {
				if (15 == mwXdr.getMwXdr().getInterfaces()
						|| 18 == mwXdr.getMwXdr().getInterfaces()) {
					iscMgXdrList.add(mwXdr);
				}
				
			}
			for (MwNew mwXdr : mwXdrList) {
				if ("14".equals(mwXdr.getMwXdr().getInterfaces())
						&& (ExceptionConstnts.VOLET_VOICE_CALL_FAIL == mwXdr
								.getEtype() || ExceptionConstnts.VOLET_VIEDO_CALL_FAIL == mwXdr
								.getEtype() && "1".equals(mwXdr.getMwXdr().getCall_side()))) {
					mwExceptionMessage = this._volteNoConnCalledAnalyOne(mwXdr,
							s1mmeXdrList, uuXdrList, iscMgXdrList,
							lteMroSourceList, cellXdrMap, exceptionMap, lteCellMap);
					if (mwExceptionMessage != null) {
						mwExceptionMessageList.add(mwExceptionMessage);
						continue;
					}
				}else {
					mwExceptionMessageList.add(new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.SIP,
					exceptionMap, lteCellMap));
				}
			}
		}
		return mwExceptionMessageList;
	}

	/**
	 * \u5931\u8d25\u89e3\u6790
	 * 
	 * @param mwXdr
	 *            mw\u63a5\u53e3
	 * @param s1mmeXdrList
	 *            s1mme\u63a5\u53e3
	 * @param uuXdrList
	 *            uu\u63a5\u53e3
	 * @param exceptionMap
	 * @author zhangchao15
	 * @version 2016-11-11
	 * @return
	 */
	private MwExceptionMessage _volteNoConnCalledAnalyOne(MwNew mwXdr,
			List<S1mmeXdrNew> s1mmeXdrList, List<UuXdrNew> uuXdrList,
			List<MwNew> iscMgXdrList, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		MwExceptionMessage mwExceptionMessage = null;
		// \u6b65\u9aa41
		if ("4".equals(mwXdr.getMwXdr().getResponse_code())
				|| "8".equals(mwXdr.getMwXdr().getResponse_code())
				|| "7".equals(mwXdr.getMwXdr().getResponse_code())) {
			if (s1mmeXdrList.size() > 0) {

				for (S1mmeXdrNew s1mmeXdr : s1mmeXdrList) {
					// \u6b65\u9aa42
					if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr()
							.getProcedurestarttime() - 2000
							&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
									.getProcedureendtime() + 2000) {
						if ("13".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
							// \u6b65\u9aa43
							if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr()
									.getProcedurestarttime() - 2000
									&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
											.getProcedureendtime() + 2000) {
								if ("5".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
									mwExceptionMessage = new MwExceptionMessage(
											mwXdr, "6", InterfaceConstants.UU,
											exceptionMap, lteCellMap);
									return mwExceptionMessage;
								} else {
									// \u6b65\u9aa44
									if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr()
											.getProcedurestarttime() - 10000
											&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
													.getProcedureendtime() + 10000) {
										if (uuXdrList != null
												&& uuXdrList.size() > 0) {
											// \u6b65\u9aa45
											this._volteNoConnCalledAnalyFifth(
													uuXdrList, mwXdr,
													s1mmeXdrList,
													lteMroSourceList,
													exceptionMap, lteCellMap);
										} else {
											// \u6b65\u9aa47
											this._volteNoConnCalledAnalySeven(
													mwXdr, iscMgXdrList,
													s1mmeXdrList,
													lteMroSourceList,
													cellXdrMap, exceptionMap,
													lteCellMap);
										}
									}
								}
							}
						} else {
							// \u6b65\u9aa48
							mwExceptionMessage = this
									._volteNoConnCalledAnalyEight(mwXdr,
											iscMgXdrList, s1mmeXdrList,
											lteMroSourceList, cellXdrMap,
											exceptionMap, lteCellMap);
						}
					}
				}
			} else {
				return new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(),
						InterfaceConstants.SIP, exceptionMap, lteCellMap);
			}
		} else {
			// \u6b65\u9aa47
			mwExceptionMessage = this._volteNoConnCalledAnalySeven(mwXdr,
					iscMgXdrList, s1mmeXdrList, lteMroSourceList, cellXdrMap,
					exceptionMap, lteCellMap);
		}
		return mwExceptionMessage;
	}

	private MwExceptionMessage _volteNoConnCalledAnalyFifth(
			List<UuXdrNew> uuXdrList, MwNew mwXdr, List<S1mmeXdrNew> s1mmeXdrList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> exceptionMap, Map<String, String> lteCellMap) {

		UuComparator uc = new UuComparator();
		// \u6309\u65f6\u95f4\u6392\u5e8f\u5347\u5e8f
		Collections.sort(uuXdrList, uc);
		UuXdrNew uxfirst = uuXdrList.get(0);
		UuXdrNew uxlast = uuXdrList.get(uuXdrList.size() - 1);
		List<LteMroSourceNew> ueMrList = new ArrayList<LteMroSourceNew>();
		// \u5224\u65ad\u7b2c\u4e00\u4e2a\u4e0e\u6700\u540e\u4e00\u4e2a\u662f\u5426\u540c\u4e00\u5c0f\u533a \u6216\u662f\u5426\u53ea\u6709\u4e00\u6761\u8bb0\u5f55
		if (uxfirst.getUuXdr().getCellId().equals(uxlast.getUuXdr().getCellId())
				|| uuXdrList.size() == 1) {
			mwExceptionMessage = new MwExceptionMessage(mwXdr, "7",
					InterfaceConstants.UU, exceptionMap, lteCellMap);
			return mwExceptionMessage;
		} else {
			// \u6b65\u9aa46\uff0c \u6b65\u9aa46 \u4e0d\u4f1a\u8d70\u6b65\u9aa47\uff0c\u5fc5\u5b9a\u8fd4\u56de\u5f02\u5e38
			if (lteMroSourceList.size() > 0) {
				for (LteMroSourceNew lteMroSource : lteMroSourceList) {
					if (TablesConstants.TABLE_UE_MR_XDR.equals(lteMroSource.getLtemrosource()
							.getMrname())
							&& lteMroSource.getLtemrosource().getMrtime() >= mwXdr.getMwXdr()
									.getProcedurestarttime() - 10000
							&& lteMroSource.getLtemrosource().getMrtime() <= mwXdr.getMwXdr()
									.getProcedureendtime() + 10000
							&& lteMroSource.getLtemrosource().getCellid().equals(
									mwXdr.getMwXdr().getSource_eci())) {
						ueMrList.add(lteMroSource);
					}
				}
				miniKpi = this.getMinKpi(mwXdr, ueMrList);
				if (miniKpi != null && miniKpi.getLtemrosource().getKpi1() < 21) {
					mwExceptionMessage = new MwExceptionMessage(mwXdr, "8",
							InterfaceConstants.UU, exceptionMap, lteCellMap);
					return mwExceptionMessage;
				} else {
					mwExceptionMessage = new MwExceptionMessage(mwXdr, "9",
							InterfaceConstants.UU, exceptionMap, lteCellMap);
					return mwExceptionMessage;
				}

			} else {
				return new MwExceptionMessage(mwXdr, "9",
						InterfaceConstants.UU, exceptionMap, lteCellMap);
			}
		}
	}

	private MwExceptionMessage _volteNoConnCalledAnalySeven(MwNew mwXdr,
			List<MwNew> iscMgXdrList, List<S1mmeXdrNew> s1mmeXdrList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		if ("408".equals(mwXdr.getMwXdr().getProceduretype())
				|| "480".equals(mwXdr.getMwXdr().getProceduretype())
				|| "500".equals(mwXdr.getMwXdr().getProceduretype())
				|| "503".equals(mwXdr.getMwXdr().getProceduretype())
				|| "504".equals(mwXdr.getMwXdr().getProceduretype())
				|| "580".equals(mwXdr.getMwXdr().getProceduretype())) {
			// \u6b65\u9aa48
			mwExceptionMessage = this._volteNoConnCalledAnalyEight(mwXdr,
					iscMgXdrList, s1mmeXdrList, lteMroSourceList, cellXdrMap,
					exceptionMap, lteCellMap);
		} else {
			mwExceptionMessage = new MwExceptionMessage(mwXdr,
					mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.SIP,
					exceptionMap, lteCellMap);
		}
		return mwExceptionMessage;
	}

	private MwExceptionMessage _volteNoConnCalledAnalyEight(MwNew mwXdr,
			List<MwNew> iscMgXdrList, List<S1mmeXdrNew> s1mmeXdrList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		for (MwNew iscMgXdr : iscMgXdrList) {
			if (iscMgXdr.getMwXdr().getProcedurestarttime() >= mwXdr.getMwXdr()
					.getProcedurestarttime()
					&& iscMgXdr.getMwXdr().getProcedureendtime() <= mwXdr.getMwXdr()
							.getProcedureendtime()) {
				if ("0".equals(iscMgXdr.getMwXdr().getProceduretype())) {
					// \u6b65\u9aa49
					for (S1mmeXdrNew s1mmeXdr : s1mmeXdrList) {
						if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr()
								.getProcedurestarttime()
								&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
										.getProcedureendtime()) {
							if (15 == s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces()
									&& "13".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
								// \u6b65\u9aa410
								if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr()
										.getProcedurestarttime()
										&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
												.getProcedureendtime()) {
									if (15 == s1mmeXdr.getS1mmeXdr().getCommXdr()
											.getInterfaces()
											&& "13".equals(s1mmeXdr.getS1mmeXdr()
													.getProcedureType())
											&& "0".equals(s1mmeXdr.getS1mmeXdr()
													.getProcedureStatus())) {
										// \u6b65\u9aa411
										mwExceptionMessage = this
												._volteNoConnCalledAnalyEleven(
														mwXdr, iscMgXdrList,
														s1mmeXdr,
														lteMroSourceList,
														cellXdrMap,
														exceptionMap,
														lteCellMap);
									} else {
										return new MwExceptionMessage(mwXdr,
												mwXdr.getMwXdr().getResponse_code(),
												InterfaceConstants.NAS,
												exceptionMap, lteCellMap);
									}
								}
							} else {
								mwExceptionMessage = this
										._volteNoConnCalledAnalyEleven(mwXdr,
												iscMgXdrList, s1mmeXdr,
												lteMroSourceList, cellXdrMap,
												exceptionMap, lteCellMap);
							}
						}
					}
				} else {
					return new MwExceptionMessage(mwXdr,
							mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.SIP,
							exceptionMap, lteCellMap);
				}
			}
		}
		return mwExceptionMessage;
	}

	private MwExceptionMessage _volteNoConnCalledAnalyEleven(MwNew mwXdr,
			List<MwNew> iscMgXdrList, S1mmeXdrNew s1mmeXdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		// \u6b65\u9aa411
		if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr().getProcedurestarttime()
				&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
						.getProcedureendtime()) {
			if (15 == s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces()
					&& "21".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
				// \u6b65\u9aa412
				this._volteNoConnCalledAnalyTweleve(mwXdr, iscMgXdrList,
						s1mmeXdr, lteMroSourceList, cellXdrMap, exceptionMap,
						lteCellMap);
			} else {
				if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr()
						.getProcedurestarttime()
						&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
								.getProcedureendtime()) {
					if (s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces() != 5
							&& "13".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
						return new MwExceptionMessage(mwXdr, "2006",
								InterfaceConstants.S1AP, exceptionMap,
								lteCellMap);
					} else if (s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces() == 5
							&& "13".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
						return new MwExceptionMessage(mwXdr, "2005",
								InterfaceConstants.S1AP, exceptionMap,
								lteCellMap);
					} else {
						// \u6b65\u9aa412
						this._volteNoConnCalledAnalyTweleve(mwXdr,
								iscMgXdrList, s1mmeXdr, lteMroSourceList,
								cellXdrMap, exceptionMap, lteCellMap);
					}
				}
			}
		}
		return mwExceptionMessage;
	}

	private MwExceptionMessage _volteNoConnCalledAnalyTweleve(MwNew mwXdr,
			List<MwNew> iscMgXdrList, S1mmeXdrNew s1mmeXdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		List<LteMroSourceNew> ueMr1List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMr6List = new ArrayList<LteMroSourceNew>();
		if (s1mmeXdr.getS1mmeXdr().getProcedureStartTime() >= mwXdr.getMwXdr().getProcedurestarttime()
				&& s1mmeXdr.getS1mmeXdr().getProcedureEndTime() <= mwXdr.getMwXdr()
						.getProcedureendtime()) {
			if (5 == s1mmeXdr.getS1mmeXdr().getCommXdr().getInterfaces()
					&& "21".equals(s1mmeXdr.getS1mmeXdr().getProcedureType())) {
				String bear1reqcaus = s1mmeXdr.getS1mmeXdr().getBearerArr()[0]
						.split(StringUtils.DELIMITER_INNER_ITEM)[4];
				if ("3".equals(bear1reqcaus) || "6".equals(bear1reqcaus)
						|| "21".equals(bear1reqcaus)
						|| "26".equals(bear1reqcaus)) {
					// \u6b65\u9aa413
					for (LteMroSourceNew lteMrSource : lteMroSourceList) {
						if (lteMrSource.getLtemrosource().getMrtime() >= mwXdr.getMwXdr()
								.getProcedurestarttime() - 20000
								&& lteMrSource.getLtemrosource().getMrtime() <= mwXdr.getMwXdr()
										.getProcedureendtime() + 1000
								&& lteMrSource.getLtemrosource().getCellid().equals(
										mwXdr.getMwXdr().getSource_eci())
								&& lteMrSource.getLtemrosource().getVid() == 1) {
							if (TablesConstants.TABLE_UE_MR_XDR
									.equals(lteMrSource.getLtemrosource().getMrname())) {
								if (lteMrSource.getLtemrosource().getKpi1() != 0) {
									ueMr1List.add(lteMrSource);
								} else if (lteMrSource.getLtemrosource().getKpi6() != 0) {
									ueMr6List.add(lteMrSource);
								}
							}
						}
					}
					if (ueMr1List.size() > 0
							&& this.getMinKpi(mwXdr, ueMr1List).getLtemrosource().getKpi1() < 31) {
						return new MwExceptionMessage(mwXdr, "1",
								InterfaceConstants.UU, exceptionMap, lteCellMap);
					}

					long avg = this.getAvgKpi(mwXdr, cellXdrMap);
					if (avg > 111) {
						return new MwExceptionMessage(mwXdr, "2",
								InterfaceConstants.UU, exceptionMap, lteCellMap);
					}
					if (ueMr6List.size() > 0) {
						LteMroSourceNew ue6MrSource = this.getMinKpi(mwXdr,
								ueMr6List);
						if (ue6MrSource.getLtemrosource().getKpi1() != 0) {
							return new MwExceptionMessage(mwXdr, "1",
									InterfaceConstants.UU, exceptionMap,
									lteCellMap);
						}
						if (ue6MrSource.getLtemrosource().getKpi6() < 25) {
							if (ue6MrSource.getLtemrosource().getKpi8() >= 22) {
								return new MwExceptionMessage(mwXdr, "3",
										InterfaceConstants.UU, exceptionMap,
										lteCellMap);
							} else {
								return new MwExceptionMessage(mwXdr, "4",
										InterfaceConstants.UU, exceptionMap,
										lteCellMap);
							}
						}
					}
				} else {
					return new MwExceptionMessage(mwXdr,
							mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.S1AP,
							exceptionMap, lteCellMap);
				}
			}
		}
		return mwExceptionMessage;
	}

	public LteMroSourceNew getMinKpi(MwNew mwXdr,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = 10000000L;
		Long curTime = 0L;
		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			curTime = Math.abs(mwXdr.getMwXdr().getProcedurestarttime()
					- lteMroSource.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			LteMroSourceMap.put(minTime, lteMroSource);
		}
		return LteMroSourceMap.get(minTime);
	}

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
						.abs(mwXdr.getMwXdr().getProcedurestarttime()
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