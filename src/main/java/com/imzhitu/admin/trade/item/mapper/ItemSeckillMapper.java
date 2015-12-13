package com.imzhitu.admin.trade.item.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

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
	@DataSource("master")
	void insert(@Param("id")Integer id, @Param("deadline")Date deadline);
	
	/**
	 * 根据商品id集合删除
	 * 
	 * @param ids		商品id集合
	 * @author zhangbo	2015年12月11日
	 */
	@DataSource("master")
	void deleteByIds(@Param("ids")Integer[] ids);
	
}
