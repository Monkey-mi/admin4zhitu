package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.InteractTypeOptionWorldDto;

public interface InteractTypeOptionWorldService extends BaseService{
	
	public void insertTypeOptionWorld(Integer worldId,Integer userId,Integer valid,Integer superb,Integer operatorId)throws Exception;
	
	public void delTypeOptionWorldByIds(String idsStr)throws Exception;
	
	public void delTypeOptionWorldByWIds(String widsStr)throws Exception;
	
	public void updateTypeOptionWorld(Integer id,Integer worldId,Integer userId,Integer valid,Integer superb,Integer top,Integer operatorId)throws Exception;
	
	public void queryTypeOptionWorldForList(Integer maxId, int page, int rows,
			Integer id,Integer worldId,Integer userId,Integer valid,Integer superb,Integer top,Map<String, Object> jsonMap) throws Exception;
	
	public void autoAddStarWorld()throws Exception;
	
	/**
	 * 重新排序
	 * @param wids
	 * @param schedula
	 * @param operatorId
	 * @throws Exception
	 */
	public void reSort(String[] wids,Date schedula,Integer operatorId)throws Exception;
	
	/**
	 * 修改精选点评
	 * @param dto
	 */
	public void updateReview(Integer worldId,String review)throws Exception;
	
	/**
	 * 查询某个精选备选
	 * @param dto
	 * @return
	 */
	public InteractTypeOptionWorldDto queryTypeOptionWorld(Integer worldId)throws Exception;
	
	/**
	 * 根据织图id，查询是否已经存在于精选备选中
	 * 
	 * @param worldId	织图id	
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年11月29日
	 */
	boolean isExist(Integer worldId) throws Exception;

}
