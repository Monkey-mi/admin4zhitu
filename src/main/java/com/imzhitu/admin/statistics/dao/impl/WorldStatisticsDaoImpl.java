package com.imzhitu.admin.statistics.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.StatisticsCountGroupByDay;
import com.imzhitu.admin.common.pojo.StatisticsSummary;
import com.imzhitu.admin.statistics.dao.WorldStatisticsDao;

@Repository
public class WorldStatisticsDaoImpl extends BaseDaoImpl implements
		WorldStatisticsDao {

	private static final String QUERY_WORLD_COUNT_GROUP_BY_DATE =
			"select count(*) world_count, SUM(child_count) child_count,"
			+ "DATE_FORMAT(date_added, '%Y-%m-%d') d from " 
			+ HTS.HTWORLD_HTWORLD + " where date_added between ? and ? group by d "
			+ " order by date_added desc";
	
	private static final String QUERY_WORLD_COUNT = "select date_format(date_added, '%Y-%m-%d') d, count(*) c from "
			+ HTS.HTWORLD_HTWORLD + " where date_added between ? and ?"
			+ " group by date_format(date_added, '%Y-%m-%d') order by id desc";
	
	private static final String QUERY_CHILD_WORLD_COUNT = "select date_format(h.date_added, '%Y-%m-%d') d,"
			+ " count(*) c from " + HTS.HTWORLD_HTWORLD + " h," + HTS.HTWORLD_CHILD_WORLD + " hc"
			+ " where h.id=hc.world_id and h.date_added between ? and ? "
			+ "group by date_format(h.date_added, '%Y-%m-%d') order by h.id desc";
	
	
	
	
	@Override
	public List<StatisticsSummary> querySummaryGroupByDate(Date beginDate, Date endDate) {
		return getJdbcTemplate().query(QUERY_WORLD_COUNT_GROUP_BY_DATE, new Object[]{beginDate, endDate}, 
				new RowMapper<StatisticsSummary>(){

			@Override
			public StatisticsSummary mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				StatisticsSummary s = new StatisticsSummary();
				s.setDate(rs.getString("d"));
				s.setWorldCount(rs.getLong("world_count"));
				s.setChildCount(rs.getLong("child_count"));
				return s;
			}
		}); 
	}

	@Override
	public void queryWorldCount(Date beginDate, Date endDate,
			final RowCallback<StatisticsCountGroupByDay> callback) {
		getJdbcTemplate().query(QUERY_WORLD_COUNT, 
				new Object[]{beginDate, endDate},new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildStatisticsCountGroupByDay(rs));
			}
		});
	}

	@Override
	public void queryChildWorldCount(Date beginDate, Date endDate,
			final RowCallback<StatisticsCountGroupByDay> callback) {
		getJdbcTemplate().query(QUERY_CHILD_WORLD_COUNT, 
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
