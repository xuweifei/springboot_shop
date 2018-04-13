package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.SensitiveWord;

/**
 * 【敏感词汇】构造查询条件
 *
 * @version
 * @author Hermit 2018年03月19日 下午16:01:12
 */ 
@QBindEntity(entityClass = SensitiveWord.class)
public class SensitiveWordQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "wId", where = Where.equal)
	private Long wId;

	@QBindAttrField(fieldName = "wordType", where = Where.equal)
	private Short wordType;

	@QBindAttrField(fieldName = "word", where = Where.equal)
	private String word;

	@QBindAttrField(fieldName = "isUse", where = Where.equal)
	private Short isUse;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getWId(){
		return wId;
	}

	public void setWId(Long wId){
		this.wId = wId;
	}

	public Short getWordType(){
		return wordType;
	}

	public void setWordType(Short wordType){
		this.wordType = wordType;
	}

	public String getWord(){
		return word;
	}

	public void setWord(String word){
		this.word = word;
	}

	public Short getIsUse(){
		return isUse;
	}

	public void setIsUse(Short isUse){
		this.isUse = isUse;
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

