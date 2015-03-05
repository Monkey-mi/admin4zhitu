package com.imzhitu.admin.interact.dao;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldLabelTreeDto;
import com.imzhitu.admin.common.pojo.WorldLabelCommentLabelDto;
import com.imzhitu.admin.common.pojo.UserLabelDto;

public interface InteractWorldLabelCommentLabelDao  extends BaseDao{
	/**
	 * 查询织图标签与评论标签关联列表
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<WorldLabelCommentLabelDto> QueryWorldLabelCommentLabel(Integer maxId, RowSelection rowSelection);
	
	/**
	 * 查询织图标签与评论标签关联列表
	 * @param rowSelection
	 * @return
	 */
	public List<WorldLabelCommentLabelDto> QueryWorldLabelCommentLabel(RowSelection rowSelection);
	
	/**
	 * 获取织图标签与评论标签关联列表总数
	 * @param maxId
	 * @return
	 */
	public long GetWorldLabelCommentLabelCount(Integer maxId);
	
	/**
	 * 根据ids删除织图标签与评论标签关联
	 * @param ids
	 */
	public void DeleteWorldLabelCommentLabelByIds(Integer[] ids);
	
	/**
	 * 根据织图标签id查询织图标签与评论标签关联
	 * @param uId
	 * @return
	 */
	public WorldLabelCommentLabelDto QueryWorldLabelCommentLabelByUId(Integer uId);
	
	/**
	 * 增加织图标签与评论标签关联
	 * @param worldLabelCommentLabelDto
	 */
	public void AddWorldLabelCommentLabelCount(WorldLabelCommentLabelDto worldLabelCommentLabelDto);
	
	/**
	 * 查询织图标签列表to Tree
	 */
	public List<InteractWorldLabelTreeDto> QueryWorldLabelToTreeByTypeId(Integer typeId);
	
	/**
	 * 查询织图类型列表to Tree
	 */
	public List<InteractWorldLabelTreeDto> QueryAllWorldTypeToTree();
}
