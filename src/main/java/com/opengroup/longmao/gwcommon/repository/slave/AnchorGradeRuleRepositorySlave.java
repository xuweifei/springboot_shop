package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.AnchorGradeRule;

/**
 * 
 *【主播等级规则表】RepositorySlave接口
 * @author ShenZiYang 
 * @date 2018年3月13日 下午1:50:01
 */
public interface AnchorGradeRuleRepositorySlave extends BaseRepository<AnchorGradeRule, Long>{
	
	
	/**
	 * 
	 *【根据主播经验值判断当前主播等级】 
	 * @param exper
	 * @param isDelete
	 * @return AnchorGradeRule返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月13日下午2:56:06
	 * @throws 异常
	 */
	@Query("SELECT anchor FROM AnchorGradeRule anchor WHERE anchor.minVal <= ?1 AND anchor.maxVal > ?1 AND anchor.isDelete = ?2")
	AnchorGradeRule findAnchorGradeRuleByExper(Long exper,Short isDelete);
	
	/**
	 * 
	 *【根据主播经验值判断当前主播等级】
	 * OR语句是用来判断主播的等级经验值达到最大值时
	 * @param levelVal
	 * @param isDelete
	 * @return List<AnchorGradeRule>返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月16日上午10:47:30
	 * @throws
	 */
	@Query("SELECT anchor FROM AnchorGradeRule anchor WHERE (anchor.minVal <= ?1 AND anchor.maxVal > ?1 AND anchor.isDelete = ?2) OR anchor.isMax = 1 ORDER BY grade ASC")
	List<AnchorGradeRule> findAnchorGradeRuleList(Long levelVal, Short isDelete);
	
	
	
}
