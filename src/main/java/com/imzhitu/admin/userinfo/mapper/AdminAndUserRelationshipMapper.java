package com.imzhitu.admin.userinfo.mapper;

import java.util.List;

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
}
