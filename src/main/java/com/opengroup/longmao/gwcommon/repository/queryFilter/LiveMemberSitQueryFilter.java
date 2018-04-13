package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.LiveMemberSit;

/**
 * 【直播群组成员禁言列表】构造查询条件
 *
 * @version
 * @author Hermit 2017年07月14日 下午15:13:09
 */
@QBindEntity(entityClass = LiveMemberSit.class)
public class LiveMemberSitQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "groupId", where = Where.equal)
	private Long groupId;

	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private Long userId;

	@QBindAttrField(fieldName = "liveUserId", where = Where.equal)
	private Long liveUserId;

	@QBindAttrField(fieldName = "forbidStatus", where = Where.equal)
	private Short forbidStatus;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLiveUserId() {
		return liveUserId;
	}

	public void setLiveUserId(Long liveUserId) {
		this.liveUserId = liveUserId;
	}

	public Short getForbidStatus() {
		return forbidStatus;
	}

	public void setForbidStatus(Short forbidStatus) {
		this.forbidStatus = forbidStatus;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

}
