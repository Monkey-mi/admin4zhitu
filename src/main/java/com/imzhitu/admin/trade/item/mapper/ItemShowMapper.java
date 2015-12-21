package com.imzhitu.admin.trade.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.trade.item.pojo.ItemShow;

/**
 * 商品秀展示操作
 * @author mishengliang
 *
 */
public interface ItemShowMapper {

	/**
	 * 查询商品秀
	 * @param itemShow
	 * @return 
		*	2015年12月19日
		*	mishengliang
	 */
	@DataSource("slave")
	public List<ItemShow> queryItemShow(ItemShow itemShow);
	
	/**
	 * 插入买家秀记录
	 * @param itemShow 
		*	2015年12月19日
		*	mishengliang
	 */
	@DataSource("master")
	public void insertItemShow(ItemShow itemShow);
	
	/**
	 * 删除一条买家秀记录
	 * @param serial 
		*	2015年12月19日
		*	mishengliang
	 */
	@DataSource("master")
	public void deleteItemShowById(@Param("id")Integer id);
	
	/**
	 * 买家秀排序
	 *  
		*	2015年12月21日
		*	mishengliang
	 */
	@DataSource("master")
	public void updateSerial(@Param("worldId")Integer worldId, @Param("itemSetId")Integer itemSetId, @Param("serial")Integer serial);
	
}
