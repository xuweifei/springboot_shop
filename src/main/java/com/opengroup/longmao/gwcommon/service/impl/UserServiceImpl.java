package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.opengroup.longmao.gwcommon.configuration.elasticsearch.service.ESService;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.TxUserConfig;
import com.opengroup.longmao.gwcommon.entity.dto.ESUserDTO;
import com.opengroup.longmao.gwcommon.entity.po.Attentions;
import com.opengroup.longmao.gwcommon.entity.po.LiveMemberSit;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsNormalEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.UserTypeEnum;
import com.opengroup.longmao.gwcommon.enums.LoginWayEnum;
import com.opengroup.longmao.gwcommon.repository.master.UserRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.LiveMemberSitQueryFilter;
import com.opengroup.longmao.gwcommon.repository.queryFilter.UserQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.LiveMemberSitRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AttentionsService;
import com.opengroup.longmao.gwcommon.service.PictureService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.UserService;
import com.opengroup.longmao.gwcommon.tools.EntityDtoConverter;
import com.opengroup.longmao.gwcommon.tools.http.okhttp.OkhttpRequestApi;
import com.opengroup.longmao.gwcommon.tools.http.okhttp.OkhttpRequestApi.Param;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;

import io.searchbox.core.Index;

/**
 * 【用户信息表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private LiveMemberSitRepositorySlave liveMemberSitRepositorySlave;

	@Autowired
	private UserRepositoryMaster userRepositoryMaster;
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private AttentionsService attentionsService;
	
	@Autowired
	private PictureService pictureService;
	
	@Autowired
	private RedisReadWriteService redis;

	@Autowired
	private ESService<ESUserDTO> eSService;
	
	@Autowired
	private TxUserConfig txConfig;
	
	/**
	 * 【（根据用户类型）查询用户信息表并分页】
	 */
	@Override
	public Page<User> findAllUser(User user, Integer pageNo, Integer pageSize, String sortField) {
		// 组合查询语句
		UserQueryFilter query = new UserQueryFilter();
		query.setIsDelete(IsNormalEnum.YES.getVal());
		if(user != null){
			if(StringUtils.isNotBlank(user.getUserType().toString())){
				query.setUserType(user.getUserType());
			}
			if(StringUtils.isNotBlank(user.getCity())){
				query.setCity(user.getCity());
			}
			if(StringUtils.isNotBlank(user.getSex().toString())){
				query.setSex(user.getSex());
			}
		}
		//字段排序
		Sort sort = new Sort(Direction.DESC, sortField);
		// 分页
		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		Page<User> pageList = userRepositorySlave.findAll(query, page);
		return pageList;
	}
	
	/**
	 * @Title: editUserData
	 * @Description: 编辑用户资料
	 * @param param
	 * @return Boolean
	 */
	@Override
	public Boolean editUserData(Map<String, String> param) {
		User u = queryUserByUserId(param.get("userId"));
		if (null != u) {
			GwsLogger.info("修改用户资料:userId={},phoneId={},nickName={},oldPhoneId={},oldNickName={}", u.getUserId(),
					param.get("phoneId"), param.get("nickName"), u.getPhoneId(), u.getNickName());
            String phoneId = u.getPhoneId();
			u.setPhoneId(StringUtils.isBlank(param.get("phoneId")) ? phoneId : param.get("phoneId"));
			u.setNickName(StringUtils.isBlank(param.get("nickName")) ? u.getNickName() : param.get("nickName"));
			u.setSex(StringUtils.isBlank(param.get("sex")) ? u.getSex() : Short.valueOf(param.get("sex")));
			u.setCity(StringUtils.isBlank(param.get("city")) ? u.getCity() : param.get("city"));
			u.setSigned(null == param.get("signed") ? u.getSigned() : param.get("signed"));
			u.setGmtModified(new Date());
			User user = userRepositoryMaster.save(u);
			if (null != user) {
				GwsLogger.info("修改用户资料成功：userId={}",u.getUserId());
				List<String> keyList = new ArrayList<String>();
				if (UserTypeEnum.ANCHOR.getVal().equals(user.getUserType())) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", user.getUserId());
					map.put("nickName", user.getNickName());
					map.put("phoneId", user.getPhoneId());
					map.put("grade", user.getGrade());
					map.put("signed", user.getSigned());
					redis.hashSet(ConfigConstant.FUZZY_QUERY, user.getUserId(), map);
					GwsLogger.info("rides修改用户资料成功：userId={}",user.getUserId());
				}
				//判断图片是否新上传
				if (StringUtils.isNotBlank(user.getPhoneId()) && StringUtils.isNotBlank(phoneId)
						&& !phoneId.equals(user.getPhoneId())) {
					keyList.add(phoneId);
				}
				
				//by szy 2018.4.10  更新wap服务中客户扩展表数据
				//updateCustomerExpand(user);
				
				Index index = null;
				try {
					index = eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
					GwsLogger.info("ES修改用户资料成功：user={},u={}",JSON.toJSONString(user),JSON.toJSONString(u));
				} catch (Exception e) {
					GwsLogger.error("ES修改用户资料异常：user={},u={},e={}",JSON.toJSONString(user),JSON.toJSONString(u),e.getMessage());
					return false;
				}
				if(null != index){
					GwsLogger.info("ES搜索引擎更新成功：userId={}",u.getUserId());
				}
				try {
					pictureService.deleteToQiniu(keyList);//删除旧有头像图片
				} catch (Exception e) {
					GwsLogger.error("删除旧有头像图片异常：user={},u={},keyList={},e={}",JSON.toJSONString(user),JSON.toJSONString(u),JSON.toJSONString(keyList),e.getMessage());
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	//by szy 2018.4.10  更新wap服务中客户扩展表数据
//	private void updateCustomerExpand(User user) {
//		Param[] params = new Param[] { new Param("userId", user.getUserId()),
//				new Param("sex", user.getSex().toString()), 
//				new Param("nickName", user.getNickName()),
//				new Param("phoneId", user.getPhoneId())};
//		GwsLogger.info("修改客户扩展表userId={},sex={},nickName={},phoneId={}",user.getUserId(),user.getNickName(),user.getPhoneId());
//		Map<String, Object> data = OkhttpRequestApi.httppost(txConfig.getMallCustomerExpand().trim(), params);
//		if (CommConstant.GWSCOD0000.equals(data.get("code")) && CommConstant.GWSMSG0000.equals(data.get("message"))) {
//			GwsLogger.info("修改客户扩展表信息成功!");
//		} else {
//			GwsLogger.error("修改客户扩展表信息失败");
//		}
//	}

	
	@Override
	public Map<String, Object> relation(Long userId, Long groupId, Long anchorId, Long manageId) {
		//管理ID为主播ID即主播查看userId权限
		if (anchorId.equals(manageId)) {
			manageId = userId;
		}
		
		Map<String, Object> uMap = new HashMap<String, Object>();
		//查询用户个人信息
		User u = queryUserByUserId(String.valueOf(userId));
		if (null != u) {
			
			uMap.put("userId", userId);
			uMap.put("userType", u.getUserType());
			uMap.put("nickName", u.getNickName());
			uMap.put("phoneId", u.getPhoneId());
			uMap.put("sex", u.getSex());
			uMap.put("grade", u.getGrade());
			uMap.put("signed", u.getSigned());
			uMap.put("calorie", u.getCalorie());
			//关注用户的人(粉丝)
			//Integer fans = cache.hashGet(ConfigConstant.USER_RELATION_INFO + "fansCount", String.valueOf(userId));
			Integer fans = redis.hashGet(ConfigConstant.FANS_COUNT, String.valueOf(userId));
			if (null == fans) {
				List<Attentions> userFans = attentionsService.queryUserByAnchorUserId(String.valueOf(userId));
				fans = CollectionUtils.isNotEmpty(userFans) ? userFans.size() : 0;
			}
			//用户关注的人(关注)
			//Integer follow = cache.hashGet(CommConstant.USER_RELATION_INFO + "followCount", String.valueOf(userId));
			Integer follow = redis.hashGet(ConfigConstant.FOLLOW_COUNT, String.valueOf(userId));
			if (null == follow) {
				List<Attentions> userFollow = attentionsService.queryUserByUserId(String.valueOf(userId));
				follow = CollectionUtils.isNotEmpty(userFollow) ? userFollow.size() : 0;
			}
			//是否关注主播
			Attentions followMe = attentionsService.queryUserByUserIdAndFollowId(String.valueOf(userId), String.valueOf(anchorId));
			//判断是否场控
			//Map<Long, Short> liveAdmin = cache.hashGet(CommConstant.USER_RELATION_INFO + "isLiveAdmin", String.valueOf(groupId));
			Map<Long, Short> liveAdmin = redis.hashGet(ConfigConstant.IS_LIVE_ADMIN, String.valueOf(groupId));
			//判断是否禁言
			//Map<Long, Short> mapForbid = cache.hashGet(CommConstant.USER_RELATION_INFO + "isForbid", String.valueOf(groupId));
			Map<Long, Short> mapForbid = redis.hashGet(ConfigConstant.IS_FORBID, String.valueOf(groupId));
			
			//Short isOverallForbid = cache.hashGet(CommConstant.USER_RELATION_INFO + "isOverallForbid", String.valueOf(userId));
			//Short isAppAdmin = cache.hashGet(CommConstant.USER_RELATION_INFO + "isAppAdmin", String.valueOf(manageId));
			Short isOverallForbid = redis.hashGet(ConfigConstant.IS_OVERALL_FORBID, String.valueOf(userId));
			Short isAppAdmin = redis.hashGet(ConfigConstant.IS_APP_ADMIN, String.valueOf(manageId));
			Short isLiveAdmin = (null != liveAdmin && liveAdmin.containsKey(manageId)) ? IsOrNotEnum.YES.getType()
					: IsOrNotEnum.NO.getType();
			
			uMap.put("fans", fans);//用户粉丝数(userId)
			uMap.put("follow", follow);//用户关注数(userId)
			uMap.put("isFollowMe", null == followMe ? IsOrNotEnum.NO.getType() : followMe.getAttentionsState());//是否关注
			uMap.put("isForbid", null != mapForbid && mapForbid.containsKey(userId) ? mapForbid.get(userId)  : IsOrNotEnum.NO.getType());//是否群组禁言(userId)
			uMap.put("isOverallForbid", null == isOverallForbid ? IsOrNotEnum.NO.getType() : isOverallForbid);//是否全局禁言
			uMap.put("isLiveAdmin", isLiveAdmin);//是否场控(manageId)
			uMap.put("isAppAdmin", null == isAppAdmin ? IsOrNotEnum.NO.getType() : isAppAdmin);//是否超管(manageId)
		}
		
		Map<String, Object> aMap = new HashMap<String, Object>();
		//查询主播个人信息
		User anchor = queryUserByUserId(String.valueOf(anchorId));
		if (null != anchor) {
			aMap.put("userId", anchorId);
			aMap.put("userType", anchor.getUserType());
			aMap.put("nickName", anchor.getNickName());
			aMap.put("phoneId", anchor.getPhoneId());
			aMap.put("sex", anchor.getSex());
			aMap.put("grade", anchor.getGrade());
			aMap.put("signed", anchor.getSigned());
			aMap.put("calorie", anchor.getCalorie());
			//关注主播的人(粉丝)
			//Integer fans = cache.hashGet(CommConstant.USER_RELATION_INFO + "fansCount", String.valueOf(anchorId));
			Integer fans = redis.hashGet(ConfigConstant.FANS_COUNT, String.valueOf(anchorId));
			if (null == fans) {
				List<Attentions> anchorFans = attentionsService.queryUserByAnchorUserId(String.valueOf(anchorId));
				fans = CollectionUtils.isNotEmpty(anchorFans) ? anchorFans.size() : 0;
			}
			//主播关注的人(关注)
			//Integer follow = cache.hashGet(CommConstant.USER_RELATION_INFO + "followCount", String.valueOf(anchorId));
			Integer follow = redis.hashGet(ConfigConstant.FOLLOW_COUNT, String.valueOf(anchorId));
			if (null == follow) {
				List<Attentions> anchorFollow = attentionsService.queryUserByUserId(String.valueOf(anchorId));
				follow = CollectionUtils.isNotEmpty(anchorFollow) ? anchorFollow.size() : 0;
			}
			//是否关注用户
			Attentions followHim = attentionsService.queryUserByUserIdAndFollowId(String.valueOf(anchorId), String.valueOf(userId));
			
			uMap.put("fans", fans);//主播粉丝数(anchorId)
			uMap.put("follow", follow);//主播关注数(anchorId)
			aMap.put("isFollowHim", null == followHim ? IsOrNotEnum.NO.getType() : followHim.getAttentionsState());//是否关注
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userInfo", uMap);
		map.put("anchorInfo", aMap);
		return map;
	}
	
	public LiveMemberSit findForbidMember(Long groupId, Long userId, Long liveUserId) {
		LiveMemberSitQueryFilter query = new LiveMemberSitQueryFilter();
		query.setGroupId(groupId);
		query.setUserId(userId);
		query.setLiveUserId(liveUserId);
		query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		LiveMemberSit sit = liveMemberSitRepositorySlave.findOne(query);
		return sit;
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
	 * @Title: queryUserByNickName 
	 * @Description: 根据nickName查询用户信息表 
	 * @param nickName
	 * @return User
	 */
	@Override
	public List<User> queryUserByNickName(String nickName) {
		if (StringUtils.isBlank(nickName)) {
			return null;
		}
		return userRepositorySlave.queryUserByNickName(nickName);
	}

	/**
	 * 【根据手机号码查询用户】
	 * @author xwf 2018年1月12日
	 * @param phoneNum
	 * @return
	 */
	@Override
	public User queryUserByMobile(String userName) {
		return userRepositorySlave.queryUserByUserName(userName);
	}

	/**
	 * 根据三方登录账号查询用户
	 * @author xwf 2018年1月23日
	 * @param loginId
	 * @param way
	 * @param pushId
	 */
	@Override
	public User getUserByLoginId(String loginId, Short way, String pushId) {
		User user = null;
		if (LoginWayEnum.MOB_WECHAT_WARRANTY.getVal().equals(way)) {
			user = userRepositorySlave.queryUserByWechatId(loginId);
		}
		else if (LoginWayEnum.MOB_QQ_WARRANTY.getVal().equals(way)) {
			user = userRepositorySlave.queryUserByQQId(loginId);
		}
		else if (LoginWayEnum.MOB_WEIBO_WARRANTY.getVal().equals(way)) {
			user = userRepositorySlave.queryUserByWeiboId(loginId);
		}
//		else if (LoginWayEnum.MOBILE.getVal().equals(way)) {
//			user = userRepositorySlave.queryUserByUserName(loginId);
//		}
		return user;
	}
	
	
	
}
