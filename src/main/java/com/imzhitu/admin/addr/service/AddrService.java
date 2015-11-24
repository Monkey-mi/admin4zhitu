package com.imzhitu.admin.addr.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 地理位置接口类
 * 
 * @author zhangbo	2015年11月20日
 *
 */
public interface AddrService {

	/**
	 * 查询省信息
	 * @author zhangbo	2015年11月20日
	 */
	List<Map<String,Serializable>> queryProvinces();
	
	/**
	 * 查询城市信息
	 * @author zhangbo	2015年11月20日
	 */
	List<Map<String,Serializable>> queryCities();
	
	/**
	 * 查询行政区信息
	 * @author zhangbo	2015年11月20日
	 */
	List<Map<String,Serializable>> queryDistricts();

	/**
	 * 构造数据完后需删除
	 * 
	 * 通过区名获取区id
	 * @param districtName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getDistrictId(String districtName);
	
	/**
	 * 构造数据完后需删除
	 * 
	 * 通过城市名模糊匹配出城市id
	 * @param cityName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getCityId(String cityName);
	
	/**
	 * 构造数据完后需删除
	 * 
	 * 通过城市名模糊匹配出城市id
	 * @param provinceName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getProvinceId(String provinceName);
	
	/**
	 *  构造数据完后需删除
	 * 
	 * @param countryName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getCountryId(String countryName);
}


