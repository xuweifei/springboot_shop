package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.LiveMemberSit;

/**
 * 【直播群组成员禁言列表】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年07月14日 下午15:13:09
 */ 
public interface LiveMemberSitRepositorySlave extends BaseRepository<LiveMemberSit, Long> {
	
	@Query("select lms from LiveMemberSit lms where lms.groupId = ?1 and lms.userId = ?2 and lms.forbidStatus = 1 and lms.isDelete = 1")
	List<LiveMemberSit> findForbidMembers(Long groupId, Long userId);
}

