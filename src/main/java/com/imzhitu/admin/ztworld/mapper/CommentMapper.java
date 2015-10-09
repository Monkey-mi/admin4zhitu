package com.imzhitu.admin.ztworld.mapper;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

public interface CommentMapper {

	@DataSource("slave")
	public Integer queryMaxId();
	
	@DataSource("slave")
	public String queryContent(@Param("id")Integer id);
	
	@DataSource("master")
	public void updateContent(@Param("id")Integer id,
			@Param("content")String content);
}
