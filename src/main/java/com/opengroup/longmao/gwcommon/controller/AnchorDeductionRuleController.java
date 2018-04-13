package com.opengroup.longmao.gwcommon.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorDeductionRule;
import com.opengroup.longmao.gwcommon.service.AnchorDeductionRuleService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【主播信誉减分规则】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午11:38:40
 */
@RestController
@EnableSwagger2
@Api(value = "主播信誉减分规则", tags = "anchorDeductionRule")
@RequestMapping(value = { "/anchorDeductionRule" })
public class AnchorDeductionRuleController {

	@Autowired
	private AnchorDeductionRuleService anchorDeductionRuleService;

	/**
	 * 【根据id查询主播减分规则】
	 * 
	 * @param id
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午11:38:40
	 */
	@ApiOperation(value = "根据id查询主播减分规则", notes = "根据id查询主播减分规则")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Long")
	@RequestMapping(value = "/findAnchorDeductionRuleById", method = RequestMethod.GET)
	public RetResult findAnchorDeductionRuleById(Long id) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据id查询主播减分规则开始:code={},message={},id={}", code, message, id);
		// 根据id查询主播减分规则
		AnchorDeductionRule anchorDeductionRule = null;
		try {
			if(StringUtils.isNotBlank(id.toString())){
				anchorDeductionRule = anchorDeductionRuleService.findAnchorDeductionRule(id);
			}else{
				code = CommConstant.GWSCOD0003;
			    message = CommConstant.GWSMSG0003;
				GwsLogger.error("id为空:code={},message={},", code, message);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("根据id查询主播减分规则异常:code={},message={},id={},e={}", code, message, id, e);
		}
		GwsLogger.info("根据id查询主播减分规则结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, anchorDeductionRule);
	}

	/**
	 * 【查询所有主播减分规则】
	 * 
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午11:38:40
	 */
	@ApiOperation(value = "查询所有主播减分规则", notes = "查询所有主播减分规则")
	@RequestMapping(value = "/findAllAnchorDeductionRule", method = RequestMethod.GET)
	public RetResult findAllAnchorDeductionRule() {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("查询所有主播减分规则开始");
		// 查询所有主播减分规则
		List<AnchorDeductionRule> list = null;
		try {
			list = anchorDeductionRuleService.findAnchorDeductionRule();
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询所有主播减分规则异常:code={},message={},e={}", code, message, e);
		}
		GwsLogger.info("查询所有主播减分规则结束");
		return RetResult.setRetDate(code, message, list);
	}

}