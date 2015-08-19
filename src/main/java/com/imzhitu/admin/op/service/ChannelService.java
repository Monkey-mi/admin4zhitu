package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.common.pojo.OpChannelTopOne;
import com.imzhitu.admin.common.pojo.OpChannelTopOnePeriod;
import com.imzhitu.admin.common.pojo.OpChannelTopType;
import com.imzhitu.admin.common.pojo.OpChannelWorld;

/**
 * <p>
 * 频道模块业务逻辑访问接口，包括:<br><br>
 * 频道、Top红人、频道织图等子模块CURD
 * </p>
 * 
 * 创建时间: 2014-11-04
 * @author lynch
 *
 */
public interface ChannelService extends BaseService {
	
	/**
	 * 更新top one缓存
	 * 
	 * @throws Exception
	 */
	public void updateTopOneCache() throws Exception;
	
	/**
	 * 更新top one标题缓存
	 * 
	 * @param title
	 * @throws Exception
	 */
	public void updateTopOneTitleCache(Date beginDate, Date endDate) throws Exception;
	
	/**
	 * 更新频道织图缓存
	 * 
	 * @param world
	 * @throws Exception
	 */
	public void updateChannelWorldCache(OpChannelWorld world, Integer childCountBase) throws Exception;
	
	/**
	 * 构建频道列表
	 * 
	 * @param channel
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannel(OpChannel channel, int page, int rows,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据id查询频道
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OpChannel queryChannelById(Integer id) throws Exception;
	
	/**
	 * 查询所有的频道
	 * @return
	 * @throws Exception
	 */
	public List<OpChannel> queryAllChannel()throws Exception;
	
	/**
	 * 更新频道
	 * 
	 * @param channel
	 * @throws Exception
	 */
	public void updateChannel(OpChannel channel) throws Exception;
	
	/**
	 * 保存频道
	 * 
	 * @param channel
	 * @throws Exception
	 */
	public void saveChannel(OpChannel channel) throws Exception;
	
	/**
	 * 删除频道
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteChannel(String idsStr) throws Exception;
	
	/**
	 * 增加频道序号
	 * 
	 * @param idStrs
	 * @throws Exception
	 */
	public void addChannelSerial(String[] idStrs) throws Exception;
	
	/**
	 * 更新频道有效性
	 * 
	 * @param idsStr
	 * @param valid
	 * @throws Exception
	 */
	public void updateChannelValid(String idsStr, Integer valid) throws Exception;
	
	/**
	 * 构建top one列表
	 * 
	 * @param topOne
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildTopOneDto(OpChannelTopOne topOne, int page, int rows, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新top one
	 * 
	 * @param topOne
	 * @throws Exception
	 */
	public void updateTopOne(OpChannelTopOne topOne) throws Exception;
	
	/**
	 * 保存top one
	 * @param topOne
	 * @throws Exception
	 */
	public void saveTopOne(OpChannelTopOne topOne) throws Exception;
	
	/**
	 * 删除top one
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteTopOne(String idsStr) throws Exception;
	
	/**
	 * 更新top one有效性
	 * 
	 * @param idsStr
	 * @param valid
	 * @throws Exception
	 */
	public void updateTopOneValid(String idsStr, Integer valid) throws Exception;
	
	/**
	 * 增加top one id
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void addTopOneId(String[] idsStr) throws Exception;
	
	/**
	 * 根据id查询top one
	 * 
	 * @param id
	 */
	public OpChannelTopOne queryTopOneById(Integer id);

	/**
	 * 查询期数列表
	 * 
	 * @param period
	 */
	public List<OpChannelTopOnePeriod> queryTopOnePeriodList(OpChannelTopOnePeriod period, int start, int limit,
			Boolean addAllTag);
	
	/**
	 * 添加top one推荐消息
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void addTopOneRecommendMsg(Integer id) throws Exception;
	
	/**
	 * 批量添加top one推荐消息
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void addTopOneRecommendMsgs(String idsStr) throws Exception;
	
	
	/**
	 * 构建top type列表
	 * 
	 * @param addAllTag
	 * @param jsonMap
	 * @throws Exception
	 */
	public List<OpChannelTopType> queryTopType(Boolean addAllTag) throws Exception;
	
