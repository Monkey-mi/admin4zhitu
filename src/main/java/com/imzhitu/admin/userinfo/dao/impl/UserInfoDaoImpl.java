package com.imzhitu.admin.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.UserInfoDto;
import com.imzhitu.admin.userinfo.dao.UserInfoDao;

/**
 * <p>
 * 用户信息数据访问对象
 * </p>
 * 
 * 创建时间：2013-6-21
 * @author ztj
 *
 */
@Repository
public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao{

	/**
	 * 用户信息表
	 */
	private static String table = HTS.USER_INFO;
	
	/**
	 * 更新屏蔽标志
	 */
	private static final String UPDATE_SHIELD = "update " + table + " set shield=? where id=?";
	
	/**
	 * 根据id查询用户
	 */
	private static final String QUERY_USER_INFO_BY_ID = "select u.*, ur.user_accept, ur.sys_accept from " 
			+ "(select * from " + table + " where id=?)"
			+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id";
	
	/**
	 * 查询用户列表
	 */
//	private static final String QUERY_USER_INFO = "select u.*, ur.user_accept, ur.sys_accept from " 
//			+ "(select * from " + table + " where id>-10000000 order by register_date desc limit ?,?)"
//			+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id";
	private static final String QUERY_USER_INFO = "select u.*, ur.user_accept, ur.sys_accept from " 
	+ "(select * from " + table + " order by id desc limit ?,?)"
	+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id";
	
	/**
	 * 根据最大id查询用户列表
	 */
//	private static final String QUERY_USER_INFO_BY_MAX_ID = "select u.*, ur.user_accept, ur.sys_accept from " 
//			+ "(select * from " + table + " where id>-10000000 and id<=? order by register_date desc limit ?,?)"
//			+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id";
	private static final String QUERY_USER_INFO_BY_MAX_ID = "select u.*, ur.user_accept, ur.sys_accept from " 
	+ "(select * from " + table + " where id<=? order by id desc limit ?,?)"
	+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id";
	
	/**
	 * 根据最大id查询用户总数
	 */
//	private static final String QUERY_USER_INFO_COUNT_BY_MAX_ID = "select count(*) from " + table + " where id>-10000000 and id<=?";
	private static final String QUERY_USER_INFO_COUNT_BY_MAX_ID = "select count(*) from " + table + " where id<=?";
	
	/**
	 * 更新明星标记
	 */
	private static final String UPDATE_STAR_BY_ID = "update " + table + " set star=? where id=?";
	
	/**
	 * 根据ids更新明星标记
	 */
	private static final String UPDATE_STAT_BY_IDS = "update " + table + " set star=? where id in ";
	
	/**
	 * 更新用户标签
	 */
	private static final String UPDATE_USER_LABEL = "update " + table + " set user_label=? where id=?";
	
	/**
	 * 更新信任标记
	 */
	private static final String UPDATE_TRUST_BY_ID = "update " + table + " set trust=? where id=?";
	
	/**
	 * 查询APP版本
	 */
	private static final String QUERY_VER_BY_ID = "select ver from " + table + " where id=?";
	
	/**
	 * 更新用户签名
	 */
	private static final String UPDATE_SIGNATURE = "update " + table + " set signature=? where id=?";
	
	/**
	 * 查询明星标记
	 */
	private static final String QUERY_STAR_BY_ID = "select star from " + table + " where id=?";
	
	/**
	 * 查询是否是马甲
	 */
	private static final String CHECK_IS_ZOMBIE = "select count(*) from hts.operations_user_zombie zb where zb.user_id=?";
	
	/**
	 * 查询精选总数
	 */
	private static final String QUERY_USER_WORLD_COUNT_BY_USER_ID = "select count(*) from hts.htworld_type_world htw left join hts.htworld_htworld hh on htw.world_id=hh.id where hh.author_id=? and hh.valid=1 and hh.shield=0 and htw.valid=1 and htw.superb=1";
	
	@Override
	public List<UserInfoDto> queryUserInfo(RowSelection rowSelection) {
		
		return getJdbcTemplate().query(QUERY_USER_INFO,
				new Object[]{rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<UserInfoDto>(){

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
			
		});
	}
	
	@Override
	public UserInfoDto queryMaintainUserInfo(Integer id) {
		return queryForObjectWithNULL(QUERY_USER_INFO_BY_ID, new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
			
		});
	}

	@Override
	public List<UserInfoDto> queryUserInfoByMaxId(int maxId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_USER_INFO_BY_MAX_ID, 
				new Object[]{maxId,rowSelection.getFirstRow(),rowSelection.getLimit()}, 
				new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
		});
	}

	@Override
	public long queryUseInfoCountByMaxId(int maxId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_INFO_COUNT_BY_MAX_ID, maxId);
	}
	
	@Override
	public List<UserInfoDto> queryUserInfoByAttrMap(Map<String, Object> attrMap, RowSelection rowSelection) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = "select u.*, 1-ISNULL(ur.user_id) as userrecd from (select * from " + table + selection + ")"
				+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id" 
				+ ORDER_BY_ID_DESC;
		
		return queryForPage(sql, attrMap.values().toArray(), new RowMapper<UserInfoDto>(){

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<UserInfoDto> queryUserInfoByMaxIdAndAttrMap(int maxId, Map<String, Object> attrMap,
			RowSelection rowSelection) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = "select u.*, 1-ISNULL(ur.user_id) as userrecd from (select * from " + table + selection + " and id<=?)"
				+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id" 
				+ ORDER_BY_ID_DESC;
		attrMap.put("id", maxId);
		Object[] args = attrMap.values().toArray();
		return queryForPage(sql, args, new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
		}, rowSelection);
	}
	@Override
	public List<UserInfoDto> queryUserInfoByUserName(String userName, RowSelection rowSelection) {
//		String sql = "select u.*, ur.user_accept,ur.sys_accept from (select * from " + table + " where user_name like ?)"
//				+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id" 
//				+ ORDER_BY_ID_DESC;
		//为了出差，以后修改
		String sql = "select u.*, ur.user_accept,ur.sys_accept from (select * from " + table + " where user_name like ? and id>0)"
				+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id" 
				+ ORDER_BY_ID_DESC;
		
		return queryForPage(sql, new Object[]{"%"+userName+"%"}, new RowMapper<UserInfoDto>(){

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<UserInfoDto> queryUserInfoByMaxIdAndUserName(int maxId, String userName,
			RowSelection rowSelection) {
//		String sql = "select u.*, ur.user_accept,ur.sys_accept from (select * from " + table + " where user_name like ? and id<=?)"
//				+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id" 
//				+ ORDER_BY_ID_DESC;
		//为了出差，以后修改
		String sql = "select u.*, ur.user_accept,ur.sys_accept from (select * from " + table + " where user_name like ? and id<=? and id>0)"
				+ " as u left join " + HTS.OPERATIONS_USER_RECOMMEND + " as ur on u.id=ur.user_id" 
				+ ORDER_BY_ID_DESC;
		
		return queryForPage(sql, new Object[]{"%"+userName+"%", maxId}, new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
		}, rowSelection);
	}

	@Override
	public long queryUseInfoCountByMaxIdAndUserName(int maxId, String userName) {
//		String sql = "select count(*) from " + table + " where user_name like ? and id<=?";
		//为了出差，以后修改
		String sql = "select count(*) from " + table + " where user_name like ? and id<=? and id>0";
		return getJdbcTemplate().queryForLong(sql, new Object[]{"%"+userName+"%", maxId});
	}
	
	/**
	 * 查询该userId是否为马甲
	 */
	@Override
	public boolean queryUserIsZombieByUserId(Integer userId){
		long r = getJdbcTemplate().queryForLong(CHECK_IS_ZOMBIE, userId);
		if( r == 0)return false;
		else return true;
	}
	
	@Override
	public void updateShield(Integer userId, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_SHIELD, new Object[]{valid, userId});
	}
	
	@Override
	public UserInfoDto queryUserInfoDtoById(Integer id) {
		return queryForObjectWithNULL(QUERY_USER_INFO_BY_ID, new Object[]{id}, new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoDto(rs);
			}
		});
	}
	
	@Override
	public void updateStarById(Integer id, Integer star) {
		getMasterJdbcTemplate().update(UPDATE_STAR_BY_ID, new Object[]{star, id});
	}
	
	@Override
	public void updateStarByIds(Integer[] ids, Integer star) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = UPDATE_STAT_BY_IDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{star}, true);
		getMasterJdbcTemplate().update(sql, args);
	}
	
	@Override
	public void updateUserLabel(Integer userId, String label) {
		getMasterJdbcTemplate().update(UPDATE_USER_LABEL, new Object[]{label, userId});
	}
	
	@Override
	public void updateTrustById(Integer userId, Integer trust) {
		getMasterJdbcTemplate().update(UPDATE_TRUST_BY_ID, new Object[]{trust, userId});
	}
	
	@Override
	public float queryVer(Integer userId) {
		return getJdbcTemplate().queryForObject(QUERY_VER_BY_ID, 
				new Object[]{userId}, Float.class);
	}
	
	/**
	 * 更新用户签名
	 */
	@Override
	public void updateSignature(Integer userId,String signature){
		getMasterJdbcTemplate().update(UPDATE_SIGNATURE, signature,userId);
	}
	

	@Override
	public Integer queryStar(Integer id) {
		return getJdbcTemplate().queryForObject(QUERY_STAR_BY_ID, 
				new Object[]{id}, Integer.class);
	}
	/**
	 * 查询某用户的织图精选总数
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月28日 16:53:22 
	 */
	@Override
	public long queryUserWorldCountByUserId(Integer userId){
		return getJdbcTemplate().queryForLong(QUERY_USER_WORLD_COUNT_BY_USER_ID,userId);
	}
	
	@Override
	public UserInfo buildUserInfo(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new UserInfo(
				rs.getInt("id"),
				rs.getInt("platform_code"),
				rs.getString("platform_token"),
				rs.getLong("platform_token_expires"),
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
				rs.getInt("accept_sys_push"),
				rs.getInt("accept_comment_push"),
				rs.getInt("accept_reply_push"),
				rs.getInt("accept_liked_push"),
				rs.getInt("accept_keep_push"),
				rs.getInt("accept_concern_push"),
				rs.getInt("accept_msg_push"),
				rs.getInt("accept_umsg_push"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("liked_count"),
				rs.getInt("keep_count"),
				rs.getInt("star"),
				rs.getInt("trust"));
	}
	
	/**
	 * 根据游标构建用户信息POJO
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public UserInfoDto buildUserInfoDto(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		Object userAcceptObj = rs.getObject("user_accept");
		Integer userAccept = (Integer)userAcceptObj;
		if(userAcceptObj == null)
			userAccept = -1;
		
		Object sysAcceptObj = rs.getObject("sys_accept");
		Integer sysAccept = (Integer)sysAcceptObj;
		if(sysAcceptObj == null) {
			sysAccept = -1;
		}
		
		UserInfoDto user = new UserInfoDto(
				rs.getInt("id"),
				rs.getInt("platform_code"),
				rs.getString("platform_token"),
				rs.getLong("platform_token_expires"),
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
				rs.getString("phone_sys"),
				rs.getString("phone_ver"),
				rs.getInt("online"),
				rs.getInt("accept_sys_push"),
				rs.getInt("accept_comment_push"),
				rs.getInt("accept_reply_push"),
				rs.getInt("accept_liked_push"),
				rs.getInt("accept_keep_push"),
				rs.getInt("accept_concern_push"),
				rs.getInt("accept_msg_push"),
				rs.getInt("accept_umsg_push"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("liked_count"),
				rs.getInt("keep_count"),
				rs.getFloat("ver"),
				rs.getInt("star"),
				rs.getInt("trust"),
				rs.getInt("shield"),
				userAccept,
				sysAccept);
		return user;
	}

}
