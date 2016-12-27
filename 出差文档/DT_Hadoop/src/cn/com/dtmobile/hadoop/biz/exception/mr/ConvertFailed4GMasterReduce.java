package cn.com.dtmobile.hadoop.biz.exception.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.com.dtmobile.hadoop.biz.exception.model.GxExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.model.MwExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.model.S1mmeExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.model.UuExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.model.X2ExceptionMessage;
import cn.com.dtmobile.hadoop.biz.exception.service.AttachFailAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.EsrvccFailAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.Exception4GAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.IMSRegistFailAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.Lose4GAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.OffLine4GAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.ServiceRequestAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.TauFailAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.VolteExceptionAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.VolteExceptionCalledAnalyService;
import cn.com.dtmobile.hadoop.biz.exception.service.VolteExceptionLoseAnalyService;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.GxRxNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LteMroSourceNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.MwNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.S1mmeXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.SvNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.UuXdrNew;
import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.X2XdrNew;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.MapTableUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class ConvertFailed4GMasterReduce extends
		Reducer<Text, Text, NullWritable, Text> {
	List<S1mmeXdrNew> s1mmeXdrList = null;
	List<LteMroSourceNew> lteMroSourceList = null;
	List<X2XdrNew> x2List = null;
	List<MwNew> mwXdrList = null;
	List<UuXdrNew> uuList = null;
	List<SvNew> svList = null;
	List<GxRxNew> gxXdrList = null;
	Map<String, String> exceptionResonMap = new HashMap<String, String>();
	Map<String, String> cellXdrMap = new HashMap<String, String>();
	Map<String, String> t_professMap = new HashMap<String, String>();
	Map<String, String> ltecellMap = new HashMap<String, String>();

	@Override
	protected void setup(Reducer<Text, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		MapTableUtils maptables = new MapTableUtils();
		exceptionResonMap = maptables.map_exceptions(context);
		cellXdrMap = maptables.map_cellXdr(context);
		t_professMap = maptables.t_profess(context);
		ltecellMap = maptables.map_lte(context);
	}

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		x2List = new ArrayList<X2XdrNew>();
		uuList = new ArrayList<UuXdrNew>();
		s1mmeXdrList = new ArrayList<S1mmeXdrNew>();
		lteMroSourceList = new ArrayList<LteMroSourceNew>();
		svList = new ArrayList<SvNew>();
		mwXdrList = new ArrayList<MwNew>();
		gxXdrList = new ArrayList<GxRxNew>();

		for (Text value : values) {
			int len = value.toString().split(StringUtils.DELIMITER_INNER_ITEM).length;
			String tableTag = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM)[len - 1];
			StringBuffer sb = new StringBuffer();
			sb.append(value.toString());
			if (TablesConstants.TABLE_UU_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				uuList.add(new UuXdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_S1MME_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 6, sb.length());
				s1mmeXdrList.add(new S1mmeXdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_LTE_MRO_SOU.equals(tableTag)) {
				sb.delete(sb.length() - 15, sb.length());
				lteMroSourceList.add(new LteMroSourceNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_X2.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				x2List.add(new X2XdrNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_SV.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				svList.add(new SvNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_MW_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				mwXdrList.add(new MwNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else if (TablesConstants.TABLE_RX_XDR.equals(tableTag)) {
				sb.delete(sb.length() - 3, sb.length());
				gxXdrList.add(new GxRxNew(sb.toString().split(
						StringUtils.DELIMITER_INNER_ITEM)));
			} else {
				return;
			}

		}

		Exception4GAnalyService exception4GAnalyService = new Exception4GAnalyService();
		List<X2ExceptionMessage> x2ExceptionMessageList = exception4GAnalyService
				.exception4GAnalyServiceX2(x2List, s1mmeXdrList, uuList,
						ltecellMap, cellXdrMap, lteMroSourceList,
						exceptionResonMap, t_professMap);
		if (x2ExceptionMessageList.size() > 0) {
			for (X2ExceptionMessage x2ExceptionMessage : x2ExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("4G切换失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(x2ExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList = exception4GAnalyService
				.exception4GAnalyServiceS1(s1mmeXdrList, uuList, ltecellMap,
						cellXdrMap, lteMroSourceList, exceptionResonMap,
						t_professMap);
		if (s1mmeExceptionMessageList.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("4G切换失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
		List<UuExceptionMessage> uuExceptionMessageList = exception4GAnalyService
				.exception4GAnalyServiceUu(x2List, s1mmeXdrList, uuList,
						ltecellMap, cellXdrMap, lteMroSourceList,
						exceptionResonMap, t_professMap);
		if (uuExceptionMessageList.size() > 0) {
			for (UuExceptionMessage uuExceptionMessage : uuExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("4G切换失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(uuExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
		AttachFailAnalyService attachFailAnalyService = new AttachFailAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList_att = attachFailAnalyService
				.attachFailAnalyService(s1mmeXdrList, uuList, lteMroSourceList,
						cellXdrMap, exceptionResonMap);
		if (s1mmeExceptionMessageList_att.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList_att) {
				StringBuffer sb = new StringBuffer();
				sb.append("Attach失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
		EsrvccFailAnalyService esrvccFailAnalyService = new EsrvccFailAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList_Esrv = esrvccFailAnalyService
				.esrvccFailAnalyService(svList, uuList, s1mmeXdrList,
						lteMroSourceList, exceptionResonMap);
		if (s1mmeExceptionMessageList_Esrv.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList_Esrv) {
				StringBuffer sb = new StringBuffer();
				sb.append("ESRVCC失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
		IMSRegistFailAnalyService iMSRegistFailAnalyService = new IMSRegistFailAnalyService();
		List<MwExceptionMessage> mwExceptionMessageList_IMS = iMSRegistFailAnalyService
				.imsRegistFailAnalyService(mwXdrList, lteMroSourceList,
						cellXdrMap, exceptionResonMap, ltecellMap);
		if (mwExceptionMessageList_IMS.size() > 0) {

			for (MwExceptionMessage mwExceptionMessage : mwExceptionMessageList_IMS) {
				StringBuffer sb = new StringBuffer();
				sb.append("IMS注册失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(mwExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
				System.out.println(mwExceptionMessage.getMwXdr().getMwXdr().getProcedurestarttime());
			}
		}
		Lose4GAnalyService lose4GAnalyService = new Lose4GAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList_lose4g = lose4GAnalyService
				.Lose4GService(s1mmeXdrList, lteMroSourceList, uuList,
						cellXdrMap, exceptionResonMap);
		if (s1mmeExceptionMessageList_lose4g.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList_lose4g) {
				StringBuffer sb = new StringBuffer();
				sb.append("4G掉线");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(NullWritable.get(), new Text(sb.toString()));
			}
		}
		OffLine4GAnalyService offLine4GAnalyService = new OffLine4GAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList_tuowang4g = offLine4GAnalyService
				.OffLine4GService(s1mmeXdrList, lteMroSourceList, uuList,
						cellXdrMap, exceptionResonMap);
		if (s1mmeExceptionMessageList_tuowang4g.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList_tuowang4g) {
				StringBuffer sb = new StringBuffer();
				sb.append("4G脱网");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(null, new Text(sb.toString()));
			}
		}
		ServiceRequestAnalyService serviceRequestAnalyService = new ServiceRequestAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList_servicerequest = serviceRequestAnalyService
				.volteServiceRequestService(s1mmeXdrList, lteMroSourceList,
						cellXdrMap, exceptionResonMap);
		if (s1mmeExceptionMessageList_servicerequest.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList_servicerequest) {
				StringBuffer sb = new StringBuffer();
				sb.append("Service Request失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(null, new Text(sb.toString()));
			}
		}
		TauFailAnalyService tauFailAnalyService = new TauFailAnalyService();
		List<S1mmeExceptionMessage> s1mmeExceptionMessageList_tau = tauFailAnalyService
				.TauFailService(s1mmeXdrList, lteMroSourceList, uuList,
						cellXdrMap, exceptionResonMap);
		if (s1mmeExceptionMessageList_tau.size() > 0) {
			for (S1mmeExceptionMessage s1mmeExceptionMessage : s1mmeExceptionMessageList_tau) {
				StringBuffer sb = new StringBuffer();
				sb.append("TAU失败");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(s1mmeExceptionMessage.toString());
				context.write(null, new Text(sb.toString()));
			}
		}
		VolteExceptionLoseAnalyService volteExceptionLoseAnalyService = new VolteExceptionLoseAnalyService();
		List<GxExceptionMessage> gxExceptionMessageList = volteExceptionLoseAnalyService
				.volteNoConnService(gxXdrList, s1mmeXdrList, lteMroSourceList,
						cellXdrMap, exceptionResonMap);
		if (gxExceptionMessageList.size() > 0) {
			for (GxExceptionMessage gxExceptionMessage : gxExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("VOLTE掉话");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(gxExceptionMessage.toString());
				context.write(null, new Text(sb.toString()));
			}
		}
		VolteExceptionCalledAnalyService volteExceptionCalledAnalyService = new VolteExceptionCalledAnalyService();
		List<MwExceptionMessage> mwExceptionMessageList = volteExceptionCalledAnalyService
				.volteNoConnCalledService(mwXdrList, s1mmeXdrList, uuList,
						lteMroSourceList, cellXdrMap, exceptionResonMap,
						ltecellMap);
		if (mwExceptionMessageList.size() > 0) {
			for (MwExceptionMessage mwExceptionMessage : mwExceptionMessageList) {
				StringBuffer sb = new StringBuffer();
				sb.append("VOLTE未接通（被叫）");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(mwExceptionMessage.toString());
				context.write(null, new Text(sb.toString()));
			}
		}
		VolteExceptionAnalyService volteExceptionAnalyService = new VolteExceptionAnalyService();
		List<MwExceptionMessage> mwExceptionMessageList_volte = volteExceptionAnalyService
				.volteNoConnService(mwXdrList, s1mmeXdrList, lteMroSourceList,
						cellXdrMap, exceptionResonMap, ltecellMap);
		if (mwExceptionMessageList_volte.size() > 0) {

			for (MwExceptionMessage mwExceptionMessage : mwExceptionMessageList_volte) {
				StringBuffer sb = new StringBuffer();
				sb.append("VOLTE未接通（主叫）");
				sb.append(StringUtils.DELIMITER_INNER_ITEM);
				sb.append(mwExceptionMessage.toString());
				context.write(null, new Text(sb.toString()));
			}
		}
	}

}
