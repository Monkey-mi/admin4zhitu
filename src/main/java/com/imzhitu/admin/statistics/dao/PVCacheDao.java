package com.imzhitu.admin.statistics.dao;

import java.util.List;
import java.util.Set;

import com.imzhitu.admin.statistics.pojo.StatPvDto;

/**
 * PV缓存数据访问接口
 * 
 * @author lynch 2015-12-19
 *
 */
public interface PVCacheDao {

	/**
	 * 查询所有pv
	 * 
	 * @return
	 * @author lynch 2015-12-21
	 */
	public List<StatPvDto> queryAllPv();
	
	
	/**
	 * 设置pv数
	 * 
	 * @param list
	 */
	public void addReadTimePv(List<StatPvDto> list);
	
	/**
	 * 获取所有pv cache key
	 * 
	 * @return
	 * @author lynch 2015-12-21
	 */
	public Set<String> queryAllPvKey();
	
	/**
	 * 增加pv
	 * 
	 * @param pvkey
	 * @param subkey
	 * @param pv
	 * @author lynch 2015-12-21
	 */
	public void incPv(Integer pvkey, Integer subkey, long pv);
	
	/**
	 * 清空pv
	 * 
	 * @param pvkey
	 * @param subkey
	 * @author lynch 2015-12-21
	 */
	public void clearPv(Integer pvkey, Integer subkey);
	
	/**
	 * 根据缓存key获取pv
	 * 
	 * @param cacheKey
	 * @return
	 * @author lynch 2015-12-21
	 */
	public StatPvDto getPvDtoByKey(String cacheKey);

	/**
	 * 获取pv数
	 * 
	 * @param cacheKey
	 * @return
	 * @author lynch 2015-12-21
	 */
	public Long getPv(String cacheKey);
	
}
