package com.imzhitu.admin.trade.shop.service;

import java.util.Map;

/**
 * 商家业务逻辑接口
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public interface ShopService {
	
	/**
	 * 新增商家
	 * 
	 * @param shopName		商家名称
	 * @param description	描述
	 * @param countryId		国家id
	 * @param provinceId	省id
	 * @param cityId		城市id
	 * @param districtId	行政区id
	 * @param address		详细地址
	 * @param email			E-mail
	 * @param zipcode		邮编
	 * @param website		网址
	 * @param phone			手机
	 * @param telephone		电话（固话）
	 * @param qq			QQ
	 * @param typeIds		商家类型id集合
	 * @author zhangbo	2015年11月20日
	 */
	void addShop(String shopName, String description, Integer countryId, Integer provinceId, Integer cityId, Integer districtId, String address, String email, String zipcode, String website, String phone, String telephone, Integer qq, String typeIds);
	
	/**
	 * 构建商家信息列表，用于前台展示
	 * 
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 * @author zhangbo	2015年11月19日
	 */
	void buildShopList(int page, int rows, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 删除商家，通过主键id
	 * 
	 * @param id	商家主键id
	 * @author zhangbo	2015年11月19日
	 */
	void deleteShop(Integer id);

}
