package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 【敏感词汇停顿符】 持久化对象
 *
 * @version
 * @author Hermit 2017年05月31日 上午11:07:59
 */ 
@Entity
@Table(name = "sensitive_pause") 
public class SensitivePause implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 2982031755230697825L;

	@Id
	@Column(name = "p_id")
	private Long pId;

	@Column(name = "symbol")
	private String symbol;
	
	@ApiModelProperty(value = "是否使用:1是;2否",required = true)
	@Column(name = "is_use")
	private Short isUse;

	@ApiModelProperty(value = "创建时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "更新时间",required = true)
	@Column(name = "utime")
	private Integer utime;

	@ApiModelProperty(value = "是否删除(1正常,-1-删除)",required = true)
	@Column(name = "is_delete")
	private Short isDelete;


	public Long getPId(){
		return pId;
	}

	public void setPId(Long pId){
		this.pId = pId;
	}

	public String getSymbol(){
		return symbol;
	}

	public void setSymbol(String symbol){
		this.symbol = symbol;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public Short getIsUse() {
		return isUse;
	}

	public void setIsUse(Short isUse) {
		this.isUse = isUse;
	}

	public Integer getCtime() {
		return ctime;
	}

	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}

	public Integer getUtime() {
		return utime;
	}

	public void setUtime(Integer utime) {
		this.utime = utime;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

}

