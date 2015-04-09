package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelTopOne;
import com.imzhitu.admin.common.pojo.OpChannelTopOneDto;
import com.imzhitu.admin.common.pojo.OpChannelTopOnePeriod;

public interface ChannelTopOneMapper {

	/**
	 * 查询缓存top　one列表
	 * @return
	 */
	@DataSource("slave")
	public List<com.hts.web.common.pojo.OpChannelTopOne> queryCacheTopOne();

	/**
	 * 查询top　one列表
	 * 
	 * @param topOne
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelTopOneDto> queryTopOnes(OpChannelTopOne topOne);
	
	/**
	 * 查询top one总数
	 * @param topOne
	 * @return
	 */
	@DataSource("slave")
	public long queryTopOneCount(OpChannelTopOne topOne);
	
	/**
	 * 保存top　one
	 * 
	 * @param topOne
	 */
	public void save(OpChannelTopOne topOne);

	/**
	 * 保存top　one
	 * 
	 * @param topOne
	 */
	public void update(OpChannelTopOne topOne);

	/**
	 *　根据ids删除记录
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
	 * 更新id
	 * 
	 * @param id
	 * @param newId
	 */
	public void updateId(@Param("id")Integer id, @Param("newId")Integer newId);
	
	/**
	 * 根据id查询top one
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public OpChannelTopOne queryTopOneById(Integer id);
	
	/**
	 * 精品最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> querySuperbTopOne(OpChannelTopOne dto);
	
	/**
	 * 被赞最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryBeLikedTopOne(OpChannelTopOne dto);
	
	/**
	 * 赞他人最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> querylikeTopOne(OpChannelTopOne dto);
	
	/**
	 * 涨粉最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryFollowerIncreaseTopOne(OpChannelTopOne dto);
	
	/**
	 * 关注最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryFollowTopOne(OpChannelTopOne dto);
	
	/**
	 * 评论他人最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryCommentTopOne(OpChannelTopOne dto);
	
	/**
	 * 发图使用标签总数最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryLabelTopOne(OpChannelTopOne dto);
	
	/**
	 * 更新排行榜
	 * @param dto
	 * @return
	 */
	public Integer insertTopOne(OpChannelTopOne dto);
	
	/**
	 * 被浏览最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryBeClickTopOne(OpChannelTopOne dto);
	
	/**
	 * 织图最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryWorldTopOne(OpChannelTopOne dto);
	
	/**
	 * 图片最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryPictureTopOne(OpChannelTopOne dto);
	
	/**
	 * 活动最多
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryActivityTopOne(OpChannelTopOne dto);
	
	/**
	 * 根据toptype来更新valid
	 * @param dto
	 */
	public void updateTopOneValidByTopTypeIdAndPeriod(OpChannelTopOne dto);
	
	/**
	 * 查询最新期数
	 * 
	 * @return
	 */
	@DataSource("slave")
	public Integer queryMaxPeriod();
	
	/**
	 * 查询期数列表
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelTopOnePeriod> queryPeriodList(OpChannelTopOnePeriod period);
}
