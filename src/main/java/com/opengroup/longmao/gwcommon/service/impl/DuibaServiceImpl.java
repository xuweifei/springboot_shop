package com.opengroup.longmao.gwcommon.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.properties.ApiConfig;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.entity.po.BeanChangeRecord;
import com.opengroup.longmao.gwcommon.entity.po.OrderBuyDetail;
import com.opengroup.longmao.gwcommon.entity.po.OrderInfo;
import com.opengroup.longmao.gwcommon.entity.po.OrderTradeFlow;
import com.opengroup.longmao.gwcommon.entity.vo.OrderBuyDetailVO;
import com.opengroup.longmao.gwcommon.entity.vo.OrderInfoVO;
import com.opengroup.longmao.gwcommon.entity.vo.OrderTradeFlowVO;
import com.opengroup.longmao.gwcommon.enums.BeanRecordTypeEnum;
import com.opengroup.longmao.gwcommon.enums.DeliveryStatusEnum;
import com.opengroup.longmao.gwcommon.enums.ExchangeEnum;
import com.opengroup.longmao.gwcommon.enums.ExchangeRateEnum;
import com.opengroup.longmao.gwcommon.enums.GoodsCategoryEnum;
import com.opengroup.longmao.gwcommon.enums.GoodsChlEnum;
import com.opengroup.longmao.gwcommon.enums.GoodsTypeEnum;
import com.opengroup.longmao.gwcommon.enums.HuYiStatusCodeEnum;
import com.opengroup.longmao.gwcommon.enums.IsNormalEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.enums.OrderDiscountWayEnum;
import com.opengroup.longmao.gwcommon.enums.OrderStatusEnum;
import com.opengroup.longmao.gwcommon.enums.PayFlowStatusEnum;
import com.opengroup.longmao.gwcommon.enums.PayPlatformEnum;
import com.opengroup.longmao.gwcommon.enums.PayWayEnum;
import com.opengroup.longmao.gwcommon.enums.RefundStatusEnum;
import com.opengroup.longmao.gwcommon.repository.master.BeanAccountRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.BeanChangeRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.OrderBuyDetailMaster;
import com.opengroup.longmao.gwcommon.repository.master.OrderInfoRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.OrderTradeFlowRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.OrderInfoRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.OrderTradeFlowRepositorySlave;
import com.opengroup.longmao.gwcommon.service.DuibaService;
import com.opengroup.longmao.gwcommon.tools.date.DateUtil;
import com.opengroup.longmao.gwcommon.tools.http.HttpRequest;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;
import com.opengroup.longmao.gwcommon.tools.runnable.DuiBaPushRunnable;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConfirmParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConsumeParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditConsumeResult;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditNotifyParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.CreditVirtualParams;
import com.opengroup.longmao.gwcommon.tools.sdk.duiba.SignTool;
import com.opengroup.longmao.gwcommon.tools.sdk.topup.HuYiNotifyParams;
import com.opengroup.longmao.gwcommon.tools.sdk.topup.TopUpByHuYi;

/**
 * @author Hermit
 *
 */
@Service
public class DuibaServiceImpl implements DuibaService {

	@Autowired
	private BeanAccountRepositoryMaster beanAccountRepositoryMaster;

	@Autowired
	private BeanChangeRecordRepositoryMaster beanChangeRecordRepositoryMaster;
	
	@Autowired
	private OrderInfoRepositoryMaster orderInfoRepositoryMaster;
	
	@Autowired
	private OrderBuyDetailMaster orderBuyDetailMaster;
	
	@Autowired
	private OrderTradeFlowRepositoryMaster orderTradeFlowRepositoryMaster;

	@Autowired
	private OrderInfoRepositorySlave orderInfoRepositorySlave;
	
	@Autowired
	private OrderTradeFlowRepositorySlave orderTradeFlowRepositorySlave;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private TopUpByHuYi topUpByHuYi;
	
	@Autowired
	private ApiConfig config;

