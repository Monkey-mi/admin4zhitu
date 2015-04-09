package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractUser;
import com.imzhitu.admin.interact.dao.InteractUserDao;

/**
 * <p>
 * 用户互动数据访问对象
 * </p>
 * 
 * 创建时间：2014-2-19
 * @author tianjie
 *
 */
@Repository
public class InteractUserDaoImpl extends BaseDaoImpl implements InteractUserDao {

	private static String table = Admin.INTERACT_USER;

	/**
	 * 保存互动
	 */
	private static final String SAVE_INTERACT = "insert into " + table 
			+ " (id,user_id,follow_count,duration,date_added,valid) values (?,?,?,?,?,?)";
	
	/**
	 * 查询用户互动公用头部
	 */
	private static final String QUERY_INTERACT_HEAD = "select * from " + table;
	
	/**
	 * 查询用户互动
	 */
	private static final String QUERY_INTERACT = QUERY_INTERACT_HEAD + ORDER_BY_ID_DESC;
	
	/**
	 * 根据最大id查询用户互动
	 */
	private static final String QUERY_INTERACT_BY_MAX_ID = QUERY_INTERACT_HEAD 
			+ " where id<=?" + ORDER_BY_ID_DESC;
	
	/**
	 * 根据最大id查询用户互动总数
	 */
	private static final String QUERY_INTERACT_COUNT_BY_MAX_ID = "select count(*) from " + table 
			+ " where id<=?";
	
	/**
	 * 根据用户id查询互动
	 */
	private static final String QUERY_INTERACT_BY_USER_ID = "select * from " + table 
			+ " where user_id=?";
	
	/**
	 * 更新用户互动
	 */
	private static final String UPDATE_INTERACT = "update " + table 
			+ " set follow_count=?, duration=? where id=?";
	
	/**
	 * 根据id删除互动
	 */
	private static final String DELETE_BY_IDS = "delete from " + table + " where id in ";
	
	/**
	 * 根据用户id查询互动
	 */
	private static final String QUERY_INTERACT_BY_UID = "select * from " + table + " where user_id=?";
	
	/**
	 * 根据用户ids查询互动用户id列表
	 */
	private static final String QUERY_UID_BY_UIDS = "select user_id from " + table + " where user_id in";
	
	
	@Override
	public void saveInteract(InteractUser interact) {
		getMasterJdbcTemplate().update(SAVE_INTERACT, new Object[]{
			interact.getId(),
			interact.getUserId(),
			interact.getFollowCount(),
			interact.getDuration(),
			interact.getDateAdded(),
			interact.getValid()
		});
	}

	@Override
	public List<InteractUser> queryInteract(RowSelection rowSelection) {
		return queryForPage(QUERY_INTERACT, new RowMapper<InteractUser>() {

			@Override
			public InteractUser mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildInteract(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<InteractUser> queryInteract(Integer maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_INTERACT_BY_MAX_ID, new Object[]{maxId},
				new RowMapper<InteractUser>() {

			@Override
			public InteractUser mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildInteract(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public long queryInteractCount(Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_INTERACT_COUNT_BY_MAX_ID, new Object[]{maxId});
	}
	
	@Override
	public InteractUser queryUserInteractByUID(Integer userId) {
		return queryForObjectWithNULL(QUERY_INTERACT_BY_USER_ID, new Object[]{userId}, 
				new RowMapper<InteractUser>() {

					@Override
					public InteractUser mapRow(ResultSet rs, int num)
							throws SQLException {
						return buildInteract(rs);
					}
		});
	}
	
	@Override
	public void updateInteract(Integer interactId, Integer followCount, Integer duration) {
		getMasterJdbcTemplate().update(UPDATE_INTERACT, new Object[]{followCount, duration, interactId});
	}
	
	@Override
	public void deleteByIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = DELETE_BY_IDS + inSelection;
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	@Override
	public InteractUser queryInteractByUID(Integer userId) {
		return queryForObjectWithNULL(QUERY_INTERACT_BY_UID, new Object[]{userId}, 
				new RowMapper<InteractUser>() {

					@Override
					public InteractUser mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return buildInteract(rs);
					}
		});
	}
	
	@Override
	public void queryUserIdByUIDS(Integer[] userIds,
			final RowCallback<Integer> callback) {
		String inSelection = SQLUtil.buildInSelection(userIds);
		String sql = QUERY_UID_BY_UIDS + inSelection;
		getJdbcTemplate().query(sql, (Object[])userIds, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("user_id"));
			}
		});
	}

	
	@Override
	public InteractUser buildInteract(ResultSet rs) throws SQLException {
		return new InteractUser(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("follow_count"),
				rs.getInt("duration"), 
				(Date)rs.getObject("date_added"), 
				rs.getInt("valid"));
	}


}
