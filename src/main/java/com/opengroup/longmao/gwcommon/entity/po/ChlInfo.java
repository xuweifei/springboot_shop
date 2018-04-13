package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【测试表】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月15日 下午15:32:20
 */ 
@Entity
@Table(name = "chl_info") 
public class ChlInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "chl_id")
	private Long chlId;

	@Column(name = "chl_name")
	private String chlName;

	@Column(name = "is_enable")
	private Short isEnable;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;


	public Long getChlId(){
		return chlId;
	}

	public void setChlId(Long chlId){
		this.chlId = chlId;
	}

	public String getChlName(){
		return chlName;
	}

	public void setChlName(String chlName){
		this.chlName = chlName;
	}

	public Short getIsEnable(){
		return isEnable;
	}

	public void setIsEnable(Short isEnable){
		this.isEnable = isEnable;
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

