package com.imzhitu.admin.trade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.trade.mapper.ShopTypeMapper;
import com.imzhitu.admin.trade.pojo.ShopType;
import com.imzhitu.admin.trade.service.ShopTypeService;

/**
 * @author zhangbo	2015年11月20日
 *
 */
@Service
public class ShopTypeServiceImpl implements ShopTypeService {
	
	@Autowired
	private ShopTypeMapper shopTypeMapper;

	@Override
	public List<ShopType> queryShopType() {
		return shopTypeMapper.queryShopType();
	}

}
