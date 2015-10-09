package com.imzhitu.admin.interact.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;
import com.imzhitu.admin.interact.dao.InteractWorldlevelListDao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class InteractWorldlevelListDaoImpl extends BaseDaoImpl implements InteractWorldlevelListDao {
	private static final String table=Admin.INTERACT_WORLD_LEVEL_LIST;
	private static final String QUERY_WORLD_LEVEL_LIST = " select wl.*,aui.user_name as operatorName from " 
			+ table 
			+ " wl left join hts_admin.admin_user_info aui on wl.operator_id=aui.id where 1=1 ";
	private static final String QUERY_WORLD_LEVEL_LIST_BY_MAX_ID = " select wl.*,aui.user_name as operatorName from " 
			+ table 
			+ " wl left join hts_admin.admin_user_info aui on wl.operator_id=aui.id"
			+ " where world_id<=?  ";
	private static final String GET_WORLD_LEVEL_LIST_COUNT_BY_MAX_ID = " select count(1) from " + table + " wl where world_id<=? ";
	private static final String ADD_WORLD_LEVEL_LIST = " insert into " + table + " (world_id,world_level_id,validity,date_add,date_modify,operator_id) values (?,?,?,?,?,?)";
	private static final String DEL_WORLD_LEVEL_LIST_BY_WIDS = " delete from " + table + " where world_id in ";
	private static final String UPDATE_WORLD_LEVEL_LIST = " update " + table + " set world_level_id=? , validity=?,date_modify=?,operator_id=? where world_id=?";
	private static final String CHECK_WORLD_LEVEL_LIST_IS_EXIST = " select count(1) from " + table + " where world_id=?";
	private static final String QUERY_WORLD_LEVEL_LIST_BY_WID =  " select wl.*,aui.user_name as operatorName from " 
			+ table 
			+ " wl left join hts_admin.admin_user_info aui on wl.operator_id=aui.id where world_id = ? ";
	
	@Override
	public List<InteractWorldLevelListDto> queryWorldLevelList(Map<String,Object> attr,RowSelection rowSelection){
		String sql = buildSQLByMap(QUERY_WORLD_LEVEL_LIST," order by wl.date_add desc",attr);
		return queryForPage(sql,new RowMapper<InteractWorldLevelListDto>(){
			@Override
			public InteractWorldLevelListDto mapRow(ResultSet rs , int num)throws SQLException{
				return buildWorldLevelList(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<InteractWorldLevelListDto> queryWorldLevelList(Integer maxId,Map<String,Object> attr,RowSelection rowSelection){
		String sql = buildSQLByMap(QUERY_WORLD_LEVEL_LIST_BY_MAX_ID," order by wl.date_add desc",attr);
		return queryForPage(sql,new Object[]{maxId},new RowMapper<InteractWorldLevelListDto>(){
			@Override
			public InteractWorldLevelListDto mapRow(ResultSet rs , int num)throws SQLException{
				return buildWorldLevelList(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public long getWorldLevelListCountByMaxId(Integer maxId,Map<String,Object> attr){
		String sql = buildSQLByMap(GET_WORLD_LEVEL_LIST_COUNT_BY_MAX_ID,null,attr);
		return getJdbcTemplate().queryForLong(sql, maxId);
	}
	
	@Override
	public void addWorldLevelList(Integer worldId,Integer  world_level_id,Integer validity,Date addDate,Date modifyDate,Integer operatorId){
		getMasterJdbcTemplate().update(ADD_WORLD_LEVEL_LIST, worldId,world_level_id,validity,addDate,modifyDate,operatorId);
	}
	
	@Override
	public void delWorldLevelListByWIds(Integer[] wIds){
		String sql = DEL_WORLD_LEVEL_LIST_BY_WIDS+SQLUtil.buildInSelection(wIds);
		getMasterJdbcTemplate().update(sql, (Object[])wIds);
	}
	
	@Override
	public void updateWorldLevelList(Integer worldId , Integer world_level_id,Integer validity,Date modifyDate,Integer operatorId){
		getMasterJdbcTemplate().update(UPDATE_WORLD_LEVEL_LIST, world_level_id,validity,modifyDate,operatorId,worldId);
	}
	
	
	@Override
	public boolean checkWorldLevelListIsExistByWId(Integer worldId){
		long  r = getJdbcTemplate().queryForLong(CHECK_WORLD_LEVEL_LIST_IS_EXIST, worldId);
		return r==0?false:true;
	}
	
	@Override
	public InteractWorldLevelListDto queryWorldLevelListByWid(Integer wid){
		try{
			return getMasterJdbcTemplate().queryForObject(QUERY_WORLD_LEVEL_LIST_BY_WID, new Object[]{wid}, new RowMapper<InteractWorldLevelListDto>(){
				@Override
				public InteractWorldLevelListDto mapRow(ResultSet rs,int num)throws SQLException{
					return buildWorldLevelList(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	private String buildSQLByMap(String sqlHead,String sqlNail,Map<String,Object>attr){
		String sql=sqlHead;
		if(attr.get("worldId")!= null)
			sql += " and wl.world_id="+(Integer)attr.get("worldId") + " ";
		Integer timeType = (Integer)(attr.get("timeType"));
		Date beginTime = (Date)(attr.get("beginTime"));
		Date endTime = (Date)(attr.get("endTime"));
		SimpleDateFormat  df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if((null != timeType) && (null != beginTime) && (null != endTime)){
			switch(timeType.intValue()){
			case 1:
				sql += " and wl.date_add ";break;
			case 2:
				sql += " and wl.date_modify ";break;
			}
			sql += " between str_to_date(\"" + df.format(beginTime) + "\",\"%Y-%m-%d %H:%i:%s\") and str_to_date(\"" + df.format(endTime)+ "\",\"%Y-%m-%d %H:%i:%s\") ";
		}
		if(sqlNail != null)
			sql += sqlNail;
		return sql;
			
	}
	
	private InteractWorldLevelListDto buildWorldLevelList(ResultSet rs) throws SQLException{
		return new InteractWorldLevelListDto(
				rs.getInt("world_id"),
				rs.getInt("world_level_id"),
				rs.getInt("validity"),
				(Date)rs.getObject("date_add"),
				(Date)rs.getObject("date_modify"),
				rs.getInt("operator_id"),
				rs.getString("operatorName")
				);
	}
}
