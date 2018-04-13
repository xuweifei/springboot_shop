/**
 * @Title: AnchorServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AllCount;
import com.opengroup.longmao.gwcommon.entity.po.AnchorGradeRule;
import com.opengroup.longmao.gwcommon.entity.po.BrokerConfraternity;
import com.opengroup.longmao.gwcommon.entity.po.GiftGiveCount;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;
import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.vo.AttentionsVO;
import com.opengroup.longmao.gwcommon.entity.vo.CalorieTopVO;
import com.opengroup.longmao.gwcommon.entity.vo.UserCalCntVO;
import com.opengroup.longmao.gwcommon.enums.AllCountTypeEnum;
import com.opengroup.longmao.gwcommon.enums.FollowStateEnum;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.LivePlayStatusEnum;
import com.opengroup.longmao.gwcommon.enums.UserTypeEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.GiftGiveCountQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.AnchorGradeRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.BrokerConfraternityRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.GiftGiveCountRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.IdentityInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.LiveRoomInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AnchorService;
import com.opengroup.longmao.gwcommon.service.AttentionsService;
import com.opengroup.longmao.gwcommon.service.LiveRoomInfoService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;

/**
 * @ClassName: AnchorServiceImpl 
 * @Description: 主播管理ServiceImpl
 * @author Mr.Zhu
 */
@Service
public class AnchorServiceImpl implements AnchorService {
	
	@Autowired
	private GiftGiveCountRepositorySlave giftGiveCountRepositorySlave;
	
	@Autowired
	private LiveRoomInfoRepositorySlave liveRoomInfoRepositorySlave;
	
	@Autowired
	private IdentityInfoRepositorySlave identityInfoRepositorySlave;
	
	@Autowired
	private BrokerConfraternityRepositorySlave brokerConfraternityRepositorySlave;
	
	@Autowired
	private LiveRoomInfoService liveRoomInfoService;
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private AttentionsService attentionsService;
	
	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private AnchorGradeRuleRepositorySlave anchorGradeRuleRepositorySlave;
	
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
	@Override
	public AttentionsVO queryAnchor(AttentionsVO follow, String userId, String condition, Integer pageNo,
			Integer pageSize, Short isPage) {
		Map<String, Map<String, Object>> aM = queryAnchor();//获取主播信息
		if (null == aM || aM.isEmpty()) {//无主播信息
			return follow;
		}
		List<Map<String, Object>> mapL = new ArrayList<Map<String, Object>>();
		Map<String, Short> fM = queryFollow(userId, FollowStateEnum.FOLLOW.getVal());
		List<Map<String, Object>> mapOneL = queryRanking(3, aM, fM);//获取明星主播榜
		follow.setUserId(userId);
		follow.setContent(condition);
		Integer totalPage = 1;
		if (IsOrNotEnum.YES.getType().equals(isPage) || IsOrNotEnum.NO.getType().equals(isPage)) {
			for (String m : aM.keySet()) {
				Map<String, Object> a = aM.get(m);
				if (String.valueOf(a.get("nickName")).indexOf(condition) != -1
						|| String.valueOf(a.get("userId")).equals(condition)) {
					Map<String, Object> b = new HashMap<String, Object>();
					b.putAll(a);
					b.put("follow", FollowStateEnum.UNFOLLOW.getVal());
					b.put("live", queryLive(String.valueOf(b.get("userId"))));
					if (null != fM && fM.containsKey(b.get("userId"))) {
						b.put("follow", FollowStateEnum.FOLLOW.getVal());
					}
					mapL.add(b);
				}
			}
			
			Integer totalCount = 0 < mapL.size() ? mapL.size() : 1;
			totalPage = (totalCount + pageSize -1) / pageSize;//搜索数据总页数
			if (IsOrNotEnum.YES.getType().equals(isPage)) { //是否分页
				int startnum = ((pageNo - 1) * pageSize) >= mapL.size() ? mapL.size() : ((pageNo - 1) * pageSize);
				int endnum = (pageNo * pageSize) >= mapL.size() ? mapL.size() : (pageNo * pageSize);
				mapL = mapL.subList(startnum, endnum);
			}
		}
		follow.setTotalPage(totalPage);
		follow.setObjList(mapL);
		follow.setObjListOne(mapOneL);
		return follow;
	}
	
