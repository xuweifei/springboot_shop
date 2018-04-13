package com.opengroup.longmao.gwcommon.enums;

public enum ChannelTypeEnum {

	SELF(Short.parseShort("1"), "自营渠道"), 
	PROXY(Short.parseShort("2"), "代理渠道"),
	;
	
	private Short type;
	private String desc;

	private ChannelTypeEnum(Short type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Short getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public static ChannelTypeEnum getEnumByType(Short type) {
		if (null == type) {
			return null;
		}
		for (ChannelTypeEnum c : ChannelTypeEnum.values()) {
			if (c.getType().equals(type)) {
				return c;
			}
		}
		return null;
	}

	public static ChannelTypeEnum getEnumByDesc(String desc) {
		if (null == desc) {
			return null;
		}
		for (ChannelTypeEnum c : ChannelTypeEnum.values()) {
			if (desc.equals(c.getDesc())) {
				return c;
			}
		}
		return null;
	}

}
