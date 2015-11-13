package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpChannelWorldSchedulaDto;


public interface OpChannelWorldSchedulaService extends BaseService{
	/**
	 * 分页查询频道有效性
	 * @throws Exception
	 */
	void queryChannelWorldValidSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * mishengliang
	 * 分页查询频道精选
	 * @throws Exception
	 */
	void queryChannelWorldSuperbSchedulaForList(Integer maxId, int page, int rows, 
			Integer id, Integer userId, Integer worldId,Integer channelId,Integer finish,
			Integer valid,Date addDate,Date modifyDate, Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * 根据频道id与织图id查询频道织图有效性计划对象集合
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @return	频道织图有效性计划对象集合（理论上只有一条）
	 * @author zhangbo	2015年11月4日
	 */
	List<OpChannelWorldSchedulaDto> queryChannelWorldValidSchedulaForList(Integer channelId, Integer worldId) throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	void delChannelWorldValidSchedula(String idsStr)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	void delChannelWorldSuperbSchedula(String idsStr)throws Exception;
	
	/**
	 * 批量频道织图计划排序和生效
	 * @param wIds
	 * @param channelId
	 * @param finish
	 * @param valid
	 * @param operator
	 * @throws Exception
	 */
	void batchChannelWorldToSortAndValidSchedula(Integer channelId, Integer[] worldIds, Date schedula, Integer minuteTimeSpan)throws Exception;
	
	/**
	 * 批量频道织图计划加精
	 * 
	 * @param channelId			频道id
	 * @param worldIds			织图id集合
	 * @param schedula			计划执行时间
	 * @param minuteTimeSpan	每个织图在执行时间上的间隔的时间
	 * @author zhangbo	2015年9月12日
	 */
	void batchChannelWorldToSuperbSchedula(Integer channelId, Integer[] worldIds, Date schedula, Integer minuteTimeSpan);
	
	/**
	 * 重新排序频道有效性计划
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	void reSortValid(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception;
	
	
	/**
	 * 重新排序频道精选计划
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	void reSortSuperb(String[] ids,Date schedula,Integer minuteTimeSpan,Integer operator)throws Exception;

}
