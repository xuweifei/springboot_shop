package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.UserGradeRule;

/**
 * 【用户等级规则】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年04月17日 上午11:44:29
 */ 
public interface UserGradeRuleRepositorySlave extends BaseRepository<UserGradeRule, Long> {

	@Query("SELECT g FROM UserGradeRule g WHERE g.minVal <= ?1 AND g.maxVal > ?1 AND g.isDelete = ?2 ")
	UserGradeRule findUserGradeRule(Long levelVal, Short isDelete);
	
	@Query("SELECT g FROM UserGradeRule g WHERE (g.minVal <= ?1 AND g.maxVal > ?1 AND g.isDelete = ?2) OR g.isMax = 1 ORDER BY grade ASC")
	List<UserGradeRule> findUserGradeRuleList(Long levelVal, Short isDelete);
}

