/**
 * @Title: IdentityService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;

/**
 * 
 *【@ClassName: IdentityService】  
 * @author ShenZiYang 
 * @date 2018年3月19日 下午6:08:55
 */
public interface IdentityService {
	

	/**
	 * 
	 *【通过主播ID查询主播实体】 
	 * @param userId
	 * @return IdentityService返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月19日下午6:11:14
	 * @throws 异常
	 */
	IdentityInfo findIdentityByUserId(String userId);
	
	
	/**
	 * 
	 *【查询主播ID】 
	 * @return List<String>返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月27日上午11:47:09
	 * @throws 异常
	 */
	List<String> findUserIdList();
	
	
	/**
	 * 
	 *【更新主播的经验值和等级】 
	 * @param calorie
	 * @param grade
	 * @param userId
	 * @return int返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月27日下午2:16:24
	 * @throws 异常
	 */
	int updateGradeAndExper(Long calorie,Integer grade,String userId);
	
	
	
}
