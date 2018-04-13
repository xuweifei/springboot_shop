/**
 * @Title: SmscController.java 
 * @Package com.opengroup.longmao.gwcommon.controller 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.controller;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.LmSmsMessage;
import com.opengroup.longmao.gwcommon.service.SmscService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: SmscController 
 * @Description: TODO
 * @author Mr.Zhu
 */
@EnableSwagger2
@Api(value = "用户短消息", tags = "sms")
@RestController
@RequestMapping(value = { "/sms" })
public class SmscController {
	
	@Autowired
	private SmscService smscService;
	
	@ApiOperation(value = "发送验证码短讯", notes = "通用短讯发送方法,userId查询userName")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String")})
	@RequestMapping(value = "/sendCaptchaSms", method = RequestMethod.POST)
	public RetResult sendCaptchaSms(@RequestParam("userId") String userId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("发送验证码短讯ByUserId,开始:userId={},startTime={}", userId, startTime);
		LmSmsMessage msg = new LmSmsMessage();
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("发送验证码短讯ByUserId,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, msg);
		}

		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			msg = smscService.sendCommonSmsByUserId(userId);
			if (null != msg) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("发送验证码短讯ByUserId,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("发送验证码短讯ByUserId,失败:code={},message={},userId={},endTime={}毫秒,e={}", code, message, userId,
					(System.currentTimeMillis() - startTime), e);
		} catch (Exception e) {
			GwsLogger.error("发送验证码短讯ByUserId,异常:code={},message={},userId={},endTime={}毫秒,e={}", code, message, userId,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, msg);
	}
	
	@ApiOperation(value = "发送验证码短讯", notes = "通用短讯发送方法")
	@ApiImplicitParams({ @ApiImplicitParam(name="userName", value="用户userName", required=true, dataType="String")})
	@RequestMapping(value = "/sendSmsByUserName", method = RequestMethod.GET)
	public RetResult sendSmsByUserName(@RequestParam("userName") String userName) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("发送验证码短讯ByUserName,开始:userName={},startTime={}", userName, startTime);
		
		LmSmsMessage msg = new LmSmsMessage();
		if (StringUtils.isBlank(userName)) {
			GwsLogger.error("发送验证码短讯ByUserName,参数为空:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, msg);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("发送验证码短讯ByUserName,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, msg);
		}

		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			msg = smscService.sendCommonSms(userName);
			if (null != msg) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("发送验证码短讯ByUserName,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("发送验证码短讯ByUserName,失败:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		} catch (Exception e) {
			GwsLogger.error("发送验证码短讯ByUserName,异常:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, msg);
	}
	
	@ApiOperation(value = "发送注册验证码短讯", notes = "注册短讯发送方法")
	@ApiImplicitParams({ @ApiImplicitParam(name="userName", value="手机号码", required=true, dataType="String")})
	@RequestMapping(value = "/sendRegCaptcha", method = RequestMethod.GET)
	public RetResult sendRegCaptcha(@RequestParam("userName") String userName) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("发送注册验证码短讯,开始:userName={},startTime={}", userName, startTime);
		
		LmSmsMessage msg = new LmSmsMessage();
		if (StringUtils.isBlank(userName)) {
			GwsLogger.error("发送注册验证码短讯,参数为空:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, msg);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("发送注册验证码短讯,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, msg);
		}

		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			msg = smscService.sendRegister(userName);
			if (null != msg) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("发送注册验证码短讯,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("发送注册验证码短讯,失败:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		} catch (Exception e) {
			GwsLogger.error("发送注册验证码短讯,异常:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, msg);
	}
	
	@ApiOperation(value = "发送找回密码验证码短讯", notes = "找回密码短讯发送方法")
	@ApiImplicitParams({ @ApiImplicitParam(name="userName", value="手机号码", required=true, dataType="String")})
	@RequestMapping(value = "/sendFindPwd", method = RequestMethod.GET)
	public RetResult sendFindPwd(@RequestParam("userName") String userName) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("发送找回密码验证码短讯,开始:userName={},startTime={}", userName, startTime);
		
		LmSmsMessage msg = new LmSmsMessage();
		if (StringUtils.isBlank(userName)) {
			GwsLogger.error("发送找回密码验证码短讯,参数为空:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, msg);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("发送找回密码验证码短讯,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, msg);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			msg = smscService.sendFindPwd(userName);
			if (null != msg) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("发送找回密码验证码短讯,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("发送找回密码验证码短讯,失败:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		} catch (Exception e) {
			GwsLogger.error("发送找回密码验证码短讯,异常:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, msg);
	}
	
	@ApiOperation(value = "发送更换手机号验证码短讯", notes = "更换手机号短讯发送方法")
	@ApiImplicitParams({ @ApiImplicitParam(name="userName", value="手机号码", required=true, dataType="String")})
	@RequestMapping(value = "/sendChangeMobile", method = RequestMethod.GET)
	public RetResult sendChangeMobile(@RequestParam("userName") String userName) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("发送更换手机号验证码短讯,开始:userName={},startTime={}", userName, startTime);
		
		LmSmsMessage msg = new LmSmsMessage();
		if (StringUtils.isBlank(userName)) {
			GwsLogger.error("发送更换手机号验证码短讯,参数为空:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, msg);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("发送更换手机号验证码短讯,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, msg);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			msg = smscService.sendChangeMobile(userName);
			if (null != msg) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("发送更换手机号验证码短讯,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("发送更换手机号验证码短讯,失败:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		} catch (Exception e) {
			GwsLogger.error("发送更换手机号验证码短讯,异常:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, msg);
	}
	
	@ApiOperation(value = "发送快速登录验证码短讯", notes = "快速登录短讯发送方法")
	@ApiImplicitParams({ @ApiImplicitParam(name="userName", value="手机号码", required=true, dataType="String")})
	@RequestMapping(value = "/sendLogin", method = RequestMethod.GET)
	public RetResult sendLogin(@RequestParam("userName") String userName) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("发送快速登录验证码短讯,开始:userName={},startTime={}", userName, startTime);
		
		LmSmsMessage msg = new LmSmsMessage();
		if (StringUtils.isBlank(userName)) {
			GwsLogger.error("发送快速登录验证码短讯,参数为空:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, msg);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("发送快速登录验证码短讯,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, msg);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			msg = smscService.sendLogin(userName);
			if (null != msg) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("发送快速登录验证码短讯,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("发送快速登录验证码短讯,失败:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		} catch (Exception e) {
			GwsLogger.error("发送快速登录验证码短讯,异常:code={},message={},userName={},endTime={}毫秒,e={}", code, message, userName,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, msg);
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
