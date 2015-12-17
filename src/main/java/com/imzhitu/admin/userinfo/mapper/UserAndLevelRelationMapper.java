package com.imzhitu.admin.userinfo.mapper;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.UserLevelDto;

/**
 * 用户与用户等级关联关系数据操作类
 * 
 * @author zhangbo	2015年12月17日
 *
 */
public interface UserAndLevelRelationMapper {

	/**
	 * 根据用户id，获取用户等级对象
	 * 
	 * @param uid	用户id
	 * @throws Exception
	 * @author zhangbo	2015年12月17日
	 */
    @DataSource("slave")
    UserLevelDto getUserLevel(@Param("uid")Integer uid);
}
