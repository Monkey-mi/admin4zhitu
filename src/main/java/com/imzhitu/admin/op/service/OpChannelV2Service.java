package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.hts.web.common.pojo.OpChannelLink;
import com.hts.web.common.pojo.OpChannelTheme;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;

public interface OpChannelV2Service extends BaseService{
	/**
	 * 增加
	 * @param dto
	 */
	public void insertOpChannel(Integer ownerId,String channelName,String channelTitle,String subtitle,String channelDesc,
			String channelIcon,Integer channelTypeId,String channelLabelNames,String channelLabelIds,Integer worldCount,
			Integer worldPictureCount,Integer memberCount,Integer  superbCount,Integer childCountBase,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,Integer themeId)throws Exception;
	
	/**
	 * 删除
	 * @param dto
	 */
	public void deleteOpChannel(Integer channelId,Integer ownerId)throws Exception;
	
	/**
	 * 修改
	 * @param dto
	 */
	public void updateOpChannel(Integer channelId,Integer ownerId,String channelName,String channelTitle,String subtitle,String channelDesc,
			String channelIcon,Integer channelTypeId,String channelLabelNames,String channelLabelIds,Integer worldCount,
			Integer worldPictureCount,Integer memberCount,Integer  superbCount,Integer childCountBase,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,Integer themeId)throws Exception;
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public void queryOpChannel(Integer channelId,String channelName,Integer channelTypeId,Integer ownerId,Integer superb,Integer valid,Integer top,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,Integer themeId,int start,int rows,Integer maxId,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long queryOpChannelTotalCount(Integer channelId,String channelName,Integer channelTypeId,Integer ownerId,Integer superb,Integer valid,
			Integer serial,Integer danmu,Integer moodFlag,Integer worldFlag,Integer maxId,Integer themeId)throws Exception;
	
	/**
	 * 从opensearch查询标签。
	 * @param label
	 * @param jsonMap
	 * @throws Exception
	 */
	public JSONArray  queryOpChannelLabel(String label)throws Exception;
	
	/**
	 * 根据id查询频道
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OpChannelV2Dto queryOpChannelByIdOrName(Integer id,String channelName)throws Exception;
	
	/**
	 * 更新
	 * @param channelId
	 * @throws Exception
	 */
	public void updateValid(Integer  channelId,Integer valid)throws Exception;
	
	/**
	 * 批量update
	 * @param channelIdsStr
	 * @throws Exception
	 */
	public void batchUpdateValid(String channelIdsStr,Integer valid)throws Exception;
	
	/**
	 * 根据管理员账号查询其对应的频道
	 * 
	 * @param adminUserId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryOpChannelByAdminUserId(Integer channelId,String channelName,Integer channelTypeId,Integer adminUserId,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 批量插入织图到频道
	 * @param channelId
	 * @param worldAndAuthorIds
	 * @throws Exception
	 */
	public void batchInsertWorldToChannel(Integer channelId,String worldAndAuthorIds)throws Exception;
	
	/**
	 * 查询昨天新增织图数，不包含马甲
	 * @param yestoday
	 * @param today
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public long queryYestodayWorldIncreasement(Date yestoday, Date today,Integer  channelId)throws Exception;
	
	/**
	 * 查询昨天新增成员数，不包含马甲
	 * @param yestodayTime
	 * @param todayTime
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public long queryYestodayMemberIncreasement(Long yestodayTime, Long todayTime,Integer  channelId)throws Exception;

	/**
	 * 刷新频道缓存，同时刷新置顶与加精的频道
	 * 
	 * @author zhangbo 2015年6月10日
	 */
	public void updateChannelCache() throws Exception;

	/**
	 * 查询关联频道列表
	 *
	 * @author zhangbo 2015年6月10日
	 */
	public List<OpChannelLink> queryRelatedChannelList(Integer channelId) throws Exception;

	/**
	 * 添加关联频道
	 * 
	 * @param channelId
	 * @author zhangbo 2015年6月11日
	 */
	public void addRelatedChannel(Integer channelId, Integer linkChannelId) throws Exception;

	/**
	 * 批量删除关联频道
	 * @param channelId
	 * @param deleteIds
	 * @author zhangbo 2015年6月11日
	 */
	public void deleteRelatedChannels(Integer channelId, Integer[] deleteIds) throws Exception;

	/**
	 * 重新排序关联频道
	 * 
	 * @param ids
	 * @author zhangbo 2015年6月13日
	 */
	public void updateRelatedChannelSerial(Integer channelId,String[] linkChannelIds) throws Exception;
	
	/**
	 * 保存频道置顶信息到存储表channel_top中，top字段为1
	 * 
	 * @param channelId	频道id
	 * @author zhangbo 2015年6月12日
	 */
	public void saveChannelTop(Integer channelId) throws Exception;

	/**
	 * 删除频道置顶信息
	 * 
	 * @param channelId	频道id
	 * @author zhangbo 2015年6月12日
	 */
	public void deleteChannelTop(Integer channelId) throws Exception;

	/**
	 * 根据频道查询与该频道关联的所有标签
	 *
	 * @param channelId	频道ID
	 * @return List<Map<Integer, String>>	返回值中，为标签的id与名称键值对list集合，例“id”：“123”，“label_name”：“ChannelLabelName”
	 * @author zhangbo 2015年6月17日
	 */
	public List<Map<String, Object>> queryOpChannelLabelList(Integer channelId) throws Exception;

	/**
	 * 修改频道标签
	 * 
	 * @param channelId		频道ID
	 * @param channelLabelIds	标签id集合，由“,”号分隔
	 * @param channelLabelNames	标签名称集合，由“,”号分隔
	 * @author zhangbo 2015年6月17日
	 */
	public void updateOpChannelLabel(Integer channelId,
		String channelLabelIds, String channelLabelNames) throws Exception;

	/**
	 * 查询频道专题
	 * 
	 * @return
	 * @author zhangbo 2015年6月17日
	 */
	public List<OpChannelTheme> queryChannelThemeList();

}
