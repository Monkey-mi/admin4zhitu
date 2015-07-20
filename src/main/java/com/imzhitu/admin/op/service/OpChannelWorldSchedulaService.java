package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;


public interface OpChannelWorldSchedulaService extends BaseService{
	/**
	 * 分页查询
	 * @throws Exception
	 */
	public void queryChannelWorldSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * 更新 
	 * @throws Exception
	 */
	public void updateChannelWorldSchedula(Integer id,Integer userId,Integer worldId,
			Integer channelId,Integer finish,Integer valid,Integer operatorId,Date schedulaDate)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void delChannelWorldSchedula(String idsStr)throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insertChannelWorldSchedula(Integer worldId,
			Integer channelId,Integer finish,Integer valid,Integer operatorId)throws Exception; 
	
	/**
	 * 批量添加
	 * @param wIds
	 * @param channelId
	 * @param finish
	 * @param valid
	 * @param operator
	 * @throws Exception
	 */
	public void batchAddChannelWorldSchedula(String[] wIds, Date schedula,Integer minuteTimeSpan,Integer channelId,Integer finish,
			Integer valid,Integer operator)throws Exception;
	
	/**
	 * 更新计划
	 * @throws Exception
	 */
	public void channelWorldSchedula()throws Exception;
	
	/**
	 * 重新排序
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	public void reSort(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception;

}
