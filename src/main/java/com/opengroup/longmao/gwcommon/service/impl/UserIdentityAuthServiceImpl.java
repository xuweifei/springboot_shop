package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.UserIdentityAuth;
import com.opengroup.longmao.gwcommon.enums.IdentityAuthStatusEnum;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.repository.master.UserIdentityAuthRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.UserIdentityAuthRepositorySlave;
import com.opengroup.longmao.gwcommon.service.PictureService;
import com.opengroup.longmao.gwcommon.service.UserIdentityAuthService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;

/**
 * 【用户身份认证表】 Service接口实现
 * @version 1.0
 * @author Hermit 2018年01月25日 下午16:40:59
 */ 
@Service
public class UserIdentityAuthServiceImpl implements UserIdentityAuthService{

	@Autowired
	private UserIdentityAuthRepositoryMaster userIdentityAuthRepositoryMaster;

	@Autowired
	private UserIdentityAuthRepositorySlave userIdentityAuthRepositorySlave;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private PictureService pictureService;

	/**
	 * @Title: findUserIdentityAuth 
	 * @Description: 根据id查询用户身份认证表 
	 * @param userId
	 * @return UserIdentityAuth
	 */
	@Override
	public UserIdentityAuth findUserIdentityAuth(Long userId){
		if (null != userId) {
			return userIdentityAuthRepositorySlave.queryIdentityByUserId(userId);
		}
		return null;
	}
	
	/**
	  * @Title: findAuthByIdCard 
	  * @Description: 查询用户身份认证表 
	  * @param idCard
	  * @return List<UserIdentityAuth>
	  */
	@Override
	public List<UserIdentityAuth> findAuthByIdCard(String idCard){
		return userIdentityAuthRepositorySlave.queryIdentityByIdCard(idCard);
	}
	
	/**
	 * @Title: verifyIdCard 
	 * @Description: 查验用户身份证号是否被使用 
	 * @param idCard
	 * @return Boolean
	 */
	@Override
	public Boolean verifyIdCard(String idCard) {
		List<UserIdentityAuth> uL = userIdentityAuthRepositorySlave.verifyIdCard(idCard,
				IdentityAuthStatusEnum.IDENTITY_UNDER_REVIEW.getType(), IdentityAuthStatusEnum.IDENTITY_PASS.getType());
		if (CollectionUtils.isNotEmpty(uL)) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: saveUserIdentityAuth 
	 * @Description: 保存用户身份认证表 
	 * @param identity
	 * @return UserIdentityAuth
	 */
	@Override
	public UserIdentityAuth saveUserIdentityAuth(UserIdentityAuth identity){
		//判断对象是否存在
		if (null != identity) {
			UserIdentityAuth u = findUserIdentityAuth(identity.getUserId());
			if (null == u) {
				//id统一生成
				Long authId = idGlobalGenerator.getSeqId(UserIdentityAuth.class);
				identity.setAuthId(authId);
				identity.setAuthStatus(IdentityAuthStatusEnum.IDENTITY_UNDER_REVIEW.getType());
				identity.setCtime(DateUtil.currentSecond());
				identity.setUtime(DateUtil.currentSecond());
				identity.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
				UserIdentityAuth userIdentityAuth = userIdentityAuthRepositoryMaster.save(identity);
				if (null != userIdentityAuth) {
					GwsLogger.info("用户身份认证(user_identity_auth)信息,新增成功");
				} else {
					GwsLogger.info("用户身份认证(user_identity_auth)信息,新增失败");
				}
				return userIdentityAuth;
			} else {
				Short authStatus = IdentityAuthStatusEnum.IDENTITY_NO_PASS.getType().equals(u.getAuthStatus())
						? IdentityAuthStatusEnum.IDENTITY_UNDER_REVIEW.getType() : u.getAuthStatus();
				String frontIdentityUrl = u.getFrontIdentityUrl();
				String oppositeIdentityUrl = u.getOppositeIdentityUrl();
				u.setAuthStatus(authStatus);
				u.setRealName(identity.getRealName());
				u.setMobile(identity.getMobile());
				u.setIdCard(identity.getIdCard());
				u.setFrontIdentityUrl(identity.getFrontIdentityUrl());
				u.setOppositeIdentityUrl(identity.getOppositeIdentityUrl());
				u.setUtime(DateUtil.currentSecond());
				UserIdentityAuth userIdentityAuth = userIdentityAuthRepositoryMaster.save(u);
				if (null != userIdentityAuth) {
					GwsLogger.info("用户身份认证(user_identity_auth)信息,修改成功");
					List<String> keyList = new ArrayList<String>();
					//判断图片是否重新上传
					if (!frontIdentityUrl.equals(identity.getFrontIdentityUrl())) {
						keyList.add(frontIdentityUrl);
					}
					if (!oppositeIdentityUrl.equals(identity.getOppositeIdentityUrl())) {
						keyList.add(oppositeIdentityUrl);
					}
					//删除旧有七牛文件
					pictureService.deleteToQiniu(keyList);
				} else {
					GwsLogger.info("用户身份认证(user_identity_auth)信息,修改失败");
				}
				return userIdentityAuth;
			}
		}
		GwsLogger.error("传参对象(UserIdentityAuth)不存在,保存失败:UserIdentityAuth={}",JSON.toJSONString(identity));
		return null;
	}

}


