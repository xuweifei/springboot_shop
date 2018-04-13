package com.opengroup.longmao.gwcommon.repository.queryFilter;

import java.util.List;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.LivePlayInfo;

@QBindEntity(entityClass = LivePlayInfo.class)
public class LivePlayInfoQueryFilter extends BaseQuery {
	
	@QBindAttrField(fieldName = "playId", where = Where.equal)
	private String playId;
	
	@QBindAttrField(fieldName = "playId", where = Where.in)
	private List<String> playIdList;
	
	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public List<String> getPlayIdList() {
		return playIdList;
	}

	public void setPlayIdList(List<String> playIdList) {
		this.playIdList = playIdList;
	}

	public Integer getUtime() {
		return utime;
	}

	public void setUtime(Integer utime) {
		this.utime = utime;
	}
	
	
}
