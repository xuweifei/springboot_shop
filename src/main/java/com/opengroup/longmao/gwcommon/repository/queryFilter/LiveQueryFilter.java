package com.opengroup.longmao.gwcommon.repository.queryFilter;

import java.util.List;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.Live;

/**
 * 【主播直播列表】构造查询条件
 *
 * @version
 * @author Hermit 2017年06月12日 下午14:26:21
 */
@QBindEntity(entityClass = Live.class)
public class LiveQueryFilter extends BaseQuery {
	
	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private String userId;

	@QBindAttrField(fieldName = "userId", where = Where.in)
	private List<String> userIdList;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}

}
