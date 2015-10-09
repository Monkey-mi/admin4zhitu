package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.StarModule;

public interface InteractStarModuleService {

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
	public void add(String title,String subtitle,Integer userId,String pics,String pic02,String pic03,String pic04,String intro,Integer topicId)  throws Exception;

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
	public void update(Integer id,String title,String subtitle,Integer userId,String pics,String pic02,String pic03,String pic04,String intro)  throws Exception;
	
	/**
	 * 
	 * @param id
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	public void destory(Integer id)  throws Exception;
}
