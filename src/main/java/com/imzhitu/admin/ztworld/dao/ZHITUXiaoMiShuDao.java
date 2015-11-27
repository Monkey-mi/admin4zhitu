package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.ZTWorldDto;

public interface ZHITUXiaoMiShuDao extends BaseDao{

	public void delZTWorldByIds(Integer[] ids);
	public void updateZTWorldDesc(Integer wid,String worldDesc);
	public void updateWorldLabel(Integer wid,String worldLabel);
	public long getWorldCount(Integer authorId,Integer maxId);
}
