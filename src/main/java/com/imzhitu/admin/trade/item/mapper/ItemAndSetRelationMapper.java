package com.imzhitu.admin.trade.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	void insertItemToSet(@Param("itemSetId")Integer itemSetId,@Param("itemId")Integer itemId);
	
	/**
	 * 根据商品集合id，查询其下的商品列表
	 * 
	 * @param itemSetId	商品集合id
	 * @return
	 * @author zhangbo	2015年12月10日
	 */
	List<Item> queryItemListBySetId(@Param("itemSetId")Integer itemSetId);

}
