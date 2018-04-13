
package com.opengroup.longmao.gwcommon.enums;


public enum SmsTypeEnum {
	VERIFY_CODE("verify_code", "验证码类"),
	NOTICE("notice", "通知类"),
	WARNING("warning", "告警类"),
	ADVERT("advert", "营销类");
	private String val;
	private String code;

	private SmsTypeEnum(String val, String code) {
		this.val = val;
		this.code = code;
	}

	public String getVal() {
		return val;
	}

	public String getCode() {
		return code;
	}

	public static SmsTypeEnum getEnumByVal(String val) {
		if (val == null)
			return null;
		for (SmsTypeEnum smsTypeEnum : SmsTypeEnum.values()) {
			if (smsTypeEnum.getVal().equals(val))
				return smsTypeEnum;
		}
		return null;
	}

	public static SmsTypeEnum getEnumByCode(String code) {
		if (code == null)
			return null;
		for (SmsTypeEnum smsTypeEnum : SmsTypeEnum.values()) {
			if (smsTypeEnum.getCode().equals(code))
				return smsTypeEnum;
		}
		return null;
	}
}
