package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;
import com.imzhitu.admin.interact.dao.InteractWorldlevelDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class InteractWorldlevelDaoImpl extends BaseDaoImpl implements
		InteractWorldlevelDao {

	private static final String table = Admin.INTERACT_WORLD_LEVEL;
	
	private static final String QUERY_WORLD_LEVEL_LIST = "select * from " + table + " order by id desc";
	private static final String QUERY_WORLD_LEVEL_LIST_BY_ID = "select * from " + table + " where id=?";
	private static final String QUERY_WORLD_LEVEL_LIST_BY_MAXID = "select * from " + table + " where id <=? order by id desc";
	private static final String DELETE_WORLD_LEVEL_BY_IDS = "delete from " + table + " where id in ";
	private static final String ADD_WORLD_LEVEL = "insert into "+ table + 
			" (min_fans_count,max_fans_count, min_liked_count,max_liked_count,min_comment_count,max_comment_count,min_play_times,max_play_times,time,level_description,weight) values (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_WORLD_LEVEL = "update "+ table +
			" set min_fans_count=?,max_fans_count=?,min_liked_count=?,max_liked_count=?,min_comment_count=?,max_comment_count=?,min_play_times=?,max_play_times=?,time=?,level_description=?,weight=? where id=?";
	private static final String QUERY_WORLD_LEVEL_COUNT = "select count(1) from " + table + " where id<=?";
	private static final String QUERY_USER_ID_BY_WORLD = " select author_id from hts.htworld_htworld where id=?";
	
	@Override
	public List<ZTWorldLevelDto> QueryWorldlevelList(RowSelection rowSelection) {
		return queryForPage(QUERY_WORLD_LEVEL_LIST, new RowMapper<ZTWorldLevelDto>(){
			@Override
			public ZTWorldLevelDto mapRow(ResultSet rs,int rowNum) throws SQLException{
				return buildZTWorldLevelDto(rs);
			}
		},rowSelection);
	}

	@Override
	public ZTWorldLevelDto QueryWorldlevelById(Integer id) throws Exception {
		try{
			return getJdbcTemplate().queryForObject(QUERY_WORLD_LEVEL_LIST_BY_ID, new Object[]{id}, new RowMapper<ZTWorldLevelDto>(){
				@Override
				public ZTWorldLevelDto mapRow(ResultSet rs,int rowNum)throws SQLException{
					return buildZTWorldLevelDto(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public Integer QueryUIDByWID(Integer wid){
		try{
			return getJdbcTemplate().queryForObject(QUERY_USER_ID_BY_WORLD,Integer.class,wid);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public void DeleteWorldLevelByIds(Integer[] ids) throws Exception {
		String sql = DELETE_WORLD_LEVEL_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}

	@Override
	public void AddWorldlevel(ZTWorldLevelDto worldlevelDto) throws Exception {
		getMasterJdbcTemplate().update(ADD_WORLD_LEVEL, new Object[]{
				worldlevelDto.getMin_fans_count(),worldlevelDto.getMax_fans_count(),
				worldlevelDto.getMin_liked_count(),worldlevelDto.getMax_liked_count(),
				worldlevelDto.getMin_comment_count(),worldlevelDto.getMax_comment_count(),
				worldlevelDto.getMin_play_times(),worldlevelDto.getMax_play_times(),
				worldlevelDto.getTime(),worldlevelDto.getLevel_description(),worldlevelDto.getWeight()});
	}

	@Override
	public void UpdateWorldlevel(ZTWorldLevelDto worldlevelDto)	throws Exception {
		getMasterJdbcTemplate().update(UPDATE_WORLD_LEVEL, new Object[]{
				worldlevelDto.getMin_fans_count(),worldlevelDto.getMax_fans_count(),
				worldlevelDto.getMin_liked_count(),worldlevelDto.getMax_liked_count(),
				worldlevelDto.getMin_comment_count(),worldlevelDto.getMax_comment_count(),
				worldlevelDto.getMin_play_times(),worldlevelDto.getMax_play_times(),
				worldlevelDto.getTime(),worldlevelDto.getLevel_description(),worldlevelDto.getWeight(),
				worldlevelDto.getId()});
	}

	@Override
	public long GetWorldlevelCountByMaxId(Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_WORLD_LEVEL_COUNT, maxId);
	}

	@Override
	public List<ZTWorldLevelDto> QueryWorldlevelListByMaxId(Integer maxId,RowSelection rowSelection) {
		return queryForPage(QUERY_WORLD_LEVEL_LIST_BY_MAXID,new Object[]{maxId}, new RowMapper<ZTWorldLevelDto>(){
			@Override
			public ZTWorldLevelDto mapRow(ResultSet rs,int rowNum) throws SQLException{
				return buildZTWorldLevelDto(rs);
			}
		},rowSelection);
	}
	
	@Override
	public List<ZTWorldLevelDto> QueryWorldLevel(){
		return getJdbcTemplate().query(QUERY_WORLD_LEVEL_LIST, new RowMapper<ZTWorldLevelDto>(){
			@Override
			public ZTWorldLevelDto mapRow(ResultSet rs,int rowNum) throws SQLException{
				return buildZTWorldLevelDto(rs);
			}
		});
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	
	public ZTWorldLevelDto buildZTWorldLevelDto(ResultSet rs) throws SQLException{
		return new ZTWorldLevelDto(
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
