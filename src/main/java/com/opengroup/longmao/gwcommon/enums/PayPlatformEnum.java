package com.opengroup.longmao.gwcommon.enums;
/**
 * 支付平台枚举(什么平台支付)
 * @author Hermit
 *
 */
public enum PayPlatformEnum {
	//platformId:支付平台id; name:支付平台名称; desc:支付平台描述
	PAY_PLATFORM_DUIBA((short) 1, "duiba", "兑吧商城"),
	PAY_PLATFORM_PINGXX((short) 2, "pingxx", "ping++支付"),
	PAY_PLATFORM_WECHAT((short) 3, "WECHAT", "微信支付"),
	PAY_PLATFORM_HUYI((short) 7, "ihuyi", "互亿无线"),
	PAY_PLATFORM_TOTORO((short) 8, "totoro", "龙猫平台"),
	PAY_PLATFORM_ALIPAY((short) 9, "alipay", "支付宝"),
//	PAY_PLATFORM_ACTIVITY_SERVE((short) 10, "activity","活动服务"),
	PAY_PLATFORM_IOS((short) 11, "ios", "apple支付");

	//支付平台id
	private Short platformId;
	//支付平台名称
	private String name;
	//支付平台描述
	private String desc;

	public Short getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Short platformId) {
		this.platformId = platformId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private PayPlatformEnum(Short platformId, String name, String desc) {
		this.platformId = platformId;
		this.name = name;
		this.desc = desc;
	}

	public static PayPlatformEnum getEnumByPlatformId(Short platformId){
		if (platformId == null)
            return null;
        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (payPlatformEnum.getPlatformId().equals(platformId)){
                return payPlatformEnum;
            }
        }
        return null;
	}
	
	public static PayPlatformEnum getEnumByName(String name){
		if (name == null)
            return null;
        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (payPlatformEnum.getName().equals(name)){
                return payPlatformEnum;
            }
        }
        return null;
	}
	
	public static PayPlatformEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (payPlatformEnum.getDesc().equals(desc)){
                return payPlatformEnum;
            }
        }
        return null;
	}
	
}