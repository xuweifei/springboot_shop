package com.opengroup.longmao.gwcommon.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.Attentions;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;
import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserIdentityAuth;
import com.opengroup.longmao.gwcommon.entity.vo.UserVO;
import com.opengroup.longmao.gwcommon.enums.AnchorStatusEnum;
import com.opengroup.longmao.gwcommon.enums.AnchorTypeEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.service.AnchorService;
import com.opengroup.longmao.gwcommon.service.AttentionsService;
import com.opengroup.longmao.gwcommon.service.BeanAccountService;
import com.opengroup.longmao.gwcommon.service.IdentityService;
import com.opengroup.longmao.gwcommon.service.IncomeService;
import com.opengroup.longmao.gwcommon.service.LoginService;
import com.opengroup.longmao.gwcommon.service.SysConfigService;
import com.opengroup.longmao.gwcommon.service.UserGradeRuleService;
import com.opengroup.longmao.gwcommon.service.UserIdentityAuthService;
import com.opengroup.longmao.gwcommon.service.UserService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.idutil.StringUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;
import com.opengroup.longmao.gwcommon.tools.sensitive.WordFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【用户信息表】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
@EnableSwagger2
@Api(value = "用户信息", tags = "uic")
@RestController
@RequestMapping(value = { "/uic" })
public class UserController {
	
	@Autowired
	private WordFilter wordFilter;

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private IncomeService incomeService;
	
	@Autowired
	private AnchorService anchorService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private AttentionsService attentionsService;
	
	@Autowired
	private BeanAccountService beanAccountService;
	
	@Autowired
	private UserGradeRuleService userGradeRuleService;
	
	@Autowired
	private UserIdentityAuthService userIdentityAuthService;
	
	@Autowired	
	private IdentityService identityService;
	
	
	/**
	 * 【根据id查询用户信息】
	 * @param userId
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 上午09:59:49
	 */
	@ApiOperation(value = "根据userId查询用户信息", notes = "根据userId查询用户信息")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String")
	@RequestMapping(value = "/userCenter.do", method = RequestMethod.POST)
	public RetResult userCenter(@RequestParam("userId") String userId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询用户信息By用户Id,开始:userId={},startTime={}", userId, startTime);
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询用户信息By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		UserVO userVO = null;
		try {
			User u = userService.queryUserByUserId(userId);
			if (null != u) {
				userVO = UserVO.readFromUser(u);
				//主播累计卡路里
				Long totClr = anchorService.getGiftCalorieByReceiver(Long.valueOf(userId));
				userVO.setCalorie(totClr);
				//我所关注的人(关注)
				List<Attentions> attention = attentionsService.queryUserByUserId(userId);
				userVO.setAttentionNum(null == attention ? 0 : attention.size());
				//关注我的人(粉丝)
				List<Attentions> fans = attentionsService.queryUserByAnchorUserId(userId);
				userVO.setAttentionedNum(null == fans ? 0 : fans.size());
				//用户头像
				userVO.setPhotoUrl(null == u.getPhoneId() ? "" : u.getPhoneId());
				//主播直播封面
				LiveRoomInfo roomInfo = anchorService.findRoomInfoByUserId(Long.valueOf(u.getUserId()));
				userVO.setLiveCover(null == roomInfo ? u.getPhoneId() : roomInfo.getLiveCover());
				//用户头像(后期删除)
				userVO.setPhoneId(null == u.getPhoneId() ? "" : u.getPhoneId());
				//主播信誉(后期删除)
				IdentityInfo info = incomeService.queryIdentityByUserId(userId);
				userVO.setAnchorType(AnchorTypeEnum.FAMILY_ANCHOR.getType());
				if (null != info) {
					userVO.setCreditGrade(info.getCreditGrade());
					userVO.setCreditGradeExplain(info.getCreditGradeExplain());
					userVO.setIsGuess(info.getIsGuess());
					userVO.setAnchorType(info.getAnchorType());
				}
				//龙猫币
				//CoinAccount coin = coinAccountService.getCoinByUserId(userId);
				//userVO.setLmCoin(null == coin ? 0L : new BigDecimal(coin.getLmCoinNum()).longValue());
				//龙猫豆
				BeanAccount bean = beanAccountService.getBeanByUserId(userId);
				userVO.setLmBean(null == bean ? 0L : new BigDecimal(bean.getLmBeanNum()).longValue());
				//设置密码
				userVO.setLoginPwd(u.getLoginPwd());
			}
			
			//add by szy 2018.3.20 增加主播等级字段 beg
			IdentityInfo identity = identityService.findIdentityByUserId(userId);
			if (null != identity) {
				userVO.setAnchorGrade(identity.getGrade());
			} 
			//add by szy 2018.3.20 增加主播等级字段 end
			
			
			GwsLogger.info("查询用户信息By用户Id,结束:userId={},endTime={}毫秒", userId,(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, userVO);
		} catch (Exception e) {
			GwsLogger.error("查询用户信息By用户Id,异常:code={},message={},userId={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, userId, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
	}

	@ApiOperation(value = "查询用户信息与主播关系", notes = "查询用户信息与主播关系")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "groupId", value = "群组Id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "anchorId", value = "主播Id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "manageId", value = "管理Id", required = false, dataType = "Long")})
	@RequestMapping(value = "/relation", method = RequestMethod.GET)
	public RetResult relation(Long userId, Long groupId, Long anchorId, Long manageId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询用户信息与主播关系,开始:userId={},groupId={},anchorId={},manageId={},startTime={}", userId, groupId,
				anchorId, manageId, startTime);
		Map<String, Object> map = new HashMap<String, Object>();
		
