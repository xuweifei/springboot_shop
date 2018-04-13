package com.opengroup.longmao.gwcommon.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.ActConfig;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.entity.po.CoinAccount;
import com.opengroup.longmao.gwcommon.entity.po.CoinChangeRecord;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserGradeRule;
import com.opengroup.longmao.gwcommon.enums.ActivityTypeEnum;
import com.opengroup.longmao.gwcommon.enums.CoinRecordTypeEnum;
import com.opengroup.longmao.gwcommon.enums.ExchangeRateEnum;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.repository.master.CoinAccountRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.CoinChangeRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.CoinAccountRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserGradeRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.ActConfigService;
import com.opengroup.longmao.gwcommon.service.BeanAccountService;
import com.opengroup.longmao.gwcommon.service.CoinAccountService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;

/**
 * 【龙猫币账户】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:49:16
 */
@Service
public class CoinAccountServiceImpl implements CoinAccountService {

	@Autowired
	private CoinAccountRepositoryMaster coinAccountRepositoryMaster;

	@Autowired
	private CoinAccountRepositorySlave coinAccountRepositorySlave;

	@Autowired
	private CoinChangeRecordRepositoryMaster coinChangeRecordRepositoryMaster;

	@Autowired
	private UserRepositoryMaster userRepositoryMaster;

	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private UserGradeRuleRepositorySlave userGradeRuleRepositorySlave;

	@Autowired
	private ActConfigService actConfigService;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private BeanAccountService beanAccountService;
	
	volatile private ReentrantLock c = new ReentrantLock(true);
	
	volatile private ReentrantLock cEb = new ReentrantLock(true);
	
