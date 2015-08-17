package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractLikeFollowRecord;

/**
 * 互粉互赞记录表
 * @author zxx
 *
 */
public interface InteractLikeFollowRecordMapper {
	/**
	 * 添加
	 * @param dto
	 */
	public void insertLikeFollowRecord(InteractLikeFollowRecord dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteLikeFollowRecord(Integer[] ids);
	
	/**
	 * 批量更新完成情况
	 * @param complete
	 * @param ids
	 */
	public void batchUpdateLikeFollowRecord(Integer complete,Integer[]ids);
	
	/**
	 * 查询互动互赞
	 * @param dto
	 */
	public List<InteractLikeFollowRecord> queryLikeFollowRecord(InteractLikeFollowRecord dto);

	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	public long queryLikeFollowRecordCount(InteractLikeFollowRecord dto);
}
