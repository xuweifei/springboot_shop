package com.opengroup.longmao.gwcommon.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserInvite;
import com.opengroup.longmao.gwcommon.service.UserInviteService;
import com.opengroup.longmao.gwcommon.service.UserService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.idutil.StringUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 用户邀请关系表 Controller
 * @version 
 * @author xwf  2018年1月4日 下午2:32:02
 */
@EnableSwagger2
@Api(value = "用户邀请关系", tags = "invite")
@RestController
@RequestMapping(value = {"/invite"})
public class UserInviteController {
	
	@Autowired
	private UserInviteService userInviteService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 【创建邀请关系】
	 * @param userId
	 * @param phoneNum
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2018年01月04日 上午09:59:49
	 */
	@ApiOperation(value = "创建邀请关系", notes = "用户接受邀请,填写手机号")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "phoneNum", value = "手机号", required = true, dataType = "String")})
	@RequestMapping(value = "/createInvitation", method = RequestMethod.POST)
	public RetResult createInvitation(Long userId, String phoneNum) {
		int startSecond = DateUtil.currentSecond();
		GwsLogger.info("创建邀请关系开始:userId={},phoneNum={}", userId, phoneNum);
		
		// 参数校验
		if (null == userId || userId < 1) {
			GwsLogger.info("userId非法:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (StringUtil.isBlank(phoneNum) || phoneNum.trim().length() != 11) {
			GwsLogger.info("phoneNum非法:phoneNum={}", phoneNum);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Map<String, Object> map = null;
		try {
			// 判断该手机号是否已经注册
			User user = userService.queryUserByMobile(phoneNum);
			if (null != user) {
				GwsLogger.info("该手机已经注册:phoneNum={}", phoneNum);
				return RetResult.setRetDate(CommConstant.GWSCOD0009, CommConstant.GWSMSG0009, null);
			}
			// 判断该手机号是否已经被邀请
			UserInvite userInvite = userInviteService.getInvitationByPhoneNum(phoneNum.trim());
			if (null != userInvite) {
				GwsLogger.info("phoneNum已经被邀请过:phoneNum={}", phoneNum);
				return RetResult.setRetDate(CommConstant.GWSCOD0032, CommConstant.GWSMSG0032, null);
			}
			
			// 创建邀请关系
			map = userInviteService.createInvitation(userId, phoneNum.trim());
		}
		catch(ImplException e) {
			GwsLogger.error("创建邀请关系失败:Exception={}", e);
			return RetResult.setRetDate(e.getCode(), e.getMessage(), null);
		}
		catch(Exception e) {
			GwsLogger.error("创建邀请关系异常:Exception={}", e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0003, null);
		}
		
		GwsLogger.info("创建邀请关系结束:耗时={}", DateUtil.currentSecond() - startSecond);
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
	}
	
	
	/**
	 * 【查询邀请奖励总览】
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "查询邀请奖励总览", notes = "查询邀请奖励总览")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long")
	@RequestMapping(value = "/findInviteReward", method = RequestMethod.POST)
	public RetResult findInviteReward(Long userId) {
		int startSecond = DateUtil.currentSecond();
		GwsLogger.info("查询邀请奖励总览开始:userId={}", userId);
		
		// 参数校验
		if (null == userId || userId < 1) {
			GwsLogger.info("userId非法:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Map<String, Object> map = null;
		try {
			map = userInviteService.findInviteReward(userId);
		}catch(Exception e) {
			GwsLogger.error("查询邀请奖励总览异常:Exception={}", e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0003, null);
		}
		
		GwsLogger.info("查询邀请奖励总览结束:耗时={}", DateUtil.currentSecond() - startSecond);
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
	}
	
	
	
	/**
	 * 【分页查询邀请明细】
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "查询邀请明细", notes = "查询邀请明细")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "pageNo", value = "第几页", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条", required = true, dataType = "Integer")})
	@RequestMapping(value = "/findInviteDetail", method = RequestMethod.POST)
	public RetResult findInviteDetail(Long userId, Integer pageNo, Integer pageSize) {
		int startSecond = DateUtil.currentSecond();
		GwsLogger.info("查询邀请明细开始:userId={}", userId);
		
		// 参数校验
		if (null == pageNo || pageNo < 1 || null == pageSize || pageSize < 1) {
			GwsLogger.info("参数非法:pageNo={},pageSize={}", pageNo, pageSize);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (null == userId || userId < 1) {
			GwsLogger.info("userId非法:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Page<UserInvite> invitePage = null;
		try {
			invitePage = userInviteService.findInviteDetail(userId, pageNo - 1, pageSize);
		}catch(Exception e) {
			GwsLogger.error("查询邀请明细异常:Exception={}", e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
		
		GwsLogger.info("查询邀请明细结束:耗时={}", DateUtil.currentSecond() - startSecond);
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, invitePage);
	}
	
	
	/**
	 * 查询邀请配置信息
	 * @author xwf 2018年1月5日
	 * @return
	 */
	@RequestMapping(value = "/getInviteConfig", method = RequestMethod.POST)
	public RetResult getInviteConfig() {
		int startSecond = DateUtil.currentSecond();
		GwsLogger.info("查询邀请配置信息");
		
		Map<String, String> configMap = null;
		try {
			configMap = userInviteService.getInviteConfig();
		}catch(Exception e) {
			GwsLogger.error("查询邀请配置信息异常:Exception={}", e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
		
		GwsLogger.info("查询邀请配置信息结束:耗时={}", DateUtil.currentSecond() - startSecond);
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, configMap);
	}
	
	
	
	
}