	/**
	 * @Title: saveOrder
	 * @Description: 保存兑吧订单-扣龙猫豆订单
	 * @param params
	 * @param itemCode
	 * @return CreditConsumeResult
	 */
	@Override
	public CreditConsumeResult saveOrderInfo(CreditConsumeParams params, String itemCode) {
		CreditConsumeResult result = new CreditConsumeResult(false);
		OrderInfo newOrder = setOrderInfo(params);
		if (newOrder != null) {
			// 根据兑吧订单号获取订单
			OrderInfo order = getOrderInfoBychlOrderId(newOrder.getChlOrderId());
			if (order == null) {
				// 先获取用户目前的总积分
				BeanAccount userScore = getBeanByUserId(newOrder.getBuyerUid().toString());
				if (userScore != null) {
					// 用户目前的总积分(龙猫豆)
					Double score = userScore.getLmBeanNum();
					// 获取用户正在处理中的订单
					List<OrderInfo> list = getOrderInfoListByBuyerUid(newOrder.getBuyerUid(), newOrder.getSellerUid(),
							newOrder.getChlId(), OrderStatusEnum.UN_PAY.getType(),
							DeliveryStatusEnum.UN_DELIVERY.getType());
					if (0 < list.size()) {
						for (OrderInfo orderInfo : list) {
							// 订单正在处理中，积分正在预扣中
							BigDecimal b1 = new BigDecimal(score);
							BigDecimal b2 = orderInfo.getPayPrice().multiply(new BigDecimal(100));//支付金额换算成龙猫豆
							score = b1.subtract(b2).doubleValue(); // 要减去正在预扣的积分
						}
					}
					// 如果剩余的有效的积分小于本次兑换需要扣除的积分
					BigDecimal b3 = new BigDecimal(score);
					BigDecimal b4 = newOrder.getPayPrice().multiply(new BigDecimal(100));
					if (b3.compareTo(b4) < 0) {
						Integer integral = newOrder.getPayPrice().multiply(new BigDecimal(100)).intValue();
						result.setErrorMessage("目前剩余" + b3 + "积分，本次兑换需要扣除" + integral + "积分");
						GwsLogger.info("用户目前剩余" + b3 + "龙猫豆，本次兑换需要扣除" + integral + "龙猫豆");
						return result;
					}

					newOrder.setOrderId(idGlobalGenerator.getSeqId(OrderInfo.class));
					OrderInfo saveOrder = orderInfoRepositoryMaster.save(newOrder);

					if (saveOrder != null && saveOrder.getOrderId() > 0) {
						OrderBuyDetailVO detail = new OrderBuyDetailVO(saveOrder.getOrderId(), 8L,
								params.getDescription(), saveOrder.getOrderPrice());
						saveOrderBuyDetail(detail);//创建订单详情表
						
						OrderTradeFlowVO flow = new OrderTradeFlowVO(saveOrder.getOrderId(),
								PayFlowStatusEnum.UN_PAY.getType(), saveOrder.getPayPrice(),
								saveOrder.getChlOrderId(), params.getTimestamp(), IsNormalEnum.NO.getVal());
						saveOrderTradeFlow(flow);//创建交易流水表
						
						BeanAccount beanAcct = updateBeanAcct(saveOrder, true, BeanRecordTypeEnum.EXCHANGE.getVal());// 更新用户积分(扣龙猫豆)
						
						if (beanAcct != null) {
							OrderTradeFlowVO flowPay = new OrderTradeFlowVO(saveOrder.getOrderId(),
									PayFlowStatusEnum.ING_PAY.getType(), saveOrder.getPayPrice(),
									saveOrder.getChlOrderId(), beanAcct.getGmtModified(), IsNormalEnum.NO.getVal());
							saveOrderTradeFlow(flowPay);
							//更改订单表支付状态
							OrderInfoVO orderVO = new OrderInfoVO(saveOrder.getOrderId(),
									OrderStatusEnum.IS_PAY.getType(), DeliveryStatusEnum.UN_DELIVERY.getType(),
									"龙猫豆扣除成功,待发货");
							updateOrderState(saveOrder, orderVO);
							CreditConsumeResult resultOk = new CreditConsumeResult(true);
							resultOk.setBizId(String.valueOf(saveOrder.getOrderId())); // 我们自己的兑吧订单id
							resultOk.setCredits(new BigDecimal(userScore.getLmBeanNum()).longValue()); // 用户积分余额(扣除本次兑换需要的积分)
							GwsLogger.info("兑吧订单保存成功,扣除龙猫豆成功");
							return resultOk;
						} else {
							OrderTradeFlowVO flowPay = new OrderTradeFlowVO(saveOrder.getOrderId(),
									PayFlowStatusEnum.FAIL_PAY.getType(), saveOrder.getPayPrice(),
									saveOrder.getChlOrderId(), params.getTimestamp(), IsNormalEnum.YES.getVal());
							saveOrderTradeFlow(flowPay);
							OrderInfoVO orderVO = new OrderInfoVO(saveOrder.getOrderId(),
									OrderStatusEnum.FAIL.getType(), DeliveryStatusEnum.DELIVERY_FAIL.getType(),
									"龙猫豆扣除失败");
							updateOrderState(saveOrder, orderVO);
							GwsLogger.info("兑吧订单保存成功,扣除龙猫豆失败");
							result.setErrorMessage("扣除用户龙猫豆失败!");
							return result;
						}
					}
				} else {
					result.setErrorMessage("用户没有积分");
					GwsLogger.info("用户没有龙猫豆:params={}", ToStringBuilder.reflectionToString(params));
					return result;
				}
			} else {
				result.setErrorMessage("订单正在处理中");
				GwsLogger.info("兑吧订单正在处理中:params={}", ToStringBuilder.reflectionToString(params));
				return result;
			}
		}
		result.setErrorMessage("兑吧传参为NUll");
		GwsLogger.error("兑吧传参为NUll,保存兑吧订单失败:params={}", ToStringBuilder.reflectionToString(params));
		return result;
	}

