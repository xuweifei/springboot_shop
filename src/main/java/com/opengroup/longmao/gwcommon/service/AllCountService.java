package com.opengroup.longmao.gwcommon.service;

import org.springframework.data.domain.Page;
import com.opengroup.longmao.gwcommon.entity.po.AllCount;

/**
 * 【全服统计表】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年12月21日 下午17:16:36
 */ 
public interface AllCountService {

	 /**
	 * 【查询全服统计表】
	 * @param allCount
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<AllCount>
	 * @version 1.0
	 * @author Hermit 2017年12月21日 下午17:16:36
	 */ 
	 Page<AllCount> findAllCount(AllCount allCount,Integer pageNo,Integer pageSize,String sortField);


}

