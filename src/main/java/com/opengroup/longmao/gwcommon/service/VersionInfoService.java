package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.VersionInfo;

/**
 * 【版本控制信息】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年06月14日 下午14:59:13
 */ 
public interface VersionInfoService {

	 /**
	 * 【查询版本控制信息】
	 * @param id
	 * @return VersionInfo
	 * @version 1.0
	 * @author Hermit 2017年06月14日 下午14:59:13
	 */ 
	 VersionInfo findVersionInfo(Long id);
	 
	 /**
	  * @Title: osInfo 
	  * @Description: 获取版本控制信息 
	  * @param os
	  * @param chlId
	  * @return Map<String,String>
	  */
	 Map<String, String> osInfo(String os, Long chlId);

}

