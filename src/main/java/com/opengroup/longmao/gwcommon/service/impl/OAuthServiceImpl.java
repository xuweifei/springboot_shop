/**
 * @Title: OAuthServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.AuthConfig;
import com.opengroup.longmao.gwcommon.service.OAuthService;
import com.opengroup.longmao.gwcommon.tools.http.HttpRequest;

/**
 * @ClassName: OAuthServiceImpl 
 * @Description: TODO
 * @author Mr.Zhu
 */
@Service
public class OAuthServiceImpl implements OAuthService {

//	@Autowired
//	private AuthInfoRepositorySlave authInfoRepositorySlave;
	
	@Autowired
	private AuthConfig auth;
	
	/**
	 * @Title: qqToken 
	 * @Description: 获取QQ用户信息 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> qqToken(String grantType, String redirectUri, String urlCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String accessToken = getQQToken(grantType, redirectUri, urlCode);
			if (!"".equals(accessToken)) {
				Map<String, Object> openId = getQqOpedId(accessToken, "1");
				if (openId.containsKey("openid")) {
					String param = "access_token=" + accessToken + "&oauth_consumer_key=" + auth.getQqAppId().trim()
							+ "&openid=" + openId.get("openid").toString();
					
					String content = HttpRequest.httpsGET(auth.getQqUserUrl().trim(), param);
					String unionid = ",\"unionid\":\"" + openId.get("unionid").toString() + "\"}";
					String jsonStr = content.replace("}", "");
					JSONObject jsonObject = JSONObject.parseObject(jsonStr + unionid);
					GwsLogger.info("获取QQ用户信息信息:urlCode={},json={}", urlCode, jsonObject);
					if (null != jsonObject.getString("unionid")) {
						map = jsonObject;
						return map;
					}
				}
			}
		} catch (IOException e) {
			GwsLogger.error("获取QQ用户信息失败:urlCode={},redirectUri={},urlCode={}", grantType, redirectUri, urlCode);
		}
		return map;
	}
	
	/**
	 * @Title: getQqOpedId 
	 * @Description: 获取QQ授权unionid 
	 * @param accessToken
	 * @param uId
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getQqOpedId(String accessToken, String uId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String param = "access_token=" + accessToken + "&unionid=" + uId;
			
			String content = HttpRequest.httpsGET(auth.getQqOpenIdUrl().trim(), param);
			String jsonStr = content.replace("callback(", "").replace(");", "");
			JSONObject json = JSONObject.parseObject(jsonStr);
			GwsLogger.info("获取QQOpenid信息:urlCode={},json={}", uId, json);
			if (null != json.getString("openid")) {
				map = json;
				return map;
			}
		} catch (IOException e) {
			GwsLogger.error("获取QQOpenid失败:access_token={},unionid={}", accessToken, uId);
		}
		return map;
	}
	
	/**
	 * @Title: getQQToken 
	 * @Description: 获取QQToken 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return String
	 */
	private String getQQToken(String grantType, String redirectUri, String urlCode) {
		try {
			StringBuffer param = new StringBuffer();
			param.append("client_id=" + auth.getQqAppId().trim() + "&client_secret=" + auth.getQqAppKey().trim() + "");
			param.append("&grant_type=" + grantType + "&redirect_uri=" + redirectUri + "&code=" + urlCode);
			
			String content = HttpRequest.httpsGET(auth.getQqTokenUrl().trim(), param.toString());
			String jsonStr = "{\"" + content.replace("=", "\":\"").replace("&", "\",\"") + "\"}";
			JSONObject json = JSONObject.parseObject(jsonStr);
			GwsLogger.info("获取QQToken信息:urlCode={},json={}", urlCode, json);
			if (null != json.getString("access_token")) {
				return json.getString("access_token");
			}
		} catch (IOException e) {
			GwsLogger.error("获取QQToken失败:client_id={},client_secret={}", auth.getQqAppId(), auth.getQqAppKey());
		}
		return "";
	}
	
