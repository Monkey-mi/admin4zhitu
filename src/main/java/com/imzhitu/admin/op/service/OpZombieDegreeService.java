package com.imzhitu.admin.op.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpZombieDegree;

public interface OpZombieDegreeService extends BaseService{
	/**
	 * 增加
	 * @param degreeName
	 * @param weight
	 * @throws Exception
	 */
	public void insertZombieDegree(String degreeName,Integer weight)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 */
	public void batchDeleteZombieDegree(String idsStr);
	
	/**
	 * 修改
	 * @param id
	 * @param degreeName
	 * @param weight
	 * @throws Exception
	 */
	public void updateZombieDegree(Integer id,String degreeName,Integer weight)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param weight
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryZombieDegree(Integer id,Integer weight,Integer maxId,int page,int rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param id
	 * @param weight
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public long queryZombieDegreeTotalCount(Integer id,Integer weight,Integer maxId)throws Exception;
	
	/**
	 * 查询所有的记录
	 * @return
	 */
	public List<OpZombieDegree> queryAllZombieDegree()throws Exception;
}
