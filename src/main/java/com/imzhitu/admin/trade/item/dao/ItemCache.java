package com.imzhitu.admin.trade.item.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;

/**
 * 商品redis缓存操作类
 * 注：存储数据供客户端使用，为web端商品对象
 * 
 * @author zhangbo	2015年12月10日
 *
 */
@Repository
public class ItemCache extends BaseCacheDaoImpl<com.hts.web.trade.item.dto.ItemDTO>{
	
	public void updateItemListBySetId(Integer ItemSetId, List<com.hts.web.trade.item.dto.ItemDTO> itemList) {
		// 若存在redis的key值，则清空缓存
		if(getRedisTemplate().hasKey(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId)) {
			getRedisTemplate().delete(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId);
		}
		if(itemList.size() > 0) {
			com.hts.web.trade.item.dto.ItemDTO[] list = new com.hts.web.trade.item.dto.ItemDTO[itemList.size()];
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ITEM_LIST_BY_SETID, itemList.toArray(list));
		}
		
	}
	
	/**
	 * 根据商品集合id，查询其下的商品列表
	 * 
	 * @param ItemSetId		商品集合id
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	public List<com.hts.web.trade.item.dto.ItemDTO> queryItemListBySetId(Integer ItemSetId, RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}
}
