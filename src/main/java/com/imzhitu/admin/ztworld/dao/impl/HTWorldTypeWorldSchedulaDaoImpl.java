package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.HTWorldTypeWorldSchedulaDto;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeWorldSchedulaDao;

/**
 * 广场分类计划dao
 * @author zxx
 * @time 2014年5月13日 15:32:17
 */
@Repository
public class HTWorldTypeWorldSchedulaDaoImpl extends BaseDaoImpl implements HTWorldTypeWorldSchedulaDao{
	private static final String table = Admin.INTERACT_TYPE_WORLD_SCHEDULA;
	private static final String ADD_TYPE_WORLD_SCHEDULA = " insert into " + table + " (world_id,schedula,date_add,date_modify,operator_id,complete) values (?,?,?,?,?,?)";
	private static final String DEL_TYPE_WORLD_SCHEDULA_BY_IDS = " delete from " + table + " where world_id in";
	private static final String QUERY_TYPE_WORLD_SCHEDULA = " select ts.*,aui.user_name as operatorName from " 
			+ table + " ts left join hts_admin.admin_user_info aui on ts.operator_id=aui.id where 1=1";
	private static final String QUERY_TYPE_WORLD_SCHEDULA_BY_MAXSCHEDULA = " select ts.*,,aui.user_name as operatorName from " 
			+ table + " ts left join hts_admin.admin_user_info aui on ts.operator_id=aui.id where ts.schedula <=? ";
	private static final String QUERY_TYPE_WORLD_SCHEDULA_COUNT_BY_MAXSCHEDULA = " select count(1) from " + table + " ts where ts.schedula<=? ";
	private static final String QUERY_TYPE_WORLD_SCHEDULA_COUNT = " select count(1) from " + table + " ts where 1=1 ";
	private static final String UPDATE_COMPLETE_BY_WORLD_IDS = " update " + table + " set complete=? where world_id in ";
	private static final String GET_WORLD_ID_BY_SCHEDULA = " select ts.world_id from " 
			+ table + " as ts where ts.complete=0 and ts.schedula between ? and ? order by ts.schedula asc ";
	private static final String CHECK_IS_EXIST_BY_WID = " select count(1) from " + table + " where world_id=?";
	private static final String UPDATE_TYPE_WORLD_SCHEDULA = " update " + table + " set schedula=?,complete=?,date_modify=?,operator_id=? where world_id=?";
	@Override
	public void addTypeWorldSchedula(HTWorldTypeWorldSchedulaDto dto){
		getJdbcTemplate().update(ADD_TYPE_WORLD_SCHEDULA,
				dto.getType_world_id(),
				dto.getSchedula(),
				dto.getAddDate(),
				dto.getModifyDate(),
				dto.getOperatorId(),
				dto.getComplete());
	}
	
	@Override 
	public void delTypeWorldSchedulaByIds(Integer[] ids){
		String sql = DEL_TYPE_WORLD_SCHEDULA_BY_IDS +SQLUtil.buildInSelection(ids);
		getJdbcTemplate().update(sql, (Object[])ids);
	}
	
	
	@Override
	public List<HTWorldTypeWorldSchedulaDto> queryTypeWorldSchedula(Map<String , Object> attr,RowSelection rowSelection){
		String sql = buildSqlByMap(QUERY_TYPE_WORLD_SCHEDULA, " order by ts.schedula desc ", attr);
		return queryForPage(sql, new RowMapper<HTWorldTypeWorldSchedulaDto>(){
			@Override
			public HTWorldTypeWorldSchedulaDto mapRow(ResultSet rs, int num)throws SQLException{
				return buildTypeWorldSchedula(rs);
			}
		}, rowSelection);
	}
	@Override
	public List<HTWorldTypeWorldSchedulaDto> queryTypeWorldSchedula(Date maxSchedula,Map<String , Object> attr,RowSelection rowSelection){
		String sql = buildSqlByMap(QUERY_TYPE_WORLD_SCHEDULA_BY_MAXSCHEDULA, " order by ts.schedula desc", attr);
		return queryForPage(sql,new Object[]{maxSchedula},new RowMapper<HTWorldTypeWorldSchedulaDto>(){
			@Override
			public HTWorldTypeWorldSchedulaDto mapRow(ResultSet rs, int num)throws SQLException{
				return buildTypeWorldSchedula(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public long queryTypeWorldSchedulaCountByMaxId(Date maxSchedula,Map<String , Object> attr){
		String sql = buildSqlByMap(QUERY_TYPE_WORLD_SCHEDULA_COUNT_BY_MAXSCHEDULA, null, attr);
		return getJdbcTemplate().queryForLong(sql, maxSchedula);
	}
	
	@Override
	public long queryTypeWorldSchedulaCountByMaxId(Map<String , Object> attr){
		String sql = buildSqlByMap(QUERY_TYPE_WORLD_SCHEDULA_COUNT, null, attr);
		return getJdbcTemplate().queryForLong(sql);
	}
	
	@Override
	public void updateCompleteByIds(Integer[] ids,Integer complete){
		String sql = UPDATE_COMPLETE_BY_WORLD_IDS + SQLUtil.buildInSelection(ids);
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{complete}, true);
		getJdbcTemplate().update(sql,args);
	}
	
	@Override
	public List<Integer> getWorldIdBySchedula(Date begin,Date end){
		return getJdbcTemplate().queryForList(GET_WORLD_ID_BY_SCHEDULA,new Object[]{begin,end}, Integer.class);
	}
	
	@Override
	public boolean checkIsExistByWid(Integer wid){
		long r = getJdbcTemplate().queryForLong(CHECK_IS_EXIST_BY_WID, wid);
		return r!=0;
	}
	
	@Override
	public void updateTypeWorldSchedula(Integer world_id,Date schedula,Date modifyDate,Integer operatorId,Integer complete){
		getJdbcTemplate().update(UPDATE_TYPE_WORLD_SCHEDULA, schedula,complete,modifyDate,operatorId,world_id);
	}
	
	/**
	 * 结果集里构建dto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private HTWorldTypeWorldSchedulaDto buildTypeWorldSchedula(ResultSet rs)throws SQLException{
		return new HTWorldTypeWorldSchedulaDto(rs.getInt("world_id"),
				(Date)rs.getObject("schedula"),
				(Date)rs.getObject("date_add"),
				(Date)rs.getObject("date_modify"),
				rs.getInt("operator_id"),
				rs.getString("operatorName"),
				rs.getInt("complete"));
	}
	
	/**
	 * 构建sql
	 */
	private String buildSqlByMap(String sqlHead,String sqlNail,Map<String,Object>attr){
		String sql =sqlHead;
		if(attr.get("worldId")!=null)
			sql += " and ts.world_id= " + (Integer)(attr.get("worldId")) + " ";
		Integer timeType = (Integer)(attr.get("timeType"));
		Date beginTime = (Date)(attr.get("beginTime"));
		Date endTime = (Date)(attr.get("endTime"));
		SimpleDateFormat  df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if((null != timeType) && (null != beginTime) && (null != endTime)){
			switch(timeType.intValue()){
			case 0:
				sql += " and ts.schedula ";break;
			case 1:
				sql += " and ts.date_add ";break;
			case 2:
				sql += " and ts.date_modify ";break;
			}
			sql += " between str_to_date(\"" + df.format(beginTime) + "\",\"%Y-%m-%d %H:%i:%s\") and str_to_date(\"" + df.format(endTime)+ "\",\"%Y-%m-%d %H:%i:%s\") ";
		}
		if(null != sqlNail)
			sql += sqlNail;
		return sql;
	}
}
