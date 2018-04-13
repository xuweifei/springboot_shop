package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【版本控制信息】 持久化对象
 *
 * @version
 * @author Hermit 2017年06月14日 下午14:59:13
 */ 
@Entity
@Table(name = "version_info") 
public class VersionInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "chl_id")
	private Long chlId;

	@Column(name = "code")
	private String code;

	@Column(name = "ver")
	private String ver;

	@Column(name = "is_update")
	private String isUpdate;

	@Column(name = "down_url")
	private String downUrl;

	@Column(name = "ver_explain")
	private String verExplain;

	@Column(name = "remark")
	private String remark;

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

	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getVer(){
		return ver;
	}

	public void setVer(String ver){
		this.ver = ver;
	}

	public String getIsUpdate(){
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate){
		this.isUpdate = isUpdate;
	}

	public String getDownUrl(){
		return downUrl;
	}

	public void setDownUrl(String downUrl){
		this.downUrl = downUrl;
	}

	public String getVerExplain(){
		return verExplain;
	}

	public void setVerExplain(String verExplain){
		this.verExplain = verExplain;
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

