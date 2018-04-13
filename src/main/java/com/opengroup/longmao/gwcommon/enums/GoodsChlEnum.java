package com.opengroup.longmao.gwcommon.enums;
/**
 * 商品渠道枚举
 * @author
 *
 */
public enum GoodsChlEnum {
	//goodChlId, name,desc
	GOODS_CHL_DUIBA_SHOP(1L,"goods_chl_duiba_shop","兑吧商城商品"),
	GOODS_CHL_TOTORO_RECHARGE(2L,"goods_chl_totoro_recharge","龙猫豆充值商品(支付宝娱乐充值除外)"),
	GOODS_CHL_HUYI(7L,"goods_chl_huyi","互亿无线商品"),
	GOODS_CHL_TOTORO_CALORIE(8L,"goods_chl_totoro_calorie","龙猫直播卡路里商品"),
	GOODS_CHL_ALIPAY_RECHARGE(9L,"goods_chl_alipay_recharge","支付宝娱乐直播商品"),
	GOODS_CHL_ACTIVITY_SERVE(10L,"goods_chl_activity_serve","活动服务商品"),
	GOODS_CHL_IOS_PAY(11L,"goods_chl_ios_pay","apple内购商品"),
	GOODS_CHL_TOTORO_OPAL(12L,"goods_chl_totoro_opal","龙猫直播贡献值商品");
	
	private Long goodChlId;
	private String name;
	private String desc;

	public Long getGoodChlId() {
		return goodChlId;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * 
	 * 【 GoodsChlEnum 构造器】
	 * @param goodChlId
	 * @param name
	 * @param desc
	 */
	private GoodsChlEnum(Long goodChlId, String name, String desc) {
		this.goodChlId = goodChlId;
		this.name = name;
		this.desc = desc;
	}

	/**
	 * 
	 * 【通过商品渠道id查询枚举】
	 * 
	 * @author Hermit 2017年12月6日
	 * @param goodChlId
	 * @return
	 */
	public static GoodsChlEnum getEnumByGoodChlId(Long goodChlId){
		if (goodChlId == null)
            return null;
        for (GoodsChlEnum goodsChlEnum : GoodsChlEnum.values()) {
            if (goodsChlEnum.getGoodChlId().equals(goodChlId))
                return goodsChlEnum;
        }
        return null;
	}
	
	/**
	 * 
	 * 【通过商品渠道name查询枚举】
	 * 
	 * @author Hermit 2017年12月6日
	 * @param name
	 * @return
	 */
	public static GoodsChlEnum getEnumByName(String name){
		if (name == null)
            return null;
        for (GoodsChlEnum goodsChlEnum : GoodsChlEnum.values()) {
            if (goodsChlEnum.getName().equals(name))
                return goodsChlEnum;
        }
        return null;
	}
	
	/**
	 * 
	 * 【通过商品渠道描述查询枚举】
	 * 
	 * @author Hermit 2017年12月6日
	 * @param desc
	 * @return
	 */
	public static GoodsChlEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (GoodsChlEnum chlInfoTmp : GoodsChlEnum.values()) {
            if (chlInfoTmp.getDesc().equals(desc))
                return chlInfoTmp;
        }
        return null;
	}
	
}


