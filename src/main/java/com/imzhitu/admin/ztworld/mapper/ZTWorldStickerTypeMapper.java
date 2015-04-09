package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldStickerType;

/**
 * <p>
 * 查询分类数据映射接口
 * </p>
 * 
 * 创建：2014-05-01
 * @author lynch
 *
 */
public interface ZTWorldStickerTypeMapper {

	/**
	 * 查询缓存分类
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<HTWorldStickerTypeDto> queryCacheType();
	
	/**
	 * 查询缓存推荐分类
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<HTWorldStickerTypeDto> queryCacheRecommendType();
	
	/**
	 * 根据ids查询缓存推荐分类
	 * 
	 * @param ids
	 * @return
	 */
	@DataSource("slave")
	public List<HTWorldStickerTypeDto> queryCacheRecommendTypeByIds(Integer[] ids);
	
	/**
	 * 查询分类列表
	 * 
	 * @param type
	 * @return
	 */
	@DataSource("slave")
	public List<ZTWorldStickerType> queryTypes(ZTWorldStickerType type);
	
	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	@DataSource("slave")
	public List<ZTWorldStickerType> queryAllType(ZTWorldStickerType type);
	
	/**
	 * 根据id查询分类
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public ZTWorldStickerType queryById(Integer id);
	
	/**
	 * 查询分类总数
	 * 
	 * @param type
	 * @return
	 */
	@DataSource("slave")
	public long queryTotal(ZTWorldStickerType type);
	
	/**
	 * 保存分类
	 * 
	 * @param type
	 */
	public void save(ZTWorldStickerType type);

	/**
	 * 更新分类
	 * 
	 * @param type
	 */
	public void update(ZTWorldStickerType type);
	
	/**
	 * 根据ids删除
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 更新有效性
	 * 
	 * @param ids
	 * @param valid
	 */
	public void updateValidByIds(@Param("ids")Integer[] ids, @Param("valid")Integer valid);
	
	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateSerialById(@Param("id")Integer id, @Param("serial")Integer serial);
	
	/**
	 * 查询最大序号
	 * 
	 */
	@DataSource("slave")
	public Integer queryMaxSerial();

}
