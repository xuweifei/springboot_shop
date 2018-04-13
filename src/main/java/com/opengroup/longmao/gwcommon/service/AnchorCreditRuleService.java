package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditRule;

/**
 * 【[全民主播]信誉等级提成规则】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:06:58
 */ 
public interface AnchorCreditRuleService {

	 /**
	 * 【查询[全民主播]信誉等级提成规则】
	 * @param id
	 * @return AnchorCreditRule
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:06:58
	 */ 
	 AnchorCreditRule findAnchorCreditRule(Long id);

	 /**
	 * 【查询[全民主播]信誉等级提成规则】
	 * @param anchorCreditRule
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:06:58
	 */ 
	 List<AnchorCreditRule> findAnchorCreditRule();

	 /**
	  * 
	  * 【根据等级分数查询[全民主播]信誉等级提成规则】
	  * 
	  * @author Hermit 2017年3月31日
	  * @param levelVal
	  * @return
	  */
	 AnchorCreditRule findAnchorCreditRule(Short levelVal,Short isDelete);
	 
}

