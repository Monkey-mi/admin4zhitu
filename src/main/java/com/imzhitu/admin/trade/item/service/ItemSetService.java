package com.imzhitu.admin.trade.item.service;

import java.util.Map;

/**
 * 商品集合业务操作类
 * 
 * @author zhangbo	2015年12月8日
 *
 */
public interface ItemSetService {
	
	/**
	 * 根据公告id添加有奖活动banner
	 * 注：可能客户端过几个版本以后，就不会将活动banner放入商品集合展现整体中了
	 * 
	 * @param ids	公告id集合，以逗号分隔
	 * 
	 * @throws Exception
	 * @author zhangbo	2015年12月8日
	 */
	void addAwardActivityByBullentin(String ids) throws Exception;
	
	/**
	 * 构造商品集合展示列表
	 * 
	 * @param page		分页查询页数
	 * @param rows		分页查询每页数量
	 * @param cacheFlag	是否正在展示， 1为正在展示的限时秒杀，2为正在展示的推荐商品，null为查询全部数据
	 * @param jsonMap	返回值json
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	void buildItemSetList(Integer page, Integer rows, Integer cacheFlag, Map<String, Object> jsonMap);

	/**
	 * 新增商品集合
	 * 
	 * @param description	商品集合描述
	 * @param path			商品集合图片路径
	 * @param thumb			商品集合缩略图路径
	 * @param type			商品集合链接类型
	 * @param link			商品集合链接内容
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	void addItemSet(String description, String path, String thumb, Integer type, String link);
	
	/**
	 * 更新商品集合
	 * 
	 * @param id			商品集合主键id
	 * @param description	商品集合描述
	 * @param path			商品集合图片路径
	 * @param thumb			商品集合缩略图路径
	 * @param type			商品集合链接类型
	 * @param link			商品集合链接内容
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	void updateItemSet(Integer id, String description, String path, String thumb, Integer type, String link);
	
	/**
	 * 批量删除商家集合banner
	 * 
	 * @param idArray	商家集合id的集合
	 * @author zhangbo	2015年12月8日
	 */
	void batchDelete(Integer[] idArray);

}