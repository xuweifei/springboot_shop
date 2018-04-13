package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.Attentions;
import com.opengroup.longmao.gwcommon.entity.po.Live;
import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.vo.AttentionsVO;
import com.opengroup.longmao.gwcommon.enums.FollowStateEnum;
import com.opengroup.longmao.gwcommon.repository.master.AttentionsRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.AttentionsQueryFilter;
import com.opengroup.longmao.gwcommon.repository.queryFilter.LiveQueryFilter;
import com.opengroup.longmao.gwcommon.repository.queryFilter.UserQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.AttentionsRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.LiveRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.LiveRoomInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AttentionsService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;

/**
 * 【关注信息表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午13:41:34
 */
@Service
public class AttentionsServiceImpl implements AttentionsService {
	
	@Autowired
	private LiveRoomInfoRepositorySlave liveRoomInfoRepositorySlave;

	@Autowired
	private AttentionsRepositoryMaster attentionsRepositoryMaster;
	
	@Autowired
	private AttentionsRepositorySlave attentionsRepositorySlave;
	
	@Autowired
	private LiveRepositorySlave liveRepositorySlave;
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private RedisReadWriteService redis;
	
	/**
	 * @Title: attent
	 * @Description: TODO
	 * @param userId
	 * @param followId
	 * @param State
	 * @return Attentions
	 */
	@Override
	public Attentions attent(String userId, String followId, Short State) {
		//被关注用户粉丝数(followId)
		//Integer fans = cache.hashGet(CommConstant.USER_RELATION_INFO + "fansCount", String.valueOf(followId));
		Integer fans = redis.hashGet(ConfigConstant.FANS_COUNT, String.valueOf(followId));
		if (null == fans) {
			List<Attentions> userFans = queryUserByAnchorUserId(String.valueOf(followId));
			fans = CollectionUtils.isNotEmpty(userFans) ? userFans.size() : 0;
		}
		
		if (FollowStateEnum.UNFOLLOW.getVal().equals(State)) {
			fans = 0 < fans ? fans - 1 : 0;//用户取关,减去被关注用户粉丝数
		} else {
			fans = fans + 1;//累计一个被关注用户粉丝数
		}
		//cache.hashSet(CommConstant.USER_RELATION_INFO + "fansCount", String.valueOf(followId), fans);
		redis.hashSet(ConfigConstant.FANS_COUNT, String.valueOf(followId), fans);
		GwsLogger.info("被关注用户粉丝数:followId={},fans={},State={}", followId, fans, State);
		//用户关注数(userId)
		//Integer follow = cache.hashGet(CommConstant.USER_RELATION_INFO + "followCount", String.valueOf(userId));
		Integer follow = redis.hashGet(ConfigConstant.FOLLOW_COUNT, String.valueOf(userId));
		if (null == follow) {
			List<Attentions> userFollow = queryUserByUserId(String.valueOf(userId));
			follow = CollectionUtils.isNotEmpty(userFollow) ? userFollow.size() : 0;
		}
		
		if (FollowStateEnum.UNFOLLOW.getVal().equals(State)) {
			follow = 0 < follow ? follow - 1 : 0;//用户取关,减去一个关注数
		} else {
			follow = follow + 1;//累计一个用户关注数
		}
		//cache.hashSet(CommConstant.USER_RELATION_INFO + "followCount", String.valueOf(userId), follow);
		redis.hashSet(ConfigConstant.FOLLOW_COUNT, String.valueOf(userId), follow);
		GwsLogger.info("用户关注数:userId={},follow={},State={}", userId, follow, State);
		Attentions attent = queryUserByUserIdAndFollowId(userId, followId);
		Short isAttention = isAttention(followId, userId, State);
		if (null != attent) {
			attent.setAttentionsState(State);
			attent.setIsAttention(isAttention);
			attent.setGmtModified(new Date());
			attent.setGmtModifiedUser(userId);
			return attentionsRepositoryMaster.save(attent);
		} else {
			// 用户关注表ID
			Long id = idGlobalGenerator.getSeqId(Attentions.class);
			Attentions a = new Attentions();
			a.setId(id + "");
			a.setUserId(userId);
			a.setAnchoruserId(followId);
			a.setAttentionsState(State);
			a.setIsAttention(isAttention);
			a.setGmtCreate(new Date());
			a.setGmtCreateUser(userId);
			return attentionsRepositoryMaster.save(a);
		}
	}
	
	/**
	 * @Title: isAttention 
	 * @Description: 相互关注 
	 * @param userId
	 * @param followId
	 * @param State
	 * @return Short
	 */
	public Short isAttention(String userId, String followId, Short State) {
		Attentions attent = queryUserByUserIdAndFollowId(userId, followId);
		if (null != attent) {
			attent.setIsAttention(State);
			attent.setGmtModified(new Date());
			attent.setGmtModifiedUser(followId);
			Attentions a = attentionsRepositoryMaster.save(attent);
			return a.getAttentionsState();
		} else {
			return FollowStateEnum.UNFOLLOW.getVal();
		}
	}
	
