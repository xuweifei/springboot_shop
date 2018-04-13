package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditFlow;

/**
 * 【主播信誉流水】构造查询条件
 *
 * @version
 * @author Hermit 2017年03月23日 上午09:47:09
 */ 
@QBindEntity(entityClass = AnchorCreditFlow.class)
public class AnchorCreditFlowQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "id", where = Where.equal)
	private Long id;

	@QBindAttrField(fieldName = "anchorId", where = Where.equal)
	private Long anchorId;

	@QBindAttrField(fieldName = "addOrDel", where = Where.equal)
	private Short addOrDel;

	@QBindAttrField(fieldName = "ruleId", where = Where.equal)
	private Integer ruleId;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAnchorId(){
		return anchorId;
	}

	public void setAnchorId(Long anchorId){
		this.anchorId = anchorId;
	}

	public Short getAddOrDel(){
		return addOrDel;
	}

	public void setAddOrDel(Short addOrDel){
		this.addOrDel = addOrDel;
	}

	public Integer getRuleId(){
		return ruleId;
	}

	public void setRuleId(Integer ruleId){
		this.ruleId = ruleId;
	}

	public Short getIsDelete(){
		return isDelete;
	}

	public void setIsDelete(Short isDelete){
		this.isDelete = isDelete;
	}

}