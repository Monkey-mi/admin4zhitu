package com.imzhitu.admin.addr.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	 * @author lynch 2015-12-05
	 */
	public City queryCityById(Integer id);
	
	/**
	 * 根据id列表查询城市列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<City> queryCityByIds(Integer[] ids);
	
	/**
	 * 查询城市列表
	 * 
	 * @param city
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @author lynch 2015-12-05
	 */
	public void queryCity(City city, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;

	/**
	 * 查询城市行政区
	 * 
	 * @param cityId
	 * @param jsonMap
	 * @author zhangbo	2015年12月28日
	 */
	void queryCityDistict(Integer cityId, Map<String, Object> jsonMap);

	/**
	 * 批量城市列表
	 * 
	 * @param distictIds
	 * @author zhangbo	2015年12月28日
	 */
	void batchDeleteCityDistict(Integer[] distictIds);

	/**
	 * @param id
	 * @return
	 * @author zhangbo	2015年12月28日
	 */
	List<Map<String, Serializable>> queryDistrictsByCityId(Integer cityId);

	/**
	 * @param id
	 * @param distictIds
	 * @author zhangbo	2015年12月28日
	 */
	void addCityDistict(Integer cityId, Integer[] distictIds);
}


