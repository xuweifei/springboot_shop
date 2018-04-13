package com.opengroup.longmao.gwcommon.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorPlusRule;
import com.opengroup.longmao.gwcommon.service.AnchorPlusRuleService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【主播信誉加分规则】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午11:38:58
 */
@RestController
@EnableSwagger2
@Api(value = "主播信誉加分规则", tags = "anchorPlusRule")
@RequestMapping(value = { "/anchorPlusRule" })
public class AnchorPlusRuleController {

	@Autowired
	private AnchorPlusRuleService anchorPlusRuleService;

	/**
	 * 【根据id查询主播加分规则】
	 * 
	 * @param id
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午11:38:58
	 */
	@ApiOperation(value = "根据id查询主播加分规则", notes = "根据id查询主播加分规则")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Long")
	@RequestMapping(value = "/findAnchorPlusRuleById", method = RequestMethod.GET)
	public RetResult findAnchorPlusRuleById(Long id) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据id查询主播加分规则开始:code={},message={},id={}", code, message, id);
		// 根据id查询主播加分规则
		AnchorPlusRule anchorPlusRule = null;
		try {
			if(StringUtils.isNotBlank(id.toString())){
				anchorPlusRule = anchorPlusRuleService.findAnchorPlusRule(id);
			}else{
				code = CommConstant.GWSCOD0003;
			    message = CommConstant.GWSMSG0003;
				GwsLogger.error("id为空:code={},message={},", code, message);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("根据id查询主播加分规则异常:code={},message={},id={},e={}", code, message, id, e);
		}
		GwsLogger.info("根据id查询主播加分规则结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, anchorPlusRule);
	}

	/**
	 * 【查询所有主播加分规则】
	 * 
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午11:38:58
	 */
	@ApiOperation(value = "查询所有主播加分规则", notes = "查询所有主播加分规则")
	@RequestMapping(value = "/findAllAnchorPlusRule", method = RequestMethod.GET)
	public RetResult findAllAnchorPlusRule() {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("查询所有主播加分规则开始");
		// 查询所有主播加分规则
		List<AnchorPlusRule> list = null;
		try {
			list = anchorPlusRuleService.findAnchorPlusRule();
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询所有主播加分规则异常:code={},message={}e={}", code, message, e);
		}
		GwsLogger.info("查询所有主播加分规则结束");
		return RetResult.setRetDate(code, message, list);
	}

}
