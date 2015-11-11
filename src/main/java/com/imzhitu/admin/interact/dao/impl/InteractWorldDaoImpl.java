package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractWorld;
import com.imzhitu.admin.interact.dao.InteractWorldDao;

@Repository
public class InteractWorldDaoImpl extends BaseDaoImpl implements
		InteractWorldDao {
	
	public static String table = Admin.INTERACT_WORLD;
	
	/**
	 * 根据worldId列表来更新有效状态
	 */
	private static final String UPDATE_VALID_TRUE_BY_WORLDID = "update "+table +" set valid=" + Tag.TRUE+" where world_id in ";
	private static final String UPDATE_VALID_FALSE_BY_WORLDID = "update "+table +" set valid=" + Tag.FALSE+" where world_id in ";
	
	/**
	 * 保存互动
	 */
	private static final String SAVE_INTERACT = "insert into " + table 
			+ " (id,world_id,click_count,comment_count,liked_count,duration,date_added,valid)"
			+ " values (?,?,?,?,?,?,?,?)";
	
	/**
	 * 根据织图id查询互动
	 */
	private static final String QUERY_INTERACT_BY_WID = "select * from " 
			+ table + " where world_id=?";
	
	/**
	 * 更新互动
	 */
	private static final String UPDATE_INTERACT = "update " + table 
			+ " set click_count=?,comment_count=?,liked_count=?,duration=? where id=?";
	
	
	/**
	 * 查询互动列表
	 */
	private static final String QUERY_INTERACT = "select * from " + table;
	
	/**
	 * 查询互动总数
	 */
	private static final String QUERY_INTERACT_TOTAL = "select count(*) from " + table;
	
	/**
	 * 根据最大id更新有效标记
	 */
	private static final String UPDATE_VALID_BY_MAX_ID = "update " + table 
			+ " set valid=? where id<=? and valid=?";
	
	/**
	 * 批量删除互动
	 */
	private static final String DELETE_INTERACT = "delete from " + table + " where id in";
	
	/**
	 * 根据wids查询wid
	 */
	private static final String QUERY_WORLD_ID_BY_WORLD_IDS = "select DISTINCT world_id from " 
			+ table + " where world_id in ";
	
	/**
	 * 根据id删除互动
	 */
	private static final String DELETE_BY_ID = "delete from " + table + " where id=?";
	/**
	 * 根据织图id来查询互动id
	 */
	private static final String QUERY_INTERACT_ID_BY_WORLD_ID = " select id from " + table + " where world_id=?";
	
	@Override
	public void saveInteract(InteractWorld interact) {
		getMasterJdbcTemplate().update(SAVE_INTERACT, new Object[]{
			interact.getId(),
			interact.getWorldId(),
			interact.getClickCount(),
			interact.getCommentCount(),
			interact.getLikedCount(),
			interact.getDuration(),
			interact.getDateAdded(),
			interact.getValid()
		});
	}

	@Override
	public InteractWorld queryInteractByWorldId(Integer worldId) throws Exception{
		try {
			return getMasterJdbcTemplate().queryForObject(QUERY_INTERACT_BY_WID, new Object[]{worldId}, new RowMapper<InteractWorld>() {

				@Override
				public InteractWorld mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildInteract(rs);
				}
				
			});
		} catch(EmptyResultDataAccessException e) {
			return null; // 没有查询记录
		}
		
	}
	
	@Override
	public void updateInteract(Integer id, Integer clickCount,
			Integer commentCount, Integer likedCount, Integer duration) {
		getMasterJdbcTemplate().update(UPDATE_INTERACT, new Object[]{
			clickCount, commentCount, likedCount, duration, id
		});
	}
	

	@Override
	public List<InteractWorld> queryInteract(RowSelection rowSelection, Map<String, Object> attrMap) {
		StringBuilder sqlBuilder = new StringBuilder(QUERY_INTERACT);
		sqlBuilder.append(SQLUtil.buildSelection(attrMap)).append(ORDER_BY_ID_DESC);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		return queryForPage(sqlBuilder.toString(), argsList.toArray(), new RowMapper<InteractWorld>(){

			@Override
			public InteractWorld mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildInteract(rs);
			}
			
		},rowSelection);
	}

	
	@Override
	public List<InteractWorld> queryInteract(int maxId, RowSelection rowSelection, Map<String, Object> attrMap) {
		StringBuilder sqlBuilder = new StringBuilder(QUERY_INTERACT);
		sqlBuilder.append(SQLUtil.buildSelection(attrMap));
		if(attrMap.size() > 0) {
			sqlBuilder.append(" and id<=?");
		} else {
			sqlBuilder.append(" where id<=?");
		}
		sqlBuilder.append(ORDER_BY_ID_DESC);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxId);
		return queryForPage(sqlBuilder.toString(), argsList.toArray(), new RowMapper<InteractWorld>(){

			@Override
			public InteractWorld mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildInteract(rs);
			}
			
		},rowSelection);
	}

	@Override
	public long queryInteractTotal(int maxId, Map<String, Object> attrMap) {
		StringBuilder sqlBuilder = new StringBuilder(QUERY_INTERACT_TOTAL);
		sqlBuilder.append(SQLUtil.buildSelection(attrMap));
		if(attrMap.size() > 0) {
			sqlBuilder.append(" and id<=?");
		} else {
			sqlBuilder.append(" where id<=?");
		}
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxId);
		return getJdbcTemplate().queryForLong(sqlBuilder.toString(), argsList.toArray());
	}
	
	@Override
	public void updateValid(Integer maxId, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_VALID_BY_MAX_ID, new Object[]{valid, maxId, 1-valid});
	}
	
	@Override
	public void deleteInteract(Integer[] ids) {
		String sql = DELETE_INTERACT + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}

	@Override
	public List<Integer> queryWorldIdByWIDs(Integer[] worldIds) {
		String sql = QUERY_WORLD_ID_BY_WORLD_IDS + SQLUtil.buildInSelection(worldIds);
		try {
			return getJdbcTemplate().queryForList(sql, Integer.class, (Object[])worldIds);
		} catch(DataAccessException e) {
			return null; // 没有查询记录
		}
	}
	
	@Override
	public void deleteInteract(Integer id) {
		getMasterJdbcTemplate().update(DELETE_BY_ID, id);
	}
	
	@Override
	public void queryWorldIdByWIDs(Integer[] wids,
			final RowCallback<Integer> callback) {
		String sql = QUERY_WORLD_ID_BY_WORLD_IDS + SQLUtil.buildInSelection(wids);
		getJdbcTemplate().query(sql, (Object[])wids, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("world_id"));
			}
		});
		
	}
	
	/**
	 * 构建织图互动信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public InteractWorld buildInteract(ResultSet rs) throws SQLException {
		return new InteractWorld(
				rs.getInt("id"),
				rs.getInt("world_id"),
				rs.getInt("click_count"),
				rs.getInt("comment_count"),
				rs.getInt("liked_count"),
				rs.getInt("duration"),
				(Date)rs.getObject("date_added"),
				rs.getInt("valid"));
	}
	
	/**
	 * 根据worldID列表来更新worldID对应的有效状态
	 * @param wids
	 * @param valid
	 */
	@Override
	public void upInteractValidByWIDs(Integer[] wids,Integer valid)
	{
		String head;
		if(valid==Tag.TRUE){
			head=UPDATE_VALID_TRUE_BY_WORLDID;
		}else{
			head=UPDATE_VALID_FALSE_BY_WORLDID;
		}
		String sql = head + SQLUtil.buildInSelection(wids);
		getMasterJdbcTemplate().update(sql,(Object[])wids);
	}
	
	/**
	 * 根据织图id查询互动id
	 */
	@Override
	public Integer queryIntegerIdByWorldId(Integer wId){
		try{
			return getMasterJdbcTemplate().queryForObject(QUERY_INTERACT_ID_BY_WORLD_ID, new Object[]{wId}, Integer.class);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
}
