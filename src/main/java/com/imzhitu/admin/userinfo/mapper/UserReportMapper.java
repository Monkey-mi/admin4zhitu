package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.UserReportDto;

/**
 * <p>
 * 用户举报数据映射
 * </p>
 * 
 * @author lynch
 *
 */
public interface UserReportMapper {
	
	public List<UserReportDto> queryReport(UserReportDto report);
	
	public long queryTotal(UserReportDto report);
	
	public void updateValidByIds(Integer[] ids);
	
}
