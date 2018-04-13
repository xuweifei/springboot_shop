package com.opengroup.longmao.gwcommon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditRule;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;
import com.opengroup.longmao.gwcommon.service.AnchorCreditRuleService;
import com.opengroup.longmao.gwcommon.service.IncomeService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【[全民主播]信誉等级提成规则】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午11:38:18
 */
@RestController
@EnableSwagger2
@Api(value = "[全民主播]信誉等级提成规则", tags = "anchorCreditRule")
@RequestMapping(value = { "/anchorCreditRule" })
public class AnchorCreditRuleController {

	@Autowired
	private AnchorCreditRuleService anchorCreditRuleService;
	
	@Autowired
	private IncomeService incomeService;

	/**
	 * 【根据userId查询用户信誉等级】
	 * 
	 * @param id
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午11:38:18
	 */
	@ApiOperation(value = "根据userId查询用户信誉等级", notes = "根据userId查询用户信誉等级")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String")
	@RequestMapping(value = "/findCreditGradeByUserId", method = RequestMethod.GET)
	public RetResult findCreditGradeByUserId(String userId) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据userId查询用户信誉等级开始:code={},message={},id={}", code, message, userId);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(StringUtils.isNotBlank(userId)){
				// 根据userId查询用户信誉等级
				IdentityInfo info = incomeService.queryIdentityByUserId(userId);
				if(null != info){
					map.put("creditGrade", info.getCreditGrade());
					map.put("creditGradeExplain", info.getCreditGradeExplain());
				}
			}else{
				code = CommConstant.GWSCOD0003;
			    message = CommConstant.GWSMSG0003;
				GwsLogger.error("userId为空:code={},message={},", code, message);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("根据userId查询用户信誉等级异常:code={},message={},id={},e={}", code, message, userId, e);
		}
		GwsLogger.info("根据userId查询用户信誉等级结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, map);
	}
	
	/**
	 * 【根据id查询[全民主播]信誉等级提成规则】
	 * 
	 * @param id
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午11:38:18
	 */
	@ApiOperation(value = "根据id查询[全民主播]信誉等级提成规则", notes = "根据id查询[全民主播]信誉等级提成规则")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Long")
	@RequestMapping(value = "/findAnchorCreditRuleById", method = RequestMethod.GET)
	public RetResult findAnchorCreditRuleById(Long id) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据id查询[全民主播]信誉等级提成规则开始:code={},message={},id={}", code, message, id);
		// 根据id查询[全民主播]信誉等级提成规则
		AnchorCreditRule anchorCreditRule = null;
		try {
			if(StringUtils.isNotBlank(id.toString())){
			   anchorCreditRule = anchorCreditRuleService.findAnchorCreditRule(id);
			}else{
				code = CommConstant.GWSCOD0003;
			    message = CommConstant.GWSMSG0003;
				GwsLogger.error("id为空:code={},message={},", code, message);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("根据id查询[全民主播]信誉等级提成规则异常:code={},message={},id={},e={}", code, message, id, e);
		}
		GwsLogger.info("根据id查询[全民主播]信誉等级提成规则结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, anchorCreditRule);
	}

	/**
	 * 【查询所有[全民主播]信誉等级提成规则】
	 * 
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午11:38:18
	 */
	@ApiOperation(value = "查询所有[全民主播]信誉等级提成规则", notes = "查询所有[全民主播]信誉等级提成规则")
	@RequestMapping(value = "/findAllAnchorCreditRule", method = RequestMethod.GET)
	public RetResult findAllAnchorCreditRule() {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("查询所有[全民主播]信誉等级提成规则开始");
		// 查询所有[全民主播]信誉等级提成规则
		List<AnchorCreditRule> list = null;
		try {
			list = anchorCreditRuleService.findAnchorCreditRule();
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询所有[全民主播]信誉等级提成规则异常:code={},message={},e={}", code, message, e);
		}
		GwsLogger.info("查询所有[全民主播]信誉等级提成规则结束");
		return RetResult.setRetDate(code, message, list);
	}

}