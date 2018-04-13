package com.opengroup.longmao.gwcommon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.elasticsearch.service.ESService;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.dto.ESUserDTO;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.entity.po.GoodNumber;
import com.opengroup.longmao.gwcommon.entity.po.PhotoAlbum;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserChannel;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.LoginWayEnum;
import com.opengroup.longmao.gwcommon.enums.PhotoTypeEnum;
import com.opengroup.longmao.gwcommon.enums.UserInfoEnum;
import com.opengroup.longmao.gwcommon.repository.master.BeanAccountRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.PhotoAlbumRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserChannelRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.GoodNumberRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.RegisterService;
import com.opengroup.longmao.gwcommon.tools.EntityDtoConverter;
import com.opengroup.longmao.gwcommon.tools.Md5Util;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.sig.TlsSigUtils;


/**
 * 用户注册 Service接口实现
 * @version 1.0
 * @author Hermit 2018年1月23日
 */ 
@Service
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	private BeanAccountRepositoryMaster beanAccountRepositoryMaster;
	
	@Autowired
	private UserChannelRepositoryMaster userChannelRepositoryMaster;
	
	@Autowired
	private UserRepositoryMaster userRepositoryMaster;
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private GoodNumberRepositorySlave goodNumberRepositorySlave;
	
	@Autowired
	private PhotoAlbumRepositoryMaster photoAlbumRepositoryMaster;
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private TlsSigUtils tlsSigUtils;
	
	@Autowired
	private ESService<ESUserDTO> eSService;
	
	/**
	 * 微信/QQ/微博三方授权注册
	 * @author Hermit 2018年1月23日
	 */
	@Override
	public Map<String, Object> thirdPartyReg(String loginId, String nickName, String userHead, Short way, String pushId,
			String chlId, String thirdPartName) {
		User u = new User();
		u.setPushId(pushId);
		u.setPhoneId(userHead);
		if (LoginWayEnum.MOB_WECHAT_WARRANTY.getVal() == way) {// MOB-微信授权登录
			u.setWechatId(loginId);
			u.setWechatName(thirdPartName);
		} else if (LoginWayEnum.MOB_QQ_WARRANTY.getVal() == way) {// MOB-QQ授权登录
			u.setQqId(loginId);;
			u.setQqName(thirdPartName);
		} else if (LoginWayEnum.MOB_WEIBO_WARRANTY.getVal() == way) {// MOB-微博授权登录
			u.setWeiboId(loginId);
			u.setWeiboName(thirdPartName);
		}
		
		// 第三方账户注册
		User user = register(u, loginId, null, nickName);
		
		// 保存用户渠道信息
		UserChannel channel = createUserChannel(user, chlId, way);
		if(null == channel) {
			GwsLogger.error("创建用户渠道信息失败,userId={}", user.getUserId());
		}
		
		// 第三方账户保存默认头像到相册(用于直播封面)
		savePhoto(user);
		
		return setUserMap(user, pushId);
	}
	
	/**
	 * 用户注册时创建渠道信息
	 */
	private UserChannel createUserChannel(User user, String chlId, Short way) {
		UserChannel userChannel = new UserChannel();
		int currentSecond = DateUtil.currentSecond();
		userChannel.setUserId(Long.valueOf(user.getUserId()));
		userChannel.setChannelId(Long.valueOf(chlId));
		userChannel.setCreateDate(new Date());
		userChannel.setLoginWay(way);
		userChannel.setResetNum(0);
		userChannel.setRemark("第三方注册");
		userChannel.setCtime(currentSecond);
		userChannel.setUtime(currentSecond);
		userChannel.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		
		return userChannelRepositoryMaster.save(userChannel);
	}

	/**
	 * @Title: setUserMap 
	 */
	public Map<String, Object> setUserMap(User u, String pushId) {
		if (null != u) {
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", u.getUserId());
			userMap.put("nickName", null == u.getNickName() ? "" : u.getNickName());
			userMap.put("phoneId", u.getPhoneId());
//			if (StringUtils.isBlank(u.getPushId()) && StringUtils.isNotBlank(pushId)) {
//				u.setPushId(pushId);
//				userRepositoryMaster.save(u);
//			}
			return userMap;
		}
		return null;
	}
	
	/**
	 * @Title: savePhoto 
	 * @Description: 保存相册 
	 * @param user
	 * @return void
	 */
	public void savePhoto(User user) {
		try {
			if (null != user && StringUtils.isNotBlank(user.getUserId())) {
				// 三方登录保存默认头像到相册(用于直播封面)
				Long id = idGlobalGenerator.getSeqId(PhotoAlbum.class);
				PhotoAlbum photo = new PhotoAlbum();
				photo.setId(id + "");
				photo.setUserId(user.getUserId());
				photo.setPhotoName(user.getPhoneId());
				photo.setPhotoType(PhotoTypeEnum.PHOTO_HEAD.getType());
				photo.setPhotoUrl(user.getPhoneId());
				photo.setGmtCreate(new Date());
				photo.setGmtCreateUser("REG");
				photo.setGmtModified(new Date());
				photo.setGmtModifiedUser("REG");
				photoAlbumRepositoryMaster.save(photo);
			}
		} catch (Exception e) {
			GwsLogger.error("保存相册异常:User={},e={}", JSON.toJSONString(user), e);
		}
	}
	
	/**
	 * @Title: register 
	 * @Description: 用户注册 
	 * @param u
	 * @param pwd
	 * @param userName
	 * @param nickName
	 * @return User
	 */
	public User register(User u, String pwd, String userName, String nickName) {
		GwsLogger.info("用户注册,创建普通用户账户");
		//龙猫账户
		Long id = idGlobalGenerator.getSeqId(User.class);
		Long userId = getUserId();//获取龙猫ID(靓号库例外的ID)
		if ((null != id && 0 < id) && (null != userId && 0 < userId)) {
			//nickName = null != nickName ? nickName : "LM" + userId;
			//腾讯云动态链接库生成SIG
			String sig = tlsSigUtils.createSig(userId + "");
			String md5Pwd = Md5Util.thirtyTwo(pwd).substring(6, 16);

			u.setId(id + "");
			u.setUserId(userId + "");
			u.setUserType(UserInfoEnum.ORDINARY.getType().intValue());
			u.setUserState(UserInfoEnum.USER_OPEN.getType());
			u.setUserName(userName);
			u.setLoginPwd(md5Pwd);
			u.setNickName(nickName);
			u.setGrade(1);
			u.setSex(UserInfoEnum.SEX_WOMEN.getType());
			u.setCalorie(0L);
			u.setExperience("0");
			u.setCity("保密");
			u.setSig(sig);
			u.setSigCreate(new Date());
			u.setGmtCreate(new Date());
			u.setGmtCreateUser("REG");
			u.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			
//			// 龙猫币账户
//			CoinAccount coin = createCoin(u);

			// 龙猫豆账户
			BeanAccount bean = createBean(u);
			if (null != bean) {
				User user = userRepositoryMaster.save(u);
				if(null != user){
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
				}
				return user;
			}
		}
		return null;
	}
	
	/**
	 * @Title: createBean 
	 * @Description: 创建龙猫豆账户 
	 * @param u
	 * @return BeanAccount
	 */
	public BeanAccount createBean(User u) {
		GwsLogger.info("用户注册,创建龙猫豆账户");
		Long beanId = idGlobalGenerator.getSeqId(BeanAccount.class);
		Date date = new Date();
		
		BeanAccount bean = new BeanAccount();
		bean.setId(beanId + "");
		bean.setUserId(u.getUserId());
		bean.setLmBeanNum(0d);
		bean.setGmtCreate(date);
		bean.setGmtCreateUser("REG");
		BeanAccount beanAccount = beanAccountRepositoryMaster.save(bean);
//		if (null != beanAccount) {
//			Long beanRecordId = idGlobalGenerator.getSeqId(BeanChangeRecord.class);
//			
//			BeanChangeRecord beanRecord = new BeanChangeRecord();
//			beanRecord.setId(beanRecordId + "");
//			beanRecord.setUserId(u.getUserId());
//			beanRecord.setAgoNum(0d);
//			beanRecord.setChangeType(IsPlusEnum.PLUS.getVal());
//			beanRecord.setChangeNum(0d);
//			beanRecord.setBizType(BeanRecordTypeEnum.REGISTER.getVal());
//			beanRecord.setOutBizId(beanId + "");
//			beanRecord.setGmtCreate(date);
//			beanRecord.setGmtCreateUser("REGISTER");
//			beanChangeRecordRepositoryMaster.save(beanRecord);			
//		}
		return beanAccount;
	}
	
	/**
	 * @Title: getUserId 
	 * @Description: 获取用户ID(龙猫ID) 
	 * @return Long
	 */
	public Long getUserId() {
		Long lmId = idGlobalGenerator.getSeqId("LmId");
		if (10000L > lmId) {
			String userId = userRepositorySlave.queryUserIdMax();
			lmId = idGlobalGenerator.getSeqId("LmId", Long.valueOf(userId));
		}
		GoodNumber good = getGoodNumber(lmId);
		while (null != good) {
			lmId = idGlobalGenerator.getSeqId("LmId");
			good = getGoodNumber(lmId);
		}
		return lmId;
	}
	
	/**
	 * @Title: getGoodNumber 
	 * @Description: 获取靓号数据 
	 * @param number
	 * @return GoodNumber
	 */
	public GoodNumber getGoodNumber(Long number) {
		GoodNumber good = null;
		try {
			good = goodNumberRepositorySlave.queryGoodNumberByNumber(number);
		} catch (Exception e) {
			throw new ImplException(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001);
		}
		return good;
	}
	
}
