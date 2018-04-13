package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.token.JwtUtil;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.enums.LoginWayEnum;
import com.opengroup.longmao.gwcommon.service.LoginService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.UserService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;
import com.opengroup.longmao.gwcommon.tools.sensitive.WordFilter;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【用户登录/账户】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
@EnableSwagger2
@Api(value = "用户登录/账户", tags = "uic")
@RestController
@RequestMapping(value = { "/uic" })
public class LoginController {
	
	@Autowired
	private RedisReadWriteService redis;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WordFilter wordFilter;
	
	@Autowired
	private JwtUtil jwt;
	
	
	@ApiOperation(value = "手机、密码注册", notes = "手机、密码注册")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "用户手机号码", required = true, dataType = "String"), 
			@ApiImplicitParam(name = "passwd", value = "登录密码", required = true, dataType = "String"), 
			@ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String"), 
			@ApiImplicitParam(name = "pushId", value = "推送ID", required = false, dataType = "String"),
			@ApiImplicitParam(name = "chlId", value = "渠道ID", required = false, dataType = "String")
	})
	@RequestMapping(value = "/pwdReg", method = RequestMethod.POST)
	public RetResult pwdReg(String userName, String passwd, String captcha, String pushId, String chlId) {
		Long startTime = System.currentTimeMillis();
		String token = "";
		GwsLogger.info(
				"User服务(LoginController:/uic/pwdReg):手机密码注册,开始:userName={},captcha={},pushId={},chlId={},startTime={}",
				userName, captcha, pushId, chlId, startTime);
		
		// 根据id查询用户信息表
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(passwd) || StringUtils.isBlank(captcha)) {
			GwsLogger.error("手机密码注册,参数非法:userName={},passwd={},captcha={}", userName, passwd, captcha);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userMap, token);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("手机密码注册,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, userMap, token);
		}
		String msg = redis.get(ConfigConstant.REGISTER_MSG, userName);
		if (null == msg) {
			GwsLogger.info("手机密码注册,验证短信未发送或已过期:captcha={}", captcha);
			return RetResult.setRetDate(CommConstant.GWSCOD0006, CommConstant.GWSMSG0006, userMap, token);
		}
		if (!msg.equals(captcha)) {
			GwsLogger.info("手机密码注册,验证码不正确:captcha={}", captcha);
			return RetResult.setRetDate(CommConstant.GWSCOD0005, CommConstant.GWSMSG0005, userMap, token);
		}
		User u = loginService.queryUserByUserName(userName);
		if (null != u) {
			GwsLogger.info("手机密码注册,手机已注册:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0009, CommConstant.GWSMSG0009, userMap, token);
		}
		
		String userId = "注册失败";
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			//手机、验证码登录系统
			userMap = loginService.captchaLogin(userName, passwd, pushId);
			redis.delete(ConfigConstant.REGISTER_MSG, userName);//Redis删除登录验证码
			if (null != userMap && !userMap.isEmpty()) {//登录信息记录
				userId = (String) userMap.get("userId");
				Map<String, Object> loginMap = new HashMap<String, Object>();
				loginMap.put("userId", userMap.get("userId"));
				loginMap.put("chlId", chlId);
				loginMap.put("loginId", userName);
				loginMap.put("way", LoginWayEnum.MOBILE_CAPTCHA.getVal());
				loginMap.put("isFirst", userMap.get("isFirst"));
				loginService.loginRecord(loginMap);
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			//生成token
//			if(Short.valueOf(cache.get(CommConstant.TURN_TOKEN, CommConstant.OFF_ON).toString()).equals(IsOrNotEnum.YES.getType())){
//				createToken(userMap);
			token = establishToken(userMap);
//			};
			GwsLogger.info("手机密码注册,结束:code={},message={},userId={},userName={},endTime={}毫秒", code, message, userId,
					userName, (System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			GwsLogger.error("手机密码注册,失败:code={},message={},userName={},captcha={},pushId={},chlId={},endTime={}毫秒",
					e.getCode(), e.getMessage(), userName, captcha, pushId, chlId, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), userMap,token);
		} catch (Exception e) {
			GwsLogger.error(
					"手机密码注册,异常:code={},message={},userName={},captcha={},pushId={},chlId={},endTime={}毫秒,e={}",
					code, message, userName, captcha, pushId, chlId, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, userMap, token);
	}
	
	/**
	 * 【手机、验证码快速登录】
	 * @author Hermit 2017年5月12日
	 * @param userName
	 * @param captcha
	 * @param pushId
	 * @param chlId
	 * @return RetResult
	 */
	@ApiOperation(value = "手机、验证码登录", notes = "手机、验证码快速登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "用户手机号码", required = true, dataType = "String"), 
		@ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String"), 
		@ApiImplicitParam(name = "pushId", value = "推送ID", required = false, dataType = "String"),
		@ApiImplicitParam(name = "chlId", value = "渠道ID", required = false, dataType = "String")
	})
	@RequestMapping(value = "/quickLogin", method = RequestMethod.POST)
	public RetResult quickLogin(@RequestParam("userName") String userName, @RequestParam("captcha") String captcha,
			String pushId, String chlId) {
		Long startTime = System.currentTimeMillis();
		String token = "";
		GwsLogger.info("手机验证码快速登录,开始:userName={},captcha={},pushId={},chlId={},startTime={}", userName, captcha,
				pushId, chlId, startTime);
		
		// 根据id查询用户信息表
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(captcha)) {
			GwsLogger.error("手机验证码快速登录,参数为空:userName={},captcha={}", userName, captcha);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userMap,token);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("手机验证码快速登录,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, userMap,token);
		}
		//String msg = cache.get(CommConstant.LOGIN_MSG, userName);
		String msg = redis.get(ConfigConstant.LOGIN_MSG, userName);
		if (null == msg) {
			GwsLogger.info("手机验证码快速登录,验证短信未发送或已过期:captcha={}", captcha);
			return RetResult.setRetDate(CommConstant.GWSCOD0006, CommConstant.GWSMSG0006, userMap,token);
		}
		if (!msg.equals(captcha)) {
			GwsLogger.info("手机验证码快速登录,验证码不正确:captcha={}", captcha);
			return RetResult.setRetDate(CommConstant.GWSCOD0005, CommConstant.GWSMSG0005, userMap,token);
		}
		
		String userId = "注册失败";
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			//手机、验证码登录系统
			userMap = loginService.captchaLogin(userName, captcha, pushId);
			redis.delete(ConfigConstant.LOGIN_MSG, userName);//Redis删除登录验证码
			if (null != userMap && !userMap.isEmpty()) {//登录信息记录
				userId = (String) userMap.get("userId");
				Map<String, Object> loginMap = new HashMap<String, Object>();
				loginMap.put("userId", userMap.get("userId"));
				loginMap.put("chlId", chlId);
				loginMap.put("pushId", null == pushId ? "" : pushId);
				loginMap.put("loginId", userName);
				loginMap.put("way", LoginWayEnum.MOBILE_CAPTCHA.getVal());
				loginMap.put("isFirst", userMap.get("isFirst"));
				loginService.loginRecord(loginMap);
				// 添加商城登录key
				Map<String, Object> keyMap = loginService.getShopLoginKey(Long.valueOf(userId));
				userMap.put("shopKey", keyMap.get("shopKey"));
//				userMap.put("shopKey", userId);
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			//生成token
//			if(Short.valueOf(cache.get(CommConstant.TURN_TOKEN, CommConstant.OFF_ON).toString()).equals(IsOrNotEnum.YES.getType())){
//				createToken(userMap);
			token = establishToken(userMap);
//			};
			GwsLogger.info("手机验证码快速登录,结束:code={},message={},userId={},userName={},endTime={}毫秒", code, message, userId,
					userName, (System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			GwsLogger.error("手机验证码快速登录,失败:code={},message={},userName={},captcha={},pushId={},chlId={},endTime={}毫秒",
					e.getCode(), e.getMessage(), userName, captcha, pushId, chlId, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), userMap,token);
		} catch (Exception e) {
			GwsLogger.error(
					"手机验证码快速登录,异常:code={},message={},userName={},captcha={},pushId={},chlId={},endTime={}毫秒,e={}",
					code, message, userName, captcha, pushId, chlId, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, userMap,token);
	}
	
	/**
	 * 【手机、密码快速登录】
	 * @author Hermit 2017年5月12日
	 * @param userName
	 * @param passwd
	 * @param pushId
	 * @param chlId
	 * @return RetResult
	 */
	@ApiOperation(value = "手机、密码登录", notes = "手机、密码快速登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "用户手机号码", required = true, dataType = "String"), 
			@ApiImplicitParam(name = "passwd", value = "登录密码", required = true, dataType = "String"), 
			@ApiImplicitParam(name = "pushId", value = "推送ID", required = false, dataType = "String"),
			@ApiImplicitParam(name = "chlId", value = "渠道ID", required = false, dataType = "String")
	})
	@RequestMapping(value = "/pwdLogin", method = RequestMethod.POST)
	public RetResult pwdLogin(@RequestParam("userName") String userName, @RequestParam("passwd") String passwd,
			String pushId, String chlId) {
		Long startTime = System.currentTimeMillis();
		String token = "";
		GwsLogger.info(
				"User服务(LoginController:/uic/pwdLogin):---手机密码速登录,开始:userName={},passwd={},pushId={},chlId={},startTime={}",
				userName, passwd, pushId, chlId, startTime);

		// 根据id查询用户信息表
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(passwd)) {
			GwsLogger.error("手机密码速登录,参数为空:userName={},passwd={}", userName, passwd);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userMap,token);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("手机密码速登录,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, userMap,token);
		}
		
		String userId = "注册失败";
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			userMap = loginService.pwdLogin(userName, passwd, pushId);
			//手机、密码登录系统
			if (null != userMap && !userMap.isEmpty()) {//登录信息记录
				userId = (String) userMap.get("userId");
				Map<String, Object> loginMap = new HashMap<String, Object>();
				loginMap.put("userId", userMap.get("userId"));
				loginMap.put("chlId", chlId);
				loginMap.put("loginId", userName);
				loginMap.put("way", LoginWayEnum.MOBILE_PSSWD.getVal());
				loginMap.put("isFirst", userMap.get("isFirst"));
				loginMap.put("pushId", null == pushId ? "" : pushId);
				loginService.loginRecord(loginMap);
				// 添加商城登录key
				Map<String, Object> keyMap = loginService.getShopLoginKey(Long.valueOf(userId));
				userMap.put("shopKey", keyMap.get("shopKey"));
//				userMap.put("shopKey", userId);
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			//生成token
//			if(Short.valueOf(cache.get(CommConstant.TURN_TOKEN, CommConstant.OFF_ON).toString()).equals(IsOrNotEnum.YES.getType())){
//				createToken(userMap);
			token = establishToken(userMap);
//			};
			GwsLogger.info("手机密码快速登录,结束:code={},message={},userId={},userName={},endTime={}毫秒", code,
					message, userId, userName, (System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			GwsLogger.error("手机密码快速登录,失败:code={},message={},userName={},passwd={},pushId={},chlId={},endTime={}毫秒",
					e.getCode(), e.getMessage(), userName, passwd, pushId, chlId, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), userMap,token);
		} catch (Exception e) {
			GwsLogger.error("手机密码快速登录,异常:code={},message={},userName={},passwd={},pushId={},chlId={},endTime={}毫秒,e={}",
					code, message, userName, passwd, pushId, chlId, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, userMap,token);
	}
	
	/**
	 * 【微信/QQ/微博快速登录】
	 * @author Hermit 2017年5月12日
	 * @param loginId
	 * @param nickName
	 * @param userHead
	 * @param way
	 * @param pushId
	 * @param chlId
	 * @return RetResult
	 */
	@ApiOperation(value = "第三方登录", notes = "微信/QQ/微博快速登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "loginId", value = "用户唯一账户ID", required = true, dataType = "String"), 
			@ApiImplicitParam(name = "nickName", value = "用户昵称", required = false, dataType = "String"),
			@ApiImplicitParam(name = "userHead", value = "用户头像", required = false, dataType = "String"),
			@ApiImplicitParam(name="way", value="MOB-微信:6, MOB-QQ:7, MOB-微博:8", required=false, dataType="Short"),
			@ApiImplicitParam(name = "pushId", value = "推送ID", required = false, dataType = "String"),
			@ApiImplicitParam(name = "chlId", value = "渠道ID", required = false, dataType = "String"),
			@ApiImplicitParam(name = "oldNickName", value = "原有昵称", required = false, dataType = "String")
	})
	@RequestMapping(value = "/thirdPartyLogin", method = RequestMethod.POST)
	public RetResult thirdPartyLogin(@RequestParam("loginId") String loginId, String nickName, String userHead,
			Short way, String pushId, String chlId, String oldNickName) {
		Long startTime = System.currentTimeMillis();
		String token = "";
		GwsLogger.info("第三方登录,开始:loginId={},nickName={},userHead={},way={},pushId={},chlId={},oldNickName={},startTime={}",
				loginId, nickName, userHead, way, pushId, chlId, oldNickName, startTime);
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(loginId) || StringUtils.isBlank(nickName)) {
			GwsLogger.error("第三方登录,参数为空:loginId={},nickName={}", loginId, nickName);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userMap,token);
		}
		
		String userId = "注册失败";
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("loginId", loginId);
			param.put("nickName", nickName);
			param.put("oldNickName", oldNickName);
			param.put("userHead", userHead);
			param.put("way", way);
			param.put("pushId", pushId);
			
			//根据第三方登录ID查询用户信息
			userMap = loginService.getUserByLoginId(loginId, way, pushId);
			if (null == userMap || userMap.isEmpty()) {
				//昵称敏感词汇过滤判断
				if (wordFilter.isContains(nickName)) {
					GwsLogger.info("第三方登录,昵称存在敏感词汇:loginId={},nickName={}", loginId, nickName);
					return RetResult.setRetDate(CommConstant.GWSCOD0018, CommConstant.GWSMSG0018, userMap,token);
				}
				//昵称唯一性判断
				List<User> userL = userService.queryUserByNickName(nickName);
				if (CollectionUtils.isNotEmpty(userL)) {
					GwsLogger.info("第三方登录,该昵称已存在:loginId={},nickName={}", loginId, nickName);
					return RetResult.setRetDate(CommConstant.GWSCOD0020, CommConstant.GWSMSG0020, userMap,token);
				}
				//登录、注册账户
				userMap = loginService.thirdPartyReg(param);
			}
			if (null != userMap && !userMap.isEmpty()) {
				userId = (String) userMap.get("userId");
				param.put("userId", userMap.get("userId"));
				param.put("chlId", chlId);
				param.put("isFirst", userMap.get("isFirst"));
				loginService.loginRecord(param);//登录信息记录
				// 添加商城登录key
				Map<String, Object> keyMap = loginService.getShopLoginKey(Long.valueOf(userId));
				userMap.put("shopKey", keyMap.get("shopKey"));
//				userMap.put("shopKey", userId);
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			//生成token
//			if(Short.valueOf(cache.get(CommConstant.TURN_TOKEN, CommConstant.OFF_ON).toString()).equals(IsOrNotEnum.YES.getType())){
//				createToken(userMap);
			token = establishToken(userMap);
//			};
			GwsLogger.info("第三方登录,结束:code={},message={},userId={},userMap={},endTime={}毫秒", code,
					message, userId, userMap, (System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			GwsLogger.error("第三方登录,失败:code={},message={},userId={},userMap={},endTime={}毫秒",
					e.getCode(), e.getMessage(), userId, userMap, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), userMap,token);
		} catch (Exception e) {
			GwsLogger.error("第三方登录,异常:code={},message={},userId={},userMap={},endTime={}毫秒,e={}",
					code, message, userId, userMap, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, userMap,token);
	}
	
	/**
	 * 【账号绑定记录】
	 * @author Hermit 2017年5月11日
	 * @param userId
	 * @return RetResult
	 */
	@ApiOperation(value = "账号绑定记录", notes = "账号绑定记录")
	@ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String")
	@RequestMapping(value = "/accountLog", method = RequestMethod.GET)
	public RetResult accountLog(@RequestParam("userId") String userId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询账号绑定记录,开始:userId={},startTime={}", userId, startTime);
		// 根据id查询用户信息表
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询账号绑定记录,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userMap);
		}

		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			userMap = loginService.accountLog(userId);
			if (null != userMap && !userMap.isEmpty()) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("查询账号绑定记录,结束:code={},message={},userId={},endTime={}毫秒", code,
					message, userId, (System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			GwsLogger.error("查询账号绑定记录,失败:code={},message={},userId={},endTime={}毫秒", e.getCode(), e.getMessage(),
					userId, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), userMap);
		} catch (Exception e) {
			GwsLogger.error("查询账号绑定记录,异常:code={},message={},userId={},endTime={}毫秒,e={}", code,
					message, userId, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, userMap);
	}
	
	/**
	 * 【账号绑定】
	 * @author Hermit 2017年5月11日
	 * @param userId
	 * @param account
	 * @param nickName
	 * @param captcha
	 * @param way
	 * @return RetResult
	 */
	@ApiOperation(value = "账号绑定", notes = "绑定手机/微信/QQ/微博等")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String"),
			@ApiImplicitParam(name="account", value="账户", required=true, dataType="String"),
			@ApiImplicitParam(name="nickName", value="昵称", required=false, dataType="String"),
			@ApiImplicitParam(name="captcha", value="验证码", required=false, dataType="String"),
			@ApiImplicitParam(name="way", value="手机:0, 微信:6, QQ:7, 微博:8", required=true, dataType="String")
	})
	@RequestMapping(value = "/boundAccount", method = RequestMethod.POST)
	public RetResult boundAccount(@RequestParam("userId") String userId, @RequestParam("account") String account,
			String nickName, String captcha, Short way) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("账号绑定,开始:userId={},account={},nickName={},captcha={},way={},startTime={}", userId, account,
				nickName, captcha, way, startTime);
		// 根据id查询用户信息表
		Map<String, Object> userMap = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(account)) {
			GwsLogger.error("账号绑定,参数为空:userId={},account={}", userId, account);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, userMap);
		}
		if (LoginWayEnum.MOBILE.getVal() == way && (11 != account.length() || !isNumeric(account))) {
			GwsLogger.error("手机验证码快速登录,手机号码不合法:userName={}", account);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, userMap);
		}
		//根据第三方登录ID查询用户信息
		Map<String, Object> uMap = loginService.getUserByLoginId(account, way, null);
		if (null != uMap && !uMap.isEmpty()) {
			GwsLogger.info("账号绑定,已绑定其他账户:userId={},account={}", userId, account);
			return RetResult.setRetDate(CommConstant.GWSCOD0015, CommConstant.GWSMSG0015, userMap);
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("account", account);
		param.put("nickName", nickName);
		param.put("captcha", captcha);
		param.put("way", String.valueOf(way));
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		try {
			userMap = loginService.boundAccount(param);// 绑定手机/微信/QQ/微博
			if (null != userMap && !userMap.isEmpty()) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("账号绑定,结束:code={},message={},userId={},userMap={},endTime={}毫秒", code, message, userId, param,
					(System.currentTimeMillis() - startTime));
		} catch (ImplException e) {
			GwsLogger.error("账号绑定,失败:code={},message={},userId={},userMap={},endTime={}毫秒",
					e.getCode(), e.getMessage(), userId, param, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), userMap);
		} catch (Exception e) {
			GwsLogger.error("账号绑定,异常:code={},message={},userId={},userMap={},endTime={}毫秒,e={}", code,
					message, userId, param, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, userMap);
	}
	
	/**
	 * 【解除手机/微信/QQ/微博等绑定】
	 * @author Hermit 2017年5月12日
	 * @param userId
	 * @param way
	 * @return RetResult
	 */
	@ApiOperation(value = "解除账号绑定", notes = "解除手机/微信/QQ/微博等绑定")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String"),
			@ApiImplicitParam(name="way", value="手机:0, 微信:6, QQ:7, 微博:8", required=true, dataType="String")
	})
	@RequestMapping(value = "/boundRemove", method = RequestMethod.POST)
	public RetResult boundRemove(@RequestParam("userId") String userId, @RequestParam("way") String way) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("解除账号绑定,开始:userId={},way={},startTime={}", userId, way, startTime);
		// 根据id查询用户信息表
		Map<String, String> param = new HashMap<String, String>();
		
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("解除账号绑定,参数为空:userId={},way={}", userId, way);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}

		try {
			param.put("userId", userId);
			param.put("way", way);
			Boolean flag = loginService.boundRemove(param);//解除绑定手机/微信/QQ/微博
			GwsLogger.info("解除账号绑定,结束:userId={},way={},flag={},endTime={}毫秒", userId, way, flag,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (ImplException e) {
			GwsLogger.error("解除账号绑定,失败:code={},message={},userId={},way={},endTime={}毫秒", e.getCode(), e.getMessage(), userId, way,
					(System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), false);
		} catch (Exception e) {
			GwsLogger.error("解除账号绑定,异常:code={},message={},userId={},way={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, userId, way, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}
	
	/**
	 * @Title: findBackPwd 
	 * @Description: 找回密码 
	 * @param userId
	 * @param loginPwd
	 * @param verificationCode
	 * @return RetResult
	 */
	@ApiOperation(value = "找回密码", notes = "找回密码,修改密码")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String"),
			@ApiImplicitParam(name="loginPwd", value="MD5加密", required=true, dataType="String"),
			@ApiImplicitParam(name="verificationCode", value="验证码", required=true, dataType="String")
	})
	@RequestMapping(value = "/findBackPwd.do", method = RequestMethod.POST)
	public RetResult findBackPwd(@RequestParam("userId") String userId, @RequestParam("loginPwd") String loginPwd,
			@RequestParam("verificationCode") String verificationCode) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("找回密码,开始:userId={},loginPwd={},verificationCode={},startTime={}", userId, loginPwd,
				verificationCode, startTime);

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(loginPwd) || StringUtils.isBlank(verificationCode)) {
			GwsLogger.error("找回密码,参数为空:userId={},loginPwd={},verificationCode={}", userId, loginPwd, verificationCode);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}

		try {
			Boolean flag = loginService.findBackPwd(userId, loginPwd, verificationCode);// 找回密码
			GwsLogger.info("找回密码,结束:userId={},loginPwd={},verificationCode={},endTime={}毫秒", userId, loginPwd,
					verificationCode, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (ImplException e) {
			GwsLogger.error("找回密码,失败:code={},message={},userId={},loginPwd={},verificationCode={},endTime={}毫秒",
					e.getCode(), e.getMessage(), userId, loginPwd, verificationCode, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), false);
		} catch (Exception e) {
			GwsLogger.error("找回密码,异常:code={},message={},userId={},loginPwd={},verificationCode={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, loginPwd, verificationCode, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}
	
	/**
	 * @Title: changeMobile 
	 * @Description: 更换手机号码 
	 * @param userId
	 * @param userName
	 * @param captcha
	 * @return RetResult
	 */
	@ApiOperation(value = "更换手机", notes = "用户更换手机号")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String"),
			@ApiImplicitParam(name="userName", value="手机号码", required=true, dataType="String"),
			@ApiImplicitParam(name="captcha", value="验证码", required=true, dataType="String")
	})
	@RequestMapping(value = "/changeMobile.do", method = RequestMethod.POST)
	public RetResult changeMobile(@RequestParam("userId") String userId, @RequestParam("userName") String userName,
			@RequestParam("captcha") String captcha) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("更换手机号,开始:userId={},userName={},captcha={},startTime={}", userId, userName, captcha, startTime);

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(userName) || StringUtils.isBlank(captcha)) {
			GwsLogger.error("更换手机号,参数为空:userId={},userName={},captcha={}", userId, userName, captcha);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, false);
		}
		if (11 != userName.length() || !isNumeric(userName)) {
			GwsLogger.error("手机验证码快速登录,手机号码不合法:userName={}", userName);
			return RetResult.setRetDate(CommConstant.GWSCOD0008, CommConstant.GWSMSG0008, false);
		}

		try {
			Boolean flag = loginService.changeMobile(userId, userName, captcha);//更换手机号码
			GwsLogger.info("更换手机号,结束:userId={},userName={},captcha={},endTime={}毫秒", 
					userId, userName, captcha, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, flag);
		} catch (ImplException e) {
			GwsLogger.error("更换手机号,失败:code={},message={},userId={},userName={},captcha={},endTime={}毫秒", e.getCode(),
					e.getMessage(), userId, userName, captcha, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(e.getCode(), e.getMessage(), false);
		} catch (Exception e) {
			GwsLogger.error("更换手机号,异常:code={},message={},userId={},userName={},captcha={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, userName, captcha, (System.currentTimeMillis() - startTime), e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, false);
		}
	}
	
	
	/**
	 * 
	 * @Title: refreshToken 
	 * @Description: TODO【刷新token】
	 * @param userId
	 * @param userName
	 * @param captcha
	 * @return    设定文件 
	 * RetResult    返回类型 
	 * @author Yangst
	 * @throws
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public RetResult refreshToken(@RequestParam("token") String token, @RequestParam("refreshtoken") String refreshtoken) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("刷新token开始:code={},message={},token={},refreshtoken={}", code, message, token,refreshtoken);
		if (StringUtils.isBlank(token) || StringUtils.isBlank(refreshtoken)) {
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Claims claims = null;
		try{
			claims = jwt.parseJWT(token);
		}catch(Exception e){
			GwsLogger.error("token过期:code={},message={},token={},e={}", code, message,
					token, e.getMessage());
			try {
				claims = jwt.parseJWT(refreshtoken);
			} catch (Exception e1) {
				GwsLogger.error("refreshtoken过期:code={},message={},refreshtoken={},e={}", code, message,
						refreshtoken, e1.getMessage());
			}		
		}
		try{
			String json = claims.getSubject();
			Map<String, Object> user = JSON.parseObject(json, Map.class);
			String subject = JwtUtil.generalSubject(user);
			token = jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_TTL);
			refreshtoken=jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_REFRESH_TTL);
			//cache.set(CommConstant.JWT_TOKEN, token.trim(), user, CommConstant.JWT_TTL/1000);
			redis.set(ConfigConstant.JWT_TOKEN, token.trim(), user, ConfigConstant.JWT_TTL/1000);
			map.put("token", token);
			map.put("refreshtoken", refreshtoken);
		}catch(Exception e){
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("刷新token异常:code={},message={},token={},refreshtoken={},e={}", code, message,
					token,refreshtoken, e.getMessage());
		}
		
		GwsLogger.info("刷新token结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, map);
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
	
	public Map<String, Object> createToken(Map<String, Object> userMap){
		try {
			String subject = JwtUtil.generalSubject(userMap);
			String token = jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_TTL);
			String refreshToken = jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_REFRESH_TTL);
			userMap.put("token", token);
			userMap.put("refreshToken", refreshToken);
			//cache.set(CommConstant.JWT_TOKEN, token.trim(), userMap, CommConstant.JWT_TTL/1000);
			redis.set(ConfigConstant.JWT_TOKEN, token.trim(), userMap, ConfigConstant.JWT_TTL/1000);
		} catch (Exception e) {
			GwsLogger.error("生成token令牌失败：userMap={}", userMap);
		}
		return userMap;
	}
	
	public String establishToken(Map<String, Object> userMap){
		String token = null;
		try {
			String subject = JwtUtil.generalSubject(userMap);
//			token = jwt.createJWT(ConfigConstant.JWT_ID, subject, ConfigConstant.JWT_TTL);
			token = UUID.randomUUID().toString().replaceAll("-", "");
			redis.set(ConfigConstant.JWT_TOKEN, token.trim(), userMap);			
		} catch (Exception e) {
			GwsLogger.error("生成token令牌失败：userMap={}", userMap);
		}
		return token;
	}
	
	/**
	 * 根据userId生成商城登录key
	 */
	@RequestMapping(value = "/getShopLoginKey", method = RequestMethod.POST)
	public RetResult getShopLoginKey(Long userId) {
		GwsLogger.info("user服务getShopLoginKey接口,根据userId获取商城登录key开始,userId={}", userId);
		
		// 参数校验
		if(null == userId || userId < 1) {
			GwsLogger.info("getShopLoginKey参数非法,userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Map<String, Object> map = null;
		try {
			// 校验是否存在用户
			User user = userService.queryUserByUserId(userId.toString());
			if(null == user) {
				GwsLogger.info("getShopLoginKey用户不存在,userId={}", userId);
				return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
			}
			map = loginService.getShopLoginKey(userId);
		}catch(Exception e) {
			GwsLogger.error("getShopLoginKey异常,error={}", e);
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
		
		GwsLogger.info("user服务getShopLoginKey接口,根据userId获取商城登录key结束");
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
	}
	
}
