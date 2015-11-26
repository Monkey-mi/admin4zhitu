package com.imzhitu.admin.trade.service;

import java.util.List;

import com.imzhitu.admin.trade.pojo.ShopType;

/**
 * @author zhangbo	2015年11月20日
 *
 */
public interface ShopTypeService {

	/**
	 * 查询商家类型列表
	 * 
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	List<ShopType> queryShopType();

}
