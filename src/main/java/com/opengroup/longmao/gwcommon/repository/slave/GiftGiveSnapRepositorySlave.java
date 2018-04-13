package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.GiftGiveSnap;

/**
 * 【礼物发送信息】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年08月28日 下午16:02:25
 */ 
public interface GiftGiveSnapRepositorySlave extends BaseRepository<GiftGiveSnap, Long> {
	
	
	/**
	 * 
	 *【根据receiver查询主播卡路里】 
	 * @param receiver
	 * @return Long返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月27日下午1:32:49
	 * @throws 异常
	 */
	@Query("SELECT SUM(g.calorie) FROM GiftGiveSnap g WHERE g.receiver = ?1")
	Long findCalorieByUserId(Long receiver);
	
	
}

