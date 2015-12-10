package com.imzhitu.admin.trade.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.trade.item.mapper.ItemAndSetRelationMapper;
import com.imzhitu.admin.trade.item.service.ItemAndSetRelationService;

/**
 * 商品和集合关系
 * @author mishengliang
 *
 */
@Service
public class ItemAndSetRelationServiceImpl implements ItemAndSetRelationService {

	@Autowired
	private ItemAndSetRelationMapper insertItemToSetMapper;
	
	/**
	 * 批量向集合中添加商品
	 * @param itemSetId    集合id
	 * @param itemIds			商品ids
	 * @throws Exception 
		*	2015年12月10日
		*	mishengliang
	 */
	@Override
	public void insertItemToSet(Integer itemSetId, String itemIds) throws Exception {
		String[] ids = itemIds.split(",");
		for(int i = 0; i < ids.length; i++){
			try {
				insertItemToSetMapper.insertItemToSet(itemSetId,Integer.parseInt(ids[i]));
			} catch (Exception e) {
				// TODO: handle exception
				continue;
			}
		}
	}
	

}
