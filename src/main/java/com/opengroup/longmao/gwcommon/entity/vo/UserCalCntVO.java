/**
 * @Title: UserCalCntVO.java 
 * @Package com.opengroup.longmao.gwcommon.entity.vo 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: UserCalCntVO
 * @Description: TODO
 * @author Mr.Zhu
 */
public class UserCalCntVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -5025933168950532218L;
	private String userId; //主播userId
	private String nickName;//主播昵称
	private String facePhoto;// 主播头像
	private Long totClr; //主播卡路里总计
	private List<CalorieTopVO> calorieTop; //卡路里信息列表(分页)

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

	public Long getTotClr() {
		return totClr;
	}

	public void setTotClr(Long totClr) {
		this.totClr = totClr;
	}

	public List<CalorieTopVO> getCalorieTop() {
		return calorieTop;
	}

	public void setCalorieTop(List<CalorieTopVO> calorieTop) {
		this.calorieTop = calorieTop;
	}

}
