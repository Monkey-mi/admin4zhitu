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
	void insert(Item item);
	
	/**
	 * 根据id更新商品，可以更新商品对象的所有属性
	 * 
	 * @param item
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	void update(Item item);

	/**
	 * 分页查询商品，若fristRow与limit传递为null，则查询全部商品
	 * 
	 * @param fristRow	分页起始位置
	 * @param limit		每页查询数量
	 * 
	 * @return List<Item>	商品结果集
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("slave")
	List<Item> queryItemList(@Param("firstRow")Integer firstRow, @Param("limit")Integer limit);
	
	/**
	 * 查询商品总数
	 * 
	 * @return total	商品总数
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("slave")
	Integer queryItemTotal();
	
	/**
	 * 根据id删除商品
	 * 
	 * @param id
	 * @author zhangbo	2015年12月9日
	 */
	@DataSource("master")
	void deleteById(@Param("id")Integer id);
	
}
