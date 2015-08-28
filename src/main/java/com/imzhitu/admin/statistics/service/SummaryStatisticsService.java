package com.imzhitu.admin.statistics.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface SummaryStatisticsService extends BaseService {

	public void buildSummary(Date beginDate, Date endDate, 
			Map<String, Object> jsonMap) throws Exception;
	
	public void buidlSummary(Date maxDate, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建注册统计
	 * 
	 * @param date
	 * @param interval
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildRegisterSum(Date date, Integer interval,
			Map<String, Object> jsonMap) throws Exception;
}
