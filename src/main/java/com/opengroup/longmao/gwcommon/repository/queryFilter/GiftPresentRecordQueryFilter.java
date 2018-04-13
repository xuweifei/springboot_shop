package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import java.math.BigDecimal;
import java.util.Date;
import com.opengroup.longmao.gwcommon.entity.po.GiftPresentRecord;

/**
 * 【礼物赠送信息】构造查询条件
 *
 * @version
 * @author Hermit 2017年06月05日 下午15:18:08
 */
@QBindEntity(entityClass = GiftPresentRecord.class)
public class GiftPresentRecordQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "sender", where = Where.equal)
	private String sender;

	@QBindAttrField(fieldName = "receiver", where = Where.equal)
	private String receiver;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
