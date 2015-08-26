package com.imzhitu.admin.op.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpSquare;
import com.hts.web.common.pojo.OpUserZombie;

/**
 * <p>
 * 僵尸用户数据访问接口
 * </p>
 * 
 * 创建时间：2013-9-10
 * @author ztj
 *
 */
public interface UserZombieDao extends BaseDao {

	/**
	 * 查询僵尸用户
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpUserZombie> queryZombieUser(Integer min,Integer max,Integer userId,String userName,Date begin,Date end,RowSelection rowSelection);
	
	/**
	 * 查询僵尸用户总数
	 * 
	 * @return
	 */
	public long queryZombieUserCount(Integer min,Integer max,Integer userId,String userName,Date begin,Date end);
	
	/**
	 * 保存推荐用户
	 * 
	 * @param recommend
	 */
	public void saveZombieUser(OpSquare recommend);
	

	/**
	 * 根据用户id删除僵尸
	 * 
	 * @param userId
	 */
	public void deleteZombieUserByUserId(Integer userId);
	
	/**
	 * 根据id删除僵尸
	 * 
	 * @param id
	 */
	public void deleteZombieUserById(Integer id);
	
	/**
	 * 随机查询僵尸id
	 * 
	 * @param limit
	 * @return
	 */
	public List<Integer> queryRandomZombieId(int limit);
	
	/**
	 * 更新屏蔽标志
	 * 
	 * @param userId
	 * @param shield
	 */
	public void updateShield(Integer userId, Integer shield);
	
	/**
	 * 查询所有马甲用户id
	 * 
	 * @return
	 */
	public List<Integer> queryUnShieldZombieUserId();
	
	/**
	 * 查询马甲总数
	 * 
	 * @param shield
	 * @return
	 */
	public long queryZombieCount(Integer shield);
	
	
	/**
	 * 根据页码查询用户id
	 * 
	 * @param shield
	 * @param page
	 * @return
	 */
	public Integer queryUserIdByPageIndex(int shield, int page);
	
	/**
	 * 查询未跟随的马甲总数
	 * 
	 * @param userId
	 * @param shield
	 * @return
	 */
	public long queryUnFollowZombieCount(Integer userId, Integer shield);
	
	/**
	 * 根据页码查询未跟随用户id
	 * 
	 * @param shield
	 * @param page
	 * @return
	 */
	public Integer queryUnFollowUserIdByPageIndex(Integer userId, int shield, int page);
	
	/**
	 * 查询userId对应的僵尸粉
	 * @param userId
	 * @param limit
	 * @return
	 */
	public Integer queryZombieFollowByUserId(Integer userId,int page,int limit);
	
	/**
	 * 查询userId对应的僵尸粉总数
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年8月26日 17:23:45
	 */
	public long queryZombieFollowCountByUserId(Integer userId);
	
	/**
	 * 查询userId对应的已关注的马甲列表，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:14
	 */
	public Integer queryZombieFollowByUserIdForInteractForInteract(Integer userId,Integer worldId,int page,int limit);
	
	/**
	 * 查询userId对应的已关注的马甲总数，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:03
	 */
	public long queryZombieFollowCountByUserIdForInteractForInteract(Integer userId,Integer worldId);
	
	/**
	 * 查询userId对应的未关注的马甲列表，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:14
	 */
	public Integer queryUnFollowUserIdByPageIndexForInteract(Integer userId,Integer worldId, int shield,int page);
	
	/**
	 * 查询userId对应的未关注的马甲总数，但这些马甲是没有评论该织图的马甲
	 * @param userId
	 * @return
	 * @author zxx
	 * @time 2014年9月23日 17:05:03
	 */
	public long queryUnFollowZombieCountForInteract(Integer userId, Integer worldId,Integer shield);
	
	/**
	 * 随机选取total个
	 * @param userId
	 * @param worldId
	 * @param shield
	 * @param total
	 * @return
	 */
	public List<Integer> queryRandomUnFollowUserId(Integer userId,Integer worldId,int shield,int total);
	
	/**
	 * 查询userId对应的已关注的马甲列表，但这些马甲是没有评论该织图的马甲。一次性查取total个
	 * @param userId
	 * @param worldId
	 * @param total
	 * @return
	 */
	public List<Integer> queryRandomZombieFollow(Integer userId,Integer worldId,int total);
	
	/**
	 * 更新马甲的性别
	 * @param sex
	 * @param userId
	 */
	public void updateZombieSex(Integer sex,Integer userId);
	
	/**
	 *更新马甲的签名 
	 * @param signture
	 * @param userId
	 */
	public void updateZombieSignText(String signture,Integer userId);
	
	/**
	 * 更新马甲的昵称
	 * @param userName
	 * @param userId
	 */
	public void updateZombieUserName(String userName,Integer userId);
	
	/**
	 * 更新马甲的职业
	 * @param job
	 * @param userId
	 */
	public void updateZombieJob(String job,Integer userId);
	
	/**
	 * 更新马甲的被赞总数
	 * @param likeMeCount
	 * @param userId
	 */
	public void updateZombieLikeMeCount(Integer  likeMeCount,Integer userId);
	
	/**
	 * 更新马甲的地址
	 * @param address
	 * @param userId
	 */
	public void updateZombieAddress(String address,Integer userId);
	
	/**
	 * 更新省份和城市
	 * @param province
	 * @param city
	 * @param userId
	 */
	public void updateZombieProvinceCity(String province,String city,Integer userId);
	
	/**
	 * 查询是否为马甲
	 * @param userId
	 * @return
	 */
	public boolean isZombie(Integer userId);
}
