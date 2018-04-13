package com.opengroup.longmao.gwcommon.enums;


public enum UserInfoEnum {
	ORDINARY(Short.valueOf("0"), "普通用户"), 
	ANCHOR(Short.valueOf("1"), "主播"), 
	ENTERPRISE(Short.valueOf("2"), "企业"),
	CARENUMBER(Short.valueOf("3"), "托号用户"),
	ROBOT(Short.valueOf("10"), "机器人"), 
	SEX_WOMEN(Short.valueOf("1"),"女"), 
	SEX_MAN(Short.valueOf("0"),"男"),  
	USER_OPEN(Short.valueOf("0"),"开启"), 
	USER_BAN(Short.valueOf("1"),"封禁"),
	USER_OPEN_MSG(Short.valueOf("2"),"开启/禁言"),
	USER_BAN_MSG(Short.valueOf("3"),"封禁/禁言"),
	;
	private Short type;
	private String explain;

	private UserInfoEnum (Short type,String explain) {
		this.type = type;
		this.explain = explain;
	}
	
	public Short getType() {
		return type;
	}
	public String getExplain() {
		return explain;
	}
		
	public static UserInfoEnum getEnumByType(Short type){
		if (type == null)
            return null;
        for (UserInfoEnum userInfoEnum : UserInfoEnum.values()) {
            if (userInfoEnum.getType().equals(type))
                return userInfoEnum;
        }
        return null;
	}
	
	public static UserInfoEnum getEnumByExplain(String explain){
		if (explain == null)
            return null;
        for (UserInfoEnum userInfoEnum : UserInfoEnum.values()) {
            if (userInfoEnum.getExplain().equals(explain))
                return userInfoEnum;
        }
        return null;
	}
	
}


