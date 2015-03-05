package com.imzhitu.admin.interact.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.InteractPlanComment;

public interface InteractPlanCommentService  extends BaseService{
	/**
	 * 更新计划评论的有效性
	 * @param id
	 * @param valid
	 * @param operatorId
	 * @throws Exception
	 */
	public void  updatePlanCommentValidById(Integer id,Integer valid,Integer operatorId)throws Exception;
	
	/**
	 * 增加计划评论
	 * @param id
	 * @param groupId
	 * @param content
	 * @param valid
	 * @param operatorId
	 * @throws Exception
	 */
	public void addPlanComment(File file,Integer groupId,String content,Integer valid,Integer operatorId)throws Exception;
	
	/**
	 * 删除计划评论
	 * @param ids
	 * @param operatorId
	 * @throws Exception
	 */
	public void delPlanCommentByIds(String ids,Integer operatorId)throws Exception;
	
	/**
	 * 查询计划评论
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryPlanCommentForTable(Integer  maxId,Integer groupId, String content,Integer page, Integer rows,Map<String , Object> jsonMap)throws Exception;
	
	/**
	 * 根据rowjson来更新计划评论
	 * @param rowJson
	 * @param operatorId
	 * @throws Exception
	 */
	public void updatePlanCommentByRowJson(String rowJson,Integer operatorId)throws Exception;
	
	/**
	 * 根据标签组id随机N个评论
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public List<InteractPlanComment> queryNRandomPlanCommentByGroupId(Integer n,Integer groupId)throws Exception;
	
	/**
	 * 批量导入
	 * @param file
	 * @param groupId
	 * @param operatorId
	 * @param valid
	 * @throws Exception
	 */
	public void batchAddPlanComment(File file,Integer groupId,Integer operatorId,Integer valid)throws Exception;
}
