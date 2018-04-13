package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditRule;

/**
 * 【[全民主播]信誉等级提成规则】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:06:58
 */ 
public interface AnchorCreditRuleRepositorySlave extends BaseRepository<AnchorCreditRule, Long> {

	/**
	 * 
	 * 【根据等级分数查询[全民主播]信誉等级提成规则】
	 * 
	 * @author Hermit 2017年3月31日
	 * @param levelVal
	 * @return
	 */
	@Query("select a from AnchorCreditRule a where a.minVal <= ?1 and ?2 <= a.maxVal and a.isDelete =?2")
	AnchorCreditRule findAnchorCreditRule(Short levelVal,Short isDelete);
}

