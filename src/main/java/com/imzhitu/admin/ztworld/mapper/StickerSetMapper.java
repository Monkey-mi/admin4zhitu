package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import com.hts.web.common.pojo.HTWorldStickerSet;
import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldStickerSet;

public interface StickerSetMapper {

	/**
	 * 查询缓存系列
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<HTWorldStickerSet> queryCacheSet();
	
	/**
	 * 查询系列列表
	 * 
	 * @param stickerSet
	 * @return
	 */
	@DataSource("slave")
	public List<ZTWorldStickerSet> querySets(ZTWorldStickerSet stickerSet);
	
	/**
	 * 根据id查询系列
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public ZTWorldStickerSet querySetById(Integer id);
	
	/**
	 * 查询系列总数
	 * 
	 * @param stickerSet
	 * @return
	 */
	@DataSource("slave")
	public long queryTotal(ZTWorldStickerSet stickerSet);
	
	/**
	 * 保存系列
	 * 
	 * @param stickerSet
	 */
	@DataSource("master")
	public void save(ZTWorldStickerSet stickerSet);
	
	/**
	 * 更新系列
	 * 
	 * @param stickerSet
	 */
	@DataSource("master")
	public void update(ZTWorldStickerSet stickerSet);
	
	/**
	 * 批量删除系列
	 * 
	 * @param ids
	 */
	@DataSource("master")
	public void deleteByIds(Integer[] ids);
	
}
