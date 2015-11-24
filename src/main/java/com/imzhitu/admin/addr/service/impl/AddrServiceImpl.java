package com.imzhitu.admin.addr.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.addr.mapper.CityMapper;
import com.imzhitu.admin.addr.mapper.DistrictMapper;
import com.imzhitu.admin.addr.mapper.ProvinceMapper;
import com.imzhitu.admin.addr.pojo.City;
import com.imzhitu.admin.addr.pojo.District;
import com.imzhitu.admin.addr.pojo.Province;
import com.imzhitu.admin.addr.service.AddrService;

/**
 * 地理位置业务逻辑实现类
 *  
 * @author zhangbo	2015年11月20日
 *
 */
@Service
public class AddrServiceImpl implements AddrService {
	
	@Autowired
	private ProvinceMapper pMapper;
	
	@Autowired
	private CityMapper cMapper;
	
	@Autowired
	private DistrictMapper dMapper;

	@Override
	public List<Map<String,Serializable>> queryProvinces() {
		List<Map<String,Serializable>> rtnList = new ArrayList<Map<String,Serializable>>();
		for (Province p : pMapper.queryAllProvince()) {
			Map<String,Serializable> map = new HashMap<String, Serializable>();
			map.put("id", p.getId());
			map.put("name", p.getName());
			rtnList.add(map);
		}
		return rtnList;
	}

	@Override
	public List<Map<String,Serializable>> queryCities() {
		List<Map<String,Serializable>> rtnList = new ArrayList<Map<String,Serializable>>();
		for (City c : cMapper.queryAllCity()) {
			Map<String,Serializable> map = new HashMap<String, Serializable>();
			map.put("id", c.getId());
			map.put("name", c.getName());
			rtnList.add(map);
		}
		
		return rtnList;
	}

	@Override
	public List<Map<String,Serializable>> queryDistricts() {
		List<Map<String,Serializable>> rtnList = new ArrayList<Map<String,Serializable>>();
		for (District d : dMapper.queryAllDistrict()) {
			Map<String,Serializable> map = new HashMap<String, Serializable>();
			map.put("id", d.getId());
			map.put("name", d.getName());
			rtnList.add(map);
		}
		
		return rtnList;
	}
	
	/**
	 * 构造数据完后需删除
	 * 
	 * 通过区名获取区id
	 * @param districtName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getDistrictId(String districtName,Integer cityId){
		return dMapper.getDistrictId(districtName,cityId);
	}
	
	/**
	 * 构造数据完后需删除
	 * 
	 * 通过城市名模糊匹配出城市id
	 * @param cityName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getCityId(String cityName){
		return cMapper.getCityId(cityName);
	}
	
	/**
	 * 构造数据完后需删除
	 * 
	 * 通过省份名模糊匹配出省份id
	 * @param provinceName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getProvinceId(String provinceName){
		return pMapper.getProvinceId(provinceName);
	}
	
	/**
	 *  构造数据完后需删除
	 * 
	 * @param countryName
	 * @return 
		*	2015年11月24日
		*	mishengliang
	 */
	public Integer getCountryId(String countryName){
		return null;
	}
	
}
