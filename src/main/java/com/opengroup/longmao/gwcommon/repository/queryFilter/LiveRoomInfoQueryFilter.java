package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import java.math.BigDecimal;
import java.util.Date;
import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;

/**
 * 【直播房间表对象】构造查询条件
 *
 * @version
 * @author Hermit 2017年12月22日 下午14:36:58
 */ 
@QBindEntity(entityClass = LiveRoomInfo.class)
public class LiveRoomInfoQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "roomId", where = Where.equal)
	private Long roomId;

	@QBindAttrField(fieldName = "roomType", where = Where.equal)
	private Short roomType;

	@QBindAttrField(fieldName = "roomTag", where = Where.equal)
	private Long roomTag;

	@QBindAttrField(fieldName = "hotType", where = Where.equal)
	private Short hotType;

	@QBindAttrField(fieldName = "streamId", where = Where.equal)
	private String streamId;

	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private Long userId;

	@QBindAttrField(fieldName = "groupId", where = Where.equal)
	private Long groupId;

	@QBindAttrField(fieldName = "playId", where = Where.equal)
	private Long playId;

	@QBindAttrField(fieldName = "playStatus", where = Where.equal)
	private Short playStatus;

	@QBindAttrField(fieldName = "liveTitle", where = Where.equal)
	private String liveTitle;

	@QBindAttrField(fieldName = "liveCover", where = Where.equal)
	private String liveCover;

	@QBindAttrField(fieldName = "lng", where = Where.equal)
	private Double lng;

	@QBindAttrField(fieldName = "lat", where = Where.equal)
	private Double lat;

	@QBindAttrField(fieldName = "location", where = Where.equal)
	private String location;

	@QBindAttrField(fieldName = "checkStatus", where = Where.equal)
	private Short checkStatus;

	@QBindAttrField(fieldName = "forbidflag", where = Where.equal)
	private Short forbidflag;

	@QBindAttrField(fieldName = "remark", where = Where.equal)
	private String remark;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getRoomId(){
		return roomId;
	}

	public void setRoomId(Long roomId){
		this.roomId = roomId;
	}

	public Short getRoomType(){
		return roomType;
	}

	public void setRoomType(Short roomType){
		this.roomType = roomType;
	}

	public Long getRoomTag(){
		return roomTag;
	}

	public void setRoomTag(Long roomTag){
		this.roomTag = roomTag;
	}

	public Short getHotType(){
		return hotType;
	}

	public void setHotType(Short hotType){
		this.hotType = hotType;
	}

	public String getStreamId(){
		return streamId;
	}

	public void setStreamId(String streamId){
		this.streamId = streamId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getGroupId(){
		return groupId;
	}

	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}

	public Long getPlayId(){
		return playId;
	}

	public void setPlayId(Long playId){
		this.playId = playId;
	}

	public Short getPlayStatus(){
		return playStatus;
	}

	public void setPlayStatus(Short playStatus){
		this.playStatus = playStatus;
	}

	public String getLiveTitle(){
		return liveTitle;
	}

	public void setLiveTitle(String liveTitle){
		this.liveTitle = liveTitle;
	}

	public String getLiveCover(){
		return liveCover;
	}

	public void setLiveCover(String liveCover){
		this.liveCover = liveCover;
	}

	public Double getLng(){
		return lng;
	}

	public void setLng(Double lng){
		this.lng = lng;
	}

	public Double getLat(){
		return lat;
	}

	public void setLat(Double lat){
		this.lat = lat;
	}

	public String getLocation(){
		return location;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public Short getCheckStatus(){
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus){
		this.checkStatus = checkStatus;
	}

	public Short getForbidflag(){
		return forbidflag;
	}

	public void setForbidflag(Short forbidflag){
		this.forbidflag = forbidflag;
	}

	public String getRemark(){
		return remark;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public Integer getCtime(){
		return ctime;
	}

	public void setCtime(Integer ctime){
		this.ctime = ctime;
	}

	public Integer getUtime(){
		return utime;
	}

	public void setUtime(Integer utime){
		this.utime = utime;
	}

	public Short getIsDelete(){
		return isDelete;
	}

	public void setIsDelete(Short isDelete){
		this.isDelete = isDelete;
	}

}

