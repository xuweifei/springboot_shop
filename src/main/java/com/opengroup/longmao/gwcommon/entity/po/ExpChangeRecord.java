package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户经验值流水】 持久化对象
 *
 * @version
 * @author Hermit 2017年08月02日 下午16:05:51
 */ 
@Entity
@Table(name = "ui_exp_change_record") 
public class ExpChangeRecord implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -3350742994982303371L;

	@Id
	@Column(name = "exp_id")
	private Long expId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "change_type")
	private Short changeType;

	@Column(name = "change_num")
	private Long changeNum;

	@Column(name = "biz_type")
	private Short bizType;

	@Column(name = "out_biz_id")
	private String outBizId;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;


	public Long getExpId(){
		return expId;
	}

	public void setExpId(Long expId){
		this.expId = expId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public Short getChangeType(){
		return changeType;
	}

	public void setChangeType(Short changeType){
		this.changeType = changeType;
	}

	public Long getChangeNum(){
		return changeNum;
	}

	public void setChangeNum(Long changeNum){
		this.changeNum = changeNum;
	}

	public Short getBizType(){
		return bizType;
	}

	public void setBizType(Short bizType){
		this.bizType = bizType;
	}

	public String getOutBizId(){
		return outBizId;
	}

	public void setOutBizId(String outBizId){
		this.outBizId = outBizId;
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

