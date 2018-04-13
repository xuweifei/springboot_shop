package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 【直播群组成员禁言列表】 持久化对象
 *
 * @version
 * @author Hermit 2017年07月14日 下午15:13:09
 */ 
@Entity
@Table(name = "live_member_sit") 
@ApiModel(value = "直播群组成员禁言列表对象",description = "LiveMemberSit")
public class LiveMemberSit implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 4260926459257214448L;

	@Id
	@ApiModelProperty(value = "群信息id",required = true)
	@Column(name = "sit_id")
	private Long sitId;

	@ApiModelProperty(value = "群组id",required = true)
	@Column(name = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "主播id",required = true)
	@Column(name = "user_id")
	private Long userId;

	@ApiModelProperty(value = "用户id",required = true)
	@Column(name = "live_user_id")
	private Long liveUserId;

	@ApiModelProperty(value = "昵称",required = false)
	@Column(name = "nick_name")
	private String nickName;

	@ApiModelProperty(value = "头像",required = false)
	@Column(name = "phone_id")
	private String phoneId;
	
	@ApiModelProperty(value = "用户等级",required = false)
	@Column(name = "grade")
	private Integer grade;

	@ApiModelProperty(value = "是否禁言 1是  2否",required = true)
	@Column(name = "forbid_status")
	private Short forbidStatus;

	@ApiModelProperty(value = "禁言时间",required = true)
	@Column(name = "shutup_time")
	private Integer shutupTime;

	@ApiModelProperty(value = "创建时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "修改时间",required = true)
	@Column(name = "utime")
	private Integer utime;

	@ApiModelProperty(value = "是否删除(1-正常,-1-删除)",required = true)
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getSitId(){
		return sitId;
	}

	public void setSitId(Long sitId){
		this.sitId = sitId;
	}

	public Long getGroupId(){
		return groupId;
	}

	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getLiveUserId(){
		return liveUserId;
	}

	public void setLiveUserId(Long liveUserId){
		this.liveUserId = liveUserId;
	}

	public String getNickName(){
		return nickName;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getPhoneId(){
		return phoneId;
	}

	public void setPhoneId(String phoneId){
		this.phoneId = phoneId;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Short getForbidStatus(){
		return forbidStatus;
	}

	public void setForbidStatus(Short forbidStatus){
		this.forbidStatus = forbidStatus;
	}

	public Integer getShutupTime(){
		return shutupTime;
	}

	public void setShutupTime(Integer shutupTime){
		this.shutupTime = shutupTime;
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

