package com.opengroup.longmao.gwcommon.service;

/**
 * 
 *【虚拟送礼赠送快照】 service接口
 * @author ShenZiYang 
 * @date 2018年3月27日 下午1:38:40
 */
public interface GiftGiveSnapService {
	
	/**
	 * 
	 *【根据receiver查询主播卡路里】 
	 * @param receiver
	 * @return Long返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月27日下午1:43:00
	 * @throws 异常
	 */
	Long findCalorieByUserId(Long receiver);
	
}