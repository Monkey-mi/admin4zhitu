package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpNearCityGroupDto;

/**
 * 附近城市分组 组管理 持久层
 * @author zxx 2015-12-4 16:41:27
 *
 */
public interface OpNearCityGroupMapper {
	/**
	 * 插入
	 * @param dto
	 * @author zxx 2015-12-4 16:41:27
	 */
	@DataSource("master")
	public void insertNearCityGroup(OpNearCityGroupDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 * @author zxx 2015-12-4 16:41:27
	 */
	@DataSource("master")
	public void batchDeleteNearCityGroup(Integer[] ids);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 * @author zxx 2015-12-4 16:41:27
	 */
	@DataSource("slave")
	public List<OpNearCityGroupDto> queryNearCityGroup(OpNearCityGroupDto dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 * @author zxx 2015-12-4 16:41:27
	 */
	@DataSource("master")
	public long queryNearCityGroupTotalCount(OpNearCityGroupDto dto);
	
	/**
	 * 附近最大序列号
	 * @return
	 * @author zxx 2015-12-4 16:41:27
	 */
	@DataSource("master")
	public Integer queryNearCityGroupMaxSerial();
}
