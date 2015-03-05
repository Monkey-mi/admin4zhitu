package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.OpAdAppLink;
import com.hts.web.common.pojo.OpAdAppLinkRecord;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.op.dao.AppLinkDao;
import com.imzhitu.admin.op.dao.AppLinkRecordDao;
import com.imzhitu.admin.op.service.OpAdService;

@Service
public class OpAdServiceImpl extends BaseServiceImpl implements OpAdService {

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private AppLinkDao appLinkDao;
	
	@Autowired
	private AppLinkRecordDao appLinkRecordDao;

	@Override
	public void saveAppLink(String appName, String appIcon, String appDesc, String appLink, Integer phoneCode, Integer open)
			throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_AD_APP_ID);
		String shortLink = MD5Encrypt.shortUrl(Long.valueOf(id));
		appLinkDao.saveAppLink(new OpAdAppLink(id, appName, appIcon, appDesc, appLink, shortLink, phoneCode, 0, id, open));
	}
	
	@Override
	public void updateAppLinkSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				int serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_AD_APP_ID);
				appLinkDao.updateSerial(id, serial);
			}
		}
	}
	
	@Override
	public void buildAppLinkRecord(final Integer appId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<OpAdAppLinkRecord>() {

			@Override
			public List<OpAdAppLinkRecord> getSerializables(
					RowSelection rowSelection) {
				return appLinkRecordDao.queryRecord(appId, rowSelection);
			}

			@Override
			public List<OpAdAppLinkRecord> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return appLinkRecordDao.queryRecord(maxId, appId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return appLinkRecordDao.queryRecordCount(maxId, appId);
			}
		}, OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public void buildAppLink(final Integer open, final Integer phoneCode, final int maxSerial, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap, new SerializableListAdapter<OpAdAppLink>() {

			@Override
			public List<OpAdAppLink> getSerializables(RowSelection rowSelection) {
				return appLinkDao.queryAppLink(open, phoneCode, rowSelection);
			}

			@Override
			public List<OpAdAppLink> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return appLinkDao.queryAppLink(maxId, open, phoneCode, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return appLinkDao.queryAppLinkCount(maxId, open, phoneCode);
			}
		}, OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}

	@Override
	public void updateAppLinkByJSON(String json) throws Exception {
		JSONArray jsArray = JSONArray.fromObject(json);
		for(int i = 0; i < jsArray.size(); i++) {
			JSONObject jsObj = jsArray.getJSONObject(i);
			Integer id = jsObj.getInt("id");
			String appName = StringUtil.convertEmpty2NULL(jsObj.getString("appName"));
			String appIcon = StringUtil.convertEmpty2NULL(jsObj.getString("appIcon"));
			String appDesc = StringUtil.convertEmpty2NULL(jsObj.getString("appDesc"));
			String appLink = StringUtil.convertEmpty2NULL(jsObj.getString("appLink"));
			Integer phoneCode = jsObj.getInt("phoneCode");
			OpAdAppLink link = new OpAdAppLink();
			link.setAppName(appName);
			link.setAppIcon(appIcon);
			link.setAppDesc(appDesc);
			link.setAppLink(appLink);
			link.setPhoneCode(phoneCode);
			link.setId(id);
			appLinkDao.updateAppLink(link);
		}
	}

	@Override
	public void deleteAppLink(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		appLinkDao.updateOpenByIds(ids, Tag.TRUE);
	}
	
}
