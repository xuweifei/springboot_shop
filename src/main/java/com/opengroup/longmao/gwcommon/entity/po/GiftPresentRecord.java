package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【礼物赠送信息】 持久化对象
 *
 * @version
 * @author Hermit 2017年06月05日 下午15:18:08
 */ 
@Entity
@Table(name = "co_gift_present_record") 
public class GiftPresentRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "sender")
	private String sender;

	@Column(name = "receiver")
	private String receiver;

	@Column(name = "gift_id")
	private String giftId;

	@Column(name = "live_id")
	private String liveId;

	@Column(name = "calorie")
	private Double calorie;

	@Column(name = "exp_val")
	private Integer expVal;

	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_create_user")
	private String gmtCreateUser;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	@Column(name = "gmt_modified_user")
	private String gmtModifiedUser;
	
	public GiftPresentRecord(String sender, String receiver, Double calorie) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.calorie = calorie;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getSender(){
		return sender;
	}

	public void setSender(String sender){
		this.sender = sender;
	}

	public String getReceiver(){
		return receiver;
	}

	public void setReceiver(String receiver){
		this.receiver = receiver;
	}

	public String getGiftId(){
		return giftId;
	}

	public void setGiftId(String giftId){
		this.giftId = giftId;
	}

	public String getLiveId(){
		return liveId;
	}

	public void setLiveId(String liveId){
		this.liveId = liveId;
	}

	public Double getCalorie(){
		return calorie;
	}

	public void setCalorie(Double calorie){
		this.calorie = calorie;
	}

	public Integer getExpVal(){
		return expVal;
	}

	public void setExpVal(Integer expVal){
		this.expVal = expVal;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public String getGmtCreateUser(){
		return gmtCreateUser;
	}

	public void setGmtCreateUser(String gmtCreateUser){
		this.gmtCreateUser = gmtCreateUser;
	}

	public Date getGmtModified(){
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public String getGmtModifiedUser(){
		return gmtModifiedUser;
	}

	public void setGmtModifiedUser(String gmtModifiedUser){
		this.gmtModifiedUser = gmtModifiedUser;
	}

}

