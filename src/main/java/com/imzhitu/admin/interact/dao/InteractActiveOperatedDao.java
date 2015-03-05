package com.imzhitu.admin.interact.dao;

import com.hts.web.common.dao.BaseDao;

public interface InteractActiveOperatedDao extends BaseDao{
	/**
	 * 根据world_id查询是活动是否被操作过 
	 * @param wid
	 * @return
	 */
	public boolean checkIsOperatedByWId(Integer wid);
	
	/**
	 * 增加
	 * @param wid
	 * @param Operated
	 */
	public void addOperated(Integer wid,Integer operated);
	
	/**
	 * 删除
	 * 
	 */
	public void delOperated(Integer wid);
	
	/**
	 * 更新
	 * @param wid
	 * @param operated
	 */
	public void updateOperated(Integer wid,Integer  operated);
	
}
