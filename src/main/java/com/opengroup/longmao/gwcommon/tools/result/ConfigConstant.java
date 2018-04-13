package com.opengroup.longmao.gwcommon.tools.result;

/**
 * @ClassName: ConfigConstant 
 * @Description: 配置参数常量
 * @author Mr.Zhu
 */
public class ConfigConstant {
	/** 常用 **/
	// 用户关系信息
	public static final String USER_RELATION_INFO = "USER_RELATION_INFO:";
	public static final String IS_FORBID = "USER_RELATION_INFO:isForbid";// 是否禁言
	public static final String IS_APP_ADMIN = "USER_RELATION_INFO:isAppAdmin";// 是否场控
	public static final String IS_LIVE_ADMIN = "USER_RELATION_INFO:isLiveAdmin";// 是否超管
	public static final String IS_OVERALL_FORBID = "USER_RELATION_INFO:isOverallForbid";// 是否全局禁言
	public static final String FANS_COUNT = "USER_RELATION_INFO:fansCount";//粉丝数
	public static final String FOLLOW_COUNT = "USER_RELATION_INFO:followCount";//关注数
	public static final String IS_LOGIN = "USER_RELATION_INFO:login";//是否登录
	// APP信息
	public static final String SDK_INFO = "SDK_INFO:";
	public static final String APP_SIG = "SDK_INFO:APP_SIG";// APP管理SIG
	// 主播开播轮询
	public static final String LIVE_STATUS = "ANCHOR_PLAY_STATUS:ID_";
	// 是否主播权限
	public static final String ANCHOR_INFO = "ANCHOR_INFO:";
	public static final String IS_ANCHOR = "ANCHOR_INFO:IS_ANCHOR";
	public static final String FUZZY_QUERY = "ANCHOR_INFO:FUZZY_QUERY";
	//直播列表
	public static final String LIVE_LIST = "LIVE_LIST:";
	public static final String LIVE_LIST_FORUM = "LIVE_LIST:FORUM_";
	public static final String LIVE_LIST_INFO = "LIVE_LIST:INFO";
	public static final String LIVE_HOT = "HOT";
	public static final String LIVE_NEWEST = "NEWEST";
	public static final String LIVE_FORUM = "LIVE_LIST:FORUM_CONFIG";
	public static final String LIVE_FORUM_FUNC = "LIVE_LIST:FORUM_FUNC";
	// 系统开关配置
	public static final String SYS_CONFIG = "SYS_CONFIG:";
	public static final String SYS_ALL_CONFIG = "SYS_CONFIG:All";
	// 群组永久禁言
	public static final Integer FORERVER = 315360000;
	// 群组解除禁言
	public static final Integer FORZERO = 0;
	// 主播开播状态失效时间
	public static final Long LIVE_LAPSE_TIME = 300L;
	/** **/
	public static final String SEE_LIVE = "SEE_";

	// 排序
	public static final String SORT_FIELD_CTIME = "ctime";

	// 方法名常量
	public static final String METHODNAME = "methodName";

	// 微信公众号充值类型商品前缀
	public static final String WX_PUB_RECHARGE_GOODS_TYPE_PREFIX = "WX_PUB_RECHARGE_GOODS_TYPE_PREFIX_";

	public static final String MOBILE_SMSC_CAPTCHA = "MOBILE_SMSC_CAPTCHA_";// 通用短讯Redis前缀
	public static final String REGISTER_MSG = "REGISTER_";// 注册
	public static final String LOGIN_MSG = "LOGIN_";// 快速登录
	public static final String FINDBACKPWD_MSG = "FINDBACKPWD_";// 找回密码
	public static final String CHANGEMOBILE_MSG = "CHANGEMOBILE_";// 更换手机号码
	public static final String SENSITIVE_FILTERING = "SENSITIVE_FILTERING:";// 敏感词汇过滤前缀
	public static final String SENSITIVE_FILTERING_WORD = "SENSITIVE_FILTERING:WORD";//敏感词汇前缀
	public static final String SENSITIVE_FILTERING_PAUSE = "PAUSE";//敏感词汇分隔停顿符前缀
	// 腾讯云SIG失效时间
	public static final int SIG_EXPIRE = 165;
	// 龙猫卖家id
	public static final Long TOTORO_SELLER_ID = 100L;

	/**
	 * jwt
	 */
	public static final String TOKEN = "token_";
	public static final String REFRESH_TOKEN = "refreshtoken_";
	public static final String TYPE = "type_";
	public static final String JWT_ID = "jwt";
	public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
	public static final long JWT_TTL = 60 * 60 * 1000; // millisecond
	// public static final long JWT_TTL = 60*1000; //millisecond
	public static final long JWT_REFRESH_TTL = 12 * 60 * 60 * 1000; // millisecond

	// Android开关配置
	public static final String VERSION_INFO = "VERSION_INFO:";
	// IOS开关配置
	public static final String APP_ONOFF = "APP_ONOFF:";
	// jwttoken
	public static final String JWT_TOKEN = "JWT_TOKEN:";

	// 全服排行统计Redis
	public static final String ALL_COUNT_RANKING = "ALL_COUNT_RANKING:";

	// 用户配置
	public static final String USER_CONFIG = "USER_CONFIG:";
	// 邀请好友配置
	public static final String INVITE_CONFIG = "invite_config";
	
	//平台管理id
  	public static final Long OWN_MANAGE_SYSTEM_ID = 888888L;
	public static final Long OWN_MANAGE_MINUS_ID = 777777L;

	// ----------------- add by szy 2018.1.9 运营后台短信模版配置
	// beg-------------------------

	// 短信模版配置 大键
	public static final String LM_SMS_CONFIG = "lm_sms_config:";

	// 短信模版配置 小键 (短信标识--更换手机号验证码短信通知)
	public static final String CHANGE_MOBILE = "change_mobile";

	// 短信模版配置 小键 (短信标识--通用短信发送)
	public static final String COMMON_CMS = "common_sms";

	// 短信模版配置 小键 (短信标识--找回密码验证码通知)
	public static final String FIND_BACK_PWD = "find_back_pwd";

	// 短信模版配置 小键 (短信标识--快速登录验证码通知)
	public static final String LOGIN_SMS = "login_totoro";

	// 短信模版配置 小键 (短信标识--注册验证码短信通知)
	public static final String REGISTER_SMS = "register_totoro";
	
	//短信失效时间
	public static final Long SMS_EXPIRE_TIME = 600L;
	
	//-----------------add by szy 2018.1.9 运营后台短信模版配置 end-------------------------
	/* 帮助中心 */
	public static final String QUESTION_ANSWER_TYPE = "HELP_CENTER:QUESTION_ANSWER_TYPE";
	
	// 商城登录key前缀&保存时间
	public static final String SHOP_LOGIN_KEY_PREFIX = "SHOP_LOGIN_PREFIX";
	public static final Long SHOP_LOGIN_KEY_TIMEOUT = 1 * 60L;
	
}
