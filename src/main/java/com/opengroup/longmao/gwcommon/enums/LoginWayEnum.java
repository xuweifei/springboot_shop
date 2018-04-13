package com.opengroup.longmao.gwcommon.enums;

/**
 * @ClassName: LoginWayEnum
 * @Description: 登入途径枚举
 * @author Mr.Zhu
 */
public enum LoginWayEnum {
	MOBILE((short) 0, "手机绑定"),
	MOBILE_PSSWD((short) 1, "手机密码"),
	MOBILE_CAPTCHA((short) 2, "手机验证码"),
	WECHAT_WARRANTY((short) 3, "微信授权"),
	QQ_WARRANTY((short) 4, "QQ授权"),
	WEIBO_WARRANTY((short) 5, "微博授权"),
	MOB_WECHAT_WARRANTY((short) 6, "MOB-微信授权"),
	MOB_QQ_WARRANTY((short) 7, "MOB-QQ授权"),
	MOB_WEIBO_WARRANTY((short) 8, "MOB-微博授权");
	private Short val;
	private String explain;

	private LoginWayEnum(Short val, String explain) {
		this.val = val;
		this.explain = explain;
	}

	public Short getVal() {
		return val;
	}

	public String getExplain() {
		return explain;
	}

	public static LoginWayEnum getEnumByVal(Short val) {
		if (null == val)
			return null;
		for (LoginWayEnum loginWayEnum : LoginWayEnum.values()) {
			if (loginWayEnum.getVal() == val)
				return loginWayEnum;
		}
		return null;
	}

	public static LoginWayEnum getEnumByExplain(String explain) {
		if (null == explain)
			return null;
		for (LoginWayEnum loginWayEnum : LoginWayEnum.values()) {
			if (loginWayEnum.getExplain().equals(explain))
				return loginWayEnum;
		}
		return null;
	}

}
