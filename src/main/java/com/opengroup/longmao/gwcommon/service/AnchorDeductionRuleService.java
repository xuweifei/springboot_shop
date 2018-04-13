package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import com.opengroup.longmao.gwcommon.entity.po.AnchorDeductionRule;

/**
 * 【主播减分规则】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:04:56
 */ 
public interface AnchorDeductionRuleService {

	 /**
	 * 【查询主播减分规则】
	 * @param id
	 * @return AnchorDeductionRule
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:04:56
	 */ 
	 AnchorDeductionRule findAnchorDeductionRule(Long id);

	 /**
	 * 【查询主播减分规则】
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:04:56
	 */ 
	 List<AnchorDeductionRule> findAnchorDeductionRule();
}