		String code = CommConstant.GWSCOD0003;
		String message = CommConstant.GWSMSG0003;
		if (null == userId || 0 > userId) {
			GwsLogger.error("查询用户信息与主播关系,用户userId不合法:code={},message={},userId={}", code, message, userId);
			return RetResult.setRetDate(code, message, map);
		}
		if (null == anchorId || 0 > anchorId) {
			GwsLogger.error("查询用户信息与主播关系,主播Id不合法:code={},message={},anchorId={}", code, message, anchorId);
			return RetResult.setRetDate(code, message, map);
		}
		if (null == groupId || 0 > groupId) {
			GwsLogger.error("查询用户信息与主播关系,群组Id不合法:code={},message={},groupId={}", code, message, groupId);
			return RetResult.setRetDate(code, message, map);
		}
		if (null == manageId || 0 > manageId) {//未传manageId则赋值userId
			GwsLogger.info("查询用户信息与主播关系,管理Id为空,userId赋值manageId:userId={},manageId={}", userId, manageId);
			manageId = userId;
		}
		
		try {
			map = userService.relation(userId, groupId, anchorId, manageId);
			GwsLogger.info("查询用户信息与主播关系,结束:userId={},groupId={},anchorId={},manageId={},endTime={}毫秒", userId, groupId,
					anchorId, manageId, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
		} catch (Exception e) {
			GwsLogger.error("查询用户信息与主播关系,异常:code={},message={},userId={},userId={},anchorId={},manageId={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, groupId, anchorId, manageId,
					(System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, map);
		}
	}
	
	/**
	 * 【根据userId查询用户头像和昵称】
	 * @param id
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 上午09:59:49
	 */
	@ApiOperation(value = "根据userId查询用户头像和昵称", notes = "根据userId查询用户头像和昵称")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String")
	@RequestMapping(value = "/getUserHeadAndNickName", method = RequestMethod.POST)
	public RetResult getUserHeadAndNickName(@RequestParam("userId") String userId) {
		//封装数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("photoUrl", "");
		map.put("nickName", "");
		
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询用户头像和昵称By用户Id,开始:userId={},startTime={}", userId, startTime);
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询用户头像和昵称By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, map);
		}
		
