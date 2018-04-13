package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserConfig;
import com.opengroup.longmao.gwcommon.entity.po.UserInvite;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.IsReceivableEnum;
import com.opengroup.longmao.gwcommon.repository.master.UserInviteRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.UserInviteQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.UserConfigRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserInviteRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.UserInviteService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;

/**
 * 用户邀请关系表 ServiceImpl
 * @version 
 * @author xwf  2018年1月4日 下午2:32:02
 */
@Service
public class UserInviteServiceImpl implements UserInviteService{
	
	@Autowired
	private UserInviteRepositoryMaster userInviteRepositoryMaster;
	
	@Autowired
	private UserInviteRepositorySlave userInviteRepositorySlave;
	
	@Autowired
	private UserConfigRepositorySlave userConfigRepositorySlave;
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private RedisReadWriteService redis;
	
	/**
	 * 创建邀请关系
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @param phoneNum
	 * @return
	 */
	@Override
	public Map<String, Object> createInvitation(Long userId, String phoneNum) {
		UserInvite userInvite = new UserInvite();
		int currentSecond = DateUtil.currentSecond();
		// 查询邀请好友配置信息
		Map<String, String> configMap = getInviteConfig();
		// 判断开关是否开启
		if(configMap.get("invite_is_on").equals(IsOrNotEnum.NO.getType().toString())) {
			throw new ImplException(CommConstant.GWSCOD0033, CommConstant.GWSMSG0033);
		}
		
		userInvite.setId(idGlobalGenerator.getSeqId(UserInvite.class));
		userInvite.setUserId(userId);
		userInvite.setPhoneNum(phoneNum);
		userInvite.setInvitedUserId(null);
		userInvite.setInviteReward(Integer.valueOf(configMap.get("invite_reward")));
		userInvite.setInvitedReward(Integer.valueOf(configMap.get("invited_reward")));
		userInvite.setIsReceivable(IsReceivableEnum.CAN_NOT_RECEIVE.getVal());
		userInvite.setRemark("");
		userInvite.setCtime(currentSecond);
		userInvite.setUtime(currentSecond);
		userInvite.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		
		UserInvite newUserInvite = userInviteRepositoryMaster.save(userInvite);
		
		Map<String, Object> map = new HashMap<>();
		if(null == newUserInvite) {
			map.put("flag", false);
			map.put("userId", userId);
		}else {
			map.put("flag", true);
			map.put("userId", userId);
		}
		return map;
	}

	/**
	 * 查询邀请配置信息
	 * @author xwf 2018年1月5日
	 * @return
	 */
	@Override
	public Map<String, String> getInviteConfig() {
		
		// redis中配置信息
		//Map<String, String> configMap = cache.hashGet(CommConstant.USER_CONFIG + CommConstant.INVITE_CONFIG);
		Map<String, String> configMap = redis.hashGet(ConfigConstant.USER_CONFIG + ConfigConstant.INVITE_CONFIG);
		// 如果redis中没有数据,从数据库中查询,并保存到redis
		if(null == configMap || configMap.keySet().size() < 3) {
			GwsLogger.info("redis中没有配置信息:configMap={}", configMap.toString());
			configMap = new HashMap<>();
			List<UserConfig> configList = userConfigRepositorySlave.getInviteConfig(ConfigConstant.INVITE_CONFIG);
			for(UserConfig u : configList) {
				configMap.put(u.getConfigKey(), u.getConfigValue());
				//cache.hashSet(CommConstant.USER_CONFIG + CommConstant.INVITE_CONFIG, u.getConfigKey(), u.getConfigValue());
				redis.hashSet(ConfigConstant.USER_CONFIG + ConfigConstant.INVITE_CONFIG, u.getConfigKey(), u.getConfigValue());
			}
		}
		return configMap;
	}


	/**
	 * 根据手机号查询邀请关系
	 * @author xwf 2018年1月4日
	 * @param phoneNum
	 * @return
	 */
	@Override
	public UserInvite getInvitationByPhoneNum(String phoneNum) {
		return userInviteRepositorySlave.getInvitationByPhoneNum(phoneNum);
	}

	
	/**
	 * 【查询邀请奖励总览】
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String, Object> findInviteReward(Long userId) {
		UserInvite userInvite = userInviteRepositorySlave.findInviteReward(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("inviteNum", (null == userInvite || null == userInvite.getId()) ? 0 : userInvite.getId());
		map.put("totalReward", (null == userInvite || null == userInvite.getUserId()) ? 0 : userInvite.getUserId());
		return map;
	}

	/**
	 * 查询邀请明细
	 * @author xwf 2018年1月4日
	 * @param userId
	 * @return
	 */
	@Override
	public Page<UserInvite> findInviteDetail(Long userId, Integer pageNo, Integer pageSize) {
		// 分页条件
		Sort sort = new Sort(Direction.DESC, "utime");
		Pageable pageable = new PageRequest(pageNo, pageSize, sort);
		// 查询条件
		UserInviteQueryFilter query = new UserInviteQueryFilter();
		query.setUserId(userId);
		
		// 查询数据
		Page<UserInvite> userInvitePage = userInviteRepositorySlave.findAll(query, pageable);
		List<UserInvite> inviteList = userInvitePage.getContent();
		if (CollectionUtils.isEmpty(inviteList)) {
			return userInvitePage;
		}
		// 被邀请者头像
		Map<Long, String> headUrlMap = getHeadUrlMap(inviteList);
		for(UserInvite u : inviteList) {
			u.setHeadUrl("");
			if(u.getIsReceivable().equals(IsReceivableEnum.HAVE_RECEIVED.getVal())) {
				u.setHeadUrl(null == headUrlMap.get(u.getInvitedUserId()) ? "" : headUrlMap.get(u.getInvitedUserId()));
				u.setRemark(DateUtil.timestampToDates(u.getUtime(),DateUtil.DATE_PATTON_DEFAULT));
			}
		}
		
		return userInvitePage;
	}
	
	/**
	 * 查询被邀请者头像
	 * @author xwf 2018年1月6日
	 * @param inviteList
	 * @return
	 */
	private Map<Long, String> getHeadUrlMap(List<UserInvite> inviteList) {
		List<String> userIdList = new ArrayList<>();
		for(UserInvite u : inviteList) {
			if (u.getIsReceivable().equals(IsReceivableEnum.HAVE_RECEIVED.getVal())) {
				userIdList.add(u.getInvitedUserId().toString());
			}
		}
		
		Map<Long, String> map = new HashMap<>();
		if (userIdList.size() < 1) {
			return map;
		}
		
		// 查询头像
		List<User> userList = userRepositorySlave.getUserListByIdList(userIdList);
		for(User u : userList) {
			map.put(Long.valueOf(u.getUserId()), u.getPhoneId());
		}
		return map;
	}

	
	
	
	
}
