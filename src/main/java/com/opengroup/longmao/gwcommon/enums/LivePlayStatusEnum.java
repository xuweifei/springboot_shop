package com.opengroup.longmao.gwcommon.enums;
/**
 * 主播播放状态枚举
 * @author
 *
 */
public enum LivePlayStatusEnum {
	//val, name
	ING_PLAY(Short.parseShort("0"),"上线"),
	UN_PLAY(Short.parseShort("1"),"下线"),
	READY_PLAY(Short.parseShort("2"),"准备上线");
	private Short type;
	private String desc;

	private LivePlayStatusEnum (Short type,String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public Short getType() {
		return type;
	}
	public String getDesc() {
		return desc;
	}
		
	public static LivePlayStatusEnum getEnumByNumber(Short type){
		if (type == null)
            return null;
        for (LivePlayStatusEnum payFlowStatusTmp : LivePlayStatusEnum.values()) {
            if (payFlowStatusTmp.getType().equals(type))
                return payFlowStatusTmp;
        }
        return null;
	}
	
	public static LivePlayStatusEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (LivePlayStatusEnum payFlowStatusTmp : LivePlayStatusEnum.values()) {
            if (payFlowStatusTmp.getDesc().equals(desc))
                return payFlowStatusTmp;
        }
        return null;
	}
	
}


