package com.opengroup.longmao.gwcommon.enums;

/**
 * 全服统计的类型
 * 
 * @author hjy
 *
 * 2017年12月21日 下午2:37:54
 */
public enum AllCountTypeEnum {
	//val, name
    COUNT_CALORIE_RECEIVER(Short.parseShort("1"),"明星主播榜"),
    COUNT_ROB_PROFIT_USERID(Short.parseShort("2"),"竞猜收益榜"),
    COUNT_CALORIE_SENDER(Short.parseShort("3"),"土豪守护榜"),
	;
	private Short val;
	private String desc;

	private AllCountTypeEnum (Short val,String desc) {
		this.val = val;
		this.desc = desc;
	}
	
	public Short getVal() {
		return val;
	}
	public String getDesc() {
		return desc;
	}
		
	public static AllCountTypeEnum getEnumByNumber(Short val){
		if (val == null)
            return null;
        for (AllCountTypeEnum tSORNOTEnum : AllCountTypeEnum.values()) {
            if (tSORNOTEnum.getVal().equals(val))
                return tSORNOTEnum;
        }
        return null;
	}
	
	public static AllCountTypeEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (AllCountTypeEnum tSORNOTEnum : AllCountTypeEnum.values()) {
            if (tSORNOTEnum.getDesc().equals(desc))
                return tSORNOTEnum;
        }
        return null;
	}
}
