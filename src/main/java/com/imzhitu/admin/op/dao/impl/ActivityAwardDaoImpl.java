package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivityAward;
import com.imzhitu.admin.op.dao.ActivityAwardDao;

@Repository
public class ActivityAwardDaoImpl extends BaseDaoImpl implements
		ActivityAwardDao {

	private static final String table = HTS.OPERATIONS_ACTIVITY_AWARD;
	
	/**
	 * 保存奖品
	 */
	private static final String SAVE_AWARD = "insert into " + table 
			+ " (activity_id, icon_thumb_path, icon_path, award_name, award_desc,"
			+ " price, award_link, total, remain, serial) values "
			+ " (?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 更新奖品
	 */
	private static final String UPDATE_AWARD = "update " + table 
			+ " set activity_id=?,icon_thumb_path=?,icon_path=?,"
			+ "award_name=?,award_desc=?,price=?,award_link=?,total=?,"
			+ "remain=?,serial=? where id=?";
	
	/**
	 * 查询活动奖品公用SQL
	 */
	private static final String QUERY_AWARD_HEAD = "select * from " + table
			+ " where activity_id=?";
	
	/**
	 * 查询活动奖品
	 */
	private static final String QUERY_AWARD = QUERY_AWARD_HEAD + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询活动奖品
	 */
	private static final String QUERY_AWARD_BY_MAX_SERIAL = QUERY_AWARD_HEAD + " and serial<=?"
			+ ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询奖品总数
	 */
	private static final String QUERY_AWARD_COUNT_BY_MAX_SERIAL = "select count(*) from " + table
			+ " where activity_id=? and serial<=?";
	
	/**
	 * 更新序号
	 */
	private static final String UPDATE_SERIAL = "update " + table
			+ " set serial=? where id=?";
	
	/**
	 * 根据id查询奖品
	 */
	private static final String QUERY_AWARD_BY_ID = "select * from " + table
			+ " where id=?" + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据活动ids查询奖品
	 */
	private static final String QUERY_AWARD_BY_ACTIVITY_IDS = "select * from " + table
			+ " where activity_id in ";
	
	@Autowired
	private com.hts.web.operations.dao.ActivityAwardDao webActivityAwardDao;
	
	@Override
	public void saveAward(OpActivityAward award) {
		getJdbcTemplate().update(SAVE_AWARD, new Object[]{
				award.getActivityId(),
				award.getIconThumbPath(),
				award.getIconPath(),
				award.getAwardName(),
				award.getAwardDesc(),
				award.getPrice(),
				award.getAwardLink(),
				award.getTotal(),
				award.getRemain(),
				award.getSerial()
		});
	}

	@Override
	public void updateAward(OpActivityAward award) {
		getJdbcTemplate().update(UPDATE_AWARD, new Object[]{
				award.getActivityId(),
				award.getIconThumbPath(),
				award.getIconPath(),
				award.getAwardName(),
				award.getAwardDesc(),
				award.getPrice(),
				award.getAwardLink(),
				award.getTotal(),
				award.getRemain(),
				award.getSerial(),
				award.getId()
		});
	}

	@Override
	public void deleteAward(Integer[] ids) {
		deleteByIds(table, ids);
	}
	
	@Override
	public void updateSerial(Integer id, Integer serial) {
		getJdbcTemplate().update(UPDATE_SERIAL, new Object[]{serial, id});
	}
	
	@Override
	public List<OpActivityAward> queryAward(Integer activityId) {
		return getJdbcTemplate().query(QUERY_AWARD, new Object[]{activityId}, new RowMapper<OpActivityAward>() {

			@Override
			public OpActivityAward mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return webActivityAwardDao.buildAward(rs);
			}
			
		});
	}

	
	@Override
	public List<OpActivityAward> queryAward(Integer activityId, RowSelection rowSelection) {
		return queryForPage(QUERY_AWARD, new Object[]{activityId}, new RowMapper<OpActivityAward>() {

			@Override
			public OpActivityAward mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return webActivityAwardDao.buildAward(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<OpActivityAward> queryAward(Integer maxSerial, Integer activityId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_AWARD_BY_MAX_SERIAL, new Object[]{activityId, maxSerial}, 
				new RowMapper<OpActivityAward>() {

					@Override
					public OpActivityAward mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return webActivityAwardDao.buildAward(rs);
					}
		}, rowSelection);
	}

	@Override
	public long queryAwardCount(Integer maxSerial, Integer activityId) {
		return getJdbcTemplate().queryForLong(QUERY_AWARD_COUNT_BY_MAX_SERIAL, 
				new Object[]{activityId, maxSerial});
	}

	@Override
	public OpActivityAward queryAwardById(Integer id) {
		return queryForObjectWithNULL(QUERY_AWARD_BY_ID, new Object[]{id}, 
				new RowMapper<OpActivityAward>() {

					@Override
					public OpActivityAward mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return webActivityAwardDao.buildAward(rs);
					}
		});
	}

	@Override
	public void queryAwards(Integer[] activityIds,
			final RowCallback<OpActivityAward> callback) {
		String inSelection = SQLUtil.buildInSelection(activityIds);
		String sql = QUERY_AWARD_BY_ACTIVITY_IDS + inSelection + ORDER_BY_SERIAL_DESC;
		getJdbcTemplate().query(sql, (Object[])activityIds,
				new RowCallbackHandler() {
					
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						callback.callback(webActivityAwardDao.buildAward(rs));
					}
				});
	}

}
