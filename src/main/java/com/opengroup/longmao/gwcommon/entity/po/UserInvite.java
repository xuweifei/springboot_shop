package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 【用户邀请关系表】 持久化对象
 * @version
 * @author Hermit 2018年01月03日 上午09:59:49
 */ 
@Entity
@Table(name = "ui_user_invite") 
public class UserInvite  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "phone_num")
	private String phoneNum;
	
	@Column(name = "invited_user_id")
	private Long invitedUserId;
	
	@Column(name = "push_id")
	private String pushId;
	
	@Column(name = "head_url")
	private String headUrl;
	
	@Column(name = "invite_reward")
	private Integer inviteReward;
	
	@Column(name = "invited_reward")
	private Integer invitedReward;
	
	@Column(name = "is_receivable")
	private Short isReceivable;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Long getInvitedUserId() {
		return invitedUserId;
	}

	public void setInvitedUserId(Long invitedUserId) {
		this.invitedUserId = invitedUserId;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public Integer getInviteReward() {
		return inviteReward;
	}

	public void setInviteReward(Integer inviteReward) {
		this.inviteReward = inviteReward;
	}

	public Integer getInvitedReward() {
		return invitedReward;
	}

	public void setInvitedReward(Integer invitedReward) {
		this.invitedReward = invitedReward;
	}

	public Short getIsReceivable() {
		return isReceivable;
	}

	public void setIsReceivable(Short isReceivable) {
		this.isReceivable = isReceivable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public UserInvite() {
		super();
	}

	public UserInvite(Long id, Long userId) {
		super();
		this.id = id;
		this.userId = userId;
	}

	
}
