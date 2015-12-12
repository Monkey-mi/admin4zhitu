package com.imzhitu.admin.trade.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.item.pojo.Item;

/**
 * 商品与商品集合关联关系数据操作类
 * 
 * @author mishengliang
 *
 */
public interface ItemAndSetRelationMapper {
	
	/**
	 * 插入单条关系数据
	 * @param itemSetId
	 * @param itemId
	 * @throws Exception 
		*	2015年12月10日
		*	mishengliang
	 */
	@DataSource("master")
	void insertItemToSet(@Param("itemSetId")Integer itemSetId,@Param("itemId")Integer itemId);
	
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
	 * 删除集合中商品
	 * @param itemSetId
	 * @param itemId 
		*	2015年12月12日
		*	mishengliang
	 */
	@DataSource("slave")
	void batchDeleteItemFromSet(@Param("itemSetId")Integer itemSetId,@Param("itemId")Integer itemId);

}
