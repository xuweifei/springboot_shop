package com.opengroup.longmao.gwcommon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.AnchorGradeRule;
import com.opengroup.longmao.gwcommon.entity.po.ExpChangeRecord;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.repository.master.ExpChangeRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.IdentityInfoRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.AnchorGradeRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.IdentityInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.service.AnchorGradeRuleService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;

/**
 * 
 *【主播等级规则】Service接口实现类  
 * @author ShenZiYang 
 * @date 2018年3月13日 下午1:54:54
 */
@Service("anchorGradeRuleService")
public class AnchorGradeRuleServiceImpl implements AnchorGradeRuleService{
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	@Autowired
	private IdentityInfoRepositorySlave identityInfoRepositorySlave;
	@Autowired
	private IdentityInfoRepositoryMaster identityInfoRepositoryMaster;
	@Autowired
	private AnchorGradeRuleRepositorySlave anchorGradeRuleRepositorySlave;
	@Autowired
	private ExpChangeRecordRepositoryMaster expChangeRecordRepositoryMaster;
	
	
	/**
	 * 
	 *【查询主播等级ByUserId】  
	 * @param userId 主播Id
	 * @return 返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月15日下午3:00:45
	 * @throws 异常
	 */
	@Override
	public Map<String, Object> findAnchorGradeRule(String userId) {
		IdentityInfo uiIdentity = identityInfoRepositorySlave.queryIdentityByUserId(userId);
		
		
		if(uiIdentity != null){
			Long exper = uiIdentity.getExperience(); //获取经验值
			List<AnchorGradeRule> agrList = anchorGradeRuleRepositorySlave.findAnchorGradeRuleList(exper, IsDeleteEnum.UN_DELETE.getVal());
			if(CollectionUtils.isNotEmpty(agrList) && 1 <= agrList.size()){
				Map<String,Object> ruleMap = new HashMap<String,Object>();
				AnchorGradeRule agr = agrList.get(0);
				Long differValue = agr.getMaxVal() - exper;  //距离某某等级还差differValue经验值
				ruleMap.put("grade", agr.getGrade()); //主播当前等级
				ruleMap.put("alias", agr.getAlias());
				ruleMap.put("experience",  exper);
				ruleMap.put("differValue", differValue);
				ruleMap.put("maxVal", agr.getMaxVal());
				ruleMap.put("minVal", agr.getMinVal());
				ruleMap.put("isMax", agr.getIsMax());
				return ruleMap;
			}
		}
		
		return null;
	}
	
	@Override
	public Short findAnchorGrade(Long calorie) {
		if (null != calorie) {
			List<AnchorGradeRule> agrList = anchorGradeRuleRepositorySlave.findAnchorGradeRuleList(calorie,IsDeleteEnum.UN_DELETE.getVal());
			if (CollectionUtils.isNotEmpty(agrList) && 1 <= agrList.size()) {
				AnchorGradeRule agr = agrList.get(0);
				return agr.getGrade();
			}
		}
		return null;
	}
	
	/**
	 * 
	 *【更新主播经验值】  
	 * @param userId 主播ID
	 * @param anchorExper 主播经验值
	 * @return 返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月15日下午3:01:30
	 * @throws 异常
	 */
	@Override
	public boolean updateAnchorExp(String userId, Long anchorExper) {
		boolean flag = false;
		IdentityInfo uiIdentity = identityInfoRepositorySlave.queryIdentityByUserId(userId);
		if(uiIdentity != null){
			Long newExper = uiIdentity.getExperience() + anchorExper; //新经验值=已有经验值+开播所获经验值 
			List<AnchorGradeRule> agrList =  anchorGradeRuleRepositorySlave.findAnchorGradeRuleList(newExper, IsDeleteEnum.UN_DELETE.getVal());
			Integer grade = uiIdentity.getGrade(); //默认等级为账户原等级
			
			if(CollectionUtils.isNotEmpty(agrList) && 1 <= agrList.size()){
				AnchorGradeRule agr = agrList.get(0); 
				grade = agr.getGrade().intValue();
			}
			Integer res = identityInfoRepositoryMaster.updateAnchorExper(anchorExper, grade, DateUtil.currentSecond(), "EXP", uiIdentity.getId());
			//创建主播经验值流水
			createAnchorRecord(uiIdentity,anchorExper,IsPlusEnum.PLUS.getVal().shortValue(),Short.valueOf("0"));
			flag = (Integer.valueOf("1").equals(res)) ? true : false;
		}
		return flag;
	}
	
	/**
	 * 
	 *【创建主播经验值流水】 
	 * @param uiIdentity 主播认证实体
	 * @param anchorExper 经验值
	 * @param shortValue 
	 * @param valueOf void返回类型   
	 * @author ShenZiYang
	 * @date 2018年3月16日上午10:24:40
	 * @throws 异常
	 */
	private ExpChangeRecord createAnchorRecord(IdentityInfo uiIdentity, Long anchorExper, Short changeType, Short bizType) {
		ExpChangeRecord exp = new ExpChangeRecord();
		Long expId = idGlobalGenerator.getSeqId(ExpChangeRecord.class);
		
		exp.setExpId(expId);
		exp.setUserId(uiIdentity.getUserId());
		exp.setChangeType(changeType);
		exp.setChangeNum(anchorExper);
		exp.setBizType(bizType);
		exp.setOutBizId(uiIdentity.getId().toString());
		exp.setCtime(DateUtil.currentSecond());
		exp.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		ExpChangeRecord ret = expChangeRecordRepositoryMaster.save(exp);
		if(null != ret){
			GwsLogger.info("创建主播经验值流水成功:ret={}",JSON.toJSON(ret));
			return ret;
		}
		GwsLogger.error("创建主播经验值流水失败:ret={}",JSON.toJSON(ret));
		return null;
	}

	
	
}
