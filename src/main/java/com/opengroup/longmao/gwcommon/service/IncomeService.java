/**
 * @Title: IncomeService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import java.util.List;
import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;

/**
 * @ClassName: IncomeService
 * @Description: TODO
 * @author Mr.Zhu
 */
public interface IncomeService {

	/**
	 * @Title: extractCash
	 * @Description: 主播提现卡路里
	 * @param map
	 * @return boolean
	 */
	boolean extractCash(Map<String, Object> map);
	
	/**
	 * @Title: extractCalorie
	 * @Description: 主播提现卡路里
	 * @param map
	 * @return boolean
	 */
	boolean extractCalorie(Map<String, Object> map);

	/**
	 * @Title: editIdentity
	 * @Description: 新增、更新认证
	 * @param map
	 * @return boolean
	 */
	boolean editIdentity(Map<String, Object> map);

	/**
	 * @Title: queryIdentityByUserId
	 * @Description: 查询主播直播认证、转账提现信息
	 * @param userId
	 * @return IdentityInfo
	 */
	IdentityInfo queryIdentityByUserId(String userId);

	/**
	 * @Title: queryIdentityInfoVOByUserId
	 * @Description: 根据userId查询主播收益
	 * @param userId
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryExtractCashByUserId(String userId);

	/**
	 * @Title: queryExtractCashLogByUserId
	 * @Description: 根据userId查询主播提现记录
	 * @param userId
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryExtractCashLogByUserId(String userId);
	
	/**
	 * @Title: queryExtractCashDetails 
	 * @Description: 根据userId和orderId获取提现详情记录 
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryExtractCashDetails(String userId, String orderId);
	
	/**
	 * @Title: updateOpal 
	 * @Description: 更新主播猫眼石值 
	 * @param userId
	 * @param userOpal
	 * @param changeType
	 * @return Boolean
	 */
	Boolean updateOpal(String userId, Long userOpal, Integer changeType);
	
	/**
	 * @Title: updateOpalBatch 
	 * @Description: 批量更新主播猫眼石值 
	 * @param opalList
	 * @return Boolean
	 */
	Boolean updateOpalBatch(List<Map<String, Object>> opalList);
	
	/**
	 * @Title: updateCalorie 
	 * @Description: 更新主播卡路里 
	 * @param userId
	 * @param calorie
	 * @param changeType
	 * @return Boolean
	 */
	Boolean updateCalorie(String userId, Long calorie, Integer changeType);

}
