package com.opengroup.longmao.gwcommon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.elasticsearch.service.ESService;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.dto.ESUserDTO;
import com.opengroup.longmao.gwcommon.entity.po.ExpChangeRecord;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserGradeRule;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.repository.master.ExpChangeRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserGradeRuleRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.UserGradeRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.UserGradeRuleService;
import com.opengroup.longmao.gwcommon.tools.EntityDtoConverter;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
/**
 * 【用户等级规则】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年04月17日 上午11:44:29
 */ 
@Service
public class UserGradeRuleServiceImpl implements UserGradeRuleService{

	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private UserRepositoryMaster userRepositoryMaster;
	
	@Autowired
	private UserGradeRuleRepositoryMaster userGradeRuleRepositoryMaster;

	@Autowired
	private UserGradeRuleRepositorySlave userGradeRuleRepositorySlave;
	
	@Autowired
	private ExpChangeRecordRepositoryMaster expChangeRecordRepositoryMaster;
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	@Autowired
	private ESService<ESUserDTO> eSService;

	/**
	* 【分页查询用户等级规则】
	* @param userGradeRule
	* @param pageNo
	* @param pageSize
	* @param sortField
	* @return userGradeRule
	* @version 1.0
	* @author Hermit 2017年04月17日 上午11:44:29
	*/ 
	@Override
	public Page<UserGradeRule> findUserGradeRule(UserGradeRule userGradeRule,Integer pageNo, Integer pageSize, String sortField){
		// 组合查询语句
		//UserGradeRuleQueryFilter query = new UserGradeRuleQueryFilter();
		//query.setId(userGradeRule.getId());
		//query.setIsDelete(IsNormalEnum.NO.getVal());
		//字段排序
//		Sort sort = new Sort(Direction.DESC, sortField);
		// 分页
//		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		//Page<UserGradeRule> pageList = userGradeRuleRepositorySlave.findAll(query, page);
		//return pageList;
		return null;
	}

	/**
	* 【根据id查询用户等级规则】
	* @param id
	* @return userGradeRule
	* @version 1.0
	* @author Hermit 2017年04月17日 上午11:44:29
	*/ 
	@Override
	public UserGradeRule findUserGradeRule(Long id){
	    UserGradeRule userGradeRule = null;
		if(StringUtils.isNotBlank(id.toString())){
	      userGradeRule = userGradeRuleRepositorySlave.findOne(id);
		}else{
		  GwsLogger.info("id不存在");
		}
		return userGradeRule;
	}

	/**
	* 【保存用户等级规则】
	* @param userGradeRule
	* @return userGradeRule
	* @version 1.0
	* @author Hermit 2017年04月17日 上午11:44:29
	*/ 
	@Override
	public UserGradeRule saveUserGradeRule(UserGradeRule userGradeRule){
		//判断对象是否存在
		if(userGradeRule!= null){
		   //id统一生成
		   //Long id = idGlobalGenerator.getSeqId(UserGradeRule.class);
		   //userGradeRule.setId(id);
		   userGradeRule = userGradeRuleRepositoryMaster.save(userGradeRule);
		   GwsLogger.info("用户等级规则保存成功");
		}else{
			GwsLogger.info("用户等级规则对象不存在，保存失败:userGradeRule={}",ToStringBuilder.reflectionToString(userGradeRule));
		    return null;
		}
		return userGradeRule;
	}
	
	/**
	  * @Title: findUserGrade 
	  * @Description: 根据用户经验值获取等级 
	  * @param experience
	  * @return Integer
	  */
	@Override
	public Integer findUserGrade(String experience) {
		Integer grade = 0;
		List<UserGradeRule> gL = userGradeRuleRepositorySlave.findUserGradeRuleList(Long.parseLong(experience),IsDeleteEnum.UN_DELETE.getVal());
		if (null != gL && !gL.isEmpty()) {
			grade = gL.get(0).getGrade().intValue();
		}
		return grade;
	 }
	
	/**
	  * @Title: findUserGradeRule 
	  * @Description: 根据经验值查询用户等级 
	  * @param levelVal
	  * @param isDelete
	  * @return UserGradeRule
	  */
	@Override
	public UserGradeRule findUserGradeRule(Long levelVal, Short isDelete) {
		return userGradeRuleRepositorySlave.findUserGradeRule(levelVal, isDelete);
	}
	
	/**
	  * @Title: findUserGradeRule 
	  * @Description: 根据userId查询用户相关等级信息 
	  * @param userId
	  * @return UserGradeRule
	  */
	@Override
	public Map<String, Object> findUserGradeRule(String userId) {
		User user = queryUserByUserId(userId);
		if (null != user) {
			Long exp = Long.valueOf(user.getExperience());
			List<UserGradeRule> gL = userGradeRuleRepositorySlave
					.findUserGradeRuleList(exp, IsDeleteEnum.UN_DELETE.getVal());
			if (CollectionUtils.isNotEmpty(gL)) {
				UserGradeRule g = gL.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				Long differ = 0L;
				if (1 < gL.size()) {
					differ = g.getMaxVal() - exp;
				}
				map.put("grade", g.getGrade());
				map.put("alias", g.getAlias());
				map.put("experience", exp);
				map.put("differ", differ);
				map.put("maxVal", g.getMaxVal());
				map.put("minVal", g.getMinVal());
				map.put("isMax", g.getIsMax());
				return map;
			}
		}
		return null;
	}
	
