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
import cn.com.dtmobile.hadoop.util.DateUtils;

public class AttachFailAnalyService {
	S1mmeExceptionMessage s1mmeExceptionMessage = null;

	public List<S1mmeExceptionMessage> attachFailAnalyService(
			List<S1mmeXdrNew> s1XdrList, List<UuXdrNew> uuXdrList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList = new ArrayList<S1mmeExceptionMessage>();
		// Enter the interface 13
		if (s1XdrList.size() > 0) {

			for (S1mmeXdrNew s1Xdr : s1XdrList) {
				if (s1Xdr.getS1mmeXdr().getCommXdr().getInterfaces() == 5) {
					// step 1
					s1mmeExceptionMessage = this.stepOne(s1Xdr,
							lteMroSourceList, cellXdrMap, uuXdrList,
							exceptionMap);
					if (s1mmeExceptionMessage != null) {
						s1mmeExceptionMessageList.add(s1mmeExceptionMessage);
						continue;
					}
				} 
			}
		}
		return s1mmeExceptionMessageList;
	}

	public S1mmeExceptionMessage stepOne(S1mmeXdrNew s1Xdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, List<UuXdrNew> uuList,
			Map<String, String> exceptionMap) {
		if (ExceptionConstnts.ATTACH_FAIL == s1Xdr.getEtype()) {
			if ("1".equals(s1Xdr.getS1mmeXdr().getProcedureStatus())) {
				return new S1mmeExceptionMessage(s1Xdr,
						s1Xdr.getS1mmeXdr().getFailureCause(), InterfaceConstants.NAS,
						exceptionMap);
			} else {
				// step 2
				int times = this.times(s1Xdr, uuList, exceptionMap);
				if (times > 3) {
					return new S1mmeExceptionMessage(s1Xdr, "65535",
							InterfaceConstants.S1AP, exceptionMap);
				} else {
					// step 3
					this.stepThree(s1Xdr, lteMroSourceList, cellXdrMap,
							exceptionMap);
				}
			}
		}
		return s1mmeExceptionMessage;
	}

	public int times(S1mmeXdrNew s1Xdr, List<UuXdrNew> uuList,
			Map<String, String> exceptionMap) {
		int times = 0;
		for (UuXdrNew uuXdr : uuList) {
			if (uuXdr.getUuXdr().getProcedureStartTime() >= s1Xdr.getS1mmeXdr().getProcedureStartTime() - 1000
					&& uuXdr.getUuXdr().getProcedureStartTime() <= s1Xdr.getS1mmeXdr()
							.getProcedureStartTime() + 5000) {
				if ("13".equals(uuXdr.getUuXdr().getProcedureType())
						&& uuXdr.getUuXdr().getCellId().equals(s1Xdr.getS1mmeXdr().getCellId())) {
					times++;
				}
			}
		}
		return times;
	}

	public S1mmeExceptionMessage stepThree(S1mmeXdrNew s1Xdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> cellXdrMap, Map<String, String> exceptionMap) {
		List<LteMroSourceNew> ueMr1List = new ArrayList<LteMroSourceNew>();
		List<LteMroSourceNew> ueMr6List = new ArrayList<LteMroSourceNew>();
		if (lteMroSourceList != null) {
			for (LteMroSourceNew LteMroSourceNew : lteMroSourceList) {
				if (LteMroSourceNew.getLtemrosource().getMrtime() >= s1Xdr.getS1mmeXdr()
						.getProcedureStartTime() - 20000
						&& LteMroSourceNew.getLtemrosource().getMrtime() <= s1Xdr.getS1mmeXdr()
								.getProcedureStartTime() + 1000
						&& LteMroSourceNew.getLtemrosource().getCellid().equals(s1Xdr.getS1mmeXdr().getOtherEci())
						&& LteMroSourceNew.getLtemrosource().getVid() == 1) {
					if (TablesConstants.TABLE_UE_MR_XDR_FLAG
							.equals(LteMroSourceNew.getLtemrosource().getMrname())) {
						if (LteMroSourceNew.getLtemrosource().getKpi1() != 0) {
							ueMr1List.add(LteMroSourceNew);
						} else if (LteMroSourceNew.getLtemrosource().getKpi6() != 0) {
							ueMr6List.add(LteMroSourceNew);
						}
					}
				}
			}

			if (ueMr1List.size() > 0
					&& this.getMinKpi(s1Xdr, ueMr1List).getLtemrosource().getKpi1() < 31) {
				return new S1mmeExceptionMessage(s1Xdr, "1",
						InterfaceConstants.UU, exceptionMap);
			} else {
				double avg = this.getAvgKpi(s1Xdr, cellXdrMap);
				if (avg > 111) {
					return new S1mmeExceptionMessage(s1Xdr, "2",
							InterfaceConstants.UU, exceptionMap);
				} else {
					if (ueMr6List.size() > 0) {
						LteMroSourceNew ue6MrSource = this.getMinKpi(s1Xdr,
								ueMr6List);
						if (ue6MrSource.getLtemrosource().getKpi6() < 25) {
							if (ue6MrSource.getLtemrosource().getKpi8() >= 22) {
								return new S1mmeExceptionMessage(s1Xdr, "3",
										InterfaceConstants.UU, exceptionMap);
							} else if (ue6MrSource.getLtemrosource().getKpi8() < 22) {
								return new S1mmeExceptionMessage(s1Xdr, "4",
										InterfaceConstants.UU, exceptionMap);
							}
						} else {
							return new S1mmeExceptionMessage(s1Xdr,
									s1Xdr.getS1mmeXdr().getFailureCause(),
									InterfaceConstants.NAS, exceptionMap);
						}
					}
				}
			}
		} else {
			return new S1mmeExceptionMessage(s1Xdr, s1Xdr.getS1mmeXdr().getFailureCause(),
					InterfaceConstants.NAS, exceptionMap);
		}
		return s1mmeExceptionMessage;
	}

	public LteMroSourceNew getMinKpi(S1mmeXdrNew s1Xdr,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		for (LteMroSourceNew LteMroSourceNew : lteMroSourceList) {
			curTime = Math.abs(s1Xdr.getS1mmeXdr().getProcedureStartTime()
					- LteMroSourceNew.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			LteMroSourceMap.put(minTime, LteMroSourceNew);
		}
		return LteMroSourceMap.get(minTime);
	}

	@SuppressWarnings("rawtypes")
	public Double getAvgKpi(S1mmeXdrNew s1Xdr, Map<String, String> cellXdrMap) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Iterator iter = cellXdrMap.entrySet().iterator();
		Double kpiAvg = 0.0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getKey().equals(s1Xdr.getS1mmeXdr().getOtherEci())) {
				curTime = Math
						.abs(s1Xdr.getS1mmeXdr().getProcedureStartTime()
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
