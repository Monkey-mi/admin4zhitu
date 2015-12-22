package com.imzhitu.admin.statistics.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.statistics.pojo.StatPvDto;

/**
 * 统计业务逻辑访问接口
 * 
 * @author lynch 2015-12-19
 *
 */
public interface StatService extends BaseService {

	/**
	 * 构建PV列表
	 * 
	 * @param pv
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildPV(StatPvDto pv, String beginDateStr, String endDateStr, 
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 获取统计名称列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<StatPvDto> getStatKeyNameList() throws Exception;
	
}
