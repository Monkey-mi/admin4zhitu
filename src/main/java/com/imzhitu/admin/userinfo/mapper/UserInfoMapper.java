package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.UserInfoDto;

public interface UserInfoMapper {

	@DataSource("master")
	public int deleteById(Integer id);
    
	@DataSource("master")
	public int insertSelective(UserInfo record);

	@DataSource("slave")
	public UserInfo selectById(Integer id);

	@DataSource("master")
	public int updateLoginInfoById(UserInfo record);
	
	@DataSource("master")
	public int updateByIdSelective(UserInfo record);
	
	@DataSource("slave")
	public List<UserInfo> selectByIds(Integer[] ids);
	
	@DataSource("slave")
	public int selectMaxId();
	
	@DataSource("master")
	public void updateTrust(@Param("id")Integer id, 
			@Param("trust")Integer trust);
	
	@DataSource("slave")
	public List<Integer> queryUID(@Param("minId")Integer minId, @Param("maxId")Integer maxId, @Param("limit")Integer limit);
	
	/**
	 * 这个方法与queryUserInfoDto基本是相同的，区别点就是返回值不同
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<UserInfo> queryUserInfo(UserInfo dto);
	
	/**
	 *  这个方法与queryUserInfo基本是相同的，区别点就是返回值不同
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<UserInfoDto> queryUserInfoDto(UserInfo dto);
	
	@DataSource("slave")
	public long queryUserInfoTotalCount(UserInfo dto);
	
	@DataSource("master")
	public void updateStarByIds(@Param("ids")Integer[]ids, @Param("star")Integer star );
	
	/**
	 * 查询用户名
	 * 
	 * @param id
	 * @return
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-29
	 */
	@DataSource("slave")
	public String queryUserName(@Param("id")Integer id);
	
	/**
	 * 根据ids查询用户信息列表
	 * 
	 * @param ids
	 * @return
	 */
	@DataSource("slave")
	public List<UserInfoDto> queryUserInfoDtoByIds(@Param("ids")Integer[] ids);

	/**
	 * 根据id获取用户信息
	 * 
	 * @param userId 用户id
	 * @author zhangbo	2015年11月26日
	 */
	@DataSource("slave")
	UserInfo getUserInfo(@Param("id")Integer userId);

	/**
	 * 根据用户名称得到用户id集合，名称为模糊匹配
	 * 
	 * @param userName	用户名
	 * @return
	 * @author zhangbo	2015年12月17日
	 */
	@DataSource("slave")
	List<Integer> getUserIdsByName(@Param("userName")String userName);

}