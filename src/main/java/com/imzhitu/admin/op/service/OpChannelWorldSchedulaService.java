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
	 * 批量频道织图计划排序和生效
	 * @param wIds
	 * @param channelId
	 * @param finish
	 * @param valid
	 * @param operator
	 * @throws Exception
	 */
	public void batchChannelWorldToSortAndValidSchedula(Integer channelId, Integer[] worldIds, Date schedula, Integer minuteTimeSpan)throws Exception;
	
	/**
	 * 批量频道织图计划加精
	 * 
	 * @param channelId			频道id
	 * @param worldIds			织图id集合
	 * @param schedula			计划执行时间
	 * @param minuteTimeSpan	每个织图在执行时间上的间隔的时间
	 * @author zhangbo	2015年9月12日
	 */
	public void batchChannelWorldToSuperbSchedula(Integer channelId, Integer[] worldIds, Date schedula, Integer minuteTimeSpan);
	
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
