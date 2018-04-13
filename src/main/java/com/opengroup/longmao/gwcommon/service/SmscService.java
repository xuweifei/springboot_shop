/**
 * @Title: SmscService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import com.opengroup.longmao.gwcommon.entity.po.LmSmsMessage;

/**
 * @ClassName: SmscService 
 * @Description: TODO
 * @author Mr.Zhu
 */
public interface SmscService {
	
	/**
	 * @Title: sendCommonSms 
	 * @Description: 通用短信发送 
	 * @param userName
	 * @return LmSmsMessage
	 */
	LmSmsMessage sendCommonSms(String userName);
	
	/**
	 * @Title: sendCommonSmsByUserId
	 * @Description: 通用短信发送(userId查询UserName)
	 * @param userId
	 * @return LmSmsMessage
	 */
	LmSmsMessage sendCommonSmsByUserId(String userId);
	
	/**
	 * @Title: sendRegister 
	 * @Description: 发送注册验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	LmSmsMessage sendRegister(String userName);
	
	/**
	 * @Title: sendLogin 
	 * @Description: 发送快速登录验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	LmSmsMessage sendLogin(String userName);
	
	/**
	 * @Title: sendFindPwd 
	 * @Description: 发送找回密码验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	LmSmsMessage sendFindPwd(String userName);
	
	/**
	 * @Title: sendChangeMobile 
	 * @Description: 发送更换手机号验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	LmSmsMessage sendChangeMobile(String userName);
}
