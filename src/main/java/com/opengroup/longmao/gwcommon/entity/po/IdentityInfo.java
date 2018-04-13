package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 【主播直播认证、转账提现】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月30日 下午16:15:53
 */ 
@Entity
@Table(name = "ui_identity") 
public class IdentityInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -6348814282354613036L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "anchor_type")
	private Short anchorType;
	
	@ApiModelProperty(value = "直播版块:1推荐/2娱乐",required = false)
	@Column(name = "live_forum")
	private Short liveForum;

	@Column(name = "real_name")
	private String realName;

	@Column(name = "id_card")
	private String idCard;

	@Column(name = "id_card_url")
	private String idCardUrl;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "status")
	private Short status;

	@Column(name = "alipay_id")
	private String alipayId;

	@Column(name = "ratio")
	private String ratio;
	
	@Column(name = "islive")
	private Short isLive;
	
	@Column(name = "isguess")
	private Short isGuess;
	
	@Column(name = "opal")
	private Long opal;
	
	@Column(name = "base")
	private Long base;
	
	@Column(name = "multiple")
	private Short multiple;
	
	//信誉等级分数
	@Column(name = "credit_grade")
	private Short creditGrade;
			
	//信用等级说明
	@Column(name = "credit_grade_explain")
	private String creditGradeExplain;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "gmt_create")
	private Integer gmtCreate;

	@Column(name = "gmt_create_user")
	private String gmtCreateUser;

	@Column(name = "gmt_modified")
	private Integer gmtModified;

	@Column(name = "gmt_modified_user")
	private String gmtModifiedUser;
	
	//经验值 add by szy 2018.3.13
	@Column(name = "experience")
	private Long experience;
	
	//主播等级(默认1级) add by szy 2018.3.13
	@Column(name = "grade")
	private Integer grade;
	
	//所属公会ID add by szy 2018.3.14
	@Column(name = "confraternity_id")
	private Long confraternityId;
	
	
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public Short getAnchorType() {
		return anchorType;
	}

	public void setAnchorType(Short anchorType) {
		this.anchorType = anchorType;
	}

	public Short getLiveForum() {
		return liveForum;
	}

	public void setLiveForum(Short liveForum) {
		this.liveForum = liveForum;
	}

	public String getRealName(){
		return realName;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getIdCard(){
		return idCard;
	}

	public void setIdCard(String idCard){
		this.idCard = idCard;
	}

	public String getIdCardUrl(){
		return idCardUrl;
	}

	public void setIdCardUrl(String idCardUrl){
		this.idCardUrl = idCardUrl;
	}

	public String getMobile(){
		return mobile;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public Short getStatus(){
		return status;
	}

	public void setStatus(Short status){
		this.status = status;
	}

	public String getAlipayId(){
		return alipayId;
	}

	public void setAlipayId(String alipayId){
		this.alipayId = alipayId;
	}

	public String getRatio(){
		return ratio;
	}

	public void setRatio(String ratio){
		this.ratio = ratio;
	}
	
	public Short getIsLive() {
		return isLive;
	}

	public void setIsLive(Short isLive) {
		this.isLive = isLive;
	}
	
	public Short getIsGuess() {
		return isGuess;
	}

	public void setIsGuess(Short isGuess) {
		this.isGuess = isGuess;
	}

	public Long getOpal() {
		return opal;
	}

	public void setOpal(Long opal) {
		this.opal = opal;
	}

	public Long getBase() {
		return base;
	}

	public void setBase(Long base) {
		this.base = base;
	}

	public Short getMultiple() {
		return multiple;
	}

	public void setMultiple(Short multiple) {
		this.multiple = multiple;
	}

	public Short getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(Short creditGrade) {
		this.creditGrade = creditGrade;
	}

	public String getCreditGradeExplain() {
		return creditGradeExplain;
	}

	public void setCreditGradeExplain(String creditGradeExplain) {
		this.creditGradeExplain = creditGradeExplain;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtCreate(Integer gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public String getGmtCreateUser(){
		return gmtCreateUser;
	}

	public void setGmtCreateUser(String gmtCreateUser){
		this.gmtCreateUser = gmtCreateUser;
	}

	public Integer getGmtModified(){
		return gmtModified;
	}

	public void setGmtModified(Integer gmtModified){
		this.gmtModified = gmtModified;
	}

	public String getGmtModifiedUser(){
		return gmtModifiedUser;
	}

	public void setGmtModifiedUser(String gmtModifiedUser){
		this.gmtModifiedUser = gmtModifiedUser;
	}

	public Long getExperience() {
		return experience;
	}

	public void setExperience(Long experience) {
		this.experience = experience;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Long getConfraternityId() {
		return confraternityId;
	}

	public void setConfraternityId(Long confraternityId) {
		this.confraternityId = confraternityId;
	}
	
	
	
}

