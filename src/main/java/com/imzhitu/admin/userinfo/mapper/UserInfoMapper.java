package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.UserInfo;

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
	
}