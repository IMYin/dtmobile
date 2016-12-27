package cn.com.dtmobile.hadoop.util;

import java.lang.reflect.Method;
import java.util.Comparator;

public class UuComparator implements Comparator<Object>{

	private String fieldName;
	
	public int compare(Object obj1 , Object obj2) {
		if(_getLongVal(obj1 ) < _getLongVal(obj1)){
			return 0;
		}else{
			return 1;
		}
	}
	
	public long _getLongVal(Object xdr) {
		Method method;
		try {
			method = xdr.getClass().getMethod(fieldName);
			return (Long) method.invoke(xdr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
