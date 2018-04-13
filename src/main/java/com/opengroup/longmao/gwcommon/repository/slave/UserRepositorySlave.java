package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.User;

/**
 * 【用户信息表】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */ 
public interface UserRepositorySlave extends BaseRepository<User, Long> {
	
	@Query("select u from User u where u.userId = ?1")
	User queryUserByUserId(String userId);
	
	@Query("SELECT u FROM User u WHERE u.userName = ?1")
	User queryUserByUserName(String userName);
	
	@Query("SELECT u FROM User u WHERE u.nickName = ?1")
	List<User> queryUserByNickName(String nickName);
	
	@Query("SELECT u FROM User u WHERE u.wechatId = ?1")
	User queryUserByWechatId(String wechatId);
	
	@Query("SELECT u FROM User u WHERE u.qqId = ?1")
	User queryUserByQQId(String qqId);
	
	@Query("SELECT u FROM User u WHERE u.weiboId = ?1")
	User queryUserByWeiboId(String weiboId);
	
	@Query("SELECT u FROM User u WHERE u.userName = ?1 AND u.loginPwd = ?2")
	User queryUserByUserNamePwd(String userName, String pwd);
	
	@Query("SELECT MAX(userId + 0) AS userId FROM User")
	String queryUserIdMax();

	@Query("SELECT u FROM User u WHERE u.userId in ?1")
	List<User> getUserListByIdList(List<String> userIdList);
	
	@Query("select u from User u where u.userId like %?1% or u.nickName like %?1% and u.userType = ?2 and u.isDelete = ?3")
	List<User> queryAnchorByUserIdOrNickName(String condition, Integer userType, Short isDelete);
	
	@Query("SELECT u FROM User u where u.userType = ?1 AND u.isDelete = ?2 ORDER BY gmtCreate DESC")
	List<User> queryAnchorByUserType(Integer userType, Short isDelete);
}

