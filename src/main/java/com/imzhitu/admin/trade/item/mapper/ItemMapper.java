package com.imzhitu.admin.trade.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.item.pojo.Item;

/**
 * 商品数据操作类
 * 
 * @author zhangbo	2015年12月9日
 *
 */
public interface ItemMapper {
	
	/**
	 * 新增商品，商品对象设置的属性都会持久化到数据库
	 * 
	 * @param item
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	public void saveItem(Item item);
	
	/**
	 * 根据id更新商品，可以更新商品对象的所有属性
	 * 
	 * @param item
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	public void updateItem(Item item);
	
	/**
	 * 根据id删除商品
	 * 
	 * @param id
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	public void deleteById(@Param("id")Integer id);
	
	/**
	 * 根据ids删除商品
	 * 
	 * @param ids
	 * @author lynch 2015-12-13
	 */
	@DataSource("master")
	public void deleteByIds(@Param("ids")Integer[] ids);

	/**
	 * 查询商品列表
	 * 
	 * @param item
	 * @return
	 */
	@DataSource("slave")
	public List<Item> queryItemList(Item item);
	
	/**
	 * 查询商品总数
	 * 
	 * @param item
	 * @return
	 */
	@DataSource("slave")
	public long queryItemTotal(Item item);
	
	/**
	 * 根据id查询商品
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Item queryItemById(Integer id);
	
	
}
