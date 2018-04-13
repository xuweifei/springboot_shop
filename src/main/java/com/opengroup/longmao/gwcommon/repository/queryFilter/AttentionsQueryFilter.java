package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.Attentions;

/**
 * 【用户关注/粉丝】构造查询条件
 *
 * @version
 * @author Hermit 2017年06月09日 下午16:08:01
 */ 
@QBindEntity(entityClass = Attentions.class)
public class AttentionsQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private String userId;

	@QBindAttrField(fieldName = "anchoruserId", where = Where.equal)
	private String anchoruserId;

	@QBindAttrField(fieldName = "attentionsState", where = Where.equal)
	private Short attentionsState;

	@QBindAttrField(fieldName = "isAttention", where = Where.equal)
	private Short isAttention;

	public String getUserId(){
		return userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getAnchoruserId(){
		return anchoruserId;
	}

	public void setAnchoruserId(String anchoruserId){
		this.anchoruserId = anchoruserId;
	}

	public Short getAttentionsState(){
		return attentionsState;
	}

	public void setAttentionsState(Short attentionsState){
		this.attentionsState = attentionsState;
	}

	public Short getIsAttention(){
		return isAttention;
	}

	public void setIsAttention(Short isAttention){
		this.isAttention = isAttention;
	}

}

