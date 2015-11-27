package com.imzhitu.admin.ztworld.service;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ZTWorldDto;

public interface ZHITUXiaoMiShuService extends BaseService{
	public void delZTWorldByIds(String idsStr)throws Exception;
	public void updateWorldDescByWid(Integer wid,String worldDesc)throws Exception;
	public void updateWorldLabelByWid(Integer wid,String worldLable)throws Exception;
}
