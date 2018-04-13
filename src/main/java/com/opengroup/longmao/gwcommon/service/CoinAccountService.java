package com.opengroup.longmao.gwcommon.service;

import java.math.BigDecimal;
import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.CoinAccount;

/**
 * 【龙猫币账户】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:49:16
 */ 
public interface CoinAccountService {
	/**
	 * 用户充值
	 * @param userId 用户userId
	 * @param rmb RMB
	 * @return boolean
	 */
	boolean recharge(String userId,BigDecimal rmb);
	
	
	/**
	 * 用户充值
	 * @param userId 用户userId
	 * @param rmb RMB
	 * @return boolean
	 */
	boolean rechargeByUserId(String userId,BigDecimal rmb);
	
	/**
	 * @Title: getCoinById
	 * @Description: 根据id查询龙猫币
	 * @param id
	 * @return CoinAccount
	 */
	CoinAccount getCoinById(String id);
	
	/**
	 * @Title: getCoinByUserId 
	 * @Description: 根据userId查询龙猫币 
	 * @param userId
	 * @return CoinAccount
	 */
	CoinAccount getCoinByUserId(String userId);
	
	/**
	 * @Title: updateCoin 
	 * @Description: 更新龙猫币/增加龙猫币流水 
	 * @param userId
	 * @param lmCoinNum
	 * @param changeType
	 * @param bizType
	 * @return boolean
	 */
	boolean updateCoin(String userId, BigDecimal lmCoinNum, Integer changeType, Integer bizType);
	
	/**
	 * @Title: coinToBean 
	 * @Description: 龙猫币兑换龙猫豆 
	 * @param userId
	 * @param lmCoinNum
	 * @return Map<String,Object>
	 */
	Map<String, Object> coinToBean(String userId, Integer lmCoinNum);
	
	/**
	 * @Title: coinExchBean 
	 * @Description: 全体用户龙猫币转换龙猫豆 
	 * @return Map<String, Object>
	 */
	Map<String, Object> coinExchBean();
}

