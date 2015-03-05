package com.imzhitu.admin.common.service;

import com.hts.web.common.service.BaseService;

public interface KeyGenService extends BaseService {
	
	/**
	 * 生成id
	 * @param keyId
	 * @return
	 */
	public Integer generateId(Integer keyId);

}
