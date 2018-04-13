package com.opengroup.longmao.gwcommon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.ActConfig;
import com.opengroup.longmao.gwcommon.repository.master.ActConfigRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.ActConfigQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.ActConfigRepositorySlave;
import com.opengroup.longmao.gwcommon.service.ActConfigService;

/**
 * 【活动配置表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年12月12日 下午14:41:20
 */ 
@Service
public class ActConfigServiceImpl implements ActConfigService{

	@Autowired
	private ActConfigRepositorySlave actConfigRepositorySlave;
	
	/**
	 * 【查询活动配置表】
	 */
	@Override
	public ActConfig findActConfigByType(Short activityType) {
		return actConfigRepositorySlave.findActConfigByType(activityType);
	}

}


