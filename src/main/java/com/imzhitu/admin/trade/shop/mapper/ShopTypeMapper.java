package com.imzhitu.admin.trade.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.shop.pojo.ShopType;

/**
 * 商家类型数据操作类
 * 
 * @author zhangbo	2015年11月20日
 *
 */
public interface ShopTypeMapper {
	
	/**
	 * 新增商家类型
	 * 
	 * @param shop	商家对象
	 * @author zhangbo	2015年11月20日
	 */
	@DataSource("master")
	void insertShopType(@Param("name")String name);
	
	/**
	 * 查询商家类型列表
	 * 
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	@DataSource("slave")
	List<ShopType> queryShopType();
	
	/**
	 * 根据id删除商家类型
	 * 
	 * @param id	商家类型主键id
	 * @author zhangbo	2015年11月20日
	 */
	@DataSource("master")
	void delete(@Param("id")Integer id);

}
