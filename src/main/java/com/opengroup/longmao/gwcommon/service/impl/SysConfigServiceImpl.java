package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.entity.po.AppOnOff;
import com.opengroup.longmao.gwcommon.entity.po.SysConfig;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.SysConfigQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.AppOnOffRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.SysConfigRepositorySlave;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.SysConfigService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;

/**
 * 【系统配置信息】 Service接口实现
 *
 * @version 1.0
 * @author Yangst 2017年04月28日 上午10:52:17
 */ 
@Service
public class SysConfigServiceImpl implements SysConfigService{

	@Autowired
	private SysConfigRepositorySlave sysConfigRepositorySlave;
	
	
	@Autowired
	private AppOnOffRepositorySlave appOnOffRepositorySlave;

	@Autowired
	private RedisReadWriteService redis;
	
	/**
	  * @Title: sysConfig 
	  * @Description: 直播开关(Redis) 
	  * @param sysKey
	  * @return String
	  */
	@Override
	public String sysConfig(String sysKey) {
		//String sysSet = cache.hashGet(CommConstant.SYS_CONFIG + "All", sysKey);
		String sysSet = redis.hashGet(ConfigConstant.SYS_ALL_CONFIG, sysKey);
		if (StringUtils.isBlank(sysSet)) {
			SysConfig sys = getSysByName(sysKey);
			sysSet = (null == sys) ? "1" : sys.getVal();
			//cache.hashSet(CommConstant.SYS_CONFIG + "All", sysKey, sysSet);
			redis.hashSet(ConfigConstant.SYS_ALL_CONFIG, sysKey, sysSet);
		}
		return sysSet;
	}
	
	@Override
	public Map<String, String> getConfig(String sysKey) {
		if (!"ios".equals(sysKey) && !"android".equals(sysKey) && !"All".equals(sysKey)) {
			String key = sysKey.replace("h5", "h5_enter").replace("alipay", "zzz").replace("pay", "zf");
			String sysSet = redis.hashGet(ConfigConstant.SYS_ALL_CONFIG, key);
			if (StringUtils.isBlank(sysSet)) {
				SysConfig sys = getSysByName(sysKey);
				if (null != sys) {
					sysSet = sys.getVal();
					redis.hashSet(ConfigConstant.SYS_ALL_CONFIG, key, sys.getVal());
				} else {
					sysSet = String.valueOf(IsOrNotEnum.NO.getType());
				}
			}
			Map<String, String> osInfo = new HashMap<String, String>();
			osInfo.put(key, sysSet);
			return osInfo;
		} else {
			return findAllSys(sysKey);
		}
	}
	/**
	 * @Title: getSysByName 
	 * @Description: 根据Name查询系统配置 
	 * @param sysKey
	 * @return SysConfig
	 */
	public SysConfig getSysByName(String sysKey) {
		if (StringUtils.isNotBlank(sysKey)) {
			return sysConfigRepositorySlave.getSysByName(sysKey, IsDeleteEnum.UN_DELETE.getVal());
		}
		return null;
	}
	
	/**
	 * @Title: findSysConfig
	 * @Description: 【查询系统配置信息】
	 * @param sysKey
	 * @return SysConfig
	 */
	@Override
	public SysConfig findSysConfig(String sysKey) {
		// 组合查询语句
		SysConfigQueryFilter query = new SysConfigQueryFilter();
		query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		query.setName(sysKey);
		// 查询数据
		SysConfig sysL = sysConfigRepositorySlave.findOne(query);
		return sysL;
	}
	
	/**
	 * @Title: findSysConfig
	 * @Description: 【查询系统配置信息】
	 * @param sysKey
	 * @return List<SysConfig>
	 */
	@Override
	public List<SysConfig> findAllSysConfig(String sysKey) {
		if (StringUtils.isNotBlank(sysKey)) {
			List<SysConfig> sysL = sysConfigRepositorySlave.sysLikeName(sysKey, IsDeleteEnum.UN_DELETE.getVal());
			return sysL;
		}
		return null;
	}
	
	/**
	 * @Title: findAllSysConfig 
	 * @Description: 【查询系统配置信息】 
	 * @param sysKey
	 * @return Map<String, String>
	 */
	@Override
	public Map<String, String> findAllSys(String sysKey) {
		Map<String, String> osInfo = redis.hashGet(ConfigConstant.SYS_CONFIG + sysKey);
		List<SysConfig> sysL = new ArrayList<SysConfig>();
		if (osInfo.isEmpty()) {
			sysL = ("All".equals(sysKey)) 
					? sysConfigRepositorySlave.sysConfig(IsDeleteEnum.UN_DELETE.getVal())
					: sysConfigRepositorySlave.sysLikeName(sysKey, IsDeleteEnum.UN_DELETE.getVal());
			if (CollectionUtils.isNotEmpty(sysL)) {
				if ("All".equals(sysKey)) {
					for (SysConfig sys : sysL) {
						String key = sys.getName().replace("alipay", "zzz").replace("pay", "zf");
						osInfo.put(key, sys.getVal());
						redis.hashSet(ConfigConstant.SYS_CONFIG + sysKey, key, sys.getVal());
					}
				} else {
					for (SysConfig sys : sysL) {
						osInfo.put(sys.getName(), sys.getVal());
						redis.hashSet(ConfigConstant.SYS_CONFIG + sysKey, sys.getName(), sys.getVal());
					}
				}
			}
		}
		return osInfo; 
	}
	
	/**
	  * @Title: findAllAppOnOff 
	  * @Description: 【查询系统配置信息】 
	  * @param code
	  * @param sysKey
	  * @return Map<String, String>
	  */
	@Override
	public Map<String, String> findAllAppOnOff(Long code, String sysKey) {
		//Map<String, String> osInfo = cache.hashGet(CommConstant.SYS_CONFIG + code);
		Map<String, String> osInfo = redis.hashGet(ConfigConstant.SYS_CONFIG + code);
		if (osInfo.isEmpty()) {
			List<AppOnOff> appL = StringUtils.isNotBlank(sysKey)
					? appOnOffRepositorySlave.sysLikeName(code, sysKey, IsDeleteEnum.UN_DELETE.getVal())
					: appOnOffRepositorySlave.sysConfig(code, IsDeleteEnum.UN_DELETE.getVal());
			if (CollectionUtils.isNotEmpty(appL)) {
				for (AppOnOff app : appL) {
					String key = app.getName().replace("alipay", "zzz").replace("pay", "zf");
					osInfo.put(key, app.getVal());
					//cache.hashSet(CommConstant.SYS_CONFIG + code, key, app.getVal());
					redis.hashSet(ConfigConstant.SYS_CONFIG + code, key, app.getVal());
				}
				return osInfo;
			} else {
				sysKey = StringUtils.isNotBlank(sysKey) ? sysKey : "All";
				return findAllSys(sysKey);
			}
		}
		return osInfo;
	}
	
}