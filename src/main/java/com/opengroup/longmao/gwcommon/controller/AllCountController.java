package com.opengroup.longmao.gwcommon.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.AllCount;
import com.opengroup.longmao.gwcommon.entity.po.Attentions;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;
import com.opengroup.longmao.gwcommon.entity.po.LiveRoomInfo;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.vo.AllCountVO;
import com.opengroup.longmao.gwcommon.enums.AllCountTypeEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.LivePlayStatusEnum;
import com.opengroup.longmao.gwcommon.service.AllCountService;
import com.opengroup.longmao.gwcommon.service.AttentionsService;
import com.opengroup.longmao.gwcommon.service.IdentityService;
import com.opengroup.longmao.gwcommon.service.LiveRoomInfoService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.service.UserService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.RetResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【全服统计表】 控制类
 *
 * @version 1.0
 * @author Hermit 2017年12月21日 下午17:16:36
 */ 
@RestController
@EnableSwagger2
@Api(value= "全服统计",tags="allCount")
@RequestMapping(value = {"/allCount"})
public class AllCountController {
	
	@Autowired
	private LiveRoomInfoService liveRoomInfoService;
	
	@Autowired
	private AttentionsService attentionsService;

	@Autowired
	private AllCountService allCountService;
	
	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private UserService userService;
	
	@Autowired	
	private IdentityService identityService;
	
