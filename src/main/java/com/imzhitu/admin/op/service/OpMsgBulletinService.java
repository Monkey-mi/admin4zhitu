package com.imzhitu.admin.op.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;

public interface OpMsgBulletinService extends BaseService{
	
	/**
	 * 添加
	 * @param path
	 * @param type
	 * @param link
	 * @param operator
	 * @throws Exception
	 */
	public void insertMsgBulletin(String path,Integer type,String link,Integer operator,String bulletinName,String bulletinThumb)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteMsgBulletin(String idsStr)throws Exception;
	
	/**
	 * 更新
	 * @param id
	 * @param path
	 * @param type
	 * @param link
	 * @param valid
	 * @param operator
	 * @throws Exception
	 */
	public void updateMsgBulletin(Integer id, String path,Integer type,String link,Integer valid,Integer operator,String bulletinName,String bulletinThumb)throws Exception;
	
	/**
	 * 批量更新有效性
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchUpdateMsgBulletinValid(String idsStr,Integer valid,Integer operator)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param type
	 * @param valid
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryMsgBulletin(Integer id,Integer type,Integer valid,Integer isCache,Integer maxId,int page,int rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param id
	 * @param type
	 * @param valid
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public long queryMsgBulletinTotalCount(Integer id,Integer type,Integer valid,Integer maxId)throws Exception;
	
	/**
	 * 根据ids来查询
	 * @param idsStr
	 * @return
	 * @throws Exception
	 */
	public List<OpMsgBulletin> queryMsgBulletinByIds(String idsStr)throws Exception;
	
	/**
	 * 更新缓存
	 * @param idsStr
	 * @throws Exception
	 */
	public void updateMsgBulletinCache(String idsStr,Integer operator)throws Exception;
}
