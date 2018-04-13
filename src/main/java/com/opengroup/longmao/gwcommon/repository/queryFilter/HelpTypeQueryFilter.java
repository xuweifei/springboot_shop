package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.HelpType;

/**
 * 【帮助中心问答类型】构造查询条件
 *
 * @version
 * @author Hermit 2018年03月14日 下午16:48:14
 */ 
@QBindEntity(entityClass = HelpType.class)
public class HelpTypeQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "typeId", where = Where.equal)
	private Long typeId;

	@QBindAttrField(fieldName = "typeName", where = Where.equal)
	private String typeName;

	@QBindAttrField(fieldName = "typeIcon", where = Where.equal)
	private String typeIcon;

	@QBindAttrField(fieldName = "typeRank", where = Where.equal)
	private Short typeRank;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getTypeId(){
		return typeId;
	}

	public void setTypeId(Long typeId){
		this.typeId = typeId;
	}

	public String getTypeName(){
		return typeName;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	public String getTypeIcon(){
		return typeIcon;
	}

	public void setTypeIcon(String typeIcon){
		this.typeIcon = typeIcon;
	}

	public Short getTypeRank(){
		return typeRank;
	}

	public void setTypeRank(Short typeRank){
		this.typeRank = typeRank;
	}

	public Integer getCtime(){
		return ctime;
	}

	public void setCtime(Integer ctime){
		this.ctime = ctime;
	}

	public Integer getUtime(){
		return utime;
	}

	public void setUtime(Integer utime){
		this.utime = utime;
	}

	public Short getIsDelete(){
		return isDelete;
	}

	public void setIsDelete(Short isDelete){
		this.isDelete = isDelete;
	}

}

