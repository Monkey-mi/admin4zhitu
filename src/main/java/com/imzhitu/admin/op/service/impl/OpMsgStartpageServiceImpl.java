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
import com.hts.web.operations.dao.StartPageCacheDao;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpMsgStartpage;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.op.mapper.OpMsgStartpageMapper;
import com.imzhitu.admin.op.service.OpMsgStartpageService;

@Service
public class OpMsgStartpageServiceImpl extends BaseServiceImpl implements OpMsgStartpageService{
	
	@Autowired
	private OpMsgStartpageMapper startpageMapper;
	
	@Autowired
	private StartPageCacheDao startpageCacheDao;
	
	@Autowired
	private KeyGenService keyGenService;

	@Override
	public void insertMsgStartpage(String linkPath, Integer linkType,
			String link, Date beginDate, Date endDate, Integer operator)
			throws Exception {
		Integer serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_START_PAGE_SERIAL);
		OpMsgStartpage dto = new OpMsgStartpage();
		Date now = new Date();
		dto.setAddDate(now);
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		dto.setLastModified(now);
		dto.setLink(link);
		dto.setLinkPath(linkPath);
		dto.setLinkType(linkType);
		dto.setOperator(operator);
		dto.setValid(Tag.FALSE);
		dto.setSerial(serial);
		startpageMapper.insertMsgStartpage(dto);
	}

	@Override
	public void batchDeleteMsgStartpage(String idsStr) throws Exception {
		Integer[]ids = StringUtil.convertStringToIds(idsStr);
		startpageMapper.batchDeleteMsgStartpage(ids);
	}

	@Override
	public void updateMsgStartpage(Integer id, String linkPath,
			Integer linkType, String link, Integer valid, Date beginDate,
			Date endDate, Integer operator) throws Exception {
		OpMsgStartpage dto = new OpMsgStartpage();
		Date now = new Date();
		dto.setId(id);
		dto.setAddDate(now);
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		dto.setLastModified(now);
		dto.setLink(link);
		dto.setLinkPath(linkPath);
		dto.setLinkType(linkType);
		dto.setOperator(operator);
		startpageMapper.updateMsgStartpage(dto);
	}

	@Override
	public void batchUpdateMsgStartpageValid(String idsStr, Integer valid,
			Integer operator) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		startpageMapper.batchUpdateMsgStartpageValid(ids, valid, operator);
	}

	@Override
	public void queryMsgStartpage(Integer id, Integer linkType, Integer valid,
			Integer isCache, Date beginDate, Date endDate, Integer maxId,
			int page, int rows, Map<String, Object> jsonMap) throws Exception {
		Integer reMaxId = 0;
		long total 		= 0;
		List<OpMsgStartpage> list = null;
		if(isCache != null && isCache == 0){
			List<com.hts.web.common.pojo.OpMsgStartPage> webStarpage = startpageCacheDao.queryStartPage();
			if(webStarpage != null){
				total = webStarpage.size();
			}
			jsonMap.put(OptResult.JSON_KEY_ROWS, webStarpage);
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
			return;
		}
		
		OpMsgStartpage dto = new OpMsgStartpage();
		dto.setId(id);
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		dto.setLinkType(linkType);
		dto.setValid(valid);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		dto.setMaxId(maxId);
		
		total = startpageMapper.queryMsgStartpageTotalCount(dto);
		if(total > 0){
			list = startpageMapper.queryMsgStartpage(dto);
			if(list != null && list.size() > 0){
				reMaxId = list.get(0).getSerial();
			}
		}
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
		
	}

	@Override
	public long queryMsgStartpageTotalCount(Integer id, Integer linkType,
			Integer valid, Date beginDate, Date endDate, Integer maxId)
			throws Exception {
		OpMsgStartpage dto = new OpMsgStartpage();
		dto.setId(id);
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		dto.setLinkType(linkType);
		dto.setValid(valid);
		dto.setMaxId(maxId);
		return startpageMapper.queryMsgStartpageTotalCount(dto);
	}

	@Override
	public List<OpMsgStartpage> queryMsgBulletinByIds(String idsStr)
			throws Exception {
		if(idsStr == null || idsStr.trim().equals("")){
			throw new Exception("idsStr cannot be null");
		}
		Integer[]ids = StringUtil.convertStringToIds(idsStr);
		
		return startpageMapper.queryMsgBulletinByIds(ids);
	}

	@Override
	public void updateMsgStartpageCache(String idsStr,Integer operator) throws Exception {
		Date now = new Date();
		if(idsStr == null || idsStr.trim().equals("")){
			startpageCacheDao.updateStartPage(new ArrayList<com.hts.web.common.pojo.OpMsgStartPage>());
			return;
		}
		Integer[]ids = StringUtil.convertStringToIds(idsStr);
		List<OpMsgStartpage> list = startpageMapper.queryMsgBulletinByIds(ids);
		List<com.hts.web.common.pojo.OpMsgStartPage> webStarpageList = new ArrayList<com.hts.web.common.pojo.OpMsgStartPage>();
		
		for(int i=0; i < ids.length; i++){
			
			//排序
			for(int j=0; j < list.size(); j++){
				if(ids[i] == list.get(j).getId()){
					OpMsgStartpage dto = list.get(j);
					com.hts.web.common.pojo.OpMsgStartPage webStartpage = new com.hts.web.common.pojo.OpMsgStartPage();
					webStartpage.setBeginDate(dto.getBeginDate());
					webStartpage.setEndDate(dto.getEndDate());
					webStartpage.setId(dto.getId());
					webStartpage.setLastModified(dto.getLastModified().getTime());
					webStartpage.setLink(dto.getLink());
					webStartpage.setLinkPath(dto.getLinkPath());
					webStartpage.setLinkType(dto.getLinkType());
					webStarpageList.add(webStartpage);
					break;
				}
			}
			
			//更新排序
			Integer serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_START_PAGE_SERIAL);
			OpMsgStartpage startpage = new OpMsgStartpage();
			startpage.setLastModified(now);
			startpage.setSerial(serial);
			startpage.setOperator(operator);
			startpage.setId(ids[ids.length - i - 1]);
			startpageMapper.updateMsgStartpage(startpage);
		}
		
		if(webStarpageList.size() > 0){
			startpageCacheDao.updateStartPage(webStarpageList);
//			startpageMapper.batchUpdateMsgStartpageLastModified(ids);
		}else{
			throw new Exception("update 0 rows.reason: no data for update!");
		}
	}

}
