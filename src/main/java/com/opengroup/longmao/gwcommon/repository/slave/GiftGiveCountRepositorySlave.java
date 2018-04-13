package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.GiftGiveCount;

/**
 * 【用户礼物赠送统计】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年08月28日 下午16:01:13
 */ 
public interface GiftGiveCountRepositorySlave extends BaseRepository<GiftGiveCount, Long> {

	@Query("SELECT SUM(g.calorie) AS calorie FROM GiftGiveCount g WHERE g.receiver = ?1")
	Long getGiftCalorieByReceiver(Long userId);
	
}

