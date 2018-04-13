package com.opengroup.longmao.gwcommon.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opengroup.longmao.gwcommon.configuration.elasticsearch.service.ESService;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.TxUserConfig;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.dto.ESUserDTO;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.entity.po.CoinAccount;
import com.opengroup.longmao.gwcommon.entity.po.GoodNumber;
import com.opengroup.longmao.gwcommon.entity.po.LoginRecord;
import com.opengroup.longmao.gwcommon.entity.po.PhotoAlbum;
import com.opengroup.longmao.gwcommon.entity.po.SysConfig;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserChannel;
import com.opengroup.longmao.gwcommon.entity.po.UserInvite;
import com.opengroup.longmao.gwcommon.enums.BeanRecordTypeEnum;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsNormalEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.enums.IsReceivableEnum;
import com.opengroup.longmao.gwcommon.enums.LoginWayEnum;
import com.opengroup.longmao.gwcommon.enums.PhotoTypeEnum;
import com.opengroup.longmao.gwcommon.enums.UserInfoEnum;
import com.opengroup.longmao.gwcommon.enums.UserTypeEnum;
import com.opengroup.longmao.gwcommon.repository.master.BeanAccountRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.CoinAccountRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.LoginRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.PhotoAlbumRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserChannelRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserInviteRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.LoginRecordQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.GoodNumberRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.LoginRecordRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserInviteRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.BeanAccountService;
import com.opengroup.longmao.gwcommon.service.LoginService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.SysConfigService;
import com.opengroup.longmao.gwcommon.service.UserChannelService;
import com.opengroup.longmao.gwcommon.tools.EntityDtoConverter;
import com.opengroup.longmao.gwcommon.tools.Md5Util;
import com.opengroup.longmao.gwcommon.tools.crypto.MD5Util;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.http.okhttp.OkhttpRequestApi;
import com.opengroup.longmao.gwcommon.tools.http.okhttp.OkhttpRequestApi.Param;
import com.opengroup.longmao.gwcommon.tools.idutil.StringUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.sig.TlsSigUtils;

