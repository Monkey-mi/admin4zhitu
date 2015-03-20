package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.UserLevelListDto;
import com.imzhitu.admin.common.pojo.InteractWorldLabelDto;
import com.imzhitu.admin.interact.dao.InteractUserlevelListDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class InteractUserlevelListDaoImpl extends BaseDaoImpl implements InteractUserlevelListDao {

//	private static final String table = Admin.INTERACT_USER_LEVEL_LIST;
	private static final String table = "hts_admin.interact_user_level_list";
	private static final String userLevelTable = "hts_admin.interact_user_level";
	private static final String userInfoTable = "hts.user_info";
//	private static final String htworld_table = HTS.HTWORLD_HTWORLD;
	private static final String htworld_table = "hts.htworld_htworld";
	private static final String adminuserinfo = "hts_admin.admin_user_info";
	private static final String QUERY_USER_LEVEL_LIST = "select ull.*,ul.level_description as level_description,ui.user_name as user_name,aui.user_name as operatorName from " 
			+ table + " ull left join "
			+ adminuserinfo + " aui on ull.operator_id= aui.id left join "
			+ userLevelTable + " ul on ull.user_level_id=ul.id left join "
			+ userInfoTable + " ui  on ull.user_id=ui.id where 1=1 ";
	private static final String QUERY_USER_LEVEL_LIST_BY_MAXID = "select ull.*,ul.level_description as level_description,ui.user_name as user_name ,aui.user_name as operatorName from " 
			+ table + " ull left join "
			+ adminuserinfo + " aui on ull.operator_id= aui.id left join "
			+ userLevelTable + " ul on ull.user_level_id=ul.id left join "
			+ userInfoTable + " ui  on ull.user_id=ui.id where ull.id<=? ";
	private static final String QUERY_USER_LEVEL_BY_ID = "select ull.*,ul.level_description as level_description,ui.user_name as user_name,aui.user_name as operatorName from "
			+ " (select * from " + table + " where id=?) as ull left join "
			+ adminuserinfo + " aui on ull.operator_id= aui.id left join "
			+ userLevelTable + " ul on ull.user_level_id=ul.id left join "
			+ userInfoTable + " ui  on ull.user_id=ui.id ";
	private static final String QUERY_USER_LEVEL_BY_USERID = "select ull.*,ul.level_description as level_description,ui.user_name as user_name,aui.user_name as operatorName from "
			+ " (select * from " + table + " where user_id=?) as ull left join "
			+ adminuserinfo + " aui on ull.operator_id= aui.id left join "
			+ userLevelTable + " ul on ull.user_level_id=ul.id left join "
			+ userInfoTable + " ui  on ull.user_id=ui.id ";
	private static final String UPDATE_VALIDITY_BY_ID = "update " + table + " set validity=?  where id=?";
	private static final String UPDATE_VALIDITY_BY_USERID = "update " + table + " set validity=? where user_id=?";
	private static final String UPDATE_USER_LEVEL_BU_USER_ID = "update " + table +" set user_level_id=?,validity=?,date_modify=?,operator_id=? where user_id=?";
	private static final String ADD_USER_LEVEL = "insert into " + table + " ( user_id,user_level_id,validity,date_add,date_modify,operator_id ) values (?,?,?,?,?,?)";
	private static final String DELETE_USER_LEVEL_BY_IDS = "delete from " + table + " where id in ";
	private static final String DELETE_USER_LEVEL_BY_USERIDS = " delete from " + table + " where user_id in ";
	private static final String GET_USER_LEVEL_COUNT = " select count(1) from " + table + " ull where id<=? ";
	private static final String CHECK_USER_LEVEL_EXIST_BY_USER_ID = "select count(1) from  " + table + " where user_id=?"; 
	private static final String QUERY_USER_LEVEL_ID_BY_USER_ID = "select user_level_id from " + table + " where user_id=?";
	
	/**
	 * 查询单位时间内，新发的织图的等级用户列表
	 */
//	private static final String QUERY_NEW_WORLD_USER_LEVEL_LIST_BY_TIME = "select userLevelList.*,htworld.id as worldId from " +  htworld_table +"  htworld, "+table+ 
//			" userLevelList where userLevelList.validity=" + Tag.TRUE+" and userLevelList.user_id=htworld.author_id and htworld.date_added between ? and ?";
//	private static final String QUERY_NEW_WORLD_USER_LEVEL_LIST_BY_TIME = "select userLevelList.*,htworld.id as worldId,htworld.author_id as authorId from " +  htworld_table +"  htworld left join "+table+ 
//			" userLevelList on userLevelList.user_id=htworld.author_id where  htworld.valid=1 and htworld.id>0 and htworld.author_id>0 and htworld.date_added between ? and ?";
	private static final String QUERY_NEW_WORLD_USER_LEVEL_LIST_BY_TIME = "select htworld.id as worldId,htworld.author_id as authorId from " +  htworld_table +"  htworld "
			+ "where  htworld.valid=1 and htworld.id>0 and htworld.author_id>0 and htworld.date_added between ? and ?";
	
	@Override
	public List<UserLevelListDto> QueryUserlevelList(Map<String,Object> attr,RowSelection rowSelection){
		String sql = buildSqlByMap(QUERY_USER_LEVEL_LIST, "order by ull.date_add desc", attr);
		return queryForPage(sql, new RowMapper<UserLevelListDto>(){
			@Override
			public UserLevelListDto mapRow(ResultSet rs, int rowNum)throws SQLException{
				return buildUserLevelList(rs);
			}
		},rowSelection);
	}
	
	@Override
	public List<UserLevelListDto> QueryUserlevelListByMaxId(Integer maxId,Map<String,Object> attr,RowSelection rowSelection){
		String sql = buildSqlByMap(QUERY_USER_LEVEL_LIST_BY_MAXID, "order by ull.date_add desc", attr);
		return queryForPage(sql,new Object[]{maxId}, new RowMapper<UserLevelListDto>(){
			@Override
			public UserLevelListDto mapRow(ResultSet rs, int rowNum)throws SQLException{
				return buildUserLevelList(rs);
			}
		},rowSelection);
	}
	
	@Override
	public long GetUserlevelListCount(Integer maxId,Map<String,Object> attr){
		String sql = buildSqlByMap(GET_USER_LEVEL_COUNT, null, attr);
		return getJdbcTemplate().queryForLong(sql, maxId);
	}
	
	@Override
	public UserLevelListDto QueryUserlevelById(Integer id){
		try{
			return getJdbcTemplate().queryForObject(QUERY_USER_LEVEL_BY_ID, new Object[]{id},new RowMapper<UserLevelListDto>(){
				@Override
				public UserLevelListDto mapRow(ResultSet rs, int rowNum)throws SQLException{
					return buildUserLevelList(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public Integer QueryUserlevelIdByUserId(Integer userId){
		try{
			return getJdbcTemplate().queryForObject(QUERY_USER_LEVEL_ID_BY_USER_ID, new Object[]{userId}, Integer.class);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public UserLevelListDto QueryUserlevelByUserId(Integer userId){
		try{
			return getJdbcTemplate().queryForObject(QUERY_USER_LEVEL_BY_USERID,  new Object[]{userId},new RowMapper<UserLevelListDto>(){
				@Override
				public UserLevelListDto mapRow(ResultSet rs, int rowNum)throws SQLException{
					return buildUserLevelList(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	@Override
	public void UpdateValidityByUserId(Integer userId, Integer validity){
		getJdbcTemplate().update(UPDATE_VALIDITY_BY_USERID, validity,userId);
	}
	
	@Override
	public void UpdateValidityById(Integer id,Integer validity){
		getJdbcTemplate().update(UPDATE_VALIDITY_BY_ID, validity,id);
	}
	
	@Override
	public void AddUserlevel(UserLevelListDto userLevelDto){
		getJdbcTemplate().update(ADD_USER_LEVEL, new Object[]{
				userLevelDto.getUser_id(),
				userLevelDto.getUser_level_id(),
				userLevelDto.getValidity(),
				userLevelDto.getAddDate(),
				userLevelDto.getModifyDate(),
				userLevelDto.getOperatorId()
		});
	}
	
	@Override
	public void DeleteUserlevelByIds(Integer[] ids){
		String sql = DELETE_USER_LEVEL_BY_IDS + SQLUtil.buildInSelection(ids);
		getJdbcTemplate().update(sql,(Object[])ids);
	}
	
	@Override
	public void DeleteUserlevelByUserids(Integer[] userIds){
		String sql = DELETE_USER_LEVEL_BY_USERIDS + SQLUtil.buildInSelection(userIds);
		getJdbcTemplate().update(sql);
	}
	
	/**
	 * 新发的织图的等级用户列表
	 */
	@Override
	public List<InteractWorldLabelDto> QueryNewWorldUserlevelListByTime(Date startTime,Date endTime){
		try{
			return getJdbcTemplate().query(QUERY_NEW_WORLD_USER_LEVEL_LIST_BY_TIME, new Object[]{startTime,endTime} ,new RowMapper<InteractWorldLabelDto>(){
				@Override
				public InteractWorldLabelDto mapRow(ResultSet rs, int rowNum)throws SQLException{
					return buildInteractWorldLabelDto(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	/**
	 * 检查userId是否被有等级
	 */
	public boolean CheckUserlevelExistByUserId(Integer userId){
		long c = getJdbcTemplate().queryForLong(CHECK_USER_LEVEL_EXIST_BY_USER_ID, userId);
		return c == 0 ? false : true ;
	}
	
	/**
	 * 更新用户等级
	 */
	@Override
	public void UpdateUserlevelByUserId(UserLevelListDto userLevelListDto){
		getJdbcTemplate().update(UPDATE_USER_LEVEL_BU_USER_ID, 
				userLevelListDto.getUser_level_id(),userLevelListDto.getValidity(),
				userLevelListDto.getModifyDate(),userLevelListDto.getOperatorId(),userLevelListDto.getUser_id());
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	/*
	private UserLevelListDto buildUserLevelListDto(ResultSet rs) throws SQLException{
		return new UserLevelListDto(rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("user_level_id"),
				rs.getInt("validity"));
	}
	*/
	/**
	 * 从查询记录中构建dto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private InteractWorldLabelDto buildInteractWorldLabelDto(ResultSet rs)throws SQLException{
//		return new InteractWorldLabelDto(
//				rs.getInt("worldId"),
//				rs.getInt("authorId"),
//				rs.getInt("user_level_id")
//				);
		InteractWorldLabelDto dto = new InteractWorldLabelDto();
		dto.setUser_id(rs.getInt("authorId"));
		dto.setWorldId(rs.getInt("worldId"));
		return dto;
	}
	
	/**
	 * 从查询记录中构建dto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private UserLevelListDto buildUserLevelList(ResultSet rs)throws SQLException{
		return new UserLevelListDto(rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("user_level_id"),
				rs.getInt("validity"),
				rs.getString("user_name"),
				rs.getString("level_description"),
				(Date)rs.getObject("date_add"),
				(Date)rs.getObject("date_modify"),
				rs.getInt("operator_id"),
				rs.getString("operatorName"));
	}
	
	/**
	 * 构建sql
	 */
	private String buildSqlByMap(String sqlHead,String sqlNail,Map<String,Object>attr){
		String sql = sqlHead;
		if(attr.get("userId")!=null)
			sql += " and ull.user_id=" + (Integer)(attr.get("userId")) + " ";
		Integer timeType = (Integer)(attr.get("timeType"));
		Date beginTime = (Date)(attr.get("beginTime"));
		Date endTime = (Date)(attr.get("endTime"));
		SimpleDateFormat  df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if((null != timeType) && (null != beginTime) && (null != endTime)){
			switch(timeType.intValue()){
			case 1:
				sql += " and ull.date_add ";break;
			case 2:
				sql += " and ull.date_modify ";break;
			}
			sql += " between str_to_date(\"" + df.format(beginTime) + "\",\"%Y-%m-%d %H:%i:%s\") and str_to_date(\"" + df.format(endTime)+ "\",\"%Y-%m-%d %H:%i:%s\") ";
		}
		if(sqlNail != null)
			sql += sqlNail;
		return sql;
	}
	
	
	
	
}
