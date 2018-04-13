package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.ApiConfig;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.service.DuibaService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConfirmParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConsumeParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConsumeResult;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditNotifyParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditTool;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditVirtualParams;
import com.opengroup.longmao.gwcommon.tools.sdk.topup.HuYiNotifyParams;
import com.opengroup.longmao.gwcommon.tools.sdk.topup.TopUpByHuYi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hermit
 *
 */
@RestController
@EnableSwagger2
@Api(value = "兑吧", tags = "duiba")
@RequestMapping(value = {"/duiba"})
public class DuibaController {
	
	@Autowired
	private DuibaService duibaService;
	
	@Autowired
	private TopUpByHuYi topUpByHuYi;
	
	@Autowired
	private ApiConfig config;

	@ApiOperation(value = "兑吧免登录URL接口")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "userId", value = "用户ID", required = false, dataType = "String"),
		@ApiImplicitParam(name = "dbredirect", value = "直达URL", required = false, dataType = "String")
		})
	@RequestMapping(value = "/autologin", method = RequestMethod.GET)
	public RetResult autoLogin(HttpServletRequest request, String userId, String dbredirect) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("生成兑吧免登录url开始:startTime={}", startTime);
		String url = null;
		try {
			Map<String, String> params = new HashMap<String, String>();

			if (StringUtils.isNotEmpty(userId) && !StringUtils.equals("null", userId)) {
				params.put("uid", userId);
				BeanAccount userScore = duibaService.getBeanByUserId(userId); //获取用户龙猫豆
				params.put("credits", userScore != null ? "" + userScore.getLmBeanNum().intValue() : "0");
			} else {
				params.put("uid", "not_login");  //使用游客身份登录
				params.put("credits", "0");
			}
			
			if (StringUtils.isNotEmpty(dbredirect) && !StringUtils.equals("null", dbredirect)) {
				params.put("redirect", dbredirect);
			}
			CreditTool tool = new CreditTool(config.getAppKey().trim(), config.getAppSecret().trim());
			url = tool.buildUrlWithSign(config.getDuiBaHttps().trim(), params);
			GwsLogger.info("生成兑吧免登录url成功:code={},message={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, (System.currentTimeMillis() - startTime));
			return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, url);
		} catch (Exception e) {
			GwsLogger.error("生成兑吧免登录url失败:code={},message={},userId={},dbredirect={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, dbredirect,
					(System.currentTimeMillis() - startTime), e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, url);
		}
	}
	
	@ApiOperation(value = "兑吧直达页面免登录url接口")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "userId", value = "用户ID", required = false, dataType = "String"),
		@ApiImplicitParam(name = "dbredirect", value = "直达URL", required = false, dataType = "String")
		})
	@RequestMapping(value = "/through", method = RequestMethod.GET)
	public void through(HttpServletRequest request, HttpServletResponse response, String userId, String dbredirect) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("生成兑吧直达页面免登录url开始:startTime={}", startTime);
		String url = null;
		try {
			Map<String, String> params = new HashMap<String, String>();

			if (StringUtils.isNotEmpty(userId) && !StringUtils.equals("null", userId)) {
				params.put("uid", userId);
				BeanAccount userScore = duibaService.getBeanByUserId(userId); //获取用户龙猫豆
				params.put("credits", userScore != null ? "" + userScore.getLmBeanNum().intValue() : "0");
			} else {
				params.put("uid", "not_login");  //使用游客身份登录
				params.put("credits", "0");
			}
			
			if (StringUtils.isNotEmpty(dbredirect) && !StringUtils.equals("null", dbredirect)) {
				params.put("redirect", dbredirect);
			}
			CreditTool tool = new CreditTool(config.getAppKey().trim(), config.getAppSecret().trim());
			url = tool.buildUrlWithSign(config.getDuiBaHttps().trim(), params);
			GwsLogger.info("生成兑吧直达页面免登录url成功:code={},message={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, (System.currentTimeMillis() - startTime));
			response.sendRedirect(url);
		} catch (Exception e) {
			GwsLogger.error("生成兑吧直达页面免登录url失败:code={},message={},userId={},dbredirect={},endTime={}毫秒,e={}",
					CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, userId, dbredirect,
					(System.currentTimeMillis() - startTime), e.getMessage());
		}
	}
	
	@ApiOperation(value = "生成兑吧兑换订单接口-扣龙猫豆")
	@RequestMapping(value = "/deductCredits", method = RequestMethod.GET)
	public Object deductCredits(HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("生成兑吧兑换订单-扣龙猫豆开始:startTime={}", startTime);
		CreditConsumeParams params = null;
		
		try {
			CreditTool tool = new CreditTool(config.getAppKey().trim(), config.getAppSecret().trim());
			// 利用tool来解析这个请求
			params = tool.parseCreditConsume(request);
			String itemCode = request.getParameter("itemCode"); // 商品编码 (自有商品)
			CreditConsumeResult result = duibaService.saveOrderInfo(params, itemCode);
			JSONObject json = JSONObject.parseObject(result.toString());
			String str = "fail".equals(json.getString("status")) ? "失败" : "成功";
			GwsLogger.info("生成兑吧兑换订单-扣龙猫豆" + str + ":code={},message={},params={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime));
			return JSONObject.parse(result.toString());
		} catch (Exception e) {
			GwsLogger.error("生成兑吧兑换订单-扣龙猫豆异常:code={},message={},params={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime), e.getMessage());
			CreditConsumeResult result = new CreditConsumeResult(false);
			result.setErrorMessage("创建兑吧订单产生Exception");
			return JSONObject.parse(result.toString());
		}
	}
	
	@ApiOperation(value = "生成兑吧兑换订单接口-加龙猫豆")
	@RequestMapping(value = "/plusCredits", method = RequestMethod.GET)
	public Object plusCredits(HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("生成兑吧兑换订单-加龙猫豆开始:startTime={}", startTime);
		CreditConsumeParams params = null;
		try {
			CreditTool tool = new CreditTool(config.getAppKey().trim(), config.getAppSecret().trim());
			// 利用tool来解析这个请求
			params = tool.parseCreditConsume(request);
			CreditConsumeResult result = duibaService.saveOrderInfo(params);
			JSONObject json = JSONObject.parseObject(result.toString());
			String str = "fail".equals(json.getString("status")) ? "失败" : "成功";
			GwsLogger.info("生成兑吧兑换订单-加龙猫豆成功" + str + ":code={},message={},params={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime));
			return JSONObject.parse(result.toString());
		} catch (Exception e) {
			GwsLogger.error("生成兑吧兑换订单-加龙猫豆异常:code={},message={},params={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime), e.getMessage());
			CreditConsumeResult result = new CreditConsumeResult(false);
			result.setErrorMessage("加积分产生Exception");
			return JSONObject.parse(result.toString());
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "兑吧兑换结果通知接口")
	@RequestMapping(value = "/notifyResult", method = RequestMethod.GET)
	public String notifyResult(HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("兑吧兑换通知开始:startTime={}", startTime);
		CreditNotifyParams params = null;
		try {
			CreditTool tool = new CreditTool(config.getAppKey().trim(), config.getAppSecret().trim());
			// 利用tool来解析这个请求
			params = tool.parseCreditNotify(request);
			duibaService.updateOrderState(params);
			GwsLogger.info("兑吧兑换通知结束:code={},message={},params={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("兑吧兑换通知异常:code={},message={},params={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime), e.getMessage());
		}
		return "ok";
	}

	@ApiOperation(value = "兑吧虚拟商品充值接口")
	@RequestMapping(value = "/virtual", method = RequestMethod.GET)
	public Object virtualTopUp(HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("兑吧虚拟充值通知开始:startTime={}", startTime);
		CreditNotifyParams params = null;
		try {
			CreditTool tool = new CreditTool(config.getAppKey().trim(), config.getAppSecret().trim());
			// 利用tool来解析这个请求
			params = tool.parseVirtualNotify(request);
			CreditVirtualParams result = duibaService.phoneFeeTopUpInDuiBa(params);
			GwsLogger.info("兑吧虚拟充值通知完成:code={},message={},params={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime));
			return JSONObject.parse(result.toString());
		} catch (Exception e) {
			GwsLogger.error("兑吧虚拟充值通知异常:code={},message={},params={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, JSONObject.toJSONString(params), (System.currentTimeMillis() - startTime), e.getMessage());
			CreditVirtualParams result = new CreditVirtualParams((short)2);
			result.setErrorMessage("虚拟充值通知产生Exception");
			return JSONObject.parse(result.toString());
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "充值结果推送接口")
	@RequestMapping(value = "/resultPush", method = RequestMethod.POST)
	public String resultPush(HttpServletRequest request) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("充值结果推送通知开始:startTime={}", startTime);
		HuYiNotifyParams notify = null;
		try {
			notify = topUpByHuYi.parsePushNotify(request);
			
			CreditConfirmParams params = duibaService.pushNotify(notify);
			CreditTool tool = new CreditTool(config.getAppKey().trim(), config.getAppSecret().trim());
			String signParams = tool.buildVirtualConfirmRequest(params);
			
			duibaService.duiBaNotify(config.getDuiBaInformHttp().trim(), signParams);
			GwsLogger.info("充值结果推送通知完成:code={},message={},notify={},endTime={}毫秒", CommConstant.GWSCOD0000,
					CommConstant.GWSMSG0000, JSONObject.toJSONString(notify), (System.currentTimeMillis() - startTime));
			return "success";
		} catch (Exception e) {
			GwsLogger.error("充值结果推送通知异常:code={},message={},notify={},endTime={}毫秒,e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, JSONObject.toJSONString(notify), (System.currentTimeMillis() - startTime), e.getMessage());
			return "error";
		}
	}
}
