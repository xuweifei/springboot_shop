package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;

import com.opengroup.longmao.gwcommon.entity.po.User;

/**
 * @ClassName: UserLoginVO 
 * @Description: 【用户登录信息VO】
 * @author Mr.Zhu
 */
public class UserLoginVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 935237340441349702L;
	
	private String userId;
	private Integer userType;
	private String userName;
	private String wechatId;// 微信唯一ID,绑定登录
	private String wechatName;// 微信昵称
	private String qqId;// QQ唯一ID,绑定登录
	private String qqName;// QQ昵称
	private String weiboId;// 微博唯一ID,绑定登录
	private String weiboName;//微博昵称
	private String nickName;
	private String phoneId;
	private Short sex;
	private Integer grade;
	private String signed;
	private Long calorie;
	private String experience;
	private String sig;
	private String city;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static UserLoginVO readFromUser(User u) {
        if (null == u) {
            return null;
        }
		UserLoginVO result = new UserLoginVO();
		result.userId = u.getUserId();
		result.userName = u.getUserName();
		result.userType = u.getUserType();
		result.nickName = u.getNickName();
		result.phoneId = u.getPhoneId();
		result.grade = u.getGrade();
		result.sex = u.getSex();
		result.signed = u.getSigned();
		result.calorie = u.getCalorie();
		result.sig = u.getSig();
		result.experience = u.getExperience();
		result.city = u.getCity();
		result.wechatId = u.getWechatId();
		result.wechatName = u.getWechatName();
		result.qqId = u.getQqId();
		result.qqName = u.getQqName();
		result.weiboId = u.getWeiboId();
		result.wechatName = u.getWechatName();
		return result;
    }

}