	/**
	 * @Title: coinExchBean
	 * @Description: 全体用户龙猫币转换龙猫豆
	 * @return Boolean
	 */
	@Override
	public Map<String, Object> coinExchBean() {
		cEb.lock();
		Map<String, Object> coinM = new HashMap<String, Object>();

		try {
			Boolean flag = false;
			List<CoinAccount> cL = coinAccountRepositoryMaster.queryCoinS();
			if (CollectionUtils.isNotEmpty(cL)) {
				for (CoinAccount c : cL) {
					String userId = c.getUserId();
					Double lmCoinNum = c.getLmCoinNum();
					//更新龙猫币数量/增加龙猫币流水
					BigDecimal coinNum = new BigDecimal(lmCoinNum);
					Boolean cFlag = updateCoin(userId, coinNum, IsPlusEnum.MINUS.getVal(), CoinRecordTypeEnum.COIN_IN_BEAN.getVal());
					//更新龙猫豆数量/增加龙猫豆流水
					if (cFlag) {
						//龙猫币兑换龙猫豆数量(换算比率计算)
						BigDecimal beanNum = new BigDecimal(lmCoinNum)
								.multiply(new BigDecimal(ExchangeRateEnum.COIN_RATE_BEAN.getVal()))
								.setScale(0, BigDecimal.ROUND_DOWN);
						flag = beanAccountService.updateBean(userId, beanNum, IsPlusEnum.PLUS.getVal(), CoinRecordTypeEnum.COIN_IN_BEAN.getVal());
					}
					if (flag) {
						GwsLogger.info("用户【" + userId + "】龙猫币转换龙猫豆成功:userId={}", userId);
					} else {
						GwsLogger.info("用户【" + userId + "】龙猫币转换龙猫豆失败:userId={}", userId);
						coinM.put("userId", userId);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
			}
		} finally {
			cEb.unlock();
		}
		return coinM;
	}
	
	/**
	 * @Title: coinToBean 
	 * @Description: 龙猫币兑换龙猫豆 
	 * @param userId
	 * @param lmCoinNum
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> coinToBean(String userId, Integer lmCoinNum) {
		c.lock();
		Map<String, Object> coinM = new HashMap<String, Object>();
		try {
			Boolean flag = false;
			//更新龙猫币数量/增加龙猫币流水
			BigDecimal coinNum = new BigDecimal(lmCoinNum);
			Boolean cFlag = updateCoin(userId, coinNum, IsPlusEnum.MINUS.getVal(), CoinRecordTypeEnum.COIN_IN_BEAN.getVal());
			//更新龙猫豆数量/增加龙猫豆流水
			if (cFlag) {
				//龙猫币兑换龙猫豆数量(换算比率计算)
				BigDecimal beanNum = new BigDecimal(lmCoinNum)
						.multiply(new BigDecimal(ExchangeRateEnum.COIN_RATE_BEAN.getVal()))
						.setScale(0, BigDecimal.ROUND_DOWN);
				flag = beanAccountService.updateBean(userId, beanNum, IsPlusEnum.PLUS.getVal(), CoinRecordTypeEnum.COIN_IN_BEAN.getVal());
			}
			
			//查询币账户
			CoinAccount coin = getCoinById(userId);
			//查询豆账户
			BeanAccount bean = beanAccountService.getBeanById(userId);
			
			coinM.put("userId", userId);
			coinM.put("lmCoinNum", null != coin ? coin.getLmCoinNum() : 0);
			coinM.put("lmBeanNum", null != bean ? bean.getLmBeanNum() : 0);
			coinM.put("isSuccess", flag);
		} finally {
			c.unlock();
		}
		return coinM;
	}
	
	/**
	 * @Title: updateCoin 
	 * @Description: 更新龙猫币/增加龙猫币流水 
	 * @param userId
	 * @param lmCoinNum
	 * @param changeType
	 * @param bizType
	 * @return boolean
	 */
	@Override
	@Transactional
	public boolean updateCoin(String userId, BigDecimal lmCoinNum, Integer changeType, Integer bizType) {
		if (0 <= new BigDecimal("0.00").compareTo(lmCoinNum)) {
			GwsLogger.info("龙猫币数量有误：lmCoinNum={}", lmCoinNum);
			return true;
		}
		//龙猫币账户
		CoinAccount coin = getCoinById(userId);
		if (null == coin) {
			GwsLogger.error("userId龙猫币账户BeanAccount不存在：userId={}", userId);
			throw new ImplException(CommConstant.GWSCOD0027, CommConstant.GWSMSG0027);
		} else {
			Integer flag = 0;
			//账户龙猫币数量
			BigDecimal coinNum = new BigDecimal(coin.getLmCoinNum());
			//增加龙猫币
			if (changeType.equals(IsPlusEnum.PLUS.getVal())) {
				flag = coinAccountRepositoryMaster.updateCoinAccount(lmCoinNum.doubleValue(), new Date(), userId, userId);
			//减少龙猫币
			} else if (changeType.equals(IsPlusEnum.MINUS.getVal())) {
				//判断龙猫币兑换数量是否超额
				if(0 <= coinNum.compareTo(lmCoinNum)){
					flag = coinAccountRepositoryMaster.updateCoinAccount(-lmCoinNum.doubleValue(), new Date(), userId, userId);
				}else{
					GwsLogger.error("userId龙猫币余额不足：userId={},coinNum={},lmBeanNum={}", userId, coinNum, lmCoinNum);
					throw new ImplException(CommConstant.GWSCOD0028, CommConstant.GWSMSG0028);
				}
			}
			
			CoinChangeRecord record = null;
			if(Integer.valueOf("1").equals(flag)){
				CoinChangeRecord ccr = new CoinChangeRecord();
				final String id = idGlobalGenerator.getSeqId(CoinChangeRecord.class).toString();

				ccr.setId(id);
				ccr.setUserId(userId);
				ccr.setChangeType(changeType);
				ccr.setChangeNum(lmCoinNum.doubleValue());
				ccr.setBizType(bizType);
				ccr.setOutBizId(coin.getId());
				ccr.setGmtCreate(new Date());
				ccr.setGmtModified(new Date());
				ccr.setGmtCreateUser("");
				ccr.setGmtModifiedUser("");
				record = coinChangeRecordRepositoryMaster.save(ccr);
			}
			return record == null ? false : true;
		}
	}

	/**
	 * 用户充值
	 * 
	 * @param userId
	 *            用户userId
	 * @param rmb
	 *            RMB
	 * @param rate
	 *            参与充值送活动 比例；无活动传入0；送5%传入0.05
	 * @return boolean
	 */
	@Override
	@Transactional
	public boolean recharge(String userId, BigDecimal rmb) {
		// 用户在活动期间内充值龙猫币大于等于98元，将赠送5%的龙猫币（自定义时四舍五入）
		double rate = 0;
		// BigDecimal比较大小
		int r = rmb.compareTo(new BigDecimal(98));
		//rmb >= 98,就赠送5%的龙猫币（自定义时四舍五入），r>-1就是>0或者>1
		if (r>-1) {
			ActConfig act = actConfigService.findActConfigByType(ActivityTypeEnum.BEAN_RECHARGE_GIVE.getVal());
			GwsLogger.info("rmb > 98：boolean={}", r == 1);
			if (act != null) {
				Date now = new Date();
				if (act.getStatus().equals(IsOrNotEnum.YES.getType())
						&&act.getStartTime() != null && act.getStartTime().before(now) 
						&& act.getEndTime() != null&& act.getEndTime().after(now)) {
					// 用户在活动期间内充值人民币大于等于98元，将赠送5%的龙猫币（自定义时四舍五入）
					rate = 0.05;
					GwsLogger.info("活动中，赠送比例：rate={},活动结束时间={}", rate,act.getEndTime());
				}else{
					GwsLogger.info("活动已结束，赠送比例：rate={},活动结束时间={}", 0, rate,act.getEndTime());
				}
			}
		}

		// 通过用户id查询龙猫币账户
		final CoinAccount ca = coinAccountRepositorySlave.queryCoinaccountByUserIdEntity(userId);
		if (null == ca) {
			GwsLogger.error("userId所在的龙猫币账户coinAccount不存在：userId={}", userId);
			return false;
		}
		final Date date = new Date();
		CoinAccount cAccount = null;

		double coinNum = rmb.multiply(new BigDecimal(10)).doubleValue();
		GwsLogger.info("需要增加的经验值coinNum：coinNum={}", coinNum);
		
		// 龙猫币账户流水id
		final String id = idGlobalGenerator.getSeqId(CoinChangeRecord.class).toString();

		// 业务ID
		String outBizId = ca.getId();
		GwsLogger.info("业务ID：outBizId={}", outBizId);

		// 账户记录表
		CoinChangeRecord coinrecord = new CoinChangeRecord();
		coinrecord.setId(id);
		coinrecord.setUserId(userId);
		coinrecord.setBizType(CoinRecordTypeEnum.RECHARGE.getVal());
		coinrecord.setChangeNum(coinNum);
		coinrecord.setGmtCreate(date);
		coinrecord.setGmtModified(date);
		// 变更类型：0：增加；1：减少
		coinrecord.setChangeType(IsPlusEnum.PLUS.getVal());
		coinrecord.setOutBizId(outBizId);
		coinChangeRecordRepositoryMaster.save(coinrecord);

		// 赠送
		if (rate > 0) {
			GwsLogger.info("进入充值赠送业务：rate={}", rate);
			coinNum += Math.round(coinNum * rate);
			GwsLogger.info("累计后的coinNum：coinNum={}", coinNum);
			// 龙猫币账户流水id
			final String rateId = idGlobalGenerator.getSeqId(CoinChangeRecord.class).toString();
			// 账户记录表
			CoinChangeRecord coinrecordext = new CoinChangeRecord();
			coinrecordext.setId(rateId);
			coinrecordext.setUserId(userId);
			coinrecordext.setBizType(CoinRecordTypeEnum.RECHARGE_GIVE.getVal());
			coinrecordext.setChangeNum(Math.round(coinNum * rate));
			coinrecordext.setGmtCreate(date);
			coinrecordext.setGmtModified(date);
			// 变更类型：0：增加；1：减少
			coinrecordext.setChangeType(IsPlusEnum.PLUS.getVal());
			// 龙猫币账户不存在
			coinrecordext.setOutBizId(outBizId);
			coinChangeRecordRepositoryMaster.save(coinrecord);
		}

		// 更新用户经验值
		User user = userRepositorySlave.queryUserByUserId(userId);
		if (null != user) {
			// 用户之前经验
			BigDecimal experience = new BigDecimal(user.getExperience());
			GwsLogger.info("用户之前经验：experience={}", experience);
			// 用户需充值经验(1人民币=10龙猫币=100龙猫豆=100经验值)
			BigDecimal rechargeExperience = rmb.multiply(new BigDecimal(100));
			GwsLogger.info("用户需充值经验：rechargeExperience={}", rechargeExperience);
			// 用户当前经验 = 用户之前经验 + 充值送的经验
			BigDecimal currentExp = experience.add(rechargeExperience);
			GwsLogger.info("用户当前经验 = 用户之前经验 + 充值送的经验：currentExp={}", currentExp);

			Integer grade = 1;
			//根据经验获取用户等级
			if (1 == currentExp.compareTo(BigDecimal.ZERO)) {
				List<UserGradeRule> gL = userGradeRuleRepositorySlave.findUserGradeRuleList(currentExp.longValue(),
						IsDeleteEnum.UN_DELETE.getVal());
				
				if (CollectionUtils.isNotEmpty(gL)) {
					UserGradeRule userGradeRule = gL.get(0);
					grade = userGradeRule.getGrade().intValue();
				}
			} 
			
			user.setGrade(grade);
			user.setExperience(Integer.toString(currentExp.intValue()));
			user.setGmtModified(new Date());
			userRepositoryMaster.save(user);
		}
		// 更新龙猫币账户信息
		ca.setGmtModified(new Date());
		ca.setLmCoinNum(ca.getLmCoinNum() + coinNum);
		cAccount = coinAccountRepositoryMaster.save(ca);
		// 返回结果
		return cAccount == null ? false : true;
	}
	
	
	
	/**
	 * 用户充值
	 * 
	 * @param userId
	 *            用户userId
	 * @param rmb
	 *            RMB
	 * @param rate
	 *            参与充值送活动 比例；无活动传入0；送5%传入0.05
	 * @return boolean
	 */
	@Override
	@Transactional
	public boolean rechargeByUserId(String userId, BigDecimal rmb) {
		// 通过用户id查询龙猫币账户
		final CoinAccount ca = coinAccountRepositorySlave.queryCoinaccountByUserIdEntity(userId);
		if (null == ca) {
			GwsLogger.error("userId所在的龙猫币账户coinAccount不存在：userId={}", userId);
			return false;
		}
		final Date date = new Date();
		CoinAccount cAccount = null;

		double coinNum = rmb.multiply(new BigDecimal(10)).doubleValue();
		GwsLogger.info("需要增加的经验值coinNum：coinNum={}", coinNum);
		// 龙猫币账户流水id
		final String id = idGlobalGenerator.getSeqId(CoinChangeRecord.class).toString();

		// 业务ID
		String outBizId = ca.getId();
		GwsLogger.info("业务ID：outBizId={}", outBizId);

		// 账户记录表
		CoinChangeRecord coinrecord = new CoinChangeRecord();
		coinrecord.setId(id);
		coinrecord.setUserId(userId);
		coinrecord.setBizType(CoinRecordTypeEnum.RECHARGE.getVal());
		coinrecord.setChangeNum(coinNum);
		coinrecord.setGmtCreate(date);
		coinrecord.setGmtModified(date);
		// 变更类型：0：增加；1：减少
		coinrecord.setChangeType(IsPlusEnum.PLUS.getVal());
		coinrecord.setOutBizId(outBizId);
		coinChangeRecordRepositoryMaster.save(coinrecord);

		// 更新用户经验值
//		User user = userRepositorySlave.queryUserByUserId(userId);
//		if (null != user) {
//			// 用户之前经验
//			BigDecimal experience = new BigDecimal(user.getExperience());
//			GwsLogger.info("用户之前经验：experience={}", experience);
//			// 用户需充值经验(1人民币=10龙猫币=100龙猫豆=100经验值)
//			BigDecimal rechargeExperience = rmb.multiply(new BigDecimal(100));
//			GwsLogger.info("用户需充值经验：rechargeExperience={}", rechargeExperience);
//			// 用户当前经验 = 用户之前经验 + 充值送的经验
//			BigDecimal currentExp = experience.add(rechargeExperience);
//			GwsLogger.info("用户当前经验 = 用户之前经验 + 充值送的经验：currentExp={}", currentExp);
//
//			Integer grade = 1;
//			//根据经验获取用户等级
//			if (1 == currentExp.compareTo(BigDecimal.ZERO)) {
//				List<UserGradeRule> gL = userGradeRuleRepositorySlave.findUserGradeRuleList(currentExp.longValue(),
//						IsDeleteEnum.UN_DELETE.getVal());
//				
//				if (CollectionUtils.isNotEmpty(gL)) {
//					UserGradeRule userGradeRule = gL.get(0);
//					grade = userGradeRule.getGrade().intValue();
//				}
//			} 
//			
//			user.setGrade(grade);
//			user.setExperience(Integer.toString(currentExp.intValue()));
//			user.setGmtModified(new Date());
//			userRepositoryMaster.save(user);
//		}
		// 更新龙猫币账户信息
		ca.setGmtModified(new Date());
		ca.setLmCoinNum(ca.getLmCoinNum() + coinNum);
		cAccount = coinAccountRepositoryMaster.save(ca);
		// 返回结果
		return cAccount == null ? false : true;
	}
	
	/**
	 * @Title: getCoinByUserId
	 * @Description: 根据userId查询龙猫币
	 * @param userId
	 * @return CoinAccount
	 */
	@Override
	public CoinAccount getCoinByUserId(String userId) {
		return coinAccountRepositorySlave.queryCoinaccountByUserIdEntity(userId);
	}
	
	/**
	 * @Title: getCoinById 
	 * @Description: 根据id查询龙猫币 
	 * @param userId
	 * @return CoinAccount
	 */
	@Override
	public CoinAccount getCoinById(String userId) {
		return coinAccountRepositoryMaster.queryCoinByUserId(userId);
	}
}
