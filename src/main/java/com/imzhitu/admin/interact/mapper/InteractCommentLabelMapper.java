package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
//import org.springframework.stereotype.Service;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;

/*
 * add by mishengliang
 * 整改代码，将dao层优化为Mapper层
 * 08-11-2015
 * */

/*
 * 查询全部的二级标签
 * */
public interface InteractCommentLabelMapper {
	
	@DataSource("master")
	public List<InteractCommentLabel> selectInteractCommentLabel() throws Exception;
	
	/**
	 * 查询是否存在该名字的标签
	 */
	@DataSource("slave")
	public Integer checkLabelExsistByLabelName(@Param("labelName")String labelName) throws Exception;
	
	/*
	 * 将新建的标签插入数据库
	 * add by mishengliang
	 * 08-12-2015	
	 * */
	@DataSource("master")
	public void saveLabel(@Param("labelName")String labelName,@Param("groupId")Integer groupId) throws Exception;
	
	/*
	 * 查询所有 的标签
	 * */
	@DataSource("master")
	public List<InteractCommentLabel> queryLabel() throws Exception;
}



