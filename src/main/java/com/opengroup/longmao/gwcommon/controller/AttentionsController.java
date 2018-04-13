package com.opengroup.longmao.gwcommon.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.Attentions;
import com.opengroup.longmao.gwcommon.entity.vo.AttentionsVO;
import com.opengroup.longmao.gwcommon.enums.FollowStateEnum;
import com.opengroup.longmao.gwcommon.service.AttentionsService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【用户关注/粉丝】 控制类
 * @version 1.0
 * @author Hermit 2017年06月09日 下午16:05:21
 */ 
@RestController
@EnableSwagger2
@Api(value= "用户关注/粉丝",tags="follow/follower")
@RequestMapping(value = {"/attent"})
public class AttentionsController {

	@Autowired
	private AttentionsService attentionsService;

	/**
	 * @Title: follow 
	 * @Description: 用户关注 
	 * @param userId
	 * @param followId
	 * @return RetResult
	 */
	@ApiOperation(value = "用户关注",notes="关注某用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name ="userId",value ="用户userId", required = true, dataType ="String"),
		@ApiImplicitParam(name ="followId",value ="被关注Id", required = true, dataType ="String") })
	@RequestMapping(value = "/follow",method =RequestMethod.POST)
	public RetResult follow(@RequestParam("userId") String userId, @RequestParam("followId") String followId){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("用户关注/粉丝开始:userId={},followId={},isFollow={},startTime={}", userId, followId,
				FollowStateEnum.FOLLOW.getVal(), startTime);
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(followId)) {
			GwsLogger.error("参数非法:code={},message={},userId={},followId={}", CommConstant.GWSCOD0003,
					CommConstant.GWSMSG0003, userId, followId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}
		if (followId.equals(userId)) {
			GwsLogger.error("不能关注自己哟:userId={},followId={}", userId, followId);
			return RetResult.setRetDate(CommConstant.GWSCOD0036, CommConstant.GWSMSG0036, false);
		}
		
		try {
			Boolean flag = false;
			Attentions attent = attentionsService.attent(userId, followId, FollowStateEnum.FOLLOW.getVal());
			if (null != attent) {
				flag = (FollowStateEnum.FOLLOW.getVal() == attent.getAttentionsState()) ? true : false;
			}
			GwsLogger.info("用户关注/粉丝结束:code={},message={},endTime={}", 
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (ImplException e) {
			GwsLogger.error("用户关注/粉丝异常:code={},message={},userId={},followId={},endTime={},e={}", e.getCode(),
					e.getMessage(), userId, followId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(e.getCode(), e.getMessage(), false);
		} catch (Exception e) {
			GwsLogger.error("用户关注/粉丝异常:code={},message={},userId={},followId={},endTime={},e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, followId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}

	/**
	 * @Title: unFollow 
	 * @Description: 用户取消关注 
	 * @param userId
	 * @param followId
	 * @return RetResult
	 */
	@ApiOperation(value = "用户取消关注",notes="取消关注某用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name ="userId",value ="用户userId", required = true, dataType ="String"),
		@ApiImplicitParam(name ="followId",value ="被关注Id", required = true, dataType ="String") })
	@RequestMapping(value = "/unFollow",method =RequestMethod.POST)
	public RetResult unFollow(@RequestParam("userId") String userId, @RequestParam("followId") String followId){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("用户取消关注/粉丝开始:userId={},followId={},isFollow={},startTime={}", userId, followId,
				FollowStateEnum.UNFOLLOW.getVal(), startTime);
	
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(followId)) {
			GwsLogger.error("参数非法:code={},message={},userId={},followId={}", CommConstant.GWSCOD0003,
					CommConstant.GWSMSG0003, userId, followId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}
		
		try {
			Boolean flag = false;
			Attentions attent = attentionsService.attent(userId, followId, FollowStateEnum.UNFOLLOW.getVal());
			if (null != attent) {
				flag = (FollowStateEnum.UNFOLLOW.getVal() == attent.getAttentionsState()) ? true : false;
			}
			GwsLogger.info("用户取消关注/粉丝结束:code={},message={},endTime={}", 
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (ImplException e) {
			GwsLogger.error("用户取消关注/粉丝异常:code={},message={},userId={},followId={},endTime={},e={}", e.getCode(),
					e.getMessage(), userId, followId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(e.getCode(), e.getMessage(), false);
		} catch (Exception e) {
			GwsLogger.error("用户取消关注/粉丝异常:code={},message={},userId={},followId={},endTime={},e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, followId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}
	
	@ApiOperation(value = "用户关注列表",notes="查询用户关注列表信息")
	@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "String")
	@RequestMapping(value = "/queryFollow",method =RequestMethod.GET)
	public RetResult queryFollow(@RequestParam("userId") String userId){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("获取用户关注列表开始:userId={},startTime={}", userId, startTime);
		
		AttentionsVO follow = new AttentionsVO();
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("参数非法:code={},message={},userId={}", 
					CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, follow);
		}
		
		try {
			follow = attentionsService.queryFollow(userId);
			GwsLogger.info("获取用户关注列表结束:code={},message={},endTime={}", 
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, follow);
		} catch (ImplException e) {
			GwsLogger.error("获取用户关注列表异常:code={},message={},userId={},endTime={},e={}", e.getCode(),
					e.getMessage(), userId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(e.getCode(), e.getMessage(), follow);
		} catch (Exception e) {
			GwsLogger.error("获取用户关注列表异常:code={},message={},userId={},endTime={},e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, follow);
		}
	}
	
	@ApiOperation(value = "用户关注列表(直播中)",notes="查询用户关注列表信息(直播中)")
	@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "String")
	@RequestMapping(value = "/queryFollowLive",method =RequestMethod.GET)
	public RetResult queryFollowLive(@RequestParam("userId") String userId){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("获取用户关注列表(直播中)开始:userId={},startTime={}", userId, startTime);
		
		AttentionsVO follow = new AttentionsVO();
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("参数非法:code={},message={},userId={}", 
					CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, follow);
		}
		
		try {
			follow = attentionsService.queryFollowLive(userId);
			GwsLogger.info("获取用户关注列表(直播中)结束:code={},message={},endTime={}", 
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, follow);
		} catch (ImplException e) {
			GwsLogger.error("获取用户关注列表(直播中)异常:code={},message={},userId={},endTime={},e={}", e.getCode(),
					e.getMessage(), userId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(e.getCode(), e.getMessage(), follow);
		} catch (Exception e) {
			GwsLogger.error("获取用户关注列表(直播中)异常:code={},message={},userId={},endTime={},e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, follow);
		}
	}
	
	@ApiOperation(value = "用户粉丝列表",notes="查询用户粉丝列表信息")
	@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "String")
	@RequestMapping(value = "/queryFans",method =RequestMethod.GET)
	public RetResult queryFans(@RequestParam("userId") String userId){
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("获取用户粉丝列表开始:userId={},startTime={}", userId, startTime);
		
		AttentionsVO fans = new AttentionsVO();
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("参数非法:code={},message={},userId={}", 
					CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, fans);
		}
		
		try {
			fans = attentionsService.queryFans(userId);
			GwsLogger.info("获取用户粉丝列表结束:code={},message={},endTime={}", 
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, fans);
		} catch (ImplException e) {
			GwsLogger.error("获取用户粉丝列表异常:code={},message={},userId={},endTime={},e={}", e.getCode(),
					e.getMessage(), userId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(e.getCode(), e.getMessage(), fans);
		} catch (Exception e) {
			GwsLogger.error("获取用户粉丝列表异常:code={},message={},userId={},endTime={},e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, fans);
		}
	}
	
}

