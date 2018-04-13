/**
 * @Title: HelpCenterServiceImpl.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.opengroup.longmao.gwcommon.entity.po.HelpQuestionAnswer;
import com.opengroup.longmao.gwcommon.entity.po.HelpType;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.HelpQuestionAnswerQueryFilter;
import com.opengroup.longmao.gwcommon.repository.queryFilter.HelpTypeQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.HelpQuestionAnswerRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.HelpTypeRepositorySlave;
import com.opengroup.longmao.gwcommon.service.HelpCenterService;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;

/**
 * @ClassName: HelpCenterServiceImpl 
 * @Description: TODO
 * @author Mr.Zhu
 */
@Service
public class HelpCenterServiceImpl implements HelpCenterService {
	
	@Autowired
	private HelpQuestionAnswerRepositorySlave helpQuestionAnswerRepositorySlave;

	@Autowired
	private HelpTypeRepositorySlave helpTypeRepositorySlave;
	
	@Autowired
	private RedisReadWriteService redis;
	
	/**
	 * @Title: findQuestionAnswer 
	 * @Description:  查询帮助中心问答-类型  
	 * @return List<Map<String, Object>>
	 */
	@Override
	public List<Map<String, Object>> findQuestionAnswer() {
		Map<String, Map<String, Object>> map = findHelpTypeByRedis();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		if (null != map && !map.isEmpty()) {
			List<Map.Entry<String, Map<String, Object>>> sortMap = new ArrayList<Map.Entry<String, Map<String, Object>>>(
					map.entrySet());
			Collections.sort(sortMap, new Comparator<Map.Entry<String, Map<String, Object>>>() {
				public int compare(Map.Entry<String, Map<String, Object>> arg0,
						Map.Entry<String, Map<String, Object>> arg1) {
					return Short.valueOf(arg0.getValue().get("typeRank").toString())
							.compareTo(Short.valueOf(arg1.getValue().get("typeRank").toString()));
				}
			});
			Sort sort = new Sort(Direction.DESC, "helpType");
			List<HelpQuestionAnswer> helpList = findHelpQuestionAnswer(sort, null, IsOrNotEnum.YES.getType());
			
			for (Map.Entry<String, Map<String, Object>> m : sortMap) {
				List<Map<String, Object>> questionList = new ArrayList<Map<String, Object>>();
				Map<String, Object> help = new HashMap<String, Object>();
				Map<String, Object> hM = m.getValue();
				help.put("typeId", hM.get("typeId"));
				help.put("typeName", hM.get("typeName"));
				help.put("typeIcon", hM.get("typeIcon"));
				help.put("typeRank", hM.get("typeRank"));
				if (CollectionUtils.isNotEmpty(helpList)) {
					Iterator<HelpQuestionAnswer> it = helpList.iterator();
					while(it.hasNext()){
						HelpQuestionAnswer h = it.next();
						if (m.getKey().equals(String.valueOf(h.getHelpType()))) {
							Map<String, Object> question = new HashMap<String, Object>();
							question.put("questionId", h.getQuestionId());
							question.put("questionLabel", h.getQuestionLabel());
							question.put("questionRank", h.getQuestionRank());
							questionList.add(question);
					        it.remove();
					        helpList.remove(h);
					    }
					}
				}
				help.put("question", questionList);
				mapList.add(help);
			}
		}
		return mapList;
	}
	
	/**
	 * @Title: findHelpQuestionAnswer 
	 * @Description: 查询帮助中心问答-内容 
	 * @param sort
	 * @param helpType
	 * @param isShow
	 * @return List<HelpQuestionAnswer>
	 */
	@Override
	public List<HelpQuestionAnswer> findHelpQuestionAnswer(Sort sort, Long helpType, Short isShow) {
		HelpQuestionAnswerQueryFilter query = new HelpQuestionAnswerQueryFilter();
		if (null != helpType && 0 < helpType) {
			query.setHelpType(helpType);
		}
		if (null != isShow && 0 < isShow) {
			query.setIsShow(isShow);
		}
		query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());

