package com.opengroup.longmao.gwcommon.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditRule;
import com.opengroup.longmao.gwcommon.enums.IsNormalEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.AnchorCreditRuleQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.AnchorCreditRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AnchorCreditRuleService;
/**
 * 【[全民主播]信誉等级提成规则】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:06:58
 */ 
@Service
public class AnchorCreditRuleServiceImpl implements AnchorCreditRuleService{

	@Autowired
	private AnchorCreditRuleRepositorySlave anchorCreditRuleRepositorySlave;

	/**
	* 【查询[全民主播]信誉等级提成规则】
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:06:58
	*/ 
	@Override
	public List<AnchorCreditRule> findAnchorCreditRule(){
		// 组合查询语句
		AnchorCreditRuleQueryFilter query = new AnchorCreditRuleQueryFilter();
		query.setIsDelete(IsNormalEnum.YES.getVal());
		// 查询分页数据
		List<AnchorCreditRule> list = anchorCreditRuleRepositorySlave.findAll(query);
		if(!list.isEmpty()){
			for(AnchorCreditRule acr:list){
		      String explain = acr.getRemark();
		      explain = explain.replace("[min_val]", acr.getMinVal().toString());
		      explain = explain.replace("[max_val]", acr.getMaxVal().toString());
		      acr.setRemark(explain);
			}
		}
		return list;
	}
	
	/**
	* 【根据id查询[全民主播]信誉等级提成规则】
	* @param id
	* @return anchorCreditRule
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:06:58
	*/ 
	@Override
	public AnchorCreditRule findAnchorCreditRule(Long id){
	    AnchorCreditRule anchorCreditRule = null;
		if(StringUtils.isNotBlank(id.toString())){
	      anchorCreditRule = anchorCreditRuleRepositorySlave.findOne(id);
	      String explain = anchorCreditRule.getRemark();
	      explain = explain.replace("[min_val]", anchorCreditRule.getMinVal().toString());
	      explain = explain.replace("[max_val]", anchorCreditRule.getMaxVal().toString());
	      anchorCreditRule.setRemark(explain);
		}else{
		  GwsLogger.info("id不存在");
		}
		return anchorCreditRule;
	}
	
	/**
	 * 
	 * 【根据等级分数查询[全民主播]信誉等级提成规则】
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.service.AnchorCreditRuleService#findAnchorCreditRule(java.lang.Integer)
	 */
	@Override
	public AnchorCreditRule findAnchorCreditRule(Short levelVal,Short isDelete) {
		AnchorCreditRule anchorCreditRule = null;
		if(StringUtils.isNotBlank(levelVal.toString())){
	      anchorCreditRule = anchorCreditRuleRepositorySlave.findAnchorCreditRule(levelVal,isDelete);
		}else{
		  GwsLogger.info("等级分数不存在");
		}
		return anchorCreditRule;
	}
	
}