package com.imzhitu.admin.privileges.dao;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public interface UserLoginPersistentDao extends PersistentTokenRepository {

	/**
	 * 根据用户id查询Token
	 * 
	 * @param userId
	 * @return
	 */
	public PersistentRememberMeToken queryTokenByUserId(Integer userId);
}
