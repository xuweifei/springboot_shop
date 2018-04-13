package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 直播间信息类
 *
 */ 
@Entity 
@Table(name = "live_play_info") 
public class LivePlayInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "play_id")
	private Long playId;
	
	@Column(name = "room_id")
	private Long roomId;
	
	@Column(name = "room_tag")
	private Long roomTag;
	
	@Column(name = "room_password")
	private String roomPassword;
	
	@Column(name = "hot_type")
	private Short hotType;
	
	@Column(name = "live_label")
	private Short liveLabel;
	
	@Column(name = "live_min_label")
	private Short liveMinLabel;
	
	@Column(name = "like_count")
	private Integer likeCount;
	
	@Column(name = "viewer_count")
	private Integer viewerCount;
	
	@Column(name = "robot")
	private Integer robot;
	
	@Column(name = "push_url")
	private String pushUrl;
	
	@Column(name = "play_url")
	private String playUrl;
	
	@Column(name = "hls_play_url")
	private String hlsPlayUrl;
	
	@Column(name = "nick_name")
	private String nickName;
	
	@Column(name = "phone_id")
	private String phoneId;
	
	@Column(name = "guess_title")
	private String guessTitle;
	
	@Column(name = "guess_cover")
	private String guessCover;
	
	@Column(name = "ctime")
	private Integer ctime;
	
	@Column(name = "utime")
	private Integer utime;
	
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getPlayId() {
		return playId;
	}

	public void setPlayId(Long playId) {
		this.playId = playId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getRoomTag() {
		return roomTag;
	}

	public void setRoomTag(Long roomTag) {
		this.roomTag = roomTag;
	}

	public String getRoomPassword() {
		return roomPassword;
	}

	public void setRoomPassword(String roomPassword) {
		this.roomPassword = roomPassword;
	}

	public Short getHotType() {
		return hotType;
	}

	public void setHotType(Short hotType) {
		this.hotType = hotType;
	}

	public Short getLiveLabel() {
		return liveLabel;
	}

	public void setLiveLabel(Short liveLabel) {
		this.liveLabel = liveLabel;
	}

	public Short getLiveMinLabel() {
		return liveMinLabel;
	}

	public void setLiveMinLabel(Short liveMinLabel) {
		this.liveMinLabel = liveMinLabel;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getViewerCount() {
		return viewerCount;
	}

	public void setViewerCount(Integer viewerCount) {
		this.viewerCount = viewerCount;
	}

	public Integer getRobot() {
		return robot;
	}

	public void setRobot(Integer robot) {
		this.robot = robot;
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getHlsPlayUrl() {
		return hlsPlayUrl;
	}

	public void setHlsPlayUrl(String hlsPlayUrl) {
		this.hlsPlayUrl = hlsPlayUrl;
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

	public String getGuessTitle() {
		return guessTitle;
	}

	public void setGuessTitle(String guessTitle) {
		this.guessTitle = guessTitle;
	}

	public String getGuessCover() {
		return guessCover;
	}

	public void setGuessCover(String guessCover) {
		this.guessCover = guessCover;
	}

	public Integer getCtime() {
		return ctime;
	}

	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}

	public Integer getUtime() {
		return utime;
	}

	public void setUtime(Integer utime) {
		this.utime = utime;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

	public LivePlayInfo() {
		super();
	}

	public LivePlayInfo(Long playId, Integer ctime, Integer utime) {
		super();
		this.playId = playId;
		this.ctime = ctime;
		this.utime = utime;
	}


}
