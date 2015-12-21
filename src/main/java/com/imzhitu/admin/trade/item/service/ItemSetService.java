package com.imzhitu.admin.trade.item.service;

import java.util.Date;
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
	void buildItemSetList(Integer page, Integer rows, Integer cacheFlag, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 新增商品集合
	 * 
	 * @param title			商品集合标题
	 * @param description	商品集合描述
	 * @param path			商品集合图片路径
	 * @param thumb			商品集合缩略图路径
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	void addItemSet(String title, String description, String path, String thumb) throws Exception;
	
	/**
	 * 更新商品集合
	 * 
	 * @param id			商品集合主键id
	 * @param title			商品集合标题
	 * @param description	商品集合描述
	 * @param path			商品集合图片路径
	 * @param thumb			商品集合缩略图路径
	 * 
	 * @author zhangbo	2015年12月9日
	 */
	void updateItemSet(Integer id, String title, String description, String path, String thumb) throws Exception;
	
	/**
	 * 批量删除商家集合banner
	 * 
	 * @param ids	商家集合id的集合
	 * @author zhangbo	2015年12月8日
	 */
	void batchDelete(Integer[] ids) throws Exception;
	
	/**
	 * 刷新redis缓存，会刷新限时秒杀商品集合banner，与限时秒杀商品集合下商品列表的缓存
	 * 
	 * @param id		商家集合id
	 * @param deadline	秒杀截止日期 
	 * @author zhangbo	2015年12月10日
	 */
	void addSeckillToTemp(Integer id, Date deadline) throws Exception;

	/**
	 * 从秒杀商品集合中去除传递过来的商品集合id
	 * 
	 * @param id	商家集合id
	 * @author zhangbo	2015年12月12日
	 */
	void cancelSeckillFromTemp(Integer id) throws Exception;
	
	/**
	 * 刷新redis缓存，会刷新限时秒杀商品集合与推荐商品集合banner，与其下关联的商品列表的缓存
	 * 
	 * @param ids	商家集合id的集合
	 * @author zhangbo	2015年12月10日
	 */
	void refreshItemSetCache() throws Exception;

	/**
	 * 重新排序
	 * 
	 * @param ids	商家集合ids
	 * @author zhangbo	2015年12月12日
	 */
	void reorder(String ids) throws Exception;

	/**
	 * 根据商品集合id或商品集合标题查询商品集合展示列表
	 * 
	 * @param idOrName
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @author zhangbo	2015年12月21日
	 */
	void buildItemSetListByIdOrName(String idOrName, Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception;

}
