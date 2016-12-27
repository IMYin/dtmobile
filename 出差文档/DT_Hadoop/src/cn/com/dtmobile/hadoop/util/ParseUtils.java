package cn.com.dtmobile.hadoop.util;

import org.apache.commons.lang.StringUtils;

public class ParseUtils {

	public static Long  parseLong(String val){
		if("null".equals(val)||"NULL".equals(val)){
			return (long)0;
		}
		if(StringUtils.isNotEmpty(val)){
			return Long.valueOf(val);
		}
		return null;
	}
	
	public static Double  parseDouble(String val){
		if("null".equals(val)||"NULL".equals(val)){
			return 0.0;
		}
		if(StringUtils.isNotEmpty(val)){
			return Double.valueOf(val);
		}
		return null;
	}
	
	public static Integer  parseInteger(String val){
		if("null".equals(val)||"NULL".equals(val)){
			return 0;
		}
		if(StringUtils.isNotEmpty(val)){
			return Integer.valueOf(val);
		}
		return null;
	}
	
}
