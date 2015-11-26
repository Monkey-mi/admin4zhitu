package com.imzhitu.admin.trade.mapper;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

/**
 * 商家评论数据操作类
 * 
 * @author zhangbo	2015年11月23日
 *
 */
public interface ShopCommentMapper {
	
	/**
	 * 新增商家评论
	 * 
	 * @param shopId	商家id
	 * @param content	评论内容
	 * @author zhangbo	2015年11月23日
	 */
	@DataSource("master")
	void insert(@Param("shopId")Integer shopId, @Param("content")String content);
	
	/**
	 * 根据id删除商家评论
	 * 
	 * @param id	商家评论主键id
	 * @author zhangbo	2015年11月23日
	 */
	@DataSource("master")
	void delete(@Param("id")Integer id);
}