	/**
	 * @Title: queryFollow 
	 * @Description: 查询关注列表 
	 * @param userId
	 * @return AttentionsVO
	 */
	@Override
	public AttentionsVO queryFollow(String userId) {
		AttentionsVO fans = new AttentionsVO();
		List<Map<String, Object>> mapL = new ArrayList<Map<String, Object>>();
		fans.setUserId(userId);

//		AttentionsQueryFilter attentQuery = new AttentionsQueryFilter();
//		attentQuery.setUserId(userId);
//		attentQuery.setAttentionsState(FollowStateEnum.FOLLOW.getVal());
//		//字段排序
//		Sort attentSort = new Sort(Direction.DESC, "gmtCreate");
//		List<Attentions> aL = attentionsRepositorySlave.findAll(attentQuery, attentSort);
		List<Attentions> aL = queryAttentions(userId);
		
		if (CollectionUtils.isNotEmpty(aL)) {
			List<String> userIdList = new ArrayList<String>();
			UserQueryFilter userQuery = new UserQueryFilter();
			for (Attentions a : aL) {
				userIdList.add(a.getAnchoruserId());
			}
			userQuery.setUserIdList(userIdList);
			List<User> uL = userRepositorySlave.findAll(userQuery);
			
			for (Attentions a : aL) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (CollectionUtils.isNotEmpty(uL)) {
					for (User u : uL) {
						if (u.getUserId().equals(a.getAnchoruserId())) {
							map.put("userId", u.getUserId());
							map.put("userName", u.getUserName());
							map.put("userType", u.getUserType()+"");
							map.put("nickName", u.getNickName());
							map.put("sex", u.getSex());
							map.put("signed", u.getSigned());
							map.put("facePhoto", u.getPhoneId());
							map.put("grade", u.getGrade());
							map.put("city", u.getCity());
							map.put("isAttention", a.getIsAttention());
							map.put("attention", 1);
							
							LiveRoomInfo liveRoomInfo = liveRoomInfoRepositorySlave.findAnchorIsPlaying(Long.parseLong(u.getUserId()));
							
							map.put("liveInfo", liveRoomInfo);
							uL.remove(u);
							break;
						}
					}
				}
				mapL.add(map);
			}
		}
		fans.setObjList(mapL);
		return fans;
	}
	
	/**
	 * @Title: queryFollowLive 
	 * @Description: 查询关注列表(直播中) 
	 * @param userId
	 * @return AttentionsVO
	 */
	@Override
	public AttentionsVO queryFollowLive(String userId) {
		AttentionsVO fans = new AttentionsVO();
		List<Map<String, Object>> mapL = new ArrayList<Map<String, Object>>();
		fans.setUserId(userId);

//		AttentionsQueryFilter attentQuery = new AttentionsQueryFilter();
//		attentQuery.setUserId(userId);
//		attentQuery.setAttentionsState(FollowStateEnum.FOLLOW.getVal());
//		//字段排序
//		Sort attentSort = new Sort(Direction.DESC, "gmtCreate");
//		List<Attentions> aL = attentionsRepositorySlave.findAll(attentQuery, attentSort);
		List<Attentions> aL = queryAttentions(userId);
		
		if (CollectionUtils.isNotEmpty(aL)) {
			List<String> userIdList = new ArrayList<String>();
			UserQueryFilter userQuery = new UserQueryFilter();
			LiveQueryFilter liveQuery = new LiveQueryFilter();
			for (Attentions a : aL) {
				userIdList.add(a.getAnchoruserId());
			}
			userQuery.setUserIdList(userIdList);
			List<User> uL = userRepositorySlave.findAll(userQuery);
			
			Sort liveSort = new Sort(Direction.DESC, "gmtCreate");	//字段排序
			Pageable livePage = new PageRequest(0, 1, liveSort);	//分页
			
			for (Attentions a : aL) {
				liveQuery.setUserId(a.getAnchoruserId());
				Page<Live> pageL = liveRepositorySlave.findAll(liveQuery, livePage);
				Live l = 0 < pageL.getContent().size() ? pageL.getContent().get(0) : null;
				if (!userId.equals(l.getUserId()) && 0 == l.getStats()) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (CollectionUtils.isNotEmpty(uL)) {
						for (User u : uL) {
							if (u.getUserId().equals(a.getAnchoruserId())) {
								map.put("userId", u.getUserId());
								map.put("userName", u.getUserName());
								map.put("userType", u.getUserType()+"");
								map.put("nickName", u.getNickName());
								map.put("sex", u.getSex());
								map.put("signed", u.getSigned());
								map.put("facePhoto", u.getPhoneId());
								map.put("grade", u.getGrade());
								map.put("city", u.getCity());
								map.put("isAttention", a.getIsAttention());
								map.put("attention", 1);
								
								//Integer seeNums = cache.get(SEE_LIVE, l.getUserId());
								Integer seeNums = redis.get(ConfigConstant.SEE_LIVE, l.getUserId());
								l.setParticNum(null == seeNums ? l.getRobot() : seeNums);
								map.put("liveInfo", l);
								uL.remove(u);
								break;
							}
						}
					}
					mapL.add(map);
				}
			}
		}
		fans.setObjList(mapL);
		return fans;
	}
	
	/**
	 * @Title: queryAttentions 
	 * @Description: 查询用户关注（我所关注的人）
	 * @param userId
	 * @return List<Attentions>
	 */
	@Override
	public List<Attentions> queryAttentions(String userId) {
		AttentionsQueryFilter attentQuery = new AttentionsQueryFilter();
		attentQuery.setUserId(userId);
		attentQuery.setAttentionsState(FollowStateEnum.FOLLOW.getVal());
		//字段排序
		Sort attentSort = new Sort(Direction.DESC, "gmtCreate");
		List<Attentions> aL = attentionsRepositorySlave.findAll(attentQuery, attentSort);
		return aL;
	}
	
	/**
	 * @Title: queryAttentions 
	 * @Description: 查询关注信息By是否关注 
	 * @param userId
	 * @param attentionsState
	 * @return List<String>
	 */
	@Override
	public List<String> queryAttentions(String userId, Short attentionsState) {
		if (StringUtils.isNotBlank(userId) && null != attentionsState) {
			List<String> userIdL = attentionsRepositorySlave.queryAttentions(userId, attentionsState);
			return userIdL;
		}
		return null;
	}
	
	/**
	 * @Title: queryFans 
	 * @Description: 查询粉丝列表 
	 * @param userId
	 * @return AttentionsVO
	 */
	@Override
	public AttentionsVO queryFans(String userId) {
		AttentionsVO fans = new AttentionsVO();
		List<Map<String, Object>> mapL = new ArrayList<Map<String, Object>>();
		fans.setUserId(userId);

		AttentionsQueryFilter attentQuery = new AttentionsQueryFilter();
		attentQuery.setAnchoruserId(userId);
		attentQuery.setAttentionsState(FollowStateEnum.FOLLOW.getVal());
		//字段排序
		Sort attentSort = new Sort(Direction.DESC, "gmtCreate");
		List<Attentions> aL = attentionsRepositorySlave.findAll(attentQuery, attentSort);
		
		
		if (CollectionUtils.isNotEmpty(aL)) {
			List<String> userIdList = new ArrayList<String>();
			UserQueryFilter userQuery = new UserQueryFilter();
			for (Attentions a : aL) {
				userIdList.add(a.getUserId());
			}
			userQuery.setUserIdList(userIdList);
			List<User> uL = userRepositorySlave.findAll(userQuery);
			
			for (Attentions a : aL) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (CollectionUtils.isNotEmpty(uL)) {
					for (User u : uL) {
						if (u.getUserId().equals(a.getUserId())) {
							map.put("userId", u.getUserId());
							map.put("userName", u.getUserName());
							map.put("userType", u.getUserType()+"");
							map.put("nickName", u.getNickName());
							map.put("sex", u.getSex());
							map.put("signed", u.getSigned());
							map.put("facePhoto", u.getPhoneId());
							map.put("grade", u.getGrade());
							map.put("city", u.getCity());
							map.put("isAttention", a.getIsAttention());
							map.put("attention", 1);
							
							LiveRoomInfo liveRoomInfo = liveRoomInfoRepositorySlave.findAnchorIsPlaying(Long.parseLong(u.getUserId()));

							map.put("liveInfo", liveRoomInfo);
							uL.remove(u);
							break;
						}
					}
				}
				mapL.add(map);
			}
		}
		fans.setObjList(mapL);
		return fans;
	}
	
	/**
	 * @Title: queryUserByUserIdAndFollowId 
	 * @Description: TODO 
	 * @param userId
	 * @param followId
	 * @return Attentions
	 */
	@Override
	public Attentions queryUserByUserIdAndFollowId(String userId, String followId) {
		Attentions attentions = attentionsRepositorySlave.queryUserByUserIdAndFollowId(userId, followId);
		return attentions;
	}

	/**
	 * 
	 * 【根据ID查询对象（我所关注的人）】
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.service.AttentionsService#queryUserByUserId(java.lang.String)
	 */
	@Override
	public List<Attentions> queryUserByUserId(String userId) {
		List<Attentions> attentionsList = attentionsRepositorySlave.queryUserByUserId(userId);
		return attentionsList;
	}

	/**
	 * 
	 * 【根据ID查询对象（关注我的人）】
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.service.AttentionsService#queryUserByAnchorUserId(java.lang.String)
	 */
	@Override
	public List<Attentions> queryUserByAnchorUserId(String anchorUserId) {
		List<Attentions> attentionsList = attentionsRepositorySlave.queryUserByAnchorUserId(anchorUserId);
		return attentionsList;
	}

}
