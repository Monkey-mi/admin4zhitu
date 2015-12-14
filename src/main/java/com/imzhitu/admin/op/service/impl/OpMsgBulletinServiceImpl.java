package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.BulletinCacheDao;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.op.mapper.OpMsgBulletinMapper;
import com.imzhitu.admin.op.service.OpMsgBulletinService;

@Service
public class OpMsgBulletinServiceImpl extends BaseServiceImpl implements OpMsgBulletinService{
	
	@Autowired
	private OpMsgBulletinMapper msgBulletinMapper;
	
	@Autowired
	private BulletinCacheDao bulletinCacheDao;
	
	@Autowired
	private KeyGenService keyGenService;
	
	private Logger log = Logger.getLogger(OpMsgBulletinServiceImpl.class);

	@Override
	public void insertMsgBulletin(String path, Integer type, String link,
			Integer operator,String bulletinName,String bulletinThumb) throws Exception {
		Integer  serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
		OpMsgBulletin dto = new OpMsgBulletin();
		Date now = new Date();
		dto.setBulletinPath(path);
		dto.setBulletinType(type);
		dto.setLink(link);
		dto.setOperator(operator);
		dto.setValid(Tag.FALSE);
		dto.setModifyDate(now);
		dto.setAddDate(now);
		dto.setBulletinName(bulletinName);
		dto.setBulletinThumb(bulletinThumb);
		dto.setSerial(serial);
		msgBulletinMapper.insertMsgBulletin(dto);
	}

