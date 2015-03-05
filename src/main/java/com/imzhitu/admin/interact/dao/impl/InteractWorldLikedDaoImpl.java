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
import com.imzhitu.admin.common.pojo.InteractWorldLiked;
import com.imzhitu.admin.interact.dao.InteractWorldLikedDao;

@Repository
public class InteractWorldLikedDaoImpl extends BaseDaoImpl implements
		InteractWorldLikedDao {
	
	public static String table = Admin.INTERACT_WORLD_LIKE;
	
	private static final String UPDATE_VALID_SCHTIME_BY_ID = "update "+table + " set valid=? , date_schedule=? where id=?";
	public static final String SAVE_LIKED = "insert into " + table 
			+ " (interact_id,world_id,user_id,date_added,date_schedule,valid,finished) values (?,?,?,?,?,?,?)";
	
	private static final String QUERY_LIKED_BY_WID_HEAD = "select * from " + table + " where interact_id=?";
	
	/**
	 * 根据worldid查询未完成的计划
	 */
	private static final String QUERY_UNDO_LIKED_BY_INTERACT_ID = QUERY_LIKED_BY_WID_HEAD +" and finished="+Tag.FALSE;
	
	private static final String QUERY_LIKED_BY_WID = QUERY_LIKED_BY_WID_HEAD + ORDER_BY_ID_DESC;
	
	private static final String QUERY_LIKED_BY_WID_AND_MAX_ID = QUERY_LIKED_BY_WID_HEAD + " and id<=?" + ORDER_BY_ID_DESC;
	
	private static final String QUERY_LIKED_TOTAL_BY_WID_HEAD = "select count(*) from " + table + " where interact_id=?";
	
	private static final String QUERY_LIKED_TOTAL_BY_WID_AND_MAX_ID = QUERY_LIKED_TOTAL_BY_WID_HEAD + " and id<=?";
	
	private static final String UPDATE_FINISHED = "update " + table + " set finished=? where id=?";
	
	private static final String UPDATE_VALID_BY_MAX_INTERACT_ID = "update " + table + " set valid=? where interact_id<=? and valid=?";
	
	private static final String DELETE_BY_INTERACT_IDS = "delete from " + table + " where interact_id in ";
	
	private static final String DELETE_BY_INTERACT_ID = "delete from " + table + " where interact_id=?";
	
	private static final String QUERY_UN_FINISHED_LIKED = "select * from " + table +" as wl,"
			+ " hts.htworld_htworld as hh "
			+ " where wl.world_id=hh.id and hh.valid=1 and wl.valid=? and wl.finished=? and wl.date_schedule between ? and ? order by wl.date_schedule asc";
	
	private static final String UPDATE_UN_FINISH_SCHEDULE = "update " + table 
			+ " SET date_schedule=DATE_ADD(date_schedule,INTERVAL 1 DAY) where date_schedule<? and finished=?";

	@Override
	public void saveLiked(InteractWorldLiked liked) {
		getJdbcTemplate().update(SAVE_LIKED, new Object[]{
			liked.getInteractId(),
			liked.getWorldId(),
			liked.getUserId(),
			liked.getDateAdded(),
			liked.getDateSchedule(),
			liked.getValid(),
			liked.getFinished()
		});
	}

	@Override
	public List<InteractWorldLiked> queryLiked(Integer interactId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_LIKED_BY_WID, new Object[]{interactId}, new RowMapper<InteractWorldLiked>(){

			@Override
			public InteractWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLikedByResultSet(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<InteractWorldLiked> queryLiked(Integer interactId, int maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_LIKED_BY_WID_AND_MAX_ID, new Object[]{interactId, maxId}, new RowMapper<InteractWorldLiked>(){

			@Override
			public InteractWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLikedByResultSet(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public long queryLikedTotal(Integer interactId) {
		return getJdbcTemplate().queryForLong(QUERY_LIKED_TOTAL_BY_WID_HEAD, new Object[]{interactId});
	}

	@Override
	public long queryLikedTotal(Integer interactId, int maxId) {
		return getJdbcTemplate().queryForLong(QUERY_LIKED_TOTAL_BY_WID_AND_MAX_ID, new Object[]{interactId, maxId});
	}
	
	@Override
	public void updateFinished(Integer id, Integer finished) {
		getJdbcTemplate().update(UPDATE_FINISHED, new Object[]{finished, id});
	}
	
	@Override
	public void updateValid(Integer maxInteractId, Integer valid) {
		getJdbcTemplate().update(UPDATE_VALID_BY_MAX_INTERACT_ID, new Object[]{valid, maxInteractId, 1-valid});
	}

	@Override
	public void deleteByInteractIds(Integer[] interactIds) {
		String sql = DELETE_BY_INTERACT_IDS + SQLUtil.buildInSelection(interactIds);
		getJdbcTemplate().update(sql, (Object[])interactIds);
	}
	
	@Override
	public void deleteByInteractId(Integer interactId) {
		getJdbcTemplate().update(DELETE_BY_INTERACT_ID, interactId);
	}
	
	@Override
	public List<InteractWorldLiked> queryUnfinishedLiked(Date startDate,
			Date currentDate) {
		return getJdbcTemplate().query(QUERY_UN_FINISHED_LIKED, new Object[]{Tag.TRUE, Tag.FALSE, startDate, currentDate}, 
				new RowMapper<InteractWorldLiked>() {

			@Override
			public InteractWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLikedByResultSet(rs);
			}
			
		});
	}
	
	@Override
	public void updateUnFinishedSchedule(Date now) {
		getJdbcTemplate().update(UPDATE_UN_FINISH_SCHEDULE, new Object[]{now, Tag.FALSE});
	}
	
	
	public InteractWorldLiked buildLikedByResultSet(ResultSet rs) throws SQLException {
		return new InteractWorldLiked(
				rs.getInt("id"),
				rs.getInt("interact_id"),
				rs.getInt("world_id"),
				rs.getInt("user_id"),
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
		getJdbcTemplate().update(UPDATE_VALID_SCHTIME_BY_ID, valid,date_schedule,id);
	}
	
	/**
	 * 根据interactId查询所有的互动喜欢计划
	 * @param interactId
	 * @return
	 */
	@Override
	public List<InteractWorldLiked> queryLikedByInteractId(Integer interactId){
		return getJdbcTemplate().query(QUERY_UNDO_LIKED_BY_INTERACT_ID, new Object[]{interactId}, new RowMapper<InteractWorldLiked>() {
			@Override
			public InteractWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLikedByResultSet(rs);
			}
			
		});
//		return getJdbcTemplate().queryForList(QUERY_UNDO_LIKED_BY_INTERACT_ID, InteractWorldLiked.class, interactId);
	}
	

}
