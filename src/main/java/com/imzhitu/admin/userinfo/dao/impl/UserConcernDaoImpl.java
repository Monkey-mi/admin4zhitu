package com.imzhitu.admin.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.UserConcern;
import com.imzhitu.admin.userinfo.dao.UserConcernDao;

/**
 * <p>
 * 用户关注信息数据访问对象
 * </p>
 * 
 * 创建时间：2013-7-2
 * 
 * @author ztj
 * 
 */
@Repository
public class UserConcernDaoImpl extends BaseDaoImpl implements UserConcernDao{

	private static final String ORDER_BY_UC_ID_DESC = " order by uc.id desc";
	
	/**
	 * 表：用户关注表
	 */
	private static final String table = HTS.USER_CONCERN;
	
	/** 查询粉丝总数SQL头部 */
	private static final String QUERY_FOLLOW_COUNT_HEADER =  "select count(*) from " 
			+ table + " as uc, " + HTS.USER_INFO + " as u where uc.user_id = u.id" 
			+ " and uc.user_id != uc.concern_id and uc.concern_id=? and uc.valid=? and u.shield=?";
	
	/** 根据最大id查询粉丝总数 */
	private static final String QUERY_FOLLOW_COUNT_BY_MAX_ID = QUERY_FOLLOW_COUNT_HEADER + " and uc.id<=?";
	
	/** 查询粉丝信息公用SQL头部 */
	private static final String QUERY_FOLLOW_HEAD = "select uc.*,u.* from " 
			+ table + " as uc, " + HTS.USER_INFO + " as u where uc.user_id = u.id" 
			+ " and uc.user_id != uc.concern_id and uc.concern_id=? and uc.valid=? and u.shield=?";
	
	/** 查询粉丝信息 */
	private static final String QUERY_FOLLOW = QUERY_FOLLOW_HEAD + ORDER_BY_UC_ID_DESC;
	
	/** 根据最大id查询粉丝信息 */
	private static final String QUERY_FOLLOW_BY_MAX_ID = QUERY_FOLLOW_HEAD + " and uc.id<=?" + ORDER_BY_UC_ID_DESC;
	
	
	/** 查询关注用户总数SQL头部 */
	public static final String QUERY_CONCERN_COUNT_HEADER = "select count(*) from " 
			+ table + " as uc, " + HTS.USER_INFO + " as u where uc.concern_id=u.id" 
			+ " and uc.user_id != uc.concern_id and uc.user_id=? and uc.valid=? and u.shield=?";
	
	/** 根据最大id查询关注用户总数 */
	public static final String QUERY_CONCERN_COUNT_BY_MAX_ID = QUERY_CONCERN_COUNT_HEADER + " and uc.id<=?";
	
	/** 查询关注信息公用SQL头部 */
	private static final String QUERY_CONCERN_HEAD = "select uc.*,u.* from " 
			+ table + " as uc, " + HTS.USER_INFO + " as u where uc.concern_id = u.id" 
			+ " and uc.user_id != uc.concern_id and uc.user_id=? and uc.valid=? and u.shield=?";
	
	/** 查询关注信息 */
	private static final String QUERY_CONCERN = QUERY_CONCERN_HEAD + ORDER_BY_UC_ID_DESC;
	
	/** 根据最大id查询关注信息 */
	private static final String QUERY_CONCERN_BY_MAX_ID = QUERY_CONCERN_HEAD + " and uc.id<=?" + ORDER_BY_UC_ID_DESC;

	@Override
	public long queryFollowCountByMaxId(Integer userId, Integer maxId){
		return getJdbcTemplate().queryForLong(QUERY_FOLLOW_COUNT_BY_MAX_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, maxId});
	}
	
	
	@Override
	public long queryConcernCountByMaxId(Integer userId, Integer maxId){
		return getJdbcTemplate().queryForLong(QUERY_CONCERN_COUNT_BY_MAX_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, maxId});
	}
	
	@Override
	public List<UserConcern> queryFollow(Integer userId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_FOLLOW, new Object[]{userId, Tag.TRUE, Tag.FALSE},
				new RowMapper<UserConcern>() {

			@Override
			public UserConcern mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcern(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<UserConcern> queryFollowByMaxId(Integer userId, Integer maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_FOLLOW_BY_MAX_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, maxId},
				new RowMapper<UserConcern>() {

			@Override
			public UserConcern mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcern(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<UserConcern> queryConcern(
			Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_CONCERN, new Object[]{userId, Tag.TRUE, Tag.FALSE},
				new RowMapper<UserConcern>() {

			@Override
			public UserConcern mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcern(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<UserConcern> queryConcernByMaxId(
			Integer userId, Integer maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_CONCERN_BY_MAX_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, maxId},
				new RowMapper<UserConcern>() {

			@Override
			public UserConcern mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcern(rs);
			}
		}, rowSelection);
	}
	
	/**
	 * 构建UserConcern
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UserConcern buildUserConcern(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		UserConcern dto = new UserConcern(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("concern_id"),
				rs.getString("user_name"), 
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"), 
				rs.getInt("sex"), 
				rs.getString("email"),
				rs.getString("address"),
				birthday, 
				rs.getString("signature"), 
				(Date)rs.getObject("register_date"),
				rs.getInt("online"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("liked_count"),
				rs.getInt("keep_count"),
				rs.getInt("star"),
				rs.getInt("trust"),
				rs.getInt("shield"));
		return dto;
		
	}
	
}
