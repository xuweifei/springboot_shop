package com.opengroup.longmao.gwcommon.enums;


/**
 * 商品类别枚举
 * @author
 *
 */
public enum GoodsCategoryEnum {
	//cateGoryId , typeName , categoryName , price, typeId
	GCTGY_RECHARGE_WX_60(16L,"龙猫豆充值","60币","6",4L),
	GCTGY_RECHARGE_WX_300(17L,"龙猫豆充值","300币","30",4L),
	GCTGY_RECHARGE_WX_980(18L,"龙猫豆充值","980币","98",4L),
	GCTGY_RECHARGE_WX_29800(19L,"龙猫豆充值","2980币","298",4L),
	GCTGY_RECHARGE_WX_5880(20L,"龙猫豆充值","5880币","588",4L),
	GCTGY_RECHARGE_WX_19980(21L,"龙猫豆充值","19980币","1998",4L),
	GCTGY_RECHARGE_WX_10(22L,"龙猫豆充值","10币","1",4L),
	
	GCTGY_RECHARGE_DUIBA(29L,"兑吧商品类别","自定义","1",7L),
	GCTGY_RECHARGE_HUYI(30L,"互亿无线话费充值","自定义","1",8L),
	GCTGY_RECHARGE_CALORIE(31L,"卡路里兑换RMB","自定义","1",9L),
	GCTGY_RECHARGE_ZFB_60(32L,"支付宝娱乐直播充值","60币","6",4L),
	GCTGY_RECHARGE_ZFB_300(33L,"支付宝娱乐直播充值","300币","30",4L),
	GCTGY_RECHARGE_ZFB_1029(34L,"支付宝娱乐直播充值","1029币","98",4L),
	GCTGY_RECHARGE_ZFB_3129(35L,"支付宝娱乐直播充值","3129币","298",4L),
	GCTGY_RECHARGE_ZFB_6174(36L,"支付宝娱乐直播充值","6174币","588",4L),
	GCTGY_RECHARGE_ZFB_20979(37L,"支付宝娱乐直播充值","20979币","1998",4L),
	
	ACTIVITYDRAW(38L,"活动抽奖","0币","0",11L),

	GCTGY_RECHARGE_APPLE_420(39L,"苹果内支付","420豆","6",12L),
	GCTGY_RECHARGE_APPLE_2100(40L,"苹果内支付","2100豆","30",12L),
	GCTGY_RECHARGE_APPLE_1260(52L,"苹果内支付","1260豆","18",12L),
	GCTGY_RECHARGE_APPLE_4200(53L,"苹果内支付","4200豆","60",12L),
	
	GCTGY_RECHARGE_OPAL(41L,"贡献值兑换RMB","自定义","1",13L),//modi by rcj 2017年11月3日09:29:21
	
	GOLDENEGG(49L,"双蛋活动-金蛋礼物","兑换金蛋需要龙猫豆","5000000",11L),
	SILVEREGG(50L,"双蛋活动-银蛋礼物","兑换银蛋需要龙猫豆","1000000",11L),
	COPPEREGGS(51L,"双蛋活动-铜蛋礼物","兑换铜蛋需要龙猫豆","100000",11L),
	;
	
	//类别id
	private Long number;
	//类型名称
	private String desc;
	//类别名称
	private String name;
	//面额
	private String price;
	//类型id
	private Long type;
	
//	public static void main(String[] args) {
//		GoodsCategoryEnum enumm = getEnumByTypeAndPrice(2L, "500");
//		GoodsCategoryEnum enumm2 = getEnumByNumber(4L);
//		System.out.println(enumm.getDesc());
//		System.out.println(enumm2.getDesc());
//	}
	
	private GoodsCategoryEnum (Long number,String desc,String name,String price,Long type) {
		this.number = number;
		this.desc = desc;
		this.name = name;
		this.price = price;
		this.type = type;
	}
	
	public Long getNumber() {
		return number;
	}
	public String getDesc() {
		return desc;
	}
	public Long getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}

	public static GoodsCategoryEnum getEnumByNumber(Long number){
		if (number == null)
            return null;
        for (GoodsCategoryEnum goodsCategoryTmp : GoodsCategoryEnum.values()) {
            if (goodsCategoryTmp.getNumber().equals(number))
                return goodsCategoryTmp;
        }
        return null;
	}
	
	/*public static GoodsCategoryEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (GoodsCategoryEnum goodsCategoryTmp : GoodsCategoryEnum.values()) {
            if (goodsCategoryTmp.getDesc().equals(desc))
                return goodsCategoryTmp;
        }
        return null;
	}*/
	
	public static GoodsCategoryEnum getEnumByName(String name){
		if (name == null)
            return null;
        for (GoodsCategoryEnum goodsCategoryTmp : GoodsCategoryEnum.values()) {
            if (goodsCategoryTmp.getName().equals(name))
                return goodsCategoryTmp;
        }
        return null;
	}
	
	public static GoodsCategoryEnum getEnumByTypeAndPrice(Long type,String price){
		if (type == null)
            return null;
		if (price == null)
            return null;
        for (GoodsCategoryEnum goodsCategoryTmp : GoodsCategoryEnum.values()) {
            if (goodsCategoryTmp.getType()==type&&goodsCategoryTmp.getPrice().equals(price))
                return goodsCategoryTmp;
        }
        return null;
	}
	
}


