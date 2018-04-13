package com.opengroup.longmao.gwcommon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.configuration.redis.cache.IdGlobalGenerator;
import com.opengroup.longmao.gwcommon.entity.po.ActConfig;
import com.opengroup.longmao.gwcommon.entity.po.BeanAccount;
import com.opengroup.longmao.gwcommon.entity.po.BeanChangeRecord;
import com.opengroup.longmao.gwcommon.entity.po.CoinAccount;
import com.opengroup.longmao.gwcommon.entity.po.User;
import com.opengroup.longmao.gwcommon.entity.po.UserGradeRule;
import com.opengroup.longmao.gwcommon.enums.ActivityTypeEnum;
import com.opengroup.longmao.gwcommon.enums.BeanRecordTypeEnum;
import com.opengroup.longmao.gwcommon.enums.ExchangeRateEnum;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.enums.IsPlusEnum;
import com.opengroup.longmao.gwcommon.repository.master.BeanAccountRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.BeanChangeRecordRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.master.UserRepositoryMaster;
import com.opengroup.longmao.gwcommon.repository.slave.UserGradeRuleRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.UserRepositorySlave;
import com.opengroup.longmao.gwcommon.service.ActConfigService;
import com.opengroup.longmao.gwcommon.service.BeanAccountService;
import com.opengroup.longmao.gwcommon.service.CoinAccountService;
import com.opengroup.longmao.gwcommon.service.UserGradeRuleService;
import com.opengroup.longmao.gwcommon.tools.result.CommConstant;
import com.opengroup.longmao.gwcommon.tools.result.ImplException;

/**
 * 【龙猫豆账户】 Service接口实现
 *
 * @version 1.0
 * @author Hermit 2017年03月15日 下午15:48:32
 */
@Service
public class BeanAccountServiceImpl implements BeanAccountService {
	
	@Autowired
	private BeanChangeRecordRepositoryMaster beanChangeRecordRepositoryMaster;
	
	@Autowired
	private UserGradeRuleRepositorySlave userGradeRuleRepositorySlave;
	
	@Autowired
	private BeanAccountRepositoryMaster beanAccountRepositoryMaster;
	
	@Autowired
	private UserGradeRuleService userGradeRuleService;

	@Autowired
	private UserRepositoryMaster userRepositoryMaster;

	@Autowired
	private UserRepositorySlave userRepositorySlave;
	
	@Autowired
	private CoinAccountService coinAccountService;

	@Autowired
	private IdGlobalGenerator idGlobalGenerator;
	
	@Autowired
	private ActConfigService actConfigService;
	
	volatile private ReentrantLock r = new ReentrantLock(true);
	
	volatile private ReentrantLock b = new ReentrantLock(true);
	
