package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.SuperAdministrator;

/**
 * 【App超级管理表】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年11月27日 上午10:42:52
 */ 
public interface SuperAdministratorRepositorySlave extends BaseRepository<SuperAdministrator, Long> {
	
	@Query("SELECT s FROM SuperAdministrator s WHERE s.userId = ?1 AND s.isDelete = ?2")
	SuperAdministrator findSuperAdministrator(Long userId, Short isDelete);
}

