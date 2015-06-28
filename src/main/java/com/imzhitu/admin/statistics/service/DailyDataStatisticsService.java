/**
 * 
 */
package com.imzhitu.admin.statistics.service;

import java.util.Map;

import com.imzhitu.admin.common.pojo.OpDataStatisticsDto;

/**
 * 每日频道数据统计接口
 *
 * @author zhangbo 2015年6月19日
 */
public interface DailyDataStatisticsService {

	/**
	 * 查询每日频道相关数据统计
	 * 
	 * @author zhangbo 2015年6月25日
	 * @param jsonMap 	返回前台结果集
	 * @param rows 		page对应分页查询start
	 * @param page 		对应分页查询limit
	 * @param sort 		排序字段
	 * @param order 	正序/倒序
	 */
	public void queryDailyData(OpDataStatisticsDto dto, Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception;

	/**
	 *
	 * @author zhangbo 2015年6月25日
	 */
	void doDataStatisticsSchedulaJob();

}
