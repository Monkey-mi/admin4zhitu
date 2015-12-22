package com.imzhitu.admin.trade.item.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.trade.item.mapper.ItemShowMapper;
import com.imzhitu.admin.trade.item.pojo.ItemShow;
import com.imzhitu.admin.trade.item.pojo.ItemShowDTO;
import com.imzhitu.admin.trade.item.service.ItemShowService;


@Service
public class ItemShowServiceImpl extends BaseServiceImpl implements ItemShowService {
	
	@Autowired
	private ItemShowMapper mapper;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private  com.imzhitu.admin.userinfo.service.UserInfoService userInfoService;
	
	@Autowired
	private com.imzhitu.admin.ztworld.service.ZTWorldService ztWorldService;
	
	@Autowired
	private com.imzhitu.admin.trade.item.service.ItemSetService itemSetService;
	
	/**
	 * 将返回数据丰富，转化格式
	 * @param list
	 * @return
	 * @throws Exception 
		*	2015年12月22日
		*	mishengliang
	 */
	private List<ItemShowDTO> transFormate(List<ItemShow> list){
		List<ItemShowDTO> dto = new ArrayList<ItemShowDTO>();
		if (list != null){
			for(ItemShow itemShow : list){
				ItemShowDTO itemShowDTO = new ItemShowDTO();
				ZTWorldDto zTWorldDto = ztWorldService.getZTWorldByWorldId(itemShow.getWorldId());
				UserInfo userInfo = null;
				
				try {
					userInfo = userInfoService.getUserInfo(zTWorldDto.getAuthorId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*itemSetService.buildItemSetListByIdOrName(itemSetId,null,null,jsonMap);*/
				itemShowDTO.setId(itemShow.getId());
				itemShowDTO.setItemSetId(itemShow.getItemSetId());
				itemShowDTO.setWorldId(itemShow.getWorldId());
				/*itemShowDTO.setTitleThumbPath(zTWorldDto.getTitleThumbPath());*/
				itemShowDTO.setSerial(itemShow.getSerial());
				itemShowDTO.setAuthorName(userInfo.getUserName());
				itemShowDTO.setAuthorAvatar(userInfo.getUserAvatar());
				itemShowDTO.setShortLink(zTWorldDto.getShortLink());
				
				dto.add(itemShowDTO);
			}
		}else{
			dto = null;
		}
		return dto;
	}

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
		List<ItemShowDTO> listDto= transFormate(list);
		
		jsonMap.put(OptResult.JSON_KEY_ROWS,listDto);
		jsonMap.put(OptResult.JSON_KEY_TOTAL,listDto.size());
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
