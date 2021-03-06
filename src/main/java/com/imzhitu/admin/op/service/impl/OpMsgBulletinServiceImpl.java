package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.BulletinCacheDao;
import com.imzhitu.admin.addr.pojo.City;
import com.imzhitu.admin.addr.service.AddrService;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.op.dao.mongo.NearBulletinMongoDao;
import com.imzhitu.admin.op.mapper.OpMsgBulletinMapper;
import com.imzhitu.admin.op.mapper.OpMsgNearBulletinMapper;
import com.imzhitu.admin.op.pojo.NearBulletinCityDto;
import com.imzhitu.admin.op.pojo.NearBulletinDto;
import com.imzhitu.admin.op.service.OpMsgBulletinService;

@Service
public class OpMsgBulletinServiceImpl extends BaseServiceImpl implements OpMsgBulletinService {
	
	@Autowired
	private OpMsgBulletinMapper msgBulletinMapper;

	@Autowired
	private BulletinCacheDao bulletinCacheDao;

	@Autowired
	private KeyGenService keyGenService;

	@Autowired
	private NearBulletinMongoDao nearBulletinMongoDao;

	@Autowired
	private OpMsgNearBulletinMapper nearBulletinMapper;

	@Autowired
	private AddrService addrService;

	private Logger log = Logger.getLogger(OpMsgBulletinServiceImpl.class);

	@Override
	public void insertMsgBulletin(String path, Integer category, Integer type, String link, Integer operator, String bulletinName, String bulletinThumb) throws Exception {
		Integer serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
		OpMsgBulletin dto = new OpMsgBulletin();
		Date now = new Date();
		dto.setCategory(category);
		dto.setBulletinPath(path);
		dto.setBulletinType(type);
		dto.setLink(link);
		dto.setOperator(operator);
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
		if (ids.length > 0) {
			msgBulletinMapper.batchDeleteMsgBulletin(ids);
		}
	}

	@Override
	public void updateMsgBulletin(Integer id, String path, Integer category, Integer type, String link, Integer operator, String bulletinName, String bulletinThumb) throws Exception {
		OpMsgBulletin dto = new OpMsgBulletin();
		Date now = new Date();
		dto.setId(id);
		dto.setBulletinPath(path);
		dto.setBulletinType(type);
		dto.setLink(link);
		dto.setOperator(operator);
		dto.setModifyDate(now);
		dto.setBulletinName(bulletinName);
		dto.setBulletinThumb(bulletinThumb);
		msgBulletinMapper.updateMsgBulletin(dto);
	}

