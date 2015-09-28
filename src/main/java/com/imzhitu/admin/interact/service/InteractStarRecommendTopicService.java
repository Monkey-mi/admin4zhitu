package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.StarRecommendTopic;

public interface InteractStarRecommendTopicService {

	/**
	 * 
	 * @param backgroundColor 背景色
	 * @param fileName  文件名
	 * @param title 主题
	 * @param introduceHead 头介绍文本
	 * @param introduceFoot尾介绍文本
	 * @throws Exception 
		*	2015年9月22日
		*	mishengliang
	 */
	public void addTopic(String backgroundColor,String fileName,String title,String introduceHead,String introduceFoot)  throws Exception;

	/**
	 * 
	 * @param jsonMap
	 * @return
	 * @throws Exception 
		*	2015年9月22日
		*	mishengliang
	 */
	public List<StarRecommendTopic> getTopic()  throws Exception;
	
	public List<Integer> getTopicId()  throws Exception;
	/**
	 * 
	 * @param id
	 * @param backgroundColor 背景色
	 * @param fileName  文件名
	 * @param title 主题
	 * @param introduceHead 头介绍文本
	 * @param introduceFoot尾介绍文本
	 * @throws Exception 
		*	2015年9月22日
		*	mishengliang
	 */
	public void updateTopic(Integer id,String backgroundColor,String fileName,String title,String introduceHead,String introduceFoot)  throws Exception;
	
	/**
	 * 
	 * @param id
	 * @throws Exception 
		*	2015年9月22日
		*	mishengliang
	 */
	public void destoryTopic(Integer id)  throws Exception;
}