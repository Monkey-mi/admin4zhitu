package com.imzhitu.admin.interact.service;

import java.util.Map;

public interface InteractStarWorldModuleService {

	/**
	 * 
	 * @param title  小标题
	 * @param subtitle 小副标题	 * @param userId 用户ID
	 * @param pics 图片名
	 * @param pic02 
	 * @param pic03
	 * @param pic04 
	 * @param Intro 图片介绍
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void addWorldModule(String title,String subtitle,Integer userId,Integer worldId,String intro,Integer topicId)  throws Exception;

	/**
	 * 获取模块信息
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void getWorldModule(Integer page,Integer rows,Integer maxId,Integer topicId,Map<String, Object> jsonMap)  throws Exception;
	
	
	/**
	 * 
	 * @param id
	 * @param title
	 * @param subtitle
	 * @param userId
	 * @param pics
	 * @param pic02 
	 * @param pic03
	 * @param pic04 
	 * @param Intro
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void updateWorldModule(Integer id,String title,String subtitle,Integer userId,Integer worldId,String intro)  throws Exception;
	
	/**
	 * 
	 * @param id
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void destroyWorldModule(String ids)  throws Exception;
	
	
	/**
	 * 利用传输过来id顺序进行排序
	 * @param ids
	 * @throws Exception 
		*	2015年11月13日
		*	mishengliang
	 */
	public void reOrderIndex(String[] ids)  throws Exception;
	
}