	/**
	 * @Title: wechatTokenH5 
	 * @Description: 获取微信(PC)用户信息 
	 * @param grantType
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> wechatTokenH5(String grantType, String urlCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject json = getWeChatTokenH5(grantType, urlCode);
			if (null != json) {
				String param = "access_token=" + json.getString("access_token") + "&openid=" + json.getString("openid");
				
				String content = HttpRequest.httpsGET(auth.getWechatUserUrl().trim(), param);
				JSONObject jsonObject = JSONObject.parseObject(content);
				GwsLogger.info("获取H5微信用户信息:urlCode={},json={}", urlCode, jsonObject);
				if (null != jsonObject.getString("openid")) {
					map = jsonObject;
					return map;
				}
			}
		} catch (IOException e) {
			GwsLogger.error("获取H5微信用户信息失败:urlCode={},urlCode={}", grantType, urlCode);
		}
		return map;
	}
	
	/**
	 * @Title: getWeChatTokenH5 
	 * @Description: 获取微信(PC)Token 
	 * @param grantType
	 * @param urlCode
	 * @return JSONObject
	 */
	private JSONObject getWeChatTokenH5(String grantType, String urlCode) {
		JSONObject json = null;
		try {
			StringBuffer param = new StringBuffer();
			param.append("appid=" + auth.getWechatWebAppId().trim() + "&secret=" + auth.getWechatWebAppSecret().trim() + "");
			param.append("&code=" + urlCode + "&grant_type=" + grantType);
			
			String content = HttpRequest.httpsPOST(auth.getWechatTokenUrl().trim(), param.toString());
			json = JSONObject.parseObject(content);
			GwsLogger.info("获取H5微信Token信息:urlCode={},json={}", urlCode, json);
			if (null != json.getString("scope")) {
				return json;
			}
		} catch (IOException e) {
			GwsLogger.error("获取H5微信Token失败:appid={},secret={}", auth.getWechatWebAppId(), auth.getWechatWebAppSecret());
		}
		return json;
	}

	/**
	 * @Title: qqToken 
	 * @Description: 获取微信(公众号)用户信息 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> wechatToken(String grantType, String urlCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject json = getWeChatToken(grantType, urlCode);
			if (null != json) {
				String param = "access_token=" + json.getString("access_token") + "&openid=" + json.getString("openid");
				
				String content = HttpRequest.httpsGET(auth.getWechatUserUrl().trim(), param);
				JSONObject jsonObject = JSONObject.parseObject(content);
				GwsLogger.info("获取微信用户信息:urlCode={},json={}", urlCode, jsonObject);
				if (null != jsonObject.getString("openid")) {
					map = jsonObject;
					return map;
				}
			}
		} catch (IOException e) {
			GwsLogger.error("获取微信用户信息失败:urlCode={},urlCode={}", grantType, urlCode);
		}
		return map;
	}
	
//	public Map<String, String> getToken(String appId, String grantType, String urlCode) {
//		Map<String, String> map = new HashMap<String, String>();
//		AuthInfo authInfo = getAuth(appId);
//		
//		if (null != authInfo) {
//			String token = authInfo.getAccessToken();
//			if (authInfo.getInvalidTime() < DateUtil.currentSecond()) {
//				token = ticket();//获取token
//				if (!"".equals(token)) {
//					Integer invalidTime = DateUtil.currentSecond() + 7199;
//					authInfo.setToken(token);
//					authInfo.setInvalidTime(invalidTime);
//					authInfo.setUtime(DateUtil.currentSecond());
//					authInfoRepositoryMaster.save(authInfo);
//				}
//			}
//			map.put("ticket", token);
//		} else {
//			Map<String, Object> token = getWeChatToken(grantType, urlCode);//获取token
//			if (token.containsKey("access_token")) {
//				Long authId = idGlobalGenerator.getSeqId(AuthInfo.class);
//				String accessToken = token.get("access_token").toString();
//				String refreshToken = token.get("access_token").toString();
//				Integer invalidTime = DateUtil.currentSecond() + 7200;
//				
//				AuthInfo a = new AuthInfo();
//				a.setAuthId(authId);
//				a.setAppId(appId);;
//				a.setAccessToken(accessToken);
//				a.setRefreshToken(refreshToken);
//				a.setInvalidTime(invalidTime);
//				a.setCtime(DateUtil.currentSecond());
//				a.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
//				authInfoRepositoryMaster.save(a);
//			}
//			map.put("ticket", token);
//		}
//		return map;
//	}
	
	/**
	 * @Title: getWeChatToken 
	 * @Description: 获取微信(公众号)Token 
	 * @param grantType
	 * @param urlCode
	 * @return JSONObject
	 */
	private JSONObject getWeChatToken(String grantType, String urlCode) {
		JSONObject json = null;
		try {
			StringBuffer param = new StringBuffer();
			param.append("appid=" + auth.getWechatAppId().trim() + "&secret=" + auth.getWechatAppSecret().trim() + "");
			param.append("&code=" + urlCode + "&grant_type=" + grantType);
			
			String content = HttpRequest.httpsPOST(auth.getWechatTokenUrl().trim(), param.toString());
			json = JSONObject.parseObject(content);
			GwsLogger.info("获取微信Token信息:urlCode={},json={}", urlCode, json);
			if (null != json.getString("scope")) {
				return json;
			}
		} catch (IOException e) {
			GwsLogger.error("获取微信Token失败:appid={},secret={}", auth.getWechatAppId(), auth.getWechatAppSecret());
		}
		return json;
	}
	
