package com.imzhitu.admin.userinfo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.op.dao.OpUserVerifyDtoCacheDao;
import com.imzhitu.admin.userinfo.dao.UserVerifyCacheDao;
import com.imzhitu.admin.userinfo.dao.UserVerifyDao;
import com.imzhitu.admin.userinfo.service.UserVerifyService;

/**
 * <p>
 * 用户认证业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2014-7-16
 * @author tianjie
 *
 */
@Service
public class UserVerifyServiceImpl extends BaseServiceImpl implements
		UserVerifyService {

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private UserVerifyDao userVerifyDao;
	
	@Autowired
	private OpUserVerifyDtoCacheDao opUserVerifyDtoCacheDao;
	
	@Autowired
	private UserVerifyCacheDao userVerifyCacheDao;
	
	@Override
	public void buildVerify(Integer maxSerial, int start, int limit, Map<String, Object> jsonMap)
			throws Exception {
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap,
				new SerializableListAdapter<UserVerify>() {

					@Override
					public List<UserVerify> getSerializables(
							RowSelection rowSelection) {
						return userVerifyDao.queryVerify(rowSelection);
					}

					@Override
					public List<UserVerify> getSerializableByMaxId(int maxId,
							RowSelection rowSelection) {
						return userVerifyDao.queryVerify(maxId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return userVerifyDao.queryVerifyCount(maxId);
					}
					
				}, OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}
	
	@Override
	public void buildVerify(Boolean addAllTag, Map<String, Object> jsonMap) throws Exception {
		List<UserVerify> list = userVerifyDao.queryAllVerify();
		if(addAllTag) {
			list.add(0, new UserVerify(0, "所有认证", "所有认证", null, 9999));
		}
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, list);
	}

	@Override
	public void saveVerify(String verifyName, String verifyDesc,
			String verifyIcon) throws Exception {
		Integer id = webKeyGenService.generateId(
				KeyGenServiceImpl.USER_VERIFY_ID);
		userVerifyDao.saveVerify(new UserVerify(id, verifyName, verifyDesc, verifyIcon, id));
	}

	@Override
	public void updateVerify(Integer id, String verifyName, String verifyDesc,
			String verifyIcon, Integer serial) throws Exception {
		userVerifyDao.updateVerify(new UserVerify(id, verifyName, verifyDesc, 
				verifyIcon, serial));
	}

	@Override
	public void deleteVerify(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		userVerifyDao.deleteVerify(ids);
	}

	@Override
	public void updateVerifySerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.USER_VERIFY_ID);
				userVerifyDao.updateVerifySerial(id, serial);
			}
		}
	}

	@Override
	public void updateVerifyCache(int limit) throws Exception {
		opUserVerifyDtoCacheDao.updateVerifyDto(new RowSelection(1, limit));
		userVerifyCacheDao.updateVerify();
	}

	@Override
	public UserVerify queryVerify(Integer id) throws Exception {
		return userVerifyDao.queryVerify(id);
	}


}
