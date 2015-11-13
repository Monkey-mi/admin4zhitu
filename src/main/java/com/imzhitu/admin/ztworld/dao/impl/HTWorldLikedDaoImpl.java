package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.ZTWorldLikeDto;
import com.imzhitu.admin.userinfo.dao.UserInfoDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLikedDao;

/**
 * <p>
 * 织图世界喜欢数据访问对象
 * </p>
 * 
 * 创建时间：2013-7-4
 * @author ztj
 *
 */
@Repository
public class HTWorldLikedDaoImpl extends BaseDaoImpl implements HTWorldLikedDao{

	/** 织图喜欢表  */
	public static String table = HTS.HTWORLD_LIKED;
	
	/** 查询喜欢用户 */
	private static final String QUERY_LIKE_USER = "select hl.*,u.* from " 
			+ table + " as hl," + HTS.USER_INFO + " as u where hl.user_id=u.id and hl.world_id=?";
	
	/** 根据最大id查询喜欢用户 */
	private static final String QUERY_LIKED_USER_BY_MAX_ID = QUERY_LIKE_USER + " and hl.id<=?";
	
	/** 根据最大id查询喜欢用户总数 */
	private static final String QUERY_LIKED_USER_COUNT_BY_MAX_ID = "select count(*) from " 
			+ table + " as hl where  hl.world_id=? and hl.id<=?"; 
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	
	@Override
	public List<ZTWorldLikeDto> queryLikedUser(Integer worldId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_LIKE_USER, new Object[]{ worldId}, new RowMapper<ZTWorldLikeDto>() {

			@Override
			public ZTWorldLikeDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				userInfoDao.buildUserInfo(rs);
				return buildLikeDto(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<ZTWorldLikeDto> queryLikedUser(Integer maxId,
			Integer worldId, RowSelection rowSelection) {
		return queryForPage(QUERY_LIKED_USER_BY_MAX_ID, new Object[]{worldId, maxId}, new RowMapper<ZTWorldLikeDto>() {

			@Override
			public ZTWorldLikeDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLikeDto(rs);
			}
		}, rowSelection);
	}

	@Override
	public long queryLikedUserCount(Integer maxId, Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_LIKED_USER_COUNT_BY_MAX_ID, new Object[]{
			worldId,maxId
		});
	}
	
	/**
	 * 构建ZTWorldLikeDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ZTWorldLikeDto buildLikeDto(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new ZTWorldLikeDto(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("platform_code"),
				rs.getString("login_code"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("sex"),
				rs.getString("email"),
				rs.getString("address"),
				birthday,
				rs.getString("signature"),
				rs.getString("user_label"),
				(Date)rs.getObject("register_date"),
				rs.getString("push_token"),
				rs.getInt("phone_code"),
				rs.getInt("online"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("liked_count"),
				rs.getInt("keep_count"),
				rs.getInt("star"),
				rs.getInt("trust"));
	}

}
