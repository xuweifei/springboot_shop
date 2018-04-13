package com.opengroup.longmao.gwcommon.enums;
/**
 * 龙猫豆流水类型
 * @author
 *
 */
public enum BeanRecordTypeEnum {
	//val, name
	COIN_IN_BEAN(0, "币换豆"),
    GUESSING(1,"竞猜"),
    PK(2,"发起PK"),
	GIFT_GIVE(3, "送礼消费"),
	GIVE(4, "赠送"),
	EXCHANGE(5, "兑换"),
	ERR_RETURN(6, "交易失败返还"),
	REGISTER(7, "注册"),
	REGISTER_GIVE(8, "注册送"),
	BEAN_IN_COIN(9, "豆换币"),
	BEAN_COIN_FEE(10, "豆换币手续费"),
	BEAN_CAPITAL(11, "豆本资"),
	BARRAGE_CONSUME(12, "弹幕消费"),
	AUTO_SUPPLY_BEAN(13, "自动补齐豆"),
	GAMEING(14, "游戏"),
	RECHARGE(100, "充值"),
	RECHARGE_GIVE(101, "充值送"),
	SIGN_REWARD(200, "签到任务奖励"),
	WATCH_LIVE_REWARD(202, "看直播任务奖励"),
	BARRAGE_SPEAK_REWARD(203, "弹幕发言任务奖励"),
	SHARE_ANCHOR_REWARD(204, "分享主播任务奖励"),
	ATTEND_GUESS_REWARD(205, "参与竞猜任务奖励"),
	GIVE_GIFT_REWARD(206, "打赏礼物任务奖励"),
	RECHARGE_REWARD(207, "充值任务奖励"),
	ACTIVITY_GUESS_REWARD(208, "活动竞猜任务奖励"),
	INVITE_REWARD(250, "邀请好友奖励"),
	INVITED_REWARD(251, "接受好友邀请奖励"),
	;
	private Integer val;
	private String desc;

	private BeanRecordTypeEnum (Integer val,String desc) {
		this.val = val;
		this.desc = desc;
	}
	
	public Integer getVal() {
		return val;
	}
	public String getDesc() {
		return desc;
	}
		
	public static BeanRecordTypeEnum getEnumByNumber(Integer val){
		if (val == null)
            return null;
        for (BeanRecordTypeEnum tSORNOTEnum : BeanRecordTypeEnum.values()) {
            if (tSORNOTEnum.getVal().equals(val))
                return tSORNOTEnum;
        }
        return null;
	}
	
	public static BeanRecordTypeEnum getEnumByDesc(String desc){
		if (desc == null)
            return null;
        for (BeanRecordTypeEnum tSORNOTEnum : BeanRecordTypeEnum.values()) {
            if (tSORNOTEnum.getDesc().equals(desc))
                return tSORNOTEnum;
        }
        return null;
	}
	
}