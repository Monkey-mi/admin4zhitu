package com.imzhitu.admin.trade.item.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.trade.item.dto.ItemDTO;
import com.imzhitu.admin.trade.item.dao.ItemCacheDao;

/**
 * @author zhangbo	2015年12月21日
 *
 */
@Repository("com.imzhitu.admin.trade.item.dao.ItemCache")
public class ItemCacheDaoImpl extends BaseCacheDaoImpl<com.hts.web.trade.item.dto.ItemDTO> implements ItemCacheDao {

	@Override
	public void updateItemListBySetId(Integer ItemSetId, List<ItemDTO> itemList) {
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

	@Override
	public void updateItemPosition(Integer itemSetId, List<ItemDTO> itemList) {
		for (com.hts.web.trade.item.dto.ItemDTO item : itemList) {
			// 查询结果为Map，key为ItemSetId，value为index，即在List当中的位置
			getRedisTemplate().opsForHash().put(CacheKeies.ITEM_POSITION_IN_SET + item.getId(), itemSetId, itemList.indexOf(item));
		}
	}

	@Override
	public void emptyItemPosition(Integer[] itemIds) {
		for (Integer id : itemIds) {
			// 若存在redis的key值，则清空缓存
			if(getRedisTemplate().hasKey(CacheKeies.ITEM_POSITION_IN_SET + id)) {
				getRedisTemplate().delete(CacheKeies.ITEM_POSITION_IN_SET + id);
			}
		}
	}

}
