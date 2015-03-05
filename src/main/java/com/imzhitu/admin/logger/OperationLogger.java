package com.imzhitu.admin.logger;

import org.aspectj.lang.ProceedingJoinPoint;

import com.hts.web.common.service.BaseService;

/**
 * 用户操作日志业务逻辑访问接口
 * 
 * @author tianjie
 *
 */
public interface OperationLogger extends BaseService {

	/**
	 * 保存用户操作日志
	 * 
	 * @param joinPoint
	 * @throws Exception
	 */
	public void saveUserOperationLog(ProceedingJoinPoint joinPoint) throws Exception;
}
