package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.pojo.OpChannelStar;
import com.imzhitu.admin.common.pojo.OpChannelStarDto;

/**
 * <p>
 * 频道明星数据映射
 * </p>
 * 
 * 创建时间:2014-11-3
 * @author lynch
 *
 */
public interface ChannelStarMapper {
	
	/**
	 * 查询缓存明星列表
	 * 
	 * @param star
	 * @return
	 */
	public List<com.hts.web.common.pojo.OpChannelStar> queryCacheStar(OpChannelStar star);
	
	/**
	 * 查询置顶明星列表
	 * 
	 * @param star
	 * @return
	 */
	public List<com.hts.web.common.pojo.OpChannelStar> queryCacheStarWithWeight(OpChannelStar star);
	
	/**
	 * 查询明星列表
	 * 
	 * @param star
	 * @return
	 */
	public List<OpChannelStarDto> queryStars(OpChannelStar star);
	
	/**
	 * 查询明星总数
	 * 
	 * @param star
	 * @return
	 */
	public long queryStarCount(OpChannelStar star);
	
	/**
	 * 保存频道明星
	 * 
	 * @param star
	 */
	public void save(OpChannelStar star);
	
	/**
	 * 更新频道明星
	 * 
	 * @param star
	 */
	public void update(OpChannelStar star);
	
	/**
	 * 根据id查询用户id
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryUserIdById(Integer id);
	
	/**
	 * 根据ids更新频道明星有效标记
	 * 
	 * @param ids
	 * @param valid
	 */
	public void updateValidByIds(@Param("ids")Integer[] ids, @Param("valid")Integer valid);
	
	/**
	 * 删除频道明星
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 更新id
	 * 
	 * @param star
	 */
	public void updateId(OpChannelStar star);
	
	/**
	 * 根据id查询频道明星
	 * 
	 * @param id
	 * @return
	 */
	public OpChannelStar queryStarById(Integer id);
	
	/**
	 * 根据频道查询明星
	 * 
	 * @param channelId
	 * @param userId
	 * @return
	 */
	public OpChannelStar queryStarByChannelId(OpChannelStar star);
	
	/**
	 * 查询最大频道红人记录id
	 * 
	 * @param channelId
	 * @return
	 */
	public Integer queryMaxId(Integer channelId);
	
}
