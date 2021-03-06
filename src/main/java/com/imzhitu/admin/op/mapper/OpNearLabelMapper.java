package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpNearLabelDto;

/**
 * 附近织图持久化 接口
 * @author zxx 2015-12-4 09:48:06
 *
 */
public interface OpNearLabelMapper {
	/**
	 * 插入
	 * @param dto
	 * @author zxx 2015-12-4 09:48:06
	 */
	@DataSource("master")
	void insertNearLabel(OpNearLabelDto dto);
	/**
	 * 更新
	 * @param dto
	 * @author zxx 2015-12-4 09:48:06
	 */
	@DataSource("master")
	void updateNearLabel(OpNearLabelDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 * @author zxx 2015-12-4 09:48:06
	 */
	@DataSource("master")
	void batchDeleteNearLabel(Integer[] ids);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 * @author zxx 2015-12-4 09:48:06
	 */
	@DataSource("slave")
	List<OpNearLabelDto> queryNearLabel(OpNearLabelDto dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 * @author zxx 2015-12-4 09:48:06
	 */
	@DataSource("slave")
	long queryNearLabelTotalCount(OpNearLabelDto dto);
	
	/**
	 * 查询最大的序列号
	 * @param cityId
	 * @return
	 * @author zxx 2015-12-4 09:48:06
	 */
	@DataSource("slave")
	public Integer selectMaxSerialByCityId(Integer cityId);

	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 * 
	 * @author lynch 2015-12-05
	 */
	@DataSource("master")
	public void updateSerial(@Param("id")Integer id, @Param("serial")Integer serial);
	
	/**
	 * 根据id查询标签
	 * 
	 * @param id
	 * @return
	 * 
	 * @author lynch 2015-12-05
	 */
	@DataSource("slave")
	public OpNearLabelDto queryNearLabelById(@Param("id")Integer id);
	
}
