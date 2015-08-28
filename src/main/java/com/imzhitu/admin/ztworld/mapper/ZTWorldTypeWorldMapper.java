package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldDto;

public interface ZTWorldTypeWorldMapper {
	/**
	 * 添加
	 * @param dto
	 */
	@DataSource("master")
	public void saveTypeWorld(ZTWorldTypeWorldDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<ZTWorldTypeWorldDto> queryTypeWorld(ZTWorldTypeWorldDto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryTypeWorldTotalCount(ZTWorldTypeWorldDto dto);
	
	/**
	 * 根据worldIds批量删除
	 * @param worldIds
	 */
	@DataSource("master")
	public void batchDeleteTypeWorldByWorldIds(Integer[] worldIds);
	
	/**
	 * 根据ids批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteTypeWorldByIds(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateTypeWorld(ZTWorldTypeWorldDto dto);
	
	/**
	 * 根据worldIds批量更新有效性
	 * @param worldIds
	 * @param valid
	 */
	@DataSource("master")
	public void updateTypeWorldValidByWorldIds(@Param("worldIds")Integer[] worldIds,@Param("valid")Integer valid);
	
	/**
	 * 根据worldIds查询分类
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<ZTWorldTypeWorldDto> queryTypeWorldByWorldIds( Integer[] wids);
	
	/**
	 * 更新所有的已经推荐的分类织图有效性
	 * @param beforeValid
	 * @param afterValid
	 */
	@DataSource("master")
	public void updateAllRecommendTypeWorldValid(@Param("beforeValid")Integer beforeValid,@Param("afterValid")Integer afterValid);
	
	/**
	 * 根据userId查询分类总数
	 * @param userId
	 * @return
	 */
	@DataSource("slave")
	public long queryTypeWorldCountByUserId(@Param("userId")Integer userId);
}
