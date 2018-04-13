package com.opengroup.longmao.gwcommon.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.HelpQuestionAnswer;
import com.opengroup.longmao.gwcommon.entity.po.HelpType;
import com.opengroup.longmao.gwcommon.service.HelpCenterService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: HelpCenterManageController 
 * @Description: 【帮助中心】 控制类
 * @author Mr.Zhu
 */
@EnableSwagger2
@Api(value = "帮助中心管理", tags = "help")
@RestController
@RequestMapping(value = { "/help" })
public class HelpCenterController {
	
	@Autowired
	private HelpCenterService helpCenterService;
	
	@ApiOperation(value = "查询帮助中心-问答首页", notes = "查询帮助中心-问答首页")
	@RequestMapping(value = "/findQuestionAnswer", method = RequestMethod.POST)
	public RetResult findQuestionAnswer() {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(HelpCenterController:/help/findQuestionAnswer):查询帮助中心-问答首页,开始:tartTime={}", startTime);

		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		List<Map<String, Object>> helpList = null;
		try {
			helpList = helpCenterService.findQuestionAnswer();
			GwsLogger.info("查询帮助中心-问答首页,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询帮助中心-问答首页,异常:code={},message={},endTime={}毫秒,e={}", code, message,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, helpList);
	}
	
	@ApiOperation(value = "根据typeId查询帮助中心问答-内容", notes = "根据typeId查询帮助中心问答-内容", response = HelpQuestionAnswer.class)
	@ApiImplicitParam(name = "typeId", value = "类型ID", required = true, dataType = "Long")
	@RequestMapping(value = "/findQuestionAnswerByTypeId", method = RequestMethod.POST)
	public RetResult findQuestionAnswerByTypeId(Long typeId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info(
				"User服务(HelpCenterController:/help/findQuestionAnswerByTypeId):查询帮助中心问答-内容ByTypeId,开始:typeId={},startTime={}",
				typeId, startTime);
		if (null == typeId || 1 > typeId) {
			GwsLogger.error("查询帮助中心问答-内容ByTypeId,参数非法:typeId={}", typeId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}

		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		List<HelpQuestionAnswer> helpList = null;
		try {
			Sort sort = new Sort(Direction.ASC, "questionRank");
			helpList = helpCenterService.findHelpQuestionAnswer(sort, typeId, null);
			GwsLogger.info("查询帮助中心问答-内容ByTypeId,结束:code={},message={},typeId={},endTime={}毫秒", code, message, typeId,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询帮助中心问答-内容ByTypeId,异常:code={},message={},typeId={},endTime={}毫秒,e={}", code, message, typeId,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, helpList);
	}
	
	@ApiOperation(value = "根据typeId查询帮助中心问答-类型 ", notes = "根据typeId查询帮助中心问答-类型 ", response = HelpType.class)
	@ApiImplicitParam(name = "typeId", value = "类型ID", required = true, dataType = "Long")
	@RequestMapping(value = "/findHelpType", method = RequestMethod.POST)
	public RetResult findHelpType(Long typeId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("User服务(HelpCenterController:/help/findHelpType):查询帮助中心问答-类型,开始:typeId={},startTime={}", typeId,
				startTime);
		if (null == typeId || 1 > typeId) {
			GwsLogger.error("查询帮助中心问答-类型,参数非法:typeId={}", typeId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}

		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		HelpType helpType = null;
		try {
			helpType = helpCenterService.findHelpType(typeId);
			GwsLogger.info("查询帮助中心问答-类型,结束:code={},message={},typeId={},endTime={}毫秒", code, message, typeId,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询帮助中心问答-类型,异常:code={},message={},typeId={},endTime={}毫秒,e={}", code, message, typeId,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, helpType);
	}
	
	@ApiOperation(value = "分页查询帮助中心问答-类型", notes = "分页查询帮助中心问答-类型", response = HelpType.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNo", value = "第几页", required = false, dataType = "Integer"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "Integer") })
	@RequestMapping(value = "/findHelpTypePage", method = RequestMethod.POST)
	public RetResult findHelpTypePage(Integer pageNo, Integer pageSize) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info(
				"User服务(HelpCenterController:/help/findHelpTypePage):分页查询帮助中心问答-类型,开始:pageNo={},pageSize={},startTime={}",
				pageNo, pageSize, startTime);
		if (null == pageNo || 1 > pageNo || null == pageSize || 1 > pageSize) {
			GwsLogger.error("分页查询帮助中心问答-类型,参数非法:pageNo={},pageSize={}", pageNo, pageSize);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Page<HelpType> HelpType = null;
		try {
			HelpType = helpCenterService.findHelpType(pageNo - 1, pageSize);
			GwsLogger.info("分页查询帮助中心问答-类型,结束:code={},message={},endTime={}毫秒", code, message,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("分页查询帮助中心问答-类型,异常:code={},message={},endTime={}毫秒,e={}", code, message,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, HelpType);
	}
	
	
	@ApiOperation(value = "根据questionId查询帮助中心问答-内容", notes = "根据questionId查询帮助中心问答-内容", response = HelpQuestionAnswer.class)
	@ApiImplicitParam(name = "questionId", value = "问答ID", required = true, dataType = "Long")
	@RequestMapping(value = "/findHelpQuestionAnswer", method = RequestMethod.POST)
	public RetResult findHelpQuestionAnswer(Long questionId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info(
				"User服务(HelpCenterController:/help/findHelpQuestionAnswer):查询帮助中心问答-内容,开始:questionId={},startTime={}",
				questionId, startTime);
		if (null == questionId || 1 > questionId) {
			GwsLogger.error("查询帮助中心问答-内容,参数非法:questionId={}", questionId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}

		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		HelpQuestionAnswer helpQuestionAnswer = null;
		try {
			helpQuestionAnswer = helpCenterService.findHelpQuestionAnswer(questionId);
			GwsLogger.info("查询帮助中心问答-内容,结束:code={},message={},questionId={},endTime={}毫秒", code, message, questionId,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询帮助中心问答-内容,异常:code={},message={},questionId={},endTime={}毫秒,e={}", code, message, questionId,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, helpQuestionAnswer);
	}
	
	@ApiOperation(value = "分页查询帮助中心问答-内容", notes = "分页查询帮助中心问答-内容", response = HelpQuestionAnswer.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isShow", value = "是否首页展示:1是2否", required = false, dataType = "Short"),
			@ApiImplicitParam(name = "pageNo", value = "第几页", required = false, dataType = "Integer"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "Integer") })
	@RequestMapping(value = "/findHelpQuestionAnswerPage", method = RequestMethod.POST)
	public RetResult findHelpQuestionAnswerPage(Short isShow, Integer pageNo, Integer pageSize) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info(
				"User服务(HelpCenterController:/help/findHelpQuestionAnswerPage):分页查询帮助中心问答-内容,开始:isShow={},pageNo={},pageSize={},startTime={}",
				isShow, pageNo, pageSize, startTime);
		if (null == pageNo || 1 > pageNo || null == pageSize || 1 > pageSize) {
			GwsLogger.error("分页查询帮助中心问答-内容,参数非法:pageNo={},pageSize={}", pageNo, pageSize);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		Page<HelpQuestionAnswer> helpQuestionAnswer = null;
		try {
			helpQuestionAnswer = helpCenterService.findHelpQuestionAnswer(isShow, pageNo - 1, pageSize);
			GwsLogger.info("分页查询帮助中心问答-内容,结束:code={},message={},isShow={},endTime={}毫秒", code, message, isShow,
					(System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("分页查询帮助中心问答-内容,异常:code={},message={},isShow={},endTime={}毫秒,e={}", code, message, isShow,
					(System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, helpQuestionAnswer);
	}
	
}
