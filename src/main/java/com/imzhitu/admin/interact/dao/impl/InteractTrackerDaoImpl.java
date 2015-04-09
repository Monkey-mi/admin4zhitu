package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractTracker;
import com.imzhitu.admin.interact.dao.InteractTrackerDao;

@Repository
public class InteractTrackerDaoImpl extends BaseDaoImpl implements InteractTrackerDao {

	private static String table = Admin.INTERACT_TRACKER;
	
	/**
	 * 保存互动跟踪
	 */
	private static final String SAVE_TRACKER = "insert into " + table 
			+ " (interact_desc,interact_step,interact_begin,interact_stop,"
			+ "last_interact_date,last_track_date, valid) values (?,?,?,?,?,?,?)";
	
	/**
	 * 更新最后互动时间
	 */
	private static final String UPDATE_LAST_INTERACT_DATE = "update " + table 
			+ " set last_interact_date=? where id=?";
	
	/**
	 * 更新最后跟踪时间
	 */
	private static final String UPDATE_LAST_TRACK_DATE = "update " + table 
			+ " set last_track_date=?";
	
	/**
	 * 更新有效性
	 */
	private static final String UPDATE_VALID = "update " + table
			+ " set valid=?";
	
	/**
	 * 查询互动跟踪
	 */
	private static final String QUERY_TRACKER = "select * from " + table;
	
	/**
	 * 根据id查询跟踪器
	 * 
	 */
	private static final String QUERY_TRACKER_BY_ID = QUERY_TRACKER + " where id=?";
	
	
	@Override
	public void saveTrack(InteractTracker tracker) {
		getMasterJdbcTemplate().update(SAVE_TRACKER, new Object[]{
			tracker.getInteractDesc(),
			tracker.getInteractStep(),
			tracker.getInteractBegin(),
			tracker.getInteractStop(),
			tracker.getLastInteractDate(),
			tracker.getLastTrackDate(),
			tracker.getValid()
		});
	}


	@Override
	public void updateLastInteractDate(Integer id, Date lastDate) {
		getMasterJdbcTemplate().update(UPDATE_LAST_INTERACT_DATE, new Object[]{lastDate,id});
	}


	@Override
	public void updateLastTrackDate(Date lastDate) {
		getMasterJdbcTemplate().update(UPDATE_LAST_TRACK_DATE, new Object[]{lastDate});
	}


	@Override
	public void updateTrackValid(Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_VALID, new Object[]{valid});
	}


	@Override
	public List<InteractTracker> queryTracker() {
		return getJdbcTemplate().query(QUERY_TRACKER, new Object[]{}, 
				new RowMapper<InteractTracker>(){

					@Override
					public InteractTracker mapRow(ResultSet rs, int num)
							throws SQLException {
						return buildTracker(rs);
					}
			
		});
	}
	
	public InteractTracker buildTracker(ResultSet rs) throws SQLException {
		return new InteractTracker(
				rs.getInt("id"), 
				rs.getString("interact_desc"), 
				rs.getInt("interact_step"), 
				rs.getInt("interact_begin"),
				rs.getInt("interact_stop"), 
				(Date)rs.getObject("last_interact_date"), 
				(Date)rs.getObject("last_track_date"), 
				rs.getInt("valid"));
		
	}

}
