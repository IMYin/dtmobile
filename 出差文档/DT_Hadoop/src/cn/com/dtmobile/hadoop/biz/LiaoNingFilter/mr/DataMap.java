package cn.com.dtmobile.hadoop.biz.LiaoNingFilter.mr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.StringUtils;

@SuppressWarnings("deprecation")
public class DataMap extends Mapper<LongWritable, Text, Text, Text> {
	Set<String> h = new HashSet<String>();
	private Text key = new Text();

	@SuppressWarnings("resource")
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		Path[] paths;
		paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		BufferedReader volte_data = new BufferedReader(new FileReader(
				paths[0].toString()));
		String lines_volte = null;
		HashSet<String> h = new HashSet<String>();
		while (StringUtils.isNotEmpty((lines_volte = volte_data.readLine()))) {
			h.add(lines_volte);
		}
	}

	@Override
	public void map(LongWritable keys, Text value, Context context)
			throws IOException, InterruptedException {
		if (value.getLength() > 0) {
			String[] values = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM);
			String filePath = context.getInputSplit().toString().toUpperCase();
			if (filePath.contains(TablesConstants.TABLE_MW_XDR)) {
				if (h.contains(values[5])) {
					key.set(TablesConstants.TABLE_MW_XDR);
					context.write(key, value);
				}
			} else if (filePath.contains(TablesConstants.TABLE_UU_XDR)) {
				if (h.contains(values[5])) {
					key.set(values[16]+StringUtils.DELIMITER_INNER_ITEM+TablesConstants.TABLE_UU_XDR);
					context.write(key, value);
				}
			} else if (filePath.contains(TablesConstants.TABLE_SV)) {
				if (h.contains(values[5])) {
					key.set(TablesConstants.TABLE_SV);
					context.write(key, value);
				}
			} else if (filePath.contains(TablesConstants.TABLE_GX)) {
				if (h.contains(values[5])) {
					key.set(TablesConstants.TABLE_GX);
					context.write(key, value);
				}
			} else if (filePath.contains(TablesConstants.TABLE_S1MME_XDR)) {
				if (h.contains(values[5])) {
					key.set(values[33]+StringUtils.DELIMITER_INNER_ITEM+TablesConstants.TABLE_S1MME_XDR);
					context.write(key, value);
				}
			} else if (filePath.contains(TablesConstants.TABLE_X2)) {
				if (h.contains(values[5])) {
					key.set(values[12]+StringUtils.DELIMITER_INNER_ITEM+TablesConstants.TABLE_X2);
					context.write(key, value);
				}
			} else if (filePath.contains(TablesConstants.TABLE_SGS_XDR)) {
				if (h.contains(values[5])) {
					key.set(values[28]+StringUtils.DELIMITER_INNER_ITEM+TablesConstants.TABLE_SGS_XDR);
					context.write(key, value);
				}
			} else if (filePath.contains(TablesConstants.TABLE_LTE_MRO_SOU)) {
				if (h.contains(values[97])) {
					key.set(values[9]+StringUtils.DELIMITER_INNER_ITEM+TablesConstants.TABLE_LTE_MRO_SOU);
					context.write(key, value);
				}
			}else {
				return;
			}
		}

	}

}
