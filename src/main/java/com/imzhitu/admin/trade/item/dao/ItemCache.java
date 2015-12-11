package com.imzhitu.admin.trade.item.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.hts.web.base.constant.CacheKeies;

/**
 * 商品redis缓存操作类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public class ItemCache {
	
	/**
	 * redis操作对象，存储数据供客户端使用，为web端商品对象
	 * @author zhangbo	2015年12月8日
	 */
	@Autowired
	private RedisTemplate<String, com.hts.web.trade.item.dto.ItemDTO> redisTemplate;
	
	public void updateItemListBySetId(Integer ItemSetId, List<com.hts.web.trade.item.dto.ItemDTO> itemList) {
		// 若存在redis的key值，则清空缓存
		if(redisTemplate.hasKey(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId)) {
			redisTemplate.delete(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId);
		}
		if(itemList.size() > 0) {
			com.hts.web.trade.item.dto.ItemDTO[] list = new com.hts.web.trade.item.dto.ItemDTO[itemList.size()];
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			redisTemplate.opsForList().rightPushAll(CacheKeies.ITEM_LIST_BY_SETID, itemList.toArray(list));
		}
		
	}
}
