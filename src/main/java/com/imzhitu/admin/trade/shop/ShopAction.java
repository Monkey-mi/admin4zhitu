package com.imzhitu.admin.trade.shop;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.trade.shop.service.ShopService;

/**
 * 商家信息控制类
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public class ShopAction extends BaseCRUDAction {

	/**
	 * 序列号
	 * @author zhangbo	2015年11月19日
	 */
	private static final long serialVersionUID = -8634299057247974331L;
	
	/**
	 * 商家主键id
	 * @author zhangbo	2015年11月20日
	 */
	private Integer id;
	
	/**
	 * 商家名称
	 * @author zhangbo	2015年11月19日
	 */
	private String shopName;
	
	/**
	 * 描述
	 * @author zhangbo	2015年11月19日
	 */
	private String description;
	
	/**
	 * 国家id，对应所在国家
	 * @author zhangbo	2015年11月19日
	 */
	private Integer countryId;
	
	/**
	 * 省id，对应所在省份
	 * @author zhangbo	2015年11月19日
	 */
	private Integer provinceId;
	
	/**
	 * 市id，对应所在城市
	 * @author zhangbo	2015年11月19日
	 */
	private Integer cityId;
	
	/**
	 * 区id，对应所在城市行政区
	 * @author zhangbo	2015年11月19日
	 */
	private Integer districtId;
	
	/**
	 * 详细地址
	 * @author zhangbo	2015年11月19日
	 */
	private String address;
	
	/**
	 * 邮箱
	 * @author zhangbo	2015年11月19日
	 */
	private String email;
	
	/**
	 * 邮编
	 * @author zhangbo	2015年11月19日
	 */
	private String zipcode;
	
	/**
	 * 网站
	 * @author zhangbo	2015年11月19日
	 */
	private String website;
	
	/**
	 * 手机
	 * @author zhangbo	2015年11月19日
	 */
	private String phone;
	
	/**
	 * 电话号码
	 * @author zhangbo	2015年11月19日
	 */
	private String telephone;
	
	/**
	 * QQ号码
	 * @author zhangbo	2015年11月19日
	 */
	private Integer qq;
	
	/**
	 * 商家类型，存储的为类型id，以逗号分隔
	 * @author zhangbo	2015年11月19日
	 */
	private String typeIds;
	
	/**
	 * 商家id集合，以“,”逗号分隔
	 * @author zhangbo	2015年11月19日
	 */
	private String shopids;
	
	@Autowired
	private ShopService shopService;

	/**
	 * 构建商家信息列表，用于前台展示
	 * @return
	 */
	public String buildShopList() {
		try {

			shopService.buildShopList(cityId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加商家
	 * 
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	public String addShop() {
		try {

			shopService.addShop(shopName,description,countryId,provinceId,cityId,districtId,address,email,zipcode,website,phone,telephone,qq,typeIds);
			JSONUtil.optSuccess("添加成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除商家
	 * 
	 * @param shopids	商家主键id集合
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	public String batchDelete() {
		try {
			Integer[] sids = StringUtil.convertStringToIds(shopids);
			for (Integer id : sids) {
				shopService.deleteShop(id);
			}
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setQq(Integer qq) {
		this.qq = qq;
	}

	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}

	public void setShopids(String shopids) {
		this.shopids = shopids;
	}

}
