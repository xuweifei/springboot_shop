package com.opengroup.longmao.gwcommon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.opengroup.longmao.gwcommon.entity.po.BeanChangeRecord;

/**
 * 【龙猫豆流水】 service接口
 *
 * @version 1.0
 * @author Hermit 2017年04月12日 下午15:44:56
 */ 
public interface BeanChangeRecordService {

	 /**
	 * 【保存龙猫豆流水】
	 * @param beanChangeRecord
	 * @return beanChangeRecord
	 * @version 1.0
	 * @author Hermit 2017年04月12日 下午15:44:56
	 */ 
	 BeanChangeRecord saveBeanChangeRecord(BeanChangeRecord beanChangeRecord);

	 /**
	 * 【查询龙猫豆流水】
	 * @param id
	 * @return BeanChangeRecord
	 * @version 1.0
	 * @author Hermit 2017年04月12日 下午15:44:56
	 */ 
	 BeanChangeRecord findBeanChangeRecord(Long id);

	 /**
	 * 【查询龙猫豆流水】
	 * @param beanChangeRecord
	 * @param pageNo
	 * @param pageSize
	 * @param sortField
	 * @return Page<BeanChangeRecord>
	 * @version 1.0
	 * @author Hermit 2017年04月12日 下午15:44:56
	 */ 
	 Page<BeanChangeRecord> findBeanChangeRecord(BeanChangeRecord beanChangeRecord,Integer pageNo,Integer pageSize,String sortField);

	 /**
	  * 
	  * 【批量保存龙猫豆账户流水】
	  * 
	  * @author Hermit 2017年4月12日
	  * @param beanChangeRecordList
	  * @return
	  */
	Boolean updateBeanChangeRecordBatch(List<BeanChangeRecord> beanChangeRecordList);

}

