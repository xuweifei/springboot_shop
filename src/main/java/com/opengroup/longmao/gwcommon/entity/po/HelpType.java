package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 【帮助中心问答类型】 持久化对象
 *
 * @version
 * @author Hermit 2018年03月14日 下午16:48:14
 */ 
@Entity
@Table(name = "ui_help_type") 
@ApiModel(value = "帮助中心问答类型",description = "HelpType")
public class HelpType implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1356177129477694689L;

	@Id
	@ApiModelProperty(value = "问题类型ID",required = true)
	@Column(name = "type_id")
	private Long typeId;

	@ApiModelProperty(value = "问题类型名称",required = true)
	@Column(name = "type_name")
	private String typeName;

	@ApiModelProperty(value = "问题类型图标",required = true)
	@Column(name = "type_icon")
	private String typeIcon;

	@ApiModelProperty(value = "类型排序",required = true)
	@Column(name = "type_rank")
	private Short typeRank;

	@ApiModelProperty(value = "创建时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "修改时间",required = true)
	@Column(name = "utime")
	private Integer utime;

	@ApiModelProperty(value = "是否删除(1-正常,-1-删除)",required = true)
	@Column(name = "is_delete")
	private Short isDelete;
	
	public HelpType() {
		super();
	}

	public HelpType(String typeName, String typeIcon) {
		super();
		this.typeName = typeName;
		this.typeIcon = typeIcon;
	}

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

