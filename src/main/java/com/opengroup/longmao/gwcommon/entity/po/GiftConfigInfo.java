package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【礼物配置信息】 持久化对象
 *
 * @version
 * @author Hermit 2017年08月28日 下午16:01:57
 */ 
@Entity
@Table(name = "gift_config_info") 
public class GiftConfigInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "gift_name")
	private String giftName;

	@Column(name = "gift_desc")
	private String giftDesc;

	@Column(name = "png_name")
	private String pngName;

	@Column(name = "coin")
	private Integer coin;

	@Column(name = "calorie")
	private Integer calorie;

	@Column(name = "exp_val")
	private Integer expVal;

	@Column(name = "experience")
	private String experience;

	@Column(name = "gift_pic")
	private String giftPic;

	@Column(name = "is_continuity")
	private Short isContinuity;

	@Column(name = "gift_type")
	private Short giftType;

	@Column(name = "is_enable")
	private Short isEnable;

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

