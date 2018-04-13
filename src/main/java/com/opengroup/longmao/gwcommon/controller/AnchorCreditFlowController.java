package com.opengroup.longmao.gwcommon.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.annotation.JsonParam;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AnchorCreditFlow;
import com.opengroup.longmao.gwcommon.service.AnchorCreditFlowService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【主播信誉流水】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月23日 上午09:08:34
 */
@RestController
@EnableSwagger2
@Api(value = "主播信誉流水", tags = "anchorCreditFlow")
@RequestMapping(value = { "/anchorCreditFlow" })
public class AnchorCreditFlowController {

	@Autowired
	private AnchorCreditFlowService anchorCreditFlowService;
	
	/**
	 * 【保存主播信誉流水】
	 * 
	 * @param anchorCreditFlow
	 * @param request
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */
//	@ApiOperation(value = "保存主播信誉流水", notes = "保存主播信誉流水")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "anchorCreditFlow", value = "详细实体anchorCreditFlow", required = true, dataType = "AnchorCreditFlow"),
//			@ApiImplicitParam(name = "request", value = "HttpServletRequest", required = true, dataType = "HttpServletRequest") })
//	@RequestMapping(value = "/saveAnchorCreditFlow", method = RequestMethod.POST)
//	public RetResult saveAnchorCreditFlow(@JsonParam AnchorCreditFlow anchorCreditFlow, HttpServletRequest request) {
//		String code = CommConstant.GWSCOD0000;
//		String message = CommConstant.GWSMSG0000;
//		GwsLogger.info("主播信誉流水保存开始:code={},message={},anchorCreditFlow", code, message,
//				ToStringBuilder.reflectionToString(anchorCreditFlow));
//		try {
//			if (anchorCreditFlow != null) {
//				anchorCreditFlow = anchorCreditFlowService.saveAnchorCreditFlow(anchorCreditFlow);
//				GwsLogger.info("主播信誉流水保存成功:code={},message={},", code, message);
//			} else {
//				code = CommConstant.GWSCOD0001;
//				message = CommConstant.GWSMSG0001;
//				GwsLogger.info("主播信誉流水保存失败:code={},message={},anchorCreditFlow={}", code, message,
//						ToStringBuilder.reflectionToString(anchorCreditFlow));
//			}
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("主播信誉流水保存异常:anchorCreditFlow={},e={}", ToStringBuilder.reflectionToString(anchorCreditFlow),
//					e);
//		}
//		GwsLogger.info("主播信誉流水保存结束:code={},message={}", code, message);
//		return RetResult.setRetDate(code, message, anchorCreditFlow);
//	}

	/**
	 * 【根据id查询主播信誉流水】
	 * 
	 * @param id
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */
	@ApiOperation(value = "根据id查询主播信誉流水", notes = "根据id查询主播信誉流水")
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Long")
	@RequestMapping(value = "/findAnchorCreditFlowById", method = RequestMethod.GET)
	public RetResult findAnchorCreditFlowById(Long id) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据id查询主播信誉流水开始:code={},message={},id={}", code, message, id);
		// 根据id查询主播信誉流水
		AnchorCreditFlow anchorCreditFlow = null;
		try {
			if(StringUtils.isNotBlank(id.toString())){
				anchorCreditFlow = anchorCreditFlowService.findAnchorCreditFlow(id);
			}else{
				code = CommConstant.GWSCOD0003;
			    message = CommConstant.GWSMSG0003;
				GwsLogger.error("id为空:code={},message={},", code, message);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("根据id查询主播信誉流水异常:code={},message={},id={},e={}", code, message, id, e);
		}
		GwsLogger.info("根据id查询主播信誉流水结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, anchorCreditFlow);
	}

	/**
	 * 【根据anchorId查询主播信誉流水，默认降序排列】
	 * 
	 * @param anchorId
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */
	@ApiOperation(value = "根据主播id查询主播信誉流水", notes = "根据主播id查询主播信誉流水")
	@ApiImplicitParam(name = "anchorId", value = "主播id", required = true, dataType = "Long")
	@RequestMapping(value = "/findAnchorCreditFlowByAnchorId", method = RequestMethod.GET)
	public RetResult findAnchorCreditFlowByAnchorId(Long anchorId, Integer pageNo, Integer pageSize) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据anchorId查询主播信誉流水开始:pageNo={},pageSize={}", pageNo- 1, pageSize);
		// 根据id查询主播信誉流水
		Page<AnchorCreditFlow> page = null;
		try {
			if(StringUtils.isNotBlank(anchorId.toString())){
				page = anchorCreditFlowService.findAnchorCreditFlow(anchorId, pageNo- 1, pageSize, ConfigConstant.SORT_FIELD_CTIME);
			}else{
				code = CommConstant.GWSCOD0003;
			    message = CommConstant.GWSMSG0003;
				GwsLogger.error("anchorId为空:code={},message={},", code, message);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("根据anchorId查询主播信誉流水异常:code={},message={},anchorId={},e={}", code, message, anchorId, e);
		}
		GwsLogger.info("根据anchorId查询主播信誉流水结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, page);
	}

	/**
	 * 【查询所有主播信誉流水，默认降序排列】
	 * 
	 * @param anchorCreditFlow
	 * @param pageNo
	 * @param pageSize
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月23日 上午09:08:34
	 */
	@ApiOperation(value = "查询所有主播信誉流水", notes = "查询所有主播信誉流水")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "anchorCreditFlow", value = "详细实体anchorCreditFlow", required = true, dataType = "AnchorCreditFlow"),
			@ApiImplicitParam(name = "pageNo", value = "第几页", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "String") })
	@RequestMapping(value = "/findAllAnchorCreditFlow", method = RequestMethod.GET)
	public RetResult findAllAnchorCreditFlow(@JsonParam AnchorCreditFlow anchorCreditFlow, Integer pageNo,
			Integer pageSize) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("查询所有主播信誉流水开始:pageNo={},pageSize={}", pageNo- 1, pageSize);
		// 查询所有主播信誉流水
		Page<AnchorCreditFlow> page = null;
		try {
			page = anchorCreditFlowService.findAnchorCreditFlow(anchorCreditFlow, pageNo - 1, pageSize,
					ConfigConstant.SORT_FIELD_CTIME);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error(
					"查询所有主播信誉流水异常:code={},message={},anchorCreditFlow={},pageNo={},pageSize={},softField={},e={}", code,
					message, ToStringBuilder.reflectionToString(anchorCreditFlow), pageNo - 1, pageSize, e);
		}
		GwsLogger.info("查询所有主播信誉流水结束:pageNo={},pageSize={},softField={}", pageNo - 1, pageSize);
		return RetResult.setRetDate(code, message, page);
	}
	
}
