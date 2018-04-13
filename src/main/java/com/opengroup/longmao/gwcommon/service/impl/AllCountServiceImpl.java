package com.opengroup.longmao.gwcommon.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.AllCount;
import com.opengroup.longmao.gwcommon.repository.master.AllCountRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.AllCountRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AllCountService;
import com.opengroup.longmao.gwcommon.repository.queryFilter.AllCountQueryFilter;

/**
 * 【全服统计表】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年12月21日 下午17:16:36
 */ 
@Service
public class AllCountServiceImpl implements AllCountService{

	@Autowired
	private AllCountRepositoryMaster allCountRepositoryMaster;

	@Autowired
	private AllCountRepositorySlave allCountRepositorySlave;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;

	/**
	* 【分页查询全服统计表】
	* @param allCount
	* @param pageNo
	* @param pageSize
	* @param sortField
	* @return allCount
	* @version 1.0
	* @author Hermit 2017年12月21日 下午17:16:36
	*/ 
	@Override
	public Page<AllCount> findAllCount(AllCount allCount,Integer pageNo, Integer pageSize, String sortField){
		// 组合查询语句
		AllCountQueryFilter query = new AllCountQueryFilter();
		//query.setId(allCount.getId());
		query.setCountType(allCount.getCountType());
		//query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		//字段排序
		Sort sort = new Sort(Direction.ASC, sortField);
		// 分页
		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		Page<AllCount> pageList = allCountRepositorySlave.findAll(query, page);
		return pageList;
	}

	

}


