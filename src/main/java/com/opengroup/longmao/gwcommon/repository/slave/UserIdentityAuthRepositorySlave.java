package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.UserIdentityAuth;

/**
 * 【用户身份认证表】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2018年01月25日 下午16:40:59
 */ 
public interface UserIdentityAuthRepositorySlave extends BaseRepository<UserIdentityAuth, Long> {
	
	@Query("SELECT u FROM UserIdentityAuth u WHERE u.userId = ?1")
	UserIdentityAuth queryIdentityByUserId(Long userId);

	@Query("SELECT u FROM UserIdentityAuth u WHERE u.idCard = ?1")
	List<UserIdentityAuth> queryIdentityByIdCard(String idCard);
	
	@Query("SELECT u FROM UserIdentityAuth u WHERE (u.idCard = ?1 AND u.authStatus = ?2) OR (u.idCard = ?1 AND u.authStatus = ?3)")
	List<UserIdentityAuth> verifyIdCard(String idCard, Short underReview, Short pass);
	
}

