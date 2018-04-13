/**
 * @Title: IncomeServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.ApiConfig;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.IdentityInfo;
import com.opengroup.longmao.gwcommon.entity.po.OpalChangeRecord;
import com.opengroup.longmao.gwcommon.entity.po.OrderBuyDetail;
import com.opengroup.longmao.gwcommon.entity.po.OrderInfo;
import com.opengroup.longmao.gwcommon.entity.po.OrderTradeFlow;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.vo.OrderBuyDetailVO;
import com.opengroup.longmao.gwcommon.entity.vo.OrderInfoVO;
import com.opengroup.longmao.gwcommon.entity.vo.OrderTradeFlowVO;
import com.opengroup.longmao.gwcommon.enums.AnchorStatusEnum;
import com.opengroup.longmao.gwcommon.enums.AnchorTypeEnum;
import com.opengroup.longmao.gwcommon.enums.CreditGradeEnum;
import com.opengroup.longmao.gwcommon.enums.DeliveryStatusEnum;
import com.opengroup.longmao.gwcommon.enums.ExchangeRateEnum;
import com.opengroup.longmao.gwcommon.enums.GoodsCategoryEnum;
import com.opengroup.longmao.gwcommon.enums.GoodsChlEnum;
import com.opengroup.longmao.gwcommon.enums.GoodsTypeEnum;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.enums.OrderDiscountWayEnum;
import com.opengroup.longmao.gwcommon.enums.OrderStatusEnum;
import com.opengroup.longmao.gwcommon.enums.PayFlowStatusEnum;
import com.opengroup.longmao.gwcommon.enums.PayPlatformEnum;
import com.opengroup.longmao.gwcommon.enums.PayWayEnum;
import com.opengroup.longmao.gwcommon.enums.RefundStatusEnum;
import com.opengroup.longmao.gwcommon.repository.master.IdentityInfoRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.OpalChangeRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.OrderBuyDetailMaster;
import com.opengroup.longmao.gwcommon.repository.master.OrderInfoRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.OrderTradeFlowRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.queryFilter.OrderInfoQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.IdentityInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.OrderInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.OrderTradeFlowRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.IncomeService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.Md5Util;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;

/**
 * @ClassName: IncomeServiceImpl 
 * @Description: TODO
 * @author Mr.Zhu
 */
@Service
public class IncomeServiceImpl implements IncomeService {
	
	@Autowired
	private OpalChangeRecordRepositoryMaster opalChangeRecordRepositoryMaster;
	
	@Autowired
	private OrderTradeFlowRepositoryMaster orderTradeFlowRepositoryMaster;
	
	@Autowired
	private OrderTradeFlowRepositorySlave orderTradeFlowRepositorySlave;
	
	@Autowired
	private IdentityInfoRepositoryMaster identityInfoRepositoryMaster;
	
	@Autowired
	private IdentityInfoRepositorySlave identityInfoRepositorySlave;
	
	@Autowired
	private OrderInfoRepositoryMaster orderInfoRepositoryMaster;
	
	@Autowired
	private OrderInfoRepositorySlave orderInfoRepositorySlave;
	
	@Autowired
	private OrderBuyDetailMaster orderBuyDetailMaster;
	
	@Autowired
	private UserRepositoryMaster userRepositoryMaster;
	
	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private RedisReadWriteService redis;
	
	@Autowired
	private ApiConfig api;
	
	volatile private ReentrantLock r = new ReentrantLock(true);
	
