package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.UserInvite;

/**
 * 【用户邀请关系表】 RepositoryMaster接口
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */ 
public interface UserInviteRepositorySlave extends BaseRepository<UserInvite, Long> {
	
	
	@Query("select u from UserInvite u where u.phoneNum = ?1")
	UserInvite getInvitationByPhoneNum(String phoneNum);

	@Query("select new UserInvite(count(u.id) as id,sum(u.inviteReward) as userId) from UserInvite u where u.userId = ?1 and u.isReceivable = 3")
	UserInvite findInviteReward(Long userId);

	@Query("select u from UserInvite u where u.pushId = ?1 and u.isReceivable = 3")
	List<UserInvite> getInvitationByPushId(String pushId);
	
}

