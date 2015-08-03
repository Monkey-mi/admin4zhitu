package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ChatDTO;

/**
 * @author zhangbo	2015年8月3日
 *
 */
public interface ChatMapper {
	
	/**
	 * 查询骚扰私聊列表
	 * 
	 * @param world
	 * @return
	 */
	@DataSource("slave")
	List<ChatDTO> queryChatList(ChatDTO dto);
	
	/**
	 * 查询骚扰私聊总数
	 * 
	 * @param world
	 * @return
	 */
	@DataSource("slave")
	long queryChatCount(ChatDTO dto);

	/**
	 * 批量删除骚扰私聊
	 * 
	 * @param ids
	 * @author zhangbo	2015年8月3日
	 */
	@DataSource("master")
	void deleteChats(Integer[] ids);

}
