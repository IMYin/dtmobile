package cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.ProcessNetCell;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.VolteFreeNew;
import cn.com.dtmobile.hadoop.biz.train.service.highspeeduser.VolteBusiNewService;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class VolteTrainS1mmeReduce extends Reducer<Text, Text, NullWritable, Text> {
	private MultipleOutputs<NullWritable, Text> mos;
	VolteFreeNew volteFree = null;
	Long confSpeed = 0L;
	Text value = new Text();
	Map<String, String> professNetCellMap = new HashMap<String, String>();
	Map<String, String> publicNetCellMap = new HashMap<String, String>();
	
	Set<String> busiImsiSet=new HashSet<String>();
	Set<String> tacSet=new HashSet<String>(); 
	
	ProcessNetCell processNetCell = null;

	/*
	 * \u516c\u53c2\u8868t_profess_net_cell
	 * \u4ee5cellid\u4e3akey,\u53d6rrunum\u6700\u5927\u7684\u7ecf\u7eac\u5ea6\u3001rrnum\u6700\u5c0f\u7684\u7eac\u5ea6\u548cseqnum\uff0c\u4ee5_\u5206\u9694,\u7ecf\u5ea6\u4e4b\u95f4\u4ee5\u9017\u53f7\u5206\u9694 \u5982 cellid
	 * \u6700\u5c0flat\uff0cLon_\u6700\u5927lat\uff0cLon\uff0cseqnum cellid,\u6700\u5927\u7ecf\u7eac\u5ea6_\u6700\u5c0f\u7ecf\u7eac\u5ea6_seqnum
	 */
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		mos = new MultipleOutputs<NullWritable, Text>(context);
		// \u83b7\u5f97\u8bbe\u7f6e\u901f\u5ea6\uff08\u4ece\u53c2\u6570\u4f20\u5165\uff09
		Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		confSpeed = Long.valueOf(context.getConfiguration().get("confSpeed"));
		//×¨Íø
		BufferedReader in = new BufferedReader(new FileReader(paths[0].toString()));
		String line = null;
		String minlatLon = "";
		String maxlatLon = "";
		String seqnm = "";
		// \u76f8\u540ccellid\uff0c\u6240\u6709\u7ecf\u7eac\u5ea6
		Map<String, List<ProcessNetCell>> map = new HashMap<String, List<ProcessNetCell>>();
		List<ProcessNetCell> processNetCellList = null;
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			processNetCell = new ProcessNetCell(values);
			tacSet.add(processNetCell.getTac());
			if (!map.containsKey(processNetCell.getCellid())) {
				processNetCellList = new ArrayList<ProcessNetCell>();
				processNetCellList.add(processNetCell);
			} else {
				processNetCellList = map.get(processNetCell.getCellid());
				processNetCellList.add(processNetCell);
			}
			map.put(processNetCell.getCellid(), processNetCellList);
		}
		
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
		in.close();
		//¹«Íø
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
		in = new BufferedReader(new FileReader(paths[2].toString()));
		line = null;
		// publicNetCell cellid
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
				String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
				busiImsiSet.add(values[0]);
		}
		in.close();
	}

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		VolteBusiNewService volteBusiNewService = new VolteBusiNewService();
		Iterator<Text> iter = values.iterator();
		// \u5b58\u653e\u9ad8\u94c1\u7528\u6237\u8bc6\u522b\u6240\u9700\u6570\u636e
		List<VolteFreeNew> volteFreeNewTrainAnalyList = new ArrayList<VolteFreeNew>();

		while (iter.hasNext()) {
			volteFree = new VolteFreeNew(iter.next().toString().split(StringUtils.DELIMITER_INNER_ITEM));
			// \u53d6\u5b58\u653e\u9ad8\u94c1\u7528\u6237\u8bc6\u522b\u6240\u9700\u6570\u636e(\u5c0f\u533acellid\u548ctargetcellid\u5728t_profess_net_cell\u4e2d\u5b58\u5728\uff0cprocedurestatus=0)
			if (tacSet.contains(volteFree.getS1mmeXdr().getTac())&&professNetCellMap.containsKey(volteFree.getS1mmeXdr().getCellId())&&!busiImsiSet.contains(volteFree.getS1mmeXdr().getCommXdr().getImsi())
					&& "0".equals(volteFree.getS1mmeXdr().getProcedureStatus())&&(volteFree.getS1mmeXdr().getProcedureType().equals("5"))) {
				String seqnum=professNetCellMap.get(volteFree.getS1mmeXdr().getCellId()).split(StringUtils.DELIMITER_INNER_ITEM1)[2];
				volteFree.setSeqnum(Integer.parseInt(seqnum));
				volteFreeNewTrainAnalyList.add(volteFree);
			}
		}
		Collections.sort(volteFreeNewTrainAnalyList,new Comparator<VolteFreeNew>() {

			@Override
			public int compare(VolteFreeNew o1, VolteFreeNew o2) {
				// TODO Auto-generated method stub
				return o1.getS1mmeXdr().getProcedureStartTime()-o2.getS1mmeXdr().getProcedureStartTime()>0?1:-1;
			}
		});
		VolteFreeNew volteFreeuserdata= volteBusiNewService.getVolteFreeNewService(volteFreeNewTrainAnalyList,
				professNetCellMap, publicNetCellMap, confSpeed);
		//\u5982\u679c\u662f\u9ad8\u94c1\u7528\u6237Z
		if (volteFreeuserdata != null) {
			value.set(volteFreeuserdata.toString());
			mos.write(NullWritable.get(), value, "VOLTE_GT_FREE_USER");
		}else if(!busiImsiSet.contains(volteFree.getS1mmeXdr().getCommXdr().getImsi())){
			// \u666e\u901a\u7528\u6237
			value.set(volteFree.toString());
			mos.write(NullWritable.get(), value, "COMM_USER_DATA");			
		}
	}

	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		mos.close();
	}
}
