package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.ZombieWorldSchedulaDto;

public interface InteractZombieWorldSchedulaService {
	/**
	 * 插入
	 * @param zombieWorldId
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	public void insertZombieWorldSchedula(Integer zombieWorldId,Date schedula,Integer operator)throws Exception;
	
	/**
	 * 批量插入马甲发图计划
	 * @param zombieWorldIdsStr
	 * @param schedula
	 * @param minuteTimeSpan
	 * @param operactor
	 * @throws Exception
	 */
	public void batchInsertZombieWorldSchedual(String zombieWorldIdsStr,Date schedula,Integer minuteTimeSpan,Integer operactor)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteZombieWorldSchedula(String idsStr)throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void updateZombieWorldSchedula(Integer id,Integer zombieWorldId,Integer valid,Integer finished,Date schedula,Integer operator)throws Exception;
	
	/**
	 * 分页查询
	 * @throws Exception
	 */
	public void queryZombieWorldSchedula(Integer id,Integer valid ,Integer finished,Integer zombieWorldId,Integer maxId,int page,int rows,Map<String,Object>jsonMap)throws Exception;

	/**
	 * 分页查询总数
	 * @param id
	 * @param valid
	 * @param finished
	 * @param zombieWorldId
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public long queryZombieWorldSchedulaTotalCount(Integer id,Integer valid ,Integer finished,Integer zombieWorldId,Integer maxId)throws Exception;
	
	/**
	 * 根据时间查询计划
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<ZombieWorldSchedulaDto> queryZombieWorldSchedulaByTime(Date beginDate,Date endDate,Integer valid,Integer finished)throws Exception;
	
	/**
	 * 马甲发图job
	 * @throws Exception
	 */
	public void doZombieWorldSchedulaJob()throws Exception;
}
