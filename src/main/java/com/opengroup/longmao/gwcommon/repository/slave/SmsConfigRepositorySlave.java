package com.opengroup.longmao.gwcommon.repository.slave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.SmsConfig;

/**
 * 
 * (龙猫运营后台短信配置dao层接口-从库) 
 * @ClassName SmsConfigRepositoryMaster 
 * @author ShenZiYang 
 * @date 2017年12月27日 上午11:45:55
 */
public interface SmsConfigRepositorySlave extends BaseRepository<SmsConfig, Long>{
	
	
	

	/**
	 * 
	 * @Description TODO(根据短信名称查询短信模版) 
	 * @Title findSmsConfigBySmsName 
	 * @param smsName
	 * @return SmsConfig返回类型   
	 * @author ShenZiYang
	 * @date 2017年12月27日下午5:26:22
	 * @throws
	 */
	@Query("SELECT s FROM SmsConfig s WHERE s.smsName = ?1 AND s.isDelete = 1")
	SmsConfig findSmsConfigBySmsName(String smsName);
	
	
	/**
	 * 
	 * (根据短信标识查询短信配置实体) 
	 * @Title findSmsConfigBySmsMark 
	 * @param smsMark
	 * @return SmsConfig返回类型   
	 * @author ShenZiYang
	 * @date 2018年1月9日下午2:46:23
	 * @throws 异常
	 */
	@Query("SELECT s FROM SmsConfig s WHERE s.smsMark = ?1 AND s.isDelete = 1")
	SmsConfig findSmsConfigBySmsMark(String smsMark);
	
	
	/**
	 * 
	 * 【查询短信配置列表】
	 * 
	 * @author sunchangjunn 2018年1月2日
	 * @return
	 */
	@Query("select s from SmsConfig s  where s.isDelete=1 order by utime desc")
	List<SmsConfig> getSmsConfigList();
	/**
	 * 
	 * 【根据id查询对象信息】
	 * 
	 * @author sunchangjunn 2018年1月2日
	 * @param smsId
	 * @return
	 */
	@Query("select s from SmsConfig s  where s.smsId=?1 and s.isDelete=1 ")
	SmsConfig getSmsConfigById(Long smsId);
	
}
