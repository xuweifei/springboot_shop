package com.opengroup.longmao.gwcommon.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.Channel;
import com.opengroup.longmao.gwcommon.enums.ChannelTypeEnum;
import com.opengroup.longmao.gwcommon.service.UserChannelService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



/**
 * 【用户渠道信息表】 controller
 * @version 1.0
 * @author xwf 2017年03月15日 上午09:59:49
 */
@EnableSwagger2
@Api(value = "用户渠道信息", tags = "channel")
@RestController
@RequestMapping(value = {"/channel"})
public class UserChannelController {
	
	@Autowired
	private UserChannelService userChannelService;
	
	
	/**
	 * 【用户重新设置渠道号】
	 * @param userId
	 * @param channelId
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2018年01月04日 上午09:59:49
	 */
	@ApiOperation(value = "用户重新设置渠道号", notes = "用户重新设置渠道号")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "channelId", value = "渠道Id", required = true, dataType = "Long")})
	@RequestMapping(value = "/resetChannel", method = RequestMethod.POST)
	public RetResult resetChannel(Long userId, Long channelId) {
		int startSecond = DateUtil.currentSecond();
		GwsLogger.info("用户重新设置渠道号开始:userId={},channelId={}", userId, channelId);
		
		// 参数校验
		if (null == userId || userId < 1) {
			GwsLogger.info("userId非法:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		if (null == channelId || channelId < 1 || channelId - 20001 == 0) {
			GwsLogger.info("channelId非法:channelId={}", channelId);
			return RetResult.setRetDate(CommConstant.GWSCOD0031, CommConstant.GWSMSG0031, null);
		}
		
		Map<String, Object> map = null;
		try {
			// 判断渠道号是否存在
			Channel channel = userChannelService.getChannelById(channelId);
			if (null == channel || channel.getChannelType().equals(ChannelTypeEnum.SELF.getType())) {
				GwsLogger.info("channelId非法,该渠道不存在:channelId={}", channelId);
				return RetResult.setRetDate(CommConstant.GWSCOD0031, CommConstant.GWSMSG0031, null);
			}
			// 修改用户渠道号
			map = userChannelService.resetChannel(userId, channelId);
		}
		catch(ImplException e) {
			GwsLogger.error("用户重新设置渠道号失败:Exception={}", e);
			return RetResult.setRetDate(e.getCode(), e.getMessage(), null);
		}
		catch(Exception e) {
			GwsLogger.error("用户重新设置渠道号异常:Exception={}", e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0001, null);
		}
		
		GwsLogger.info("根据userId查询用户信息结束:耗时={}", DateUtil.currentSecond() - startSecond);
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
	}
	
	
	
	/**
	 * 【查询用户渠道号】注意:这个接口暂时不用,查询账号绑定信息时调service中方法返回渠道号!!!
	 * @param userId
	 * @return RetResult
	 * @version 1.0
	 * @author Hermit 2018年01月04日 上午09:59:49
	 */
	@ApiOperation(value = "根据userId查询用户渠道号", notes = "根据userId查询用户渠道号")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long")
	@RequestMapping(value = "/getChannelId", method = RequestMethod.POST)
	public RetResult getChannelId(Long userId) {
		int startSecond = DateUtil.currentSecond();
		GwsLogger.info("用户查询渠道号开始:userId={}", userId);
		
		// 参数校验
		if (null == userId || userId < 1) {
			GwsLogger.info("userId非法:userId={}", userId);
			return RetResult.setRetDate(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003, null);
		}
		
		Map<String, Object> map = null;
		try {
			map = userChannelService.getChannelId(userId);
		}catch(Exception e) {
			GwsLogger.error("用户查询渠道号异常:Exception={}", e.getMessage());
			return RetResult.setRetDate(CommConstant.GWSCOD0001, CommConstant.GWSMSG0003, null);
		}
		
		GwsLogger.info("用户查询渠道号结束:耗时={}", DateUtil.currentSecond() - startSecond);
		return RetResult.setRetDate(CommConstant.GWSCOD0000, CommConstant.GWSMSG0000, map);
	}
	
	
	
	
}
