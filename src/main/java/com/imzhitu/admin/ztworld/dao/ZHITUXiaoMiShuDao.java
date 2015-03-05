package com.imzhitu.admin.ztworld.dao;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;

import java.util.List;

import com.imzhitu.admin.common.pojo.ZTWorldDto;

public interface ZHITUXiaoMiShuDao extends BaseDao{

	public void addZTWorld(ZTWorldDto dto);
	public void delZTWorldByIds(Integer[] ids);
	public List<ZTWorldDto> queryZTWorldByUId(Integer uid,Integer limit);
	public ZTWorldDto queryWorldByWid(Integer wid);
	public void updateZTWorldDesc(Integer wid,String worldDesc);
	public void updateWorldLabel(Integer wid,String worldLabel);
	public List<ZTWorldDto> queryWorld(Integer authorId,RowSelection rowSelection);
	public List<ZTWorldDto> queryWorld(Integer authorId,Integer maxId,RowSelection rowSelection);
	public long getWorldCount(Integer authorId,Integer maxId);
}
