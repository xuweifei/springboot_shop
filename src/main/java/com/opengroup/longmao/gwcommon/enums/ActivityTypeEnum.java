package com.opengroup.longmao.gwcommon.enums;
/**
 * 活动类型
 * @author
 *
 */
public enum ActivityTypeEnum {
	//val, name
    BEAN_LUCK_DRAW(Short.parseShort("0"),"龙猫豆抽奖"),
    BEAN_RECEIVE_PACKET(Short.parseShort("1"),"领红包"),
    BEAN_SEND_PACKET(Short.parseShort("2"),"送红包"),
	SMASH_GOLD_EGGS(Short.parseShort("4"),"双蛋活动"),
	BEAN_RECHARGE_GIVE(Short.parseShort("5"),"充值送"),
	;
	private Short val;
	private String desc;

	private ActivityTypeEnum (Short val,String desc) {
		this.val = val;
		this.desc = desc;
	}
	
	public Short getVal() {
		return val;
	}
	public String getDesc() {
		return desc;
	}
		
	public static ActivityTypeEnum getEnumByNumber(Short val){
		if (val == null)
            return null;
        for (ActivityTypeEnum tSORNOTEnum : ActivityTypeEnum.values()) {
            if (tSORNOTEnum.getVal().equals(val))
                return tSORNOTEnum;
        }
        return null;
	}
	
	public static ActivityTypeEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (ActivityTypeEnum tSORNOTEnum : ActivityTypeEnum.values()) {
            if (tSORNOTEnum.getDesc().equals(desc))
                return tSORNOTEnum;
        }
        return null;
	}
	
}


