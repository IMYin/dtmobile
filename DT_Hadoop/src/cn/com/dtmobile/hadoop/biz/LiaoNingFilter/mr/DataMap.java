package cn.com.dtmobile.hadoop.biz.LiaoNingFilter.mr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import cn.com.dtmobile.hadoop.constants.TablesConstants;
import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

@SuppressWarnings("deprecation")
public class DataMap extends Mapper<LongWritable, Text, NullWritable, Text> {
	Set<String> h = new HashSet<String>();
	// private Text key = new Text();
	@SuppressWarnings("rawtypes")
	public MultipleOutputs mos;

	@SuppressWarnings({ "resource", "unchecked", "rawtypes" })
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		mos = new MultipleOutputs(context);
		Path[] paths;
		paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		// File file = new File(
		// "/home/imyin/dtmobile/workspace/input/t_process.csv");
		// BufferedReader volte_data = new BufferedReader(new FileReader(file));
		BufferedReader volte_data = new BufferedReader(new FileReader(
				paths[0].toString()));
		String lines_volte = null;
		while (StringUtils.isNotEmpty((lines_volte = volte_data.readLine()))) {
			String cgi = lines_volte.split(StringUtils.DELIMITER_COMMA)[12];
			String[] cgi_items = cgi.split(StringUtils.DELIMITER_INNER_ITEM5);
			int cellid = ParseUtils
					.parseInteger(cgi_items[cgi_items.length - 2])
					* 256
					- ParseUtils.parseInteger(cgi_items[cgi_items.length - 1]);
			h.add(String.valueOf(cellid));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void map(LongWritable keys, Text value, Context context)
			throws IOException, InterruptedException {

		if (value.getLength() > 0) {
			String filePath = context.getInputSplit().toString().toUpperCase();
			String[] values = value.toString().split(
					StringUtils.DELIMITER_INNER_ITEM);
			if (filePath.contains(TablesConstants.FILTER_SV)) {
				mos.write("voltesv", NullWritable.get(), value);
			} else if (filePath.contains(TablesConstants.FILTER_RX)) {
				mos.write("volterx", NullWritable.get(), value);
			} else if (filePath.contains(TablesConstants.FILTER_S1MME)) {
				if (values.length > 33) {
					if (h.contains(values[33])) {
						mos.write("s1mmeorgn", NullWritable.get(), value);
					}
				}
			} else if (filePath.contains(TablesConstants.FILTER_SGS)) {
				if (values.length > 28) {
					if (h.contains(values[28])) {
						mos.write("sgsorgn", NullWritable.get(), value);
					}
				}
			} else if (filePath.contains(TablesConstants.FILTER_VOLTE)) {
				mos.write("volteorgn", NullWritable.get(), value);
			} else if (filePath.contains(TablesConstants.FILTER_HTTP)) {
				if (values.length > 16) {
					if (h.contains(values[16])) {
						mos.write("s1uhttporgn", NullWritable.get(), value);
					}
				}
			}
		}

	}

	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		mos.close();
	}

}
