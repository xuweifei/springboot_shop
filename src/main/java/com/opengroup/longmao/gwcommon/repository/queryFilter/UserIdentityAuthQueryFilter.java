package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.UserIdentityAuth;

/**
 * 【用户身份认证表】构造查询条件
 *
 * @version
 * @author Hermit 2018年01月25日 下午16:40:59
 */ 
@QBindEntity(entityClass = UserIdentityAuth.class)
public class UserIdentityAuthQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "authId", where = Where.equal)
	private Long authId;

	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private Long userId;

	@QBindAttrField(fieldName = "authIdentity", where = Where.equal)
	private Short authIdentity;

	@QBindAttrField(fieldName = "authStatus", where = Where.equal)
	private Short authStatus;

	@QBindAttrField(fieldName = "realName", where = Where.equal)
	private String realName;

	@QBindAttrField(fieldName = "idCard", where = Where.equal)
	private String idCard;

	@QBindAttrField(fieldName = "frontIdentityUrl", where = Where.equal)
	private String frontIdentityUrl;

	@QBindAttrField(fieldName = "oppositeIdentityUrl", where = Where.equal)
	private String oppositeIdentityUrl;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getAuthId(){
		return authId;
	}

	public void setAuthId(Long authId){
		this.authId = authId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Short getAuthIdentity(){
		return authIdentity;
	}

	public void setAuthIdentity(Short authIdentity){
		this.authIdentity = authIdentity;
	}

	public Short getAuthStatus(){
		return authStatus;
	}

	public void setAuthStatus(Short authStatus){
		this.authStatus = authStatus;
	}

	public String getRealName(){
		return realName;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getIdCard(){
		return idCard;
	}

	public void setIdCard(String idCard){
		this.idCard = idCard;
	}

	public String getFrontIdentityUrl(){
		return frontIdentityUrl;
	}

	public void setFrontIdentityUrl(String frontIdentityUrl){
		this.frontIdentityUrl = frontIdentityUrl;
	}

	public String getOppositeIdentityUrl(){
		return oppositeIdentityUrl;
	}

	public void setOppositeIdentityUrl(String oppositeIdentityUrl){
		this.oppositeIdentityUrl = oppositeIdentityUrl;
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

