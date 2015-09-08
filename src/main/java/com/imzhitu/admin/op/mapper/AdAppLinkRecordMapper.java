package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.pojo.OpAdAppLinkRecord;

/**
 * <p>
 * 换量链接访问记录数据访问接口
 * </p>
 * 
 * 创建时间: 2015-09-07
 * @author lynch
 *
 */
public interface AdAppLinkRecordMapper {

	
	/**
	 * 查询点击链接
	 * 
	 * @param record
	 * @return
	 */
	public List<OpAdAppLinkRecord> queryRecord(OpAdAppLinkRecord record);
	

	/**
	 * 查询点击链接总数
	 * 
	 * @param record
	 * @return
	 */
	public long queryRecordCount(OpAdAppLinkRecord record);

	/**
	 * 删除点击记录
	 * 
	 * @param appId
	 */
	public void deleteByAppId(@Param("appId")Integer appId);
	
}
