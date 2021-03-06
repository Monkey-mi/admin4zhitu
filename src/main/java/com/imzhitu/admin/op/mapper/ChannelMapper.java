package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannel;

/**
 * <p>
 * 频道数据映射访问接口
 * </p>
 * 
 * 创建时间:2014-11-04
 * @author lynch
 *
 */
public interface ChannelMapper {

	/**
	 * 查询频道列表
	 * 
	 * @param channel
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannel> queryChannels(OpChannel channel);
	
	/**
	 * 查询频道总数
	 * 
	 * @param channel
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelCount(OpChannel channel);
	
	/**
	 * 查询所有的频道
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannel> queryAllChannel();
	
	/**
	 * 保存频道
	 * 
	 * @param channel
	 */
	@DataSource("master")
	public void save(OpChannel channel);
	
	/**
	 * 更新频道
	 * 
	 * @param channel
	 */
	@DataSource("master")
	public void update(OpChannel channel);
	
	/**
	 * 根据ids批量删除记录
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
	 * 根据id查询频道
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public OpChannel queryChannelById(Integer id);
	
	/**
	 * 查询图片基数
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Integer queryChildCountBase(Integer id);
	
	@DataSource("slave")
	public List<OpChannel> searchChannel(OpChannel channel);

	@DataSource("slave")
	public long searchChannelCount(OpChannel channel);
		
}
