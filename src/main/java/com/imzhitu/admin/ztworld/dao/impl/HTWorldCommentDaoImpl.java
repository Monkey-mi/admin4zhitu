package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
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
	private static final String table_del = HTS.HTWORLD_COMMENT_DELETE;
	
	private static final String ORDER_BY_HC_ID_DESC = " order by hc.id desc";
	
	/** 查询评论SQL头部 */
	private static final String QUERY_COMMENT_HEAD = "select hc.*," + U0_INFO + " from " 
			+ table + " as hc," + HTS.USER_INFO + " as u0"
			+" where hc.author_id=u0.id";
	
	
	/** 根据最大id查询评论SQL尾部 */
	private static final String QUERY_COMMENT_BY_MAX_ID_FOOT = " and hc.id<=?";
	
	/** 根据最小id查询评论SQL尾部 */
	private static final String QUERY_COMMENT_BY_MIN_ID_FOOT = " and hc.id>?";
	
	/** 查询评论总数SQL头部 */
	private static final String QUERY_COMMENT_COUNT_HEADER = "select count(*) from " 
			+ table + " as hc," + HTS.USER_INFO + " as u0"
			+" where hc.author_id=u0.id";
	
	/** 根据最大id查询评论总数SQL尾部 */
	private static final String QUERY_COMMENT_COUNT_BY_MAX_ID_FOOT = " and hc.id<=?";
	
	/** 根据最小id查询评论总数SQL尾部 */
	private static final String QUERY_COMMENT_COUNT_BY_MIN_ID_FOOT = " and hc.id>?";
	
	/**
	 * 更新用户的所有评论的屏蔽标记
	 */
	private static final String UPDATE_COMMENT_SHIELD_BY_USER_ID = "update " + table 
			+ " set shield=? where author_id=?";
	
	private static final String QUERY_WID_BY_ID = "select world_id from " + table
			+ " where id=?";
	
	private static final String QUERY_WIDS_BY_AUTHOR_ID = "select world_id from " + table
			+ " where author_id=?";
	
	/**
	 * 从htworld_comment表中读取一条数据，插入htworld_comment_delete，然后在htworld_comment中删除该记录
	 * 用以替代评论或者删除操作
	 * @author zxx 2015年11月10日 20:10:41
	 */
	private static final String INSERT_COMMENT_DELETE_BY_ID = "INSERT INTO htworld_comment_delete(id,author_id,content,comment_date,world_id,world_author_id,re_author_id)"
			+ "SELECT id,author_id,content,comment_date,world_id,world_author_id,re_author_id FROM htworld_comment WHERE id=?";
	private static final String DELETE_COMMENT_BY_ID = "DELETE FROM htworld_comment where id=?";
	
	private static final String INSERT_COMMENT_DELETE_BY_USER_ID = "INSERT INTO htworld_comment_delete(id,author_id,content,comment_date,world_id,world_author_id,re_author_id)"
			+ "SELECT id,author_id,content,comment_date,world_id,world_author_id,re_author_id FROM htworld_comment WHERE author_id=?";
	private static final String DELETE_COMMENT_BY_USER_ID = "DELETE FROM htworld_comment where author_id=?";
	
	/**
	 * 因为从htworld_comment表删除了数据，所以想要恢复的话就得从htworld_comment_delete表中恢复
	 * @author zxx 2015年11月10日 20:47:50
	 */
	private static final String RECOVERY_COMMENT_BY_ID = "INSERT INTO htworld_comment(id,author_id,content,comment_date,world_id,world_author_id,re_author_id)"
			+ "SELECT id,author_id,content,comment_date,world_id,world_author_id,re_author_id FROM htworld_comment_delete WHERE id=?";
	private static final String DELETE_COMMENT_DELETE_BY_ID = "DELETE FROM htworld_comment_delete where id=?";
	
	private static final String RECOVERY_COMMENT_BY_USER_ID = "INSERT INTO htworld_comment(id,author_id,content,comment_date,world_id,world_author_id,re_author_id)"
			+ "SELECT id,author_id,content,comment_date,world_id,world_author_id,re_author_id FROM htworld_comment_delete WHERE author_id=?";
	private static final String DELETE_COMMENT_DELETE_BY_USER_ID = "DELETE FROM htworld_comment_delete where author_id=?";
	
	/**
	 * 将htworld_comment表中的数据同步到htworld_comment_week
	 */
	private static final String FILE_COMMENT_TO_WEEK	= "INSERT INTO htworld_comment_week(id,author_id,content,comment_date,world_id,world_author_id,re_author_id)"
			+ "SELECT id,author_id,content,comment_date,world_id,world_author_id,re_author_id FROM htworld_comment WHERE id>?";
	
	/**
	 * 查询htworld_comment_week最大Id
	 */
	private static final String QUERY_COMMENT_WEEK_MAX_ID = "SELECT MAX(id) FROM htworld_comment_week";
	
	/**
	 * 查询htworld_comment_week最小Id
	 */
	private static final String QUERY_COMMENT_WEEK_MIN_ID_BY_DATE = "SELECT MIN(id) FROM htworld_comment_week WHERE comment_date >=?";
	
	/**
	 * 查询htworld_comment最小id
	 */
	private static final String QUERY_COMMENT_MIN_ID  = "SELECT MIN(id) FROM htworld_comment WHERE comment_date >= ?";
	
	@Override
	public List<ZTWorldCommentDto> queryComment(Map<String, Object> attrMap, Map<String, Object> userAttrMap, RowSelection rowSelection) {
		/*
		 * 此方法目前在正式环境上被用到的，只有广告评论状态更改时，所以去掉了foot中valid的限制
		 * 以此可以获得只根据查询条件attrMap得到的结果集
		 * @ modify by zhangbo 2015-09-29
		 */
		String sql = buildSelectWorldSQL(QUERY_COMMENT_HEAD, "", attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
//		argsList.add(Tag.TRUE);
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
		String sql = buildSelectWorldSQL(QUERY_COMMENT_COUNT_HEADER, "", attrMap, userAttrMap);
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
	
	/**
	 * 删掉原来的update valid and shield
	 * @param authorId
	 * @author zxx
	 * @time 2015年11月10日 19:42:24
	 */
	@Override
	public void deleteCommentByUserId(Integer authorId){
		getMasterJdbcTemplate().update(INSERT_COMMENT_DELETE_BY_USER_ID,authorId);
		getMasterJdbcTemplate().update(DELETE_COMMENT_BY_USER_ID,authorId);
	}
	
	/**
	 * 删掉原来的update valid and shield
	 * @param authorId
	 * @author zxx
	 * @time 2015年11月10日 19:42:24
	 */
	@Override
	public void deleteCommentById(Integer id){
		getMasterJdbcTemplate().update(INSERT_COMMENT_DELETE_BY_ID,id);
		getMasterJdbcTemplate().update(DELETE_COMMENT_BY_ID,id);
	}
	
	/**
	 * 从htworld_comment_delete表中删除一条数据，并将这条数据恢复到htworld_comment表中
	 * @param id
	 * @author zxx 2015年11月10日 20:55:11
	 */
	@Override
	public void recoveryCommentById(Integer id){
		getMasterJdbcTemplate().update(RECOVERY_COMMENT_BY_ID, id);
		getMasterJdbcTemplate().update(DELETE_COMMENT_DELETE_BY_ID, id);
	}
	
	/**
	 * 从htworld_comment_delete表中删除符合条件的数据，并将这数据恢复到htworld_comment表中
	 * @author zxx 2015年11月10日 20:55:11
	 * @param authorId
	 */
	@Override
	public void recoverCommentByUserId(Integer authorId){
		getMasterJdbcTemplate().update(RECOVERY_COMMENT_BY_USER_ID, authorId);
		getMasterJdbcTemplate().update(DELETE_COMMENT_DELETE_BY_USER_ID, authorId);
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
	 * 将评论归档到最新一周的评论表中。即：将htworld_comment表中的最新的数据同步到htworld_comment_week 中去
	 * @param minId 最小Id，不包含这个id对应的数据
	 * @author zxx 2015年11月11日 18:21:12
	 */
	@Override
	public void fileCommentToWeek(Integer minId){
		getJdbcTemplate().update(FILE_COMMENT_TO_WEEK, minId);
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
				rs.getInt("world_author_id"),
				rs.getInt("re_id"), 
				rs.getInt("re_author_id"));
		if(dto.getAuthorId() != 0) {
			dto.setAuthorName(rs.getString("user_name"));
			dto.setAuthorAvatar(rs.getString("user_avatar"));
			dto.setStar(rs.getInt("star"));
			dto.setTrust(rs.getInt("trust"));
		}
		return dto;
	}

	/**
	 * 根据时间查询最小Id，目的就是初始化htworld_comment_week的时候，需要用到,用以同步数据
	 * @author zxx 2015年11月12日 10:04:21
	 */
	@Override
	public Integer queryCommentMinIdByDate(Date date) {
		try{
			return getJdbcTemplate().queryForInt(QUERY_COMMENT_MIN_ID, date);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	/**
	 * 查询htworld_comment_week的最大Id，目的是当redis宕机时候，重启时候能保证数据准确
	 * @author zxx 2015年11月12日 10:04:21
	 */
	@Override
	public Integer queryCommentWeekMaxId() {
		try{
			return getMasterJdbcTemplate().queryForInt(QUERY_COMMENT_WEEK_MAX_ID);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	/**
	 * 查询htworld_comment_week中最小的Id，目的是当redis宕机后，数据能确保正确
	 * @param date
	 * @return
	 * @author zxx 2015年11月12日 10:04:21
	 */
	@Override
	public Integer queryCommentWeekMinIdByDate(Date date) {
		try{
			return getJdbcTemplate().queryForInt(QUERY_COMMENT_WEEK_MIN_ID_BY_DATE, date);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}


}
