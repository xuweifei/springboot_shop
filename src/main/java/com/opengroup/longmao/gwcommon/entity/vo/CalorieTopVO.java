/**
 * @Title: CalorieTopVO.java 
 * @Package com.opengroup.longmao.gwcommon.entity.vo 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;

/**
 * @ClassName: CalorieTopVO 
 * @Description: TODO
 * @author Mr.Zhu
 */
public class CalorieTopVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -8542473866533149373L;
	private String userId;	//用户ID
	private String nickName;//用户昵称
	private String facePhoto;//用户头像
	private Short sex;//用户性别
	private Integer grade; //用户等级
	private Long sumClr;//赠送卡路里值
	private int sort;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFacePhoto() {
		return facePhoto;
	}

	public void setFacePhoto(String facePhoto) {
		this.facePhoto = facePhoto;
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

	public Long getSumClr() {
		return sumClr;
	}

	public void setSumClr(Long sumClr) {
		this.sumClr = sumClr;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
