package com.imzhitu.admin.statistics.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.StatisticsCountGroupByDay;
import com.imzhitu.admin.common.pojo.StatisticsSummary;
import com.imzhitu.admin.statistics.dao.UserStatisticsDao;

@Repository
public class UserStatisticsDaoImpl extends BaseDaoImpl implements
		UserStatisticsDao {

	private static final String QUERY_REGISTER_COUNT_GROUP_BY_DATE =
			"select count(*) register_count,DATE_FORMAT(register_date, '%Y-%m-%d') d from " 
			+ HTS.USER_INFO + " where register_date between ? and ? group by d";
	
	private static final String QUERY_REGISTER_COUNT = "select date_format(register_date, '%Y-%m-%d') d,"
			+ " count(*) c from " + HTS.USER_INFO + " where register_date between ? and ?"
			+ " group by date_format(register_date, '%Y-%m-%d') order by id desc";
	
	@Override
	public void queryRegisterCountGroupByDate(Date beginDate, Date endDate,
			final RowCallback<StatisticsSummary> callback) {
		getJdbcTemplate().query(QUERY_REGISTER_COUNT_GROUP_BY_DATE, new Object[]{beginDate, endDate}, 
				new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						StatisticsSummary s = new StatisticsSummary();
						s.setDate(rs.getString("d"));
						s.setUserCount(rs.getLong("register_count"));
						callback.callback(s);
					}
			
		});
	}
	
	@Override
	public void queryRegisterCount(Date beginDate, Date endDate,
			final RowCallback<StatisticsCountGroupByDay> callback) {
		getJdbcTemplate().query(QUERY_REGISTER_COUNT,
				new Object[]{beginDate, endDate},new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						callback.callback(buildStatisticsCountGroupByDay(rs));
					}
			
		});
	}
	
	public StatisticsCountGroupByDay buildStatisticsCountGroupByDay(ResultSet rs) throws SQLException {
		return new StatisticsCountGroupByDay(
				rs.getString("d"),
				rs.getLong("c"));
	}

}
