package com.imzhitu.admin.interact.dao.impl;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.interact.dao.InteractUserlevelDao;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.UserLevelDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class InteractUserlevelDaoImpl extends BaseDaoImpl implements InteractUserlevelDao {
	private static final String table = Admin.INTERACT_USER_LEVEL;//用户等级表
	/**
	 * 查询用户等级列表sql
	 */
	private static final String QUERY_USER_LEVEL_LIST = "select * from " + table + " order by id desc";
	/**
	 * 根据maxId来查询用户等级
	 */
	private static final String QUERY_USER_LEVEL_BY_MAXID = "select * from " + table + " where id <= ? order by id desc";
	/**
	 * 根据id来查询用户等级
	 */
	private static final String QUERY_USER_LEVEL_BY_ID = "select * from " + table + " where id=?";
	/**
	 * 根据ids删除用户等级sql
	 */
	private static final String DELETE_USER_LEVEL_BY_IDS = "delete from " + table + " where id in ";
	
	/**
	 * 查询用户等级总数sql
	 */
	private static final String QUERY_USER_LEVEL_COUNT =  "select count(1) from " + table + " where id <= ?";
	
	/**
	 * 增加用户等级sql
	 */
	private static final String ADD_USER_LEVEL = "insert into " + table +
			" ( min_fans_count,max_fans_count, min_liked_count,max_liked_count,min_comment_count,max_comment_count,min_play_times,max_play_times,time,level_description,weight) values (?,?,?,?,?,?,?,?,?,?,?)";
	/**
	 * 更新用户等级
	 */
	private static final String UPDATE_USER_LEVEL = "update "+ table +
			" set min_fans_count=?,max_fans_count=?,min_liked_count=?,max_liked_count=?,min_comment_count=?,max_comment_count=?,min_play_times=?,max_play_times=?,time=?,level_description=?,weight=? where id=?";
	
	/**
	 * 查询用户等级列表
	 */
	@Override
	public List<UserLevelDto> QueryUserlevelList(RowSelection rowSelection){		
		return queryForPage(QUERY_USER_LEVEL_LIST,new RowMapper<UserLevelDto>(){
			
			@Override
			public UserLevelDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserLevelDto(rs);
			}
			
		},rowSelection);		
	}
	
	/**
	 * 查询用户等级列表
	 */
	@Override
	public List<UserLevelDto> QueryUserlevelList(){		
		return getJdbcTemplate().query(QUERY_USER_LEVEL_LIST,new RowMapper<UserLevelDto>(){
			
			@Override
			public UserLevelDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserLevelDto(rs);
			}
			
		});		
	}
	
	/**
	 * 根据id查询用户等级
	 */
	@Override
	public UserLevelDto QueryUserlevelById(Integer id)throws Exception{
		try{
			return getJdbcTemplate().queryForObject(QUERY_USER_LEVEL_BY_ID, new Object[]{id},new RowMapper<UserLevelDto>(){
				@Override
				public UserLevelDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildUserLevelDto(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			throw e;
		}
	}
	
	/**
	 * 根据ids删除用户等级
	 */
	@Override
	public void DeleteUserlevelByIds(Integer[] ids)throws Exception{
		try{
			String sql = DELETE_USER_LEVEL_BY_IDS + SQLUtil.buildInSelection(ids);
			getMasterJdbcTemplate().update(sql,(Object[])ids);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 增加用户等级
	 */
	@Override
	public void AddUserlevel(UserLevelDto userlevelDto)throws Exception {
		try{
			getMasterJdbcTemplate().update(ADD_USER_LEVEL, new Object[]{
					userlevelDto.getMin_fans_count(),userlevelDto.getMax_fans_count(),
					userlevelDto.getMin_liked_count(),userlevelDto.getMax_liked_count(),
					userlevelDto.getMin_comment_count(),userlevelDto.getMax_comment_count(),
					userlevelDto.getMin_play_times(),userlevelDto.getMax_play_times(),
					userlevelDto.getTime(),userlevelDto.getLevel_description(),userlevelDto.getWeight()});
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 根据id来更新用户等级
	 */
	@Override
	public void UpdateUserlevelById(UserLevelDto userlevelDto)throws Exception{
		try{
			getMasterJdbcTemplate().update(UPDATE_USER_LEVEL, new Object[]{
					userlevelDto.getMin_fans_count(),userlevelDto.getMax_fans_count(),
					userlevelDto.getMin_liked_count(),userlevelDto.getMax_liked_count(),
					userlevelDto.getMin_comment_count(),userlevelDto.getMax_comment_count(),
					userlevelDto.getMin_play_times(),userlevelDto.getMax_play_times(),
					userlevelDto.getTime(),userlevelDto.getLevel_description(),userlevelDto.getWeight(),
					userlevelDto.getId()});
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 查询用户等级总数
	 */
	@Override
	public long GetUserLevelCountByMaxId(int maxId){		
		return getJdbcTemplate().queryForLong(QUERY_USER_LEVEL_COUNT,maxId);		
	}
	
	/**
	 * 查询用户等级列表
	 */
	public List<UserLevelDto> QueryUserLevel(){
		return getJdbcTemplate().query(QUERY_USER_LEVEL_LIST,new RowMapper<UserLevelDto>(){
			@Override
			public UserLevelDto mapRow(ResultSet rs, int rowNum)
					throws SQLException{
				return buildUserLevelDto(rs);					
			}
		});
	}
	
	/**
	 * 根据maxId查询用户等级
	 */
	@Override
	public List<UserLevelDto> QueryUserlevelListByMaxId(int maxId,RowSelection rowSelection){		
		return queryForPage(QUERY_USER_LEVEL_BY_MAXID,new Object[]{maxId}, new RowMapper<UserLevelDto>(){
			@Override
			public UserLevelDto mapRow(ResultSet rs, int rowNum)
					throws SQLException{
				return buildUserLevelDto(rs);					
			}
		},rowSelection);
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	
	public UserLevelDto buildUserLevelDto(ResultSet rs) throws SQLException{
		return new UserLevelDto(
				rs.getInt("id"),
				rs.getInt("min_fans_count"),
				rs.getInt("max_fans_count"),
				rs.getInt("min_liked_count"),
				rs.getInt("max_liked_count"),
				rs.getInt("min_comment_count"),
				rs.getInt("max_comment_count"),
				rs.getInt("min_play_times"),
				rs.getInt("max_play_times"),
				rs.getInt("time"),
				rs.getString("level_description"),
				rs.getInt("weight")
				);
	}

}
