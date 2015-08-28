package com.imzhitu.admin.statistics.dao;

import java.util.Date;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.StatisticsCountGroupByDay;
import com.imzhitu.admin.common.pojo.StatisticsSummary;

/**
 * <p>
 * 用户信息统计数据访问接口
 * </p>
 * 
 * @author tianjie
 *
 */
public interface UserStatisticsDao extends BaseDao {

	public void queryRegisterCountGroupByDate(Date beginDate, Date endDate, 
			RowCallback<StatisticsSummary> callback);
	
	public void queryRegisterCount(Date beginDate, Date endDate,
			RowCallback<StatisticsCountGroupByDay> callback);
	
	
}