	/**
	 * @Title: saveOrder
	 * @Description: 保存兑吧订单-加龙猫豆订单
	 * @param params
	 * @return CreditConsumeResult
	 */
	@Override
	public CreditConsumeResult saveOrderInfo(CreditConsumeParams params) {
		CreditConsumeResult result = new CreditConsumeResult(false);
		OrderInfo newOrder = setOrderInfo(params);
		if (newOrder != null) {
			// 根据兑吧订单号获取订单
			OrderInfo order = getOrderInfoBychlOrderId(newOrder.getChlOrderId());
			if (order == null) {
				newOrder.setOrderId(idGlobalGenerator.getSeqId(OrderInfo.class));
				OrderInfo saveOrder = orderInfoRepositoryMaster.save(newOrder);

				if (saveOrder != null && saveOrder.getOrderId() > 0) {
					OrderBuyDetailVO detail = new OrderBuyDetailVO(saveOrder.getOrderId(), 8L,
							params.getDescription(), saveOrder.getOrderPrice());
					saveOrderBuyDetail(detail);//创建订单详情表
					
					OrderTradeFlowVO flow = new OrderTradeFlowVO(saveOrder.getOrderId(),
							PayFlowStatusEnum.UN_PAY.getType(), saveOrder.getPayPrice(),
							saveOrder.getChlOrderId(), params.getTimestamp(), IsNormalEnum.NO.getVal());
					saveOrderTradeFlow(flow);//创建交易流水表
					
					BeanAccount userScore = updateBeanAcct(saveOrder, false, BeanRecordTypeEnum.GIVE.getVal());// 更新用户积分(加龙猫豆)
					
					if (userScore != null) {
						OrderTradeFlowVO flowPay = new OrderTradeFlowVO(saveOrder.getOrderId(),
								PayFlowStatusEnum.ING_PAY.getType(), saveOrder.getPayPrice(),
								saveOrder.getChlOrderId(), userScore.getGmtModified(), IsNormalEnum.NO.getVal());
						saveOrderTradeFlow(flowPay);
						CreditConsumeResult resultOk = new CreditConsumeResult(true);
						resultOk.setBizId(String.valueOf(saveOrder.getOrderId())); // 我们自己的兑吧订单id
						resultOk.setCredits(new BigDecimal(userScore.getLmBeanNum()).longValue()); // 用户积分余额(加上本次兑换增加的积分)
						GwsLogger.info("兑吧加积分成功,增加龙猫豆成功");
						return resultOk;
					} else {
						OrderTradeFlowVO flowPay = new OrderTradeFlowVO(saveOrder.getOrderId(),
								PayFlowStatusEnum.FAIL_PAY.getType(), saveOrder.getPayPrice(),
								saveOrder.getChlOrderId(), params.getTimestamp(), IsNormalEnum.YES.getVal());
						saveOrderTradeFlow(flowPay);
						OrderInfoVO orderVO = new OrderInfoVO(saveOrder.getOrderId(), OrderStatusEnum.FAIL.getType(),
								DeliveryStatusEnum.DELIVERY_FAIL.getType(), "龙猫豆增加失败");
						updateOrderState(saveOrder, orderVO);
						GwsLogger.info("兑吧订单保存成功,增加龙猫豆失败");
						result.setErrorMessage("增加用户龙猫豆失败!");
						return result;
					}
				}
			} else {
				result.setErrorMessage("订单正在处理中");
				GwsLogger.info("兑吧订单正在处理中:params={}", ToStringBuilder.reflectionToString(params));
				return result;
			}
		}
		result.setErrorMessage("兑吧传参为NUll");
		GwsLogger.error("兑吧传参为NUll,保存兑吧订单失败:params={}", ToStringBuilder.reflectionToString(params));
		return result;
	}
	
