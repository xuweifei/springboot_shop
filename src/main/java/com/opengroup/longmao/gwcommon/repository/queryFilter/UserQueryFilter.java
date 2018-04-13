package com.opengroup.longmao.gwcommon.repository.queryFilter;

import java.util.List;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.User;

/**
 * 【用户】构造查询条件
 *
 * @version
 * @author Hermit 2017年03月23日 上午09:08:34
 */ 
@QBindEntity(entityClass = User.class)
public class UserQueryFilter extends BaseQuery {
	
	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;

	@QBindAttrField(fieldName = "userType", where = Where.equal)
	private Integer userType;
	
	@QBindAttrField(fieldName = "creditGradeExplain", where = Where.equal)
	private String creditGradeExplain;
	
	@QBindAttrField(fieldName = "city", where = Where.equal)
	private String city;

	@QBindAttrField(fieldName = "sex", where = Where.equal)
	private Short sex;
	
	@QBindAttrField(fieldName = "userId", where = Where.in)
	private List<String> userIdList;
	
	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}
	
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getCreditGradeExplain() {
		return creditGradeExplain;
	}

	public void setCreditGradeExplain(String creditGradeExplain) {
		this.creditGradeExplain = creditGradeExplain;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}
	
}

