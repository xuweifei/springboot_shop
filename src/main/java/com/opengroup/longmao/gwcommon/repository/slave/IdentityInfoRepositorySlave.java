/**
 * @Title: IdentityInfoRepositorySlave.java 
 * @Package com.opengroup.longmao.gwcommon.repository.slave 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;

/**
 * @ClassName: IdentityInfoRepositorySlave 
 * @Description: 主播直播认证、转账提现 RepositorySlave 接口
 * @author Mr.Zhu
 */
public interface IdentityInfoRepositorySlave extends BaseRepository<IdentityInfo, Long> {
	
	@Query("select i from IdentityInfo i where i.userId = ?1")
	IdentityInfo queryIdentityByUserId(String userId);
	
	/**
	 * 
	 *【查询主播ID集合】 
	 * @return List<String>返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月27日上午11:43:31
	 * @throws 异常
	 */ 
	@Query("SELECT i.userId FROM IdentityInfo i WHERE i.status = 4")
	List<String> queryUserIdList();
	
	
	
	
	
}
