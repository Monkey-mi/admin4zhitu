package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractWorldComment;
import com.imzhitu.admin.common.pojo.InteractWorldCommentDto;
import com.imzhitu.admin.interact.dao.InteractWorldCommentDao;

@Repository
public class InteractWorldCommentDaoImpl extends BaseDaoImpl implements
		InteractWorldCommentDao {

	public static String table = Admin.INTERACT_WORLD_COMMENT;
	
	private static final String QUERY_COMMENT_BY_INTERACTID = "select * from "+ table + " where interact_id=?";
	
	private static final String QUERY_UNDO_COMMENT_BY_INTERACT_ID = "select wc.*,wc.valid as comment_valid  from "+ table + " as wc where interact_id=?" + " and finished="+Tag.FALSE;
	
	private static final String UPDATE_VALID_SCHTIME_BY_ID = "update "+table + " set valid=? , date_schedule=? where id=?";
	
	private static final String ORDER_BY_IC_ID_DESC = " order by ic.id desc";
	
	public static final String SAVE_COMMENT = "insert into " + table 
			+ " (interact_id,world_id,user_id,comment_id,date_added,date_schedule,valid,finished) values (?,?,?,?,?,?,?,?)";
	
	private static final String QUERY_COMMENT_BY_WID_HEAD = "select ic.*, wc.valid as comment_valid, wc.content from " + table + " as ic, " 
			+ Admin.INTERACT_COMMENT + " as wc where ic.comment_id=wc.id and ic.interact_id=?";
	
	private static final String QUERY_COMMENT_BY_WID = QUERY_COMMENT_BY_WID_HEAD + ORDER_BY_IC_ID_DESC;
	
	private static final String QUERY_COMMENT_BY_WID_AND_MAX_ID = QUERY_COMMENT_BY_WID_HEAD + " and ic.id<=?" + ORDER_BY_IC_ID_DESC;
	
	private static final String QUERY_COMMENT_TOTAL_BY_WID_HEAD = "select count(*) from " + table + " as ic, " 
			+ Admin.INTERACT_COMMENT + " as wc where ic.comment_id=wc.id and ic.interact_id=?";
	
	private static final String QUERY_COMMENT_TOTAL_BY_WID_AND_MAX_ID = QUERY_COMMENT_TOTAL_BY_WID_HEAD + " and ic.id<=?";
	
	private static final String QUERY_UN_FINISHED_COMMENT = "select ic.*, wc.valid as comment_valid, wc.content from " + table + " as ic, " 
			+ Admin.INTERACT_COMMENT + " as wc, "
			+ " hts.htworld_htworld as hh where ic.world_id=hh.id and hh.valid=1 and ic.comment_id=wc.id and wc.valid=? and ic.valid=? and ic.finished=? and ic.date_schedule between ? and ? order by ic.date_schedule asc";
	
	private static final String UPDATE_FINISHED = "update " + table + " set finished=? where id=?";
	
	private static final String UPDATE_VALID_BY_MAX_INTERACT_ID = "update " + table + " set valid=? where interact_id<=? and valid=?";
	
	private static final String DELETE_BY_INTERACT_IDS = "delete from " + table + " where interact_id in ";
	
	private static final String DELETE_BY_INTERACT_ID  = "delete from " + table + " where interact_id=?";
	
	private static final String UPDATE_UN_FINISH_SCHEDULE = "update " + table 
			+ " SET date_schedule=DATE_ADD(date_schedule,INTERVAL 1 DAY) where date_schedule<? and finished=?";
	
	private static final String DELETE_BY_IDS =  "delete from " + table + " where id in";
	
	private static final String UPDATE_COMMENT_ID_BY_ID = "update " + table + " set comment_id=? where id=?";

	@Override
	public void saveComment(InteractWorldComment comment) {
		getMasterJdbcTemplate().update(SAVE_COMMENT, new Object[]{
			comment.getInteractId(),
			comment.getWorldId(),
			comment.getUserId(),
			comment.getCommentId(),
			comment.getDateAdded(),
			comment.getDateSchedule(),
			comment.getValid(),
			comment.getFinished()
		});
	}

	@Override
	public List<InteractWorldCommentDto> queryComment(Integer interact,
			RowSelection rowSelection) {
		return queryForPage(QUERY_COMMENT_BY_WID, new Object[]{interact}, new RowMapper<InteractWorldCommentDto>(){

			@Override
			public InteractWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildCommentDtoByResult(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<InteractWorldCommentDto> queryComment(Integer interactId, int maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_COMMENT_BY_WID_AND_MAX_ID, new Object[]{interactId, maxId}, new RowMapper<InteractWorldCommentDto>(){

			@Override
			public InteractWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildCommentDtoByResult(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public long queryCommentTotal(Integer interactId) {
		return getJdbcTemplate().queryForLong(QUERY_COMMENT_TOTAL_BY_WID_HEAD, new Object[]{interactId});
	}

	@Override
	public long queryCommentTotal(Integer interactId, int maxId) {
		return getJdbcTemplate().queryForLong(QUERY_COMMENT_TOTAL_BY_WID_AND_MAX_ID, new Object[]{interactId, maxId});
	}
	
	@Override
	public List<InteractWorldCommentDto> queryUnFinishedComment(Date startDate,
			Date endDate) {
		return getJdbcTemplate().query(QUERY_UN_FINISHED_COMMENT, new Object[]{Tag.TRUE, Tag.TRUE, Tag.FALSE, startDate, endDate}, new RowMapper<InteractWorldCommentDto>(){

			@Override
			public InteractWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildCommentDtoByResult(rs);
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
	
	
	public InteractWorldCommentDto buildCommentDtoByResult(ResultSet rs) throws SQLException {
		return new InteractWorldCommentDto(
				rs.getInt("id"),
				rs.getInt("interact_id"),
				rs.getInt("world_id"),
				rs.getInt("user_id"),
				rs.getInt("comment_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_schedule"),
				rs.getInt("valid"),
				rs.getInt("finished"),
				rs.getInt("comment_valid"),
				rs.getString("content"));
	}
	
	/**
	 * 构建存interact_world_comment表数据
	 */
	public InteractWorldCommentDto buildCommentDateByResult(ResultSet rs) throws SQLException {
		return new InteractWorldCommentDto(
				rs.getInt("id"),
				rs.getInt("interact_id"),
				rs.getInt("world_id"),
				rs.getInt("user_id"),
				rs.getInt("comment_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_schedule"),
				rs.getInt("valid"),
				rs.getInt("finished"),
				rs.getInt("comment_valid"),
				null);
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
	 * 根据interactId查询所有的评论互动计划
	 * @param interactId
	 * @return
	 */
	@Override 
	public List<InteractWorldCommentDto> queryCommentByInteractId(Integer interactId){
		return getJdbcTemplate().query(QUERY_UNDO_COMMENT_BY_INTERACT_ID, new Object[]{interactId}, new RowMapper<InteractWorldCommentDto>(){

			@Override
			public InteractWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildCommentDateByResult(rs);
			}
			
		});
	}  
	
	/**
	 * 删除评论计划by ids
	 */
	@Override
	public void deleteInteractCommentByids(Integer[] ids){
		String sql = DELETE_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	/**
	 * 更新评论id by id
	 */
	@Override
	public void updateCommentIdById(Integer id,Integer commentId){
		getMasterJdbcTemplate().update(UPDATE_COMMENT_ID_BY_ID, commentId,id);
	}

}
