package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 *【工会实体对象】  
 * @author ShenZiYang 
 * @date 2018年3月14日 下午3:57:46
 */
@Entity
@Table(name = "broker_confraternity")
@ApiModel(value = "工会实体对象", description = "BrokerConfraternity")
public class BrokerConfraternity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "confraternity_id")
	private Long confraternityId;

	@Column(name = "broker_id")
	private Long brokerId;
	
	@Column(name = "remark")
	private String remark;
	
	@ApiModelProperty(value = "工会name", required = true)
	@Column(name = "confraternity_name")
	private String confraternityName;

	@ApiModelProperty(value = "工会类型:1个人、2企业3、组织", required = true)
	@Column(name = "confraternity_type")
	private Short confraternityType;
	
	// 公会头像
	@Column(name = "conf_head_url")
	private String confHeadUrl;

	@ApiModelProperty(value = "会长姓名", required = true)
	@Column(name = "leader_name")
	private String leaderName;

	@ApiModelProperty(value = "会长电话", required = true)
	@Column(name = "leader_mobile")
	private String leaderMobile;

	@ApiModelProperty(value = "会长身份证号码", required = true)
	@Column(name = "leader_identity_num")
	private String leaderIdentityNum;

	@ApiModelProperty(value = "会长身份证正面照片", required = true)
	@Column(name = "front_identity_url")
	private String frontIdentityUrl;

	@ApiModelProperty(value = "会长身份证反面照片", required = true)
	@Column(name = "opposite_identity_url")
	private String oppositeIdentityUrl;

	@ApiModelProperty(value = "营业执照url", required = false)
	@Column(name = "business_licence_url")
	private String businessLicenceUrl;

	@ApiModelProperty(value = "营业执照注册号", required = false)
	@Column(name = "business_licence_num")
	private String businessLicenceNum;

	@ApiModelProperty(value = "企业名称", required = false)
	@Column(name = "company_name")
	private String companyName;

	@ApiModelProperty(value = "组织机构代码证url", required = false)
	@Column(name = "organization_certificate_url")
	private String organizationCertificateUrl;

	@ApiModelProperty(value = "组织机构代码号", required = false)
	@Column(name = "organization_code")
	private String organizationCode;

	@ApiModelProperty(value = "组织机构名称", required = false)
	@Column(name = "organization_name")
	private String organizationName;

	@ApiModelProperty(value = "审核状态:1审核中、2审核通过、3审核不通过", required = true)
	@Column(name = "status")
	private Short status;

	@ApiModelProperty(value = "创建时间", required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "修改时间", required = true)
	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;
	
	
	@Column(name = "is_enable")
	private Short isEnable;

	
	
	public Short getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConfHeadUrl() {
		return confHeadUrl;
	}

	public void setConfHeadUrl(String confHeadUrl) {
		this.confHeadUrl = confHeadUrl;
	}


	public BrokerConfraternity() {
		super();
	}

	public String getConfraternityName() {
		return confraternityName;
	}

	public void setConfraternityName(String confraternityName) {
		this.confraternityName = confraternityName;
	}

	public Short getConfraternityType() {
		return confraternityType;
	}

	public void setConfraternityType(Short confraternityType) {
		this.confraternityType = confraternityType;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getLeaderMobile() {
		return leaderMobile;
	}

	public void setLeaderMobile(String leaderMobile) {
		this.leaderMobile = leaderMobile;
	}

	public String getLeaderIdentityNum() {
		return leaderIdentityNum;
	}

	public void setLeaderIdentityNum(String leaderIdentityNum) {
		this.leaderIdentityNum = leaderIdentityNum;
	}

	public String getFrontIdentityUrl() {
		return frontIdentityUrl;
	}

	public void setFrontIdentityUrl(String frontIdentityUrl) {
		this.frontIdentityUrl = frontIdentityUrl;
	}

	public String getOppositeIdentityUrl() {
		return oppositeIdentityUrl;
	}

	public void setOppositeIdentityUrl(String oppositeIdentityUrl) {
		this.oppositeIdentityUrl = oppositeIdentityUrl;
	}

	public String getBusinessLicenceUrl() {
		return businessLicenceUrl;
	}

	public void setBusinessLicenceUrl(String businessLicenceUrl) {
		this.businessLicenceUrl = businessLicenceUrl;
	}

	public String getBusinessLicenceNum() {
		return businessLicenceNum;
	}

	public void setBusinessLicenceNum(String businessLicenceNum) {
		this.businessLicenceNum = businessLicenceNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getOrganizationCertificateUrl() {
		return organizationCertificateUrl;
	}

	public void setOrganizationCertificateUrl(String organizationCertificateUrl) {
		this.organizationCertificateUrl = organizationCertificateUrl;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

	public Long getConfraternityId() {
		return confraternityId;
	}

	public void setConfraternityId(Long confraternityId) {
		this.confraternityId = confraternityId;
	}

	public Long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}



}
