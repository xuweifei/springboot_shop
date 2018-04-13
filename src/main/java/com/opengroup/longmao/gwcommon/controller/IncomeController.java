/**
 * @Title: IncomeController.java 
 * @Package com.opengroup.longmao.gwcommon.controller 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.service.IncomeService;
import com.opengroup.longmao.gwcommon.service.SysConfigService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: IncomeController 
 * @Description: 用户中心-我的收益
 * @author Mr.Zhu
 */
@EnableSwagger2
@Api(value = "我的收益", tags = "inc")
@RestController
@RequestMapping(value = { "/inc" })
public class IncomeController {
	
	@Autowired
	private IncomeService incomeService;
	
	@Autowired
	private SysConfigService SysConfigService;
	
	@ApiOperation(value = "发起提现操作接口", notes = "获取提现相关数据")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String")})
	@RequestMapping(value = "/enterExtractCash", method = RequestMethod.GET)
	public RetResult enterExtractCash(@RequestParam("userId") String userId) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("发起提现开始:code={},message={},userId={}", code, message, userId);
		Map<String, Object> map = new HashMap<String, Object>();
		return RetResult.setRetDate(code, message, map);
//		try {
//			if (StringUtils.isNotBlank(userId)) {
//				map = incomeService.queryExtractCashByUserId(userId);
//			} else {
//				GwsLogger.error("userId不存在:userId={}", userId);
//			}
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("发起提现异常:code={},message={},userId={},e={}", code, message, userId, e);
//		}
//		GwsLogger.info("发起提现结束:code={},message={}", code, message);
//		return RetResult.setRetDate(code, message, map);
	}
	
	@ApiOperation(value = "提现记录操作接口", notes = "获取提现记录相关数据")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String")})
	@RequestMapping(value = "/extractCashLog", method = RequestMethod.GET)
	public RetResult extractCashLog(@RequestParam("userId") String userId) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("获取提现记录开始:code={},message={},userId={}", code, message, userId);
		Map<String, Object> map = new HashMap<String, Object>();
		return RetResult.setRetDate(code, message, map);
//		try {
//			if (StringUtils.isNotBlank(userId)) {
//				map = incomeService.queryExtractCashLogByUserId(userId);
//			} else {
//				GwsLogger.error("userId不存在:userId={}", userId);
//			}
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("获取提现记录异常:code={},message={},userId={},e={}", code, message, userId, e);
//		}
//		GwsLogger.info("获取提现记录结束:code={},message={}", code, message);
//		return RetResult.setRetDate(code, message, map);
	}
	
	@ApiOperation(value = "提现记录详情操作接口", notes = "获取提现详情记录相关数据")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String"),
			@ApiImplicitParam(name="orderId", value="订单号", required=true, dataType="String")
	})
	@RequestMapping(value = "/extractCashDetails", method = RequestMethod.GET)
	public RetResult extractCashDetails(@RequestParam("userId") String userId, @RequestParam("orderId") String orderId) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("获取提现详情记录开始:code={},message={},userId={},orderId={}", code, message, userId, orderId);
		
		Map<String, Object> details = new HashMap<String, Object>();
		return RetResult.setRetDate(code, message, details);
