package com.opengroup.longmao.gwcommon.service;

import com.opengroup.longmao.gwcommon.entity.po.SuperAdministrator;

/**
 * 【App超级管理表】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年11月27日 上午10:42:52
 */
public interface SuperAdministratorService {

	/**
	 * @Title: findSuperAdministrator 
	 * @Description: 查询APP超管 
	 * @param userId
	 * @return SuperAdministrator
	 */
	SuperAdministrator findSuperAdministrator(Long userId);
}