	/**
	 * @Title: createExp 
	 * @Description: 创建用户经验值流水 
	 * @param u
	 * @param userExp
	 * @param changeType
	 * @param bizType
	 * @return ExpChangeRecord
	 */
	@Override
	public ExpChangeRecord createExp(User u, Long userExp, Short changeType, Short bizType) {
		ExpChangeRecord exp = new ExpChangeRecord();
		Long expId = idGlobalGenerator.getSeqId(ExpChangeRecord.class);
		
		exp.setExpId(expId);
		exp.setUserId(u.getUserId());
		exp.setChangeType(changeType);
		exp.setChangeNum(userExp);
		exp.setBizType(bizType);
		exp.setOutBizId(u.getId());
		exp.setCtime(DateUtil.currentSecond());
		exp.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		return expChangeRecordRepositoryMaster.save(exp);
	}
	
	/**
	  * @Title: updateExp 
	  * @Description: 更新用户经验值 
	  * @param userId
	  * @param userExp
	  * @return Boolean
	  */
	@Override
	public Boolean updateExp(String userId, Long userExp) {
		User u = queryUserByUserId(userId);
		if (null != u) {
			Long exp = Long.valueOf(u.getExperience()) + userExp;
			List<UserGradeRule> gL = userGradeRuleRepositorySlave.findUserGradeRuleList(exp,
					IsDeleteEnum.UN_DELETE.getVal());
			Integer grade = u.getGrade();//默认等级为账户原等级
			if (CollectionUtils.isNotEmpty(gL)) {
				UserGradeRule userGradeRule = gL.get(0);
				grade = userGradeRule.getGrade().intValue();
			}
			Integer flag = userRepositoryMaster.updateUser(String.valueOf(userExp), grade, new Date(), "EXP", u.getId());
			if(flag != null && flag > 0){
				User uu = userRepositorySlave.queryUserByUserId(userId);
				eSService.saveEntity(EntityDtoConverter.UserCopyToESUserDTO(uu));
			}
			//创建用户经验值流水
			createExp(u, userExp, IsPlusEnum.PLUS.getVal().shortValue(), Short.valueOf("0"));
			return (Integer.valueOf("1").equals(flag)) ? true : false;
		}
		return false;
	}
	
	/**
	  * @Title: updateExpBatch 
	  * @Description: 批量更新用户经验值 
	  * @param expList
	  * @return Boolean
	  */
	@Override
	public Boolean updateExpBatch(List<Map<String, Object>> expList) {
		Boolean flag = false;
		for (Map<String, Object> map : expList) {
			Long userExp = map.containsKey("userExp") ? Long.valueOf(map.get("userExp").toString()) : 0L;
			if (map.containsKey("userId") && 0L < userExp) {
				flag = updateExp(map.get("userId").toString(), userExp);
			}
			GwsLogger.info("批量更新用户经验值,更新:userId={},exp={},flag={}", map.get("userId"), map.get("userExp"), flag);
		}
		return flag;
	}
	
	/**
	* 【修改用户等级规则】
	* @param userGradeRule
	* @return userGradeRule
	* @version 1.0
	* @author Hermit 2017年04月17日 上午11:44:29
	*/ 
	@Override
	public UserGradeRule updateUserGradeRule(UserGradeRule userGradeRule){
		if(null != userGradeRule){
		    //先从库中查出该对象
	        UserGradeRule userGradeRuleBean = userGradeRuleRepositorySlave.findOne(Long.valueOf(userGradeRule.getId()));
		    //判断对象是否存在
			if(userGradeRuleBean!= null){
		       //该处数据填充代码请自行补全....
			   userGradeRule = userGradeRuleRepositoryMaster.save(userGradeRuleBean);
			   GwsLogger.info("用户等级规则修改成功");
			}else{
			    GwsLogger.info("用户等级规则对象不存在，修改失败:userGradeRule={}",ToStringBuilder.reflectionToString(userGradeRule));
		        return null;
			}
		}else{
			 GwsLogger.error("用户等级规则id不存在，修改失败:userGradeRule={}",ToStringBuilder.reflectionToString(userGradeRule));
		     return null;
		}
		return userGradeRule;
	}

	/**
	 * 【根据id删除用户等级规则】
	 * @param id
	 * @return void
	 * @version 1.0
	 * @author Hermit 2017年04月17日 上午11:44:29
	 */ 
	@Override
	public void deleteUserGradeRule(Long id){
		//先从库中查出该对象
		UserGradeRule userGradeRule = userGradeRuleRepositorySlave.findOne(id);
		//判断对象是否存在
		if(userGradeRule!=null){
			//将用户状态改为删除
			//userGradeRule.setIsDelete(IsDeleteEnum.YES.getVal());
			UserGradeRule newUserGradeRule = userGradeRuleRepositoryMaster.save(userGradeRule);
			//判断对象是否存在
			if(newUserGradeRule!=null){
				GwsLogger.info("用户等级规则删除成功");
			}else{
				GwsLogger.info("用户等级规则删除失败:id={}",id);
			}
		}else{
			GwsLogger.info("用户等级规则对象不存在:id={}",id);
		}
	}
	
	/**
	 * @Title: queryUserByUserId 
	 * @Description: 查询用户基本信息 
	 * @param userId
	 * @return User
	 */
	public User queryUserByUserId(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			User user = userRepositorySlave.queryUserByUserId(userId);
			return user;
		}
		return null;
	}

}

