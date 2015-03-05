package com.imzhitu.admin.interact.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.interact.service.InteractActiveOperatedService;
import com.imzhitu.admin.interact.dao.InteractActiveOperatedDao;

@Service
public class InteractActiveOperatedServiceImpl extends BaseServiceImpl implements InteractActiveOperatedService{
	
	@Autowired
	private InteractActiveOperatedDao interactActiveOperatedDao;
	
	@Override
	public boolean checkIsOperatedByWId(Integer wid)throws Exception{
		return interactActiveOperatedDao.checkIsOperatedByWId(wid);
	}
	
	@Override
	public void addOperated(Integer wid,Integer operated)throws Exception{
		if(interactActiveOperatedDao.checkIsOperatedByWId(wid)){
			interactActiveOperatedDao.updateOperated(wid, operated);
		}else{
			interactActiveOperatedDao.addOperated(wid, operated);
		}		
	}
	
	@Override
	public void delOperated(Integer wid)throws Exception{
		interactActiveOperatedDao.delOperated(wid);
	}
	
	@Override
	public void updateOperated(Integer wid,Integer  operated)throws Exception{
		if(interactActiveOperatedDao.checkIsOperatedByWId(wid)){
			interactActiveOperatedDao.updateOperated(wid, operated);
		}else{
			interactActiveOperatedDao.addOperated(wid, operated);
		}
	}

}
