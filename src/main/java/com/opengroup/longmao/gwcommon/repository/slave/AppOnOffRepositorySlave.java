package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.AppOnOff;

/**
 * 【APP系统开关】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年06月26日 下午14:07:30
 */ 
public interface AppOnOffRepositorySlave extends BaseRepository<AppOnOff, Long> {

	@Query("SELECT a FROM AppOnOff a WHERE a.code = ?1 AND a.name Like ?2% AND a.isDelete = ?3")
	List<AppOnOff> sysLikeName(Long code, String name, Short isDelete);
	
	@Query("SELECT a FROM AppOnOff a WHERE a.code = ?1 AND a.isDelete = ?2")
	List<AppOnOff> sysConfig(Long code, Short isDelete);
}

