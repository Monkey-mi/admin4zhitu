package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpActivityStar;


public interface ActivityStarMapper {

	
	public List<com.hts.web.common.pojo.OpActivityStar> queryCacheStar(OpActivityStar star);
	
}
