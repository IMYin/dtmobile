package cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LocGuserMark;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.ProcessNetCell;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.VolteBusiNew;
import cn.com.dtmobile.hadoop.biz.train.service.highspeeduser.VolteBusiNewService;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteTrainUuReduce extends Reducer<Text, Text, NullWritable, Text> {
	private MultipleOutputs<NullWritable, Text> mos;
	VolteBusiNew volteBusiNew = null;
	Long confSpeed = 0L;
	Text value = new Text();
	// 公参表t_profess_net_cell key:cell_id value:lat,lon(经度，纬度)
	Map<String, String> professNetCellMap = new HashMap<String, String>();
	Map<String, String> publicNetCellMap = new HashMap<String, String>();
	//起点到切换点距离公参表
	Map<String, Double> switchFpMap = new HashMap<String, Double>();
	//key:CELLID,upordown,targetcellid  value NLONG,NLAT,DEFSPEED
	Map<String, String> htSwitChfpMap = new HashMap<String, String>();
	
	ProcessNetCell processNetCell = null;

	/*
	 * 公参表t_profess_net_cell
	 * 以cellid为key,取rrunum最大的经纬度、rrnum最小的纬度和seqnum，以_分隔,经度之间以逗号分隔 如 cellid
	 * 最小lat，Lon_最大lat，Lon，seqnum cellid,最大经纬度_最小经纬度_seqnum
	 */
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		mos = new MultipleOutputs<NullWritable, Text>(context);
		// 获得设置速度（从参数传入）
		Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		//专网
		BufferedReader in = new BufferedReader(new FileReader(paths[0].toString()));
		String line = null;
		String minlatLon = "";
		String maxlatLon = "";
		String seqnm = "";
		// 相同cellid，所有经纬度
		Map<String, List<ProcessNetCell>> map = new HashMap<String, List<ProcessNetCell>>();
		List<ProcessNetCell> processNetCellList = null;
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			processNetCell = new ProcessNetCell(values);
			if (!map.containsKey(processNetCell.getCellid())) {
				processNetCellList = new ArrayList<ProcessNetCell>();
				processNetCellList.add(processNetCell);
			} else {
				processNetCellList = map.get(processNetCell.getCellid());
				processNetCellList.add(processNetCell);
			}
			map.put(processNetCell.getCellid(), processNetCellList);
		}
		in.close();
		for (Map.Entry<String, List<ProcessNetCell>> entry : map.entrySet()) {
			
			List<ProcessNetCell> tempList=entry.getValue();
			Collections.sort(tempList, new Comparator<ProcessNetCell>() {

				@Override
				public int compare(ProcessNetCell o1, ProcessNetCell o2) {
					// TODO Auto-generated method stub
					return o1.getRrunum()-o2.getRrunum();
				}
			});
			seqnm=tempList.get(0).getSeqnum();
			minlatLon = tempList.get(0).getLat() + StringUtils.DELIMITER_INNER_ITEM + tempList.get(0).getLon();
			maxlatLon=tempList.get(tempList.size()-1).getLat() + StringUtils.DELIMITER_INNER_ITEM + tempList.get(tempList.size()-1).getLon();
			professNetCellMap.put(entry.getKey(), minlatLon + StringUtils.DELIMITER_INNER_ITEM1 + maxlatLon
					+ StringUtils.DELIMITER_INNER_ITEM1 + seqnm);
		}
		//公网
		in = new BufferedReader(new FileReader(paths[1].toString()));
		line = null;
		// publicNetCell cellid
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			if(StringUtils.isNotEmpty(values[3]) && StringUtils.isNotEmpty(values[1])){
				publicNetCellMap.put(values[3], values[6] + StringUtils.DELIMITER_INNER_ITEM1 + values[7]);
			}					
		}						
		in.close();
		//ht_switch
		in = new BufferedReader(new FileReader(paths[2].toString()));
		line = null;
		// 初始化公参表htSwitChfpMap
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			if(StringUtils.isNotEmpty(values[0]) && StringUtils.isNotEmpty(values[1])){
				htSwitChfpMap.put(values[1] + StringUtils.DELIMITER_INNER_ITEM + values[0] + StringUtils.DELIMITER_INNER_ITEM + values[2], values[5] + StringUtils.DELIMITER_INNER_ITEM + values[6]);
			}
		}
		in.close();
		//起点到切换点的距离Map ht_sw_distance
		in = new BufferedReader(new FileReader(paths[3].toString()));
		line = null;
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			if(StringUtils.isNotEmpty(values[0]) && StringUtils.isNotEmpty(values[1]) && StringUtils.isNotEmpty(values[2])){
				switchFpMap.put(values[1] +StringUtils.DELIMITER_INNER_ITEM+ values[2] +StringUtils.DELIMITER_INNER_ITEM+  values[0] , Double.valueOf(values[5]));
			}
		}
		in.close();
		confSpeed = Long.valueOf(context.getConfiguration().get("confSpeed"));
	}

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		VolteBusiNewService volteBusiNewService = new VolteBusiNewService();
		Iterator<Text> iter = values.iterator();
		// 存放高铁用户识别所需数据
		List<VolteBusiNew> volteBusiNewTrainAnalyList = new ArrayList<VolteBusiNew>();
		// 全量数据
		List<VolteBusiNew> volteBusiNewList = new ArrayList<VolteBusiNew>();
		

		while (iter.hasNext()) {
			volteBusiNew = new VolteBusiNew(iter.next().toString().split(StringUtils.DELIMITER_INNER_ITEM));
			// 取存放高铁用户识别所需数据(小区cellid和targetcellid在t_profess_net_cell中存在，procedurestatus=0)
			if (professNetCellMap.containsKey(volteBusiNew.getUuXdr().getCellId())
					&& professNetCellMap.containsKey(volteBusiNew.getUuXdr().getTargetCellId())
					&& "0".equals(volteBusiNew.getUuXdr().getProcedureStatus())&&(volteBusiNew.getUuXdr().getProcedureType().equals("7")||volteBusiNew.getUuXdr().getProcedureType().equals("8"))) {
				String seqnum=professNetCellMap.get(volteBusiNew.getUuXdr().getCellId()).split(StringUtils.DELIMITER_INNER_ITEM1)[2];
				volteBusiNew.setSeqnum(Integer.parseInt(seqnum));
				volteBusiNewTrainAnalyList
						.add(volteBusiNew);
			}
			if ((professNetCellMap.containsKey(volteBusiNew.getUuXdr().getCellId())||publicNetCellMap.containsKey(volteBusiNew.getUuXdr().getCellId()))
					&&(volteBusiNew.getUuXdr().getProcedureType().equals("7")||volteBusiNew.getUuXdr().getProcedureType().equals("8"))) {
				//临时存放全量用户数据，如果是高铁用记，则返回
				volteBusiNewList.add(volteBusiNew);
			}
		}
		Collections.sort(volteBusiNewTrainAnalyList,new Comparator<VolteBusiNew>() {

			@Override
			public int compare(VolteBusiNew o1, VolteBusiNew o2) {
				// TODO Auto-generated method stub
				return o1.getUuXdr().getProcedureStartTime()-o2.getUuXdr().getProcedureStartTime()>0?1:-1;
			}
		});
		
		VolteBusiNew volteBusiuserdata= volteBusiNewService.getVolteBusiNewService(volteBusiNewTrainAnalyList,
				professNetCellMap, publicNetCellMap, confSpeed);
		
		//如果是高铁用户Z
		if (volteBusiuserdata != null) {
			
			Collections.sort(volteBusiNewList,new Comparator<VolteBusiNew>() {

				@Override
				public int compare(VolteBusiNew o1, VolteBusiNew o2) {
					// TODO Auto-generated method stub
					return o1.getUuXdr().getProcedureStartTime()-o2.getUuXdr().getProcedureStartTime()>0?1:-1;
				}
			});
			List<LocGuserMark> locGuserMarkList = new ArrayList<LocGuserMark>();
			LocGuserMark  locGuserMark = new LocGuserMark();
			
			value.set(volteBusiuserdata.toString());
			mos.write(NullWritable.get(), value, "VOLTE_GT_BUSI_USER");
			
			String distanceKey;
			for (VolteBusiNew volteBusiNew : volteBusiNewList) {
				volteBusiNew.setDir(volteBusiuserdata.getDir());
				String volteKey = volteBusiNew.getUuXdr().getCellId()+StringUtils.DELIMITER_INNER_ITEM+volteBusiNew.getDir()+StringUtils.DELIMITER_INNER_ITEM+volteBusiNew.getUuXdr().getTargetCellId();
				if(htSwitChfpMap.containsKey(volteKey)){
              //NLONG,NLAT,DEFSPEED
					locGuserMark = new LocGuserMark(volteBusiNew.getUuXdr().getCommXdr().getImsi(), Double.parseDouble(htSwitChfpMap.get(volteKey).split(",")[0]), Double.parseDouble(htSwitChfpMap.get(volteKey).split(",")[1]), volteBusiNew.getRangetime(), volteBusiNew.getDir(), volteBusiNew.getUuXdr().getProcedureStartTime());
					distanceKey = volteBusiNew.getUuXdr().getCellId() +StringUtils.DELIMITER_INNER_ITEM+ volteBusiNew.getUuXdr().getTargetCellId() +StringUtils.DELIMITER_INNER_ITEM+ locGuserMark.getDir_State();
					//cellid+targetcellid+上下行 取 distance
					if(switchFpMap.containsKey(distanceKey)){
						locGuserMark.setSwitchfpDistance(switchFpMap.get(distanceKey));
					}
					locGuserMarkList.add(locGuserMark);
				}
			}
			List<LocGuserMark> gusermarkList=volteBusiNewService.getLocGuserMarkList(locGuserMarkList);
			for (LocGuserMark lg : gusermarkList) {
				value.set(lg.toString());
				mos.write(NullWritable.get(), value, "loc_guser_mark");
			}
		} else {
			// 普通用户
			value.set(volteBusiNew.toString());
			mos.write(NullWritable.get(), value, "COMM_USER_DATA");
		}
	}

	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		mos.close();
	}
}
