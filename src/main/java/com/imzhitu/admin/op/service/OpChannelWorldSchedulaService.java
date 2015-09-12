package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;


public interface OpChannelWorldSchedulaService extends BaseService{
	/**
	 * 分页查询频道有效性
	 * @throws Exception
	 */
	public void queryChannelWorldValidSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * mishengliang
	 * 分页查询频道精选
	 * @throws Exception
	 */
	public void queryChannelWorldSuperbSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception;
	
	
	/**
	 * 更新 
	 * @throws Exception
	 */
	public void updateChannelWorldValidSchedula(Integer id,Integer userId,Integer worldId,
			Integer channelId,Integer finish,Integer valid,Integer operatorId,Date schedulaDate)throws Exception;
	
/*	
	*//**
	*更新 频道精选计划
	 * @throws Exception
	 *//*
	public void updateChannelWorldSuperbSchedula(Integer id,Integer userId,Integer worldId,
			Integer channelId,Integer finish,Integer valid,Integer operatorId,Date schedulaDate)throws Exception;*/
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void delChannelWorldValidSchedula(String idsStr)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void delChannelWorldSuperbSchedula(String idsStr)throws Exception;
	
	/**
	 * 批量添加
	 * @param wIds
	 * @param superbWids	加精的worldid集合，用,分割
	 * @param channelId
	 * @param finish
	 * @param valid
	 * @param operator
	 * @throws Exception
	 */
	public void batchAddChannelWorldSchedula(String[] wIds,Date schedula,Integer minuteTimeSpan,Integer channelId,Integer finish,
			Integer valid,Integer operator)throws Exception;
	
	/**
	 * 更新计划
	 * @throws Exception
	 */
	public void channelWorldSchedula()throws Exception;
	
	/**
	 * 重新排序频道有效性计划
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	public void reSortValid(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception;
	
	
	/**
	 * 重新排序频道精选计划
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	public void reSortSuperb(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception;

}
