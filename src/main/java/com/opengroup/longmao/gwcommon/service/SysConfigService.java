package com.opengroup.longmao.gwcommon.service;

import java.util.List;
import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.SysConfig;

/**
 * 【系统配置信息】 service接口
 *
 * @version 1.0
 * @author Yangst 2017年04月28日 上午10:52:17
 */ 
public interface SysConfigService {

	/**
	  * @Title: sysConfig 
	  * @Description: 直播开关(Redis) 
	  * @param sysKey
	  * @return String
	  */
	 String sysConfig(String sysKey);
	 
	 Map<String, String> getConfig(String sysKey);

	 /**
	  * @Title: findSysConfig 
	  * @Description: 【查询系统配置信息】 
	  * @param sysKey
	  * @return SysConfig
	  */
	 SysConfig findSysConfig(String sysKey);
	 
	 /**
	  * @Title: findSysConfig 
	  * @Description: 【查询系统配置信息】 
	  * @param sysKey
	  * @return List<SysConfig>
	  */
	 List<SysConfig> findAllSysConfig(String sysKey);
	 
	/**
	 * @Title: findAllSys
	 * @Description: 【查询系统配置信息】
	 * @param sysKey
	 * @return Map<String, String>
	 */
	Map<String, String> findAllSys(String sysKey);
	 
	 /**
	  * @Title: findAllAppOnOff 
	  * @Description: 【查询系统配置信息】 
	  * @param code
	  * @param sysKey
	  * @return Map<String, String>
	  */
	 Map<String, String> findAllAppOnOff(Long code, String sysKey);

}

