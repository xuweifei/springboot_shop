package com.opengroup.longmao.gwcommon.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.annotation.JsonParam;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.UserIdentityAuth;
import com.opengroup.longmao.gwcommon.service.UserIdentityAuthService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: UserIdentityController 
 * @Description: 用户身份管理Controller
 * @author Mr.Zhu
 */
@EnableSwagger2
@Api(value = "用户身份管理", tags = "identity")
@RestController
@RequestMapping(value = { "/identity" })
public class UserIdentityController {
	
	@Autowired
	private UserIdentityAuthService userIdentityAuthService;
	
	@ApiOperation(value = "查询用户身份认证信息", notes="查询用户身份认证信息", response = UserIdentityAuth.class)
	@ApiImplicitParams(
			{@ApiImplicitParam(name ="userId",value ="用户ID", required = true, dataType ="String"),
	})
	@RequestMapping(value = "/findIdentity",method =RequestMethod.POST)
	public RetResult findIdentity(String userId){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/identity/findIdentity):查询用户身份认证信息,开始:userId={},startTime={}", userId, startTime);
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询用户身份认证信息,参数非法:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		UserIdentityAuth identity = null;
		try {
			identity = userIdentityAuthService.findUserIdentityAuth(Long.valueOf(userId));
			GwsLogger.info("查询用户身份认证信息,结束:code={},message={},userId={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, userId, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, identity);
		} catch (Exception e) {
			GwsLogger.error("查询用户身份认证信息,异常:code={},message={},userId={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, userId, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
	}
	
	@ApiOperation(value = "查询用户身份认证信息", notes="查询用户身份认证信息", response = UserIdentityAuth.class)
	@ApiImplicitParams(
			{@ApiImplicitParam(name ="idCard",value ="用户身份号", required = true, dataType ="String"),
	})
	@RequestMapping(value = "/findIdentityByIdCard",method =RequestMethod.POST)
	public RetResult findIdentityByIdCard(String idCard){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/identity/findIdentityByIdCard):查询用户身份认证信息ByIdCard,开始:idCard={},startTime={}", idCard, startTime);
		if (StringUtils.isBlank(idCard)) {
			GwsLogger.error("查询用户身份认证信息ByIdCard,参数非法:idCard={}", idCard);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Boolean flag = false;
		try {
			flag = userIdentityAuthService.verifyIdCard(idCard);
			if (flag) {
				GwsLogger.error("查询用户身份认证信息ByIdCard,身份证已认证:idCard={}", idCard);
				flag = true;
			}
			GwsLogger.info("查询用户身份认证信息ByIdCard,结束:code={},message={},userId={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, idCard, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (Exception e) {
			GwsLogger.error("查询用户身份认证信息ByIdCard,异常:code={},message={},idCard={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, idCard, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}
	
	@ApiOperation(value = "保存用户身份认证信息", notes="保存用户身份认证信息", response = UserIdentityAuth.class)
	@ApiImplicitParams(
			{@ApiImplicitParam(name ="identityInfo",value ="身份实体UserIdentityAuth", required = true, dataType ="UserIdentityAuth"),
	})
	@RequestMapping(value = "/saveIdentity",method =RequestMethod.POST)
	public RetResult saveIdentity(@JsonParam UserIdentityAuth identityInfo){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/identity/saveIdentity):新增用户身份认证信息,开始:UserIdentityAuth={},startTime={}",
				JSON.toJSONString(identityInfo), startTime);
		if (null == identityInfo) {
			GwsLogger.error("新增用户身份认证信息,参数非法:UserIdentityAuth={}", identityInfo);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (null == identityInfo.getUserId() || 1 > identityInfo.getUserId()) {
			GwsLogger.error("新增用户身份认证信息,参数非法:userId={}", identityInfo.getUserId());
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (StringUtils.isBlank(identityInfo.getRealName()) || StringUtils.isBlank(identityInfo.getIdCard())
				|| StringUtils.isBlank(identityInfo.getMobile())
				|| StringUtils.isBlank(identityInfo.getFrontIdentityUrl())
				|| StringUtils.isBlank(identityInfo.getOppositeIdentityUrl())) {
			GwsLogger.error("新增用户身份认证信息,参数非法:userId={},realName={},idCard={},mobile={},frontUrl={},oppositeUrl={}",
					identityInfo.getUserId(), identityInfo.getRealName(), identityInfo.getIdCard(),
					identityInfo.getMobile(), identityInfo.getFrontIdentityUrl(),
					identityInfo.getOppositeIdentityUrl());
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		Boolean flag = userIdentityAuthService.verifyIdCard(identityInfo.getIdCard());
		if (flag) {
			GwsLogger.error("新增用户身份认证信息,身份证已认证:userId={},idCard={}", identityInfo.getUserId(),
					identityInfo.getIdCard());
			return RetResult.setRetDate(CommConstant.GWSCOD0037, CommConstant.GWSMSG0037, null);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		UserIdentityAuth identity = null;
		try {
			identity = userIdentityAuthService.saveUserIdentityAuth(identityInfo);
			if (null != identity) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000; 
			}
			GwsLogger.info("新增用户身份认证信息,结束:code={},message={},userId={},endTime={}毫秒", code, message, identity.getUserId(),
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("新增用户身份认证信息,异常:code={},message={},userId={},endTime={}毫秒,e={}", code, message, identity.getUserId(),
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, identity);
	}
	
	@ApiOperation(value = "保存用户身份认证信息", notes="保存用户身份认证信息", response = UserIdentityAuth.class)
	@ApiImplicitParams(
			{@ApiImplicitParam(name ="identityInfo",value ="身份实体UserIdentityAuth", required = true, dataType ="UserIdentityAuth"),
	})
	@RequestMapping(value = "/updateIdentity",method =RequestMethod.POST)
	public RetResult updateIdentity(@JsonParam UserIdentityAuth identityInfo){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(/identity/updateIdentity):修改用户身份认证信息,开始:UserIdentityAuth={},startTime={}",
				JSON.toJSONString(identityInfo), startTime);
		if (null == identityInfo) {
			GwsLogger.error("修改用户身份认证信息,参数非法:UserIdentityAuth={}", identityInfo);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (null == identityInfo.getUserId() || 1 > identityInfo.getUserId()) {
			GwsLogger.error("修改用户身份认证信息,参数非法:userId={}", identityInfo.getUserId());
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (StringUtils.isBlank(identityInfo.getRealName()) || StringUtils.isBlank(identityInfo.getIdCard())
				|| StringUtils.isBlank(identityInfo.getMobile())
				|| StringUtils.isBlank(identityInfo.getFrontIdentityUrl())
				|| StringUtils.isBlank(identityInfo.getOppositeIdentityUrl())) {
			GwsLogger.error("修改用户身份认证信息,参数非法:userId={},realName={},idCard={},mobile={},frontUrl={},oppositeUrl={}",
					identityInfo.getUserId(), identityInfo.getRealName(), identityInfo.getIdCard(),
					identityInfo.getMobile(), identityInfo.getFrontIdentityUrl(),
					identityInfo.getOppositeIdentityUrl());
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		Boolean flag = userIdentityAuthService.verifyIdCard(identityInfo.getIdCard());
		if (flag) {
			GwsLogger.error("修改用户身份认证信息,身份证已认证:userId={},idCard={}", identityInfo.getUserId(),
					identityInfo.getIdCard());
			return RetResult.setRetDate(CommConstant.GWSCOD0037, CommConstant.GWSMSG0037, null);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		UserIdentityAuth identity = null;
		try {
			identity = userIdentityAuthService.saveUserIdentityAuth(identityInfo);
			if (null != identity) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000; 
			}
			GwsLogger.info("修改用户身份认证信息,结束:code={},message={},userId={},endTime={}毫秒", code, message, identity.getUserId(),
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("修改用户身份认证信息,异常:code={},message={},userId={},endTime={}毫秒,e={}", code, message, identity.getUserId(),
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, identity);
	}
	
}
