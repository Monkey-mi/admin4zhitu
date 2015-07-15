package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractWorldClick;
import com.imzhitu.admin.interact.dao.InteractWorldClickDao;

@Repository
public class InteractWorldClickDaoImpl extends BaseDaoImpl implements
		InteractWorldClickDao {

	private static String table = Admin.INTERACT_WORLD_CLICK;
	
	private static final String UPDATE_VALID_SCHTIME_BY_ID = "update "+table + " set valid=? , date_schedule=? where id=?";

	private static final String SAVE_CLICK = "insert into " + table
			+ " (interact_id,world_id,click,date_added,date_schedule,valid,finished) values (?,?,?,?,?,?,?)";
	private static final String BATCH_SAVE_CLICK_HEAD = "insert into " + table
			+ " (interact_id,world_id,click,date_added,date_schedule,valid,finished)";
	
	private static final String QUERY_CLICK_BY_ID_HEAD = "select * from " + table + " where interact_id=?";
	
	private static final String QUERY_UNDO_CLICK_BY_INTERACT_ID = QUERY_CLICK_BY_ID_HEAD+" and finished="+Tag.FALSE;
	
	private static final String QUERY_CLICK_BY_ID = QUERY_CLICK_BY_ID_HEAD + ORDER_BY_ID_DESC;
	
	private static final String QUERY_CLICK_BY_ID_AND_MAX_ID = QUERY_CLICK_BY_ID_HEAD + " and id<=?" + ORDER_BY_ID_DESC;
	
	private static final String QUERY_CLICK_TOTAL_BY_WID_HEAD = "select count(*) from " + table + " where interact_id=?";
	
	private static final String QUERY_CLICK_TOTAL_BY_WID_AND_MAX_ID = QUERY_CLICK_TOTAL_BY_WID_HEAD + " and id<=?";
	
	private static final String QUERY_UN_FINISHED_CLICK = "select * from " + table +"  wc left join "
			+ " hts.htworld_htworld  hh "
			+ " on wc.world_id=hh.id where hh.valid=1 and wc.valid=? and wc.finished=? and wc.date_schedule between ? and ? order by wc.date_schedule asc";
	
	private static final String UPDATE_FINISHED = "update " + table + " set finished=? where id=?";

	private static final String UPDATE_VALID_BY_MAX_INTERACT_ID = "update " + table + " set valid=? where interact_id<=? and valid=?";
	
	private static final String DELETE_BY_INTERACT_IDS = "delete from " + table + " where interact_id in ";
	
	private static final String DELETE_BY_INTERACT_ID = "delete from " + table + " where interact_id=?";
	
	private static final String UPDATE_UN_FINISH_SCHEDULE = "update " + table 
			+ " SET date_schedule=DATE_ADD(date_schedule,INTERVAL 1 DAY) where date_schedule<? and finished=?";
	
	@Override
	public void saveClick(InteractWorldClick click) {
		getMasterJdbcTemplate().update(SAVE_CLICK, new Object[]{
			click.getInteractId(),
			click.getWorldId(),
			click.getClick(),
			click.getDateAdded(),
			click.getDateSchedule(),
			click.getValid(),
			click.getFinished()
		});
	}

	@Override
	public List<InteractWorldClick> queryClick(Integer interactId, RowSelection rowSelection) {
		return queryForPage(QUERY_CLICK_BY_ID, new Object[]{interactId}, new RowMapper<InteractWorldClick>(){

			@Override
			public InteractWorldClick mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildClickByResult(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<InteractWorldClick> queryClick(Integer interactId, int maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_CLICK_BY_ID_AND_MAX_ID, new Object[]{interactId, maxId}, new RowMapper<InteractWorldClick>(){

			@Override
			public InteractWorldClick mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildClickByResult(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public long queryClickTotal(Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_CLICK_TOTAL_BY_WID_HEAD, worldId);
	}

	@Override
	public long queryClickTotal(Integer worldId, int maxId) {
		return getJdbcTemplate().queryForLong(QUERY_CLICK_TOTAL_BY_WID_AND_MAX_ID, new Object[]{worldId, maxId});
	}
	
	@Override
	public List<InteractWorldClick> queryUnFinishedClick(Date startDate, Date endDate) {
		return getJdbcTemplate().query(QUERY_UN_FINISHED_CLICK, new Object[]{Tag.TRUE, Tag.FALSE, startDate, endDate},
				new RowMapper<InteractWorldClick>() {

			@Override
			public InteractWorldClick mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildClickByResult(rs);
			}
			
		});
	}
	
	@Override
	public void updateFinished(Integer id, Integer finished) {
		getMasterJdbcTemplate().update(UPDATE_FINISHED, new Object[]{finished, id});
	}
	
	@Override
	public void updateValid(Integer maxInteractId, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_VALID_BY_MAX_INTERACT_ID, new Object[]{valid, maxInteractId, 1-valid});
	}
	
	@Override
	public void deleteByInteractIds(Integer[] interactIds) {
		String sql = DELETE_BY_INTERACT_IDS + SQLUtil.buildInSelection(interactIds);
		getMasterJdbcTemplate().update(sql, (Object[])interactIds);
	}
	
	@Override
	public void deleteByInteractId(Integer interactId) {
		getMasterJdbcTemplate().update(DELETE_BY_INTERACT_ID, interactId);
	}
	
	@Override
	public void updateUnFinishedSchedule(Date now) {
		getMasterJdbcTemplate().update(UPDATE_UN_FINISH_SCHEDULE, new Object[]{now, Tag.FALSE});
	}
	
	public InteractWorldClick buildClickByResult(ResultSet rs) throws SQLException {
		return new InteractWorldClick(
				rs.getInt("id"),
				rs.getInt("interact_id"),
				rs.getInt("world_id"),
				rs.getInt("click"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_schedule"),
				rs.getInt("valid"),
				rs.getInt("finished"));
	}
	
	/**
	 * 更新计划时间和有效性
	 * @param valid
	 * @param date_schedule
	 */
	@Override
	public void updateValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id){
		getMasterJdbcTemplate().update(UPDATE_VALID_SCHTIME_BY_ID, valid,date_schedule,id);
	}
	
	/**
	 * 根据interactId查询所有的播放计划
	 * @param interactId
	 * @return
	 */
	@Override
	public List<InteractWorldClick> queryClickbyInteractId(Integer interactId){
		return getJdbcTemplate().query(QUERY_UNDO_CLICK_BY_INTERACT_ID, new Object[]{interactId}, new RowMapper<InteractWorldClick>(){

			@Override
			public InteractWorldClick mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildClickByResult(rs);
			}
			
		});
//		return getJdbcTemplate().queryForList(QUERY_UNDO_CLICK_BY_INTERACT_ID, InteractWorldClick.class, interactId);
//		return getJdbcTemplate().query(QUERY_UNDO_CLICK_BY_INTERACT_ID, interactId, rowMapper)
	}
	


}
