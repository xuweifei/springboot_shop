package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.LiveGroupAdmin;

/**
 * 【直播群组管理员表】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年07月13日 上午09:50:49
 */ 
public interface LiveGroupAdminRepositorySlave extends BaseRepository<LiveGroupAdmin, Long> {
	
	@Query("select lga from LiveGroupAdmin lga where lga.groupId =?1 and lga.userId =?2 and lga.isDelete =1")
	List<LiveGroupAdmin> findLiveGroupAdminByGroupIdAndUserId(Long groupId,Long userId);
	
	
	@Query("select lga from LiveGroupAdmin lga where lga.groupId =?1 and lga.userId =?2 and lga.liveUserId =?3")
	LiveGroupAdmin findLiveGroupAdminByGroupIdAndUserIdAndLiveUserId(Long groupId,Long userId,Long liveUserId);
	
	
	@Query("select lga from LiveGroupAdmin lga where lga.groupId =?1 and lga.userId =?2 and lga.liveUserId =?3 and lga.isDelete =1")
	LiveGroupAdmin findLiveGroupAdminByParam(Long groupId,Long userId,Long liveUserId);
	
	@Query("select count(1) from LiveGroupAdmin lga where lga.groupId =?1 and lga.userId =?2  and lga.isDelete =1")
	Integer countLiveGroupAdmins(Long groupId, Long userId);

}

