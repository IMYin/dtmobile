package cn.com.dtmobile.hadoop.biz.exception.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.exception.constants.ExceptionConstnts;
import cn.com.dtmobile.hadoop.biz.exception.constants.InterfaceConstants;
import cn.com.dtmobile.hadoop.biz.exception.model.S1mmeExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.model.UuExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.model.X2ExceptionMessage;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.X2XdrNew;
import cn.com.dtmobile.hadoop.model.LteCellSource;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class Exception4GAnalyService {
	X2ExceptionMessage x2ExceptionMessage = null;
	S1mmeExceptionMessage s1mmeExceptionMessage = null;
	UuExceptionMessage uuExceptionMessage = null;

	public List<X2ExceptionMessage> exception4GAnalyServiceX2(List<X2XdrNew> x2List,
			List<S1mmeXdrNew> s1mmeXdrList, List<UuXdrNew> uuList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		List<X2ExceptionMessage> x2ExceptionMessageList = new ArrayList<X2ExceptionMessage>();
		// enter the interface X2XdrNew
		if (x2List.size() > 0) {

			for (X2XdrNew x2 : x2List) {
				if (x2.getX2Xdr().getCommXdr().getInterfaces() == 2
						&& ExceptionConstnts.FG_SWITCH_FAIL == x2.getEtype()) {
					// step 2
					this.stepTwo(x2, uuList, s1mmeXdrList, lteMroSourceList,
							ltecellMap, cellXdrMap, exceptionMap, t_professMap);
				} else {
					x2ExceptionMessageList.add(new X2ExceptionMessage(x2, "12",
							InterfaceConstants.UU, exceptionMap));
				}
			}
		}
		return x2ExceptionMessageList;
	}

	public List<S1mmeExceptionMessage> exception4GAnalyServiceS1(
			List<S1mmeXdrNew> s1mmeXdrList, List<UuXdrNew> uuList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList = new ArrayList<S1mmeExceptionMessage>();
		// enter the interface S1
		if (s1mmeXdrList.size() > 0) {

			for (S1mmeXdrNew s1Xdr : s1mmeXdrList) {
				if (s1Xdr.getS1mmeXdr().getCommXdr().getInterfaces() == 5
						&& ExceptionConstnts.FG_SWITCH_FAIL == s1Xdr.getEtype()) {
					// step 5
					this.stepFive(s1Xdr, uuList, lteMroSourceList, ltecellMap,
							cellXdrMap, exceptionMap, t_professMap);
				} else {
					s1mmeExceptionMessageList.add(new S1mmeExceptionMessage(
							s1Xdr, "12", InterfaceConstants.UU, exceptionMap));
				}
			}
		}
		return s1mmeExceptionMessageList;
	}

	public List<UuExceptionMessage> exception4GAnalyServiceUu(List<X2XdrNew> x2List,
			List<S1mmeXdrNew> s1mmeXdrList, List<UuXdrNew> uuList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		List<UuExceptionMessage> uuExceptionMessageList = new ArrayList<UuExceptionMessage>();
		if (uuList.size() > 0) {

			for (UuXdrNew uuXdr : uuList) {
				if (uuXdr.getUuXdr().getCommXdr().getInterfaces() == 1
						&& ExceptionConstnts.FG_SWITCH_FAIL == uuXdr.getEtype()) {
					// step 6
					this.stepSixUu(uuXdr, lteMroSourceList, ltecellMap,
							cellXdrMap, exceptionMap, t_professMap);
				} else {
					uuExceptionMessageList.add(new UuExceptionMessage(uuXdr,
							"12", InterfaceConstants.UU, exceptionMap));
				}
			}
		}
		return uuExceptionMessageList;
	}

	public X2ExceptionMessage stepTwo(X2XdrNew x2, List<UuXdrNew> uuList,
			List<S1mmeXdrNew> s1mmeXdrList, List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		if (uuList != null) {
			for (UuXdrNew uuXdr : uuList) {
				if (uuXdr.getUuXdr().getCommXdr().getInterfaces() == 1
						&& uuXdr.getUuXdr().getProcedureStartTime() >= x2.getX2Xdr()
								.getProcedureStartTime()
						&& uuXdr.getUuXdr().getProcedureEndTime() <= x2.getX2Xdr()
								.getProcedureEndTime()) {
					if ("0".equals(uuXdr.getUuXdr().getProcedureStatus())) {
						// step 3
						if (s1mmeXdrList != null) {
							for (S1mmeXdrNew s1Xdr : s1mmeXdrList) {
								if (s1Xdr.getS1mmeXdr().getCommXdr().getInterfaces() == 5
										&& s1Xdr.getS1mmeXdr().getProcedureStartTime() >= x2.getX2Xdr()
												.getProcedureStartTime()
										&& s1Xdr.getS1mmeXdr().getProcedureEndTime() <= x2.getX2Xdr()
												.getProcedureEndTime()
										&& "14".equals(s1Xdr.getS1mmeXdr()
												.getProcedureStatus())
										&& s1Xdr.getS1mmeXdr().getCellId().equals(
												x2.getX2Xdr().getTargetCellId())) {
									if ("0".equals(s1Xdr.getS1mmeXdr().getProcedureStatus())) {
										// step 4
										for (S1mmeXdrNew s1Xdr2 : s1mmeXdrList) {
											if (s1Xdr2.getS1mmeXdr().getCommXdr()
													.getInterfaces() == 5
													&& s1Xdr2.getS1mmeXdr()
															.getProcedureStartTime() >= x2.getX2Xdr()
															.getProcedureStartTime() - 1000
													&& s1Xdr2.getS1mmeXdr()
															.getProcedureStartTime() <= x2.getX2Xdr()
															.getProcedureStartTime() + 5000
													&& "14".equals(s1Xdr2.getS1mmeXdr()
															.getProcedureStatus())) {
												if ("1".equals(s1Xdr2.getS1mmeXdr()
														.getProcedureStatus())) {
													return new X2ExceptionMessage(
															x2,
															x2.getX2Xdr().getFailureCause(),
															InterfaceConstants.S1AP,
															exceptionMap);
												} else {
													return new X2ExceptionMessage(
															x2,
															"2004",
															InterfaceConstants.S1AP,
															exceptionMap);
												}
											}
										}
									} else {
										return new X2ExceptionMessage(x2,
												"1001", InterfaceConstants.X2,
												exceptionMap);
									}
								}
							}
						} else {
							return new X2ExceptionMessage(x2, "12",
									InterfaceConstants.UU, exceptionMap);
						}
					} else {
						// step 6
						this.stepSixX2(x2, lteMroSourceList, ltecellMap,
								cellXdrMap, exceptionMap, t_professMap);
					}
				}
			}
		} else {
			return new X2ExceptionMessage(x2, "12", InterfaceConstants.UU,
					exceptionMap);
		}
		return x2ExceptionMessage;
	}

	public S1mmeExceptionMessage stepFive(S1mmeXdrNew s1Xdr, List<UuXdrNew> uuList,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		if (uuList != null) {
			for (UuXdrNew uuXdr : uuList) {
				if (uuXdr.getUuXdr().getCommXdr().getInterfaces() == 1
						&& uuXdr.getUuXdr().getProcedureStartTime() >= s1Xdr.getS1mmeXdr()
								.getProcedureStartTime() - 1000
						&& uuXdr.getUuXdr().getProcedureEndTime() <= s1Xdr.getS1mmeXdr()
								.getProcedureEndTime() + 1000
						&& "8".equals(uuXdr.getUuXdr().getProcedureStatus())
						&& s1Xdr.getS1mmeXdr().getCellId().equals(uuXdr.getUuXdr().getTargetCellId())) {
					if ("0".equals(uuXdr.getUuXdr().getProcedureStatus())) {
						return new S1mmeExceptionMessage(s1Xdr,
								s1Xdr.getS1mmeXdr().getFailureCause(),
								InterfaceConstants.S1AP, exceptionMap);
					} else {
						// step 6
						this.stepSixS1(s1Xdr, lteMroSourceList, ltecellMap,
								cellXdrMap, exceptionMap, t_professMap);
					}
				}
			}
		} else {
			return new S1mmeExceptionMessage(s1Xdr, "12",
					InterfaceConstants.UU, exceptionMap);
		}
		return s1mmeExceptionMessage;
	}

	public X2ExceptionMessage stepSixX2() {

		return x2ExceptionMessage;
	}

	@SuppressWarnings("rawtypes")
	public UuExceptionMessage stepSixUu(UuXdrNew uuXdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		if (lteMroSourceList != null) {
			for (LteMroSourceNew lteMroSource : lteMroSourceList) {
				if (lteMroSource.getLtemrosource().getMrtime() <= uuXdr.getUuXdr()
						.getProcedureStartTime() - 20000
						&& lteMroSource.getLtemrosource().getMrtime() >= uuXdr.getUuXdr()
								.getProcedureStartTime() + 1000
						&& lteMroSource.getLtemrosource().getCellid().equals(uuXdr.getUuXdr().getCellId())
						&& lteMroSource.getLtemrosource().getKpi1() != 0
						&& "A3".equals(lteMroSource.getLtemrosource().getEventtype())) {
					LteMroSourceNew miniueMr = this.getMinKpi(
							uuXdr.getUuXdr().getProcedureStartTime(), lteMroSourceList);
					Iterator iter = ltecellMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						if (miniueMr.getLtemrosource().getKpi12().equals(entry.getKey())
								&& miniueMr.getLtemrosource().getCellid().equals(entry.getKey())) {
							// TODO
							// 根据目标小区ECI在工参里面查询
							// step 8
							Exception4GUtils step8 = new Exception4GUtils();
							String status = step8.stepEightUu(uuXdr,
									lteMroSourceList, ltecellMap, cellXdrMap,
									exceptionMap);
							if ("O3".equals(status)) {
								return new UuExceptionMessage(uuXdr, "1",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O10".equals(status)) {
								return new UuExceptionMessage(uuXdr, "15",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O5".equals(status)) {
								return new UuExceptionMessage(uuXdr, "3",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O6".equals(status)) {
								return new UuExceptionMessage(uuXdr, "4",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O8".equals(status)) {
								return new UuExceptionMessage(uuXdr, "12",
										InterfaceConstants.UU, exceptionMap);
							}
						} else {
							// step 7 hold on
							String[] words = t_professMap.get(entry.getValue())
									.split(StringUtils.DELIMITER_BETWEEN_ITEMS);
							double lat1 = Double.parseDouble(words[0]);
							double lon1 = Double.parseDouble(words[1]);
							double lat2 = miniueMr.getLtemrosource().getGridcenterlatitude();
							double lon2 = miniueMr.getLtemrosource().getGridcenterlongitude();
							double dist = this.cell_distance(lat1, lon1, lat2,
									lon2);
							if (dist < 6) {
								return new UuExceptionMessage(uuXdr, "11",
										InterfaceConstants.UU, exceptionMap);
							} else {
								// step 8
								Exception4GUtils step8 = new Exception4GUtils();
								String status = step8.stepEightUu(uuXdr,
										lteMroSourceList, ltecellMap,
										cellXdrMap, exceptionMap);
								if ("O3".equals(status)) {
									return new UuExceptionMessage(uuXdr, "1",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O10".equals(status)) {
									return new UuExceptionMessage(uuXdr, "15",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O5".equals(status)) {
									return new UuExceptionMessage(uuXdr, "3",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O6".equals(status)) {
									return new UuExceptionMessage(uuXdr, "4",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O8".equals(status)) {
									return new UuExceptionMessage(uuXdr, "12",
											InterfaceConstants.UU, exceptionMap);
								}

							}

						}
					}
				}
			}
		} else {
			return new UuExceptionMessage(uuXdr, "12", InterfaceConstants.UU,
					exceptionMap);
		}
		return uuExceptionMessage;
	}

	@SuppressWarnings("rawtypes")
	public S1mmeExceptionMessage stepSixS1(S1mmeXdrNew s1Xdr,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		if (lteMroSourceList != null) {
			for (LteMroSourceNew lteMroSource : lteMroSourceList) {
				if (lteMroSource.getLtemrosource().getMrtime() <= s1Xdr.getS1mmeXdr()
						.getProcedureStartTime() - 20000
						&& lteMroSource.getLtemrosource().getMrtime() >= s1Xdr.getS1mmeXdr()
								.getProcedureStartTime() + 1000
						&& lteMroSource.getLtemrosource().getCellid().equals(s1Xdr.getS1mmeXdr().getCellId())
						&& lteMroSource.getLtemrosource().getKpi1() != 0
						&& "A3".equals(lteMroSource.getLtemrosource().getEventtype())) {
					LteMroSourceNew miniueMr = this.getMinKpi(
							s1Xdr.getS1mmeXdr().getProcedureStartTime(), lteMroSourceList);
					Iterator iter = ltecellMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						if (miniueMr.getLtemrosource().getKpi12().equals(entry.getKey())
								&& miniueMr.getLtemrosource().getCellid().equals(entry.getKey())) {
							// TODO
							// 根据目标小区ECI在工参里面查询
							// )
							// {
							// step 8
							Exception4GUtils step8 = new Exception4GUtils();
							String status = step8.stepEightUu(s1Xdr,
									lteMroSourceList, ltecellMap, cellXdrMap,
									exceptionMap);
							if ("O3".equals(status)) {
								return new S1mmeExceptionMessage(s1Xdr, "1",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O10".equals(status)) {
								return new S1mmeExceptionMessage(s1Xdr, "15",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O5".equals(status)) {
								return new S1mmeExceptionMessage(s1Xdr, "3",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O6".equals(status)) {
								return new S1mmeExceptionMessage(s1Xdr, "4",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O8".equals(status)) {
								return new S1mmeExceptionMessage(s1Xdr, "12",
										InterfaceConstants.UU, exceptionMap);
							}
						} else {
							// step 7 hold on
							String[] words = t_professMap.get(entry.getValue())
									.split(StringUtils.DELIMITER_BETWEEN_ITEMS);
							double lat1 = Double.parseDouble(words[0]);
							double lon1 = Double.parseDouble(words[1]);
							double lat2 = miniueMr.getLtemrosource().getGridcenterlatitude();
							double lon2 = miniueMr.getLtemrosource().getGridcenterlongitude();
							double dist = this.cell_distance(lat1, lon1, lat2,
									lon2);
							if (dist < 6) {
								return new S1mmeExceptionMessage(s1Xdr, "11",
										InterfaceConstants.UU, exceptionMap);
							} else {
								// step 8
								Exception4GUtils step8 = new Exception4GUtils();
								String status = step8.stepEightUu(s1Xdr,
										lteMroSourceList, ltecellMap,
										cellXdrMap, exceptionMap);
								if ("O3".equals(status)) {
									return new S1mmeExceptionMessage(s1Xdr,
											"1", InterfaceConstants.UU,
											exceptionMap);
								} else if ("O10".equals(status)) {
									return new S1mmeExceptionMessage(s1Xdr,
											"15", InterfaceConstants.UU,
											exceptionMap);
								} else if ("O5".equals(status)) {
									return new S1mmeExceptionMessage(s1Xdr,
											"3", InterfaceConstants.UU,
											exceptionMap);
								} else if ("O6".equals(status)) {
									return new S1mmeExceptionMessage(s1Xdr,
											"4", InterfaceConstants.UU,
											exceptionMap);
								} else if ("O8".equals(status)) {
									return new S1mmeExceptionMessage(s1Xdr,
											"12", InterfaceConstants.UU,
											exceptionMap);
								}

							}
						}
					}
				}
			}
		} else {
			return new S1mmeExceptionMessage(s1Xdr, "12",
					InterfaceConstants.UU, exceptionMap);
		}
		return s1mmeExceptionMessage;
	}

	@SuppressWarnings("rawtypes")
	public X2ExceptionMessage stepSixX2(X2XdrNew x2,
			List<LteMroSourceNew> lteMroSourceList,
			Map<String, String> ltecellMap, Map<String, String> cellXdrMap,
			Map<String, String> exceptionMap, Map<String, String> t_professMap) {
		if (lteMroSourceList != null) {
			for (LteMroSourceNew lteMroSource : lteMroSourceList) {
				if (lteMroSource.getLtemrosource().getMrtime() <= x2.getX2Xdr().getProcedureStartTime() - 20000
						&& lteMroSource.getLtemrosource().getMrtime() >= x2.getX2Xdr()
								.getProcedureStartTime() + 1000
						&& lteMroSource.getLtemrosource().getCellid()
								.equals(x2.getX2Xdr().getTargetCellId())
						&& lteMroSource.getLtemrosource().getKpi1() != 0
						&& "A3".equals(lteMroSource.getLtemrosource().getEventtype())) {
					LteMroSourceNew miniueMr = this.getMinKpi(
							x2.getX2Xdr().getProcedureStartTime(), lteMroSourceList);
					Iterator iter = ltecellMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						if (miniueMr.getLtemrosource().getKpi12().equals(entry.getKey())
								&& miniueMr.getLtemrosource().getCellid().equals(entry.getKey())) {
							// step 8
							Exception4GUtils step8 = new Exception4GUtils();
							String status = step8.stepEightUu(x2,
									lteMroSourceList, ltecellMap, cellXdrMap,
									exceptionMap);
							if ("O3".equals(status)) {
								return new X2ExceptionMessage(x2, "1",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O10".equals(status)) {
								return new X2ExceptionMessage(x2, "15",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O5".equals(status)) {
								return new X2ExceptionMessage(x2, "3",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O6".equals(status)) {
								return new X2ExceptionMessage(x2, "4",
										InterfaceConstants.UU, exceptionMap);
							} else if ("O8".equals(status)) {
								return new X2ExceptionMessage(x2, "12",
										InterfaceConstants.UU, exceptionMap);
							}
						} else {
							// step 7 hold on
							String[] words = t_professMap.get(entry.getValue())
									.split(StringUtils.DELIMITER_BETWEEN_ITEMS);
							double lat1 = Double.parseDouble(words[0]);
							double lon1 = Double.parseDouble(words[1]);
							double lat2 = miniueMr.getLtemrosource().getGridcenterlatitude();
							double lon2 = miniueMr.getLtemrosource().getGridcenterlongitude();
							double dist = this.cell_distance(lat1, lon1, lat2,
									lon2);
							if (dist < 6) {
								return new X2ExceptionMessage(x2, "11",
										InterfaceConstants.UU, exceptionMap);
							} else {
								// step 8
								Exception4GUtils step8 = new Exception4GUtils();
								String status = step8.stepEightUu(x2,
										lteMroSourceList, ltecellMap,
										cellXdrMap, exceptionMap);
								if ("O3".equals(status)) {
									return new X2ExceptionMessage(x2, "1",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O10".equals(status)) {
									return new X2ExceptionMessage(x2, "15",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O5".equals(status)) {
									return new X2ExceptionMessage(x2, "3",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O6".equals(status)) {
									return new X2ExceptionMessage(x2, "4",
											InterfaceConstants.UU, exceptionMap);
								} else if ("O8".equals(status)) {
									return new X2ExceptionMessage(x2, "12",
											InterfaceConstants.UU, exceptionMap);
								}
							}
						}
					}
				}
			}
		} else {
			return new X2ExceptionMessage(x2, "12", InterfaceConstants.UU,
					exceptionMap);
		}
		return x2ExceptionMessage;
	}

	public X2ExceptionMessage step7X2(LteCellSource lteCell,
			Map<String, String> t_professMap) {
		return x2ExceptionMessage;
	}

	public LteMroSourceNew getMinKpi(long starttime,
			List<LteMroSourceNew> lteMroSourceList) {
		Long minTime = Long.MAX_VALUE;
		Long curTime = 0L;

		Map<Long, LteMroSourceNew> LteMroSourceMap = new HashMap<Long, LteMroSourceNew>();
		Double curkpi2 = 0.0;
		for (LteMroSourceNew lteMroSource : lteMroSourceList) {
			curTime = Math.abs(starttime - lteMroSource.getLtemrosource().getMrtime());
			if (minTime > curTime) {
				minTime = curTime;
				curkpi2 = lteMroSource.getLtemrosource().getKpi2();
			} else if (minTime == curTime) {
				if (lteMroSource.getLtemrosource().getKpi2() > curkpi2) {
					LteMroSourceMap.put(minTime, lteMroSource);
				}
			}
		}
		return LteMroSourceMap.get(minTime);
	}

	public double cell_distance(double lat1, double lon1, double lat2,
			double lon2) {
		double dist = 0;
		double pi = Math.PI;
		dist = 6371004 * Math.acos(Math.sin(lat1 * pi / 180)
				* Math.sin(lat2 * pi / 180) + Math.cos(lat1 * pi / 180)
				+ Math.cos(lat2 * pi / 180)
				* Math.cos(lon2 * pi / 180 - lon1 * pi / 180));
		return dist;

	}
}
