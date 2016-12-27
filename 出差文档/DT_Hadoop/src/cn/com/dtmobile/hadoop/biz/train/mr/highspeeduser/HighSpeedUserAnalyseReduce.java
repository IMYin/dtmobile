package cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.GxRxNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LocGuserMark;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.MwNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.SgsXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.SvNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.X2XdrNew;
import cn.com.dtmobile.hadoop.biz.train.service.highspeeduser.XdrNewService;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.model.GxXdr;
import cn.com.dtmobile.hadoop.model.LteMroSource;
import cn.com.dtmobile.hadoop.model.MwXdr;
import cn.com.dtmobile.hadoop.model.S1mmeXdr;
import cn.com.dtmobile.hadoop.model.SgsXdr;
import cn.com.dtmobile.hadoop.model.Sv;
import cn.com.dtmobile.hadoop.model.UuXdr;
import cn.com.dtmobile.hadoop.model.X2;
import cn.com.dtmobile.hadoop.util.StringUtils;

@SuppressWarnings("deprecation")
public class HighSpeedUserAnalyseReduce extends Reducer<Text, Text, NullWritable, Text>{

	Map<String,String> professNetCellMap = new HashMap<String,String>();
	Map<String,String> publicNetCellMap = new HashMap<String,String>();
	Map<String,String[]> switchPointMap = new HashMap<String,String[]>();
	Map<String, String> htSwitChfpMap = new HashMap<String, String>();
	Double distanceRange;
	MultipleOutputs<NullWritable, Text> mos;
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		BufferedReader in = new BufferedReader(new FileReader(paths[0].toString()));
		String line = null;
		//公网
		while(StringUtils.isNotEmpty(line = in.readLine())){
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			publicNetCellMap.put(values[3],values[6]+StringUtils.DELIMITER_INNER_ITEM+values[7]);//cellid
		}
		in.close();
		//私网
		in = new BufferedReader(new FileReader(paths[1].toString()));
		line = null;
		while(StringUtils.isNotEmpty(line = in.readLine())){
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			professNetCellMap.put(values[3],values[8]+StringUtils.DELIMITER_INNER_ITEM+values[9]);//cellid
		}
		in.close();
		//切换点  用于回填经纬度
		in = new BufferedReader(new FileReader(paths[2].toString()));
		line = null;
		while(StringUtils.isNotEmpty(line = in.readLine())){
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			switchPointMap.put(values[3], values);
		}
		in.close();
		
