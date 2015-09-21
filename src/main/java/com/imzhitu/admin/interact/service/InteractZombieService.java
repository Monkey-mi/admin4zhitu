package com.imzhitu.admin.interact.service;

import java.io.File;
import java.util.Date;
import java.util.Map;

public interface InteractZombieService {
	/**
	 * 检查image是否被下载过，若下载过返回1，否则返回0，并且插入到数据库
	 * @param imagePath
	 * @return
	 * @throws Exception
	 */
	public Integer beenDownload(String imagePath)throws Exception;
	
	/**
	 * 保存马甲织图
	 * @return
	 * @throws Exception
	 */
	public Integer saveZombieWorld(String childsJSON, Integer titleId,
			Integer authorId, String worldName,
			String worldDesc, String worldLabel, String labelIds,  
			String coverPath, String titlePath, String titleThumbPath, 
			Double longitude, Double latitude,
			String locationAddr, Integer size,Integer channelId)throws Exception;
	
	/**
	 * 查询马甲织图数据
	 * @return
	 * @throws Exception
	 */
	public Integer queryZombieWorld(Map<String,Object>jsonMap,Integer limit)throws Exception;
	
	/**
	 * 将马甲织图正式发布到织图列表里去
	 * @param zombieWorldId
	 * @throws Exception
	 */
	public Integer saveZombieWorldToHtWorld(Integer zombieWorldId)throws Exception;
	
	/**
	 * 分页查询
	 * @param childs
	 * @return
	 * @throws Exception
	 */
	public void queryZombieWorldForTable(Integer channelId,int maxId,Date addDate,Date modifyDate,int page,int rows,Integer complete,Integer schedulaFlag,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 批量更新标签
	 * @throws Exception
	 */
	public void batchUpdateZombieWorldLabel(String zombieWorldIds,String labelString)throws Exception;
	
	/**
	 * 将马甲织图发布到织图和频道下
	 * @param zombieWorldId
	 * @throws Exception
	 */
	public void saveZombieWorldToChannelAndWorld(Integer zombieWorldId)throws Exception;
	
	/**
	 * 批量删除
	 */
	public void batchDeleteZombieWorld(String idsStr)throws Exception;

	/**
	 * 更新马甲织图
	 * 
	 * @param id		马甲织图表主键id
	 * @param worldDesc	马甲织图描述，可以为null
	 * @param channelId	马甲织图所在频道id，可以为null 
	 * @param authorId	马甲织图作者id，可以为null 
	 * @author zhangbo	2015年7月28日
	 */
	public void updateZombieWorld(Integer id, String worldDesc, Integer channelId, Integer authorId);
	
	
	/**
	 * 将文件中的评论解析，并且加入到指定的评论标签（‘其他旧’）中
	 * @param commentsFile 上传的评论文件
	 * @param authorId 马甲ID
		*	2015年9月6日
		*	mishengliang
	 */
	public void addCommentsFile(File commentsFile,Integer zombieWorldId)throws Exception;
}
