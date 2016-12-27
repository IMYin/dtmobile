package cn.com.dtmobile.hadoop.model;

import cn.com.dtmobile.hadoop.util.ParseUtils;
import cn.com.dtmobile.hadoop.util.StringUtils;

public class MwXdr {
	private Integer length;
	private String city;
	private Integer interfaces;
	private String xdrid;
	private Integer rat;
	private String imsi;
	private String imei;
	private String msisdn;
	private String proceduretype;
	private Long procedurestarttime;
	private Long procedureendtime;
	private String servicetype;
	private String procedurestatus;
	private String calling_number;
	private String called_number;
	private String calling_party_uri;
	private String request_uri;
	private String user_ip;
	private String callid;
	private String icid;
	private String source_ne_ip;
	private String source_ne_port;
	private String dest_ne_ip;
	private String dest_ne_port;
	private String call_side;
	private String source_access_type;
	private String source_eci;
	private String source_tac;
	private String source_imsi;
	private String source_imei;
	private String dest_access_type;
	private String dest_eci;
	private String dest_tac;
	private String dest_imsi;
	private String dest_imei;
	private String auth_type;
	private String expires_time_req;
	private String expires_time_rsp;
	private String calling_sdp_ip_addr;
	private String calling_audio_sdp_port;
	private String calling_video_sdp_port;
	private String called_sdp_ip_addr;
	private String called_audio_sdp_port;
	private String called_video_port;
	private String audio_codec;
	private String video_codec;
	private String redirecting_party_address;
	private String original_party_address;
	private String redirect_reason;
	private String response_code;
	private String finish_warning_code;
	private String finish_reason_protocol;
	private String finish_reason_cause;
	private String firfailtime;
	private String first_fail_ne_ip;
	private String alerting_time;
	private String answer_time;
	private String release_time;
	private String call_duration;
	private String auth_req_time;
	private String auth_rsp_time;
	private String stnsr;
	private String atcfmgmt;
	private String atusti;
	private String cmsisdn;
	private String ssi;
	private String rangeTime;

