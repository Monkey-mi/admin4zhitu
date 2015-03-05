package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	 * 查询有效频道列表
	 * 
	 * @param valid
	 * @return
	 */
	public List<com.hts.web.common.pojo.OpChannel> queryValidChannel(@Param("limit")Integer limit);

	/**
	 * 查询频道列表
	 * 
	 * @param channel
	 * @return
	 */
	public List<OpChannel> queryChannels(OpChannel channel);
	
	/**
	 * 查询频道总数
	 * 
	 * @param channel
	 * @return
	 */
	public long queryChannelCount(OpChannel channel);
	
	/**
	 * 查询所有的频道
	 * @return
	 */
	public List<OpChannel> queryAllChannel();
	
	/**
	 * 保存频道
	 * 
	 * @param channel
	 */
	public void save(OpChannel channel);
	
	/**
	 * 更新频道
	 * 
	 * @param channel
	 */
	public void update(OpChannel channel);
	
	/**
	 * 根据ids批量删除记录
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);

	/**
	 * 根据ids更新有效性
	 * 
	 * @param ids
	 * @param valid
	 */
	public void updateValidByIds(@Param("ids")Integer[] ids, @Param("valid")Integer valid);
	
	/**
	 * 根据id查询频道
	 * 
	 * @param id
	 * @return
	 */
	public OpChannel queryChannelById(Integer id);
	
	/**
	 * 查询图片基数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryChildCountBase(Integer id);
		
}
