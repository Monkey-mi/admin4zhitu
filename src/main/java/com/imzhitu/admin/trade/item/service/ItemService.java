package com.imzhitu.admin.trade.item.service;

import java.util.Map;

import com.imzhitu.admin.trade.item.pojo.Item;

/**
 * 商品业务操作类
 * 
 * @author zhangbo	2015年12月9日
 *
 */
public interface ItemService {

	/**
	 * 保存商品
	 * 
	 * @param item
	 */
	public Integer saveItem(Item item);
	
	/**
	 * 更新商品
	 * 
	 * @param item
	 */
	public void updateItem(Item item);
	
	/**
	 * 构建商品列表
	 * 
	 * @param item
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * 
	 * @throws Exception
	 */
	public void buildItemList(Item item, Integer page, Integer rows, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据id查询商品
	 * 
	 * @param id
	 * @return
	 */
	public Item queryItemById(Integer id);
	
	/**
	 * 批量删除商品
	 * 
	 * @param idsStr
	 */
	public void batchDeleteItem(String idsStr);
	
	/**
	 * 保存商品到集合
	 * 
	 * @param itemSetId
	 * @param itemId
	 */
	public void saveSetItem(Integer itemSetId, Integer itemId);
	
	/**
	 * 构建集合商品列表
	 * 
	 * @param item
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSetItem(Item item, Integer page, Integer rows,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 删除集合中的商品
	 * 
	 * @param itemSetId
	 * @param ids
	 * @throws Exception
	 */
	public void batchDeleteSetItem(Integer itemSetId, String itemIdsStr) throws Exception;
	
	/**
	 * 更新集合商品序号
	 * 
	 * @param itemSetId
	 * @param itemStrs
	 * @throws Exception
	 */
	public void updateSetItemSerial(Integer itemSetId, String[] itemStrs) throws Exception;

	
}
