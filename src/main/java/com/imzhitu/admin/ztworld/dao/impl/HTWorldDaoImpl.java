package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.userinfo.dao.UserInfoDao;
import com.imzhitu.admin.ztworld.dao.HTWorldDao;

/**
 * <p>
 * 汇图世界数据访问对象
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
@Repository
public class HTWorldDaoImpl extends BaseDaoImpl implements HTWorldDao{
	
	
	private static String table = HTS.HTWORLD_HTWORLD;
	
	/** 
	 * 添加播放次数
	 */
	private static final String ADD_CLICK_COUNT = "update " + table + " set click_count=click_count+? where id=? and valid=?";
	/** 
	 * 减少播放次数 
	 */
	private static final String REDUCE_CLICK_COUNT = "update " + table + " set click_count=click_count-? where id=? and valid=?";
	
	/**
	 * 添加喜欢次数 
	 */
	private static final String ADD_LIKED_COUNT = "update " + table + " set like_count=like_count+? where id=? and valid=?";
	
	/**
	 * 减少喜欢次数 
	 */
	private static final String REDUCE_LIKED_COUNT = "update " + table + " set like_count=like_count-? where id=? and valid=?";

	/** 
	 * 添加评论次数 
	 */
	private static final String ADD_COMMENT_COUNT = "update " + table + " set comment_count=comment_count+? where id=? and valid=?";
	
	/** 
	 * 减少评论次数 
	 */
	private static final String REDUCE_COMMENT_COUNT = "update " + table + " set comment_count=comment_count-? where id=? and valid=?";

	/** 
	 * 添加收藏次数
	 */
	private static final String ADD_KEEP_COUNT = "update " + table + " set keep_count=keep_count+? where id=? and valid=?";
	
	/** 
	 * 减少收藏次数 
	 */
	private static final String REDUCE_KEEP_COUNT = "update " + table + " set keep_count=keep_count-? where id=? and valid=?";
	
	/** 
	 * 更新评论次数
	 */
	private static final String UPDATE_COMMENT_COUNT = "update " + table + " set comment_count=? where id=? and valid=?";
	
	/** 
	 * 更新收藏次数 
	 */
	private static final String UPDATE_KEEP_COUNT = "update " + table + " set keep_count=? where id=? and valid=?";

	/** 
	 * 查询织图头部
	 */
	private static final String QUERY_WORLD_HEAD = "select h.*, 1-ISNULL(hq.world_id) as squarerecd from"
			+ " (select h2.*," + U0_INFO + ",u0.ver app_ver,u0.phone_sys,u0.phone_ver from " + table + " as h2," + HTS.USER_INFO + " as u0"
			+ " where h2.author_id=u0.id and h2.date_modified<=?";
	
	/** 
	 * 查询织图尾部 
	 */
	private static final String QUERY_WORLD_FOOT = " limit ?,?) as h left join " 
			+ HTS.HTWORLD_TYPE_WORLD + " as hq"
			+ " on h.id = hq.world_id and hq.recommender_id != 0";
	
	/** 
	 * 查询织图总数头部 
	 */
	private static final String QUERY_WORLD_COUNT_BY_MAX_ID_HEAD = "select count(*) from " + table + " as h2," 
			+ HTS.USER_INFO + " as u0 where h2.author_id=u0.id and h2.date_modified<=?";
	
	
	/** 
	 * 根据最大id查询织图总数
	 */
	private static final String QUERY_WORLD_COUNT_BY_MAX_ID_FOOT = " and h2.id<=?";

	/**
	 * 日期区间SQL
	 */
	private static final String BETWEEN_DATE_MODIFIED = " and DATE_FORMAT(h2.date_modified,'%Y-%m-%d') between ? and ? ";
	
	private static final String QUERY_MAX_ID_HEAD_BETWEEN_DATE_MODIFIED = "select max(id) from " + table 
			+ " where DATE_FORMAT(date_modified,'%Y-%m-%d') between ? and ? ";
	
	/**
	 * 更新屏蔽标记
	 */
	private static final String UPDATE_WORLD_SHIELD = "update " + table + " set shield=? where id=?";
	
	/**
	 * 根据ids删除织图
	 */
	private static final String DELETE_WORLD_BY_IDS = "delete from " + table + " where id in ";
	
	/**
	 * 更新织图分类
	 */
	private static final String UPDATE_WORLD_TYPE_LABEL = "update " + table 
			+ " set type_id=?, world_type=? where id=?";
	
	/**
	 * 根据织图id查询作者信息
	 */
	private static final String QUERY_USER_INFO_BY_WORLD_ID = "select u.* from " + table + " as h," + HTS.USER_INFO + " as u"
			+ " where h.author_id=u.id and h.id=?";
	
	/**
	 * 更新最新有效性
	 */
	private static final String UPDATE_WORLD_LATEST_VALID = "update " + table + " set latest_valid=?, date_added=? where id=?";
	
	@Autowired
	private UserInfoDao userInfoDao;
	

	@Override
	public int addClickCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(ADD_CLICK_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int reduceClickCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(REDUCE_CLICK_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int addLikedCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(ADD_LIKED_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int reduceLikedCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(REDUCE_LIKED_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int addKeepCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(ADD_KEEP_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int reduceKeepCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(REDUCE_KEEP_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int addCommentCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(ADD_COMMENT_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int reduceCommentCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(REDUCE_COMMENT_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}
	
	@Override
	public int updateCommentCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(UPDATE_COMMENT_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	@Override
	public int updateKeepCount(Integer worldId, Integer count) {
		return getMasterJdbcTemplate().update(UPDATE_KEEP_COUNT, new Object[]{count, worldId, Tag.TRUE});
	}

	
	
	private static String buildSelection(Map<String, Object> attrMap, Map<String, Object> userAttrMap, boolean hasDateCondition) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		for(int i = 0; i < keies.length; i++) {
			builder.append(" and h2.").append(keies[i]).append("=?");
		}
		Set<String> userKeySet = userAttrMap.keySet();
		Object[] userKeies = userKeySet.toArray();
		for(int i = 0; i < userKeies.length; i++) {
			builder.append(" and u0.").append(userKeies[i]).append("=?");
		}
		if(hasDateCondition) {
			builder.append(BETWEEN_DATE_MODIFIED);
		}
		return builder.toString();
	}
	
	private static String buildSelectWorldSQL(boolean hasDateCondition, String header, String middle, 
			String foot, Map<String, Object> attrMap, Map<String, Object> userAttrMap, String orderKey, String orderBy) {
		String selection = buildSelection(attrMap,userAttrMap, hasDateCondition);
		String sql = header + selection + middle + " order by h2." + orderKey + " " + orderBy + foot;
		return sql;
	}
	
	@Override
	public List<ZTWorldDto> queryHTWorldByAttrMap(Date currentDate, String startDateStr,
			String endDateStr, Map<String, Object> attrMap, Map<String, Object> userAttrMap, String orderKey, 
			String orderBy, RowSelection rowSelection) {
		boolean hasDateCondition = false;
		if(startDateStr != null || endDateStr != null) {
			hasDateCondition = true;
		}
		
		// 设置活动过滤条件
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(currentDate);
		String sqlHead = QUERY_WORLD_HEAD;
		String sql = buildSelectWorldSQL(hasDateCondition, sqlHead, "", QUERY_WORLD_FOOT, 
				attrMap,userAttrMap, orderKey, orderBy);
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		if(hasDateCondition) {
			argsList.add(startDateStr);
			argsList.add(endDateStr);
		}
		argsList.add(rowSelection.getFirstRow());
		argsList.add(rowSelection.getLimit());
		Object[] args = argsList.toArray();
		return getJdbcTemplate().query(sql, args, new RowMapper<ZTWorldDto>() {

			@Override
			public ZTWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildZTWorldDto(rs);
			}
		});
	}
	
	@Override
	public List<ZTWorldDto> queryHTWorldByAttrMapByMaxId(Date currentDate, final Integer maxId, String startDateStr,
			String endDateStr, Map<String, Object> attrMap, Map<String, Object> userAttrMap, String orderKey, 
			String orderBy, RowSelection rowSelection) {
		boolean hasDateCondition = false;
		if(startDateStr != null || endDateStr != null) {
			hasDateCondition = true;
		}
		
		// 设置活动过滤条件
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(currentDate);
		String sqlHead = QUERY_WORLD_HEAD;
		String sql = buildSelectWorldSQL(hasDateCondition, sqlHead, " and h2.id<=? ", QUERY_WORLD_FOOT , 
				attrMap, userAttrMap, orderKey, orderBy);
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		if(hasDateCondition) {
			argsList.add(startDateStr);
			argsList.add(endDateStr);
		}
		argsList.add(maxId);
		argsList.add(rowSelection.getFirstRow());
		argsList.add(rowSelection.getLimit());
		Object[] args = argsList.toArray();
		return getJdbcTemplate().query(sql, args, new RowMapper<ZTWorldDto>() {

			@Override
			public ZTWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildZTWorldDto(rs);
			}
		});
	}
	
	@Override
	public long queryHTWorldCountByMaxId(Date currentDate, Integer maxId, String startDateStr, String endDateStr, 
			Map<String, Object> attrMap,  Map<String, Object> userAttrMap) {
		boolean hasDateCondition = false;
		if(startDateStr != null || endDateStr != null) {
			hasDateCondition = true;
		}
		List<Object> argsList = new ArrayList<Object>();
		String sqlHead = QUERY_WORLD_COUNT_BY_MAX_ID_HEAD;
		argsList.add(currentDate);
		String selection = buildSelection(attrMap, userAttrMap, hasDateCondition);
		String sql = sqlHead + selection + QUERY_WORLD_COUNT_BY_MAX_ID_FOOT;
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		if(hasDateCondition) {
			argsList.add(startDateStr);
			argsList.add(endDateStr);
		}
		argsList.add(maxId);
		Object[] args = argsList.toArray();
		return getJdbcTemplate().queryForLong(sql, args);
	}

	@Override
	public void updateWorldShield(Integer worldId, Integer shield) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_SHIELD, new Object[]{shield, worldId});
	}
	
	
	@Override
	public void updateWorld(Integer worldId, Map<String, Object> attrMap) {
		String sql = SQLUtil.buildUpdateSQL(table, attrMap, "id=?");
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		attrMap.clear();
		attrMap = null;
		argsList.add(worldId);
		getMasterJdbcTemplate().update(sql, argsList.toArray());
	}
	
	@Override
	public void deleteByIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = DELETE_WORLD_BY_IDS + inSelection;
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	@Override
	public void updateWorldTypeLabel(Integer worldId, Integer typeId,
			String worldType) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_TYPE_LABEL, new Object[]{typeId,
				worldType, worldId});
	}

	@Override
	public UserInfo queryAuthorInfoByWorldId(Integer worldId) {
		return queryForObjectWithNULL(QUERY_USER_INFO_BY_WORLD_ID, new Object[]{worldId}, new RowMapper<UserInfo>() {

			@Override
			public UserInfo mapRow(ResultSet rs, int num)
					throws SQLException {
				return userInfoDao.buildUserInfo(rs);
			}
		});
	}
	
	@Override
	public void updateLatestValid(Integer id, Integer valid, Date dateAdded) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_LATEST_VALID, new Object[]{valid, dateAdded, id});
	}
	
	@Override
	public Integer queryMaxId(String startDateStr, String endDateStr) {
		return getJdbcTemplate().queryForInt(QUERY_MAX_ID_HEAD_BETWEEN_DATE_MODIFIED, new Object[]{startDateStr, endDateStr});
	}

	
	/**
	 * 根据结果集构建WorldMaintainDto
	 * 
	 * @param rs
	 * @param urlPrefix
	 * @return
	 * @throws SQLException 
	 */
	public ZTWorldDto buildZTWorldDto(ResultSet rs) throws SQLException {
		ZTWorldDto dto = new ZTWorldDto(
				rs.getInt("id"),
				rs.getString("short_link"),
				rs.getInt("author_id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getInt("star"),
				rs.getInt("trust"),
				rs.getFloat("app_ver"),
				rs.getString("phone_sys"),
				rs.getString("phone_ver"),
				rs.getString("world_name"), 
				rs.getString("world_desc"), 
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"), 
				(Date)rs.getObject("date_modified"), 
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"), 
				rs.getInt("keep_count"), 
				rs.getString("cover_path"), 
				rs.getString("title_path"),
				rs.getString("title_thumb_path"), 
				rs.getDouble("longitude"),
				rs.getDouble("latitude"), 
				rs.getString("location_desc"),
				rs.getString("location_addr"), 
				rs.getInt("phone_code"), 
				rs.getString("province"), 
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("valid"),
				rs.getInt("latest_valid"),
				rs.getInt("shield"),
				rs.getObject("squarerecd"));
		dto.setWorldId(dto.getId());
		
		if(dto.getShortLink() != null) {
			dto.setWorldURL(urlPrefix + dto.getShortLink()); 
		} else {
			dto.setWorldURL(urlPrefix + dto.getId());
		}
		return dto;
	}
	
}
