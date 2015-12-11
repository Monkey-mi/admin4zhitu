package com.imzhitu.admin.trade.item.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * 秒杀商品数据操作类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public interface ItemSeckillMapper {

	/**
	 * 新增秒杀商品
	 * 
	 * @param id		商品id
	 * @param deadline	截止日期
	 * @author zhangbo	2015年12月11日
	 */
	void insert(@Param("id")Integer id, @Param("deadline")Date deadline);
	
	
	
}
