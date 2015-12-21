package com.imzhitu.admin.trade.item.dao;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * @author zhangbo	2015年12月21日
 *
 */
public interface SeckillItemSetCacheDao extends BaseCacheDao {

	/**
	 * 添加秒杀商品集合到秒杀记录中
	 * 
	 * @param itemSetId	商家集合id
	 * @param deadline	秒杀截止时间
	 * @author zhangbo	2015年12月12日
	 */
	public void addSeckillTemp(Integer itemSetId, Date deadline);
	
	/**
	 * 从记录秒杀Map中删除指定的商家集合
	 * 
	 * @param itemSetId	商家集合id
	 * @author zhangbo	2015年12月12日
	 */
	public void deleteFromSeckillTempById(Integer itemSetId);
	
	/**
	 * 获取秒杀商品集合临时存储
	 * 
	 * @return
	 * @author zhangbo	2015年12月12日
	 */
	public Map<Integer, Date> getSeckillTemp();
}
