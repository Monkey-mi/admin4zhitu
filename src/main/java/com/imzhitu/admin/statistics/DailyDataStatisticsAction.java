/**
 * 
 */
package com.imzhitu.admin.statistics;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpDataStatisticsDto;
import com.imzhitu.admin.constant.LoggerKeies;
import com.imzhitu.admin.statistics.service.DailyDataStatisticsService;

/**
 * 每日频道相关数据统计action类
 * 
 * @author zhangbo 2015年6月25日
 */
public class DailyDataStatisticsAction  extends BaseCRUDAction {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5153352264836626486L;
	
	private static Logger log = Logger.getLogger(LoggerKeies.DAILY_DATA_STATISTICS);
	
	private Integer channelId;	// 频道ID
	private String channelName;	// 频道名称
	private Date startTime;		// 查询起始日期
	private Date endTime;		// 查询结束日期
	private Integer themeId;	// 频道主题ID 
	
	/**
	 * @return the channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the themeId
	 */
	public Integer getThemeId() {
		return themeId;
	}

	/**
	 * @param themeId the themeId to set
	 */
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	@Autowired
	private DailyDataStatisticsService dailyService;
	
	/**
	 * 查询每日频道相关数据统计
	 * @return
	 */
	public String queryDailyData() {
		try {
			OpDataStatisticsDto dto = new OpDataStatisticsDto();
			dto.setChannelId(getChannelId());
			dto.setChannelName(getChannelName());
			dto.setStartTime(getStartTime());
			dto.setEndTime(getEndTime());
			dto.setSortKey(getSort());	// 设置排序关键字
			dto.setOrder(getOrder());	// 设置正序/倒序
			dto.setThemeId(getThemeId());

			dailyService.queryDailyData(dto, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}
}
