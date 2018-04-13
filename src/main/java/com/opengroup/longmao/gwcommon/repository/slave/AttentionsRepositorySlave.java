package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.Attentions;

/**
 * 【用户关注/粉丝】 RepositorySlave接口
 * @version 1.0
 * @author Hermit 2017年03月15日 下午13:41:34
 */ 
public interface AttentionsRepositorySlave extends BaseRepository<Attentions, Long> {

	/**
	 * 【我所关注的人】
	 * @author Administrator 2017年3月15日
	 * @param userId
	 * @return
	 */
	@Query("select u from Attentions u where u.userId = ?1 and u.attentionsState = 1")
	List<Attentions> queryUserByUserId(String userId);
	
	/**
	 * 【关注我的人】
	 * @author Administrator 2017年3月15日
	 * @param anchorUserId
	 * @return
	 */
	@Query("select u from Attentions u where u.anchoruserId = ?1 and u.attentionsState = 1")
	List<Attentions> queryUserByAnchorUserId(String anchorUserId);
	
	/**
	 * @Title: queryUserByUserIdAndFollowId 
	 * @Description: 根据userId、followId查询关注数据 
	 * @param userId
	 * @param followId
	 * @return Attentions
	 */
	@Query("SELECT u FROM Attentions u WHERE u.userId = ?1 AND u.anchoruserId = ?2")
	Attentions queryUserByUserIdAndFollowId(String userId, String followId);
	
	@Query("SELECT a.anchoruserId FROM Attentions a WHERE a.userId = ?1 AND a.attentionsState = ?2 ORDER BY a.gmtCreate DESC")
	List<String> queryAttentions(String userId, Short attentionsState);
    
}

