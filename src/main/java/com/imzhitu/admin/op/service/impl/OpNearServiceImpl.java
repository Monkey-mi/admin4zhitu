package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldCount;
import com.hts.web.common.pojo.OpNearWorldDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.service.NearService;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.imzhitu.admin.addr.pojo.City;
import com.imzhitu.admin.addr.service.AddrService;
import com.imzhitu.admin.common.pojo.OpNearCityGroupDto;
import com.imzhitu.admin.common.pojo.OpNearLabelDto;
import com.imzhitu.admin.common.pojo.OpNearLabelWorldDto;
import com.imzhitu.admin.common.pojo.OpNearRecommendCityDto;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.op.dao.mongo.NearLabelMongoDao;
import com.imzhitu.admin.op.dao.mongo.NearWorldLastMongoDao;
import com.imzhitu.admin.op.dao.mongo.NearWorldMongoDao;
import com.imzhitu.admin.op.mapper.OpNearCityGroupMapper;
import com.imzhitu.admin.op.mapper.OpNearLabelMapper;
import com.imzhitu.admin.op.mapper.OpNearLabelWorldMapper;
import com.imzhitu.admin.op.mapper.OpNearRecommendCityMapper;
import com.imzhitu.admin.op.service.OpNearService;
import com.imzhitu.admin.userinfo.service.UserInfoService;

@Service
public class OpNearServiceImpl extends BaseServiceImpl implements OpNearService{

	@Autowired
	private OpNearLabelMapper nearLabelMapper;
	
	@Autowired
	private OpNearCityGroupMapper nearCityGroupMapper;
	
	@Autowired
	private OpNearRecommendCityMapper nearRecommendCityMapper;
	
	@Autowired
	private OpNearLabelWorldMapper nearLabelWorldMapper;
	
	@Autowired
	private NearLabelMongoDao nearLabelMongoDao;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private AddrService addrService;
	
	@Autowired
	private NearService nearService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private NearWorldMongoDao nearWorldMongoDao;
	
	@Autowired
	private NearWorldLastMongoDao nearWorldLastMongoDao;
	
	private List<OpNearLabelWorldDto> addUserInfo(List<OpNearLabelWorldDto> list) throws Exception{
		for(int i = 0; i < list.size(); i++){
			UserInfo userInfo = userInfoService.getUserInfo(list.get(i).getAuthorId());
			list.get(i).setAuthorAvatar(userInfo.getUserAvatarL());
			list.get(i).setAuthorName(userInfo.getUserName());
		}
		return list;
	}
	
	@Override
	public void queryNearLabel(OpNearLabelDto label, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception {
		if(label.getLabelName() != null) {
			label.setLabelName("%" + label.getLabelName() + "%");
		}
		buildNumberDtos("getSerial", label, start, limit, jsonMap, 
				new NumberDtoListAdapter<OpNearLabelDto>() {

			@Override
			public List<? extends Serializable> queryList(OpNearLabelDto dto) {
				return nearLabelMapper.queryNearLabel(dto);
			}

			@Override
			public long queryTotal(OpNearLabelDto dto) {
				return nearLabelMapper.queryNearLabelTotalCount(dto);
			}
		});
	}

	@Override
	public void updateNearLabel(OpNearLabelDto label) throws Exception {
		if(label.getCityId() != null) {
			City city = addrService.queryCityById(label.getCityId());
			label.setLongitude(city.getLongitude());
			label.setLatitude(city.getLatitude());
		}
		com.hts.web.common.pojo.OpNearLabelDto webLabel = new com.hts.web.common.pojo.OpNearLabelDto();
		BeanUtils.copyProperties(webLabel, label);
		if(label.getLongitude() != null && label.getLatitude() != null) {
			webLabel.setLoc(new Double[]{label.getLongitude(), label.getLatitude()});
		}
		nearLabelMongoDao.updateLabel(webLabel);
		nearLabelMapper.updateNearLabel(label);
	}

	@Override
	public void insertNearLabel(OpNearLabelDto label) throws Exception {
		if(label.getCityId() == null)
			throw new IllegalArgumentException("please set city id");
		
		City city = addrService.queryCityById(label.getCityId());
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_ID);
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_SERIAL);
		
		label.setLongitude(city.getLongitude());
		label.setLatitude(city.getLatitude());
		label.setId(id);
		label.setSerial(serial);

