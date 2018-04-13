package com.opengroup.longmao.gwcommon.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;

/**
 * 【龙猫豆账户】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:48:32
 */ 
public interface BeanAccountService {
	
	/**
	 * 用户充值
	 * @param userId 用户userId
	 * @param rmb RMB
	 * @return boolean
	 */
	boolean recharge(String userId, BigDecimal rmb);

	/**
	 * 【根据id查询对象】
	 *
	 * @param id
	 * @return BeanAccount
	 */
	BeanAccount queryById(String id);
	
	/**
	 * @Title: getBeanById
	 * @Description: 根据id查询龙猫豆
	 * @param id
	 * @return BeanAccount
	 */
	BeanAccount getBeanById(String id);

	/**
	 * 
	 * 【根据userId查询对象】
	 * 
	 * @author Hermit 2017年4月13日
	 * @param userId
	 * @return
	 */
	BeanAccount getBeanByUserId(String userId);
	
	/**
	 * 
	 * 【根据用户id修改龙猫豆】
	 * 
	 * @author Hermit 2017年4月10日
	 * @param userId
	 * @param changeNum
	 * @param changeType
	 * @return
	 */
	boolean updateBeanAccount(String userId, Double changeNum, Integer changeType, Integer bizType);
	
	/**
	 * 
	 * 【批量增减用户龙猫豆】
	 * 
	 * @author Hermit 2017年4月12日
	 * @param beanAccountList
	 * @return
	 */
	boolean updateBeanAccountBatch(List<Map<String, Object>> beanAccountList);
	
	/**
	 * @Title: updateBean 
	 * @Description: 更新龙猫豆/增加龙猫豆流水
	 * @param userId
	 * @param lmBeanNum
	 * @param changeType
	 * @param bizType
	 * @return boolean
	 */
	boolean updateBean(String userId, BigDecimal lmBeanNum, Integer changeType, Integer bizType);
	
	/**
	 * @Title: beanToCoin 
	 * @Description: 龙猫豆兑换龙猫币 
	 * @param userId
	 * @param lmBeanNum
	 * @return Map<String,Object>
	 */
	Map<String, Object> beanToCoin(String userId, Integer lmBeanNum);

}

