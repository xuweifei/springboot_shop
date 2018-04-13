package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import com.opengroup.longmao.gwcommon.entity.po.AnchorPlusRule;

/**
 * 【主播加分规则】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:04:19
 */ 
public interface AnchorPlusRuleService {

	 /**
	 * 【查询主播加分规则】
	 * @param id
	 * @return AnchorPlusRule
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:04:19
	 */ 
	 AnchorPlusRule findAnchorPlusRule(Long id);


	 /**
	 * 【查询主播加分规则】
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:04:19
	 */ 
	 List<AnchorPlusRule> findAnchorPlusRule();


}

