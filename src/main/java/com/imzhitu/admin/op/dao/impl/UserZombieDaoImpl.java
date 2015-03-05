package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpSquare;
import com.hts.web.common.pojo.OpUserZombie;
import com.imzhitu.admin.op.dao.UserZombieDao;

@Repository
public class UserZombieDaoImpl extends BaseDaoImpl implements UserZombieDao {
	
	private static String table = HTS.OPERATIONS_USER_ZOMBIE;
	
	private static final String ORDER_BY_UR_ID_DESC = " order by ur.id desc";
	
	/**
	 * 查询推荐用户
	 */
	private static final String QUERY_ZOMBIE_USER = "select ur.*,u.* from " + table + " as ur left join " + HTS.USER_INFO + " as u on u.id=ur.user_id " 
			+ " where  u.world_count between ? and ?"
			+ ORDER_BY_UR_ID_DESC;
	/**
	 * 根据USER id来查询马甲
	 */
	private static final String QUERY_ZOMBIE_BY_USER_ID = "select ur.*,u.* from " + table + " as ur left join " + HTS.USER_INFO + " as u on u.id=ur.user_id " 
			+ " where u.id=? ";
	/**
	 * 根据USER id来查询马甲总数
	 */
	private static final String QUERY_ZOMBIE_COUNT_BY_USER_ID = "select count(*) from " + table + " as ur  " 
			+ " where ur.user_id=? ";
	/**
	 * 根据userName 来查询马甲
	 */
	private static final String QUERY_ZOMBIE_BY_USER_NAME = "select ur.*,u.* from " + table + " as ur left join " + HTS.USER_INFO + " as u on u.id=ur.user_id " 
			+ " where u.user_name=? ";
	/**
	 * 根据userName 来查询马甲总数
	 */
	private static final String QUERY_ZOMBIE_COUNT_BY_USER_NAME = "select count(*) from " + table + " as ur left join " + HTS.USER_INFO + " as u on u.id=ur.user_id " 
			+ " where u.user_name=? ";
	/**
	 * 根据发图时间来查询马甲
	 */
	private static final String QUERY_ZOMBIE_BY_WORLD_TIME = "select ur.*,u.* from " + table + " as ur left join " + HTS.USER_INFO + " as u on u.id=ur.user_id inner join "
			+ " (select distinct hh.author_id from  hts.operations_user_zombie uz inner join hts.htworld_htworld hh on uz.user_id=hh.author_id "
			+ " where hh.date_added between ? and ? ) t1 on ur.user_id=t1.author_id";
	/**
	 * 根据发图时间来查询马甲总数
	 */
	private static final String QUERY_ZOMBIE_COUNT_BY_WORLD_TIME = "select count(distinct hh.author_id) from  hts.operations_user_zombie uz inner join hts.htworld_htworld hh on uz.user_id=hh.author_id "
			+ " where hh.date_added between ? and ?";
	
	private static final String QUERY_ZOMBIE_USER_COUNT = "select count(*) from " + HTS.USER_INFO + " as u," + table + " as ur"
			+ " where u.id=ur.user_id and u.world_count between ? and ?";
	
	private static final String DELETE_BY_USER_ID = "delete from " + table + " where user_id=?";
	
	private static final String DELETE_BY_ID = "delete from " + table + " where id=?";
	
	private static final String SAVE_ZOMBIE_USER = "insert into " + table + " (user_id, recommender, recommend_date) values(?,?,?)";
	
	private static final String QUERY_RANDOM_ZOMBIE = "select uz1.user_id from " + table 
			+ " as uz1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM " +table+ ")-(SELECT MIN(id) FROM " 
			+ table + " ))+(SELECT MIN(id) FROM " 
			+ table + ") - ?) AS rand_id) AS uz2 WHERE uz1.id >= uz2.rand_id and uz1.shield=? ORDER BY uz1.id LIMIT ?";
	
