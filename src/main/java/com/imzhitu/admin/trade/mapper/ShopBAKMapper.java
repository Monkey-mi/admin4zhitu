package com.imzhitu.admin.trade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.ShopBAK;
import com.imzhitu.admin.trade.pojo.Shop;

/**
 * @author zhangbo	2015年11月23日
 *
 */
public interface ShopBAKMapper {
	/**
	 * @param start
	 * @param limit
	 * @return
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("slave")
	List<ShopBAK> queryShopBakListByLimit(@Param("firstRow")Integer start, @Param("limit")Integer limit);
	
}
