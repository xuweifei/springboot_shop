/**
 * @Title: IdentityServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;
import com.opengroup.longmao.gwcommon.repository.master.IdentityInfoRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.IdentityInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.service.IdentityService;

/**
 * 
 *【IdentityServiceImpl 】  
 * @author ShenZiYang 
 * @date 2018年3月19日 下午6:09:40
 */
@Service("identityService")
public class IdentityServiceImpl implements IdentityService {
	
	@Autowired
	private IdentityInfoRepositorySlave identityInfoRepositorySlave;
	
	@Autowired
	private IdentityInfoRepositoryMaster identityInfoRepositoryMaster;
	
	@Override
	public IdentityInfo findIdentityByUserId(String userId) {
		return identityInfoRepositorySlave.queryIdentityByUserId(userId);
	}

	@Override
	public List<String> findUserIdList() {
		return identityInfoRepositorySlave.queryUserIdList();
	}

	@Override
	public int updateGradeAndExper(Long calorie, Integer grade, String userId) {
		return identityInfoRepositoryMaster.updateGradeAndExper(calorie, grade, userId);
	}
	
	
}
