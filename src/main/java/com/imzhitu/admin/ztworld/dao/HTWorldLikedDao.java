package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.ZTWorldLikeDto;

/**
 * <p>
 * 织图世界喜欢数据访问对象
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldLikedDao extends BaseDao {

	/**
	 * 查询喜欢指定织图的运营用户数据
	 * 
	 * @param worldId
	 */
	public List<ZTWorldLikeDto> queryLikedUser(Integer worldId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询喜欢指定织图的运营用户数据
	 * 
	 * @param worldId
	 */
	public List<ZTWorldLikeDto> queryLikedUser(Integer maxId, Integer worldId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询喜欢指定织图的运营用户总数
	 * 
	 * @param worldId
	 */
	public long queryLikedUserCount(Integer maxId, Integer worldId);
	
	
}
