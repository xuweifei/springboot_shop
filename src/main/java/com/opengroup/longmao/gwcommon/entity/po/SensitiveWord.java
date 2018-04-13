package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 【敏感词汇】 持久化对象
 *
 * @version
 * @author Hermit 2017年05月31日 上午11:08:27
 */ 
@Entity
@Table(name = "sensitive_word") 
public class SensitiveWord implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 832731418939029210L;

	@Id
	@ApiModelProperty(value = "敏感词汇ID",required = true)
	@Column(name = "w_id")
	private Long wId;

	@ApiModelProperty(value = "词汇类别(0其他/1脏话/2色情/3政治/4领导/5民生)",required = true)
	@Column(name = "word_type")
	private Short wordType;

	@ApiModelProperty(value = "敏感词汇",required = true)
	@Column(name = "word")
	private String word;

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

	public Long getwId() {
		return wId;
	}

	public void setwId(Long wId) {
		this.wId = wId;
	}

	public Short getWordType() {
		return wordType;
	}

	public void setWordType(Short wordType) {
		this.wordType = wordType;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
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

