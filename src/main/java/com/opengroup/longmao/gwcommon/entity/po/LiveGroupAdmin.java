package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 【直播群组管理员表】 持久化对象
 *
 * @version
 * @author Hermit 2017年07月13日 上午09:50:49
 */ 
@Entity
@Table(name = "live_group_admin") 
@ApiModel(value = "直播群组管理员表对象",description = "LiveGroupAdmin")
public class LiveGroupAdmin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "群信息id",required = true)
	@Column(name = "admin_id")
	private Long adminId;

	@ApiModelProperty(value = "群组id",required = true)
	@Column(name = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "主播id",required = true)
	@Column(name = "user_id")
	private Long userId;

	@ApiModelProperty(value = "用户id",required = true)
	@Column(name = "live_user_id")
	private Long liveUserId;

	@ApiModelProperty(value = "昵称",required = true)
	@Column(name = "nick_name")
	private String nickName;

	@ApiModelProperty(value = "头像",required = true)
	@Column(name = "phone_id")
	private String phoneId;
	
	@Column(name = "grade")
	private Integer grade;

	@ApiModelProperty(value = "创建时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "修改时间",required = true)
	@Column(name = "utime")
	private Integer utime;

	@ApiModelProperty(value = "是否删除(1-正常,-1-删除)",required = true)
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getAdminId(){
		return adminId;
	}

	public void setAdminId(Long adminId){
		this.adminId = adminId;
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

