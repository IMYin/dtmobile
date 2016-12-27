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
import cn.com.dtmobile.hadoop.constants.TablesConstants;

public class IMSRegistFailAnalyService {
	MwExceptionMessage mwExceptionMessage = null;

	public List<MwExceptionMessage> imsRegistFailAnalyService(
			List<MwNew> mwXdrList, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		List<MwExceptionMessage> mwExceptionMessageList = new ArrayList<MwExceptionMessage>();
		List<MwNew> iscList = new ArrayList<MwNew>();
		if (mwXdrList.size() > 0) {

			for (MwNew mwXdr : mwXdrList) {
				if (mwXdr.getMwXdr().getInterfaces() == 18) {
					iscList.add(mwXdr);
				}
			}
			// enter the interface 14
			for (MwNew mwXdr : mwXdrList) {
				if (mwXdr.getMwXdr().getInterfaces() == 14
						&& ExceptionConstnts.IMS_REG_FAIL == mwXdr.getEtype()) {
					// step 1
					mwExceptionMessage = this.stepOne(mwXdr, iscList,
							lteMroSourceList, cellXdrMap, exceptionMap,
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

	public MwExceptionMessage stepOne(MwNew mwXdr, List<MwNew> iscList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		if ("401".equals(mwXdr.getMwXdr().getResponse_code())) {
			return new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(),
					InterfaceConstants.SIP, exceptionMap, lteCellMap);
		} else {
			// step 2
			if ("408".equals(mwXdr.getMwXdr().getResponse_code())
					|| "480".equals(mwXdr.getMwXdr().getResponse_code())
					|| "500".equals(mwXdr.getMwXdr().getResponse_code())
					|| "503".equals(mwXdr.getMwXdr().getResponse_code())
					|| "487".equals(mwXdr.getMwXdr().getResponse_code())) {
				// step 3
				this.stepThree(mwXdr, iscList, lteMroSourceList, cellXdrMap,
						exceptionMap, lteCellMap);
			} else {
				return new MwExceptionMessage(mwXdr, mwXdr.getMwXdr().getResponse_code(),
						InterfaceConstants.SIP, exceptionMap, lteCellMap);
			}
		}
		return mwExceptionMessage;
	}

	public MwExceptionMessage stepThree(MwNew mwXdr, List<MwNew> iscList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		for (MwNew isc : iscList) {
			if (isc.getMwXdr().getInterfaces() == 18
					&& isc.getMwXdr().getProcedurestarttime() >= mwXdr.getMwXdr()
							.getProcedurestarttime() - 1000
					&& isc.getMwXdr().getProcedurestarttime() <= mwXdr.getMwXdr()
							.getProcedurestarttime() + 5000) {
				if (!"0".equals(isc.getMwXdr().getProcedurestatus())) {
					return new MwExceptionMessage(mwXdr,
							mwXdr.getMwXdr().getResponse_code(), InterfaceConstants.SIP,
							exceptionMap, lteCellMap);
				} else {
					// step 4
					this.stepFour(mwXdr, lteMroSourceList, cellXdrMap,
							exceptionMap, lteCellMap);
				}
			}
		}
		return mwExceptionMessage;
	}

	public MwExceptionMessage stepFour(MwNew mwXdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap,
			Map<String, String> lteCellMap) {
		List<LteMroSourceNew> ueMr1List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMr6List = new ArrayList<LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			if (lteMroSource.getLtemrosource().getMrtime() >= mwXdr.getMwXdr().getProcedurestarttime() - 20000
					&& lteMroSource.getLtemrosource().getMrtime() <= mwXdr.getMwXdr()
							.getProcedurestarttime() + 1000
					&& lteMroSource.getLtemrosource().getCellid().equals(mwXdr.getMwXdr().getSource_eci())
					&& lteMroSource.getLtemrosource().getVid() == 1) {
				if (TablesConstants.TABLE_UE_MR_XDR_FLAG.equals(lteMroSource.getLtemrosource()
						.getMrname())) {
					if (lteMroSource.getLtemrosource().getKpi1() != 0) {
						ueMr1List.add(lteMroSource);
					} else if (lteMroSource.getLtemrosource().getKpi6() != 0) {
						ueMr6List.add(lteMroSource);
					}
				}
			}
		}

		if (ueMr1List.size() > 0
				&& this.getMinKpi(mwXdr, ueMr1List).getLtemrosource().getKpi1() < 31) {
			return new MwExceptionMessage(mwXdr, "1", InterfaceConstants.UU,
					exceptionMap, lteCellMap);
		} else {
			double avg = this.getAvgKpi(mwXdr, cellXdrMap);
			if (avg > 111) {
				return new MwExceptionMessage(mwXdr, "2",
						InterfaceConstants.UU, exceptionMap, lteCellMap);
			} else {
				if (ueMr6List.size() > 0) {
					LteMroSourceNew ue6MrSource = this.getMinKpi(mwXdr, ueMr6List);
					if (ue6MrSource.getLtemrosource().getKpi6() < 25) {
						if (ue6MrSource.getLtemrosource().getKpi8() >= 22) {
							return new MwExceptionMessage(mwXdr, "3",
									InterfaceConstants.UU, exceptionMap,
									lteCellMap);
						} else if (ue6MrSource.getLtemrosource().getKpi8() < 22) {
							return new MwExceptionMessage(mwXdr, "4",
									InterfaceConstants.UU, exceptionMap,
									lteCellMap);
						}
					} else {
						return new MwExceptionMessage(mwXdr,
								mwXdr.getMwXdr().getResponse_code(),
								InterfaceConstants.SIP, exceptionMap,
								lteCellMap);
					}
				}
			}
		}

		return mwExceptionMessage;
	}

	public LteMroSourceNew getMinKpi(MwNew mwXdr,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = Long.MAX_VALUE;
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
	public Double getAvgKpi(MwNew mwXdr, Map<String, String> cellXdrMap) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Iterator iter = cellXdrMap.entrySet().iterator();
		Double kpiAvg = 0.0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getKey().equals(mwXdr.getMwXdr().getSource_eci())) {
				curTime = Math
						.abs(mwXdr.getMwXdr().getProcedurestarttime()
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
