package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.CoinAccount;

/**
 * 【龙猫币账户】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:49:16
 */ 
public interface CoinAccountRepositorySlave extends BaseRepository<CoinAccount, Long> {

	/**
	 * 
	 * 【请根据id查询龙猫币账户】
	 * 
	 * @author Administrator 2017年3月15日
	 * @param userId
	 * @return
	 */
	@Query("select c from CoinAccount c where c.userId = ?1")
	CoinAccount queryCoinaccountByUserIdEntity(String userId);
	
}

