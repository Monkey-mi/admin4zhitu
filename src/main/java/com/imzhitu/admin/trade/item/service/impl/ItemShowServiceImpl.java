package com.imzhitu.admin.trade.item.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.trade.item.mapper.ItemShowMapper;
import com.imzhitu.admin.trade.item.pojo.ItemShow;
import com.imzhitu.admin.trade.item.service.ItemShowService;

@Service
public class ItemShowServiceImpl extends BaseServiceImpl implements ItemShowService {
	
	@Autowired
	private ItemShowMapper mapper;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;

	@Override
	public void bulidItemShowList(Integer itemSetId, Integer worldId,Map<String, Object> jsonMap) {
		ItemShow itemShow = new ItemShow();
		if (itemSetId == null && worldId == null) {
			itemShow = null;
		} else {
			itemShow.setItemSetId(itemSetId);
			itemShow.setWorldId(worldId);
		}
		
		List<ItemShow> list = mapper.queryItemShow(itemShow);
		jsonMap.put(OptResult.JSON_KEY_ROWS,list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL,list.size());
	}

	@Override
	public void addItemShowList(Integer itemSetId, Integer worldId,Map<String, Object> jsonMap){
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SHOW);
		ItemShow itemShow = new ItemShow();
		
		itemShow.setItemSetId(itemSetId);
		itemShow.setWorldId(worldId);
		itemShow.setSerial(serial);
		
		mapper.insertItemShow(itemShow);
	}
	
	@Override
	public void batchDelete(String ids){
		Integer[] idsNums = StringUtil.convertStringToIds(ids);
		// 删除所有与之对应的关联
		for(int i = 0; i < idsNums.length; i++) {
			mapper.deleteItemShowById(idsNums[i]);
		}
	}
	
	@Override
	public void updateSetItemSerial(Integer itemSetId, String[] worldIdsStr) throws Exception {
		
		for (int i = worldIdsStr.length - 1; i >= 0; i--) {
			if (StringUtil.checkIsNULL(worldIdsStr[i]))
				continue;
			int worldId = Integer.parseInt(worldIdsStr[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SHOW);
			
			mapper.updateSerial(worldId, itemSetId, serial);
			
		}
		
	}
	
}
