package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.UserInfo;

/**
 * <p>
 * 织图世界收藏数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldKeepDao extends BaseDao {


	/**
	 * 查询收藏指定织图的运营用户数据
	 * 
	 * @param worldId
	 * @param rowSelection
	 * @return
	 */
	public List<UserInfo> queryKeepUser(
			Integer worldId, RowSelection rowSelection);


	/**
	 * 根据最大id查询收藏指定织图的运营用户数据
	 * 
	 * @param worldId
	 */
	public List<UserInfo> queryKeepUser(
			Integer maxId, Integer worldId, RowSelection rowSelection);


	/**
	 * 根据最大id查询收藏指定织图的运营用户总数
	 * 
	 * @param worldId
	 */
	public long queryKeepUserCount(Integer maxId, Integer worldId);
}
