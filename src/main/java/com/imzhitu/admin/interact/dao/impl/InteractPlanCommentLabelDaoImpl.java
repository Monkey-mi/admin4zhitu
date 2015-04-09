package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;
import com.imzhitu.admin.interact.dao.InteractPlanCommentLabelDao;

@Repository
public class InteractPlanCommentLabelDaoImpl extends BaseDaoImpl implements InteractPlanCommentLabelDao{
	public static final String table = Admin.INTERACT_PLAN_COMMENT_LABEL;
	
	public static final String ADD_PLAN_COMMENT_LABEL = " insert into " + table + " (description,start_time,deadline,work_start_time,work_end_time,valid,date_add,date_modify,operator_id,group_id) values (?,?,?,?,?,?,?,?,?,?)";
	public static final String DEL_PLAN_COMMENT_LABEL_BY_IDS = " delete from " + table + " ipcl where ipcl.id in ";
	public static final String DEL_PLAN_COMMENT_LABEL_BY_GROUP_ID = " delete from " + table + " ipcl where ipcl.group_id=? ";
	public static final String UPDATE_PLAN_COMMENT_LABEL_BY_ID = " update " + table + " set description=?,start_time=,deadline=?,work_start_time=?,work_end_time=?,valid=?,operator_id=?,date_modify=? where id=?";
	public static final String UPDATE_PLAN_COMMENT_LABEL_VALID_BY_ID = " update " + table + " set valid=? ,operator_id=?,date_modify=? where id=?";
	public static final String QUERY_PLAN_COMMENT_LABEL_BY_ID = " select ipcl.*,aui.user_name as operatorName from " + table + " ipcl left join hts_admin.admin_user_info aui on ipcl.operator_id=aui.id where ipcl.id=? and ipcl.valid=1 ";
	public static final String QUERY_PLAN_COMMENT_LABEL_COUNT_BY_MAXID = " select count(1) from " + table + " ipcl where ipcl.id<= ? and ipcl.valid=1";
	public static final String QUERY_PLAN_COMMENT_LABEL_LIST_FOR_PAGE = " select ipcl.*,aui.user_name as operatorName from " + table + " ipcl left join hts_admin.admin_user_info aui on ipcl.operator_id=aui.id where ipcl.valid=1 order by ipcl.id desc";
	public static final String QUERY_PLAN_COMMENT_LABEL_LIST_FOR_PAGE_BY_MAX_ID = 
			"select ipcl.*,aui.user_name as operatorName from " + table + " ipcl left join hts_admin.admin_user_info aui on ipcl.operator_id=aui.id where ipcl.id<=? and ipcl.valid=1 order by ipcl.id desc";
	public static final String QUERY_PLAN_COMMENT_LABEL_LIST_BY_DATE_AND_TIME = " select ipcl.*,aui.user_name as operatorName from " + table + " ipcl left join  hts_admin.admin_user_info aui on ipcl.operator_id=aui.id where ipcl.start_time<=? and ipcl.deadline>=? and ipcl.work_start_time<=? and ipcl.work_end_time>=? and ipcl.valid=1";
	
	@Override
	public void addPlanCommentLabel(String description,Date startTime,Date deadline,Date workStartTime,Date workEndTime,Integer valid,Date addDate,Date modifyDate,Integer operatorId,Integer groupId ){
		getMasterJdbcTemplate().update(ADD_PLAN_COMMENT_LABEL, description,startTime,deadline,workStartTime,workEndTime,valid,addDate,modifyDate,operatorId,groupId);
	}
	
	@Override
	public void delPlanCommentLabelByIds(Integer[]ids){
		String sql = DEL_PLAN_COMMENT_LABEL_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql , (Object[])ids);
	}
	
	@Override
	public void delPlanCommentLabelByGroupId(Integer groupId){
		getMasterJdbcTemplate().update(DEL_PLAN_COMMENT_LABEL_BY_GROUP_ID, groupId);
	}
	
	@Override
	public void updatePlanCommentLabelById(Integer id,String description,Date startTime,Date deadline,Time workStartTime,Time workEndTime,Integer  valid,Integer operatorId,Date modifyDate){
		getMasterJdbcTemplate().update(UPDATE_PLAN_COMMENT_LABEL_BY_ID, description,startTime,deadline,workStartTime,workEndTime,valid,operatorId,modifyDate,id);
	}
	
	@Override
	public void updatePlanCommentValidById(Integer valid,Integer id,Integer operatorId,Date modifyDate){
		getMasterJdbcTemplate().update(UPDATE_PLAN_COMMENT_LABEL_VALID_BY_ID, valid,operatorId,modifyDate,id);
	}
	
	@Override
	public InteractPlanCommentLabel queryInteractPlanCommentLabelById(Integer id){
		try{
			return getJdbcTemplate().queryForObject(QUERY_PLAN_COMMENT_LABEL_BY_ID, new Object[]{id},new RowMapper<InteractPlanCommentLabel>(){
				@Override
				public InteractPlanCommentLabel mapRow(ResultSet rs , int rowNum)throws SQLException{
					return buildInteractPlanCommentLabel(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	
	@Override
	public long queryInteractPlanCommentLabelCountByMaxId(Integer maxId){
		return getJdbcTemplate().queryForLong(QUERY_PLAN_COMMENT_LABEL_COUNT_BY_MAXID, maxId);
	}
	
	@Override
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel(RowSelection rowSelection){
		return queryForPage(QUERY_PLAN_COMMENT_LABEL_LIST_FOR_PAGE, new RowMapper<InteractPlanCommentLabel>(){
			@Override
			public InteractPlanCommentLabel mapRow(ResultSet rs,int rowNum)throws SQLException{
				return buildInteractPlanCommentLabel(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByMaxId(Integer maxId,RowSelection rowSelection){
		return queryForPage(QUERY_PLAN_COMMENT_LABEL_LIST_FOR_PAGE_BY_MAX_ID,new Object[]{maxId},new RowMapper<InteractPlanCommentLabel>(){
			@Override
			public InteractPlanCommentLabel mapRow(ResultSet rs,int rowNum)throws SQLException{
				return buildInteractPlanCommentLabel(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel(){
		try{
			return getJdbcTemplate().query(QUERY_PLAN_COMMENT_LABEL_LIST_FOR_PAGE, new RowMapper<InteractPlanCommentLabel>(){
				@Override
				public InteractPlanCommentLabel mapRow(ResultSet rs,int rowNum)throws SQLException{
					return buildInteractPlanCommentLabel(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByDateAndTime(Date date,Date time){
		try{
			return getJdbcTemplate().query(QUERY_PLAN_COMMENT_LABEL_LIST_BY_DATE_AND_TIME, new Object[]{date,date,time,time}, new RowMapper<InteractPlanCommentLabel>(){
				@Override
				public InteractPlanCommentLabel mapRow(ResultSet rs,int rowNum)throws SQLException{
					return buildInteractPlanCommentLabel(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	/**
	 * 构建计划评论标签
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private InteractPlanCommentLabel buildInteractPlanCommentLabel(ResultSet rs)throws SQLException{
		return  new InteractPlanCommentLabel(
				rs.getInt("id"),
				rs.getString("description"),
				(Date)rs.getObject("start_time"),
				(Date)rs.getObject("deadline"),
				(Date)rs.getObject("work_start_time"),
				(Date)rs.getObject("work_end_time"),
				rs.getInt("valid"),
				(Date)rs.getObject("date_add"),
				(Date)rs.getObject("date_modify"),
				rs.getInt("operator_id"),
				rs.getString("operatorName"),
				rs.getInt("group_id")
				);
	}

}
