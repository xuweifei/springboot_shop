package com.opengroup.longmao.gwcommon.repository.queryFilter;

import com.opengroup.longmao.gwcommon.configuration.query.Where;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindAttrField;
import com.opengroup.longmao.gwcommon.configuration.query.annotation.QBindEntity;
import com.opengroup.longmao.gwcommon.configuration.query.core.BaseQuery;
import com.opengroup.longmao.gwcommon.entity.po.HelpQuestionAnswer;

/**
 * 【帮助中心问答】构造查询条件
 *
 * @version
 * @author Hermit 2018年03月14日 下午16:47:36
 */ 
@QBindEntity(entityClass = HelpQuestionAnswer.class)
public class HelpQuestionAnswerQueryFilter extends BaseQuery {

	@QBindAttrField(fieldName = "questionId", where = Where.equal)
	private Long questionId;

	@QBindAttrField(fieldName = "helpType", where = Where.equal)
	private Long helpType;

	@QBindAttrField(fieldName = "question", where = Where.equal)
	private String question;

	@QBindAttrField(fieldName = "questionRank", where = Where.equal)
	private Short questionRank;

	@QBindAttrField(fieldName = "questionLabel", where = Where.equal)
	private String questionLabel;

	@QBindAttrField(fieldName = "answer", where = Where.equal)
	private String answer;
	
	@QBindAttrField(fieldName = "answerIos", where = Where.equal)
	private String answerIos;
	
	@QBindAttrField(fieldName = "isCommon", where = Where.equal)
	private Short isCommon;

	@QBindAttrField(fieldName = "isShow", where = Where.equal)
	private Short isShow;

	@QBindAttrField(fieldName = "ctime", where = Where.equal)
	private Integer ctime;

	@QBindAttrField(fieldName = "utime", where = Where.equal)
	private Integer utime;

	@QBindAttrField(fieldName = "isDelete", where = Where.equal)
	private Short isDelete;


	public Long getQuestionId(){
		return questionId;
	}

	public void setQuestionId(Long questionId){
		this.questionId = questionId;
	}

	public Long getHelpType(){
		return helpType;
	}

	public void setHelpType(Long helpType){
		this.helpType = helpType;
	}

	public String getQuestion(){
		return question;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public Short getQuestionRank(){
		return questionRank;
	}

	public void setQuestionRank(Short questionRank){
		this.questionRank = questionRank;
	}

	public String getQuestionLabel(){
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel){
		this.questionLabel = questionLabel;
	}

	public String getAnswer(){
		return answer;
	}

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswerIos() {
		return answerIos;
	}

	public void setAnswerIos(String answerIos) {
		this.answerIos = answerIos;
	}

	public Short getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(Short isCommon) {
		this.isCommon = isCommon;
	}

	public Short getIsShow(){
		return isShow;
	}

	public void setIsShow(Short isShow){
		this.isShow = isShow;
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

