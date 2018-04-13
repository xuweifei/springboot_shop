package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.GoodNumber;

/**
 * 【靓号】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年05月09日 下午15:08:22
 */ 
public interface GoodNumberRepositorySlave extends BaseRepository<GoodNumber, Long> {
	
	@Query("SELECT g FROM GoodNumber g WHERE g.number = ?1")
	GoodNumber queryGoodNumberByNumber(Long number);

}

