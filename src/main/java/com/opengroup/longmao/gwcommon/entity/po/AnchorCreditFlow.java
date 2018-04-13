package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【主播信誉等流水】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月23日 上午09:08:34
 */ 
@Entity
@Table(name = "anchor_credit_flow") 
public class AnchorCreditFlow implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "anchor_id")
	private Long anchorId;

	@Column(name = "add_or_del")
	private Short addOrDel;

	@Column(name = "remark")
	private String remark;

	@Column(name = "val")
	private Short val;

	@Column(name = "rule_id")
	private Integer ruleId;

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

	public Long getAnchorId(){
		return anchorId;
	}

	public void setAnchorId(Long anchorId){
		this.anchorId = anchorId;
	}

	public Short getAddOrDel(){
		return addOrDel;
	}

	public void setAddOrDel(Short addOrDel){
		this.addOrDel = addOrDel;
	}

	public String getRemark(){
		return remark;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public Short getVal(){
		return val;
	}

	public void setVal(Short val){
		this.val = val;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
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

