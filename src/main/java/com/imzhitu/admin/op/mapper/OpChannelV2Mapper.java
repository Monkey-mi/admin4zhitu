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

	/**
	 * 添加关联频道
	 * 
	 * @param channelId	频道id
	 * @param linkChannelId	关联频道id
	 * @param serial	序号
	 * @author zhangbo 2015年6月11日
	 */
	public void addRelatedChannel(@Param("channelId")Integer channelId, @Param("linkChannelId")Integer linkChannelId, @Param("serial")Integer serial);

	/**
	 * 删除关联频道
	 * @param channelId	频道id
	 * @param linkId	关联频道id
	 * @author zhangbo 2015年6月11日
	 */
	public void deleteRelatedChannel(@Param("channelId")Integer channelId, @Param("linkChannelId")Integer linkChannelId);

	/**
	 * 关联频道重新排序，即刷新serial字段
	 * 
	 * @param channelId
	 * @param linkCid
	 * @param serial
	 * @author zhangbo 2015年6月13日
	 */
	public void updateRelatedChannelSerial(@Param("channelId")Integer channelId, @Param("linkChannelId")Integer linkCid, @Param("serial")Integer serial);
	
	/**
	 * 添加置顶频道到channel_top表
	 * 
	 * @param channelId	频道id
	 * @author zhangbo 2015年6月12日
	 */
	public void insertChannelTop(Integer channelId);

	/**
	 * 删除频道置顶表中的记录
	 * 
	 * @param channelId	频道id
	 * @author zhangbo 2015年6月12日
	 */
	public void deleteChannelTop(Integer channelId);

	/**
	 * 查询频道置顶表数据
	 *
	 * @return
	 * @author zhangbo 2015年6月12日
	 */
	public List<OpChannelV2Dto> queryChannelTop();

}
