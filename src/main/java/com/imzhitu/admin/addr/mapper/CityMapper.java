package com.imzhitu.admin.addr.mapper;

import java.util.List;

import com.imzhitu.admin.addr.pojo.City;
import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

/**
 * 城市数据操作类
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public interface CityMapper {
	
	/**
	 * 查询所有城市
	 * 
	 * @return
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("slave")
	List<City> queryAllCity();

}
