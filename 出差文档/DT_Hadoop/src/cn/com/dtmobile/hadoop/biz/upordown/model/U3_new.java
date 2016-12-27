package cn.com.dtmobile.hadoop.biz.upordown.model;
public class U3_new {
	String groupname;
	String groupmapping;
	
	public U3_new(String[] values){
		this.groupname = values[0];
		this.groupmapping = values[1];
	}
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupmapping() {
		return groupmapping;
	}
	public void setGroupmapping(String groupmapping) {
		this.groupmapping = groupmapping;
	}
}
