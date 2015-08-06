package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.InteractLikeFollowCommentLabel;

public interface InteractLikeFollowCommentLabelService {
	/**
	 * 添加
	 * @param labelId
	 * @throws Exception
	 */
	public void insertLikeFollowCommentLabel(Integer labelId)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteLikeFollowCommentLabel(String idsStr)throws Exception;
	
	/**
	 * 根据标签ids查询标签名称
	 * @param labelIdsStr
	 * @return
	 * @throws Exception
	 */
	public List<InteractLikeFollowCommentLabel> queryCommentLabelNameByLabelIds(String labelIdsStr)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param labelId
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryLikeFollowCommentLabel(Integer id,Integer labelId,Integer maxId,int page,int rows,Map<String, Object>jsonMap)throws Exception;
}
