package com.imzhitu.admin.logger;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.logger.dao.UserOperationDao;

public class OperationLoggerImpl extends BaseServiceImpl implements
		OperationLogger {

	private Integer maxArgsLength = 500;

	private UserOperationDao loggerUserOperationDao;

	public UserOperationDao getLoggerUserOperationDao() {
		return loggerUserOperationDao;
	}

	public void setLoggerUserOperationDao(
			UserOperationDao loggerUserOperationDao) {
		this.loggerUserOperationDao = loggerUserOperationDao;
	}

	public Integer getMaxArgsLength() {
		return maxArgsLength;
	}

	public void setMaxArgsLength(Integer maxArgsLength) {
		this.maxArgsLength = maxArgsLength;
	}

	@Override
	public void saveUserOperationLog(ProceedingJoinPoint joinPoint)
			throws Exception {
		Integer uid = 0;
		String optArgs = null;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			uid = ((AdminUserDetails) auth.getPrincipal()).getId();
		}
		try {
			joinPoint.proceed();
		} catch (Throwable e) {
			throw new Exception(e);
		}

//		if (uid == 0) // 非登录用户，不作记录
//			return;

		// 获取参数
		Object[] args = joinPoint.getArgs();
		if (args.length > 0) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				if (i > 0) {
					builder.append(",");
				}
				builder.append(args[i]);
			}
			if (builder.length() > maxArgsLength) {
				builder.substring(0, maxArgsLength);
			}
			optArgs = builder.toString();
		}

		String optName = joinPoint.getSignature().toString();
		int optId = optName.hashCode();
		loggerUserOperationDao.saveLog(uid, optId, optArgs, new Date());

	}
	
}
