package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.SensitivePause;

/**
 * 【敏感词汇分隔符】构造查询条件
 *
 * @version
 * @author Hermit 2018年03月19日 下午16:01:12
 */
@QBindEntity(entityClass = SensitivePause.class)
public class SensitivePauseQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "pId", where = Where.equal)
	private Long pId;

	@QBindAttrField(fieldName = "symbol", where = Where.equal)
	private String symbol;

	@QBindAttrField(fieldName = "isUse", where = Where.equal)
	private Short isUse;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Short getIsUse() {
		return isUse;
	}

	public void setIsUse(Short isUse) {
		this.isUse = isUse;
	}

	public Integer getCtime() {
		return ctime;
	}

	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}

	public Integer getUtime() {
		return utime;
	}

	public void setUtime(Integer utime) {
		this.utime = utime;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

}
