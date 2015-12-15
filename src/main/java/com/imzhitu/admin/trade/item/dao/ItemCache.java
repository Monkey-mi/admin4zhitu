package com.imzhitu.admin.trade.item.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;

/**
 * 商品redis缓存操作类
 * 注：存储数据供客户端使用，为web端商品对象
 * 
 * @author zhangbo	2015年12月10日
 *
 */
@Repository("com.imzhitu.admin.trade.item.dao.ItemCache")
public class ItemCache extends BaseCacheDaoImpl<com.hts.web.trade.item.dto.ItemDTO>{
	
	/**
	 * 更新商品集合其下的商品列表
	 * 
	 * @param ItemSetId	商品集合id
	 * @param itemList	将刷入redis缓存的商品list
	 * @author zhangbo	2015年12月15日
	 */
	public void updateItemListBySetId(Integer ItemSetId, List<com.hts.web.trade.item.dto.ItemDTO> itemList) {
		// 若存在redis的key值，则清空缓存
		if(getRedisTemplate().hasKey(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId)) {
			getRedisTemplate().delete(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId);
		}
		if(itemList.size() > 0) {
			com.hts.web.trade.item.dto.ItemDTO[] list = new com.hts.web.trade.item.dto.ItemDTO[itemList.size()];
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId, itemList.toArray(list));
		}
	}

	/**
	 * 更新商品位置，在对应的商品集合id下的list中的位置，进行记录
	 * 
	 * @param itemSetId	商品集合id
	 * @param itemList	商品list
	 * @author zhangbo	2015年12月15日
	 */
	public void updateItemPosition(Integer itemSetId, List<com.hts.web.trade.item.dto.ItemDTO> itemList) {
		for (com.hts.web.trade.item.dto.ItemDTO item : itemList) {
			// 查询结果为Map，key为ItemSetId，value为index，即在List当中的位置
			getRedisTemplate().opsForHash().put(CacheKeies.ITEM_POSITION_IN_SET + item.getId(), itemSetId, itemList.indexOf(item));
		}
	}
	
	/**
	 * 清空商品位置redis缓存
	 * 
	 * @param itemIds	商品id集合
	 * @author zhangbo	2015年12月15日
	 */
	public void emptyItemPosition (Integer[] itemIds) {
		for (Integer id : itemIds) {
			// 若存在redis的key值，则清空缓存
			if(getRedisTemplate().hasKey(CacheKeies.ITEM_POSITION_IN_SET + id)) {
				getRedisTemplate().delete(CacheKeies.ITEM_POSITION_IN_SET + id);
			}
		}
	}

}
