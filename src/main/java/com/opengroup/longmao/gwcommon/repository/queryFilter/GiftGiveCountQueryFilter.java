package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.GiftGiveCount;

/**
 * 【用户礼物赠送统计】构造查询条件
 *
 * @version
 * @author Hermit 2017年08月28日 下午16:01:13
 */ 
@QBindEntity(entityClass = GiftGiveCount.class)
public class GiftGiveCountQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "id", where = Where.equal)
	private Long id;

	@QBindAttrField(fieldName = "sender", where = Where.equal)
	private Long sender;

	@QBindAttrField(fieldName = "receiver", where = Where.equal)
	private Long receiver;

	@QBindAttrField(fieldName = "liveId", where = Where.equal)
	private Long liveId;

	@QBindAttrField(fieldName = "giftCount", where = Where.equal)
	private Integer giftCount;

	@QBindAttrField(fieldName = "coin", where = Where.equal)
	private Long coin;

	@QBindAttrField(fieldName = "calorie", where = Where.equal)
	private Long calorie;

	@QBindAttrField(fieldName = "expVal", where = Where.equal)
	private Long expVal;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
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

