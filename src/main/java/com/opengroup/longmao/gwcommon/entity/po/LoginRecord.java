package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户登录记录】 持久化对象
 *
 * @version
 * @author Hermit 2017年04月24日 下午18:19:03
 */
@Entity
@Table(name = "ui_loginrecord")
public class LoginRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "chl_id")
	private String chlId;

	@Column(name = "login_way")
	private Short loginWay;

	@Column(name = "login_user")
	private String loginUser;

	@Column(name = "login_last_time")
	private Date loginLastTime;

	@Column(name = "login_site")
	private String loginSite;
	
	@Column(name = "is_first")
	private Short isFirst;

	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_create_user")
	private String gmtCreateUser;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	@Column(name = "gmt_modified_user")
	private String gmtModifiedUser;
	
	@Column(name = "is_delete")
	private Short isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public Short getLoginWay() {
		return loginWay;
	}

	public void setLoginWay(Short loginWay) {
		this.loginWay = loginWay;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public Date getLoginLastTime() {
		return loginLastTime;
	}

	public void setLoginLastTime(Date loginLastTime) {
		this.loginLastTime = loginLastTime;
	}

	public String getLoginSite() {
		return loginSite;
	}

	public void setLoginSite(String loginSite) {
		this.loginSite = loginSite;
	}
	
	public Short getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Short isFirst) {
		this.isFirst = isFirst;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtCreateUser() {
		return gmtCreateUser;
	}

	public void setGmtCreateUser(String gmtCreateUser) {
		this.gmtCreateUser = gmtCreateUser;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getGmtModifiedUser() {
		return gmtModifiedUser;
	}

	public void setGmtModifiedUser(String gmtModifiedUser) {
		this.gmtModifiedUser = gmtModifiedUser;
	}
	
	public Short getIsDelete(){
		return isDelete;
	}

	public void setIsDelete(Short isDelete){
		this.isDelete = isDelete;
	}

}
