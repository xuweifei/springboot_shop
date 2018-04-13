package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户信息表】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月15日 上午09:59:49
 */ 
@Entity
@Table(name = "ui_user") 
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_type")
	private Integer userType;
	
	@Column(name = "user_state")
	private Short userState;

	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "wechat_id")
	private String wechatId;// 微信唯一ID,绑定登录
	
	@Column(name = "wechat_name")
	private String wechatName;// 微信昵称

	@Column(name = "qq_id")
	private String qqId;// QQ唯一ID,绑定登录
	
	@Column(name = "qq_name")
	private String qqName;// QQ昵称

	@Column(name = "weibo_id")
	private String weiboId;// 微博唯一ID,绑定登录
	
	@Column(name = "weibo_name")
	private String weiboName;//微博昵称
	
	@Column(name = "login_pwd")
	private String loginPwd;

	@Column(name = "nick_name")
	private String nickName;

	@Column(name = "phone_id")
	private String phoneId;

	@Column(name = "sex")
	private Short sex;

	@Column(name = "grade")
	private Integer grade;

	@Column(name = "signed")
	private String signed;

	@Column(name = "calorie")
	private Long calorie;

	@Column(name = "profession")
	private String profession;

	@Column(name = "hobbies")
	private String hobbies;

	@Column(name = "sports")
	private Integer sports;

	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_create_user")
	private String gmtCreateUser;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	@Column(name = "gmt_modified_user")
	private String gmtModifiedUser;

	@Column(name = "experience")
	private String experience;

	@Column(name = "sig")
	private String sig;
	
	@Column(name = "sig_create")
	private Date sigCreate;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "push_id")
	private String pushId;
	
	@Column(name = "is_delete")
	private Short isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Short getUserState() {
		return userState;
	}

	public void setUserState(Short userState) {
		this.userState = userState;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getQqName() {
		return qqName;
	}

	public void setQqName(String qqName) {
		this.qqName = qqName;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getWeiboName() {
		return weiboName;
	}

	public void setWeiboName(String weiboName) {
		this.weiboName = weiboName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

	public Long getCalorie() {
		return calorie;
	}

	public void setCalorie(Long calorie) {
		this.calorie = calorie;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public Integer getSports() {
		return sports;
	}

	public void setSports(Integer sports) {
		this.sports = sports;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtCreateUser() {
		return gmtCreateUser;
	}

	public void setGmtCreateUser(String gmtCreateUser) {
		this.gmtCreateUser = gmtCreateUser;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getGmtModifiedUser() {
		return gmtModifiedUser;
	}

	public void setGmtModifiedUser(String gmtModifiedUser) {
		this.gmtModifiedUser = gmtModifiedUser;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public Date getSigCreate() {
		return sigCreate;
	}

	public void setSigCreate(Date sigCreate) {
		this.sigCreate = sigCreate;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

}

