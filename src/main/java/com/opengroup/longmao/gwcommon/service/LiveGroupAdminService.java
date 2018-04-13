package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.opengroup.longmao.gwcommon.entity.po.LiveGroupAdmin;

/**
 * 【直播群组管理员表】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年07月13日 上午09:50:49
 */ 
public interface LiveGroupAdminService {


	 /**
	 * 【查询直播群组管理员表】
	 * @param id
	 * @return LiveGroupAdmin
	 * @version 1.0
	 * @author Hermit 2017年07月13日 上午09:50:49
	 */ 
	 List<LiveGroupAdmin> findLiveGroupAdminByGroupIdAndUserId(Long groupId,Long userId);
	 
	 /**
	  * @Title: findLiveGroupAdminByParam 
	  * @Description: 【查询该用户是否是该直播间群组管理员表】
	  * @param groupId
	  * @param userId
	  * @param liveUserId
	  * @return    设定文件 
	  * LiveGroupAdmin    返回类型 
	  * @author Yangst
	  * @throws
	  */
	 LiveGroupAdmin findLiveGroupAdminByParam(Long groupId,Long userId,Long liveUserId);
	 
	 /**
	  * 
	  * @Title: countLiveGroupAdmins 
	  * @Description: 【查询该直播间群组管理员数量表】
	  * @param groupId
	  * @param userId
	  * @return    设定文件 
	  * Integer    返回类型 
	  * @author Yangst
	  * @throws
	  */
	 Integer countLiveGroupAdmins(Long groupId,Long userId);

	 /**
	 * 【查询直播群组管理员表】
	 * @param liveGroupAdmin
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<LiveGroupAdmin>
	 * @version 1.0
	 * @author Hermit 2017年07月13日 上午09:50:49
	 */ 
	 Page<LiveGroupAdmin> findLiveGroupAdmin(LiveGroupAdmin liveGroupAdmin,Integer pageNo,Integer pageSize,String sortField);


}

