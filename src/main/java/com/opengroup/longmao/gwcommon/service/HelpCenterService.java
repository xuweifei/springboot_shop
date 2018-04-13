/**
 * @Title: HelpCenterService.java 
 * @Package com.opengroup.longmao.gwcommon.service 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.opengroup.longmao.gwcommon.entity.po.HelpQuestionAnswer;
import com.opengroup.longmao.gwcommon.entity.po.HelpType;

/**
 * @ClassName: HelpCenterService 
 * @Description: TODO
 * @author Mr.Zhu
 */
public interface HelpCenterService {
	
	/**
	 * @Title: findQuestionAnswer 
	 * @Description:  查询帮助中心问答-类型  
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> findQuestionAnswer();
	
	/**
	 * @Title: findHelpQuestionAnswer 
	 * @Description: 查询帮助中心问答-内容 
	 * @param sort
	 * @param helpType
	 * @param isShow
	 * @return List<HelpQuestionAnswer>
	 */
	List<HelpQuestionAnswer> findHelpQuestionAnswer(Sort sort, Long helpType, Short isShow);
	
	/**
	 * @Title: findHelpType 
	 * @Description: 根据类型ID查询帮助中心问答-类型 
	 * @param typeId
	 * @return HelpType
	 */
	HelpType findHelpType(Long typeId);
	
	/**
	 * @Title: findHelpQuestionAnswer 
	 * @Description: 根据问题ID查询帮助中心问答-内容 
	 * @param questionId
	 * @return HelpQuestionAnswer
	 */
	HelpQuestionAnswer findHelpQuestionAnswer(Long questionId);
	
	/**
	 * @Title: findHelpType 
	 * @Description: 查询所有帮助中心问答-类型 
	 * @param pageNo
	 * @param pageSize
	 * @return Page<HelpType>
	 */
	Page<HelpType> findHelpType(Integer pageNo, Integer pageSize);
	
	/**
	 * @Title: findHelpQuestionAnswer 
	 * @Description: 查询所有帮助中心问答-内容 
	 * @param isShow
	 * @param pageNo
	 * @param pageSize
	 * @return Page<HelpQuestionAnswer>
	 */
	Page<HelpQuestionAnswer> findHelpQuestionAnswer(Short isShow, Integer pageNo, Integer pageSize);
	
}
