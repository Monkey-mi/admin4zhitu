package com.imzhitu.admin.ztworld.service;

import java.io.File;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ZTWorldSubtitle;

public interface SubtitleService extends BaseService {
	
	public void updateSubtitleCache(Integer limit) throws Exception;
	
	public void saveSubtitleByFile(File file, String transTo) throws Exception;

	public void buildTitles(ZTWorldSubtitle sticker, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	public void saveTitle(ZTWorldSubtitle title) throws Exception;
	
	public ZTWorldSubtitle queryTitleById(Integer id) throws Exception;
	
	public void deleteTitleByIds(String idsStr) throws Exception;
	
	public void updateTitle(ZTWorldSubtitle title) throws Exception;

	public void updateTitleSerial(String[] ids) throws Exception;
	
}
