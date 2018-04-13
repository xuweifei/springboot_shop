package com.opengroup.longmao.gwcommon.entity.po;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户等级】 持久化对象
 *
 * @version
 * @author Hermit 2017年03月16日 上午10:58:26
 */ 
@Entity
@Table(name = "co_user_level") 
public class UserLevel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "level")
	private Integer level;

	@Column(name = "level_title")
	private String levelTitle;

	@Column(name = "exp_dist")
	private Integer expDist;

	@Column(name = "start_exp")
	private Integer startExp;

	@Column(name = "end_exp")
	private Integer endExp;

	@Column(name = "level_pic")
	private String levelPic;

	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_create_user")
	private String gmtCreateUser;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	@Column(name = "gmt_modified_user")
	private String gmtModifiedUser;


	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public Integer getLevel(){
		return level;
	}

	public void setLevel(Integer level){
		this.level = level;
	}

	public String getLevelTitle(){
		return levelTitle;
	}

	public void setLevelTitle(String levelTitle){
		this.levelTitle = levelTitle;
	}

	public Integer getExpDist(){
		return expDist;
	}

	public void setExpDist(Integer expDist){
		this.expDist = expDist;
	}

	public Integer getStartExp(){
		return startExp;
	}

	public void setStartExp(Integer startExp){
		this.startExp = startExp;
	}

	public Integer getEndExp(){
		return endExp;
	}

	public void setEndExp(Integer endExp){
		this.endExp = endExp;
	}

	public String getLevelPic(){
		return levelPic;
	}

	public void setLevelPic(String levelPic){
		this.levelPic = levelPic;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public String getGmtCreateUser(){
		return gmtCreateUser;
	}

	public void setGmtCreateUser(String gmtCreateUser){
		this.gmtCreateUser = gmtCreateUser;
	}

	public Date getGmtModified(){
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public String getGmtModifiedUser(){
		return gmtModifiedUser;
	}

	public void setGmtModifiedUser(String gmtModifiedUser){
		this.gmtModifiedUser = gmtModifiedUser;
	}

}

