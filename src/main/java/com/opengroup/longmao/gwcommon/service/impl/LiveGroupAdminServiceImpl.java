package com.opengroup.longmao.gwcommon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.properties.ApiConfig;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.LiveGroupAdmin;
import com.opengroup.longmao.gwcommon.repository.master.LiveGroupAdminRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.LiveGroupAdminQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.LiveGroupAdminRepositorySlave;
import com.opengroup.longmao.gwcommon.service.LiveGroupAdminService;

/**
 * 【直播群组管理员表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年07月13日 上午09:50:49
 */ 
@Service
public class LiveGroupAdminServiceImpl implements LiveGroupAdminService{

	@Autowired
	private LiveGroupAdminRepositoryMaster liveGroupAdminRepositoryMaster;

	@Autowired
	private LiveGroupAdminRepositorySlave liveGroupAdminRepositorySlave;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private ApiConfig apiConfig;

	/**
	* 【分页查询直播群组管理员表】
	* @param liveGroupAdmin
	* @param pageNo
	* @param pageSize
	* @param sortField
	* @return liveGroupAdmin
	* @version 1.0
	* @author Hermit 2017年07月13日 上午09:50:49
	*/ 
	@Override
	public Page<LiveGroupAdmin> findLiveGroupAdmin(LiveGroupAdmin liveGroupAdmin,Integer pageNo, Integer pageSize, String sortField){
		// 组合查询语句
		LiveGroupAdminQueryFilter query = new LiveGroupAdminQueryFilter();
		//query.setId(liveGroupAdmin.getId());
		//query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		//字段排序
		Sort sort = new Sort(Direction.DESC, sortField);
		// 分页
		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		Page<LiveGroupAdmin> pageList = liveGroupAdminRepositorySlave.findAll(query, page);
		return pageList;
	}

	/**
	* 【根据id查询直播群组管理员表】
	* @param id
	* @return liveGroupAdmin
	* @version 1.0
	* @author Hermit 2017年07月13日 上午09:50:49
	*/ 
	@Override
	public List<LiveGroupAdmin> findLiveGroupAdminByGroupIdAndUserId(Long groupId,Long userId){
		return liveGroupAdminRepositorySlave.findLiveGroupAdminByGroupIdAndUserId(groupId,userId);
	}
	
	@Override
	public LiveGroupAdmin findLiveGroupAdminByParam(Long groupId,Long userId,Long liveUserId){
		return liveGroupAdminRepositorySlave.findLiveGroupAdminByParam(groupId,userId,liveUserId);
	}

	@Override
	public Integer countLiveGroupAdmins(Long groupId, Long userId) {
		return liveGroupAdminRepositorySlave.countLiveGroupAdmins(groupId,userId);
	}

}


