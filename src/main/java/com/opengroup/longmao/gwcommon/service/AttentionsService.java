package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import com.opengroup.longmao.gwcommon.entity.po.Attentions;
import com.opengroup.longmao.gwcommon.entity.vo.AttentionsVO;

/**
 * 【关注信息表】 service接口
 * @version 1.0
 * @author Hermit 2017年03月15日 下午13:41:34
 */ 
public interface AttentionsService {
	
	/**
	 * @Title: queryAttentions 
	 * @Description: 查询关注信息By是否关注 
	 * @param userId
	 * @param attentionsState
	 * @return List<String>
	 */
	List<String> queryAttentions(String userId, Short attentionsState);
	
	/**
	 * @Title: queryAttentions 
	 * @Description: 查询用户关注（我所关注的人）
	 * @param userId
	 * @return List<Attentions>
	 */
	List<Attentions> queryAttentions(String userId);
	
	/**
	 * 根据ID查询对象（我所关注的人）
	 * @param userId
	 * @return UserDTO
	 */
	List<Attentions> queryUserByUserId(String userId);

	/**
	 * 根据ID查询对象（关注我的人）
	 * @param anchorUserId
	 * @return UserDTO
	 */
	List<Attentions> queryUserByAnchorUserId(String anchorUserId);
	
	/**
	 * @Title: queryUserByUserIdAndFollowId 
	 * @Description: 查询用户关注信息 
	 * @param userId
	 * @param followId
	 * @return Attentions
	 */
	Attentions queryUserByUserIdAndFollowId(String userId, String followId);
	
	/**
	 * @Title: attent 
	 * @Description: TODO 
	 * @param userId
	 * @param followId
	 * @param State
	 * @return Attentions
	 */
	Attentions attent(String userId, String followId, Short State);
	
	/**
	 * @Title: queryFans 
	 * @Description: 查询粉丝列表 
	 * @param userId
	 * @return AttentionsVO
	 */
	AttentionsVO queryFans(String userId);
	
	/**
	 * @Title: queryFollow 
	 * @Description: 查询关注列表 
	 * @param userId
	 * @return AttentionsVO
	 */
	AttentionsVO queryFollow(String userId);
	
	/**
	 * @Title: queryFollowLive 
	 * @Description: 查询关注列表(直播中) 
	 * @param userId
	 * @return AttentionsVO
	 */
	AttentionsVO queryFollowLive(String userId);

}

