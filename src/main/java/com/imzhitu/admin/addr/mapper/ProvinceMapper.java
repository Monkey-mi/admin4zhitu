package com.imzhitu.admin.addr.mapper;

import java.util.List;

import com.imzhitu.admin.addr.pojo.Province;
import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

/**
 * 省份数据操作类
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public interface ProvinceMapper {
	
	/**
	 * 查询所有省份
	 * 
	 * @return
	 * @author zhangbo	2015年11月19日
	 */
	@DataSource("slave")
	List<Province> queryAllProvince();
}
