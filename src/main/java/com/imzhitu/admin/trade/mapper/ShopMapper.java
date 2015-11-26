package com.imzhitu.admin.trade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.pojo.Shop;

/**
 * 商家数据操作类
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public interface ShopMapper {
	
	/**
	 * 新增商家
	 * 
	 * @param shop	商家对象
	 * @author zhangbo	2015年11月20日
	 */
	@DataSource("master")
	void insertShop(Shop shop);
	
	@DataSource("master")
	void insertShopLinshi(Shop shop);
	
	/**
	 * @param start
	 * @param limit
	 * @return
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("slave")
	List<Shop> queryShopListByLimit(@Param("firstRow")Integer start, @Param("limit")Integer limit);
	
	/**
	 * 获取商家总数
	 * @return
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("slave")
	Integer getShopTotalCount();

	/**
	 * 根据id删除商家
	 * 
	 * @param id	商家主键id
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("master")
	void delete(@Param("id")Integer id);

}