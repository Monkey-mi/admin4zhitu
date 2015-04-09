package com.imzhitu.admin.common.dataSourceMasterSlave;

/**
 * 动态数据源holder
 * @author zxx
 *
 */
public class DynamicDataSourceHolder {
	public static final ThreadLocal<String> holder = new ThreadLocal<String>();
	
	public static void putDataSource(String name) {
		holder.set(name);
	}
	
	public static String getDataSource() {
		return holder.get();
	}
}
