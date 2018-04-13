package com.opengroup.longmao.gwcommon.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.annotation.JsonParam;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.LmSmsMessage;
import com.opengroup.longmao.gwcommon.service.LmSmsMessageService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【短信记录表】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年04月27日 下午16:14:13
 */ 
@RestController
@EnableSwagger2
@Api(value= "短信记录表",tags="lmSmsMessage")
@RequestMapping(value = {"/lmSmsMessage"})
public class LmSmsMessageController {

	@Autowired
	private LmSmsMessageService lmSmsMessageService;

	/**
	* 【保存短信记录表】
	* @param lmSmsMessage
	* @param request
	* @return RetResult
	* @version 1.0
	* @author Hermit 2017年04月27日 下午16:14:13
	*/ 
	@ApiOperation(value = "保存短信记录表",notes="保存短信记录表")
	@ApiImplicitParams({
		@ApiImplicitParam(name ="lmSmsMessage",value ="详细实体lmSmsMessage", required = true, dataType ="LmSmsMessage"),
		@ApiImplicitParam(name ="request",value ="HttpServletRequest", required = true, dataType ="HttpServletRequest") })
	@RequestMapping(value = "/saveLmSmsMessage",method =RequestMethod.POST)
	public RetResult saveLmSmsMessage(@JsonParam LmSmsMessage lmSmsMessage,HttpServletRequest request){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("短信记录表保存开始:code={},message={},lmSmsMessage",code,message,ToStringBuilder.reflectionToString(lmSmsMessage));
		try {
			if(lmSmsMessage!=null){
			   lmSmsMessage = lmSmsMessageService.saveLmSmsMessage(lmSmsMessage);
			   GwsLogger.info("短信记录表保存成功:code={},message={},",code,message);
			}else{
			   code = CommConstant.GWSCOD0001;
			   message = CommConstant.GWSMSG0001;
			   GwsLogger.info("短信记录表保存失败:code={},message={},lmSmsMessage={}",code,message,ToStringBuilder.reflectionToString(lmSmsMessage));
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("短信记录表保存异常:lmSmsMessage={},e={}",ToStringBuilder.reflectionToString(lmSmsMessage),e);
		}
		GwsLogger.info("短信记录表保存结束:code={},message={}",code,message);
		return RetResult.setRetDate(code, message, lmSmsMessage);
	}

}

