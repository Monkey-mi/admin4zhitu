package com.imzhitu.admin.addr.mapper;

import java.util.List;

import com.imzhitu.admin.addr.pojo.District;
import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

/**
 * 行政区数据操作类
 * @author zhangbo	2015年11月19日
 *
 */
public interface DistrictMapper {

	/**
	 * 查询所有行政区
	 * 
	 * @return
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("slave")
	List<District> queryAllDistrict();
}
