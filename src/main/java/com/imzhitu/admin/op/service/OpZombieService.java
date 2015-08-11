package com.imzhitu.admin.op.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpZombie;

public interface OpZombieService extends BaseService{
	/**
	 * 增加
	 * @param userId
	 * @param degreeId
	 * @param commentCount
	 * @param concernCount
	 * @throws Exception
	 */
	public void insertZombie(Integer userId,Integer  degreeId,Integer commentCount,Integer concernCount)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 */
	public void batchDeleteZombie(String idsStr)throws Exception;
	
	/**
	 * 批量更新马甲状态
	 * @param commentCount
	 * @param concernCount
	 * @param ids
	 * @throws Exception
	 */
	public void batchUpdateZombie(Integer commentCount,Integer concernCount,Integer[] ids)throws Exception;
	
	/**
	 * 分页查询
	 * @param userId
	 * @param degree
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryZombie(Integer userId,Integer degree,Integer maxId,int page,int rows, Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param userId
	 * @param degree
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public long queryZombieTotalCount(Integer userId,Integer degree,Integer maxId)throws Exception;
	
	/**
	 * 查询n个马甲
	 * @param degreeId
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public List<OpZombie> queryNZombie(Integer degreeId,Integer n)throws Exception;
	
	/*
	 * 更新马甲的性别和签名
	 * 
	 * */
	public void updateSexAndSignature(String zombieInfoJSON);
}
