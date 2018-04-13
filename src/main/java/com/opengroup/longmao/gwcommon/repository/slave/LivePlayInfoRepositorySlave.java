package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.LivePlayInfo;

public interface LivePlayInfoRepositorySlave extends BaseRepository<LivePlayInfo, Long> {

	@Query("select l from LivePlayInfo l where l.roomId = ?1 and l.utime is null")
	LivePlayInfo findLiveIsPlay(Long roomId);

}
