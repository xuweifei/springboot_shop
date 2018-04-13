package com.opengroup.longmao.gwcommon.service;

import com.opengroup.longmao.gwcommon.entity.po.ActConfig;

/**
 * 【活动配置表】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年12月12日 下午14:41:20
 */ 
public interface ActConfigService {

	 /**
	  * @Title: findActConfigByType 
	  * @Description: 【查询活动配置表】
	  * @param activityType
	  * @return    设定文件 
	  * ActConfig    返回类型 
	  * @author Yangst
	  * @throws
	  */
	 ActConfig findActConfigByType(Short activityType);

}

