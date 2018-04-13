package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户渠道信息表】 持久化对象
 * @version
 * @author Hermit 2018年01月03日 上午09:59:49
 */ 
@Entity
@Table(name = "ui_user_channel") 
public class UserChannel  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "channel_id")
	private Long channelId;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "login_way")
	private Short loginWay;
	
	@Column(name = "reset_num")
	private Integer resetNum;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;

	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Integer getResetNum() {
		return resetNum;
	}

	public void setResetNum(Integer resetNum) {
		this.resetNum = resetNum;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Short getLoginWay() {
		return loginWay;
	}

	public void setLoginWay(Short loginWay) {
		this.loginWay = loginWay;
	}
	
}
