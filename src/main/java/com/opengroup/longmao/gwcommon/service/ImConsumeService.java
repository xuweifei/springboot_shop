/**
 * @Title: ImConsumeService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ImConsumeService 
 * @Description: TODO
 * @author Mr.Zhu
 */
public interface ImConsumeService {

	/**
	 * @Title: barrageConsumeCoin 
	 * @Description: IM弹幕消息消费(扣币) 
	 * @param json
	 * @return Boolean
	 */
	Boolean barrageConsumeCoin(JSONObject json);
	
	/**
	 * @Title: giftConsumeCoin
	 * @Description: IM礼物消息消费(扣币)
	 * @param json
	 * @return Boolean
	 */
	Boolean giftConsumeCoin(JSONObject json);
	
	/**
	 * @Title: barrageConsumeBean
	 * @Description: IM弹幕消息消费(扣豆) 
	 * @param json
	 * @return Boolean
	 */
	Boolean barrageConsumeBean(JSONObject json);
	
	/**
	 * @Title: giftConsumeBean
	 * @Description: IM礼物消息消费(扣豆)
	 * @param json
	 * @return Boolean
	 */
	Boolean giftConsumeBean(JSONObject json);
}
