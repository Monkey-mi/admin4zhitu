package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.InteractWorldLabelTreeDto;
import com.imzhitu.admin.common.pojo.WorldLabelCommentLabelDto;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;

public interface InteractWorldLabelCommentLabelService extends BaseService{
	/**
	 * 查询织图标签与评论标签关联列表
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void QueryULCLList(int maxId,int start,int limit,Map<String ,Object> jsonMap) throws Exception;
	
	/**
	 * 查询评论标签列表
	 * @return
	 */
	public List<InteractCommentLabel> QueryCommentLabel();
	
	/**
	 * 根据ids删除评论标签与制图标签关联
	 * @param idsStr
	 */
	public void DeleteWorldLabelCommentLabelByIds(String idsStr);
	
	/**
	 * 增加织图标签与评论标签关联
	 * @param worldLabelCommentLabelDto
	 * @throws Exception
	 */
	public void AddWorldLabelCommentLabelCount(WorldLabelCommentLabelDto worldLabelCommentLabelDto)throws Exception;
	/**
	 * 查询织图分组树
	 * @param type_id
	 * @param selected
	 * @param hasTotal
	 * @return
	 * @throws Exception
	 */
	public List<InteractWorldLabelTreeDto> GetWorldLabelTree() throws Exception;
}
