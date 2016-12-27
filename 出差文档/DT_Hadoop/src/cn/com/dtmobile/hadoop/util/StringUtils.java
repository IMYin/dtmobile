package cn.com.dtmobile.hadoop.util;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	public static final String DELIMITER_INNER_ITEM = ",";
	public static final String DELIMITER_INNER_ITEM1 = "_";
	public static final String DELIMITER_INNER_ITEM2 = ":";
	public static final String DELIMITER_INNER_ITEM3 = "\005";
	public static final String DELIMITER_INNER_ITEM4 = ";";
	public static final String DELIMITER_INNER_ITEM5 = "-";
		
	public static final String DELIMITER_BETWEEN_ITEMS = "\001";
	public static final String DELIMITER_BETWEEN_ITEMS2 = "\\|";
	public static final String DELIMITER_SPLIT_ITEMS = "\\\001|\\\002";
	
	public static final String DELIMITER_BETWEEN_ELEMENT = "\007";
	
	public static final String ARGS_DELIMITER = " ";
	public static final String DELIMITER_BETWEEN_KEYVALUE = "\t";
	public static final String DELIMITER_END = "\n";
	public static final String DELIMITER_FIELD = "|";
	public static final String ARGS_NULL1 = "\\N";
	private StringUtils() {}
}
