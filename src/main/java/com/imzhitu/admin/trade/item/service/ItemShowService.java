package com.imzhitu.admin.trade.item.service;

import java.util.Map;

/**
 * 商品秀操作
 * @author mishengliang
 *
 */
public interface ItemShowService {

	/**
	 *  查询商品秀列表
	 * @param itemSetId 集合ID
	 * @param worldId  织图ID
		*	2015年12月19日
		*	mishengliang
	 */
	public void bulidItemShowList(Integer itemSetId,Integer worldId,Map<String, Object> jsonMap);
	
	/**
	 * 添加商品秀记录
	 * @param itemSetId
	 * @param worldId
	 * @param jsonMap 
		*	2015年12月19日
		*	mishengliang
	 */
	public void addItemShowList(Integer itemSetId,Integer worldId,Map<String, Object> jsonMap);
	
	/**
	 * 批量删除
	 * @param serials 
		*	2015年12月19日
		*	mishengliang
	 */
	public void batchDelete(String ids);
	
	/**
	 * 更新集合买家秀序号
	 * 
	 * @param itemSetId
	 * @param itemStrs
	 * @throws Exception
	 */
	public void updateSetItemSerial(Integer itemSetId, String[] ids) throws Exception;
	
}
