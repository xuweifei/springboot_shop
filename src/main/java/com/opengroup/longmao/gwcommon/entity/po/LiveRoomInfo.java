package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "live_room_info") 
public class LiveRoomInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "room_id")
	private Long roomId;
	
	@Column(name = "room_type")
	private Short roomType;
	
	@Column(name = "stream_id")
	private String streamId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "group_id")
	private Long groupId;
	
	@Column(name = "play_id")
	private Long playId;
	
	@Column(name = "play_status")
	private Short playStatus;
	
	@Column(name = "live_title")
	private String liveTitle;
	
	@Column(name = "live_cover")
	private String liveCover;
	
	@Column(name = "lng")
	private Double lng;
	
	@Column(name = "lat")
	private Double lat;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "check_status")
	private Short checkStatus;
	
	@Column(name = "forbidflag")
	private Short forbidflag;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "ctime")
	private int ctime;
	
	@Column(name = "utime")
	private int utime;
	
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Short getRoomType() {
		return roomType;
	}

	public void setRoomType(Short roomType) {
		this.roomType = roomType;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getPlayId() {
		return playId;
	}

	public void setPlayId(Long playId) {
		this.playId = playId;
	}

	public Short getPlayStatus() {
		return playStatus;
	}

	public void setPlayStatus(Short playStatus) {
		this.playStatus = playStatus;
	}

	public String getLiveTitle() {
		return liveTitle;
	}

	public void setLiveTitle(String liveTitle) {
		this.liveTitle = liveTitle;
	}

	public String getLiveCover() {
		return liveCover;
	}

	public void setLiveCover(String liveCover) {
		this.liveCover = liveCover;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Short getForbidflag() {
		return forbidflag;
	}

	public void setForbidflag(Short forbidflag) {
		this.forbidflag = forbidflag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCtime() {
		return ctime;
	}

	public void setCtime(int ctime) {
		this.ctime = ctime;
	}

	public int getUtime() {
		return utime;
	}

	public void setUtime(int utime) {
		this.utime = utime;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}
	
	
	

}
