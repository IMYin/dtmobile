package cn.com.dtmobile.hadoop.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Reducer.Context;

@SuppressWarnings({ "deprecation" })
public class MapTableUtils {
	
	Map<String, String> exceptionResonMap = new HashMap<String, String>();
	Map<String, String> cellXdrMap = new HashMap<String, String>();
	Map<String, String> ltecellmap = new HashMap<String, String>();
	Map<String, String> t_professmap = new HashMap<String, String>();
	@SuppressWarnings({ "rawtypes" })
	public Map<String, String> map_exceptions(Context context){
		Path[] paths;
		try {
//			File file = new File("/root/workspace/input/EXCEPTIONMAP.tsv");
			Configuration conf=context.getConfiguration(); 
			paths = DistributedCache.getLocalCacheFiles(conf);
			FileSystem fsopen= FileSystem.getLocal(conf); 
			FSDataInputStream data = fsopen.open(paths[0]); 
//			BufferedReader data = new BufferedReader(new FileReader(paths[0].toString()));
//			BufferedReader data = new BufferedReader(new FileReader(file));
			String lines = null;
			while (StringUtils.isNotEmpty((lines = data.readLine()))) {
				String[] values = lines.split(StringUtils.DELIMITER_INNER_ITEM4);
				exceptionResonMap.put(values[1] + values[2], values[4]);
			}
			data.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exceptionResonMap;
	}
	@SuppressWarnings({ "rawtypes" })
	public Map<String, String> map_cellXdr(Context context){
		Path[] paths;
		try {
//			File file = new File("/root/workspace/input/CELL_MR.csv");
			Configuration conf=context.getConfiguration(); 
			paths = DistributedCache.getLocalCacheFiles(conf);
			FileSystem fsopen= FileSystem.getLocal(conf); 
			FSDataInputStream data = fsopen.open(paths[1]);
//			paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
//			BufferedReader data = new BufferedReader(new FileReader(paths[1].toString()));
//			BufferedReader data = new BufferedReader(new FileReader(file));
			String lines = null;
			double a = 0;
			while (StringUtils.isNotEmpty(lines = data.readLine())){
				String[] values = lines.split(StringUtils.DELIMITER_INNER_ITEM);
				for(int i=20;i<=30;i++){
					if ("".equals(values[i])) {
						continue;
					}else{
						a += Double.parseDouble(values[i]);
					}
				}
			double avg = a/10;
				cellXdrMap.put(values[7], String.valueOf(avg));
			}
			data.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cellXdrMap;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String, String> map_lte(Context context){
		Path[] paths;
		try {
//			File file = new File("/root/workspace/input/LTCELL.csv");
//			BufferedReader data = new BufferedReader(new FileReader(file));
			Configuration conf=context.getConfiguration(); 
			paths = DistributedCache.getLocalCacheFiles(conf);
			FileSystem fsopen= FileSystem.getLocal(conf); 
			FSDataInputStream data = fsopen.open(paths[2]);
//			paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
//			BufferedReader data = new BufferedReader(new FileReader(paths[2].toString()));
			String lines = null;
			while (StringUtils.isNotEmpty(lines = data.readLine())){
				String[] values = lines.split(StringUtils.DELIMITER_INNER_ITEM);
				ltecellmap.put(values[8], values[10]+StringUtils.DELIMITER_VERTICAL+values[11]);
			}
			data.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ltecellmap;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String, String> t_profess(Context context){
		Path[] paths;
		try {
			Configuration conf=context.getConfiguration(); 
			paths = DistributedCache.getLocalCacheFiles(conf);
			FileSystem fsopen= FileSystem.getLocal(conf); 
			FSDataInputStream data = fsopen.open(paths[3]);
//			paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
//			BufferedReader data = new BufferedReader(new FileReader(paths[3].toString()));
			String lines = null;
			while (StringUtils.isNotEmpty(lines = data.readLine())){
				String[] values = lines.split(StringUtils.DELIMITER_INNER_ITEM);
				t_professmap.put(values[17]+values[18], values[6]+StringUtils.DELIMITER_VERTICAL+values[7]);
			}
			data.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t_professmap;
	}
}
