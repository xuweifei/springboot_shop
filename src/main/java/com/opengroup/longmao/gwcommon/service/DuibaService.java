package com.opengroup.longmao.gwcommon.service;

import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConfirmParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConsumeParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConsumeResult;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditNotifyParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditVirtualParams;
import com.opengroup.longmao.gwcommon.tools.sdk.topup.HuYiNotifyParams;

public interface DuibaService {

	/**
	 * @Title: saveOrderInfo 
	 * @Description: 保存兑吧订单-扣龙猫豆订单 
	 * @param params
	 * @param itemCode
	 * @return CreditConsumeResult
	 */
	public CreditConsumeResult saveOrderInfo(CreditConsumeParams params, String itemCode);
	
	/**
	 * @Title: saveOrderInfo 
	 * @Description: 保存兑吧订单-加龙猫豆订单 
	 * @param params
	 * @return CreditConsumeResult
	 */
	public CreditConsumeResult saveOrderInfo(CreditConsumeParams params);
	
	/**
	 * @Title: updateOrderState 
	 * @Description: 更新兑吧订单 
	 * @param params
	 */
	public void updateOrderState(CreditNotifyParams params);
	
	/**
	 * @Title: getBeanByUserId
	 * @Description: userId 获取用户龙猫豆
	 * @param userId
	 * @return BeanAccount
	 */
	public BeanAccount getBeanByUserId(String userId);
	
	/**
	 * @Title: phoneFeeTopUpInDuiBa 
	 * @Description: 兑吧订单虚拟充值 
	 * @param params
	 * @return CreditVirtualParams
	 */
	public CreditVirtualParams phoneFeeTopUpInDuiBa(CreditNotifyParams params);
	
	/**
	 * @Title: pushNotify 
	 * @Description: 充值结果推送 
	 * @param params
	 * @return CreditConfirmParams
	 */
	public CreditConfirmParams pushNotify(HuYiNotifyParams params);
	
	/**
	 * @Title: duiBaNotify 
	 * @Description: 兑吧充值结果通知 
	 * @param url
	 * @param signParams
	 */
	public void duiBaNotify(String url, String signParams);
}
