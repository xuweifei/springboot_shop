package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.SysConfig;

/**
 * 【系统配置信息】 RepositorySlave接口
 *
 * @version 1.0
 * @author Yangst 2017年04月28日 上午10:52:17
 */ 
public interface SysConfigRepositorySlave extends BaseRepository<SysConfig, Long> {
	
	@Query("SELECT s FROM SysConfig s WHERE s.name = ?1 AND s.isDelete = ?2")
	SysConfig getSysByName(String name, Short isDelete);

	@Query("SELECT s FROM SysConfig s WHERE s.name Like ?1% AND s.isDelete = ?2")
	List<SysConfig> sysLikeName(String name, Short isDelete);
	
	@Query("SELECT s FROM SysConfig s WHERE s.isDelete = ?1")
	List<SysConfig> sysConfig(Short isDelete);
}

