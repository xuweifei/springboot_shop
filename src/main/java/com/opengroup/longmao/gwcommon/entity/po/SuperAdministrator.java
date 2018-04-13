package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 【APP超管表】 持久化对象
 *
 * @version
 * @author Hermit 2017年11月27日 上午10:42:52
 */ 
@Entity
@Table(name = "super_administrator") 
@ApiModel(value = "APP超管表对象",description = "SuperAdministrator")
public class SuperAdministrator implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 5200204022160636537L;

	@Id
	@ApiModelProperty(value = "超级app权限id",required = true)
	@Column(name = "super_administrator_id")
	private Long superAdministratorId;

	@ApiModelProperty(value = "用户Id",required = true)
	@Column(name = "user_id")
	private Long userId;

	@ApiModelProperty(value = "创建时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "修改时间",required = true)
	@Column(name = "utime")
	private Integer utime;

	@ApiModelProperty(value = "是否删除-1已删除,1未删除",required = true)
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getSuperAdministratorId(){
		return superAdministratorId;
	}

	public void setSuperAdministratorId(Long superAdministratorId){
		this.superAdministratorId = superAdministratorId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
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

