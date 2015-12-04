package com.imzhitu.admin.op.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.OpChannelLink;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;

import net.sf.json.JSONArray;

public interface OpChannelV2Service extends BaseService{
	
	/**
	 * 新增频道
	 * @param dto
	 */
	void insertOpChannel(String channelDesc, String channelIcon, 
			String channelSubIcon, String channelBanner, String channelReview, String channelName,
			Integer channelTypeId, Integer ownerId, Integer themeId)throws Exception;
	
	/**
	 * 删除
	 * @param dto
	 */
	void deleteOpChannel(Integer channelId,Integer ownerId)throws Exception;
	
	/**
	 * 修改频道，根据channelId
	 * @param dto
	 */
	void updateOpChannel(Integer channelId, String channelDesc, String channelIcon, 
			String channelSubIcon, String channelBanner, String channelReview, String channelName,
			Integer channelTypeId, Integer ownerId, Integer themeId)throws Exception;
	
	/**
	 * 修改频道的精选
	 * 
	 * @param channelId	频道id
	 * @param superb	是否精选，精选：1，非精选：0
	 * @throws Exception
	 * @author zhangbo	2015年8月19日
	 */
	void updateOpChannelSuperb(Integer channelId, Integer superb) throws Exception;
	
	/**
	 * 修改频道的有效性
	 * 
	 * @param channelId	频道id
	 * @param valid	是否有效，有效：1，无效：0
	 * @throws Exception
	 * @author zhangbo	2015年8月19日
	 */
	void updateOpChannelValid(Integer channelId, Integer valid) throws Exception;
	
	/**
	 * 修改频道是否有弹幕
	 * 
	 * @param channelId	频道id
	 * @param danmu		是否有弹幕，有弹幕：1，没有弹幕：0
	 * @throws Exception
	 * @author zhangbo	2015年8月19日
	 */
	void updateOpChannelDanmu(Integer channelId, Integer danmu) throws Exception;
	
	/**
	 * 修改频道的发心情标记
	 * 
	 * @param channelId	频道id
	 * @param moodFlag	是否可以发心情，可以：1，不可以：0
	 * @throws Exception
	 * @author zhangbo	2015年8月19日
	 */
	void updateOpChannelMoodFlag(Integer channelId, Integer moodFlag) throws Exception;
	
