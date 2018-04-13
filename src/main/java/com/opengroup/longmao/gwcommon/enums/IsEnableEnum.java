package com.opengroup.longmao.gwcommon.enums;
public enum IsEnableEnum {


	ENABLE(Short.parseShort("1"),"可用"),
	UN_ENABLE(Short.parseShort("2"),"不可用");
	
	private Short type;
	private String desc;
	
	private IsEnableEnum(Short type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static IsEnableEnum getEnumByType(Short type) {
		if(null == type) {
			return null;
		}
		for(IsEnableEnum isEnableEnum:IsEnableEnum.values()) {
			if(isEnableEnum.getType().equals(type)) {
				return isEnableEnum;
			}
		}
		return null;
	}
	
	public static IsEnableEnum getEnumByDesc(String desc) {
		if(null == desc) {
			return null;
		}
		for(IsEnableEnum isEnableEnum:IsEnableEnum.values()) {
			if(isEnableEnum.getDesc().equals(desc)) {
				return isEnableEnum;
			}
		}
		return null;
	}
}
