package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpAdAppLink;
import com.imzhitu.admin.common.pojo.OpAdAppLinkRecord;
import com.imzhitu.admin.op.mapper.AdAppLinkMapper;
import com.imzhitu.admin.op.mapper.AdAppLinkRecordMapper;
import com.imzhitu.admin.op.service.OpAdService;

@Service
public class OpAdServiceImpl extends BaseServiceImpl implements OpAdService {

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private AdAppLinkRecordMapper recordMapper;

	@Autowired
	private AdAppLinkMapper linkMapper;

	@Override
	public void saveAppLink(OpAdAppLink link)
			throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_AD_APP_ID);
		String shortLink = MD5Encrypt.shortUrl(Long.valueOf(id));
		link.setId(id);
		link.setShortLink(shortLink);
		link.setSerial(id);
		linkMapper.save(link);
	}
	
	@Override
	public void updateAppLink(OpAdAppLink link) {
		linkMapper.update(link);
	}
	
	@Override
	public void updateAppLinkSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				int serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_AD_APP_ID);
				linkMapper.updateSerialById(id, serial);
			}
		}
	}
	
	@Override
	public void buildAppLink(final OpAdAppLink link, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos("getSerial", link, start, limit, jsonMap, new NumberDtoListAdapter<OpAdAppLink>() {

			@Override
			public List<? extends Serializable> queryList(OpAdAppLink dto) {
				return linkMapper.queryLink(link);
			}

			@Override
			public long queryTotal(OpAdAppLink dto) {
				return linkMapper.queryLinkCount(link);
			}
		});
	}
	
	@Override
	public void deleteAppLink(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		linkMapper.deleteByIds(ids);
		for(Integer appId : ids) {
			recordMapper.deleteByAppId(appId);
		}
	}

	@Override
	public void buildAppLinkById(Integer id, Map<String, Object> jsonMap) throws Exception {
		OpAdAppLink link = linkMapper.queryLinkById(id);
		jsonMap.put(OptResult.JSON_KEY_OBJ, link);
	}

	@Override
	public void updateAppLinkValid(String idsStr, Integer valid) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		linkMapper.updateValidByIds(ids, valid);
	}
	
	@Override
	public void buildAppLinkRecord(OpAdAppLinkRecord record, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		
		buildNumberDtos(record, start, limit, jsonMap, new NumberDtoListAdapter<OpAdAppLinkRecord>() {

			@Override
			public List<? extends Serializable> queryList(OpAdAppLinkRecord dto) {
				return recordMapper.queryRecord(dto);
			}

			@Override
			public long queryTotal(OpAdAppLinkRecord dto) {
				return recordMapper.queryRecordCount(dto);
			}
		});
	}
	
}
