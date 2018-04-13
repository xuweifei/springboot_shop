package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

/**
 * 用户注册 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
public interface RegisterService {

	/**
	 * 微信/QQ/微博三方授权注册
	 * @author Hermit 2018年1月23日
	 */
	Map<String, Object> thirdPartyReg(String loginId, String nickName, String userHead, Short way, String pushId, String chlId, String thirdPartName);
	
	
	
}