//		if (StringUtils.isBlank(userId) && StringUtils.isBlank(orderId)) {
//			return RetResult.setRetDate(CommConstant.GWSCOD0001, "参数不存在!", details);
//		}
//
//		try {
//			details = incomeService.queryExtractCashDetails(userId, orderId);
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("获取提现详情记录异常:code={},message={},userId={},orderId={},e={}", code, message, userId, orderId,
//					e);
//		}
//		GwsLogger.info("获取提现详情记录结束:code={},message={}", code, message);
//		return RetResult.setRetDate(code, message, details);
	}
	
	@ApiOperation(value = "绑定提现账户接口", notes = "绑定提现账户-支付宝")
	@ApiImplicitParams({ @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="String"),
			@ApiImplicitParam(name="account", value="支付宝账户", required=true, dataType="String"), 
			@ApiImplicitParam(name="realName", value="真实姓名", required=true, dataType="String"), 
			@ApiImplicitParam(name="captcha", value="验证码(可用于更改提现账户验证)", required=false, dataType="String")})
	@RequestMapping(value = "/boundAlipay", method = RequestMethod.POST)
	public RetResult boundAlipay(@RequestParam("userId") String userId, @RequestParam("account") String account,
			@RequestParam("realName") String realName, String captcha) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("绑定支付宝开始:code={},message={},userId={}", code, message, userId);

		boolean flag = false;
		try {
			if (StringUtils.isNotBlank(userId)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("account", account);
				map.put("realName", realName);
				map.put("captcha", captcha);

				flag = incomeService.editIdentity(map);
			} else {
				GwsLogger.error("userId不存在:userId={}", userId);
			}
			GwsLogger.info("绑定支付宝结束:code={},message={}", code, message);
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("绑定支付宝异常:code={},message={},userId={},account={},realName={},captcha={},e={}", code,
					message, userId, account, realName, captcha, e);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("绑定支付宝异常:code={},message={},userId={},account={},realName={},captcha={},e={}", code,
					message, userId, account, realName, captcha, e);
		}
		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "提现接口", notes = "卡路里提现RMB")
	@ApiImplicitParams({ @ApiImplicitParam(name="data", value="Base64加密数据", required=true, dataType="String")})
	@RequestMapping(value = "/extractCash", method = RequestMethod.POST)
	public RetResult extractCash(@RequestParam("data") String data) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("卡路里提现开始:code={},message={},data={}", code, message, data);
		// 判断提现功能是否开启(1是,2否)
		String incSet = SysConfigService.sysConfig("inc_set");
		if ("2".equals(incSet)) {
			GwsLogger.info("卡路里提现功能关闭：data={}", data);
			return RetResult.setRetDate(CommConstant.GWSCOD0022, CommConstant.GWSMSG0022, false);
		}
		return RetResult.setRetDate(code, message, false);
//		boolean flag = false;
//		try {
////			Calendar cal = Calendar.getInstance();
////			cal.setTime(new Date()); 
////			int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
////			if (1 == week) {
//				String decryptStr = new String(Base64.decodeBase64(data.getBytes("utf-8")), "utf-8");
//				if (StringUtils.isNotBlank(decryptStr)) {
//					JSONObject json = JSON.parseObject(decryptStr);
//					if (null == json.get("userId") && null == json.get("balance") && null == json.get("sign")) {
//						GwsLogger.error("卡路里提现参数不能为空:userId={},balance={},sign={}", json.get("userId"), json.get("balance"),
//								json.get("sign"));
//						return RetResult.setRetDate(CommConstant.GWSCOD0003, "卡路里提现参数不能为空", null);
//					}
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("userId", json.get("userId"));
//					map.put("balance", json.get("balance"));
//					map.put("sign", json.get("sign"));
//
//					flag = incomeService.extractCash(map);
//					GwsLogger.info("卡路里提现结束:code={},message={}", code, message);
//				} else {
//					GwsLogger.error("Base64解密失败:data={}", data);
//					return RetResult.setRetDate(CommConstant.GWSCOD0003, "Base64解密失败", null);
//				}
////			} else {
////				code = CommConstant.GWSCOD0001;
////				message = "Sorry,提现时间为每周一!";
////			}
//		} catch (ImplException e) {
//			code = e.getCode();
//			message = e.getMessage();
//			GwsLogger.error("卡路里提现异常:code={},message={},data={},e={}", code, message, data, e);
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("卡路里提现异常:code={},message={},data={},e={}", code, message, data, e);
//		}
//		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "提现接口", notes = "卡路里提现RMB(卡路里)")
	@ApiImplicitParams({ @ApiImplicitParam(name="data", value="Base64加密数据", required=true, dataType="String")})
	@RequestMapping(value = "/extractCalorie", method = RequestMethod.POST)
	public RetResult extractCalorie(@RequestParam("data") String data) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("卡路里提现开始:code={},message={},data={}", code, message, data);
		// 判断提现功能是否开启(1是,2否)
		String incSet = SysConfigService.sysConfig("inc_set");
		if ("2".equals(incSet)) {
			GwsLogger.info("卡路里提现功能关闭：data={}", data);
			return RetResult.setRetDate(CommConstant.GWSCOD0022, CommConstant.GWSMSG0022, null);
		}
		return RetResult.setRetDate(code, message, false);
