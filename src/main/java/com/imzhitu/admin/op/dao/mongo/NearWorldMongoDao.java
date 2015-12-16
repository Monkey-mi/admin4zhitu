package com.imzhitu.admin.op.dao.mongo;

import java.util.List;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.common.pojo.OpNearWorldDto;

public interface NearWorldMongoDao extends BaseMongoDao {
	/**
	 * 查询附近织图
	 * @param cityId
	 * @param limit
	 * @return
	 * @author zxx 2015-12-8 19:33:48
	 */
	public List<OpNearWorldDto> queryNear(int maxId,int cityId,int start,int limit);
	
	/**
	 * 查询附近织图总数
	 * @param cityId
	 * @return
	 * @author zxx 2015-12-8 19:33:48
	 */
	public long queryNearTotalCount(int cityId);
	
	/**
	 * 插入附近织图
	 * @param world
	 * @author zxx 2015-12-8 19:33:48
	 */
	public void saveWorld(OpNearWorldDto world);
}
