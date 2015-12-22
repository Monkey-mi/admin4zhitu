package com.imzhitu.admin.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.statistics.pojo.StatPvDto;

/**
 * pv数据访问接口
 * 
 * @author lynch 2015-12-21
 *
 */
public interface PvMapper {

	/**
	 * 批量保存pv
	 * 
	 * @param pvs
	 * @author lynch 2015-12-21
	 */
	public void savePvs(List<StatPvDto> pvs);
	
	/**
	 * 保存pv
	 * 
	 * @param pv
	 * @author lynch 2015-12-21
	 */
	public void savePv(StatPvDto pv);
	
	/**
	 * 查询pv列表
	 * 
	 * @param pv
	 * @return
	 * @author lynch 2015-12-21
	 */
	public List<StatPvDto> queryList(StatPvDto pv);

	/**
	 * 查询PV列表总数
	 * 
	 * @param pv
	 * @return
	 * @author lynch 2015-12-21
	 */
	public long queryTotal(StatPvDto pv);
	
	/**
	 * 查询pv
	 * 
	 * @param pvkey
	 * @param subkey
	 * @param pvtime
	 * @return
	 * @author lynch 2015-12-21
	 */
	public Integer queryPvId(@Param("pvkey")Integer pvkey, 
			@Param("subkey")Integer subkey, @Param("pvtime")Long pvtime);
	
	/**
	 * 根据id添加pv
	 * 
	 * @param id
	 * @param pv
	 * @author lynch 2015-12-21
	 */
	public void addPvById(@Param("id")Integer id, @Param("pv")Long pv);

}
