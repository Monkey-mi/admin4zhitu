package com.imzhitu.admin.userinfo.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.UserConcern;

/**
 * <p>
 * 用户关注信息数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * 
 * @author ztj
 * 
 */
public interface UserConcernDao extends BaseDao {

	
	/**
	 * 根据最大id查询粉丝总数
	 * 
	 * @param userId
	 * @param maxId
	 * @return
	 */
	public long queryFollowCountByMaxId(Integer userId, Integer maxId);
	
	
	/**
	 * 查询用户粉丝信息
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcern> queryFollow(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询用户粉丝信息
	 * 
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcern> queryFollowByMaxId(Integer userId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询关注总数
	 * 
	 * @param userId
	 * @param maxId
	 * @return
	 */
	public long queryConcernCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 查询关注用户信息
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcern> queryConcern(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询关注用户信息
	 * 
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcern> queryConcernByMaxId(Integer userId, Integer maxId, RowSelection rowSelection);
	
}