	/**
	 * 用户充值
	 * @param userId 用户userId
	 * @param rmb RMB
	 * @param rate 参与充值送活动 比例；无活动传入0；送5%传入0.05
	 * @return boolean
	 */
	@Override
//	@Transactional
	public boolean recharge(String userId, BigDecimal rmb) {
		//用户在活动期间内充值人民币大于等于98元，将赠送人民币5%的龙猫豆（自定义时四舍五入）
		double rate = 0;
		//BigDecimal比较大小
		int r = rmb.compareTo(new BigDecimal(98));
		//rmb >= 98,就赠送5%的龙猫豆（自定义时四舍五入），r>-1就是>0或者>1
		if (r > -1) {
			ActConfig act = actConfigService.findActConfigByType(ActivityTypeEnum.BEAN_RECHARGE_GIVE.getVal());
			GwsLogger.info("rmb > 98：boolean={}", r == 1);
			if (act != null) {
				Date now = new Date();
				if (act.getStatus().equals(IsOrNotEnum.YES.getType())
						&&act.getStartTime() != null && act.getStartTime().before(now) 
						&& act.getEndTime() != null && act.getEndTime().after(now)) {
					//用户在活动期间内充值人民币大于等于98元，将赠送人民币5%的龙猫豆（自定义时四舍五入）
					rate = 0.05;
					GwsLogger.info("活动中，赠送比例：rate={},活动结束时间={}", rate, act.getEndTime());
				} else {
					GwsLogger.info("活动已结束，赠送比例：rate={},活动结束时间={}", 0, rate, act.getEndTime());
				}
			}
		}

		// 通过用户id查询龙猫豆账户
		final BeanAccount ba = beanAccountRepositoryMaster.queryBeanByUserId(userId);
		if (null == ba) {
			GwsLogger.error("userId所在的龙猫豆账户beanAccount不存在：userId={}", userId);
			return false;
		}
		final Date date = new Date();
		Integer flag = 0;

		double beanNum = rmb.multiply(new BigDecimal(100)).doubleValue();
		GwsLogger.info("需要增加的经验值:exp={}", beanNum);

		// 龙猫豆账户流水id
//		final String id = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();
		String id = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();
		// 业务ID
		String outBizId = ba.getId();
		GwsLogger.info("业务ID：outBizId={}", outBizId);

		// 账户记录表
		BeanChangeRecord beanRecord = new BeanChangeRecord();
		try{
			GwsLogger.info("开始生成账户记录");
			beanRecord.setId(id);
			beanRecord.setUserId(userId);
			beanRecord.setAgoNum(ba.getLmBeanNum());
			//变更类型：0：增加；1：减少
			beanRecord.setChangeType(IsPlusEnum.PLUS.getVal());
			beanRecord.setChangeNum(beanNum);
			beanRecord.setBizType(BeanRecordTypeEnum.RECHARGE.getVal());
			beanRecord.setOutBizId(outBizId);
			beanRecord.setGmtCreate(date);
			beanRecord.setGmtCreateUser(userId);
			beanRecord.setGmtModified(date);
			beanRecord.setGmtModifiedUser(userId);
			beanChangeRecordRepositoryMaster.save(beanRecord);
			GwsLogger.info("结束生成账户记录");
		}catch(Exception e){
			GwsLogger.error("BeanChangeRecord保存异常：e={}", e.getMessage());
		}

		//赠送龙猫豆
		if (rate > 0) {
			GwsLogger.info("进入充值赠送业务：rate={}", rate);
			double beanGive = Math.round(beanNum * rate);
			beanNum = beanNum + beanGive;
			GwsLogger.info("累计后的龙猫豆:beanNum={}", beanNum);
			//龙猫豆账户流水id
//			final String rateId = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();
			String rateId = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();
			//账户记录表
			BeanChangeRecord beanRecordext = new BeanChangeRecord();
			beanRecordext.setId(rateId);
			beanRecordext.setUserId(userId);
			beanRecordext.setAgoNum(ba.getLmBeanNum() + beanNum);
			//变更类型：0：增加；1：减少
			beanRecordext.setChangeType(IsPlusEnum.PLUS.getVal());
			beanRecordext.setChangeNum(beanGive);
			beanRecordext.setBizType(BeanRecordTypeEnum.RECHARGE_GIVE.getVal());
			beanRecordext.setOutBizId(outBizId);
			beanRecordext.setGmtCreate(date);
			beanRecordext.setGmtCreateUser(userId);
			beanRecordext.setGmtModified(date);
			beanRecordext.setGmtModifiedUser(userId);
			beanChangeRecordRepositoryMaster.save(beanRecord);
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
			// 根据经验获取用户等级
			if (1 == currentExp.compareTo(BigDecimal.ZERO)) {
				List<UserGradeRule> gL = userGradeRuleRepositorySlave.findUserGradeRuleList(currentExp.longValue(),
						IsDeleteEnum.UN_DELETE.getVal());

				if (CollectionUtils.isNotEmpty(gL)) {
					UserGradeRule userGradeRule = gL.get(0);
					grade = userGradeRule.getGrade().intValue();
				}
			}
			try{
				GwsLogger.info("开始修改账户经验值");
				user.setGrade(grade);
				user.setExperience(Integer.toString(currentExp.intValue()));
				user.setGmtModified(new Date());
				userRepositoryMaster.save(user);
				GwsLogger.info("结束修改账户经验值");
				//创建用户经验值流水
				userGradeRuleService.createExp(user, rechargeExperience.longValue(),
						IsPlusEnum.PLUS.getVal().shortValue(), Short.valueOf("0"));
			}catch(Exception e){
				GwsLogger.error("user经验值修改异常：e={}", e.getMessage());
			}
		}
		// 更新龙猫豆账户信息
		try{
			GwsLogger.info("开始修改账户龙猫豆");
			flag = beanAccountRepositoryMaster.updateBeanAccount(beanNum, new Date(), outBizId);
			GwsLogger.info("结束修改账户龙猫豆");
		}catch(Exception e){
			GwsLogger.error("updateBeanAccount经验值修改异常：e={}", e.getMessage());
		}
		// 返回结果
		return 0 == flag ? false : true;
	}
	
