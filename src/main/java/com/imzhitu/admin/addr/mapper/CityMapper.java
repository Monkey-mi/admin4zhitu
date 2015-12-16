package com.imzhitu.admin.addr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	@DataSource("slave")
	Integer getCityId(@Param("cityName")String cityName);
	
	/**
	 * 查询所有城市缓存
	 * 
	 * @return
	 * @author lynch 2015-12-03
	 */
	@DataSource("slave")
	public List<com.hts.web.common.pojo.AddrCity> queryAllCityCache();
	
	/**
	 * 根据id查询城市
	 * 
	 * @param 
	 * id
	 * @return
	 * @author lynch 2015-15-05
	 */
	@DataSource("slave")
	public City queryCityById(Integer id);
	
	/**
	 * 根据ids查询城市列表
	 * 
	 * @param ids
	 * @return
	 */
	@DataSource("slave")
	public List<City> queryCityByIds(@Param("ids")Integer[] ids);
	
	/**
	 * 查询城市列表
	 * 
	 * @param city
	 * @return
	 * @author lynch 2015-12-05
	 */
	public List<City> queryCity(City city);
	
	/**
	 * 查询城市总数
	 * 
	 * @param city
	 * @return
	 * @author lynch 2015-12-05
	 */
	public long queryCityCount(City city);
}
