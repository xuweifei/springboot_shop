/**
 * @Title: ImConsumeController.java 
 * @Package com.opengroup.longmao.gwcommon.controller 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.service.ImConsumeService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;
import com.opengroup.longmao.gwcommon.tools.sensitive.WordFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: ImConsumeController 
 * @Description: TODO
 * @author Mr.Zhu
 */
@RestController
@EnableSwagger2
@Api(value = "IM消息消费Controller", tags = "ImConsume")
@RequestMapping(value = {"/imc"})
public class ImConsumeController {
	
	@Autowired
	private ImConsumeService imConsumeService;
	
	@Autowired
	private WordFilter wordFilter;
	
	@ApiOperation(value = "IM弹幕消息(扣币)")
	@RequestMapping(value = "/barrage", method = RequestMethod.POST)
	public RetResult barrage(HttpServletRequest request) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		String params = getParams(request);
		JSONObject json = JSON.parseObject(params);
		GwsLogger.info("IM群组弹幕消费(扣币)开始:JSONObject={}", json);
		Boolean flag = false;
		if (!json.containsKey("userId") || !json.containsKey("msgContent")) {
			GwsLogger.error("参数非法:JSONObject={}", json);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, flag);
		}
		String msgContent = json.getString("msgContent");
		//弹幕消息敏感词汇判断
		if (wordFilter.isContains(msgContent)) {
			GwsLogger.info("弹幕消息存在敏感词汇:JSONObject={}", json);
			return RetResult.setRetDate(CommConstant.GWSCOD0030, CommConstant.GWSMSG0030, flag);
		}
		
		try {
			flag = imConsumeService.barrageConsumeCoin(json);
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("IM群组弹幕消费(扣币)异常:code={},message={},JSONObject={}", code, message, json);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("IM群组弹幕消费(扣币)异常:code={},message={},JSONObject={},e={}", code, message, json, e);
		}
		GwsLogger.info("IM群组弹幕消费结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "IM礼物消息(扣币)")
	@RequestMapping(value = "/gift", method = RequestMethod.POST)
	public RetResult gift(HttpServletRequest request) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		String params = getParams(request);
		JSONObject json = JSON.parseObject(params);
		GwsLogger.info("IM群组礼物消费(扣币)开始:JSONObject={}", json);
		Boolean flag = false;
		if (!json.containsKey("userId") || !json.containsKey("giftNum") || !json.containsKey("giftCoin")) {
			GwsLogger.error("参数非法:JSONObject={}", json);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, flag);
		}
		
		try {
			flag = imConsumeService.giftConsumeCoin(json);
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("IM群组礼物消费(扣币)异常:code={},message={},JSONObject={}", code, message, json);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("IM群组礼物消费(扣币)异常:code={},message={},JSONObject={},e={}", code, message, json, e);
		}
		GwsLogger.info("IM群组礼物消费(扣币)结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "IM弹幕消息(扣豆)")
	@RequestMapping(value = "/barrageBean", method = RequestMethod.POST)
	public RetResult barrageBean(HttpServletRequest request) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		String params = getParams(request);
		JSONObject json = JSON.parseObject(params);
		GwsLogger.info("IM群组弹幕消费(扣豆)开始:JSONObject={}", json);
		Boolean flag = false;
		if (!json.containsKey("userId") || !json.containsKey("msgContent")) {
			GwsLogger.error("参数非法:JSONObject={}", json);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, flag);
		}
		String msgContent = json.getString("msgContent");
		//弹幕消息敏感词汇判断
		if (wordFilter.isContains(msgContent)) {
			GwsLogger.info("弹幕消息存在敏感词汇:JSONObject={}", json);
			return RetResult.setRetDate(CommConstant.GWSCOD0030, CommConstant.GWSMSG0030, flag);
		}
		
		try {
			flag = imConsumeService.barrageConsumeBean(json);
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("IM群组弹幕消费(扣豆)异常:code={},message={},JSONObject={}", code, message, json);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("IM群组弹幕消费(扣豆)异常:code={},message={},JSONObject={},e={}", code, message, json, e);
		}
		GwsLogger.info("IM群组弹幕消费(扣豆)结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, flag);
	}
	
	@ApiOperation(value = "IM礼物消息(扣豆)")
	@RequestMapping(value = "/giftBean", method = RequestMethod.POST)
	public RetResult newGift(HttpServletRequest request) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		String params = getParams(request);
		JSONObject json = JSON.parseObject(params);
		GwsLogger.info("IM群组礼物消费(扣豆)开始:JSONObject={}", json);
		Boolean flag = false;
		if (!json.containsKey("userId") || !json.containsKey("giftNum") || !json.containsKey("giftCoin")) {
			GwsLogger.error("参数非法:JSONObject={}", json);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, flag);
		}
		
		try {
			flag = imConsumeService.giftConsumeBean(json);
		} catch (ImplException e) {
			code = e.getCode();
			message = e.getMessage();
			GwsLogger.error("IM群组礼物消费(扣豆)异常:code={},message={},JSONObject={}", code, message, json);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("IM群组礼物消费(扣豆)异常:code={},message={},JSONObject={},e={}", code, message, json, e);
		}
		GwsLogger.info("IM群组礼物消费(扣豆)结束:code={},message={}", code, message);
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
}
