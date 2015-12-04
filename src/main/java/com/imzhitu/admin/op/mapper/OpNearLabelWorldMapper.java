package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpNearLabelWorldDto;

/**
 * 附近标签织图持久层
 * @author zxx 2015-12-4 18:03:18
 *
 */
public interface OpNearLabelWorldMapper {
	/**
	 * 添加
	 * @param dto
	 */
	@DataSource("master")
	public void insertNearLabelWorld(OpNearLabelWorldDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteNearLabelWorld(Integer[] ids);
	
	/**
	 * 修改serial
	 * @param dto
	 */
	@DataSource("master")
	public void updateNearLabelWorldSerial(OpNearLabelWorldDto dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpNearLabelWorldDto> queryNearLabelWorld(OpNearLabelWorldDto dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("master")
	public long queryNearLabelWorldTotalCount(OpNearLabelWorldDto dto);
	
	/**
	 * 查询最大serial
	 * @param neraLabelId
	 * @return
	 */
	@DataSource("master")
	public Integer queryNearLabelWorldMaxSerialByNearLabelId(Integer nearLabelId);
}
