package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.OpMsgStartpage;

/**
 * 
 * @author zxx
 *
 */
public interface OpMsgStartpageService {
	/**
	 * 增加
	 * @param linkPath
	 * @param linkType
	 * @param link
	 * @param beginDate
	 * @param endDate
	 * @param operator
	 * @throws Exception
	 */
	public void insertMsgStartpage(String linkPath,Integer linkType,String link,Date beginDate,Date endDate,Integer operator)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteMsgStartpage(String idsStr)throws Exception;
	
	/**
	 * 更新
	 * @param id
	 * @param linkPath
	 * @param linkType
	 * @param link
	 * @param valid
	 * @param beginDate
	 * @param endDate
	 * @param operator
	 * @throws Exception
	 */
	public void updateMsgStartpage(Integer id, String linkPath,Integer linkType,String link,Integer valid,Date beginDate,Date endDate,Integer operator)throws Exception;
	
	/**
	 * 批量更新有效性
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchUpdateMsgStartpageValid(String idsStr,Integer valid,Integer operator)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param linkType
	 * @param valid
	 * @param isCache
	 * @param beginDate
	 * @param endDate
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryMsgStartpage(Integer id,Integer linkType,Integer valid,Integer isCache,Date beginDate,Date endDate,Integer maxId,int page,int rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param id
	 * @param linkType
	 * @param valid
	 * @param beginDate
	 * @param endDate
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public long queryMsgStartpageTotalCount(Integer id,Integer linkType,Integer valid,Date beginDate,Date endDate,Integer maxId)throws Exception;
	
	/**
	 * 根据ids 来查询
	 * @param ids
	 * @return
	 */
	public List<OpMsgStartpage> queryMsgBulletinByIds(String idsStr)throws Exception;
	
	/**
	 * 更新缓存
	 * @param idsStr
	 * @throws Exception
	 */
	public void updateMsgStartpageCache(String idsStr)throws Exception;
}
