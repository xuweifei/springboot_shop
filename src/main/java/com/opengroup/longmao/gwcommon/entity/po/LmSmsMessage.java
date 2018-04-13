package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【短信记录表】 持久化对象
 *
 * @version
 * @author Hermit 2017年04月27日 下午16:14:13
 */ 
@Entity
@Table(name = "lm_sms_message") 
public class LmSmsMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "sms_id")
	private Long smsId;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "content")
	private String content;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;


	public Long getSmsId(){
		return smsId;
	}

	public void setSmsId(Long smsId){
		this.smsId = smsId;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMobile(){
		return mobile;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getContent(){
		return content;
	}

	public void setContent(String content){
		this.content = content;
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

