package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【活动配置表】 持久化对象
 *
 * @version
 * @author Hermit 2017年12月12日 下午14:41:20
 */ 
@Entity
@Table(name = "act_config") 
@ApiModel(value = "活动配置表对象",description = "ActConfig")
public class ActConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "活动配置id",required = true)
	@Column(name = "config_id")
	private Long configId;
	
	@ApiModelProperty(value = "活动类型 0龙猫豆抽奖 1领红包 2送红包 4双蛋活动 5充值送",required = true)
	@Column(name = "activity_type")
	private Short activityType;

	@ApiModelProperty(value = "活动名称",required = true)
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "活动内容",required = true)
	@Column(name = "content")
	private String content;

	@ApiModelProperty(value = "活动图片URL",required = true)
	@Column(name = "pic_url")
	private String picUrl;

	@ApiModelProperty(value = "活动奖励类型 1满减 2满送 3抽奖 4其他",required = true)
	@Column(name = "reward_type")
	private Short rewardType;

	@ApiModelProperty(value = "开始时间",required = true)
	@Column(name = "start_time")
	private Date startTime;

	@ApiModelProperty(value = "结束时间",required = true)
	@Column(name = "end_time")
	private Date endTime;

	@ApiModelProperty(value = "开关状态：1开 2关",required = true)
	@Column(name = "status")
	private Short status;

	@ApiModelProperty(value = "创建时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "更新时间",required = true)
	@Column(name = "utime")
	private Integer utime;

	@ApiModelProperty(value = "是否删除：1正常、-1删除",required = true)
	@Column(name = "is_delete")
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

