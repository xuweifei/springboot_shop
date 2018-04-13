package com.opengroup.longmao.gwcommon.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.VersionInfo;
import com.opengroup.longmao.gwcommon.repository.slave.VersionInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.SysConfigService;
import com.opengroup.longmao.gwcommon.service.VersionInfoService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
/**
 * 【版本控制信息】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年06月14日 下午14:59:13
 */ 
@Service
public class VersionInfoServiceImpl implements VersionInfoService{
	
	@Autowired
	private VersionInfoRepositorySlave versionInfoRepositorySlave;
	
	@Autowired
	private SysConfigService SysConfigService;

	@Autowired
	private RedisReadWriteService redis;

	/**
	* 【根据id查询版本控制信息】
	* @param id
	* @return versionInfo
	* @version 1.0
	* @author Hermit 2017年06月14日 下午14:59:13
	*/ 
	@Override
	public VersionInfo findVersionInfo(Long id){
	    VersionInfo versionInfo = null;
		if(StringUtils.isNotBlank(id.toString())){
	      versionInfo = versionInfoRepositorySlave.findOne(id);
		}else{
		  GwsLogger.info("id不存在");
		}
		return versionInfo;
	}
	
	/**
	 * @Title: osInfo
	 * @Description: 获取版本控制信息
	 * @param os
	 * @param chlId
	 * @return Map<String,String>
	 */
	@Override
	public Map<String, String> osInfo(String os, Long chlId) {
		//Map<String, String> osInfo = cache.hashGet(CommConstant.SYS_CONFIG + chlId);
		Map<String, String> osInfo = redis.hashGet(ConfigConstant.SYS_CONFIG + chlId);
		if (osInfo.isEmpty()) {
			VersionInfo v = versionInfoRepositorySlave.findOne(chlId);
			if (null != v) {
				osInfo.put(os + "_code", v.getCode());
				osInfo.put(os + "_version", v.getVer());
				osInfo.put(os + "_update", v.getIsUpdate());
				osInfo.put(os + "_url", v.getDownUrl());
				osInfo.put(os + "_explain", v.getVerExplain());
				//cache.hashSet(CommConstant.SYS_CONFIG + chlId, os + "_code", v.getCode());
				//cache.hashSet(CommConstant.SYS_CONFIG + chlId, os + "_version", v.getVer());
				//cache.hashSet(CommConstant.SYS_CONFIG + chlId, os + "_update", v.getIsUpdate());
				//cache.hashSet(CommConstant.SYS_CONFIG + chlId, os + "_url", v.getDownUrl());
				//cache.hashSet(CommConstant.SYS_CONFIG + chlId, os + "_explain", v.getVerExplain());
				redis.hashSet(ConfigConstant.SYS_CONFIG + chlId, os + "_code", v.getCode());
				redis.hashSet(ConfigConstant.SYS_CONFIG + chlId, os + "_version", v.getVer());
				redis.hashSet(ConfigConstant.SYS_CONFIG + chlId, os + "_update", v.getIsUpdate());
				redis.hashSet(ConfigConstant.SYS_CONFIG + chlId, os + "_url", v.getDownUrl());
				redis.hashSet(ConfigConstant.SYS_CONFIG + chlId, os + "_explain", v.getVerExplain());
				return osInfo;
			} else {
				os = "All";
				return SysConfigService.findAllSys(os);
			}
		}
		return osInfo;
	}
	
}

