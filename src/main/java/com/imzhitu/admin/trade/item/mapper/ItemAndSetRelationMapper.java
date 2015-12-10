package com.imzhitu.admin.trade.item.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author mishengliang
 *
 */
public interface ItemAndSetRelationMapper {
	
	/**
	 * 插入单条关系数据
	 * @param itemSetId
	 * @param itemId
	 * @throws Exception 
		*	2015年12月10日
		*	mishengliang
	 */
	void insertItemToSet(@Param("itemSetId")Integer itemSetId,@Param("itemId")Integer itemId) throws Exception;

}
