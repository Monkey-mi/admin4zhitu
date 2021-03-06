package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
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
	@DataSource("master")
	public void insertLikeFollowRecord(InteractLikeFollowRecord dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteLikeFollowRecord(Integer[] ids);
	
	/**
	 * 批量更新完成情况
	 * @param complete
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateLikeFollowRecord(@Param("complete")Integer complete,@Param("ids")Integer[]ids);
	
	/**
	 * 查询互动互赞
	 * @param dto
	 */
	@DataSource("slave")
	public List<InteractLikeFollowRecord> queryLikeFollowRecord(InteractLikeFollowRecord dto);

	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryLikeFollowRecordCount(InteractLikeFollowRecord dto);
	
	/**
	 * 查询未完成的互粉互赞记录
	 * @param type
	 * @return
	 */
	@DataSource("slave")
	public List<InteractLikeFollowRecord> queryUnCompleteLikeFollowRecordByType(@Param("type")Integer type);
}
