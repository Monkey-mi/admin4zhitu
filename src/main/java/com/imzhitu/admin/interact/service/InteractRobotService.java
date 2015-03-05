package com.imzhitu.admin.interact.service;

import com.hts.web.common.service.BaseService;

public interface InteractRobotService extends BaseService{
	public String getAnswer(String question)throws Exception;
	/**
	 * 从图灵机器人那里获取回复
	 * @param question
	 * @return
	 * @throws Exception
	 */
	public String getAnswerFromTuLing(String question)throws Exception;
}
