package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.UserLevel;

/**
 * 【用户等级】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年03月16日 上午10:58:27
 */ 
public interface UserLevelRepositorySlave extends BaseRepository<UserLevel, Long> {

	@Query("select u from UserLevel u where u.startExp <= ?1 and u.endExp>= ?1 order by u.gmtCreate desc")
	List<UserLevel> queryByCurrentExp1(Integer currentExp);
	
	@Query("select u from UserLevel u where u.startExp = ?1 order by u.gmtCreate desc")
	List<UserLevel> queryByCurrentExp2(Integer currentExp);
}

