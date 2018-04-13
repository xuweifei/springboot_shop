package com.opengroup.longmao.gwcommon.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.CoinAccount;
import com.opengroup.longmao.gwcommon.service.CoinAccountService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【龙猫币账户管理】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:51:27
 */
@EnableSwagger2
@Api(value = "龙猫币账户管理", tags = "coinAccount")
@RestController
@RequestMapping(value = { "/coinAccount" })
public class CoinAccountController {
	
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private SysConfigService SysConfigService;

	@Autowired
	private CoinAccountService coinAccountService;
	
	/**
	 * 【根据userId查询龙猫币账户】
	 * @param userId
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 下午15:51:27
	 */
	@ApiOperation(value = "根据userId查询龙猫币账户", notes = "根据userId查询龙猫币账户")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String")
	@RequestMapping(value = "/findCoin", method = RequestMethod.GET)
	public RetResult findCoin(@RequestParam("userId") String userId) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据userId查询龙猫币开始:code={},message={},userId={}", code, message, userId);
		//根据userId查询龙猫币
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CoinAccount coin = coinAccountService.getCoinByUserId(userId);
			if (null != coin) {
				map.put("userId", coin.getUserId());
				map.put("lmCoinNum", coin.getLmCoinNum());
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("根据userId查询龙猫币异常:code={},message={},userId={},e={}", code, message, userId, e);
		}
		GwsLogger.info("根据userId查询龙猫币结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, map);
	}

