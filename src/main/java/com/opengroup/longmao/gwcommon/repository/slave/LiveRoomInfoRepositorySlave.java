package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;

public interface LiveRoomInfoRepositorySlave extends BaseRepository<LiveRoomInfo, Long>{

	@Query("select l from LiveRoomInfo l where l.userId = ?1 and l.playStatus = 0")
	LiveRoomInfo findAnchorIsPlaying(long parseLong);
	
	@Query("select l from LiveRoomInfo l where l.userId = ?1")
	LiveRoomInfo findRoomInfoByUserId(long userId);
	
	@Query("SELECT l.userId FROM LiveRoomInfo l WHERE l.playStatus = ?1 ORDER BY utime DESC")
	List<Long> queryLiveS(Short playStatus);

}
