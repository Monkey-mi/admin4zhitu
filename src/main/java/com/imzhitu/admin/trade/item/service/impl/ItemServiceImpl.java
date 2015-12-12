package com.imzhitu.admin.trade.item.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.imzhitu.admin.trade.item.dao.ItemCache;
import com.imzhitu.admin.trade.item.mapper.ItemAndSetRelationMapper;
import com.imzhitu.admin.trade.item.mapper.ItemMapper;
import com.imzhitu.admin.trade.item.pojo.Item;
import com.imzhitu.admin.trade.item.service.ItemService;

/**
 * 商品业务实现类
 * 
 * @author zhangbo	2015年12月9日
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemCache itemCache;
	
	@Autowired
	private ItemAndSetRelationMapper itemAndSetRelationMapper;
	
	@SuppressWarnings("null")
	private List<Item> transFormateByItemDTO(List<com.hts.web.trade.item.dto.ItemDTO> listFromWeb){
		List<Item> list = null;
		if (listFromWeb != null) {
			for(com.hts.web.trade.item.dto.ItemDTO dto : listFromWeb){
				Item item = null;
				item.setId(dto.getId());
				item.setImgPath(dto.getImgPath());
				item.setImgThumb(dto.getImgThumb());
				item.setName(dto.getName());
				item.setSummary(dto.getSummary());
				
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * 查询总的商品，但传入集合ID时，需要查找非此集合下的商品，用与商品捡如集合时使用
	 */
	@Override
	public void buildItemList(String name,Integer itemSetId,Integer page, Integer rows, Map<String, Object> jsonMap) {
		Integer fristRow = (page-1) * rows;
		Integer limit = rows;
		
		Item item = new Item();
		item.setName(name);
		item.setFirstRow(fristRow);
		item.setLimit(limit);
		item.setItemSetId(itemSetId);
		
		List<Item> list = null;
		
		 list = itemMapper.queryItemList(item);
		jsonMap.put(OptResult.ROWS, list);
		jsonMap.put(OptResult.TOTAL,3);		
	}
	
	/**
	 * 查询集合下的商品
	 */
	@Override
	public void buildItemListForSetItem(Integer itemSetId,Integer page, Integer rows, Map<String, Object> jsonMap) {
		Integer fristRow = (page-1) * rows;
		Integer limit = rows;
		
		Item item = new Item();
		item.setFirstRow(fristRow);
		item.setLimit(limit);
		
		List<Item> list = null;
		
		list = itemMapper.queryItemListBySetId(itemSetId);
		jsonMap.put(OptResult.ROWS, list);
		jsonMap.put(OptResult.TOTAL,3);		
	}
	
	
	/**
	 * mishengliang 2015-12-11
	 * 通过集合ID查询商品
	 */
	@Override
	public void buildItemListBySetId(Integer itemSetId,Integer page, Integer rows, Map<String, Object> jsonMap) {
		int start = (page - 1)*rows;
		int limit = rows;
		List<com.hts.web.trade.item.dto.ItemDTO> listFromWeb = itemCache.queryItemListBySetId(itemSetId, new RowSelection(start, limit));
		List<Item> list = transFormateByItemDTO(listFromWeb);
		jsonMap.put(OptResult.ROWS, list);
		jsonMap.put(OptResult.TOTAL,5);		
	}

	@Override
	public void batchDelete(Integer[] ids) {
		for (Integer id : ids) {
			itemMapper.deleteById(id);
		}
	}

	@Override
	public void insertItem(String name, String imgPath, String imgThumb, String summary, String description, Integer worldId, BigDecimal price, BigDecimal sale, Integer sales, Integer stock, Integer trueItemId, Integer trueItemType,
			Integer categoryId, Integer brandId,Integer like) {
		Item item = new Item();
		item.setName(name);
		item.setImgPath(imgPath);
		item.setImgThumb(imgThumb);
		item.setSummary(summary);
		item.setDescription(description);
		item.setWorldId(worldId);
		item.setPrice(price);
		item.setSale(sale);
		item.setSales(sales);
		item.setStock(stock);
		item.setItemId(trueItemId);
		item.setItemType(trueItemType);
		item.setCategoryId(categoryId);
		item.setBrandId(brandId);
		item.setLike(like);
		
		itemMapper.insert(item);
	}

	@Override
	public void updateItem(Integer id, String name, String imgPath, String imgThumb, String summary, String description, Integer worldId, BigDecimal price, BigDecimal sale, Integer sales, Integer stock, Integer trueItemId, Integer trueItemType,
			Integer categoryId, Integer brandId,Integer like) {
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		item.setImgPath(imgPath);
		item.setImgThumb(imgThumb);
		item.setSummary(summary);
		item.setDescription(description);
		item.setWorldId(worldId);
		item.setPrice(price);
		item.setSale(sale);
		item.setSales(sales);
		item.setStock(stock);
		item.setItemId(trueItemId);
		item.setItemType(trueItemType);
		item.setCategoryId(categoryId);
		item.setBrandId(brandId);
		item.setLike(like);
		
		itemMapper.update(item);
	}
	
	/**
	 * 删除集合中的商品
	 * @param itemSetId
	 * @param ids 
		*	2015年12月12日
		*	mishengliang
	 */
	@Override
	public void batchDeleteItemFromSet(Integer itemSetId,Integer[] ids){
		try {
			for(Integer itemId : ids){
				itemAndSetRelationMapper.batchDeleteItemFromSet(itemSetId,itemId);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
