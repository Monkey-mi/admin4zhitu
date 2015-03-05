package com.imzhitu.admin.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ZTWorldDto;

public interface ZHITUXiaoMiShuService extends BaseService{
	public void addZTWorld(ZTWorldDto dto) throws Exception;
	public void delZTWorldByIds(String idsStr)throws Exception;
	public List<ZTWorldDto> queryZTWorldByUId(Integer limit)throws Exception;
	public void saveZTWorld(Integer worldId)throws Exception;
	public void updateWorldDescByWid(Integer wid,String worldDesc)throws Exception;
	public void updateWorldLabelByWid(Integer wid,String worldLable)throws Exception;
	public void queryWorld(Integer maxId, int start, int limit, Integer worldId, Map<String, Object> jsonMap)throws Exception;
}
