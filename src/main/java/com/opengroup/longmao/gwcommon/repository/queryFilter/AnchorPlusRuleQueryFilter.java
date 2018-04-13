package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditFlow;

/**
 * 【主播加分规则】构造查询条件
 *
 * @version
 * @author Hermit 2017年03月23日 上午09:08:34
 */ 
@QBindEntity(entityClass = AnchorCreditFlow.class)
public class AnchorPlusRuleQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}
	
}

