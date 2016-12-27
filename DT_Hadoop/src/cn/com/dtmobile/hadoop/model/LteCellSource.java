package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;

public class LteCellSource {
	private Integer id;
	private Integer mcc;
	private Integer mnc;
	private Integer mmegroupid;
	private Integer mmeid;
	private Integer enodebid;
	private Integer sitename;
	private Integer cellname;
	private Integer localcellid;
	private Integer cellid;
	private Integer tai;
	private Integer pci;
	private Integer freq1;
	private Integer freq2;
	private String bandwidth1;
	private String bandwidth2;
	private Integer freqcount;
	private String longitude;
	private String latitude;
	private Integer sectortype;
	private Integer doortype;
	private String tilttotal;
	private String tiltm;
	private String tilte;
	private String azimuth;
	private String beamwidth;
	private String vbeamwidth;
	private String aheight;
	private String city;
	private String company;
	private String region;
	private String enbtype;
	private String enbversion;
	
	public LteCellSource(String[] array) {
		this.id = ParseUtils.parseInteger(array[0]);         
		this.mcc = ParseUtils.parseInteger(array[1]);        
		this.mnc = ParseUtils.parseInteger(array[2]);        
		this.mmegroupid = ParseUtils.parseInteger(array[3]); 
		this.mmeid = ParseUtils.parseInteger(array[4]);      
		this.enodebid = ParseUtils.parseInteger(array[5]);   
		this.sitename = ParseUtils.parseInteger(array[6]);   
		this.cellname = ParseUtils.parseInteger(array[7]);   
		this.localcellid = ParseUtils.parseInteger(array[8]);
		this.cellid = ParseUtils.parseInteger(array[9]);     
		this.tai = ParseUtils.parseInteger(array[10]);        
		this.pci = ParseUtils.parseInteger(array[11]);        
		this.freq1 = ParseUtils.parseInteger(array[12]);      
		this.freq2 = ParseUtils.parseInteger(array[13]);      
		this.bandwidth1 = array[14]; 
		this.bandwidth2 = array[15]; 
		this.freqcount = ParseUtils.parseInteger(array[16]);  
		this.longitude = array[17];  
		this.latitude = array[18];   
		this.sectortype = ParseUtils.parseInteger(array[19]); 
		this.doortype = ParseUtils.parseInteger(array[20]);   
		this.tilttotal = array[21];  
		this.tiltm = array[22];      
		this.tilte = array[23];      
		this.azimuth = array[24];    
		this.beamwidth = array[25];  
		this.vbeamwidth = array[26]; 
		this.aheight = array[27];    
		this.city = array[28];       
		this.company = array[29];    
		this.region = array[30];     
		this.enbtype = array[31];    
		this.enbversion = array[32]; 

	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMcc() {
		return mcc;
	}
	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}
	public Integer getMnc() {
		return mnc;
	}
	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}
	public Integer getMmegroupid() {
		return mmegroupid;
	}
	public void setMmegroupid(Integer mmegroupid) {
		this.mmegroupid = mmegroupid;
	}
	public Integer getMmeid() {
		return mmeid;
	}
	public void setMmeid(Integer mmeid) {
		this.mmeid = mmeid;
	}
	public Integer getEnodebid() {
		return enodebid;
	}
	public void setEnodebid(Integer enodebid) {
		this.enodebid = enodebid;
	}
	public Integer getSitename() {
		return sitename;
	}
	public void setSitename(Integer sitename) {
		this.sitename = sitename;
	}
	public Integer getCellname() {
		return cellname;
	}
	public void setCellname(Integer cellname) {
		this.cellname = cellname;
	}
	public Integer getLocalcellid() {
		return localcellid;
	}
	public void setLocalcellid(Integer localcellid) {
		this.localcellid = localcellid;
	}
	public Integer getCellid() {
		return cellid;
	}
	public void setCellid(Integer cellid) {
		this.cellid = cellid;
	}
	public Integer getTai() {
		return tai;
	}
	public void setTai(Integer tai) {
		this.tai = tai;
	}
	public Integer getPci() {
		return pci;
	}
	public void setPci(Integer pci) {
		this.pci = pci;
	}
	public Integer getFreq1() {
		return freq1;
	}
	public void setFreq1(Integer freq1) {
		this.freq1 = freq1;
	}
	public Integer getFreq2() {
		return freq2;
	}
	public void setFreq2(Integer freq2) {
		this.freq2 = freq2;
	}
	public String getBandwidth1() {
		return bandwidth1;
	}
	public void setBandwidth1(String bandwidth1) {
		this.bandwidth1 = bandwidth1;
	}
	public String getBandwidth2() {
		return bandwidth2;
	}
	public void setBandwidth2(String bandwidth2) {
		this.bandwidth2 = bandwidth2;
	}
	public Integer getFreqcount() {
		return freqcount;
	}
	public void setFreqcount(Integer freqcount) {
		this.freqcount = freqcount;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Integer getSectortype() {
		return sectortype;
	}
	public void setSectortype(Integer sectortype) {
		this.sectortype = sectortype;
	}
	public Integer getDoortype() {
		return doortype;
	}
	public void setDoortype(Integer doortype) {
		this.doortype = doortype;
	}
	public String getTilttotal() {
		return tilttotal;
	}
	public void setTilttotal(String tilttotal) {
		this.tilttotal = tilttotal;
	}
	public String getTiltm() {
		return tiltm;
	}
	public void setTiltm(String tiltm) {
		this.tiltm = tiltm;
	}
	public String getTilte() {
		return tilte;
	}
	public void setTilte(String tilte) {
		this.tilte = tilte;
	}
	public String getAzimuth() {
		return azimuth;
	}
	public void setAzimuth(String azimuth) {
		this.azimuth = azimuth;
	}
	public String getBeamwidth() {
		return beamwidth;
	}
	public void setBeamwidth(String beamwidth) {
		this.beamwidth = beamwidth;
	}
	public String getVbeamwidth() {
		return vbeamwidth;
	}
	public void setVbeamwidth(String vbeamwidth) {
		this.vbeamwidth = vbeamwidth;
	}
	public String getAheight() {
		return aheight;
	}
	public void setAheight(String aheight) {
		this.aheight = aheight;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEnbtype() {
		return enbtype;
	}
	public void setEnbtype(String enbtype) {
		this.enbtype = enbtype;
	}
	public String getEnbversion() {
		return enbversion;
	}
	public void setEnbversion(String enbversion) {
		this.enbversion = enbversion;
	}
}
