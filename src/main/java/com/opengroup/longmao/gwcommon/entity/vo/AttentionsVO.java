/**
 * @Title: AttentionsVO.java 
 * @Package com.opengroup.longmao.gwcommon.entity.vo 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.entity.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AttentionsVO
 * @Description: TODO
 * @author Mr.Zhu
 */
public class AttentionsVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6384652646125707445L;
	private String userId; // 用户ID
	private List<Map<String, Object>> objList; // 关注或被关注对象列表
	private List<Map<String, Object>> objListOne;
	private String content;
	private Integer totalPage;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Map<String, Object>> getObjList() {
		return objList;
	}

	public void setObjList(List<Map<String, Object>> objList) {
		this.objList = objList;
	}

	public List<Map<String, Object>> getObjListOne() {
		return objListOne;
	}

	public void setObjListOne(List<Map<String, Object>> objListOne) {
		this.objListOne = objListOne;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
