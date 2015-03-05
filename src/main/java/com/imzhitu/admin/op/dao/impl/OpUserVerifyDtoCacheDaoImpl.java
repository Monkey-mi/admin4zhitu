package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.common.pojo.UserVerify;
import com.imzhitu.admin.op.dao.OpUserVerifyDtoCacheDao;
import com.imzhitu.admin.userinfo.dao.UserVerifyDao;

@Repository
public class OpUserVerifyDtoCacheDaoImpl extends
		BaseCacheDaoImpl<OpUserVerifyDto> implements OpUserVerifyDtoCacheDao {

	@Autowired
	private UserVerifyDao userVerifyDao;

	@Override
	public void updateVerifyDto(RowSelection rowSelection) {
		List<UserVerify> list = userVerifyDao.queryVerify(rowSelection);
		if (getRedisTemplate().hasKey(CacheKeies.OP_USER_VERIFY)) {
			getRedisTemplate().delete(CacheKeies.OP_USER_VERIFY);
		}
		if (list.size() > 0) {
			OpUserVerifyDto[] dtos = new OpUserVerifyDto[list.size()];
			for (int i = 0; i < dtos.length; i++) {
				UserVerify verify = list.get(i);
				dtos[i] = new OpUserVerifyDto(verify.getId(),
						verify.getVerifyName(), verify.getVerifyDesc(),
						verify.getVerifyIcon());
			}
			getRedisTemplate().opsForList().rightPushAll(
					CacheKeies.OP_USER_VERIFY, dtos);
		}
	}
}
