package com.imzhitu.admin.interact.dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;


public interface InteractPlanCommentLabelDao extends BaseDao{

	/**
	 * 增加计划评论标签
	 * @param description
	 * @param startTime
	 * @param deadline
	 * @param workTime
	 * @param valid
	 * @param addDate
	 * @param modifyDate
	 * @param operatorId
	 * @param groupId
	 */
	public void addPlanCommentLabel(String description,Date startTime,Date deadline,Date workStartTime,Date workEndTime,Integer valid,Date addDate,Date modifyDate,Integer operatorId,Integer groupId );
	
	/**
	 * 根据ids删除计划评论标签
	 * @param ids
	 */
	public void delPlanCommentLabelByIds(Integer[]ids);
	
	/**
	 * 根据组id来删除计划评论标签
	 * @param groupId
	 */
	public void delPlanCommentLabelByGroupId(Integer groupId);
	
	/**
	 * 跟新计划评论标签
	 * @param id
	 * @param Description
	 * @param startTime
	 * @param deadline
	 * @param workTime
	 * @param valid
	 * @param operatorId
	 * @param modifyDate
	 */
	public void updatePlanCommentLabelById(Integer id,String Description,Date startTime,Date deadline,Time workStartTime,Time workEndTime,Integer  valid,Integer operatorId,Date modifyDate);
	
	/**
	 * 更新计划评论标签的有效性
	 * @param valid
	 * @param id
	 * @param operatorId
	 * @param modifyDate
	 */
	public void updatePlanCommentValidById(Integer valid,Integer id,Integer operatorId,Date modifyDate);

	/**
	 * 根据id来查询计划评论标签
	 * @param id
	 * @return
	 */
	public InteractPlanCommentLabel queryInteractPlanCommentLabelById(Integer id);
	
	/**
	 * 根据最大id来查询总数
	 * @param maxId
	 * @return
	 */
	public long queryInteractPlanCommentLabelCountByMaxId(Integer maxId);
	
	/**
	 * 分页查询列表
	 * @param rowSelection
	 * @return
	 */
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel(RowSelection rowSelection);
	
	/**
	 * 分页查询列表
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByMaxId(Integer maxId,RowSelection rowSelection);
	
	/**
	 * 查询计划评论标签列表
	 * @return
	 */
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel();
	
	/**
	 * 根据有效时间和工作时间来查询计划评论标签
	 * @param date
	 * @param time
	 * @return
	 */
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByDateAndTime(Date date,Date time);
}
