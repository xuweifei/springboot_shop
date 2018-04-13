package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.HelpType;

/**
 * 【帮助中心问答类型】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2018年03月14日 下午16:48:14
 */ 
public interface HelpTypeRepositorySlave extends BaseRepository<HelpType, Long> {
	
	@Query("SELECT COUNT(h.typeId) FROM HelpType h")
	Integer getHelpTypeSize();

}

