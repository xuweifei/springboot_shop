package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.LmSmsMessage;

/**
 * 【短信记录表】构造查询条件
 *
 * @version
 * @author Hermit 2017年04月27日 下午16:14:13
 */ 
@QBindEntity(entityClass = LmSmsMessage.class)
public class LmSmsMessageQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "smsId", where = Where.equal)
	private Long smsId;

	@QBindAttrField(fieldName = "mobile", where = Where.equal)
	private String mobile;

	@QBindAttrField(fieldName = "content", where = Where.equal)
	private String content;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getSmsId(){
		return smsId;
	}

	public void setSmsId(Long smsId){
		this.smsId = smsId;
	}

	public String getMobile(){
		return mobile;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getContent(){
		return content;
	}

	public void setContent(String content){
		this.content = content;
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

