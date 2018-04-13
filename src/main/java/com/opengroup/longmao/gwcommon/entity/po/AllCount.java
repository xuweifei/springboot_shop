package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【全服统计表】 持久化对象
 *
 * @version
 * @author Hermit 2017年12月21日 下午17:16:36
 */ 
@Entity
@Table(name = "all_count") 
@ApiModel(value = "全服统计表对象",description = "AllCount")
public class AllCount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "全服统计id",required = true)
	@Column(name = "id")
	private Long id;

	@ApiModelProperty(value = "统计的类型 1-全服主播累计所获卡路里 2-全服用户累计竞猜收益 3-全服用户累计送出礼物金额",required = true)
	@Column(name = "count_type")
	private Short countType;

	@ApiModelProperty(value = "受礼人（主播）id",required = true)
	@Column(name = "anchor_id")
	private Long anchorId;

	@ApiModelProperty(value = "送礼人（用户）id",required = true)
	@Column(name = "sender_id")
	private Long senderId;

	@ApiModelProperty(value = "序号",required = true)
	@Column(name = "sort_num")
	private Integer sortNum;

	@ApiModelProperty(value = "统计龙猫币",required = true)
	@Column(name = "coin_count")
	private Long coinCount;

	@ApiModelProperty(value = "统计卡路里",required = true)
	@Column(name = "calorie_count")
	private Long calorieCount;

	@ApiModelProperty(value = "统计竞猜收益",required = true)
	@Column(name = "rob_profit_count")
	private Long robProfitCount;

	@ApiModelProperty(value = "统计时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "是否删除(1正常,-1-删除)",required = true)
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Short getCountType(){
		return countType;
	}

	public void setCountType(Short countType){
		this.countType = countType;
	}

	public Long getAnchorId(){
		return anchorId;
	}

	public void setAnchorId(Long anchorId){
		this.anchorId = anchorId;
	}

	public Long getSenderId(){
		return senderId;
	}

	public void setSenderId(Long senderId){
		this.senderId = senderId;
	}

	public Integer getSortNum(){
		return sortNum;
	}

	public void setSortNum(Integer sortNum){
		this.sortNum = sortNum;
	}

	public Long getCoinCount(){
		return coinCount;
	}

	public void setCoinCount(Long coinCount){
		this.coinCount = coinCount;
	}

	public Long getCalorieCount(){
		return calorieCount;
	}

	public void setCalorieCount(Long calorieCount){
		this.calorieCount = calorieCount;
	}

	public Long getRobProfitCount(){
		return robProfitCount;
	}

	public void setRobProfitCount(Long robProfitCount){
		this.robProfitCount = robProfitCount;
	}

	public Integer getCtime(){
		return ctime;
	}

	public void setCtime(Integer ctime){
		this.ctime = ctime;
	}

	public Short getIsDelete(){
		return isDelete;
	}

	public void setIsDelete(Short isDelete){
		this.isDelete = isDelete;
	}

}