	/** 根据屏蔽状态查询马甲总数 */
	private static final String QUERY_ZOMBIE_COUNT_BY_SHIELD = "select count(*) from " + table + " where shield=?";
	
	/**
	 * 更新屏蔽状态
	 */
	private static final String UPDATE_SHIELD = "update " + table + " set shield=? where user_id=?";
	
	/**
	 * 根据用户id查询未屏蔽的马甲
	 */
	private static final String QUERY_UN_SHIELD_ZOMBIE_UID = "select user_id from " + table + " where shield=?";
	
	/**
	 * 根据页码查询马甲id
	 */
	private static final String QUERY_USER_ID_BY_PAGE_INDEX = "select DISTINCT user_id from " + table + " where shield=? LIMIT ?,1";
	
	/**
	 * 查询未为跟随的马甲总数
	 */
//	private static final String QUERY_UN_FOLLOW_ZOMBIE_COUNT_BY_SHIELD = QUERY_ZOMBIE_USER_COUNT 
//			+ " where user_id not in"
//			+ " (select concern_id from " + HTS.USER_CONCERN + " where user_id=? and valid=1)"
//			+ " and shield=?";
	private static final String QUERY_UN_FOLLOW_ZOMBIE_COUNT_BY_SHIELD =  "select count(distinct zb.user_id) from " 
			+ table 
			+ " zb where not exists ( select uc.user_id from "
			+ HTS.USER_CONCERN
			+ " uc where uc.concern_id=? and uc.user_id=zb.user_id) and zb.shield=?";
	
	/**
	 * 根据页码查询未关注的马甲id
	 */
//	private static final String QUERY_UN_FOLLOW_USER_ID_BY_PAGE_INDEX = "select DISTINCT user_id from " + table 
//			+ " where user_id not in"
//			+ " (select concern_id from " + HTS.USER_CONCERN + " where user_id=? and valid=1)"
//			+ " and shield=? LIMIT ?,1";
	private static final String QUERY_UN_FOLLOW_USER_ID_BY_PAGE_INDEX = "select DISTINCT zb.user_id from " 
			+ table 
			+ " zb where not exists ( select uc.user_id from "
			+ HTS.USER_CONCERN
			+ " uc where uc.concern_id=? and uc.user_id=zb.user_id) and zb.shield=? limit ?,1";
	
	/**
	 * 查询userId对应的已关注的马甲总数
	 */
//	private static final String QUERY_FOLLOW_ZOMBIE_COUNT_BY_USER_ID = " select count( DISTINCT zb.user_id) from " + HTS.USER_CONCERN + " uc , " 
//			+ table + " zb ,hts.htworld_htworld hh  where uc.user_id=? and uc.concern_id=zb.user_id and zb.shield=1 and zb.user_id = hh.author_id";
	private static final String QUERY_FOLLOW_ZOMBIE_COUNT_BY_USER_ID = " select count( DISTINCT zb.user_id) from " + HTS.USER_CONCERN + " uc left join  " 
			+ table + " zb on uc.user_id=zb.user_id where uc.concern_id=?  and zb.shield=0 ";

	/**
	 * 查询userId对应的已经关注的马甲列表
	 */
//	private static final String QUERY_FOLLOW_ZOMBIE_BY_USER_ID = " select DISTINCT zb.user_id from " + HTS.USER_CONCERN + " uc , " 
//			+ table + " zb  ,hts.htworld_htworld hh  where uc.user_id=? and uc.concern_id=zb.user_id and zb.shield=1 and zb.user_id = hh.author_id order by hh.id desc limit ?,?";
	private static final String QUERY_FOLLOW_ZOMBIE_BY_USER_ID = " select DISTINCT zb.user_id from " + HTS.USER_CONCERN + " uc left join " 
			+ table + " zb  on uc.user_id=zb.user_id where uc.concern_id=? and  zb.shield=0  limit ?,1";
	