		in = new BufferedReader(new FileReader(paths[3].toString()));
		line = null;
		// 初始化公参表htSwitChfpMap
		while (StringUtils.isNotEmpty((line = in.readLine()))) {
			String[] values = line.split(StringUtils.DELIMITER_INNER_ITEM);
			if(StringUtils.isNotEmpty(values[0]) && StringUtils.isNotEmpty(values[1])){
				htSwitChfpMap.put(values[1] + StringUtils.DELIMITER_INNER_ITEM + values[0] + StringUtils.DELIMITER_INNER_ITEM + values[2], values[3]+StringUtils.DELIMITER_INNER_ITEM+values[4]+StringUtils.DELIMITER_INNER_ITEM+values[6]);
			}
		}
		in.close();
		//栅格距离
		distanceRange = Double.valueOf(context.getConfiguration().get("gridLength"));
		mos = new MultipleOutputs<NullWritable, Text>(context);
	}
	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		List<LocGuserMark> locGuserMarkList = new ArrayList<LocGuserMark>();
		List<X2> x2XdrNewList = new ArrayList<X2>();
		List<UuXdr> uUxdrNewList = new ArrayList<UuXdr>();
		List<LteMroSource> lteMroSourceNewList = new ArrayList<LteMroSource>();
		List<MwXdr> mwNewList = new ArrayList<MwXdr>();
		List<Sv> svNewList = new ArrayList<Sv>();
		List<SgsXdr> sgsXdrNewList = new ArrayList<SgsXdr>();
		List<S1mmeXdr> s1mmeXdrNewList = new ArrayList<S1mmeXdr>();
		List<GxXdr> gxRxNewList = new ArrayList<GxXdr>();
		//8种接口数据
		Iterator<Text> iter = values.iterator();
		String tableTag="";
		while(iter.hasNext()){
			String objStr=iter.next().toString();
			String[] valueTemp=objStr.split(StringUtils.DELIMITER_INNER_ITEM);
			tableTag = objStr.split(StringUtils.DELIMITER_INNER_ITEM)[valueTemp.length-1];
			String str=objStr.substring(0, objStr.lastIndexOf(StringUtils.DELIMITER_INNER_ITEM));
			String[] value=str.split(StringUtils.DELIMITER_INNER_ITEM);
			
			if(TablesConstants.TABLE_X2.equals(tableTag)){
				x2XdrNewList.add(new X2(value));
			}else if(TablesConstants.TABLE_UU_XDR.equals(tableTag)){
				uUxdrNewList.add(new UuXdr(value));
			}else if(TablesConstants.TABLE_LTE_MRO_SOU.equals(tableTag)){
				lteMroSourceNewList.add(new LteMroSource(value));
			}else if(TablesConstants.TABLE_LOC_GUSER_MARK.equals(tableTag)){
				locGuserMarkList.add(new LocGuserMark(value));
			}else if(TablesConstants.TABLE_MW_XDR.equals(tableTag)){
				mwNewList.add(new MwXdr(value));
			}else if(TablesConstants.TABLE_SV.equals(tableTag)){
				svNewList.add(new Sv(value));
			}else if(TablesConstants.TABLE_SGS_XDR.equals(tableTag)){
				sgsXdrNewList.add(new SgsXdr(value));
			}else if(TablesConstants.TABLE_S1MME_XDR.equals(tableTag)){
				s1mmeXdrNewList.add(new S1mmeXdr(value));
			}else if(TablesConstants.TABLE_GX.equals(tableTag)){
				gxRxNewList.add(new GxXdr(value));
			}
		}
		try {
		//调用Service，并且write输出
		XdrNewService xdrNewService = new XdrNewService();
		if(x2XdrNewList.size()>0){
			List<X2XdrNew> x2ResultList = null;
			x2ResultList = xdrNewService.analyseX2XdrNew(x2XdrNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange, htSwitChfpMap);
			for (X2XdrNew X2XdrNew : x2ResultList) {
				//context.write(NullWritable.get(), new Text(X2XdrNew.toString()));
				mos.write(NullWritable.get(), new Text(X2XdrNew.toString()),"tb_xdr_ifc_x2_new");
			}
		}
		if(uUxdrNewList.size()>0){
			List<UuXdrNew> uuResultList = xdrNewService.analyseUuXdrNew(uUxdrNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange, htSwitChfpMap);
			for (UuXdrNew uuXdrNew : uuResultList) {
				//context.write(NullWritable.get(), new Text(uuXdrNew.toString()));
				mos.write(NullWritable.get(), new Text(uuXdrNew.toString()),"tb_xdr_ifc_uu_new");
			}
		}
		if(lteMroSourceNewList.size()>0){
			List<LteMroSourceNew> lteMroSourceResultList = null;
			lteMroSourceResultList = xdrNewService.analyseLteMroSourceNew(lteMroSourceNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange);
			for (LteMroSourceNew lteMroSourceNew : lteMroSourceResultList) {
				//context.write(NullWritable.get(), new Text(lteMroSourceNew.toString()));
				mos.write(NullWritable.get(), new Text(lteMroSourceNew.toString()),"lte_mro_source_new");
			}
		}
		if(mwNewList.size()>0){
			List<MwNew> mwNewResultList = null;
			mwNewResultList = xdrNewService.analyseMwNew(mwNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange, htSwitChfpMap);
			for (MwNew mwNew : mwNewResultList) {
				//context.write(NullWritable.get(), new Text(mwNew.toString()));
				mos.write(NullWritable.get(), new Text(mwNew.toString()),"tb_xdr_ifc_mw_new");
			}
		}
		if(sgsXdrNewList.size()>0){
			List<SgsXdrNew> sgsXdrNewResultList = null;
			sgsXdrNewResultList = xdrNewService.analyseSgsXdrNew(sgsXdrNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange, htSwitChfpMap);
			for (SgsXdrNew sgsXdrNew : sgsXdrNewResultList) {
				//context.write(NullWritable.get(), new Text(sgsXdrNew.toString()));
				mos.write(NullWritable.get(), new Text(sgsXdrNew.toString()),"tb_xdr_ifc_sgs_new");
			}
		}
		if(svNewList.size()>0){
			List<SvNew> svNewResultList = null;
			svNewResultList = xdrNewService.analyseSvXdrNew(svNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange);
			for (SvNew svNew : svNewResultList) {
				//context.write(NullWritable.get(), new Text(svNew.toString()));
				mos.write(NullWritable.get(), new Text(svNew.toString()),"tb_xdr_ifc_sv_new");
			}
		}
		if(s1mmeXdrNewList.size()>0){
			List<S1mmeXdrNew> s1mmeXdrNewResultList = null;
			s1mmeXdrNewResultList = xdrNewService.analyseS1mmeXdrNew(s1mmeXdrNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange, htSwitChfpMap);
			for (S1mmeXdrNew s1mmeXdrNew : s1mmeXdrNewResultList) {
				//context.write(NullWritable.get(), new Text(s1mmeXdrNew.toString()));
				mos.write(NullWritable.get(), new Text(s1mmeXdrNew.toString()),"tb_xdr_ifc_s1mme_new");
			}
		}
		if(gxRxNewList.size()>0){
			List<GxRxNew> gxRxResultNewList = null;
			gxRxResultNewList = xdrNewService.analyseGxRxXdrNew(gxRxNewList, locGuserMarkList, professNetCellMap, publicNetCellMap, switchPointMap, distanceRange);
			for (GxRxNew gxRxNew : gxRxResultNewList) {
				//context.write(NullWritable.get(), new Text(s1mmeXdrNew.toString()));
				mos.write(NullWritable.get(), new Text(gxRxNew.toString()),"tb_xdr_ifc_gxrx_new");
			}
		}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		mos.close();
	}
}
