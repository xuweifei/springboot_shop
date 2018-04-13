package com.opengroup.longmao.gwcommon.enums;

public enum IdentityAuthStatusEnum {
	IDENTITY_UNDER_REVIEW(Short.parseShort("0"), "实名认证审核中"), 
	IDENTITY_PASS(Short.parseShort("1"), "实名认证已通过"), 
	IDENTITY_NO_PASS(Short.parseShort("2"), "实名认证未通过"),
	;
	private Short type;
	private String explain;

	private IdentityAuthStatusEnum(Short type, String explain) {
		this.type = type;
		this.explain = explain;
	}

	public Short getType() {
		return type;
	}

	public String getExplain() {
		return explain;
	}

	public static IdentityAuthStatusEnum getEnumByType(Short type) {
		if (type == null)
			return null;
		for (IdentityAuthStatusEnum anchorStatusEnum : IdentityAuthStatusEnum.values()) {
			if (anchorStatusEnum.getType().equals(type))
				return anchorStatusEnum;
		}
		return null;
	}

	public static IdentityAuthStatusEnum getEnumByExplain(String explain) {
		if (explain == null)
			return null;
		for (IdentityAuthStatusEnum anchorStatusEnum : IdentityAuthStatusEnum.values()) {
			if (anchorStatusEnum.getExplain().equals(explain))
				return anchorStatusEnum;
		}
		return null;
	}

}
