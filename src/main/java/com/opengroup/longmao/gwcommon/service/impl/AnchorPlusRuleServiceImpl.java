package com.opengroup.longmao.gwcommon.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorPlusRule;
import com.opengroup.longmao.gwcommon.enums.IsNormalEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.AnchorPlusRuleQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.AnchorPlusRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AnchorPlusRuleService;

/**
 * 【主播加分规则】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:04:19
 */
@Service
public class AnchorPlusRuleServiceImpl implements AnchorPlusRuleService {

	@Autowired
	private AnchorPlusRuleRepositorySlave anchorPlusRuleRepositorySlave;

	/**
	 * 【查询主播加分规则】
	 * 
	 * @param anchorPlusRule
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:04:19
	 */
	@Override
	public List<AnchorPlusRule> findAnchorPlusRule() {
		// 组合查询语句
		AnchorPlusRuleQueryFilter query = new AnchorPlusRuleQueryFilter();
		query.setIsDelete(IsNormalEnum.YES.getVal());
		// 查询分页数据
		List<AnchorPlusRule> list = anchorPlusRuleRepositorySlave.findAll(query);
		if (!list.isEmpty()) {
			for (AnchorPlusRule apr : list) {
				String plusItem = apr.getPlusItem();
				plusItem = plusItem.replace("[condition]", apr.getCondition().toString());
				apr.setPlusItem(plusItem);
			}
		}
		return list;
	}

	/**
	 * 【根据id查询主播加分规则】
	 * 
	 * @param id
	 * @return anchorPlusRule
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:04:19
	 */
	@Override
	public AnchorPlusRule findAnchorPlusRule(Long id) {
		AnchorPlusRule anchorPlusRule = null;
		if (StringUtils.isNotBlank(id.toString())) {
			anchorPlusRule = anchorPlusRuleRepositorySlave.findOne(id);
			String plusItem = anchorPlusRule.getPlusItem();
			plusItem = plusItem.replace("[condition]", anchorPlusRule.getCondition().toString());
			anchorPlusRule.setPlusItem(plusItem);
		} else {
			GwsLogger.info("id不存在");
		}
		return anchorPlusRule;
	}

}
