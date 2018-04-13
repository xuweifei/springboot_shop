package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【第三方授权信息】 持久化对象
 * @version
 * @author Hermit 2017年07月20日 下午17:14:06
 */ 
@Entity
@Table(name = "auth_info") 
public class AuthInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 292297349881729173L;

	@Id
	@Column(name = "auth_id")
	private Long authId;

	@Column(name = "app_id")
	private String appId;

	@Column(name = "access_token")
	private String accessToken;
	
	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "invalid_time")
	private Integer invalidTime;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;


	public Long getAuthId(){
		return authId;
	}

	public void setAuthId(Long authId){
		this.authId = authId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getInvalidTime(){
		return invalidTime;
	}

	public void setInvalidTime(Integer invalidTime){
		this.invalidTime = invalidTime;
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

