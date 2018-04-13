package com.opengroup.longmao.gwcommon.enums;

/**
 * 支付方式枚举(什么支付方式)
 * @author
 *
 */
public enum PayWayEnum {
	//payWayId:支付方式id;name:支付方式名称;desc:支付方式描述;
	
	ALIPAY((short) 1, "alipay", "支付宝APP支付"),
	ALIPAY_WAP((short) 2, "alipay_wap", "支付宝手机网页支付"),
	ALIPAY_PC_DIRECT((short) 3, "alipay_pc_direct", "支付宝电脑网站支付"),
	ALIPAY_QR((short) 4, "alipay_qr", "支付宝当面付，即支付宝扫码支付"),
	BFB((short) 5, "bfb", "百度钱包移动快捷支付，即百度钱包 APP支付"),
	BFB_WAP((short) 6, "bfb_wap", "百度钱包手机网页支付"),
	CP_B2B((short) 7, "cp_b2b", "银联企业网银支付，即B2B银联PC网页支付"),
	UPACP((short) 8, "upacp", "银联支付，即银联APP支付"),//（2015年1月1日后的银联新商户使用。若有疑问，请与Ping++或者相关的收单行联系）
	UPACP_WAP((short) 9, "upacp_wap", "银联手机网页支付"),//（2015年1月1日后的银联新商户使用。若有疑问，请与Ping++或者相关的收单行联系）
	UPACP_PC((short) 10, "upacp_pc", "银联网关支付，即银联PC网页支付"),
	WX((short) 11, "wx", "微信APP支付"),
	WX_PUB((short) 12, "wx_pub", "微信公众号支付"),
	WX_PUB_QR((short) 13, "wx_pub_qr", "微信公众号扫码支付"),
	WX_WAP((short) 14, "wx_wap", "微信WAP支付（此渠道仅针对特定客户开放）"),
	WX_LITE((short) 15, "wx_lite", "微信小程序支付"),
	YEEPAY_WAP((short) 16, "yeepay_wap", "易宝手机网页支付"),
	JDPAY_WAP((short) 17, "jdpay_wap", "京东手机网页支付"),
	FQLPAY_WAP((short) 18, "fqlpay_wap", "分期乐支付"),
	QGBC_WAP((short) 19, "qgbc_wap", "量化派支付"),
	CMB_WALLET((short) 20, "cmb_wallet", "招行一网通"),
	APPLEPAY_UPACP((short) 21, "applepay_upacp", "Apple Pay"),
	MMDPAY_WAP((short) 22, "mmdpay_wap", "么么贷"),
	QPAY((short) 23, "qpay", "QQ钱包支付"),
	
	PAY_WAY_BEAN((short) 100, "bean", "龙猫豆"),
	PAY_WAY_PLATFORM_FEE((short) 101, "fee", "话费充值"),//互亿无线话费充值
	PAY_WAY_CALORIE((short) 102, "calorie","卡路里"),
	PAY_WAY_ALIPAY_RECHARGE((short) 103, "alipay", "支付宝娱乐直播"),
	PAY_WAY_IOS_INNER((short) 104, "ios", "ios内支付"),
	PAY_WAY_OPAL((short) 105, "opal","贡献值");
	
	//原来的数据，暂时保留，以作参考
//	PAY_WAY_BEAN((short) 5, "bean", "龙猫豆", (short) 1),
//	PAY_WAY_PLATFORM_FEE((short) 6, "fee", "平台扣费", (short) 7),
//	PAY_WAY_CALORIE((short) 7, "calorie","卡路里", (short) 8),
//	PAY_WAY_ALIPAY_RECHARGE((short) 9, "alipay", "支付宝娱乐直播", (short) 9),
//	PAY_WAY_IOS_INNER((short) 4, "ios", "ios内支付", (short) 11),
//	PAY_WAY_OPAL((short) 12, "opal","贡献值", (short) 8);
	
	//payWayId:支付方式id
	private Short payWayId;
	//name:支付方式名称
	private String name;
	//desc:支付方式描述
	private String desc;
	
	public Short getPayWayId() {
		return payWayId;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * 
	 * 【PayMenthodEnum 构造器】
	 * @param payWayId
	 * @param platformId
	 * @param name
	 * @param desc
	 */
	private PayWayEnum(Short payWayId, String name, String desc) {
		this.payWayId = payWayId;
		this.name = name;
		this.desc = desc;
	}

	/**
	 * 
	 * 【通过支付方式id得到枚举信息】
	 * 
	 * @author Hermit 2017年12月5日
	 * @param payWayId
	 * @return
	 */
	public static PayWayEnum getEnumByPayWayId(Short payWayId){
		if (payWayId == null)
            return null;
        for (PayWayEnum payWayTmp : PayWayEnum.values()) {
            if (payWayTmp.getPayWayId().equals(payWayId))
                return payWayTmp;
        }
        return null;
	}
	
	/**
	 * 
	 * 【通过支付方式得到枚举信息】
	 * 
	 * @author Hermit 2017年12月5日
	 * @param desc
	 * @return
	 */
	public static PayWayEnum getEnumByName(String name){
		if (name == null)
            return null;
        for (PayWayEnum payWayTmp : PayWayEnum.values()) {
            if (payWayTmp.getName().equals(name))
                return payWayTmp;
        }
        return null;
	}
	
	/**
	 * 
	 * 【通过支付方式描述得到枚举信息】
	 * 
	 * @author Hermit 2017年12月5日
	 * @param desc
	 * @return
	 */
	public static PayWayEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (PayWayEnum payWayTmp : PayWayEnum.values()) {
            if (payWayTmp.getDesc().equals(desc))
                return payWayTmp;
        }
        return null;
	}
	
}