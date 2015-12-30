package com.imzhitu.admin.trade.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.shop.pojo.ShopBase;

/**
 * 商家数据操作类
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public interface ShopBaseMapper {
	
	/**
	 * 新增商家
	 * 
	 * @param shop	商家对象
	 * @author zhangbo	2015年11月20日
	 */
	@DataSource("master")
	void insertShop(ShopBase shop);
	
	@DataSource("master")
	void insertShopLinshi(ShopBase shop);
	
	/**
	 * @param start
	 * @param limit
	 * @return
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("slave")
	List<ShopBase> queryShopListByLimit(@Param("cityId")Integer cityId, @Param("firstRow")Integer start, @Param("limit")Integer limit);
	
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

	/**
	 * @param sids
	 * @author zhangbo	2015年12月30日
	 */
	List<ShopBase> queryShopByIds(Integer[] sids);

}
