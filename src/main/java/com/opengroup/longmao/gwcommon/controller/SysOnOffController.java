package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.service.SysConfigService;
import com.opengroup.longmao.gwcommon.service.VersionInfoService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【系统开关】 控制类
 * @version 1.0
 * @author Yangst 2017年04月25日 上午11:12:07
 */
@RestController
@EnableSwagger2
@Api(value = "系统参数开关", tags = "sys")
@RequestMapping(value = { "/sys" })
public class SysOnOffController {
	
	@Autowired
	private SysConfigService SysConfigService;
	
	@Autowired
	private VersionInfoService versionInfoService;

	@ApiOperation(value = "版本比较",notes="版本比较,小于版本则更新提示")
	@ApiImplicitParams({ @ApiImplicitParam(name ="os",value ="版本比较的操作系统", required = false, dataType ="String"),
			@ApiImplicitParam(name = "chlId", value = "渠道ID", required = false, dataType = "String")
	})
	@RequestMapping(value = "/getOsInfo",method =RequestMethod.GET)
	public RetResult getOsInfo(String os, String chlId){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("获取操作系统信息开始:code={},message={},os={}", code, message, os);
		Map<String, String> osInfo = new HashMap<String, String>();

		try {
			if (StringUtils.isBlank(chlId)) {
				os = (StringUtils.isNotBlank(os)) ? os : "All";
				osInfo = SysConfigService.findAllSys(os);
			} else {
				osInfo = versionInfoService.osInfo(os, Long.valueOf(chlId));
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("获取操作系统信息异常:code={},message={},e={}", code, message, e);
		}
		GwsLogger.info("获取操作系统信息结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, osInfo);
	}
	
	@ApiOperation(value = "系统开关",notes="获取系统参数开关")
	@ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "系统参数Key", required = false, dataType = "String"),
			@ApiImplicitParam(name = "osCode", value = "系统CODE", required = false, dataType = "String") 
	})
	@RequestMapping(value = "/isOnOff",method =RequestMethod.GET)
	public RetResult isOnOff(String key, String osCode){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("获取系统参数开关开始:code={},message={},os={}", code, message, key);
		Map<String, String> osInfo = new HashMap<String, String>();

		try {
			if (StringUtils.isBlank(osCode)) {
				key = (StringUtils.isNotBlank(key)) ? key : "All";
				osInfo = SysConfigService.getConfig(key);
			} else {
				osInfo = SysConfigService.findAllAppOnOff(Long.valueOf(osCode), key);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("获取系统参数开关异常:code={},message={},e={}", code, message, e);
		}
		GwsLogger.info("获取系统参数开关结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, osInfo);
	}
}
