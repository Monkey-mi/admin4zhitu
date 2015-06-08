package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelNameDto;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpChannelWorldDto;

/**
 * <p>
 * 频道织图数据映射访问接口
 * </p>
 * 
 * 创建时间:2014-11-04
 * @author lynch
 *
 */
public interface ChannelWorldMapper {

	/**
	 * 查询频道织图列表
	 * 
	 * @param world
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelWorldDto> queryChannelWorlds(OpChannelWorld world);
	
	/**
	 * 查询频道织图总数
	 * 
	 * @param world
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelWorldCount(OpChannelWorld world);
	
	/**
	 * 查询频道织图最大maxId
	 * 
	 * @return
	 */
	@DataSource("slave")
	public Integer queryChannelWorldMaxId();
	
	/**
	 * 保存频道织图
	 * 
	 * @param world
	 */
	@DataSource("master")
	public void save(OpChannelWorld world);
	
	/**
	 * 更新频道织图
	 * 
	 * @param world
	 */
	@DataSource("master")
	public void update(OpChannelWorld world);
	
	/**
	 * 通过织图id和频道id共同更新频道织图
	 * 
	 * @param world
	 */
	@DataSource("master")
	public void updateChannelWorldByWorldIdAndChannelId(OpChannelWorld world);
	
	/**
	 * 根据ids删除记录
	 * 
	 * @param ids
	 */
	@DataSource("master")
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 根据ids更新有效性
	 * 
	 * @param ids
	 * @param valid
	 */
	@DataSource("master")
	public void updateValidByIds(@Param("ids")Integer[] ids, @Param("valid")Integer valid);
	
	/**
	 * 根据wid更新有效性
	 * @param wids
	 * @param valid
	 */
	@DataSource("master")
	public void updateValidByWIds(@Param("wids")Integer[]wids,@Param("valid")Integer valid);
	
	/**
	 * 更新id
	 * 
	 * @param id
	 * @param newId
	 */
	@DataSource("master")
	public void updateId(OpChannelWorld world);
	
	/**
	 * 根据id更新id
	 * 
	 * @param id
	 * @param newId
	 */
	@DataSource("master")
	public void updateSerialById(@Param("id")Integer id, @Param("serial")Integer serial);
	
	/**
	 * 根据id查询频道织图
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public OpChannelWorld queryChannelWorldById(Integer id);
	
	/**
	 * 根据频道id查询图片总数
	 * 
	 * @param channelId
	 * @return
	 */
	@DataSource("slave")
	public Integer querySumChildCountByChannelId(Integer channelId);
	
	/**
	 * 根据频道id查询织图
	 * 
	 * @param channelId
	 * @param worldId
	 * @return
	 */
	@DataSource("slave")
	public OpChannelWorld queryWorldByChannelId(OpChannelWorld world);
	
	/**
	 * 根据织图id来查询频道织图信息(不完整的信息,若需要完整信息，需要修改xml文件里的Map)
	 * @param worldId
	 * @return
	 */
	@DataSource("slave")
	public OpChannelWorld queryChannelWorldByWorldId(Integer worldId);
	
	/**
	 * 根据织图id，查询该织图入选了哪些频道
	 */
	@DataSource("slave")
	public List<String> queryChannelNameByWorldId(Integer worldId);
	
	@DataSource("slave")
	public List<OpChannelNameDto> queryChannelNameByWIDs(Integer[] wids);
}
