package com.opengroup.longmao.gwcommon.repository.slave;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;

/**
 * 【龙猫豆账户】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:48:32
 */ 
public interface BeanAccountRepositorySlave extends BaseRepository<BeanAccount, Long> {

//	/**
//	 * 【根据id查询龙猫豆账户】
//	 * @author Hermit 2017年3月15日
//	 * @param id
//	 * @return
//	 */
//	@Query("select b from BeanAccount b where b.id = ?1")
//	BeanAccount queryById(String id);
//
//	/**
//	 * @Title: queryBeanByUserId 
//	 * @Description: userId 获取用户龙猫豆 
//	 * @param userId
//	 * @return BeanAccount
//	 */
//	@Query("select b from BeanAccount b where b.userId = ?1")
//	BeanAccount queryBeanByUserId(String userId);
}