	/**
	 * @Title: beanToCoin
	 * @Description: 龙猫豆兑换龙猫币
	 * @param userId
	 * @param lmBeanNum
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> beanToCoin(String userId, Integer lmBeanNum) {
		b.lock();
		Map<String, Object> beanM = new HashMap<String, Object>();
		try {
			Boolean flag = false;
			//更新龙猫豆数量/增加龙猫豆流水
			BigDecimal beanNum = new BigDecimal(lmBeanNum);
			Boolean bFlag = updateBean(userId, beanNum, IsPlusEnum.MINUS.getVal(), BeanRecordTypeEnum.BEAN_IN_COIN.getVal());
			//更新龙猫币数量/增加龙猫币流水
			if (bFlag) {
				//龙猫豆兑换龙猫币数量(换算比率计算)
				BigDecimal coinNum = new BigDecimal(lmBeanNum)
						.multiply(new BigDecimal(ExchangeRateEnum.BEAN_RATE_COIN.getVal()))
						.multiply(new BigDecimal(ExchangeRateEnum.BEAN_RATE_COIN_RATE.getVal()))
						.setScale(0, BigDecimal.ROUND_DOWN);
				BigDecimal fee = new BigDecimal(lmBeanNum)
						.multiply(new BigDecimal(ExchangeRateEnum.BEAN_RATE_COIN.getVal())).subtract(coinNum)
						.setScale(0, BigDecimal.ROUND_DOWN);
				//兑换手续费入账(主账户)
				coinAccountService.updateCoin("888888", fee, IsPlusEnum.PLUS.getVal(), BeanRecordTypeEnum.BEAN_COIN_FEE.getVal());
				flag = coinAccountService.updateCoin(userId, coinNum, IsPlusEnum.PLUS.getVal(), BeanRecordTypeEnum.BEAN_IN_COIN.getVal());
			}
			
			//查询豆账户
			BeanAccount bean = getBeanById(userId);
			//查询币账户
			CoinAccount coin = coinAccountService.getCoinById(userId);
					
			beanM.put("userId", userId);
			beanM.put("lmCoinNum", null != coin ? coin.getLmCoinNum() : 0);
			beanM.put("lmBeanNum", null != bean ? bean.getLmBeanNum() : 0);
			beanM.put("isSuccess", flag);
		} finally {
			b.unlock();
		}
		return beanM;
	}
	
	/**
	 * @Title: updateBean 
	 * @Description: 更新龙猫豆/增加龙猫豆流水
	 * @param userId
	 * @param lmBeanNum
	 * @param changeType
	 * @param bizType
	 * @return boolean
	 */
	@Override
	@Transactional
	public boolean updateBean(String userId, BigDecimal lmBeanNum, Integer changeType, Integer bizType) {
		if (0 <= new BigDecimal("0.00").compareTo(lmBeanNum)) {
			GwsLogger.info("龙猫豆数量有误：lmBeanNum={}", lmBeanNum);
			return true;
		}
		//龙猫豆账户
		BeanAccount bean = getBeanById(userId);
		if (null == bean) {
			GwsLogger.error("userId龙猫豆账户BeanAccount不存在：userId={}", userId);
			throw new ImplException(CommConstant.GWSCOD0025, CommConstant.GWSMSG0025);
		} else {
			Integer flag = 0;
			//账户龙猫豆数量
			BigDecimal beanNum = new BigDecimal(bean.getLmBeanNum());
			//增加龙猫豆
			if (changeType.equals(IsPlusEnum.PLUS.getVal())) {
				flag = beanAccountRepositoryMaster.updateBeanAccount(lmBeanNum.doubleValue(), new Date(), userId, userId);
			//减少龙猫豆
			} else if (changeType.equals(IsPlusEnum.MINUS.getVal())) {
				//判断龙猫豆兑换数量是否超额
				if(0 <= beanNum.compareTo(lmBeanNum)){
					flag = beanAccountRepositoryMaster.updateBeanAccount(-lmBeanNum.doubleValue(), new Date(), userId, userId);
				}else{
					GwsLogger.info("userId龙猫豆余额不足：userId={},beanNum={},lmBeanNum={}", userId, beanNum, lmBeanNum);
					throw new ImplException(CommConstant.GWSCOD0026, CommConstant.GWSMSG0026);
				}
			}
			
			BeanChangeRecord record = null;
			if(Integer.valueOf("1").equals(flag)){
				BeanChangeRecord bcr = new BeanChangeRecord();
				final String id = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();

				bcr.setId(id);
				bcr.setAgoNum(beanNum.doubleValue());
				bcr.setUserId(userId);
				bcr.setChangeType(changeType);
				bcr.setChangeNum(lmBeanNum.doubleValue());
				bcr.setBizType(bizType);
				bcr.setOutBizId(bean.getId());
				bcr.setGmtCreate(new Date());
				bcr.setGmtModified(new Date());
				bcr.setGmtCreateUser("");
				bcr.setGmtModifiedUser("");
				record = beanChangeRecordRepositoryMaster.save(bcr);
			}
			return record == null ? false : true;
		}
	}

