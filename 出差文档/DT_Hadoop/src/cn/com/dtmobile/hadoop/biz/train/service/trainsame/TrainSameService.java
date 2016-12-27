package cn.com.dtmobile.hadoop.biz.train.service.trainsame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.dtmobile.hadoop.biz.train.model.trainsame.TrainSame;
import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class TrainSameService {
	
	/*
	 * @param:trainSameList 同一切换点（相同cellid和targetCellid)用户
	 * 
	 * @param:confRang 相差10秒
	 * 
	 * @param:step 5秒为一STEP
	 */
	public Object[] getU1TrainSameList(List<TrainSame> trainSameList, Long confRang, Long step,Long imsiRange ,int sn) {
		List<TrainSame> tmpList = null;
		Map<String, List<TrainSame>> trainSameMap = new HashMap<String, List<TrainSame>>();
		Map<String, List<TrainSame>> exceptionMap = new HashMap<String, List<TrainSame>>();
		Object[] retObj = new Object[3];
		String key = "";
		TrainSame trainSame = null;
		Long beginTime = trainSameList.get(0).getProcedureStartTime();
		Long maxTime = beginTime + confRang;
		int k = 0;
		int i = 0;
		int j = 0;
		if(sn == 0){
			sn = 1;
		}
		for (i = k; i < trainSameList.size(); i++) {
			trainSame = trainSameList.get(i);
			if (trainSame.getProcedureStartTime() - beginTime >= step) {
				// 找到下一个step
				j = i;
			}

			if(trainSame.getProcedureStartTime() >= beginTime && trainSame.getProcedureStartTime() <= maxTime) {
				key = "U1" + StringUtils.DELIMITER_INNER_ITEM1 + sn;
				tmpList = trainSameMap.get(key);
				if (tmpList == null || tmpList.size() == 0) {
					tmpList = new ArrayList<TrainSame>();
				}
				trainSame.setSn(sn);
				trainSame.setGroupName("U1"+ StringUtils.DELIMITER_INNER_ITEM1 +sn);
				tmpList.add(trainSame);
				trainSameMap.put(key, tmpList);
			} else {
				sn++;
				k = j;
				beginTime = trainSame.getProcedureStartTime() + step;
				maxTime = beginTime + confRang;
				key = "U1" + StringUtils.DELIMITER_INNER_ITEM1 + sn;
				tmpList = trainSameMap.get(key);
				if (tmpList == null || tmpList.size() == 0) {
					tmpList = new ArrayList<TrainSame>();
				}
				trainSame.setSn(sn);
				trainSame.setGroupName("U1"+ StringUtils.DELIMITER_INNER_ITEM1 +sn);
				tmpList.add(trainSame);
				trainSameMap.put(key, tmpList);
				//如果imsi小于指定个数   默认8个  则不分组 视为异常
//				if(tmpList.size() < imsiRange){
//					trainSameMap.remove(key);
//					exceptionMap.put(key, trainSameList);
//				}
			}
			
		}
		retObj[0] = trainSameMap;
		retObj[1] = exceptionMap;
		retObj[2] = sn;
		return retObj;
	}


	/**
	 * 得到U1 imsi 的交集关系 并且归属到新的U1组 需要相邻的imsi
	 * @param groupNameList
	 * @return
	 */
	public Map<String, String>  getImsiMapping(List<String> groupNameList , int seq,Map<String, String>  imsiMapping){
		StringBuffer sb = new StringBuffer();
		if(imsiMapping!=null && imsiMapping.containsKey(groupNameList.get(0))){
			//same group
			return imsiMapping;
		}
		if(groupNameList.size() == 1){
			imsiMapping.put(groupNameList.get(0), "U2" + StringUtils.DELIMITER_INNER_ITEM1 + seq);
			return imsiMapping;
		}	
		
		for (int i = 0; i < groupNameList.size(); i++) {
			int isNeighSelf = ParseUtils.parseInteger(groupNameList.get(i).substring(3,groupNameList.get(i).length()));
			int isNeighNext = ParseUtils.parseInteger(groupNameList.get(i+1).substring(3,groupNameList.get(i+1).length()));
			//判断是否相邻
			if(isNeighNext - isNeighSelf == 1 || isNeighSelf - isNeighNext == 1){
				sb.append(groupNameList.get(i) + StringUtils.DELIMITER_INNER_ITEM5 + groupNameList.get(i+1));
				if(i+1<groupNameList.size()-1){
					i = _findNextNeigh(groupNameList, i+1, sb);
				}
			}
			imsiMapping.put(sb.toString(), "U2" + StringUtils.DELIMITER_INNER_ITEM1 + seq);
		}
		return imsiMapping;
	}
	/**
	 * 得到U2 imsi 的交集关系 并且归属到新的U3组
	 * @param groupNameList
	 * @return
	 */
	public Object[]  getImsiMappingU2(List<String> groupNameList,int seq,Map<String, String> imsiMappingU3){
		Object [] obj = new Object[2];
		String self = null;
		String next = null;
//		if(imsiMappingU3!=null && imsiMappingU3.containsKey(trainSameList.get(0))){
//			//same group
//			return imsiMappingU3;
//		}
		if(groupNameList.size() == 1){
			imsiMappingU3.put(groupNameList.get(0), "U3" + StringUtils.DELIMITER_INNER_ITEM1 + seq);
			seq++;
			obj[0] = imsiMappingU3;
			obj[1] = seq;
			return obj;
		}	
		for (int i = 0; i < groupNameList.size(); i++) {
			StringBuffer sb = new StringBuffer();
			self = groupNameList.get(i);
			
			if(i < groupNameList.size() - 1){
				next = groupNameList.get(i+1);
			}
			
			if(self != null && next != null){
				sb.append(self +StringUtils.DELIMITER_INNER_ITEM5 + next);
			}else{
				i = _findU2Next(groupNameList, i+1, sb);
			}
			imsiMappingU3.put(sb.toString(), "U3"+StringUtils.DELIMITER_INNER_ITEM1+seq);
			seq++;
		}
		obj[0] = imsiMappingU3;
		obj[1] = seq;
		return obj;
	}
	/**
	 * 寻找相邻的imsi
	 * @param groupNameList
	 * @param num
	 * @param sb
	 * @return
	 */
	private int _findNextNeigh(List<String> groupNameList , int num , StringBuffer sb){
		String  trainSameSelf = groupNameList.get(num);
		int self = ParseUtils.parseInteger(trainSameSelf.substring(3,trainSameSelf.length()));
		num++;
		String trainSameNext = groupNameList.get(num);
		int next = ParseUtils.parseInteger(trainSameNext.substring(3,trainSameNext.length()));
		if(self - next == 1 || next - self == 1){
			sb.append(StringUtils.DELIMITER_INNER_ITEM1);
			sb.append(trainSameSelf + StringUtils.DELIMITER_INNER_ITEM1 + trainSameNext);
			_findNextNeigh(groupNameList, num, sb);
		}
		return num;
	}
	/**
	 * 得到U3 imsi 的交集关系 并且归属到新的U4组
	 * @param groupNameList
	 * @return
	 */
	public  Object[]  getImsiMappingU3(List<String> groupNameList,int seq,Map<String, String> imsiMappingU3){
		Object [] obj = new Object[2];
		if(groupNameList.size() == 1){
			imsiMappingU3.put(groupNameList.get(0), "U4" + StringUtils.DELIMITER_INNER_ITEM1 + seq);
			seq++;
			obj[0] = imsiMappingU3;
			obj[1] = seq;
			return obj;
		}	
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < groupNameList.size(); i++) {
			if(i < groupNameList.size()-1){
				String self = groupNameList.get(i);
				String next = groupNameList.get(i = i+1);
				if(self != null && next != null){
					sb.append(self +StringUtils.DELIMITER_INNER_ITEM5 + next);
					this._findU3Next(groupNameList, i+1, sb);
				}
				imsiMappingU3.put(sb.toString(), "U4" + StringUtils.DELIMITER_INNER_ITEM1 + seq);
				seq++;
				break;
			}
		}
		obj[0] = imsiMappingU3;
		obj[1] = seq;
		return obj;
	}
	
	private int _findU3Next(List<String> groupNameList , int num , StringBuffer sb){
		if(num == groupNameList.size()-1 && groupNameList.size() %2 != 0){
			sb.append(StringUtils.DELIMITER_INNER_ITEM5);
			sb.append(groupNameList.get(num));
			return num;
		}
		if(num == 26){
			System.out.println(123);
		}
		String  self = groupNameList.get(num);
		num++;
		String next = groupNameList.get(num);
		if(self !=  null && next != null){
			sb.append(StringUtils.DELIMITER_INNER_ITEM5);
			sb.append(self + StringUtils.DELIMITER_INNER_ITEM5 + next);
			if(num == groupNameList.size() - 1){
				return num;
			}
			_findU3Next(groupNameList, num+1, sb);
		}
		return num;
	}
	
	private int _findU2Next(List<String> groupNameList , int num , StringBuffer sb){
		String  self = groupNameList.get(num);
		num++;
		String next = groupNameList.get(num);
		if(next != null){
			sb.append(self + StringUtils.DELIMITER_INNER_ITEM1 + next);
		}else{
			_findU2Next(groupNameList, num, sb);
		}
		return num;
	}
}
