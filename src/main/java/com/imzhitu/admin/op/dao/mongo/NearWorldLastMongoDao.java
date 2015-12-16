package com.imzhitu.admin.op.dao.mongo;

import java.util.List;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.common.pojo.OpNearWorldDto;

public interface NearWorldLastMongoDao extends BaseMongoDao{
	/**
	 * 查询附近织图
	 * @param cityId
	 * @param limit
	 * @return
	 * @author zxx 2015-12-8 19:33:48
	 */
	public List<OpNearWorldDto> queryNear(int maxId,Integer cityId,int start,int limit);
	
	/**
	 * 根据id查询附近织图
	 * @param id
	 * @return
	 * @author zxx 2015-12-15 20:26:20
	 */
	public OpNearWorldDto queryNearWorldLastById(Integer id);
	
	/**
	 * 查询附近织图总数
	 * @param cityId
	 * @return
	 * @author zxx 2015-12-8 19:33:48
	 */
	public long queryNearTotalCount(Integer cityId);
	
	
	/**
	 * 保存织图
	 * 
	 * @param world
	 * @author zxx 2015-12-15 19:56:18
	 */
	public void saveNearWorldLast(OpNearWorldDto world);
	
	/**
	 * 删除织图
	 * 
	 * @param id
	 * @author zxx 2015-12-15 19:56:18
	 */
	public void deleteNearWorldLast(Integer id);
}
