package com.imzhitu.admin.common.dataSourceMasterSlave;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源，动态获取数据源的实现
 * @author zxx
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSource();
	}

}
