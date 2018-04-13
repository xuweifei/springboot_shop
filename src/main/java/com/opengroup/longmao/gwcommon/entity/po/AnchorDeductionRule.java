package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【主播减分规则】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月23日 上午09:04:56
 */ 
@Entity
@Table(name = "anchor_deduction_rule") 
public class AnchorDeductionRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "deduction_item")
	private String deductionItem;

	@Column(name = "condition")
	private String condition;

	@Column(name = "credit_val")
	private String creditVal;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeductionItem(){
		return deductionItem;
	}

	public void setDeductionItem(String deductionItem){
		this.deductionItem = deductionItem;
	}

	public String getCondition(){
		return condition;
	}

	public void setCondition(String condition){
		this.condition = condition;
	}

	public String getCreditVal(){
		return creditVal;
	}

	public void setCreditVal(String creditVal){
		this.creditVal = creditVal;
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

