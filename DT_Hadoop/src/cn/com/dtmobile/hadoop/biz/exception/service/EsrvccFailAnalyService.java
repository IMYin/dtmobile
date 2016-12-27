package cn.com.dtmobile.hadoop.biz.exception.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.ExceptionConstnts;
import cn.com.dtmobile.hadoop.biz.exception.constants.InterfaceConstants;
import cn.com.dtmobile.hadoop.biz.exception.model.S1mmeExceptionMessage;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.SvNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;

public class EsrvccFailAnalyService {
	S1mmeExceptionMessage s1mmeExceptionMessage = null;

	public List<S1mmeExceptionMessage> esrvccFailAnalyService(List<SvNew> svList,
			List<UuXdrNew> uuXdrList, List<S1mmeXdrNew> s1mmeXdrList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> exceptionMap) {
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList = new ArrayList<S1mmeExceptionMessage>();
		// enter the interface 13
		if (s1mmeXdrList.size() > 0) {

			for (S1mmeXdrNew s1Xdr : s1mmeXdrList) {
				if (s1Xdr.getS1mmeXdr().getCommXdr().getInterfaces() == 5
						&& ExceptionConstnts.ESRVCC_FAIL == s1Xdr.getEtype()) {
					// step 1
					s1mmeExceptionMessage = this.stepOne(s1Xdr,
							lteMroSourceList, uuXdrList, svList, exceptionMap);
					if (s1mmeExceptionMessage != null) {
						s1mmeExceptionMessageList.add(s1mmeExceptionMessage);
						continue;
					}
				} 
			}
		}
		return s1mmeExceptionMessageList;
	}

	// step 1
	public S1mmeExceptionMessage stepOne(S1mmeXdrNew s1Xdr,
			List<LteMroSourceNew> lteMroSourceList, List<UuXdrNew> uuList,
			List<SvNew> svList, Map<String, String> exceptionMap) {
		if (svList != null) {
			for (SvNew svXdr : svList) {
				if (svXdr.getSv().getProcedureStartTime() >= s1Xdr.getS1mmeXdr()
						.getProcedureStartTime()
						&& svXdr.getSv().getProcedureEndTime() <= s1Xdr.getS1mmeXdr()
								.getProcedureEndTime()) {
					if (svXdr.getSv().getCommXdr().getInterfaces() == 19
							&& "1".equals(svXdr.getSv().getProcedureType())) {
						if (!"0".equals(svXdr.getSv().getResult())) {
							return new S1mmeExceptionMessage(s1Xdr,
									s1Xdr.getS1mmeXdr().getFailureCause(),
									InterfaceConstants.SV, exceptionMap);
						} else {
							this.stepTwo(uuList, lteMroSourceList, s1Xdr,
									exceptionMap);
						}
					}
				}
			}
		} else {
			return new S1mmeExceptionMessage(s1Xdr, s1Xdr.getS1mmeXdr().getFailureCause(),
					InterfaceConstants.S1AP, exceptionMap);
		}
		return s1mmeExceptionMessage;
	}

	public S1mmeExceptionMessage stepTwo(List<UuXdrNew> uuList,
			List<LteMroSourceNew> lteMroSourceList, S1mmeXdrNew s1Xdr,
			Map<String, String> exceptionMap) {
		if (uuList != null) {
			for (UuXdrNew uuXdr : uuList) {
				if (uuXdr.getUuXdr().getProcedureStartTime() <= s1Xdr.getS1mmeXdr()
						.getProcedureStartTime() - 1000
						&& uuXdr.getUuXdr().getProcedureEndTime() >= s1Xdr.getS1mmeXdr()
								.getProcedureEndTime() + 1000) {
					if (uuXdr.getUuXdr().getCommXdr().getInterfaces() == 1
							&& "10".equals(uuXdr.getUuXdr().getProcedureType())) {
						// step 2
						if ("0".equals(uuXdr.getUuXdr().getProcedureStatus())) {
							return new S1mmeExceptionMessage(s1Xdr,
									s1Xdr.getS1mmeXdr().getFailureCause(),
									InterfaceConstants.S1AP, exceptionMap);
						} else {
							// step 3
							if ("6".equals(s1Xdr.getS1mmeXdr().getRequestCause())) {
								return new S1mmeExceptionMessage(s1Xdr, "13",
										InterfaceConstants.UU, exceptionMap);
							}
							// step 4
							else if ("8".equals(s1Xdr.getS1mmeXdr().getRequestCause())) {
								// step 5
								this.stepFive(s1Xdr, lteMroSourceList,
										exceptionMap);
							} else {
								// step 6
								return new S1mmeExceptionMessage(s1Xdr,
										s1Xdr.getS1mmeXdr().getFailureCause(),
										InterfaceConstants.S1AP, exceptionMap);
							}
						}
					}
				}
			}
		} else {
			return new S1mmeExceptionMessage(s1Xdr, s1Xdr.getS1mmeXdr().getFailureCause(),
					InterfaceConstants.S1AP, exceptionMap);
		}
		return s1mmeExceptionMessage;
	}

	public S1mmeExceptionMessage stepFive(S1mmeXdrNew s1Xdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> exceptionMap) {
		List<LteMroSourceNew> ueMrList = new ArrayList<LteMroSourceNew>();
		if (lteMroSourceList != null) {
			for (LteMroSourceNew lteMroSource : lteMroSourceList) {
				if (lteMroSource.getLtemrosource().getVid() == 1
						&& lteMroSource.getLtemrosource().getMrtime() >= s1Xdr.getS1mmeXdr()
								.getProcedureStartTime() - 20000
						&& lteMroSource.getLtemrosource().getMrtime() <= s1Xdr.getS1mmeXdr()
								.getProcedureStartTime() + 1000) {
					if (TablesConstants.TABLE_UE_MR_XDR_FLAG
							.equals(lteMroSource.getLtemrosource().getMrname())) {
						ueMrList.add(lteMroSource);
					}
				}
			}
			for (LteMroSourceNew ueMr : ueMrList) {
				if (ueMr != null) {
					LteMroSourceNew miniMr = this.getMinKpi(s1Xdr, ueMrList);
					if (miniMr.getLtemrosource().getKpi1() < 31) {
						return new S1mmeExceptionMessage(s1Xdr, "1",
								InterfaceConstants.UU, exceptionMap);
					} else {
						return new S1mmeExceptionMessage(s1Xdr,
								s1Xdr.getS1mmeXdr().getFailureCause(),
								InterfaceConstants.S1AP, exceptionMap);
					}
				}
			}
		} else {
			return new S1mmeExceptionMessage(s1Xdr, s1Xdr.getS1mmeXdr().getFailureCause(),
					InterfaceConstants.S1AP, exceptionMap);
		}
		return s1mmeExceptionMessage;
	}

	public LteMroSourceNew getMinKpi(S1mmeXdrNew s1Xdr,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;
		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			curTime = Math.abs(s1Xdr.getS1mmeXdr().getProcedureStartTime()
					- lteMroSource.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
			}
			LteMroSourceMap.put(minTime, lteMroSource);
		}
		return LteMroSourceMap.get(minTime);
	}
}
