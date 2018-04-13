/**
 * @Title: OAuthController.java 
 * @Package com.opengroup.longmao.gwcommon.controller 
 * @Description:
 * @author Mr.Zhu
 * @version V1.9
 */
package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.service.OAuthService;
import com.opengroup.longmao.gwcommon.tools.Md5Util;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: OAuthController 
 * @Description: 三方授权签名
 * @author Mr.Zhu
 */
@EnableSwagger2
@Api(value = "三方授权签名", tags = "auth")
@RestController
@RequestMapping(value = { "/auth" })
public class OAuthController {
	
	@Autowired
	private OAuthService oAuthService;
	
	@ApiOperation(value = "获取微博授权用户信息", notes = "获取微博授权用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grantType ", value = "grantType", required = false, dataType = "String"),
			@ApiImplicitParam(name = "redirectUri ", value = "redirectUri", required = false, dataType = "String"),
			@ApiImplicitParam(name = "urlCode ", value = "urlCode", required = false, dataType = "String")})
	@RequestMapping(value = "/weibo", method = RequestMethod.GET)
	public RetResult weiboToken(String grantType, String redirectUri, String urlCode) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/auth/weibo):获取微博授权用户信息,开始:grantType={},redirectUri={},urlCode={},startTime={}",
				grantType, redirectUri, urlCode, startTime);
		if (StringUtils.isBlank(grantType)) {
			GwsLogger.error("获取微博授权用户信息,参数非法:grantType={}", grantType);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (StringUtils.isBlank(redirectUri)) {
			GwsLogger.error("获取微博授权用户信息,参数非法:redirectUri={}", redirectUri);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (StringUtils.isBlank(urlCode)) {
			GwsLogger.error("获取微博授权用户信息,参数非法:urlCode={}", urlCode);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map = oAuthService.weiboToken(grantType, redirectUri, urlCode);
			if (!map.containsKey("idstr")) {
				GwsLogger.info("获取微博授权用户信息,失败:code={},message={},endTime={}毫秒,map={}", CommConstant.GWSCOD0029,
						CommConstant.GWSMSG0029, (System.currentTimeMillis() - startTime), map);
				return RetResult.setRetDate(CommConstant.GWSCOD0029, CommConstant.GWSMSG0029, map);
			}
			GwsLogger.info("获取微博授权用户信息,结束:code={},message={},endTime={}毫秒", code, message, (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("获取微博授权用户信息,异常:code={},message={},endTime={}毫秒,e={}", code, message, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, map);
	}
	
	@ApiOperation(value = "获取微博签名信息", notes = "获取微博签名信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "url", value = "url", required = false, dataType = "String")})
	@RequestMapping(value = "/weiboH5", method = RequestMethod.GET)
	public RetResult weiboToken(String url) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/auth/weiboH5):获取微博签名信息,开始:url={},startTime={}", url, startTime);
		if (StringUtils.isBlank(url)) {
			GwsLogger.error("获取微博签名信息,参数非法:url={}", url);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map = oAuthService.weiboH5(url);
			if (!map.containsKey("js_ticket")||!map.containsKey("expire_time")) {
				GwsLogger.info("获取微博签名信息,失败:code={},message={},endTime={}毫秒,map={}", CommConstant.GWSCOD0029,
						CommConstant.GWSMSG0029, (System.currentTimeMillis() - startTime), map);
				return RetResult.setRetDate(CommConstant.GWSCOD0029, CommConstant.GWSMSG0029, map);
			}
			String ints = Math.random()+"";
			String noncestr = Md5Util.thirtyTwo(ints);//随机字符串
			long timestamp = System.currentTimeMillis()/1000;
			map.put("noncestr", noncestr);
			map.put("timestamp", timestamp);
			map.put("url", url);
			GwsLogger.info("获取微博签名信息,结束:code={},message={},endTime={}毫秒", code, message, (System.currentTimeMillis() - startTime));
		}catch(Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("获取微博签名信息,异常:code={},message={},endTime={}毫秒,e={}", code, message, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, map);
	}
	
	
	
	
	@ApiOperation(value = "获取微信(公众号)授权用户信息", notes = "获取微信(公众号)授权用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grantType ", value = "grantType", required = false, dataType = "String"),
			@ApiImplicitParam(name = "urlCode ", value = "urlCode", required = false, dataType = "String")})
	@RequestMapping(value = "/wechat", method = RequestMethod.GET)
	public RetResult weiboToken(String grantType, String urlCode) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/auth/wechat):获取微信(公众号)授权用户信息,开始:grantType={},urlCode={},startTime={}", grantType,
				urlCode, startTime);
		if (StringUtils.isBlank(grantType)) {
			GwsLogger.error("获取微信(公众号)授权用户信息,参数非法:grantType={}", grantType);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (StringUtils.isBlank(urlCode)) {
			GwsLogger.error("获取微信(公众号)授权用户信息,参数非法:urlCode={}", urlCode);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map = oAuthService.wechatToken(grantType, urlCode);
			if (!map.containsKey("openid")) {
				GwsLogger.info("获取微信(公众号)授权用户信息,失败:code={},message={},endTime={}毫秒,map={}", CommConstant.GWSCOD0029,
						CommConstant.GWSMSG0029, (System.currentTimeMillis() - startTime), map);
				return RetResult.setRetDate(CommConstant.GWSCOD0029, CommConstant.GWSMSG0029, map);
			}
			GwsLogger.info("获取微信(公众号)授权用户信息,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("获取微信(公众号)授权用户信息,异常:code={},message={},endTime={}毫秒,e={}", code, message,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, map);
	}
	
	@ApiOperation(value = "获取微信(PC)授权用户信息", notes = "获取微信(PC)授权用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grantType ", value = "grantType", required = false, dataType = "String"),
			@ApiImplicitParam(name = "urlCode ", value = "urlCode", required = false, dataType = "String")})
	@RequestMapping(value = "/wechatH5", method = RequestMethod.GET)
	public RetResult weiboTokenH5(String grantType, String urlCode) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/auth/wechatH5):获取微信(PC)授权用户信息,开始:grantType={},urlCode={},startTime={}", grantType,
				urlCode, startTime);
		if (StringUtils.isBlank(grantType)) {
			GwsLogger.error("获取微信(PC)授权用户信息,参数非法:grantType={}", grantType);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (StringUtils.isBlank(urlCode)) {
			GwsLogger.error("获取微信(PC)授权用户信息,参数非法:urlCode={}", urlCode);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map = oAuthService.wechatTokenH5(grantType, urlCode);
			if (!map.containsKey("openid")) {
				GwsLogger.info("获取微信(PC)授权用户信息,失败:code={},message={},endTime={}毫秒,map={}", CommConstant.GWSCOD0029,
						CommConstant.GWSMSG0029, (System.currentTimeMillis() - startTime), map);
				return RetResult.setRetDate(CommConstant.GWSCOD0029, CommConstant.GWSMSG0029, map);
			}
			GwsLogger.info("获取微信(PC)授权用户信息,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("获取微信(PC)授权用户信息,异常:code={},message={},endTime={}毫秒,e={}", code, message,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, map);
	}
	
	@ApiOperation(value = "获取QQ授权用户信息", notes = "获取QQ授权用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grantType ", value = "grantType", required = false, dataType = "String"),
			@ApiImplicitParam(name = "redirectUri ", value = "redirectUri", required = false, dataType = "String"),
			@ApiImplicitParam(name = "urlCode ", value = "urlCode", required = false, dataType = "String")})
	@RequestMapping(value = "/qq", method = RequestMethod.GET)
	public RetResult qqToken(String grantType, String redirectUri, String urlCode) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/auth/qq):获取QQ授权用户信息,开始:grantType={},redirectUri={},urlCode={},startTime={}", grantType,
				redirectUri, urlCode, startTime);
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map = oAuthService.qqToken(grantType, redirectUri, urlCode);
			if (!map.containsKey("unionid")) {
				GwsLogger.info("获取QQ授权用户信息,失败:code={},message={},endTime={}毫秒,map={}", CommConstant.GWSCOD0029,
						CommConstant.GWSMSG0029, (System.currentTimeMillis() - startTime), map);
				return RetResult.setRetDate(CommConstant.GWSCOD0029, CommConstant.GWSMSG0029, map);
			}
			GwsLogger.info("获取QQ授权用户信息,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("获取QQ授权用户信息,异常:code={},message={},endTime={}毫秒,e={}", code, message,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, map);
	}

}
