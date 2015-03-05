package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface InteractRefreshZombieHtWorldService extends BaseService{
	/**
	 * 分页查询马甲织图
	 * @param days
	 * @param page
	 * @param row
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryZombieHtworldList(Integer days,int page,int row ,Map<String,Object> jsonMap)throws Exception;
	
	/**
	 * 更新织图的时间
	 * @param ids
	 * @param refreshDate
	 * @throws Exception
	 */
	public void refreshWorldCreateDate(Integer[] ids,Date refreshDate)throws Exception;
	
	/**
	 * 更新织图评论时间
	 * @param ids
	 * @param refreshDate
	 * @throws Exception
	 */
	public void refreshCommentCreateDate(Integer[]ids,Date refreshDate)throws Exception;
	
	/**
	 * 更新织图以及其评论的时间,by worldIds
	 * @param idsStr
	 * @param refreshDate
	 * @throws Exception
	 */
	public void updateZombieHtworld(String idsStr,Date refreshDate)throws Exception;
	
	/**
	 * 根据用户id更新其所有的织图的时间
	 * 例如refreshDate 为2014-8-1 12:00:00 daySpan为6，
	 * 则某id对应马甲的最新织图的刷新时间为2014-8-1 12:00:00
	 * 其第二新织图的刷新时间为2014-7-26 12:00:00
	 * 以此倒推
	 * @param uids	用户idsStr
	 * @param refreshDate	最后的刷新时间
	 * @param daySpan		时间间隔
	 * @throws Exception
	 */
	public void updateZombieHtworldByUserIds(String uidsStr,Date refreshDate,Integer daySpan)throws Exception;
}
