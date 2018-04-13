/**
 * @Title: ImConsumeServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.enums.BeanRecordTypeEnum;
import com.opengroup.longmao.gwcommon.enums.CoinRecordTypeEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.service.BeanAccountService;
import com.opengroup.longmao.gwcommon.service.CoinAccountService;
import com.opengroup.longmao.gwcommon.service.ImConsumeService;

/**
 * @ClassName: ImConsumeServiceImpl 
 * @Description: TODO
 * @author Mr.Zhu
 */
@Service
public class ImConsumeServiceImpl implements ImConsumeService {
	
	@Autowired
	private CoinAccountService coinAccountService;
	
	@Autowired
	private BeanAccountService beanAccountService;
	
	/**
	 * @Title: barrageConsumeCoin
	 * @Description: IM弹幕消息消费(扣币)
	 * @param json
	 * @return Map<String, Object>
	 */
	@Override
	public Boolean barrageConsumeCoin(JSONObject json) {
		String userId = json.getString("userId");

		//弹幕消息消费
		//消耗币账户
		Integer price = 1;
		BigDecimal lmCoinNum = new BigDecimal(price);
		return coinAccountService.updateCoin(userId, lmCoinNum, IsPlusEnum.MINUS.getVal(), CoinRecordTypeEnum.BARRAGE_CONSUME.getVal());
	}
	
	/**
	 * @Title: giftConsumeCoin 
	 * @Description: IM礼物消息消费(扣币) 
	 * @param json
	 * @return Map<String,Object>
	 */
	@Override
	public Boolean giftConsumeCoin(JSONObject json) {
		String userId = json.getString("userId");
		
		//获取礼物信息并消费
		Integer giftNum = json.getInteger("giftNum");
		Integer giftCoin = json.getInteger("giftCoin");
		//消耗币账户
		BigDecimal lmCoinNum = new BigDecimal(giftCoin).multiply(new BigDecimal(giftNum));
		return coinAccountService.updateCoin(userId, lmCoinNum, IsPlusEnum.MINUS.getVal(), CoinRecordTypeEnum.GIFT_GIVE.getVal());
	}
	
	/**
	 * @Title: barrageConsumeBean
	 * @Description: IM弹幕消息消费(扣豆)
	 * @param json
	 * @return Map<String, Object>
	 */
	@Override
	public Boolean barrageConsumeBean(JSONObject json) {
		String userId = json.getString("userId");

		//弹幕消息消费
		//消耗豆账户
		Integer price = 10;
		BigDecimal lmBeanNum = new BigDecimal(price);
		return beanAccountService.updateBean(userId, lmBeanNum, IsPlusEnum.MINUS.getVal(), BeanRecordTypeEnum.BARRAGE_CONSUME.getVal());
	}
	
	/**
	 * @Title: giftConsumeBean 
	 * @Description: IM礼物消息消费(扣豆) 
	 * @param json
	 * @return Map<String,Object>
	 */
	@Override
	public Boolean giftConsumeBean(JSONObject json) {
		String userId = json.getString("userId");
		
		//获取礼物信息并消费
		Integer giftNum = json.getInteger("giftNum");
		//Integer giftCoin = json.getInteger("giftCoin");
		Integer giftBean = json.getInteger("giftBean");
		//消耗豆账户
		//BigDecimal lmBeanNum = new BigDecimal(giftCoin).multiply(new BigDecimal(giftNum)).multiply(new BigDecimal("10"));
		BigDecimal lmBeanNum = new BigDecimal(giftBean).multiply(new BigDecimal(giftNum));
		return beanAccountService.updateBean(userId, lmBeanNum, IsPlusEnum.MINUS.getVal(), BeanRecordTypeEnum.GIFT_GIVE.getVal());
	}
	
}
