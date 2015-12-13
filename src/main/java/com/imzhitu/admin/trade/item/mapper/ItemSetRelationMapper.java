package com.imzhitu.admin.trade.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.item.pojo.Item;

/**
 * 商品集合关联数据访问接口
 * 
 * @author lynch
 *
 */
public interface ItemSetRelationMapper {
	
	/**
	 * 插入单条关系数据
	 * @param itemSetId
	 * @param itemId
	 * @throws Exception 
	 */
	@DataSource("master")
	public void saveItem(@Param("itemSetId")Integer itemSetId,
			@Param("itemId")Integer itemId, @Param("serial")Integer serial);
	
	/**
	 * 根据商品集合id，查询其下的商品列表
	 * 
	 * @param itemSetId	商品集合id
	 * @return
	 * @author zhangbo	2015年12月10日
	 */
	@DataSource("slave")
	List<Item> queryItemListBySetId(@Param("itemSetId")Integer itemSetId);
	
	/**
	 * 根据商品id删除集合内的商品
	 * 
	 * @param itemId
	 */
	@DataSource("master")
	public void deleteByItemId(@Param("itemSetId")Integer itemSetId, 
			@Param("itemId")Integer itemId);
	
	/**
	 * 根据itemId查询集合id
	 * @param itemId
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> querySetId(@Param("itemId")Integer itemId);
	
	
	/**
	 * 更新排序
	 * 
	 * @param itemId
	 * @param setId
	 * @param serial
	 */
	@DataSource("master")
	public void updateSerial(@Param("itemId")Integer itemId, 
			@Param("itemSetId")Integer itemSetId, @Param("serial")Integer serial);
	
	
	/**
	 * 查询集合内的商品列表
	 * 
	 * @param item
	 * @return
	 */
	@DataSource("slave")
	public List<Item> querSetItem(Item item);
	
	/**
	 * 查询集合内的商品总数
	 * 
	 * @param item
	 * @return
	 * 
	 * @author lynch 2015-12-13
	 */
	@DataSource("slave")
	public long querySetItemTotal(Item item);

}