	/**
	 * @Title: phoneFeeTopUpInDuiBa 
	 * @Description: 兑吧订单虚拟充值 
	 * @param params
	 * @return CreditVirtualParams
	 */
	@Override
	public CreditVirtualParams phoneFeeTopUpInDuiBa(CreditNotifyParams params) {
		CreditVirtualParams result = new CreditVirtualParams((short)2);
		if (params != null) {
			String orderId = params.getBizId();// 自有订单号
			// 获取兑吧订单
			OrderInfo order = getOrderInfoByOrderId(orderId);
			if (order != null) {
				// 订单不为空，且订单正在处理中
				BeanAccount beanAcct = getBeanByUserId(order.getBuyerUid().toString());
				BigDecimal b1 = new BigDecimal(beanAcct.getLmBeanNum());
				if (OrderStatusEnum.UN_PAY.getType() == order.getOrderStatus()
						&& DeliveryStatusEnum.UN_DELIVERY.getType() == order.getDeliveryStatus()) {
					
					OrderInfo newOrder = setOrderInfo(order);//创建第三方充值订单(互亿无线)
					newOrder.setOrderId(idGlobalGenerator.getSeqId(OrderInfo.class));
					OrderInfo saveOrder = orderInfoRepositoryMaster.save(newOrder);
					
					if(saveOrder != null) {
						OrderBuyDetailVO detail = new OrderBuyDetailVO(saveOrder.getOrderId(), 9L,
								params.getDescription(), saveOrder.getOrderPrice());
						saveOrderBuyDetail(detail);//创建订单详情表
						
						OrderTradeFlowVO flow = new OrderTradeFlowVO(saveOrder.getOrderId(),
								PayFlowStatusEnum.UN_PAY.getType(), saveOrder.getPayPrice(),
								saveOrder.getChlOrderId(), params.getTimestamp(), IsNormalEnum.NO.getVal());
						OrderTradeFlow orderFlow = saveOrderTradeFlow(flow);//创建交易流水表
						
						
						String mobile = saveOrder.getParam();
						String price = String.valueOf(saveOrder.getOrderPrice().intValue());
						Map<String, String> map = new HashMap<String, String>();
						map.put("mobile", mobile);
						map.put("orderId", newOrder.getOrderId()+"");
						map.put("price", price);
						Map<String, String> jsonMap = topUpByHuYi.phoneFee(map, config.getIsTest().trim());//提交充值请求到互亿无线接口,互译无线返回Map
						Date time = new Date();
						
						if ("1".equals(jsonMap.get("code"))) {
							String payFlowNo = jsonMap.get("taskid");
							OrderInfoVO orderVO = new OrderInfoVO(saveOrder.getOrderId(),
									OrderStatusEnum.HADNl_ING.getType(), DeliveryStatusEnum.DELIVERY_ING.getType(),
									time, payFlowNo, "");
							updateOrderState(saveOrder, orderVO);
							
							OrderTradeFlowVO orderFlowVO = new OrderTradeFlowVO(orderFlow.getFlowId(), payFlowNo, time);
							updateOrderTradeFlow(orderFlow, orderFlowVO);
							
							OrderTradeFlowVO flowPay = new OrderTradeFlowVO(saveOrder.getOrderId(),
									PayFlowStatusEnum.ING_PAY.getType(), saveOrder.getPayPrice(), payFlowNo, time,
									IsNormalEnum.NO.getVal());
							saveOrderTradeFlow(flowPay);
							
							CreditVirtualParams success = new CreditVirtualParams((short)0);
							success.setBizId(String.valueOf(order.getOrderId())); // 我们自己的兑吧订单ID
							success.setCredits(b1.longValue()); // 用户积分余额
							
							virtualTest(newOrder.getOrderId());//测试路线
							return success;
						} else {
							OrderInfoVO orderVO = new OrderInfoVO(saveOrder.getOrderId(),
									OrderStatusEnum.FAIL.getType(), DeliveryStatusEnum.DELIVERY_FAIL.getType(), time,
									saveOrder.getChlOrderId(), "");
							updateOrderState(saveOrder, orderVO);
							
							OrderTradeFlowVO flowPay = new OrderTradeFlowVO(saveOrder.getOrderId(),
									PayFlowStatusEnum.FAIL_PAY.getType(), saveOrder.getPayPrice(),
									saveOrder.getChlOrderId(), time, IsNormalEnum.YES.getVal());
							saveOrderTradeFlow(flowPay);
							
							if (Integer.parseInt(jsonMap.get("code")) > 2000) {
								result.setErrorMessage(HuYiStatusCodeEnum.getEnumByCode(jsonMap.get("code")).getExplain());
							}
							result.setBizId(String.valueOf(order.getOrderId()));
							result.setCredits(b1.longValue()); // 用户积分余额
							GwsLogger.error("外部充值接口处理失败:params={},code={}", ToStringBuilder.reflectionToString(params),
									HuYiStatusCodeEnum.getEnumByCode(jsonMap.get("code")).getExplain());
							
							virtualTest(newOrder.getOrderId());//测试路线
							return result;
						}
					}
				} else {
					GwsLogger.info("兑吧充值正在处理:params={}", ToStringBuilder.reflectionToString(params));
					CreditVirtualParams process = new CreditVirtualParams((short)1);
					process.setErrorMessage("订单正在处理中");
					process.setBizId(String.valueOf(order.getOrderId()));
					process.setCredits(b1.longValue()); // 用户积分余额
					return process;
				}
			}
		}
		result.setErrorMessage("兑吧传参为NUll");
		GwsLogger.error("兑吧回调数据为空,充值失败:params={}", ToStringBuilder.reflectionToString(params));
		return result;
	}
	
	/**
	 * @Title: pushNotify 
	 * @Description: 充值结果推送 
	 * @param params
	 * @return CreditConfirmParams
	 */
	@Override
	public CreditConfirmParams pushNotify(HuYiNotifyParams params) {
		CreditConfirmParams creditParams = null;
		if (params != null) {
			creditParams = new CreditConfirmParams();
			OrderInfo hOrder = getOrderInfoByOrderId(params.getOrderID());//获取充值订单
			OrderInfo dOrder = getOrderInfoByOrderId(hOrder.getChlOrderId());
			Date time = new Date(); 
			if ("1".equals(params.getState())) {
				OrderInfoVO orderVO = new OrderInfoVO(hOrder.getOrderId(), OrderStatusEnum.SUCCESS.getType(),
						DeliveryStatusEnum.IS_DELIVERY.getType(), "");
				updateOrderState(hOrder, orderVO);
				
				OrderTradeFlowVO flowPay = new OrderTradeFlowVO(hOrder.getOrderId(), PayFlowStatusEnum.IS_PAY.getType(),
						hOrder.getPayPrice(), params.getTaskId(), time, IsNormalEnum.YES.getVal());
				saveOrderTradeFlow(flowPay);
				
				creditParams.setSuccess(true);
				GwsLogger.error("外部充值接口结果推送,充值成功");
			} else {// 充值失败, 交易回滚, 返还龙猫豆
				OrderInfoVO orderVO = new OrderInfoVO(hOrder.getOrderId(), OrderStatusEnum.FAIL.getType(),
						DeliveryStatusEnum.DELIVERY_FAIL.getType(), "");
				updateOrderState(hOrder, orderVO);
				updateBeanAcct(dOrder, false, BeanRecordTypeEnum.ERR_RETURN.getVal());// 回滚用户积分(加龙猫豆)
				
				OrderTradeFlowVO flowPay = new OrderTradeFlowVO(hOrder.getOrderId(),
						PayFlowStatusEnum.FAIL_PAY.getType(), hOrder.getPayPrice(), params.getTaskId(), time,
						IsNormalEnum.YES.getVal());
				saveOrderTradeFlow(flowPay);

				creditParams.setSuccess(false);
				GwsLogger.error("外部充值接口结果推送,充值失败:params={}", ToStringBuilder.reflectionToString(params));
			}
			creditParams.setOrderNum(dOrder.getChlOrderId());
			creditParams.setErrorMessage(params.getMessage());
		}
		return creditParams;
	}
	
