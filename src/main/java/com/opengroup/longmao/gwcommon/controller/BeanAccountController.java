package com.opengroup.longmao.gwcommon.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.opengroup.longmao.gwcommon.configuration.annotation.JsonParam;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.entity.po.BeanChangeRecord;
import com.opengroup.longmao.gwcommon.service.BeanAccountService;
import com.opengroup.longmao.gwcommon.service.BeanChangeRecordService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【龙猫豆账户管理】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:51:27
 */
@EnableSwagger2
@Api(value = "龙猫豆账户管理", tags = "beanAccount")
@RestController
@RequestMapping(value = { "/beanAccount" })
public class BeanAccountController {
	
	@Autowired
	private BeanChangeRecordService beanChangeRecordService;

	@Autowired
	private BeanAccountService beanAccountService;
	
	/**
	 * 【根据userId查询龙猫豆账户】
	 * @param userId
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 下午15:51:27
	 */
	@ApiOperation(value = "根据userId查询龙猫豆账户", notes = "根据userId查询龙猫豆账户")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String")
	@RequestMapping(value = "/findBean", method = RequestMethod.GET)
	public RetResult findBean(@RequestParam("userId") String userId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询用户龙猫豆By用户Id,开始:userId={},startTime={}", userId, startTime);
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询用户龙猫豆By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		//根据userId查询龙猫豆
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("lmBeanNum", 0.00);
		try {
			BeanAccount bean = beanAccountService.getBeanByUserId(userId);
			if (null != bean) {
				map.put("lmBeanNum", bean.getLmBeanNum());
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("查询用户龙猫豆By用户Id,结束:code={},message={},userId={},beanMap={},endTime={}毫秒", code, message, userId,
					map, (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("查询用户龙猫豆By用户Id,异常:code={},message={},userId={},beanMap={},endTime={}毫秒,e={}", code, message,
					userId, map, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, map);
	}
	
	/**
	 * 【根据userId查询龙猫豆账户】
	 * @param userId
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 下午15:51:27
	 */
	@ApiOperation(value = "根据userId查询龙猫豆账户", notes = "根据userId查询龙猫豆账户")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String")
	@RequestMapping(value = "/findBeanAccount", method = RequestMethod.POST)
	public RetResult findBeanAccount(String userId) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("查询龙猫豆账户By用户Id,开始:userId={},startTime={}", userId, startTime);
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("查询龙猫豆账户By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		// 根据id查询账户管理
		BeanAccount beanAccount = null;
		try {
			beanAccount = beanAccountService.getBeanByUserId(userId);
			if (null != beanAccount) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("查询龙猫豆账户By用户Id,结束:code={},message={},userId={},BeanAccount={},endTime={}毫秒", code, message,
					userId, beanAccount, (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("查询龙猫豆账户By用户Id,异常:code={},message={},userId={},BeanAccount={},endTime={}毫秒,e={}", code,
					message, userId, beanAccount, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, beanAccount);
	}

	/**
	 * 
	 * 【增减用户龙猫豆】
	 * 
	 * @author Hermit 2017年4月12日
	 * @param userId
	 * @param changeNum
	 * @param changeType
	 * @return
	 */
	@ApiOperation(value = "增减用户龙猫豆", notes = "根据用户id增减用户龙猫豆")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "账户id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "changeNum", value = "龙猫豆数量", required = true, dataType = "Double"),
			@ApiImplicitParam(name = "changeType", value = "增/减", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "bizType", value = "用途", required = true, dataType = "Integer")})
	@RequestMapping(value = "/updateBeanAccount", method = RequestMethod.POST)
	public RetResult updateBeanAccount(String userId, Double changeNum, Integer changeType,Integer bizType) {
		Long startTime = System.currentTimeMillis();
		GwsLogger.info("增减用户龙猫豆By用户Id,开始:userId={},changeNum={},changeType={},bizType={},startTime={}", userId, changeNum,
				changeType, bizType, startTime);
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("增减用户龙猫豆By用户Id,参数为空:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (null == changeNum || 0 > changeNum) {
			GwsLogger.error("增减用户龙猫豆By用户Id,参数为空:changeNum={}", changeNum);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (null == changeType || 0 > changeType) {
			GwsLogger.error("增减用户龙猫豆By用户Id,参数为空:changeType={}", changeType);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (null == bizType || 0 > bizType) {
			GwsLogger.error("增减用户龙猫豆By用户Id,参数为空:bizType={}", bizType);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		String code = CommConstant.GWSCOD0001;
		String message = CommConstant.GWSMSG0001;
		// 根据id查询账户管理
		Boolean isflag = false;
		try {
			isflag = beanAccountService.updateBeanAccount(userId, changeNum, changeType, bizType);
			if (isflag) {
				code = CommConstant.GWSCOD0000;
				message = CommConstant.GWSMSG0000;
			}
			GwsLogger.info("增减用户龙猫豆By用户Id,结束:code={},message={},userId={},isflag={},endTime={}毫秒", code, message,
					userId, isflag, (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			GwsLogger.error("增减用户龙猫豆By用户Id,异常:code={},message={},userId={},isflag={},endTime={}毫秒,e={}", code, message,
					userId, isflag, (System.currentTimeMillis() - startTime), e);
		}
		return RetResult.setRetDate(code, message, isflag);
	}

	/**
	 * 
	 * 【批量增减用户龙猫豆】
	 * 
	 * @author Hermit 2017年4月12日
	 * @param beanAccountList
	 * @return
	 */
	@ApiOperation(value = "批量增减用户龙猫豆", notes = "批量增减用户龙猫豆")
	@ApiImplicitParam(name = "beanAccountListString", value = "用户龙猫豆批量增减集合", required = true, dataType = "String")
	@RequestMapping(value = "/updateBeanAccountBatch", method = RequestMethod.POST)
	public RetResult updateBeanAccountBatch(String beanAccountListString) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("批量增减用户龙猫豆开始:code={},message={},beanAccountListString={}", code, message, beanAccountListString);
		//批量增减用户龙猫豆
		Boolean isflag = null;
		List<Map<String, Object>> mapList=new ArrayList<Map<String, Object>>();
		try {
			mapList= JSON.parseObject(beanAccountListString, new TypeReference<List<Map<String,Object>>>(){});
			if(mapList.isEmpty()){
				GwsLogger.info("批量增减用户龙猫豆,不存在数据");
				isflag = true;
			}else{
				isflag = beanAccountService.updateBeanAccountBatch(mapList);
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("批量增减用户龙猫豆异常:code={},message={},List<BeanAccount>.size={}", code, message, mapList.size());
		}
		GwsLogger.info("批量增减用户龙猫豆结束:code={},message={},isflag={}", code, message,isflag);
		return RetResult.setRetDate(code, message, isflag);
	}
	
	/**
	 * 【保存龙猫豆账户流水】
	 * 
	 * @param beanAccount
	 * @param request
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2017年03月15日 下午15:51:27
	 */
	@ApiOperation(value = "保存龙猫豆账户流水", notes = "保存龙猫豆账户流水")
	@ApiImplicitParam(name = "beanChangeRecord", value = "龙猫豆账户流水详细实体beanChangeRecord", required = true, dataType = "BeanChangeRecord")
	@RequestMapping(value = "/saveBeanChangeRecord", method = RequestMethod.POST)
	public RetResult saveBeanChangeRecord(@JsonParam BeanChangeRecord beanChangeRecord) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("保存龙猫豆账户流水开始:code={},message={},beanChangeRecord={}", code, message,ToStringBuilder.reflectionToString(beanChangeRecord));
		try {
			if (beanChangeRecord != null) {
				beanChangeRecord = beanChangeRecordService.saveBeanChangeRecord(beanChangeRecord);
				GwsLogger.info("保存龙猫豆账户流水成功:code={},message={},", code, message);
			} else {
				code = CommConstant.GWSCOD0001;
				message = CommConstant.GWSMSG0001;
				GwsLogger.info("保存龙猫豆账户流水失败:code={},message={},beanChangeRecord={}", code, message,ToStringBuilder.reflectionToString(beanChangeRecord));
			}
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("保存龙猫豆账户流水异常:beanChangeRecord={},e={}", ToStringBuilder.reflectionToString(beanChangeRecord), e);
		}
		GwsLogger.info("保存龙猫豆账户流水结束:code={},message={}", code, message);
		return RetResult.setRetDate(code, message, beanChangeRecord);
	}
	
	/**
	 * 
	 * 【批量保存龙猫豆账户流水】
	 * 
	 * @author Hermit 2017年4月12日
	 * @param beanChangeRecordList
	 * @return
	 */
	@ApiOperation(value = "批量保存龙猫豆账户流水", notes = "批量保存龙猫豆账户流水")
	@ApiImplicitParam(name = "beanChangeRecordList", value = "用户龙猫豆账户流水集合", required = true, dataType = "List")
	@RequestMapping(value = "/updateBeanChangeRecordBatch", method = RequestMethod.POST)
	public RetResult updateBeanChangeRecordBatch(@JsonParam List<BeanChangeRecord> beanChangeRecordList) {
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("批量保存龙猫豆账户流水开始:code={},message={},List<BeanChangeRecord>.size={}", code, message, beanChangeRecordList.size());
		//批量保存龙猫豆账户流水
		Boolean isflag = null;
		try {
			isflag = beanChangeRecordService.updateBeanChangeRecordBatch(beanChangeRecordList);
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("批量保存龙猫豆账户流水异常:code={},message={},List<BeanChangeRecord>.size={}", code, message, beanChangeRecordList.size());
		}
		GwsLogger.info("批量保存龙猫豆账户流水结束:code={},message={},isflag={}", code, message,isflag);
		return RetResult.setRetDate(code, message, isflag);
	}
	
	@ApiOperation(value = "龙猫豆兑换龙猫币", notes = "用户龙猫豆兑换龙猫币")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "lmBeanNum", value = "龙猫豆数量", required = true, dataType = "Integer") })
	@RequestMapping(value = "/beanToCoin", method = RequestMethod.POST)
	public RetResult beanToCoin(@RequestParam("userId") String userId, @RequestParam("lmBeanNum") Integer lmBeanNum) {
		GwsLogger.info("龙猫豆兑换龙猫币:userId={}", userId);
		Map<String, Object> beanM = new HashMap<String, Object>();
		return RetResult.setRetDate(CommConstant.GWSCOD0026, "兑换已停止使用,请更换版本。", beanM);
//		String code = CommConstant.GWSCOD0000;
//		String message = CommConstant.GWSMSG0000;
//		GwsLogger.info("龙猫豆兑换龙猫币开始:code={},message={},userId={},lmBeanNum={}", code, message, userId, lmBeanNum);
//		Map<String, Object> beanM = new HashMap<String, Object>();
//		
//		if (StringUtils.isBlank(userId)) {
//			GwsLogger.error("userId不存在:userId={}", userId);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, beanM);
//		}
//		
//		if (1 > lmBeanNum) {
//			GwsLogger.error("龙猫豆数量有误:lmBeanNum={}", lmBeanNum);
//			return RetResult.setRetDate(CommConstant.GWSCOD0003, "龙猫豆数量有误", beanM);
//		}
//		
//		User u = userService.queryUserByUserId(userId);
//		if (null == u) {
//			return RetResult.setRetDate(CommConstant.GWSCOD0010, CommConstant.GWSMSG0010, beanM);
//		} else {
//			SysConfig config = SysConfigService.findSysConfig("bean_" + u.getUserType() + "_");
//			if (null != config && config.getVal().equals(IsOrNotEnum.NO.getType().toString())) {
//				GwsLogger.info("龙猫豆兑换龙猫币功能关闭：userId={}", userId);
//				return RetResult.setRetDate(CommConstant.GWSCOD0023, CommConstant.GWSMSG0023, beanM);
//			}
//		}
//		
//		try {
//			//减少龙猫豆增加龙猫币,增加相应豆/币流水
//			beanM = beanAccountService.beanToCoin(userId, lmBeanNum);
//		} catch (ImplException e) {
//			code = e.getCode();
//			message = e.getMessage();
//			GwsLogger.error("龙猫豆兑换龙猫币异常:code={},message={},userId={},lmBeanNum={},e={}", code, message, userId,
//					lmBeanNum, e);
//		} catch (Exception e) {
//			code = CommConstant.GWSCOD0001;
//			message = CommConstant.GWSMSG0001;
//			GwsLogger.error("龙猫豆兑换龙猫币异常:code={},message={},userId={},lmBeanNum={},e={}", code, message, userId,
//					lmBeanNum, e);
//		}
//		GwsLogger.info("龙猫豆兑换龙猫币结束:code={},message={}", code, message);
//		return RetResult.setRetDate(code, message, beanM);
	}
	
	@ApiOperation(value = "根据人民币增加龙猫豆", notes = "根据人民币增加龙猫豆")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "rmb", value = "人民币", required = true, dataType = "BigDecimal") })
	@RequestMapping(value = "/addBean", method = RequestMethod.POST)
	public RetResult addBean(@RequestParam("userId") String userId, @RequestParam("rmb") BigDecimal rmb) {
		if (StringUtils.isBlank(userId)) {
			GwsLogger.error("userId不存在:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		int r = rmb.compareTo(BigDecimal.ZERO);
		if (r != 1) {
			GwsLogger.error("rmb充值金额无效:rmb={}", rmb);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		GwsLogger.info("根据人民币增加龙猫豆开始:userId={},rmb={}", userId, rmb);
		boolean result = false;
		try {
			result = beanAccountService.recharge(userId, rmb);
			if (result) {
				GwsLogger.info("根据人民币增加龙猫豆操作成功");
			} else {
				GwsLogger.error("根据人民币增加龙猫豆操作失败功:userId={},rmb={}", userId, rmb);
			}
		} catch (Exception e) {
			GwsLogger.error("根据人民币增加龙猫豆操作异常:code={},message={},userId={},e={}", CommConstant.GWSCOD0001,
					CommConstant.GWSMSG0001, userId, e);
		}
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, result);
	}
	
}
