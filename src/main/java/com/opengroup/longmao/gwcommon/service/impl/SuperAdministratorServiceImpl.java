package com.opengroup.longmao.gwcommon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.entity.po.SuperAdministrator;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.repository.slave.SuperAdministratorRepositorySlave;
import com.opengroup.longmao.gwcommon.service.SuperAdministratorService;

/**
 * 【App超级管理表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年11月27日 上午10:42:52
 */ 
@Service
public class SuperAdministratorServiceImpl implements SuperAdministratorService{

	@Autowired
	private SuperAdministratorRepositorySlave superAdministratorRepositorySlave;

	/**
	 * @Title: findSuperAdministrator 
	 * @Description: 查询APP超管 
	 * @param userId
	 * @return SuperAdministrator
	 */ 
	@Override
	public SuperAdministrator findSuperAdministrator(Long userId){
		return superAdministratorRepositorySlave.findSuperAdministrator(userId, IsDeleteEnum.UN_DELETE.getVal());
	}
}


