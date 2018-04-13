/**
 * @Title: IdentityInfoRepositoryMaster.java 
 * @Package com.opengroup.longmao.gwcommon.repository.master 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.repository.master;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;

/**
 * @ClassName: IdentityInfoRepositoryMaster 
 * @Description: 主播直播认证、转账提现 RepositoryMaster 接口
 * @author Mr.Zhu
 */
public interface IdentityInfoRepositoryMaster extends BaseRepository<IdentityInfo, Long> {
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE IdentityInfo i SET i.opal = i.opal + ?1, i.gmtModified = ?2, i.gmtModifiedUser = ?3 WHERE i.id= ?4")
	Integer updateOpal(Long opal, Integer gmtModified, String gmtModifiedUser, Long id);
	
	
	/**
	 * 
	 *【更新主播经验值】 
	 * @param experience 经验值
	 * @param grade 主播等级
	 * @param gmtModified 修改时间
	 * @param gmtModifiedUser 修改者
	 * @param id 
	 * @return Integer返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月13日下午5:31:57
	 * @throws 异常
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE IdentityInfo i SET i.experience = i.experience + ?1, i.grade = ?2, i.gmtModified = ?3, i.gmtModifiedUser = ?4 WHERE i.id= ?5")
	Integer updateAnchorExper(Long experience, Integer grade, Integer gmtModified, String gmtModifiedUser, Long id);
	
	
	/**
	 * 
	 *【更新主播经验值和等级】 
	 * @param experience
	 * @param grade
	 * @param userId
	 * @return Integer返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月27日下午2:23:19
	 * @throws
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE IdentityInfo i SET i.experience = ?1, i.grade = ?2 WHERE i.userId= ?3")
	Integer updateGradeAndExper(Long experience,Integer grade,String userId);
	
	
	
}