//		boolean flag = false;
//		try {
////			Calendar cal = Calendar.getInstance();
////			cal.setTime(new Date()); 
////			int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
////			if (1 == week) {
//				String decryptStr = new String(Base64.decodeBase64(data.getBytes("utf-8")), "utf-8");
//				if (StringUtils.isNotBlank(decryptStr)) {
//					JSONObject json = JSON.parseObject(decryptStr);
//					if (null == json.get("userId") && null == json.get("balance") && null == json.get("sign")) {
//						GwsLogger.error("卡路里提现参数不能为空:userId={},balance={},sign={}", json.get("userId"), json.get("balance"),
//								json.get("sign"));
//						return RetResult.setRetDate(CommConstant.GWSCOD0003, "卡路里提现参数不能为空", null);
//					}
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("userId", json.get("userId"));
//					map.put("balance", json.get("balance"));
//					map.put("sign", json.get("sign"));
//
//					flag = incomeService.extractCalorie(map);
//					GwsLogger.info("卡路里提现结束:code={},message={}", code, message);
//				} else {
//					GwsLogger.error("Base64解密失败:data={}", data);
//					return RetResult.setRetDate(CommConstant.GWSCOD0003, "Base64解密失败", null);
//				}
////			} else {
////				code = CommConstant.GWSCOD0001;
////				message = "Sorry,提现时间为每周一!";
////			}
//		} catch (ImplException e) {
//			code = e.getCode();
//			message = e.getMessage();
//			GwsLogger.error("卡路里提现异常:code={},message={},data={},e={}", code, message, data, e);
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("卡路里提现异常:code={},message={},data={},e={}", code, message, data, e);
//		}
//		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "更新主播猫眼石值", notes = "更新主播猫眼石值")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "userOpal", value = "主播猫眼石", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "changeType", value = "猫眼石增减", required = true, dataType = "Integer")
	})
	@RequestMapping(value = "/updateOpal", method = RequestMethod.POST)
	public RetResult updateOpal(@RequestParam("userId") String userId, @RequestParam("userOpal") Long userOpal,
			@RequestParam("changeType") Integer changeType) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("更新主播猫眼石值开始:code={},message={},userId={},userOpal={}", code, message, userId, userOpal);
		// 根据id查询用户信息表
		Boolean flag = false;

		try {
			if (StringUtils.isNotBlank(userId) && 0L < userOpal) {
				flag = incomeService.updateOpal(userId, userOpal, changeType);
			} else {
				GwsLogger.error("userId不存在:userId={}", userId);
			}
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("更新主播猫眼石值异常:code={},message={},userId={},userOpal={},changeType={},e={}", code, message,
					userId, userOpal, changeType, e);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("更新主播猫眼石值异常:code={},message={},userId={},userOpal={},changeType={},e={}", code, message,
					userId, userOpal, changeType, e);
		}
		GwsLogger.info("更新主播猫眼石值结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "批量主播猫眼石值", notes = "批量主播猫眼石值")
	@ApiImplicitParam(name = "userOpalList", value = "主播猫眼石值", required = true, dataType = "String")
	@RequestMapping(value = "/updateOpalBatch", method = RequestMethod.POST)
	public RetResult updateOpalBatch(String userOpalList) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("批量更新主播猫眼石值开始:code={},message={},userOpalList={}", code, message, userOpalList);
		// 根据id查询用户信息表
		Boolean flag = false;
		List<Map<String, Object>> opalList = new ArrayList<Map<String, Object>>();
		
		try {
			opalList = JSON.parseObject(userOpalList, new TypeReference<List<Map<String,Object>>>(){});
			flag = incomeService.updateOpalBatch(opalList);
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("批量更新主播猫眼石值异常:code={},message={},opalList={},e={}", code, message, opalList, e);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("批量更新主播猫眼石值异常:code={},message={},opalList={},e={}", code, message, opalList, e);
		}
		GwsLogger.info("批量更新主播猫眼石值结束:code={},message={},flag={}", code, message, flag);
		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "更新主播卡路里", notes = "更新主播卡路里")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "calorie", value = "主播卡路里", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "changeType", value = "卡路里增减", required = true, dataType = "Integer")
	})
	@RequestMapping(value = "/updateCalorie", method = RequestMethod.POST)
	public RetResult updateCalorie(@RequestParam("userId") String userId, @RequestParam("calorie") Long calorie,
			@RequestParam("changeType") Integer changeType) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("更新主播卡路里开始:code={},message={},userId={},calorie={}", code, message, userId, calorie);
		// 根据id查询用户信息表
		Boolean flag = false;
		if (StringUtils.isBlank(userId) && 1L > calorie) {
			GwsLogger.info("userId不存在:userId={}, calorie={}", userId, calorie);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, flag);
		}

		try {
			flag = incomeService.updateCalorie(userId, calorie, changeType);
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("更新主播卡路里异常:code={},message={},userId={},calorie={},changeType={},e={}", code, message,
					userId, calorie, changeType, e);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("更新主播卡路里异常:code={},message={},userId={},calorie={},changeType={},e={}", code, message,
					userId, calorie, changeType, e);
		}
		GwsLogger.info("更新主播卡路里结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, flag);
	}

}
