package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.AuthInfo;

/**
 * 【第三方授权信息】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年07月20日 下午17:14:06
 */ 
public interface AuthInfoRepositorySlave extends BaseRepository<AuthInfo, Long> {
	
	@Query("SELECT a FROM AuthInfo a WHERE a.appId =?1")
	AuthInfo queryAuthByAppId(String appId);
}

