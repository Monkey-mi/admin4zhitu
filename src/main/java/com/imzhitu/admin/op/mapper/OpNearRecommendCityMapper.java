package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpNearRecommendCityDto;

/**
 * 附近推荐城市 持久层
 * @author zxx 2015-12-4 17:24:05
 *
 */
public interface OpNearRecommendCityMapper {
	/**
	 * 插入附近推荐城市
	 * @param dto
	 * @author zxx 2015-12-4 17:24:05
	 */
	@DataSource("master")
	public void insertNearRecommendCity(OpNearRecommendCityDto dto);
	
	/**
	 * 
	 * @param ids
	 * @author zxx 2015-12-4 17:24:05
	 */
	@DataSource("master")
	public void batchDeleteNearRecommendCity(Integer[] ids);
	
	/**
	 * 
	 * @param dto
	 * @return
	 * @author zxx 2015-12-4 17:24:05
	 */
	@DataSource("slave")
	public List<OpNearRecommendCityDto> queryNearRecommendCity(OpNearRecommendCityDto dto);
	
	/**
	 * 
	 * @param dto
	 * @return
	 * @author zxx 2015-12-4 17:24:05
	 */
	@DataSource("master")
	public long queryNearRecommendCityTotalCount(OpNearRecommendCityDto dto);
	
	
	/**
	 * 更新附近推荐城市serial
	 * @param dto
	 * @author zxx 2015-12-8 11:26:09
	 */
	@DataSource("master")
	public void updateNearRecommendCitySerial(OpNearRecommendCityDto dto);
}
