package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpUserRecommend;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpUserRecommendDto;
import com.imzhitu.admin.op.dao.UserRecommendDao;

@Repository
public class UserRecommendDaoImpl extends BaseDaoImpl implements
		UserRecommendDao {
	
	
	private static String table = HTS.OPERATIONS_USER_RECOMMEND;
	
	private static final String ORDER_BY_UR0_ID_DESC = " order by ur0.id desc";
	
	/**
	 * 查询推荐用户 SQL头部
	 */
	private static final String QUERY_RECOMMEND_USER_HEAD = "select ur.*,uv.verify_name,uv.verify_icon,u.*,a.user_name as recommender from (" 
			+ "select ur0.* from " + table + " as ur0 left join  hts.user_login_persistent ulp on ulp.user_id=ur0.user_id where 1=1 ";
	
	/**
	 * 查询推荐用户SQL尾部
	 */
	private static final String QUERY_RECOMMEND_USER_FOOT = " order by ur0.id desc limit ?,?) ur, " + HTS.USER_INFO + " u," + Admin.ADMIN_USER_INFO 
			+ " as a," + HTS.USER_VERIFY + " as uv" + " where ur.user_id=u.id and ur.recommender_id=a.id and ur.verify_id=uv.id order by ur.id desc";
	
	/**
	 * 根据名字查询推荐用户SQL头部
	 */
	private static final String QUERY_RECOMMEND_USER_BY_NAME_HEAD = "select ur0.*,uv.verify_name,uv.verify_icon,u0.*,a.user_name as recommender from " 
			+ table + " ur0, " + HTS.USER_INFO +" u0," + Admin.ADMIN_USER_INFO + " as a," + HTS.USER_VERIFY 
			+ " as uv where ur0.user_id=u0.id and ur0.recommender_id=a.id and ur0.verify_id=uv.id";
	
	/** 
	 * 查询推荐用户总数 
	 */
	private static final String QUERY_RECOMMEND_USER_COUNT = "select count(*) from " + table + " as ur0 left join  hts.user_login_persistent ulp on ulp.user_id=ur0.user_id, " 
			+ HTS.USER_INFO + " as u0 where u0.id=ur0.user_id";
	
	/** 
	 * 根据用户删除推荐 
	 */
	private static final String DELETE_BY_USER_ID = "delete from " + table + " where user_id=?";
	
	/** 
	 * 更新推荐描述 
	 */
	private static final String UPDATE_RECOMMEND_DESC = "update " + table
			+ " set recommend_desc=? where id=?";
	
	/**
	 * 更新推荐id
	 */
	private static final String UPDATE_ID = "update " + table
			+ " set id=? where user_id=?";
	
	/** 
	 * 根据id查询用户id列表
	 */
	private static final String QUERY_USER_ID_BY_IDS = "select user_id from " 
			+ table + " where id in ";
	
	/**
	 * 根据ids更新系统允许标记
	 */
	private static final String UPDATE_SYS_ACCEPT_BY_IDS = "update " + table 
			+ " set sys_accept=? where id in ";
	
	/**
	 * 更新通知状态
	 */
	private static final String UPDATE_NOTIFIED_BY_ID = "update " + table 
			+ " set notified=? where id=?";
	
	/**
	 * 穿通知状态
	 */
	private static final String QUERY_NOTIFIED_BY_ID = "select notified from " + table
			+ " where id=?";
	
	/**
	 * 根据ids查询用户id
	 */
	private static final String QUERY_UID_BY_IDS = "select user_id from " + table
			+ " where id in";
	
	/**
	 * 根据ids查询已经接受推荐的用户id
	 */
	private static final String QUERY_ACCEPT_UID_BY_IDS = "select * from " + table
			+ " where user_accept=1 and id in";
	
	private static final String UPDATE_FIX_POS_BY_UID = "update " + table + " set fix_pos=? where user_id=?";
	
	private static final String QUERY_BY_ID = "select * from " + table + " where id=?";
	
	private static final String UPDATE_VERIFY_ID = "update " + table + " set verify_id=? where id=?";
	
	private static final String UPDATE_WEIGHT = "update " + table + " set weight=? where id=?";
	
	private static final String QUERY_WEIGHT_BY_UID = "select weight from " + table + " where user_id=?";
	
	private static final String QUERY_ID_BY_UID = "select id from " + table + " where user_id=?";
	
	private static final String QUERY_USER_ACCEPT_SYS_ACCPET_RESULT =  " select count(*) from  " + table + " where user_accept=1 and sys_accept=1 and user_id=?";
	
	@Override
	public List<OpUserRecommendDto> queryRecommendUser(Map<String, Object> attrMap, 
			RowSelection rowSelection) {
		String selection = buildSelection(attrMap);
		String sql = QUERY_RECOMMEND_USER_HEAD + selection + QUERY_RECOMMEND_USER_FOOT;
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(rowSelection.getFirstRow());
		argsList.add(rowSelection.getLimit());
		return getJdbcTemplate().query(sql, argsList.toArray(),
				new RowMapper<OpUserRecommendDto>() {

			@Override
			public OpUserRecommendDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpRecommendUserDto(rs);
			}
			
		});
	}
	
	@Override
	public List<OpUserRecommendDto> queryRecommendUser(Integer maxId, 
			Map<String, Object> attrMap, RowSelection rowSelection) {
		String selection = buildSelection(attrMap);
		String sql = QUERY_RECOMMEND_USER_HEAD + selection + " and ur0.id<=?" + QUERY_RECOMMEND_USER_FOOT;
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxId);
		argsList.add(rowSelection.getFirstRow());
		argsList.add(rowSelection.getLimit());
		return getJdbcTemplate().query(sql, argsList.toArray(), new RowMapper<OpUserRecommendDto>() {

			@Override
			public OpUserRecommendDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpRecommendUserDto(rs);
			}
			
		});
	}
	
	@Override
	public List<OpUserRecommendDto> queryRecommendUserByName(Map<String, Object> attrMap, 
			RowSelection rowSelection) {
		String selection = buildSelection(attrMap);
		String sql = QUERY_RECOMMEND_USER_BY_NAME_HEAD + selection + ORDER_BY_UR0_ID_DESC;
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		return queryForPage(sql, argsList.toArray(),
				new RowMapper<OpUserRecommendDto>() {

			@Override
			public OpUserRecommendDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpRecommendUserDto(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<OpUserRecommendDto> queryRecommendUserByName(Integer maxId, 
			Map<String, Object> attrMap, RowSelection rowSelection) {
		String selection = buildSelection(attrMap);
		String sql = QUERY_RECOMMEND_USER_BY_NAME_HEAD + " and ur0.id<=?" + selection + ORDER_BY_UR0_ID_DESC;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(maxId);
		CollectionUtil.collectMapValues(argsList, attrMap);
		return queryForPage(sql, argsList.toArray(), new RowMapper<OpUserRecommendDto>() {

			@Override
			public OpUserRecommendDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpRecommendUserDto(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public long queryRecommendUserCount(Integer maxId, Map<String, Object> attrMap) {
		String selection = buildSelection(attrMap);
		String sql = QUERY_RECOMMEND_USER_COUNT + " and ur0.id<=? " + selection;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(maxId);
		CollectionUtil.collectMapValues(argsList, attrMap);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}
	
	/**
	 * 构建推荐用户查询条件
	 * 
	 * @param attrMap
	 * @return
	 */
	private String buildSelection(Map<String, Object> attrMap) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		for(int i = 0; i < keies.length; i++) {
			if(keies[i].equals("last_used")){
				builder.append(" and ulp.").append(keies[i]).append(" < ? ");
			}else if(keies[i].equals("user_name"))
				builder.append(" and u0.").append(keies[i]).append(" like ?");
			else if(keies[i].equals("weight")){
				if((Integer)(attrMap.get("weight")) == 0)
					builder.append(" and ur0.").append(keies[i]).append(" =?");
				else
					builder.append(" and ur0.").append(keies[i]).append(" >=?");
			}else
				builder.append(" and ur0.").append(keies[i]).append("=?");
		}
		return builder.toString();
	}
	
	@Override
	public void deleteRecommendUserByUserId(Integer userId) {
		getMasterJdbcTemplate().update(DELETE_BY_USER_ID, userId);
	}

	@Override
	public void deleteRecommendUserByIds(Integer[] ids) {
		deleteByIds(HTS.OPERATIONS_USER_RECOMMEND, ids);
	}

	@Override
	public void updateRecommendDesc(Integer id, String desc) {
		getMasterJdbcTemplate().update(UPDATE_RECOMMEND_DESC, 
				new Object[]{desc, id});
	}
	
	@Override
	public void updateRecommendId(Integer userId, Integer id) {
		getMasterJdbcTemplate().update(UPDATE_ID, new Object[]{id, userId});
	}
	
	@Override
	public List<Integer> queryUserIdByIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_USER_ID_BY_IDS + inSelection;
		return getJdbcTemplate().queryForList(sql, ids, Integer.class);
	}
	
	@Override
	public void updateSysAccept(Integer[] ids, Integer state) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = UPDATE_SYS_ACCEPT_BY_IDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{state}, true);
		getMasterJdbcTemplate().update(sql, args);
	}
	
	@Override
	public void updateNotified(Integer id, Integer notified) {
		getMasterJdbcTemplate().update(UPDATE_NOTIFIED_BY_ID, new Object[]{notified, id});
	}
	
	@Override
	public List<Integer> queryUserId(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_UID_BY_IDS + inSelection;
		return getJdbcTemplate().queryForList(sql, ids, Integer.class);
	}

	@Override
	public List<OpUserRecommend> queryAcceptUserId(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_ACCEPT_UID_BY_IDS + inSelection;
		return getJdbcTemplate().query(sql, ids, new RowMapper<OpUserRecommend>() {

			@Override
			public OpUserRecommend mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserRecommend(rs);
			}
			
		});
	}

	@Override
	public void setFixPosByUserId(Integer uid , Integer fixPos){
		getMasterJdbcTemplate().update(UPDATE_FIX_POS_BY_UID, fixPos,uid);
	}
	
	@Override
	public OpUserRecommend queryRecommendById(Integer id) {
		return getJdbcTemplate().queryForObject(QUERY_BY_ID, new Object[]{id},
				new RowMapper<OpUserRecommend>(){

					@Override
					public OpUserRecommend mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildUserRecommend(rs);
					}
		});
	}

	@Override
	public void updateVerifyId(Integer id, Integer verifyId) {
		getMasterJdbcTemplate().update(UPDATE_VERIFY_ID, new Object[]{verifyId, id});
	}
	
	@Override
	public Integer queryNotified(Integer id) {
		return getJdbcTemplate().queryForInt(QUERY_NOTIFIED_BY_ID, id);
	}
	
	@Override
	public void updateWeight(Integer id, Integer weight) {
		getMasterJdbcTemplate().update(UPDATE_WEIGHT, new Object[]{weight, id});
	}
	
	@Override
	public Integer queryWeightByUID(Integer userId) {
		return queryForObjectWithNULL(QUERY_WEIGHT_BY_UID, new Object[]{userId}, Integer.class);
	}
	
	@Override
	public Integer queryIdByUID(Integer userId) {
		return queryForObjectWithNULL(QUERY_ID_BY_UID, new Object[]{userId}, Integer.class);
	}

	
	@Override
	public long queryUserAccpetAndSysAcceptResult(Integer userId){
		return getJdbcTemplate().queryForLong(QUERY_USER_ACCEPT_SYS_ACCPET_RESULT, userId);
	}

	/**
	 * 从结果及构建推荐用户数据传输对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpUserRecommendDto buildOpRecommendUserDto(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new OpUserRecommendDto(
				rs.getInt("id"), 
				rs.getInt("user_id"), 
				rs.getString("recommend_desc"),
				rs.getString("recommender"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("user_accept"),
				rs.getInt("sys_accept"),
				rs.getInt("notified"),
				rs.getInt("fix_pos"),
				rs.getInt("weight"),
				rs.getInt("verify_id"),
				rs.getString("verify_name"),
				rs.getString("verify_icon"),
				rs.getInt("platform_code"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("sex"),
				rs.getString("email"),
				rs.getString("address"),
				rs.getString("province"),
				rs.getString("city"),
				birthday,
				rs.getString("signature"),
				rs.getString("user_label"),
				(Date)rs.getObject("register_date"),
				rs.getInt("phone_code"),
				rs.getString("phone_sys"),
				rs.getString("phone_ver"),
				rs.getInt("online"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("liked_count"),
				rs.getInt("keep_count"),
				rs.getFloat("ver"),
				rs.getInt("star"),
				rs.getInt("trust"));
	}
	
	public OpUserRecommend buildUserRecommend(ResultSet rs) throws SQLException {
		return new OpUserRecommend(
				rs.getInt("id"), 
				rs.getInt("user_id"), 
				rs.getInt("verify_id"),
				rs.getString("recommend_desc"),
				rs.getInt("recommender_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("user_accept"),
				rs.getInt("sys_accept"));
	}

}
