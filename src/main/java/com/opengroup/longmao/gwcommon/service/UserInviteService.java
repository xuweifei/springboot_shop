package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.opengroup.longmao.gwcommon.entity.po.UserInvite;

/**
 * 用户邀请关系表 Service
 * @version 
 * @author xwf  2018年1月4日 下午2:32:02
 */
public interface UserInviteService {

	
	/**
	 * 创建邀请关系
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @param phoneNum
	 * @return
	 */
	Map<String, Object> createInvitation(Long userId, String phoneNum);
	
	
	/**
	 * 根据手机号查询邀请关系
	 * @author xwf 2018年1月4日
	 * @param phoneNum
	 * @return
	 */
	UserInvite getInvitationByPhoneNum(String phoneNum);

	/**
	 * 查询邀请奖励总览
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	Map<String, Object> findInviteReward(Long userId);

	/**
	 * 查询邀请明细
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	Page<UserInvite> findInviteDetail(Long userId, Integer pageNo, Integer pageSize);

	/**
	 * 查询邀请配置信息
	 * @author xwf 2018年1月5日
	 * @return
	 */
	Map<String, String> getInviteConfig();
	
	
	
	
}