	/**
	 * 
	 * 【根据id查询龙猫豆】
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.opengroup.longmao.gwcommon.service.BeanAccountService#queryById(java.lang.String)
	 */
	@Override
	public BeanAccount queryById(String id) {
		if (StringUtils.isNotBlank(id)) {
			return beanAccountRepositoryMaster.queryById(id);
		}
		return null;
	}
	
	/**
	 * 
	 * 【根据userId查询龙猫豆】
	 * 
	 * @author Hermit 2017年4月13日
	 * @param userId
	 * @return
	 */
	@Override
	public BeanAccount getBeanByUserId(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			return beanAccountRepositoryMaster.queryBeanByUserId(userId);
		}
		return null;
	}
	
	/**
	 * @Title: getBeanById
	 * @Description: 根据id查询龙猫豆
	 * @param id
	 * @return BeanAccount
	 */
	@Override
	public BeanAccount getBeanById(String userId) {
		return beanAccountRepositoryMaster.queryBeanByUserId(userId);
	}
	
	/**
	 * 
	 * 【根据用户id增减龙猫豆】
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.opengroup.longmao.gwcommon.service.BeanAccountService#updateBeanAccount(java.lang.String,
	 *      double, java.lang.Integer)
	 */
	@Override
//	@Transactional
	public boolean updateBeanAccount(String userId, Double changeNum, Integer changeType, Integer bizType) {
		GwsLogger.info("增减用户龙猫豆,账户Start:userId={},changeNum={},changeType={},bizType={}", userId, changeNum,
				changeType, bizType);
		r.lock();
		BeanChangeRecord b = null;
		Integer flag = 0;
		try {
            //龙猫豆账户
			BeanAccount bean = getBeanByUserId(userId);
			if (null == bean) {
				GwsLogger.error("增减用户龙猫豆,龙猫豆账户(BeanAccount)不存在:userId={},BeanAccount={}", userId, bean);
				return false;
			}
			
			//增加龙猫豆
			if (IsPlusEnum.PLUS.getVal().equals(changeType)) {
				flag = beanAccountRepositoryMaster.updateBeanAccount(changeNum, new Date(), bean.getId());
				GwsLogger.info("增减用户龙猫豆,增加龙猫豆:userId={},lmBeanNum={},changeNum={},isUpdate={}", userId,
						bean.getLmBeanNum(), changeNum, flag);
			}
			//减少龙猫豆
			if (IsPlusEnum.MINUS.getVal().equals(changeType)) {
				if (bean.getLmBeanNum() >= changeNum) {
					flag = beanAccountRepositoryMaster.updateBeanAccount(-changeNum, new Date(), bean.getId());
					GwsLogger.info("增减用户龙猫豆,减少龙猫豆:userId={},lmBeanNum={},changeNum={},isUpdate={}", userId,
							bean.getLmBeanNum(), changeNum, flag);
				} else {
					GwsLogger.error("增减用户龙猫豆,龙猫豆余额不足:userId={},lmBeanNum={},changeNum={},isUpdate={}", userId,
							bean.getLmBeanNum(), changeNum, flag);
					return false;
				}
			}
			
			if (0 < flag) {
				BeanChangeRecord bcr = new BeanChangeRecord();
				bcr.setUserId(userId);
				bcr.setChangeNum(changeNum);
				bcr.setChangeType(changeType);
				bcr.setBizType(bizType);
				createBeanChangeRecord(bcr, bean.getLmBeanNum(), bean.getId());
				b = beanChangeRecordRepositoryMaster.save(bcr);
			} else {
				GwsLogger.error("增减用户龙猫豆,增减失败:userId={},isUpdate={}", userId, flag);
			}
		} finally {
			r.unlock();
		}
		GwsLogger.info("增减用户龙猫豆,账户End:userId={},isUpdate={}", userId, flag);
		return b == null ? false : true;
	}
	
