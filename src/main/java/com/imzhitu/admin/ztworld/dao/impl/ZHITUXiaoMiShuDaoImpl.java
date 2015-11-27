package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.ztworld.dao.ZHITUXiaoMiShuDao;

@Repository
public class ZHITUXiaoMiShuDaoImpl extends BaseDaoImpl implements ZHITUXiaoMiShuDao{
	private static final String table = HTS.HTWORLD_HTWORLD;
	
	/**
	 * 删除世界
	 */
	private static final String DEL_WORLD_BY_IDS = " delete from " + table + " where id in "; 
	
	private static final String UPDATE_WORLD_DESC_BY_WID = " update " + table + " set world_desc=? where id=?";
	
	private static final String UPDATE_WORLD_LABEL_BY_WID = " update " + table + " set world_label=? where id=?";
	
	private static final String GET_WORLD_COUNT  = " select count(1) from " + table + " where author_id=? and id<?";
	
	@Override
	public void delZTWorldByIds(Integer[] ids){
		String sql = DEL_WORLD_BY_IDS+SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	@Override
	public void updateZTWorldDesc(Integer wid,String worldDesc){
		getMasterJdbcTemplate().update(UPDATE_WORLD_DESC_BY_WID, worldDesc,wid);
	}
	
	@Override
	public void updateWorldLabel(Integer wid,String worldLabel){
		getMasterJdbcTemplate().update(UPDATE_WORLD_LABEL_BY_WID,worldLabel,wid);
	}
	
	@Override
	public long getWorldCount(Integer authorId,Integer maxId){
		return getJdbcTemplate().queryForLong(GET_WORLD_COUNT, authorId,maxId);
	}
	
}
