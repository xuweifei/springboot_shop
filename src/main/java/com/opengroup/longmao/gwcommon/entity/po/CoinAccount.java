package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【龙猫币账户】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月15日 下午15:49:16
 */ 
@Entity
@Table(name = "ui_coin_account") 
public class CoinAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "lm_coin_num")
	private Double lmCoinNum;

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

	public Double getLmCoinNum(){
		return lmCoinNum;
	}

	public void setLmCoinNum(Double lmCoinNum){
		this.lmCoinNum = lmCoinNum;
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

