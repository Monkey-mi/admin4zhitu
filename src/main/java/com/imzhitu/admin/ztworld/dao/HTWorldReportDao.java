package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.imzhitu.admin.common.pojo.ZTWorldReport;
import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 查询举报
 * </p>
 * 
 * 创建时间：2014年3月4日 10:47:09
 * 
 * @author zxx
 *
 */
public interface HTWorldReportDao extends BaseDao{
	
	public List<ZTWorldReport> queryReport( RowSelection rowSelection);
	
	public List<ZTWorldReport> queryReport(Integer maxId, RowSelection rowSelection);
	
	public long queryReportCount();
	public void deleteReportById(Integer[] ids);

}
