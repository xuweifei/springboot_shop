package com.opengroup.longmao.gwcommon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;
import com.opengroup.longmao.gwcommon.repository.master.LiveRoomInfoRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.LiveRoomInfoQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.LiveRoomInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.service.LiveRoomInfoService;

/**
 * 【直播房间表对象】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年12月22日 下午14:36:58
 */ 
@Service
public class LiveRoomInfoServiceImpl implements LiveRoomInfoService{

	@Autowired
	private LiveRoomInfoRepositoryMaster liveRoomInfoRepositoryMaster;

	@Autowired
	private LiveRoomInfoRepositorySlave liveRoomInfoRepositorySlave;

	/**
	* 【分页查询直播房间表对象】
	* @param liveRoomInfo
	* @param pageNo
	* @param pageSize
	* @param sortField
	* @return liveRoomInfo
	* @version 1.0
	* @author Hermit 2017年12月22日 下午14:36:58
	*/ 
	@Override
	public Page<LiveRoomInfo> findLiveRoomInfo(LiveRoomInfo liveRoomInfo,Integer pageNo, Integer pageSize, String sortField){
		// 组合查询语句
		LiveRoomInfoQueryFilter query = new LiveRoomInfoQueryFilter();
		//query.setId(liveRoomInfo.getId());
		//query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		//字段排序
		Sort sort = new Sort(Direction.DESC, sortField);
		// 分页
		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		Page<LiveRoomInfo> pageList = liveRoomInfoRepositorySlave.findAll(query, page);
		return pageList;
	}

	/**
	* 【根据id查询直播房间表对象】
	* @param id
	* @return liveRoomInfo
	* @version 1.0
	* @author Hermit 2017年12月22日 下午14:36:58
	*/ 
	@Override
	public LiveRoomInfo findLiveRoomInfo(Long id){
		return liveRoomInfoRepositorySlave.findOne(id);
	}

	/**
	 * 【根据主播查询直播房间表对象】
	 * @param id
	 * @return LiveRoomInfo
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */ 
	 public LiveRoomInfo findLiveRoomInfoByuserId(Long userId){
		 return liveRoomInfoRepositorySlave.findRoomInfoByUserId(userId);
	 }
	
	/**
	* 【保存直播房间表对象】
	* @param liveRoomInfo
	* @return liveRoomInfo
	* @version 1.0
	* @author Hermit 2017年12月22日 下午14:36:58
	*/ 
	@Override
	public LiveRoomInfo saveLiveRoomInfo(LiveRoomInfo liveRoomInfo){
		//判断对象是否存在
		if(liveRoomInfo!= null){
		   //id统一生成
		   //Long id = idGlobalGenerator.getSeqId(LiveRoomInfo.class);
		   //liveRoomInfo.setId(id);
		   liveRoomInfo = liveRoomInfoRepositoryMaster.save(liveRoomInfo);
		   GwsLogger.info("直播房间表对象保存成功");
		}else{
			GwsLogger.error("直播房间表对象对象不存在，保存失败:liveRoomInfo={}",JSON.toJSONString(liveRoomInfo));
		    return null;
		}
		return liveRoomInfo;
	}

	/**
	* 【修改直播房间表对象】
	* @param liveRoomInfo
	* @return liveRoomInfo
	* @version 1.0
	* @author Hermit 2017年12月22日 下午14:36:58
	*/ 
	@Override
	public LiveRoomInfo updateLiveRoomInfo(LiveRoomInfo liveRoomInfo){
		if(liveRoomInfo.getRoomId()!=null&&liveRoomInfo.getRoomId()>0){
		    //先从库中查出该对象
	        LiveRoomInfo liveRoomInfoBean = liveRoomInfoRepositorySlave.findOne(liveRoomInfo.getRoomId());
		    //判断对象是否存在
			if(liveRoomInfoBean!= null){
		       //该处数据填充代码请自行补全....
			   liveRoomInfo = liveRoomInfoRepositoryMaster.save(liveRoomInfoBean);
			   GwsLogger.info("直播房间表对象修改成功");
			}else{
			    GwsLogger.error("直播房间表对象对象不存在，修改失败:liveRoomInfo={}",JSON.toJSONString(liveRoomInfo));
		        return null;
			}
		}else{
			 GwsLogger.error("直播房间表对象id不存在，修改失败:liveRoomInfo={}",JSON.toJSONString(liveRoomInfo));
		     return null;
		}
		return liveRoomInfo;
	}

	/**
	 * 【根据id删除直播房间表对象】
	 * @param id
	 * @return void
	 * @version 1.0
	 * @author Hermit 2017年12月22日 下午14:36:58
	 */ 
	@Override
	public void deleteLiveRoomInfo(Long id){
		//先从库中查出该对象
		LiveRoomInfo liveRoomInfo = liveRoomInfoRepositorySlave.findOne(id);
		//判断对象是否存在
		if(liveRoomInfo!=null){
			//将用户状态改为删除
			//liveRoomInfo.setIsDelete(IsDeleteEnum.YES.getVal());
			LiveRoomInfo newLiveRoomInfo = liveRoomInfoRepositoryMaster.save(liveRoomInfo);
			//判断对象是否存在
			if(newLiveRoomInfo!=null){
				GwsLogger.info("直播房间表对象删除成功");
			}else{
				GwsLogger.info("直播房间表对象删除失败:id={}",id);
			}
		}else{
			GwsLogger.info("直播房间表对象对象不存在:id={}",id);
		}
	}
	
	/**
	 * @Title: queryLiveS 
	 * @Description: 查询主播By直播状态 
	 * @param playStatus
	 * @return List<Long>
	 */
	@Override
	public List<Long> queryLiveS(Short playStatus) {
		if (null != playStatus) {
			List<Long> userIdL = liveRoomInfoRepositorySlave.queryLiveS(playStatus);
			return userIdL;
		}
		return null;
	}

}