	/**
	 * 查询userId对应的已关注的马甲总数，但这些马甲是没有评论该织图的马甲
	 */
	private static final String QUERY_FOLLOW_ZOMBIE_COUNT_BY_USER_ID_FOR_INTERACT = " select count( DISTINCT zb.user_id) from " + HTS.USER_CONCERN + " uc left join  " 
			+ table + " zb on uc.user_id=zb.user_id where uc.concern_id=?  and zb.shield=0 "
					+ "and not exists ( "
					+ "select * from hts_admin.interact_world_comment iwc where iwc.world_id= ? and iwc.user_id=zb.user_id ) ";
	
	/**
	 * 查询userId对应的已关注的马甲列表，但这些马甲是没有评论该织图的马甲
	 */
	private static final String QUERY_FOLLOW_ZOMBIE_BY_USER_ID_FOR_INTERACT = " select DISTINCT zb.user_id from " + HTS.USER_CONCERN + " uc left join " 
			+ table + " zb  on uc.user_id=zb.user_id where uc.concern_id=? and  zb.shield=0 and not exists ( "
					+ "select * from hts_admin.interact_world_comment iwc where iwc.world_id= ? and iwc.user_id=zb.user_id "
					+ ") limit ?,1";
	private static final String QUERY_RANDOM_FOLLOW_ZOMBIE = "select * from ( select DISTINCT zb.user_id from " + HTS.USER_CONCERN + " uc left join " 
			+ table + " zb  on uc.user_id=zb.user_id where uc.concern_id=? and  zb.shield=0 and not exists ( "
					+ "select * from hts_admin.interact_world_comment iwc where iwc.world_id= ? and iwc.user_id=zb.user_id "
					+ ") ) as T1 order by rand() limit ?";
	
	
	/**
	 * 查询userId对应的未关注的马甲总数，但这些马甲是没有评论该织图的马甲
	 */
	private static final String QUERY_UN_FOLLOW_ZOMBIE_COUNT_BY_SHIELD_FOR_INTERACT =  "select count(zb.user_id) from " + table + " zb "
					+ " where zb.shield=? and not exists ( select uc.id from hts.user_concern as uc where uc.concern_id=? and uc.user_id=zb.user_id) "
					+ " and not exists (select iwc.user_id  from hts_admin.interact_world_comment iwc where iwc.user_id=zb.user_id and iwc.world_id=?)";
	
	/**
	 * 查询userId对应的未关注的马甲列表，但这些马甲是没有评论该织图的马甲
	 */
	private static final String QUERY_RANDOM_UN_FOLLOW_USER_ID = "select t1.user_id from "
			+ " (select zb.user_id,ui.concern_count from hts.operations_user_zombie zb left join hts.user_info ui ON zb.user_id = ui.id"
			+ "  where zb.shield=? ORDER BY  ui.concern_count ASC limit 0,200) t1 "
			+ " left join ( select uf.follow_id,count(uf.follow_id) as fcount from hts_admin.interact_user_follow AS uf "
			+ " where uf.finished=0 and uf.valid=1 group by uf.follow_id order by fcount asc limit 0,200) t2 on t1.user_id=t2.follow_id "
			+ " where not exists "
			+ " ( select uc.id from hts.user_concern as uc where uc.concern_id=? and uc.user_id=t1.user_id) "
			+ " and not exists "
			+ " (select iwc.user_id  from hts_admin.interact_world_comment iwc where iwc.user_id=t1.user_id and iwc.world_id=?) "
			+ " order by (IFNULL(t1.concern_count,0)+IFNULL(t2.fcount,0)) asc limit 0,? ";
	
	private static final String QUERY_UN_FOLLOW_USER_ID_BY_PAGE_INDEX_FOR_INTERACT = "select zb.user_id from " + table + " zb "
			+ " where zb.shield=? and not exists ( select uc.id from hts.user_concern as uc where uc.concern_id=? and uc.user_id=zb.user_id) "
			+ " and not exists (select iwc.user_id  from hts_admin.interact_world_comment iwc where iwc.user_id=zb.user_id and iwc.world_id=?)"
					+ " LIMIT ?,1";
	
	
	/**
	 * 修改性别
	 */
	private static final String UPDATE_ZOMBIE_SEX = "update hts.user_info set sex=? where id=? and exists (select id from hts.operations_user_zombie zb where zb.user_id=? )";
	
