package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;

/**
 * 【直播房间表对象】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年12月22日 下午14:36:58
 */ 
public interface LiveRoomInfoService {
	
	/**
	 * @Title: queryLiveS 
	 * @Description: 查询主播By直播状态 
	 * @param playStatus
	 * @return List<Long>
	 */
	List<Long> queryLiveS(Short playStatus);

	/**
	 * 【保存直播房间表对象】
	 * @param liveRoomInfo
	 * @return liveRoomInfo
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */
	LiveRoomInfo saveLiveRoomInfo(LiveRoomInfo liveRoomInfo);

	/**
	 * 【删除直播房间表对象】
	 * @param id
	 * @return void
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */
	void deleteLiveRoomInfo(Long id);

	/**
	 * 【修改直播房间表对象】
	 * @param liveRoomInfo
	 * @return liveRoomInfo
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */
	LiveRoomInfo updateLiveRoomInfo(LiveRoomInfo liveRoomInfo);

	/**
	 * 【查询直播房间表对象】
	 * @param id
	 * @return LiveRoomInfo
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */
	LiveRoomInfo findLiveRoomInfo(Long id);

	/**
	 * 【根据主播查询直播房间表对象】
	 * @param id
	 * @return LiveRoomInfo
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */
	LiveRoomInfo findLiveRoomInfoByuserId(Long userId);

	/**
	 * 【查询直播房间表对象】
	 * @param liveRoomInfo
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<LiveRoomInfo>
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */
	Page<LiveRoomInfo> findLiveRoomInfo(LiveRoomInfo liveRoomInfo, Integer pageNo, Integer pageSize, String sortField);


}

