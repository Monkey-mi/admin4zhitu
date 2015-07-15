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
import com.imzhitu.admin.common.pojo.InteractUserFollow;
import com.imzhitu.admin.interact.dao.InteractUserFollowDao;

/**
 * <p>
 * 用户互动加粉数据访问对象
 * </p>
 * 
 * 创建时间：2014-2-19
 * @author tianjie
 *
 */
@Repository
public class InteractUserFollowDaoImpl extends BaseDaoImpl implements
		InteractUserFollowDao {

	private static String table = Admin.INTERACT_USER_FOLLOW;
	
	/**
	 * 保存粉丝
	 */
	private static final String SAVE_FOLLOW = "insert into " + table 
			+ "(interact_id,user_id,follow_id,date_added,date_schedule,valid,finished)"
			+ " values (?,?,?,?,?,?,?)";
	
	/**
	 * 查询粉丝
	 */
	private static final String QUERY_FOLLOW = "select * from " + table
			+ " where interact_id=?";
	
	/**
	 * 根据最大id查询粉丝
	 */
	private static final String QUERY_FOLLOW_BY_MAX_ID = QUERY_FOLLOW + " and id<=?";
	
	/**
	 * 根据最大id查询粉丝数
	 */
	private static final String QUERY_FOLLOW_COUNT_BY_MAX_ID = "select count(*) from " + table
			+ " where interact_id=? and id<=?";
	
	/**
	 * 根据互动id删除粉丝
	 */
	private static final String DELETE_BY_INTERACT_IDS = "delete from " + table + " where interact_id in ";
	
	/**
	 * 查询未完成的粉丝
	 */
	private static final String QUERY_UN_FINISH_FOLLOW = "select * from " + table 
			+ " where valid=? and finished=? and date_schedule between ? and ? order by id asc";
	
	/**
	 * 更新未完成粉丝计划时间
	 */
	private static final String UPDATE_UN_FINISH_SCHEDULE = "update " + table 
			+ " SET date_schedule=DATE_ADD(date_schedule,INTERVAL 1 DAY) where date_schedule<? and finished=?";
	
	/**
	 * 更新未完成状态
	 */
	private static final String UPDATE_FINISHED = "update " + table + " set finished=? where id=?";
	
	
	@Override
	public void saveFollow(InteractUserFollow follow) {
		getMasterJdbcTemplate().update(SAVE_FOLLOW, new Object[]{
			follow.getInteractId(),
			follow.getUserId(),
			follow.getFollowId(),
			follow.getDateAdded(),
			follow.getDateSchedule(),
			follow.getValid(),
			follow.getFinished()
		});
	}
	
	@Override
	public List<InteractUserFollow> queryFollow(Integer interactId, RowSelection rowSelection) {
		return queryForPage(QUERY_FOLLOW, new Object[]{interactId}, new RowMapper<InteractUserFollow>() {

			@Override
			public InteractUserFollow mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildFollow(rs);
			}
		},rowSelection);
	}

	@Override
	public List<InteractUserFollow> queryFollow(Integer interactId, Integer maxId, 
			RowSelection rowSelection) {
		return queryForPage(QUERY_FOLLOW_BY_MAX_ID, new Object[]{interactId, maxId}, new RowMapper<InteractUserFollow>() {

			@Override
			public InteractUserFollow mapRow(ResultSet rs, int num) throws SQLException {
				return null;
			}
		}, rowSelection);
	}

	@Override
	public long queryFollowCount(Integer interactId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_FOLLOW_COUNT_BY_MAX_ID,
				new Object[]{interactId, maxId});
	}
	
	@Override
	public void deleteByInteractId(Integer[] interactIds) {
		String inSelection = SQLUtil.buildInSelection(interactIds);
		String sql = DELETE_BY_INTERACT_IDS + inSelection;
		getMasterJdbcTemplate().update(sql, (Object[])interactIds);
	}
	

	@Override
	public List<InteractUserFollow> queryUnFinishedFollow(Date startDate,
			Date endDate) {
		return getJdbcTemplate().query(QUERY_UN_FINISH_FOLLOW, 
				new Object[]{Tag.TRUE, Tag.FALSE, startDate, endDate},
				new RowMapper<InteractUserFollow>() {

			@Override
			public InteractUserFollow mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildFollow(rs);
			}
			
		});
	}
	
	@Override
	public void updateUnFinishedSchedule(Date now) {
		getMasterJdbcTemplate().update(UPDATE_UN_FINISH_SCHEDULE, new Object[]{now, Tag.FALSE});
	}
	
	@Override
	public void updateFinished(Integer id, Integer finished) {
		getMasterJdbcTemplate().update(UPDATE_FINISHED, new Object[]{finished, id});
	}
	
	@Override
	public InteractUserFollow buildFollow(ResultSet rs) throws SQLException {
		return new InteractUserFollow(
				rs.getInt("id"),
				rs.getInt("interact_id"),
				rs.getInt("user_id"),
				rs.getInt("follow_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_schedule"),
				rs.getInt("valid"),
				rs.getInt("finished"));
	}

}