	/**
	 * 修改个性签名
	 */
	private static final String UPDATE_ZOMBIE_SIGN_TEXT = " update hts.user_info set signature=? where id=? and exists (select id from hts.operations_user_zombie zb where zb.user_id=? )";
	
	/**
	 * 修改昵称
	 */
	private static final String UPDATE_ZOMBIE_USER_NAME = " update hts.user_info set user_name=? where id=? and exists (select id from hts.operations_user_zombie zb where zb.user_id=? )";
	
	/**
	 * 修改职业
	 */
	private static final String UPDATE_ZOMBIE_USER_JOB = "update hts.user_info set job=? where id=? and exists (select id from hts.operations_user_zombie zb where zb.user_id=? )";
	
	/**
	 * 修改被赞数
	 */
	private static final String UPDATE_ZOMBIE_USER_LIKEMECOUNT = "update hts.user_info set like_me_count=? where id=? and exists (select id from hts.operations_user_zombie zb where zb.user_id=? )";
	
	/**
	 * 修改地址
	 */
	private static final String UPDATE_ZOMBIE_USER_ADDRESS = "update hts.user_info set address=? where id=? and exists (select id from hts.operations_user_zombie zb where zb.user_id=? )";
	
	/**
	 * 修改城市和省份
	 */
	private static final String UPDATE_ZOMBIE_PROVINCE_CITY =  "update hts.user_info set province=?,city=? where id=? and exists (select id from hts.operations_user_zombie zb where zb.user_id=? )";
	
	@Override
	public void updateZombieProvinceCity(String province,String city,Integer userId){
		getJdbcTemplate().update(UPDATE_ZOMBIE_PROVINCE_CITY, province,city,userId,userId);
	}
	
	@Override
	public void updateZombieJob(String job,Integer userId){
		getJdbcTemplate().update(UPDATE_ZOMBIE_USER_JOB, job,userId,userId);
	}
	
	@Override
	public void updateZombieLikeMeCount(Integer  likeMeCount,Integer userId){
		getJdbcTemplate().update(UPDATE_ZOMBIE_USER_LIKEMECOUNT, likeMeCount,userId,userId);
	}
	
	@Override
	public void updateZombieAddress(String address,Integer userId){
		getJdbcTemplate().update(UPDATE_ZOMBIE_USER_ADDRESS, address,userId,userId);
	}
	
	@Override
	public void updateZombieSex(Integer sex,Integer userId){
		getJdbcTemplate().update(UPDATE_ZOMBIE_SEX, sex,userId,userId);
	}
	
	@Override
	public void updateZombieSignText(String signture,Integer userId){
		getJdbcTemplate().update(UPDATE_ZOMBIE_SIGN_TEXT, signture,userId,userId);
	}
	
	@Override
	public void updateZombieUserName(String userName,Integer userId){
		getJdbcTemplate().update(UPDATE_ZOMBIE_USER_NAME, userName,userId,userId);
	}
	
