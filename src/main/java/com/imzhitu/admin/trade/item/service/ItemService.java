package com.imzhitu.admin.trade.item.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 商品业务操作类
 * 
 * @author zhangbo	2015年12月9日
 *
 */
public interface ItemService {

	/**
	 * 构造商品列表
	 * 
	 * @param page		分页查询页数
	 * @param rows		分页查询每页数量
	 * @param jsonMap	返回值json
	 * @author zhangbo	2015年12月9日
	 */
	void buildItemList(String name,Integer itemSetId,Integer page, Integer rows, Map<String, Object> jsonMap);
	
	/**
	 * 查询集合下的商品
	 * @param itemSetId
	 * @param page
	 * @param rows
	 * @param jsonMap 
		*	2015年12月12日
		*	mishengliang
	 */
	void buildItemListForSetItem(Integer itemSetId,Integer page, Integer rows, Map<String, Object> jsonMap);
	
	/**
	 * 通过集合ID查询商品列表
	 * @param itemSetId
	 * @param page
	 * @param rows
	 * @param jsonMap 
		*	2015年12月11日
		*	mishengliang
	 */
	void buildItemListBySetId(Integer itemSetId,Integer page, Integer rows, Map<String, Object> jsonMap);
	
	/**
	 * @param name
	 * @param imgPath
	 * @param imgThumb
	 * @param summary
	 * @param description
	 * @param worldId
	 * @param price
	 * @param sale
	 * @param sales
	 * @param stock
	 * @param trueItemId
	 * @param trueItemType
	 * @param categoryId
	 * @param brandId
	 * @author zhangbo	2015年12月9日
	 */
	void insertItem(String name, String imgPath, String imgThumb, String summary, String description, Integer worldId, BigDecimal price, BigDecimal sale, Integer sales, Integer stock, Integer trueItemId, Integer trueItemType, Integer categoryId,
			Integer brandId,Integer like);

	/**
	 * @param id
	 * @param name
	 * @param imgPath
	 * @param imgThumb
	 * @param summary
	 * @param description
	 * @param worldId
	 * @param price
	 * @param sale
	 * @param sales
	 * @param stock
	 * @param trueItemId
	 * @param trueItemType
	 * @param categoryId
	 * @param brandId
	 * @author zhangbo	2015年12月9日
	 */
	void updateItem(Integer id, String name, String imgPath, String imgThumb, String summary, String description, Integer worldId, BigDecimal price, BigDecimal sale, Integer sales, Integer stock, Integer trueItemId, Integer trueItemType,
			Integer categoryId, Integer brandId,Integer like);

	/**
	 * 批量删除商品
	 * 
	 * @param ids	商品id集合，以逗号分隔
	 * @author zhangbo	2015年12月9日
	 */
	void batchDelete(Integer[] ids);

	/**
	 * 删除集合中的商品
	 * @param itemSetId
	 * @param ids
	 * @throws Exception 
		*	2015年12月12日
		*	mishengliang
	 */
	void batchDeleteItemFromSet(Integer itemSetId,Integer[] ids) throws Exception;
	
}