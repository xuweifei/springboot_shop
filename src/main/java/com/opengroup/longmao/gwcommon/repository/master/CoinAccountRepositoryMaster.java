package com.opengroup.longmao.gwcommon.repository.master;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.CoinAccount;

/**
 * 【龙猫币账户】 RepositoryMaster接口
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:49:16
 */ 
public interface CoinAccountRepositoryMaster extends BaseRepository<CoinAccount, Long> {
	
	@Query("select c from CoinAccount c where c.userId = ?1")
	CoinAccount queryCoinByUserId(String userId);
	
	@Query("select c from CoinAccount c where c.lmCoinNum > 0 and c.userId != '777777' and c.userId != '888888'")
	List<CoinAccount> queryCoinS();

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update CoinAccount c set c.lmCoinNum = c.lmCoinNum + ?1, c.gmtModified = ?2, c.gmtModifiedUser = ?3 where c.userId= ?4")
	Integer updateCoinAccount(Double coinNum, Date gmtModified, String gmtModifiedUser, String userId);
}