		List<HelpQuestionAnswer> helpList = helpQuestionAnswerRepositorySlave.findAll(query, sort);
		return helpList;
	}
	
	/**
	 * @Title: findHelpType 
	 * @Description: 根据类型ID查询帮助中心问答-类型 
	 * @param typeId
	 * @return HelpType
	 */
	@Override
	public HelpType findHelpType(Long typeId) {
		if (null != typeId) {
			return helpTypeRepositorySlave.findOne(typeId);
		}
		return null;
	}
	
	/**
	 * @Title: findHelpQuestionAnswer 
	 * @Description: 根据问题ID查询帮助中心问答-内容 
	 * @param questionId
	 * @return HelpQuestionAnswer
	 */
	@Override
	public HelpQuestionAnswer findHelpQuestionAnswer(Long questionId) {
		if (null != questionId) {
			return helpQuestionAnswerRepositorySlave.findOne(questionId);
		}
		return null;
	}
	
	/**
	 * @Title: findHelpType 
	 * @Description: 查询所有帮助中心问答-类型 
	 * @param pageNo
	 * @param pageSize
	 * @return Page<HelpType>
	 */
	@Override
	public Page<HelpType> findHelpType(Integer pageNo, Integer pageSize) {
		HelpTypeQueryFilter query = new HelpTypeQueryFilter();
		query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "typeRank"));
		orders.add(new Order(Direction.DESC, "utime"));
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(pageNo, pageSize, sort);
		
		Page<HelpType> helpList = helpTypeRepositorySlave.findAll(query, pageable);
		return helpList;
	}
	
	/**
	 * @Title: findHelpQuestionAnswer 
	 * @Description: 查询所有帮助中心问答-内容 
	 * @param isShow
	 * @param pageNo
	 * @param pageSize
	 * @return Page<HelpQuestionAnswer>
	 */
	@Override
	public Page<HelpQuestionAnswer> findHelpQuestionAnswer(Short isShow, Integer pageNo, Integer pageSize) {
		HelpQuestionAnswerQueryFilter query = new HelpQuestionAnswerQueryFilter();
		if (null != isShow) {
			query.setIsShow(isShow);
		}
		query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.DESC, "questionRank"));
		orders.add(new Order(Direction.DESC, "utime"));
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(pageNo, pageSize, sort);
		
		Page<HelpQuestionAnswer> helpList = helpQuestionAnswerRepositorySlave.findAll(query, pageable);
		return helpList;
	}
	
	/**
	 * @Title: setHelpTypeToRedis 
	 * @Description: 帮助中心类型写入Redis 
	 * @param help
	 * @return void
	 */
	private void setHelpTypeToRedis(HelpType help) {
		if (null != help) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", help.getTypeId());
			map.put("typeName", help.getTypeName());
			map.put("typeIcon", help.getTypeIcon());
			map.put("typeRank", help.getTypeRank());
			redis.hashSet(ConfigConstant.QUESTION_ANSWER_TYPE, String.valueOf(help.getTypeId()), map);
		}
	}
	
	private Map<String, Map<String, Object>> findHelpTypeByRedis() {
		Map<String, Map<String, Object>> map = redis.hashGet(ConfigConstant.QUESTION_ANSWER_TYPE);
		if (null == map || map.isEmpty()) {
			HelpTypeQueryFilter query = new HelpTypeQueryFilter();
			query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			List<HelpType> helpList = helpTypeRepositorySlave.findAll(query);
			if (CollectionUtils.isNotEmpty(helpList)) {
				for (HelpType h : helpList) {
					setHelpTypeToRedis(h);
				}
				map = redis.hashGet(ConfigConstant.QUESTION_ANSWER_TYPE);
			}
		}
		return map;
	}
	
}
