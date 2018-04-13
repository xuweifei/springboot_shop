package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.LiveGroupAdmin;

/**
 * 【直播群组管理员表】构造查询条件
 *
 * @version
 * @author Hermit 2017年07月13日 上午09:50:49
 */ 
@QBindEntity(entityClass = LiveGroupAdmin.class)
public class LiveGroupAdminQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "adminId", where = Where.equal)
	private Long adminId;

	@QBindAttrField(fieldName = "groupId", where = Where.equal)
	private Long groupId;

	@QBindAttrField(fieldName = "userId", where = Where.equal)
	private Long userId;

	@QBindAttrField(fieldName = "liveUserId", where = Where.equal)
	private Long liveUserId;

	@QBindAttrField(fieldName = "nickName", where = Where.equal)
	private String nickName;

	@QBindAttrField(fieldName = "phoneId", where = Where.equal)
	private String phoneId;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getAdminId(){
		return adminId;
	}

	public void setAdminId(Long adminId){
		this.adminId = adminId;
	}

	public Long getGroupId(){
		return groupId;
	}

	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getLiveUserId(){
		return liveUserId;
	}

	public void setLiveUserId(Long liveUserId){
		this.liveUserId = liveUserId;
	}

	public String getNickName(){
		return nickName;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getPhoneId(){
		return phoneId;
	}

	public void setPhoneId(String phoneId){
		this.phoneId = phoneId;
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

