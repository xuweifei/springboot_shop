package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

/**
 * 
 *【主播等级规则】 Service接口
 * @author ShenZiYang 
 * @date 2018年3月13日 下午1:53:59
 */
public interface AnchorGradeRuleService {
	
	/**
	 * 
	 *【查询主播等级规则信息byUserId】 
	 * @param userId
	 * @return Map<String,Object>返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月13日下午2:34:25
	 * @throws 异常
	 */
	Map<String,Object> findAnchorGradeRule(String userId);
	
	/**
	 * 
	 *【更新主播的经验值】 
	 * @param userId 主播ID
	 * @param anchorExper 主播经验值
	 * @return boolean返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月13日下午5:14:44
	 * @throws 异常
	 */
	boolean updateAnchorExp(String userId,Long anchorExper);
	
	/**
	 * 
	 *【根据经验值查询主播等级】 
	 * @param calorie
	 * @param isDelete
	 * @return Short返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月27日下午2:00:43
	 * @throws 异常
	 */
	Short findAnchorGrade(Long calorie);
	
	
	
}