//	@Override
////	@Transactional
//	public boolean updateBeanAccount(String userId, double changeNum, Integer changeType, Integer bizType) {
//		
//		r.lock();
//		BeanChangeRecord b =null;
//		BeanAccount beanAccount =new BeanAccount();
//		try {
//            //龙猫豆账户
//			beanAccount = beanAccountRepositorySlave.getBeanByUserId(userId);
//			if (null == beanAccount) {
//				GwsLogger.error("userId所在的龙猫豆账户BeanAccount不存在：userId={}",userId);
//				return false;
//			}
//			double lmBeanNum = beanAccount.getLmBeanNum();
//			GwsLogger.info("该用户的龙猫豆账户余额：userId={},lmBeanNum={}",userId,lmBeanNum);
//			//增加
//			if (changeType.equals(IsPlusEnum.PLUS.getVal())) {
//				beanAccount.setLmBeanNum(lmBeanNum + changeNum);
//				GwsLogger.info("增加龙猫豆相关信息：lmBeanNum={}",beanAccount.getLmBeanNum());
//			}
//			//减少
//			if (changeType.equals(IsPlusEnum.MINUS.getVal())) {
//				if(lmBeanNum >= changeNum){
//					beanAccount.setLmBeanNum(lmBeanNum - changeNum);
//					GwsLogger.info("减少龙猫豆相关信息：lmBeanNum={}",beanAccount.getLmBeanNum());
//				}else{
//					GwsLogger.error("userId所在的龙猫豆不足：userId={}，lmBeanNum={},changeNum={}",userId,lmBeanNum,changeNum);
//					return false;
//				}
//			}
//			beanAccount.setGmtModified(new Date());
//			BeanAccount ba = beanAccountRepositoryMaster.save(beanAccount);
//			//Integer ba = beanAccountRepositoryMaster.updateBeanAccount(beanAccount.getLmBeanNum(),beanAccount.getGmtModified(),beanAccount.getId());
//			if(ba!=null){
//				GwsLogger.info("修改龙猫豆相关信息：lmBeanNum={}",ba.getLmBeanNum());
//				BeanChangeRecord bcr = new BeanChangeRecord();
//				// ID
//				createBeanChangeRecord(userId, changeNum, changeType, bizType, lmBeanNum, ba.getId(), bcr);
//				b = beanChangeRecordRepositoryMaster.save(bcr);
//			}
//		} finally {
//			r.unlock();
//		}
//		return b == null?false:true;
//	}
	
	private void createBeanChangeRecord(BeanChangeRecord bcr, Double lmBeanNum, String beanAccountId) {
		GwsLogger.info("增减用户龙猫豆,流水Start:userId={}", bcr.getUserId());
		final String id = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();
		bcr.setId(id);
		bcr.setAgoNum(lmBeanNum);
		if (StringUtils.isNotEmpty(beanAccountId)) {
			bcr.setOutBizId(beanAccountId);
		} else {
			bcr.setOutBizId(id);
		}
		bcr.setGmtCreate(new Date());
		bcr.setGmtModified(new Date());
		bcr.setGmtCreateUser(bcr.getUserId());
		bcr.setGmtModifiedUser(bcr.getUserId());
		GwsLogger.info("增减用户龙猫豆,流水End:BeanChangeRecord={}", JSON.toJSONString(bcr));
	}
	
	/**
	 * 
	 * @Title: createBeanChangeRecord 
	 * @Description: 创建流水 
	 * @param userId
	 * @param changeNum
	 * @param changeType
	 * @param bizType
	 * @param lmBeanNum
	 * @param beanAccountId
	 * @param bcr    设定文件 
	 * void    返回类型 
	 * @author Yangst
	 * @throws
	 */
	private void createBeanChangeRecord(String userId, double changeNum, Integer changeType, Integer bizType,
			double lmBeanNum, String beanAccountId, BeanChangeRecord bcr) {
		GwsLogger.info("增减用户龙猫豆,流水Start:userId={},changeNum={},changeType={},bizType={}", userId, changeNum, changeType,
				bizType);
		final String id = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();
		bcr.setId(id);
		bcr.setAgoNum((double)lmBeanNum);
		bcr.setUserId(userId);
		bcr.setChangeType(changeType);
		bcr.setChangeNum((double)changeNum);
		bcr.setBizType(bizType);
		if(StringUtils.isNotEmpty(beanAccountId)){
			bcr.setOutBizId(beanAccountId);
		}else{
			bcr.setOutBizId(id);
		}
		bcr.setGmtCreate(new Date());
		bcr.setGmtModified(new Date());
		bcr.setGmtCreateUser("");
		bcr.setGmtModifiedUser("");
	}
	
	/**
	 * 
	 * 【批量增减用户龙猫豆】
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.service.BeanAccountService#updateBeanAccountBatch(java.util.List)
	 */
