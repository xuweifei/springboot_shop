package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.LoginRecord;

/**
 * 【用户登录记录】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年04月24日 下午18:19:03
 */ 
public interface LoginRecordRepositorySlave extends BaseRepository<LoginRecord, Long> {
	
	@Query("select l from LoginRecord l where l.userId = ?1 and l.isFirst = 1")
	LoginRecord getFirstRecord(String userId);
	
}

