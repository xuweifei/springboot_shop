package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【龙猫豆流水】 持久化对象
 *
 * @version
 * @author Hermit 2017年04月12日 下午15:44:56
 */ 
@Entity
@Table(name = "ui_bean_change_record") 
public class BeanChangeRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "ago_num")
	private Double agoNum;

	@Column(name = "change_type")
	private Integer changeType;

	@Column(name = "change_num")
	private Double changeNum;

	@Column(name = "biz_type")
	private Integer bizType;

	@Column(name = "out_biz_id")
	private String outBizId;

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

	public Double getAgoNum(){
		return agoNum;
	}

	public void setAgoNum(Double agoNum){
		this.agoNum = agoNum;
	}

	public Integer getChangeType(){
		return changeType;
	}

	public void setChangeType(Integer changeType){
		this.changeType = changeType;
	}

	public Double getChangeNum(){
		return changeNum;
	}

	public void setChangeNum(Double changeNum){
		this.changeNum = changeNum;
	}

	public Integer getBizType(){
		return bizType;
	}

	public void setBizType(Integer bizType){
		this.bizType = bizType;
	}

	public String getOutBizId(){
		return outBizId;
	}

	public void setOutBizId(String outBizId){
		this.outBizId = outBizId;
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