	@Override
	public List<OpUserZombie> queryZombieUser(Integer min,Integer max,Integer userId,String userName,Date begin,Date end,
			RowSelection rowSelection) {
		if( userId != null){
			return queryForPage(QUERY_ZOMBIE_BY_USER_ID, new Object[]{userId}, new RowMapper<OpUserZombie>() {

				@Override
				public OpUserZombie mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildOperationsZombie(rs);
				}
			}, rowSelection);
		} else if (userName != null && !"".equals(userName)) {
			return queryForPage(QUERY_ZOMBIE_BY_USER_NAME, new Object[]{userName}, new RowMapper<OpUserZombie>() {

				@Override
				public OpUserZombie mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildOperationsZombie(rs);
				}
			}, rowSelection);
		} else if ( begin != null && end != null) {
			return queryForPage(QUERY_ZOMBIE_BY_WORLD_TIME, new Object[]{begin,end}, new RowMapper<OpUserZombie>() {

				@Override
				public OpUserZombie mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildOperationsZombie(rs);
				}
			}, rowSelection);
		} else {
			return queryForPage(QUERY_ZOMBIE_USER, new Object[]{min,max}, new RowMapper<OpUserZombie>() {

				@Override
				public OpUserZombie mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildOperationsZombie(rs);
				}
			}, rowSelection);
		}
		
	}

	@Override
	public long queryZombieUserCount(Integer min,Integer max,Integer userId,String userName,Date begin,Date end) {
		
		if( userId != null){
			return getJdbcTemplate().queryForLong(QUERY_ZOMBIE_COUNT_BY_USER_ID,userId);
		} else if (userName != null && !"".equals(userName) ) {
			return getJdbcTemplate().queryForLong(QUERY_ZOMBIE_COUNT_BY_USER_NAME,userName);
		} else if ( begin != null && end != null) {
			return getJdbcTemplate().queryForLong(QUERY_ZOMBIE_COUNT_BY_WORLD_TIME,begin,end);
		} else {
			return getJdbcTemplate().queryForLong(QUERY_ZOMBIE_USER_COUNT,min,max);
		}
	}

	@Override
	public void saveZombieUser(OpSquare recommend) {
		getJdbcTemplate().update(SAVE_ZOMBIE_USER, new Object[]{
			recommend.getTargetId(),
			recommend.getRecommender(),
			recommend.getRecommendDate()
		});
	}

	@Override
	public void deleteZombieUserByUserId(Integer userId) {
		getJdbcTemplate().update(DELETE_BY_USER_ID, userId);
	}

	@Override
	public void deleteZombieUserById(Integer id) {
		getJdbcTemplate().update(DELETE_BY_ID, id);
	}

	@Override
	public List<Integer> queryRandomZombieId(int limit) {
		return getJdbcTemplate().queryForList(QUERY_RANDOM_ZOMBIE, Integer.class, new Object[]{limit, Tag.FALSE, limit});
	}

	@Override
	public void updateShield(Integer userId, Integer shield) {
		getJdbcTemplate().update(UPDATE_SHIELD, new Object[]{shield, userId});
	}

	@Override
	public List<Integer> queryUnShieldZombieUserId() {
		return getJdbcTemplate().queryForList(QUERY_UN_SHIELD_ZOMBIE_UID, Integer.class, new Object[]{Tag.FALSE});
	}
	
	@Override
	public long queryZombieCount(Integer shield) {
		return getJdbcTemplate().queryForLong(QUERY_ZOMBIE_COUNT_BY_SHIELD, new Object[]{shield});
	}

	@Override
	public Integer queryUserIdByPageIndex(int shield, int page) {
		try{
			return getJdbcTemplate().queryForInt(QUERY_USER_ID_BY_PAGE_INDEX, new Object[]{shield, page});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public long queryUnFollowZombieCount(Integer userId, Integer shield) {
		return getJdbcTemplate().queryForLong(QUERY_UN_FOLLOW_ZOMBIE_COUNT_BY_SHIELD, 
				new Object[]{userId, shield});
	}
	
	@Override
	public Integer queryUnFollowUserIdByPageIndex(Integer userId, int shield,
			int page) {
		return getJdbcTemplate().queryForInt(QUERY_UN_FOLLOW_USER_ID_BY_PAGE_INDEX, 
				new Object[]{userId, shield, page});
	}

	
	/**
	 * 查询userId对应的僵尸粉总数
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年8月26日 17:23:45
	 */
	@Override
	public long queryZombieFollowCountByUserId(Integer userId){
		return getJdbcTemplate().queryForLong(QUERY_FOLLOW_ZOMBIE_COUNT_BY_USER_ID, userId);
	}
	
	@Override
	public Integer queryZombieFollowByUserId(Integer userId,int page,int limit){
		try{
			return getJdbcTemplate().queryForObject(QUERY_FOLLOW_ZOMBIE_BY_USER_ID,Integer.class,userId,page);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	/**
	 * 查询userId对应的已关注的马甲总数，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:03
	 */
	@Override
	public long queryZombieFollowCountByUserIdForInteractForInteract(Integer userId,Integer worldId){
		return getJdbcTemplate().queryForLong(QUERY_FOLLOW_ZOMBIE_COUNT_BY_USER_ID_FOR_INTERACT, userId,worldId);
	}
	
	/**
	 * 查询userId对应的已关注的马甲列表，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:14
	 */
	@Override
	public Integer queryZombieFollowByUserIdForInteractForInteract(Integer userId,Integer worldId,int page,int limit){
		try{
			return getJdbcTemplate().queryForObject(QUERY_FOLLOW_ZOMBIE_BY_USER_ID_FOR_INTERACT,Integer.class,userId,worldId,page);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	/**
	 * 查询userId对应的已关注的马甲列表，但这些马甲是没有评论该织图的马甲。一次性查取total个
	 * @param userId
	 * @param worldId
	 * @param total
	 * @return
	 */
	@Override
	public List<Integer> queryRandomZombieFollow(Integer userId,Integer worldId,int total){
		try{
			return getJdbcTemplate().queryForList(QUERY_RANDOM_FOLLOW_ZOMBIE, Integer.class, userId,worldId,total);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	
	/**
	 * 查询userId对应的未关注的马甲总数，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:03
	 */
	@Override
	public long queryUnFollowZombieCountForInteract(Integer userId, Integer worldId,Integer shield) {
		return getJdbcTemplate().queryForLong(QUERY_UN_FOLLOW_ZOMBIE_COUNT_BY_SHIELD_FOR_INTERACT, 
				new Object[]{ shield,userId,worldId});
	}
	
	/**
	 * 查询userId对应的未关注的马甲列表，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:14
	 */
	@Override
	public Integer queryUnFollowUserIdByPageIndexForInteract(Integer userId,Integer worldId, int shield,
			int page) {
		return getJdbcTemplate().queryForInt(QUERY_UN_FOLLOW_USER_ID_BY_PAGE_INDEX_FOR_INTERACT, 
				new Object[]{shield, userId,worldId, page});
	}
	
	/**
	 * 查询userId对应的未关注的马甲列表，但这些马甲是没有评论该织图的马甲，随机选取total个
	 * @param userId
	 * @param worldId
	 * @param shield
	 * @param total
	 * @return
	 */
	@Override
	public List<Integer> queryRandomUnFollowUserId(Integer userId,Integer worldId,int shield,int total){
		try{
			return getJdbcTemplate().queryForList(QUERY_RANDOM_UN_FOLLOW_USER_ID, Integer.class, shield,userId,worldId,total);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	/**
	 * 从结果及构建马甲信息
	 * @param rs
	 * @param hasRecommendDesc
	 * @return
	 * @throws SQLException
	 */
	public static OpUserZombie buildOperationsZombie(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new OpUserZombie(
				rs.getInt("id"), 
				rs.getInt("user_id"), 
				rs.getInt("platform_code"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("sex"),
				rs.getString("email"),
				rs.getString("address"),
				birthday,
				rs.getString("signature"),
				(Date)rs.getObject("register_date"),
				rs.getInt("phone_code"),
				rs.getInt("online"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("liked_count"),
				rs.getInt("keep_count"),
				rs.getInt("star"),
				rs.getInt("trust"),
				rs.getInt("shield"),
				rs.getString("recommender"),
				(Date)rs.getObject("recommend_date"),
				rs.getInt("platform_verify"),
				rs.getInt("like_me_count"),
				rs.getString("job"),
				rs.getString("province"),
				rs.getString("city"));
	}

}
