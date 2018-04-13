/**
 * @Title: AnchorService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;
import com.opengroup.longmao.gwcommon.entity.vo.AttentionsVO;
import com.opengroup.longmao.gwcommon.entity.vo.UserCalCntVO;

/**
 * @ClassName: AnchorService 
 * @Description: 主播管理Service
 * @author Mr.Zhu
 */
public interface AnchorService {
	
	/**
	 * @Title: queryAnchor 
	 * @Description: 查询主播信息 
	 * @param follow
	 * @param userId
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @param isPage
	 * @return AttentionsVO
	 */
	AttentionsVO queryAnchor(AttentionsVO follow, String userId, String condition, Integer pageNo, Integer pageSize,
			Short isPage);
	
	/**
	 * @Title: defendRanking 
	 * @Description: 主播守护排行榜 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return UserCalCntVO
	 */
	UserCalCntVO defendRanking(String userId, Integer pageNo, Integer pageSize);
	
	/**
	 * @Title: getGiftCalorieByReceiver 
	 * @Description: 获取累计卡路里 
	 * @param userId
	 * @return Long
	 */
	Long getGiftCalorieByReceiver(Long userId);
	
	/**
	 * @Title: findRoomInfoByUserId 
	 * @Description: 查找直播信息 
	 * @param userId
	 * @return LiveRoomInfo
	 */
	LiveRoomInfo findRoomInfoByUserId(Long userId);
	
	/**
	 * 
	 *【查询 主播等级；所属公会；直播版块 信息】 
	 * @param userId
	 * @return Map<String,Object>返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月14日下午3:21:48
	 * @throws 异常
	 */
	Map<String,Object> findAnchorInfo(String userId);
	
	
}