	/**
	 * @Title: weiboToken 
	 * @Description: 获取微博用户信息 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> weiboToken(String grantType, String redirectUri, String urlCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject json = getWeiboToken(grantType, redirectUri, urlCode);
			if (null != json) {
				String param = "access_token=" + json.getString("access_token") + "&uid=" + json.getString("uid");
				
				String content = HttpRequest.httpsGET(auth.getWeiboShowUrl().trim(), param);
				JSONObject jsonObject = JSONObject.parseObject(content);
				GwsLogger.info("获取H5微博用户信息:urlCode={},json={}", urlCode, jsonObject);
				if (null != jsonObject.getString("idstr")) {
					map = jsonObject;
					return map;
				}
			}
		} catch (IOException e) {
			GwsLogger.error("获取H5微博用户信息失败:urlCode={},redirectUri={},urlCode={}", grantType, redirectUri, urlCode);
		}
		return map;
	}
	
	/**
	 * @Title: weiboH5 
	 * @Description: 获取微博H5信息 
	 * @return Map<String,Object>
	 */
	public Map<String, Object> weiboH5(String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//拼接查询参数
		String param = "client_id=" + auth.getWeiboAppKey().trim() + "&client_secret=" + auth.getWeiboAppSecret().trim();
		try {
			//得到微博返回的response字符串
			String content = HttpRequest.httpsPOST(auth.getWeiboTicketUrl().trim(), param);
			JSONObject json = JSONObject.parseObject(content);
			GwsLogger.info("获取H5微博用户信息:url={},json={}", url, json);
			if(json.containsKey("js_ticket")) {
				String js_ticket = json.getString("js_ticket");
				String expire_time = json.getString("expire_time");
				map.put("js_ticket", js_ticket);
				map.put("expire_time", expire_time);
			}
		}catch(Exception e) {
			GwsLogger.error("获取H5微博用户信息失败:");
		}
		return map;
	}
	
	
	/**
	 * @Title: getWeiboToken 
	 * @Description: 获取微博Token 
	 * @param grantType
	 * @param redirectUri
	 * @param urlCode
	 * @return JSONObject
	 */
	private JSONObject getWeiboToken(String grantType, String redirectUri, String urlCode) {
		JSONObject json = null;
		try {
			StringBuffer param = new StringBuffer();
			param.append("client_id=" + auth.getWeiboAppKey().trim() + "&client_secret=" + auth.getWeiboAppSecret().trim() + "");
			param.append("&grant_type=" + grantType + "&redirect_uri=" + redirectUri + "&code=" + urlCode);
			
			String content = HttpRequest.httpsPOST(auth.getWeiboTokenUrl().trim(), param.toString());
			json = JSONObject.parseObject(content);
			GwsLogger.info("获取H5微博Token信息:urlCode={},json={}", urlCode, json);
			if (null != json.getString("access_token")) {
				return json;
			}
		} catch (IOException e) {
			GwsLogger.error("获取H5微博Token失败:client_id={},client_secret={}", auth.getWeiboAppKey(), auth.getWeiboAppSecret());
		}
		return json;
	}
	
//	/**
//	 * @Title: getAuth 
//	 * @Description: 获取数据库授权Token 
//	 * @param appId
//	 * @return AuthInfo
//	 */
//	private AuthInfo getAuth(String appId) {
//		return authInfoRepositorySlave.queryAuthByAppId(appId);
//	}

}
