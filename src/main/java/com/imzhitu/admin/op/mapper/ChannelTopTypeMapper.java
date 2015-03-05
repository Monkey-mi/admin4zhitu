package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpChannelTopType;

/**
 * <p>
 * 频道top-type数据映射访问接口
 * </p>
 * 
 * 创建时间:2014-11-03
 * @author lynch
 *
 */
public interface ChannelTopTypeMapper {

	/**
	 * 查询频道top-type列表
	 * 
	 * @param type
	 * @return
	 */
	public List<OpChannelTopType> queryTopTypes();
	
	/**
	 * 查询top type总数
	 * 
	 * @param type
	 * @return
	 */
	public long queryTopTypeCount(OpChannelTopType type);
	
	/**
	 * 保存top-type
	 * 
	 * @param type
	 */
	public void save(OpChannelTopType type);
	
	/**
	 * 更新top-type
	 * 
	 * @param type
	 */
	public void update(OpChannelTopType type);
	
	/**
	 * 根据ids删除记录
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 根据id查询描述
	 * 
	 * @param id
	 * @return
	 */
	public String queryTitleById(Integer id);
}
