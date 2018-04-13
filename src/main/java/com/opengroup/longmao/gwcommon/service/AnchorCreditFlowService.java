package com.opengroup.longmao.gwcommon.service;

import org.springframework.data.domain.Page;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditFlow;

/**
 * 【主播信誉流水】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:08:34
 */ 
public interface AnchorCreditFlowService {

	 /**
	 * 【保存主播信誉流水】
	 * @param anchorCreditFlow
	 * @return anchorCreditFlow
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */ 
//	 AnchorCreditFlow saveAnchorCreditFlow(AnchorCreditFlow anchorCreditFlow);

	 /**
	 * 【根据主键id查询主播信誉流水】
	 * @param id
	 * @return AnchorCreditFlow
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */ 
	 AnchorCreditFlow findAnchorCreditFlow(Long id);
	 
	 /**
	 * 【根据anchorId查询主播信誉流水，默认降序排列】
	 * @param anchorId
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<AnchorCreditFlow>
	 * @return AnchorCreditFlow
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */ 
	 Page<AnchorCreditFlow> findAnchorCreditFlow(Long anchorId,Integer pageNo,Integer pageSize,String sortField);

	 /**
	 * 【分页查询主播信誉流水，默认降序排列】
	 * @param anchorCreditFlow
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<AnchorCreditFlow>
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */ 
	 Page<AnchorCreditFlow> findAnchorCreditFlow(AnchorCreditFlow anchorCreditFlow,Integer pageNo,Integer pageSize,String sortField);

}

