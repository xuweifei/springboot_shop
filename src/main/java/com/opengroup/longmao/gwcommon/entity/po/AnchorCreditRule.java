package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【[全民主播]信誉等级提成规则】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月23日 上午09:06:58
 */ 
@Entity
@Table(name = "anchor_credit_rule") 
public class AnchorCreditRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "grade")
	private String grade;

	@Column(name = "min_val")
	private Short minVal;

	@Column(name = "max_val")
	private Short maxVal;

	@Column(name = "dist_ratio")
	private Short distRatio;

	@Column(name = "remark")
	private String remark;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrade(){
		return grade;
	}

	public void setGrade(String grade){
		this.grade = grade;
	}

	public Short getMinVal(){
		return minVal;
	}

	public void setMinVal(Short minVal){
		this.minVal = minVal;
	}

	public Short getMaxVal(){
		return maxVal;
	}

	public void setMaxVal(Short maxVal){
		this.maxVal = maxVal;
	}

	public Short getDistRatio(){
		return distRatio;
	}

	public void setDistRatio(Short distRatio){
		this.distRatio = distRatio;
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

