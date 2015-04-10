package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
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
	
	@DataSource("slave")
	public List<UserReportDto> queryReport(UserReportDto report);
	
	@DataSource("slave")
	public long queryTotal(UserReportDto report);
	
	@DataSource("master")
	public void updateValidByIds(Integer[] ids);
	
}