	/**
	 * 修改频道是否可以发织图的标记
	 * 
	 * @param channelId	频道id
	 * @param worldFlag	是否可以在频道中发织图，可以：1，不可以 ：0
	 * @throws Exception
	 * @author zhangbo	2015年8月19日
	 */
	void updateOpChannelWorldFlag(Integer channelId, Integer worldFlag) throws Exception;
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	void queryOpChannel(Integer channelId,String channelName,Integer channelTypeId,Integer ownerId,Integer superb,Integer valid,Integer top,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,Integer themeId,int start,int rows,Integer maxId,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	long queryOpChannelTotalCount(Integer channelId,String channelName,Integer channelTypeId,Integer ownerId,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,Integer maxId,Integer themeId)throws Exception;
	
	/**
	 * 从opensearch查询标签。
	 * @param label
	 * @param jsonMap
	 * @throws Exception
	 */
	JSONArray  queryOpChannelLabel(String label)throws Exception;
	
	/**
	 * 根据id查询频道
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OpChannelV2Dto queryOpChannelByIdOrName(Integer id,String channelName)throws Exception;
	
	/**
	 * 更新
	 * @param channelId
	 * @throws Exception
	 */
	void updateValid(Integer  channelId,Integer valid)throws Exception;
	
	/**
	 * 根据管理员账号查询其对应的频道
	 * 
	 * @param adminUserId
	 * @param jsonMap
	 * @throws Exception
	 */
	void queryOpChannelByAdminUserId(Integer channelId,String channelName,Integer channelTypeId,Integer adminUserId,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 批量插入织图到频道
	 * @param channelId
	 * @param worldAndAuthorIds
	 * @throws Exception
	 */
	void batchInsertWorldToChannel(Integer channelId,String worldAndAuthorIds)throws Exception;
	
	/**
	 * 查询昨天新增成员数，不包含马甲
	 * @param yestodayTime
	 * @param todayTime
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	long queryYestodayMemberIncreasement(Long yestodayTime, Long todayTime,Integer  channelId)throws Exception;

	/**
	 * 刷新频道缓存，同时刷新置顶与加精的频道，置顶频道即推荐频道，加精频道即客户端频道缓存
	 * 
	 * @author zhangbo 2015年6月10日
	 */
	void updateChannelCache() throws Exception;

	/**
	 * 查询关联频道列表
	 *
	 * @author zhangbo 2015年6月10日
	 */
	List<OpChannelLink> queryRelatedChannelList(Integer channelId) throws Exception;

	/**
	 * 添加关联频道
	 * 
	 * @param channelId
	 * @author zhangbo 2015年6月11日
	 */
	void addRelatedChannel(Integer channelId, Integer linkChannelId) throws Exception;

	/**
	 * 批量删除关联频道
	 * @param channelId
	 * @param deleteIds
	 * @author zhangbo 2015年6月11日
	 */
	void deleteRelatedChannels(Integer channelId, Integer[] deleteIds) throws Exception;

	/**
	 * 重新排序关联频道
	 * 
	 * @param ids
	 * @author zhangbo 2015年6月13日
	 */
	void updateRelatedChannelSerial(Integer channelId,String[] linkChannelIds) throws Exception;
	
	/**
	 * 保存频道置顶信息到存储表channel_top中，top字段为1
	 * 
	 * @param channelId	频道id
	 * @author zhangbo 2015年6月12日
	 */
	void saveChannelTop(Integer channelId) throws Exception;

	/**
	 * 删除频道置顶信息
	 * 
	 * @param channelId	频道id
	 * @author zhangbo 2015年6月12日
	 */
	void deleteChannelTop(Integer channelId) throws Exception;

	/**
	 * 根据频道查询与该频道关联的所有标签
	 *
	 * @param channelId	频道ID
	 * @return List<Map<Integer, String>>	返回值中，为标签的id与名称键值对list集合，例“id”：“123”，“label_name”：“ChannelLabelName”
	 * @author zhangbo 2015年6月17日
	 */
	List<Map<String, Object>> queryOpChannelLabelList(Integer channelId) throws Exception;

	/**
	 * 修改频道标签
	 * 
	 * @param channelId		频道ID
	 * @param channelLabelIds	标签id集合，由“,”号分隔
	 * @param channelLabelNames	标签名称集合，由“,”号分隔
	 * @author zhangbo 2015年6月17日
	 */
	void updateOpChannelLabel(Integer channelId,
		String channelLabelIds, String channelLabelNames) throws Exception;

	/**
	 * 查询频道专题
	 * 
	 * @return
	 * @author zhangbo 2015年6月17日
	 */
	void queryChannelThemeList(Integer themeId,Map<String,Object> jsonMap);
	
	/**
	 * 插入新的专属主题
	 * @param themeName 
		*	2015年12月2日
		*	mishengliang
	 */
	void insertChannelTheme(String themeName);
	
	/**
	 * 修改专属主题
	 * @param themeId
	 * @param themeName 
		*	2015年12月2日
		*	mishengliang
	 */
	void updateChannelTheme(Integer themeId,String themeName);
	
	/**
	 * 删除专属主题，和ｖａｌｉｄ字段无关，这时属于硬删除
	 * @param themeId 
		*	2015年12月2日
		*	mishengliang
	 */
	void deleteChannelTheme(Integer themeId);
	
	/**
	 * 添加频道自动通过id
	 * 
	 * @param channelId
	 * @author lynch 2015-09-14
	 */
	void addAutoRejectId(Integer channelId) throws Exception;
	
	/**
	 * 删除频道自动通过id
	 * 
	 * @param channelId
	 * @throws Exception
	 */
	void deleteAutoRejectId(Integer channelId) throws Exception;
	
	/**
	 * 刷新新版本主题缓存
	 * 
	 * @throws Exception 
		*	2015年12月3日
		*	mishengliang
	 */
	void channelThemeRefreshCache() throws Exception;
	
	
	/**
	 * 重新排序
	 * @param ids
	 * @throws Exception 
		*	2015年12月4日
		*	mishengliang
	 */
	void addChannelThemeSerial(String[] ids)  throws Exception;
	
}
