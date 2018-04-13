package com.opengroup.longmao.gwcommon.enums;

public enum IsReceivableEnum {
	
	CAN_RECEIVE(Short.valueOf("1"),"可领取"),
	CAN_NOT_RECEIVE(Short.valueOf("2"),"不可领取"),
	HAVE_RECEIVED(Short.valueOf("3"),"已领取"),
	DUPLICATE_INVITE(Short.valueOf("4"),"重复邀请")
	;
	
	private Short val;
	private String desc;
	
	private IsReceivableEnum(Short val, String desc) {
		this.val = val;
		this.desc = desc;
	}

	public Short getVal() {
		return val;
	}

	public String getDesc() {
		return desc;
	}
	
	public static IsReceivableEnum getEnumByVal(Short val) {
		if (null == val) {
			return null;
		}
		for (IsReceivableEnum i : IsReceivableEnum.values()) {
			if (val.equals(i.getVal())) {
				return i;
			}
		}
		return null;
	}
	
	public static IsReceivableEnum getEnumByVal(String desc) {
		if (null == desc) {
			return null;
		}
		for (IsReceivableEnum i : IsReceivableEnum.values()) {
			if (desc.equals(i.getDesc())) {
				return i;
			}
		}
		return null;
	}
	
}
