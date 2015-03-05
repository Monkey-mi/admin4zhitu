package com.imzhitu.admin.interact.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldLabel;
import com.imzhitu.admin.interact.dao.InteractWorldlevelWorldLabelDao;

import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class InteractWorldlevelWorldLabelDaoImpl extends BaseDaoImpl implements InteractWorldlevelWorldLabelDao{
	private static final String table = Admin.INTERACT_WORLD_LEVEL_WORLD_LABEL;
	private static final String QUERY_WORLD_LABEL_BY_WID = "select * from " + table + " where world_id=? and complete=0 ";
	private static final String ADD_WORLD_LABEL = " insert into " + table + " (world_id,label_id,complete,date_add) values(?,?,?,?)";
	private static final String UPDATE_COMPLETE = " update " + table + " set complete=? where world_id=?";
	
	public List<InteractWorldlevelWorldLabel> queryWorldlevelWorldLabelByWid(Integer wid){
		try{
			return getJdbcTemplate().query(QUERY_WORLD_LABEL_BY_WID, new Object[]{wid}, new RowMapper<InteractWorldlevelWorldLabel>(){
				@Override
				public InteractWorldlevelWorldLabel mapRow(ResultSet rs, int rowNum)throws SQLException{
					return buildInteractWorldlevelWorldLabel(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public void addWorldlevelWorldLabel(Integer wid,Integer lid,Integer complete,Date dateAdd){
		getJdbcTemplate().update(ADD_WORLD_LABEL, wid,lid,complete,dateAdd);
	}
	
	public void updateWorldlevelWorldLabelComplete(Integer wid,Integer complete){
		getJdbcTemplate().update(UPDATE_COMPLETE,complete,wid);
	}
	
	private InteractWorldlevelWorldLabel buildInteractWorldlevelWorldLabel(ResultSet rs)throws SQLException{
		return new InteractWorldlevelWorldLabel(
				rs.getInt("id"),
				rs.getInt("world_id"),
				rs.getInt("label_id"),
				rs.getInt("complete"),
				rs.getDate("date_add")
				);
	}
}
