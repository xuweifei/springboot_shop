package com.opengroup.longmao.gwcommon.repository.master;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;

/**
 * 【龙猫豆账户】 RepositoryMaster接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:48:32
 */ 
public interface BeanAccountRepositoryMaster extends BaseRepository<BeanAccount, Long> {
	
	/**
	 * 【根据id查询龙猫豆账户】
	 * @author Hermit 2017年3月15日
	 * @param id
	 * @return
	 */
	@Query("select b from BeanAccount b where b.id = ?1")
	BeanAccount queryById(String id);

	/**
	 * @Title: queryBeanByUserId 
	 * @Description: userId 获取用户龙猫豆 
	 * @param userId
	 * @return BeanAccount
	 */
	@Query("select b from BeanAccount b where b.userId = ?1")
	BeanAccount queryBeanByUserId(String userId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update BeanAccount ba set ba.lmBeanNum = ba.lmBeanNum +?1,ba.gmtModified =?2 where ba.id= ?3")
	Integer updateBeanAccount(Double changeBeans,Date gmtModified,String id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update BeanAccount b set b.lmBeanNum = b.lmBeanNum + ?1, b.gmtModified = ?2, b.gmtModifiedUser = ?3 where b.userId= ?4")
	Integer updateBeanAccount(Double beanNum, Date gmtModified, String gmtModifiedUser, String userId);
	
}

