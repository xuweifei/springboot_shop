package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.GiftConfigInfo;

/**
 * 【礼物配置信息】构造查询条件
 *
 * @version
 * @author Hermit 2017年08月28日 下午16:01:57
 */ 
@QBindEntity(entityClass = GiftConfigInfo.class)
public class GiftConfigInfoQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "id", where = Where.equal)
	private Long id;

	@QBindAttrField(fieldName = "giftName", where = Where.equal)
	private String giftName;

	@QBindAttrField(fieldName = "giftDesc", where = Where.equal)
	private String giftDesc;

	@QBindAttrField(fieldName = "pngName", where = Where.equal)
	private String pngName;

	@QBindAttrField(fieldName = "coin", where = Where.equal)
	private Integer coin;

	@QBindAttrField(fieldName = "calorie", where = Where.equal)
	private Integer calorie;

	@QBindAttrField(fieldName = "expVal", where = Where.equal)
	private Integer expVal;

	@QBindAttrField(fieldName = "experience", where = Where.equal)
	private String experience;

	@QBindAttrField(fieldName = "giftPic", where = Where.equal)
	private String giftPic;

	@QBindAttrField(fieldName = "isContinuity", where = Where.equal)
	private Short isContinuity;

	@QBindAttrField(fieldName = "giftType", where = Where.equal)
	private Short giftType;

	@QBindAttrField(fieldName = "isEnable", where = Where.equal)
	private Short isEnable;

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

	public String getGiftName(){
		return giftName;
	}

	public void setGiftName(String giftName){
		this.giftName = giftName;
	}

	public String getGiftDesc(){
		return giftDesc;
	}

	public void setGiftDesc(String giftDesc){
		this.giftDesc = giftDesc;
	}

	public String getPngName(){
		return pngName;
	}

	public void setPngName(String pngName){
		this.pngName = pngName;
	}

	public Integer getCoin(){
		return coin;
	}

	public void setCoin(Integer coin){
		this.coin = coin;
	}

	public Integer getCalorie(){
		return calorie;
	}

	public void setCalorie(Integer calorie){
		this.calorie = calorie;
	}

	public Integer getExpVal(){
		return expVal;
	}

	public void setExpVal(Integer expVal){
		this.expVal = expVal;
	}

	public String getExperience(){
		return experience;
	}

	public void setExperience(String experience){
		this.experience = experience;
	}

	public String getGiftPic(){
		return giftPic;
	}

	public void setGiftPic(String giftPic){
		this.giftPic = giftPic;
	}

	public Short getIsContinuity(){
		return isContinuity;
	}

	public void setIsContinuity(Short isContinuity){
		this.isContinuity = isContinuity;
	}

	public Short getGiftType(){
		return giftType;
	}

	public void setGiftType(Short giftType){
		this.giftType = giftType;
	}

	public Short getIsEnable(){
		return isEnable;
	}

	public void setIsEnable(Short isEnable){
		this.isEnable = isEnable;
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

