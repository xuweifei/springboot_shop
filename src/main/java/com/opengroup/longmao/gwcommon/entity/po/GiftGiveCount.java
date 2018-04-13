package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户礼物赠送统计】 持久化对象
 *
 * @version
 * @author Hermit 2017年08月28日 下午16:01:13
 */ 
@Entity
@Table(name = "gift_give_count") 
public class GiftGiveCount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "sender")
	private Long sender;

	@Column(name = "receiver")
	private Long receiver;

	@Column(name = "live_id")
	private Long liveId;

	@Column(name = "gift_count")
	private Integer giftCount;

	@Column(name = "coin")
	private Long coin;

	@Column(name = "calorie")
	private Long calorie;

	@Column(name = "exp_val")
	private Long expVal;

	@Column(name = "ctime")
	private Integer ctime;

	@Column(name = "utime")
	private Integer utime;

	@Column(name = "is_delete")
	private Short isDelete;


	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getSender(){
		return sender;
	}

	public void setSender(Long sender){
		this.sender = sender;
	}

	public Long getReceiver(){
		return receiver;
	}

	public void setReceiver(Long receiver){
		this.receiver = receiver;
	}

	public Long getLiveId(){
		return liveId;
	}

	public void setLiveId(Long liveId){
		this.liveId = liveId;
	}

	public Integer getGiftCount(){
		return giftCount;
	}

	public void setGiftCount(Integer giftCount){
		this.giftCount = giftCount;
	}

	public Long getCoin(){
		return coin;
	}

	public void setCoin(Long coin){
		this.coin = coin;
	}

	public Long getCalorie(){
		return calorie;
	}

	public void setCalorie(Long calorie){
		this.calorie = calorie;
	}

	public Long getExpVal(){
		return expVal;
	}

	public void setExpVal(Long expVal){
		this.expVal = expVal;
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

