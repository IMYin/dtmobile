package cn.com.dtmobile.hadoop.biz.train.mr.highspeeduser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.biz.train.model.highspeeduser.LocGuserMark;
import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;
public class NewCommonMap extends Mapper<LongWritable, Text, Text,Text> {

public final Text key = new Text();
	
	public final List<String> ipList=new ArrayList<String>(Arrays.asList("10.188.41.1","10.188.41.2","10.188.41.17","10.188.41.18","10.184.209.1","10.184.209.2","10.184.209.17","10.184.209.18"));
	
	
	@Override
	protected void map(LongWritable inKey, Text value, Context context)
			throws IOException, InterruptedException {
		if(value.getLength() > 0){
			String path = context.getInputSplit().toString();
			String [] values = value.toString().split(StringUtils.DELIMITER_INNER_ITEM);
			if(path.toUpperCase().contains(TablesConstants.TABLE_LTE_MRO_SOU)){
				key.set(values[97]);
				if("MR.LteScRSRP".equals(values[8])){
					context.write(key, new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_LTE_MRO_SOU));
				}
			}else if(path.toUpperCase().contains(TablesConstants.TABLE_UU_XDR)){
				key.set(values[6]);
				context.write(key,new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_UU_XDR));
 			}else if(path.toUpperCase().contains(TablesConstants.TABLE_X2)){
				key.set(values[6]);
				context.write(key,new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_X2));
			}else if(path.toUpperCase().toUpperCase().contains(TablesConstants.TABLE_MW_XDR)&&(ipList.contains(values[20])||ipList.contains(values[22]))){
				key.set(values[5]);
				context.write(key,new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_MW_XDR));
			}else if(path.toUpperCase().contains(TablesConstants.TABLE_SV)){
				key.set(values[6]);
				context.write(key,new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_SV));
			}else if(path.toUpperCase().contains(TablesConstants.TABLE_SGS_XDR)){
				key.set(values[6]);
				context.write(key,new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_SGS_XDR));
			}else if(path.toUpperCase().contains(TablesConstants.TABLE_S1MME_XDR)){
				key.set(values[6]);
				context.write(key,new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_S1MME_XDR));
			}else if(path.toUpperCase().contains(TablesConstants.TABLE_RX_XDR)){
				key.set(values[5]);
				context.write(key,new Text(value + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_RX_XDR));
			}else if(path.toUpperCase().contains(TablesConstants.TABLE_LOC_GUSER_MARK)){
				key.set(values[1]);
				LocGuserMark locGuserMark = new LocGuserMark(values);
				if(1==locGuserMark.getSpeedok() || 2==locGuserMark.getSpeedok()){
					context.write(key, new Text(locGuserMark.toString() + StringUtils.DELIMITER_INNER_ITEM + TablesConstants.TABLE_LOC_GUSER_MARK));
				}
			}else{
				return ;
			}
		}
	}
}
