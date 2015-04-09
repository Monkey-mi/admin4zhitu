package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldComment;
import com.imzhitu.admin.interact.dao.InteractWorldlevelWorldCommentDao;

@Repository
public class InteractWorldlevelWorldCommentDaoImpl extends BaseDaoImpl implements InteractWorldlevelWorldCommentDao{
	private static final String table = Admin.INTERACT_WORLD_LEVEL_WORLD_COMMENT;
	private static final String QUERY_WORLD_COMMENT_BY_WID = " select * from " + table + " wc where wc.world_id=? and complete=0 ";
	private static final String ADD_WORLD_COMMENT = "insert into " + table + " (world_id,comment_id,complete,date_add) values (?,?,?,?)";
	private static final String UPDATE_COMPLETE = " update " + table + " wc set complete=? where world_id=?";
	
	@Override
	public List<InteractWorldlevelWorldComment> queryWorldlevelWorldCommentByWid(Integer worldId){
		try{
			return getJdbcTemplate().query(QUERY_WORLD_COMMENT_BY_WID, new RowMapper<InteractWorldlevelWorldComment>(){
				@Override
				public InteractWorldlevelWorldComment mapRow(ResultSet rs,int rowNum)throws SQLException{
					return buildWorldComment(rs);
				}
			}, worldId);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public void addWorldlevelWorldComment(Integer wid,Integer cid,Integer complete,Date dateAdd){
		getMasterJdbcTemplate().update(ADD_WORLD_COMMENT, wid,cid,complete,dateAdd);
	}
	
	@Override
	public void updateWorldlevelWorldCommentComplete(Integer wid,Integer complete){
		getMasterJdbcTemplate().update(UPDATE_COMPLETE,complete,wid);
	}
	
	private InteractWorldlevelWorldComment buildWorldComment(ResultSet rs)throws SQLException{
		return new InteractWorldlevelWorldComment(
				rs.getInt("id"),
				rs.getInt("world_id"),
				rs.getInt("comment_id"),
				rs.getInt("complete"),
				rs.getDate("date_add")
				);
	}

}
