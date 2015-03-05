package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpNotice;

public interface NoticeCacheDao extends BaseCacheDao {

	public List<OpNotice> queryNotice();
	
	/**
	 * 保存公告
	 * 
	 * @param notice
	 */
	public void saveNotice(OpNotice notice);
	
	/**
	 * 查询公告
	 * 
	 * @param phoneCodes
	 */
	public void deleteNotice(Integer[] phoneCodes);
	
	/**
	 * 查询公告
	 * 
	 * @param phoneCode
	 * @return
	 */
	public OpNotice queryNotice(Integer phoneCode);
}
