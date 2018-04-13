package com.opengroup.longmao.gwcommon.entity.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.opengroup.longmao.gwcommon.tools.date.DateUtil;

import io.searchbox.annotations.JestId;

public class ESUserDTO implements Serializable {

private static final long serialVersionUID = 1L;

	
	private String id;

	@JestId
	private String userId;

	
	private Integer userType;

	
	private Short userState;

	
	private String userName;

	
	private String wechatId;// 微信唯一ID,绑定登录

	
	private String wechatName;// 微信昵称

	
	private String qqId;// QQ唯一ID,绑定登录

	
	private String qqName;// QQ昵称

	
	private String weiboId;// 微博唯一ID,绑定登录

	
	private String weiboName;// 微博昵称
	
	private String nickName;

	
	private String phoneId;

	
	private Short sex;

	
	private Integer grade;

	
	private String signed;

	
	private Long calorie;

	
	private String profession;

	
	private String hobbies;

	
	private Integer sports;

	
	private Long gmtCreate;

	
	private String gmtCreateUser;

	
	private Long gmtModified;

	
	private String gmtModifiedUser;

	
	private String experience;
	
	private String remark;

	
	private Long sigCreate;

	
	private String city;

	
	private String pushId;

	
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
		return DateUtil.getDateByLong(gmtCreate);
	}


	public void setGmtCreate(Date gmtCreate) {
		if(gmtCreate != null){
			this.gmtCreate = gmtCreate.getTime();
		}
	}


	public String getGmtCreateUser() {
		return gmtCreateUser;
	}


	public void setGmtCreateUser(String gmtCreateUser) {
		this.gmtCreateUser = gmtCreateUser;
	}


	public Date getGmtModified() {
		return DateUtil.getDateByLong(gmtModified);
	}


	public void setGmtModified(Date gmtModified) {
		if(gmtModified != null){
			this.gmtModified = gmtModified.getTime();
		}
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getSigCreate() {
		return DateUtil.getDateByLong(sigCreate);
	}


	public void setSigCreate(Date sigCreate) {
		if(sigCreate != null){
			this.sigCreate = sigCreate.getTime();
		}
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
