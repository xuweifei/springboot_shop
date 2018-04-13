package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.BrokerConfraternity;

/**
 * 
 *【工会信息】 RepositorySlave接口
 * @author ShenZiYang 
 * @date 2018年3月14日 下午4:02:19
 */
public interface BrokerConfraternityRepositorySlave extends BaseRepository<BrokerConfraternity, Long>{
	
	/**
	 * 
	 *【根据公会ID查询公会实体】 
	 * @param confraternityId
	 * @return BrokerConfraternity返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月14日下午4:03:52
	 * @throws 异常
	 */
	@Query("SELECT bc FROM BrokerConfraternity bc WHERE bc.confraternityId = ?1 AND bc.isDelete = 1")
	BrokerConfraternity findBrokerConById(Long confraternityId);
	
}
