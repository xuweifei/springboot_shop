package com.opengroup.longmao.gwcommon.service.impl;

import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.service.UserLevelService;
/**
 * 【用户等级】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月16日 上午10:58:27
 */ 
@Service
public class UserLevelServiceImpl implements UserLevelService{

//	@Autowired
//	private UserLevelRepositoryMaster userLevelRepositoryMaster;
//
//	@Autowired
//	private UserLevelRepositorySlave userLevelRepositorySlave;
//
//	@Autowired
//	private IdGlobalGenerator idGlobalGenerator;

//	/**
//	* 【分页查询用户等级】
//	* @param userLevel
//	* @param pageNo
//	* @param pageSize
//	* @param sortField
//	* @return userLevel
//	* @version 1.0
//	* @author Hermit 2017年03月16日 上午10:58:27
//	*/ 
//	@Override
//	public Page<UserLevel> findUserLevel(UserLevel userLevel,Integer pageNo, Integer pageSize, String sortField){
//		// 组合查询语句
//		//UserLevelQueryFilter query = new UserLevelQueryFilter();
//		//query.setIsId(userLevel.getId());
//		//query.setIsDelete(IsDeleteEnum.NO.getVal());
//		//字段排序
//		Sort sort = new Sort(Direction.DESC, sortField);
//		// 分页
//		Pageable page = new PageRequest(pageNo, pageSize, sort);
//		// 查询分页数据
//		//Page<UserLevel> pageList = userLevelRepositorySlave.findAll(query, page);
//		//return pageList;
//		return null;
//	}
//	/**
//	* 【根据id查询用户等级】
//	* @param id
//	* @return userLevel
//	* @version 1.0
//	* @author Hermit 2017年03月16日 上午10:58:27
//	*/ 
//	@Override
//	public UserLevel findUserLevel(Long id){
//	    UserLevel userLevel = null;
//		if(StringUtils.isNotBlank(id.toString())){
//	      userLevel = userLevelRepositorySlave.findOne(id);
//		}else{
//		  GwsLogger.info("id不存在");
//		}
//		return userLevel;
//	}
//	/**
//	* 【保存用户等级】
//	* @param userLevel
//	* @return userLevel
//	* @version 1.0
//	* @author Hermit 2017年03月16日 上午10:58:27
//	*/ 
//	@Override
//	public UserLevel saveUserLevel(UserLevel userLevel){
//		//判断对象是否存在
//		if(userLevel!= null){
//		   //id统一生成
//		   //Long id = idGlobalGenerator.getSeqId(UserLevel.class);
//		   //userLevel.setId(id);
//		   userLevel = userLevelRepositoryMaster.save(userLevel);
//		   GwsLogger.info("用户等级保存成功");
//		}else{
//			GwsLogger.info("用户等级对象不存在，保存失败:userLevel={}",ToStringBuilder.reflectionToString(userLevel));
//		    return null;
//		}
//		return userLevel;
//	}
//
//	/**
//	* 【修改用户等级】
//	* @param userLevel
//	* @return userLevel
//	* @version 1.0
//	* @author Hermit 2017年03月16日 上午10:58:27
//	*/ 
//	@Override
//	public UserLevel updateUserLevel(UserLevel userLevel){
//		if(StringUtils.isNotBlank(userLevel.getId())){
//		    //先从库中查出该对象
//	        UserLevel userLevelBean = userLevelRepositorySlave.findOne(Long.valueOf(userLevel.getId()));
//		    //判断对象是否存在
//			if(userLevelBean!= null){
//		       //该处数据填充代码请自行补全....
//			   userLevel = userLevelRepositoryMaster.save(userLevelBean);
//			   GwsLogger.info("用户等级修改成功");
//			}else{
//			    GwsLogger.info("用户等级对象不存在，修改失败:userLevel={}",ToStringBuilder.reflectionToString(userLevel));
//		        return null;
//			}
//		}else{
//			 GwsLogger.error("用户等级id不存在，修改失败:userLevel={}",ToStringBuilder.reflectionToString(userLevel));
//		     return null;
//		}
//		return userLevel;
//	}
//
//	/**
//	 * 【根据id删除用户等级】
//	 * @param id
//	 * @return void
//	 * @version 1.0
//	 * @author Hermit 2017年03月16日 上午10:58:27
//	 */ 
//	@Override
//	public void deleteUserLevel(Long id){
//		//先从库中查出该对象
//		UserLevel userLevel = userLevelRepositorySlave.findOne(id);
//		//判断对象是否存在
//		if(userLevel!=null){
//			//将用户状态改为删除
//			//userLevel.setIsDelete(IsDeleteEnum.YES.getVal());
//			UserLevel newUserLevel = userLevelRepositoryMaster.save(userLevel);
//			//判断对象是否存在
//			if(newUserLevel!=null){
//				GwsLogger.info("用户等级删除成功");
//			}else{
//				GwsLogger.info("用户等级删除失败:id={}",id);
//			}
//		}else{
//			GwsLogger.info("用户等级对象不存在:id={}",id);
//		}
//	}

}

