package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractPlanComment;
import com.imzhitu.admin.interact.dao.InteractPlanCommentDao;

@Repository
public class InteractPlanCommentDaoImpl extends BaseDaoImpl implements InteractPlanCommentDao {
	private static final String table = Admin.INTERACT_PLAN_COMMENT;
	private static final String ADD_PLAN_COMMENT = " insert into " + table + " (group_id,content,date_add,date_modify,operator_id,valid,interact_comment_id) values (?,?,?,?,?,?,?)";
	private static final String DEL_PLAN_COMMENT_BY_IDS = " delete from " + table + " ipc where ipc.id in ";
	private static final String DEL_PLAN_COMMENT_BY_GROUP_ID = " delete from " + table + " ipc where ipc.group_id=?";
	private static final String UPDATE_PLAN_COMMENT_CONTENT_BY_ID = " update " + table + " ipc set ipc.content=? , ipc.group_id=? , ipc.valid=? , ipc.date_modify=?,ipc.operator_id=? where ipc.id=?";
	private static final String UPDATE_PLAN_COMMENT_VALID_BY_ID = " update " + table + " ipc set valid=?,operator_id=?,date_modify=? where id=?";
	private static final String QUERY_PLAN_COMMENT_FOR_PAGE = " select ipc.*,aui.user_name as operatorName,ipcl.description from " 
			+ table 
			+ " ipc left join hts_admin.admin_user_info aui on ipc.operator_id = aui.id "
			+ " left join hts_admin.interact_plan_comment_label ipcl on ipc.group_id=ipcl.id "
			+ " where ipcl.valid=1 and ipc.valid=1 ";
	private static final String QUERY_PLAN_COMMENT_FOR_PAGE_BY_MAX_ID = " select ipc.*,aui.user_name as operatorName,ipcl.description from "
			+ table
			+ " ipc left join hts_admin.admin_user_info aui on ipc.operator_id = aui.id "
			+ " left join hts_admin.interact_plan_comment_label ipcl on ipc.group_id=ipcl.id "
			+ " where ipcl.valid=1 and ipc.valid=1 and ipc.id<=? ";
	private static final String QUERY_PLAN_COMMENT_TOTAL_COUNT_BY_MAX_ID = " select count(1) from " 
			+ table
			+ " ipc left join hts_admin.interact_plan_comment_label ipcl on ipc.group_id=ipcl.id "
			+ "  where ipc.id<=? and ipc.valid=1 and ipcl.valid=1 ";
	private static final String QUERY_PLAN_COMMENT_COUNT_BY_GROUP_ID = " select count(1) from " + table + " ipc where ipc.group_id=? and ipc.valid=1 ";
	
	@Override
	public void addPlanComment(Integer groupId,String content,Date addDate , Date modifyDate,Integer operatorId,Integer valid,Integer interactCommentId){
		getMasterJdbcTemplate().update(ADD_PLAN_COMMENT, groupId,content,addDate,modifyDate,operatorId,valid,interactCommentId);
	}
	
	@Override
	public void delPlanCommentByIds(Integer[] ids){
		String sql = DEL_PLAN_COMMENT_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	@Override
	public void delPlanCommentByGroupId(Integer groupId){
		getMasterJdbcTemplate().update(DEL_PLAN_COMMENT_BY_GROUP_ID, groupId);
	}
	
	@Override
	public void updateCommentContentById(Integer id,String content,Integer groupId,Integer valid,Date modifyDate,Integer operatorId){
		getMasterJdbcTemplate().update(UPDATE_PLAN_COMMENT_CONTENT_BY_ID,content,groupId,valid,modifyDate,operatorId,id);
	}
	
	@Override
	public void updatePlanCommentValidById(Integer id,Integer valid,Integer operatorId,Date modifyDate ){
		getMasterJdbcTemplate().update(UPDATE_PLAN_COMMENT_VALID_BY_ID,valid,operatorId,modifyDate,id);
	}
	
	/**
	 * 分页查询列表
	 * @param rowSelection
	 * @return
	 */
	@Override
	public List<InteractPlanComment> queryInteractPlanComment(Map<String,Object>attr,RowSelection rowSelection){
		String sql = buildSql(attr, QUERY_PLAN_COMMENT_FOR_PAGE, " order by ipc.id desc");
		return queryForPage(sql, new RowMapper<InteractPlanComment>(){
			@Override
			public InteractPlanComment mapRow(ResultSet rs , int rowNum)throws SQLException{
				return buildInteractPlanComment(rs);
			}
		}, rowSelection);
	}
	
	/**
	 * 分页查询列表
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	@Override
	public List<InteractPlanComment> queryInteractPlanCommentByMaxId(Integer maxId,Map<String,Object>attr,RowSelection rowSelection){
		String sql = buildSql(attr, QUERY_PLAN_COMMENT_FOR_PAGE_BY_MAX_ID, " order by ipc.id desc");
		return queryForPage(sql,new Object[]{maxId}, new RowMapper<InteractPlanComment>(){
			@Override
			public InteractPlanComment mapRow(ResultSet rs , int rowNum)throws SQLException{
				return buildInteractPlanComment(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public long queryInteractPlanCommentTotalCountByMaxId(Integer maxId,Map<String,Object>attr){
		String sql = buildSql(attr, QUERY_PLAN_COMMENT_TOTAL_COUNT_BY_MAX_ID, null);
		return getJdbcTemplate().queryForLong(sql, maxId);
	}
	
	
	@Override
	public long queryInteractPlanCommentCountByGroupId(Integer groupId){
		return getJdbcTemplate().queryForLong(QUERY_PLAN_COMMENT_COUNT_BY_GROUP_ID, groupId);
	}
	
	private String buildSql(Map<String,Object>attr,String sqlHead,String sqlNail){
		String sql =sqlHead;
		if(attr.get("groupId") != null)
			sql += " and ipc.group_id="+ (Integer)attr.get("groupId") + " ";
		if(sqlNail !=null)
			sql += sqlNail;
		return sql;
	}
	
	private InteractPlanComment buildInteractPlanComment(ResultSet rs)throws SQLException{
		return new InteractPlanComment(
				rs.getInt("id"),
				rs.getInt("group_id"),
				rs.getString("description"),
				rs.getString("content"),
				(Date)rs.getObject("date_add"),
				(Date)rs.getObject("date_modify"),
				rs.getInt("operator_id"),
				rs.getString("operatorName"),
				rs.getInt("valid"),
				rs.getInt("interact_comment_id")
				);
	}
}
