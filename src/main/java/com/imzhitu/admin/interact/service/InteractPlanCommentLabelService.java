package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;

public interface InteractPlanCommentLabelService {
	/**
	 * 增加计划评论标签
	 * @param groupId
	 * @param description
	 * @param startTime
	 * @param deadline
	 * @param workStartTime
	 * @param workEndTime
	 * @param operatorId
	 * @param valid
	 * @throws Exception
	 */
	public void addPlanCommentLabel(Integer groupId,String description,Date startTime,Date deadline,String workStartTime,String workEndTime,Integer operatorId,Integer valid)throws Exception;

	/**
	 * 查询计划评论标签
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryPlanCommentLabelForTable(Integer  maxId, Integer page, Integer rows,Map<String , Object> jsonMap)throws Exception;
	
	/**
	 * 删除计划评论标签
	 * @param ids
	 * @param operatorId
	 * @throws Exception
	 */
	public void delPlanCommentLabelByIds(String ids,Integer operatorId)throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel()throws Exception;
	
	/**
	 * 根据有效时间和工作时间来查询计划评论标签
	 * @param date
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByDateAndTime(Date date,Date time)throws Exception;
}
