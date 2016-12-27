package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class LteMroSource {
	private String objectid;
	private int vid;
	private String fileformatversion;
	private String starttime;
	private String endtime;
	private String period;
	private String enbid;
	private String userlabel;
	private String mrname;
	private Long cellid;
	private String earfcn;
	private String subframenbr;
	private String prbnbr;
	private String mmeues1apid;
	private String mmegroupid;
	private String mmecode;
	private String meatime;
	private String eventtype;
	private Double gridcenterlongitude;
	private Double gridcenterlatitude;
	private Double kpi1;
	private Double kpi2;
	private Double kpi3;
	private Double kpi4;
	private Double kpi5;
	private Double kpi6;
	private Double kpi7;
	private Double kpi8;
	private Double kpi9;
	private Double kpi10;
	private Double kpi11;
	private Double kpi12;
	private Double kpi13;
	private Double kpi14;
	private Double kpi15;
	private Double kpi16;
	private Double kpi17;
	private Double kpi18;
	private Double kpi19;
	private Double kpi20;
	private Double kpi21;
	private Double kpi22;
	private Double kpi23;
	private Double kpi24;
	private Double kpi25;
	private Double kpi26;
	private Double kpi27;
	private Double kpi28;
	private Double kpi29;
	private Double kpi30;
	private Double kpi31;
	private Double kpi32;
	private Double kpi33;
	private Double kpi34;
	private Double kpi35;
	private Double kpi36;
	private Double kpi37;
	private Double kpi38;
	private Double kpi39;
	private Double kpi40;
	private Double kpi41;
	private Double kpi42;
	private Double kpi43;
	private Double kpi44;
	private Double kpi45;
	private Double kpi46;
	private Double kpi47;
	private Double kpi48;
	private Double kpi49;
	private Double kpi50;
	private Double kpi51;
	private Double kpi52;
	private Double kpi53;
	private Double kpi54;
	private Double kpi55;
	private Double kpi56;
	private Double kpi57;
	private Double kpi58;
	private Double kpi59;
	private Double kpi60;
	private Double kpi61;
	private Double kpi62;
	private Double kpi63;
	private Double kpi64;
	private Double kpi65;
	private Double kpi66;
	private Double kpi67;
	private Double kpi68;
	private Double kpi69;
	private Double kpi70;
	private Double kpi71;
	private String length;
	private String city;
	private String xdrtype;
	private String interfaces;
	private String xdrid;
	private String rat;
	private String imsi;
	private String imei;
	private String msisdn;
	private String mrtype;
	private String neighborcellnumber;
	private String gsmneighborcellnumber;
	private String tdsneighborcellnumber;
	private String v_enb;
	private Long mrtime;
	
	public LteMroSource(String[] values) {
		this.objectid = values[0];
		this.vid = ParseUtils.parseInteger(values[1]);
		this.fileformatversion = values[2];
		this.starttime = values[3];
		this.endtime = values[4];
		this.period = values[5];
		this.enbid = values[6];
		this.userlabel = values[7];
		this.mrname = values[8];
		this.cellid = toLong(values[9]);
		this.earfcn = values[10];
		this.subframenbr = values[11];
		this.prbnbr = values[12];
		this.mmeues1apid = values[13];
		this.mmegroupid = values[14];
		this.mmecode = values[15];
		this.meatime = values[16];
		this.eventtype = values[17];
		this.gridcenterlongitude = values[18].equals("")?0:Double.valueOf(values[18]);
		this.gridcenterlatitude = values[19].equals("")?0:Double.valueOf(values[19]);
		this.kpi1 = ParseUtils.parseDouble(values[20]);
		this.kpi2 = ParseUtils.parseDouble(values[21]);
		this.kpi3 = ParseUtils.parseDouble(values[22]);
		this.kpi4 = ParseUtils.parseDouble(values[23]);
		this.kpi5 = ParseUtils.parseDouble(values[24]);
		this.kpi6 = ParseUtils.parseDouble(values[25]);
		this.kpi7 = ParseUtils.parseDouble(values[26]);
		this.kpi8 = ParseUtils.parseDouble(values[27]);
		this.kpi9 = ParseUtils.parseDouble(values[28]);
		this.kpi10 =ParseUtils.parseDouble( values[29]);
		this.kpi11 = ParseUtils.parseDouble(values[30]);
		this.kpi12 = ParseUtils.parseDouble(values[31]);
		this.kpi13 = ParseUtils.parseDouble(values[32]);
		this.kpi14 = ParseUtils.parseDouble(values[33]);
		this.kpi15 = ParseUtils.parseDouble(values[34]);
		this.kpi16 = ParseUtils.parseDouble(values[35]);
		this.kpi17 = ParseUtils.parseDouble(values[36]);
		this.kpi18 = ParseUtils.parseDouble(values[37]);
		this.kpi19 = ParseUtils.parseDouble(values[38]);
		this.kpi20 = ParseUtils.parseDouble(values[39]);
		this.kpi21 = ParseUtils.parseDouble(values[40]);
		this.kpi22 = ParseUtils.parseDouble(values[41]);
		this.kpi23 = ParseUtils.parseDouble(values[42]);
		this.kpi24 =ParseUtils.parseDouble( values[43]);
		this.kpi25 = ParseUtils.parseDouble(values[44]);
		this.kpi26 = ParseUtils.parseDouble(values[45]);
		this.kpi27 = ParseUtils.parseDouble(values[46]);
		this.kpi28 = ParseUtils.parseDouble(values[47]);
		this.kpi29 = ParseUtils.parseDouble(values[48]);
		this.kpi30 = ParseUtils.parseDouble(values[49]);
		this.kpi31 = ParseUtils.parseDouble(values[50]);
		this.kpi32 = ParseUtils.parseDouble(values[51]);
		this.kpi33 = ParseUtils.parseDouble(values[52]);
		this.kpi34 = ParseUtils.parseDouble(values[53]);
		this.kpi35 = ParseUtils.parseDouble(values[54]);
		this.kpi36 = ParseUtils.parseDouble(values[55]);
		this.kpi37 = ParseUtils.parseDouble(values[56]);
		this.kpi38 = ParseUtils.parseDouble(values[57]);
		this.kpi39 = ParseUtils.parseDouble(values[58]);
		this.kpi40 = ParseUtils.parseDouble(values[59]);
		this.kpi41 = ParseUtils.parseDouble(values[60]);
		this.kpi42 =ParseUtils.parseDouble( values[61]);
		this.kpi43 = ParseUtils.parseDouble(values[62]);
		this.kpi44 = ParseUtils.parseDouble(values[63]);
		this.kpi45 = ParseUtils.parseDouble(values[64]);
		this.kpi46 = ParseUtils.parseDouble(values[65]);
		this.kpi47 = ParseUtils.parseDouble(values[66]);
		this.kpi48 = ParseUtils.parseDouble(values[67]);
		this.kpi49 = ParseUtils.parseDouble(values[68]);
		this.kpi50 = ParseUtils.parseDouble(values[69]);
		this.kpi51 = ParseUtils.parseDouble(values[70]);
		this.kpi52 = ParseUtils.parseDouble(values[71]);
		this.kpi53 = ParseUtils.parseDouble(values[72]);
		this.kpi54 = ParseUtils.parseDouble(values[73]);
		this.kpi55 = ParseUtils.parseDouble(values[74]);
		this.kpi56 = ParseUtils.parseDouble(values[75]);
		this.kpi57 = ParseUtils.parseDouble(values[76]);
		this.kpi58 = ParseUtils.parseDouble(values[77]);
		this.kpi59 = ParseUtils.parseDouble(values[78]);
		this.kpi60 = ParseUtils.parseDouble(values[79]);
		this.kpi61 = ParseUtils.parseDouble(values[80]);
		this.kpi62 = ParseUtils.parseDouble(values[81]);
		this.kpi63 = ParseUtils.parseDouble(values[82]);
		this.kpi64 = ParseUtils.parseDouble(values[83]);
		this.kpi65 = ParseUtils.parseDouble(values[84]);
		this.kpi66 = ParseUtils.parseDouble(values[85]);
		this.kpi67 = ParseUtils.parseDouble(values[86]);
		this.kpi68 = ParseUtils.parseDouble(values[87]);
		this.kpi69 = ParseUtils.parseDouble(values[88]);
		this.kpi70 = ParseUtils.parseDouble(values[89]);
		this.kpi71 = ParseUtils.parseDouble(values[90]);
		this.length = values[91];
		this.city = values[92];
		this.xdrtype = values[93];
		this.interfaces = values[94];
		this.xdrid = values[95];
		this.rat = values[96];
		this.imsi = values[97];
		this.imei = values[98];
		this.msisdn = values[99];
		this.mrtype = values[100];
		this.neighborcellnumber = values[101];
		this.gsmneighborcellnumber = values[102];
		this.tdsneighborcellnumber = values[103];
		this.v_enb = values[104];
		this.mrtime = Long.valueOf(values[105]);	
	}
	
	private Long toLong(String str){
		if(StringUtils.isNotEmpty(str)){
			return Long.valueOf(str);
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(objectid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(vid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(fileformatversion);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(starttime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(endtime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(period);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(enbid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(userlabel);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mrname);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(cellid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(earfcn);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(subframenbr);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(prbnbr);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmeues1apid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmegroupid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mmecode);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(meatime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(eventtype);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gridcenterlongitude);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gridcenterlatitude);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi1);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi2);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi3);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi4);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi5);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi6);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi7);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi8);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi9);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi10);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi11);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi12);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi13);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi14);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi15);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi16);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi17);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi18);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi19);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi20);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi21);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi22);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi23);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi24);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi25);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi26);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi27);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi28);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi29);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi30);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi31);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi32);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi33);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi34);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi35);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi36);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi37);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi38);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi39);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi40);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi41);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi42);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi43);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi44);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi45);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi46);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi47);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi48);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi49);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi50);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi51);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi52);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi53);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi54);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi55);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi56);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi57);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi58);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi59);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi60);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi61);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi62);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi63);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi64);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi65);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi66);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi67);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi68);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi69);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi70);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(kpi71);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(length);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(city);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(xdrtype);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(interfaces);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(xdrid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(rat);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(imei);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(msisdn);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mrtype);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(neighborcellnumber);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(gsmneighborcellnumber);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(tdsneighborcellnumber);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(v_enb);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(mrtime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		return sb.toString();
	}
	
	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public String getFileformatversion() {
		return fileformatversion;
	}

	public void setFileformatversion(String fileformatversion) {
		this.fileformatversion = fileformatversion;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getEnbid() {
		return enbid;
	}

	public void setEnbid(String enbid) {
		this.enbid = enbid;
	}

	public String getMeatime() {
		return meatime;
	}

	public void setMeatime(String meatime) {
		this.meatime = meatime;
	}

	public String getUserlabel() {
		return userlabel;
	}

	public void setUserlabel(String userlabel) {
		this.userlabel = userlabel;
	}

	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public Long getCellid() {
		return cellid;
	}

	public void setCellid(Long cellid) {
		this.cellid = cellid;
	}

	public String getEarfcn() {
		return earfcn;
	}

	public void setEarfcn(String earfcn) {
		this.earfcn = earfcn;
	}

	public String getSubframenbr() {
		return subframenbr;
	}

	public void setSubframenbr(String subframenbr) {
		this.subframenbr = subframenbr;
	}

	public String getPrbnbr() {
		return prbnbr;
	}

	public void setPrbnbr(String prbnbr) {
		this.prbnbr = prbnbr;
	}

	public String getMmeues1apid() {
		return mmeues1apid;
	}

	public void setMmeues1apid(String mmeues1apid) {
		this.mmeues1apid = mmeues1apid;
	}

	public String getMmegroupid() {
		return mmegroupid;
	}

	public void setMmegroupid(String mmegroupid) {
		this.mmegroupid = mmegroupid;
	}

	public String getMmecode() {
		return mmecode;
	}

	public void setMmecode(String mmecode) {
		this.mmecode = mmecode;
	}

	public String getEventtype() {
		return eventtype;
	}

	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}


	public Double getGridcenterlongitude() {
		return gridcenterlongitude;
	}

	public void setGridcenterlongitude(Double gridcenterlongitude) {
		this.gridcenterlongitude = gridcenterlongitude;
	}

	public Double getGridcenterlatitude() {
		return gridcenterlatitude;
	}

	public void setGridcenterlatitude(Double gridcenterlatitude) {
		this.gridcenterlatitude = gridcenterlatitude;
	}

	public Double getKpi1() {
		return kpi1;
	}

	public void setKpi1(Double kpi1) {
		this.kpi1 = kpi1;
	}

	public Double getKpi2() {
		return kpi2;
	}

	public void setKpi2(Double kpi2) {
		this.kpi2 = kpi2;
	}

	public Double getKpi3() {
		return kpi3;
	}

	public void setKpi3(Double kpi3) {
		this.kpi3 = kpi3;
	}

	public Double getKpi4() {
		return kpi4;
	}

	public void setKpi4(Double kpi4) {
		this.kpi4 = kpi4;
	}

	public Double getKpi5() {
		return kpi5;
	}

	public void setKpi5(Double kpi5) {
		this.kpi5 = kpi5;
	}

	public Double getKpi6() {
		return kpi6;
	}

	public void setKpi6(Double kpi6) {
		this.kpi6 = kpi6;
	}

	public Double getKpi7() {
		return kpi7;
	}

	public void setKpi7(Double kpi7) {
		this.kpi7 = kpi7;
	}

	public Double getKpi8() {
		return kpi8;
	}

	public void setKpi8(Double kpi8) {
		this.kpi8 = kpi8;
	}

	public Double getKpi9() {
		return kpi9;
	}

	public void setKpi9(Double kpi9) {
		this.kpi9 = kpi9;
	}

	public Double getKpi10() {
		return kpi10;
	}

	public void setKpi10(Double kpi10) {
		this.kpi10 = kpi10;
	}

	public Double getKpi11() {
		return kpi11;
	}

	public void setKpi11(Double kpi11) {
		this.kpi11 = kpi11;
	}

	public Double getKpi12() {
		return kpi12;
	}

	public void setKpi12(Double kpi12) {
		this.kpi12 = kpi12;
	}

	public Double getKpi13() {
		return kpi13;
	}

	public void setKpi13(Double kpi13) {
		this.kpi13 = kpi13;
	}

	public Double getKpi14() {
		return kpi14;
	}

	public void setKpi14(Double kpi14) {
		this.kpi14 = kpi14;
	}

	public Double getKpi15() {
		return kpi15;
	}

	public void setKpi15(Double kpi15) {
		this.kpi15 = kpi15;
	}

	public Double getKpi16() {
		return kpi16;
	}

	public void setKpi16(Double kpi16) {
		this.kpi16 = kpi16;
	}

	public Double getKpi17() {
		return kpi17;
	}

	public void setKpi17(Double kpi17) {
		this.kpi17 = kpi17;
	}

	public Double getKpi18() {
		return kpi18;
	}

	public void setKpi18(Double kpi18) {
		this.kpi18 = kpi18;
	}

	public Double getKpi19() {
		return kpi19;
	}

	public void setKpi19(Double kpi19) {
		this.kpi19 = kpi19;
	}

	public Double getKpi20() {
		return kpi20;
	}

	public void setKpi20(Double kpi20) {
		this.kpi20 = kpi20;
	}

	public Double getKpi21() {
		return kpi21;
	}

	public void setKpi21(Double kpi21) {
		this.kpi21 = kpi21;
	}

	public Double getKpi22() {
		return kpi22;
	}

	public void setKpi22(Double kpi22) {
		this.kpi22 = kpi22;
	}

	public Double getKpi23() {
		return kpi23;
	}

	public void setKpi23(Double kpi23) {
		this.kpi23 = kpi23;
	}

	public Double getKpi24() {
		return kpi24;
	}

	public void setKpi24(Double kpi24) {
		this.kpi24 = kpi24;
	}

	public Double getKpi25() {
		return kpi25;
	}

	public void setKpi25(Double kpi25) {
		this.kpi25 = kpi25;
	}

	public Double getKpi26() {
		return kpi26;
	}

	public void setKpi26(Double kpi26) {
		this.kpi26 = kpi26;
	}

	public Double getKpi27() {
		return kpi27;
	}

	public void setKpi27(Double kpi27) {
		this.kpi27 = kpi27;
	}

	public Double getKpi28() {
		return kpi28;
	}

	public void setKpi28(Double kpi28) {
		this.kpi28 = kpi28;
	}

	public Double getKpi29() {
		return kpi29;
	}

	public void setKpi29(Double kpi29) {
		this.kpi29 = kpi29;
	}

	public Double getKpi30() {
		return kpi30;
	}

	public void setKpi30(Double kpi30) {
		this.kpi30 = kpi30;
	}

	public Double getKpi31() {
		return kpi31;
	}

	public void setKpi31(Double kpi31) {
		this.kpi31 = kpi31;
	}

	public Double getKpi32() {
		return kpi32;
	}

	public void setKpi32(Double kpi32) {
		this.kpi32 = kpi32;
	}

	public Double getKpi33() {
		return kpi33;
	}

	public void setKpi33(Double kpi33) {
		this.kpi33 = kpi33;
	}

	public Double getKpi34() {
		return kpi34;
	}

	public void setKpi34(Double kpi34) {
		this.kpi34 = kpi34;
	}

	public Double getKpi35() {
		return kpi35;
	}

	public void setKpi35(Double kpi35) {
		this.kpi35 = kpi35;
	}

	public Double getKpi36() {
		return kpi36;
	}

	public void setKpi36(Double kpi36) {
		this.kpi36 = kpi36;
	}

	public Double getKpi37() {
		return kpi37;
	}

	public void setKpi37(Double kpi37) {
		this.kpi37 = kpi37;
	}

	public Double getKpi38() {
		return kpi38;
	}

	public void setKpi38(Double kpi38) {
		this.kpi38 = kpi38;
	}

	public Double getKpi39() {
		return kpi39;
	}

	public void setKpi39(Double kpi39) {
		this.kpi39 = kpi39;
	}

	public Double getKpi40() {
		return kpi40;
	}

	public void setKpi40(Double kpi40) {
		this.kpi40 = kpi40;
	}

	public Double getKpi41() {
		return kpi41;
	}

	public void setKpi41(Double kpi41) {
		this.kpi41 = kpi41;
	}

	public Double getKpi42() {
		return kpi42;
	}

	public void setKpi42(Double kpi42) {
		this.kpi42 = kpi42;
	}

	public Double getKpi43() {
		return kpi43;
	}

	public void setKpi43(Double kpi43) {
		this.kpi43 = kpi43;
	}

	public Double getKpi44() {
		return kpi44;
	}

	public void setKpi44(Double kpi44) {
		this.kpi44 = kpi44;
	}

	public Double getKpi45() {
		return kpi45;
	}

	public void setKpi45(Double kpi45) {
		this.kpi45 = kpi45;
	}

	public Double getKpi46() {
		return kpi46;
	}

	public void setKpi46(Double kpi46) {
		this.kpi46 = kpi46;
	}

	public Double getKpi47() {
		return kpi47;
	}

	public void setKpi47(Double kpi47) {
		this.kpi47 = kpi47;
	}

	public Double getKpi48() {
		return kpi48;
	}

	public void setKpi48(Double kpi48) {
		this.kpi48 = kpi48;
	}

	public Double getKpi49() {
		return kpi49;
	}

	public void setKpi49(Double kpi49) {
		this.kpi49 = kpi49;
	}

	public Double getKpi50() {
		return kpi50;
	}

	public void setKpi50(Double kpi50) {
		this.kpi50 = kpi50;
	}

	public Double getKpi51() {
		return kpi51;
	}

	public void setKpi51(Double kpi51) {
		this.kpi51 = kpi51;
	}

	public Double getKpi52() {
		return kpi52;
	}

	public void setKpi52(Double kpi52) {
		this.kpi52 = kpi52;
	}

	public Double getKpi53() {
		return kpi53;
	}

	public void setKpi53(Double kpi53) {
		this.kpi53 = kpi53;
	}

	public Double getKpi54() {
		return kpi54;
	}

	public void setKpi54(Double kpi54) {
		this.kpi54 = kpi54;
	}

	public Double getKpi55() {
		return kpi55;
	}

	public void setKpi55(Double kpi55) {
		this.kpi55 = kpi55;
	}

	public Double getKpi56() {
		return kpi56;
	}

	public void setKpi56(Double kpi56) {
		this.kpi56 = kpi56;
	}

	public Double getKpi57() {
		return kpi57;
	}

	public void setKpi57(Double kpi57) {
		this.kpi57 = kpi57;
	}

	public Double getKpi58() {
		return kpi58;
	}

	public void setKpi58(Double kpi58) {
		this.kpi58 = kpi58;
	}

	public Double getKpi59() {
		return kpi59;
	}

	public void setKpi59(Double kpi59) {
		this.kpi59 = kpi59;
	}

	public Double getKpi60() {
		return kpi60;
	}

	public void setKpi60(Double kpi60) {
		this.kpi60 = kpi60;
	}

	public Double getKpi61() {
		return kpi61;
	}

	public void setKpi61(Double kpi61) {
		this.kpi61 = kpi61;
	}

	public Double getKpi62() {
		return kpi62;
	}

	public void setKpi62(Double kpi62) {
		this.kpi62 = kpi62;
	}

	public Double getKpi63() {
		return kpi63;
	}

	public void setKpi63(Double kpi63) {
		this.kpi63 = kpi63;
	}

	public Double getKpi64() {
		return kpi64;
	}

	public void setKpi64(Double kpi64) {
		this.kpi64 = kpi64;
	}

	public Double getKpi65() {
		return kpi65;
	}

	public void setKpi65(Double kpi65) {
		this.kpi65 = kpi65;
	}

	public Double getKpi66() {
		return kpi66;
	}

	public void setKpi66(Double kpi66) {
		this.kpi66 = kpi66;
	}

	public Double getKpi67() {
		return kpi67;
	}

	public void setKpi67(Double kpi67) {
		this.kpi67 = kpi67;
	}

	public Double getKpi68() {
		return kpi68;
	}

	public void setKpi68(Double kpi68) {
		this.kpi68 = kpi68;
	}

	public Double getKpi69() {
		return kpi69;
	}

	public void setKpi69(Double kpi69) {
		this.kpi69 = kpi69;
	}

	public Double getKpi70() {
		return kpi70;
	}

	public void setKpi70(Double kpi70) {
		this.kpi70 = kpi70;
	}

	public Double getKpi71() {
		return kpi71;
	}

	public void setKpi71(Double kpi71) {
		this.kpi71 = kpi71;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getXdrtype() {
		return xdrtype;
	}

	public void setXdrtype(String xdrtype) {
		this.xdrtype = xdrtype;
	}

	public String getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}

	public String getXdrid() {
		return xdrid;
	}

	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}

	public String getRat() {
		return rat;
	}

	public void setRat(String rat) {
		this.rat = rat;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMrtype() {
		return mrtype;
	}

	public void setMrtype(String mrtype) {
		this.mrtype = mrtype;
	}

	public String getNeighborcellnumber() {
		return neighborcellnumber;
	}

	public void setNeighborcellnumber(String neighborcellnumber) {
		this.neighborcellnumber = neighborcellnumber;
	}

	public String getGsmneighborcellnumber() {
		return gsmneighborcellnumber;
	}

	public void setGsmneighborcellnumber(String gsmneighborcellnumber) {
		this.gsmneighborcellnumber = gsmneighborcellnumber;
	}

	public String getTdsneighborcellnumber() {
		return tdsneighborcellnumber;
	}

	public void setTdsneighborcellnumber(String tdsneighborcellnumber) {
		this.tdsneighborcellnumber = tdsneighborcellnumber;
	}

	public String getV_enb() {
		return v_enb;
	}

	public void setV_enb(String v_enb) {
		this.v_enb = v_enb;
	}

	public Long getMrtime() {
		return mrtime;
	}

	public void setMrtime(Long mrtime) {
		this.mrtime = mrtime;
	}

}
