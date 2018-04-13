package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【靓号】 持久化对象
 *
 * @version
 * @author Hermit 2017年05月09日 下午15:08:22
 */
@Entity
@Table(name = "good_number")
public class GoodNumber implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5214648075488064244L;

	@Id
	@Column(name = "good_id")
	private Long goodId;

	@Column(name = "number")
	private Long number;

	@Column(name = "remark")
	private String remark;

	@Column(name = "is_retain")
	private Short isRetain;

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getIsRetain() {
		return isRetain;
	}

	public void setIsRetain(Short isRetain) {
		this.isRetain = isRetain;
	}

}
