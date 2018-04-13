package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.UserInvite;

/**
 * 【用户】构造查询条件
 *
 * @version
 * @author Hermit 2017年03月23日 上午09:08:34
 */ 
@QBindEntity(entityClass = UserInvite.class)
public class UserInviteQueryFilter extends BaseQuery {
	
	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private Long userId;

	
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
}