	@Override
	public void queryMsgBulletin(Integer id, Integer category, Integer type, Integer isCache, Integer maxId, int page, int rows, Map<String, Object> jsonMap) throws Exception {
		List<OpMsgBulletin> list = null;
		
		long total = 0;
		
		if (isCache != null && isCache == 0) {// 从缓存里拿数据
			List<com.hts.web.common.pojo.OpMsgBulletin> bulletinList = bulletinCacheDao.queryBulletin();
			if (bulletinList != null) {
				total = bulletinList.size();
			}
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
			jsonMap.put(OptResult.JSON_KEY_ROWS, bulletinList);
			return;
		}
		total = msgBulletinMapper.queryMsgBulletinTotalCount(category);

		if (total > 0) {
			list = msgBulletinMapper.queryMsgBulletin(category, rows * (page - 1), rows);
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public List<OpMsgBulletin> queryMsgBulletinByIds(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		return msgBulletinMapper.queryMsgBulletinByIds(ids);
	}
	
	@Override
	public void reorder(Integer[] ids, Integer operator) {
		// 按照勾选顺序倒序排序，因为serial越大越靠前
		for (int i = ids.length - 1 ; i > 0; i--) {
			// 更新serial
			Integer serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
			OpMsgBulletin bulletin = new OpMsgBulletin();
			bulletin.setModifyDate(new Date());
			bulletin.setSerial(serial);
			bulletin.setId(ids[i]);
			bulletin.setOperator(operator);
			msgBulletinMapper.updateMsgBulletin(bulletin);
		}
	}
	
	@Override
	public void refreshActivityThemeCache() {
		// 定义放入缓存集合
		List<OpMsgBulletin> bulletinList = new ArrayList<OpMsgBulletin>();
		
		// 刷新活动专题，包含两部分，有奖活动与无奖活动，排列按照有奖在前，无奖在后的顺序
		int awardActivity = 1;
		int activity = 2;
		List<OpMsgBulletin> awardActivityList = msgBulletinMapper.queryMsgBulletin(awardActivity, null, null);
		List<OpMsgBulletin> activityList = msgBulletinMapper.queryMsgBulletin(activity, null, null);
		bulletinList.addAll(awardActivityList);
		bulletinList.addAll(activityList);
		
		if (bulletinList.size() > 0) {
			// 转换为web端公告对象
			List<com.hts.web.common.pojo.OpMsgBulletin> webbulletinList = bulletinToWeb(bulletinList);
			bulletinCacheDao.updateActivityThemeBulletin(webbulletinList);
		}
	}
	
	@Override
	public void refreshUserThemeCache() {
		// 刷新达人专题，category为3
		List<OpMsgBulletin> userBulletinList = msgBulletinMapper.queryMsgBulletin(3, null, null);
		
		if ( userBulletinList != null && userBulletinList.size() > 0) {
			// 转换为web端公告对象
			List<com.hts.web.common.pojo.OpMsgBulletin> webbulletinList = bulletinToWeb(userBulletinList);
			bulletinCacheDao.updateUserThemeBulletin(webbulletinList);
		}
	}
	
	@Override
	public void refreshContentThemeCache() {
		// 刷新内容专题，category为4
		List<OpMsgBulletin> contentList = msgBulletinMapper.queryMsgBulletin(4, null, null);
		
		if ( contentList != null && contentList.size() > 0) {
			// 转换为web端公告对象
			List<com.hts.web.common.pojo.OpMsgBulletin> webbulletinList = bulletinToWeb(contentList);
			bulletinCacheDao.updateContentThemeBulletin(webbulletinList);
		}
	}
	
	/**
	 * 转换为web端公告对象
	 * 
	 * @param bulletinList
	 * @return
	 * @author zhangbo	2015年12月19日
	 */
	private List<com.hts.web.common.pojo.OpMsgBulletin> bulletinToWeb(List<OpMsgBulletin> bulletinList) {
		// 定义返回值
		List<com.hts.web.common.pojo.OpMsgBulletin> rtnList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
		for (OpMsgBulletin bulletin : bulletinList) {
			com.hts.web.common.pojo.OpMsgBulletin webBulletin = new com.hts.web.common.pojo.OpMsgBulletin();
			webBulletin.setId(bulletin.getId());
			webBulletin.setBulletinName(bulletin.getBulletinName());
			webBulletin.setBulletinPath(bulletin.getBulletinPath());
			webBulletin.setBulletinThumb(bulletin.getBulletinThumb());
			webBulletin.setBulletinType(bulletin.getBulletinType());
			webBulletin.setLink(bulletin.getLink());
		}
		return rtnList;
	}

	@Override
	public void updateMsgBulletinCacheOld(String idsStr, Integer operator) throws Exception {
		Date now = new Date();
		if (idsStr == null || idsStr.trim().equals("")) {
			bulletinCacheDao.updateBulletin(new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>());
			return;
		}
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		List<OpMsgBulletin> list = msgBulletinMapper.queryMsgBulletinByIds(ids);
		List<com.hts.web.common.pojo.OpMsgBulletin> webBulletinList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();

		for (int i = 0; i < ids.length; i++) {

			// 排序
			for (int j = 0; j < list.size(); j++) {
				if (ids[i].equals(list.get(j).getId())) {
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

			// 更新serial
			Integer serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
			OpMsgBulletin bulletin = new OpMsgBulletin();
			bulletin.setModifyDate(now);
			bulletin.setSerial(serial);
			bulletin.setId(ids[ids.length - i - 1]);
			bulletin.setOperator(operator);
			msgBulletinMapper.updateMsgBulletin(bulletin);
		}

		if (webBulletinList.size() > 0) {
			bulletinCacheDao.updateBulletin(webBulletinList);
		}

		// 更新 用户推荐列表缓存com.hts.web.base.constant.CacheKeies.OP_MSG_USER_THEME 对应的type为1
		try {
			OpMsgBulletin tmpBulletin = new OpMsgBulletin();
			List<com.hts.web.common.pojo.OpMsgBulletin> webUserThemeList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
			tmpBulletin.setBulletinType(1);
			List<OpMsgBulletin> userThemeList = msgBulletinMapper.queryMsgBulletinByType(tmpBulletin);
			if (userThemeList != null && userThemeList.size() > 0) {
				for (OpMsgBulletin dto : userThemeList) {
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

			if (webUserThemeList.size() > 0) {
				bulletinCacheDao.updateUserThemeBulletin(webUserThemeList);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

		// 更新频道推荐缓存com.hts.web.base.constant.CacheKeies.OP_MSG_CHANNEL_THEME 对应的type为2
		try {
			OpMsgBulletin tmpChannelBulletin = new OpMsgBulletin();
			List<com.hts.web.common.pojo.OpMsgBulletin> webChannelThemeList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
			tmpChannelBulletin.setBulletinType(2);
			List<OpMsgBulletin> channelThemeList = msgBulletinMapper.queryMsgBulletinByType(tmpChannelBulletin);
			if (channelThemeList != null && channelThemeList.size() > 0) {
				for (OpMsgBulletin dto : channelThemeList) {
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

			if (webChannelThemeList.size() > 0) {
				bulletinCacheDao.updateChannelThemeBulletin(webChannelThemeList);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

		// 缓存活动/专题推荐列表 com.hts.web.base.constant.CacheKeies.OP_MSG_THEME 对应的type为4
		try {
			OpMsgBulletin tmpBulletin = new OpMsgBulletin();
			List<com.hts.web.common.pojo.OpMsgBulletin> webThemeList = new ArrayList<com.hts.web.common.pojo.OpMsgBulletin>();
			tmpBulletin.setBulletinType(4);
			List<OpMsgBulletin> themeList = msgBulletinMapper.queryMsgBulletinByType(tmpBulletin);
			if (themeList != null && themeList.size() > 0) {
				for (OpMsgBulletin dto : themeList) {
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
			if (webThemeList.size() > 0) {
				bulletinCacheDao.updateThemeBulletin(webThemeList);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}

	@Override
	public void saveNearBulletin(NearBulletinDto dto) throws Exception {
		Integer bulletinId = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_ID);

		// 向选中的城市插入公告信息
		List<Integer> cityIds = dto.getCityIds();
		if (cityIds != null && !cityIds.isEmpty()) {
			NearBulletinDto cityBulletin = new NearBulletinDto();
			BeanUtils.copyProperties(dto, cityBulletin);
			cityBulletin.setBulletinId(bulletinId);
			for (int i = cityIds.size() - 1; i >= 0; i--) {
				cityBulletin.setCityId(cityIds.get(i));
				saveCityBulletin(cityBulletin);
			}
		}

		// 保存公告信息到数据库
		Integer serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
		dto.setSerial(serial);
		dto.setId(bulletinId);

		nearBulletinMapper.save(dto);
	}

	/**
	 * 向指定城市保存公告
	 * 
	 * @param bulletin
	 */
	private void saveCityBulletin(NearBulletinDto bulletin) {
		Integer serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_NEAR_BULLETIN_SERIAL);
		Integer id = keyGenService.generateId(Admin.KEYGEN_OP_MSG_NEAR_BULLETIN_ID);
		City city = addrService.queryCityById(bulletin.getCityId());

		bulletin.setSerial(serial);
		bulletin.setId(id);
		bulletin.setLoc(new Double[] { city.getLongitude(), city.getLatitude() });

		nearBulletinMongoDao.save(bulletin);
	}

	@Override
	public void updateNearBulletin(NearBulletinDto dto) throws Exception {

		List<Integer> cityIds = dto.getCityIds();

		if (dto.getCityIds() != null && !cityIds.isEmpty()) {
			nearBulletinMongoDao.delByBulletinId(dto.getId());
			NearBulletinDto cityBulletin = new NearBulletinDto();
			BeanUtils.copyProperties(dto, cityBulletin);
			cityBulletin.setBulletinId(dto.getId());
			for (int i = cityIds.size() - 1; i >= 0; i--) {
				cityBulletin.setCityId(cityIds.get(i));
				saveCityBulletin(cityBulletin);
			}
		}

		nearBulletinMapper.update(dto);
	}

	@Override
	public void delCityBulletinByIds(Integer[] ids) throws Exception {
		nearBulletinMongoDao.delByIds(ids);
	}

	@Override
	public void buildNearBulletin(NearBulletinDto bulletin, int start, int limit, Map<String, Object> jsonMap) throws Exception {

		buildNumberDtos("getSerial", bulletin, start, limit, jsonMap, new NumberDtoListAdapter<NearBulletinDto>() {

			@Override
			public List<NearBulletinDto> queryList(NearBulletinDto dto) {
				List<NearBulletinDto> list = null;

				if (dto.getCityId() != null && !dto.getCityId().equals(0))
					list = nearBulletinMongoDao.queryBulletin(dto);
				else {
					list = nearBulletinMapper.queryList(dto);
					extractCity(list);
				}
				return list;
			}

			@Override
			public long queryTotal(NearBulletinDto dto) {
				long total = 0l;

				if (dto.getCityId() != null && !dto.getCityId().equals(0))
					total = nearBulletinMongoDao.queryCount(dto);
				else
					total = nearBulletinMapper.queryCount(dto);

				return total;
			}

		});
	}

	/**
	 * 获取城市信息
	 * 
	 * @param dtoList
	 * @author lynch 2015-12-15
	 */
	private void extractCity(List<NearBulletinDto> dtoList) {
		Integer[] bulletinIds = new Integer[dtoList.size()];
		final Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();

		for (int i = 0; i < dtoList.size(); i++) {
			bulletinIds[i] = dtoList.get(i).getId();
			idxMap.put(dtoList.get(i).getId(), i);

		}
		List<NearBulletinCityDto> cityList = nearBulletinMongoDao.queryCityByBulletinIds(bulletinIds);
		if (!cityList.isEmpty()) {
			extractCityName(cityList);

			for (NearBulletinCityDto city : cityList) {
				Integer bulletinId = city.getBulletinId();
				Integer idx = idxMap.get(bulletinId);
				dtoList.get(idx).getCityIds().add(city.getCityId());
				dtoList.get(idx).getCities().add(city.getCityName());
			}
		}

	}

	/**
	 * 获取城市名称
	 * 
	 * @param cityList
	 * @author lynch 2015-12-15
	 */
	private void extractCityName(List<NearBulletinCityDto> cityList) {
		Map<Integer, List<Integer>> cityIdxMap = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < cityList.size(); i++) {
			Integer cityId = cityList.get(i).getCityId();
			if (cityIdxMap.containsKey(cityId))
				cityIdxMap.get(cityId).add(i);
			else {
				List<Integer> l = new ArrayList<Integer>();
				l.add(i);
				cityIdxMap.put(cityId, l);
			}
		}
		Integer[] ids = new Integer[cityIdxMap.size()];
		cityIdxMap.keySet().toArray(ids);
		List<City> list = addrService.queryCityByIds(ids);
		for (City city : list) {
			for (Integer idx : cityIdxMap.get(city.getId())) {
				cityList.get(idx).setCityName(city.getName());
			}
		}
	}

	@Override
	public void delNearBulletinById(Integer id) throws Exception {
		nearBulletinMapper.delById(id);
		nearBulletinMongoDao.delByBulletinId(id);
	}

	@Override
	public NearBulletinDto queryNearBulletinById(Integer id) throws Exception {
		NearBulletinDto dto = nearBulletinMapper.queryById(id);

		List<NearBulletinCityDto> cityList = nearBulletinMongoDao.queryCityByBulletinIds(new Integer[] { id });
		if (!cityList.isEmpty()) {
			extractCityName(cityList);

			for (NearBulletinCityDto city : cityList) {
				dto.getCities().add(city.getCityName());
				dto.getCityIds().add(city.getCityId());
			}
		}

		return dto;
	}

	@Override
	public void updateCityBulletinSerial(String[] idStrs) throws Exception {
		Integer serial;
		for (int i = idStrs.length - 1; i >= 0; i--) {
			if (!StringUtil.checkIsNULL(idStrs[i])) {
				serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_NEAR_BULLETIN_SERIAL);
				nearBulletinMongoDao.updateSerial(Integer.parseInt(idStrs[i]), serial);
			}
		}
	}


}
