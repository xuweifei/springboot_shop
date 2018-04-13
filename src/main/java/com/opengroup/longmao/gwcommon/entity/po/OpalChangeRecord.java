package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【主播猫眼石流水】 持久化对象
 *
 * @version
 * @author Hermit 2017年08月02日 下午14:25:54
 */
@Entity
@Table(name = "ui_opal_change_record")
public class OpalChangeRecord implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8119929780292178873L;

	@Id
	@Column(name = "opal_id")
	private Long opalId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "change_type")
	private Short changeType;

	@Column(name = "change_num")
	private Long changeNum;

	@Column(name = "biz_type")
	private Short bizType;

	@Column(name = "out_biz_id")
	private Long outBizId;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;
	
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getOpalId() {
		return opalId;
	}

	public void setOpalId(Long opalId) {
		this.opalId = opalId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Short getChangeType() {
		return changeType;
	}

	public void setChangeType(Short changeType) {
		this.changeType = changeType;
	}

	public Long getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Long changeNum) {
		this.changeNum = changeNum;
	}

	public Short getBizType() {
		return bizType;
	}

	public void setBizType(Short bizType) {
		this.bizType = bizType;
	}

	public Long getOutBizId() {
		return outBizId;
	}

	public void setOutBizId(Long outBizId) {
		this.outBizId = outBizId;
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
