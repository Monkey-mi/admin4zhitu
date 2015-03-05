package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.userinfo.dao.UserInfoDao;
import com.imzhitu.admin.ztworld.dao.HTWorldKeepDao;

/**
 * <p>
 * 织图世界收藏数据访问对象
 * </p>
 * 
 * 创建时间：2013-7-24
 * @author ztj
 *
 */
@Repository
public class HTWorldKeepDaoImpl extends BaseDaoImpl implements HTWorldKeepDao{
	
	/**
	 * 表：织图世界收藏表
	 */
	public static String table = HTS.HTWORLD_KEEP;
	
	private static final String QUERY_KEEP_USER = "select hk.*,u.* from " 
			+ table + " as hk," + HTS.USER_INFO + " as u where hk.user_id=u.id and hk.valid=? and hk.world_id=?";
	
	private static final String QUERY_KEEP_USER_BY_MAX_ID = QUERY_KEEP_USER 
			+ " and hk.id<=?";
	
	private static final String QUERY_KEEP_USER_COUNT_BY_MAX_ID = "select count(*) from " 
			+ table + " as hk where hk.valid=? and hk.world_id=? and hk.id<=?"; 
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public List<UserInfo> queryKeepUser(Integer worldId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_KEEP_USER, new Object[]{Tag.TRUE, worldId}, new RowMapper<UserInfo>() {

			@Override
			public UserInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return userInfoDao.buildUserInfo(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<UserInfo> queryKeepUser(Integer maxId,
			Integer worldId, RowSelection rowSelection) {
		return queryForPage(QUERY_KEEP_USER_BY_MAX_ID, new Object[]{Tag.TRUE, worldId, maxId}, new RowMapper<UserInfo>() {

			@Override
			public UserInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return userInfoDao.buildUserInfo(rs);
			}
		}, rowSelection);
	}

	@Override
	public long queryKeepUserCount(Integer maxId, Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_KEEP_USER_COUNT_BY_MAX_ID, new Object[]{
			Tag.TRUE,worldId,maxId
		});
	}

}