	/**
	 * @Title: queryRanking 
	 * @Description: 获取主播明星榜 
	 * @param listSum
	 * @param aM
	 * @param fM
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryRanking(int listSum, Map<String, Map<String, Object>> aM,
			Map<String, Short> fM) {
		List<AllCount> AllCountList = redis.getList(ConfigConstant.ALL_COUNT_RANKING,
				String.valueOf(AllCountTypeEnum.COUNT_CALORIE_RECEIVER.getVal()));
		List<Map<String, Object>> rankL = new ArrayList<Map<String, Object>>();
		if (CollectionUtils.isNotEmpty(AllCountList)) {
			Collections.sort(AllCountList,new Comparator<AllCount>(){
	            public int compare(AllCount arg0, AllCount arg1) {
	                return arg0.getSortNum().compareTo(arg1.getSortNum());
	            }
	        });
			int i = 0;
			for (AllCount count : AllCountList) {
				if (listSum <= i) {
					break;
				}
				String userId = String.valueOf(count.getAnchorId());
				if (aM.containsKey(userId)) {
					Map<String, Object> a = aM.get(userId);
					Map<String, Object> r = new HashMap<String, Object>();
					r.putAll(a);
					r.put("follow", FollowStateEnum.UNFOLLOW.getVal());
					r.put("live", queryLive(userId));
					if (null != fM && fM.containsKey(userId)) {
						r.put("follow", FollowStateEnum.FOLLOW.getVal());
					}
					rankL.add(r);
					i++;
				}
			}
			return rankL;
		}
		return null;
	}
	
	/**
	 * @Title: queryAnchor 
	 * @Description: 搜索主播信息 
	 * @return Map<String,Map<String,Object>>
	 */
	private Map<String, Map<String, Object>> queryAnchor() {
		Map<String, Map<String, Object>> aM = redis.hashGet(ConfigConstant.FUZZY_QUERY);
		if (null == aM || aM.isEmpty()) {
			List<User> uL = userRepositorySlave.queryAnchorByUserType(UserTypeEnum.ANCHOR.getVal(),
					IsDeleteEnum.UN_DELETE.getVal());
			if (CollectionUtils.isNotEmpty(uL)) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (User u : uL) {
					map.put("userId", u.getUserId());
					map.put("nickName", u.getNickName());
					map.put("phoneId", u.getPhoneId());
					map.put("grade", u.getGrade());
					map.put("signed", u.getSigned());
					redis.hashSet(ConfigConstant.FUZZY_QUERY, u.getUserId(), map);
				}
			}
			aM = redis.hashGet(ConfigConstant.FUZZY_QUERY);
		}
		Map<String, Map<String, Object>> sortMap = new TreeMap<String, Map<String, Object>>(
				new MapKeyComparator());
        sortMap.putAll(aM);
		return sortMap;
	}
	
	/**
	 * @Title: queryFollow 
	 * @Description: 根据用户和关注状态查询被关注ID 
	 * @param userId
	 * @param followStatus
	 * @return Map<String,Short>
	 */
	public Map<String, Short> queryFollow(String userId, Short followStatus) {
		if (StringUtils.isNotBlank(userId)) {
			List<String> userIdL = attentionsService.queryAttentions(userId, followStatus);
			if (CollectionUtils.isNotEmpty(userIdL)) {
				Map<String, Short> map = new HashMap<String, Short>();
				for (String anchorUserId : userIdL) {
					map.put(anchorUserId, followStatus);
				}
				return map;
			}
		}
		return null;
	}
	
	/**
	 * @Title: queryLive 
	 * @Description: 根据用户ID获取主播状态 
	 * @param userId
	 * @return Short
	 */
	public Short queryLive(String userId) {
		String play = redis.get(ConfigConstant.LIVE_STATUS, userId);
		if (StringUtils.isBlank(play)) {
			return LivePlayStatusEnum.UN_PLAY.getType();
		}
		return LivePlayStatusEnum.ING_PLAY.getType();
	}
	
	/**
	 * @Title: queryLive 
	 * @Description: 根据直播状态获取主播ID 
	 * @param playStatus
	 * @return Map<String,Short>
	 */
	public Map<String, Short> queryLive(Short playStatus) {
		List<Long> userIdL = liveRoomInfoService.queryLiveS(playStatus);
		if (CollectionUtils.isNotEmpty(userIdL)) {
			Map<String, Short> map = new HashMap<String, Short>();
			for (Long userId : userIdL) {
				map.put(String.valueOf(userId), playStatus);
			}
			return map;
		}
		return null;
	}
	
	/**
	 * @Title: queryUser 
	 * @Description: 根据条件模糊查询用户信息 
	 * @param condition
	 * @return List<User>
	 */
	public List<User> queryUser(String condition, Integer userType) {
		if (StringUtils.isNotBlank(condition)) {
			List<User> uL = userRepositorySlave.queryAnchorByUserIdOrNickName(condition, userType,
					IsDeleteEnum.UN_DELETE.getVal());
			return uL;
		}
		return null;
	}
	
	/**
	 * @Title: defendRanking 
	 * @Description: 主播守护排行榜 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return UserCalCntVO
	 */
	@Override
	public UserCalCntVO defendRanking(String userId, Integer pageNo, Integer pageSize) {
		//主播守护排行榜信息
		UserCalCntVO anchor = new UserCalCntVO();
		User u = queryUserByUserId(userId);
		if (null != u) {
			Long totClr = getGiftCalorieByReceiver(Long.valueOf(userId));
			anchor.setUserId(userId);
			anchor.setNickName(u.getNickName());
			anchor.setFacePhoto(u.getPhoneId());
			anchor.setTotClr(totClr);
			
			List<CalorieTopVO> calorieL = new ArrayList<CalorieTopVO>();
			Page<GiftGiveCount> giftL = getGiftRecordByReceiver(Long.valueOf(userId), pageNo, pageSize);
			CalorieTopVO top = null;
			int i = 1;
			for (GiftGiveCount g : giftL.getContent()) {
				//查询对应的榜单用户信息
				User user = queryUserByUserId(String.valueOf(g.getSender()));
				if (null != user) {
					top = new CalorieTopVO();
					top.setUserId(user.getUserId());
					top.setNickName(user.getNickName());
					top.setFacePhoto(user.getPhoneId());
					top.setSex(user.getSex());
					top.setGrade(user.getGrade());
					top.setSumClr(g.getCalorie().longValue());
					top.setSort(i);
					i++;
					calorieL.add(top);
				}
			}
			anchor.setCalorieTop(calorieL);
		}
		return anchor;
	}
	
	/**
	 * 【根据id查询用户信息表】
	 * @param id
	 * @return uiUser
	 * @version 1.0
	 * @author Hermit 2017年03月15日 上午09:59:49
	 */
	public User queryUserByUserId(String userId) {
		return userRepositorySlave.queryUserByUserId(userId);
	}
	
	/**
	 * @Title: getGiftCalorieByReceiver 
	 * @Description: 主播卡路里总计 
	 * @param userId
	 * @return Long
	 */
	@Override
	public Long getGiftCalorieByReceiver(Long userId) {
		try {
			Long totClr = giftGiveCountRepositorySlave.getGiftCalorieByReceiver(userId);
			return null == totClr ? 0L : totClr.longValue();
		} catch (Exception e) {
			GwsLogger.error("查询主播卡路里异常:userId={},e={}", userId, e);
			return 0L;
		}
	}
	
	public Page<GiftGiveCount> getGiftRecordByReceiver(Long userId, Integer pageNo, Integer pageSize) {
		try {
			GiftGiveCountQueryFilter query = new GiftGiveCountQueryFilter();
			query.setReceiver(userId);
			query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			//字段排序
			Sort sort = new Sort(Direction.DESC, "calorie");
			// 分页
			Pageable page = new PageRequest(0 > pageNo ? 0 : pageNo, pageSize, sort);
			//增加分页Page查询
			Page<GiftGiveCount> pageList = giftGiveCountRepositorySlave.findAll(query, page);
			return pageList;
		} catch (Exception e) {
			GwsLogger.error("查询主播卡路里异常:userId={},e={}", userId, e);
			return null;
		}
	}
	
	/**
	 * @Title: findRoomInfoByUserId 
	 * @Description: 查找直播信息 
	 * @param userId
	 * @return LiveRoomInfo
	 */
	@Override
	public LiveRoomInfo findRoomInfoByUserId(Long userId) {
		LiveRoomInfo liveRoomInfo = liveRoomInfoRepositorySlave.findRoomInfoByUserId(userId);
		return liveRoomInfo;
	}
	
	
	/**
	 * 
	 *【查询 主播等级；所属公会；直播版块 信息】  
	 * @param userId
	 * @return 返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月14日下午3:22:38
	 * @throws 异常
	 */
	@Override
	public Map<String, Object> findAnchorInfo(String userId) {

		Map<String,Object> anchorInfoMap = new HashMap<String,Object>();
		
		IdentityInfo identityInfo = identityInfoRepositorySlave.queryIdentityByUserId(userId);
		if(null != identityInfo){
			//从redis中获取版块配置信息
			Map<String, Object> forumConfig = redis.hashGet(ConfigConstant.LIVE_FORUM,String.valueOf(identityInfo.getLiveForum()));
			String forumName = forumConfig == null ? "" : (String)forumConfig.get("forumName");
			//获取主播所属公会
			BrokerConfraternity bc = brokerConfraternityRepositorySlave.findBrokerConById(identityInfo.getConfraternityId());
			String confraName = bc == null ? "无" : bc.getConfraternityName();
			//获取主播等级，先判断主播等级
			Long exper = identityInfo.getExperience();
			List<AnchorGradeRule> agrList = anchorGradeRuleRepositorySlave.findAnchorGradeRuleList(exper, IsDeleteEnum.UN_DELETE.getVal());
			Short anchorGrade = null;
			if(CollectionUtils.isNotEmpty(agrList) && 1 <= agrList.size()){
				AnchorGradeRule agr = agrList.get(0);
				anchorGrade = agr.getGrade();
			}
			String grade = anchorGrade == null ? "" : anchorGrade.toString();
			anchorInfoMap.put("anchorGrade","Lv."+grade);
			anchorInfoMap.put("liveForum", forumName); 
			anchorInfoMap.put("confraternity",confraName);
			return anchorInfoMap;
		}
		
		GwsLogger.error("查询 主播等级；所属公会；直播版块 为空:userId={},主播认证实体:identityInfo={}", userId,JSON.toJSONString(identityInfo));
		return null;
	}
	
}
