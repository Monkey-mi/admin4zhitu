package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.StarModule;

public interface InteractStarModuleService {

	/**
	 * 
	 * @param title1  小标题
	 * @param title2 小副标题	 * @param userId 用户ID
	 * @param pics 图片名
	 * @param Intro 图片介绍
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void add(String title1,String title2,Integer userId,String pics,String Intro)  throws Exception;

	/**
	 * 获取模块信息
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public List<StarModule> get(Integer topicId)  throws Exception;
	
	
	/**
	 * 
	 * @param id
	 * @param title1
	 * @param title2
	 * @param userId
	 * @param pics
	 * @param Intro
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void update(Integer id,String title1,String title2,Integer userId,String pics,String Intro)  throws Exception;
	
	/**
	 * 
	 * @param id
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void destory(Integer id)  throws Exception;
}
