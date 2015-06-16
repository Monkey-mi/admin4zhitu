package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.BulletinCacheDao;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;
import com.imzhitu.admin.op.mapper.OpMsgBulletinMapper;
import com.imzhitu.admin.op.service.OpMsgBulletinService;

@Service
public class OpMsgBulletinServiceImpl extends BaseServiceImpl implements OpMsgBulletinService{
	
	@Autowired
	private OpMsgBulletinMapper msgBulletinMapper;
	
	@Autowired
	private BulletinCacheDao bulletinCacheDao;
	

	@Override
	public void insertMsgBulletin(String path, Integer type, String link,
			Integer operator) throws Exception {
		// TODO Auto-generated method stub
		OpMsgBulletin dto = new OpMsgBulletin();
		Date now = new Date();
		dto.setBulletinPath(path);
		dto.setBulletinType(type);
		dto.setLink(link);
		dto.setOperator(operator);
		dto.setValid(Tag.FALSE);
		dto.setModifyDate(now);
		dto.setAddDate(now);
		
		msgBulletinMapper.insertMsgBulletin(dto);
	}

	@Override
	public void batchDeleteMsgBulletin(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids.length > 0){
			msgBulletinMapper.batchDeleteMsgBulletin(ids);
		}
	}

	@Override
	public void updateMsgBulletin(Integer id, String path, Integer type,
			String link, Integer valid, Integer operator) throws Exception {
		// TODO Auto-generated method stub
		OpMsgBulletin dto = new OpMsgBulletin();
		Date now = new Date();
		dto.setId(id);
		dto.setBulletinPath(path);
		dto.setBulletinType(type);
		dto.setLink(link);
		dto.setOperator(operator);
		dto.setValid(valid);
		dto.setModifyDate(now);
		msgBulletinMapper.updateMsgBulletin(dto);
	}

	@Override
	public void batchUpdateMsgBulletinValid(String idsStr,Integer valid,Integer operator) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids.length > 0){
			msgBulletinMapper.batchUpdateMsgBulletinValid(ids,valid,operator);
		}
	}

	@Override
	public void queryMsgBulletin(Integer id, Integer type, Integer valid,Integer isCache,
			Integer maxId, int page, int rows, Map<String, Object> jsonMap)
			throws Exception {
		// TODO Auto-generated method stub
		List<OpMsgBulletin> list = null;
		Integer reMaxId = 0;
		long total = 0;
		if(isCache != null && isCache == 0){//从缓存里拿数据
			List<com.hts.web.common.pojo.OpMsgBulletin> bulletinList = bulletinCacheDao.queryBulletin();
			if(bulletinList != null ){
				total = bulletinList.size();
			}
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
			jsonMap.put(OptResult.JSON_KEY_ROWS, bulletinList);
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
			return;
		}
		OpMsgBulletin dto = new OpMsgBulletin();
		dto.setId(id);
		dto.setBulletinType(type);
		dto.setValid(valid);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		total = msgBulletinMapper.queryMsgBulletinTotalCount(dto);
		
		if(total > 0){
			list = msgBulletinMapper.queryMsgBulletin(dto);
			reMaxId = list.get(0).getId();
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}

	@Override
	public long queryMsgBulletinTotalCount(Integer id, Integer type,
			Integer valid, Integer maxId) throws Exception {
		// TODO Auto-generated method stub
		OpMsgBulletin dto = new OpMsgBulletin();
		dto.setId(id);
		dto.setBulletinType(type);
		dto.setValid(valid);
		dto.setMaxId(maxId);
		
		return msgBulletinMapper.queryMsgBulletinTotalCount(dto);
	}

	@Override
	public List<OpMsgBulletin> queryMsgBulletinByIds(String idsStr)
			throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		return msgBulletinMapper.queryMsgBulletinByIds(ids);
	}

	@Override
	public void updateMsgBulletinCache(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		if(idsStr == null || idsStr.trim().equals("")){
			throw new Exception("参数不能为空");
		}
		List<OpMsgBulletin> list = queryMsgBulletinByIds(idsStr);
		List<com.hts.web.common.pojo.OpMsgBulletin> webBulletinList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
		for(OpMsgBulletin dto:list){
			com.hts.web.common.pojo.OpMsgBulletin webBulletin = new com.hts.web.common.pojo.OpMsgBulletin();
			webBulletin.setBulletinPath(dto.getBulletinPath());
			webBulletin.setBulletinType(dto.getBulletinType());
			webBulletin.setId(dto.getId());
			webBulletin.setLink(dto.getLink());
			webBulletinList.add(webBulletin);
		}
		
		if(webBulletinList.size() > 0){
			bulletinCacheDao.updateBulletin(webBulletinList);
		}
	}

}
