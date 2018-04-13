package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.entity.po.User;

/**
 * 【用户信息表】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
public interface LoginService {
	
	/**
	 * @Title: queryUserByUserName 
	 * @Description: 根据手机号码查询用户信息 
	 * @param userName
	 * @return User
	 */
	User queryUserByUserName(String userName);

	/**
	 * @Title: pwdLogin
	 * @Description: 使用密码登录系统
	 * @param userName
	 * @param pwd
	 * @param pushId
	 * @return Map<String, Object>
	 */
	Map<String, Object> pwdLogin(String userName, String pwd, String pushId);

	/**
	 * @Title: captchaLogin
	 * @Description: 使用验证码登录系统
	 * @param userName
	 * @param captcha
	 * @param pushId
	 * @return Map<String, Object>
	 */
	Map<String, Object> captchaLogin(String userName, String captcha, String pushId);

	/**
	 * @Title: thirdPartyReg
	 * @Description: 第三方账户信息注册
	 * @param param
	 * @return Map<String, Object>
	 */
	Map<String, Object> thirdPartyReg(Map<String, Object> param);

	/**
	 * @Title: boundAccount
	 * @Description: 绑定手机/微信/QQ/微博
	 * @param param
	 * @return Map<String, Object>
	 */
	Map<String, Object> boundAccount(Map<String, String> param);

	/**
	 * @Title: boundRemove
	 * @Description: 解除绑定手机/微信/QQ/微博
	 * @param param
	 * @return Boolean
	 */
	Boolean boundRemove(Map<String, String> param);

	/**
	 * @Title: loginRecord
	 * @Description: 登录直播记录
	 * @param loginMap
	 * @return void
	 */
	void loginRecord(Map<String, Object> loginMap);

	/**
	 * @Title: accountLog
	 * @Description: 账号绑定记录
	 * @param userId
	 * @return Map<String,Object>
	 */
	Map<String, Object> accountLog(String userId);

	/**
	 * @Title: findBackPwd 
	 * @Description: 用户找回密码 
	 * @param userId
	 * @param pwd
	 * @param captcha
	 * @return Boolean
	 */
	Boolean findBackPwd(String userId, String pwd, String captcha);

	/**
	 * @Title: changeMobile 
	 * @Description: 用户更换手机号码 
	 * @param userId
	 * @param userName
	 * @param captcha
	 * @return Boolean
	 */
	Boolean changeMobile(String userId, String userName, String captcha);
	
	/**
	 * @Title: getUserByLoginId 
	 * @Description: 获取用户登录ID 
	 * @param loginId
	 * @param way
	 * @param pushId
	 * @return Map<String,Object>
	 */
	Map<String, Object> getUserByLoginId(String loginId, Short way, String pushId);
	
	/**
	 * @Title: robotReg 
	 * @Description: 机器人注册 
	 * @param json
	 * @return User
	 */
	User robotReg(JSONObject json);

	/**
	 * 根据userId生成商城登录key
	 */
	Map<String, Object> getShopLoginKey(Long userId);

}
