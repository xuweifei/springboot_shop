package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 【帮助中心问答】 持久化对象
 *
 * @version
 * @author Hermit 2018年03月14日 下午16:47:36
 */ 
@Entity
@Table(name = "ui_help_question_answer") 
@ApiModel(value = "帮助中心问答内容",description = "HelpQuestionAnswer")
public class HelpQuestionAnswer implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -7616985581919393465L;

	@Id
	@ApiModelProperty(value = "问答ID",required = true)
	@Column(name = "question_id")
	private Long questionId;

	@ApiModelProperty(value = "类型ID",required = true)
	@Column(name = "help_type")
	private Long helpType;

	@ApiModelProperty(value = "帮助中心问答-问题",required = true)
	@Column(name = "question")
	private String question;

	@ApiModelProperty(value = "帮助中心问答-排序",required = true)
	@Column(name = "question_rank")
	private Short questionRank;

	@ApiModelProperty(value = "帮助中心问答-标签",required = true)
	@Column(name = "question_label")
	private String questionLabel;

	@ApiModelProperty(value = "帮助中心问答-答案",required = true)
	@Column(name = "answer")
	private String answer;
	
	@ApiModelProperty(value = "帮助中心问答-答案(IOS)",required = true)
	@Column(name = "answer_ios")
	private String answerIos;
	
	@ApiModelProperty(value = "是否共用:1是;2否",required = true)
	@Column(name = "is_common")
	private Short isCommon;

	@ApiModelProperty(value = "是否显示:1是;2否",required = true)
	@Column(name = "is_show")
	private Short isShow;

	@ApiModelProperty(value = "创建时间",required = true)
	@Column(name = "ctime")
	private Integer ctime;

	@ApiModelProperty(value = "修改时间",required = true)
	@Column(name = "utime")
	private Integer utime;

	@ApiModelProperty(value = "是否删除(1-正常,-1-删除)",required = true)
	@Column(name = "is_delete")
	private Short isDelete;
	
	public HelpQuestionAnswer() {
		super();
	}

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

