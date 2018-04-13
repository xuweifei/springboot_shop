package com.opengroup.longmao.gwcommon.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.opengroup.longmao.gwcommon.entity.po.User;

/**
 * 【用户信息表】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
public interface UserService {

	/**
	 * 【根据id查询用户信息表】
	 * 
	 * @param id
	 * @return UiUser
	 * @version 1.0
	 * @author Hermit 2017年03月15日 上午09:59:49
	 */
	User queryUserByUserId(String userId);

	/**
	 * 
	 * 【查询所有用户信息表并分页】
	 * 
	 * @author Hermit 2017年3月24日
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<User>
	 */
	Page<User> findAllUser(User user, Integer pageNo, Integer pageSize, String sortField);

	/**
	 * @Title: editUserData
	 * @Description: 编辑用户资料
	 * @param param
	 * @return Boolean
	 */
	Boolean editUserData(Map<String, String> param);
	
	/**
	 * @Title: queryUserByNickName 
	 * @Description: 根据nickName查询用户信息表 
	 * @param nickName
	 * @return User
	 */
	List<User> queryUserByNickName(String nickName);
	
	/**
	 * @Title: relation 
	 * @Description: 用户关系 
	 * @param userId
	 * @param groupId
	 * @param anchorId
	 * @param manageId
	 * @return Map<String,Object>
	 */
	Map<String, Object> relation(Long userId, Long groupId, Long anchorId, Long manageId);
	
	/**
	 * 【根据手机号码查询用户】
	 * @author xwf 2018年1月12日
	 * @param phoneNum
	 * @return
	 */
	User queryUserByMobile(String phoneNum);

	/**
	 * 根据三方登录账号查询用户
	 * @author xwf 2018年1月23日
	 * @param loginId
	 * @param way
	 * @param pushId
	 */
	User getUserByLoginId(String loginId, Short way, String pushId);

}
