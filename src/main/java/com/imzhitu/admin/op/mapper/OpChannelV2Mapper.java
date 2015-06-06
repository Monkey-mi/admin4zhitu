package com.imzhitu.admin.op.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;

public interface OpChannelV2Mapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 删除
	 * @param dto
	 */
	@DataSource("master")
	public void deleteOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 修改
	 * @param dto
	 */
	@DataSource("master")
	public void updateOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelV2Dto> queryOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryOpChannelTotalCount(OpChannelV2Dto dto);
	
	/**
	 * 根据拥有着IdArray 来查询
	 * @param ownerIdArray
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelV2Dto>queryOpChannelByOwnerIds(Integer[] ownerIdArray);
	
	/**
	 * 查询昨日新增成员数。不包括马甲
	 * @param yestodayTime
	 * @param todayTime
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryYestodayMemberIncreasement(@Param("yestodayTime") Long yestodayTime,@Param("todayTime") Long todayTime,@Param("channelId")Integer  channelId);
	
	
	/**
	 *	查询昨日新增织图数 ,不包括马甲
	 * @param yestodayTime
	 * @param todayTime
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryYestodayWorldIncreasement(@Param("yestoday") Date yestoday,@Param("today") Date today,@Param("channelId")Integer  channelId);
}
