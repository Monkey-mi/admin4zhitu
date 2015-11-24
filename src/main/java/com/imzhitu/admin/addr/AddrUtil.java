package com.imzhitu.admin.addr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.addr.mapper.CityMapper;
import com.imzhitu.admin.addr.mapper.DistrictMapper;
import com.imzhitu.admin.addr.mapper.ProvinceMapper;
import com.imzhitu.admin.addr.pojo.City;
import com.imzhitu.admin.addr.pojo.District;
import com.imzhitu.admin.addr.pojo.Province;

/**
 * 地址工具类
 * @author zhangbo	2015年11月19日
 *
 */
public class AddrUtil {
	
	/**
	 * 省份常量
	 * @author zhangbo	2015年11月19日
	 */
	private static Map<Integer, String> provinceMap;
	
	/**
	 * 城市常量
	 * @author zhangbo	2015年11月19日
	 */
	private static Map<Integer, String> cityMap;
	
	/**
	 * 行政区常量
	 * @author zhangbo	2015年11月19日
	 */
	private static Map<Integer, String> districtMap;
	
	@Autowired
	private static ProvinceMapper pMapper;
	
	@Autowired
	private static CityMapper cMapper;
	
	@Autowired
	private static DistrictMapper dMapper;
	
	static {
		// 存储省份
		List<Province> provinceList = pMapper.queryAllProvince();
		for (Province province : provinceList) {
			provinceMap.put(province.getId(), province.getName());
		}
		
		// 存储城市
		List<City> cityList = cMapper.queryAllCity();
		for (City city : cityList) {
			cityMap.put(city.getId(), city.getName());
		}
		
		// 存储行政区
		List<District> districtList = dMapper.queryAllDistrict();
		for (District district : districtList) {
			districtMap.put(district.getId(), district.getName());
		}
	}
	
	/**
	 * 获取省份，根据主键id
	 * 
	 * @param provinceId	省份主键id
	 * @return provinceName	省份名称
	 * @author zhangbo	2015年11月19日
	 */
	public static String getProvince(Integer provinceId) {
		return provinceMap.get(provinceId);
	}
	
	/**
	 * 获取城市，根据主键id
	 * 
	 * @param cityId	城市主键id
	 * @return	cityName	城市名称
	 * @author zhangbo	2015年11月19日
	 */
	public static String getCity(Integer cityId) {
		return cityMap.get(cityId);
	}
	
	/**
	 * 获取行政区，根据主键id
	 * 
	 * @param districtId	行政区主键id
	 * @return	districtName	行政区名称
	 * @author zhangbo	2015年11月19日
	 */
	public static String getDistrict(Integer districtId) {
		return districtMap.get(districtId);
	}
	
}
