package com.opengroup.longmao.gwcommon.repository.master;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.User;

/**
 * 【用户信息表】 RepositoryMaster接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */ 
public interface UserRepositoryMaster extends BaseRepository<User, Long> {
	
	@Query("select u from User u where u.userId = ?1")
	User queryUserByUserId(String userId);
	
	@Query("SELECT u FROM User u WHERE u.userName = ?1")
	User queryUserByUserName(String userName);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE User u SET u.experience = u.experience + ?1, u.grade = ?2, u.gmtModified = ?3, u.gmtModifiedUser = ?4 WHERE u.id= ?5")
	Integer updateUser(String experience, Integer grade, Date gmtModified, String gmtModifiedUser, String id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE User u SET u.calorie = u.calorie + ?1, u.gmtModified = ?2, u.gmtModifiedUser = ?3 WHERE u.userId= ?4")
	Integer updateCalorie(Long calorie, Date gmtModified, String gmtModifiedUser, String userId);
}

