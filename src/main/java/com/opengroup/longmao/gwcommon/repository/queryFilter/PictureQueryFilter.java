package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.PhotoAlbum;

/**
 * 【图片】构造查询条件
 * @version
 * @author Hermit 2017年03月23日 上午09:08:34
 */
@QBindEntity(entityClass = PhotoAlbum.class)
public class PictureQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private String userId;

	@QBindAttrField(fieldName = "photoType", where = Where.equal)
	private Integer photoType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPhotoType() {
		return photoType;
	}

	public void setPhotoType(Integer photoType) {
		this.photoType = photoType;
	}

}