		try {
			User user = userService.queryUserByUserId(userId);
			if (null != user) {
				String nickName = StringUtils.isNotBlank(user.getNickName()) ? user.getNickName() : "";
				String photoUrl = StringUtils.isNotBlank(user.getPhoneId()) ? user.getPhoneId() : "";
				map.put("userId", userId);
				map.put("photoUrl", photoUrl);
				map.put("nickName", nickName);
			}
			GwsLogger.info("查询用户头像和昵称By用户Id,结束:userMap={},endTime={}毫秒", map, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
		} catch (Exception e) {
			GwsLogger.error("查询用户头像和昵称By用户Id,异常:code={},message={},userMap={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, map, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, map);
		}
	}

	/**
	 * 【查询所有用户信息并分页】
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 上午09:59:49
	 */
	@ApiOperation(value = "查询所有用户信息并分页", notes = "查询所有用户信息并分页")
	@ApiImplicitParams({ @ApiImplicitParam(name = "User", value = "详细实体User", required = true, dataType = "User"),
			@ApiImplicitParam(name = "pageNo", value = "第几页", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "String")
	})
	@RequestMapping(value = "/findAllUser", method = RequestMethod.GET)
	public RetResult findAllUser(@RequestBody User user, Integer pageNo, Integer pageSize) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询所有用户信息,开始:pageNo={},pageSize={},user={},startTime={}", pageNo, pageSize,
				JSON.toJSONString(user), startTime);
		// 查询所有用户信息表
		Page<User> page = null;
		try {
			page = userService.findAllUser(user, pageNo - 1, pageSize, ConfigConstant.SORT_FIELD_CTIME);
			GwsLogger.info("查询所有用户信息,结束:user={},pageNo={},pageSize={},softField={},endTime={}毫秒",
					(System.currentTimeMillis() - startTime), pageNo, pageSize, ConfigConstant.SORT_FIELD_CTIME, JSON.toJSONString(user));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, page);
		} catch (Exception e) {
			GwsLogger.error("查询所有用户信息,异常:code={},message={},user={},pageNo={},pageSize={},softField={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, JSON.toJSONString(user),
					pageNo, pageSize, ConfigConstant.SORT_FIELD_CTIME, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
	}
	
	/**
	 * 【是否可以开播】
	 * @author Hermit 2017年5月11日
	 * @param userId
	 * @return RetResult
	 */
	@ApiOperation(value = "是否可以开播", notes = "查询主播开播权限")
	@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String")
	@RequestMapping(value = "/isOpen", method = RequestMethod.GET)
	public RetResult isOpen(@RequestParam("userId") String userId) {
		// 根据id查询用户信息表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAuth", 10);
		map.put("isLive", false);
		map.put("isAnchor", false);
		map.put("isOpen", false);
		map.put("anchorType", AnchorTypeEnum.FAMILY_ANCHOR.getType());
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询用户开播权限By用户Id,开始:userId={},startTime={}", userId, startTime);
		
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询用户开播权限By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, map);
		}
		
		try {
			IdentityInfo info = incomeService.queryIdentityByUserId(userId);
			UserIdentityAuth auth = userIdentityAuthService.findUserIdentityAuth(Long.valueOf(userId));
			if (null != info) {
				// 判断服务端是否开启直播(1是,2否)
				String liveSet = sysConfigService.sysConfig("live_set");
				Boolean isLive = IsOrNotEnum.YES.getType().equals(Short.valueOf(liveSet)) ? true : false;
				Boolean isAnchor = (AnchorStatusEnum.ANCHOR_NO_PASS.getType() == info.getStatus() ? true : false);
				Boolean isOpen = (AnchorStatusEnum.OPEN_LIVE.getType() == info.getIsLive() ? true : false);
				Short anchorType = info.getAnchorType();
				map.put("isLive", isLive);
				map.put("isAnchor", isAnchor);
				map.put("isOpen", isOpen);
				map.put("anchorType", anchorType);
			}
			if (null != auth) {
				map.put("isAuth", auth.getAuthStatus());	
			}
			GwsLogger.info("查询用户开播权限By用户Id,结束:code={},message={},userId={},userMap={},endTime={}毫秒",
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, userId, map,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
		} catch (Exception e) {
			GwsLogger.error("查询用户开播权限By用户Id,异常:code={},message={},userId={},userMap={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, map, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, map);
		}
	}
	
	@ApiOperation(value = "查询主播信息", notes = "查询主播信息")
	@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String")
	@RequestMapping(value = "/anchorInfo", method = RequestMethod.POST)
	public RetResult anchorInfo(@RequestParam("userId") String userId) {
		// 根据id查询用户信息表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAnchor", false);
		map.put("isOpen", false);
		map.put("nickName", "");
		map.put("phoneId", "");
		map.put("grade", 1);
		
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询主播信息By用户Id,开始:userId={},startTime={}", userId, startTime);
		
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询主播信息By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, map);
		}
		
		try {
			IdentityInfo info = incomeService.queryIdentityByUserId(userId);
			User u = userService.queryUserByUserId(userId);
			if (null != info && null != u) {
				boolean isAnchor = (AnchorStatusEnum.ANCHOR_NO_PASS.getType() == info.getStatus() ? true : false);
				boolean isOpen = (AnchorStatusEnum.OPEN_LIVE.getType() == info.getIsLive() ? true : false);
				map.put("isAnchor", isAnchor);
				map.put("isOpen", isOpen);
				map.put("nickName", u.getNickName());
				map.put("phoneId", u.getPhoneId());
				map.put("grade", u.getGrade());
			}
			GwsLogger.info("查询主播信息By用户Id,结束:code={},message={},userId={},userMap={},endTime={}毫秒",
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, userId, map,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
		} catch (Exception e) {
			GwsLogger.error("查询主播信息By用户Id,异常:code={},message={},userId={},userMap={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, map,
					(System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, map);
		}
	}
	
	/**
	 * 【用户等级】
	 * @author Hermit 2017年5月11日
	 * @param userId
	 * @return RetResult
	 */
	@ApiOperation(value = "用户等级", notes = "查询用户等级")
	@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String")
	@RequestMapping(value = "/findGrade", method = RequestMethod.GET)
	public RetResult findGrade(@RequestParam("userId") String userId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询用户等级By用户Id,开始:userId={},startTime={}", userId, startTime);
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询用户等级By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		try {
			Map<String, Object> map = userGradeRuleService.findUserGradeRule(userId);
			GwsLogger.info("查询用户等级By用户Id,结束:code={},message={},userId={},userMap={},endTime={}毫秒",
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, userId, map,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
		} catch (Exception e) {
			GwsLogger.error("查询用户等级By用户Id,异常:code={},message={},userId={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, userId, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
	}
	
	/**
	 * 【更新用户经验值】
	 * @author Hermit 2017年5月11日
	 * @param userId
	 * @param userExp
	 * @return RetResult
	 */
	@ApiOperation(value = "更新用户经验值", notes = "更新用户经验值")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "userExp", value = "用户经验值", required = true, dataType = "Long") })
	@RequestMapping(value = "/updateExp", method = RequestMethod.POST)
	public RetResult updateExp(@RequestParam("userId") String userId, @RequestParam("userExp") Long userExp) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("更新用户经验值By用户Id,开始:userId={},userExp={},startTime={}", userId, userExp, startTime);
		if (StringUtils.isBlank(userId) || null == userExp || 0L > userExp) {
			GwsLogger.error("更新用户经验值By用户Id,参数为空:userId={},userExp={}", userId, userExp);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}

		try {
			Boolean flag = userGradeRuleService.updateExp(userId, userExp);
			GwsLogger.info("更新用户经验值By用户Id,结束:code={},message={},userId={},userExp={},flag={},endTime={}毫秒",
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, userId, userExp, flag,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (Exception e) {
			GwsLogger.error("更新用户经验值By用户Id,异常:code={},message={},userId={},userExp={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, userExp,
					(System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}
	
	/**
	 * 【批量更新用户经验值】
	 * @author Hermit 2017年5月11日
	 * @param userExpList
	 * @return RetResult
	 */
	@ApiOperation(value = "批量更新用户经验值", notes = "批量更新用户经验值")
	@ApiImplicitParam(name = "userExpList", value = "用户经验值集合", required = true, dataType = "String")
	@RequestMapping(value = "/updateExpBatch", method = RequestMethod.POST)
	public RetResult updateExpBatch(String userExpList) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("批量更新用户经验值,开始:userExpList={},startTime={}", userExpList, startTime);
		if (StringUtils.isBlank(userExpList)) {
			GwsLogger.error("批量更新用户经验值,参数为空:userExpList={}", userExpList);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}

		try {
			List<Map<String, Object>> expList = JSON.parseObject(userExpList, 
					new TypeReference<List<Map<String,Object>>>(){});
			Boolean flag = userGradeRuleService.updateExpBatch(expList);
			GwsLogger.info("批量更新用户经验值,结束:code={},message={},userExpList={},flag={},endTime={}毫秒",
					CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, userExpList, flag,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (Exception e) {
			GwsLogger.error("批量更新用户经验值,异常:code={},message={},userExpList={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, userExpList, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}
	
	/**
	 * @Title: editUser 
	 * @Description: 编辑用户资料 
	 * @param userId
	 * @param phoneId
	 * @param nickName
	 * @param sex
	 * @param city
	 * @param signed
	 * @return RetResult
	 */
	@ApiOperation(value = "编辑用户资料", notes = "编辑用户资料:昵称/头像/简介/性别/城市等")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String"),
			@ApiImplicitParam(name="phoneId", value="用户头像", required=false, dataType="String"),
			@ApiImplicitParam(name="nickName", value="用户昵称", required=false, dataType="String"),
			@ApiImplicitParam(name="sex", value="用户性别", required=false, dataType="String"),
			@ApiImplicitParam(name="city", value="用户城市", required=false, dataType="String"),
			@ApiImplicitParam(name="signed", value="用户简介", required=false, dataType="String")
	})
	@RequestMapping(value = "/editUserData", method = RequestMethod.POST)
	public RetResult editUser(@RequestParam("userId") String userId, String phoneId, String nickName, String sex,
			String city, String signed) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("编辑用户资料,开始:userId={},phoneId={},nickName={},sex={},city={},signed={},startTime={}",
				userId, phoneId, nickName, sex, city, signed, startTime);

		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("编辑用户资料,参数非法:userId={},nickName={}", userId, nickName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}
		//昵称敏感词汇过滤判断
		if (wordFilter.isContains(nickName)) {
			GwsLogger.info("编辑用户资料,昵称存在敏感词汇!:code={},message={},userId={},nickName={}", CommConstant.GWSCOD0018,
					CommConstant.GWSMSG0018, userId, nickName);
			return RetResult.setRetDate(CommConstant.GWSCOD0018, CommConstant.GWSMSG0018, false);
		}
		if (StringUtils.isNotBlank(nickName)) {
			//昵称唯一性判断
			List<User> userL = userService.queryUserByNickName(nickName);
			if (CollectionUtils.isNotEmpty(userL)) {
				if (1 == userL.size() && !userId.equals(userL.get(0).getUserId())) {
					GwsLogger.info("编辑用户资料,该昵称已存在!:code={},message={},userId={},nickName={}", CommConstant.GWSCOD0020,
							CommConstant.GWSMSG0020, userId, nickName);
					return RetResult.setRetDate(CommConstant.GWSCOD0020, CommConstant.GWSMSG0020, false);
				} else if (1 < userL.size()) {
					GwsLogger.info("编辑用户资料,该昵称已存在!:code={},message={},userId={},nickName={}", CommConstant.GWSCOD0020,
							CommConstant.GWSMSG0020, userId, nickName);
					return RetResult.setRetDate(CommConstant.GWSCOD0020, CommConstant.GWSMSG0020, false);
				}
			}
		}
		//简介敏感词汇过滤判断
		if (wordFilter.isContains(signed)) {
			GwsLogger.info("编辑用户资料,简介存在敏感词汇!:code={},message={},userId={},nickName={}", CommConstant.GWSCOD0019,
					CommConstant.GWSMSG0019, userId, nickName);
			return RetResult.setRetDate(CommConstant.GWSCOD0019, CommConstant.GWSMSG0019, false);
		}
		
		Map<String, String> param = new HashMap<String, String>();
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		Boolean flag = false;
		try {
			param.put("userId", userId);
			param.put("phoneId", phoneId);
			param.put("nickName", nickName);
			param.put("sex", sex);
			param.put("city", city);
			param.put("signed", signed);
			flag = userService.editUserData(param);
			if (flag) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("编辑用户资料,结束:code={},message={},userId={},userMap={},flag={},endTime={}毫秒",
					code, message, userId, param, flag,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("编辑用户资料,异常:code={},message={},userId={},userMap={},endTime={}毫秒,e={}", code,
					message, userId, param, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "机器人注册")
	@RequestMapping(value = "/createRobot", method = RequestMethod.POST)
	public RetResult createRobot(HttpServletRequest request) {
		String params = getParams(request);
		JSONObject json = JSON.parseObject(params);
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("Manage调用机器人注册,开始:JSONObject={},startTime={}", json, startTime);
		
		if (!json.containsKey("userId") || !json.containsKey("loginId") || !json.containsKey("nickName")
				|| !json.containsKey("phoneId") || !json.containsKey("sex") || !json.containsKey("grade")) {
			GwsLogger.error("Manage调用机器人注册,参数非法:JSONObject={}", json);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		Boolean flag = false;
		try {
			User u = loginService.robotReg(json);
			if (null != u) {
				flag = true;
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("Manage调用机器人注册,结束:code={},message={},flag={},endTime={}毫秒", code, message, flag,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("Manage调用机器人注册,异常:code={},message={},JSONObject={},endTime={}毫秒,e={}",
					code, message, json, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, flag);
	}
	
	/**
	 * @Title: getParams 
	 * @Description: 获取数据流 
	 * @param request
	 * @return String
	 */
	private static String getParams(HttpServletRequest request) {
		BufferedReader reader = null;
		InputStream is = null;
		StringBuffer sbf = new StringBuffer();
		try {
			is = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return sbf.toString();
	}
	
	@ApiOperation(value = "查询第三方账号是否已注册", notes = "微信/QQ/微博注册账号")
	@ApiImplicitParams({ @ApiImplicitParam(name = "loginId", value = "用户唯一账户ID", required = true, dataType = "String"), 
			@ApiImplicitParam(name="way", value="MOB-微信:6, MOB-QQ:7, MOB-微博:8", required=false, dataType="Short"),
	})
	@RequestMapping(value = "/checkLoginId", method = RequestMethod.POST)
	public RetResult checkLoginId(String loginId, Short way) {
		int beginSecond = DateUtil.currentSecond();
		GwsLogger.info("查询第三方账号是否已注册开始:loginId={},way={}", loginId, way);
		
		// 参数校验
		if(StringUtil.isBlank(loginId) || !(way - 6 >= 0 && way - 6 <= 2)) {
			GwsLogger.info("查询第三方账号是否已注册参数非法:loginId={},way={}", loginId, way);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		// 判断是否已经注册
		Map<String, Object> map = new HashMap<>();
		map.put("flag", 1);
		try {
			User userInDB = userService.getUserByLoginId(loginId, way, "pushId");
			if(null == userInDB) {
				map.put("flag", 2);
			}
		}catch(Exception e) {
			GwsLogger.info("查询第三方账号是否已注册结束,耗时={}秒", DateUtil.currentSecond() - beginSecond);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
		
		GwsLogger.info("查询第三方账号是否已注册结束,耗时={}秒", DateUtil.currentSecond() - beginSecond);
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
	}
	
	@ApiOperation(value = "查询手机号是否已注册", notes = "查询手机号是否已注册")
	@ApiImplicitParam(name="userName", value="MOB-微信:6, MOB-QQ:7, MOB-微博:8", required=false, dataType="Short")
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	public RetResult checkUserName(String userName) {
		GwsLogger.info("checkUserName查询手机号是否已注册开始");
		
		// 参数校验
		if(StringUtil.isBlank(userName)) {
			GwsLogger.info("checkUserName参数非法,userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		String isExist = "2";
		try {
			User user = userService.queryUserByMobile(userName);
			if(null != user) {
				isExist = "1";
			}
		}catch(Exception e) {
			GwsLogger.info("checkUserName异常,e={}", e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
		GwsLogger.info("checkUserName查询手机号是否已注册结束");
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, isExist);
	}
	
}
