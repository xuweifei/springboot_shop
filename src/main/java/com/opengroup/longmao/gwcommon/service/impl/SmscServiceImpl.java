/**
 * @Title: SmscServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.LmSmsMessage;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.enums.IsEnableEnum;
import com.opengroup.longmao.gwcommon.enums.SmsTypeEnum;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.LmSmsMessageService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.SmscService;
import com.opengroup.longmao.gwcommon.tools.idutil.IdUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.sdk.smsc.SmscYunPian;

/**
 * @ClassName: SmscServiceImpl 
 * @Description: TODO
 * @author Mr.Zhu
 */
@Service
public class SmscServiceImpl implements SmscService {
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private LmSmsMessageService lmSmsMessageService;
	
	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private SmscYunPian smscYunPian;
	
	/**
	 * @Title: sendCommonSms
	 * @Description: 通用短信发送
	 * @param userName
	 * @return LmSmsMessage
	 */
	@Override
	public LmSmsMessage sendCommonSms(String userName) {
		if (null == userName) {
			throw new ImplException(CommConstant.GWSCOD0001, "参数错误,无法发生验证短信!");
		}
		
		User user = queryUserByUserName(userName);
		if (null != user) {
			throw new ImplException(CommConstant.GWSCOD0009, CommConstant.GWSMSG0009);
		}
		
		
		// 获取通用短信发送短信模版 modify by szy 2018.1.9 beg
		Map<String, String> smsConfigMap = lmSmsMessageService.getSmsConfig(ConfigConstant.COMMON_CMS);
		String smsTemplate = smsConfigMap.get("content");
		String status = smsConfigMap.get("smsStatus");
		Short smsStatus = Short.valueOf(status);
		
		//判断短信发送状态是否启用
		if (IsEnableEnum.UN_ENABLE.getType().equals(smsStatus)) {
			GwsLogger.info("发送通用短信验证码通知已停用(1:启用,2:停用),当前状态为:smsStatus={}", smsStatus);
			throw new ImplException(CommConstant.GWSCOD0001, "系统异常!");
		}
		// 获取通用短信发送短信模版 modify by szy 2018.1.9 end
		
		
		String messagecode = IdUtil.getMessageCode(new Random().nextInt(1000000));
		
		//String content = "提醒您，您的验证码：" + messagecode + "。如非本人操作，请忽略本消息。";
		String content = smsTemplate.replace("messagecode", messagecode); //modify by szy 2018.1.9
		
		redis.set(ConfigConstant.MOBILE_SMSC_CAPTCHA, userName, messagecode, ConfigConstant.SMS_EXPIRE_TIME);
	    try {
	    	return lmSmsMessageService.sendLmSmsMessage(content, null, userName, SmsTypeEnum.VERIFY_CODE.getVal());
		} catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
	}
	
	/**
	 * @Title: sendCommonSmsByUserId
	 * @Description: 通用短信发送(userId查询UserName)
	 * @param userId
	 * @return LmSmsMessage
	 */
	@Override
	public LmSmsMessage sendCommonSmsByUserId(String userId) {
		User user = queryUserByUserId(userId);
		if (null == user) {
			throw new ImplException(CommConstant.GWSCOD0001, "参数错误,无法发生验证短信!");
		}
		String messagecode = IdUtil.getMessageCode(new Random().nextInt(1000000));
		String content = "提醒您，您的验证码：" + messagecode + "。如非本人操作，请忽略本消息。";
		redis.set(ConfigConstant.MOBILE_SMSC_CAPTCHA, userId, messagecode, ConfigConstant.SMS_EXPIRE_TIME);
		try {
			return lmSmsMessageService.sendLmSmsMessage(content, Long.valueOf(user.getUserId()), user.getUserName(),
					SmsTypeEnum.VERIFY_CODE.getVal());
		} catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
	}
	
