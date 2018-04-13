package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 【用户身份认证表】 持久化对象
 *
 * @version
 * @author Hermit 2018年01月25日 下午16:40:59
 */ 
@Entity
@Table(name = "user_identity_auth") 
@ApiModel(value = "用户身份认证表对象",description = "UserIdentityAuth")
public class UserIdentityAuth implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 8047062572370315434L;

	@Id
	@ApiModelProperty(value = "身份认证ID",required = true)
	@Column(name = "auth_id")
	private Long authId;

	@ApiModelProperty(value = "用户UserID",required = false)
	@Column(name = "user_id")
	private Long userId;

	@ApiModelProperty(value = "认证身份0:普通用户;1:主播;2:企业;3托号;10:机器人",required = false)
	@Column(name = "auth_identity")
	private Short authIdentity;

	@ApiModelProperty(value = "身份认证审核状态:0审核中;1通过;2未通过",required = false)
	@Column(name = "auth_status")
	private Short authStatus;

	@ApiModelProperty(value = "真实姓名",required = false)
	@Column(name = "real_name")
	private String realName;
	
	@ApiModelProperty(value = "用户手机号码",required = false)
	@Column(name = "mobile")
	private String mobile;

	@ApiModelProperty(value = "身份证号码",required = false)
	@Column(name = "id_card")
	private String idCard;

	@ApiModelProperty(value = "身份证正面照",required = false)
	@Column(name = "front_identity_url")
	private String frontIdentityUrl;

	@ApiModelProperty(value = "身份证反面照",required = false)
	@Column(name = "opposite_identity_url")
	private String oppositeIdentityUrl;

	@ApiModelProperty(value = "创建时间",required = false)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "修改时间",required = false)
	@Column(name = "utime")
	private Integer utime;
	
	@ApiModelProperty(value = "认证备注",required = false)
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty(value = "是否删除(1-正常,-1-删除)",required = false)
	@Column(name = "is_delete")
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getIsDelete(){
		return isDelete;
	}

	public void setIsDelete(Short isDelete){
		this.isDelete = isDelete;
	}

}

