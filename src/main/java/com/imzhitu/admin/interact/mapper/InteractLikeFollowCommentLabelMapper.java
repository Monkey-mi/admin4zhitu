package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractLikeFollowCommentLabel;

/**
 * 互赞互粉对应的评论标签
 * @author demonsader
 *
 */
public interface InteractLikeFollowCommentLabelMapper {
	/**
	 * 添加
	 * @param dto
	 */
	public void insertLikeFollowCommentLabel(InteractLikeFollowCommentLabel dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteLikeFollowCommentLabel(Integer[] ids);
	
	/**
	 * 根据标签ids查询标签名称
	 * @param labelIds
	 * @return
	 */
	public List<InteractLikeFollowCommentLabel> queryCommentLabelNameByLabelIds(Integer[] labelIds);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public List<InteractLikeFollowCommentLabel> queryLikeFollowCommentLabel(InteractLikeFollowCommentLabel dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long queryLikeFollowCommentLabelTotalCount(InteractLikeFollowCommentLabel dto);
}