	/**
	 * 【根据人民币增加龙猫币】
	 * @param beanAccount
	 * @param request
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 下午15:51:27
	 */
	@ApiOperation(value = "根据人民币增加龙猫币", notes = "根据人民币增加龙猫币")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "rmb", value = "人民币", required = true, dataType = "BigDecimal") })
	@RequestMapping(value = "/addCoin", method = RequestMethod.POST)
	public RetResult addCoin(@RequestParam("userId") String userId, @RequestParam("rmb") BigDecimal rmb) {
		GwsLogger.info("龙猫币充值:userId={}", userId);
		return RetResult.setRetDate(CommConstant.GWSCOD0028, "龙猫币充值已停止使用,请更换版本。", false);
//		if (StringUtils.isBlank(userId)) {
//			GwsLogger.error("userId不存在:userId={}", userId);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
//		}
//		int r = rmb.compareTo(BigDecimal.ZERO);
//		if (r != 1) {
//			GwsLogger.error("rmb充值金额无效:rmb={}", rmb);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
//		}
//		GwsLogger.info("根据人民币增加龙猫币开始:userId={},rmb={}", userId, rmb);
//		boolean result = false;
//		try {
//			result = coinAccountService.recharge(userId, rmb);
//			if (result) {
//				GwsLogger.info("根据人民币增加龙猫币操作成功");
//			} else {
//				GwsLogger.error("根据人民币增加龙猫币操作失败功:userId={},rmb={}", userId, rmb);
//			}
//		} catch (Exception e) {
//			GwsLogger.error("根据人民币增加龙猫币操作异常:code={},message={},userId={},e={}", CommConstant.GWSCOD0001,
//					CommConstant.GWSMSG0001, userId, e);
//		}
//		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, result);
	}
	
	
	/**
	 * 【根据人民币增加龙猫币无需考虑活动】
	 * @param beanAccount
	 * @param request
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 下午15:51:27
	 */
	@ApiOperation(value = "根据人民币增加龙猫币", notes = "根据人民币增加龙猫币")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "rmb", value = "人民币", required = true, dataType = "BigDecimal") })
	@RequestMapping(value = "/rechargeByUserId", method = RequestMethod.POST)
	public RetResult rechargeByUserId(@RequestParam("userId") String userId, @RequestParam("rmb") BigDecimal rmb) {
		GwsLogger.info("支付宝娱乐龙猫币充值:userId={}", userId);
		return RetResult.setRetDate(CommConstant.GWSCOD0028, "龙猫币充值已停止使用,请更换版本。", false);
//		if (StringUtils.isBlank(userId)) {
//			GwsLogger.error("userId不存在:userId={}", userId);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
//		}
//		int r = rmb.compareTo(BigDecimal.ZERO);
//		if (r != 1) {
//			GwsLogger.error("rmb充值金额无效:rmb={}", rmb);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
//		}
//		GwsLogger.info("根据人民币增加龙猫币开始:userId={},rmb={}", userId, rmb);
//		boolean result = false;
//		try {
//			result = coinAccountService.rechargeByUserId(userId, rmb);
//			if (result) {
//				GwsLogger.info("根据人民币增加龙猫币操作成功");
//			} else {
//				GwsLogger.error("根据人民币增加龙猫币操作失败功:userId={},rmb={}", userId, rmb);
//			}
//		} catch (Exception e) {
//			GwsLogger.error("根据人民币增加龙猫币操作异常:code={},message={},userId={},e={}", CommConstant.GWSCOD0001,
//					CommConstant.GWSMSG0001, userId, e);
//		}
//		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, result);
	}

	@ApiOperation(value = "龙猫币兑换龙猫豆", notes = "用户龙猫币兑换龙猫豆")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "lmCoinNum", value = "龙猫币数量", required = true, dataType = "Integer") })
	@RequestMapping(value = "/coinToBean", method = RequestMethod.POST)
	public RetResult coinToBean(@RequestParam("userId") String userId, @RequestParam("lmCoinNum") Integer lmCoinNum) {
		GwsLogger.info("龙猫币兑换龙猫豆:userId={}", userId);
		Map<String, Object> coinM = new HashMap<String, Object>();
		return RetResult.setRetDate(CommConstant.GWSCOD0028, "兑换已停止使用,请更换版本。", coinM);
//		String code = CommConstant.GWSCOD0000;
//		String message = CommConstant.GWSMSG0000;
//		GwsLogger.info("龙猫币兑换龙猫豆开始:code={},message={},userId={},lmCoinNum={}", code, message, userId, lmCoinNum);
//		Map<String, Object> coinM = new HashMap<String, Object>();
//		
//		if (StringUtils.isBlank(userId)) {
//			GwsLogger.error("userId不存在:userId={}", userId);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, coinM);
//		}
//		
//		if (1 > lmCoinNum) {
//			GwsLogger.error("龙猫币数量有误:lmBeanNum={}", lmCoinNum);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, "龙猫币数量有误", coinM);
//		}
//		
//		User u = userService.queryUserByUserId(userId);
//		if (null == u) {
//			return RetResult.setRetDate(CommConstant.GWSCOD0010, CommConstant.GWSMSG0010, coinM);
//		} else {
//			SysConfig config = SysConfigService.findSysConfig("coin_" + u.getUserType() + "_");
//			if (null != config && config.getVal().equals(IsOrNotEnum.NO.getType().toString())) {
//				GwsLogger.info("龙猫币兑换龙猫豆功能关闭：userId={}", userId);
//				return RetResult.setRetDate(CommConstant.GWSCOD0024, CommConstant.GWSMSG0024, coinM);
//			}
//		}
//		
//		try {
//			//减少龙猫币增加龙猫豆,增加相应币/豆流水
//			coinM = coinAccountService.coinToBean(userId, lmCoinNum);
//		} catch (ImplException e) {
//			code = e.getCode();
//			message = e.getMessage();
//			GwsLogger.error("龙猫币兑换龙猫豆异常:code={},message={},userId={},lmCoinNum={},e={}", code, message, userId,
//					lmCoinNum, e);
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("龙猫币兑换龙猫豆异常:code={},message={},userId={},lmCoinNum={},e={}", code, message, userId,
//					lmCoinNum, e);
//		}
//		GwsLogger.info("龙猫币兑换龙猫豆结束:code={},message={}", code, message);
//		return RetResult.setRetDate(code, message, coinM);
	}

	@ApiOperation(value = "龙猫币转龙猫豆", notes = "龙猫币转龙猫豆")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户userId", required = false, dataType = "String"),
		@ApiImplicitParam(name = "lmCoinNum", value = "龙猫币数量", required = false, dataType = "Integer") })
	@RequestMapping(value = "/allUserCoinExchBean", method = RequestMethod.POST)
	public RetResult allUserCoinExchBean(String userId, Integer lmCoinNum) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("全体用户龙猫币转换龙猫豆开始");
		Map<String, Object> coinM = new HashMap<String, Object>();
		if (StringUtils.isBlank(userId) || !"777777".equals(userId)) {
			GwsLogger.error("userId不存在:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, coinM);
		}
		if (null == lmCoinNum || !Integer.valueOf("20171016").equals(lmCoinNum)) {
			GwsLogger.error("龙猫币数量有误:lmBeanNum={}", lmCoinNum);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, "龙猫币数量有误", coinM);
		}
		
		try {
			//减少龙猫币增加龙猫豆,增加相应币/豆流水
			coinM = coinAccountService.coinExchBean();
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("全体用户龙猫币转换龙猫豆异常:code={},message={},e={}", code, message, e.getMessage());
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("全体用户龙猫币转换龙猫豆异常:code={},message={},e={}", code, message, e.getMessage());
		}
		GwsLogger.info("全体用户龙猫币转换龙猫豆结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, coinM);
	}
}
