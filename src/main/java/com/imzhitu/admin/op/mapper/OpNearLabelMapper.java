package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpNearLabelDto;

/**
 * 附近织图持久化 接口
 * @author zxx 2015-12-4 09:48:06
 *
 */
public interface OpNearLabelMapper {
	/**
	 * 插入
	 * @param dto
	 */
	void insertNearLabel(OpNearLabelDto dto);
	/**
	 * 更新
	 * @param dto
	 */
	void updateNearLabel(OpNearLabelDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDeleteNearLabel(Integer[] ids);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	List<OpNearLabelDto> queryNearLabel(OpNearLabelDto dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	long queryNearLabelTotalCount(OpNearLabelDto dto);
}
