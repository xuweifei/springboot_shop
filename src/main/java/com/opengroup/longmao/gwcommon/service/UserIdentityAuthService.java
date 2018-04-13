package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import com.opengroup.longmao.gwcommon.entity.po.UserIdentityAuth;

/**
 * 【用户身份认证表】 service接口
 *
 * @version 1.0
 * @author Hermit 2018年01月25日 下午16:40:59
 */
public interface UserIdentityAuthService {

	/**
	 * 【保存用户身份认证表】
	 * @param identity
	 * @return UserIdentityAuth
	 * @version 1.0
	 * @author Hermit 2018年01月25日 下午16:40:59
	 */
	UserIdentityAuth saveUserIdentityAuth(UserIdentityAuth identity);

	/**
	 * 【查询用户身份认证表】
	 * @param userId
	 * @return UserIdentityAuth
	 * @version 1.0
	 * @author Hermit 2018年01月25日 下午16:40:59
	 */
	UserIdentityAuth findUserIdentityAuth(Long userId);

	/**
	 * @Title: findAuthByIdCard
	 * @Description: 查询用户身份认证表
	 * @param idCard
	 * @return List<UserIdentityAuth>
	 */
	List<UserIdentityAuth> findAuthByIdCard(String idCard);

	/**
	 * @Title: verifyIdCard
	 * @Description: 查验用户身份证号是否被使用
	 * @param idCard
	 * @return Boolean
	 */
	Boolean verifyIdCard(String idCard);

}
