package com.opengroup.longmao.gwcommon.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.Channel;
import com.opengroup.longmao.gwcommon.entity.po.LoginRecord;
import com.opengroup.longmao.gwcommon.entity.po.UserChannel;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.repository.master.UserChannelRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.ChannelRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.LoginRecordRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserChannelRepositorySlave;
import com.opengroup.longmao.gwcommon.service.UserChannelService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;



/**
 * 【用户渠道信息表】 serviceImpl
 * @version 1.0
 * @author xwf 2017年03月15日 上午09:59:49
 */
@Service
public class UserChannelServiceImpl implements UserChannelService{
	
	@Autowired
	private ChannelRepositorySlave channelRepositorySlave;
	
	@Autowired
	private LoginRecordRepositorySlave loginRecordRepositorySlave;
	
	@Autowired
	private UserChannelRepositoryMaster userChannelRepositoryMaster;
	
	@Autowired
	private UserChannelRepositorySlave userChannelRepositorySlave;
	
	
	/**
	 * 根据渠道号查询渠道信息
	 * @author xwf 2018年1月4日
	 * @param channelId
	 * @return
	 */
	@Override
	public Channel getChannelById(Long channelId) {
		return channelRepositorySlave.findOne(channelId);
	}

	/**
	 * 用户重新设置渠道号
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @param channelId
	 * @return
	 */
	@Override
	public Map<String, Object> resetChannel(Long userId, Long channelId) {
		Map<String, Object> returnMap = new HashMap<>();
		int currentSecond = DateUtil.currentSecond();
		
		// 查询用户渠道信息
		UserChannel userChannel = userChannelRepositorySlave.findOne(userId);
		if (null == userChannel) {
			GwsLogger.info("用户没有没有渠道信息,创建渠道信息:userId={}", userId);
			LoginRecord loginRecord = loginRecordRepositorySlave.getFirstRecord(userId.toString());
			if (null == loginRecord) {
				returnMap.put("channelId", 20001L);
				returnMap.put("flag", false);
				return returnMap;
			}
			
			userChannel = new UserChannel();
			userChannel.setUserId(userId);
			userChannel.setChannelId(Long.valueOf(loginRecord.getChlId()));
			userChannel.setCreateDate(loginRecord.getGmtCreate());
			userChannel.setLoginWay(loginRecord.getLoginWay());
			userChannel.setResetNum(0);
			userChannel.setRemark(null);
			userChannel.setCtime(null == loginRecord.getGmtCreate() ? 0 :(int)(loginRecord.getGmtCreate().getTime()/1000));
			userChannel.setUtime(null == loginRecord.getGmtModified() ? 0 : (int)(loginRecord.getGmtModified().getTime()/1000));
			userChannel.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			
			userChannel = userChannelRepositoryMaster.save(userChannel);
		}
		// 不是20001不能修改渠道号
		if (userChannel.getChannelId() - 20001 != 0) {
			throw new ImplException(CommConstant.GWSCOD0034, CommConstant.GWSMSG0034);
		}
		
		// 修改用户渠道信息并保存
		userChannel.setChannelId(channelId);
		userChannel.setResetNum(userChannel.getResetNum() + 1);
		userChannel.setUtime(currentSecond);
		UserChannel afterSave = userChannelRepositoryMaster.save(userChannel);
		
		if (null == afterSave) {
			returnMap.put("channelId", 20001L);
			returnMap.put("flag", false);
		}else {
			returnMap.put("channelId", channelId);
			returnMap.put("flag", true);
		}
		return returnMap;
	}

	
	/**
	 * 查询用户渠道号
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String, Object> getChannelId(Long userId) {
		// 查询用户渠道信息
		UserChannel userChannel = userChannelRepositorySlave.findOne(userId);
		
		if (null == userChannel) {
			GwsLogger.info("userChannel为空,查询首次登录信息...");
			// 查询第一次登录信息,如果首次登录信息为null,不作判断,返回001
			LoginRecord loginRecord = loginRecordRepositorySlave.getFirstRecord(userId.toString());
			
			userChannel = new UserChannel();
			userChannel.setUserId(userId);
			userChannel.setChannelId(Long.valueOf(loginRecord.getChlId()));
			userChannel.setCreateDate(loginRecord.getGmtCreate());
			userChannel.setLoginWay(loginRecord.getLoginWay());
			userChannel.setResetNum(0);
			userChannel.setRemark(null);
			userChannel.setCtime(null == loginRecord.getGmtCreate() ? 0 :(int)(loginRecord.getGmtCreate().getTime()/1000));
			userChannel.setUtime(null == loginRecord.getGmtModified() ? 0 : (int)(loginRecord.getGmtModified().getTime()/1000));
			userChannel.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			
			userChannelRepositoryMaster.save(userChannel);
		}
		
		Map<String, Object> returnMap = new HashMap<>();
		Long channelId = (null == userChannel.getChannelId()) ? 10001 : userChannel.getChannelId();
		returnMap.put("channelId", channelId);
		
		return returnMap;
	}
	
	
	
	
	
}
