package com.imzhitu.admin.interact.service;

import com.hts.web.common.service.BaseService;

public interface InteractActiveOperatedService extends BaseService{
	/**
	 * 根据world_id查询是活动是否被操作过 
	 * @param wid
	 * @return
	 */
	public boolean checkIsOperatedByWId(Integer wid)throws Exception;
	
	/**
	 * 增加
	 * @param wid
	 * @param Operated
	 */
	public void addOperated(Integer wid,Integer operated)throws Exception;
	
	/**
	 * 删除
	 * 
	 */
	public void delOperated(Integer wid)throws Exception;
	
	/**
	 * 更新
	 * @param wid
	 * @param operated
	 */
	public void updateOperated(Integer wid,Integer  operated)throws Exception;
}
