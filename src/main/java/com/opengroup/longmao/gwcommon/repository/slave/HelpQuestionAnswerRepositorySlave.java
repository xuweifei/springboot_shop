package com.opengroup.longmao.gwcommon.repository.slave;

import org.springframework.data.jpa.repository.Query;

import com.opengroup.longmao.gwcommon.configuration.query.core.BaseRepository;
import com.opengroup.longmao.gwcommon.entity.po.HelpQuestionAnswer;

/**
 * 【帮助中心问答】 RepositorySlave接口
 *
 * @version 1.0
 * @author Hermit 2018年03月14日 下午16:47:36
 */ 
public interface HelpQuestionAnswerRepositorySlave extends BaseRepository<HelpQuestionAnswer, Long> {
	
	@Query("SELECT COUNT(h.questionId) FROM HelpQuestionAnswer h")
	Integer getHelpQuestionAnswerSize();

}

