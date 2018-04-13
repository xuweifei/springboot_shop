package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.ActConfig;

/**
 * 【活动配置表】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年12月12日 下午14:41:20
 */ 
public interface ActConfigRepositorySlave extends BaseRepository<ActConfig, Long> {
	@Query("select ac from ActConfig ac where ac.activityType = ?1")
	ActConfig findActConfigByType(Short activityType);

}