	/**
	 * @Title: virtualTest 
	 * @Description: 测试结果推送路线 
	 * @param orderId
	 * @return void
	 */
	public void virtualTest(Long orderId) {
		try {
			if ("true".equals(config.getIsTest().trim())) {//判断是否测试
				
				HuYiNotifyParams params = new HuYiNotifyParams();
				params.setTaskId("Test_888888");
				params.setOrderID(orderId.toString());
				params.setMobile("13456781709");
				params.setState("1");
				params.setMessage("话费充值测试成功");
				
				CreditConfirmParams p = pushNotify(params);
				
				String url = config.getDuiBaInformHttp().trim();
				String appKey = config.getAppKey().trim();
				String appSecret = config.getAppSecret().trim();
				
				Map<String, String> map = new HashMap<String, String>();
				Long timestamp = new Date().getTime();
				map.put("appSecret", appSecret);
				map.put("appKey", appKey);
				map.put("timestamp", timestamp + "");
				map.put("success", p.isSuccess() + "");
				map.put("errorMessage", p.getErrorMessage());
				map.put("orderNum", p.getOrderNum());
				String sign = SignTool.sign(map);

				String signParams = "appKey=" + appKey + "&sign=" + sign + "&timestamp=" + timestamp + "&success=" + p.isSuccess()
				+ "&errorMessage=" + p.getErrorMessage() + "&orderNum=" + p.getOrderNum();
				
				//String content = HttpURLConnectionRequest.sendHttp(url, signParams, "POST");
				String content = HttpRequest.httpPOST(url, signParams);
				if (!"ok".equalsIgnoreCase(content)) {
					DuiBaPushRunnable duibaRunnable = new DuiBaPushRunnable(url, signParams);
					Thread pushTh = new Thread(duibaRunnable);
					pushTh.start();
				}
				GwsLogger.info("测试充值结果通知回调结束:url={},signParams={},content{}", url, signParams, content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title: duiBaNotify 
	 * @Description: 兑吧充值结果通知 
	 * @param url
	 * @param signParams
	 */
	@Override
	public void duiBaNotify(String url, String signParams) {
		try {
			//String content = HttpURLConnectionRequest.sendHttp(url, signParams, "POST");
			String content = HttpRequest.httpPOST(url, signParams);
			if (!"ok".equalsIgnoreCase(content)) {
				DuiBaPushRunnable duibaRunnable = new DuiBaPushRunnable(url, signParams);
				Thread pushTh = new Thread(duibaRunnable);
				pushTh.start();
			}
			GwsLogger.info("充值结果通知回调:url={},signParams={},content{}",url,signParams,content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title: setOrderInfo 
	 * @Description: OrderInfo对象赋值 
	 * @param param
	 * @return OrderInfo
	 */
	private OrderInfo setOrderInfo(CreditConsumeParams param) {
		if (param != null) {
			// 保存兑吧订单
			OrderInfo newOrder = new OrderInfo();
			newOrder.setBuyerUid(Long.valueOf(param.getUid()));// 用户ID
			newOrder.setSellerUid(ConfigConstant.TOTORO_SELLER_ID);//卖家ID
			newOrder.setOrderPrice(param.getFacePrice().divide(new BigDecimal(100)));//商品金额,按比例换算(兑换商品的市场价值, 单位是分)
			newOrder.setPayPrice(new BigDecimal(param.getCredits()).divide(new BigDecimal(100)));//用户支付金额,按比例换算(即本次兑换扣除用户的龙猫豆)
			newOrder.setIntegral(param.getActualPrice().divide(new BigDecimal(100)).longValue());//赠予用户的积分(即用于充值给用户的金额)
			
			newOrder.setPayPlatform(PayPlatformEnum.PAY_PLATFORM_DUIBA.getPlatformId());//所属平台
			newOrder.setPayWay(PayWayEnum.PAY_WAY_BEAN.getPayWayId());//支付方式
			newOrder.setChlId(GoodsChlEnum.GOODS_CHL_DUIBA_SHOP.getGoodChlId());//渠道
			
			newOrder.setDiscountWay(OrderDiscountWayEnum.UN.getType());
			newOrder.setOrderStatus(OrderStatusEnum.UN_PAY.getType());// 订单状态
			newOrder.setDeliveryStatus(DeliveryStatusEnum.UN_DELIVERY.getType());// 积分状态
			newOrder.setCreateDate(DateUtil.getCurrentDateByFormat(DateUtil.DATA_PATTON_YYYYMMDD));
			newOrder.setChlOrderId(param.getOrderNum());// 兑吧订单号
			newOrder.setIsDummy(ExchangeEnum.getEnumByCode(param.getType()).getType());
			newOrder.setPayTime(DateUtil.currentSecond());
			newOrder.setPayFlowNo(param.getOrderNum());//支付流水号->兑吧订单号
			newOrder.setGoodsCategoryId(GoodsCategoryEnum.GCTGY_RECHARGE_DUIBA.getNumber());
			newOrder.setGoodsTypeId(GoodsTypeEnum.DB_GTYPE_RECHARGE.getType());
			newOrder.setOrderEndTime(30);
			newOrder.setCtime(DateUtil.currentSecond());
			newOrder.setUtime(DateUtil.currentSecond());
			newOrder.setRefundStatus(RefundStatusEnum.NO_REFUND.getType());
			newOrder.setRemark(param.getParams());
			newOrder.setParam(param.getParams());
			newOrder.setIsDelete(IsNormalEnum.YES.getVal());
			if ("virtual".equals(param.getType())) {
				//虚拟充值无市场价返回,需通过所扣除积分去除加成积分获得市场价
				BigDecimal price = new BigDecimal(param.getCredits())
						.divide(new BigDecimal(ExchangeRateEnum.BEAN_RATE_GOODS.getVal()));
				BigDecimal orderPrice = price.subtract(new BigDecimal(String.valueOf(price).substring(1)));
				newOrder.setOrderPrice(orderPrice);
				newOrder.setIntegral(orderPrice.longValue());
				newOrder.setParam(param.getParams().replace("phonebillψ", ""));
			}
			return newOrder;
		}
		return null;
	}
	
	private OrderInfo setOrderInfo(OrderInfo order) {
		if (order != null) {
			// 保存兑吧订单
			OrderInfo newOrder = new OrderInfo();
			newOrder.setBuyerUid(order.getBuyerUid());// 用户ID
			newOrder.setSellerUid(ConfigConstant.TOTORO_SELLER_ID);//卖家ID
			newOrder.setOrderPrice(new BigDecimal(order.getIntegral()));//订单金额(即为用户充值的金额)
			newOrder.setPayPrice(new BigDecimal(order.getIntegral())
					.multiply(new BigDecimal(ExchangeRateEnum.HUYI_RATE_BEAN.getVal()))
					.setScale(2, BigDecimal.ROUND_DOWN));//实际支付订单金额(即支付充值平台的金额)
			newOrder.setIntegral(0L);
			
			newOrder.setPayPlatform(PayPlatformEnum.PAY_PLATFORM_HUYI.getPlatformId());//所属平台
			newOrder.setPayWay(PayWayEnum.PAY_WAY_PLATFORM_FEE.getPayWayId());//支付方式
			newOrder.setChlId(GoodsChlEnum.GOODS_CHL_HUYI.getGoodChlId());//渠道
			
			newOrder.setDiscountWay(OrderDiscountWayEnum.UN.getType());
			newOrder.setOrderStatus(OrderStatusEnum.UN_PAY.getType());// 订单状态
			newOrder.setDeliveryStatus(DeliveryStatusEnum.UN_DELIVERY.getType());// 积分状态
			newOrder.setCreateDate(DateUtil.getCurrentDateByFormat(DateUtil.DATA_PATTON_YYYYMMDD));
			newOrder.setChlOrderId(order.getOrderId()+"");// 兑吧订单号
			newOrder.setIsDummy(order.getIsDummy());
			newOrder.setPayTime(DateUtil.currentSecond());
			newOrder.setPayFlowNo(order.getOrderId()+"");//支付流水号->兑吧订单号
			newOrder.setGoodsCategoryId(GoodsCategoryEnum.GCTGY_RECHARGE_HUYI.getNumber());
			newOrder.setGoodsTypeId(GoodsTypeEnum.HY_GTYPE_RECHARGE.getType());
			newOrder.setOrderEndTime(30);
			newOrder.setCtime(DateUtil.currentSecond());
			newOrder.setUtime(DateUtil.currentSecond());
			newOrder.setRefundStatus(RefundStatusEnum.NO_REFUND.getType());
			newOrder.setParam(order.getParam());// 返回参数
			newOrder.setIsDelete(IsNormalEnum.YES.getVal());
			return newOrder;
		}
		return null;
	}
	
	/**
	 * @Title: saveOrderBuyDetail 
	 * @Description: 创建订单明细表 
	 * @param orderVO
	 * @return OrderBuyDetail
	 */
	public OrderBuyDetail saveOrderBuyDetail(OrderBuyDetailVO orderVO) {
		if (null == orderVO) {
			throw new RuntimeException(CommConstant.PARAMERR);
		}
		// 根据商品id得到商品信息
		//GoodsInfo goodsInfo = goodsInfoSlave.findOne(goodsId);
		// 插入购买商品详细表
		GwsLogger.info("开始创建订单购买明细表代码(OrderBuyDetail)");
		OrderBuyDetail orderDetail = new OrderBuyDetail();
		orderDetail.setCtime(DateUtil.currentSecond());
		orderDetail.setUtime(DateUtil.currentSecond());
		orderDetail.setGoodsDiscount(100);
		orderDetail.setGoodsName(orderVO.getGoodsName());
		orderDetail.setGoodsId(orderVO.getGoodsId());
		orderDetail.setGoodsNum(1);
		orderDetail.setGoodsPrice(orderVO.getGoodsPrice());
		orderDetail.setOrderId(orderVO.getOrderId());
		orderDetail.setIsDelete(IsNormalEnum.YES.getVal());
		Long detailId = idGlobalGenerator.getSeqId(OrderBuyDetail.class);
		orderDetail.setBuyDetailId(detailId);
		orderDetail = orderBuyDetailMaster.save(orderDetail);
		GwsLogger.info("结束创建订单购买明细表(orderDetail):duyDetailId={}", orderDetail.getBuyDetailId());
		return orderDetail;
	}
	
	
	/**
	 * @Title: saveOrderTradeFlow
	 * @Description: 创建交易订单流水表
	 * @param map
	 * @return OrderTradeFlow
	 */
	public OrderTradeFlow saveOrderTradeFlow(OrderTradeFlowVO orderVO) {
		OrderTradeFlow orderTradeFlow = new OrderTradeFlow();
		// id统一生成
		Long flowId = idGlobalGenerator.getSeqId(OrderTradeFlow.class);
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
		orderTradeFlow = orderTradeFlowRepositoryMaster.save(orderTradeFlow);
		// 判断对象是否存在
		if (orderTradeFlow != null) {
			GwsLogger.info("交易订单流水表保存成功");
		} else {
			GwsLogger.info("交易订单流水表保存失败:orderTradeFlow={}", ToStringBuilder.reflectionToString(orderTradeFlow));
			return null;
		}
		return orderTradeFlow;
	}
	
	public OrderTradeFlow updateOrderTradeFlow(OrderTradeFlow orderFlow, OrderTradeFlowVO orderVO) {
		if (orderFlow == null) {
			orderFlow = getOrderTradeFlowByOrderId(orderVO.getFlowId().toString());
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
	 * @Title: updateOrderState
	 * @Description: 更新兑吧订单
	 * @param params
	 */
	@Override
	public void updateOrderState(CreditNotifyParams params) {
		if (params != null) {
			String orderNum = params.getOrderNum();// 兑吧订单号
			// 获取兑吧订单
			OrderInfo order = getOrderInfoBychlOrderId(orderNum);
			// 订单不为空，且订单正在处理中
			if (order != null && OrderStatusEnum.IS_PAY.getType() == order.getOrderStatus()
					&& DeliveryStatusEnum.UN_DELIVERY.getType() == order.getDeliveryStatus()) {
				if (params.isSuccess()) { // 兑换成功
					OrderInfoVO orderVO = new OrderInfoVO(order.getOrderId(), OrderStatusEnum.SUCCESS.getType(),
							DeliveryStatusEnum.IS_DELIVERY.getType(), "");
					OrderInfo startOrder = updateOrderState(order, orderVO);// 订单成功，更新兑吧订单状态
					if (startOrder != null) {
						OrderTradeFlowVO flowPay = new OrderTradeFlowVO(startOrder.getOrderId(),
								PayFlowStatusEnum.IS_PAY.getType(), startOrder.getPayPrice(),
								startOrder.getChlOrderId(), params.getTimestamp(), IsNormalEnum.YES.getVal());
						saveOrderTradeFlow(flowPay);
						GwsLogger.info("兑吧兑换成功,更新订单状态成功");
					}
				} else { // 兑换失败,回滚用户积分
					OrderInfoVO orderVO = new OrderInfoVO(order.getOrderId(), OrderStatusEnum.FAIL.getType(),
							DeliveryStatusEnum.DELIVERY_FAIL.getType(), params.getErrorMessage());
					OrderInfo startOrder = updateOrderState(order, orderVO);
					if (startOrder != null) {
						GwsLogger.info("兑吧兑换失败,更新订单为兑换失败订单:params={}", ToStringBuilder.reflectionToString(params));
						BeanAccount userScore = updateBeanAcct(startOrder, false, BeanRecordTypeEnum.ERR_RETURN.getVal());// 回滚用户积分(加龙猫豆)
						if (userScore != null) {
							OrderTradeFlowVO flowPay = new OrderTradeFlowVO(startOrder.getOrderId(),
									PayFlowStatusEnum.FAIL_PAY.getType(), startOrder.getPayPrice(),
									startOrder.getChlOrderId(), userScore.getGmtModified(), IsNormalEnum.YES.getVal());
							saveOrderTradeFlow(flowPay);
							GwsLogger.info("兑吧兑换失败,回滚龙猫豆成功(加龙猫豆)");
						}
					}
				}
			}
		} else {
			GwsLogger.error("兑吧回调数据为空,更新兑吧订单失败:params={}", ToStringBuilder.reflectionToString(params));
		}
	}
	
	/**
	 * @Title: updateOrderState 
	 * @Description: 更新兑吧订单状态  
	 * @param orderInfo
	 * @param orderVO
	 * @return OrderInfo
	 */
	private OrderInfo updateOrderState(OrderInfo orderInfo, OrderInfoVO orderVO) {
		if (orderInfo == null) {
			orderInfo = getOrderInfoByOrderId(orderVO.getOrderId().toString());
		}
		orderInfo.setOrderStatus(orderVO.getOrderStatus());// 订单状态
		orderInfo.setDeliveryStatus(orderVO.getDeliveryStatus());// 积分状态
		orderInfo.setUtime(DateUtil.currentSecond());
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
	 * @Title: updateBeanAcct
	 * @Description: 更新龙猫豆账户
	 * @param orderInfo、isDeduct
	 * @return BeanAccount
	 */
	private BeanAccount updateBeanAcct(OrderInfo orderInfo, boolean isDeduct, Integer bizType) {
		BeanAccount beanAcct = getBeanByUserId(orderInfo.getBuyerUid().toString());
		if (null != beanAcct) {
			BigDecimal b1 = new BigDecimal(beanAcct.getLmBeanNum());
			BigDecimal b2 = orderInfo.getPayPrice().multiply(new BigDecimal(100));
			Integer changeType = IsPlusEnum.MINUS.getVal();
			Date date = new Date();
			Integer flag = 0;
			if (isDeduct) {// 扣龙猫豆
				//beanAcct.setLmBeanNum(b1.subtract(b2).doubleValue());
				flag = beanAccountRepositoryMaster.updateBeanAccount(-b2.doubleValue(), date,
						orderInfo.getBuyerUid().toString(), orderInfo.getBuyerUid().toString());
			} else {// 加龙猫豆
				changeType = IsPlusEnum.PLUS.getVal();
				//beanAcct.setLmBeanNum(b1.add(b2).doubleValue());
				flag = beanAccountRepositoryMaster.updateBeanAccount(b2.doubleValue(), date,
						orderInfo.getBuyerUid().toString(), orderInfo.getBuyerUid().toString());
			}
			//beanAcct.setGmtModified(date);
			//beanAcct.setGmtModifiedUser(orderInfo.getBuyerUid().toString());
			//BeanAccount acct = beanAccountRepositoryMaster.save(beanAcct);
			BeanAccount acct = null;
			if (0 < flag) {
				Long id = idGlobalGenerator.getSeqId(BeanChangeRecord.class);

				BeanChangeRecord record = new BeanChangeRecord();
				record.setId(id.toString());
				record.setUserId(orderInfo.getBuyerUid().toString());
				record.setChangeType(changeType);
				record.setAgoNum(b1.doubleValue());
				record.setChangeNum(b2.doubleValue());
				record.setBizType(bizType);
				record.setOutBizId(beanAcct.getId());
				record.setGmtCreate(date);
				record.setGmtCreateUser(orderInfo.getChlOrderId());
				record.setGmtModified(date);
				record.setGmtModifiedUser(orderInfo.getOrderId().toString());
				beanChangeRecordRepositoryMaster.save(record);
				acct = getBeanByUserId(orderInfo.getBuyerUid().toString());
			}
			return acct;
		}
		GwsLogger.info("龙猫豆账户不存在!");
		return null;
	}

	/**
	 * @Title: getBeanByUserId
	 * @Description: userId 获取用户龙猫豆
	 * @param userId
	 * @return BeanAccount
	 */
	@Override
	public BeanAccount getBeanByUserId(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			return beanAccountRepositoryMaster.queryBeanByUserId(userId);
		}
		return null;
	}

	/**
	 * @Title: getOrderInfoBychlOrderId 
	 * @Description: 渠道订单ID获取订单 
	 * @param chlOrderId
	 * @return OrderInfo
	 */
	public OrderInfo getOrderInfoBychlOrderId(String chlOrderId) {
		return orderInfoRepositorySlave.getOrderInfoBychlOrderId(chlOrderId);
	}
	
	/**
	 * @Title: getOrderInfoByOrderId 
	 * @Description: 订单ID获取订单 
	 * @param OrderId
	 * @return OrderInfo
	 */
	public OrderInfo getOrderInfoByOrderId(String OrderId) {
		return orderInfoRepositorySlave.findOne(Long.valueOf(OrderId));
	}
	
	/**
	 * @Title: getOrderTradeFlowByOrderId 
	 * @Description: 订单流水ID获取订单流水 
	 * @param flowId
	 * @return OrderTradeFlow
	 */
	public OrderTradeFlow getOrderTradeFlowByOrderId(String flowId) {
		return orderTradeFlowRepositorySlave.findOne(Long.valueOf(flowId));
	}

	/**
	 * @Title: getOrderInfoListByBuyerUid 
	 * @Description: 用户ID、订单状态、发货状态获取订单 
	 * @param buyerUid
	 * @param orderStatus
	 * @param deliveryStatus
	 * @return List<OrderInfo>
	 */
	public List<OrderInfo> getOrderInfoListByBuyerUid(Long buyerUid, Long sellerUid, Long chlId, Short orderStatus, Short deliveryStatus) {
		return orderInfoRepositorySlave.getOrderInfoListByBuyerUid(buyerUid, sellerUid, chlId, orderStatus, deliveryStatus);
	}
}
