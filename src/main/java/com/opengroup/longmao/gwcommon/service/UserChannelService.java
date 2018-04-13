package com.opengroup.longmao.gwcommon.service;

import java.util.Map;

import com.opengroup.longmao.gwcommon.entity.po.Channel;

/**
 * 【用户渠道信息表】 service接口
 * @version 1.0
 * @author xwf 2017年03月15日 上午09:59:49
 */
public interface UserChannelService {
	
	/**
	 * 根据渠道号查询渠道信息
	 * @author xwf 2018年1月4日
	 * @param channelId
	 * @return
	 */
	Channel getChannelById(Long channelId);
	
	/**
	 * 用户重新设置渠道号
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @param channelId
	 * @return
	 */
	Map<String, Object> resetChannel(Long userId, Long channelId);
	
	/**
	 * 查询用户渠道号
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	Map<String, Object> getChannelId(Long userId);
	
	
}
