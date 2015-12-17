package com.imzhitu.admin.op.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;
import com.imzhitu.admin.op.pojo.NearBulletinDto;

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
	

	/**
	 * 保存附近公告
	 * 
	 * @param bulletin
	 * @throws Exception
	 * @author lynch 2015-12-15
	 */
	public void saveNearBulletin(NearBulletinDto bulletin) throws Exception;
	
	/**
	 * 更新附近公告
	 * 
	 * @param bulletin
	 * @throws Exception
	 * @author lynch 2015-12-15
	 */
	public void updateNearBulletin(NearBulletinDto bulletin) throws Exception;
	
	/**
	 * 根据id批量删除附近公告
	 * 
	 * @param ids
	 * @throws Exception
	 * @author lynch 2015-12-15
	 */
	public void delCityBulletinByIds(Integer[] ids) throws Exception;
	
	/**
	 * 查询附近
	 * @param bulletin
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildNearBulletin(NearBulletinDto bulletin, 
			int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据id删除附近公告
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delNearBulletinById(Integer id) throws Exception;
	
	/**
	 * 根据id查询附近公告
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public NearBulletinDto queryNearBulletinById(Integer id) throws Exception;
	
	
	/**
	 * 更新城市公告序号
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void updateCityBulletinSerial(String[] idStrs) throws Exception;
}
