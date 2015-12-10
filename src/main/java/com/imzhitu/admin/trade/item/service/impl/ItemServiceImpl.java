package com.imzhitu.admin.trade.item.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
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

	@Override
	public void buildItemList(Integer page, Integer rows, Map<String, Object> jsonMap) {
		Integer fristRow = (page-1) * rows;
		Integer limit = rows;
		List<Item> list = itemMapper.queryItemList(fristRow, limit);
		jsonMap.put(OptResult.ROWS, list);
		jsonMap.put(OptResult.TOTAL,3);		
	}

	@Override
	public void batchDelete(Integer[] ids) {
		for (Integer id : ids) {
			itemMapper.deleteById(id);
		}
	}

	@Override
	public void insertItem(String name, String imgPath, String imgThumb, String summary, String description, Integer worldId, BigDecimal price, BigDecimal sale, Integer sales, Integer stock, Integer trueItemId, Integer trueItemType,
			Integer categoryId, Integer brandId) {
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
		
		itemMapper.insert(item);
	}

	@Override
	public void updateItem(Integer id, String name, String imgPath, String imgThumb, String summary, String description, Integer worldId, BigDecimal price, BigDecimal sale, Integer sales, Integer stock, Integer trueItemId, Integer trueItemType,
			Integer categoryId, Integer brandId) {
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
		
		itemMapper.update(item);
	}

}
