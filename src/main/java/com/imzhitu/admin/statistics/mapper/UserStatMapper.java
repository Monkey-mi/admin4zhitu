package com.imzhitu.admin.statistics.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户数据统计数据访问接口
 * </p>
 * 
 * 创建时间: 2015-08-26
 * @author lynch
 *
 */
public interface UserStatMapper {

	/**
	 * 根据区间查询注册总数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param phoneCode
	 * @return
	 */
	public Long selectCountByInterval(@Param("beginDate")Date beginDate, 
			@Param("endDate")Date endDate,
			@Param("phoneCode")Integer phoneCode);
	
}
