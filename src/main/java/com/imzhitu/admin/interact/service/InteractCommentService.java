package com.imzhitu.admin.interact.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.InteractComment;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractCommentLabelTree;

public interface InteractCommentService extends BaseService {

	/**
	 * 批量保存评论
	 * 
	 * @param file
	 * @param labelId
	 */
	public void batchSaveComment(File file, Integer labelId) throws Exception;
	
	/**
	 * 保存评论
	 * 
	 * @param file
	 * @param content
	 * @param labelId
	 * @throws Exception
	 */
	public void saveComment(File file, String content, Integer labelId) throws Exception;
	
	/**
	 * 保存评论
	 * 
	 * @param content	评论内容
	 * @param labelId	所属评论标签id
	 * @return	评论内容表主键id
	 * @throws Exception
	 * @author zhangbo	2015年9月14日
	 */
	public Integer saveComment(String content, Integer labelId) throws Exception;
	
	/**
	 * 
	 * @param labelId
	 * @param comment
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildComments(int labelId, String comment, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 获取指定大小的随机评论列表
	 * 
	 * @param labelId
	 * @param size
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRandomCommentIds(int labelId, int size) throws Exception;
	
	/**
	 * 根据id删除评论
	 * 
	 * @param idsStr
	 */
	public void deleteCommentByIds(String idsStr);
	
	/**
	 * 根据id获取评论
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public InteractComment getCommentById(Integer id) throws Exception;
	
	/**
	 * 更新评论
	 * 
	 * @param id
	 * @param content
	 * @param labelId
	 * @throws Exception
	 */
	public void updateComment(Integer id, String content, Integer labelId)
			throws Exception;
	
	/**
	 * 构建标签列表
	 * 
	 * @throws Exception
	 */
	public List<InteractCommentLabel> getAllLabels() throws Exception;
	
	/**
	 * 构建标签列表
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param groupId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabel(Integer maxId, int start, int limit, Integer groupId, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 构建所有标签分组列表
	 * 
	 * @throws Exception
	 */
	public List<InteractCommentLabel> getAllLabelGroup() throws Exception;
	
	/**
	 * 构建标签分组列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabelGroup(Integer maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存标签
	 * @param labelName
	 * @param groupId
	 * @throws Exception
	 */
	public void saveLabel(String labelName, Integer groupId) throws Exception;
	
	/**
	 * 
	 * @param labelJSON
	 * @throws Exception
	 */
	public void updateLabelByJSON(String labelJSON) throws Exception;
	
	/**
	 * 批量删除标签分组
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteLabelGroups(String idsStr) throws Exception;
	
	
	/**
	 * 批量删除标签
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteLabels(String idsStr) throws Exception;
	
	/**
	 * 构建标签树列表
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List<InteractCommentLabelTree> getLabelTree(Integer groupId, Integer selected, Boolean hasTotal) throws Exception;
	
	/**
	 * 更新评论内容
	 * @param content
	 * @param id
	 * @throws Exception
	 */
	public void updateCommentContentById(String content,Integer id)throws Exception;
	
	/**
	 * 更新评论内容
	 * @param content
	 * @param id
	 * @throws Exception
	 */
	public void updateCommentContentByJSON(String jsString)throws Exception;
	/**
	 * 获取评论标签树
	 * @return
	 * @throws Exception
	 */
	public List<InteractCommentLabelTree> getAllLabelTree()throws Exception;
	
	/*
	 * 获取标签中的非一级标签
	 * add by mishengliang
	 * */
	
	public List<InteractCommentLabel> getAllCommentLableUse() throws Exception;
}