	/**
	 * @Title: sendRegister 
	 * @Description: 发送注册验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	@Override
	public LmSmsMessage sendRegister(String userName) {
		if (null == userName) {
			throw new ImplException(CommConstant.GWSCOD0001, "参数错误,无法发生验证短信!");
		}
		
		User user = queryUserByUserName(userName);
		if (null != user) {
			throw new ImplException(CommConstant.GWSCOD0009, CommConstant.GWSMSG0009);
		}
		
		// 获取发送注册验证码短信模版 modify by szy 2018.1.9 beg
		Map<String, String> smsConfigMap = lmSmsMessageService.getSmsConfig(ConfigConstant.REGISTER_SMS);
		String smsTemplate = smsConfigMap.get("content");
		String status = smsConfigMap.get("smsStatus");
		Short smsStatus = Short.valueOf(status);
		
		//判断短信发送状态是否启用
		if (IsEnableEnum.UN_ENABLE.getType().equals(smsStatus)) {
			GwsLogger.info("发送注册验证码短信通知已停用(1:启用,2:停用),当前状态为:smsStatus={}", smsStatus);
			throw new ImplException(CommConstant.GWSCOD0001, "系统异常!");
		}
		// 获取发送注册验证码短信模版 modify by szy 2018.1.9 end
		
		String messagecode = IdUtil.getMessageCode(new Random().nextInt(1000000));
//		String content = "提醒您，您正在注册新用户，验证码：" + messagecode + "。如非本人操作，请忽略本消息。";
		String content = smsTemplate.replace("messagecode", messagecode); //modify by szy 2018.1.9
		
		redis.set(ConfigConstant.REGISTER_MSG, userName, messagecode, ConfigConstant.SMS_EXPIRE_TIME);
	    try {
	    	return lmSmsMessageService.sendLmSmsMessage(content, null, userName, SmsTypeEnum.VERIFY_CODE.getVal());
	    } catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
	}
	
	/**
	 * @Title: sendLogin 
	 * @Description: 发送快速登录验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	@Override
	public LmSmsMessage sendLogin(String userName) {
		if (null == userName) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
		
		//获取发送快速登录验证码 短信模版 modify by szy 2018.1.9 beg
		Map<String, String> smsConfigMap = lmSmsMessageService.getSmsConfig(ConfigConstant.LOGIN_SMS);
		String smsTemplate = smsConfigMap.get("content");
		String status = smsConfigMap.get("smsStatus");
		Short smsStatus = Short.valueOf(status);
		
		//判断短信发送状态是否启用
		if (IsEnableEnum.UN_ENABLE.getType().equals(smsStatus)){
			GwsLogger.info("发送快速登录验证码短信通知已停用(1:启用,2:停用),当前状态为:smsStatus={}",smsStatus);
    		throw new ImplException(CommConstant.GWSCOD0001, "系统异常!");
		}
		//获取发送快速登录验证码短信模版 modify by szy 2018.1.9 end
		
		String messagecode = IdUtil.getMessageCode(new Random().nextInt(1000000));
//		String content = "提醒您，您正在登录{龙猫直播}，验证码：" + messagecode + "。如非本人操作，请忽略本消息。";
		String content = smsTemplate.replace("messagecode", messagecode);
		
		redis.set(ConfigConstant.LOGIN_MSG, userName, messagecode, ConfigConstant.SMS_EXPIRE_TIME);
		try {
			return lmSmsMessageService.sendLmSmsMessage(content, null, userName, SmsTypeEnum.VERIFY_CODE.getVal());
		} catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
	}
	
	/**
	 * @Title: sendFindPwd 
	 * @Description: 发送找回密码验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	@Override
	public LmSmsMessage sendFindPwd(String userName) {
		if (null == userName) {
			throw new ImplException(CommConstant.GWSCOD0001, "参数错误,无法发生验证短信!");
		}
		
		User user = queryUserByUserName(userName);
		if (null == user) {
			throw new ImplException(CommConstant.GWSCOD0010, CommConstant.GWSMSG0010);
		}
		
		//获取发送找回密码验证码短信模版 modify by szy 2018.1.9 beg
		Map<String, String> smsConfigMap = lmSmsMessageService.getSmsConfig(ConfigConstant.FIND_BACK_PWD);
		String smsTemplate = smsConfigMap.get("content");
		String status = smsConfigMap.get("smsStatus");
		Short smsStatus = Short.valueOf(status);
		
		//判断短信发送状态是否启用
		if (IsEnableEnum.UN_ENABLE.getType().equals(smsStatus)){
			GwsLogger.info("发送找回密码验证码短信通知已停用(1:启用,2:停用),当前状态为:smsStatus={}",smsStatus);
    		throw new ImplException(CommConstant.GWSCOD0001, "系统异常!!");
		}
		
		//获取发送找回密码验证码短信模版 modify by szy 2018.1.9 end
		
		String messagecode = IdUtil.getMessageCode(new Random().nextInt(1000000));
		
		//String content = "提醒您，您正在找回密码，验证码：" + messagecode + "。如非本人操作，请忽略本消息。";
		String content = smsTemplate.replace("messagecode", messagecode); //add modify by szy 2018.1.9
		
		redis.set(ConfigConstant.FINDBACKPWD_MSG, user.getUserId(), messagecode, ConfigConstant.SMS_EXPIRE_TIME);
		try {
			return lmSmsMessageService.sendLmSmsMessage(content, Long.valueOf(user.getUserId()), userName,SmsTypeEnum.VERIFY_CODE.getVal());
		} catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
	}
	
	/**
	 * @Title: sendChangeMobile 
	 * @Description: 发送更换手机号验证码 
	 * @param userName
	 * @return LmSmsMessage
	 */
	@Override
	public LmSmsMessage sendChangeMobile(String userName) {
		if (null == userName) {
			throw new ImplException(CommConstant.GWSCOD0001, "参数错误,无法发生验证短信!");
		}
		
		User user = queryUserByUserName(userName);
		if (null != user) {
			throw new ImplException(CommConstant.GWSCOD0009, CommConstant.GWSMSG0009);
		}
		
	
		//获取更换手机号验证码短信模版 modify by szy 2018.1.9 beg
		Map<String,String> smsConfigMap = lmSmsMessageService.getSmsConfig(ConfigConstant.CHANGE_MOBILE);
		String smsTemplate = smsConfigMap.get("content");
		String status = smsConfigMap.get("smsStatus");
		Short smsStatus = Short.valueOf(status);
		
		//判断短信发送状态是否启用
		if (IsEnableEnum.UN_ENABLE.getType().equals(smsStatus)){
			GwsLogger.info("发送更换手机号验证码短信通知已停用(1:启用,2:停用),当前状态为:smsStatus={}",smsStatus);
    		throw new ImplException(CommConstant.GWSCOD0001, "系统异常!");
		}
		//获取更换手机号验证码短信模版 modify by szy 2018.1.9 end	
		
		String messagecode = IdUtil.getMessageCode(new Random().nextInt(1000000));
		
//		String content = "提醒您，您的验证码：" + messagecode + "。如非本人操作，请忽略本消息。";
		String content = smsTemplate.replace("messagecode", messagecode); //modify by szy 2018.1.9
		
		redis.set(ConfigConstant.CHANGEMOBILE_MSG, userName, messagecode, ConfigConstant.SMS_EXPIRE_TIME);
	    try {
	    	return lmSmsMessageService.sendLmSmsMessage(content, null, userName, SmsTypeEnum.VERIFY_CODE.getVal());
	    } catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
	}
	