	/**
	 * 更新top type
	 * 
	 * @param topType
	 * @throws Exception
	 */
	public void updateTopType(OpChannelTopType topType) throws Exception;
	
	/**
	 * 保存top type
	 * 
	 * @param topType
	 * @throws Exception
	 */
	public void saveTopType(OpChannelTopType topType) throws Exception;
	
	/**
	 * 根据ids删除top types
	 *
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteTopTypes(String idsStr) throws Exception;
	
	
	/**
	 * 构建top type列表
	 * 
	 * @param world
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannelWorld(OpChannelWorld world, int page, int rows,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存频道织图
	 * 
	 * @param world
	 * @throws Exception
	 */
	public void saveChannelWorld(OpChannelWorld world) throws Exception;

	/**
	 * 更新频道织图
	 *
	 * @param world
	 * @throws Exception
	 */
	public void updateChannelWorld(OpChannelWorld world) throws Exception;
	
	/**
	 * 删除频道织图
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteChannelWorlds(String idsStr) throws Exception;
	
	/**
	 * 添加频道织图id
	 * 
	 * @param channelId
	 * @param widsStr
	 * @throws Exception
	 */
	public void addChannelWorldId(Integer channelId, String[] widsStr) throws Exception;
	
	/**
	 * 添加频道织图id 
	 * 
	 * @param channelId
	 * @param wid
	 * @throws Exception
	 */
	public void addChannelWorldId(Integer channelId, Integer wid) throws Exception;
	
	/**
	 * 添加频道织图id
	 * 
	 * @param channelId
	 * @param wids
	 * @throws Exception
	 */
	public void addChannelWorldId(Integer channelId, Integer[] wids) throws Exception;
	
	/**
	 * 增加频道织图id
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void addChannelWorldId(Integer[] ids) throws Exception;
	
	/**
	 * 增加频道织图id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void addChannelWorldId(Integer id) throws Exception;
	
	/**
	 * 批量更新频道织图有效标记
	 * 
	 * @param idsStr
	 * @param valid
	 * @throws Exception
	 */
	public void updateChannelWorldValid(String idsStr, Integer valid) throws Exception;
	
	/**
	 * 根据wid更新有效性
	 * @param wids
	 * @param valid
	 */
	public void updateChannelWorldValid(Integer[]wids,Integer channelId,Integer valid)throws Exception;
	
	/**
	 * 根据频道id和织图id更新有效性
	 * 
	 * @param channelId
	 * @param worldId
	 * @param valid
	 * @throws Exception
	 */
	public void updateChannelWorldValid(Integer channelId,
			Integer worldId, Integer valid) throws Exception;
	
	/**
	 * 更新织图精选标记
	 * 
	 * @param superb
	 * @throws Exception
	 */
	public void updateChannelWorldSuperb(Integer channelId,
			Integer worldId, Integer superb) throws Exception;
	
	
	/**
	 * 添加频道织图推荐消息
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void addChannelWorldRecommendMsg(Integer id) throws Exception;
	
	/**
	 * 添加频道织图推荐消息
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void addChannelWorldRecommendMsgByWorldId(Integer worldid) throws Exception;
	
	/**
	 * 批量添加频道织图推荐消息
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void addChannelWorldRecommendMsgs(String idsStr) throws Exception;
	/**
	 * 精品最多
	 * @param dto
	 * @return
	 */
	public List<Integer> querySuperbTopOne(OpChannelTopOne dto )throws Exception;
	
	/**
	 * 被赞最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryBeLikedTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 赞他人最多
	 * @param dto
	 * @return
	 */
	public List<Integer> querylikeTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 涨粉最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryFollowerIncreaseTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 关注最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryFollowTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 评论他人最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryCommentTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 发图使用标签总数最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryLabelTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 被浏览最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryBeClickTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 织图最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryWorldTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 图片最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryPictureTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 活动最多
	 * @param dto
	 * @return
	 */
	public List<Integer> queryActivityTopOne(OpChannelTopOne dto)throws Exception;
	
	/**
	 * 更新排行榜
	 * @param dto
	 * @return
	 */
	public void updateTopOne()throws Exception;

	/**
	 * 搜索频道
	 * 
	 * @param query
	 * @param jsonMap
	 * @throws Exception
	 */
	public void searchChannel(String query, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception;
	
}
