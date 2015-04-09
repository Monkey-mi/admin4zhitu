package com.imzhitu.admin.interact.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.interact.dao.InteractActiveOperatedDao;

@Repository
public class InteractActiveOperatedDaoImpl extends BaseDaoImpl implements InteractActiveOperatedDao{
	private static final String table = Admin.INTERACT_ACTIVE_OPERATED;
	private static final String ADD_OPERATED = " insert into " + table + " (world_id,operated) values (?,?)";
	private static final String UPDATE_OPERATED = " update " + table + " set operated=? where world_id=?";
	private static final String CHECK_IS_OPERATED = " select count(*) from  " + table + " where world_id=? and operated=1";
	private static final String DEL_OPERATED = "delete from " + table + " where world_id=?";
	
	@Override
	public void addOperated(Integer wid,Integer operated){
		getMasterJdbcTemplate().update(ADD_OPERATED, wid,operated);
	}
	
	@Override
	public boolean checkIsOperatedByWId(Integer wid){
		long r = getJdbcTemplate().queryForLong(CHECK_IS_OPERATED, wid);
		if(r == 0){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public void delOperated(Integer wid){
		getMasterJdbcTemplate().update(DEL_OPERATED, wid);
	}
	
	@Override
	public void updateOperated(Integer wid,Integer  operated){
		getMasterJdbcTemplate().update(UPDATE_OPERATED, operated,wid);
	}
}
