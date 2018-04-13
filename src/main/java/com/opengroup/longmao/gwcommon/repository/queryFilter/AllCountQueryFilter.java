package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import java.math.BigDecimal;
import java.util.Date;
import com.opengroup.longmao.gwcommon.entity.po.AllCount;

/**
 * 【全服统计表】构造查询条件
 *
 * @version
 * @author Hermit 2017年12月21日 下午17:16:36
 */ 
@QBindEntity(entityClass = AllCount.class)
public class AllCountQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "id", where = Where.equal)
	private Long id;

	@QBindAttrField(fieldName = "countType", where = Where.equal)
	private Short countType;

	@QBindAttrField(fieldName = "anchorId", where = Where.equal)
	private Long anchorId;

	@QBindAttrField(fieldName = "senderId", where = Where.equal)
	private Long senderId;

	@QBindAttrField(fieldName = "sortNum", where = Where.equal)
	private Integer sortNum;

	@QBindAttrField(fieldName = "coinCount", where = Where.equal)
	private Long coinCount;

	@QBindAttrField(fieldName = "calorieCount", where = Where.equal)
	private Long calorieCount;

	@QBindAttrField(fieldName = "robProfitCount", where = Where.equal)
	private Long robProfitCount;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
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

