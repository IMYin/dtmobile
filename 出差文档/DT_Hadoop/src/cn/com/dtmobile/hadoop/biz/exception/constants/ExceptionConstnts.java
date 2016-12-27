package cn.com.dtmobile.hadoop.biz.exception.constants;

public class ExceptionConstnts {
	//IMS注册失败
	public static final int IMS_REG_FAIL = 1;
	//附着失败
	public static final int ATTACH_FAIL = 2;
	//Service Request失败
	public static final int SVR_REQ_FAIL = 3;
	//VoLTE语音未接通（VoLTE语音始呼失败）
	public static final int VOLET_VOICE_CALL_FAIL = 4;
	//VoLTE语音掉线
	public static final int VOLET_VOICE_OFFLINE_FAIL = 5;
	//VoLTE视频未接通（VoLTE视频始呼失败）
	public static final int VOLET_VIEDO_CALL_FAIL = 6;
	//VoLTE视频掉线
	public static final int VOLET_VIEDO_OFFLINE_FAIL = 7;
	//TAU失败
	public static final int TAU_FAIL = 8;
	//4G掉线
	public static final int FG_OFFLINE_FAIL = 9;
	//4G内切换失败
	public static final int FG_SWITCH_FAIL = 10;
	//CSFB失败
	public static final int CSFB_FAIL = 12;
	//eSRVCC切换失败
	public static final int ESRVCC_FAIL = 13;
    //数据脱网（含VoLTE脱网）
	public static final int ESC_NET_FAIL = 14;
	
	public static final String MASTER_CALL = "0";
}
