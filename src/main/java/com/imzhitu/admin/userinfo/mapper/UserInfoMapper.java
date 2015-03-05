package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.pojo.UserInfo;

public interface UserInfoMapper {

	public int deleteById(Integer id);
    
	public int insertSelective(UserInfo record);

	public UserInfo selectById(Integer id);

	public int updateLoginInfoById(UserInfo record);
	
	public int updateByIdSelective(UserInfo record);
	
	public List<UserInfo> selectByIds(Integer[] ids);
	
	public int selectMaxId();
	
	public void updateTrust(@Param("id")Integer id, 
			@Param("trust")Integer trust);
	
}