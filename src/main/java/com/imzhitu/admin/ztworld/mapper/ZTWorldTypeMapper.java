package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import com.hts.web.common.pojo.HTWorldType;

/**
 * <p>
 * 织图分类数据访问接口
 * </p>
 * 
 * @author lynch
 *
 */
public interface ZTWorldTypeMapper {

	/**
	 * 查询缓存分类列表
	 * 
	 * @return
	 */
	public List<HTWorldType> queryCacheType();
}