		com.hts.web.common.pojo.OpNearLabelDto webLabel = new com.hts.web.common.pojo.OpNearLabelDto();
		BeanUtils.copyProperties(webLabel, label);
		if(label.getLongitude() != null || label.getLatitude() != null) {
			webLabel.setLoc(new Double[]{label.getLongitude(), label.getLatitude()});
		}
		nearLabelMongoDao.saveLabel(webLabel);
		nearLabelMapper.insertNearLabel(label);
	}

	@Override
	public void batchDeleteNearLabel(String idsStr) throws Exception {
		Integer[] idsArray = StringUtil.convertStringToIds(idsStr);
		nearLabelMongoDao.deleteByIds(idsArray);
		nearLabelMapper.batchDeleteNearLabel(idsArray);
	}
	
	@Override
	public OpNearLabelDto queryNearLabelById(Integer id) throws Exception {
		return nearLabelMapper.queryNearLabelById(id);
	}

	@Override
	public void updateNearLabelSearial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_SERIAL);
			nearLabelMongoDao.updateSerial(id, serial);
			nearLabelMapper.updateSerial(id, serial);
		}
	}

	@Override
	public void insertNearCityGroup(String description) throws Exception {
		OpNearCityGroupDto dto = new OpNearCityGroupDto();
		dto.setDescription(description);
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_CITY_GROUP_SERIAL);
		dto.setSerial(serial);
		nearCityGroupMapper.insertNearCityGroup(dto);
	}

	@Override
	public void batchDeleteNearCityGroup(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		nearCityGroupMapper.batchDeleteNearCityGroup(ids);
	}

	@Override
	public void queryNearCityGroup(Integer id, int maxSerial, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		OpNearCityGroupDto dto = new OpNearCityGroupDto();
		dto.setFirstRow(limit * (start - 1));
		dto.setLimit(limit);
		dto.setId(id);
		dto.setMaxId(maxSerial);
		long total = nearCityGroupMapper.queryNearCityGroupTotalCount(dto);
		if(total > 0){
			List<OpNearCityGroupDto> list  = nearCityGroupMapper.queryNearCityGroup(dto);
			jsonMap.put(OptResult.ROWS, list);
			jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, list.get(0).getSerial());
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		}
	}
	
	/**
	 * 获取城市分组
	 * 
	 * @return
	 * @author zhangbo	2015年12月5日
	 */
	public List<Map<String,Serializable>> getCityGroup() {
		List<Map<String,Serializable>> rtnList = new ArrayList<Map<String,Serializable>>();
		for (OpNearCityGroupDto cg : nearCityGroupMapper.queryNearCityGroup(null)) {
			Map<String,Serializable> map = new HashMap<String, Serializable>();
			map.put("id", cg.getId());
			map.put("description", cg.getDescription());
			rtnList.add(map);
		}
		
		return rtnList;
	}
	
	@Override
	public void insertNearRecommendCity(Integer cityId, Integer cityGroupId)
			throws Exception {
		OpNearRecommendCityDto dto = new OpNearRecommendCityDto();
		dto.setCityGroupId(cityGroupId);
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_RECOMMEND_CITY_SERIAL);
		dto.setSerial(serial);
		dto.setCityId(cityId);
		
		nearRecommendCityMapper.insertNearRecommendCity(dto);
	}

	@Override
	public void batchDeleteNearRecommendCity(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		nearRecommendCityMapper.batchDeleteNearRecommendCity(ids);
	}

	@Override
	public void queryNearRecommendCity(Integer id, Integer cityId,
			Integer cityGroupId, int maxSerial, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		OpNearRecommendCityDto dto = new OpNearRecommendCityDto();
		dto.setFirstRow(limit * (start - 1));
		dto.setLimit(limit);
		dto.setId(id);
		dto.setCityId(cityId);
		dto.setCityGroupId(cityGroupId);
		dto.setMaxId(maxSerial);
		long total = nearRecommendCityMapper.queryNearRecommendCityTotalCount(dto);
		if(total > 0){
			List<OpNearRecommendCityDto> list  = nearRecommendCityMapper.queryNearRecommendCity(dto);
			jsonMap.put(OptResult.ROWS, list);
			jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, list.get(0).getSerial());
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		}
	}

	/**
	 * 添加织图标签
	 */
	@Override
	public void insertNearLabelWorld(Integer worldId, Integer worldAuthorId,
			Integer nearLabelId) throws Exception {
		nearService.deleteNearLabelWorldUserByWorldIdAndLabelId(worldId, nearLabelId);
		
		OpNearLabelWorldDto dto = new OpNearLabelWorldDto();
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_WORLD_SERIAL);
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_WORLD_ID);
		dto.setNearLabelId(nearLabelId);
		dto.setId(id);
		dto.setSerial(serial);
		dto.setWorldId(worldId);
		dto.setAuthorId(worldAuthorId);
		nearLabelWorldMapper.insertNearLabelWorld(dto);
	}

	/**
	 * 批量添加织图标签
	 */
	@Override
	public void insertNearLabelWorlds(Integer worldId, Integer worldAuthorId,
			String  nearLabelIds) throws Exception {
			String[]  nearLabelIdsForInt = nearLabelIds.split(","); 
			for(int i = 0; i < nearLabelIdsForInt.length; i++){
				int labelId = Integer.parseInt(nearLabelIdsForInt[i]);
				insertNearLabelWorld(worldId,worldAuthorId,labelId);
			}
	}
	
	@Override
	public void batchDeleteNearLabelWorld(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		nearLabelWorldMapper.batchDeleteNearLabelWorld(ids);
	}

	@Override
	public void queryNearLabelWorld(Integer id, Integer worldId,
			Integer nearLabelId, int maxSerial, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		OpNearLabelWorldDto dto = new OpNearLabelWorldDto();
		dto.setFirstRow(limit * (start - 1));
		dto.setLimit(limit);
		dto.setId(id);
		dto.setMaxId(maxSerial);
		dto.setWorldId(worldId);
		dto.setNearLabelId(nearLabelId);
		long total = nearLabelWorldMapper.queryNearLabelWorldTotalCount(dto);
		List<OpNearLabelWorldDto> list  = null;
		if(total > 0){
			list  = nearLabelWorldMapper.queryNearLabelWorld(dto);
			jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, list.get(0).getSerial());
			//增加未完善的用户信息
			 list = addUserInfo(list);
		}
			jsonMap.put(OptResult.ROWS, list);
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
	}

	/**
	 * 重新排序
	 * 
	 * @param ids
	 * @throws Exception 
		*	2015年12月5日
		*	mishengliang
	 */
	@Override
	public  void updateNearLabelWorldSerial(String[] idStrs) {
		for (int i = idStrs.length - 1; i >= 0; i--) {
			if (StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_LABEL_WORLD_SERIAL);
			OpNearLabelWorldDto dto = new OpNearLabelWorldDto();
			dto.setId(id);
			dto.setSerial(serial);
			nearLabelWorldMapper.updateNearLabelWorldSerial(dto);
		}
	}

	@Override
	public void updateNearRecommendCityCache() throws Exception {
		nearService.updateRecommendCityCache();
	}

	@Override
	public void queryNearRecommendCityCache(Map<String,Object>jsonMap) throws Exception {
		nearService.buildRecommendCity(jsonMap);
	}

	@Override
	public void reIndexNearCityGroup(String[] idStrs) throws Exception {
		
		for (int i = idStrs.length - 1; i >= 0; i--) {
			if (StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_CITY_GROUP_SERIAL);
			OpNearCityGroupDto dto = new OpNearCityGroupDto();
			dto.setId(id);
			dto.setSerial(serial);
			nearCityGroupMapper.updateNearCityGroupSerial(dto);
		}
	}

	@Override
	public void reIndexNearRecommendCity(String[] idStrs) throws Exception {
		for (int i = idStrs.length - 1; i >= 0; i--) {
			if (StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_NEAR_RECOMMEND_CITY_SERIAL);
			OpNearRecommendCityDto dto = new OpNearRecommendCityDto();
			dto.setId(id);
			dto.setSerial(serial);
			nearRecommendCityMapper.updateNearRecommendCitySerial(dto);
		}
	}

	@Override
	public void queryNearWorld(Integer cityId, int maxId,int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		long total = nearWorldMongoDao.queryNearTotalCount(cityId);
		if( total > 0 ){
			final List<OpNearWorldDto> list = nearWorldMongoDao.queryNear(maxId, cityId, start, limit);
			if(list != null && !(list.isEmpty())){
				Integer[] ids = new Integer[list.size()];
				final Map<Integer,List<Integer>> indexMap = new HashMap<Integer, List<Integer>>();
				final Map<Integer, Integer> idIndexMap = new HashMap<Integer, Integer>();
				for (int i = 0; i < list.size(); i++) {
					ids[i] = list.get(i).getId();
					idIndexMap.put(list.get(i).getId(), i);
					Integer auid = list.get(i).getAuthorId();
					if(indexMap.containsKey(auid)) {
						indexMap.get(auid).add(i);
					} else {
						List<Integer> l = new ArrayList<Integer>();
						l.add(i);
						indexMap.put(auid, l);
					}
				}
				Integer[] uids = new Integer[indexMap.size()];
				indexMap.keySet().toArray(uids);
				
				userInfoDao.queryUserInfoDtos(uids, new RowCallback<UserInfoDto>() {
					
					@Override
					public void callback(UserInfoDto t) {
						Integer uid = t.getId();
						for(Integer i : indexMap.get(uid)) {
							list.get(i).setUserInfo(t);
						}
					}
				});
				
				worldDao.queryCount(ids, new RowCallback<HTWorldCount>() {
					@Override
					public void callback(HTWorldCount t) {
						Integer idx = idIndexMap.get(t.getId());
						list.get(idx).setClickCount(t.getClickCount());
						list.get(idx).setLikeCount(t.getLikeCount());
						list.get(idx).setCommentCount(t.getCommentCount());
					}
				});
				
				jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, list.get(0).getRecommendId());
				jsonMap.put(OptResult.ROWS, list);
				jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
			}
		}
	}

	@Override
	public void batchDeleteNearWorld(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id:ids){
			nearService.deleteNearWorld(id);
		}
	}

	@Override
	public void queryNearWorldLast(Integer cityId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		long total = nearWorldLastMongoDao.queryNearTotalCount(cityId);
		if( total > 0 ){
			final List<OpNearWorldDto> list = nearWorldLastMongoDao.queryNear(maxId, cityId, start, limit);
			if(list != null && !(list.isEmpty())){
				Integer[] ids = new Integer[list.size()];
				final Map<Integer,List<Integer>> indexMap = new HashMap<Integer, List<Integer>>();
				final Map<Integer, Integer> idIndexMap = new HashMap<Integer, Integer>();
				for (int i = 0; i < list.size(); i++) {
					ids[i] = list.get(i).getId();
					idIndexMap.put(list.get(i).getId(), i);
					Integer auid = list.get(i).getAuthorId();
					if(indexMap.containsKey(auid)) {
						indexMap.get(auid).add(i);
					} else {
						List<Integer> l = new ArrayList<Integer>();
						l.add(i);
						indexMap.put(auid, l);
					}
				}
				Integer[] uids = new Integer[indexMap.size()];
				indexMap.keySet().toArray(uids);
				
				userInfoDao.queryUserInfoDtos(uids, new RowCallback<UserInfoDto>() {
					
					@Override
					public void callback(UserInfoDto t) {
						Integer uid = t.getId();
						for(Integer i : indexMap.get(uid)) {
							list.get(i).setUserInfo(t);
						}
					}
				});
				
				worldDao.queryCount(ids, new RowCallback<HTWorldCount>() {
					@Override
					public void callback(HTWorldCount t) {
						Integer idx = idIndexMap.get(t.getId());
						list.get(idx).setClickCount(t.getClickCount());
						list.get(idx).setLikeCount(t.getLikeCount());
						list.get(idx).setCommentCount(t.getCommentCount());
					}
				});
				
				jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, list.get(0).getRecommendId());
				jsonMap.put(OptResult.ROWS, list);
				jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
			}
		}
	}

	@Override
	public void insertNearWorldLast(Integer worldId) throws Exception {
		if(worldId == null){
			throw new NullPointerException("worldId is null");
		}
		HTWorld world = worldDao.queryWorldById(worldId);
		nearService.saveNearWorldLast(world);
	}
	
	@Override
	public void insertCommonNearWorld(Integer worldId) throws Exception {
		if(worldId == null){
			throw new NullPointerException("worldId is null");
		}
		HTWorld world = worldDao.queryWorldById(worldId);
		world.setLongitude(1.0);
		world.setLatitude(90.0);
		world.setCity("通用");
		nearService.saveNearWorld(world);
		nearWorldLastMongoDao.deleteNearWorldLast(world.getId());
	}

	@Override
	public void batchDeleteNearWorldLast(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id:ids){
			nearWorldLastMongoDao.deleteNearWorldLast(id);
		}
	}

	@Override
	public void batchInsertNearWorldFromLast(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id:ids){
			OpNearWorldDto dto = nearWorldLastMongoDao.queryNearWorldLastById(id);
			Integer valid = worldDao.queryValid(id);
			if(valid.equals(0)){
				
			}else{
				nearWorldMongoDao.saveWorld(dto);
			}
			nearWorldLastMongoDao.deleteNearWorldLast(id);
		}
		
	}
	
}
