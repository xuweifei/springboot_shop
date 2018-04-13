package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.service.LoginService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.RegisterService;
import com.opengroup.longmao.gwcommon.service.UserService;
import com.opengroup.longmao.gwcommon.tools.idutil.StringUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;
import com.opengroup.longmao.gwcommon.tools.sensitive.WordFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 用户注册 Controller
 * @version 1.0
 * @author Hermit 2018年01月23日 上午09:59:49
 */
@EnableSwagger2
@Api(value = "用户注册", tags = "register")
@RestController
@RequestMapping(value = { "/reg" })
public class RegisterController {
	
	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private WordFilter wordFilter;
	
	
	/**
	 * 【微信/QQ/微博三方授权注册】
	 * @author Hermit 2018年1月23日
	 * @param loginId
	 * @param nickName
	 * @param userHead
	 * @param way
	 * @param pushId
	 * @param chlId
	 * @return RetResult
	 */
	@ApiOperation(value = "第三方授权注册", notes = "微信/QQ/微博注册账号")
	@ApiImplicitParams({ @ApiImplicitParam(name = "loginId", value = "用户唯一账户ID", required = true, dataType = "String"), 
			@ApiImplicitParam(name = "nickName", value = "用户昵称", required = true, dataType = "String"),
			@ApiImplicitParam(name = "userHead", value = "用户头像", required = false, dataType = "String"),
			@ApiImplicitParam(name="way", value="MOB-微信:6, MOB-QQ:7, MOB-微博:8", required=false, dataType="Short"),
			@ApiImplicitParam(name = "pushId", value = "推送ID", required = false, dataType = "String"),
			@ApiImplicitParam(name = "chlId", value = "渠道ID", required = true, dataType = "String")
	})
	@RequestMapping(value = "/thirdPartyRegister", method = RequestMethod.POST)
	public RetResult thirdPartyRegister(String loginId, String nickName, String userHead, Short way, String pushId, String chlId) {
		Long startTime = System.currentTimeMillis();
		String thirdPartName = nickName;
		GwsLogger.info("第三方注册开始:loginId={},nickName={},userHead={},way={},pushId={},chlId={},startTime={}", loginId, nickName,
				userHead, way, pushId, chlId, startTime);
		
		// 参数校验
		if(StringUtils.isBlank(loginId) || StringUtils.isBlank(nickName) || StringUtil.isBlank(chlId) || null == way) {
			GwsLogger.error("第三方注册参数非法:loginId={},nickName={},chlId={},way={}", loginId, nickName, chlId, way);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if(!(way - 6 >= 0 && way - 6 <= 2)) {
			GwsLogger.error("第三方注册参数非法:way={}", way);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		// 账号注册
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			// 判断是否已经注册
			User userInDB = userService.getUserByLoginId(loginId, way, pushId);
			if(null != userInDB) {
				GwsLogger.info("第三方注册账号已存在:loginId={},way={}", loginId, way);
				return RetResult.setRetDate(CommConstant.GWSCOD0038, CommConstant.GWSMSG0038, null);
			}
			// 昵称敏感词汇过滤判断
			if (wordFilter.isContains(nickName)) {
				GwsLogger.info("第三方注册昵称存在敏感词汇:loginId={},nickName={}", loginId, nickName);
				return RetResult.setRetDate(CommConstant.GWSCOD0018, CommConstant.GWSMSG0018, null);
			}
			// 昵称唯一性判断
			List<User> userL = userService.queryUserByNickName(nickName);
			if (CollectionUtils.isNotEmpty(userL)) {
				GwsLogger.info("第三方注册该昵称已存在,生成新昵称:loginId={},nickName={}", loginId, nickName);
//				return RetResult.setRetDate(CommConstant.GWSCOD0020, CommConstant.GWSMSG0020, null);
//				nickName = createNewNickName(nickName);
				nickName = "";
			}
			// 注册账户
			returnMap = registerService.thirdPartyReg(loginId, nickName, userHead, way, pushId, chlId, thirdPartName);
		}
		catch (Exception e) {
			GwsLogger.error("第三方注册异常:code={},message={},e={}", CommConstant.GWSCOD0001, CommConstant.GWSCOD0001, e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSCOD0001, null);
		}
		
		GwsLogger.info("第三方注册结束:endTime={}毫秒", (System.currentTimeMillis() - startTime));
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSCOD0000, returnMap);
	}

	/**
	 * 昵称重复时生成新昵称
	 */
	private String createNewNickName(String nickName) {
		int i = 1;
		for(i = 1 ; true ; i++) {
			String temp = nickName + i;
			// 昵称唯一性判断
			List<User> userL = userService.queryUserByNickName(temp);
			if(CollectionUtils.isEmpty(userL)) {
				nickName = nickName + i;
				break;
			}
		}
		return nickName;
	}
	
}
