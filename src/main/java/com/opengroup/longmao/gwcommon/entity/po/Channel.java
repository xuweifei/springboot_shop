package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * channel 实体类
 * @author xwf 2017年11月30日
 */ 
@Entity
@Table(name = "channel_channel") 
public class Channel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "channel_id")
	private Long channelId;

	@Column(name = "channel_name")
	private String channelName;
	
	@Column(name = "admin_mobile")
	private String adminMobile;
	
	// 1自营、2代理
	@Column(name = "channel_type")
	private Short channelType;
	
	// 1一级代理、2二级代理
	@Column(name = "channel_level")
	private Short channelLevel;
	
	@Column(name = "super_channel")
	private Long superChannel;
	
	@Column(name = "channel_bar_code_url")
	private String channelBarCodeUrl;// 渠道包二维码
	
	@Column(name = "channel_spread_url")
	private String channelSpreadUrl;// 推广链接地址
	
	@Column(name = "channel_download_url")
	private String channelDownloadUrl;// apk包地址
	
	@Column(name = "remark")
	private String remark;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Short getChannelType() {
		return channelType;
	}

	public void setChannelType(Short channelType) {
		this.channelType = channelType;
	}

	public Short getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(Short channelLevel) {
		this.channelLevel = channelLevel;
	}

	public String getChannelBarCodeUrl() {
		return channelBarCodeUrl;
	}

	public void setChannelBarCodeUrl(String channelBarCodeUrl) {
		this.channelBarCodeUrl = channelBarCodeUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getSuperChannel() {
		return superChannel;
	}

	public void setSuperChannel(Long superChannel) {
		this.superChannel = superChannel;
	}

	public String getAdminMobile() {
		return adminMobile;
	}

	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}

	public String getChannelSpreadUrl() {
		return channelSpreadUrl;
	}

	public void setChannelSpreadUrl(String channelSpreadUrl) {
		this.channelSpreadUrl = channelSpreadUrl;
	}

	public String getChannelDownloadUrl() {
		return channelDownloadUrl;
	}

	public void setChannelDownloadUrl(String channelDownloadUrl) {
		this.channelDownloadUrl = channelDownloadUrl;
	}

	
	
}