/**
 * 【用户信息表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 上午09:59:49
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private BeanAccountRepositoryMaster beanAccountRepositoryMaster;
	
	@Autowired
	private CoinAccountRepositoryMaster coinAccountRepositoryMaster;
	
	@Autowired
	private LoginRecordRepositoryMaster loginRecordRepositoryMaster;
	
	@Autowired
	private UserChannelRepositoryMaster userChannelRepositoryMaster;
	
	@Autowired
	private LoginRecordRepositorySlave loginRecordRepositorySlave;
	
	@Autowired
	private UserInviteRepositoryMaster userInviteRepositoryMaster;
	
	@Autowired
	private PhotoAlbumRepositoryMaster photoAlbumRepositoryMaster;
	
	@Autowired
	private UserInviteRepositorySlave userInviteRepositorySlave;
	
	@Autowired
	private GoodNumberRepositorySlave goodNumberRepositorySlave;
	
	@Autowired
	private UserRepositoryMaster userRepositoryMaster;
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private BeanAccountService beanAccountService;
	
	@Autowired
	private UserChannelService userChannelService;
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private SysConfigService SysConfigService;
	
	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private TlsSigUtils tlsSigUtils;
	
	@Autowired
	private ESService<ESUserDTO> eSService;
	
	@Autowired
	private TxUserConfig txConfig;
	
	/**
	 * @Title: queryUserByUserName 
	 * @Description: 根据手机号码查询用户信息 
	 * @param userName
	 * @return User
	 */
	@Override
	public User queryUserByUserName(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			return userRepositoryMaster.queryUserByUserName(userName);
		}
		return null;
	}

	/**
	  * @Title: pwdLogin 
	  * @Description: 使用密码登录系统 
	  * @param userName
	  * @param pwd
	  * @param pushId
	  * @return Map<String, Object>
	  */
	@Override
	public Map<String, Object> pwdLogin(String userName, String pwd, String pushId) {
		//手机号查询用户
		User user = userRepositoryMaster.queryUserByUserName(userName);
		if (null == user) {
			GwsLogger.info("未找到匹配用户:userName={},pwd={}", userName, pwd);
			throw new ImplException(CommConstant.GWSCOD0010, CommConstant.GWSMSG0010);
		}
		if (!user.getLoginPwd().equals(pwd)) {
			GwsLogger.info("密码不正确:userName={},pwd={}", userName, pwd);
			throw new ImplException(CommConstant.GWSCOD0011, CommConstant.GWSMSG0011);
		}
		return setUserMap(user, IsOrNotEnum.NO.getType(), pushId);
	}
	 
	 /**
	  * @Title: captchaLogin 
	  * @Description: 使用验证码登录系统 
	  * @param userName
	  * @param captcha
	  * @param pushId
	  * @return Map<String, Object>
	  */
	@Override
	public Map<String, Object> captchaLogin(String userName, String captcha, String pushId) {
		User user = userRepositoryMaster.queryUserByUserName(userName);
		if (null == user) {//没有相关账户信息则注册相关账户
			User u = new User();
			u.setPushId(pushId);
			user = register(u, captcha, userName, null);
			return setUserMap(user, IsOrNotEnum.YES.getType(), pushId);
		}
		return setUserMap(user, IsOrNotEnum.NO.getType(), pushId);
	}
	
	/**
	  * @Title: thirdPartyReg
	  * @Description: 第三方账户信息注册 
	  * @param param
	  * @return Map<String, Object>
	  */
	@Override
	public Map<String, Object> thirdPartyReg(Map<String, Object> param) {
		String loginId = (String) param.get("loginId");
		Short way = (Short) param.get("way");
		String pushId = (String)  param.get("pushId");
		String nickName = (String)  param.get("nickName");
		String oldNickName = (String)  param.get("oldNickName");
		
		User u = new User();
		u.setPushId(pushId);
		u.setPhoneId((String) param.get("userHead"));
		if (LoginWayEnum.MOB_WECHAT_WARRANTY.getVal() == way) {// MOB-微信授权登录
			u.setWechatId(loginId);
			u.setWechatName(oldNickName);
		} else if (LoginWayEnum.MOB_QQ_WARRANTY.getVal() == way) {// MOB-QQ授权登录
			u.setQqId(loginId);;
			u.setQqName(oldNickName);
		} else if (LoginWayEnum.MOB_WEIBO_WARRANTY.getVal() == way) {// MOB-微博授权登录
			u.setWeiboId(loginId);
			u.setWeiboName(oldNickName);
		}
		//第三方账户注册
		User user = register(u, loginId, null, nickName);
		//第三方账户保存默认头像到相册(用于直播封面)
		savePhoto(user);
		return setUserMap(user, IsOrNotEnum.YES.getType(), pushId);
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
			GwsLogger.error("保存相册异常:User={},e={}", ToStringBuilder.reflectionToString(user), e);
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
				if(user != null){
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
				}
				return user;
			}
		}
		return null;
	}
	
	/**
	 * @Title: robotReg 
	 * @Description: 机器人注册 
	 * @param json
	 * @return User
	 */
	@Override
	public User robotReg(JSONObject json) {
		GwsLogger.info("用户注册,创建机器人账户");
		// 龙猫账户
		Long id = idGlobalGenerator.getSeqId(User.class);
		Long userId = json.getLong("userId");
		String userName = json.getString("loginId");
		// 腾讯云动态链接库生成SIG
		//String sig = tlsSigUtils.createSig(userId + "");
		String sig = userId + "";
		String md5Pwd = Md5Util.thirtyTwo(userName).substring(6, 16);
		
		User u = new User();
		u.setId(id + "");
		u.setUserId(userId + "");
		u.setUserType(UserTypeEnum.ROBOT.getVal());
		u.setUserState(UserInfoEnum.USER_OPEN.getType());
		u.setUserName(userName);
		u.setLoginPwd(md5Pwd);
		u.setNickName(json.getString("nickName"));
		u.setPhoneId(json.getString("phoneId"));
		u.setSex(json.getShort("sex"));
		u.setGrade(json.getInteger("grade"));
		u.setCalorie(0L);
		u.setExperience("0");
		u.setCity("保密");
		u.setSig(sig);
		u.setSigCreate(new Date());
		u.setGmtCreate(new Date());
		u.setGmtCreateUser("REG");
		u.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());

		// 龙猫豆账户
		BeanAccount bean = createBean(u);
		User b = ((null != bean) ? userRepositoryMaster.save(u) : null);
		if(b != null){
			eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(b));
		}
		return b;
	}
	
	/**
	  * @Title: boundAccount 
	  * @Description: 绑定手机/微信/QQ/微博 
	  * @param param
	  * @return Map<String, Object>
	  */
	@Override
	public Map<String, Object> boundAccount(Map<String, String> param) {
		String userId = param.get("userId");
		String account = param.get("account");
		String nickName = param.get("nickName");
		String captcha = param.get("captcha");
		Short way = Short.valueOf(param.get("way"));
		
		User user = userRepositoryMaster.queryUserByUserId(userId);
		if (null != user) {
			if (LoginWayEnum.MOBILE.getVal() == way) {// 绑定手机
				String msg = redis.get(ConfigConstant.MOBILE_SMSC_CAPTCHA, account);
				if (null == msg) {
					throw new ImplException(CommConstant.GWSCOD0006, CommConstant.GWSMSG0006);
				}
				if (msg.equals(captcha)) {
					//活动开关打开且第一次绑定手机号码赠送龙猫豆
//					SysConfig sys = SysConfigService.findSysConfig("binding_give_");
//					if (null != sys && "1".equals(sys.getVal()) && StringUtils.isBlank(user.getUserName())) {
//						String bean = sys.getName().replace("binding_give_", "");
//						BigDecimal lmBeanNum = new BigDecimal(bean);
//						beanAccountService.updateBean(userId, lmBeanNum, IsPlusEnum.PLUS.getVal(),
//								BeanRecordTypeEnum.GIVE.getVal());
//					}
					redis.delete(ConfigConstant.MOBILE_SMSC_CAPTCHA, account);//删除Redis数据
					user.setUserName(account);
					user = userRepositoryMaster.save(user);
					if(user != null){
						eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
					}
					return setUserMap(user, IsOrNotEnum.NO.getType(), "");
				} else {
					throw new ImplException(CommConstant.GWSCOD0005, CommConstant.GWSMSG0005);
				}
			} else if (LoginWayEnum.MOB_WECHAT_WARRANTY.getVal() == way) {// 绑定微信
				user.setWechatId(account);
				user.setWechatName(nickName);
				user = userRepositoryMaster.save(user);
				if(user != null){
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
				}
				return setUserMap(user, IsOrNotEnum.NO.getType(), "");
			} else if (LoginWayEnum.MOB_QQ_WARRANTY.getVal() == way) {// 绑定QQ
				user.setQqId(account);
				user.setQqName(nickName);
				user = userRepositoryMaster.save(user);
				if(user != null){
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
				}
				return setUserMap(user, IsOrNotEnum.NO.getType(), "");
			} else if (LoginWayEnum.MOB_WEIBO_WARRANTY.getVal() == way) {// 绑定微博
				user.setWeiboId(account);
				user.setWeiboName(nickName);
				user = userRepositoryMaster.save(user);
				if(user != null){
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
				}
				return setUserMap(user, IsOrNotEnum.NO.getType(), "");
			}
		}
		return setUserMap(null, IsOrNotEnum.NO.getType(), "");
	}
	
	/**
	 * @Title: boundRemove
	 * @Description: 解除绑定手机/微信/QQ/微博
	 * @param param
	 * @return Boolean
	 */
	@Override
	public Boolean boundRemove(Map<String, String> param) {
		Boolean flag = false;
		Integer size = 0;
		String userId = param.get("userId");
		Short way = Short.valueOf(param.get("way"));
		Map<String, Object> userMap = accountLog(userId); //查询绑定信息
		
		size = (null == userMap.get("userName") || StringUtils.isBlank(userMap.get("userName") + "")) ? size + 0
				: size + 1;
		size = (null == userMap.get("wechatId") || StringUtils.isBlank(userMap.get("wechatId") + "")) ? size + 0
				: size + 1;
		size = (null == userMap.get("qqId") || StringUtils.isBlank(userMap.get("qqId") + "")) ? size + 0 : size + 1;
		size = (null == userMap.get("weiboId") || StringUtils.isBlank(userMap.get("weiboId") + "")) ? size + 0
				: size + 1;
		if (2 > size) {
			throw new ImplException(CommConstant.GWSCOD0016, CommConstant.GWSMSG0016);
		} else {
			User user = queryUserByUserId(userId);
			if (null != user) {
				if (LoginWayEnum.MOB_WECHAT_WARRANTY.getVal() == way) {// 解除绑定微信
					user.setWechatId(null);
					user.setWechatName(null);
					user = userRepositoryMaster.save(user);
					if(user != null){
						eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
					}
					return StringUtils.isBlank(user.getWechatId()) ? true : false;
				} else if (LoginWayEnum.MOB_QQ_WARRANTY.getVal() == way) {// 解除绑定QQ
					user.setQqId(null);
					user.setQqName(null);
					user = userRepositoryMaster.save(user);
					if(user != null){
						eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
					}
					return StringUtils.isBlank(user.getQqId()) ? true : false;
				} else if (LoginWayEnum.MOB_WEIBO_WARRANTY.getVal() == way) {// 解除绑定微博
					user.setWeiboId(null);
					user.setWeiboName(null);
					user = userRepositoryMaster.save(user);
					if(user != null){
						eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
					}
					return StringUtils.isBlank(user.getWeiboId()) ? true : false;
				}
			}
		}
		return flag;
	}

	/**
	 * @Title: findBackPwd
	 * @Description: 用户找回密码
	 * @param userId
	 * @param pwd
	 * @param captcha
	 * @return Boolean
	 */
	@Override
	public Boolean findBackPwd(String userId, String pwd, String captcha) {
		Boolean flag = false;
		String msg = redis.get(ConfigConstant.FINDBACKPWD_MSG, userId);
		if (null == msg) {
			throw new ImplException(CommConstant.GWSCOD0006, CommConstant.GWSMSG0006);
		}
		if (msg.equals(captcha)) {
			User user = queryUserByUserId(userId);
			if (null != user) {
				user.setLoginPwd(pwd);
				user = userRepositoryMaster.save(user);
				if (null != user) {
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
					redis.delete(ConfigConstant.FINDBACKPWD_MSG, userId);// 删除Redis数据
					flag = true;
				}
				return flag;
			}
		} else {
			throw new ImplException(CommConstant.GWSCOD0005, CommConstant.GWSMSG0005);
		}
		return flag;
	}
	
	/**
	 * @Title: changeMobile
	 * @Description: 用户更换手机号码
	 * @param userId
	 * @param userName
	 * @param captcha
	 * @return Boolean
	 */
	@SuppressWarnings("unused")
	@Override
	public Boolean changeMobile(String userId, String userName, String captcha) {
		Boolean flag = false;
		String msg = redis.get(ConfigConstant.CHANGEMOBILE_MSG, userName);
		if (null == msg) {
			throw new ImplException(CommConstant.GWSCOD0006, CommConstant.GWSMSG0006);
		}
		if (msg.equals(captcha)) {
			User user = queryUserByUserId(userId);
			if (null != user) {
				//第一次绑定手机号码赠送龙猫豆
				if (StringUtils.isBlank(user.getUserName())) {
					flag = true;
				}
				user.setUserName(userName);
				user = userRepositoryMaster.save(user);
				if (null != user) {
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(user));
					//活动开关
//					SysConfig sys = SysConfigService.findSysConfig("binding_give_");
//					if (null != sys && "1".equals(sys.getVal()) && flag) {
//						String bean = sys.getName().replace("binding_give_", "");
//						BigDecimal lmBeanNum = new BigDecimal(bean);
//						beanAccountService.updateBean(userId, lmBeanNum, IsPlusEnum.PLUS.getVal(),
//								BeanRecordTypeEnum.GIVE.getVal());
//					}
					redis.delete(ConfigConstant.CHANGEMOBILE_MSG, userName);// 删除Redis数据
					return true;
				}
				
				//更改手机号时将手机号同步到wap服务客户信息扩表 szy
				if(null != user){
					syncPhoneToWap(user);
				}
			}
			return false;
		} else {
			throw new ImplException(CommConstant.GWSCOD0005, CommConstant.GWSMSG0005);
		}
	}
	
	private void syncPhoneToWap(User user) {
		Param[] params = new Param[] { new Param("userId", user.getUserId()),new Param("userPhone", user.getUserName())};
		GwsLogger.info("将手机号同步到wap服务：userId={},phoneId={},userPhone={}",user.getUserId(),user.getUserName());
		Map<String, Object> data = OkhttpRequestApi.httppost(txConfig.getMallCustomerExpand().trim(), params);
		GwsLogger.info("请求地址==>txConfig.getMallCustomerExpand()={}：",txConfig.getMallCustomerExpand().trim());
		GwsLogger.info("放回的data={}===>",data);
		if (CommConstant.GWSCOD0000.equals(data.get("code")) && CommConstant.GWSMSG0000.equals(data.get("message"))) {
			GwsLogger.info("将手机号同步到wap服务成功!");
		} else {
			GwsLogger.error("将手机号同步到wap服务失败");
		}
	}

	/**
	 * @Title: accountLog
	 * @Description: 账号绑定记录
	 * @param userId
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> accountLog(String userId) {
		User user = queryUserByUserId(userId);
		if (null == user) {
			throw new ImplException(CommConstant.GWSCOD0001, CommConstant.GWS1001);
		}
		Map<String, Object> returnMap = setUserMap(user, IsOrNotEnum.NO.getType(), "");
		Object channelId = userChannelService.getChannelId(Long.valueOf(userId)).get("channelId");
		returnMap.put("channelId", channelId + "");
		return returnMap;
	}
	
	/**
	 * @Title: createCoin 
	 * @Description: 创建龙猫币账户 
	 * @param u
	 * @return CoinAccount
	 */
	public CoinAccount createCoin(User u) {
		GwsLogger.info("用户注册,创建龙猫币账户");
		Long coidId = idGlobalGenerator.getSeqId(CoinAccount.class);
		Date date = new Date();
		
		CoinAccount coin = new CoinAccount();
		coin.setId(coidId + "");
		coin.setUserId(u.getUserId());
		coin.setLmCoinNum(0d);
		coin.setGmtCreate(date);
		coin.setGmtCreateUser("REG");
		CoinAccount coinAccount = coinAccountRepositoryMaster.save(coin);
//		if (null != coinAccount) {//增加龙猫币账户记录
//			Long coinRecordId = idGlobalGenerator.getSeqId(CoinChangeRecord.class);
//			
//			CoinChangeRecord coinRecord = new CoinChangeRecord();
//			coinRecord.setId(coinRecordId + "");
//			coinRecord.setUserId(u.getUserId());
//			coinRecord.setChangeType(IsPlusEnum.PLUS.getVal());
//			coinRecord.setChangeNum(0d);
//			coinRecord.setBizType(CoinRecordTypeEnum.REGISTER.getVal());
//			coinRecord.setOutBizId(coidId + "");
//			coinRecord.setGmtCreate(date);
//			coinRecord.setGmtCreateUser("REGISTER");
//			coinChangeRecordRepositoryMaster.save(coinRecord);
//		}
		return coinAccount;
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
	 * @Title: loginRecord 
	 * @Description: 登录直播记录 
	 * @param loginMap
	 * @return void
	 */
	@Override
	public void loginRecord(Map<String, Object> loginMap) {
		if (null != loginMap && !loginMap.isEmpty()) {
			String userId = (String) loginMap.get("userId");
			String chlId = (String) loginMap.get("chlId");
			String loginUser = (String) loginMap.get("loginId");
			Short loginWay = (Short) loginMap.get("way");
			Short isFirst = (Short) loginMap.get("isFirst");
			String pushId = (String) loginMap.get("pushId");
			
			LoginRecord l = null;
			
			Page<LoginRecord> page = queryLoginRecordByUserId(userId);
			List<LoginRecord> list = page.getContent();
			if (CollectionUtils.isNotEmpty(list)) {
				l = list.get(0);
			}
			Long loginId = idGlobalGenerator.getSeqId(LoginRecord.class);

			LoginRecord login = new LoginRecord();
			login.setId(loginId + "");
			login.setUserId(userId);
			login.setChlId(chlId);
			login.setLoginWay(Short.valueOf(loginWay));
			login.setLoginUser(loginUser);
			login.setLoginLastTime(null != l ? l.getGmtCreate() : null);
			login.setLoginSite("保密");
			login.setIsFirst(isFirst);
			login.setGmtCreate(new Date());
			login.setGmtCreateUser(LoginWayEnum.getEnumByVal(loginWay) + "");
			login.setIsDelete((short) 1);
			loginRecordRepositoryMaster.save(login);
			// 第一次登录,创建用户渠道信息
			if (isFirst - 1 == 0) {
				GwsLogger.info("用户第一次登录,创建用户渠道信息:userId={},chlId={},pushId={}", userId, chlId, pushId);
				careatUserChannel(userId, chlId, pushId, loginWay, loginUser);
			}
		}
	}
	
	/**
	 * 创建用户渠道信息
	 * @author xwf 2018年1月6日
	 * @param userId
	 * @param chlId
	 * @param loginWay
	 * @param loginUser
	 */
	private void careatUserChannel(String userId, String chlId, String pushId, Short loginWay, String loginUser) {
		chlId = null == chlId ? "1" : chlId;// 渠道号为空?
		UserChannel userChannel = new UserChannel();
		int currentSecond = DateUtil.currentSecond();
		userChannel.setUserId(Long.valueOf(userId));
		userChannel.setChannelId(Long.valueOf(chlId));
		if (loginWay.equals(LoginWayEnum.MOBILE_CAPTCHA.getVal())) {// 手机号&验证码登录 2
			// 判断手机号是否被邀请
			UserInvite invite = userInviteRepositorySlave.getInvitationByPhoneNum(loginUser);
			if (null != invite) {
				GwsLogger.info("该用户通过好友邀请注册:邀请者id={},手机号={}", invite.getUserId(), loginUser);
				sendInviteReward(userId, invite, pushId);
			}
		}
		userChannel.setCreateDate(new Date());
		userChannel.setLoginWay(loginWay);
		userChannel.setResetNum(0);
		userChannel.setRemark("");
		userChannel.setCtime(currentSecond);
		userChannel.setUtime(currentSecond);
		userChannel.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		
		userChannelRepositoryMaster.save(userChannel);
	}
	
	/**
	 * 发放邀请好友奖励
	 * @author xwf 2018年3月9日
	 * @param userId
	 * @param invite
	 * @param pushId
	 */
	private void sendInviteReward(String userId, UserInvite invite, String pushId) {
		boolean isDuplicateInvite = isDuplicateInvite(pushId);// 同一设备重复邀请校验,被邀请过则返回true
		if(isDuplicateInvite) {
			GwsLogger.info("该设备已经被邀请,不发放奖励,pushId={}", pushId);
			invite.setInvitedUserId(Long.valueOf(userId));
			invite.setIsReceivable(IsReceivableEnum.DUPLICATE_INVITE.getVal());
			invite.setUtime(DateUtil.currentSecond());
			userInviteRepositoryMaster.save(invite);
		}else {
			GwsLogger.info("该设备没有被邀请,发放奖励,pushId={}", pushId);
			// 调加豆方法发放邀请奖励,并修改邀请状态
			boolean plusInvitedReward = beanAccountService.updateBeanAccount(userId, invite.getInvitedReward().doubleValue(), IsPlusEnum.PLUS.getVal(), BeanRecordTypeEnum.INVITED_REWARD.getVal());
			if(!plusInvitedReward) {
				GwsLogger.error("发放被邀请奖励失败,邀请者id={},被邀请者id={}", invite.getUserId(), userId);
			}
			boolean minusInvitedReward = beanAccountService.updateBeanAccount(ConfigConstant.OWN_MANAGE_MINUS_ID.toString(), invite.getInvitedReward().doubleValue(), IsPlusEnum.MINUS.getVal(), BeanRecordTypeEnum.INVITED_REWARD.getVal());
			if(!minusInvitedReward) {
				GwsLogger.error("发放被邀请奖励后扣除系统豆子失败,邀请者id={},被邀请者id={}", invite.getUserId(), userId);
			}
			boolean plusInviteReward = beanAccountService.updateBeanAccount(invite.getUserId().toString(), invite.getInviteReward().doubleValue(), IsPlusEnum.PLUS.getVal(), BeanRecordTypeEnum.INVITE_REWARD.getVal());
			if(!plusInviteReward) {
				GwsLogger.error("发放邀请者奖励失败,邀请者id={},被邀请者id={}", invite.getUserId(), userId);
			}
			boolean minusInviteReward = beanAccountService.updateBeanAccount(ConfigConstant.OWN_MANAGE_MINUS_ID.toString(), invite.getInviteReward().doubleValue(), IsPlusEnum.MINUS.getVal(), BeanRecordTypeEnum.INVITE_REWARD.getVal());
			if(!minusInviteReward) {
				GwsLogger.error("发放邀请者奖励后扣除系统豆子失败,邀请者id={},被邀请者id={}", invite.getUserId(), userId);
			}
			invite.setInvitedUserId(Long.valueOf(userId));
			invite.setPushId(pushId);
			invite.setIsReceivable(IsReceivableEnum.HAVE_RECEIVED.getVal());
			invite.setUtime(DateUtil.currentSecond());
			userInviteRepositoryMaster.save(invite);
		}
	}

	/**
	 * 同一设备重复邀请校验
	 * @author xwf 2018年3月9日
	 * @param pushId
	 */
	private boolean isDuplicateInvite(String pushId) {
		if(StringUtil.isBlank(pushId)) {
			return false;
		}
		List<UserInvite> inviteList = userInviteRepositorySlave.getInvitationByPushId(pushId);
		GwsLogger.info("设备被邀请次数inviteList.size={}", inviteList.size());
		if(null == inviteList || inviteList.size() < 1) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: queryLoginRecordByUserId 
	 * @Description:  
	 * @param userId
	 * @return Page<LoginRecord>
	 */
	public Page<LoginRecord> queryLoginRecordByUserId(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			LoginRecordQueryFilter query = new LoginRecordQueryFilter();
			query.setIsDelete(IsNormalEnum.YES.getVal());
			query.setUserId(userId);

			Pageable page = new PageRequest(0, 1, Direction.DESC, "gmtCreate");
			Page<LoginRecord> pageList = loginRecordRepositorySlave.findAll(query, page);
			return pageList;
		} else {
			GwsLogger.error("查询LoginRecord数据,userId不存在");
		}
		return null;
	}
	
	/**
	 * @Title: isNumeric 
	 * @Description: 是否为数字 
	 * @param str
	 * @return boolean
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[1-9]\\d*$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * @Title: setUserMap 
	 * @Description: 返回登录信息 
	 * @param u
	 * @return Map<String,Object>
	 */
	public Map<String, Object> setUserMap(User u, Short isFirst, String pushId) {
		if (null != u) {
			if (UserInfoEnum.USER_BAN.getType() == u.getUserState()
					|| UserInfoEnum.USER_BAN_MSG.getType() == u.getUserState()) {
				GwsLogger.info("该账户已封禁:userId={},userState={}", u.getUserId(), u.getUserState());
				throw new ImplException(CommConstant.GWSCOD0021, CommConstant.GWSMSG0021);
			}
			String sigCreate = DateUtil.getFormatDate(u.getSigCreate(), DateUtil.DATA_PATTON_YYYYMMDD);
			Integer expireTime = Integer.valueOf(DateUtil.getAfterDateFormat(sigCreate, ConfigConstant.SIG_EXPIRE));
			Integer nowTime = Integer.valueOf(DateUtil.getFormatDate(new Date(), DateUtil.DATA_PATTON_YYYYMMDD));
			String sig = u.getSig();
			if (expireTime <= nowTime) {
				GwsLogger.info("重新生成该账户SIG:userId={},expireTime={}", u.getUserId(), expireTime);
				sig = tlsSigUtils.createSig(u.getUserId());
				u.setSig(sig);
				u.setSigCreate(new Date());
				u.setGmtModified(new Date());
				u.setGmtModifiedUser("SIG");
				User uu = userRepositoryMaster.save(u);
				if(uu != null){
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(uu));
				}
			}
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", u.getUserId());
			userMap.put("userName", u.getUserName());
			userMap.put("nickName", null == u.getNickName() ? "" : u.getNickName());
			userMap.put("phoneId", u.getPhoneId());
			userMap.put("wechatId", u.getWechatId());
			userMap.put("wechatName", u.getWechatName());
			userMap.put("qqId", u.getQqId());
			userMap.put("qqName", u.getQqName());
			userMap.put("weiboId", u.getWeiboId());
			userMap.put("weiboName", u.getWeiboName());
			userMap.put("sig", sig);
			userMap.put("isFirst", isFirst);
			if ((StringUtils.isBlank(u.getPushId()) && StringUtils.isNotBlank(pushId))
					|| (StringUtils.isNotBlank(u.getPushId()) && StringUtils.isNotBlank(pushId)
							&& !pushId.equals(u.getPushId()))) {
				u.setPushId(pushId);
				User uu = userRepositoryMaster.save(u);
				if(uu != null){
					eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(uu));
				}
			}
			return userMap;
		}
		return null;
	}
	
	/**
	 * @Title: getUserByLoginId 
	 * @Description: 第三方登录查询 
	 * @param loginId
	 * @param way
	 * @return  User
	 */
	@Override
	public Map<String, Object> getUserByLoginId(String loginId, Short way, String pushId) {
		User u = null;
		if (LoginWayEnum.MOB_WECHAT_WARRANTY.getVal() == way) {
			u = userRepositorySlave.queryUserByWechatId(loginId);
			return setUserMap(u, IsOrNotEnum.NO.getType(), pushId);
		} else if (LoginWayEnum.MOB_QQ_WARRANTY.getVal() == way) {
			u = userRepositorySlave.queryUserByQQId(loginId);
			return setUserMap(u, IsOrNotEnum.NO.getType(), pushId);
		} else if (LoginWayEnum.MOB_WEIBO_WARRANTY.getVal() == way) {
			u = userRepositorySlave.queryUserByWeiboId(loginId);
			return setUserMap(u, IsOrNotEnum.NO.getType(), pushId);
		} else if (LoginWayEnum.MOBILE.getVal() == way) {
			u = userRepositorySlave.queryUserByUserName(loginId);
			return setUserMap(u, IsOrNotEnum.NO.getType(), pushId);
		}
		return null;
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
	 * 根据userId生成商城登录key
	 */
	@Override
	public Map<String, Object> getShopLoginKey(Long userId) {
		Map<String, Object> returnMap = new HashMap<>();
		int currentSecond = DateUtil.currentSecond();
		// 生成key
		String key = createLoginKey(userId, currentSecond);
		// 把key存到redis
		redis.set(ConfigConstant.SHOP_LOGIN_KEY_PREFIX, MD5Util.MD5(key), key, ConfigConstant.SHOP_LOGIN_KEY_TIMEOUT);
		// 将key返回
		returnMap.put("shopKey", MD5Util.MD5(key));
		test(MD5Util.MD5(key));
		return returnMap;
	}

	private void test(String key) {
		Object object = redis.get(ConfigConstant.SHOP_LOGIN_KEY_PREFIX, key);
		if(null == object) {
			return;
		}
		// 转换成JSONObject
		JSONObject keyMap = JSON.parseObject(object.toString());
		Long userId = keyMap.getLong("userId");
		Integer timeStemp = keyMap.getIntValue("timeStemp");
		GwsLogger.info("userId={},timeStemp={}", userId, timeStemp);
	}

	/**
	 * 生成key
	 */
	private String createLoginKey(Long userId, int timeStemp) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId.toString());
		map.put("timeStemp", timeStemp + "");
		return JSON.toJSONString(map);
	}
	
}
