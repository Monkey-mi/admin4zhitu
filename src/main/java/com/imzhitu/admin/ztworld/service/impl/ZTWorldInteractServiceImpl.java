package com.imzhitu.admin.ztworld.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;
import com.imzhitu.admin.common.pojo.ZTWorldLikeDto;
import com.imzhitu.admin.common.pojo.ZTWorldReport;
import com.imzhitu.admin.op.dao.UserZombieDao;
import com.imzhitu.admin.ztworld.dao.HTWorldCommentDao;
import com.imzhitu.admin.ztworld.dao.HTWorldKeepDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLikedDao;
import com.imzhitu.admin.ztworld.dao.HTWorldReportDao;
import com.imzhitu.admin.ztworld.service.ZTWorldInteractService;

@Service
public class ZTWorldInteractServiceImpl extends BaseServiceImpl implements
		ZTWorldInteractService {
	
	@Autowired
	private HTWorldCommentDao worldCommentDao;
	
	@Autowired
	private HTWorldLikedDao worldLikedDao;
	
	@Autowired
	private HTWorldKeepDao worldKeepDao;
	
	@Autowired
	private UserZombieDao userZombieDao;
	
	@Autowired
	private HTWorldReportDao worldReportDao;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldCommentDao webCommentDao;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldInteractService webWorldInteractService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	

	@Override
	public void buildComments(Integer sinceId, Integer maxId, int start,
			int limit, Integer worldId, String authorName, 
			Map<String, Object> jsonMap) throws Exception{
		final Map<String, Object> attrMap = new LinkedHashMap<String, Object>(); // 织图查询条件容器
		final Map<String, Object> userAttrMap = new LinkedHashMap<String, Object>(); // 用户信息查询条件容器
		
		if(worldId != null) {
			attrMap.put("world_id", worldId);
		}
		
		// 装载用户信息查询条件
		if(!StringUtil.checkIsNULL(authorName)) {
			userAttrMap.put("user_name", authorName);
		}
		
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<ZTWorldCommentDto>() {

			@Override
			public List<ZTWorldCommentDto> getSerializables(
					RowSelection rowSelection) {
				List<ZTWorldCommentDto> list = worldCommentDao.queryComment(attrMap, userAttrMap, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public List<ZTWorldCommentDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<ZTWorldCommentDto> list = worldCommentDao.queryCommentByMaxId(maxId, attrMap, userAttrMap, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldCommentDao.queryCommentCountByMaxId(maxId, attrMap, userAttrMap);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	@Override
	public void shieldComment(Integer id) throws Exception {
		worldCommentDao.updateCommentShield(id, Tag.TRUE);
		Integer wid = worldCommentDao.queryWorldId(id);
		updateCommentCount(wid);
	}
	
	private void updateCommentCount(Integer worldId) {
		Long count = webCommentDao.queryCommentCount(worldId);
		worldDao.updateCommentCount(worldId, count.intValue());
		
	}

	@Override
	public void unShieldComment(Integer id) throws Exception {
		worldCommentDao.updateCommentShield(id, Tag.FALSE);
		Integer wid = worldCommentDao.queryWorldId(id);
		updateCommentCount(wid);
	}
	
	@Override
	public void updateCommentShieldByUserId(Integer userId,Integer shield)throws Exception{
		worldCommentDao.updateCommentShieldByUserId(userId, shield);
		List<Integer> wids = worldCommentDao.queryWorldIds(userId);
		for(Integer wid : wids) {
			updateCommentCount(wid);
		}
	}
	
	
	@Override
	public void buildLikedUser(Integer maxId, int start, int limit,
			final Integer worldId, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<ZTWorldLikeDto>() {

			@Override
			public List<ZTWorldLikeDto> getSerializables(
					RowSelection rowSelection) {
				List<ZTWorldLikeDto> list = worldLikedDao.queryLikedUser(worldId, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public List<ZTWorldLikeDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<ZTWorldLikeDto> list = worldLikedDao.queryLikedUser(maxId, worldId, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldLikedDao.queryLikedUserCount(maxId, worldId);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public void saveLikedUser(Integer[] ids, Integer worldId) throws Exception {
		for(Integer userId : ids) {
			webWorldInteractService.saveLiked(false, userId, worldId, null);
		}
	}

	@Override
	public void saveLikedUser(String idsStr, Integer worldId) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		saveLikedUser(ids, worldId);
	}

	@Override
	public void saveLikedZombieUser(int count, Integer worldId) throws Exception {
		List<Integer> idsList = userZombieDao.queryRandomZombieId(count);
		for(Integer userId : idsList) {
			webWorldInteractService.saveLiked(false, userId, worldId, null);
		}
	}
	
	@Override
	public void buildKeepUser(Integer maxId, int start, int limit,
			final Integer worldId, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserInfo>() {

			@Override
			public List<UserInfo> getSerializables(
					RowSelection rowSelection) {
				return worldKeepDao.queryKeepUser(worldId, rowSelection);
			}

			@Override
			public List<UserInfo> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return worldKeepDao.queryKeepUser(maxId, worldId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldKeepDao.queryKeepUserCount(maxId, worldId);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public void saveKeepUser(String idsStr, Integer worldId) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer userId : ids) {
			webWorldInteractService.saveKeep(userId, worldId);
		}
	}

	@Override
	public void saveKeepZombieUser(int count, Integer worldId) throws Exception {
		List<Integer> idsList = userZombieDao.queryRandomZombieId(count);
		for(Integer userId : idsList) {
			webWorldInteractService.saveKeep(userId, worldId);
		}
	}
	
	@Override
	public void queryReport(Integer maxId,Integer start, Integer limit,Integer worldId, Map<String, Object> jsonMap)throws Exception{
		
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<ZTWorldReport>() {

			@Override
			public List<ZTWorldReport> getSerializables(
					RowSelection rowSelection) {
				return worldReportDao.queryReport(rowSelection);
			}

			@Override
			public List<ZTWorldReport> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return worldReportDao.queryReport(maxId,rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldReportDao.queryReportCount();
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
		
	}
	
	@Override
	public void deleteReportById(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		worldReportDao.deleteReportById(ids);
	}
	

}
