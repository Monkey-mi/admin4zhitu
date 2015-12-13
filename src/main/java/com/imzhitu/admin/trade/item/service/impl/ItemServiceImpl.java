package com.imzhitu.admin.trade.item.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.trade.item.mapper.ItemSetRelationMapper;
import com.imzhitu.admin.trade.item.mapper.ItemMapper;
import com.imzhitu.admin.trade.item.pojo.Item;
import com.imzhitu.admin.trade.item.service.ItemService;


@Service
public class ItemServiceImpl extends BaseServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemSetRelationMapper itemSetRelationMapper;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Override
	public Integer saveItem(Item item) {
		if(item.getName() == null) {
			item.setName(item.getSummary());
		}
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.ITEM_ID);
		item.setId(id);
		itemMapper.insert(item);
		return id;
	}

	@Override
	public void updateItem(Item item) {
		itemMapper.update(item);
	}
	
	@Override
	public void buildItemList(Item item,Integer page, Integer rows,
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(item, page, rows, jsonMap, new NumberDtoListAdapter<Item>() {

			@Override
			public List<? extends Serializable> queryList(Item dto) {
				return itemMapper.queryItemList(dto);
			}

			@Override
			public long queryTotal(Item dto) {
				return itemMapper.queryItemTotal(dto);
			}
		});
		
	}
	
	@Override
	public Item queryItemById(Integer id) {
		return itemMapper.queryItemById(id);
	}
	
	@Override
	public void batchDeleteItem(String idsStr) {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0) {
			
			// 删除所有与之对应的关联
			for(int i = 0; i < ids.length; i++) {
				Integer id = ids[i];
				List<Integer> setIds = itemSetRelationMapper.querySetId(id);
				for(Integer setId : setIds) {
					itemSetRelationMapper.deleteByItemId(setId, id);
				}
			}
			
			itemMapper.deleteByIds(ids);
		}
	}
	
	@Override
	public void buildSetItem(Item item, Integer page, Integer rows, 
			Map<String, Object> jsonMap) throws Exception{
		buildNumberDtos(item, page, rows, jsonMap, new NumberDtoListAdapter<Item>() {

			@Override
			public List<? extends Serializable> queryList(Item dto) {
				return itemSetRelationMapper.querSetItem(dto);
			}

			@Override
			public long queryTotal(Item dto) {
				return itemSetRelationMapper.querySetItemTotal(dto);
			}
		});
	}
	
	@Override
	public void saveSetItem(Integer itemSetId, Integer itemId) {
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_RELATE_SERIAL);
		itemSetRelationMapper.saveItem(itemSetId, itemId, serial);
	}
	
	@Override
	public void batchDeleteSetItem(Integer itemSetId, String itemIdsStr){
		Integer[] itemIds = StringUtil.convertStringToIds(itemIdsStr);
		if(itemIds != null && itemIds.length > 0) {
			for(Integer itemId : itemIds) {
				itemSetRelationMapper.deleteByItemId(itemSetId, itemId);
			}
		}
	}

	@Override
	public void updateSetItemSerial(Integer itemSetId, String[] itemIdsStr) throws Exception {
		
		for (int i = itemIdsStr.length - 1; i >= 0; i--) {
			if (StringUtil.checkIsNULL(itemIdsStr[i]))
				continue;
			int itemId = Integer.parseInt(itemIdsStr[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_RELATE_SERIAL);
			
			itemSetRelationMapper.updateSerial(itemId, itemSetId, serial);
			
		}
		
	}

}