	/**
	 * @Title: sendSMS 
	 * @Description: 发送短讯 
	 * @param mobile
	 * @param content
	 * @return Boolean
	 */
	public Boolean sendSMS(String mobile, String content) {
		if (11 != mobile.length() || !isNumeric(mobile)) {
			throw new ImplException(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008);
		}
		
		Boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
	    
		map.put("mobile", mobile);
		map.put("realContent", content);
		
		try {
			List<Map<String, Object>> msgList = new ArrayList<Map<String, Object>>();
			msgList.add(map);
			
			smscYunPian.checkParams(msgList);
			flag  = smscYunPian.doSend(msgList);
		} catch (ImplException e) {
			throw new ImplException(CommConstant.GWSCOD0001, "发送验证码短信失败!");
		} catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0001, "发送验证码短信失败!");
		}
		return flag;
	}
	
	/**
	 * @Title: queryUserByUserId 
	 * @Description: 根据userID查询用户信息 
	 * @param userId
	 * @return User
	 */
	private User queryUserByUserId(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			return userRepositorySlave.queryUserByUserId(userId);
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: queryUserByUserName 
	 * @Description: 根据userName查询用户信息 
	 * @param userName
	 * @return User
	 */
	private User queryUserByUserName(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			return userRepositorySlave.queryUserByUserName(userName);
		}
		return null;
	}
	
	/**
	 * @Title: isNumeric 
	 * @Description: 是否为数字 
	 * @param str
	 * @return boolean
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[1-9]\\d*$");
		return pattern.matcher(str).matches();
	}
}
