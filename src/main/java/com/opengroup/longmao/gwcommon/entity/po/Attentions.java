package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户关注/粉丝】 持久化对象
 *
 * @version
 * @author Hermit 2017年06月09日 下午16:05:21
 */ 
@Entity
@Table(name = "ui_attentions") 
public class Attentions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "anchoruser_id")
	private String anchoruserId;

	@Column(name = "attentions_state")
	private Short attentionsState;

	@Column(name = "is_attention")
	private Short isAttention;

	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_create_user")
	private String gmtCreateUser;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	@Column(name = "gmt_modified_user")
	private String gmtModifiedUser;


	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getAnchoruserId(){
		return anchoruserId;
	}

	public void setAnchoruserId(String anchoruserId){
		this.anchoruserId = anchoruserId;
	}

	public Short getAttentionsState(){
		return attentionsState;
	}

	public void setAttentionsState(Short attentionsState){
		this.attentionsState = attentionsState;
	}

	public Short getIsAttention(){
		return isAttention;
	}

	public void setIsAttention(Short isAttention){
		this.isAttention = isAttention;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public String getGmtCreateUser(){
		return gmtCreateUser;
	}

	public void setGmtCreateUser(String gmtCreateUser){
		this.gmtCreateUser = gmtCreateUser;
	}

	public Date getGmtModified(){
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public String getGmtModifiedUser(){
		return gmtModifiedUser;
	}

	public void setGmtModifiedUser(String gmtModifiedUser){
		this.gmtModifiedUser = gmtModifiedUser;
	}

}

