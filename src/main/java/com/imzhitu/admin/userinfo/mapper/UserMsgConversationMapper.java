package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.UserMsgConversationDto;

/**
 * 私信对话数据访问接口
 * 
 * @author lynch 2015-10-29
 *
 */
public interface UserMsgConversationMapper {

	/**
	 * 查询对话列表
	 * 
	 * @param conver
	 * @return
	 */
	@DataSource("slave")
	public List<UserMsgConversationDto> queryConver(UserMsgConversationDto conver);
	
	/**
	 * 根据用户id查询对话列表
	 * 
	 * @param userId
	 * @return
	 */
	@DataSource("master")
	public List<UserMsgConversationDto> queryConverByOtherId(@Param("userId")Integer userId,
			@Param("otherId")Integer otherId);

	/**
	 * 查询对话总数
	 * 
	 * @param conver
	 * @return
	 */
	@DataSource("slave")
	public long queryConverCount(UserMsgConversationDto conver);
	
}
