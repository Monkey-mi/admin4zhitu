package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivityLogo;
import com.imzhitu.admin.common.pojo.OpActivityLogoDto;

/**
 * <p>
 * 活动Logo数据访问接口
 * </p>
 * 
 * 创建时间：2014-3-22
 * @author tianjie
 *
 */
public interface ActivityLogoDao extends BaseDao {

	/**
	 * 保存活动logo
	 * 
	 * @param logo
	 */
	public void saveLogo(OpActivityLogo logo);
	
	/**
	 * 查询活动logo
	 * 
	 * @param activityId
	 * @param rowSelection
	 * @return
	 */
	public List<OpActivityLogoDto> queryLogoDto(RowSelection rowSelection);
	
	/**
	 * 查询活动logo
	 * 
	 * @param maxSerial
	 * @param activityId
	 * @param rowSelection
	 * @return
	 */
	public List<OpActivityLogoDto> queryLogoDto(Integer maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询活动logo总数
	 * 
	 * @param maxSerial
	 * @return
	 */
	public long queryLogoCount(Integer maxSerial);
	
	/**
	 * 根据ids删除logo
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 查询所有有效logo
	 * 
	 * @return
	 */
	public List<OpActivityLogo> queryValidLogo();
	
	/**
	 * 更新有效性
	 * 
	 * @param ids
	 * @param valid
	 */
	public void updateValidByIds(Integer[] ids, Integer valid);
	
	/**
	 * 根据活动id更新有效性
	 * 
	 * @param activityIds
	 * @param valid
	 */
	public void updateValidByActivityIds(Integer[] activityIds, Integer valid);
	
	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateSeria(Integer id, Integer serial);
	
}
