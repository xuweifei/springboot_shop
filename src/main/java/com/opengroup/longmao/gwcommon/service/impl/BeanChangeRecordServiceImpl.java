package com.opengroup.longmao.gwcommon.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.BeanChangeRecord;
import com.opengroup.longmao.gwcommon.repository.master.BeanChangeRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.BeanChangeRecordQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.BeanChangeRecordRepositorySlave;
import com.opengroup.longmao.gwcommon.service.BeanChangeRecordService;
/**
 * 【龙猫豆流水】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年04月12日 下午15:44:56
 */ 
@Service
public class BeanChangeRecordServiceImpl implements BeanChangeRecordService{

	@Autowired
	private BeanChangeRecordRepositoryMaster beanChangeRecordRepositoryMaster;

	@Autowired
	private BeanChangeRecordRepositorySlave beanChangeRecordRepositorySlave;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;

	/**
	* 【分页查询龙猫豆流水】
	* @param beanChangeRecord
	* @param pageNo
	* @param pageSize
	* @param sortField
	* @return beanChangeRecord
	* @version 1.0
	* @author Hermit 2017年04月12日 下午15:44:56
	*/ 
	@Override
	public Page<BeanChangeRecord> findBeanChangeRecord(BeanChangeRecord beanChangeRecord,Integer pageNo, Integer pageSize, String sortField){
		// 组合查询语句
		BeanChangeRecordQueryFilter query = new BeanChangeRecordQueryFilter();
//		query.setIsDelete(IsNormalEnum.YES.getVal());
		//字段排序
		Sort sort = new Sort(Direction.DESC, sortField);
		// 分页
		Pageable page = new PageRequest(pageNo, pageSize, sort);
		// 查询分页数据
		Page<BeanChangeRecord> pageList = beanChangeRecordRepositorySlave.findAll(query, page);
		return pageList;
	}

	/**
	* 【根据id查询龙猫豆流水】
	* @param id
	* @return beanChangeRecord
	* @version 1.0
	* @author Hermit 2017年04月12日 下午15:44:56
	*/ 
	@Override
	public BeanChangeRecord findBeanChangeRecord(Long id){
		return beanChangeRecordRepositorySlave.findOne(id);
	}

	/**
	* 【保存龙猫豆流水】
	* @param beanChangeRecord
	* @return beanChangeRecord
	* @version 1.0
	* @author Hermit 2017年04月12日 下午15:44:56
	*/ 
	@Override
	public BeanChangeRecord saveBeanChangeRecord(BeanChangeRecord beanChangeRecord){
		//判断对象是否存在
		if(beanChangeRecord!= null){
		   //id统一生成
		   Long id = idGlobalGenerator.getSeqId(BeanChangeRecord.class);
		   beanChangeRecord.setId(id.toString());
		   beanChangeRecord.setGmtCreate(new Date());
		   beanChangeRecord.setGmtModified(new Date());
		   beanChangeRecord.setGmtCreateUser("");
		   beanChangeRecord.setGmtModifiedUser("");
		   beanChangeRecord = beanChangeRecordRepositoryMaster.save(beanChangeRecord);
		   GwsLogger.info("龙猫豆流水保存成功");
		}else{
			GwsLogger.info("龙猫豆流水对象不存在，保存失败:beanChangeRecord={}",ToStringBuilder.reflectionToString(beanChangeRecord));
		    return null;
		}
		return beanChangeRecord;
	}

	/**
	 * 
	 * 【批量保存龙猫豆账户流水】
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.service.BeanChangeRecordService#updateBeanChangeRecordBatch(java.util.List)
	 */
	@Override
	public Boolean updateBeanChangeRecordBatch(List<BeanChangeRecord> beanChangeRecordList) {
		boolean flg = false;
		List<BeanChangeRecord> baList = beanChangeRecordRepositoryMaster.save(beanChangeRecordList);
		if(CollectionUtils.isNotEmpty(baList)){
			flg = true;
		}
		return flg;
	}

}

