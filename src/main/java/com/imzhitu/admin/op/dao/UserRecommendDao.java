package com.imzhitu.admin.op.dao;

import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpUserRecommend;
import com.hts.web.common.pojo.UserInfoDto;
import com.imzhitu.admin.common.pojo.OpUserRecommendDto;

/**
 * <p>
 * 推荐用户数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-29
 * @author ztj
 *
 */
public interface UserRecommendDao extends BaseDao {

	/**
	 * 查询推荐用户
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<OpUserRecommendDto> queryRecommendUser(Map<String, Object> attrMap, 
			RowSelection rowSelection);
	
	/**
	 * 查询推荐用户
	 * 
	 * @param maxId
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<OpUserRecommendDto> queryRecommendUser(Integer maxId, Map<String, Object> attrMap,
			RowSelection rowSelection);
	
	
	public List<OpUserRecommendDto> queryRecommendUserByName(Map<String, Object> attrMap, 
			RowSelection rowSelection);
	
	public List<OpUserRecommendDto> queryRecommendUserByName(Integer maxId, 
			Map<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询推荐用户总数
	 * 
	 * @param maxId
	 * @return
	 */
	public long queryRecommendUserCount(Integer maxId, Map<String, Object> attrMap);
	
	/**
	 * 根据用户id删除推荐
	 * @param userId
	 */
	public void deleteRecommendUserByUserId(Integer userId);
	
	/**
	 * 根据ids删除推荐
	 * 
	 * @param ids
	 */
	public void deleteRecommendUserByIds(Integer[] ids);
	
	/**
	 * 根据ids查询用户id
	 * 
	 * @param ids
	 * @return
	 */
	public List<Integer> queryUserIdByIds(Integer[] ids);
	
	
	/**
	 * 更新推荐描述
	 * 
	 * @param id
	 * @param desc
	 */
	public void updateRecommendDesc(Integer id, String desc);
	
	/**
	 * 更新推荐id
	 * 
	 * @param userId
	 */
	public void updateRecommendId(Integer userId, Integer id);
	
	/**
	 * 更新系统允许标记
	 * 
	 * @param ids
	 */
	public void updateSysAccept(Integer[] ids, Integer state);

	/**
	 * 更新通知状态
	 * 
	 * @param id
	 * @param notified
	 */
	public void updateNotified(Integer id, Integer notified);
	
	/**
	 * 查询用户id
	 * @param ids
	 * @return
	 */
	public List<Integer> queryUserId(Integer[] ids);
	
	/**
	 * 查询已经接受推荐的用户id
	 * 
	 * @param ids
	 * @return
	 */
	public List<OpUserRecommend> queryAcceptUserId(Integer[] ids);
	
	/**
	 * 跟新固定排名
	 * @param uid
	 * @param fixPos
	 */
	public void setFixPosByUserId(Integer uid , Integer fixPos);
	
	/**
	 * 根据id查询推荐用户
	 * 
	 * @param id
	 * @return
	 */
	public OpUserRecommend queryRecommendById(Integer id);
	
	/**
	 * 更新认证id
	 * 
	 * @param id
	 * @param verifyId
	 */
	public void updateVerifyId(Integer id, Integer verifyId);
	
	/**
	 * 查询通知状态
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryNotified(Integer id);
	
	/**
	 * 更新权重
	 * 
	 * @param id
	 * @param weight
	 */
	public void updateWeight(Integer id, Integer weight);
	
	/**
	 * 查询达人置顶状态
	 * 
	 * @param userId
	 * @return
	 */
	public Integer queryWeightByUID(Integer uid);
	
	/**
	 * 根据用户id查询id
	 * 
	 * @param uid
	 * @return
	 */
	public Integer queryIdByUID(Integer uid);
	
	/**
	 * 根据用户id查询用户是否为真的明星
	 * @param userId
	 * @return
	 */
	public long queryUserAccpetAndSysAcceptResult(Integer userId);
	
	/**
	 * 查询置顶的推荐用户
	 * @param limit
	 * @return
	 */
	public List<UserInfoDto> queryWeightUserRecommend(int limit);
	
	/**
	 * 根据认证类型查询非置顶的推荐用户
	 * @param verifyId
	 * @param limit
	 * @return
	 */
	public List<UserInfoDto> queryNotWeightUserRecommendByVerifyId(Integer verifyId,int limit);
}
