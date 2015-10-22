package com.imzhitu.admin.ztworld.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldDao;
import com.imzhitu.admin.ztworld.dao.ZHITUXiaoMiShuDao;
import com.imzhitu.admin.ztworld.service.ZHITUXiaoMiShuService;

@Service
public class ZHITUXiaoMiShuServiceImpl extends BaseServiceImpl implements
		ZHITUXiaoMiShuService {

	@Value("${push.customerServiceId}")
	private Integer authorId;
	
	@Autowired
	private ZHITUXiaoMiShuDao xiaoMiShuDao;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private HTWorldChildWorldDao worldChildWorldDao;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldChildWorldDao webWorldChildWorldDao;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldDao webWorldDao;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getAuthorId() {
		return this.authorId;
	}


	@Override
	public void addZTWorld(ZTWorldDto dto) throws Exception {
		xiaoMiShuDao.addZTWorld(dto);
	}

	@Override
	public void delZTWorldByIds(String idsStr) throws Exception {
		if (idsStr == null || idsStr.equals(""))
			return;
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		xiaoMiShuDao.delZTWorldByIds(ids);
	}

	@Override
	public List<ZTWorldDto> queryZTWorldByUId(Integer limit) throws Exception {
		if (authorId == null)
			return null;
		if (limit <= 0 || limit == null)
			return null;
		return xiaoMiShuDao.queryZTWorldByUId(authorId, limit);
	}

	@Override
	public void saveZTWorld(Integer worldId) throws Exception {
		ZTWorldDto dto = xiaoMiShuDao.queryWorldByWid(worldId);
		if (dto != null) {
			Integer id = webKeyGenService
					.generateId(KeyGenServiceImpl.HTWORLD_HTWORLD_ID);
			String shortLink = MD5Encrypt.shortUrl(Long.valueOf(id));
			dto.setId(id);
			dto.setShortLink(shortLink);
			dto.setAuthorId(authorId);
			addZTWorld(dto);

			// 保存子世界
			List<HTWorldChildWorld> childList = worldChildWorldDao
					.queryChildWorld(worldId);
			Map<Integer, Integer> idMap = new HashMap<Integer, Integer>();
			for (HTWorldChildWorld child : childList) {
				Integer cid = webKeyGenService
						.generateId(KeyGenServiceImpl.HTWORLD_CHILD_WORLD_ID);
				idMap.put(child.getId(), cid);
				child.setWorldId(id);
				child.setId(cid);
			}

			for (HTWorldChildWorld child : childList) {
				Integer atId = child.getAtId();
				if (atId != 0) {
					child.setAtId(idMap.get(atId));
				}
				webWorldChildWorldDao.saveChildWorld(child);
			}
			Long worldCount = webWorldDao.queryWorldCountByAuthorId(authorId);
			Integer childCount = webWorldDao.queryChildCount(authorId);
			webUserInfoDao.updateWorldAndChildCount(authorId, worldCount.intValue(), childCount);
			
		}
	}

	@Override
	public void updateWorldDescByWid(Integer wid, String worldDesc)
			throws Exception {
		if (wid == null || worldDesc == null)
			return;
		xiaoMiShuDao.updateZTWorldDesc(wid, worldDesc);
	}

	@Override
	public void updateWorldLabelByWid(Integer wid, String worldLable)
			throws Exception {
		xiaoMiShuDao.updateWorldLabel(wid, worldLable);
	}

	@Override
	public void queryWorld(Integer maxId, int start, int limit,
			Integer worldId, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<ZTWorldDto>() {
					@Override
					public List<ZTWorldDto> getSerializables(
							RowSelection rowSelection) {
						return xiaoMiShuDao.queryWorld(authorId, rowSelection);
					}

					@Override
					public List<ZTWorldDto> getSerializableByMaxId(int maxId,
							RowSelection rowSelection) {
						return xiaoMiShuDao.queryWorld(authorId, maxId,
								rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return xiaoMiShuDao.getWorldCount(authorId, maxId);
					}

				}, OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL,
				OptResult.JSON_KEY_MAX_ID);
	}
}
