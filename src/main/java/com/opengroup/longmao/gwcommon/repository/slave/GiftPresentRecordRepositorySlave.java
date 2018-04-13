package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.GiftPresentRecord;

/**
 * 【礼物赠送信息】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年06月05日 下午15:18:08
 */ 
public interface GiftPresentRecordRepositorySlave extends BaseRepository<GiftPresentRecord, Long> {
	
	@Query("SELECT SUM(g.calorie) AS calorie FROM GiftPresentRecord g WHERE g.receiver = ?1")
	Double getGiftCalorieByReceiver(String userId);
	
	@Query("SELECT new GiftPresentRecord(g.sender as sender, g.receiver as receiver, SUM(g.calorie) as calorie) FROM GiftPresentRecord g WHERE g.receiver = ?1 GROUP BY sender ORDER BY calorie DESC ")
	Page<GiftPresentRecord> getGiftRecordByReceiver(String userId, Pageable pageable);
}

