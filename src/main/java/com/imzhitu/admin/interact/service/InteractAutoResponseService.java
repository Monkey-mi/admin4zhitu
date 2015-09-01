package com.imzhitu.admin.interact.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * 自动回复业务接口
 * 
 * @author zhangbo	2015年9月1日
 *
 */
public interface InteractAutoResponseService extends BaseService{
	/**
	 * 分页查询未完成的回复
	 * 
	 * @param maxId			页面传递本次查询最大主键id，作为标记位
	 * @param start			分页起始位置
	 * @param limit			分页每页数据数量
	 * @param userLevelId	用户等级主键id
	 * @param userLevelId	频道主键id
	 * @param jsonMap		返回的结果集jsonMap
	 * 
	 * @throws Exception
	 */
	public void queryUncompleteResponse(Integer maxId, int start, int limit,Integer userLevelId, Integer channelId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新回复完成状态
	 * @param ids
	 * @throws Exception
	 */
	public void updateResponseCompleteByIds(String idsStr,String responseIdsStr,Integer operatorId)throws Exception;
	
	/**
	 * 更新回复完成状态
	 * @param id
	 * @throws Exception
	 */
	public void updateResponseCompleteById(Integer id);
	
	/**
	 * 扫描回复，并从机器人那里获取回复
	 */
	public void scanResponseAndGetAnswer();
	
	/**
	 * 查询当id对应的回复组
	 */
	public void queryResponseGroupById(Integer id,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 删除自动回复 by ids
	 * @param ids
	 * @throws Exception
	 */
	public void delAutoResponseByIds(String idsStr)throws Exception;
	
	/**
	 * 修改内容
	 * @param rowJson
	 * @throws Exception
	 */
	public void updateCommentContentByRowJson(String rowJson)throws Exception;
	
	/**
	 * 调教机器人
	 * @param question
	 * @param answer
	 * @param jsonMap
	 * @throws Exception
	 */
	public void teachTuLingRobot(String question,String answer,Map<String,Object>jsonMap)throws Exception;
}
