/**
 * @Title: OAuthService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

/**
 * @ClassName: OAuthService 
 * @Description: TODO
 * @author Mr.Zhu
 */
public interface OAuthService {
	
	/**
	 * @Title: weiboToken 
	 * @Description: 获取微博用户信息 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	Map<String, Object> weiboToken(String grantType, String redirectUri, String urlCode);
	
	/**
	 * @Title: qqToken 
	 * @Description: 获取QQ用户信息 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	Map<String, Object> qqToken(String grantType, String redirectUri, String urlCode);
	
	/**
	 * @Title: qqToken 
	 * @Description: 获取微信(公众号)用户信息 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	Map<String, Object> wechatToken(String grantType, String urlCode);
	
	/**
	 * @Title: wechatTokenH5 
	 * @Description: 获取微信(PC)用户信息 
	 * @param grantType
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	Map<String, Object> wechatTokenH5(String grantType, String urlCode);
	
	/**
	 * @Title: weiboH5 
	 * @Description: 获取微博用户信息 
	 * @param url
	 * @return
	 */
	Map<String, Object> weiboH5(String url);
	
}
