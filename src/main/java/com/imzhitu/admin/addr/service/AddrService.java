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

}
