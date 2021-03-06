package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hts.web.common.pojo.HTWorldStickerDto;
import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldSticker;

public interface ZTWorldStickerMapper {

	/**
	 * 查询所有贴纸
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<HTWorldStickerDto> queryAllSticker();
	
	/**
	 * 查询置顶贴纸
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<HTWorldStickerDto> queryCacheTopSticker(@Param("setId")Integer setId);
	
	
	/**
	 * 查询推荐贴纸
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<HTWorldStickerDto> queryCacheRecommendStickerDto(@Param("limit")Integer limit);
	
	/**
	 * 查询贴纸列表
	 * 
	 * @param sticker
	 * @return
	 */
	@DataSource("slave")
	public List<ZTWorldSticker> queryStickers(ZTWorldSticker sticker);
	
	/**
	 * 查询贴纸总数
	 * 
	 * @param sticker
	 * @return
	 */
	@DataSource("slave")
	public long queryTotal(ZTWorldSticker sticker);
	
	/**
	 * 更新贴纸
	 * 
	 * @param sticker
	 */
	@DataSource("master")
	public void update(ZTWorldSticker sticker);
	
	/**
	 * 保存贴纸
	 * 
	 * @param sticker
	 */
	@DataSource("master")
	public void save(ZTWorldSticker sticker);
	
	/**
	 * 根据ids删除贴纸
	 * 
	 * @param ids
	 */
	@DataSource("master")
	public void deleteByIds(Integer[] ids);

	/**
	 * 根据ids更新有效性
	 * 
	 * @param ids
	 * @param valid
	 */
	@DataSource("master")
	public void updateValidByIds(@Param("ids")Integer[] ids, @Param("valid")Integer valid);
	
	/**
	 * 根据id更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	@DataSource("master")
	public void updateSerialById(@Param("id")Integer id, @Param("serial")Integer serial);
	
	/**
	 * 根据id查询贴纸
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public ZTWorldSticker queryById(Integer id);
	
	/**
	 * 查询缓存热门贴纸
	 * 
	 * @param startTime
	 * @param endTime
	 * @param limit
	 */
	@DataSource("slave")
	public List<HTWorldStickerDto> queryCacheHotStickerDto(@Param("startTime")Long startTime,
			@Param("endTime")Long endTime, @Param("limit")Integer limit);
	
}
