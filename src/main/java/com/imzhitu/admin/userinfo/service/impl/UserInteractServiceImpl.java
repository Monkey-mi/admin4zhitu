package com.imzhitu.admin.userinfo.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.UserConcern;
import com.imzhitu.admin.common.pojo.UserReportDto;
import com.imzhitu.admin.op.dao.UserZombieDao;
import com.imzhitu.admin.userinfo.dao.UserConcernDao;
import com.imzhitu.admin.userinfo.mapper.UserReportMapper;
import com.imzhitu.admin.userinfo.service.UserInteractService;

@Service
public class UserInteractServiceImpl extends BaseServiceImpl implements
		UserInteractService {
	
	@Autowired
	private com.hts.web.userinfo.service.UserInteractService webUserInteractService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;

	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private UserZombieDao userZombieDao;
	
	@Autowired
	private UserReportMapper userReportMapper;
	
	@Value("${admin.interact.minConcernCount}")
	private Integer minConcernCount;
	
	@Value("${admin.interact.maxConcernCount}")
	private Integer maxConcernCount;
	
	public Integer getMinConcernCount() {
		return minConcernCount;
	}

	public void setMinConcernCount(Integer minConcernCount) {
		this.minConcernCount = minConcernCount;
	}

	public Integer getMaxConcernCount() {
		return maxConcernCount;
	}

	public void setMaxConcernCount(Integer maxConcernCount) {
		this.maxConcernCount = maxConcernCount;
	}

	@Override
	public void buildFollow(final Integer userId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserConcern>() {

			@Override
			public List<UserConcern> getSerializables(
					RowSelection rowSelection) {
				List<UserConcern> list = userConcernDao.queryFollow(userId, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public List<UserConcern> getSerializableByMaxId(
					int maxId, RowSelection rowSelection) {
				List<UserConcern> list = userConcernDao.queryFollowByMaxId(userId, maxId, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userConcernDao.queryFollowCountByMaxId(userId, maxId);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
		

	}

	@Override
	public void buildConcern(final Integer userId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserConcern>() {

			@Override
			public List<UserConcern> getSerializables(
					RowSelection rowSelection) {
				List<UserConcern> list = userConcernDao.queryConcern(userId, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public List<UserConcern> getSerializableByMaxId(
					int maxId, RowSelection rowSelection) {
				List<UserConcern> list = userConcernDao.queryConcernByMaxId(userId, maxId, rowSelection);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userConcernDao.queryConcernCountByMaxId(userId, maxId);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);

	}

	@Override
	public void saveFollows(String idsStr, Integer userId) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(int id : ids) {
			int concernCount = NumberUtil.getRandomNum(minConcernCount, maxConcernCount);
			webUserInteractService.saveConcern(false, id, userId, concernCount);
		}
	}

	@Override
	public void saveRandomFollows(Integer count, Integer userId) throws Exception {
		List<Integer> ids = userZombieDao.queryRandomZombieId(count);
		for(int id : ids) {
			int concernCount = NumberUtil.getRandomNum(minConcernCount, maxConcernCount);
			webUserInteractService.saveConcern(false, id, userId, concernCount);
		}
	}

	@Override
	public void buildReport(UserReportDto report, final int page, final int rows, 
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(report, page, rows, jsonMap,
				new NumberDtoListAdapter<UserReportDto>() {

					@Override
					public List<? extends Serializable> queryList(
							UserReportDto dto) {
						return userReportMapper.queryReport(dto);
					}

					@Override
					public long queryTotal(UserReportDto dto) {
						return userReportMapper.queryTotal(dto);
					}
			
		});
	}

	@Override
	public void updateReportFollowed(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0) {
			userReportMapper.updateValidByIds(ids);;
		}
	}

}
