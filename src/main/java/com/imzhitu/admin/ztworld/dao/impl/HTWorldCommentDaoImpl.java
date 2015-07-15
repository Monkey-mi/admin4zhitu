package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;
import com.imzhitu.admin.ztworld.dao.HTWorldCommentDao;

/**
 * <p>
 * 织图评论数据访问对象
 * </p>
 * 
 * 创建时间：2013-8-9
 * @author ztj
 *
 */
@Repository
public class HTWorldCommentDaoImpl extends BaseDaoImpl implements
		HTWorldCommentDao {
	
	private static final String table = HTS.HTWORLD_COMMENT;
	
	private static final String ORDER_BY_HC_ID_DESC = " order by hc.id desc";
	
	/** 查询评论SQL头部 */
	private static final String QUERY_COMMENT_HEAD = "select hc.*," + U0_INFO + " from " 
			+ table + " as hc," + HTS.USER_INFO + " as u0"
			+" where hc.author_id=u0.id";
	
	/** 查询评论SQL尾部 */
	private static final String QUERY_COMMENT_FOOT = " and hc.valid=?";
	
	/** 根据最大id查询评论SQL尾部 */
	private static final String QUERY_COMMENT_BY_MAX_ID_FOOT = " and hc.valid=? and hc.id<=?";
	
	/** 根据最小id查询评论SQL尾部 */
	private static final String QUERY_COMMENT_BY_MIN_ID_FOOT = " and hc.valid=? and hc.id>?";
	
	/** 查询评论总数SQL头部 */
	private static final String QUERY_COMMENT_COUNT_HEADER = "select count(*) from " 
			+ table + " as hc," + HTS.USER_INFO + " as u0"
			+" where hc.author_id=u0.id";
	
	/** 查询评论总数SQL尾部 */
	private static final String QUERY_COMMENT_COUNT_FOOT = " and hc.valid=?";
	
	/** 根据最大id查询评论总数SQL尾部 */
	private static final String QUERY_COMMENT_COUNT_BY_MAX_ID_FOOT = " and hc.valid=? and hc.id<=?";
	
	/** 根据最小id查询评论总数SQL尾部 */
	private static final String QUERY_COMMENT_COUNT_BY_MIN_ID_FOOT = " and hc.valid=? and hc.id>?";
	
	/** 更新评论屏蔽标记 */
	private static final String UPDATE_COMMENT_SHIELD = "update " + table + " set shield=? where id=?";
	
	/** 更新评论有效性标记 */
	private static final String UPDATE_COMMENT_VALID = "update " + table + " set valid=? where id=?";
	
	/**
	 * 更新用户的所有评论的屏蔽标记
	 */
	private static final String UPDATE_COMMENT_SHIELD_BY_USER_ID = "update " + table 
			+ " set shield=? where author_id=?";
	
	private static final String QUERY_WID_BY_ID = "select world_id from " + table
			+ " where id=?";
	
	private static final String QUERY_WIDS_BY_AUTHOR_ID = "select world_id from " + table
			+ " where author_id=?";
	
	@Override
	public List<ZTWorldCommentDto> queryComment(Map<String, Object> attrMap, Map<String, Object> userAttrMap, RowSelection rowSelection) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_HEAD, QUERY_COMMENT_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(Tag.TRUE);
		return queryForPage(sql, argsList.toArray(), new RowMapper<ZTWorldCommentDto>() {

			@Override
			public ZTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildMaintaCommentDtoByResultSet(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<ZTWorldCommentDto> queryCommentByMaxId(Integer maxId, Map<String, Object> attrMap, Map<String, Object> userAttrMap, RowSelection rowSelection) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_HEAD, QUERY_COMMENT_BY_MAX_ID_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(Tag.TRUE);
		argsList.add(maxId);
		return queryForPage(sql, argsList.toArray(), new RowMapper<ZTWorldCommentDto>() {

			@Override
			public ZTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildMaintaCommentDtoByResultSet(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<ZTWorldCommentDto> queryCommentByMinId(Integer minId, Map<String, Object> attrMap, 
			Map<String, Object> userAttrMap, RowSelection rowSelection) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_HEAD, QUERY_COMMENT_BY_MIN_ID_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(Tag.TRUE);
		argsList.add(minId);
		return queryForPage(sql, argsList.toArray(), new RowMapper<ZTWorldCommentDto>() {

			@Override
			public ZTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildMaintaCommentDtoByResultSet(rs);
			}
		}, rowSelection);
	}
	
	
	@Override
	public long queryCommentCount(Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_COUNT_HEADER, QUERY_COMMENT_COUNT_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(Tag.TRUE);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}
	
	@Override
	public long queryCommentCountByMaxId(Integer maxId, Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_COUNT_HEADER, QUERY_COMMENT_COUNT_BY_MAX_ID_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(Tag.TRUE);
		argsList.add(maxId);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}
	
	@Override
	public long queryCommentCountByMinId(Integer minId, Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_COUNT_HEADER, QUERY_COMMENT_COUNT_BY_MIN_ID_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(minId);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}

	/**
	 * 构建评论查询条件
	 * 
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	private static String buildSelection(Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		for(int i = 0; i < keies.length; i++) {
			builder.append(" and hc.").append(keies[i]).append("=?");
		}
		Set<String> userKeySet = userAttrMap.keySet();
		Object[] userKeies = userKeySet.toArray();
		for(int i = 0; i < userKeies.length; i++) {
			builder.append(" and u0.").append(userKeies[i]).append("=?");
		}
		return builder.toString();
	}
	
	/**
	 * 构建评论查询SQL
	 * @param header
	 * @param foot
	 * @param attrMap
	 * @param userAttrMap
	 * @param orderKey
	 * @param orderBy
	 * @return
	 */
	private static String buildSelectWorldSQL(String header, String foot, Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		String selection = buildSelection(attrMap,userAttrMap);
		String sql = header + selection + foot + ORDER_BY_HC_ID_DESC;
		return sql;
	}
	
	
	@Override
	public void updateCommentShield(Integer id, Integer shield) {
		getMasterJdbcTemplate().update(UPDATE_COMMENT_SHIELD, new Object[]{shield, id});
	}
	
	@Override
	public void updateCommentShieldByUserId(Integer authorId,Integer shield){
		getMasterJdbcTemplate().update(UPDATE_COMMENT_SHIELD_BY_USER_ID, shield,authorId);
	}
	
	@Override
	public void updateCommentValid(Integer id, Integer valid) {
	    getMasterJdbcTemplate().update(UPDATE_COMMENT_VALID, new Object[]{valid, id});
	}
	
	@Override
	public Integer queryWorldId(Integer id) {
		return queryForObjectWithNULL(QUERY_WID_BY_ID, Integer.class, id);
	}

	@Override
	public List<Integer> queryWorldIds(Integer authorId) {
		return getJdbcTemplate().query(QUERY_WIDS_BY_AUTHOR_ID, new Object[]{authorId}, 
				new RowMapper<Integer>() {

					@Override
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("world_id");
					}
			
		});
	}
	
	
	/**
	 * 根据结果集构建MaintainCommentDto
	 * @param rs
	 * @throws SQLException 
	 */
	public ZTWorldCommentDto buildMaintaCommentDtoByResultSet(ResultSet rs) throws SQLException {
		ZTWorldCommentDto dto = new ZTWorldCommentDto(
				rs.getInt("id"), 
				rs.getInt("author_id"), 
				rs.getString("user_name"), 
				rs.getString("user_avatar"),
				rs.getString("content"), 
				(Date)rs.getObject("comment_date"),
				rs.getInt("world_id"), 
				rs.getInt("re_id"), 
				rs.getInt("re_author_id"),
				rs.getInt("ck"), 
				rs.getInt("valid"), 
				rs.getInt("shield"));
		if(dto.getAuthorId() != 0) {
			dto.setAuthorName(rs.getString("user_name"));
			dto.setAuthorAvatar(rs.getString("user_avatar"));
			dto.setStar(rs.getInt("star"));
			dto.setTrust(rs.getInt("trust"));
		}
		return dto;
	}


}
