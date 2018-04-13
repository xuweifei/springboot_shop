package com.opengroup.longmao.gwcommon.enums;
/**
 * 商品类型枚举
 * @author
 *
 */
public enum GoodsTypeEnum {
	//typeId , name
	LMB_GTYPE_RECHARGE(4L,"龙猫豆充值"),
	DB_GTYPE_RECHARGE(7L,"龙猫豆兑换"),
	HY_GTYPE_RECHARGE(8L,"话费充值"),
	CALORIE_GTYPE_RECHARGE(9L,"卡路里兑换RMB"),
	ZFB_GTYPE_RECHARGE(10L,"支付宝娱乐直播充值"),
	ACTIVITY_GTYPE_RECHARGE(11L,"活动服务"),
	IOS_GTYPE_RECHARGE(12L,"苹果内支付"),
	OPAL_GTYPE_RECHARGE(13L,"贡献值兑换RMB");
	
	private Long type;
	private String desc;

	private GoodsTypeEnum (Long type,String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public Long getType() {
		return type;
	}
	public String getDesc() {
		return desc;
	}
		
	public static GoodsTypeEnum getEnumByNumber(Long type){
		if (type == null)
            return null;
        for (GoodsTypeEnum goodsTypeTmp : GoodsTypeEnum.values()) {
            if (goodsTypeTmp.getType().equals(type))
                return goodsTypeTmp;
        }
        return null;
	}
	
	public static GoodsTypeEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (GoodsTypeEnum goodsTypeTmp : GoodsTypeEnum.values()) {
            if (goodsTypeTmp.getDesc().equals(desc))
                return goodsTypeTmp;
        }
        return null;
	}
	
}


