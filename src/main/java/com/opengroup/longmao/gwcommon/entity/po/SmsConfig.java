package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 *(龙猫运营后台短信配置) 
 * @ClassName SmsConfig 
 * @author ShenZiYang 
 * @date 2017年12月27日 上午11:24:46
 */
@Entity
@Table(name ="lm_sms_config")
public class SmsConfig implements Serializable{
	
	private static final long serialVersionUID = -9037159768839649063L;

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "sms_id")
	private Long smsId;
	
	/**
	 * 短信名称
	 */
	@Column(name = "sms_name")
	private String smsName;
	
	/**
	 * 短信标识
	 */
	@Column(name = "sms_mark")
	private String smsMark;
	
	/**
	 * 服务名称
	 */
	@Column(name = "service_name")
	private String serviceName;
	
	/**
	 * 短信模版
	 */
	@Column(name = "sms_template")
	private String smsTemplate;
	
	/**
	 * 短信收件人(手机号)
	 */
	@Column(name = "sms_addressee")
	private String smsAddressee;
	
	/**
	 * 短信接受者姓名
	 */
	@Column(name = "sms_receiver")
	private String smsReceiver;
	
	/**
	 * 状态(0：启用，1：停用)
	 */
	@Column(name = "sms_status")
	private Short smsStatus;
	
	/**
	 * 创建时间
	 */
	@Column(name = "ctime")
	private Integer ctime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "utime")
	private Integer utime;
	
	/**
	 * 是否删除(1.正常，2.删除)
	 */
	@Column(name = "is_delete")
	private Short isDelete;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public String getSmsName() {
		return smsName;
	}

	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public String getSmsAddressee() {
		return smsAddressee;
	}

	public void setSmsAddressee(String smsAddressee) {
		this.smsAddressee = smsAddressee;
	}

	public Short getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(Short smsStatus) {
		this.smsStatus = smsStatus;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSmsMark() {
		return smsMark;
	}

	public void setSmsMark(String smsMark) {
		this.smsMark = smsMark;
	}

	public String getSmsReceiver() {
		return smsReceiver;
	}

	public void setSmsReceiver(String smsReceiver) {
		this.smsReceiver = smsReceiver;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	
}
