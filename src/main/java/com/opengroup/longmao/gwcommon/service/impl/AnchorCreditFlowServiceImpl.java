package com.opengroup.longmao.gwcommon.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditFlow;
import com.opengroup.longmao.gwcommon.enums.IsNormalEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.AnchorCreditFlowQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.AnchorCreditFlowRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AnchorCreditFlowService;
/**
 * 【主播信誉等流水】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:08:34
 */ 
@Service
public class AnchorCreditFlowServiceImpl implements AnchorCreditFlowService{

	@Autowired
	private AnchorCreditFlowRepositorySlave anchorCreditFlowRepositorySlave;
	
	/**
	* 【根据主键id查询主播信誉流水】
	* @param id
	* @return anchorCreditFlow
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:08:34
	*/ 
	@Override
	public AnchorCreditFlow findAnchorCreditFlow(Long id){
		AnchorCreditFlow anchorCreditFlow = null;
		if(StringUtils.isNotBlank(id.toString())){
			anchorCreditFlow = anchorCreditFlowRepositorySlave.findOne(id);
		}else{
		  GwsLogger.info("id不存在");
		}
		return anchorCreditFlow;
	}
	
	/**
	* 【根据anchorId查询主播信誉流水，默认降序排列】
	* @param anchorId
	* @param pageNo
	* @param pageSize
	* @param sortField
	* @return anchorCreditFlow
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:08:34
	*/ 
	@Override
	public Page<AnchorCreditFlow> findAnchorCreditFlow(Long anchorId,Integer pageNo, Integer pageSize, String sortField){
		// 组合查询语句
		AnchorCreditFlowQueryFilter query = new AnchorCreditFlowQueryFilter();
		query.setIsDelete(IsNormalEnum.YES.getVal());
		if(StringUtils.isNotBlank(anchorId.toString())){
			query.setAnchorId(anchorId);
		}
		//字段排序
		Sort sort = new Sort(Direction.DESC, sortField);
		// 分页
		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		Page<AnchorCreditFlow> pageList = anchorCreditFlowRepositorySlave.findAll(query, page);
		return pageList;
	}

	/**
	* 【分页查询主播信誉流水，默认降序排列】
	* @param anchorCreditFlow
	* @param pageNo
	* @param pageSize
	* @param sortField
	* @return anchorCreditFlow
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:08:34
	*/ 
	@Override
	public Page<AnchorCreditFlow> findAnchorCreditFlow(AnchorCreditFlow anchorCreditFlow,Integer pageNo, Integer pageSize, String sortField){
		// 组合查询语句
		AnchorCreditFlowQueryFilter query = new AnchorCreditFlowQueryFilter();
		query.setIsDelete(IsNormalEnum.YES.getVal());
		if(anchorCreditFlow != null){
			if(StringUtils.isNotBlank(anchorCreditFlow.getId().toString())){
				query.setId(anchorCreditFlow.getId());
			}
			if(StringUtils.isNotBlank(anchorCreditFlow.getAnchorId().toString())){
				query.setAnchorId(anchorCreditFlow.getAnchorId());
			}
			if(StringUtils.isNotBlank(anchorCreditFlow.getAddOrDel().toString())){
				query.setAddOrDel(anchorCreditFlow.getAddOrDel());
			}
			if(StringUtils.isNotBlank(anchorCreditFlow.getRuleId().toString())){
				query.setRuleId(anchorCreditFlow.getRuleId());
			}
		}
		//字段排序
		Sort sort = new Sort(Direction.DESC, sortField);
		// 分页
		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		Page<AnchorCreditFlow> pageList = anchorCreditFlowRepositorySlave.findAll(query, page);
		return pageList;
	}

	
	/**
	* 【保存主播信誉流水】
	* @param anchorCreditFlow
	* @return anchorCreditFlow
	* @version 1.0
	* @author Hermit 2017年03月23日 上午09:08:34
	*/ 
//	@Override
//	public AnchorCreditFlow saveAnchorCreditFlow(AnchorCreditFlow anchorCreditFlow){
//		//判断对象是否存在
//		if(anchorCreditFlow!= null){
//		   //id统一生成
//		   Long id = idGlobalGenerator.getSeqId(AnchorCreditFlow.class);
//		   anchorCreditFlow.setId(id);
//		   anchorCreditFlow = anchorCreditFlowRepositoryMaster.save(anchorCreditFlow);
//		   GwsLogger.info("主播信誉等流水保存成功");
//		}else{
//			GwsLogger.info("主播信誉等流水对象不存在，保存失败");
//		    return null;
//		}
//		return anchorCreditFlow;
//	}

}
