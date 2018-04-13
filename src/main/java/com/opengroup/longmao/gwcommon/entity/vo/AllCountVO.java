package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;

import com.opengroup.longmao.gwcommon.enums.LivePlayStatusEnum;

/**
 * 全服统计
 * @author hjy
 *
 * 2017年12月22日 上午11:34:23
 */
public class AllCountVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Short countType; //统计的类型 1-全服主播累计所获卡路里 2-全服用户累计竞猜收益 3-全服用户累计送出礼物金额
	private Long anchorId;//受礼人（主播）id
	private Long senderId;//送礼人（用户）id
	private String nickName;//用户昵称
	private String facePhoto;//用户头像
	private Integer grade; //用户等级
	private Integer anchorGrade; //主播等级 add by szy 2018.3.19
	private Integer sortNum;//序号
	private Long calorieCount;//统计卡路里
	private Long robProfitCount;//统计竞猜收益
	
	private Short follow;//若是主播，登陆用户与其是否关注 1关注，2未关注
	private Short living = LivePlayStatusEnum.UN_PLAY.getType();//若是主播,是否正在直播  播放状态(0:在播；1:停播)
	private Long roomId;//若主播在直播，其对应的房间id
	private String liveCover;//若主播在直播，其对应的封面
	
	public Short getCountType() {
		return countType;
	}
	public void setCountType(Short countType) {
		this.countType = countType;
	}
	public Long getAnchorId() {
		return anchorId;
	}
	public void setAnchorId(Long anchorId) {
		this.anchorId = anchorId;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
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
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Long getCalorieCount() {
		return calorieCount;
	}
	public void setCalorieCount(Long calorieCount) {
		this.calorieCount = calorieCount;
	}
	public Long getRobProfitCount() {
		return robProfitCount;
	}
	public void setRobProfitCount(Long robProfitCount) {
		this.robProfitCount = robProfitCount;
	}
	public Short getFollow() {
		return follow;
	}
	public void setFollow(Short follow) {
		this.follow = follow;
	}
	public Short getLiving() {
		return living;
	}
	public void setLiving(Short living) {
		this.living = living;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getLiveCover() {
		return liveCover;
	}
	public void setLiveCover(String liveCover) {
		this.liveCover = liveCover;
	}
	public Integer getAnchorGrade() {
		return anchorGrade;
	}
	public void setAnchorGrade(Integer anchorGrade) {
		this.anchorGrade = anchorGrade;
	}
	
	
}