	@Override
	public void batchDeleteMsgBulletin(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids.length > 0){
			msgBulletinMapper.batchDeleteMsgBulletin(ids);
		}
	}

	@Override
	public void updateMsgBulletin(Integer id, String path, Integer type,
			String link, Integer valid, Integer operator,String bulletinName,String bulletinThumb) throws Exception {
		OpMsgBulletin dto = new OpMsgBulletin();
		Date now = new Date();
		dto.setId(id);
		dto.setBulletinPath(path);
		dto.setBulletinType(type);
		dto.setLink(link);
		dto.setOperator(operator);
		dto.setValid(valid);
		dto.setModifyDate(now);
		dto.setBulletinName(bulletinName);
		dto.setBulletinThumb(bulletinThumb);
		msgBulletinMapper.updateMsgBulletin(dto);
	}

	@Override
	public void batchUpdateMsgBulletinValid(String idsStr,Integer valid,Integer operator) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids.length > 0){
			msgBulletinMapper.batchUpdateMsgBulletinValid(ids,valid,operator);
		}
	}

	@Override
	public void queryMsgBulletin(Integer id, Integer type, Integer valid,Integer isCache,
			Integer maxId, int page, int rows, Map<String, Object> jsonMap)
			throws Exception {
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
			if(list != null && list.size() > 0){
				reMaxId = list.get(0).getSerial();
			}
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}

	@Override
	public long queryMsgBulletinTotalCount(Integer id, Integer type,
			Integer valid, Integer maxId) throws Exception {
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
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		return msgBulletinMapper.queryMsgBulletinByIds(ids);
	}

	@Override
	public void updateMsgBulletinCache(String idsStr,Integer operator) throws Exception {
		Date now  = new Date();
		if(idsStr == null || idsStr.trim().equals("")){
			bulletinCacheDao.updateBulletin(new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>());
			return;
		}
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		List<OpMsgBulletin> list = msgBulletinMapper.queryMsgBulletinByIds(ids);
		List<com.hts.web.common.pojo.OpMsgBulletin> webBulletinList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
		
		
		for(int i = 0; i < ids.length; i++){
			
			//排序
			for(int j = 0; j < list.size(); j++){
				if(ids[i].equals(list.get(j).getId())){
					OpMsgBulletin dto = list.get(j);
					com.hts.web.common.pojo.OpMsgBulletin webBulletin = new com.hts.web.common.pojo.OpMsgBulletin();
					webBulletin.setBulletinPath(dto.getBulletinPath());
					webBulletin.setBulletinType(dto.getBulletinType());
					webBulletin.setId(dto.getId());
					webBulletin.setLink(dto.getLink());
					webBulletin.setBulletinName(dto.getBulletinName());
					webBulletin.setBulletinThumb(dto.getBulletinThumb());
					webBulletinList.add(webBulletin);
					break;
				}
			}
			
			//更新serial
			Integer  serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
			OpMsgBulletin bulletin = new OpMsgBulletin();
			bulletin.setModifyDate(now);
			bulletin.setSerial(serial);
			bulletin.setId(ids[ids.length - i - 1]);
			bulletin.setOperator(operator);
			msgBulletinMapper.updateMsgBulletin(bulletin);
		}
		
		if(webBulletinList.size() > 0){
			bulletinCacheDao.updateBulletin(webBulletinList);
		}
		
		//更新 用户推荐列表缓存com.hts.web.base.constant.CacheKeies.OP_MSG_USER_THEME 对应的type为1
		try{
			OpMsgBulletin tmpBulletin = new OpMsgBulletin();
			List<com.hts.web.common.pojo.OpMsgBulletin> webUserThemeList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
			tmpBulletin.setBulletinType(1);
			List<OpMsgBulletin> userThemeList = msgBulletinMapper.queryMsgBulletin(tmpBulletin);
			if(userThemeList != null && userThemeList.size() > 0){
				for( OpMsgBulletin dto:userThemeList){
					com.hts.web.common.pojo.OpMsgBulletin webBulletin = new com.hts.web.common.pojo.OpMsgBulletin();
					webBulletin.setBulletinPath(dto.getBulletinPath());
					webBulletin.setBulletinType(dto.getBulletinType());
					webBulletin.setId(dto.getId());
					webBulletin.setLink(dto.getLink());
					webBulletin.setBulletinName(dto.getBulletinName());
					webBulletin.setBulletinThumb(dto.getBulletinThumb());
					webUserThemeList.add(webBulletin);
				}
			}
			
			if( webBulletinList.size() > 0 ) {
				bulletinCacheDao.updateUserThemeBulletin(webUserThemeList);
			}
		}catch(Exception e){
			log.warn(e.getMessage());
		}
		
		//更新频道推荐缓存com.hts.web.base.constant.CacheKeies.OP_MSG_CHANNEL_THEME 对应的type为2
		try{
			OpMsgBulletin tmpChannelBulletin = new OpMsgBulletin();
			List<com.hts.web.common.pojo.OpMsgBulletin> webChannelThemeList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
			tmpChannelBulletin.setBulletinType(2);
			List<OpMsgBulletin> channelThemeList = msgBulletinMapper.queryMsgBulletin(tmpChannelBulletin);
			if(channelThemeList != null && channelThemeList.size() > 0){
				for( OpMsgBulletin dto:channelThemeList){
					com.hts.web.common.pojo.OpMsgBulletin webChannelBulletin = new com.hts.web.common.pojo.OpMsgBulletin();
					webChannelBulletin.setBulletinPath(dto.getBulletinPath());
					webChannelBulletin.setBulletinType(dto.getBulletinType());
					webChannelBulletin.setId(dto.getId());
					webChannelBulletin.setLink(dto.getLink());
					webChannelBulletin.setBulletinName(dto.getBulletinName());
					webChannelBulletin.setBulletinThumb(dto.getBulletinThumb());
					webChannelThemeList.add(webChannelBulletin);
				}
			}
			
			if( webChannelThemeList.size() > 0 ) {
				bulletinCacheDao.updateChannelThemeBulletin(webChannelThemeList);
			}
		}catch(Exception e){
			log.warn(e.getMessage());
		}
		
		//缓存活动/专题推荐列表 com.hts.web.base.constant.CacheKeies.OP_MSG_THEME 对应的type为4
		
		try{
			OpMsgBulletin tmpBulletin = new OpMsgBulletin();
			List<com.hts.web.common.pojo.OpMsgBulletin> webThemeList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
			tmpBulletin.setBulletinType(4);
			List<OpMsgBulletin> themeList = msgBulletinMapper.queryMsgBulletin(tmpBulletin);
			if(themeList != null && themeList.size() > 0){
				for( OpMsgBulletin dto:themeList){
					com.hts.web.common.pojo.OpMsgBulletin webBulletin = new com.hts.web.common.pojo.OpMsgBulletin();
					webBulletin.setBulletinPath(dto.getBulletinPath());
					webBulletin.setBulletinType(dto.getBulletinType());
					webBulletin.setId(dto.getId());
					webBulletin.setLink(dto.getLink());
					webBulletin.setBulletinName(dto.getBulletinName());
					webBulletin.setBulletinThumb(dto.getBulletinThumb());
					webThemeList.add(webBulletin);
				}
			}
			if(webThemeList.size() > 0 ) {
				bulletinCacheDao.updateThemeBulletin(webThemeList);
			}
		}catch(Exception e){
			log.warn(e.getMessage());
		}
	}

}
