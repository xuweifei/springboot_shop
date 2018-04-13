package com.opengroup.longmao.gwcommon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.repository.slave.GiftGiveSnapRepositorySlave;
import com.opengroup.longmao.gwcommon.service.GiftGiveSnapService;


/**
 * 
 *【虚拟送礼赠送快照  Service接口实现】  
 * @author ShenZiYang 
 * @date 2018年3月27日 下午1:40:19
 */
@Service("giftGiveSnapService")
public class GiftGiveSnapServiceImpl implements GiftGiveSnapService {
	
	@Autowired
	private GiftGiveSnapRepositorySlave GiftGiveSnapRepositorySlave;
	
	@Override
	public Long findCalorieByUserId(Long receiver) {
		return GiftGiveSnapRepositorySlave.findCalorieByUserId(receiver);
	}

	
}