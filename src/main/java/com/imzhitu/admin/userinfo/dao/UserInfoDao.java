package com.imzhitu.admin.userinfo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.UserInfoDto;

/**
 * <p>
 * 用户信息数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
@Deprecated
public interface UserInfoDao extends BaseDao {
	
	
	
	/**
	 * 更新屏蔽标志
	 * 
	 * @param userId
	 * @param valid
	 */
	public void updateShield(Integer userId, Integer valid);
	
	/**
	 * 分页查询用户列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<UserInfoDto> queryUserInfo(RowSelection rowSelection);
	
	/**
	 * 根据最大id分页查询用户列表
	 * 
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserInfoDto> queryUserInfoByMaxId(int maxId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询用户总数
	 * 
	 * @param maxId
	 * @return
	 */
	public long queryUseInfoCountByMaxId(int maxId);
	
	/**
	 * 添加查询用户列表
	 * @param rowSelection
	 * @param attrMap
	 * @return
	 */
	public List<UserInfoDto> queryUserInfoByAttrMap(Map<String, Object> attrMap, RowSelection rowSelection);
	
	
	/**
	 * 查询用户列表
	 * @param id
	 * @return
	 */
	public UserInfoDto queryMaintainUserInfo(Integer id);
	
	/**
	 * 根据最大id和条件查询用户列表
	 * 
	 * @param maxId
	 * @param rowSelection
	 * @param attrMap
	 * @return
	 */
	public List<UserInfoDto> queryUserInfoByMaxIdAndAttrMap(int maxId, Map<String, Object> attrMap,
			RowSelection rowSelection);

	
	/**
	 * 根据用户名模糊查询用户列表
	 * 
	 * @param userName
	 * @param rowSelection
	 * @return
	 */
	public List<UserInfoDto> queryUserInfoByUserName(String userName, RowSelection rowSelection);
	
	
	/**
	 * 根据最大id和用户名模糊查询用户列表
	 * @param maxId
	 * @param userName
	 * @param rowSelection
	 * @return
	 */
	public List<UserInfoDto> queryUserInfoByMaxIdAndUserName(int maxId, String userName,
			RowSelection rowSelection);	
	
	/**
	 * 根据最大id和用户名模糊查询用户总数
	 * 
	 * @param maxId
	 * @param userName
	 * @return
	 */
	public long queryUseInfoCountByMaxIdAndUserName(int maxId, String userName);
	
	/**
	 * 根据id查询用户信息
	 * @param id
	 * @return
	 */
	public UserInfoDto queryUserInfoDtoById(Integer id);
	
	/**
	 * 更新明星标记
	 * 
	 * @param id
	 * @param star
	 */
	public void updateStarById(Integer id, Integer star);
	
	/**
	 * 查询明星标记
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryStar(Integer id);
	
	/**
	 * 根据ids更新明星标记
	 * 
	 * @param ids
	 * @param star
	 */
	public void updateStarByIds(Integer[] ids, Integer star);
	
	/**
	 * 根据游标构建用户信息POJO
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public UserInfo buildUserInfo(ResultSet rs) throws SQLException;
	
	/**
	 * 更新用户标签
	 * 
	 * @param userId
	 * @param label
	 */
	public void updateUserLabel(Integer userId, String label);
	
	/**
	 * 更新信任标记
	 * 
	 * @param userId
	 * @param trust
	 */
	public void updateTrustById(Integer userId, Integer trust);
	
	
	/**
	 * 查询当前用户登录的APP版本
	 * 
	 * @param userId
	 * @return
	 */
	public float queryVer(Integer userId);
	
	/**
	 * 更新用户标签
	 */
	public void updateSignature(Integer userId,String signature);
	
	/**
	 * 查询该用户是否为马甲
	 * @param userId
	 * @return
	 */
	public boolean queryUserIsZombieByUserId(Integer userId);
	
	/**
	 * 查询某用户的织图精选总数
	 * @param userId
	 * @return
	 */
	public long queryUserWorldCountByUserId(Integer userId);
	
}
