package com.opengroup.longmao.gwcommon.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorDeductionRule;
import com.opengroup.longmao.gwcommon.enums.IsNormalEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.AnchorDeductionRuleQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.AnchorDeductionRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AnchorDeductionRuleService;
/**
 * 【主播减分规则】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:04:56
 */ 
@Service
public class AnchorDeductionRuleServiceImpl implements AnchorDeductionRuleService{

	@Autowired
	private AnchorDeductionRuleRepositorySlave anchorDeductionRuleRepositorySlave;

	/**
	* 【查询主播减分规则】
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:04:56
	*/ 
	@Override
	public List<AnchorDeductionRule> findAnchorDeductionRule(){
		// 组合查询语句
		AnchorDeductionRuleQueryFilter query = new AnchorDeductionRuleQueryFilter();
		query.setIsDelete(IsNormalEnum.YES.getVal());
		//查询分页数据
		List<AnchorDeductionRule> list = anchorDeductionRuleRepositorySlave.findAll(query);
		if(!list.isEmpty()){
			for(AnchorDeductionRule adr:list){
		      String deductionItem = adr.getDeductionItem();
		      deductionItem = deductionItem.replace("[condition]", adr.getCondition());
		      adr.setDeductionItem(deductionItem);
			}
		}
		return list;
	}
	
	/**
	* 【根据id查询主播减分规则】
	* @param id
	* @return anchorDeductionRule
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:04:56
	*/ 
	@Override
	public AnchorDeductionRule findAnchorDeductionRule(Long id){
	    AnchorDeductionRule anchorDeductionRule = null;
		if(StringUtils.isNotBlank(id.toString())){
	      anchorDeductionRule = anchorDeductionRuleRepositorySlave.findOne(id);
	      String deductionItem = anchorDeductionRule.getDeductionItem();
	      deductionItem = deductionItem.replace("[condition]", anchorDeductionRule.getCondition());
	      anchorDeductionRule.setDeductionItem(deductionItem);
		}else{
		  GwsLogger.info("id不存在");
		}
		return anchorDeductionRule;
	}
	
}

