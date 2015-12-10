package com.imzhitu.admin.trade.item.service;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 商品和集合关系
 * @author mishengliang
 *
 */
public interface ItemAndSetRelationService {

	/**
	 * 批量向集合中添加商品
	 * @param itemSetId    集合id
	 * @param itemIds			商品ids
	 * @throws Exception 
		*	2015年12月10日
		*	mishengliang
	 */
	public void insertItemToSet(Integer itemSetId,String itemIds) throws Exception;

}
