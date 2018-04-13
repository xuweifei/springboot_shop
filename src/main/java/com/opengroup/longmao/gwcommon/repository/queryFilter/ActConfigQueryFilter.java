package com.opengroup.longmao.gwcommon.repository.queryFilter;

import java.util.Date;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.ActConfig;

/**
 * 【活动配置表】构造查询条件
 *
 * @version
 * @author Hermit 2017年12月12日 下午14:41:20
 */ 
@QBindEntity(entityClass = ActConfig.class)
public class ActConfigQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "configId", where = Where.equal)
	private Long configId;

	@QBindAttrField(fieldName = "activityType", where = Where.equal)
	private Short activityType;

	@QBindAttrField(fieldName = "name", where = Where.equal)
	private String name;

	@QBindAttrField(fieldName = "content", where = Where.equal)
	private String content;

	@QBindAttrField(fieldName = "picUrl", where = Where.equal)
	private String picUrl;

	@QBindAttrField(fieldName = "isEnable", where = Where.equal)
	private Short isEnable;

	@QBindAttrField(fieldName = "rewardType", where = Where.equal)
	private Short rewardType;

	@QBindAttrField(fieldName = "startTime", where = Where.greaterThanOrEqualTo)
	private Date startTime;

	@QBindAttrField(fieldName = "endTime", where = Where.lessThanOrEqualTo)
	private Date endTime;

	@QBindAttrField(fieldName = "status", where = Where.equal)
	private Short status;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getConfigId(){
		return configId;
	}

	public void setConfigId(Long configId){
		this.configId = configId;
	}

	public Short getActivityType(){
		return activityType;
	}

	public void setActivityType(Short activityType){
		this.activityType = activityType;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getContent(){
		return content;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getPicUrl(){
		return picUrl;
	}

	public void setPicUrl(String picUrl){
		this.picUrl = picUrl;
	}

	public Short getIsEnable(){
		return isEnable;
	}

	public void setIsEnable(Short isEnable){
		this.isEnable = isEnable;
	}

	public Short getRewardType(){
		return rewardType;
	}

	public void setRewardType(Short rewardType){
		this.rewardType = rewardType;
	}

	public Date getStartTime(){
		return startTime;
	}

	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}

	public Date getEndTime(){
		return endTime;
	}

	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}

	public Short getStatus(){
		return status;
	}

	public void setStatus(Short status){
		this.status = status;
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

