package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户运营模块业务访问接口
 * </p>
 * 
 * 创建时间：2014-1-6
 * @author ztj
 *
 */
public interface OpUserService extends BaseService {

	/**
	 * 构建推荐用户列表
	 * 
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param userAccept
	 * @param sysAccept
	 * @param notified
	 * @param fixPos
	 * @param verifyId
	 * @param userName
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildRecommendUser(Integer maxId, int page, int rows, 
			Integer userAccept, Integer sysAccept, Integer notified,Integer fixPos,
			Integer verifyId, String userName,Integer lastUsed, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存推荐用户
	 * 
	 * @param userId
	 * @param verifyId
	 * @param recommendDesc
	 * @param recommenderId
	 * @throws Exception
	 */
	public Integer saveRecommendUser(Integer userId, Integer verifyId, String recommendDesc, 
			Integer recommenderId) throws Exception;
	
	/**
	 * 批量保存广场推送
	 * 
	 * @param idsStr
	 * @param recommenderId
	 * @throws Exception
	 */
//	public void batchSaveRecommendUser(String idsStr, 
//			Integer recommenderId) throws Exception;
	
	/**
	 * 根据用户id删除推荐用户
	 * 
	 * @param userId 用户id
	 * @param deleteStar 删除明星标记
	 */
	public void deleteRecommendUserByUserId(Integer userId, Boolean deleteStar) throws Exception;
	
	/**
	 * 根据ids批量删除推荐用户
	 * 
	 * @param idsStr
	 * @param deleteStar
	 */
	public void deleteRecommendUserByIds(String idsStr, Boolean deleteStar,Boolean insertMessage) throws Exception;
	
	/**
	 * 根据id插入一条删除用户明星的消息
	 * @param id
	 * @param insertMessage
	 * @throws Exception
	 */
	public void insertDelMessage(Integer id,Boolean insertMessage) ;
	
	/**
	 * 根据JSON更新推荐用户
	 * 
	 * @param userJSON
	 * @throws Exception
	 */
	public void updateRecommendUserByJSON(String userJSON) throws Exception;
	
	/**
	 * 更新推荐系统接受状态
	 * 
	 * @param idsStr
	 * @param state
	 * @throws Exception
	 */
	public void updateRecommendSysAccept(String idsStr, Integer state) throws Exception;
	
	/**
	 * 更新推荐用户排序
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void updateRecommendUserIndex(String[] idStrs) throws Exception;
	
	/**
	 * 添加推荐用户私信
	 * 
	 * @param id
	 * @param userId
	 * @param recipientName
	 * @param msg
	 * @param userAccept
	 * @param accepted
	 * @throws Exception
	 */
	public void addRecommendUserMsg(Integer id, Integer userId, 
			String recipientName, String msg, String recommendType, Integer userAccept, Boolean accepted) throws Exception;
	
	/**
	 * 更新认证类型
	 * 
	 * @param id
	 * @param verifyId
	 * @throws Exception
	 */
	public void updateRecommendVerify(Integer userId, Integer verifyId) throws Exception;
	
	/**
	 * 更新权重
	 * 
	 * @return
	 */
	public Integer updateRecommendWeight(Integer id, Boolean isAdd) throws Exception;
	
	/**
	 * 根据用户id更新权重
	 * 
	 * @param userId
	 * @param isAdd
	 * @return
	 * @throws Exception
	 */
	public Integer updateRecommendWeightByUID(Integer userId, Boolean isAdd, Integer recommendUID) throws Exception;
	
	/**
	 * 构建僵尸用列表
	 * 
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildZombieUser(Integer min,Integer max,Integer userId,String userName,Date begin,Date end,int page, int rows, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存僵尸用户
	 * @param userName
	 * @param loginCode
	 * @param userAvatar
	 * @param userAvatarL
	 * @param recommender
	 * @throws Exception
	 */
	public void saveZombieUser(String userName, String loginCode, String userAvatar,
			String userAvatarL, String recommender) throws Exception;
	
	/**
	 * 保存僵尸用户
	 * @param idsStr
	 * @param recommender
	 * @throws Exception
	 */
	public void saveZombieUsers(String idsStr, String recommender) throws Exception;
	
	/**
	 * 批量删除僵尸用户
	 * 
	 * @param idsStr
	 */
	public void batchDeleteZombieUsers(String idsStr) throws Exception;
	
	/**
	 * 屏蔽僵尸
	 * @param userId
	 * @throws Exception
	 */
	public void shieldZombie(Integer userId) throws Exception;
	
	/**
	 * 取消屏蔽僵尸
	 * @param userId
	 * @throws Exception
	 */
	public void unShieldZombie(Integer userId) throws Exception;
	
	/**
	 * 更新所有马甲登陆状态
	 * 
	 * @throws Exception
	 */
	public void saveOrUpdateZombieLoginStatus() throws Exception;
	
	
	/**
	 * 获取随机马甲列表
	 * 
	 * @param length
	 * @return
	 */
	public List<Integer> getRandomUserZombieIds(Integer length);
	
	/**
	 * 获取随机未关注的马甲列表，并且该马甲没有评论过该织图
	 * 
	 * @param length
	 * @return
	 */
	public List<Integer> getRandomUnFollowUserZombieIds(Integer userId, Integer worldId,Integer length);
	
	/**
	 * 获取随机未关注的马甲列表
	 * 
	 * @param length
	 * @return
	 */
	public List<Integer> getRandomUnFollowUserZombieIds(Integer userId,Integer length);
	
	/**
	 * 删除明星标志
	 */
	public void delStar(Integer userId)throws Exception;
	
	/**
	 * 获取随机关注的马甲列表，并且该马甲没有评论过该织图
	 * @param length
	 * @return
	 */
	public List<Integer> getRandomFollowUserZombieIds(Integer userId,Integer worldId,Integer page, Integer length);
	
	/**
	 * 直接让用户接受邀请 
	 * @param userId
	 * @param verifyId
	 * @throws Exception
	 */
	public void userAcceptRecommendDirect(Integer userId,Integer verifyId)throws Exception;
	
	/**
	 * 更新马甲的性别
	 * @param sex
	 * @param userId
	 */
	public void updateZombieSex(Integer sex,Integer userId)throws Exception;
	
	/**
	 *更新马甲的签名 
	 * @param signture
	 * @param userId
	 */
	public void updateZombieSignText(String signture,Integer userId)throws Exception;
	
	/**
	 * 更新马甲的昵称
	 * @param userName
	 * @param userId
	 */
	public void updateZombieUserName(String userName,Integer userId)throws Exception;
	
	/**
	 * 更新马甲信息
	 * @param rowJson
	 * @throws Exception
	 */
	public void updateZombie(String rowJson)throws Exception;
	
	/**
	 * 更新马甲的职业
	 * @param job
	 * @param userId
	 */
	public void updateZombieJob(String job,Integer userId)throws Exception;
	
	/**
	 * 更新马甲的被赞总数
	 * @param likeMeCount
	 * @param userId
	 */
	public void updateZombieLikeMeCount(Integer  likeMeCount,Integer userId)throws Exception;
	
	/**
	 * 更新马甲的地址
	 * @param address
	 * @param userId
	 */
	public void updateZombieAddress(String address,Integer userId)throws Exception;
	
	/**
	 * 更新省份和城市
	 * @param province
	 * @param city
	 * @param userId
	 */
	public void updateZombieProvinceCity(String province,String city,Integer userId)throws Exception;
	
}
