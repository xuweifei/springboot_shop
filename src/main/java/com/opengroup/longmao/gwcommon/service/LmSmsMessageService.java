package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.LmSmsMessage;

/**
 * 【短信记录表】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年04月27日 下午16:14:13
 */
public interface LmSmsMessageService {

	/**
	 * 【保存短信记录表】
	 * 
	 * @param lmSmsMessage
	 * @return lmSmsMessage
	 * @version 1.0
	 * @author Hermit 2017年04月27日 下午16:14:13
	 */
	LmSmsMessage saveLmSmsMessage(LmSmsMessage lmSmsMessage);

	/**
	 * 【发送短信记录表】
	 * 
	 * @param lmSmsMessage
	 * @return lmSmsMessage
	 * @version 1.0
	 * @author Hermit 2017年04月27日 下午16:14:13
	 */
	LmSmsMessage sendLmSmsMessage(String content, Long userId, String mobile, String type) throws Exception;
	
	
	
	/** 
	 * (获取发送短信配置模版信息) 
	 * @Title getSmsConfig 
	 * @param smsMark 短信标识作为小键
	 * @return Map<String,String>返回类型   
	 * @author ShenZiYang
	 * @date 2018年1月11日下午12:33:34
	 * @throws 异常
	 */
	Map<String,String> getSmsConfig(String smsMark);
	

}
