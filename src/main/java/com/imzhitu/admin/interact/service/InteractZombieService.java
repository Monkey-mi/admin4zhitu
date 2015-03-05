package com.imzhitu.admin.interact.service;

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
			String locationAddr, Integer size)throws Exception;
	
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
	public void saveZombieWorldToHtWorld(Integer zombieWorldId)throws Exception;
	
	/**
	 * 分页查询
	 * @param childs
	 * @return
	 * @throws Exception
	 */
	public void queryZombieWorldForTable(int maxId,int page,int rows,Integer complete,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 批量定时发布马甲织图
	 * @param zombieWorldIds
	 * @param begin
	 * @param timeSpan
	 */
	public void batchSaveZombieWorldToHTWorld(final String zombieWorldIds,final Date begin, final Integer timeSpan);
}