	public MwXdr(String[] values) {
		this.length=ParseUtils.parseInteger(values[0]);
		this.city=values[1];
		this.interfaces=ParseUtils.parseInteger(values[2]);
		this.xdrid=values[3];
		this.rat=ParseUtils.parseInteger(values[4]);
		this.imsi=values[5];
		this.imei=values[6];
		this.msisdn=values[7];
		this.proceduretype = values[8];
		this.procedurestarttime = ParseUtils.parseLong(values[9]);
		this.procedureendtime = ParseUtils.parseLong(values[10]);
		this.servicetype = values[11];
		this.procedurestatus = values[12];
		this.calling_number = values[13];
		this.called_number = values[14];
		this.calling_party_uri = values[15];
		this.request_uri = values[16];
		this.user_ip = values[17];
		this.callid = values[18];
		this.icid = values[19];
		this.source_ne_ip = values[20];
		this.source_ne_port = values[21];
		this.dest_ne_ip = values[22];
		this.dest_ne_port = values[23];
		this.call_side = values[24];
		this.source_access_type = values[25];
		this.source_eci = values[26];
		this.source_tac = values[27];
		this.source_imsi = values[28];
		this.source_imei = values[29];
		this.dest_access_type = values[30];
		this.dest_eci = values[31];
		this.dest_tac = values[32];
		this.dest_imsi = values[33];
		this.dest_imei = values[34];
		this.auth_type = values[35];
		this.expires_time_req = values[36];
		this.expires_time_rsp = values[37];
		this.calling_sdp_ip_addr = values[38];
		this.calling_audio_sdp_port = values[39];
		this.calling_video_sdp_port = values[40];
		this.called_sdp_ip_addr = values[41];
		this.called_audio_sdp_port = values[42];
		this.called_video_port = values[43];
		this.audio_codec = values[44];
		this.video_codec = values[45];
		this.redirecting_party_address = values[46];
		this.original_party_address = values[47];
		this.redirect_reason = values[48];
		this.response_code = values[49];
		this.finish_warning_code = values[50];
		this.finish_reason_protocol = values[51];
		this.finish_reason_cause = values[52];
		this.firfailtime = values[53];
		this.first_fail_ne_ip = values[54];
		this.alerting_time = values[55];
		this.answer_time = values[56];
		this.release_time = values[57];
		this.call_duration = values[58];
		this.auth_req_time = values[59];
		this.auth_rsp_time = values[60];
		this.stnsr = values[61];
		this.atcfmgmt = values[62];
		this.atusti = values[63];
		this.cmsisdn = values[64];
		this.ssi = values[65];
		this.rangeTime = values[values.length-1];
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(length);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(city);
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
		sb.append(proceduretype);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedurestarttime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedureendtime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(servicetype);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(procedurestatus);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(calling_number);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(called_number);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(calling_party_uri);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(request_uri);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(user_ip);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(callid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(icid);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(source_ne_ip);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(source_ne_port);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(dest_ne_ip);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(call_side);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(source_access_type);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append(source_eci);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( source_tac);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( source_imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( source_imei);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( dest_access_type);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( dest_eci);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( dest_tac);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( dest_imsi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( dest_imei);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( auth_type);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( expires_time_req);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( expires_time_rsp);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( calling_sdp_ip_addr);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( calling_audio_sdp_port);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( calling_video_sdp_port);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( called_sdp_ip_addr);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( called_audio_sdp_port);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( called_video_port);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( audio_codec);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( video_codec);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( redirecting_party_address);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( original_party_address);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( redirect_reason);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( response_code);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( finish_warning_code);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( finish_reason_protocol);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( finish_reason_cause);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( firfailtime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( first_fail_ne_ip);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( alerting_time);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( answer_time);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( release_time);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( call_duration);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( auth_req_time);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( auth_rsp_time);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( stnsr);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( atcfmgmt);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( atusti);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( cmsisdn);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( ssi);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		sb.append( rangeTime);
		sb.append(StringUtils.DELIMITER_INNER_ITEM);
		return sb.toString();
	}








	public String getRangeTime() {
		return rangeTime;
	}


	public void setRangeTime(String rangeTime) {
		this.rangeTime = rangeTime;
	}


	/**
	 * @return the call_side
	 */
	public String getCall_side() {
		return call_side;
	}


	/**
	 * @param call_side the call_side to set
	 */
	public void setCall_side(String call_side) {
		this.call_side = call_side;
	}



	public Integer getLength() {
		return length;
	}


	public void setLength(Integer length) {
		this.length = length;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Integer getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(Integer interfaces) {
		this.interfaces = interfaces;
	}


	public String getXdrid() {
		return xdrid;
	}


	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}


	public Integer getRat() {
		return rat;
	}


	public void setRat(Integer rat) {
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


	public String getProceduretype() {
		return proceduretype;
	}

	public void setProceduretype(String proceduretype) {
		this.proceduretype = proceduretype;
	}

	public Long getProcedurestarttime() {
		return procedurestarttime;
	}

	public void setProcedurestarttime(Long procedurestarttime) {
		this.procedurestarttime = procedurestarttime;
	}

	public Long getProcedureendtime() {
		return procedureendtime;
	}

	public void setProcedureendtime(Long procedureendtime) {
		this.procedureendtime = procedureendtime;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getProcedurestatus() {
		return procedurestatus;
	}

	public void setProcedurestatus(String procedurestatus) {
		this.procedurestatus = procedurestatus;
	}

	public String getCalling_number() {
		return calling_number;
	}

	public void setCalling_number(String calling_number) {
		this.calling_number = calling_number;
	}

	public String getCalled_number() {
		return called_number;
	}

	public void setCalled_number(String called_number) {
		this.called_number = called_number;
	}

	public String getCalling_party_uri() {
		return calling_party_uri;
	}

	public void setCalling_party_uri(String calling_party_uri) {
		this.calling_party_uri = calling_party_uri;
	}

	public String getRequest_uri() {
		return request_uri;
	}

	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getCallid() {
		return callid;
	}

	public void setCallid(String callid) {
		this.callid = callid;
	}

	public String getIcid() {
		return icid;
	}

	public void setIcid(String icid) {
		this.icid = icid;
	}

	public String getSource_ne_ip() {
		return source_ne_ip;
	}

	public void setSource_ne_ip(String source_ne_ip) {
		this.source_ne_ip = source_ne_ip;
	}

	public String getSource_ne_port() {
		return source_ne_port;
	}

	public void setSource_ne_port(String source_ne_port) {
		this.source_ne_port = source_ne_port;
	}

	public String getDest_ne_ip() {
		return dest_ne_ip;
	}

	public void setDest_ne_ip(String dest_ne_ip) {
		this.dest_ne_ip = dest_ne_ip;
	}

	public String getDest_ne_port() {
		return dest_ne_port;
	}

	public void setDest_ne_port(String dest_ne_port) {
		this.dest_ne_port = dest_ne_port;
	}

	public String getSource_access_type() {
		return source_access_type;
	}

	public void setSource_access_type(String source_access_type) {
		this.source_access_type = source_access_type;
	}


	public String getSource_imsi() {
		return source_imsi;
	}

	public void setSource_imsi(String source_imsi) {
		this.source_imsi = source_imsi;
	}

	public String getSource_imei() {
		return source_imei;
	}

	public void setSource_imei(String source_imei) {
		this.source_imei = source_imei;
	}

	public String getDest_access_type() {
		return dest_access_type;
	}

	public void setDest_access_type(String dest_access_type) {
		this.dest_access_type = dest_access_type;
	}



	/**
	 * @return the dest_eci
	 */
	public String getDest_eci() {
		return dest_eci;
	}

	/**
	 * @param dest_eci the dest_eci to set
	 */
	public void setDest_eci(String dest_eci) {
		this.dest_eci = dest_eci;
	}

	public String getDest_tac() {
		return dest_tac;
	}

	public void setDest_tac(String dest_tac) {
		this.dest_tac = dest_tac;
	}

	public String getDest_imsi() {
		return dest_imsi;
	}

	public void setDest_imsi(String dest_imsi) {
		this.dest_imsi = dest_imsi;
	}

	public String getDest_imei() {
		return dest_imei;
	}

	public void setDest_imei(String dest_imei) {
		this.dest_imei = dest_imei;
	}

	public String getAuth_type() {
		return auth_type;
	}

	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}

	public String getExpires_time_req() {
		return expires_time_req;
	}

	public void setExpires_time_req(String expires_time_req) {
		this.expires_time_req = expires_time_req;
	}

	public String getExpires_time_rsp() {
		return expires_time_rsp;
	}

	public void setExpires_time_rsp(String expires_time_rsp) {
		this.expires_time_rsp = expires_time_rsp;
	}

	public String getCalling_sdp_ip_addr() {
		return calling_sdp_ip_addr;
	}

	public void setCalling_sdp_ip_addr(String calling_sdp_ip_addr) {
		this.calling_sdp_ip_addr = calling_sdp_ip_addr;
	}

	public String getCalling_audio_sdp_port() {
		return calling_audio_sdp_port;
	}

	public void setCalling_audio_sdp_port(String calling_audio_sdp_port) {
		this.calling_audio_sdp_port = calling_audio_sdp_port;
	}

	public String getCalling_video_sdp_port() {
		return calling_video_sdp_port;
	}

	public void setCalling_video_sdp_port(String calling_video_sdp_port) {
		this.calling_video_sdp_port = calling_video_sdp_port;
	}

	public String getCalled_sdp_ip_addr() {
		return called_sdp_ip_addr;
	}

	public void setCalled_sdp_ip_addr(String called_sdp_ip_addr) {
		this.called_sdp_ip_addr = called_sdp_ip_addr;
	}

	public String getCalled_audio_sdp_port() {
		return called_audio_sdp_port;
	}

	public void setCalled_audio_sdp_port(String called_audio_sdp_port) {
		this.called_audio_sdp_port = called_audio_sdp_port;
	}

	public String getCalled_video_port() {
		return called_video_port;
	}

	public void setCalled_video_port(String called_video_port) {
		this.called_video_port = called_video_port;
	}

	public String getAudio_codec() {
		return audio_codec;
	}

	public void setAudio_codec(String audio_codec) {
		this.audio_codec = audio_codec;
	}

	public String getVideo_codec() {
		return video_codec;
	}

	public void setVideo_codec(String video_codec) {
		this.video_codec = video_codec;
	}

	public String getRedirecting_party_address() {
		return redirecting_party_address;
	}

	public void setRedirecting_party_address(String redirecting_party_address) {
		this.redirecting_party_address = redirecting_party_address;
	}

	public String getOriginal_party_address() {
		return original_party_address;
	}

	public void setOriginal_party_address(String original_party_address) {
		this.original_party_address = original_party_address;
	}

	public String getRedirect_reason() {
		return redirect_reason;
	}

	public void setRedirect_reason(String redirect_reason) {
		this.redirect_reason = redirect_reason;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getFinish_warning_code() {
		return finish_warning_code;
	}

	public void setFinish_warning_code(String finish_warning_code) {
		this.finish_warning_code = finish_warning_code;
	}

	public String getFinish_reason_protocol() {
		return finish_reason_protocol;
	}

	public void setFinish_reason_protocol(String finish_reason_protocol) {
		this.finish_reason_protocol = finish_reason_protocol;
	}

	public String getFinish_reason_cause() {
		return finish_reason_cause;
	}

	public void setFinish_reason_cause(String finish_reason_cause) {
		this.finish_reason_cause = finish_reason_cause;
	}

	public String getFirfailtime() {
		return firfailtime;
	}

	public void setFirfailtime(String firfailtime) {
		this.firfailtime = firfailtime;
	}

	public String getFirst_fail_ne_ip() {
		return first_fail_ne_ip;
	}

	public void setFirst_fail_ne_ip(String first_fail_ne_ip) {
		this.first_fail_ne_ip = first_fail_ne_ip;
	}

	public String getAlerting_time() {
		return alerting_time;
	}

	public void setAlerting_time(String alerting_time) {
		this.alerting_time = alerting_time;
	}

	public String getAnswer_time() {
		return answer_time;
	}

	public void setAnswer_time(String answer_time) {
		this.answer_time = answer_time;
	}

	public String getRelease_time() {
		return release_time;
	}

	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}

	public String getCall_duration() {
		return call_duration;
	}

	public void setCall_duration(String call_duration) {
		this.call_duration = call_duration;
	}

	public String getAuth_req_time() {
		return auth_req_time;
	}

	public void setAuth_req_time(String auth_req_time) {
		this.auth_req_time = auth_req_time;
	}

	public String getAuth_rsp_time() {
		return auth_rsp_time;
	}

	public void setAuth_rsp_time(String auth_rsp_time) {
		this.auth_rsp_time = auth_rsp_time;
	}

	public String getStnsr() {
		return stnsr;
	}

	public void setStnsr(String stnsr) {
		this.stnsr = stnsr;
	}

	public String getAtcfmgmt() {
		return atcfmgmt;
	}

	public void setAtcfmgmt(String atcfmgmt) {
		this.atcfmgmt = atcfmgmt;
	}

	public String getAtusti() {
		return atusti;
	}

	public void setAtusti(String atusti) {
		this.atusti = atusti;
	}

	public String getCmsisdn() {
		return cmsisdn;
	}

	public void setCmsisdn(String cmsisdn) {
		this.cmsisdn = cmsisdn;
	}

	public String getSsi() {
		return ssi;
	}

	public void setSsi(String ssi) {
		this.ssi = ssi;
	}


	/**
	 * @return the source_eci
	 */
	public String getSource_eci() {
		return source_eci;
	}


	/**
	 * @param source_eci the source_eci to set
	 */
	public void setSource_eci(String source_eci) {
		this.source_eci = source_eci;
	}


	/**
	 * @return the source_tac
	 */
	public String getSource_tac() {
		return source_tac;
	}


	/**
	 * @param source_tac the source_tac to set
	 */
	public void setSource_tac(String source_tac) {
		this.source_tac = source_tac;
	} 

}
