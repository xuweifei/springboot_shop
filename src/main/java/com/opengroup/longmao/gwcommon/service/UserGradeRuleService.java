package com.opengroup.longmao.gwcommon.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.opengroup.longmao.gwcommon.entity.po.ExpChangeRecord;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserGradeRule;

/**
 * 【用户等级规则】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年04月17日 上午11:44:29
 */ 
public interface UserGradeRuleService {

	 /**
	 * 【保存用户等级规则】
	 * @param userGradeRule
	 * @return userGradeRule
	 * @version 1.0
	 * @author Hermit 2017年04月17日 上午11:44:29
	 */ 
	 UserGradeRule saveUserGradeRule(UserGradeRule userGradeRule);

	 /**
	 * 【删除用户等级规则】
	 * @param id
	 * @return void
	 * @version 1.0
	 * @author Hermit 2017年04月17日 上午11:44:29
	 */ 
	 void deleteUserGradeRule(Long id);


	 /**
	 * 【修改用户等级规则】
	 * @param userGradeRule
	 * @return userGradeRule
	 * @version 1.0
	 * @author Hermit 2017年04月17日 上午11:44:29
	 */ 
	 UserGradeRule updateUserGradeRule(UserGradeRule userGradeRule);

	 /**
	 * 【查询用户等级规则】
	 * @param id
	 * @return UserGradeRule
	 * @version 1.0
	 * @author Hermit 2017年04月17日 上午11:44:29
	 */ 
	 UserGradeRule findUserGradeRule(Long id);
	 
	 /**
	  * @Title: findUserGrade 
	  * @Description: 根据用户经验值获取等级 
	  * @param experience
	  * @return Integer
	  */
	 Integer findUserGrade(String experience);

	 /**
	  * @Title: findUserGradeRule 
	  * @Description: 根据经验值查询用户等级 
	  * @param levelVal
	  * @param isDelete
	  * @return UserGradeRule
	  */
	 UserGradeRule findUserGradeRule(Long levelVal,Short isDelete);
	 
	 /**
	  * @Title: findUserGradeRule 
	  * @Description: 根据userId查询用户相关等级信息 
	  * @param userId
	  * @return UserGradeRule
	  */
	 Map<String, Object> findUserGradeRule(String userId);
	 
	 /**
	  * @Title: updateExp 
	  * @Description: 更新用户经验值 
	  * @param userId
	  * @param userExp
	  * @return Boolean
	  */
	 Boolean updateExp(String userId, Long userExp);
	 
	 /**
	  * @Title: updateExpBatch 
	  * @Description: 批量更新用户经验值 
	  * @param expList
	  * @return Boolean
	  */
	 Boolean updateExpBatch(List<Map<String, Object>> expList);
	 
	 /**
	 * 【查询用户等级规则】
	 * @param userGradeRule
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<UserGradeRule>
	 * @version 1.0
	 * @author Hermit 2017年04月17日 上午11:44:29
	 */ 
	 Page<UserGradeRule> findUserGradeRule(UserGradeRule userGradeRule,Integer pageNo,Integer pageSize,String sortField);

	/**
	 * @Title: createExp
	 * @Description: 创建用户经验值流水
	 * @param u
	 * @param userExp
	 * @param changeType
	 * @param bizType
	 * @return ExpChangeRecord
	 */
	ExpChangeRecord createExp(User u, Long userExp, Short changeType, Short bizType);
}

