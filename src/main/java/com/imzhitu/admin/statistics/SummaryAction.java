package com.imzhitu.admin.statistics;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.statistics.service.SummaryStatisticsService;

public class SummaryAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768671241344633911L;

	private Date maxDate;
	private Date beginDate;
	private Date endDate;
	private Integer interval;
	
	@Autowired
	private SummaryStatisticsService summaryStatisticsService;

	public SummaryStatisticsService getSummaryStatisticsService() {
		return summaryStatisticsService;
	}

	public void setSummaryStatisticsService(
			SummaryStatisticsService summaryStatisticsService) {
		this.summaryStatisticsService = summaryStatisticsService;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	/**
	 * 查询汇总信息
	 * 
	 * @return
	 */
	public String querySummary() {
		try {
			//summaryStatisticsService.buildSummary(beginDate, endDate, jsonMap);
			summaryStatisticsService.buidlSummary(maxDate, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询注册汇总信息
	 * 
	 * @return
	 */
	public String queryRegisterSum() {
		try {
			summaryStatisticsService.buildRegisterSum(beginDate, interval, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
}
