package com.imzhitu.admin.addr.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.AddrCity;
import com.imzhitu.admin.addr.pojo.City;

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
	 * @param cityId 所属城市的id
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getDistrictId(String districtName,Integer cityId);
	
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
	
	/**
	 * 更新城市缓存
	 * 
	 * @author lynch 2015-12-03
	 */
	public void updateCityCache();

	/**
	 * 根据id查询城市信息
	 * 
	 * @param id
	 * @return
	 */
	public City queryCityById(Integer id);
}


