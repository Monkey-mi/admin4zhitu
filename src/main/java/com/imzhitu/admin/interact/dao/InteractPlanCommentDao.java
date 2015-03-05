package com.imzhitu.admin.interact.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractPlanComment;

public interface InteractPlanCommentDao extends BaseDao{
	/**
	 * 增加计划评论
	 * @param groupId
	 * @param content
	 * @param addDate
	 * @param modifyDate
	 * @param operatorId
	 */
	public void addPlanComment(Integer groupId,String content,Date addDate , Date modifyDate,Integer operatorId,Integer valid,Integer interactCommentId);
	
	/**
	 * 根据ids删除计划评论
	 * @param ids
	 */
	public void delPlanCommentByIds(Integer[] ids);
	
	/**
	 * 根据组id来删除计划评论
	 * @param groupId
	 */
	public void delPlanCommentByGroupId(Integer groupId);
	
	/**
	 * 更新评论内容
	 * @param id
	 * @param modifyDate
	 */
	public void updateCommentContentById(Integer id,String content,Integer groupId,Integer valid,Date modifyDate,Integer operatorId);
	
	/**
	 * 更新有效性 
	 * @param id
	 * @param valid
	 * @param operatorId
	 * @param modifyDate
	 */
	public void updatePlanCommentValidById(Integer id,Integer valid,Integer operatorId,Date modifyDate );
	
	/**
	 * 分页查询列表
	 * @param rowSelection
	 * @return
	 */
	public List<InteractPlanComment> queryInteractPlanComment(Map<String,Object>attr,RowSelection rowSelection);
	
	/**
	 * 分页查询列表
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractPlanComment> queryInteractPlanCommentByMaxId(Integer maxId,Map<String,Object>attr,RowSelection rowSelection);
	
	/**
	 * 查询总数
	 * @param maxId
	 * @return
	 */
	public long queryInteractPlanCommentTotalCountByMaxId(Integer maxId,Map<String,Object>attr);
	
	/**
	 * 根据组id查询改组标签中的评论总数
	 * @param groupId
	 * @return
	 */
	public long queryInteractPlanCommentCountByGroupId(Integer groupId);
	
}
