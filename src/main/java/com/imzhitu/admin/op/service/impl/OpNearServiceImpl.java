package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpNearCityGroupDto;
import com.imzhitu.admin.common.pojo.OpNearLabelDto;
import com.imzhitu.admin.common.pojo.OpNearLabelWorldDto;
import com.imzhitu.admin.common.pojo.OpNearRecommendCityDto;
import com.imzhitu.admin.op.dao.mongo.NearLabelMongoDao;
import com.imzhitu.admin.op.mapper.OpNearCityGroupMapper;
import com.imzhitu.admin.op.mapper.OpNearLabelMapper;
import com.imzhitu.admin.op.mapper.OpNearLabelWorldMapper;
import com.imzhitu.admin.op.mapper.OpNearRecommendCityMapper;
import com.imzhitu.admin.op.service.OpNearService;

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
	
	@Override
	public void queryNearLabel(Integer id, Integer cityId, int maxSerial,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		OpNearLabelDto dto = new OpNearLabelDto();
		dto.setId(id);
		dto.setCityId(cityId);
		dto.setMaxId(maxSerial);
		dto.setFirstRow(limit * (start-1 ));
		dto.setLimit(limit);
		
		long total = nearLabelMapper.queryNearLabelTotalCount(dto);
		if(total > 0){
			List<OpNearLabelDto> list = nearLabelMapper.queryNearLabel(dto);
			jsonMap.put(OptResult.ROWS, list);
			jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, list.get(0).getSerial());
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		}
		
	}

	@Override
	public void updateNearLabel(Integer id, Integer cityId, String labelName,
			Double longitude, Double latitude, String description,
			String bannerUrl, Integer serial) throws Exception {
		OpNearLabelDto dto = new OpNearLabelDto();
		dto.setId(id);
		dto.setCityId(cityId);
		dto.setLabelName(labelName);
		dto.setLongitude(longitude);
		dto.setLatitude(latitude);
		dto.setDescription(description);
		dto.setBannerUrl(bannerUrl);
		dto.setSerial(serial);
		nearLabelMapper.updateNearLabel(dto);
		
		com.hts.web.common.pojo.OpNearLabelDto webNearLabel = new com.hts.web.common.pojo.OpNearLabelDto();
		webNearLabel.setBannerUrl(bannerUrl);
		webNearLabel.setDescription(description);
		webNearLabel.setId(id);
		webNearLabel.setLabelName(labelName);
		webNearLabel.setSerial(serial);
		if(longitude != null && latitude != null){
			Double[] loc = new Double[2];
			loc[0] = longitude;
			loc[1] = latitude;
			webNearLabel.setLoc(loc);
		}
		nearLabelMongoDao.updateLabel(webNearLabel);
	}

	@Override
	public void insertNearLabel(Integer id, Integer cityId, String labelName,
			Double longitude, Double latitude, String description,
			String bannerUrl, Integer serial) throws Exception {
		OpNearLabelDto dto = new OpNearLabelDto();
		dto.setId(id);
		dto.setCityId(cityId);
		dto.setLabelName(labelName);
		dto.setLongitude(longitude);
		dto.setLatitude(latitude);
		dto.setDescription(description);
		dto.setBannerUrl(bannerUrl);
		
		long total = nearLabelMapper.queryNearLabelTotalCount(dto);
		if(total > 0){
			Integer maxSerial = nearLabelMapper.selectMaxSerialByCityId(cityId);
			dto.setSerial(maxSerial + 1);
		}else{
			dto.setSerial(1);
		}
		nearLabelMapper.insertNearLabel(dto);
		
		com.hts.web.common.pojo.OpNearLabelDto webNearLabel = new com.hts.web.common.pojo.OpNearLabelDto();
		webNearLabel.setBannerUrl(bannerUrl);
		webNearLabel.setDescription(description);
		webNearLabel.setId(id);
		webNearLabel.setLabelName(labelName);
		webNearLabel.setSerial(serial);
		Double[] loc = new Double[2];
		loc[0] = longitude;
		loc[1] = latitude;
		webNearLabel.setLoc(loc);
		nearLabelMongoDao.saveLabel(webNearLabel);
	}

	@Override
	public void batchDeleteNearLabel(String idsStr) throws Exception {
		Integer[] idsArray = StringUtil.convertStringToIds(idsStr);
		nearLabelMapper.batchDeleteNearLabel(idsArray);
		nearLabelMongoDao.deleteByIds(idsArray);
	}

	@Override
	public void insertNearCityGroup(String description) throws Exception {
		OpNearCityGroupDto dto = new OpNearCityGroupDto();
		dto.setDescription(description);
		long total = nearCityGroupMapper.queryNearCityGroupTotalCount(dto);
		if(total == 0){
			Integer maxSerial = nearCityGroupMapper.queryNearCityGroupMaxSerial();
			dto.setSerial(maxSerial + 1);
		}else{
			dto.setSerial(1);
		}
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

	@Override
	public void insertNearRecommendCity(Integer cityId, Integer cityGroupId)
			throws Exception {
		OpNearRecommendCityDto dto = new OpNearRecommendCityDto();
		dto.setCityGroupId(cityGroupId);
		long total = nearRecommendCityMapper.queryNearRecommendCityTotalCount(dto);
		if(total > 0){
			Integer maxSerial = nearRecommendCityMapper.queryNearRecommendCityMaxSerialByCityGroupId(cityGroupId);
			dto.setSerial(maxSerial + 1);
		}else{
			dto.setSerial(1);
		}
		
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

	@Override
	public void insertNearLabelWorld(Integer worldId, Integer worldAuthorId,
			Integer nearLabelId) throws Exception {
		OpNearLabelWorldDto dto = new OpNearLabelWorldDto();
		dto.setNearLabelId(nearLabelId);
		long total = nearLabelWorldMapper.queryNearLabelWorldTotalCount(dto);
		if(total > 0){
			Integer maxSerial = nearLabelWorldMapper.queryNearLabelWorldMaxSerialByNearLabelId(nearLabelId);
			dto.setSerial(maxSerial + 1);
		}else{
			dto.setSerial(1);
		}
		dto.setWorldId(worldId);
		dto.setAuthorId(worldAuthorId);
		nearLabelWorldMapper.insertNearLabelWorld(dto);
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
		if(total > 0){
			List<OpNearLabelWorldDto> list  = nearLabelWorldMapper.queryNearLabelWorld(dto);
			jsonMap.put(OptResult.ROWS, list);
			jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, list.get(0).getSerial());
			jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		}
	}

}