	/**
	  * @Title: extractCash 
	  * @Description: 主播提现卡路里 
	  * @param map
	  * @return boolean
	  */
	@Override
	public boolean extractCash(Map<String, Object> param) {
		r.lock();
		try {
			if (!checkSign(param)) {
				throw new ImplException(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003);
			}
			
			String userId = param.get("userId").toString();
			String balance = param.get("balance").toString();
			
			if (0 <= new BigDecimal("0.00").compareTo(new BigDecimal(balance))) {
				throw new ImplException(CommConstant.GWSCOD0003, "提现余额不可小于等于0元");
			}
			
			IdentityInfo i = queryIdentityByUserId(userId);
			if (null != i) {
				String ratio = i.getRatio();
				BigDecimal payment = new BigDecimal(balance).divide(new BigDecimal(ratio), 0, BigDecimal.ROUND_UP);
				
				User user = queryUserByUserId(userId);
				BigDecimal userCalorie = new BigDecimal(user.getCalorie());
				BigDecimal deductCalorie = payment.divide(new BigDecimal(ExchangeRateEnum.CALORIE_RATE_RMB.getVal()), 0,
						BigDecimal.ROUND_UP);
				if (0 > userCalorie.compareTo(deductCalorie)) {
					throw new ImplException(CommConstant.GWSCOD0003, "可兑换卡路里余额不足!");
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("balance", balance);
				map.put("payment", payment);
				map.put("ratio", ratio);
				map.put("realName", i.getRealName());
				map.put("alipayId", i.getAlipayId());
				
				OrderInfo info = saveOrderInfo(map);
				
				if (null == info) {
					throw new ImplException(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003);
				}
				
				OrderBuyDetailVO detail = new OrderBuyDetailVO(info.getOrderId(),
						GoodsTypeEnum.CALORIE_GTYPE_RECHARGE.getType(), GoodsTypeEnum.CALORIE_GTYPE_RECHARGE.getDesc(),
						info.getOrderPrice());
				saveOrderBuyDetail(detail);// 创建订单详情表

				OrderTradeFlowVO flow = new OrderTradeFlowVO(info.getOrderId(),
						PayFlowStatusEnum.UN_PAY.getType(), info.getPayPrice(),
						String.valueOf(info.getOrderId()), new Date(), IsDeleteEnum.DELETE.getVal());
				saveOrderTradeFlow(flow);//创建交易流水表
				
				User u = updateCalorie(userId, payment, payment.subtract(new BigDecimal(balance)));
				if (null != u) {
					OrderInfoVO orderVO = new OrderInfoVO(info.getOrderId(), OrderStatusEnum.IS_PAY.getType(),
							DeliveryStatusEnum.UN_DELIVERY.getType(), u.getGmtModified(), String.valueOf(info.getOrderId()),
							"", IsDeleteEnum.UN_DELETE.getVal());
					updateOrderState(info, orderVO);

					OrderTradeFlowVO uFlow = new OrderTradeFlowVO(info.getOrderId(), PayFlowStatusEnum.IS_PAY.getType(),
							info.getPayPrice(), String.valueOf(info.getOrderId()), u.getGmtModified(),
							IsDeleteEnum.UN_DELETE.getVal());
					saveOrderTradeFlow(uFlow);// 创建交易流水表
					return true;
				} else {
					OrderInfoVO orderVO = new OrderInfoVO(info.getOrderId(), OrderStatusEnum.FAIL.getType(),
							DeliveryStatusEnum.UN_DELIVERY.getType(), new Date(), String.valueOf(info.getOrderId()), "",
							IsDeleteEnum.DELETE.getVal());
					updateOrderState(info, orderVO);

					OrderTradeFlowVO uFlow = new OrderTradeFlowVO(info.getOrderId(), PayFlowStatusEnum.FAIL_PAY.getType(),
							info.getPayPrice(), String.valueOf(info.getOrderId()), new Date(), IsDeleteEnum.UN_DELETE.getVal());
					saveOrderTradeFlow(uFlow);// 创建交易流水表
				}
			}
		} catch (ImplException e) {
			throw new ImplException(e.getCode(), e.getMessage());
		} finally {
			r.unlock();
		}
		return false;
	}
	
	/**
	  * @Title: extractCalorie 
	  * @Description: 主播提现卡路里 
	  * @param map
	  * @return boolean
	  */
	@Override
	public boolean extractCalorie(Map<String, Object> param) {
		r.lock();
		try {
			if (!checkSign(param)) {
				throw new ImplException(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003);
			}
			
			String userId = param.get("userId").toString();
			String balance = param.get("balance").toString();
			
			if (0 <= new BigDecimal("0.00").compareTo(new BigDecimal(balance))) {
				throw new ImplException(CommConstant.GWSCOD0003, "卡路里不可小于等于0卡");
			}
			
			IdentityInfo i = queryIdentityByUserId(userId);
			User user = queryUserByUserId(userId);
			
			if (null != i && null != user) {
				if (0 > new BigDecimal(user.getCalorie()).compareTo(new BigDecimal(balance))) {
					throw new ImplException(CommConstant.GWSCOD0003, "可兑换卡路里余额不足!");
				}
				
				String ratio = i.getRatio();
				BigDecimal payment = new BigDecimal(balance)
						.multiply(new BigDecimal(ExchangeRateEnum.CALORIE_RATE_RMB.getVal()))
						.setScale(2, BigDecimal.ROUND_DOWN);

				BigDecimal balanceMoney = payment.multiply(new BigDecimal(ratio)).setScale(2, BigDecimal.ROUND_DOWN);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("balance", balanceMoney);
				map.put("payment", payment);
				map.put("ratio", ratio);
				map.put("realName", i.getRealName());
				map.put("alipayId", i.getAlipayId());
				
				OrderInfo info = saveOrderInfo(map);
				
				if (null == info) {
					throw new ImplException(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003);
				}
				
				OrderBuyDetailVO detail = new OrderBuyDetailVO(info.getOrderId(),
						GoodsTypeEnum.CALORIE_GTYPE_RECHARGE.getType(), GoodsTypeEnum.CALORIE_GTYPE_RECHARGE.getDesc(),
						info.getOrderPrice());
				saveOrderBuyDetail(detail);// 创建订单详情表

				OrderTradeFlowVO flow = new OrderTradeFlowVO(info.getOrderId(),
						PayFlowStatusEnum.UN_PAY.getType(), info.getPayPrice(),
						String.valueOf(info.getOrderId()), new Date(), IsDeleteEnum.DELETE.getVal());
				saveOrderTradeFlow(flow);//创建交易流水表
				
				User u = updateCalorie(userId, payment, payment.subtract(balanceMoney));
				if (null != u) {
					OrderInfoVO orderVO = new OrderInfoVO(info.getOrderId(), OrderStatusEnum.IS_PAY.getType(),
							DeliveryStatusEnum.UN_DELIVERY.getType(), u.getGmtModified(), String.valueOf(info.getOrderId()),
							"", IsDeleteEnum.UN_DELETE.getVal());
					updateOrderState(info, orderVO);

					OrderTradeFlowVO uFlow = new OrderTradeFlowVO(info.getOrderId(), PayFlowStatusEnum.IS_PAY.getType(),
							info.getPayPrice(), String.valueOf(info.getOrderId()), u.getGmtModified(),
							IsDeleteEnum.UN_DELETE.getVal());
					saveOrderTradeFlow(uFlow);// 创建交易流水表
					return true;
				} else {
					OrderInfoVO orderVO = new OrderInfoVO(info.getOrderId(), OrderStatusEnum.FAIL.getType(),
							DeliveryStatusEnum.UN_DELIVERY.getType(), new Date(), String.valueOf(info.getOrderId()), "",
							IsDeleteEnum.DELETE.getVal());
					updateOrderState(info, orderVO);

					OrderTradeFlowVO uFlow = new OrderTradeFlowVO(info.getOrderId(), PayFlowStatusEnum.FAIL_PAY.getType(),
							info.getPayPrice(), String.valueOf(info.getOrderId()), new Date(), IsDeleteEnum.UN_DELETE.getVal());
					saveOrderTradeFlow(uFlow);// 创建交易流水表
				}
			}
		} catch (ImplException e) {
			throw new ImplException(e.getCode(), e.getMessage());
		} finally {
			r.unlock();
		}
		return false;
	}
	
	/**
	 * @Title: saveOrderInfo 
	 * @Description: 保存订单信息 
	 * @param map
	 * @return OrderInfo
	 */
	private OrderInfo saveOrderInfo(Map<String, Object> map) {
		try {
			BigDecimal balance = new BigDecimal(map.get("balance").toString());
			BigDecimal payment = new BigDecimal(map.get("payment").toString());
			String ratio = map.get("ratio").toString();
			String realName = map.get("realName").toString();
			String alipayId = map.get("alipayId").toString();
			String param = "兑换比率[" + ratio + "];真实姓名[" + realName + "];支付宝账号[" + alipayId + "]";
			Long orderId = idGlobalGenerator.getSeqId(OrderInfo.class);
			
			OrderInfo info = new OrderInfo();
			info.setOrderId(orderId);
			info.setBuyerUid(Long.parseLong(map.get("userId").toString()));// 用户ID
			info.setSellerUid(ConfigConstant.TOTORO_SELLER_ID);//卖家ID
			info.setOrderPrice(balance);//卡路里兑换金额
			info.setPayPrice(payment);//用户支付金额,按比例换算(即用户兑换金额加上平台分成金额)
			info.setIntegral(0L);//赠予用户的积分
			
			info.setPayPlatform(PayPlatformEnum.PAY_PLATFORM_TOTORO.getPlatformId());//平台 - 龙猫平台
			info.setPayWay(PayWayEnum.PAY_WAY_CALORIE.getPayWayId());//支付方式
			info.setChlId(GoodsChlEnum.GOODS_CHL_TOTORO_CALORIE.getGoodChlId());//来源
			
			info.setDiscountWay(OrderDiscountWayEnum.UN.getType());
			info.setOrderStatus(OrderStatusEnum.UN_PAY.getType());// 订单状态
			info.setDeliveryStatus(DeliveryStatusEnum.UN_DELIVERY.getType());// 提现状态
			info.setCreateDate(DateUtil.getCurrentDateByFormat(DateUtil.DATA_PATTON_YYYYMMDD));
			info.setIsDummy((short) 0);
			info.setGoodsCategoryId(GoodsCategoryEnum.GCTGY_RECHARGE_CALORIE.getNumber());
			info.setGoodsTypeId(GoodsTypeEnum.CALORIE_GTYPE_RECHARGE.getType());
			info.setRemark(realName + ":" + alipayId);
			info.setOrderEndTime(30);
			info.setCtime(DateUtil.currentSecond());
			info.setUtime(DateUtil.currentSecond());
			info.setRefundStatus(RefundStatusEnum.NO_REFUND.getType());
			info.setParam(param);
			info.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			
			return orderInfoRepositoryMaster.save(info);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @Title: saveOrderTradeFlow
	 * @Description: 创建交易订单流水表
	 * @param map
	 * @return OrderTradeFlow
	 */
	public OrderTradeFlow saveOrderTradeFlow(OrderTradeFlowVO orderVO) {
		if (null == orderVO) {
			throw new ImplException(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003);
		}
		Long flowId = idGlobalGenerator.getSeqId(OrderTradeFlow.class);
		
		OrderTradeFlow orderTradeFlow = new OrderTradeFlow();
		orderTradeFlow.setFlowId(flowId);
		orderTradeFlow.setOrderId(orderVO.getOrderId());
		orderTradeFlow.setPayStatus(orderVO.getPayStatus());
		orderTradeFlow.setPayPrice(orderVO.getPayPrice());
		orderTradeFlow.setPayFlowNo(orderVO.getPayFlowNo());// 支付流水号
		orderTradeFlow.setCreateDate(DateUtil.getFormatDate(orderVO.getTime(), DateUtil.DATA_PATTON_YYYYMMDD));
		String timestamp = orderVO.getTime().getTime() / 1000 + "";
		orderTradeFlow.setCtime(Integer.valueOf(timestamp));
		orderTradeFlow.setUtime(Integer.valueOf(timestamp));
		orderTradeFlow.setIsDelete(orderVO.getIsDelete());
		return orderTradeFlowRepositoryMaster.save(orderTradeFlow);
	}
	
	/**
	 * @Title: saveOrderBuyDetail 
	 * @Description: 创建订单明细表 
	 * @param orderVO
	 * @return OrderBuyDetail
	 */
	public OrderBuyDetail saveOrderBuyDetail(OrderBuyDetailVO orderVO) {
		if (null == orderVO) {
			throw new ImplException(CommConstant.GWSCOD0003, CommConstant.GWSMSG0003);
		}
		Long detailId = idGlobalGenerator.getSeqId(OrderBuyDetail.class);
		
		OrderBuyDetail orderDetail = new OrderBuyDetail();
		orderDetail.setBuyDetailId(detailId);
		orderDetail.setCtime(DateUtil.currentSecond());
		orderDetail.setUtime(DateUtil.currentSecond());
		orderDetail.setGoodsDiscount(100);
		orderDetail.setGoodsName(orderVO.getGoodsName());
		orderDetail.setGoodsId(orderVO.getGoodsId());
		orderDetail.setGoodsNum(1);
		orderDetail.setGoodsPrice(orderVO.getGoodsPrice());
		orderDetail.setOrderId(orderVO.getOrderId());
		orderDetail.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		return orderBuyDetailMaster.save(orderDetail);
	}
	
	/**
	 * @Title: updateOrderState 
	 * @Description: 更新交易订单状态  
	 * @param orderInfo
	 * @param orderVO
	 * @return OrderInfo
	 */
	private OrderInfo updateOrderState(OrderInfo orderInfo, OrderInfoVO orderVO) {
		if (orderInfo == null) {
			orderInfo = getOrderInfoByOrderId(orderVO.getOrderId() + "");
		}
		orderInfo.setOrderStatus(orderVO.getOrderStatus());// 订单状态
		orderInfo.setDeliveryStatus(orderVO.getDeliveryStatus());// 发货状态
		orderInfo.setUtime(DateUtil.currentSecond());
		orderInfo.setIsDelete(orderVO.getIsDelete());
		if (StringUtils.isNotEmpty(orderVO.getRemark())) {
			orderInfo.setRemark(orderVO.getRemark());
		}
		if (orderVO.getPayTime() != null) {
			String timestamp = orderVO.getPayTime().getTime() / 1000 + "";
			orderInfo.setPayTime(Integer.valueOf(timestamp));
		}
		if (StringUtils.isNotEmpty(orderVO.getPayFlowNo())) {
			orderInfo.setPayFlowNo(orderVO.getPayFlowNo());
		}
		return orderInfoRepositoryMaster.save(orderInfo);
	}
	
	/**
	 * @Title: updateOrderTradeFlow 
	 * @Description: 更新交易流水 
	 * @param orderFlow
	 * @param orderVO
	 * @return OrderTradeFlow
	 */
	public OrderTradeFlow updateOrderTradeFlow(OrderTradeFlow orderFlow, OrderTradeFlowVO orderVO) {
		if (orderFlow == null) {
			orderFlow = getOrderTradeFlowByOrderId(orderVO.getFlowId() + "");
		}
		if (orderVO.getTime() != null) {
			String timestamp = orderVO.getTime().getTime() / 1000 + "";
			orderFlow.setUtime(Integer.valueOf(timestamp));
		}
		if (StringUtils.isNotEmpty(orderVO.getPayFlowNo())) {
			orderFlow.setPayFlowNo(orderVO.getPayFlowNo());
		}
		return orderTradeFlowRepositoryMaster.save(orderFlow);
	}
	
	/**
	 * @Title: updateCalorie 
	 * @Description: 根据userId扣除卡路里 
	 * @param userId
	 * @param balance
	 * @return User
	 */
	public User updateCalorie(String userId, BigDecimal balance, BigDecimal revenue) {
		User u = queryUserByUserId(userId);
		if (null != u) {
			BigDecimal calorie = new BigDecimal(u.getCalorie());
			BigDecimal deductCalorie = balance.divide(new BigDecimal(ExchangeRateEnum.CALORIE_RATE_RMB.getVal()), 0,
					BigDecimal.ROUND_UP);
			if (0 <= calorie.compareTo(deductCalorie)) {
				//扣除个人账户卡路里
				BigDecimal deduct = calorie.subtract(deductCalorie);
				u.setCalorie(deduct.longValue());
				u.setGmtModified(new Date());
				u.setGmtModifiedUser(userId);
				User user = userRepositoryMaster.save(u);
				if (null != user) {
					//公司账户增加手续费
					User uA = queryUserByUserId("888888");
					BigDecimal revenueCalorie = revenue.divide(new BigDecimal(ExchangeRateEnum.CALORIE_RATE_RMB.getVal()), 0,
							BigDecimal.ROUND_UP);
					
					uA.setCalorie(new BigDecimal(uA.getCalorie()).add(revenueCalorie).longValue());
					uA.setGmtModified(new Date());
					uA.setGmtModifiedUser(userId);
					userRepositoryMaster.save(uA);
					return user;
				}
			}
		}
		return null;
	}

	/**
	  * @Title: editIdentity 
	  * @Description: 新增、更新认证
	  * @param map
	  * @return boolean
	  */
	@Override
	public boolean editIdentity(Map<String, Object> map) {
		IdentityInfo flag = null;
		
		if (map.containsKey("userId")) {
			String userId = map.get("userId").toString();
			String account = map.get("account").toString();
			String realName = map.get("realName").toString();
			String captcha = map.get("captcha").toString();
			//String captcha_ = cache.get(CommConstant.MOBILE_SMSC_CAPTCHA, userId);
			String captcha_ = redis.get(ConfigConstant.MOBILE_SMSC_CAPTCHA, userId);
			
			if (null == captcha_) {
				throw new ImplException(CommConstant.GWSCOD0006, CommConstant.GWSMSG0006);
			}
			
			if (captcha_.equals(captcha)) {
				IdentityInfo info = queryIdentityByUserId(userId);
				if (null != info) {
					info.setUserId(userId);
					info.setAlipayId(account);
					info.setRealName(realName);
					info.setGmtModified(DateUtil.currentSecond());
					info.setGmtModifiedUser(userId);
					flag = identityInfoRepositoryMaster.save(info);
				} else {
					Long id = idGlobalGenerator.getSeqId(IdentityInfo.class);
					
					IdentityInfo identity = new IdentityInfo();
					identity.setId(id);
					identity.setUserId(userId);
					identity.setAnchorType(AnchorTypeEnum.PEOPLE_ANCHOR.getType());
					identity.setAlipayId(account);
					identity.setRealName(realName);
					identity.setStatus(AnchorStatusEnum.IDENTITY_PASS.getType());
					identity.setRatio(CreditGradeEnum.DEFAULT_GRADE.getRatio());
					identity.setIsLive(AnchorStatusEnum.SHUT_LIVE.getType());
					identity.setCreditGrade(CreditGradeEnum.DEFAULT_GRADE.getVal());
					identity.setCreditGradeExplain(CreditGradeEnum.DEFAULT_GRADE.getExplain());
					identity.setGmtCreate(DateUtil.currentSecond());
					identity.setIsGuess(IsOrNotEnum.YES.getType());
					identity.setGmtCreateUser(userId);
					flag = identityInfoRepositoryMaster.save(identity);
				}
				if (null != flag) {
					//cache.delete(CommConstant.MOBILE_SMSC_CAPTCHA, userId);
					redis.delete(ConfigConstant.MOBILE_SMSC_CAPTCHA, userId);
				}
			} else {
				throw new ImplException(CommConstant.GWSCOD0005, CommConstant.GWSMSG0005);
			}
		}
		return null == flag ? false : true;
	}
	
	/**
	 * @Title: queryIdentityInfoVOByUserId
	 * @Description: 根据userId查询主播收益
	 * @param userId
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryExtractCashByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		User u = queryUserByUserId(userId);
		IdentityInfo i = queryIdentityByUserId(userId);
		
		if (null != u) {
			BigDecimal calorie = new BigDecimal(u.getCalorie());
			BigDecimal balance = calorie.multiply(new BigDecimal(ExchangeRateEnum.CALORIE_RATE_RMB.getVal()));

			String ratio = CreditGradeEnum.DEFAULT_GRADE.getRatio();
			String account = null;
			String realName = null;
			Short isLive = 0;//禁播
			
			if (null != i) {
				ratio = i.getRatio();
				account = i.getAlipayId();
				realName = i.getRealName();
				isLive = i.getIsLive();
			}
			
			BigDecimal mayBalance = balance.multiply(new BigDecimal(ratio));
			BigDecimal deduct = balance.subtract(mayBalance);

			map.put("userId", u.getUserId());
			map.put("isOpen", isLive);
			map.put("account", account);
			map.put("realName", realName);
			map.put("calorie", calorie.setScale(2, BigDecimal.ROUND_DOWN) + "");
			map.put("balance", balance.setScale(2, BigDecimal.ROUND_DOWN) + "");
			map.put("ratio", ratio);
			map.put("mayBalance", mayBalance.setScale(2, BigDecimal.ROUND_DOWN) + "");
			map.put("deduct", deduct.setScale(2, BigDecimal.ROUND_DOWN) + "");
		}
		return map;
	}
	
	/**
	  * @Title: queryExtractCashLogByUserId 
	  * @Description: 根据userId查询主播提现记录 
	  * @param userId
	  * @return Map<String, Object>
	  */
	@Override
	public Map<String, Object> queryExtractCashLogByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> mapL = null;
		OrderInfoQueryFilter query = new OrderInfoQueryFilter();
		
		query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		query.setBuyerUid(Long.valueOf(userId));
		query.setSellerUid(ConfigConstant.TOTORO_SELLER_ID);
		query.setChlId(GoodsChlEnum.GOODS_CHL_TOTORO_CALORIE.getGoodChlId());
		
		//字段排序
		Sort sort = new Sort(Direction.DESC, "utime");
		
		BigDecimal totRmb = orderInfoRepositorySlave.getSumOrderPrice(query.getBuyerUid(), query.getSellerUid(),
				query.getChlId(), OrderStatusEnum.SUCCESS.getType(), DeliveryStatusEnum.IS_DELIVERY.getType());
		List<OrderInfo> infoL = orderInfoRepositorySlave.findAll(query, sort);
		map.put("userId", userId);
		map.put("totRmb", null != totRmb ? totRmb + "" : "0.00");
		
		if (CollectionUtils.isNotEmpty(infoL)) {
			mapL = new ArrayList<Map<String, Object>>();
			BigDecimal deduct = new BigDecimal("0.00");
			BigDecimal calorie = new BigDecimal("0.00");
			Integer status = null;
			
			for (OrderInfo info : infoL) {
				status = 1;
				Map<String, Object> m = new HashMap<String, Object>();
				deduct = info.getPayPrice().subtract(info.getOrderPrice()).setScale(2, BigDecimal.ROUND_DOWN);
				calorie = info.getPayPrice().divide(new BigDecimal(ExchangeRateEnum.CALORIE_RATE_RMB.getVal()), 2,
						BigDecimal.ROUND_DOWN);
				
				if (OrderStatusEnum.FAIL.getType() == info.getOrderStatus()
						&& DeliveryStatusEnum.DELIVERY_FAIL.getType() == info.getDeliveryStatus()) {
					status = 0;
				} else if (OrderStatusEnum.SUCCESS.getType() == info.getOrderStatus()
						&& DeliveryStatusEnum.IS_DELIVERY.getType() == info.getDeliveryStatus()) {
					status = 2;
				}
				m.put("orderId", info.getOrderId());
				m.put("balance", info.getOrderPrice() + "");
				m.put("deduct", deduct + "");
				m.put("calorie", calorie + "");
				m.put("time", DateUtil.timestampToDates(info.getCtime(), DateUtil.TIME_PATTON_DEFAULT) + "");
				m.put("status", status);
				mapL.add(m);
			}
		}
		map.put("logList", mapL);
		return map;
	}
	
	/**
	 * @Title: queryExtractCashDetails
	 * @Description: 根据userId和orderId获取提现详情记录
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> queryExtractCashDetails(String userId, String orderId) {
		Map<String, Object> details = new HashMap<String, Object>();
		
		OrderInfo info = getOrderInfoByOrderId(orderId, userId);
		if (null != info) {
			Integer status = 1;
			if (OrderStatusEnum.FAIL.getType() == info.getOrderStatus()
					&& DeliveryStatusEnum.DELIVERY_FAIL.getType() == info.getDeliveryStatus()) {
				status = 0;
			} else if (OrderStatusEnum.SUCCESS.getType() == info.getOrderStatus()
					&& DeliveryStatusEnum.IS_DELIVERY.getType() == info.getDeliveryStatus()) {
				status = 2;
			}
			
			BigDecimal deduct = info.getPayPrice().subtract(info.getOrderPrice()).setScale(2, BigDecimal.ROUND_DOWN);
			BigDecimal calorie = info.getPayPrice().divide(new BigDecimal(ExchangeRateEnum.CALORIE_RATE_RMB.getVal()), 2,
					BigDecimal.ROUND_DOWN);
			
			details.put("orderId", info.getOrderId());
			details.put("balance", info.getOrderPrice() + "");
			details.put("deduct", deduct + "");
			details.put("calorie", calorie + "");
			details.put("time", DateUtil.timestampToDates(info.getCtime(), DateUtil.TIME_PATTON_DEFAULT));
			details.put("status", status);
			String[] param = info.getParam().split(";");
			String ratio = 0 < param.length ? param[0].replace("兑换比率[", "").replace("]", "") : "";
			String realName = 1 < param.length ? param[1].replace("真实姓名[", "").replace("]", "") : "";
			String account = 2 < param.length ? param[2].replace("支付宝账号[", "").replace("]", "") : "";
			
			details.put("ratio", ratio);
			details.put("realName", realName);
			details.put("account", account);
			details.put("remark", info.getRemark());
		}
		return details;
	}
	
	/**
	 * @Title: createOpal 
	 * @Description: 创建主播猫眼石流水 
	 * @param info
	 * @param userOpal
	 * @param changeType
	 * @param bizType
	 * @return OpalChangeRecord
	 */
	private OpalChangeRecord createOpal(IdentityInfo info, Long userOpal, Short changeType, Short bizType) {
		OpalChangeRecord opal = new OpalChangeRecord();
		Long opalId = idGlobalGenerator.getSeqId(OpalChangeRecord.class);
		
		opal.setOpalId(opalId);
		opal.setUserId(info.getUserId());
		opal.setChangeType(changeType);
		opal.setChangeNum(userOpal);
		opal.setBizType(bizType);
		opal.setOutBizId(info.getId());
		opal.setCtime(DateUtil.currentSecond());
		opal.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		return opalChangeRecordRepositoryMaster.save(opal);
	}
	
	/**
	 * @Title: updateOpal 
	 * @Description: 更新主播猫眼石值 
	 * @param userId
	 * @param userOpal
	 * @param changeType
	 * @return Boolean
	 */
	@Override
	public Boolean updateOpal(String userId, Long userOpal, Integer changeType) {
		Boolean flag = false;
		if (0L < userOpal) {
			IdentityInfo info = queryIdentityByUserId(userId);

			if (null != info) {
				Integer i = 0;
				if (IsPlusEnum.PLUS.getVal().equals(changeType)) {// 增加
					i = identityInfoRepositoryMaster.updateOpal(userOpal, DateUtil.currentSecond(), "OPAL",
							info.getId());
					//猫眼石流水(主播获取)
					createOpal(info, userOpal, IsPlusEnum.PLUS.getVal().shortValue(), Short.valueOf("0"));
				} else if (IsPlusEnum.MINUS.getVal().equals(changeType)) {// 减少
					if (info.getOpal() >= userOpal) {
						i = identityInfoRepositoryMaster.updateOpal(-userOpal, DateUtil.currentSecond(), "OPAL",
								info.getId());
						//猫眼石流水(主播消耗)
						createOpal(info, userOpal, IsPlusEnum.MINUS.getVal().shortValue(), Short.valueOf("1"));
					} else {
						throw new ImplException(CommConstant.GWSCOD0017, CommConstant.GWSMSG0017);
					}
				}
				flag = 1 == i ? true : false;
			}
		}
		return flag;
	}
	
	/**
	 * @Title: updateOpalBatch 
	 * @Description: 批量更新主播猫眼石值 
	 * @param opalList
	 * @return Boolean
	 */
	@Override
	public Boolean updateOpalBatch(List<Map<String, Object>> opalList) {
		Boolean flag = false;

		for (Map<String, Object> map : opalList) {
			Long userOpal = map.containsKey("userOpal") ? Long.valueOf(map.get("userOpal").toString()) : 0L;
			Integer changeType = Integer.valueOf(map.get("changeType").toString());

			if (map.containsKey("userId")) {
				flag = updateOpal(map.get("userId").toString(), userOpal, changeType);
			}
		}
		return flag;
	}
	
	/**
	 * @Title: updateCalorie 
	 * @Description: 更新主播卡路里 
	 * @param userId
	 * @param calorie
	 * @param changeType
	 * @return Boolean
	 */
	@Override
	public Boolean updateCalorie(String userId, Long calorie, Integer changeType) {
		Boolean flag = false;
		if (0L < calorie) {
			Integer i = 0;
			if (IsPlusEnum.PLUS.getVal().equals(changeType)) {// 增加
				i = userRepositoryMaster.updateCalorie(calorie, new Date(), userId, userId);
			} else if (IsPlusEnum.MINUS.getVal().equals(changeType)) {// 减少
				i = userRepositoryMaster.updateCalorie(-calorie, new Date(), userId, userId);
			}
			flag = 1 == i ? true : false;
		}
		return flag;
	}
	
	/**
	  * @Title: queryIdentityByUserId 
	  * @Description: 查询主播直播认证、转账提现信息 
	  * @param userId
	  * @return IdentityInfo
	  */
	@Override
	public IdentityInfo queryIdentityByUserId(String userId) {
		return identityInfoRepositorySlave.queryIdentityByUserId(userId);
	}
	
	/**
	 * @Title: queryUserByUserId 
	 * @Description: 根据userId查询用户信息表 
	 * @param userId
	 * @return User
	 */
	public User queryUserByUserId(String userId) {
		return userRepositorySlave.queryUserByUserId(userId);
	}
	
	/**
	 * @Title: getOrderTradeFlowByOrderId 
	 * @Description: 订单流水ID获取订单流水 
	 * @param flowId
	 * @return OrderTradeFlow
	 */
	public OrderTradeFlow getOrderTradeFlowByOrderId(String flowId) {
		OrderTradeFlow orderFlow = null;
		if (StringUtils.isNotBlank(flowId)) {
			orderFlow = orderTradeFlowRepositorySlave.findOne(Long.valueOf(flowId));
		} else {
			GwsLogger.info("flowId不存在");
		}
		return orderFlow;
	}
	
	/**
	 * @Title: getOrderInfoByOrderId 
	 * @Description: 订单ID获取订单 
	 * @param orderId
	 * @return OrderInfo
	 */
	public OrderInfo getOrderInfoByOrderId(String orderId) {
		OrderInfo order = null;
		if (StringUtils.isNotBlank(orderId)) {
			order = orderInfoRepositorySlave.findOne(Long.valueOf(orderId));
		} else {
			GwsLogger.info("OrderId不存在");
		}
		return order;
	}
	
	/**
	 * @Title: getOrderInfoByOrderId 
	 * @Description: 根据用户ID和订单ID获取订单 
	 * @param orderId
	 * @param buyerUid
	 * @return OrderInfo
	 */
	public OrderInfo getOrderInfoByOrderId(String orderId, String buyerUid) {
		OrderInfo order = null;
		if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(buyerUid)) {
			order = orderInfoRepositorySlave.getOrderInfoByOrderId(Long.valueOf(orderId), Long.valueOf(buyerUid));
		} else {
			GwsLogger.info("orderId或buyerUid不存在");
		}
		return order;
	}
	
	public Boolean checkSign(Map<String, Object> param) {
		String userId = param.get("userId").toString();
		String balance = param.get("balance").toString();
		String sign = param.get("sign").toString();
		String str = "userId=" + userId + "&balance=" + balance + "&appkey=" + api.getIncAppkey().trim();
		String signE = Md5Util.thirtyTwo(str);
		return sign.equals(signE);
	}
	
}