//	@Override
//	public boolean updateBeanAccountBatch(List<Map<String, Object>> beanAccountList) {
//		GwsLogger.info("开始批量增减用户龙猫豆");
//		boolean result=false;
//		int a=0;
//		for(Map<String, Object> map:beanAccountList){
//			GwsLogger.info("批量增减龙猫豆相关信息：userId={},changeNum={},changeType={},bizType={}",map.get("userId").toString(),Double.valueOf(map.get("changeNum").toString()),Integer.valueOf(map.get("changeType").toString()), Integer.valueOf(map.get("bizType").toString()));
//			boolean bool=updateBeanAccount(map.get("userId").toString(), Double.valueOf(map.get("changeNum").toString()), 
//					Integer.valueOf(map.get("changeType").toString()), Integer.valueOf(map.get("bizType").toString()));
//			if(bool){
//				a++;
//			}
//		}
//		if(a==beanAccountList.size()){
//			GwsLogger.info("批量增减用户龙猫豆成功");
//			result=true;
//		}
//		GwsLogger.info("结束批量增减用户龙猫豆");
//		return result;
//	}

	/**
	 * 
	 * 【批量增减用户龙猫豆】
	 * 
	 * (non-Javadoc)
	 * @see com.opengroup.longmao.gwcommon.service.BeanAccountService#updateBeanAccountBatch(java.util.List)
	 */
	@Override
	public boolean updateBeanAccountBatch(List<Map<String, Object>> beanAccountList) {
		
		boolean flg = false;
		
		//最终有效数据
		Map<String,Object> maplist=new HashMap<String,Object>();
		List<BeanChangeRecord> bcrList = new ArrayList<BeanChangeRecord>();
		
		GwsLogger.info("开始批量增减用户龙猫豆，接收到批量需要处理的beanAccountList:size={}",beanAccountList.size());
		
		//遍历list中的map
		for(Map<String, Object> map:beanAccountList){
			//从map中得到用户id
			String userId = map.get("userId").toString();
			GwsLogger.info("循环体数据:userId={}",userId);
			//maplist中的用户id为空
			if(null==maplist.get(userId)){
				//如果是增加
				if(Integer.valueOf(map.get("changeType").toString()).equals(IsPlusEnum.PLUS.getVal())){
					maplist.put(userId, Integer.parseInt(map.get("changeNum").toString()));
				}else{
					//如果是减少
					maplist.put(userId, -Integer.parseInt(map.get("changeNum").toString()));
				}
			//用户id不为空
			}else{
				//用户id键对应的值是押注金额
				int val = Integer.parseInt(maplist.get(userId).toString());
				//如果是增加
				if(Integer.valueOf(map.get("changeType").toString()).equals(IsPlusEnum.PLUS.getVal())){
					val += Integer.parseInt(map.get("changeNum").toString());
					maplist.put(userId, val);
				}else{
					//如果是减少
					val += (-Integer.parseInt(map.get("changeNum").toString()));
					maplist.put(userId, val);
				}
			}
			
			BeanChangeRecord bcr = new BeanChangeRecord();
			//参数封装，生成BeanChangeRecord流水
			createBeanChangeRecord(map.get("userId").toString(), Double.valueOf(map.get("changeNum").toString()), Integer.valueOf(map.get("changeType").toString()), Integer.valueOf(map.get("bizType").toString()), 0, "", bcr);
//			 业务ID
//			String id = idGlobalGenerator.getSeqId(BeanChangeRecord.class).toString();
//			bcr.setAgoNum(0d);
//			bcr.setUserId(map.get("userId").toString());
//			bcr.setChangeType(Integer.valueOf(map.get("changeType").toString()));
//			bcr.setChangeNum(Double.valueOf(map.get("changeNum").toString()));
//			bcr.setBizType(Integer.valueOf(map.get("bizType").toString()));
//			bcr.setOutBizId(id);
//			bcr.setGmtCreate(new Date());
//			bcr.setGmtModified(new Date());
//			bcr.setGmtCreateUser("");
//			bcr.setGmtModifiedUser("");
//			bcr.setId(id);
			bcrList.add(bcr);
		}
		
		GwsLogger.info("已成功过滤重复数据结果为maplist:size={}",maplist.size());
		
		List<BeanAccount> beanList = new ArrayList<BeanAccount>();
		Iterator<String> it = maplist.keySet().iterator();
		while(it.hasNext()){
			String uid = it.next();
			// 根据用户id查询龙猫豆账户
			BeanAccount beanAccount = getBeanByUserId(uid);
			if (null == beanAccount) {
				GwsLogger.error("userId所在的龙猫豆账户BeanAccount不存在：userId={}", uid);
				return false;
			}
			beanAccount.setGmtModified(new Date());
			beanAccount.setLmBeanNum(beanAccount.getLmBeanNum()+(int)maplist.get(uid));
			beanList.add(beanAccount);
		}
		GwsLogger.info("已将map成功转换成beanList:size={}",beanList.size());
		
		//批量更新龙猫豆账户
		List<BeanAccount> baList = beanAccountRepositoryMaster.save(beanList);
		GwsLogger.info("批量更新龙猫豆账户后的baList:size={}",baList.size());
		
		if(CollectionUtils.isNotEmpty(baList)&&(baList.size()==beanList.size())){
			
			//批量保存龙猫豆流水
			List<BeanChangeRecord> bList = beanChangeRecordRepositoryMaster.save(bcrList);
			GwsLogger.info("批量保存龙猫豆流水后的bList:size={}",bList.size());
			
			if(CollectionUtils.isNotEmpty(bList)&&(bList.size()==bcrList.size())){
				flg = true;
				GwsLogger.info("批量增减用户龙猫豆成功");
			}else{
				flg = false;
				GwsLogger.info("批量增减用户龙猫豆失败");
			}
		}else{
			flg = false;
			GwsLogger.info("批量增减用户龙猫豆失败");
		}
		GwsLogger.info("批量增减用户龙猫豆结束");
		return flg;
	}	

}
