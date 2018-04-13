package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.vo.AttentionsVO;
import com.opengroup.longmao.gwcommon.entity.vo.UserCalCntVO;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.service.AnchorGradeRuleService;
import com.opengroup.longmao.gwcommon.service.AnchorService;
import com.opengroup.longmao.gwcommon.service.IdentityService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【主播】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
@EnableSwagger2
@Api(value = "主播信息", tags = "anchor")
@RestController
@RequestMapping(value = { "/anchor" })
public class AnchorController {

	@Autowired
	private AnchorService anchorService;
	
	@Autowired
	private AnchorGradeRuleService anchorGradeRuleService;
	
	@Autowired
	private IdentityService identityService;

	@ApiOperation(value = "查询主播守护排行榜信息并分页", notes = "查询主播守护排行榜信息并分页")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "主播userId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pageNo", value = "第几页", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer")
	})
	@RequestMapping(value = "/defendRanking", method = RequestMethod.GET)
	public RetResult defendRanking(@RequestParam("userId") String userId, Integer pageNo, Integer pageSize) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("查询主播守护排行榜开始:userId={},pageNo={},pageSize={}", userId, pageNo, pageSize);
		// 查询主播守护排行榜
		UserCalCntVO top = null;
		try {
			top = anchorService.defendRanking(userId, pageNo - 1, pageSize);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询主播守护排行榜异常:code={},message={},userId={},pageNo={},pageSize={},e={}", code, message,
					userId, pageNo - 1, pageSize, e);
		}
		GwsLogger.info("查询主播守护排行榜结束:userId={},pageNo={},pageSize={}", userId, pageNo, pageSize);
		return RetResult.setRetDate(code, message, top);
	}
	
	@ApiOperation(value = "查询主播信息", notes = "查询主播信息ByUserIdOrNiceName")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "condition", value = "搜索主播信息条件", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pageNo", value = "第几页", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "isPage", value = "是否分页:1是,2否/0第一次", required = true, dataType = "Short")
	})
	@RequestMapping(value = "/queryAnchor", method = RequestMethod.POST)
	public RetResult queryAnchorByUserIdOrNiceName(String userId, String condition, Integer pageNo, Integer pageSize,
			Short isPage) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询主播信息ByUserIdOrNickName,开始:userId={},condition={},isPage={},startTime={}", userId, condition, isPage, startTime);
		
		AttentionsVO follow = new AttentionsVO();
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询主播信息ByUserIdOrNickName,userId参数非法:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, follow);
		}
		if (StringUtils.isBlank(condition)) {
			GwsLogger.error("查询主播信息ByUserIdOrNickName,condition参数非法:condition={}", condition);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, follow);
		}
		if (null != isPage && (null == pageNo || null == pageSize)) {
			GwsLogger.error("查询主播信息ByUserIdOrNickName,参数非法:pageNo={},pageSize={}", pageNo, pageSize);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, follow);
		}
		if (null == isPage || null == pageNo || null == pageSize) {
			isPage = IsOrNotEnum.NO.getType();
		}
		
		try {
			follow = anchorService.queryAnchor(follow, userId, condition, pageNo, pageSize, isPage);
			GwsLogger.info("查询主播信息ByUserIdOrNickName,结束:code={},message={},userId={},isPage={},pageNo={},pageSize={},endTime={}毫秒",
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, userId, isPage, pageNo, pageSize,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, follow);
		} catch (ImplException e) {
			GwsLogger.error("查询主播信息ByUserIdOrNickName,失败:code={},message={},userId={},isPage={},endTime={}秒,e={}", e.getCode(),
					e.getMessage(), userId, isPage, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(e.getCode(), e.getMessage(), follow);
		} catch (Exception e) {
			GwsLogger.error("查询主播信息ByUserIdOrNickName,异常:code={},message={},userId={},isPage={},endTime={}秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, isPage, (System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, follow);
		}
	}
	
	
	/**
	 * 
	 *【查询 主播等级；所属公会；直播版块 信息】 
	 * @return RetResult返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月14日下午3:11:53
	 * @throws 异常
	 */ 
	@ApiOperation(value = "主播信息", notes = "查询主播等级；所属公会；直播模版")
	@ApiImplicitParam(name = "userId", value = "主播ID", required = true, dataType = "String")
	@RequestMapping(value = "/findAnchorInfo", method = RequestMethod.GET)
	public RetResult findAnchorInfo(@RequestParam("userId") String userId){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询主播等级,所属公会,直播模版By主播Id,开始:code={},message={},userId={},startTime={}", code,message,userId, startTime);
		
		// 参数校验
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询主播等级,所属公会,直播模版By主播Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Map<String, Object> anchorMapInfo = new HashMap<String, Object>();
		try {
			anchorMapInfo = anchorService.findAnchorInfo(userId);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询主播等级,所属公会,直播模版By主播Id,异常:code={},message={},userId={},e={}", code, message, userId, e);
		}

		Long endTime = System.currentTimeMillis() - startTime;
		GwsLogger.info("查询主播等级,所属公会,直播模版By主播Id,结束:code={},message={},userId={},ruleMap={},endTime={}毫秒", code, message, userId,anchorMapInfo,endTime);
		return RetResult.setRetDate(code, message, anchorMapInfo);
	}
	
	
	/**
	 * 
	 *【主播等级】 
	 * @param userId
	 * @return RetResult返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月13日下午2:01:26
	 * @throws 查询异常
	 */
	@ApiOperation(value = "主播等级", notes = "查询主播等级")
	@ApiImplicitParam(name = "userId", value = "主播ID", required = true, dataType = "String")
	@RequestMapping(value = "/findAnchorGrade", method = RequestMethod.GET)
	public RetResult findAnchorGrade(@RequestParam("userId") String userId){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询主播等级By主播Id,开始:code={},message={},userId={},startTime={}", code,message,userId, startTime);
		
		// 参数校验
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询主播等级By主播Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Map<String, Object> ruleMap = new HashMap<String, Object>();
		try {
			ruleMap = anchorGradeRuleService.findAnchorGradeRule(userId);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询主播等级By主播Id,异常:code={},message={},userId={},e={}", code, message, userId, e);
		}

		Long endTime = System.currentTimeMillis() - startTime;
		GwsLogger.info("查询主播等级By主播Id,结束:code={},message={},userId={},ruleMap={},endTime={}毫秒", code, message, userId,ruleMap, endTime);
		return RetResult.setRetDate(code, message, ruleMap);
	}
	
	
	/**
	 * 
	 *【更新主播经验值】 
	 * @param userId
	 * @param anchorExper
	 * @return RetResult返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月13日下午4:58:53
	 * @throws 异常
	 */
	@ApiOperation(value = "更新经验值", notes = "更新主播经验值")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "主播ID", required = true, dataType = "String"),
	@ApiImplicitParam(name = "anchorExper", value = "主播经验值", required = true, dataType = "Long") })
	@RequestMapping(value = "/updateAnchorExper", method = RequestMethod.POST)
	public RetResult updateAnchorExper(@RequestParam("userId") String userId, @RequestParam("anchorExper") Long anchorExper){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("更新主播经验值开始:code={},message={},userId={},startTime={}", code,message,userId, startTime);
		
		//参数校验
		if (StringUtils.isBlank(userId) || null == anchorExper || 0L > anchorExper) {
			GwsLogger.error("更新主播经验值,参数为空:userId={},anchorExper", userId,anchorExper);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		boolean ret = false;
		try{
			ret = anchorGradeRuleService.updateAnchorExp(userId, anchorExper);
		}catch(Exception e){
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("更新主播经验值,异常:code={},message={},userId={},e={}", code, message, userId, e);
		}
		
		Long endTime = System.currentTimeMillis() - startTime;
		GwsLogger.info("更新主播经验值结束:code={},message={},userId={},endTime={}毫秒", code, message,userId,endTime);
		return RetResult.setRetDate(code, message, ret);
	}
	
	
	/* 
	 * add by szy 2018.3.27
	 * 增加临时调用接口，此接口为一次性接口，主要目的是用于解决数据同步问题
	 * 上线后数据同步完成后此接口即可删除或注释
	 * 将礼物快照表中的卡路里的历史数据同步到ui_identity中的经验值
	 */
	@RequestMapping(value = "/syncAnchorExperAndGrade", method = RequestMethod.GET)
	public boolean syncAnchorExperAndGrade(){
		GwsLogger.info("进入更新主播经验值和等级接口开始...");
		boolean flag = false;
		//1.获取ui_identity中的所有主播ID
		List<String> userIdList = identityService.findUserIdList();
		GwsLogger.info("更新主播经验值和等级:userIdList={}",userIdList.size());
		
		//2.根据主播ID统计查询gift_give_snap的calorie值
		for(String userId : userIdList){
			Long calorie = anchorService.getGiftCalorieByReceiver(Long.valueOf(userId));
			
			//3.根据卡路里判断主播等级
			Short grade = anchorGradeRuleService.findAnchorGrade(calorie);
			
			//4.将calorie的值和等级更新到ui_identity表中
			Short anchortGrade = grade == null ? 1 : grade;
			int res = identityService.updateGradeAndExper(calorie, (int)anchortGrade, userId);
			if(res >= 1){
				flag = true;
				GwsLogger.info("主播经验值,等级,更新后返回值:userId={}, calorie={}, grade={}, res={}, flag={}",userId,calorie,anchortGrade,res,flag);
			}
		}
		
		GwsLogger.info("更新主播经验值和等级:flag={}",flag);
		return flag;
	}
	
}
