package com.imzhitu.admin.statistics.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.StatisticsCountGroupByDay;
import com.imzhitu.admin.common.pojo.StatisticsSummary;

/**
 * <p>
 * 织图信息统计数据访问接口
 * </p>
 * 
 * @author tianjie
 *
 */
public interface WorldStatisticsDao extends BaseDao {

	/**
	 * 查询汇总信息
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<StatisticsSummary> querySummaryGroupByDate(Date beginDate, Date endDate);
	
	public void queryWorldCount(Date beginDate, Date endDate,
			RowCallback<StatisticsCountGroupByDay> callback);
	
	public void queryChildWorldCount(Date beginDate, Date endDate,
			RowCallback<StatisticsCountGroupByDay> callback);
	
	
}
