package com.opengroup.longmao.gwcommon.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.LmSmsMessage;
import com.opengroup.longmao.gwcommon.entity.po.SmsConfig;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.repository.master.LmSmsMessageRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.SmsConfigRepositorySlave;
import com.opengroup.longmao.gwcommon.service.LmSmsMessageService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.sms.OkhttpSendSMS;
import com.opengroup.longmao.gwcommon.tools.sms.RegrexCheckUtil;

/**
 * 【短信记录表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年04月27日 下午16:14:13
 */ 
@Service
public class LmSmsMessageServiceImpl implements LmSmsMessageService{

	@Autowired
	private LmSmsMessageRepositoryMaster lmSmsMessageRepositoryMaster;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private OkhttpSendSMS okhttpSendSMS;

	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private SmsConfigRepositorySlave smsConfigRepositorySlave;
	
	/**
	* 【保存短信记录表】
	* @param lmSmsMessage
	* @return lmSmsMessage
	* @version 1.0
	* @author Hermit 2017年04月27日 下午16:14:13
	*/ 
	@Override
	public LmSmsMessage saveLmSmsMessage(LmSmsMessage lmSmsMessage){
		//判断对象是否存在
		if(lmSmsMessage!= null){
		   lmSmsMessage.setCtime(DateUtil.currentSecond());
		   lmSmsMessage.setUtime(DateUtil.currentSecond());
		   lmSmsMessage.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		   lmSmsMessage = lmSmsMessageRepositoryMaster.save(lmSmsMessage);
		   GwsLogger.info("短信记录表保存成功");
		}else{
			GwsLogger.info("短信记录表对象不存在，保存失败:lmSmsMessage={}",ToStringBuilder.reflectionToString(lmSmsMessage));
		    return null;
		}
		return lmSmsMessage;
	}

	@Override
	public LmSmsMessage sendLmSmsMessage(String content, Long userId, String mobile, String type) throws Exception {
		if(StringUtils.isEmpty(mobile)){
			GwsLogger.error("mobile不存在：mobile={}", mobile);
			throw new Exception("mobile不存在:"+mobile);
		}
		
		if(!RegrexCheckUtil.regex(mobile)){
			GwsLogger.error("mobile不存在：mobile={}", mobile);
			throw new Exception("mobile不存在:"+mobile);
		}
		//id统一生成
		Long id = idGlobalGenerator.getSeqId(LmSmsMessage.class);
		okhttpSendSMS.sendThroughZhiCallHttp(content, mobile, type, id);
		
		LmSmsMessage lsm = new LmSmsMessage();
		lsm.setMobile(mobile);
		lsm.setContent(content);
		lsm.setSmsId(id);
		lsm.setUserId(userId);
		lsm = saveLmSmsMessage(lsm);
		lsm.setContent("成功");
		return lsm;
	}

	@Override
	public Map<String, String> getSmsConfig(String smsMark) {
		
		String content = "";
		Short smsStatus = 2; //短信启用状态(1：启用，2：停用)
		
		if(smsMark != null){

			GwsLogger.info("短信标识为：smsMark={}", smsMark);
			Object object = redis.hashGet(ConfigConstant.LM_SMS_CONFIG, smsMark);
			if (null == object) {
				
				SmsConfig sc = smsConfigRepositorySlave.findSmsConfigBySmsMark(smsMark);
				content = sc.getSmsTemplate();
				smsStatus = sc.getSmsStatus(); 
				
				//将数据保存到redis中
				redis.hashSet(ConfigConstant.LM_SMS_CONFIG, sc.getSmsMark(), JSON.toJSONString(sc));
				GwsLogger.info("从数据库中获取发送短信通知数据：content={},smsStatus={}", content,smsStatus);
			
			} else {
				
				JSONObject smsConfigObj = JSON.parseObject(redis.hashGet(ConfigConstant.LM_SMS_CONFIG,smsMark).toString());
				content = smsConfigObj.getString("smsTemplate");
				smsStatus = smsConfigObj.getShort("smsStatus");
				GwsLogger.info("从redis中获取发送短信通知数据：content={},smsStatus={}", content,smsStatus);
			}
			
		}else{
			GwsLogger.error("短信标识为空:smsMark={}", smsMark);
		}
		
		Map<String,String> smsConfigMap = new HashMap<>();
		smsConfigMap.put("content", content);
		smsConfigMap.put("smsStatus", String.valueOf(smsStatus));
		return smsConfigMap;
	}
	
}

