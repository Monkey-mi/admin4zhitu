package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.Map;


public interface OpStarRecommendSchedulaService {
	/**
	 * 增加
	 * @param dto
	 */
	public void insertStarRecommendSchedula(Integer osrId,Integer userId,Date schedula,Integer operator,Integer valid,Integer top)throws Exception;
	
	/**
	 * 删除
	 * @param dto
	 */
	public void deleteStarRecommendSchedula(Integer id,Integer osrId,Integer userId,Integer valid)throws Exception;
	
	/**
	 * 修改
	 * @param dto
	 */
	public void updateStarRecommendSchedula(Integer id,Integer osrId,Integer userId,Integer valid,Date schedula,Integer operator,Integer finish)throws Exception;
	
	/**
	 * 分页查询
	 * @param dto
	 */
	public void	queryStarRecommendSchedula(Integer maxId,int page,int rows ,Map<String,Object>jsonMap,Integer id,Integer osrId,Integer userId,Integer valid,Date addDate,Date modifyDate,Integer top,Integer finish)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param dto
	 */
	public long queryStarRecommendSchedulaTotalCount(Integer maxId,Integer id,Integer osrId,Integer userId,Integer valid,Date addDate,Date modifyDate,Integer top,Integer finish)throws Exception;
	
	/**
	 * 重新排序计划
	 * @param wIds
	 * @param schedula
	 * @param operator
	 * @throws Exception
	 */
	public void reSort(String[] osrIds,Date schedula,Integer operator,Integer timeMinute)throws Exception;
	
	/**
	 * 执行达人推荐置顶计划
	 */
	public void doStarRecommendSchedula();
}
