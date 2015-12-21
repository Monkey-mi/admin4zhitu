package com.imzhitu.admin.trade.item.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 商品redis缓存操作类
 * 注：存储数据供客户端使用，为web端商品对象
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public interface ItemCacheDao extends BaseCacheDao {
	
	/**
	 * 更新商品集合其下的商品列表
	 * 
	 * @param ItemSetId	商品集合id
	 * @param itemList	将刷入redis缓存的商品list
	 * @author zhangbo	2015年12月15日
	 */
	public void updateItemListBySetId(Integer ItemSetId, List<com.hts.web.trade.item.dto.ItemDTO> itemList);

	/**
	 * 更新商品位置，在对应的商品集合id下的list中的位置，进行记录
	 * 
	 * @param itemSetId	商品集合id
	 * @param itemList	商品list
	 * @author zhangbo	2015年12月15日
	 */
	public void updateItemPosition(Integer itemSetId, List<com.hts.web.trade.item.dto.ItemDTO> itemList);
	
	/**
	 * 清空商品位置redis缓存
	 * 
	 * @param itemIds	商品id集合
	 * @author zhangbo	2015年12月15日
	 */
	public void emptyItemPosition (Integer[] itemIds);

}
