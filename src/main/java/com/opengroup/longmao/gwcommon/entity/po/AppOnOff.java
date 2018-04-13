package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【APP系统开关】 持久化对象
 *
 * @version
 * @author Hermit 2017年06月26日 下午14:07:30
 */ 
@Entity
@Table(name = "app_on_off") 
public class AppOnOff implements Serializable {


	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -2006962106388279404L;

	@Id
	@Column(name = "app_id")
	private Long appId;

	@Column(name = "code")
	private Long code;

	@Column(name = "classify")
	private Short classify;

	@Column(name = "name")
	private String name;

	@Column(name = "val")
	private String val;

	@Column(name = "remark")
	private String remark;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;


	public Long getAppId(){
		return appId;
	}

	public void setAppId(Long appId){
		this.appId = appId;
	}

	public Long getCode(){
		return code;
	}

	public void setCode(Long code){
		this.code = code;
	}

	public Short getClassify(){
		return classify;
	}

	public void setClassify(Short classify){
		this.classify = classify;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getVal(){
		return val;
	}

	public void setVal(String val){
		this.val = val;
	}

	public String getRemark(){
		return remark;
	}

	public void setRemark(String remark){
		this.remark = remark;
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

