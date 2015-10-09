package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.AdminAndUserRelationshipDto;

/**
 * 管理员账号与织图用户关联关系操作映射类
 *
 * @author zhangbo 2015年5月13日
 */
public interface AdminAndUserRelationshipMapper {
    
    /**
     * 通过DTO增加管理员账号与织图用户关联关系
     *
     * @param dto
     * @return
     * @author zhangbo 2015年5月14日
     */
    @DataSource("master")
    public void addByDTO(AdminAndUserRelationshipDto dto);
    
    /**
     * 返回全表结果集
     *
     * @return	List<AdminAndUserRelationshipDto> 结果集
     * @author zhangbo 2015年5月15日
     */
    @DataSource("slave")
    public List<AdminAndUserRelationshipDto> queryAllResults();
    
    /**
     * 通过DTO查询结果集，DTO中主要存储管理员id
     *
     * @param dto
     * @return	List<AdminAndUserRelationshipDto> 结果集
     * @author zhangbo 2015年5月14日
     */
    @DataSource("slave")
    public List<AdminAndUserRelationshipDto> queryResultsByDTO(AdminAndUserRelationshipDto dto);
    
    /**
     * 通过DTO更新管理员账号与织图用户关联关系
     *
     * @param dto
     * @return
     * @author zhangbo 2015年5月14日
     */
    @DataSource("master")
    public void updateByDTO(AdminAndUserRelationshipDto dto);
    
    /**
     * 根据DTO来删除（硬删除）
     *
     * @param dto
     * @return
     * @author zhangbo 2015年5月13日
     */
    @DataSource("master")
    public void deleteByDTO(AdminAndUserRelationshipDto dto);
    
    /**
     * 根据管理员账号id与织图用户id来查询管理员账号与织图用户的关联关系
     * 
     * @param adminUserId	管理员账号id
     * @param userId		织图用户id
     * @return
     * @author zhangbo	2015年10月9日
     */
    @DataSource("slave")
	public AdminAndUserRelationshipDto queryByAdminIdAndUserId(@Param("adminUserId")Integer adminUserId, @Param("userId")Integer userId);
    
    /**
     * 根据关联关系表主键id来查询管理员账号与织图用户的关联关系
     * 
     * @param id	关联关系表主键id	
     * @return
     * @author zhangbo	2015年10月9日
     */
    @DataSource("slave")
    public AdminAndUserRelationshipDto queryById(@Param("id")Integer id);
}