	/**
	* 【查询所有全服统计表】
	* @param allCountVO
	* @param pageNo
	* @param pageSize
	* @return RetResult
	* @version 1.0
	* @author Hermit 2017年12月21日 下午17:16:36
	*/ 
	/*@ApiOperation(value = "查询所有全服统计表",notes="查询所有全服统计表",response =AllCount.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name ="allCountVO",value ="详细实体allCount",paramType="query", required = true, dataType ="AllCount"),
		@ApiImplicitParam(name ="pageNo",value ="第几页",paramType="query", required = true, dataType ="Long"),
		@ApiImplicitParam(name ="pageSize",value ="每页条数",paramType="query", required = true, dataType ="Long")
		})
	@RequestMapping(value = "/findAllCount",method =RequestMethod.POST)
	public RetResult findAllCount(@JsonParam AllCount allCount,Integer pageNo,Integer pageSize){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("查询所有全服统计表开始:pageNo={},pageSize={},allCount={}",pageNo,pageSize,JSON.toJSONString(allCount));
		//查询所有全服统计表
		Page<AllCount> page = null;
		try {
			page = allCountService.findAllCount(allCount,pageNo-1,pageSize,"sortNum");
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询所有全服统计表异常:code={},message={},allCount={},pageNo={},pageSize={},e={}",code,message,JSON.toJSONString(allCount),pageNo-1,pageSize,e);
		}
		GwsLogger.info("查询所有全服统计表结束:pageNo={},pageSize={}",pageNo-1,pageSize);
		return RetResult.setRetDate(code, message, page);
	}*/
	
	
	/**
	* 【根据统计类型查询全服统计表】
	* @param allCountVO
	* @param pageNo
	* @param pageSize
	* @return RetResult
	* @version 1.0
	* @author Hermit 2017年12月21日 下午17:16:36
	*/ 
	@ApiOperation(value = "根据统计类型查询全服统计",notes="根据统计类型查询全服统计",response =AllCount.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name ="countType",value ="统计类型",paramType="query", required = true, dataType ="Short"),
		@ApiImplicitParam(name ="userId",value ="用户id",paramType="query", required = true, dataType ="Long"),
		@ApiImplicitParam(name ="pageNo",value ="第几页",paramType="query", required = true, dataType ="Long"),
		@ApiImplicitParam(name ="pageSize",value ="每页条数",paramType="query", required = true, dataType ="Long")
		})
	@RequestMapping(value = "/findAllCountByCountType",method =RequestMethod.POST)
	public RetResult findAllCountByCountType(Short countType,Long userId,Integer pageNo,Integer pageSize){
		String code = CommConstant.GWSCOD0000;
		String message = CommConstant.GWSMSG0000;
		GwsLogger.info("根据统计类型查询全服统计表开始:pageNo={},pageSize={},countType={}",pageNo,pageSize,countType);
		Long startTime = System.currentTimeMillis();
		if(null == countType || 0 > countType){
			code = CommConstant.GWSCOD0003;
			message = CommConstant.GWSMSG0003;
			GwsLogger.error("countType不合法:code={},message={},userId={}", code, message, userId);
			return RetResult.setRetDate(code, message, null);
		}
		if(null == pageNo || 1 > pageNo){
			code = CommConstant.GWSCOD0003;
			message = CommConstant.GWSMSG0003;
			GwsLogger.error("countType不合法:code={},message={},userId={}", code, message, userId);
			return RetResult.setRetDate(code, message, null);
		}
		if(null == pageSize || 0 > pageSize){
			code = CommConstant.GWSCOD0003;
			message = CommConstant.GWSMSG0003;
			GwsLogger.error("countType不合法:code={},message={},userId={}", code, message, userId);
			return RetResult.setRetDate(code, message, null);
		}
		//查询所有全服统计表
		List<AllCountVO> allCountVOs = new ArrayList<>();
		Page<AllCount> page = null;
		List<AllCount> AllCountList = null;
		AllCount allCount = new AllCount();
		allCount.setCountType(countType);
		try {
			//Redis缓存使用,将前100条数据存入缓存中
			if(pageNo * pageSize <= 100){
				GwsLogger.info("从缓存中取出数据开始，countType={},key={}", countType,ConfigConstant.ALL_COUNT_RANKING);
				//AllCountList = cache.getList(CommConstant.ALL_COUNT_RANKING, countType.toString());
				AllCountList = redis.getList(ConfigConstant.ALL_COUNT_RANKING, countType.toString());
				Collections.sort(AllCountList,new Comparator<AllCount>(){
		            public int compare(AllCount arg0, AllCount arg1) {
		                return arg0.getSortNum().compareTo(arg1.getSortNum());
		            }
		        });
				GwsLogger.info("从缓存中取出数据成功，条数={}", AllCountList.size());
				int startnum = ((pageNo-1) * pageSize) >= AllCountList.size() ? AllCountList.size() : ((pageNo-1) * pageSize);
				int endnum = (pageNo * pageSize) >= AllCountList.size() ? AllCountList.size() : (pageNo * pageSize);
				AllCountList = AllCountList.subList(startnum , endnum);
			}
			if(AllCountList == null || AllCountList.size()<1){
				GwsLogger.info("从缓存中取出数据为null，开始向数据库中取数据。。。。");
				page = allCountService.findAllCount(allCount,pageNo-1,pageSize,"sortNum");
				AllCountList = page.getContent();
			}
			if(AllCountList==null || AllCountList.size()<1){
				Long endtime = System.currentTimeMillis();
				GwsLogger.info("查询所有全服统计表结束,但无数据:pageNo={},pageSize={},耗时={}",pageNo-1,pageSize,endtime-startTime);
				return RetResult.setRetDate(code, message, allCountVOs);
			}
			for (AllCount allCount2 : AllCountList) {
				if(allCount2.getCountType().equals(AllCountTypeEnum.COUNT_CALORIE_RECEIVER.getVal())){//明星主播榜
					if (null == userId || 0 > userId) {
						code = CommConstant.GWSCOD0003;
						message = CommConstant.GWSMSG0003;
						GwsLogger.error("用户userId不合法:code={},message={},userId={}", code, message, userId);
						return RetResult.setRetDate(code, message, null);
					}
					
					AllCountVO allCountVO = new AllCountVO();

					Long count_userId = allCount2.getAnchorId();
					User user = userService.queryUserByUserId(count_userId.toString());
					if(user==null){
						allCountVO.setNickName("未知");//用户名
						allCountVO.setGrade(1);//等级
						allCountVO.setFacePhoto("");//头像
					}else{
						allCountVO.setNickName(user.getNickName());//用户名
						allCountVO.setGrade(user.getGrade());//等级
						allCountVO.setFacePhoto(user.getPhoneId());//头像
					}
					
					//add by szy 2018.3.20 增加主播等级字段 beg
					IdentityInfo identity = identityService.findIdentityByUserId(count_userId.toString());
					if (identity == null) {
						allCountVO.setAnchorGrade(1);
					} else {
						allCountVO.setAnchorGrade(identity.getGrade());
					}
					//add by szy 2018.3.20 增加主播等级字段 end
					
					allCountVO.setCountType(allCount2.getCountType());//类型
					allCountVO.setAnchorId(count_userId);//用户id
					allCountVO.setCalorieCount(allCount2.getCalorieCount());//统计卡路里
					
					allCountVO.setSortNum(allCount2.getSortNum());//序号
					//是否关注主播
					Attentions followMe = attentionsService.queryUserByUserIdAndFollowId(String.valueOf(userId), count_userId.toString());
					Short isFollowMe = null == followMe ? IsOrNotEnum.NO.getType() : followMe.getAttentionsState();
					allCountVO.setFollow(isFollowMe);//1关注，2未关注
					//主播是否开播
					LiveRoomInfo liveRoomInfo = liveRoomInfoService.findLiveRoomInfoByuserId(count_userId);
					if(liveRoomInfo != null){
						allCountVO.setLiving(liveRoomInfo.getPlayStatus());//播放状态(0:在播；1:停播)
						allCountVO.setRoomId(liveRoomInfo.getRoomId());//房间ID
						allCountVO.setLiveCover(null == liveRoomInfo ? user.getPhoneId() : liveRoomInfo.getLiveCover());//若主播在直播，其对应的封面
					}else{
						allCountVO.setLiving(LivePlayStatusEnum.UN_PLAY.getType());//播放状态(0:在播；1:停播)
						allCountVO.setRoomId(null);//房间ID
					}
					
					allCountVOs.add(allCountVO);
				}else if(allCount2.getCountType().equals(AllCountTypeEnum.COUNT_ROB_PROFIT_USERID.getVal())){//竞猜收益榜 2
					AllCountVO allCountVO = new AllCountVO();
					
					Long count_userId = allCount2.getSenderId();
					User user = userService.queryUserByUserId(count_userId.toString());
					if(user==null){
						allCountVO.setNickName("未知");//用户名
						allCountVO.setGrade(1);//等级
						allCountVO.setFacePhoto("");//头像
					}else{
						allCountVO.setNickName(user.getNickName());//用户名
						allCountVO.setGrade(user.getGrade());//等级
						allCountVO.setFacePhoto(user.getPhoneId());//头像
					}
					
					allCountVO.setCountType(allCount2.getCountType());//类型
					allCountVO.setSenderId(count_userId);//用户id
					allCountVO.setRobProfitCount(allCount2.getRobProfitCount());//统计竞猜收益
			//		allCountVO.setCalorieCount(allCount2.getCalorieCount());//统计卡路里
					allCountVO.setSortNum(allCount2.getSortNum());//序号
					
					allCountVOs.add(allCountVO);
				}else if(allCount2.getCountType().equals(AllCountTypeEnum.COUNT_CALORIE_SENDER.getVal())){//土豪守护榜
					AllCountVO allCountVO = new AllCountVO();
					
					Long count_userId = allCount2.getSenderId();
					User user = userService.queryUserByUserId(count_userId.toString());
					if(user==null){
						allCountVO.setNickName("未知");//用户名
						allCountVO.setGrade(1);//等级
						allCountVO.setFacePhoto("");//头像
					}else{
						allCountVO.setNickName(user.getNickName());//用户名
						allCountVO.setGrade(user.getGrade());//等级
						allCountVO.setFacePhoto(user.getPhoneId());//头像
					}
					
					allCountVO.setCountType(allCount2.getCountType());//类型
					allCountVO.setSenderId(count_userId);//用户id
					allCountVO.setCalorieCount(allCount2.getCalorieCount());//统计卡路里
					allCountVO.setSortNum(allCount2.getSortNum());//序号
					
					allCountVOs.add(allCountVO);
				}
			}
			
		} catch (Exception e) {
			code = CommConstant.GWSCOD0001;
			message = CommConstant.GWSMSG0001;
			GwsLogger.error("查询所有全服统计表异常:code={},message={},allCount={},pageNo={},pageSize={},e={}",code,message,JSON.toJSONString(allCount),pageNo-1,pageSize,e);
			return RetResult.setRetDate(code, message, null);
		}
		Long endtime = System.currentTimeMillis();
		GwsLogger.info("查询所有全服统计表结束:pageNo={},pageSize={},耗时={}",pageNo-1,pageSize,endtime-startTime);
		return RetResult.setRetDate(code, message, allCountVOs);
	}
	
	


}

