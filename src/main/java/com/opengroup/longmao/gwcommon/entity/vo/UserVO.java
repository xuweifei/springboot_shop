package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;

import com.opengroup.longmao.gwcommon.entity.po.User;

/**
 * 【用户信息表】 VO对象
 *
 * @version
 * @author Hermit 2017年03月15日 上午09:59:49
 */
public class UserVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2921239322383251184L;

	private String userId;// 用户ID(龙猫ID)
	private Integer userType;// 用户类型(0普通,1主播)
	private String userName;// 手机号码
	private String nickName;// 昵称
	private String loginPwd;// 登陸密碼
	private String photoUrl;// 头像
	private Short sex;// 性别
	private Integer grade;// 等级
	private Integer anchorGrade; //主播等级 add by szy 2018.3.20
	private String signed;// 个人简介
	private Long calorie;// 主播卡里路
	private String city;// 城市
	private String sig;// 腾讯云签名
	private Integer attentionNum;// 关注数
	private Integer attentionedNum;// 粉丝数
	private Long lmCoin;// 龙猫币
	private Long lmBean;// 龙猫豆
	private String liveCover;//封面

	private String id;// 头像(后期删除)
	private String phoneId;// 头像(后期删除)
	private Short creditGrade = 75;// 信誉等级分数(后期删除)
    private String creditGradeExplain = "良好";//信用等级说明(后期删除)
    
    private Short isGuess;//是否可开竞猜
    private Short anchorType;// 主播类型(0全职,1兼职)
    
    

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public Integer getAttentionNum() {
		return attentionNum;
	}

	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}

	public Integer getAttentionedNum() {
		return attentionedNum;
	}

	public void setAttentionedNum(Integer attentionedNum) {
		this.attentionedNum = attentionedNum;
	}

	public Long getLmCoin() {
		return lmCoin;
	}

	public void setLmCoin(Long lmCoin) {
		this.lmCoin = lmCoin;
	}

	public Long getLmBean() {
		return lmBean;
	}

	public void setLmBean(Long lmBean) {
		this.lmBean = lmBean;
	}

	public String getLiveCover() {
		return liveCover;
	}

	public void setLiveCover(String liveCover) {
		this.liveCover = liveCover;
	}

	public static UserVO readFromUser(User u) {
		if (null == u) {
			return null;
		}
		UserVO result = new UserVO();
		result.id = u.getId();
		result.userId = u.getUserId();
		result.userType = u.getUserType();
		result.userName = u.getUserName();
		result.nickName = u.getNickName();
		result.sex = u.getSex();
		result.grade = u.getGrade();
		result.signed = u.getSigned();
		result.calorie = u.getCalorie();
		result.city = u.getCity();
		result.sig = u.getSig();
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	public Short getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(Short creditGrade) {
		this.creditGrade = creditGrade;
	}

	public String getCreditGradeExplain() {
		return creditGradeExplain;
	}

	public void setCreditGradeExplain(String creditGradeExplain) {
		this.creditGradeExplain = creditGradeExplain;
	}

	public Short getIsGuess() {
		return isGuess;
	}

	public void setIsGuess(Short isGuess) {
		this.isGuess = isGuess;
	}

	public Short getAnchorType() {
		return anchorType;
	}

	public void setAnchorType(Short anchorType) {
		this.anchorType = anchorType;
	}

	public Integer getAnchorGrade() {
		return anchorGrade;
	}

	public void setAnchorGrade(Integer anchorGrade) {
		this.anchorGrade = anchorGrade;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
}
