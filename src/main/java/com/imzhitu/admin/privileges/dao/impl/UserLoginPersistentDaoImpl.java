package com.imzhitu.admin.privileges.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.privileges.dao.UserLoginPersistentDao;

/**
 * <p>
 * AccessToken数据访问对象
 * </p>
 * 
 * 创建时间：2013-8-23
 * @author ztj
 *
 */
public class UserLoginPersistentDaoImpl extends BaseDaoImpl implements
		UserLoginPersistentDao {

	private static String table = Admin.ADMIN_USER_LOGIN_PERSISTENT;
	
	/**
	 * 保存Token
	 */
	private static final String SAVE_TOKEN = "insert into " + table + " (user_id, series, token, last_used) values (?,?,?,?)";
	
	/**
	 * 更新Token
	 */
	private static final String UPDATE_TOKEN = "update " + table + " set token=?,last_used=? where series=?";
	
	/**
	 * 根据series查询Token
	 */
	private static final String QUERY_TOKEN_BY_SERIES = "select * from " + table + " where series=?";
	
	/**
	 * 根据用户id删除Token
	 */
	private static final String DELETE_TOKEN_BY_USER_ID = "delete from " + table + " where user_id=?";
	
	/**
	 * 根据用户id查询Token
	 */
	private static final String QUERY_TOKEN_BY_USER_ID = "select * from " + table + " where user_id=?";
	
	/**
	 * 根据登陆账号查询用户id
	 */
	private static final String QUERY_USER_ID_BY_LOGIN_CODE = "select id from " + Admin.ADMIN_USER_INFO + " where login_code=?";
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		Integer userId = getJdbcTemplate().queryForInt(QUERY_USER_ID_BY_LOGIN_CODE, 
				new Object[]{token.getUsername()});
		getJdbcTemplate().update(DELETE_TOKEN_BY_USER_ID, new Object[]{userId});
		getJdbcTemplate().update(SAVE_TOKEN, new Object[]{userId,
				token.getSeries(), token.getTokenValue(), token.getDate()});
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		getJdbcTemplate().update(UPDATE_TOKEN, new Object[]{tokenValue, lastUsed, series});
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		return queryForObjectWithNULL(QUERY_TOKEN_BY_SERIES, new RowMapper<PersistentRememberMeToken>(){

			@Override
			public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new PersistentRememberMeToken(
						String.valueOf(rs.getInt("user_id")),
						rs.getString("series"), 
						rs.getString("token"),
						(Date)rs.getObject("last_used"));
			}
			
		}, seriesId);
	}

	@Override
	public void removeUserTokens(String username) {
		try {
			Integer userId = getJdbcTemplate().queryForInt(QUERY_USER_ID_BY_LOGIN_CODE, 
					new Object[]{username});
			if(userId != null) {
				getJdbcTemplate().update(DELETE_TOKEN_BY_USER_ID, userId);
			}
		} catch(EmptyResultDataAccessException e) {
			
		}
	}

	@Override
	public PersistentRememberMeToken queryTokenByUserId(Integer userId) {
		return queryForObjectWithNULL(QUERY_TOKEN_BY_USER_ID, new RowMapper<PersistentRememberMeToken>(){
			@Override
			public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new PersistentRememberMeToken(
						String.valueOf(rs.getInt("user_id")),
						rs.getString("series"), 
						rs.getString("token"),
						(Date)rs.getObject("last_used"));
			}
		}, userId);
	}

}
