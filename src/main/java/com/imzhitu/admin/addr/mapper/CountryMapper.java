package com.imzhitu.admin.addr.mapper;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

/**
 * @author zhangbo	2015年11月29日
 *
 */
public interface CountryMapper {
	
	/**
	 * TODO 临时构造数据使用
	 * 
	 * @param CountryName
	 * @return
	 * @author zhangbo	2015年11月29日
	 */
	@DataSource("slave")
	Integer getCountryId(@Param("CountryName")String CountryName);

}
