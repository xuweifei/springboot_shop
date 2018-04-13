package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 *【主播等级规则表实体】  
 * @author ShenZiYang 
 * @date 2018年3月13日 下午1:28:11
 */
@Entity
@Table(name = "anchor_grade_rule") 
public class AnchorGradeRule implements Serializable{

	private static final long serialVersionUID = -3830067926427835203L;
	
	/**
	 * 规则ID
	 */
	@Id
	@Column(name = "id")
	private Long id;
	
	/**
	 * 主播等级
	 */
	@Column(name = "grade")
	private Short grade;
	
	/**
	 * 等级经验值最小值
	 */
	@Column(name = "min_val")
	private Long minVal;
	
	/**
	 * 等级经验值最大值
	 */
	@Column(name = "max_val")
	private Long maxVal;
	
	/**
	 * 等级别名
	 */
	@Column(name = "alias")
	private String alias;
	
	/**
	 * 等级说明
	 */
	@Column(name = "note")
	private String note;
	
	/**
	 * 是否最大值，0否，1是
	 */
	@Column(name = "is_max")
	private Short isMax;
	
	/**
	 * 创建时间
	 */
	@Column(name = "ctime")
	private Integer ctime;
	
	/**
	 * 修改时间
	 */
	@Column(name = "utime")
	private Integer utime;
	
	/**
	 * 是否删除(1-正常,-1-删除)
	 */
	@Column(name = "is_delete")
	private Short isDelete;
	
	//getter and setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getGrade() {
		return grade;
	}

	public void setGrade(Short grade) {
		this.grade = grade;
	}

	public Long getMinVal() {
		return minVal;
	}

	public void setMinVal(Long minVal) {
		this.minVal = minVal;
	}

	public Long getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(Long maxVal) {
		this.maxVal = maxVal;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Short getIsMax() {
		return isMax;
	}

	public void setIsMax(Short isMax) {
		this.isMax = isMax;
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
